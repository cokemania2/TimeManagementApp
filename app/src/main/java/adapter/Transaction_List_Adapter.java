package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.math.BigInteger;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import hera.api.model.Transaction;

public class Transaction_List_Adapter extends RecyclerView.Adapter<Transaction_List_Adapter.ViewHolder>{

    Context context;
    private ArrayList<Transaction> txList;

    public Transaction_List_Adapter(Context context, ArrayList<Transaction> txList) {
        this.context = context;
        this.txList = txList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tx_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tx_hash.setText(txList.get(position).getHash().toString());
        holder.sender.setText(txList.get(position).getSender().toString());
        holder.receiver.setText(txList.get(position).getRecipient().toString());
        BigInteger bi = txList.get(position).getAmount().getValue();
        holder.amount.setText(String.format("%,d", bi.divide(new BigInteger("1000000000"))));
    }

    @Override
    public int getItemCount() {

        Log.d("TEST user list size", String.valueOf(txList.size()));
        return txList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tx_hash, sender, receiver, amount;

        public ViewHolder(View itemView) {
            super(itemView);

            tx_hash = itemView.findViewById(R.id.tv_txHash);
            sender  = itemView.findViewById(R.id.tv_sender);
            receiver = itemView.findViewById(R.id.tv_receiver);
            amount  = itemView.findViewById(R.id.tv_amount);

            // 아이템 클릭 이벤트 처리.
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition() ;
//                    if (pos != RecyclerView.NO_POSITION) {
//                        Transaction item = txList.get(pos) ;
//                        Intent gotoUserInfo = new Intent(context, UserInfoActivity.class);
//
//                        gotoUserInfo.putExtra("tx_hash", item.getHash().toString());
//                        gotoUserInfo.putExtra("sender", item.getSender().toString());
//                        gotoUserInfo.putExtra("receiver", item.getRecipient().toString());
//                        gotoUserInfo.putExtra("amount", item.getAmount().toString());
//                        context.startActivity(gotoUserInfo);
//                    }
//                }
//            });
        }
    }
}
