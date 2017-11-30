package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * Event
 * id, judul, nik_owner, deskripsi, waktu, priority, lon, lati, konfirmasi
 */

public class Events {
    private int id, konfirmasi;
    private String judul, nik_owner, deskripsi, waktu, priority;
    private long lat, lon;

    public Events() {

    }

    public Events(int id, int konfirmasi, String judul, String nik_owner, String deskripsi, String waktu, String priority, long lat, long lon) {
        this.id = id;
        this.konfirmasi = konfirmasi;
        this.judul = judul;
        this.nik_owner = nik_owner;
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.priority = priority;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNik_owner() {
        return nik_owner;
    }

    public void setNik_owner(String nik_owner) {
        this.nik_owner = nik_owner;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }
}
