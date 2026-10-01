import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class EmployeeModel {
    private String currentPassword = "admin123";
    private final String username = "admin";
    private List<String> employeeRecords = new ArrayList<>();

    public boolean validateCredentials(String user, String pass) {
        return this.username.equals(user) && this.currentPassword.equals(pass);
    }

    public boolean changePassword(String oldPass, String newPass, String confirmPass) {
        if (!this.currentPassword.equals(oldPass)) return false;
        if (!newPass.equals(confirmPass) || newPass.trim().isEmpty()) return false;
        this.currentPassword = newPass;
        return true;
    }

    public void addEmployee(String id, String name, String dept) {
        employeeRecords.add("ID: " + id + " | Name: " + name + " | Dept: " + dept);
    }

    public List<String> getEmployees() {
        return employeeRecords;
    }
}

class EmployeeView {
    public void showMessage(String msg) {
        System.out.println(">> " + msg);
    }

    public void displayEmployees(List<String> list) {
        System.out.println("\n--- Employee Records ---");
        if (list.isEmpty()) {
            System.out.println("No employees registered yet.");
        } else {
            for (String emp : list) {
                System.out.println(emp);
            }
        }
    }
}

public class EmployeePortalApp {
    private EmployeeModel model;
    private EmployeeView view;

    public EmployeePortalApp(EmployeeModel model, EmployeeView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Portal Login ===");
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        if (!model.validateCredentials(u, p)) {
            view.showMessage("Invalid credentials. Exiting application.");
            return;
        }

        view.showMessage("Login Successful!\n");

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");
            System.out.println("5. Exit Application");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (ch) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    model.addEmployee(id, name, dept);
                    view.showMessage("Employee added successfully.");
                    break;
                case 2:
                    view.displayEmployees(model.getEmployees());
                    break;
                case 3:
                    System.out.print("Enter Old Password: ");
                    String oldP = sc.nextLine();
                    System.out.print("Enter New Password: ");
                    String newP = sc.nextLine();
                    System.out.print("Confirm New Password: ");
                    String confP = sc.nextLine();
                    if (!newP.equals(confP)) {
                        view.showMessage("Error: New and Confirm passwords do not match.");
                    } else if (model.changePassword(oldP, newP, confP)) {
                        view.showMessage("Password changed successfully.");
                    } else {
                        view.showMessage("Error: Old password is incorrect.");
                    }
                    break;
                case 4:
                    view.showMessage("Logged out.");
                    return;
                case 5:
                    view.showMessage("Exiting Application.");
                    System.exit(0);
                default:
                    view.showMessage("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) {
        EmployeeModel model = new EmployeeModel();
        EmployeeView view = new EmployeeView();
        EmployeePortalApp app = new EmployeePortalApp(model, view);
        app.run();
    }
}