package edu.upi.mobprogproject.model;

/**
 * Created by amaceh on 11/11/17.
 * Class to model Account User in Databases
 * Accounts Attribute
 * username TEXT PRIMARY KEY, email TEXT UNIQUE NOT NULL, " +
 "password TEXT NOT NULL
 */

public class Accounts {
    private String username, email, password;


    public Accounts() {

    }

    public Accounts(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
}
