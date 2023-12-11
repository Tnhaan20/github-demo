/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import Controller.Utils;
/**
 *
 * @author Asus
 */
public class Reservation implements Serializable{
    private String idResereved, name, contact, detail, flightNumberReserved, seatNum;
    private boolean checkIn = false;
    public Reservation(String idResereved,String flightNumberReserved, String name, String contact, String detail, 
            String seatNum) {
        this.idResereved = idResereved;
        this.name = name;
        this.contact = contact;
        this.detail = detail;
        this.flightNumberReserved = flightNumberReserved;
        this.seatNum = seatNum;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }
    
    

    public String getIdResereved() {
        return idResereved;
    }


    public void setIdResereved(String idResereved) {
        this.idResereved = idResereved;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getContact() {
        return contact;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }


    public String getDetail() {
        return detail;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getFlightNumberReserved() {
        return flightNumberReserved;
    }


    public void setFlightNumberReserved(String flightNumberReserved) {
        this.flightNumberReserved = flightNumberReserved;
    }


    public String getSeatNum() {
        return seatNum;
    }


    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }


    public void printinfo() {
        System.out.format("%-25s|%-25s|%-25s|%-25s|%-25s|%-25s%n", idResereved, flightNumberReserved, Utils.formatString(name, Utils.stringType.NAME), contact, detail, seatNum);

    }


    

}
