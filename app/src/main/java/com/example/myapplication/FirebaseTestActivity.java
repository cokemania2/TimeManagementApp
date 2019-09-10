package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseTestActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference parentRef = database.getReference("parent");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        Button btn_dbTest = findViewById(R.id.btn_dbTest);
        btn_dbTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                parentRef.child("name").setValue("Dana Kim");
                parentRef.child("child").setValue("Dora Kim");
            }
        });

        final TextView tv_dataView = findViewById(R.id.tv_dataView);

        // Read from the database
        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmpValue = (String)snapshot.getValue();
                    Log.d("MainActivity", "ValueEventListener : " + tmpValue);
                    value = value.concat(tmpValue);
                }
                tv_dataView.setText(value);

                Log.d("Read Firebase database ", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
            }
        });
    }

}
