package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;




public class testActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        CardView gorest = (CardView)findViewById(R.id.gorest);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc3) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        gorest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorest = new Intent(getApplicationContext(),restAcitivity.class);
                startActivity(gorest);
            }
        });

        // 시간 깜빡이게 하기
        /*
        TextView timerr = (TextView)findViewById(R.id.timerr);
        Animation anim = new AlphaAnimation(1,0);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        timerr.startAnimation(anim);
        */



    }
}
