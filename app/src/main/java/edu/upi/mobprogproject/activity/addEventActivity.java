package edu.upi.mobprogproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbEvents;
import edu.upi.mobprogproject.model.Events;

public class addEventActivity extends AppCompatActivity {

    SharedPreferences sp;
    DbEvents dbE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        dbE = new DbEvents(this);
        dbE.open();
    }

    public void saveEvent(View v) {
        Events E = new Events();

        EditText j = findViewById(R.id.etJudul);
        EditText d = findViewById(R.id.etDesc);
        EditText w = findViewById(R.id.etWaktu);
        EditText u = findViewById(R.id.etUrgensi);

        String judul = j.getText().toString().trim();
        String deskripsi = d.getText().toString().trim();
        String waktu = w.getText().toString().trim();
        String urgensi = u.getText().toString().trim();

        if (TextUtils.isEmpty(judul)) {
            Toast.makeText(this, "judul tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(deskripsi)) {
            Toast.makeText(this, "deskripsi tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(waktu)) {
            Toast.makeText(this, "waktu tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(urgensi)) {
            Toast.makeText(this, "urgensi tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }

        E.setJudul(judul);
        E.setDeskripsi(deskripsi);
        E.setWaktu(waktu);
        E.setPriority(urgensi);

        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        E.setNik_owner(sp.getString("nik", ""));
        long a = dbE.insertEvents(E);
        if (a != -1) {
            Toast.makeText(this, "Event Berhasil DItambahkan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mohon maaf terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }
        /*
        EditText stat= (EditText) findViewById(R.id.etStatus);
        String status=stat.getText().toString().trim();
        if(TextUtils.isEmpty(status)){
            Toast.makeText(this,"Please enter username/email",Toast.LENGTH_LONG).show();
            return;
        }

        S.setStatus(status);
        long a =dbS.insertStatus(S);
        if (a!=-1){
            Toast.makeText(this, "Status Berhasil Diperbaharui", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Mohon maaf terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }
        */
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbE.close();
    }
}
