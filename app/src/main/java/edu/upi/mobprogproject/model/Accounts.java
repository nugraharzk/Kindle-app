package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 11/11/17.
 * Class to model Account User in Databases
 * Accounts Attribute
 * username, email, password, nik, nama
 */

public class Accounts {
    private String username, email, password, nik;


    public Accounts() {

    }

    public Accounts(String username, String email, String password, String nik) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nik = nik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }
}
