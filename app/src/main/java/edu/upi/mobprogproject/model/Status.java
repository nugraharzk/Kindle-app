package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * A model to Status Table
 * Status
 * id_status INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, " +
 "status TEXT, waktu TEXT NOT NULL, like INTEGER NOT NULL," +
 "FOREIGN KEY (username) REFERENCES ACCOUNT(username)
 **/

public class Status {
    private int id_status, like;
    private String username, status, waktu;

    public Status() {

    }

    public Status(int id_status, int like, String username, String status, String waktu) {
        this.id_status = id_status;
        this.like = like;
        this.username = username;
        this.status = status;
        this.waktu = waktu;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
