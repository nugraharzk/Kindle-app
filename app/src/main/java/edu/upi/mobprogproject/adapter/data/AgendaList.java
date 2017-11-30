package edu.upi.mobprogproject.adapter.data;

/**
 * Created by amaceh on 14/11/17.
 * A class of recyclerview in Agenda
 */

public class AgendaList {
    private String urgensi, tgl, bulan, namaAcara, tempat, waktu, penyelenggara;

    public AgendaList() {

    }

    public AgendaList(String urgensi, String tgl, String bulan, String namaAcara, String tempat, String waktu, String penyelenggara) {
        this.urgensi = urgensi;
        this.tgl = tgl;
        this.bulan = bulan;
        this.namaAcara = namaAcara;
        this.tempat = tempat;
        this.waktu = waktu;
        this.penyelenggara = penyelenggara;
    }

    public String getUrgensi() {
        return urgensi;
    }

    public void setUrgensi(String urgensi) {
        this.urgensi = urgensi;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getNamaAcara() {
        return namaAcara;
    }

    public void setNamaAcara(String namaAcara) {
        this.namaAcara = namaAcara;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getPenyelenggara() {
        return penyelenggara;
    }

    public void setPenyelenggara(String penyelenggara) {
        this.penyelenggara = penyelenggara;
    }
}
