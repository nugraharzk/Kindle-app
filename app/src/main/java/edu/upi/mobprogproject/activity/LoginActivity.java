package edu.upi.mobprogproject.activity;

//import android.app.ProgressDialog;

import android.app.AlertDialog;
import android.app.Dialog;
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
import java.util.Objects;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbAccounts;
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

    int i = 0;

    DbAccounts dbU;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    //progress dialog
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);

        /*dbU = new DbAccounts(getApplicationContext());
        dbU.open();
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);

        String user = sp.getString("user", "");
        String email = sp.getString("email", "");
        boolean logged = sp.getBoolean("logged", false);
        if (!user.equals("") && !email.equals("") && logged) {
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }*/
        //initializing views
        editTextUsername = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void userLogin(View v) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.progressbar, null);
        builder.setView(view);
        builder.setCancelable(true);
        Dialog dialog = builder.create();

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
        /*Accounts u = dbU.getAccount(username, password);
        if (u.getUsername() != null) {
            ed = sp.edit();
            ed.putString("user", u.getUsername());
            ed.putString("email", u.getEmail());
            ed.putBoolean("logged", true);
            ed.apply();
            Toast.makeText(this, "Logged In", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        } else {
            Toast.makeText(this, "Username, Email atau Password salah", Toast.LENGTH_LONG).show();
        }*/

        if (accountsList != null) {
            setData(username,password);
        }else {
            getData();
            setData(username,password);
        }

        if (i == 1){
            Toast.makeText(this, "Logged In", Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Username, Email atau Password salah", Toast.LENGTH_LONG).show();
        }

        dialog.dismiss();
    }

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
        getData();
    }

    private void getData(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Accounts>> call = apiService.getAccountsList();
        call.enqueue(new Callback<List<Accounts>>() {
            @Override
            public void onResponse(Call<List<Accounts>> call, Response<List<Accounts>> response) {
                accountsList = response.body();
            }

            @Override
            public void onFailure(Call<List<Accounts>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void setData(String username, String password){
        for (Accounts acc : accountsList) {
            if (Objects.equals(username, acc.getUsername()) && acc.getPassword().equals(password)) {
                i = 1;
            }
        }
    }
}
