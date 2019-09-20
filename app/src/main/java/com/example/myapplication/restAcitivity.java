package com.example.myapplication;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.aergo.hacker_edu.SampleMain_sangyoon.sendTransaction;

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

    boolean pause_or_go;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_acitivity);
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

            activity_popup e = activity_popup.getInstance();
            e.show(getSupportFragmentManager(),activity_popup.TAG_EVENT_DIALOG);

            sendTransaction();

        }
    }
}
