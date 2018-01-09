package edu.upi.mobprogproject.model;

/**
 * Created by Rizki on 1/9/2018.
 */

public class Notifications {

    private int id_notif;
    private String username, pesan, urgensi;

    public Notifications(int id_notif, String username, String pesan, String urgensi) {
        this.id_notif = id_notif;
        this.username = username;
        this.pesan = pesan;
        this.urgensi = urgensi;
    }

    public Notifications() {
    }

    public int getId_notif() {
        return id_notif;
    }

    public void setId_notif(int id_notif) {
        this.id_notif = id_notif;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getUrgensi() {
        return urgensi;
    }

    public void setUrgensi(String urgensi) {
        this.urgensi = urgensi;
    }
}
