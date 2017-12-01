package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * User Attribute
 * username TEXT PRIMARY KEY, nama TEXT NOT NULL, ttl TEXT, " +
 "alamat TEXT, rt TEXT, telepon TEXT, jabatan TEXT,lat TEXT, lng TEXT" +
 "FOREIGN KEY (username) REFERENCES ACCOUNT(username)
 */

public class Users {
    private String username, nama, ttl, alamat, rt, telepon, jabatan, lat, lng;

    public Users() {

    }

    public Users(String username, String nama) {

        this.nama = nama;
        this.username = username;
    }

    public Users(String username, String nama, String ttl, String alamat, String rt, String telepon, String jabatan, String lat, String lng) {
        this.username = username;
        this.nama = nama;
        this.ttl = ttl;
        this.alamat = alamat;
        this.rt = rt;
        this.telepon = telepon;
        this.jabatan = jabatan;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
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
