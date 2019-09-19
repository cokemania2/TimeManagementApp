package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;

import aergo.hacker_edu.AergoCommon;
import aergo.hacker_edu.AergoQuery;
import aergo.hacker_edu.SampleMain;
import hera.wallet.Wallet;
import okhttp3.OkHttpClient;

public class userMain extends AppCompatActivity {

    Dialog myDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<User> userList;
    TextView tv_userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        //블록체인
        OkHttpClient client = new OkHttpClient();



        //액션바
        ActionBar ab = getSupportActionBar() ;
        myDialog = new Dialog(this);
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc3) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        //팝업
        Button buttonn = findViewById(R.id.popup);
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });

        //유저정보 조회
        DatabaseReference dbRef = database.getReference("user_list");
        tv_userList = findViewById(R.id.name);
        loadFromFirebase(dbRef);



        //테스트용
        /*
        Button makekey = findViewById(R.id.makeKey);
        makekey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AergoCommon.createAergoKey();
            }
        });
        Button getBlances = findViewById(R.id.getBlances);
        getBlances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleMain.getBlances();
            }
        });
        Button sendTransaction = findViewById(R.id.sendTransaction);
        sendTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleMain.sendTransaction();
            }
        });
        */


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
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
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

                }
                tv_userList.setText(userList.get(1).getName());
                Wallet wallet = AergoCommon.getAergoWallet("testnet.aergo.io:7845");
                AergoQuery.getBlance(wallet, userList.get(1).getAddress());

                wallet.close();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
            }
        });
    }
}
