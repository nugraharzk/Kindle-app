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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.HomeActivity;
import edu.upi.mobprogproject.adapter.StatusAdapter;
import edu.upi.mobprogproject.adapter.data.StatusList;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Status;
import edu.upi.mobprogproject.model.Users;
import edu.upi.mobprogproject.popup.StatusPopUp;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {

    private Activity activity;
    DbStatus dbS;
    DbUsers dbU;
    RecyclerView recyclerView;
    StatusAdapter adapter;
    static ArrayList<StatusList> status;
    View v;
    StatusPopUp mStatusPopUp;
    RelativeLayout xView;
    ImageView menu;


    public static final int ACT2_REQUEST = 99;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_status, container, false);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACT2_REQUEST) {
            setRecView();
            //adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            //karena dibalik indexnya
            recyclerView.getLayoutManager().scrollToPosition(status.size() - 1);
        }
    }

    public ArrayList<StatusList> setData(DbStatus dbs, DbUsers dbu) {
        ArrayList<StatusList> st = new ArrayList<>();
        ArrayList<Status> status = dbs.getAllStatus();
        for (Status item : status) {
            Users user = dbu.getUser(item.getUsername());
            st.add(new StatusList(user.getNama(), item.getWaktu(), item.getStatus()));
        }
        return st;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        xView = v.findViewById(R.id.viewFeeds);
        menu = v.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                activity.openOptionsMenu();
                PopupMenu popup = new PopupMenu(activity, menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_feed, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                activity,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
        ImageView addStat = v.findViewById(R.id.bell_status);
        mStatusPopUp = new StatusPopUp(activity, xView);
        mStatusPopUp.getPopUP().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setRecView();
                //adapter.notifyDataSetChanged();
                recyclerView.invalidate();
                //karena dibalik indexnya
                recyclerView.getLayoutManager().scrollToPosition(status.size() - 1);
            }
        });
        addStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatusPopUp.show();
            }
        });
        setRecView();
    }

    private void setRecView() {
        dbU = new DbUsers(activity);
        dbS = new DbStatus(activity);

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
        adapter = new StatusAdapter(activity, status);



        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    //public static ArrayList<StatusList> getStatus() {
    //  return status;
    //}

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbU.close();
        dbS.close();
    }

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
