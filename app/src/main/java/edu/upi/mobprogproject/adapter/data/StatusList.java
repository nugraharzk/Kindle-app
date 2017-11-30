package edu.upi.mobprogproject.adapter.data;

/**
 * Created by amaceh on 14/11/17.
 * A list to modelling data in Recycler View
 */

public class StatusList {
    private String nama, waktu, status;

    public StatusList(String nama, String waktu, String status) {
        this.nama = nama;
        this.waktu = waktu;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
