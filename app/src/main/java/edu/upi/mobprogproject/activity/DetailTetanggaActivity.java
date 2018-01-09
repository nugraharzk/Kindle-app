package edu.upi.mobprogproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.content.HomeFragment;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

import static java.util.Calendar.YEAR;

public class DetailTetanggaActivity extends AppCompatActivity {

    private Users data;
    private DbUsers dbU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tetangga);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent in = getIntent();
        String username = in.getStringExtra(HomeFragment.EXTRA_MESSAGE);
        dbU = new DbUsers(this);
        dbU.open();
        data = dbU.getUser(username);
        setProfile();
        ImageView img = findViewById(R.id.imageView2);
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.cover)
                .into(img);
        ImageView img2 = findViewById(R.id.circle);
        Glide.with(this)
                .asBitmap()
                .apply(RequestOptions.circleCropTransform())
                .load(R.drawable.profile_user)
                .into(img2);

        Button call = findViewById(R.id.btTPhone);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + data.getTelepon()));
                startActivity(intent);
            }
        });
    }

    private void setProfile() {

        TextView nama, kerja_umur, alamat, telepon, username;
        nama = findViewById(R.id.tvNama);
        kerja_umur = findViewById(R.id.tvKerUmur);
        alamat = findViewById(R.id.tvAlamat);
        telepon = findViewById(R.id.tvPhone);
        username = findViewById(R.id.tvUsername);
        if (data != null) {
            nama.setText(data.getNama());
            if (data.getTtl() != null && data.getAlamat() != null
                    && data.getTelepon() != null && data.getPekerjaan() != null) {
                String fill = getString(R.string.name_fill, data.getPekerjaan(), yearGenerator(data.getTtl()));
                kerja_umur.setText(fill);
                alamat.setText(data.getAlamat());
                telepon.setText(data.getTelepon());
            }
            username.setText(getString(R.string.user_fill, data.getUsername()));
        }
    }

    //    private void setData() {
//        TextView username, nama, ttl, alamat, rtrw, desa, telepon, pekerjaan;
//        username = findViewById(R.id.tvTDuser);
//        nama = findViewById(R.id.tvTDNama);
//        ttl = findViewById(R.id.tvTDTtl);
//        alamat = findViewById(R.id.tvTDalamat);
//        rtrw = findViewById(R.id.tvTDRtRw);
//        desa = findViewById(R.id.tvTDDesa);
//        telepon = findViewById(R.id.tvTDKontak);
//        pekerjaan = findViewById(R.id.tvTDPekerjaan);
//        username.setText(data.getUsername());
//        nama.setText(data.getNama());
//        String ttl_data = data.getTtl();
//        if (ttl_data != null) {
//            ttl_data = ttl_data.replace("_", ",");
//            ttl.setText(ttl_data);
//        }
//        alamat.setText(data.getAlamat());
//        String tw = this.getString(R.string.rtrw, data.getRt(), data.getRw());
//        rtrw.setText(tw);
//        desa.setText(data.getDesa());
//        telepon.setText(data.getTelepon());
//        pekerjaan.setText(data.getPekerjaan());
//    }
    public String yearGenerator(String ttl) {
        if (ttl != null) {
            String[] getTgl = ttl.split("_");
            if (getTgl.length > 0) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Calendar born = Calendar.getInstance();
                    Calendar curr = Calendar.getInstance();

                    Date date = format.parse(getTgl[1]);
                    born.setTime(date);

                    int diff = curr.get(YEAR) - born.get(YEAR);
                    return ("" + diff);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return "X";
    }
}
