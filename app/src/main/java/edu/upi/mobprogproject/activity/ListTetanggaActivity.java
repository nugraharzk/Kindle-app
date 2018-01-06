package edu.upi.mobprogproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.TetanggaAdapter;
import edu.upi.mobprogproject.adapter.data.TetanggaList;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

public class ListTetanggaActivity extends AppCompatActivity {

    private ArrayList<TetanggaList> tetangga;
    RecyclerView recyclerView;
    TetanggaAdapter adapter;
    DbUsers dbU;

    ImageView menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tetangga);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbU = new DbUsers(this);
        dbU.open();
        tetangga = setData(dbU);
        recyclerView = findViewById(R.id.rcTetangga);
        adapter = new TetanggaAdapter(this, tetangga);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                activity.openOptionsMenu();
                PopupMenu popup = new PopupMenu(ListTetanggaActivity.this, menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_list_tetangga, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                ListTetanggaActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }

    private ArrayList<TetanggaList> setData(DbUsers dbus) {
        ArrayList<TetanggaList> data = new ArrayList<>();
        ArrayList<Users> users = dbus.getAllUsers();
        for (Users item : users) {
            TetanggaList g = new TetanggaList();
            g.setUsername(item.getUsername());
            g.setNama(item.getNama());
            g.setAlamat(item.getAlamat());
            g.setKontak(item.getTelepon());
            g.setJabatan(item.getJabatan());
            data.add(g);
        }
        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbU.close();
    }
}
