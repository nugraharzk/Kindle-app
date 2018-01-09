package edu.upi.mobprogproject.popup;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbNotif;
import edu.upi.mobprogproject.model.Notifications;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by amaceh on 30/12/17.
 */

public class AlertPopUp {
    private final Context context;
//    private Activity activity;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private static String TAG = "UpNotif";
    private SharedPreferences sp;
    private DbNotif dbN;

    public AlertPopUp(Context mContext, RelativeLayout x) {
//        this.data = data;
        this.context = mContext;
//        this.activity = activity;
        mRelativeLayout = x;
        dbN = new DbNotif(context);
        dbN.open();

        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = null;
        if (inflater != null) {
            customView = inflater.inflate(R.layout.popup_alert, null);
        }

        Button biasa = customView.findViewById(R.id.btBiasa);
        Button penting = customView.findViewById(R.id.btPenting);
        Button darurat = customView.findViewById(R.id.Darurat);

        final Notifications no = new Notifications();

        final EditText etPesan = customView.findViewById(R.id.etPesan);

        sp = mContext.getSharedPreferences("edu.upi.mobprogproject.user", Context.MODE_PRIVATE);
        no.setUsername(sp.getString("user",""));

        biasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cek = etPesan.getText().toString().trim();
                if (TextUtils.isEmpty(cek)){
                    Toast.makeText(context, "Isi Pesan Anda!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    no.setPesan(cek);
                    no.setUrgensi("Biasa");
                    setNotifAlert(no.getUsername(), no.getPesan(), no.getUrgensi());
                    dbN.insertNotif(no);
                    mPopupWindow.dismiss();
                    Toast.makeText(context, "Data Tersimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        penting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cek = etPesan.getText().toString().trim();
                if (TextUtils.isEmpty(cek)){
                    Toast.makeText(context, "Isi Pesan Anda!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    no.setPesan(cek);
                    no.setUrgensi("Penting");
                    setNotifAlert(no.getUsername(), no.getPesan(), no.getUrgensi());
                    dbN.insertNotif(no);
                    mPopupWindow.dismiss();
                    Toast.makeText(context, "Data Tersimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        darurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cek = etPesan.getText().toString().trim();
                if (TextUtils.isEmpty(cek)){
                    Toast.makeText(context, "Isi Pesan Anda!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    no.setPesan(cek);
                    no.setUrgensi("Darurat");
                    setNotifAlert(no.getUsername(), no.getPesan(), no.getUrgensi());
                    dbN.insertNotif(no);
                    mPopupWindow.dismiss();
                    Toast.makeText(context, "Data Tersimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOutsideTouchable(true);
        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
//        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                background.setVisibility(View.GONE);
//            }
//        });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
        // Finally, show the popup window at the center location of root relative layout
    }

    public void show() {
        //background.setVisibility(View.VISIBLE);
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
    }

    public void setNotifAlert(String username, String pesan, String urgensi){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Notifications> call = apiService.postNotif(username,pesan,urgensi);
        call.enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}