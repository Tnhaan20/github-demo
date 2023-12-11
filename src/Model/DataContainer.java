package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataContainer implements Serializable{
    private List<flightInfo> flightList = new ArrayList<flightInfo>();
    private List<Reservation> passengerList = new ArrayList<Reservation>();
    private List<staff> staffList = new ArrayList<staff>();
    public DataContainer(List<flightInfo> flightList, List<Reservation> passengerList, List<staff> staffList) {
        this.flightList = flightList;
        this.passengerList = passengerList;
        this.staffList = staffList;
        
    }

   
    public List<flightInfo> getFlightList() {
        return flightList;
    }
    public void setFlightList(List<flightInfo> flightList) {
        this.flightList = flightList;
    }
    public List<Reservation> getPassengerList() {
        return passengerList;
    }
    public void setPassengerList(List<Reservation> passengerList) {
        this.passengerList = passengerList;
    }
    public List<staff> getStaffList() {
        return staffList;
    }
    public void setStaffList(List<staff> staffList) {
        this.staffList = staffList;
    }

   
}
