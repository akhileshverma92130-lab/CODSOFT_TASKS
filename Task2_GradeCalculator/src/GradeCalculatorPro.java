import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.time.DateTimeException;
import java.util.*;

public class GradeCalculatorPro {
    private static final int MAX_MARKS = 100;
    private static final int PASSING_PERCENTAGE = 33;
    private static final String DATA_FILE = "students_data.csv";

    private final Scanner scanner = new Scanner(System.in);
    private final List<Student> students = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        GradeCalculatorPro app = new GradeCalculatorPro();
        app.loadData();
        app.showMainMenu();
    }

    // ========== MAIN MENU ==========
    private void showMainMenu() {
        while (true) {
            printHeader("STUDENT GRADE CALCULATOR PRO");
            System.out.println("1. ➕ Add New Student");
            System.out.println("2. 📋 View All Students");
            System.out.println("3. 🔍 Search Student");
            System.out.println("4. 📊 View Statistics");
            System.out.println("5. 💾 Save & Export");
            System.out.println("6. ⚙️  Settings");
            System.out.println("0. 🚪 Exit");
            System.out.print("Choose option: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> addStudent();
                case "2" -> viewAllStudents();
                case "3" -> searchStudent();
                case "4" -> showStatistics();
                case "5" -> saveAndExport();
                case "6" -> showSettings();
                case "0" -> { saveData(); System.out.println("👋 Goodbye!"); return; }
                default -> System.out.println("⚠️ Invalid option!");
            }
            pressEnterToContinue();
        }
    }

    // ========== ADD STUDENT ==========
    private void addStudent() {
        printHeader("ADD NEW STUDENT");

        System.out.print("Student Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("⚠️ Name cannot be empty!"); return; }

        System.out.print("Roll Number: ");
        String rollNo = scanner.nextLine().trim();
        if (rollNo.isEmpty()) { System.out.println("⚠️ Roll number cannot be empty!"); return; }

        // Check duplicate
        if (students.stream().anyMatch(s -> s.rollNo.equalsIgnoreCase(rollNo))) {
            System.out.println("⚠️ Roll number already exists!");
            return;
        }

        int numSubjects = getValidInt("Number of subjects (1-15): ", 1, 15);
        List<Subject> subjects = new ArrayList<>();

        for (int i = 0; i < numSubjects; i++) {
            System.out.println("\n--- Subject " + (i + 1) + " ---");
            System.out.print("Subject Name (press Enter for default): ");
            String subName = scanner.nextLine().trim();
            if (subName.isEmpty()) subName = "Subject " + (i + 1);

            int marks = getValidInt("Marks (0-" + MAX_MARKS + "): ", 0, MAX_MARKS);
            subjects.add(new Subject(subName, marks));
        }

        Student student = new Student(name, rollNo, subjects, LocalDateTime.now());
        students.add(student);
        System.out.println("\n✅ Student added successfully!");
        student.displayReportCard();
    }

    // ========== VIEW ALL ==========
    private void viewAllStudents() {
        printHeader("ALL STUDENTS (" + students.size() + ")");
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %8s %8s %6s %s%n",
                "No.", "Name", "Roll No", "Total", "Avg%", "Grade", "Status");
        System.out.println("-".repeat(85));

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%-5d %-20s %-15s %8d %7.2f%% %6s %s%n",
                    i + 1, truncate(s.name, 20), truncate(s.rollNo, 15),
                    s.getTotalMarks(), s.getAverage(), s.getGrade(), s.getStatus());
        }
    }

    // ========== SEARCH ==========
    private void searchStudent() {
        printHeader("SEARCH STUDENT");
        System.out.print("Enter Name or Roll No: ");
        String query = scanner.nextLine().trim().toLowerCase();

        List<Student> found = students.stream()
                .filter(s -> s.name.toLowerCase().contains(query) || s.rollNo.toLowerCase().contains(query))
                .toList();

        if (found.isEmpty()) {
            System.out.println("❌ No student found.");
        } else {
            for (Student s : found) s.displayReportCard();
        }
    }

    // ========== STATISTICS ==========
    private void showStatistics() {
        printHeader("CLASS STATISTICS");
        if (students.isEmpty()) { System.out.println("No data available."); return; }

        // Overall stats
        double classAvg = students.stream().mapToDouble(Student::getAverage).average().orElse(0);
        int highest = students.stream().mapToInt(Student::getTotalMarks).max().orElse(0);
        int lowest = students.stream().mapToInt(Student::getTotalMarks).min().orElse(0);
        long passCount = students.stream().filter(Student::isPassed).count();
        long failCount = students.size() - passCount;

        System.out.println("📊 Overall Class Performance:");
        System.out.printf("   Students: %d | Pass: %d | Fail: %d | Pass Rate: %.1f%%%n",
                students.size(), passCount, failCount, (passCount * 100.0 / students.size()));
        System.out.printf("   Class Average: %.2f%% | Highest: %d | Lowest: %d%n", classAvg, highest, lowest);

        // Subject-wise stats
        if (!students.isEmpty()) {
            Map<String, List<Integer>> subjectMarks = new LinkedHashMap<>();
            for (Student s : students) {
                for (Subject sub : s.subjects) {
                    subjectMarks.computeIfAbsent(sub.name, k -> new ArrayList<>()).add(sub.marks);
                }
            }

            System.out.println("\n📚 Subject-wise Analysis:");
            System.out.printf("%-20s %8s %8s %8s %8s%n", "Subject", "Avg", "Max", "Min", "Pass%");
            System.out.println("-".repeat(55));

            for (var entry : subjectMarks.entrySet()) {
                List<Integer> marks = entry.getValue();
                double avg = marks.stream().mapToInt(Integer::intValue).average().orElse(0);
                int max = marks.stream().max(Integer::compare).orElse(0);
                int min = marks.stream().min(Integer::compare).orElse(0);
                long pass = marks.stream().filter(m -> m >= PASSING_PERCENTAGE).count();
                System.out.printf("%-20s %8.2f %8d %8d %7.1f%%%n",
                        truncate(entry.getKey(), 20), avg, max, min, (pass * 100.0 / marks.size()));
            }
        }

        // Grade distribution
        System.out.println("\n🎓 Grade Distribution:");
        Map<String, Long> gradeDist = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));

        String[] grades = {"A+", "A", "B+", "B", "C", "D", "E", "F"};
        for (String g : grades) {
            long count = gradeDist.getOrDefault(g, 0L);
            String bar = "█".repeat((int)(count * 2));
            System.out.printf("   %s: %2d %s%n", g, count, bar);
        }
    }

    // ========== SAVE & EXPORT ==========
    private void saveAndExport() {
        printHeader("SAVE & EXPORT");
        System.out.println("1. Save to CSV (data backup)");
        System.out.println("2. Export Report Cards (Text files)");
        System.out.println("3. Export Summary Report");
        System.out.print("Choose: ");

        switch (scanner.nextLine().trim()) {
            case "1" -> { saveData(); System.out.println("✅ Data saved to " + DATA_FILE); }
            case "2" -> exportReportCards();
            case "3" -> exportSummaryReport();
            default -> System.out.println("⚠️ Invalid option");
        }
    }

    private void exportReportCards() {
        try {
            Path dir = Paths.get("report_cards");
            Files.createDirectories(dir);

            for (Student s : students) {
                String filename = s.rollNo + "_" + s.name.replace(" ", "_") + ".txt";
                Path path = dir.resolve(filename);
                Files.writeString(path, s.generateReportCard());
            }
            System.out.println("✅ Report cards exported to 'report_cards/' folder");
        } catch (IOException e) {
            System.out.println("❌ Export failed: " + e.getMessage());
        }
    }

    private void exportSummaryReport() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("CLASS SUMMARY REPORT\n");
            sb.append("Generated: ").append(LocalDateTime.now().format(formatter)).append("\n");
            sb.append("=".repeat(60)).append("\n\n");

            for (Student s : students) {
                sb.append(s.generateReportCard()).append("\n\n");
            }

            Files.writeString(Paths.get("Class_Summary_Report.txt"), sb.toString());
            System.out.println("✅ Summary report saved as 'Class_Summary_Report.txt'");
        } catch (IOException e) {
            System.out.println("❌ Export failed: " + e.getMessage());
        }
    }

    // ========== SETTINGS ==========
    private void showSettings() {
        printHeader("SETTINGS");
        System.out.println("Current Passing Percentage: " + PASSING_PERCENTAGE + "%");
        System.out.println("Max Marks per Subject: " + MAX_MARKS);
        System.out.println("Data File: " + DATA_FILE);
        System.out.println("\n(Modify constants in code to change these)");
    }

    // ========== FILE I/O ==========
    private void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            // Header
            pw.println("Name,RollNo,SubjectName,Marks,Timestamp");
            for (Student s : students) {
                for (Subject sub : s.subjects) {
                    pw.printf("%s,%s,%s,%d,%s%n",
                            escapeCsv(s.name), escapeCsv(s.rollNo),
                            escapeCsv(sub.name), sub.marks, s.timestamp.format(formatter));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Save failed: " + e.getMessage());
        }
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            Map<String, Student> studentMap = new LinkedHashMap<>();

            while ((line = br.readLine()) != null) {
                String[] parts = parseCsvLine(line);
                if (parts.length < 5) continue;

                String name = parts[0], rollNo = parts[1], subName = parts[2];
                int marks = Integer.parseInt(parts[3]);
                LocalDateTime time = LocalDateTime.parse(parts[4], formatter);

                String key = name + "|" + rollNo;
                Student student = studentMap.computeIfAbsent(key, k -> new Student(name, rollNo, new ArrayList<>(), time));
                student.subjects.add(new Subject(subName, marks));
            }
            students.addAll(studentMap.values());
            System.out.println("📂 Loaded " + students.size() + " students from " + DATA_FILE);
        } catch (IOException | DateTimeException e) {
            System.out.println("⚠️ Load failed: " + e.getMessage());
        }
    }

    // ========== UTILITIES ==========
    private int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("⚠️ Enter value between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number!");
            }
        }
    }

    private void printHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   " + title);
        System.out.println("=".repeat(50));
    }

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private String truncate(String s, int len) {
        return s.length() > len ? s.substring(0, len - 3) + "..." : s;
    }

    private String escapeCsv(String s) {
        return s.contains(",") || s.contains("\"") ? "\"" + s.replace("\"", "\"\"") + "\"" : s;
    }

    private String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else sb.append(c);
        }
        result.add(sb.toString());
        return result.toArray(new String[0]);
    }

    // ========== INNER CLASSES ==========
    static class Subject {
        String name;
        int marks;
        Subject(String name, int marks) { this.name = name; this.marks = marks; }
    }

    class Student {
        final String name, rollNo;
        final List<Subject> subjects = new ArrayList<>();
        final LocalDateTime timestamp;

        Student(String name, String rollNo, List<Subject> subjects, LocalDateTime timestamp) {
            this.name = name; this.rollNo = rollNo;
            this.subjects.addAll(subjects); this.timestamp = timestamp;
        }

        int getTotalMarks() { return subjects.stream().mapToInt(s -> s.marks).sum(); }
        double getAverage() { return subjects.isEmpty() ? 0 : (double) getTotalMarks() / subjects.size(); }
        boolean isPassed() { return getAverage() >= PASSING_PERCENTAGE; }

        String getGrade() {
            double avg = getAverage();
            if (avg >= 90) return "A+";
            if (avg >= 80) return "A";
            if (avg >= 70) return "B+";
            if (avg >= 60) return "B";
            if (avg >= 50) return "C";
            if (avg >= 40) return "D";
            if (avg >= PASSING_PERCENTAGE) return "E";
            return "F";
        }

        String getStatus() { return isPassed() ? "✅ PASS" : "❌ FAIL"; }

        void displayReportCard() {
            System.out.println("\n" + "=".repeat(50));
            System.out.printf("   📋 REPORT CARD: %s (%s)%n", name, rollNo);
            System.out.println("=".repeat(50));
            System.out.printf("%-25s %5s %6s%n", "Subject", "Marks", "Grade");
            System.out.println("-".repeat(50));

            for (Subject sub : subjects) {
                String subGrade = getSubjectGrade(sub.marks);
                System.out.printf("%-25s %3d/%-3d %6s%n", truncate(sub.name, 25), sub.marks, MAX_MARKS, subGrade);
            }
            System.out.println("-".repeat(50));
            System.out.printf("%-25s %3d/%-3d%n", "TOTAL", getTotalMarks(), subjects.size() * MAX_MARKS);
            System.out.printf("%-25s %.2f%%%n", "AVERAGE", getAverage());
            System.out.printf("%-25s %s%n", "GRADE", getGrade());
            System.out.printf("%-25s %s%n", "STATUS", getStatus());
            System.out.printf("%-25s %s%n", "GPA", String.format("%.2f", calculateGPA()));
            System.out.println("=".repeat(50));
        }

        String generateReportCard() {
            StringBuilder sb = new StringBuilder();
            sb.append("REPORT CARD\n");
            sb.append("Name: ").append(name).append("\n");
            sb.append("Roll No: ").append(rollNo).append("\n");
            sb.append("Date: ").append(timestamp.format(formatter)).append("\n");
            sb.append("-".repeat(40)).append("\n");
            sb.append(String.format("%-25s %5s %6s%n", "Subject", "Marks", "Grade"));
            sb.append("-".repeat(40)).append("\n");
            for (Subject sub : subjects) {
                sb.append(String.format("%-25s %3d/%-3d %6s%n",
                        truncate(sub.name, 25), sub.marks, MAX_MARKS, getSubjectGrade(sub.marks)));
            }
            sb.append("-".repeat(40)).append("\n");
            sb.append(String.format("Total: %d/%d%n", getTotalMarks(), subjects.size() * MAX_MARKS));
            sb.append(String.format("Average: %.2f%%%n", getAverage()));
            sb.append(String.format("Grade: %s%n", getGrade()));
            sb.append(String.format("Status: %s%n", getStatus()));
            sb.append(String.format("GPA: %.2f%n", calculateGPA()));
            return sb.toString();
        }

        private String getSubjectGrade(int marks) {
            double pct = (marks * 100.0) / MAX_MARKS;
            if (pct >= 90) return "A+";
            if (pct >= 80) return "A";
            if (pct >= 70) return "B+";
            if (pct >= 60) return "B";
            if (pct >= 50) return "C";
            if (pct >= 40) return "D";
            if (pct >= PASSING_PERCENTAGE) return "E";
            return "F";
        }

        private double calculateGPA() {
            // 10-point scale: A+=10, A=9, B+=8, B=7, C=6, D=5, E=4, F=0
            return subjects.stream()
                    .mapToDouble(s -> {
                        double pct = (s.marks * 100.0) / MAX_MARKS;
                        if (pct >= 90) return 10;
                        if (pct >= 80) return 9;
                        if (pct >= 70) return 8;
                        if (pct >= 60) return 7;
                        if (pct >= 50) return 6;
                        if (pct >= 40) return 5;
                        if (pct >= PASSING_PERCENTAGE) return 4;
                        return 0;
                    })
                    .average().orElse(0);
        }
    }
}