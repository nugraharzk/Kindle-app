package edu.upi.mobprogproject.activity;

//import android.app.ProgressDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbAccounts;
import edu.upi.mobprogproject.model.Accounts;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    DbAccounts dbU;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    //progress dialog
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbU = new DbAccounts(getApplicationContext());
        dbU.open();
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);

        String user = sp.getString("user", "");
        String email = sp.getString("email", "");
        boolean logged = sp.getBoolean("logged", false);
        if (!user.equals("") && !email.equals("") && logged) {
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
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
        Accounts u = dbU.getAccount(username, password);
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
        dbU.close();
    }
}
