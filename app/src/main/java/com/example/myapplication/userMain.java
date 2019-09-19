package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import aergo.hacker_edu.AergoCommon;
import aergo.hacker_edu.SampleMain;
import okhttp3.OkHttpClient;

public class userMain extends AppCompatActivity {
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        //블록체인
        OkHttpClient client = new OkHttpClient();



        //액션바
        ActionBar ab = getSupportActionBar() ;
        myDialog = new Dialog(this);
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc3) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        Button buttonn = findViewById(R.id.popup);
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });

        Button makekey = findViewById(R.id.makeKey);
        makekey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AergoCommon.createAergoKey();
            }
        });
        Button getBlances = findViewById(R.id.getBlances);
        getBlances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleMain.getBlances();
            }
        });
        Button sendTransaction = findViewById(R.id.sendTransaction);
        sendTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleMain.sendTransaction();
            }
        });
    }

    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
