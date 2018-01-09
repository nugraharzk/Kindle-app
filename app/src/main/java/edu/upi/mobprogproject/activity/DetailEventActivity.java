package edu.upi.mobprogproject.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbEvents;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.model.Users;

public class DetailEventActivity extends AppCompatActivity {

    private DbEvents dbE;
    private DbUsers dbU;
    final String EXTRA_MESSAGE = "edu.upi.mobproject.event.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        dbE = new DbEvents(this);
        dbE.open();
        dbU = new DbUsers(this);
        dbU.open();

        Events e = null;
        Intent i = getIntent();
        int x = i.getIntExtra(EXTRA_MESSAGE, -1);
        if (x!=-1){
            e=dbE.getEvent(x);
        }else{
            Toast.makeText(this, "Ada Yang Salah", Toast.LENGTH_SHORT).show();
            finish();
        }
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView judul, detail, waktu, penyelenggara, prioritas, lokasi;
        judul = findViewById(R.id.tvEJudul);
        detail = findViewById(R.id.tvEDetail);
        waktu = findViewById(R.id.tvEWaktu);
        penyelenggara = findViewById(R.id.tvEPenyelenggara);
        prioritas = findViewById(R.id.tvEPriotitas);
        lokasi = findViewById(R.id.tvELokasi);

        if (e!=null){
            judul.setText(e.getJudul());
            detail.setText(e.getDeskripsi());
            waktu.setText(e.getWaktu());
            Users u = dbU.getUser(e.getUsername());
            penyelenggara.setText(u.getNama());
            prioritas.setText(e.getPriority());
            if (e.getLat()!=null && e.getLng()!=null){
                double lat = Double.parseDouble(e.getLat());
                double lon = Double.parseDouble(e.getLng());

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    lokasi.setText(address);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }

        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbE.close();
        dbU.close();
    }
}
