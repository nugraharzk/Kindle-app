package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * User Attribute
 * nik, nama, ttl, alamat, pekerjaan, status, rt, telepon
 */

public class Users {
    int rt;
    private String nik, nama, ttl, alamat, pekerjaan, status, telepon;

    public Users() {

    }

    public Users(String nik, String nama) {
        this.nik = nik;
        this.nama = nama;
    }

    public Users(String nik, String nama, String ttl, String alamat, String pekerjaan, String status, int rt, String telepon) {
        this.nik = nik;
        this.nama = nama;
        this.ttl = ttl;
        this.alamat = alamat;
        this.pekerjaan = pekerjaan;
        this.status = status;
        this.rt = rt;
        this.telepon = telepon;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
