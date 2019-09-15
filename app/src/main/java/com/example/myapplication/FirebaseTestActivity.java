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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FirebaseTestActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        /////////////////////// FIrebase Database 데이터 쓰기 & 읽기
        // 사용자 데이터 삽입
        // final DatabaseReference dbRef = database.getReference();
        // Parent(or Child) tmp = new Parent(or Child) (이름, 포인트, 키)
        // dbRef.child("parent(or child)").child(DB 상에 구분되어지는 카테고리 ID).setValue(tmp);

        // Firebase에서 child의 데이터를 읽어와
        final DatabaseReference childRef = database.getReference("child");
        final TextView tv_childView = findViewById(R.id.tv_childView);
        loadFromFirebase(childRef, tv_childView);

        // Firebase에서 parent의 데이터를 읽어와
        final TextView tv_parentView = findViewById(R.id.tv_parentView);
        final DatabaseReference parentRef = database.getReference("parent");
        loadFromFirebase(parentRef, tv_parentView);


        /////////////////////// TimePicker 를 이용한 시간 저장
        TimePicker tp_timeSave = findViewById(R.id.tp_timeSave);
        tp_timeSave.setOnTimeChangedListener(this);


        //////////////////////// point 보내기 (Nami > Loopy)
        Button btn_sendPoint = findViewById(R.id.btn_sendPoint);
        btn_sendPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference parentPointRef = parentRef.child("testParent2");
                int parentPoint = getValue(parentPointRef);
                Log.d("TEST parentPoint", "Value is " + parentPoint);
                parentPointRef.setValue(parentPoint - 10);

                DatabaseReference childPointRef = childRef.child("testChild2");
                int childPoint = getValue(childPointRef);
                Log.d("TEST childPoint", "Value is " + childPoint);
                childPointRef.setValue(childPoint + 10);
            }
        });

    }

    // 데이터 읽어오기
    Integer getValue(DatabaseReference ref) {
        final Integer[] v = new Integer[1];
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // TODO point 데이터 가져오기 !!
                v[0] = (Integer) dataSnapshot.child("point").getValue();
                Log.d("TEST getValue return", "value is " + v[0].toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 읽기 실패
            }
        });
        return v[0];
    }

    // Firebase에서 데이터를 읽어서 TextView를 새로고침.
    void loadFromFirebase(final DatabaseReference ref, final TextView tv) {
        // 해당 DB참조의 값변화리스너 추가
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User tmpValue;
                    if (ref.getKey().compareTo("child") == 0) {
                        tmpValue = snapshot.getValue(Child.class);
                    } else {
                        tmpValue = snapshot.getValue(Parent.class);
                    }

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

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int min) {
        // 시와 분을 아래 format에 따라 문자열로 만듦
        String str = hour + ":" + min;
        long time = 0;

        // 시간을 밀리초로 바꾸어 저장하기 위해서..
        final DatabaseReference dbRef = database.getReference();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            Log.d("Time save at Firebase", "Value is: " + str);
            Date date = timeFormat.parse(str);      // Date 객체를 만들어 저장
            time = date.getTime();                  // Date의 getTime()을 이용하여 밀리초를 구한다.
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // testChild로 로그인 되었다는 가정하에
        // testChild(Minho)에 time을 저장
        dbRef.child("child").child("testChild").child("time").setValue(time);
    }
}
