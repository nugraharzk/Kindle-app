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
 * id, judul, nik_owner, deskripsi, waktu, priority, long, lati, konfirmasi
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
        newV.put("nik_owner", k.getNik_owner());
        newV.put("deskripsi", k.getDeskripsi());
        newV.put("priority", k.getPriority());
        newV.put("long", k.getLon());
        newV.put("lati", k.getLat());
        newV.put("konfirmasi", k.getKonfirmasi());


        return db.insert("event", null, newV);
    }

    public Events getEvent(int id) {
        Cursor cur = null;
        Events K = new Events();


        String[] cols = new String[]{"id, judul, nik_owner, deskripsi, priority, lon, lat, konfiirmasi"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id};

        cur = db.query("event", cols, "id=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            ContentValues newV = new ContentValues();
            K.setId(cur.getInt(0));
            K.setJudul(cur.getString(1));
            K.setNik_owner(cur.getString(2));
            K.setDeskripsi(cur.getString(3));
            K.setPriority(cur.getString(4));
            K.setLon(cur.getLong(5));
            K.setLat(cur.getLong(6));
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
                K.setId(cur.getInt(0));
                K.setJudul(cur.getString(1));
                K.setNik_owner(cur.getString(2));
                K.setDeskripsi(cur.getString(3));
                K.setPriority(cur.getString(4));
                //K.setLon(cur.getLong(5));
                //K.setLat(cur.getLong(6));
                //K.setKonfirmasi(cur.getInt(5));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }
}
