package edu.upi.mobprogproject.content;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.addStatusActivity;
import edu.upi.mobprogproject.adapter.StatusAdapter;
import edu.upi.mobprogproject.adapter.data.StatusList;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Status;
import edu.upi.mobprogproject.model.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {

    DbStatus dbS;
    DbUsers dbU;
    RecyclerView recyclerView;
    StatusAdapter adapter;
    static ArrayList<StatusList> status;
    Context context;

    public static final int ACT2_REQUEST = 99;

    public FeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feeds, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACT2_REQUEST) {
            //adapter.notifyDataSetChanged();
            recyclerView.invalidate();
        }
    }

    public ArrayList<StatusList> setData(DbStatus dbs, DbUsers dbu) {
        ArrayList<StatusList> st = new ArrayList<>();
        ArrayList<Status> status = dbs.getAllStatus();
        int i = 0;
        for (Status item : status) {
            Users user = dbu.getUser(item.getUsername());
            st.add(new StatusList(user.getNama(), item.getWaktu(), item.getStatus()));
            i += 1;
        }
        return st;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        dbU = new DbUsers(getActivity());
        dbS = new DbStatus(getActivity());

        dbS.open();
        dbU.open();
        // Inflate the layout for this fragment

        recyclerView = v.findViewById(R.id.rcStatus);
        status = setData(dbS, dbU);

        /*
        status.add(new StatusList("Achmad", "satu jam lalu", "Akankah Yang Hilang Kembali?"));
        status.add(new StatusList("Naufal", "satu hari lalu", "Biarkan Cinta berjalan semestinya?"));
        status.add(new StatusList("Bisma", "11 november 2017", "Adakah Dia Mengerti Diriku"));
        status.add(new StatusList("Rizki", "10 november 2017", "Aku sudah lelah mananti dirimu"));
        status.add(new StatusList("Ryan", "9 november 2017", "Biarlah yang lalu biarlah berlalu"));
        */
        adapter = new StatusAdapter(getActivity(), status);

        ImageView addStat = v.findViewById(R.id.bell_status);

        addStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), addStatusActivity.class);
                startActivityForResult(i, ACT2_REQUEST);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static ArrayList<StatusList> getStatus() {
        return status;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbU.close();
        dbS.close();
    }
}
