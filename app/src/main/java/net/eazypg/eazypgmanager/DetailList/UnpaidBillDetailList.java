package net.eazypg.eazypgmanager.DetailList;

import android.app.AlertDialog;
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

import static net.eazypg.eazypgmanager.DetailList.RentCollectionPaidDetailList.EXTRA_MESSAGE;
import static net.eazypg.eazypgmanager.DetailList.RentCollectionPaidDetailList.EXTRA_MESSAGE2;

public class UnpaidBillDetailList extends RecyclerView.Adapter<UnpaidBillDetailList.MyHolder>{

    List<TenantDetails> tenantUnpaidDetails;
    List<String> unpaidAmount;
    Context context;

    public UnpaidBillDetailList(List<TenantDetails> tenantUnpaidDetails, List<String> unpaidAmount, Context context) {

        this.tenantUnpaidDetails = tenantUnpaidDetails;
        this.unpaidAmount = unpaidAmount;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rent_bill_collection_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.tenantRoomTextView.setText(tenantUnpaidDetails.get(position).room);
        holder.tenantNameTextView.setText(tenantUnpaidDetails.get(position).name);

        holder.rentAmountTextView.setText(unpaidAmount.get(position));

        holder.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + tenantUnpaidDetails.get(position).phone));
                context.startActivity(callIntent);
            }
        });

        holder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                msg91.composeMessage("EazyPG", "Hi " + tenantUnpaidDetails.get(position).name + ". Your bill is due for this month.");
                msg91.to(tenantUnpaidDetails.get(position).phone);
                String sendStatus = msg91.send();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Message Sent");
                builder.setMessage("Rent due message is sent to " + tenantUnpaidDetails.get(position).name + ".");
                builder.setNeutralButton("Ok", null);
                builder.show();
            }
        });

        holder.addFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FineRentBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, tenantUnpaidDetails.get(position).id);
                intent.putExtra(EXTRA_MESSAGE2, tenantUnpaidDetails.get(position).room);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tenantUnpaidDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tenantNameTextView, tenantRoomTextView, rentAmountTextView, textView7;
        public Button messageButton, phoneButton, addFineButton;

        public MyHolder(View itemView){
            super(itemView);

            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantRoomTextView = itemView.findViewById(R.id.roomNumberTextView);
            rentAmountTextView = itemView.findViewById(R.id.rentAmountTextView);
            textView7 = itemView.findViewById(R.id.textView7);

            messageButton = itemView.findViewById(R.id.messageButton);
            phoneButton = itemView.findViewById(R.id.callButton);
            addFineButton = itemView.findViewById(R.id.fineButton);
        }
    }
}
