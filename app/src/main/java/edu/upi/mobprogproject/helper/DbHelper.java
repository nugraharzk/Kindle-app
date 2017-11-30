package edu.upi.mobprogproject.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amaceh on 11/11/17.
 * A class to help connect with sqlite android
 * <p>
 * Account Attribute
 * username, email, password, nik
 * <p>
 * User Attribute
 * nik, nama, ttl, alamat, pekerjaan, status, rt, telepon
 * <p>
 * Status
 * id, nik, status, waktu, like
 * <p>
 * Comment
 * id_comm, id_status, nik, comment
 * <p>
 * Event
 * id, judul, nik_owner, deskripsi, waktu, priority, long, lati, konfirmasi
 */

public class DbHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DBHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBKindle.db";

    // TODO table create statement
    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE ACCOUNT(username TEXT PRIMARY KEY, email TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL, nik TEXT UNIQUE NOT NULL)";
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE USERS(nik TEXT PRIMARY KEY, nama TEXT NOT NULL, ttl TEXT, " +
                    "alamat TEXT, pekerjaan TEXT, status TEXT, rt TEXT, telepon TEXT," +
                    "FOREIGN KEY (nik) REFERENCES ACCOUNT(nik))";
    private static final String CREATE_TABLE_STATUS =
            "CREATE TABLE STATUS(id INTEGER PRIMARY KEY AUTOINCREMENT, nik TEXT NOT NULL, " +
                    "status TEXT, waktu TEXT NOT NULL, like INTEGER NOT NULL," +
                    "FOREIGN KEY (nik) REFERENCES ACCOUNT(nik))";
    private static final String CREATE_TABLE_COMMENT =
            "CREATE TABLE COMMENT(id_comm INTEGER PRIMARY KEY AUTOINCREMENT, id_status INTEGER NOT NULL, " +
                    "nik TEXT NOT NULL, comment TEXT," +
                    "FOREIGN KEY (nik) REFERENCES ACCOUNT(nik)," +
                    "FOREIGN KEY (id_status) REFERENCES STATUS(id))";
    private static final String CREATE_TABEL_EVENT =
            "CREATE TABLE EVENT(id INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT, nik_owner TEXT NOT NULL, " +
                    "deskripsi TEXT, waktu TEXT, priority TEXT, long REAL, lati REAL, konfirmasi INTEGER," +
                    "FOREIGN KEY (nik_owner) REFERENCES ACCOUNT(nik))";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STATUS);
        db.execSQL(CREATE_TABLE_COMMENT);
        db.execSQL(CREATE_TABEL_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //jika app diupgrade (diinstall yang baru) maka database akan dicreate ulang (data hilang)
        //jika tidak tidak ingin hilang, bisa diproses disini
        db.execSQL("DROP TABLE IF EXISTS ACCOUNT");
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS STATUS");
        db.execSQL("DROP TABLE IF EXISTS COMMENT");
        db.execSQL("DROP TABLE IF EXISTS EVENT");
//        db.execSQL(scriptUpdate);
//        db.needUpgrade(newVersion);

        onCreate(db);
    }
}
