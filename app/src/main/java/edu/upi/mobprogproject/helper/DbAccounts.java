package edu.upi.mobprogproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.upi.mobprogproject.model.Accounts;

/**
 * Created by amaceh on 11/11/17.
 * <p>
 * Accounts Attribute
 * username, email, password, nik, nama
 */

public class DbAccounts {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;

    public DbAccounts(Context c) {
        dbHelper = new DbHelper(c);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertAccounts(Accounts k) {
        ContentValues newV = new ContentValues();
        newV.put("username", k.getUsername());
        newV.put("email", k.getEmail());
        newV.put("password", k.getPassword());
        newV.put("nik", k.getNik());
        //newV.put("nama", k.getNama());

        return db.insert("account", null, newV);
    }

    public Accounts getAccount(String user, String password) {
        Cursor cur = null;
        Accounts K = new Accounts();

        //kolom yang diambil
        String[] cols = new String[]{"username", "password", "nik"};
        //parameter, akan mengganti ? pada NAMA=?
        String[] param = {user, password, user, password};

        cur = db.query("account", cols, "username=? AND password=? OR email=? AND password=?", param, null, null, null);

        if (cur.getCount() > 0) {  //ada data? ambil
            cur.moveToFirst();
            K.setUsername(cur.getString(0));
            K.setPassword(cur.getString(1));
            K.setNik(cur.getString(2));
            //K.setNama(cur.getString(3));
        }
        cur.close();
        return K;
    }
}
