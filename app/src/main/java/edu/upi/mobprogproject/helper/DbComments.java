package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.upi.mobprogproject.model.Comments;

/**
 * Created by amaceh on 14/11/17.
 * Model file to load data from Comments
 * Comment
 * id_comm, id_status, nik, comment
 */

public class DbComments {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;

    public DbComments(Context c) {
        dbHelper = new DbHelper(c);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertComments(Comments k) {
        ContentValues newV = new ContentValues();
        newV.put("id_status", k.getId_stat());
        newV.put("nik", k.getNik());
        newV.put("comment", k.getComment());

        return db.insert("status", null, newV);
    }

    public Comments getStatus(int id) {
        Cursor cur = null;
        Comments K = new Comments();

        //kolom yang diambil
        //nama, ttl, alamat, pekerjaan, status, rt, telepon
        String[] cols = new String[]{"nik, status, waktu, like"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id};

        cur = db.query("status", cols, "nik=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setId_com(cur.getInt(0));
            K.setId_stat(cur.getInt(1));
            K.setNik(cur.getString(3));
            K.setComment(cur.getString(4));
        }
        cur.close();
        return K;
    }

    public ArrayList<Comments> getAllComments() {
        Cursor cur = null;
        ArrayList<Comments> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM STATUS", null);
        if (cur.moveToFirst()) {
            do {
                Comments K = new Comments();
                K.setId_stat(cur.getInt(0));
                K.setId_com(cur.getInt(1));
                K.setNik(cur.getString(2));
                K.setComment(cur.getString(3));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }
}
