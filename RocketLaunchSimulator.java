import java.util.Scanner;

public class RocketLaunchSimulator {
    private static final int MAX_FUEL = 100;
    private static final int MAX_SPEED = 1000;
    private static final int ORBIT_ALTITUDE = 100;
    private static int seconds;

    private String stage;
    private int fuel;
    private int altitude;
    private int speed;


    public RocketLaunchSimulator() {
        this.stage = "Pre-Launch";
        this.fuel = 1000;
        this.altitude = 0;
        this.speed = 0;
    }

    public void startChecks() {
        System.out.println("Pre-Launch Checks: All systems are 'Go' for launch.");
    }

    public void launch() throws InterruptedException {
        if (!stage.equals("Pre-Launch")) {
            System.out.println("Launch already initiated.");
            return;
        }

        System.out.println("Launch initiated!");
        stage = "Stage 1";

        while (altitude <= ORBIT_ALTITUDE && fuel > 0) {
            // Update rocket status
            altitude += 10;
            fuel -= 10;
            speed += 100;
            if (altitude > ORBIT_ALTITUDE) {
                System.out.println("Orbit achieved! Mission Successful.");
            }

            // Display real-time updates
            System.out.println("Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
            Thread.sleep(1000);

            // Check for stage separation
            if (altitude >= ORBIT_ALTITUDE && stage.equals("Stage 1")) {
                stage = "Stage 2";
                System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.");
            }
        }
    }

    public void fastForward(int seconds) {
        if (stage.equals("Pre-Launch")) {
            System.out.println("Launch not initiated yet.");
            return;
        }

        for (int i = 0; i < seconds; i++) {
            if (altitude >= ORBIT_ALTITUDE || fuel <= 0) {
                updateStage();
                break;
            }

        }
    }

    private void updateStage() {

        altitude = altitude + 10 * seconds;
        speed += 100* seconds;
        fuel -= 10* seconds;

        System.out.println("Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");

        if (altitude >= ORBIT_ALTITUDE && stage.equals("Stage 1")) {
            stage = "Stage 2";
            System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RocketLaunchSimulator simulator = new RocketLaunchSimulator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("User Input: ");
            String input = scanner.nextLine();

            if (input.equals("start_checks")) {
                simulator.startChecks();
            } else if (input.equals("launch")) {
                simulator.launch();
            } else if (input.startsWith("fast_forward")) {
                try {
                    seconds = Integer.parseInt(input.split(" ")[1]);
                    simulator.fastForward(seconds);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid command. Use 'fast_forward X' where X is the number of seconds.");
                }
            }

            else {
                System.out.println("Invalid command. Use 'start_checks', 'launch', or 'fast_forward X'.");
            }
        }
    }
}