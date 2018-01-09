package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.media.Image;
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
import edu.upi.mobprogproject.adapter.data.ChatHeader;

/**
 * Created by amaceh on 12/11/17.
 * A recyclerView Adapter for ChatHeader
 */

public class ChatHeaderAdapter extends RecyclerView.Adapter<ChatHeaderAdapter.ViewHolder> {
    private List<ChatHeader> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context ctx;
    // data is passed into the constructor
    public ChatHeaderAdapter(Context context, List<ChatHeader> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.ctx = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitems_message_header, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatHeader name = mData.get(position);
        holder.nama.setText(name.getNama());
        holder.chat.setText(name.getChat());
        holder.jam.setText(name.getJam());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, chat, jam;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imPerson);
            nama = itemView.findViewById(R.id.tvNama);
            chat = itemView.findViewById(R.id.tvChat);
            jam = itemView.findViewById(R.id.tvHour);

            Glide.with(ctx)
                    .asBitmap()
                    .apply(RequestOptions.circleCropTransform())
                    .load(R.drawable.profile_user)
                    .into(img);
        }
    }

    // convenience method for getting data at click position
    public ChatHeader getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<ChatHeader> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
