package net.eazypg.eazypgmanager.DetailList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.Activities.TenantActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.DetailsClasses.UnderProcessTenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;


public class UnderprocessDetailList extends RecyclerView.Adapter<UnderprocessDetailList.MyHolder> {

    List<UnderProcessTenantDetails> underprocessTenantsList;
    Context context;
    String pgId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public UnderprocessDetailList(List<UnderProcessTenantDetails> underprocessTenantsList, Context context, String pgId) {
        this.underprocessTenantsList = underprocessTenantsList;
        this.context = context;
        this.pgId = pgId;

        firebaseDatabase = FirebaseDatabase.getInstance();
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

        holder.underProcessConstraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Under Process Tenant");
                builder.setMessage("Are you sure you want to delete " + holder.tenantNameTextView.getText().toString() + " from the list?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference = firebaseDatabase.getReference("PG/" + pgId + "/Tenants/UnderProcess/");
                        databaseReference.child(holder.contactNumberTextView.getText().toString()).setValue(null);

                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();

                return false;
            }
        });

        holder.contactNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Make call");
                builder.setMessage("Call " + holder.tenantNameTextView.getText().toString() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + holder.contactNumberTextView.getText().toString()));
                        context.startActivity(callIntent);

                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return underprocessTenantsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView tenantNameTextView, tenantRoomTextView, contactNumberTextView;
        public ConstraintLayout inviteTenantConstraintLayout, underProcessConstraintLayout;

        public MyHolder(View itemView){
            super(itemView);

            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantRoomTextView = itemView.findViewById(R.id.roomNoTextView);
            contactNumberTextView = itemView.findViewById(R.id.contactTenantTextView);
            inviteTenantConstraintLayout = itemView.findViewById(R.id.inviteTenantConstraintLayout);
            underProcessConstraintLayout = itemView.findViewById(R.id.underProcessConstraintLayout);
        }
    }
}
