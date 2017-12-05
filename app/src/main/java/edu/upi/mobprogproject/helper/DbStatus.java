package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.upi.mobprogproject.model.Status;

/**
 * Created by amaceh on 14/11/17.
 * Status
 * id_status INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, " +
 "status TEXT, waktu TEXT NOT NULL, like INTEGER NOT NULL," +
 "FOREIGN KEY (username) REFERENCES ACCOUNT(username)
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
        newV.put("username", k.getUsername());
        newV.put("status", k.getStatus());
        newV.put("waktu", k.getWaktu());
        newV.put("like", k.getLike());

        return db.insert("status", null, newV);
    }

    public Status getStatus(int id_status) {
        Cursor cur = null;
        Status K = new Status();

        //kolom yang diambil
        //nama, ttl, alamat, pekerjaan, status, rt, telepon
        String[] cols = new String[]{"username", "status", "waktu", "like"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id_status};

        cur = db.query("status", cols, "id_status=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setId_status(id_status);
            K.setUsername(cur.getString(0));
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
                K.setId_status(cur.getInt(0));
                K.setUsername(cur.getString(1));
                K.setStatus(cur.getString(2));
                K.setWaktu(cur.getString(3));
                K.setLike(cur.getInt(4));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }

    public void update(List<Status> stat) {
        db.delete("status", null, null);
        for (Status st : stat) {
            insertStatus(st);
        }
    }
}
