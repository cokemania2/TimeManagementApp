package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseTestActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        /////////////////////// FIrebase Database 데이터 쓰기 & 읽기

        // 사용자 데이터 삽입
        // final DatabaseReference dbRef = database.getReference();
        // User tmp = new Parent(or Child) (이름, 포인트, 키)
        // dbRef.child("parent(or child)").child(DB 상에 구분되어지는 카테고리).setValue(tmp);

        // Firebase에서 child의 데이터를 읽어와
        DatabaseReference childRef = database.getReference("child");
        final TextView tv_childView = findViewById(R.id.tv_childView);
        loadFromFirebase(childRef, tv_childView);

        // Firebase에서 parent의 데이터를 읽어와
        final TextView tv_parentView = findViewById(R.id.tv_parentView);
        DatabaseReference parentRef = database.getReference("parent");
        loadFromFirebase(parentRef, tv_parentView);

        /////////////////////// sender, receiver 선택

    }

    // Firebase에서 데이터를 읽어서 TextView를 새로고침.
    void loadFromFirebase(DatabaseReference ref, final TextView tv) {
        // 해당 참조의 리스너 추가
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User tmpValue = snapshot.getValue(User.class);
                    Log.d("FirebaseTestActivity", "ValueEventListener : " + tmpValue);
                    value = value.concat(tmpValue + "\n");
                }
                // 읽어온 데이터를 String으로 바꾸어 TextView에 삽입.
                tv.setText(value);
                Log.d("Read Firebase database ", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
            }
        });
    }


    void searchFromFirebase(DatabaseReference ref) {

        final List<User> listUsers = new ArrayList<>();
        ArrayAdapter arrAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listUsers);
        Spinner spn_sender = findViewById(R.id.spn_sender);
        spn_sender.setAdapter(arrAdapter);

        // TODO point를 송금할 Sender를 선택하는 Spinner
        spn_sender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 어떤 아이템이 선택됐을 때
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 아무것도 선택되지 않았을 때
            }
        });

        // TODO Firebase 검색 방법
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("search database", "onChildAdded:" + dataSnapshot.getKey());


                // Firebase 검색
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User tmpValue = snapshot.getValue(User.class);
                    Log.d("FirebaseTestActivity", "ValueEventListener2 : " + tmpValue);
                    listUsers.add(tmpValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("search database", "postComments:onCancelled", databaseError.toException());
            }
        });
    }
}
