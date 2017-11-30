package edu.upi.mobprogproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.model.Status;

public class addStatusActivity extends AppCompatActivity {

    DbStatus dbS;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);

        dbS = new DbStatus(this);
        dbS.open();

    }

    public void saveStatus(View v) {
        Status S = new Status();
        S.setWaktu(Calendar.getInstance().getTime().toString());
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        S.setNik(sp.getString("nik", ""));
        S.setLike(0);
        EditText stat = findViewById(R.id.etStatus);
        String status = stat.getText().toString().trim();
        if (TextUtils.isEmpty(status)) {
            Toast.makeText(this, "Please enter username/email", Toast.LENGTH_LONG).show();
            return;
        }

        S.setStatus(status);
        long a = dbS.insertStatus(S);
        if (a != -1) {
            Toast.makeText(this, "Status Berhasil Diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mohon maaf terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbS.close();
    }
}
