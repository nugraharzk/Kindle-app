package edu.upi.mobprogproject.content;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.HomeActivity;
import edu.upi.mobprogproject.adapter.ChatHeaderAdapter;
import edu.upi.mobprogproject.adapter.data.ChatHeader;

//import android.support.v7.widget.SearchView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private Activity activity;
    ImageView menu;
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ChatHeaderAdapter adapter;
        final ArrayList<ChatHeader> chats = new ArrayList<>();

        chats.add(new ChatHeader("Achmad", "Hey Bro", "07.00"));
        chats.add(new ChatHeader("Abdul", "I Am GuntanK", "12.00"));
        chats.add(new ChatHeader("Rofiq", "You Shall not Pass", "23.00"));
        chats.add(new ChatHeader("Naufal", "How Are ya?", "08.00"));
        chats.add(new ChatHeader("Fazanadi", "all fair in love", "07.00"));
        chats.add(new ChatHeader("Rizki", "All fair in war", "07.00"));
        chats.add(new ChatHeader("Nugraha", "Dasar Kids Now", "07.00"));
        chats.add(new ChatHeader("Bisma", "Hello dude", "07.00"));
        chats.add(new ChatHeader("Wahyu", "Not Good", "07.00"));
        chats.add(new ChatHeader("Ryan", "Halah Ken", "07.00"));
        chats.add(new ChatHeader("Dwika", "YO sup bro", "07.00"));
        chats.add(new ChatHeader("Putra", "hell yeah", "07.00"));

        View v = inflater.inflate(R.layout.fragment_message, container, false);


        // set up the RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.rcMess);
        SearchView sc = v.findViewById(R.id.searchView);

        // TODO to create menu, use this implementation instead of making toolbar as ActionBar
        menu = v.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                activity.openOptionsMenu();
                PopupMenu popup = new PopupMenu(activity, menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_chat, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                activity,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        adapter = new ChatHeaderAdapter(activity, chats);

        sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                callSearch(newText);
//              }
                return true;
            }

            void callSearch(String query) {
                //Do searching
                filter(query, chats, adapter);
            }

        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        // Inflate the layout for this fragment
        return v;
    }

    private void filter(String text, ArrayList<ChatHeader> chats, ChatHeaderAdapter adapter) {
        //new array list that will hold the filtered data
        ArrayList<ChatHeader> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ChatHeader s : chats) {
            //if the existing elements contains the search input
            if (s.getNama().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (activity == null && context instanceof HomeActivity) {
            activity = (HomeActivity) context;
        }
    }

    @Override
    public void onDetach() {
        this.activity = null;
        super.onDetach();
    }
}
