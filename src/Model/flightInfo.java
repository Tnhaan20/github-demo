/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Controller.Utils;

/**
 *
 * @author Asus
 */
public class flightInfo implements Serializable {

    protected String flightNum, departCity, destiCity;
    protected LocalDateTime departTime, arrivalTime;
    protected int availableSeat, totalSeat, pCount = 0, sCount = 0, gCount = 0;
    protected long duration;
    private List<staff> staffs = new ArrayList<staff>();
    private Map<String, String> seats;
    private List<Reservation> reservedList = new ArrayList<>();

    public flightInfo(String flightNum, String departCity, String destiCity, LocalDateTime departTime, LocalDateTime arrivalTime,
            int availableSeat, int totalSeat) {
        this.flightNum = flightNum;
        this.departCity = departCity;
        this.destiCity = destiCity;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.availableSeat = availableSeat;
        this.totalSeat = totalSeat;

    }

    public int getpCount() {
        return pCount;
    }

    public void setpCount(int pCount) {
        this.pCount = pCount;
    }

    public int getsCount() {
        return sCount;
    }

    public void setsCount(int sCount) {
        this.sCount = sCount;
    }

    public int getgCount() {
        return gCount;
    }

    public void setgCount(int gCount) {
        this.gCount = gCount;
    }
    
    public List<Reservation> getReservedList() {
        return reservedList;
    }

    public void setReservedList(List<Reservation> reservedList) {
        this.reservedList = reservedList;
    }

    public List<staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<staff> staffs) {
        this.staffs = staffs;
    }

    public Map<String, String> getSeats() {
        return seats;
    }

    public void setSeats(Map<String, String> seats) {
        this.seats = seats;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDestiCity() {
        return destiCity;
    }

    public void setDestiCity(String destiCity) {
        this.destiCity = destiCity;
    }

    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        this.departTime = departTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public List<staff> getCrewMembers() {
        return staffs;
    }

    public void addCrewMember(staff crewMember) {
        staffs.add(crewMember);
    }
    
    public void del(staff crewMember){
        staffs.clear();
    }

    public void printinfo() {
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm");
        System.out.format("%-25s|%-35s|%-25s|%-25s|%-25s|%-25s|%-25s%n", flightNum, crewToString(staffs), Utils.formatString(departCity, Utils.stringType.NAME), Utils.formatString(destiCity, Utils.stringType.NAME),
                departTime.format(dtm), arrivalTime.format(dtm), availableSeat);
    }

    // Tạo một phương thức để chuyển danh sách staffs thành một chuỗi hiển thị
    private String crewToString(List<staff> staffs) {

        StringBuilder crewString = new StringBuilder();

        // Chỉ lấy 2 nhân viên đầu tiên 
        int maxPrint = 2;
        for (int i = 0; i < maxPrint && i < staffs.size(); i++) {
            staff staff = staffs.get(i);
            crewString.append(Utils.formatString(staff.getNamePilot(), Utils.stringType.NAME))
                    .append(" (")
                    .append(Utils.formatString(staff.getrolePilot(), Utils.stringType.NAME))
                    .append("), ");
        }

        // Nếu vẫn còn nhân viên chưa được in
        if (staffs.size() > maxPrint) {
            crewString.append("...");
        }

        return crewString.toString();

    }

}
