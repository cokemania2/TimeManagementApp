package com.example.myapplication;

import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import aergo.hacker_edu.SampleMain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import hera.api.model.TxHash;

public class setTimeActivity extends AppCompatActivity {

    //DB코드
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //로그인한 유저
    User jiwoo = new User("addr","jiwoo","key ");

    String payLoad = "";
    TextView user_addr = null;
    TextView user_addr2 = null;
    TextView user_key = null;
    TextView user_key2 = null;

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

        Button btn_endTimeSelect = findViewById(R.id.btn_endTimeSelect);
        btn_endTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment newFragment = new TimePickerFragment(2);
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        // 숫자 선택하는 건 휴식시간 설정을 시작/종료 시간을 선택하는 걸로 바뀌어 쓰이지 않음.
//        Button btn_restTimeSelect = findViewById(R.id.btn_restTimeSelect);
//        btn_restTimeSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NumberPickerFragment newFragment = new NumberPickerFragment();
//
//                // Dialog에는 bundle을 이용해서 파라미터를 전달한다
//                Bundle bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
//                bundle.putInt("maxValue", 6);           // 최대값을 범위 설정한 값을 가져오면 좋을 듯
//                bundle.putInt("defaultValue", 3);       // 기본값 3시간
//                newFragment.setArguments(bundle);
//
//                newFragment.show(getSupportFragmentManager(), "NumberPicker");
//            }
//        });

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

                //블록체인 코드
                payLoad = time1 + "_" + time2;
                Log.d("payLoad",payLoad);

                Log.d("들어오는지","체크1");



                final DatabaseReference childRef = database.getReference("user_list"); //송신할 사용자 계정

                Log.d("들어오는지","체크2");

                loadFromFirebase(childRef, user_addr);

                Log.d("들어오는지","체크4 ");

                Intent goToSetBack = new Intent(getApplicationContext(), testActivity.class);
                startActivity(goToSetBack);
            }
        });

    }
    void loadFromFirebase(final DatabaseReference ref, final TextView tv) {
        // 해당 DB참조의 값변화리스너 추가
        Log.d("들어오는지","체크6");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("들어오는지","체크5");
                User admin = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User tmpValue;
                    if (snapshot.getKey().equals("admin")) {

                        admin = snapshot.getValue(User.class);

                        Log.d("TEST 관리자", admin.getAddress());
                        Log.d("TEST 관리자키", admin.getPrivateKey());


                    } else if(snapshot.getKey().equals(jiwoo.getName())){
                        tmpValue = snapshot.getValue(User.class);
                        Log.d("유저db",tmpValue.getAddress());
                        //PrivateKey= 송신자 키, getAddress=수신자 주소를 넣으면 돼.
                        TxHash txHash = SampleMain.toGetTxHash(admin.getAddress(), tmpValue.getPrivateKey(), payLoad);

                        // 이걸 왜 관리자 리스트에 추가하지??
                        // 트랜잭션이 "jiwoo >> admin" 이면 jiwoo 리스트에 추가되야 하는거 아닌가??
                        //전송한 해쉬값 관리자 txlist에 추가
                        SampleMain.txListPush(database.getReference("user_list/admin/txList"), txHash.toString());

                        Log.d("payLoad2",payLoad);
                        //유저 db에 payLoad 저장
                        database.getReference("user_list/jiwoo/payLoad").setValue(payLoad);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
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
