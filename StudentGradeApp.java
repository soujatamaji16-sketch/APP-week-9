import java.util.Scanner;

class StudentModel {
    private String name;
    private double total;
    private double average;
    private String grade;

    public void calculate(String name, double m1, double m2, double m3) {
        this.name = name;
        this.total = m1 + m2 + m3;
        this.average = total / 3.0;

        if (average >= 90) grade = "A";
        else if (average >= 75) grade = "B";
        else if (average >= 60) grade = "C";
        else if (average >= 50) grade = "D";
        else grade = "F";
    }

    public String getName() { return name; }
    public double getTotal() { return total; }
    public double getAverage() { return average; }
    public String getGrade() { return grade; }
}

class StudentView {
    public void displayResult(String name, double total, double average, String grade) {
        System.out.println("\n--- Result ---");
        System.out.println("Student Name : " + name);
        System.out.printf("Total Marks  : %.2f\n", total);
        System.out.printf("Average Marks: %.2f\n", average);
        System.out.println("Grade        : " + grade);
    }
}

public class StudentGradeApp {
    private StudentModel model;
    private StudentView view;

    public StudentGradeApp(StudentModel model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter marks for Subject 1: ");
        double m1 = sc.nextDouble();
        System.out.print("Enter marks for Subject 2: ");
        double m2 = sc.nextDouble();
        System.out.print("Enter marks for Subject 3: ");
        double m3 = sc.nextDouble();

        model.calculate(name, m1, m2, m3);
        view.displayResult(model.getName(), model.getTotal(), model.getAverage(), model.getGrade());
        sc.close();
    }

    public static void main(String[] args) {
        StudentModel model = new StudentModel();
        StudentView view = new StudentView();
        StudentGradeApp app = new StudentGradeApp(model, view);
        app.run();
    }
}