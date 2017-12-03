package edu.upi.mobprogproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.lang.reflect.Field;
import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.adapter.CustomViewPager;
import edu.upi.mobprogproject.adapter.ViewPagerAdapter;
import edu.upi.mobprogproject.content.CalendarFragment;
import edu.upi.mobprogproject.content.FeedsFragment;
import edu.upi.mobprogproject.content.HomeFragment;
import edu.upi.mobprogproject.content.MessageFragment;
import edu.upi.mobprogproject.content.ProfileFragment;
import edu.upi.mobprogproject.model.Users;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Toolbar toolbar;
    CustomViewPager viewPager;

    public String nama, ttl, alamat, telepon;

    HomeFragment homeFragment;
    CalendarFragment chatFragment;
    FeedsFragment feedsFragment;
    MessageFragment messageFragment;
    ProfileFragment profileFragment;
    Bundle bundle;

    MenuItem prevMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        bundle = getIntent().getExtras();

        Call<List<Users>> call = apiInterface.getUsersUsername(bundle.getString("username"));

        viewPager = findViewById(R.id.viewPager);
        viewPager.setPagingEnabled(false);

        toolbar = findViewById(R.id.toolbar2);

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_calendar:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_feeds:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_messages:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.action_profile:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return true;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> usersList = response.body();
//                Log.d("TAG", "onResponse: " + usersList.get(0).getNama());
                setupViewPager(viewPager, usersList);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, List<Users> usersList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public int getItemPosition(Object object) {
//                return POSITION_NONE;
//            }
        };
        homeFragment = new HomeFragment(usersList.get(0));
        chatFragment = new CalendarFragment();
        feedsFragment = new FeedsFragment();
        messageFragment = new MessageFragment();
        profileFragment = new ProfileFragment(usersList.get(0));

        adapter.addFragment(homeFragment);
        adapter.addFragment(chatFragment);
        adapter.addFragment(feedsFragment);
        adapter.addFragment(messageFragment);
        adapter.addFragment(profileFragment);

        viewPager.setAdapter(adapter);
    }


    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
