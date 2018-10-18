package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.Activities.FineRentBillActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class RentCollectionPaidDetailList extends RecyclerView.Adapter<RentCollectionPaidDetailList.MyHolder>{

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";

    List<TenantDetails> tenantDetails;
    List<TenantDetails> tenantPaidDetails;
    Context context;

    public RentCollectionPaidDetailList(List<TenantDetails> tenantDetails, List<TenantDetails> tenantPaidDetails, Context context) {
        this.tenantDetails = tenantDetails;
        this.tenantPaidDetails = tenantPaidDetails;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tenantPaidDetails.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rent_bill_collection_rent_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.rentAmountTextView.setText(tenantPaidDetails.get(position).rentAmount);
        holder.tenantRoomTextView.setText(tenantPaidDetails.get(position).room);
        holder.tenantNameTextView.setText(tenantPaidDetails.get(position).name);

        holder.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + tenantPaidDetails.get(position).phone));
                context.startActivity(callIntent);
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                msg91.composeMessage("EazyPG", "Hi " + tenantPaidDetails.get(position).name + ". Your rent is due for this month.");
                msg91.to(tenantPaidDetails.get(position).phone);
                String sendStatus = msg91.send();
            }
        });

        holder.addFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FineRentBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, tenantPaidDetails.get(position).id);
                intent.putExtra(EXTRA_MESSAGE2, tenantPaidDetails.get(position).room);
                context.startActivity(intent);
            }
        });
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tenantNameTextView, tenantRoomTextView, rentAmountTextView;
        public Button messageButton, phoneButton, addFineButton;

        public MyHolder(View itemView){
            super(itemView);

            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantRoomTextView = itemView.findViewById(R.id.roomNumberTextView);
            rentAmountTextView = itemView.findViewById(R.id.rentAmountTextView);

            messageButton = itemView.findViewById(R.id.messageButton);
            phoneButton = itemView.findViewById(R.id.callButton);
            addFineButton = itemView.findViewById(R.id.fineButton);
        }
    }
}
