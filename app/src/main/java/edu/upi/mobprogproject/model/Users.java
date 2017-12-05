
package edu.upi.mobprogproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Users implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("ttl")
    @Expose
    private String ttl;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("rt")
    @Expose
    private String rt;
    @SerializedName("rw")
    @Expose
    private String rw;
    @SerializedName("desa")
    @Expose
    private String desa;
    @SerializedName("telepon")
    @Expose
    private String telepon;
    @SerializedName("pekerjaan")
    @Expose
    private String pekerjaan;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    private final static long serialVersionUID = -3945537379071229002L;

    /**
     * No args constructor for use in serialization
     */
    public Users() {
    }

    /**
     *
     * @param jabatan
     * @param username
     * @param telepon
     * @param lng
     * @param rt
     * @param nama
     * @param desa
     * @param alamat
     * @param ttl
     * @param pekerjaan
     * @param rw
     * @param lat
     */
    public Users(String username, String nama, String ttl, String alamat, String rt, String rw, String desa, String telepon, String pekerjaan, String lat, String lng, String jabatan) {
        super();
        this.username = username;
        this.nama = nama;
        this.ttl = ttl;
        this.alamat = alamat;
        this.rt = rt;
        this.rw = rw;
        this.desa = desa;
        this.telepon = telepon;
        this.pekerjaan = pekerjaan;
        this.lat = lat;
        this.lng = lng;
        this.jabatan = jabatan;
    }

//    public Users(String username, String nama) {
//        super();
//        this.nama = nama;
//        this.username = username;
//        this.jabatan = "warga";
//    }

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

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

}