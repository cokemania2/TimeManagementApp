package com.example.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import aergo.hacker_edu.AergoCommon;
import aergo.hacker_edu.AergoQuery;
import aergo.hacker_edu.SampleMain;
import hera.wallet.Wallet;


public class testActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    ProgressDialog dialog;
    Dialog myDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<User> userList;
    private String key;
    private String address;
    long startTime = 0;
    long endTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        myDialog = new Dialog(this);

        CardView gorest = (CardView)findViewById(R.id.gorest);
        CardView Info = (CardView)findViewById(R.id.bankcardId);


        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        gorest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorest = new Intent(getApplicationContext(),SplahScreenActivity2.class);
                startActivity(gorest);
            }
        });

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUserInfo = new Intent(getApplicationContext(), UserInfoActivity.class);

                gotoUserInfo.putExtra("userName", "jiwoo");
                startActivity(gotoUserInfo);
            }
        });



        long time = 21120000 - 18900000;



        final DatabaseReference childRef = database.getReference("user_list"); //송신할 사용자 계정
        loadFromFirebase(childRef, startTime, endTime);





//        Animation anim = new AlphaAnimation(1,0);
//        anim.setDuration(50); //You can manage the time of the blink with this parameter
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        timerr.startAnimation(anim);



        CardView linear_restTime = findViewById(R.id.linear_restTime);
        linear_restTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSetTime = new Intent(getApplicationContext(), setTimeActivity.class);
                startActivity(goToSetTime);
            }
        });

        CardView invite = findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ProgressBar
                dialog = ProgressDialog.show(testActivity.this, "",
                        "Loading. Please wait...", true);
                DatabaseReference dbRef = database.getReference("user_list");
                loadFromFirebase(dbRef);
                SampleMain.sendTransaction();
                loadFromFirebase(dbRef);
                ShowPopup(view);
            }
        });



    }

    void loadFromFirebase(final DatabaseReference ref) {

        // 해당 DB참조의 값변화리스너 추가
        final String[] ul = new String[1];
        ul[0] = "";
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User tmpUser;
                    tmpUser= snapshot.getValue(User.class);

                    Log.d("FirebaseTestActivity", "ValueEventListener : " + tmpUser);
                    userList.add(tmpUser);
                    Log.d("userList : ",userList.toString());
                    ul[0] = ul[0].concat(tmpUser.toString() + "\n");
                }
                key = userList.get(0).getPrivateKey(); // 관리자 키
                address = userList.get(1).getAddress(); // user주소
                SampleMain.sendTransaction(address,key,"0");
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
            }
        });

    }

    void loadFromFirebase(final DatabaseReference ref, final long startTime, final long endTime) {

        try {
            ref.addValueEventListener(new ValueEventListener() {
                long st = 0;
                long et = 0;
                long time = 0;

                public void onDataChange(DataSnapshot dataSnapshot) throws NullPointerException {
                    String payLoad = null;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey().equals("jiwoo")) {
                            payLoad = snapshot.getValue(User.class).getPayload();
                            st = Long.parseLong((payLoad.split("_"))[0]);
                            et = Long.parseLong((payLoad.split("_"))[1]);
                            time = et - st;
                        }
                        break;
                    }
                    if (payLoad.equals(null))
                        throw new NullPointerException();

                    else
                        ((TextView) findViewById(R.id.timerr)).setText(time / 1000 / 3600 + " 시" + (time / 1000 % 3600 / 60) + " 분" + time / 1000 % 3600 % 60 + " 초");

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Read Firebase database", "Failed to read value.", error.toException());
                }
            });
        } catch (NullPointerException e) {
            ((TextView) findViewById(R.id.timerr)).setText("오늘의 휴식시간이 설정하세여");
        }
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
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
