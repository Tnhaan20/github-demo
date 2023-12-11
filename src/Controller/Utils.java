package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Model.DataContainer;
import Model.flightInfo;

public class Utils {

    public static enum stringType {
        NAME,
        TO_UPPER,
        TO_LOWER,
        CLEAR_SPACE,
        NO_SPACE,
        NO_DIGIT,
        NO_STRING,
        REVERSE,
        CAPITALIZE,
    }

    public static String formatString(String str, stringType stringType) {
        switch (stringType) {
            case NAME:
                str = formatString(str, stringType.CLEAR_SPACE);
                str = formatString(str, stringType.CAPITALIZE);
                break;

            case CAPITALIZE:
                str = str.trim().replaceAll("\\s+", " ");

                StringBuilder capitalizedWord = new StringBuilder();

                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (i == 0 || str.charAt(i - 1) == ' ') {
                        capitalizedWord.append(Character.toUpperCase(c));
                    } else {
                        capitalizedWord.append(Character.toLowerCase(c));
                    }
                }
                str = capitalizedWord.toString();
                break;

            case TO_UPPER:
                str = str.toUpperCase();
                break;

            case TO_LOWER:
                str = str.toLowerCase();
                break;

            case CLEAR_SPACE:
                str = str.trim();
                str = str.replaceAll("\\s+", " ");
                break;

            case NO_SPACE:
                str = str.trim();
                str = str.replaceAll("\\s+", "");
                break;

            case REVERSE:
                StringBuilder reversedString = new StringBuilder(str);
                str = reversedString.reverse().toString();
                break;

            case NO_DIGIT:
                str = str.replaceAll("\\d", "");
                break;

            case NO_STRING:
                str = str.replaceAll("\\D", "");
                break;
        }

        return str;
    }

    Scanner sc = new Scanner(System.in);

    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static int getInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be large than " + min);
                    System.out.println("Number must be smaller than " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static int getInter(String welcome, int min) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);

                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static float getloat(String welcome, int min) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static float getFloat(String welcome, int min, int max) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be large than " + min);
                    System.out.println("Number must be smaller than " + max);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min || number > max);
        return number;
    }

    public static GregorianCalendar getDate(int startYear, int endYear) {
        Random random = new Random();
        int year = random.nextInt(endYear - startYear + 1) + startYear; // Tạo năm ngẫu nhiên từ startYear đến endYear
        GregorianCalendar gc = new GregorianCalendar(year, 1, 1);
        int dayOfYear = random.nextInt(gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        return gc;
    }

    //isUpdate la 1 ham dung de kiem tra hoat dong cap nhat, true la co cap nhat va co the nhap chuoi rong de bo qua, false la tao moi ngay
    public static Date inputDate(String mess, Date min, Date max, boolean isUpdate) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(mess);
            String input = sc.nextLine();
            if (isUpdate && input.equals("")) {
                return null;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                Date date = dateFormat.parse(input);
                if (min != null) {
                    if (date.before(min)) {
                        System.out.println("Please input between " + dateFormat.format(min) + " - " + dateFormat.format(max));
                        continue;
                    }
                }
                if (max != null) {
                    if (date.after(max)) {
                        System.out.println("Please input between " + dateFormat.format(min) + " - " + dateFormat.format(max));
                        continue;
                    }
                }
                return date;
            } catch (Exception e) {
                System.out.println("Please input correct form dd/MM/yyyy.");
                continue;
            }
        }
    }

    public static String generateCode(String prefix, int length, int curNumber) {
        String formatStr = "%0" + length + "d"; // -> %07d
        return prefix + String.format(formatStr, curNumber);
    }

    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true; // It's a leap year
        } else {
            return false; // It's not a leap year
        }
    }

    public static boolean checkValidDate(String date) {
        String[] arr = date.trim().split("/"); // 2/29///2004 => arr[0] = 2, arr[1] = 29,...                                                
        int day = Integer.parseInt(arr[0]);   // Month/Day/Year, arr[0] = Month,...
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);

        if (month < 1 || month > 12) {
            return false;
        }
        if (isLeapYear(year)) {
            if (month == 2) { //In leap year, Feb = 29days
                if (day < 0 || day > 29) {
                    return false;
                }
            }
        } else { //Not a leap year
            if (month == 2) {
                if (day < 0 || day > 28) {
                    return false; //Feb in not leap year = 28days
                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) { //4, 6, 9, 11 = 30days
                if (day < 0 || day > 30) {
                    return false;
                }
            } else { //1, 3, 5, 7, 8, 10, 12 = 31days
                if (day < 0 || day > 31) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Date getDate(String msg, String err, String format) {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        while (true) {
            try {
                System.out.print(msg);
                String value = sc.nextLine();
                date = sdf.parse(value);
                if (!checkValidDate(value)) {
                    System.out.println("Invalid date!!!");
                } else {
                    return date;
                }
            } catch (ParseException e) {
                System.out.println(err);
            }
        }
    }

    public static Date getDateNoIn(String err, String format) {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        while (true) {
            try {
                String value = sc.nextLine();
                date = sdf.parse(value);
                if (!checkValidDate(value)) {
                    System.out.println("Invalid date!!!");
                } else {
                    return date;
                }
            } catch (ParseException e) {
                System.out.println(err);
            }
        }
    }

    public static Date parseDate(String dateStr, String dateFormat) {
        SimpleDateFormat dF = (SimpleDateFormat) SimpleDateFormat.getInstance(); //--->GetInstance lấy ngày tháng và vùng mặc định của máy
        dF.applyPattern(dateFormat); //----> apply format sẽ được nhập
        try {
            long t = dF.parse(dateStr).getTime(); //----> chuyển từ chuỗi được nhập sang ngày 
            //.getTime() lấy số mls từ epoch rồi biểu diễn dưới dạng số nguyên
            return new Date(t);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }

    public static boolean checkExpired(String exDate, String currDate) {
        return exDate.compareTo(currDate) < 0;
    }

    public static <T extends Serializable> void loadFromFile(String fileName, List<T> list) {
        if (list.size() > 0) {
            list.clear(); // Clear the list before loading
        }
        try {
            File file = new File(fileName); // Check if the file exists
            if (!file.exists()) {
                System.out.println("File " + fileName + " not found.");
                return; // The file doesn't exist, so nothing to load
            }

            FileInputStream fi = new FileInputStream(file); // Open the file for reading
            ObjectInputStream fo = new ObjectInputStream(fi); // Create an object input stream
            T object;
            while ((object = (T) fo.readObject()) != null) {
                list.add(object); // Read objects from the file and add them to the list
            }
            fo.close();
            fi.close();
        } catch (Exception e) {
            // Handle exceptions (e.g., IOException, ClassNotFoundException)
            // e.printStackTrace();
        }
        System.out.println("Data loaded successfully from " + fileName);
    }

    public static <T extends Serializable> void saveToFile(String fileName, List<T> list) {
        if (list.isEmpty()) {
            System.out.println("Empty list.");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(fileName); // write()
            ObjectOutputStream fo = new ObjectOutputStream(f); // writeObject();
            for (T object : list) {
                fo.writeObject(object);
            }
            fo.close();
            f.close();
            System.out.println("Data saved successfully to " + fileName);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public static LocalDateTime getDateTime(String prompt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        while (true) {
            try {
                String dateTimeString = getString(prompt, "DateTime can not be empty, please try again.");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
                return dateTime;
            } catch (Exception e) {
                System.out.println(
                        "Invalid date and time format. Please enter a valid date and time in the format: " + format);
            }
        }
    }

    public static boolean confirm(String prompt) {
        while (true) {
            String choice = Utils.getString(prompt, "Can not be empty, please try again.");
            choice = Utils.formatString(choice, stringType.TO_LOWER);
            switch (choice) {
                case "y":
                    return true; // User confirmed, exit with true
                case "n":
                    return false; // User declined, exit with false
                default:
                    System.out.println("Invalid choice. Please enter 'y' or 'n'.");
            }
        }
    }

    public static LocalDate getDate(String prompt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        while (true) {
            try {
                String dateString = getString(prompt, "Date can not be empty, please try again.");
                LocalDate date = LocalDate.parse(dateString, formatter);
                return date;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter a valid date in the format: " + format);
            }
        }
    }

    public static class Zip implements Serializable {

        private List<List> data = new ArrayList<>();
        

        public Zip(List... data) {
            for (List list : data) {
                this.data.add(list);
            }
        }

        public List<List> unZip() {
            return this.data;
        }
    }

    public static <T extends Serializable> void saveToZipFile(String fileName, T object, boolean debug) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
            if (debug) {
                System.out.println("Data saved successfully to " + fileName);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static <T extends Serializable> T loadFromZipFile(String fileName, boolean debug) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            T object = (T) ois.readObject();
            if (debug) {
                System.out.println("Data loaded successfully from " + fileName);
            }
            return object;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }
    
    

}
