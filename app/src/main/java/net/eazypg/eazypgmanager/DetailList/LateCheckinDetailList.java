package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.eazypg.eazypgmanager.DetailsClasses.LateCheckInDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class LateCheckinDetailList extends RecyclerView.Adapter<LateCheckinDetailList.MyHolder> {

    List<LateCheckInDetails> lateCheckInDetailsList;
    Context context;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.late_checkin_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return lateCheckInDetailsList.size();
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        final LateCheckInDetails lateCheckInDetails = lateCheckInDetailsList.get(position);

        holder.returnTimeTextView.setText(lateCheckInDetails.returnTime);
        holder.reasonTextView.setText(lateCheckInDetails.reason);
        //holder.tenantContactTextView.setText(lateCheckInDetails.tenantPhone);
        holder.tenantNameTextView.setText(lateCheckInDetails.tenantName);
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lateCheckInDetails.tenantPhone));
                context.startActivity(intent);
            }
        });
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView returnTimeTextView;
        public TextView reasonTextView;
        public TextView tenantNameTextView;
        public TextView tenantContactTextView;
        public FloatingActionButton callButton;

        public MyHolder(View itemView) {
            super(itemView);

            returnTimeTextView = itemView.findViewById(R.id.returnTimeTextView);
            reasonTextView = itemView.findViewById(R.id.reasonTextView);
            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantContactTextView = itemView.findViewById(R.id.tenantContactTextView);
            callButton = itemView.findViewById(R.id.callButton);
        }
    }

    public LateCheckinDetailList(List<LateCheckInDetails> lateCheckInDetailsList, Context context) {
        this.lateCheckInDetailsList = lateCheckInDetailsList;
        this.context = context;
    }


}
