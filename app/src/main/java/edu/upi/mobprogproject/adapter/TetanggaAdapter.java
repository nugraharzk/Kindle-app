package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.data.TetanggaList;

/**
 * Created by amaceh on 16/12/17.
 */

public class TetanggaAdapter extends RecyclerView.Adapter<TetanggaAdapter.ViewHolder> {
    private List<TetanggaList> mData = Collections.emptyList();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public TetanggaAdapter(Context context, List<TetanggaList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public TetanggaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitems_tetangga, parent, false);
        TetanggaAdapter.ViewHolder viewHolder = new TetanggaAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(TetanggaAdapter.ViewHolder holder, int position) {
        TetanggaList name = mData.get(position);
        Log.i("stop", "onBindViewHolder: ");
        holder.nama.setText(name.getNama());
        holder.alamat.setText(name.getAlamat());
        holder.kontak.setText(name.getKontak());
        holder.jabatan.setText(name.getJabatan());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, alamat, kontak, jabatan;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvLTNama);
            alamat = itemView.findViewById(R.id.tvLTalamat);
            kontak = itemView.findViewById(R.id.tvLTkontak);
            jabatan = itemView.findViewById(R.id.tvLeader);
        }
    }

    // convenience method for getting data at click position
    public TetanggaList getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<TetanggaList> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
