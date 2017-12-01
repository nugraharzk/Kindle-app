package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * Event
 * CREATE TABLE EVENT(id_event INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT, username TEXT NOT NULL, " +
 "waktu TEXT NOT NULL, priority TEXT NOT NULL, deskripsi TEXT NOT NULL, lat TEXT, lng TEXT, konfirmasi INTEGER," +
 "FOREIGN KEY (username) REFERENCES ACCOUNT(username))
 */

public class Events {
    private int id_event, konfirmasi;
    private String judul, username, waktu, priority, deskripsi, lat, lng;

    public Events() {

    }

    public Events(int id_event, int konfirmasi, String judul, String username, String waktu, String priority, String deskripsi, String lat, String lng) {
        this.id_event = id_event;
        this.konfirmasi = konfirmasi;
        this.judul = judul;
        this.username = username;
        this.waktu = waktu;
        this.priority = priority;
        this.deskripsi = deskripsi;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(int konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
