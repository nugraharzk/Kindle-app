package edu.upi.mobprogproject.popup;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.helper.DbUsers;
import edu.upi.mobprogproject.model.Users;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by amaceh on 16/12/17.
 */

public class TetanggaPopUp {
    private DbUsers dbU;
    private Users data;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;

    public TetanggaPopUp(String username, Context mContext, RelativeLayout x) {
//        this.data = data;
        dbU = new DbUsers(mContext);
        dbU.open();
        data = dbU.getUser(username);
        this.mContext = mContext;
        mRelativeLayout = x;

        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = null;
        if (inflater != null) {
            customView = inflater.inflate(R.layout.popup_tetangga, null);
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

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = null;
        if (customView != null) {
//            setData(customView);
            closeButton = customView.findViewById(R.id.ib_close);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Dismiss the popup window
                    mPopupWindow.dismiss();
                }
            });
        }

        // Set a click listener for the popup window close button


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
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
    }

    private void setData(View v) {
        TextView username, nama, ttl, alamat, rtrw, desa, telepon, pekerjaan;
        username = v.findViewById(R.id.tvTuser);
        nama = v.findViewById(R.id.tvTNama);
        ttl = v.findViewById(R.id.tvTTtl);
        alamat = v.findViewById(R.id.tvTalamat);
        rtrw = v.findViewById(R.id.tvTRtRw);
        desa = v.findViewById(R.id.tvTDesa);
        telepon = v.findViewById(R.id.tvTKontak);
        pekerjaan = v.findViewById(R.id.tvTPekerjaan);
        username.setText(data.getUsername());
        nama.setText(data.getNama());
        String ttl_data = data.getTtl();
        if (ttl_data != null) {
            ttl_data = ttl_data.replace("_", ",");
            ttl.setText(ttl_data);
        }
        alamat.setText(data.getAlamat());
        String tw = mContext.getString(R.string.rtrw, data.getRt(), data.getRw());
        rtrw.setText(tw);
        desa.setText(data.getDesa());
        telepon.setText(data.getTelepon());
        pekerjaan.setText(data.getPekerjaan());
    }
}
