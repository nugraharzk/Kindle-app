package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * Comment
 * id_comm, id_status, nik, comment
 */

public class Comments {
    private int id_com, id_stat;
    private String nik, comment;

    public Comments() {

    }

    public Comments(int id_com, int id_stat, String nik, String comment) {
        this.id_com = id_com;
        this.id_stat = id_stat;
        this.nik = nik;
        this.comment = comment;
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_stat() {
        return id_stat;
    }

    public void setId_stat(int id_stat) {
        this.id_stat = id_stat;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
