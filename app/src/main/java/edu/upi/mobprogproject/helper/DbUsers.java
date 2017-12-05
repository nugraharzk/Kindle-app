package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.upi.mobprogproject.model.Users;

/**
 * Created by amaceh on 14/11/17.
 * A class to connect to user Table
 * User Attribute
 * username TEXT PRIMARY KEY, nama TEXT NOT NULL, ttl TEXT, " +
 "alamat TEXT, rt TEXT, telepon TEXT, jabatan TEXT,lat TEXT, lng TEXT" +
 "FOREIGN KEY (username) REFERENCES ACCOUNT(username)
 */

public class DbUsers {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;

    public DbUsers(Context c) {
        dbHelper = new DbHelper(c);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertUsers(Users k) {
        ContentValues newV = new ContentValues();
        newV.put("username", k.getUsername());
        newV.put("nama", k.getNama());
        newV.put("ttl", k.getTtl());
        newV.put("alamat", k.getAlamat());
        newV.put("rt", k.getRt());
        newV.put("rw", k.getRw());
        newV.put("desa", k.getDesa());
        newV.put("telepon", k.getTelepon());
        newV.put("jabatan", k.getJabatan());
        newV.put("lat", k.getLat());
        newV.put("lng", k.getLng());
        newV.put("pekerjaan", k.getPekerjaan());

        return db.insert("users", null, newV);
    }

    public Users getUser(String username) {
        Cursor cur = null;
        Users K = new Users();

        //kolom yang diambil
        String[] cols = new String[]{"nama", "ttl", "alamat", "rt",
                "rw", "desa", "telepon", "pekerjaan", "jabatan", "lat", "lng"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {username};

        cur = db.query("users", cols, "username=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setUsername(username);
            K.setNama(cur.getString(0));
            K.setTtl(cur.getString(1));
            K.setAlamat(cur.getString(2));
            K.setRt(cur.getString(3));
            K.setRw(cur.getString(4));
            K.setDesa(cur.getString(5));
            K.setTelepon(cur.getString(6));
            K.setPekerjaan(cur.getString(7));
            K.setJabatan(cur.getString(8));
            K.setLat(cur.getString(9));
            K.setLng(cur.getString(10));
        }
        cur.close();
        return K;
    }

    public long updateUsers(String username, Users k) {
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {username};

        ContentValues upV = new ContentValues();
        upV.put("nama", k.getNama());
        upV.put("ttl", k.getTtl());
        upV.put("alamat", k.getAlamat());
        upV.put("rt", k.getRt());
        upV.put("rw", k.getRw());
        upV.put("desa", k.getDesa());
        upV.put("telepon", k.getTelepon());
        upV.put("lat", k.getLat());
        upV.put("lng", k.getLng());
        upV.put("pekerjaan", k.getPekerjaan());

        return db.update("users", upV, "username=?", param);
    }

    public void update(List<Users> user) {
        db.delete("users", null, null);
        for (Users us : user) {
            insertUsers(us);
        }
    }

    public ArrayList<Users> getAllUsers() {
        Cursor cur = null;
        ArrayList<Users> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM USERS", null);
        if (cur.moveToFirst()) {
            do {
                Users K = new Users();
                K.setUsername(cur.getString(0));
                K.setNama(cur.getString(1));
                K.setTtl(cur.getString(2));
                K.setAlamat(cur.getString(3));
                K.setRt(cur.getString(4));
                K.setRw(cur.getString(5));
                K.setDesa(cur.getString(6));
                K.setTelepon(cur.getString(7));
                K.setPekerjaan(cur.getString(8));
                K.setJabatan(cur.getString(9));
                K.setLat(cur.getString(10));
                K.setLng(cur.getString(11));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }
}
