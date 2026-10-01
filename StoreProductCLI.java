import java.sql.*;
import java.util.Scanner;

public class StoreProductCLI {
    private static final String URL = "jdbc:mysql://localhost:3306/store_db";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- Product Store Operations ---");
                System.out.println("1. Insert New Product");
                System.out.println("2. Retrieve Product using Product ID");
                System.out.println("3. Update Product Quantity");
                System.out.println("4. Display All Products with Quantity Below 10");
                System.out.println("5. Exit");
                System.out.print("Enter option: ");
                int opt = sc.nextInt();

                switch (opt) {
                    case 1:
                        System.out.print("Enter Product ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Product Name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        String insertSQL = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                            ps.setInt(1, pid);
                            ps.setString(2, name);
                            ps.setDouble(3, price);
                            ps.setInt(4, qty);
                            ps.executeUpdate();
                            System.out.println("Status: Product added successfully.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Product ID to search: ");
                        int sId = sc.nextInt();
                        String searchSQL = "SELECT * FROM Product WHERE ProductID = ?";
                        try (PreparedStatement ps = con.prepareStatement(searchSQL)) {
                            ps.setInt(1, sId);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    System.out.printf("\n[Found] ID: %d | Name: %s | Price: Rs. %.2f | Quantity: %d\n",
                                            rs.getInt("ProductID"), rs.getString("ProductName"),
                                            rs.getDouble("Price"), rs.getInt("Quantity"));
                                } else {
                                    System.out.println("Status: Product not found.");
                                }
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter Product ID to update: ");
                        int uId = sc.nextInt();
                        System.out.print("Enter New Quantity: ");
                        int newQty = sc.nextInt();

                        String updateSQL = "UPDATE Product SET Quantity = ? WHERE ProductID = ?";
                        try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
                            ps.setInt(1, newQty);
                            ps.setInt(2, uId);
                            int rows = ps.executeUpdate();
                            if (rows > 0) System.out.println("Status: Quantity updated successfully.");
                            else System.out.println("Status: Product ID not found.");
                        }
                        break;

                    case 4:
                        String lowStockSQL = "SELECT * FROM Product WHERE Quantity < 10";
                        try (PreparedStatement ps = con.prepareStatement(lowStockSQL);
                             ResultSet rs = ps.executeQuery()) {
                            System.out.printf("\n%-10s %-25s %-12s %-10s\n", "ProductID", "ProductName", "Price", "Quantity");
                            System.out.println("-----------------------------------------------------------");
                            boolean found = false;
                            while (rs.next()) {
                                found = true;
                                System.out.printf("%-10d %-25s %-12.2f %-10d\n",
                                        rs.getInt("ProductID"), rs.getString("ProductName"),
                                        rs.getDouble("Price"), rs.getInt("Quantity"));
                            }
                            if (!found) System.out.println("No products found with quantity below 10.");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting application.");
                        return;

                    default:
                        System.out.println("Invalid selection. Try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}