package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView patentsB = findViewById(R.id.parent);
        CardView childB = findViewById(R.id.child);
        Button button  = findViewById(R.id.button);


        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc3) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        patentsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToparent = new Intent(getApplicationContext(),userMain.class);
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


}
