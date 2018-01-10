package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.model.Notifications;

/**
 * Created by Rizki on 1/10/2018.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.MyViewHolder> {

    private List<Notifications> notificationsList;
    private LayoutInflater mInflater;
    private Context context;

    public NotifAdapter(Context context, List<Notifications> data) {
        this.mInflater = LayoutInflater.from(context);
        this.notificationsList = data;
        this.context = context;
    }

    @Override
    public NotifAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_notif, parent, false);
        NotifAdapter.MyViewHolder viewHolder = new NotifAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Notifications notifications = notificationsList.get(position);

        holder.urgen.setText(notifications.getUrgensi());
        holder.pesan.setText(notifications.getPesan());
        holder.usr.setText(notifications.getUsername());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, notifications.getUsername() + ": " + notifications.getPesan(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView urgen, pesan, usr;
        public ConstraintLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutNotif);
            urgen = itemView.findViewById(R.id.urgensiNotif);
            pesan = itemView.findViewById(R.id.pesanNotif);
            usr = itemView.findViewById(R.id.userNotif);
        }
    }
}
