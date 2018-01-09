package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.DetailEventActivity;
import edu.upi.mobprogproject.adapter.data.EventsList;

/**
 * Created by amaceh on 14/11/17.
 * An adapter class to create recycler view
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<EventsList> mData = Collections.emptyList();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public EventAdapter(Context context, List<EventsList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitems_event, parent, false);
        EventAdapter.ViewHolder viewHolder = new EventAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(final EventAdapter.ViewHolder holder, final int position) {
        final EventsList name = mData.get(position);
        /*
        private int urgensi;
        private String tgl, bulan, namaAcara, tempat, waktu, penyelenggara;
        */
        /*holder.acara.setClickable(true);*/
        holder.acara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mInflater.getContext(), "List ke - "+holder.getAdapterPosition()+".", Toast.LENGTH_SHORT).show();
                final String EXTRA_MESSAGE = "edu.upi.mobproject.event.MESSAGE";
                Intent i = new Intent(mInflater.getContext(), DetailEventActivity.class);
//                Intent i = new Intent(ctx, DetailTetanggaActivity.class);
                i.putExtra(EXTRA_MESSAGE, name.getId_event());
                mInflater.getContext().startActivity(i);
//                ctx.startActivity(i);
            }
        });
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
        public ConstraintLayout acara;

        public ViewHolder(View itemView) {
            super(itemView);
            acara = itemView.findViewById(R.id.eventContent);
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
    public EventsList getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<EventsList> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
