package edu.upi.mobprogproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upi.mobprogproject.R;

public class EditAkunActivity extends AppCompatActivity {

    EditText etmail, etolpass, etnewpass, etnewpass2;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        EditText etuser = findViewById(R.id.etUsername);
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
        }

        //Accounts u = dbA.getAccount(sp.getString("user", ""), passold);
        // if (u.getUsername() != null) {
//            Accounts x = new Accounts();
//            x.setEmail(email);
//            if (!new_pass.isEmpty()) {
//                x.setPassword(new_pass);
//            }
//            if (u.getPassword().equals(passold)) {
//                dbA.updateAccounts(sp.getString("user", ""), x);
//                ed = sp.edit();
//                ed.putString("email", x.getEmail());
//                ed.putBoolean("logged", true);
//                ed.apply();
//            }
//            Toast.makeText(this, "Tersimpan", Toast.LENGTH_LONG).show();
//            Intent i = getIntent();
//            setResult(RESULT_OK, i);
//            finish();
//        } else {
//            Toast.makeText(this, "Password Lama salah", Toast.LENGTH_LONG).show();
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dbA.close();
    }
}
