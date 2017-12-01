package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.upi.mobprogproject.model.Events;

/**
 * Created by amaceh on 14/11/17.
 * Event
 * id_event INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT, username TEXT NOT NULL, " +
 "waktu TEXT NOT NULL, priority TEXT NOT NULL, deskripsi TEXT NOT NULL, lat TEXT, lng TEXT, konfirmasi INTEGER,"
 */

public class DbEvents {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;

    public DbEvents(Context c) {
        dbHelper = new DbHelper(c);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertEvents(Events k) {
        ContentValues newV = new ContentValues();
        newV.put("judul", k.getJudul());
        newV.put("username", k.getUsername());
        newV.put("waktu", k.getWaktu());
        newV.put("priority", k.getPriority());
        newV.put("deskripsi", k.getDeskripsi());
        newV.put("lat", k.getLat());
        newV.put("lng", k.getLng());
        newV.put("konfirmasi", k.getKonfirmasi());

        return db.insert("event", null, newV);
    }

    public Events getEvent(int id_event) {
        Cursor cur = null;
        Events K = new Events();


        String[] cols = new String[]{"judul", "username", "waktu", "priority"
                , "deskripsi", "lat", "lng", "konfirmasi"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id_event};

        cur = db.query("event", cols, "id_event=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setId_event(id_event);
            K.setJudul(cur.getString(0));
            K.setUsername(cur.getString(1));
            K.setWaktu(cur.getString(2));
            K.setPriority(cur.getString(3));
            K.setDeskripsi(cur.getString(4));
            K.setLat(cur.getString(5));
            K.setLng(cur.getString(6));
            K.setKonfirmasi(cur.getInt(7));
        }
        cur.close();
        return K;
    }

    public ArrayList<Events> getAllEvents() {
        Cursor cur = null;
        ArrayList<Events> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM EVENT", null);
        if (cur.moveToFirst()) {
            do {
                Events K = new Events();
                K.setId_event(cur.getInt(0));
                K.setJudul(cur.getString(1));
                K.setUsername(cur.getString(2));
                K.setWaktu(cur.getString(3));
                K.setPriority(cur.getString(4));
                K.setDeskripsi(cur.getString(5));
                K.setLat(cur.getString(6));
                K.setLng(cur.getString(7));
                K.setKonfirmasi(cur.getInt(8));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }
}
