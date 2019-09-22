package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    int textViewNum;

    TimePickerFragment(int textViewNum) {
        this.textViewNum = textViewNum;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        android.app.TimePickerDialog tpd = new android.app.TimePickerDialog(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK, this,
                hour, minute, DateFormat.is24HourFormat(getActivity()));

        return tpd;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tv = null;

        if (textViewNum == 1) {             // start
            tv = getActivity().findViewById(R.id.tv_timeView1);
            tv.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
        }

        if (textViewNum == 2) {             // end
            tv = getActivity().findViewById(R.id.tv_timeView2);
            tv.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
        }
    }
}
