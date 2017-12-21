package edu.upi.mobprogproject.content;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.HomeActivity;
import edu.upi.mobprogproject.activity.ListTetanggaActivity;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST = 99;

    FusedLocationProviderClient mFusedLocationClient;

    private GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker tetanggaMarker;
    MapView mapView;
    private Activity activity;
    private List<Users> usersList;
    private Toolbar toolbar4;
    private DbUsers dbU;
    ImageView toListT;
    int i = 0;



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position X");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = map.addMarker(markerOptions);

                if (i == 0) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    map.animateCamera(CameraUpdateFactory.zoomTo(17));
                }

                i = 1;

                //move map camera
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        toListT = rootView.findViewById(R.id.listtetangga);
        toListT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ListTetanggaActivity.class);
                startActivity(i);
            }
        });
        dbU = new DbUsers(activity);
        dbU.open();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

//        checkForLocationRequest();
//        checkForLocationSettings();

//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<List<Users>> call = apiInterface.getUsersList();
//        call.enqueue(new Callback<List<Users>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Users>> call, @NonNull Response<List<Users>> response) {
//                Log.d(TAG, "onResponse: " + response.body());
//                usersList = response.body();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Users>> call, @NonNull Throwable t) {
//                Log.d(TAG, "onFailure: " + t);
//            }
//        });

        toolbar4 = rootView.findViewById(R.id.toolbar3);
        if (toolbar4 != null) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar4);
        }
        //toolbar4.setTitle(null);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(activity.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                map = mMap;
                getLastLocation();
                if (mLastLocation != null) {
                    LatLng all = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(all).zoom(17).build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    LatLng bandung = new LatLng(-6.90389, 107.61861);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(bandung).zoom(12).build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                try {
                    showTetangga(map);
                } catch (Exception e) {
                    Log.i("kokoko", e.toString());
                }
            }
        });

        return rootView;
    }

    private void getLastLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    mLastLocation = task.getResult();
                                }
                            }
                        });
                map.setMyLocationEnabled(true);
            }
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                            }
                        }
                    });
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        dbU.close();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);
            }
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(activity, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    private void showTetangga(GoogleMap map) {
        usersList = dbU.getAllUsers();
        for (Users us : usersList) {
            if (us.getLat() != null && us.getLng() != null) {
                Log.i("LatLng", Double.parseDouble(us.getLat()) + "+" + Double.parseDouble(us.getLng()));
                LatLng latLng = new LatLng(Double.parseDouble(us.getLat()), Double.parseDouble(us.getLng()));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(us.getNama()).snippet(us.getAlamat());
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                BitmapDescriptor iconx = BitmapDescriptorFactory.fromResource(R.drawable.home_icon);
                markerOptions.icon(iconx);
                tetanggaMarker = map.addMarker(markerOptions);
            }
        }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dbU.close();
    }
}
