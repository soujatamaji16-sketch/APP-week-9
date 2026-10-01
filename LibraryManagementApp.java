import java.sql.*;
import java.util.Scanner;

public class LibraryManagementApp {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n=== Library Menu ===");
                System.out.println("1. Insert New Book");
                System.out.println("2. Search Book by ID");
                System.out.println("3. Display All Available Books");
                System.out.println("4. Update Book Availability (Issue/Return)");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Book ID: ");
                        int bid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Enter Availability (Available/Issued): ");
                        String avail = sc.nextLine();

                        String insertQuery = "INSERT INTO Book (BookID, Title, Author, Price, Availability) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                            ps.setInt(1, bid);
                            ps.setString(2, title);
                            ps.setString(3, author);
                            ps.setDouble(4, price);
                            ps.setString(5, avail);
                            ps.executeUpdate();
                            System.out.println(">> Book inserted successfully.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Book ID to search: ");
                        int sId = sc.nextInt();
                        String searchQuery = "SELECT * FROM Book WHERE BookID = ?";
                        try (PreparedStatement ps = con.prepareStatement(searchQuery)) {
                            ps.setInt(1, sId);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    System.out.println("\nFound Book Details:");
                                    System.out.println("ID           : " + rs.getInt("BookID"));
                                    System.out.println("Title        : " + rs.getString("Title"));
                                    System.out.println("Author       : " + rs.getString("Author"));
                                    System.out.println("Price        : ₹" + rs.getDouble("Price"));
                                    System.out.println("Availability : " + rs.getString("Availability"));
                                } else {
                                    System.out.println(">> Book with ID " + sId + " not found.");
                                }
                            }
                        }
                        break;

                    case 3:
                        String availQuery = "SELECT * FROM Book WHERE LOWER(Availability) = 'available'";
                        try (Statement stmt = con.createStatement();
                             ResultSet rs = stmt.executeQuery(availQuery)) {
                            System.out.printf("\n%-10s %-25s %-20s %-10s\n", "BookID", "Title", "Author", "Price");
                            System.out.println("\n");
                            boolean found = false;
                            while (rs.next()) {
                                found = true;
                                System.out.printf("%-10d %-25s %-20s %-10.2f\n",
                                        rs.getInt("BookID"),
                                        rs.getString("Title"),
                                        rs.getString("Author"),
                                        rs.getDouble("Price"));
                            }
                            if (!found) System.out.println("No books currently marked as available.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Book ID to change status: ");
                        int uId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Status (Available/Issued): ");
                        String newStatus = sc.nextLine();

                        String updateQuery = "UPDATE Book SET Availability = ? WHERE BookID = ?";
                        try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
                            ps.setString(1, newStatus);
                            ps.setInt(2, uId);
                            int rows = ps.executeUpdate();
                            if (rows > 0) System.out.println(">> Book status updated successfully.");
                            else System.out.println(">> Book ID not found.");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting application.");
                        return;

                    default:
                        System.out.println(">> Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}