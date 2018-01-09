package edu.upi.mobprogproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.NotifAdapter;
import edu.upi.mobprogproject.helper.DbNotif;
import edu.upi.mobprogproject.model.Notifications;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifActivity extends AppCompatActivity {

    private String TAG = "Hasil";
    private DbNotif dbN;
    private List<Notifications> notificationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        dbN = new DbNotif(this);
        dbN.open();
        notificationsList = dbN.getAllNotif();

        ImageView back = findViewById(R.id.backNotif);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView rvNotif = findViewById(R.id.rvNotif);
        NotifAdapter notifAdapter = new NotifAdapter(getBaseContext(), notificationsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvNotif.setLayoutManager(layoutManager);
        rvNotif.setAdapter(notifAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNotif.getContext(),
                layoutManager.getOrientation());
        rvNotif.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
