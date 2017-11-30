package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * A model to Status Table
 * Status
 * id, nik, status, waktu, like
 **/

public class Status {
    private int id, like;
    private String nik, status, waktu;

    public Status() {

    }

    public Status(int id, int like, String nik, String status, String waktu) {
        this.id = id;
        this.like = like;
        this.nik = nik;
        this.status = status;
        this.waktu = waktu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
