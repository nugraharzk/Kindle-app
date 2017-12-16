package edu.upi.mobprogproject.adapter.data;

/**
 * Created by amaceh on 16/12/17.
 */

public class TetanggaList {
    private String username, nama, alamat, kontak, jabatan;

    public TetanggaList() {

    }

    public TetanggaList(String username, String nama, String alamat, String kontak, String jabatan) {
        this.username = username;
        this.nama = nama;
        this.alamat = alamat;
        this.kontak = kontak;
        this.jabatan = jabatan;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
