package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView patentsB = findViewById(R.id.parent);
        CardView childB = findViewById(R.id.child);
        Button button  = findViewById(R.id.button);

        myDialog = new Dialog(this);

        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        patentsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToparent = new Intent(getApplicationContext(), adminMain.class);
                startActivity(goToparent);
            }
        });
        childB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goTochild = new Intent(getApplicationContext(),testActivity.class);
                startActivity(goTochild);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToregister = new Intent(getApplicationContext(),register.class);
                startActivity(goToregister);
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
