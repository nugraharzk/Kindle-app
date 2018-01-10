package edu.upi.mobprogproject.content;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.EditProfileActivity;
import edu.upi.mobprogproject.activity.HomeActivity;
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
    private TextView tvUser;
//    private Users users;
private Activity activity;

    DbUsers dbU;
    View v;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView img = v.findViewById(R.id.imageView2);
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.cover)
                .into(img);
        ImageView img2 = v.findViewById(R.id.circle);
        Glide.with(this)
                .asBitmap()
                .apply(RequestOptions.circleCropTransform())
                .load(R.drawable.profile_user)
                .into(img2);
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
        //Glide gl = new Glide(activity).load(R.drawable.cover);
        //Glide.with(mContext).load(imgID).asBitmap().override(1080, 600).into(mImageView);
        tvUser = v.findViewById(R.id.tvUsername);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setProfile();
    }

    private void setProfile() {

        TextView nama, kerja_umur, alamat, telepon, username;
        ImageView circle;
        circle = v.findViewById(R.id.circle);
//        circle.setImageBitmap();
        circle.setMaxWidth(300);
        circle.setMaxHeight(300);
        nama = v.findViewById(R.id.tvNama);
        kerja_umur = v.findViewById(R.id.tvKerUmur);
        alamat = v.findViewById(R.id.tvAlamat);
        telepon = v.findViewById(R.id.tvPhone);
        username = v.findViewById(R.id.tvUsername);
        sp = this.activity.getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        dbU = new DbUsers(activity);
        dbU.open();
        Users user = dbU.getUser(sp.getString("user", ""));
        Glide.with(activity).asBitmap().apply(RequestOptions.circleCropTransform()).load(user.getProfile_image()).into(circle);
        Log.d("userProf", "setProfile: " + user.getProfile_image());
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
        Intent intent = new Intent(activity, LoginActivity.class);
        ed = sp.edit();
        ed.clear();
        ed.apply();
        activity.finish();
        startActivity(intent);
    }

    public void editProfile(View v) {
        Intent intent = new Intent(activity, EditProfileActivity.class);
        intent.putExtra("username", tvUser.getText().toString());
        startActivityForResult(intent, ACT2_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACT2_REQUEST && resultCode != 0) {
            Toast.makeText(activity, "Profile Berhasil Di Perbaharui", Toast.LENGTH_LONG).show();
        } else {
            if (resultCode == 0)
                Toast.makeText(activity, "Profile tidak disimpan", Toast.LENGTH_LONG).show();
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

    /*public Bitmap getBitmapFromURL(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
        }
    }*/
}
