package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import aergo.hacker_edu.AergoCommon;
import aergo.hacker_edu.AergoQuery;
import aergo.hacker_edu.SampleMain;
import hera.api.model.Aer;
import hera.api.model.Transaction;
import hera.wallet.Wallet;

public class UserInfoActivity extends AppCompatActivity {
    TextView tv_userAccount;
    TextView tv_userBalance;
    TextView tv_userTx;
    TextView tv_userName;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // 타이틀
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("test") ;
        ab.setIcon(R.drawable.gucc) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        tv_userTx = findViewById(R.id.tv_userTx);
        tv_userBalance = findViewById(R.id.tv_userBalance);
        tv_userAccount = findViewById(R.id.tv_userAccount);
        tv_userName = findViewById(R.id.tv_userName);

        // Intent로 계정 정보 넘어오고
        Intent intent = getIntent(); /*데이터 수신*/
        String userName = intent.getExtras().getString("userName");
        tv_userName.setText(userName);
        // 그걸 바탕으로 파이어베이스 검색
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("user_list/" + userName);
        Log.d("TEST dbRef path", dbRef.getPath().toString());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User tmpUser;
                tmpUser = dataSnapshot.getValue(User.class);

                Log.d("TEST user", "tmpUser: " + tmpUser.toString());
                String userAccount = tmpUser.getAddress();
                Log.d("TEST userAccount", "value is : " + userAccount);
                tv_userAccount.setText(userAccount);

                Wallet wallet = AergoCommon.getAergoWallet("testnet.aergo.io:7845");
                String balance = AergoQuery.getBalance_(wallet, userAccount);
                Log.d("TEST balance", "value is : " + balance);
                tv_userBalance.setText(balance);

                ArrayList<String> txList = new ArrayList<>();
                String text = "Transaction List\n";

                // tx hash list 가져오기. txList가 없으면 arrayList에 추가 안 함.
                if (tmpUser.getTxList() != null) {
                    for(String tx : tmpUser.getTxList()) {
                        text = text.concat(tx + "\n");
                        txList.add(tx);
                    }
                }

                Log.d("TEST tx", "value is : " + text);

                ArrayList<String> txInfo = new ArrayList<>();
                text = "";
                for(String txHash : txList) {
                    Transaction transactionInfo = AergoQuery.getTransactionInfo(wallet, txHash);
                    txInfo.add(txToString(transactionInfo));
                    text = text.concat(txToString(transactionInfo));
                }
                tv_userTx.setText(text);

                // wallet 종료
                wallet.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    String txToString(Transaction transactionInfo) {
        String info = "";
        info = info.concat(">> 송신자 주소:: "+transactionInfo.getSender().toString() + "\n");
        info = info.concat(">> 수신자 주소:: "+transactionInfo.getRecipient().toString() + "\n");
        info = info.concat(">> 전송 토큰량:: "+transactionInfo.getAmount().getValue().toString() + "\n");
        info = info.concat(">> TransactionHash :: "+transactionInfo.getHash().toString() + "\n");
        info = info.concat(">> blockhash:: "+transactionInfo.getBlockHash().toString() + "\n");
        info = info.concat(">> payload:: "+new String(transactionInfo.getPayload().getValue()) + "\n");

        return info;
    }
}
