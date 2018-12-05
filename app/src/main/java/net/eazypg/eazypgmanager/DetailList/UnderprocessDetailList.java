package net.eazypg.eazypgmanager.DetailList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.Activities.TenantActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.DetailsClasses.UnderProcessTenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;


public class UnderprocessDetailList extends RecyclerView.Adapter<UnderprocessDetailList.MyHolder> {

    List<UnderProcessTenantDetails> underprocessTenantsList;
    Context context;


    public UnderprocessDetailList(List<UnderProcessTenantDetails> underprocessTenantsList, Context context) {
        this.underprocessTenantsList = underprocessTenantsList;
        this.context = context;
    }

    @Override
    public UnderprocessDetailList.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.under_process_tenant_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UnderprocessDetailList.MyHolder holder, final int position) {

        holder.tenantNameTextView.setText(underprocessTenantsList.get(position).name);
        holder.tenantRoomTextView.setText(underprocessTenantsList.get(position).room);
        holder.contactNumberTextView.setText(underprocessTenantsList.get(position).phone);

        holder.inviteTenantConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tenant Invited");
                builder.setMessage("An invitation message with link and details has been sent to " + holder.tenantNameTextView.getText().toString() + ".");
                builder.setNeutralButton("Ok", null);
                builder.show();

                //ToDo: Firebase Dynamic link will be sent to tenant using MSG91

                MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                msg91.composeMessage("EazyPG", "Hi " + holder.tenantNameTextView.getText().toString() +  "Get your EazyPG App now https://goo.gl/M3jEhQ");
                msg91.to(holder.contactNumberTextView.getText().toString());
                String sendStatus = msg91.send();
            }
        });

    }

    @Override
    public int getItemCount() {
        return underprocessTenantsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView tenantNameTextView, tenantRoomTextView, contactNumberTextView;
        public ConstraintLayout inviteTenantConstraintLayout;

        public MyHolder(View itemView){
            super(itemView);

            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantRoomTextView = itemView.findViewById(R.id.roomNoTextView);
            contactNumberTextView = itemView.findViewById(R.id.contactTenantTextView);
            inviteTenantConstraintLayout = itemView.findViewById(R.id.inviteTenantConstraintLayout);
        }
    }

}
