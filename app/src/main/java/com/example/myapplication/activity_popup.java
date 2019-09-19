package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class activity_popup extends DialogFragment implements View.OnClickListener {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public activity_popup() {}
    public static activity_popup getInstance() {
        activity_popup e = new activity_popup();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_popup, container);
        Button mConfirmBtn = (Button) v.findViewById(R.id.button);
        mConfirmBtn.setOnClickListener(this);
        setCancelable(false);
        return v;
    }
    public void onClick(View v){
        dismiss();
        Intent goTochild = new Intent(getActivity().getBaseContext(),testActivity.class);
        startActivity(goTochild);
    }

}
