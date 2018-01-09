package edu.upi.mobprogproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.picker.MyEditTextDatePicker;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    public static final int ACT2_REQUEST = 110;

    DbUsers dbU;
    private EditText n, tmL, tL, a, rrt, w, d, t, p;
    SharedPreferences sp;

    ImageView backBtn;
    private static int PLACE_PICKER_REQUEST = 1;
    private LatLng latLng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });

        Button location = findViewById(R.id.btGetLokasi);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    startActivityForResult(builder.build(EditProfileActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        n = findViewById(R.id.etNamap);
        tmL = findViewById(R.id.etTempatLahit);
        tL = findViewById(R.id.etTglLahir);
        a = findViewById(R.id.etAlamat);
        rrt = findViewById(R.id.etRT);
        w = findViewById(R.id.etRW);
        d = findViewById(R.id.etDesa);
        t = findViewById(R.id.etTelepon);
        p = findViewById(R.id.etPekerjaan);
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);

        dbU = new DbUsers(this);
        dbU.open();
        Users us = dbU.getUser(sp.getString("user", ""));
        n.setText(us.getNama());
        //tmL.setText();
        String ttl = us.getTtl();
        if (ttl != null) {
            String[] ttl_split = ttl.split("_");
            if (ttl_split.length > 0) {
                tmL.setText(ttl_split[0]);
                tL.setText(ttl_split[1]);
            }
        }
        a.setText(us.getAlamat());
        rrt.setText(us.getRt());
        w.setText(us.getRw());
        d.setText(us.getDesa());
        t.setText(us.getTelepon());
        p.setText(us.getPekerjaan());

        //TODO i just need this class instance, not really need it to be used. so dont delete this code
        new MyEditTextDatePicker(this, R.id.etTglLahir);
    }

    public void editAkun(View v) {
        Intent ix = new Intent(this, EditAkunActivity.class);
        startActivityForResult(ix, ACT2_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = getIntent();
        if (requestCode == ACT2_REQUEST && resultCode != 0) {
            setResult(RESULT_OK, i);
            finish();
        }else if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this, data);
                latLng = place.getLatLng();
                String toast = String.format("Tempat: %s .", place.getName());
                Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Akun gagal diperbaharui", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void saveProfile(View v) {
        String nama, tempatL, tanggalL, alamat,
                rt, rw, desa, telepon, pekerjaan;


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
        if (latLng != null){
            u.setLat(Double.toString(latLng.latitude));
            u.setLng(Double.toString(latLng.longitude));
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Users> call = apiInterface.putUser(sp.getString("user", ""), nama, tempatL + "_" + tanggalL, alamat, rt, rw, desa, telepon, pekerjaan, null, null, "warga");

        Intent i = getIntent();
        try {
            dbU.updateUsers(sp.getString("user", ""), u);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });
            setResult(RESULT_OK, i);
        } catch (Exception e) {
            setResult(RESULT_CANCELED, i);
            Log.d("update err", e.toString());
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbU.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = getIntent();
        setResult(RESULT_CANCELED, i);
        finish();
    }
}
