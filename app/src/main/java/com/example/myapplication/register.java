package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    private Button sendbt;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    EditText keyE;
    EditText nameE;
    EditText recommendedkeyE;
    public String msg;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);

        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        super.onCreate(savedInstanceState);
        sendbt = (Button) findViewById(R.id.register_sendbt);

//        //입력 받은 값
//        nameE = (EditText) findViewById(R.id.nameEdit);
//        keyE = (EditText) findViewById(R.id.keyEdit);
//        recommendedkeyE = (EditText) findViewById(R.id.recommendedkeyEdit);
//
//
//        //가입 버튼

//        sendbt.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                msg = keyE.getText().toString();
//                databaseReference.child("child").child("testChild3").child("key").push().setValue(msg);
//                msg = nameE.getText().toString();
//                databaseReference.child("child").child("testChild3").child("name").push().setValue(msg);
//                databaseReference.child("child").child("testChild3").child("point").push().setValue("100");
//                msg = recommendedkeyE.getText().toString();
//                databaseReference.child("child").child("testChild3").child("recommededKey").push().setValue(msg);
//            }
//        });

        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMain = new Intent(getApplicationContext(),testActivity.class);
                goToMain.putExtra("classname","okRegister");
                startActivity(goToMain);
            }
        });

    }
}
