package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Info;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserInfoActivity;

import java.util.ArrayList;

public class Item_tx_Adapter extends RecyclerView.Adapter<Item_tx_Adapter.txViewHolder>{

    Context context;
    ArrayList<String> txlist;

    public Item_tx_Adapter(Context context, ArrayList<String> txlist) {
        this.context = context;
        this.txlist = txlist;
    }

    @NonNull
    @Override
    public txViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tx_list, parent, false);
        return new txViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull txViewHolder holder, int position) {
        holder.tx.setText(txlist.get(position));
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class txViewHolder extends RecyclerView.ViewHolder {

        TextView tx, account;

        public txViewHolder(View itemView) {
            super(itemView);

            tx = itemView.findViewById(R.id.tx);


            /*
            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition() ;
                        if (pos != RecyclerView.NO_POSITION) {
                            String item = txlist.get(pos) ;
                            Intent gotoUserInfo = new Intent(context, Info.class);
                            gotoUserInfo.putExtra("txName", item);
                            context.startActivity(gotoUserInfo);
                        }
                    }
                });
            }
         */
        }
    }
}
