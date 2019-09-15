package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainnActivity extends AppCompatActivity {
    TextView timer;
    CountDownTimer CDT;
    Button btnstart;
    TextView datetext;
    Button btnstop;
    boolean remain;
    long sec;
    long stopsec;
    //목표 휴식시간
    long goal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datetext = (TextView)findViewById(R.id.datetime);
        timer = (TextView)findViewById(R.id.textView);
        btnstart = (Button)findViewById(R.id.button3);
        btnstop = (Button)findViewById(R.id.button4);
        remain = false;
        btnstop.setClickable(false);
        //DB에서 휴식시간 가져와서 넣기
        goal = 3600*1000;
        ((TextView)findViewById(R.id.textView2)).setText(goal/1000 / 3600 + " 시" + (goal/1000 % 3600 / 60) + " 분" + goal/1000 % 3600 % 60 + " 초");
        ((TextView)findViewById(R.id.textView)).setText(goal/1000 / 3600 + " 시" + (goal/1000 % 3600 / 60) + " 분" + goal/1000 % 3600 % 60 + " 초");

        //시계
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
    }
    public void timerStart(View v){
        if(remain==false) {
            //DB에서 휴식시간 가져와서 넣기
            CDT = new MyTimer(goal+1000, 1000);
            CDT.start();
        }
        else {
            CDT = new MyTimer(stopsec * 1000+1000, 1000);
            CDT.start();
        }
    }
    public void timerPause(View v){
        btnstart.setClickable(true);
        btnstop.setClickable(false);
        remain = true;
        stopsec = sec;
        timer.setText(stopsec / 3600 + " 시" + (stopsec % 3600 / 60) + " 분" + stopsec % 3600 % 60 + " 초");
        CDT.cancel();
    }

    public class MyTimer extends CountDownTimer{

        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }
        public void onTick(long millisUntilFinished) {
            Log.d("ontick","in");
            long t = millisUntilFinished / 1000;
            timer.setText(t / 3600 + " 시" + (t % 3600 / 60) + " 분" + t % 3600 % 60 + " 초");
            sec = t;
            btnstart.setClickable(false);
            btnstop.setClickable(true);
        }

        public void onFinish() {


        }
    }
}
