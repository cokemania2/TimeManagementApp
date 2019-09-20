package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class setTimeActivity extends AppCompatActivity {

    //DB코드
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference loopyRef = database.getReference("child/testChild2");

    //지갑 객체
    //wallet 선언부
    String payLoad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        Button btn_startTimeSelect = findViewById(R.id.btn_startTimeSelect);
        btn_startTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment(1);
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        int maxTime;
        Button btn_endTimeSelect = findViewById(R.id.btn_endTimeSelect);
        btn_endTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment(2);
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        Button btn_restTimeSelect = findViewById(R.id.btn_restTimeSelect);
        btn_restTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberPickerFragment newFragment = new NumberPickerFragment();

                // Dialog에는 bundle을 이용해서 파라미터를 전달한다
                Bundle bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
                bundle.putInt("maxValue", 6);           // 최대값을 범위 설정한 값을 가져오면 좋을 듯
                bundle.putInt("defaultValue", 3);       // 기본값 3시간
                newFragment.setArguments(bundle);

                newFragment.show(getSupportFragmentManager(), "NumberPicker");
            }
        });

        Button btn_submitTime = findViewById(R.id.btn_submitTime);
        btn_submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Submit 클릭하면 Firebase에 저장 후 원래 액티비티로

                // 시작 시간
                TextView tv1 = findViewById(R.id.tv_timeView1);
                long time1 = getMill((String) tv1.getText());

                // 끝 시간
                TextView tv2 = findViewById(R.id.tv_timeView2);
                long time2 = getMill((String) tv2.getText());

                // 휴식할 시간
                TextView tv3 = findViewById(R.id.tv_timeView3);
                int restTime = Integer.valueOf((String) tv3.getText());

                // DB 코드
//                DatabaseReference loopyRef = database.getReference("child/testChild2");
//                loopyRef.child("startTime").setValue(time1);
//                loopyRef.child("endTime").setValue(time2);
//                loopyRef.child("restTime").setValue(restTime);

                //블록체인 코드
                payLoad = time1 + "_" + time2;
                //wallet.send(payLoad);


                Intent goToSetBack = new Intent(getApplicationContext(), testActivity.class);
                startActivity(goToSetBack);
            }
        });

    }

    long getMill(String str) {
        long time = 0;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            Log.d("Time save at Firebase", "Value is: " + str);
            Date date = timeFormat.parse(str);      // Date 객체를 만들어 저장
            time = date.getTime();                  // Date의 getTime()을 이용하여 밀리초를 구한다.
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }
}
