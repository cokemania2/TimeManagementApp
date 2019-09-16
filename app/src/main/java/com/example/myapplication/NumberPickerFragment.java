package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class NumberPickerFragment extends DialogFragment implements NumberPicker.OnValueChangeListener{

    int maxValue;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(0);
        maxValue = getArguments().getInt("maxValue");   // 설정한 시간 범위 내를 받아옴.
        numberPicker.setMaxValue(maxValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("시간을 선택하세요");
        builder.setMessage("");

        //Ok button을 눌렀을 때 동작 설정
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
            }
        });

        //취소 버튼을 눌렀을 때 동작 설정
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(numberPicker);
        // number picker 실행
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        TextView tv = getActivity().findViewById(R.id.tv_timeView3);
        tv.setText(String.valueOf(numberPicker.getValue()));
    }
}
