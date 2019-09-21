package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserInfoActivity;
import com.google.android.gms.actions.ItemListIntents;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by wolfsoft3 on 30/8/18.
 */

public class Item_Home_Adapter extends RecyclerView.Adapter<Item_Home_Adapter.ViewHolder> {

    Context context;
    private ArrayList<User> userList;

    public Item_Home_Adapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getName());
        holder.account.setText(userList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {

        Log.d("TEST user list size", String.valueOf(userList.size()));
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, account;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            account  = itemView.findViewById(R.id.user_account);

            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        User item = userList.get(pos) ;
                        Intent gotoUserInfo = new Intent(context, UserInfoActivity.class);
                        gotoUserInfo.putExtra("userName", item.getName());
                        context.startActivity(gotoUserInfo);
                    }
                }
            });
        }
    }
}
