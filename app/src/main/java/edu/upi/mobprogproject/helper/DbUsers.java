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
 * nik, nama, ttl, alamat, pekerjaan, status, rt, telepon
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
        newV.put("nik", k.getNik());
        newV.put("nama", k.getNama());
        newV.put("ttl", k.getTtl());
        newV.put("alamat", k.getAlamat());
        newV.put("pekerjaan", k.getPekerjaan());
        newV.put("status", k.getStatus());
        newV.put("rt", k.getRt());
        newV.put("telepon", k.getTelepon());


        return db.insert("users", null, newV);
    }

    public Users getUser(String nik) {
        Cursor cur = null;
        Users K = new Users();

        //kolom yang diambil
        //nama, ttl, alamat, pekerjaan, status, rt, telepon
        String[] cols = new String[]{"nama", "ttl", "alamat", "pekerjaan",
                "status", "rt", "telepon"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {nik};

        cur = db.query("users", cols, "nik=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setNik(nik);
            K.setNama(cur.getString(0));
            K.setTtl(cur.getString(1));
            K.setAlamat(cur.getString(2));
            K.setPekerjaan(cur.getString(3));
            K.setStatus(cur.getString(4));
            K.setRt(cur.getInt(5));
            K.setTelepon(cur.getString(6));
        }
        cur.close();
        return K;
    }
}
