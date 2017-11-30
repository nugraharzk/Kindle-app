package edu.upi.mobprogproject.content;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.LoginActivity;
import edu.upi.mobprogproject.helper.DbAccounts;
import edu.upi.mobprogproject.helper.DbUsers;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public SharedPreferences sp;
    public SharedPreferences.Editor ed;

    DbAccounts dbA;
    DbUsers dbU;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        dbU = new DbUsers(getActivity());
//        dbU.open();
//        dbA = new DbAccounts(getActivity());
//        dbA.open();

        final View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Button BtLogout = v.findViewById(R.id.btLogout);
        BtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(v);
            }
        });

//        TextView tvNama = (TextView) v.findViewById(R.id.tvNama).toString();
//        tvNama.setText(dbA.getAccount());

        //Button
        // Inflate the layout for this fragment
        return v;
    }

    public void logout(View v) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        sp = this.getActivity().getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        ed = sp.edit();
        ed.clear();
        ed.apply();
        getActivity().finish();
        startActivity(intent);
    }

}
