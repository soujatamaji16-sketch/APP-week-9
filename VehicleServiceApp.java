import java.util.Scanner;

class VehicleServiceModel {
    private String regNo;
    private String vehicleType;
    private double totalCost;

    public void computeCost(String regNo, String vehicleType, boolean general, boolean oil, boolean brake, boolean battery) {
        this.regNo = regNo;
        this.vehicleType = vehicleType;
        this.totalCost = 0.0;

        if (general) totalCost += 1000;
        if (oil) totalCost += 800;
        if (brake) totalCost += 1200;
        if (battery) totalCost += 500;
    }

    public String getRegNo() { return regNo; }
    public String getVehicleType() { return vehicleType; }
    public double getTotalCost() { return totalCost; }
}

class VehicleServiceView {
    public void displayCostEstimate(String regNo, String type, double cost) {
        System.out.println("\n--- Service Cost Estimation ---");
        System.out.println("Registration No : " + regNo);
        System.out.println("Vehicle Type    : " + type);
        System.out.printf("Total Cost      : %.2f\n", cost);
    }
}

public class VehicleServiceApp {
    private VehicleServiceModel model;
    private VehicleServiceView view;

    public VehicleServiceApp(VehicleServiceModel model, VehicleServiceView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Vehicle Registration Number: ");
        String regNo = sc.nextLine();

        System.out.println("Select Vehicle Type:");
        System.out.println("1. Two Wheeler");
        System.out.println("2. Car");
        System.out.print("Enter choice (1/2): ");
        int typeChoice = sc.nextInt();
        String vehicleType = (typeChoice == 1) ? "Two Wheeler" : "Car";

        System.out.println("\nSelect Services (y/n):");
        System.out.print("General Service (1,000)? ");
        boolean general = sc.next().equalsIgnoreCase("y");
        System.out.print("Oil Change (800)? ");
        boolean oil = sc.next().equalsIgnoreCase("y");
        System.out.print("Brake Service (1,200)? ");
        boolean brake = sc.next().equalsIgnoreCase("y");
        System.out.print("Battery Check (500)? ");
        boolean battery = sc.next().equalsIgnoreCase("y");

        model.computeCost(regNo, vehicleType, general, oil, brake, battery);
        view.displayCostEstimate(model.getRegNo(), model.getVehicleType(), model.getTotalCost());
        sc.close();
    }

    public static void main(String[] args) {
        VehicleServiceModel model = new VehicleServiceModel();
        VehicleServiceView view = new VehicleServiceView();
        VehicleServiceApp app = new VehicleServiceApp(model, view);
        app.run();
    }
}