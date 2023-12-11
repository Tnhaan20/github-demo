/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.*;
import Model.DataContainer;
import Model.Reservation;
import Model.flightInfo;
import Model.staff;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Flight_Management {

    final static String emp = "Cannot be empty";
    private List<flightInfo> flightList = new ArrayList<flightInfo>();
    private List<Reservation> passengerList = new ArrayList<Reservation>(); // add vào chỗ ngồi số bao nhiêu nữa
    private List<staff> staffList = new ArrayList<staff>();
    List<DataContainer> temp = new ArrayList<>();

    // holders
    Scanner sc = new Scanner(System.in);
    String id, departCity, destiCity;
    LocalDateTime departTime, arriveTime;
    String name, phone, email;
    boolean readFromFile = false;
    int seats, index, totalSeat;
    long durationToMili;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    //repeat the character
    public String repeat(String name, int number) {
        String result = "";
        for (int i = 0; i < number; i++) {
            result += name;
        }
        return result;
    }

    //Center a string based on length
    public static void centerString(String text, int width) {
        System.out.printf("%" + ((width - text.length()) / 2 + text.length()) + "s%n", text);
    }

    // Create and checking unique code of the flight
    public void checkCode() {
        id = Utils.getStringreg("Enter the ID of the flight (Fxxxx): ", "^F\\d{4}$",
                "Code cannot be empty!", "Wrong format, please enter again!");
        boolean isCodeExist = false;
        for (flightInfo info : flightList) {
            if (info.getFlightNum().equals(id)) {
                isCodeExist = true;
                break;
            }
        }
        // If it is true, the method will be deploy
        if (isCodeExist) {
            System.out.println("Code is exist. Please enter again");
            checkCode();
            return;
        }
    }

    // Method 1 - Add the flight
    public void addFlight() {
        int Max_Seat = 600;
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime nextday = current.plusHours(4);
        checkCode();
        departCity = Utils.getString("Enter the departure city: ", emp);
        boolean des = true;
        do {
            destiCity = Utils.getString("Enter the destination city: ", emp);
            if (departCity.equalsIgnoreCase(destiCity)) {
                System.out.println("The destination city cannot be the same as the departure city");
            } else {
                des = false;
            }

        } while (des);
        while (true) {
            departTime = Utils.getDateTime("Enter departure date and time (dd/MM/yyyy HH:mm): ",
                    "dd/MM/yyyy HH:mm");
            if (departTime.isAfter(nextday)) {
                break;
            } else {
                System.out.println("Departure time must be after 1 day from today and cannot be in the past");
            }
        }

        while (true) {
            arriveTime = Utils.getDateTime("Enter arrival date and time (dd/MM/yyyy HH:mm): ", "dd/MM/yyyy HH:mm");

            if (arriveTime.isAfter(departTime)) {
                // Thêm điều kiện kiểm tra thời gian bay tối đa
                Duration duration = Duration.between(departTime, arriveTime);
                durationToMili = duration.toMillis();

                if (durationToMili > 24 * 60 * 60 * 1000) { //max
                    System.out.println("Flight time cannot exceed 24 hours.");
                    continue;
                } else if (durationToMili < 60 * 60 * 1000) {
                    System.out.println("Flight time cannot be under 1 hours");
                    continue;
                }
                break;
            } else {
                System.out.println("Arrival time must be after departure time.");
            }
        }

        seats = Utils.getInt("Enter number of seats in this flight: ", 0, Max_Seat);
        totalSeat = seats;
        System.out.println(repeat("=", 30));

        System.out.println("The duration of the flight will be: " + durationToMili / 3600 / 1000 + " hours and "
                + (durationToMili % (60 * 60 * 1000)) / (60 * 1000) + " minutes.");
        flightInfo fl = new flightInfo(id, departCity, destiCity, departTime, arriveTime, seats, totalSeat);
        flightList.add(fl);
        System.out.println("--------------Adding flight succesfully--------------");
        Map<String, String> seatsNum = new HashMap<>();
        fl.setSeats(seatsNum);
        System.out.println(repeat("=", 80));
        if (Utils.confirm("Do you want to continue creating new flight? (Y/N): ")) {
            addFlight();
        }
    }

    // Method 2 - Reservation Methods
    // Unique reservation ID
    public String resID() {
        String reserID = "";
        String checkRes = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            int resID = rand.nextInt(checkRes.length());
            char c = checkRes.charAt(resID);
            reserID += c;
        }
        return reserID;
    }

    //Method 2 _ Check the schedule of flight -> Enter flight ID -> Book a reservation
    // Checking available flight's schedules
    public void reservation() {
        while (true) {
            System.out.println(repeat("=", 80));
            centerString("Welcome to the flight service!", 80);
            centerString("Please enter your information to get a flight's details", 80);
            System.out.println(repeat("-", 80));
            LocalDate checkDepartDate;
            String checkDepartLocact, checkArriveLocact;

            checkDepartLocact = Utils.getString("Enter the departure location: ", emp);
            boolean des = true;
            do {
                checkArriveLocact = Utils.getString("Enter the arrival location: ", emp);
                if (checkDepartLocact.equalsIgnoreCase(checkArriveLocact)) {
                    System.out.println("The destination city cannot be the same as the departure city");
                } else {
                    des = false;
                }
            } while (des);
            while (true) {
                checkDepartDate = Utils.getDate("Enter departure date of the flight (dd/MM/yyyy): ", "dd/MM/yyyy");

                if (checkDepartDate.isAfter(LocalDate.now()) || checkDepartDate.equals(LocalDate.now())) {
                    break;
                } else {
                    System.out.println("Departure time must be on this day or after today");
                }

            }
            // Checking day without time
            List<flightInfo> matchingFlights = new ArrayList<>();
            // Checking day without time
            for (flightInfo reserve : flightList) {
                LocalDate departureDate = reserve.getDepartTime().toLocalDate();
                if (reserve.getDepartCity().equalsIgnoreCase(checkDepartLocact)
                        && checkDepartDate.equals(departureDate) && checkDepartDate.isAfter(LocalDate.now())
                        && reserve.getDestiCity().equalsIgnoreCase(checkArriveLocact)) {
                    matchingFlights.add(reserve);
                }
            }

            // Checking and print the schedule with input information
            if (!matchingFlights.isEmpty()) {
                System.out.println(repeat("=", 80));
                System.out.println("");
                centerString("The flight information based on information is shown here", 100);
                System.out.println(repeat("-", 230));
                System.out.format("%-25s|%-35s|%-25s|%-25s|%-25s|%-25s|%-25s%n", "Flight Number", "Crew", "Departure City",
                        "Destination City", "Departure Time", "Arrival Time", "Seats");
                System.out.println(repeat("-", 230));
                for (flightInfo flight : matchingFlights) {
                    // In thông tin chuyến bay
                    flight.printinfo();
                }
            } else {
                // Không tìm thấy chuyến bay nào
                System.out.println(repeat("=", 80));
                System.out.println("No flight found based on this information.");
                System.out.println(repeat("=", 80));
                return;
            }

            // Booking the reservation
            String id = resID();
            boolean check = false;
            String fName = null;
            centerString("Welcome to the reservation service!", 80);
            centerString("Please enter your information to get a reservation", 80);
            centerString("Please select flight to make a reservation", 80);
            String checkFlightNum = Utils.getStringreg("Enter the ID of the flight for making reservation (Fxxxx): ",
                    "^F\\d{4}$",
                    "Code cannot be empty!", "Wrong format, please enter again!");
            for (flightInfo info : matchingFlights) {
                if (info.getFlightNum().equals(checkFlightNum)) {
                    check = true;
                    fName = info.getFlightNum();
                    break;
                }
            }
            if (!check) {
                System.out.println("The flight is not available");
                return;
            }
            System.out.println("You are making a reservation with flight " + fName);
            name = Utils.getString("Enter your name: ", emp);
            System.out.println("Phone number must have origin code (+xx) and 9 numbers digits");
            phone = Utils.getStringreg("Enter your phone: ", "^\\+\\d{1,3}\\d{10}$",
                    "Phone number cannot be empty", "Wrong phone number format");
            email = Utils.getStringreg("Enter your email: ",
                    "^[a-zA-Z0-9_+&*-]+(?:\\."
                    + "[a-zA-Z0-9_+&*-]+)*@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    + "A-Z]{2,7}$",
                    "Email cannot be empty", "Wrong email format");
            passengerList.add(new Reservation(id, fName, name, phone, email, "N/A"));
            System.out.println(repeat("=", 80));
            System.out.println("Your reservation has been created with reservation code is: " + id);
            System.out.println("You must remember this code for making a check-in the flight and booking the seats!");
            System.out.println("Have a nice day!!!");
            System.out.println(repeat("=", 80));
            return;
        }
    }
    // End of method 2

    // Method 3 - Passenger checks in and Seat availability
    //Caculate and print out the map of seats base on 2D-array
    public void seat(String fNum) {
        for (flightInfo flight : flightList) {
            if (flight.getFlightNum().equals(fNum)) {
                Map<String, String> seats = flight.getSeats();

                int totalSeats = flight.getTotalSeat();
                int rows = totalSeats / 6; // Số hàng, giả sử có 6 ghế trên mỗi hàng

                // In ra
                System.out.println(repeat("=", 60));
                System.out.println("This flight has " + flight.getAvailableSeat() + " seats");
                System.out.println("[Code]: Available \t [xx]: Booked");
                System.out.println(repeat("=", 60));

                for (int i = 1; i <= rows; i++) {
                    for (char j = 'A'; j <= 'F'; j++) {
                        String seatId = "" + j + i;
                        String status = seats.get(seatId);
                        if (status == null) {
                            System.out.format("[ %3s ]", seatId);
                        } else {
                            System.out.format("[ %3s ]", "xx");
                        }

                        // Thêm tab sau cột C
                        if (j == 'C') {
                            System.out.print("\t");
                        }
                    }
                    System.out.println();
                }

                int remainingSeats = totalSeats % 6;
                // Vòng lặp in các ghế cuối cùng
                for (int i = 1; i <= remainingSeats; i++) {
                    char col = 'A';
                    // Xác định cột dựa trên số thứ tự i
                    if (i <= remainingSeats) {
                        col = (char) ('A' + i - 1);
                    }
                    String seatId = "" + col + (rows + 1);
                    // Kiểm tra trạng thái ghế và in ra
                    String status = seats.get(seatId);
                    if (status == null) {
                        System.out.format("[ %3s ]", seatId);
                    } else {
                        System.out.format("[ %3s ]", "xx");
                    }
                    // Thêm tab sau mỗi 3 ghế 
                    if (i % 3 == 0) {
                        System.out.print("\t");
                    }

                }
                System.out.println();
                flight.setSeats(seats);
            }
        }
    }

    public flightInfo getFlightByNumber(String flightNumber) {
        flightInfo flight = null;
        for (flightInfo f : flightList) {
            if (f.getFlightNum().equals(flightNumber)) {
                flight = f;
                break;
            }
        }

        return flight;
    }

    //For unique resID
    // Booking seats
    public void checkPassenger() {
        String checkID = null, namePass = null, idFlight = null;
        
        while (true) {
            boolean isPassenger = false;
            checkID = Utils.getString("Enter the reservation ID: ", emp);
            System.out.println(repeat("-", 80));
            for (Reservation passenger : passengerList) {
                if (passenger.getIdResereved().equals(checkID)) {
                    String seatNum = passenger.getSeatNum();

                    if (!seatNum.equals("N/A")) {
                        System.out.println("This ID is taken the seat already!");
                        return;
                    }
                }
            }

            // Kiểm tra xem checkID đã đặt ghế nào chưa
            for (Reservation check : passengerList) {
                if (check.getIdResereved().equals(checkID)) {
                    isPassenger = true;
                    idFlight = check.getFlightNumberReserved();
                    namePass = check.getName();
                    break;
                }
            }
            if (!isPassenger) {
                System.out.println("No reservation with ID: " + checkID);
                return;
            }

            flightInfo fl = getFlightByNumber(idFlight);
            Map<String, String> seats = fl.getSeats();
            int totalFl = fl.getTotalSeat();
            String from = fl.getDepartCity();
            String to = fl.getDestiCity();
            LocalDateTime dateD = fl.getDepartTime();
            LocalDateTime dateA = fl.getArrivalTime();
            

            System.out.println("Hello " + Utils.formatString(namePass, Utils.stringType.NAME) + "!");
            System.out.println("Here are the available seats on this flight: " + idFlight);
            seat(idFlight); // Đảm bảo rằng bạn đã hiển thị trạng thái ghế hiện tại cho hành khách
            System.out.println(repeat("=", 60));
            String seatNum = Utils.getString("Enter the desire seat: ", emp);
            // Kiểm tra tính hợp lệ của ghế
            // Check if the seat is valid
            int maxRow = totalFl / 6; // calculate the maximum number of rows
            char c = seatNum.charAt(0); // get the column character A, B, C...
            int row = Integer.parseInt(seatNum.substring(1)); // get the row number

            boolean isValidSeat = false;
            if (row <= maxRow && c >= 'A' && c <= 'F') {
                isValidSeat = true;
            }

            // Check if the seat is in the remaining seats
            int remain = totalFl % 6;
            if (!isValidSeat && row == maxRow + 1 && c >= 'A' && c < 'A' + remain) {
                isValidSeat = true;
            }

            if (!isValidSeat) {
                System.out.println(repeat("-", 60));
                System.out.println("The seat does not exist");
                return;
            }

            // Kiểm tra xem ghế đã được đặt hay chưa
            String check = seats.get(seatNum);
            if (check != null) {
                System.out.println("This seat " + seatNum + " has been taken");
                return;
            } else {
                seats.put(seatNum, checkID);
                System.out.println(repeat("+", 80));
                System.out.println("Booking successfully");
                System.out.println("Seat " + seatNum + " is taken by " + checkID);
                updatePassengerSeat(seatNum, checkID);
                // In vé hành khách
                System.out.println(repeat("+", 80));
                System.out.println("Here is your ticket");
                DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                System.out.println(repeat("=", 80));
                centerString("Flight Ticket", 80);
                System.out.println("|Reservation ID: " + checkID);
                System.out.println("|Flight ID: " + idFlight);
                System.out.println("|Passenger's Name: " + Utils.formatString(namePass, Utils.stringType.NAME));
                System.out.println("|From: " + Utils.formatString(from, Utils.stringType.NAME) + " \t\t\t\t To: " + Utils.formatString(to, Utils.stringType.NAME));
                System.out.println("|Departure Time: " + dateD.format(dtm) + " \t\t Arrival Time: " + dateA.format(dtm));
                System.out.println("|Seat: " + seatNum);
                System.out.println(repeat("=", 80));
                for (flightInfo flight : flightList) {
                    if (flight.getFlightNum().equals(idFlight)) {
                        int newAvailableSeats = flight.getAvailableSeat() - 1;
                        flight.setAvailableSeat(newAvailableSeats);
                        break;
                    }
                }
                return;
            }
        }
    }

    public void updatePassengerSeat(String seatNum, String reservationID) {
        for (Reservation passenger : passengerList) {
            if (passenger.getIdResereved().equals(reservationID)) {
                passenger.setSeatNum(seatNum);
                break; // Tìm thấy hành khách và đã cập nhật seatNum, nên thoát khỏi vòng lặp.
            }
        }
    }

    // Ending method 3
    // Method 4 - Crew management
    // 1 chuyến bay có 3 người và 3 role khác nhau
    //Checking unique ID of each staff
    public String checkIDStaff() {
        String idStaff = Utils.getStringreg("Enter the ID of the Staff (xxx): ", "^\\d{3}$",
                "Code cannot be empty!", "Wrong format, please enter again!");
        boolean isCodeExist = false;
        for (staff info : staffList) {
            if (info.getid().equals(idStaff)) {
                isCodeExist = true;
                break;
            }
        }
        // If it is true, the method will be deploy
        if (isCodeExist) {
            System.out.println("Code is exist. Please enter again");
            return checkIDStaff();
        }
        return idStaff;
    }

    public void addCrew() {
        String id = checkIDStaff();
        String name = Utils.getString("Enter name of staff: ", emp);
        String role;
        while (true) {
            role = Utils.getString("Enter role (pilot, service, ground staff): ", emp);
            if (role.equalsIgnoreCase("pilot") || role.equalsIgnoreCase("service") || role.equalsIgnoreCase("ground staff")) {
                break;
            } else {
                System.out.println("Invalid role. The role must be 1 of 3 roles");
                continue;
            }
        }
        staffList.add(new staff(id, name, role));

        System.out.println("----------Adding staff successfully----------");
        if (Utils.confirm("Do you want to continue adding new staff? (Y/N): ")) {
            System.out.println(repeat("-", 80));
            addCrew();
        }

    }

    public void printCrew() {

        if (!staffList.isEmpty()) {
            System.out.println("List of all crew member");
            System.out.println(repeat("-", 80));
            System.out.format("%-25s | %-25s | %-25s%n", "ID Staff", "Name", "Staff's Role");
            System.out.println(repeat("-", 80));
            for (staff staff : staffList) {
                staff.printInfo();
            }
        } else {
            System.out.println("No information about crew. Please enter crew");
        }
    }

    public void addCrewToFlight() {
        int p = 0;
        int s = 0;
        int gs = 0;
        for (staff stf : staffList) {
            if (stf.getrolePilot().equalsIgnoreCase("pilot")) {
                p++;
            } else if (stf.getrolePilot().equalsIgnoreCase("service")) {
                s++;
            } else if (stf.getrolePilot().equalsIgnoreCase("ground staff")) {
                gs++;
            }
        }
        if (p < 1) {
            System.out.println("There is no pilot in the staff list, please add the pilot!");
            return;
        }
        if (s < 1) {
            System.out.println("There is no service member in the staff list, please add the service member!");
            return;
        }
        if (gs < 1) {
            System.out.println("There is no ground staff in the staff list, please add the ground staff!");
            return;
        }
        String flightID = Utils.getStringreg("Enter the flight ID to add staff (Fxxxx): ", "^F\\d{4}$",
                "Code cannot be empty!", "Wrong format, please re-enter");
        flightInfo selectedFlight = null;
        for (flightInfo flight : flightList) {
            if (flight.getFlightNum().equals(flightID)) {
                selectedFlight = flight;
                break;
            }
        }
        if (selectedFlight == null) {
            System.out.println(repeat("=", 80));
            System.out.println("Not found the flight with ID: " + flightID + "!");
            return;
        }

        boolean adding = true;

        int pilotCount = selectedFlight.getpCount();
        int serviceCount = selectedFlight.getsCount();
        int groundStaffCount = selectedFlight.getgCount();

        boolean hasAllRoles = (pilotCount > 0 && serviceCount > 0 && groundStaffCount > 0);

        while (adding) {
            String idStaff = Utils.getStringreg("Enter the ID of the member (xxx): ", "^\\d{3}$",
                    emp, "Wrong format, please try again");

            staff selectStaff = null;
            for (staff stf : staffList) {
                if (stf.getid().equals(idStaff)) {
                    selectStaff = stf;
                    break;
                }
            }

            if (selectStaff == null) {
                System.out.println("Not found the member with code: " + idStaff + "!");
                return;
            } else {
                // Check if the staff member has already been assigned to this flight
                if (selectStaff.getAssignedFlights().contains(selectedFlight)) {
                    System.out.println("This member " + selectStaff.getNamePilot() + " is already in this flight");
                } else {
                    // Check the role of the staff and update counts
                    if (selectStaff.getrolePilot().equalsIgnoreCase("pilot")) {
                        pilotCount++;
                    } else if (selectStaff.getrolePilot().equalsIgnoreCase("service")) {
                        serviceCount++;
                    } else if (selectStaff.getrolePilot().equalsIgnoreCase("ground staff")) {
                        groundStaffCount++;
                    }

                    // Assign the staff to the flight
                    selectStaff.assignFlight(selectedFlight);
                    selectedFlight.addCrewMember(selectStaff);
                    System.out.println(
                            "Staff with name " + Utils.formatString(selectStaff.getNamePilot(), Utils.stringType.NAME) + " (" + Utils.formatString(selectStaff.getrolePilot(), Utils.stringType.NAME) + ")" + " has been added to "
                            + selectedFlight.getFlightNum());
                }
            }
            selectedFlight.setpCount(pilotCount);
            selectedFlight.setsCount(serviceCount);
            selectedFlight.setgCount(groundStaffCount);

            // Check if there are already 3 distinct roles in the crew
            hasAllRoles = (pilotCount > 0 && serviceCount > 0 && groundStaffCount > 0);

            if (hasAllRoles) {
                System.out.println(repeat("-", 60));
                System.out.println("Flight had at least one member for each different role"
                        + " (Pilot: " + pilotCount + ", " + "Service: " + serviceCount + ", " + "Ground Staff: " + groundStaffCount + " )");
                System.out.println(repeat("-", 60));
                adding = Utils.confirm("Do you want to continue adding staff to this flight (Y/N): ? ");
            } else {
                System.out.println(repeat("-", 60));
                System.out.println("Flight must be have at least one member for each different role " + " (Pilot: " + pilotCount + ", " + "Service: " + serviceCount + ", " + "Ground Staff: " + groundStaffCount + " )");
                System.out.println(repeat("-", 60));
            }
        }
    }

    // End method 4
    // Method 5 - Print list from file
    public void printAllList() {
        loadedFlight.clear();
        loadedPass.clear();
        loadedStaff.clear();
        loadListFromFile();

        if (loadedFlight.isEmpty()) {
            System.out.println("No flight information available.");
            return;
        }

        // Sort the loaded flights by departure time
        Collections.sort(loadedFlight, new Comparator<flightInfo>() {
            @Override
            public int compare(flightInfo o1, flightInfo o2) {
                return o1.getDepartTime().compareTo(o2.getDepartTime());
            }
        });

        for (flightInfo flight : loadedFlight) {
            System.out.println(repeat("-", 230));
            centerString("Flight Information: " + flight.getFlightNum(), 150);
            System.out.println(repeat("-", 230));
            System.out.format("%-25s|%-35s|%-25s|%-25s|%-25s|%-25s|%-25s%n", "Flight Number", "Crew", "Departure City",
                    "Destination City", "Departure Time", "Arrival Time", "Seats");
            System.out.println(repeat("-", 230));
            flight.printinfo();

            boolean foundPassenger = false;
            for (Reservation print : loadedPass) {
                if (flight.getFlightNum().equals(print.getFlightNumberReserved())) {
                    if (!foundPassenger) {
                        System.out.println("");
                        centerString("Passenger List for this Flight", 150);
                        System.out.println(repeat("-", 180));
                        System.out.format("%-25s|%-25s|%-25s|%-25s|%-25s|%-25s%n", "Reservation ID", "Flight Number",
                                "Name Passenger", "Contact", "Email Address", "Seat Number");
                        System.out.println(repeat("-", 180));
                        foundPassenger = true;
                    }
                    print.printinfo();
                }
            }

            boolean foundStaff = false;
            for (staff staff : loadedStaff) {
                if (staff.getAssignedFlights().contains(flight)) {
                    if (!foundStaff) {
                        System.out.println("");
                        centerString("Staff List for this Flight", 80);
                        System.out.println(repeat("-", 80));
                        System.out.format("%-25s | %-25s | %-25s%n", "ID Member", "Name", "Role");
                        System.out.println(repeat("-", 80));
                        foundStaff = true;
                    }
                    staff.printInfo();
                }

            }
            System.out.println("");

            if (!foundPassenger) {
                System.out.println(repeat("+", 80));
                centerString("No passenger information available for this flight", 90);
                System.out.println(repeat("+", 80));
            }

            if (!foundStaff) {
                System.out.println(repeat("+", 80));
                centerString("No staff information available for this flight", 90);
                System.out.println(repeat("+", 80));
            }
            System.out.println("");
            System.out.println("<" + repeat("*", 160) + ">");
            System.out.println("");
        }

    }

    // Ending method 5 ----- Print List
    // Method 6 - Save list and load
    public void loadFromFile() {
        DataContainer data = new DataContainer(flightList, passengerList, staffList);
        List<DataContainer> temp = new ArrayList<>();
        temp.add(data);
        Utils.loadFromFile("product.dat", temp);
        if (!temp.isEmpty()) {
            flightList.addAll(temp.get(0).getFlightList());
            passengerList.addAll(temp.get(0).getPassengerList());
            staffList.addAll(temp.get(0).getStaffList());
        }
    }

    List<flightInfo> loadedFlight = new ArrayList<>();
    List<Reservation> loadedPass = new ArrayList<>();
    List<staff> loadedStaff = new ArrayList<>();

    // ----> phải load lên được từ file và không bị trùng <------
    public void loadListFromFile() {
        DataContainer data = new DataContainer(loadedFlight, loadedPass, loadedStaff);
        List<DataContainer> temp = new ArrayList<>();
        temp.add(data);
        Utils.loadFromFile("product.dat", temp);
        if (!temp.isEmpty()) {
            loadedFlight.addAll(temp.get(0).getFlightList());
            loadedPass.addAll(temp.get(0).getPassengerList());
            loadedStaff.addAll(temp.get(0).getStaffList());
        }
    }

    public void saveToFile() {
        List<DataContainer> temp = new ArrayList<>();
        DataContainer data = new DataContainer(flightList, passengerList, staffList);
        temp.add(data);
        Utils.saveToFile("product.dat", temp);

    }

}
