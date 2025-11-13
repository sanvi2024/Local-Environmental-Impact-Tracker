import java.util.Scanner;

public class EnvironmentalTracker {

    private static final double DEV_CO2 = 0.05;
    private static final double CAR_CO2 = 0.18;
    private static final double BIKE_CO2 = 0.0;
    private static final double WALK_CO2 = 0.0;

    private double devHrs = 0;
    private double carKms = 0;
    private double bikeKms = 0;
    private double walkKms = 0;

    private Scanner sc;

    public EnvironmentalTracker() {
        this.sc = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    logDevice();
                    break;
                case 2:
                    logTransport();
                    break;
                case 3:
                    showReport();
                    break;
                case 4:
                    showTips();
                    break;
                case 5:
                    running = false;
                    System.out.println("\nGoodbye!");
                    break;
                default:
                    System.out.println("\n[Error] Invalid choice. Please select from 1-5.");
            }
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("\n--- Impact Tracker ---");
        System.out.println("1. Log Device Use");
        System.out.println("2. Log Transport");
        System.out.println("3. Show Report");
        System.out.println("4. Get Tips");
        System.out.println("5. Exit");
        System.out.print("Enter choice (1-5): ");
    }

    private int getChoice() {
        while (!sc.hasNextInt()) {
            System.out.println("\n[Error] Not a number. Enter 1-5.");
            System.out.print("Enter choice (1-5): ");
            sc.next();
        }
        return sc.nextInt();
    }

    private double getPosDouble(String prompt) {
        double val = -1;
        while (val < 0) {
            System.out.print(prompt);
            while (!sc.hasNextDouble()) {
                System.out.println("\n[Error] Not a valid number.");
                System.out.print(prompt);
                sc.next();
            }
            val = sc.nextDouble();
            if (val < 0) {
                System.out.println("\n[Error] Enter 0 or a positive number.");
            }
        }
        return val;
    }

    private void logDevice() {
        System.out.println("\n--- Log Device Use ---");
        double hrs = getPosDouble("Enter device hours: ");
        this.devHrs += hrs;
        System.out.printf("Logged %.1f hrs. Total: %.1f hrs.\n", hrs, this.devHrs);
    }

    private void logTransport() {
        System.out.println("\n--- Log Transport ---");
        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.println("3. Walk");
        System.out.print("Enter type (1-3): ");

        int type = -1;
        while (type < 1 || type > 3) {
            type = getChoice();
            if (type < 1 || type > 3) {
                System.out.println("\n[Error] Invalid choice. Select 1-3.");
                System.out.print("Enter type (1-3): ");
            }
        }

        double kms = getPosDouble("Enter kilometers: ");

        switch (type) {
            case 1:
                this.carKms += kms;
                System.out.printf("Logged %.1f km by car. Total: %.1f km.\n", kms, this.carKms);
                break;
            case 2:
                this.bikeKms += kms;
                System.out.printf("Logged %.1f km by bike. Total: %.1f km.\n", kms, this.bikeKms);
                break;
            case 3:
                this.walkKms += kms;
                System.out.printf("Logged %.1f km by walk. Total: %.1f km.\n", kms, this.walkKms);
                break;
        }
    }

    private void showReport() {
        System.out.println("\n--- My Report ---");

        double devFootprint = this.devHrs * DEV_CO2;
        double transFootprint = this.carKms * CAR_CO2;
        double totalFootprint = devFootprint + transFootprint;

        System.out.printf("Device (%.1f hrs): \t%.2f kg CO2\n", this.devHrs, devFootprint);
        System.out.printf("Car (%.1f km): \t\t%.2f kg CO2\n", this.carKms, transFootprint);
        System.out.println("--------------------------------------");
        System.out.printf("TOTAL ESTIMATE: \t%.2f kg CO2\n", totalFootprint);

        System.out.println("\n--- Green Activities ---");
        System.out.printf("Bike: %.1f km, Walk: %.1f km. Great job!\n", this.bikeKms, this.walkKms);
    }

    private void showTips() {
        System.out.println("\n--- Greener Tips ---");
        boolean noTips = true;

        if (this.devHrs > 20) {
            System.out.println("- Unplug devices when not in use.");
            noTips = false;
        }

        if (this.carKms > 50) {
            System.out.println("- For short trips, try biking or walking.");
            noTips = false;
        }

        if (this.carKms > 10 && this.bikeKms == 0 && this.walkKms == 0) {
            System.out.println("- Swap one short car trip with a walk or bike ride!");
            noTips = false;
        }

        if (noTips) {
            System.out.println("Your logged impact is low. Keep it up!");
        }
    }

    public static void main(String[] args) {
        EnvironmentalTracker tracker = new EnvironmentalTracker();
        tracker.run();
    }
}
