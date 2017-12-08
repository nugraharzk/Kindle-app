package edu.upi.mobprogproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Accounts;
import edu.upi.mobprogproject.model.Users;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getSimpleName();

    private EditText editTextNama;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    DbUsers dbU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbU = new DbUsers(getApplicationContext());
        dbU.open();
        //initializing views
        editTextNama = findViewById(R.id.editTextNama);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void registerUser(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.progressbar, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        //getting email and password from edit texts
        String nama = editTextNama.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if there is field empty
        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


        Accounts u1 = new Accounts(username, email, password);
        //Users u2 = new Users(username, nama);
        //creating a new user
        try {
            //long rowInserted2 = dbU.insertUsers(u2);
            //if (rowInserted2 != -1) {
            //  Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
            //} else {
            //  Toast.makeText(this, "Username atau Password sudah ada", Toast.LENGTH_SHORT).show();
            //}
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Accounts> call = apiInterface.postAccount(username, password, email);
            call.enqueue(new Callback<Accounts>() {
                @Override
                public void onResponse(Call<Accounts> call, Response<Accounts> response) {
                    Log.d("PostAccounts", "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<Accounts> call, Throwable t) {
                    Log.d("PostAccounts", "onFailure: " + t);
                }
            });

            Call<Users> call1 = apiInterface.postUser(username, nama);
            call.enqueue(new Callback<Accounts>() {
                @Override
                public void onResponse(Call<Accounts> call, Response<Accounts> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<Accounts> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });
            dialog.dismiss();
        } catch (android.database.SQLException e) {
            dialog.dismiss();
            Toast.makeText(this, "Ada yang salah, hubungi developer", Toast.LENGTH_LONG).show();
            return;
        }

        finish();
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i);
    }

    public void toLogin(View view) {
        finish();
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbU.close();
    }
}