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
import edu.upi.mobprogproject.adapter.data.AgendaList;

/**
 * Created by amaceh on 14/11/17.
 * An adapter class to create recycler view
 */

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {
    private List<AgendaList> mData = Collections.emptyList();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public AgendaAdapter(Context context, List<AgendaList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public AgendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_item, parent, false);
        AgendaAdapter.ViewHolder viewHolder = new AgendaAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(AgendaAdapter.ViewHolder holder, int position) {
        AgendaList name = mData.get(position);
        /*
        private int urgensi;
        private String tgl, bulan, namaAcara, tempat, waktu, penyelenggara;
        */
        holder.urgensi.setText(name.getUrgensi());
        holder.tgl.setText(name.getTgl());
        holder.bulan.setText(name.getBulan());
        holder.namaAcara.setText(name.getNamaAcara());
        holder.tempat.setText(name.getTempat());
        holder.waktu.setText(name.getWaktu());
        holder.penyelenggara.setText(name.getPenyelenggara());
        /*
        holder.nama.setText(name.getNama());
        holder.waktu.setText(name.getWaktu());
        holder.status.setText(name.getStatus());
        */
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
        private int urgensi;
        private String tgl, bulan, namaAcara, tempat, waktu, penyelenggara;
        */
        public TextView urgensi, tgl, bulan, namaAcara, tempat, waktu, penyelenggara;

        public ViewHolder(View itemView) {
            super(itemView);
            urgensi = itemView.findViewById(R.id.tvUrgent);
            tgl = itemView.findViewById(R.id.tvTgl);
            bulan = itemView.findViewById(R.id.tvBln);
            namaAcara = itemView.findViewById(R.id.tvEvent);
            tempat = itemView.findViewById(R.id.tvTempat);
            waktu = itemView.findViewById(R.id.etWaktu);
            penyelenggara = itemView.findViewById(R.id.tvPenyelenggara);
        }
    }

    // convenience method for getting data at click position
    public AgendaList getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<AgendaList> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
