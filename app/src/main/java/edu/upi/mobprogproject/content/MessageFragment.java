package edu.upi.mobprogproject.content;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.ChatHeaderAdapter;
import edu.upi.mobprogproject.adapter.data.ChatHeader;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        adapter = new ChatHeaderAdapter(getActivity(), chats);

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

            public void callSearch(String query) {
                //Do searching
                filter(query, chats, adapter);
            }

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
}
