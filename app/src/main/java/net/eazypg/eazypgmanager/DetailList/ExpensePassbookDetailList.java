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

public class ExpensePassbookDetailList extends RecyclerView.Adapter<ExpensePassbookDetailList.MyHolder>{

    List<CashflowDetails> expensesPassbookDetailList;
    Context context;

    public ExpensePassbookDetailList(List<CashflowDetails> expensesPassbookDetailList, Context context) {
        this.expensesPassbookDetailList = expensesPassbookDetailList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return expensesPassbookDetailList.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passbook_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        CashflowDetails cashflowDetails = expensesPassbookDetailList.get(position);

        holder.amountTextView.setText(cashflowDetails.amount);
        holder.categoryTextView.setText(cashflowDetails.category);
        holder.timestampTextView.setText(cashflowDetails.date);
        holder.paidTextView.setText(cashflowDetails.paidBy);

        holder.plusMinusImage.setVisibility(View.GONE);

    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView amountTextView, timestampTextView, categoryTextView, paidTextView;
        public ImageView plusMinusImage;

        public MyHolder(View itemView){
            super(itemView);

            amountTextView = itemView.findViewById(R.id.amountTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            paidTextView = itemView.findViewById(R.id.paidForTextView);

            plusMinusImage = itemView.findViewById(R.id.plusMinusImage);
        }
    }
}
