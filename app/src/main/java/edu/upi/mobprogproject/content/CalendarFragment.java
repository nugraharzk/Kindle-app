package edu.upi.mobprogproject.content;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.HashMap;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.AddEventActivity;
import edu.upi.mobprogproject.activity.HomeActivity;
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

    public static final int ACT2_REQUEST = 100;
    DbEvents dbE;
    DbUsers dbU;
    RecyclerView recyclerView;
    AgendaAdapter adapter;
    static ArrayList<AgendaList> agenda;
    private View v;
    public CalendarFragment() {
        // Required empty public constructor
    }

    private Activity activity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setRecView();
        // Inflate the layout for this fragment
    }

    private void setRecView() {
        dbE = new DbEvents(activity);
        dbE.open();
        dbU = new DbUsers(activity);
        dbU.open();

        agenda = setData(dbE, dbU);

        ImageView addAgenda = v.findViewById(R.id.bell_event);
        addAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AddEventActivity.class);
                startActivityForResult(i, ACT2_REQUEST);
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
        recyclerView = v.findViewById(R.id.rcEvent);
        adapter = new AgendaAdapter(activity, agenda);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    public ArrayList<AgendaList> setData(DbEvents dbe, DbUsers dbu) {
        ArrayList<AgendaList> ag = new ArrayList<>();
        ArrayList<Events> event = dbe.getAllEvents();
        for (Events item : event) {
            Users user = dbu.getUser(item.getUsername());
            AgendaList one = new AgendaList();
            //judul desk, waktu, urgensi
            one.setNamaAcara(item.getJudul());
            one.setPenyelenggara(user.getNama());
            one.setUrgensi(item.getPriority());
            String times = item.getWaktu();
            Log.i("dl", "test" + times);
            if (times != null) {
                //dot need to be escaped
                String[] times_pecah = times.split("\\.");
                if (times_pecah.length > 0) {
                    one.setWaktu(times_pecah[0]);
                    String tgl = times_pecah[1];
                    if (tgl != null) {
                        String[] tgl_pecah = tgl.split("/");
                        if (tgl_pecah.length > 0) {
                            one.setTgl(tgl_pecah[0]);
                            one.setBulan(getDate(tgl_pecah[1]));
                        }
                    }
                }
                //one.setTgl(tgl_pecah[0]);
            }
            one.setTempat(item.getDeskripsi());

            ag.add(one);
            //st.add(new StatusList(user.getNama(), item.getWaktu(), item.getStatus()));
        }
        return ag;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbE.close();
        dbU.close();
    }

    private String getDate(String month) {
        HashMap<Integer, String> hasMaps = new HashMap<Integer, String>() {{
            put(1, "Jan");
            put(2, "Feb");
            put(3, "Mar");
            put(4, "Apr");
            put(5, "Mei");
            put(6, "Jun");
            put(7, "Jul");
            put(8, "Ags");
            put(9, "Sep");
            put(10, "Okt");
            put(11, "Nov");
            put(12, "Des");
        }};

        return hasMaps.get(Integer.parseInt(month));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACT2_REQUEST) {
            setRecView();
            //adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            //karena dibalik indexnya
            recyclerView.getLayoutManager().scrollToPosition(agenda.size() - 1);
        }
    }

    //public static ArrayList<AgendaList> getAgenda() {
    //    return agenda;
    //}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (activity == null && context instanceof HomeActivity) {
            activity = (HomeActivity) context;
        }
    }

    @Override
    public void onDetach() {
        this.activity = null;
        super.onDetach();
    }
}
