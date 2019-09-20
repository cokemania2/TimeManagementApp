package com.example.myapplication;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aergo.hacker_edu.AergoCommon;
import aergo.hacker_edu.AergoTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import hera.api.model.TxHash;
import hera.wallet.Wallet;



public class restAcitivity extends AppCompatActivity {
    TextView timer;
    CountDownTimer CDT;
    Button btnstart;
    //TextView datetext;
    Button btnstop;
    CheckBox chk1;
    CheckBox chk2;
    CheckBox chk3;

    TextView gonetime;

    boolean remain;
    long sec;
    long stopsec;
    //목표 휴식시간
    long goal;
    //중지 가능
    boolean pause_or_go;

    int memed=0;

    //Transactions Test
    protected static String endpoint = "testnet.aergo.io:7845";
    //개인키
    protected static String encPrivateKey = "485XeGg5e8xrFsvEZFrmRoPrAsgEnzw5bLtFWoTaVGbR2mCeAewoAr3eNNFPm5FFuAHZXBz9z";
    //송신자 주소
    protected static String fromAddress = "AmMDaD8pFMiL5i8Bcqpvy7sZj6L8pcHEiXJm5zmYp8dkJF2cKojD";
    //패스워드 설정
    protected static String password = "password";
    //수신자 주소
    protected static String toAddress = "AmPfZP5Jr8YvsZDdaDwbCnEvutASJJ5WZ1j87v5mZ6ukX3FXQKb9";
    //수수료 정책
    protected static String fee = "0";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_acitivity);

        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        //datetext = (TextView)findViewById(R.id.datetime);
        timer = (TextView)findViewById(R.id.restTime);
        btnstart = (Button)findViewById(R.id.button3);
        btnstop = (Button)findViewById(R.id.button7);

        gonetime = (TextView)findViewById(R.id.goneTime);
        chk1 = (CheckBox)findViewById(R.id.check1);
        chk2 = (CheckBox)findViewById(R.id.check2);
        chk3 = (CheckBox)findViewById(R.id.check3);

        remain = false;
        pause_or_go = false;

        btnstop.setClickable(false);

        //클릭리스너



        //DB에서 휴식시간 가져와서 넣기
        goal = 18000*1000;
        ((TextView)findViewById(R.id.targetTime)).setText(goal/1000 / 3600 + " 시" + (goal/1000 % 3600 / 60) + " 분" + goal/1000 % 3600 % 60 + " 초");
        ((TextView)findViewById(R.id.restTime)).setText(goal/1000 / 3600 + " 시" + (goal/1000 % 3600 / 60) + " 분" + goal/1000 % 3600 % 60 + " 초");




        //시계
        /*
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar calendar = Calendar.getInstance(); // 칼렌다 변수
                            int year = calendar.get(Calendar.YEAR); // 년
                            int month = calendar.get(Calendar.MONTH); // 월
                            int day = calendar.get(Calendar.DAY_OF_MONTH); // 일
                            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시
                            int minute = calendar.get(Calendar.MINUTE); // 분
                            int second = calendar.get(Calendar.SECOND); // 초
                            datetext.setText(year + "년 " +
                                    month + "월 " + day + "일\n" +
                                    hour + "시 " + minute + "분\n" +
                                    second + "초 \n"
                            );
                        }
                    });
                    try {
                        Thread.sleep(1000); // 1000 ms = 1초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } // while
            } // run()
        };
        thread.start();
        */
    }
    public void timerStart(View v){
        if((chk1.isChecked() == chk2.isChecked()) && (chk3.isChecked() == true) && (chk1.isChecked()==chk3.isChecked())) {
            if(remain==false) {
                pause_or_go = true; //중단 가능하게 된 상태가 됨.
                //DB에서 휴식시간 가져와서 넣기
                CDT = new MyTimer(goal + 1000, 1000);
                CDT.start();
                btnstop.setText("중지");
                btnstop.setClickable(true);
            }
            else {
                pause_or_go = true;
                CDT = new MyTimer(goal, 1000);
                CDT.start();
                btnstop.setText("중지");
                btnstop.setClickable(true);
            }
        }
        else{
            if(pause_or_go==true){
                btnstart.setClickable(true);
                btnstop.setClickable(false);
                remain = true;
                stopsec = sec;
                timer.setText(stopsec / 3600 + " 시" + (stopsec % 3600 / 60) + " 분" + stopsec % 3600 % 60 + " 초");
                CDT.cancel();
                chk1.setChecked(false);
                chk2.setChecked(false);
                chk3.setChecked(false);
                pause_or_go=false;
            }
        }
    }
    public void timerPause(View v){
        if(pause_or_go==true) {
            btnstart.setClickable(true);
            btnstop.setClickable(false);
            remain = true;
            stopsec = sec;
            timer.setText(stopsec / 3600 + " 시" + (stopsec % 3600 / 60) + " 분" + stopsec % 3600 % 60 + " 초");
            CDT.cancel();
            chk1.setChecked(false);
            chk2.setChecked(false);
            chk3.setChecked(false);
            pause_or_go=false;
        }
        else{

        }
    }

    public void timerFast(View v){
        goal=goal-(3600*1000);
        timer.setText(goal / 3600000 + " 시" + (goal % 3600 / 60000) + " 분" + goal/1000 % 3600 % 60 + " 초");
    }

    public void timerBack(View v){
        goal=goal+(5*1000);
        timer.setText(goal/1000 / 3600 + " 시" + (goal/1000 % 3600 / 60) + " 분" + goal/1000 % 3600 % 60 + " 초");
    }


    //DB
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference jw_dbref = database.getReference("user_list/jiwoo/txList");


    public void txList_update_from_firebase(DatabaseReference ref, final String txhash) {
        // 해당 DB참조의 값변화리스너 추가 한번만 됨.
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            int count = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tmpHash;
                    tmpHash = snapshot.getValue(String.class);
                    count = count+1;
                    Log.d("FirebaseTestActivity", "ValueEventListener : " + tmpHash);
                }
                String count_tostring = Integer.toString(count);
                jw_dbref.child(count_tostring).setValue(txhash);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
            }
        });
    }


    public class MyTimer extends CountDownTimer{

        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }
        public void onTick(long millisUntilFinished) {
            Log.d("ontick","in");
            long t = millisUntilFinished / 1000;
            sec = t;
            timer.setText(t / 3600 + " 시" + (t % 3600 / 60) + " 분" + t % 3600 % 60 + " 초");
            long t2 = goal/1000 - t;
            gonetime.setText(t2 / 3600 + " 시" + (t2 % 3600 / 60) + " 분" + t2 % 3600 % 60 + " 초");
            if(t==1){
                t=0;
            }
            btnstart.setClickable(false);
            btnstop.setClickable(true);
        }


        public void onFinish() {
            long t=0;
            timer.setText("0 시 0 분 0 초");
            Wallet wallet = AergoCommon.getAergoWallet(endpoint);
                //전송 토큰
            String amount = "1";

                //paylaod data
            String payload = "time_point";

            TxHash tx = AergoTransaction.sendTransaction(wallet, toAddress, password, encPrivateKey, payload, amount, fee);
            String tx_string = tx.toString();
            txList_update_from_firebase(jw_dbref, tx_string);
            activity_popup e = activity_popup.getInstance();
            e.show(getSupportFragmentManager(),activity_popup.TAG_EVENT_DIALOG);
        }
    }
}
