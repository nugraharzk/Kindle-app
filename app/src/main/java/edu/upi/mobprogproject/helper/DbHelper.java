package edu.upi.mobprogproject.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amaceh on 11/11/17.
 * Mofified 1/12/17
 TODO : REMOVE THIS TABLE AND LOG IN WITH SERVICE INSTEAD
 AKUN
 username PRIMARY NOT NULL
 email UNIQUE NOT NULL
 password NOT NULL
 (implement hashing sha512 atau apapun selain md5,
 pastikan varchar cukup panjang untuk hasil hashing)

 TODO : SYNC WITH SERVICE
 USER
 (ketika akun insert, otomatis user terinsert username saja,
 yang lainnya biarkan null kecuali jabatan langsung 'warga'<default value>)
 username PRIMARY NOT NULL
 FOREIGN KEY username ke tabel akun
 nama NULL
 ttl NULL
 alamat NULL
 rt NULL
 telepon NULL
 jabatan NOT NULL
 lan NULL
 lon NULL

 STATUS
 id_status auto_increment primary
 username
 FOREIGN KEY username ke tabel akun
 status NOT NULL
 waktu NOT NULL
 like (default 0)

 KOMENTAR
 id_komentar autoincrement primary
 id_status
 FOREIGN KEY id_status ke tabel status
 username
 FOREIGN KEY username ke tabel akun
 komentar NOT NULL

 AGENDA
 id_event autoincrement
 judul NOT NULL
 username (penyelenggara)
 FOREIGN KEY username ke tabel akun
 waktu NOT NULL
 tanggal NOT NULL
 priority NOT NULL
 deskripsi NOT NULL
 lon NOT NULL
 lat NOT NULL
 konfirmasi NOT NULL (default value 0)
 */

public class DbHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DBHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBKindle.db";

    // TODO remove accout table
    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE ACCOUNT(username TEXT PRIMARY KEY, email TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL)";
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE USERS(username TEXT PRIMARY KEY, nama TEXT NOT NULL, ttl TEXT, " +
                    "alamat TEXT, rt TEXT, rw TEXT, desa TEXT,telepon TEXT, pekerjaan TEXT, jabatan TEXT,lat TEXT, lng TEXT," +
                    "FOREIGN KEY (username) REFERENCES ACCOUNT(username))";
    private static final String CREATE_TABLE_STATUS =
            "CREATE TABLE STATUS(id_status INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, " +
                    "status TEXT NOT NULL, waktu TEXT NOT NULL, like INTEGER NOT NULL," +
                    "FOREIGN KEY (username) REFERENCES ACCOUNT(username))";
    private static final String CREATE_TABLE_COMMENT =
            "CREATE TABLE COMMENT(id_komentar INTEGER PRIMARY KEY AUTOINCREMENT, id_status INTEGER NOT NULL, " +
                    "username TEXT NOT NULL, comment TEXT," +
                    "FOREIGN KEY (username) REFERENCES ACCOUNT(username)," +
                    "FOREIGN KEY (id_status) REFERENCES STATUS(id_status))";
    private static final String CREATE_TABEL_EVENT =
            "CREATE TABLE EVENT(id_event INTEGER PRIMARY KEY AUTOINCREMENT, judul TEXT, username TEXT NOT NULL, " +
                    "waktu TEXT NOT NULL, priority TEXT NOT NULL, deskripsi TEXT NOT NULL, lat TEXT, lng TEXT, konfirmasi INTEGER NOT NULL," +
                    "FOREIGN KEY (username) REFERENCES ACCOUNT(username))";

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
