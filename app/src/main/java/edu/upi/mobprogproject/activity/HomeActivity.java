package edu.upi.mobprogproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.content.EventsFragment;
import edu.upi.mobprogproject.content.StatusFragment;
import edu.upi.mobprogproject.content.HomeFragment;
import edu.upi.mobprogproject.content.MessageFragment;
import edu.upi.mobprogproject.content.ProfileFragment;
import edu.upi.mobprogproject.helper.DbEvents;
import edu.upi.mobprogproject.helper.DbNotif;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Events;
import edu.upi.mobprogproject.model.Notifications;
import edu.upi.mobprogproject.model.Status;
import edu.upi.mobprogproject.model.Users;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    // Initialize the List<{Class}> item
    // Initialize the Db Class
    List<Users> userlist;
    DbUsers dbU;

    List<Status> statuslist;
    DbStatus dbS;

    List<Events> eventlist;
    DbEvents dbE;

    List<Notifications> notificationsList;
    DbNotif dbN;
    Context c = this;

    // TAG for logging
    private static final String TAG = HomeActivity.class.getSimpleName();

    // Bottom Navigation Listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    HomeFragment homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, homeFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.action_calendar:
                    EventsFragment eventsFragment = new EventsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, eventsFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.action_feeds:
                    StatusFragment statusFragment = new StatusFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, statusFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.action_messages:
                    MessageFragment messageFragment = new MessageFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, messageFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.action_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, profileFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Instantiate the Db
        dbU = new DbUsers(this);
        dbE = new DbEvents(this);
        dbS = new DbStatus(this);
        dbN = new DbNotif(this);

        // Open db
        dbU.open();
        dbE.open();
        dbS.open();
        dbN.open();

        // Get data from backend and parse to db
        getData();

        // Initialize the fragment
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, homeFragment).addToBackStack(null).commit();

        // Bottom navigation bounding
        BottomNavigationViewEx navigation = findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Get intent data from intent before
        Bundle extras = getIntent().getExtras();
        int position=0;
        if(extras != null) {
            position = extras.getInt("viewpager_position");
        }

        // Fragment selection
        if(position==0){
            HomeFragment homeFragment1 = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, homeFragment1)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(0).setChecked(true);
        }else if(position==1){
            EventsFragment eventsFragment = new EventsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, eventsFragment)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(1).setChecked(true);
        }else if(position==2){
            StatusFragment statusFragment = new StatusFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, statusFragment)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(2).setChecked(true);
        }else if(position==3){
            MessageFragment messageFragment = new MessageFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, messageFragment)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(3).setChecked(true);
        }else if(position==4){
            ProfileFragment profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, profileFragment)
                    .addToBackStack(null)
                    .commit();
            navigation.getMenu().getItem(4).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Get data void for GET Method
    private void getData() {
        // for user
        userGrab();
        // for notification
        notifGrab();
    }

    private void userGrab(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progress_user, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Users>> call = apiService.getUsersList();
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NonNull Call<List<Users>> call, @NonNull Response<List<Users>> response) {
                dialog.dismiss();
                userlist = response.body();
                if (userlist != null) {
                    dbU.update(userlist);
                    statusGrab();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Users>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(c, "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void statusGrab(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progress_status, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Status>> call2 = apiService.getStatusList();
        call2.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(@NonNull Call<List<Status>> call, @NonNull Response<List<Status>> response) {
                dialog.dismiss();
                statuslist = response.body();
                if (statuslist != null) {
                    //dbU.update(userlist);
                    dbS.update(statuslist);
                    eventGrab();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Status>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(c, "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void eventGrab(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progress_events, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Events>> call3 = apiService.getEventList();
        call3.enqueue(new Callback<List<Events>>() {
            @Override
            public void onResponse(@NonNull Call<List<Events>> call, @NonNull Response<List<Events>> response) {
                dialog.dismiss();
                eventlist = response.body();
                if (eventlist != null) {
                    //dbU.update(userlist);
                    dbE.update(eventlist);
                }
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Actions to do after 10 seconds
//                    }
//                }, 10000);
            }

            @Override
            public void onFailure(@NonNull Call<List<Events>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(c, "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void notifGrab(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progress_notif, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();
        dialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Notifications>> call4 = apiService.getNotifList();
        call4.enqueue(new Callback<List<Notifications>>() {
            @Override
            public void onResponse(@NonNull Call<List<Notifications>> call, @NonNull Response<List<Notifications>> response) {
                dialog.dismiss();
                notificationsList = response.body();
                if (notificationsList != null) {
                    dbN.update(notificationsList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Notifications>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(c, "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbU.close();
        dbE.close();
        dbS.close();
        dbN.close();
    }

    /* KODINGAN IKI HEEERRREE */
//    BottomNavigationView bottomNavigationView;
//
//    private Toolbar toolbar;
//    CustomViewPager viewPager;
//
//    public String nama, ttl, alamat, telepon;
//
//    HomeFragment homeFragment;
//    EventsFragment chatFragment;
//    StatusFragment feedsFragment;
//    MessageFragment messageFragment;
//    ProfileFragment profileFragment;
//    Bundle bundle;
//
//    MenuItem prevMenuItem;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        bundle = getIntent().getExtras();
//
//        Call<List<Users>> call = apiInterface.getUsersUsername(bundle.getString("username"));
//
//        viewPager = findViewById(R.id.viewPager);
//        viewPager.setPagingEnabled(false);
//
//        toolbar = findViewById(R.id.toolbar2);
//
//        /*setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);*/
//
//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        disableShiftMode(bottomNavigationView);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.action_home:
//                                viewPager.setCurrentItem(0);
//                                break;
//                            case R.id.action_calendar:
//                                viewPager.setCurrentItem(1);
//                                break;
//                            case R.id.action_feeds:
//                                viewPager.setCurrentItem(2);
//                                break;
//                            case R.id.action_messages:
//                                viewPager.setCurrentItem(3);
//                                break;
//                            case R.id.action_profile:
//                                viewPager.setCurrentItem(4);
//                                break;
//                        }
//                        return true;
//                    }
//                });
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (prevMenuItem != null) {
//                    prevMenuItem.setChecked(false);
//                } else {
//                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                }
//                Log.d("page", "onPageSelected: " + position);
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
//                viewPager.getAdapter().notifyDataSetChanged();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        call.enqueue(new Callback<List<Users>>() {
//            @Override
//            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
//                List<Users> usersList = response.body();
////                Log.d("TAG", "onResponse: " + usersList.get(0).getNama());
//                setupViewPager(viewPager, usersList);
//            }
//
//            @Override
//            public void onFailure(Call<List<Users>> call, Throwable t) {
//                Log.d("TAG", "onFailure: ");
//            }
//        });
//    }
//
//    private void setupViewPager(ViewPager viewPager, List<Users> usersList) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager()) {
////            @Override
////            public int getItemPosition(Object object) {
////                return POSITION_NONE;
////            }
//        };
//        homeFragment = new HomeFragment(usersList.get(0));
//        chatFragment = new EventsFragment();
//        feedsFragment = new StatusFragment();
//        messageFragment = new MessageFragment();
//        profileFragment = new ProfileFragment(usersList.get(0));
//
//        adapter.addFragment(homeFragment);
//        adapter.addFragment(chatFragment);
//        adapter.addFragment(feedsFragment);
//        adapter.addFragment(messageFragment);
//        adapter.addFragment(profileFragment);
//
//        viewPager.setAdapter(adapter);
//    }
//
//
//    @SuppressLint("RestrictedApi")
//    private void disableShiftMode(BottomNavigationView view) {
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
//        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
//            for (int i = 0; i < menuView.getChildCount(); i++) {
//                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                item.setShiftingMode(false);
//                // set once again checked value, so view will be updated
//                item.setChecked(item.getItemData().isChecked());
//            }
//        } catch (NoSuchFieldException e) {
//            Log.e("BNVHelper", "Unable to get shift mode field", e);
//        } catch (IllegalAccessException e) {
//            Log.e("BNVHelper", "Unable to change value of shift mode", e);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
