package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.upi.mobprogproject.model.Status;

/**
 * Created by amaceh on 14/11/17.
 * Status
 * id, nik, status, waktu, like
 */

public class DbStatus {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;

    public DbStatus(Context c) {
        dbHelper = new DbHelper(c);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertStatus(Status k) {
        ContentValues newV = new ContentValues();
        newV.put("nik", k.getNik());
        newV.put("status", k.getStatus());
        newV.put("waktu", k.getWaktu());
        newV.put("like", k.getLike());

        return db.insert("status", null, newV);
    }

    public Status getStatus(int id) {
        Cursor cur = null;
        Status K = new Status();

        //kolom yang diambil
        //nama, ttl, alamat, pekerjaan, status, rt, telepon
        String[] cols = new String[]{"nik, status, waktu, like"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id};

        cur = db.query("status", cols, "nik=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setId(id);
            K.setNik(cur.getString(0));
            K.setStatus(cur.getString(1));
            K.setWaktu(cur.getString(2));
            K.setLike(cur.getInt(3));
        }
        cur.close();
        return K;
    }

    public ArrayList<Status> getAllStatus() {
        Cursor cur = null;
        ArrayList<Status> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM STATUS", null);
        if (cur.moveToFirst()) {
            do {
                Status K = new Status();
                K.setId(cur.getInt(0));
                K.setNik(cur.getString(1));
                K.setStatus(cur.getString(2));
                K.setWaktu(cur.getString(3));
                K.setLike(cur.getInt(4));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }
}
