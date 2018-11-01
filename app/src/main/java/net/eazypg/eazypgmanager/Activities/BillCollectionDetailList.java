package net.eazypg.eazypgmanager.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.DetailList.RentCollectionPaidDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.BillDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class BillCollectionDetailList extends RecyclerView.Adapter<BillCollectionDetailList.MyHolder>{

    List<BillDetails> electricityBillDetails;
    List<BillDetails> wifiBillDetails;
    List<BillDetails> gasBillDetails;
    List<BillDetails> otherBillDetails;
    List<TenantDetails> tenantDetailList;
    Context context;

    public BillCollectionDetailList(List<BillDetails> electricityBillDetails, List<BillDetails> wifiBillDetails, List<BillDetails> gasBillDetails, List<BillDetails> otherBillDetails, List<TenantDetails> tenantDetailList, Context context) {
        this.electricityBillDetails = electricityBillDetails;
        this.wifiBillDetails = wifiBillDetails;
        this.gasBillDetails = gasBillDetails;
        this.otherBillDetails = otherBillDetails;
        this.tenantDetailList = tenantDetailList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return electricityBillDetails.size() + wifiBillDetails.size() + gasBillDetails.size() + otherBillDetails.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rent_bill_collection_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.rentAmountTextView.setText(tenantDetailList.get(holder.getAdapterPosition()).rentAmount);
        holder.tenantRoomTextView.setText(tenantDetailList.get(holder.getAdapterPosition()).room);
        holder.tenantNameTextView.setText(tenantDetailList.get(holder.getAdapterPosition()).name);

        holder.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + tenantDetailList.get(holder.getAdapterPosition()).phone));
                context.startActivity(callIntent);
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText inputMessageEditText = new EditText(context);
                builder.setView(inputMessageEditText);
                inputMessageEditText.setText("Hi " + tenantDetailList.get(holder.getAdapterPosition()).name + ". Your rent is due for this month.");
                builder.setTitle("Send Message");
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = inputMessageEditText.getText().toString();
                        msg91.composeMessage("EazyPG", message);
                        msg91.to(tenantDetailList.get(holder.getAdapterPosition()).phone);
                        String sendStatus = msg91.send();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        holder.addFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FineRentBillActivity.class);
                intent.putExtra(RentCollectionPaidDetailList.EXTRA_MESSAGE, tenantDetailList.get(holder.getAdapterPosition()).id);
                intent.putExtra(RentCollectionPaidDetailList.EXTRA_MESSAGE2, tenantDetailList.get(holder.getAdapterPosition()).room);
                context.startActivity(intent);
            }
        });
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tenantNameTextView, tenantRoomTextView, rentAmountTextView;
        public LinearLayout messageButton, phoneButton, addFineButton;

        public MyHolder(View itemView){
            super(itemView);

            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantRoomTextView = itemView.findViewById(R.id.roomNumberTextView);
            rentAmountTextView = itemView.findViewById(R.id.rentAmountTextView);
            messageButton = itemView.findViewById(R.id.textLinearLayout);
            phoneButton = itemView.findViewById(R.id.callLinearLayout);
            addFineButton = itemView.findViewById(R.id.fineLinearLayout);
        }
    }
}
