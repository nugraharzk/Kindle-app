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

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.EditProfileActivity;
import edu.upi.mobprogproject.activity.LoginActivity;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public SharedPreferences sp;
    public SharedPreferences.Editor ed;
    public static final int ACT2_REQUEST = 101;

    DbUsers dbU;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_profile, container, false);

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

        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
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
            if (user.getJabatan() != null && user.getTtl() != null
                    && user.getAlamat() != null && user.getTelepon() != null) {
                String fill = getString(R.string.name_fill, user.getNama(), yearGenerator(user.getTtl()));
                kerja_umur.setText(fill);
                alamat.setText(user.getAlamat());
                telepon.setText(user.getTelepon());
            }
            username.setText(getString(R.string.user_fill, user.getUsername()));
        }
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
        Toast.makeText(getActivity(), "Profile Berhasil Di Perbaharui", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbU.close();
    }

    public String yearGenerator(String ttl) {
        return "20";
    }
}
