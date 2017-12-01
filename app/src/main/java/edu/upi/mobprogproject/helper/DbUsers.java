package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        newV.put("telepon", k.getTelepon());
        newV.put("jabatan", k.getJabatan());
        newV.put("lat", k.getLat());
        newV.put("lng", k.getLng());

        return db.insert("users", null, newV);
    }

    public Users getUser(String username) {
        Cursor cur = null;
        Users K = new Users();

        //kolom yang diambil
        String[] cols = new String[]{"nama", "ttl", "alamat", "rt",
                "telepon", "jabatan", "lat", "lng"};
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
            K.setTelepon(cur.getString(4));
            K.setJabatan(cur.getString(5));
            K.setLat(cur.getString(6));
            K.setLng(cur.getString(7));
        }
        cur.close();
        return K;
    }
}
