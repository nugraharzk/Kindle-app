package edu.upi.mobprogproject.popup;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

import java.util.Calendar;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbStatus;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Status;
import edu.upi.mobprogproject.rest.ApiClient;
import edu.upi.mobprogproject.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by amaceh on 30/12/17.
 */

public class StatusPopUp {

    private static final String TAG = StatusPopUp.class.getSimpleName();

    private Context mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    View customView = null;


    DbStatus dbS;
    DbUsers dbU;
    SharedPreferences sp;
    Button saveBt;

    public StatusPopUp(Context mContext, RelativeLayout x) {
        dbS = new DbStatus(mContext);
        dbU = new DbUsers(mContext);
        dbS.open();
        dbU.open();
        this.mContext = mContext;
        mRelativeLayout = x;


        //background = bg;
//        mRelativeLayout.setVisibility(View.VISIBLE);
//        mRelativeLayout.setVisibility(View.GONE);

        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        if (inflater != null) {
            customView = inflater.inflate(R.layout.popup_status, null);
            saveBt = customView.findViewById(R.id.btSimpanStatus);
            saveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveStatus(view);
                    mPopupWindow.dismiss();
                }
            });
        }

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

    public void saveStatus(View v) {
        Status S = new Status();
        S.setWaktu(Calendar.getInstance().getTime().toString());
        sp = mContext.getSharedPreferences("edu.upi.mobprogproject.user", MODE_PRIVATE);
        S.setUsername(sp.getString("user", ""));
        S.setLike(0);
        EditText stat = customView.findViewById(R.id.etStatus);
        String status = stat.getText().toString().trim();
        if (TextUtils.isEmpty(status)) {
            Toast.makeText(mContext, "Please enter username/email", Toast.LENGTH_LONG).show();
            return;
        }

        S.setStatus(status);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Status> call = apiInterface.postStatus(sp.getString("user", ""), S.getStatus(), S.getWaktu(), String.valueOf(S.getLike()));
        long a = dbS.insertStatus(S);

        if (a != -1) {
            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });
            Toast.makeText(mContext, "Status Berhasil Diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Mohon maaf terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }
    }

    public PopupWindow getPopUP() {
        return mPopupWindow;
    }
}