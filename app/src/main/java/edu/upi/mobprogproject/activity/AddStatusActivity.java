package edu.upi.mobprogproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Status;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStatusActivity extends AppCompatActivity {

    private static final String TAG = AddStatusActivity.class.getSimpleName();


    DbStatus dbS;
    DbUsers dbU;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);

        dbS = new DbStatus(this);
        dbU = new DbUsers(this);
        dbS.open();
        dbU.open();

    }

    public void saveStatus(View v) {
        Status S = new Status();
        S.setWaktu(Calendar.getInstance().getTime().toString());
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        S.setUsername(sp.getString("user", ""));
        S.setLike(0);
        EditText stat = findViewById(R.id.etStatus);
        String status = stat.getText().toString().trim();
        if (TextUtils.isEmpty(status)) {
            Toast.makeText(this, "Please enter username/email", Toast.LENGTH_LONG).show();
            return;
        }

        S.setStatus(status);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Status> call = apiInterface.postStatus(sp.getString("user", ""), S.getStatus(), S.getWaktu(), String.valueOf(S.getLike()));
        long a = dbS.insertStatus(S);

        if (a != -1) {
            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });
            Toast.makeText(this, "Status Berhasil Diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mohon maaf terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }
        Intent intent2 = getIntent();
        setResult(RESULT_OK, intent2);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbS.close();
        dbU.close();
    }
}
