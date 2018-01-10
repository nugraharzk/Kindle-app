package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.data.StatusList;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Status;

/**
 * Created by amaceh on 14/11/17.
 * An adapter for Status
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private List<Status> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context context;
    private DbUsers dbU;
    private DbStatus dbS;

    // data is passed into the constructor
    public StatusAdapter(Context context, List<Status> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;

        dbU = new DbUsers(context);
        dbS = new DbStatus(context);
        dbU.open();
        dbS.open();
    }

    // inflates the row layout from xml when needed
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitems_status, parent, false);
        StatusAdapter.ViewHolder viewHolder = new StatusAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(StatusAdapter.ViewHolder holder, int position) {
        Status name = mData.get(position);
        String nama;

        nama = dbU.getUser(name.getUsername()).getNama();
        holder.nama.setText(nama);
        holder.waktu.setText(name.getWaktu());
        holder.status.setText(name.getStatus());
        Glide.with(context).asBitmap().apply(RequestOptions.circleCropTransform()).load(dbU.getUser(name.getUsername()).getProfile_image()).into(holder.foto);
        holder.cl.setFocusable(true);
        holder.cl.setClickable(true);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, waktu, status;
        public ImageView foto;
        public ConstraintLayout cl;

        public ViewHolder(View itemView) {
            super(itemView);
            cl = itemView.findViewById(R.id.clStatus);
            foto = itemView.findViewById(R.id.imPerson);
            foto.setMaxHeight(150);
            foto.setMaxWidth(150);
            nama = itemView.findViewById(R.id.tvNama);
            waktu = itemView.findViewById(R.id.tvTime);
            status = itemView.findViewById(R.id.tvStatus);
        }
    }

    // convenience method for getting data at click position
    public Status getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<Status> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
