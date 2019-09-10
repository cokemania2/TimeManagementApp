package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView patentsB = findViewById(R.id.parent);
        CardView childB = findViewById(R.id.child);

        patentsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToparent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(goToparent);
            }
        });
        childB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goTochild = new Intent(getApplicationContext(),userMain.class);
                startActivity(goTochild);
            }
        });

    }
}
