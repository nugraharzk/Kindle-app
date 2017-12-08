package edu.upi.mobprogproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.model.Accounts;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAkunActivity extends AppCompatActivity {

    EditText etuser, etmail, etolpass, etnewpass, etnewpass2;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        etuser = findViewById(R.id.etUsername);
        etuser.setEnabled(false);

        etmail = findViewById(R.id.etEmail);
        etolpass = findViewById(R.id.etOldPass);
        etnewpass = findViewById(R.id.etNewPass1);
        etnewpass2 = findViewById(R.id.etNewPass2);
        sp = getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);

        //Accounts ac = dbA.getAccInfo(sp.getString("user", ""));
        etuser.setText(sp.getString("user", ""));
        //etmail.setText(ac.getEmail());
    }

    public void saveAkun(View v) {
        String email, passold, passnew1, passnew2;
        email = etmail.getText().toString().trim();
        passold = etolpass.getText().toString().trim();
        passnew1 = etnewpass.getText().toString().trim();
        passnew2 = etnewpass2.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passold)) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
        String new_pass = null;
        if (TextUtils.isEmpty(passnew1) ^ TextUtils.isEmpty(passnew2)) {
            Toast.makeText(this, "Ke 2 Password tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        } else if (!passnew1.equals(passnew2)) {
            Toast.makeText(this, "Ke 2 Password berbeda", Toast.LENGTH_LONG).show();
            return;
        } else {
            new_pass = passnew1;
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Accounts> call = apiInterface.putAccount(etuser.getText().toString(), new_pass, email);
            call.enqueue(new Callback<Accounts>() {
                @Override
                public void onResponse(Call<Accounts> call, Response<Accounts> response) {
                    Log.d("EditAkunActivity", "onResponse: " + response.body());
                    success();
                    finish();
                }

                @Override
                public void onFailure(Call<Accounts> call, Throwable t) {
                    Log.d("EditAkunActivity", "onFailure: " + t);
                }
            });
        }
    }

    private void success() {
        Toast.makeText(this, "Akun Diubah!", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dbA.close();
    }
}