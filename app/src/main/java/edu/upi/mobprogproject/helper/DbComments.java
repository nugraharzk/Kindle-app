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
 * id_komentar INTEGER PRIMARY KEY AUTOINCREMENT, id_status INTEGER NOT NULL, " +
 "username TEXT NOT NULL, comment TEXT,"
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

    public long insertComment(Comments k) {
        ContentValues newV = new ContentValues();
        newV.put("id_status", k.getId_status());
        newV.put("username", k.getUsername());
        newV.put("comment", k.getComment());

        return db.insert("status", null, newV);
    }

    public Comments getComment(int id_komentar) {
        Cursor cur = null;
        Comments K = new Comments();

        //kolom yang diambil
        //nama, ttl, alamat, pekerjaan, status, rt, telepon
        String[] cols = new String[]{"id_status", "username", "comment"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {"" + id_komentar};

        cur = db.query("status", cols, "username=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();

            K.setId_comments(id_komentar);
            K.setId_status(cur.getInt(0));
            K.setUsername(cur.getString(1));
            K.setComment(cur.getString(2));

        }
        cur.close();
        return K;
    }

    public ArrayList<Comments> getAllComments() {
        Cursor cur = null;
        ArrayList<Comments> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM COMMENT", null);
        if (cur.moveToFirst()) {
            do {
                Comments K = new Comments();
                K.setId_comments(cur.getInt(0));
                K.setId_status(cur.getInt(1));
                K.setUsername(cur.getString(2));
                K.setComment(cur.getString(3));
                out.add(K);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }
}
