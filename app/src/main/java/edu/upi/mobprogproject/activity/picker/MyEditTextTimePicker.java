package edu.upi.mobprogproject.activity.picker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by amaceh on 01/12/17.
 */

public class MyEditTextTimePicker implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private EditText _editText;
    private int _hours;
    private int _minute;
    private Context _context;

    public MyEditTextTimePicker(Context context, int editTextViewID) {
        Activity act = (Activity) context;
        this._editText = act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        _hours = hourOfDay;
        _minute = minute;
        updateDisplay();
    }

    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog et = new TimePickerDialog(_context, AlertDialog.THEME_HOLO_LIGHT, this, hour, minute, true);
        et.show();
    }

    private void updateDisplay() {
        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_hours).append(":").append(_minute).append(" "));
    }
}
