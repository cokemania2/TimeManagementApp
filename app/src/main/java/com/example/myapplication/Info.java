package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.Item_Home_Adapter;
import adapter.Item_tx_Adapter;
import aergo.hacker_edu.AergoCommon;
import aergo.hacker_edu.AergoQuery;
import hera.wallet.Wallet;

public class Info extends AppCompatActivity {
    private static final String NULL ="";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<User> userList;
    ProgressDialog dialog;
    TextView tv_userList;
    TextView point;
    TextView txlist;
    String txString = "";
    String balance="";
    private Item_tx_Adapter item_tx_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        //ProgressBar

        dialog = ProgressDialog.show(Info.this, "",
                "Loading. Please wait...", true);



        //유저정보 조회
        DatabaseReference dbRef = database.getReference("user_list");
        tv_userList = findViewById(R.id.user_name);
        point = findViewById(R.id.point);

//        loadFromFirebase(dbRef);

        setProgressBarIndeterminateVisibility(true);
    }

//    void loadFromFirebase(final DatabaseReference ref) {
//        // 해당 DB참조의 값변화리스너 추가
//        final String[] ul = new String[1];
//        ul[0] = "";
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                userList = new ArrayList<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User tmpUser;
//                    tmpUser= snapshot.getValue(User.class);
//
//                    Log.d("FirebaseTestActivity", "ValueEventListener : " + tmpUser);
//                    userList.add(tmpUser);
//                    Log.d("userList : ",userList.toString());
//                    ul[0] = ul[0].concat(tmpUser.toString() + "\n");
//                }
//
//                //userList.indexOf(jiwoo);
//                tv_userList.setText(userList.get(1).getName());
//                Wallet wallet = AergoCommon.getAergoWallet("testnet.aergo.io:7845");
//                balance = AergoQuery.getBalance_(wallet,userList.get(1).getAddress());
//                wallet.close();
//                Log.d("balance : ", balance);
//                point.setText(balance);
//                dialog.dismiss();
//                recyclerView = (RecyclerView) findViewById(R.id.txlist);
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Info.this);
//                recyclerView.setLayoutManager(layoutManager);
//
//                item_tx_adapter = new Item_tx_Adapter(Info.this, userList.get(1).getTxList());
//                recyclerView.setAdapter(item_tx_adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("Read Firebase database", "Failed to read value.", error.toException());
//            }
//        });

//    }
}
