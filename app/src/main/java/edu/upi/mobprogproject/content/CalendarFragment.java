package edu.upi.mobprogproject.content;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.addEventActivity;
import edu.upi.mobprogproject.adapter.AgendaAdapter;
import edu.upi.mobprogproject.adapter.data.AgendaList;
import edu.upi.mobprogproject.helper.DbEvents;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.model.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    DbEvents dbE;
    DbUsers dbU;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        dbE = new DbEvents(getActivity());
        dbE.open();
        dbU = new DbUsers(getActivity());
        dbU.open();

        final AgendaAdapter adapter;
        final ArrayList<AgendaList> agenda = setData(dbE, dbU);

        ImageView addAgenda = v.findViewById(R.id.bell_event);
        addAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), addEventActivity.class);
                startActivity(i);
            }
        });
        /*
        agenda.add(new AgendaList("penting", "12", "Jan", "Free Android",
                "Sabuga", "12.00-13.00", "Pak RT"));
        agenda.add(new AgendaList("terdekat", "13", "Feb", "Kopdar Moprog",
                "Monju", "01.00-03.00", "Pak RW"));
        agenda.add(new AgendaList("terdekat", "14", "Mar", "Hackaton 7M",
                "Dago", "23.12-1.23", "Kades"));
        agenda.add(new AgendaList("terdekat", "15", "Apr", "Lomba ML",
                "Cibiru", "10.00-13.00", "Pemkot"));
        agenda.add(new AgendaList("terdekat", "16", "Mei", "Lomba Balap Kerupuk",
                "UPI", "09.00-10.00", "Kemakom"));
        agenda.add(new AgendaList("terdekat", "17", "Jun", "Lomba Koding",
                "GEEK", "11.00-12.00", "UPI"));
        */
        RecyclerView recyclerView = v.findViewById(R.id.rcEvent);
        adapter = new AgendaAdapter(getActivity(), agenda);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Inflate the layout for this fragment
        return v;
    }

    public ArrayList<AgendaList> setData(DbEvents dbe, DbUsers dbu) {
        ArrayList<AgendaList> ag = new ArrayList<>();
        ArrayList<Events> event = dbe.getAllEvents();
        int i = 0;
        for (Events item : event) {
            Users user = dbu.getUser(item.getUsername());
            AgendaList one = new AgendaList();
            //judul desk, waktu, urgensi
            one.setNamaAcara(item.getJudul());
            one.setPenyelenggara(user.getNama());
            one.setUrgensi(item.getPriority());
            String tgl = item.getWaktu();
            Log.i("dl", "test" + tgl);
            if (tgl != null) {
                String[] tgl_pecah = tgl.split(" ");
                one.setTgl(tgl_pecah[0]);
                one.setBulan(tgl_pecah[1]);
            }
            one.setTempat(item.getDeskripsi());

            ag.add(one);
            //st.add(new StatusList(user.getNama(), item.getWaktu(), item.getStatus()));
            i += 1;
        }
        return ag;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbE.close();
        dbU.close();
    }
}
