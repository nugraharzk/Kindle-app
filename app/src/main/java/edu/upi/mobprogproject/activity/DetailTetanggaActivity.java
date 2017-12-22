package edu.upi.mobprogproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.content.HomeFragment;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

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
        setData();
    }

    private void setData() {
        TextView username, nama, ttl, alamat, rtrw, desa, telepon, pekerjaan;
        username = findViewById(R.id.tvTDuser);
        nama = findViewById(R.id.tvTDNama);
        ttl = findViewById(R.id.tvTDTtl);
        alamat = findViewById(R.id.tvTDalamat);
        rtrw = findViewById(R.id.tvTDRtRw);
        desa = findViewById(R.id.tvTDDesa);
        telepon = findViewById(R.id.tvTDKontak);
        pekerjaan = findViewById(R.id.tvTDPekerjaan);
        username.setText(data.getUsername());
        nama.setText(data.getNama());
        String ttl_data = data.getTtl();
        if (ttl_data != null) {
            ttl_data = ttl_data.replace("_", ",");
            ttl.setText(ttl_data);
        }
        alamat.setText(data.getAlamat());
        String tw = this.getString(R.string.rtrw, data.getRt(), data.getRw());
        rtrw.setText(tw);
        desa.setText(data.getDesa());
        telepon.setText(data.getTelepon());
        pekerjaan.setText(data.getPekerjaan());
    }
}
