package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminMain extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<User> userList;
    private TextView tv_userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // 임의의 사용자 계정 삽입. MD5 hash 이용
        DatabaseReference dbRef = database.getReference("user_list");
//        dbRef.child("minho").child("account").setValue("7F9C111AB01376209BA843B61A942863");
//        dbRef.child("jiwoo").child("account").setValue("CE014A4B35CC8874B7F4AAD778C3AB8D");
//        dbRef.child("sangyoon").child("account").setValue("822F967FB5251E40153DE461DB7401A9");
//        dbRef.child("myeongjin").child("account").setValue("D71810CD8B54E8E787ED0C61C4718409");

        // Firebase에서 UserList 가져오기
        tv_userList = findViewById(R.id.tv_userList);
        final DatabaseReference childRef = database.getReference("user_list");
        loadFromFirebase(childRef);
    }

    // Firebase에서 데이터를 읽어서 recycle_userListView를 새로고침.
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
                    ul[0] = ul[0].concat(tmpUser.toString() + "\n");
                }
                tv_userList.setText(ul[0]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Read Firebase database", "Failed to read value.", error.toException());
            }
        });
    }

}
