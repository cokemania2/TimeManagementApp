package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserInfoActivity;
import com.google.android.gms.actions.ItemListIntents;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by wolfsoft3 on 30/8/18.
 */

public class Item_Home_Adapter extends RecyclerView.Adapter<Item_Home_Adapter.ViewHolder> {

    Context context;
    private ArrayList<User> userList;
    String userName;


    public Item_Home_Adapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getName());
        holder.account.setText(userList.get(position).getAddress());

        userName = userList.get(position).getName();
        GradientDrawable drawable=
                (GradientDrawable) context.getDrawable(R.drawable.background_rounding);

        if (userName.equals("jiwoo")) {
            holder.image.setImageResource(R.drawable.jiwoo);
        }
        else if (userName.equals("myeongjin")) {
            holder.image.setImageResource(R.drawable.myongjin);
        }
        else if (userName.equals("minho")) {
            holder.image.setImageResource(R.drawable.minho);
        }
        else if (userName.equals("sangyoon")) {
            holder.image.setImageResource(R.drawable.sangyoon);
        }
        else if (userName.equals("admin")) {
            holder.image.setImageResource(R.drawable.gucc);
        }
        holder.image.setBackground(new ShapeDrawable(new OvalShape()));
        holder.image.setClipToOutline(true);

    }

    @Override
    public int getItemCount() {

        Log.d("TEST user list size", String.valueOf(userList.size()));
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, account;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            account  = itemView.findViewById(R.id.user_account);
            image = itemView.findViewById(R.id.imagee);

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
