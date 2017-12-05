package edu.upi.mobprogproject.content;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.EditProfileActivity;
import edu.upi.mobprogproject.activity.LoginActivity;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

import static android.content.Context.MODE_PRIVATE;
import static java.util.Calendar.YEAR;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public SharedPreferences sp;
    public SharedPreferences.Editor ed;
    public static final int ACT2_REQUEST = 101;
//    private Users users;

    DbUsers dbU;
    View v;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        Button BtLogout = v.findViewById(R.id.btLogout);
        BtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(v);
            }
        });
        Button BtEProp = v.findViewById(R.id.btEditProp);
        BtEProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile(v);
            }
        });

        TextView tvNama = v.findViewById(R.id.tvNama);
        TextView tvTtl = v.findViewById(R.id.tvKerUmur);
        TextView tvAlamat = v.findViewById(R.id.tvAlamat);
        TextView tvTelepon = v.findViewById(R.id.tvPhone);
        TextView tvUser = v.findViewById(R.id.tvUsername);
//        tvNama.setText(users.getNama());
//        tvTtl.setText(users.getTtl());
//        tvAlamat.setText(users.getAlamat());
//        tvTelepon.setText(users.getTelepon());
//        tvUser.setText(getString(R.string.user_fill, users.getUsername()));
        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setProfile();
    }

    private void setProfile() {

        TextView nama, kerja_umur, alamat, telepon, username;
        nama = v.findViewById(R.id.tvNama);
        kerja_umur = v.findViewById(R.id.tvKerUmur);
        alamat = v.findViewById(R.id.tvAlamat);
        telepon = v.findViewById(R.id.tvPhone);
        username = v.findViewById(R.id.tvUsername);
        sp = this.getActivity().getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        dbU = new DbUsers(getActivity());
        dbU.open();
        Users user = dbU.getUser(sp.getString("user", ""));
        if (user != null) {
            nama.setText(user.getNama());
            if (user.getTtl() != null && user.getAlamat() != null
                    && user.getTelepon() != null && user.getPekerjaan() != null) {
                String fill = getString(R.string.name_fill, user.getPekerjaan(), yearGenerator(user.getTtl()));
                kerja_umur.setText(fill);
                alamat.setText(user.getAlamat());
                telepon.setText(user.getTelepon());
            }
            username.setText(getString(R.string.user_fill, user.getUsername()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfile();
    }

    public void logout(View v) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        ed = sp.edit();
        ed.clear();
        ed.apply();
        getActivity().finish();
        startActivity(intent);
    }

    public void editProfile(View v) {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivityForResult(intent, ACT2_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACT2_REQUEST && resultCode != 0) {
            Toast.makeText(getActivity(), "Profile Berhasil Di Perbaharui", Toast.LENGTH_LONG).show();
        } else {
            if (resultCode == 0)
            Toast.makeText(getActivity(), "Profile tidak disimpan", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        dbU.close();
    }

    public String yearGenerator(String ttl) {
        if (ttl != null) {
            String[] getTgl = ttl.split("_");
            if (getTgl.length > 0) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
