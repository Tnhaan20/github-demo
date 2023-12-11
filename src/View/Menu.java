package View;

import Controller.Utils;
import Controller.Flight_Management;

/**
 *
 * @author Asus
 */
public class Menu {

    int a;
    Flight_Management fm = new Flight_Management();

    public String repeat(String name, int number) {
        String result = "";
        for (int i = 0; i < number; i++) {
            result += name;
        }
        return result;
    }

    public void menuDisplay() throws ClassNotFoundException {
        int choice1;
        fm.loadFromFile();
        while (true) {
            System.out.println(repeat("=", 80));
            String text = "||Flight Management System||";
            int width = 80; // Độ rộng tổng cộng của chuỗi in ra
            System.out.printf("%" + ((width - text.length()) / 2 + text.length()) + "s%n", text);

            System.out.println(repeat("=", 80));
            System.out.print(
                    "|1. Flight schedule management\n"
                    + "|2. Passenger reservation and booking\n"
                    + "|3. Passenger check-in and seat allocation\n"
                    + "|4. Crew management and assignments\n"
                    + "|5. Print all lists from file\n"
                    + "|6. Data storage for flight details, reservations, and assignments\n"
                    + "|7. Quit");
            System.out.println();
            System.out.println(repeat("=", 80));

            choice1 = Utils.getInt("Enter a choice: ", 1, 7);
            System.out.println(repeat("=", 80));

            switch (choice1) {
                case 1:
                    System.out.println("[Flight schedule management]");
                    System.out.println(repeat("=", 80));
                    fm.addFlight();
                    break;
                case 2:
                    fm.reservation();
                    break;
                case 3:
                    fm.checkPassenger();
                    break;
                case 4:
                    menu4();
                    break;
                case 5:
                    fm.printAllList();
                    break;
                case 6:
                    fm.saveToFile();
                    break;
                case 7:
                    System.out.println("See you again\n"
                            + "Thanks for using app");
                    return;
            }
        }
    }

    public void menu4() {
        
        System.out.println(repeat("=", 120));
        System.out.println("[Passenger check-in and seat allocation]");
        System.out.println("\t1. Enter a crew");
        System.out.println("\t2. Print list of the crew");
        System.out.println("\t3. Pick the crew for flight");
        System.out.println("\t4. Back to main menu");
        a = Utils.getInt("Enter the choice: ", 1, 4);
        System.out.println(repeat("=", 120));

        switch (a) {
            case 1:
                fm.addCrew();
                menu4();
                break;
            case 2:
                fm.printCrew();
                menu4();
                break;
            case 3:
                fm.addCrewToFlight();
                menu4();
                break;
        }

    }

}
