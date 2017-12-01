package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 14/11/17.
 * Comment
 * id_komentar INTEGER PRIMARY KEY AUTOINCREMENT, id_status INTEGER NOT NULL, " +
 "username TEXT NOT NULL, comment TEXT," +
 "FOREIGN KEY (username) REFERENCES ACCOUNT(username)," +
 "FOREIGN KEY (id_status) REFERENCES STATUS(id_status)
 */

public class Comments {
    private int id_comments, id_status;
    private String username, comment;

    public Comments() {

    }

    public Comments(int id_comments, int id_status, String username, String comment) {
        this.id_comments = id_comments;
        this.id_status = id_status;
        this.username = username;
        this.comment = comment;
    }

    public int getId_comments() {
        return id_comments;
    }

    public void setId_comments(int id_comments) {
        this.id_comments = id_comments;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
