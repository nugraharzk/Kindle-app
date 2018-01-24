package edu.upi.mobprogproject.activity;

//import android.app.ProgressDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.model.Accounts;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText editTextUsername;
    private EditText editTextPassword;
    private List<Accounts> accountsList;
    private String username, password;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    //progress dialog
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Open the SharedPreferences feature
        // parse to sp
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);

        // Get current user and email from sp
        String user = sp.getString("user", "");
        String email = sp.getString("email", "");

        // Get to know if user has login or not
        boolean logged = sp.getBoolean("logged", false);

        // If user had login before
        if (!user.equals("") && !email.equals("") && logged) {
            // Fin this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        //initializing views
        editTextUsername = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    // Void for Login Button
    public void userLogin(View v) {
        // Parse from edit text to string
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        // Initialize the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progressbar, null);
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.create();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username/email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        dialog.show();

        // Retrofit for GET User Info
        final Context c = this;
        // Initialize the Retrofit Interface
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        // Calling Method for Retrofit
        // response : GET User from Accounts
        Call<List<Accounts>> call = apiService.getUserLogin(username, password);
        call.enqueue(new Callback<List<Accounts>>() {
            @Override
            public void onResponse(Call<List<Accounts>> call, Response<List<Accounts>> response) {
                // Parse from body to List<Accounts>
                accountsList = response.body();
                dialog.dismiss();

                // If on Web Db has an User from uname and pass input
                if (response.body().size() != 0){
                    // If this Accounts has verified by RT
                    if (accountsList.get(0).getVerified() == 1) {
                        ed = sp.edit();
                        ed.putString("user", accountsList.get(0).getUsername());
                        ed.putString("email", accountsList.get(0).getEmail());
                        ed.putBoolean("logged", true);
                        ed.apply();
                        Toast.makeText(c, "Logged In", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                    // Else
                    // Show verification activity
                    else{
                        Intent intent = new Intent(getBaseContext(), WaitVerification.class);
                        startActivity(intent);
                    }
                }
                // Else
                // Show Toast that uname or pass was wrong
                else{
                    Toast.makeText(c, "Username, Email atau Password salah", Toast.LENGTH_LONG).show();
                    //dialog.dismiss();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<Accounts>> call, Throwable t) {
                // If callback fails log to error section
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(c, "Ada Yang Salah", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Void for Regist Button
    public void daftar(View v) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        dbU.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}