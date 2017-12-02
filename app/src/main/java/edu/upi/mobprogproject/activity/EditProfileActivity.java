package edu.upi.mobprogproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.picker.MyEditTextDatePicker;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

public class EditProfileActivity extends AppCompatActivity {

    DbUsers dbU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dbU = new DbUsers(this);
        dbU.open();
        new MyEditTextDatePicker(this, R.id.etTglLahir);
        Button save = findViewById(R.id.btEditProp);
    }

    public void saveProfile(View v) {
        EditText n, tmL, tL, a, rrt, w, d, t, p;
        String nama, tempatL, tanggalL, alamat,
                rt, rw, desa, telepon, pekerjaan;
        n = findViewById(R.id.etNamap);
        tmL = findViewById(R.id.etTempatLahit);
        tL = findViewById(R.id.etTglLahir);
        a = findViewById(R.id.etAlamat);
        rrt = findViewById(R.id.etRT);
        w = findViewById(R.id.etRW);
        d = findViewById(R.id.etDesa);
        t = findViewById(R.id.etTelepon);
        p = findViewById(R.id.etPekerjaan);

        nama = n.getText().toString().trim();
        tempatL = tmL.getText().toString().trim();
        tanggalL = tL.getText().toString().trim();
        alamat = a.getText().toString().trim();
        rt = rrt.getText().toString().trim();
        rw = w.getText().toString().trim();
        desa = d.getText().toString().trim();
        telepon = t.getText().toString().trim();
        pekerjaan = p.getText().toString().trim();

        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(tempatL)) {
            Toast.makeText(this, "Tempat Lahir tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(tanggalL)) {
            Toast.makeText(this, "Tanggal Lahir tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(alamat)) {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(rt)) {
            Toast.makeText(this, "RT tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(rw)) {
            Toast.makeText(this, "RW tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(desa)) {
            Toast.makeText(this, "Desa tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(telepon)) {
            Toast.makeText(this, "Telepon tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pekerjaan)) {
            Toast.makeText(this, "Pekerjaan tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }

        Users u = new Users();
        u.setNama(nama);
        u.setTtl(tempatL + "_" + tanggalL);
        u.setAlamat(alamat);
        u.setRt(rt);
        u.setRw(rw);
        u.setDesa(desa);
        u.setTelepon(telepon);
        u.setPekerjaan(pekerjaan);

        SharedPreferences sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        try {
            dbU.updateUsers(sp.getString("user", ""), u);
            Intent i = getIntent();
            setResult(RESULT_OK, i);
        } catch (Exception e) {
            Log.d("update err", e.toString());
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbU.close();
    }
}
