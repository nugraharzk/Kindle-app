package edu.upi.mobprogproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.picker.MyEditTextDatePicker;
import edu.upi.mobprogproject.picker.MyEditTextTimePicker;
import edu.upi.mobprogproject.helper.DbEvents;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = AddEventActivity.class.getSimpleName();


    SharedPreferences sp;
    DbEvents dbE;
    DbUsers dbU;
    Spinner urgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        dbE = new DbEvents(this);
        dbU = new DbUsers(this);
        dbE.open();
        dbU.open();
        urgen = findViewById(R.id.spinner_urgensi);
        ArrayList<String> pilihan = new ArrayList<>();
        pilihan.add("Biasa");
        pilihan.add("Penting");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pilihan);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        urgen.setAdapter(dataAdapter);

        new MyEditTextDatePicker(this, R.id.etTgl);
        new MyEditTextTimePicker(this, R.id.etWaktu);
    }

    public void saveEvent(View v) {
        Events E = new Events();

        EditText j = findViewById(R.id.etJudul);
        EditText d = findViewById(R.id.etDesc);
        EditText t = findViewById(R.id.etTgl);
        EditText w = findViewById(R.id.etWaktu);

        String judul = j.getText().toString().trim();
        String deskripsi = d.getText().toString().trim();
        String tanggal = t.getText().toString().trim();
        String waktu = w.getText().toString().trim();
        String urgensi = String.valueOf(urgen.getSelectedItem());
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

        if (TextUtils.isEmpty(tanggal)) {
            Toast.makeText(this, "tanggal tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }

        E.setJudul(judul);
        E.setDeskripsi(deskripsi);
        E.setWaktu(waktu + "." + tanggal);
        E.setPriority(urgensi);
        E.setKonfirmasi(0);
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        E.setUsername(sp.getString("user", ""));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Events> call = apiInterface.postEvent(E.getJudul(), sp.getString("user", ""), E.getWaktu(), E.getPriority(), E.getDeskripsi(), E.getLat(), E.getLng(), String.valueOf(E.getKonfirmasi()));


        long a = dbE.insertEvents(E);
        if (a != -1) {
            call.enqueue(new Callback<Events>() {
                @Override
                public void onResponse(Call<Events> call, Response<Events> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<Events> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });
            Toast.makeText(this, "Event Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
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
        Intent intent2 = getIntent();
        setResult(RESULT_OK, intent2);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbE.close();
        dbU.close();
    }
}
