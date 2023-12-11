package Model;

import Controller.Utils;
import java.io.Serializable;
import java.util.*;


public class staff implements Serializable{
    String id, namePilot, rolePilot;
    List<flightInfo> assignedFlights = new ArrayList<>(); // Danh sách chuyến bay mà nhân viên đang phụ trách

    public staff(String id, String namePilot, String rolePilot) {
        this.id = id;
        this.namePilot = namePilot;
        this.rolePilot = rolePilot;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getNamePilot() {
        return namePilot;
    }

    public void setNamePilot(String namePilot) {
        this.namePilot = namePilot;
    }

    public String getrolePilot() {
        return rolePilot;
    }

    public void setrolePilot(String rolePilot) {
        this.rolePilot = rolePilot;
    }

    public List<flightInfo> getAssignedFlights() {
        return assignedFlights;
    }

    public void assignFlight(flightInfo flight) {
        assignedFlights.add(flight);
    }

    public void unassignFlight(flightInfo flight) {
        assignedFlights.remove(flight);
    }

    public void printInfo() {
        System.out.format("%-25s | %-25s | %-25s%n", id, Controller.Utils.formatString(namePilot, Utils.stringType.NAME),Controller.Utils.formatString(rolePilot, Utils.stringType.NAME));
        
    }
}
