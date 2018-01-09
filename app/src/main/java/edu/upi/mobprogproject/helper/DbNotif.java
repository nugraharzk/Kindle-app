package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.model.Notifications;

public class DbNotif {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;

    public DbNotif(Context c) {
        dbHelper = new DbHelper(c);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertNotif(Notifications n) {
        ContentValues newV = new ContentValues();
        newV.put("username", n.getUsername());
        newV.put("pesan", n.getPesan());
        newV.put("urgensi", n.getUrgensi());

        return db.insert("notif", null, newV);
    }

    public Notifications getNotif(int id_notif) {
        Cursor cur = null;
        Notifications N = new Notifications();


        String[] cols = new String[]{"username", "pesan", "urgensi"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id_notif};

        cur = db.query("notif", cols, "id_notif=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            N.setId_notif(id_notif);
            N.setUsername(cur.getString(0));
            N.setPesan(cur.getString(1));
            N.setUrgensi(cur.getString(2));
        }
        cur.close();
        return N;
    }

    public ArrayList<Notifications> getAllNotif() {
        Cursor cur = null;
        ArrayList<Notifications> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM NOTIF", null);
        if (cur.moveToFirst()) {
            do {
                Notifications K = new Notifications();
                K.setId_notif(cur.getInt(0));
                K.setUsername(cur.getString(1));
                K.setPesan(cur.getString(2));
                K.setUrgensi(cur.getString(3));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }

    public void update(List<Notifications> even) {
        db.delete("notif", null, null);
        for (Notifications ev : even) {
            insertNotif(ev);
        }
    }
}
