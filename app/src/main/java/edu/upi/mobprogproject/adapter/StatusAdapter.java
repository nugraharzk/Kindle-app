package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.data.StatusList;

/**
 * Created by amaceh on 14/11/17.
 * An adapter for Status
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private List<StatusList> mData = Collections.emptyList();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public StatusAdapter(Context context, List<StatusList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.status_item, parent, false);
        StatusAdapter.ViewHolder viewHolder = new StatusAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(StatusAdapter.ViewHolder holder, int position) {
        StatusList name = mData.get(position);

        holder.nama.setText(name.getNama());
        holder.waktu.setText(name.getWaktu());
        holder.status.setText(name.getStatus());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, waktu, status;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            waktu = itemView.findViewById(R.id.tvTime);
            status = itemView.findViewById(R.id.tvStatus);
        }
    }

    // convenience method for getting data at click position
    public StatusList getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<StatusList> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
