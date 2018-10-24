package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class IncomePassbookDetailList extends RecyclerView.Adapter<IncomePassbookDetailList.MyHolder>{

    List<CashflowDetails> incomePassbookDetailList;
    Context context;

    public IncomePassbookDetailList(List<CashflowDetails> incomePassbookDetailList, Context context) {
        this.incomePassbookDetailList = incomePassbookDetailList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return incomePassbookDetailList.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passbook_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        CashflowDetails cashflowDetails = incomePassbookDetailList.get(position);

        holder.amountTextView.setText(cashflowDetails.amount);
        holder.categoryTextView.setText(cashflowDetails.category);
        holder.timestampTextView.setText(cashflowDetails.date);
        holder.paidTextView.setText(cashflowDetails.paidBy);

        holder.billImageView.setVisibility(View.GONE);
        holder.paidToTextView.setVisibility(View.GONE);

        holder.plusMinusImageView.setVisibility(View.GONE);
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView amountTextView, timestampTextView, categoryTextView, paidTextView, paidToTextView;
        public ImageView plusMinusImageView, billImageView;

        public MyHolder(View itemView){
            super(itemView);

            amountTextView = itemView.findViewById(R.id.amountTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            paidTextView = itemView.findViewById(R.id.paidForTextView);
            paidToTextView = itemView.findViewById(R.id.paidToTextView);
            plusMinusImageView = itemView.findViewById(R.id.plusMinusImage);
            billImageView = itemView.findViewById(R.id.billImageView);
        }
    }
}
