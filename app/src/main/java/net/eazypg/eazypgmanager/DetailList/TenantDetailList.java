package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.Activities.FineActivity;
import net.eazypg.eazypgmanager.Activities.PaymentActivity;
import net.eazypg.eazypgmanager.Activities.TenantDashboardFragmentActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

public class TenantDetailList extends ArrayAdapter<TenantDetails> {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText input;

    private Activity context;
    private List<TenantDetails> tenantList;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "com.example.myfirstapp.MESSAGE5";

    public String id;
    private Button callButton, paymentButton;
    private Button fineButton, messageButton;
    private TextView rentAmount;

    public TenantDetailList(Activity context, List<TenantDetails> tenantList) {
        super(context, R.layout.tenant_row, tenantList);

        this.context = context;
        this.tenantList = tenantList;
    }

    List<String> ids = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();

        View listViewItemTenant = inflater.inflate(R.layout.tenant_row, null, true);

        callButton = listViewItemTenant.findViewById(R.id.callButton);
        paymentButton = listViewItemTenant.findViewById(R.id.paymentButton);
        fineButton = listViewItemTenant.findViewById(R.id.fineButton);
        messageButton = listViewItemTenant.findViewById(R.id.messageButton);
        rentAmount = listViewItemTenant.findViewById(R.id.rentAmount);

        input = new EditText(context);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final TenantDetails tenantDetails = tenantList.get(position);

        rentAmount.setText(tenantDetails.rentAmount);

        databaseReference = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Tenants/CurrentTenants");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TenantDetails tenantDetails1 = snapshot.getValue(TenantDetails.class);
                    id = tenantDetails1.id;
                    ids.add(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TenantDashboardFragmentActivity.class);
                intent.putExtra(EXTRA_MESSAGE3, ids.get(position));
                intent.putExtra(EXTRA_MESSAGE4, tenantDetails.getRoom());
                intent.putExtra(EXTRA_MESSAGE5, tenantDetails.getName());
                context.startActivity(intent);

            }
        });

        final TextView first = listViewItemTenant.findViewById(R.id.firstTextView);
        TextView second = listViewItemTenant.findViewById(R.id.secondTextView);
        final TextView third = listViewItemTenant.findViewById(R.id.thirdTextView);

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra(EXTRA_MESSAGE, ids.get(position));
                context.startActivity(intent);
            }
        });


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + third.getText().toString()));
                    context.startActivity(callIntent);

                }
                catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call failed!", Toast.LENGTH_SHORT).show();
                }
                catch (SecurityException e) {
                    Toast.makeText(context, "Call failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogFine);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String fine = fineEditText.getText().toString();
                        String fineId = databaseReference.push().getKey();

                        databaseReference = firebaseDatabase.getReference("Tenants/" + tenantDetails.id + "/");
                        databaseReference.child("Accounts").child("Fine").child(fineId).setValue(fine);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();*/
                Intent intent = new Intent(context, FineActivity.class);
                intent.putExtra(EXTRA_MESSAGE, tenantDetails.id);
                intent.putExtra(EXTRA_MESSAGE2, tenantDetails.room);
                context.startActivity(intent);
                context.finish();
            }
        });

        /*billButton.setOnClickListener(new View.OnClickListener() {
            @Override
            *//*public void onClick(View view) {
                *//**//*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogBill);
                builder.setTitle("Enter Bill");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        billAmount = billAmountEditText.getText().toString();
                        billCategory = billCategoryEditText.getText().toString();

                        Toast.makeText(context,billAmount + " " + billCategory, Toast.LENGTH_SHORT).show();

                        String billId = databaseReference.push().getKey();
                        BillDetails billDetails = new BillDetails(billId, billCategory, billAmount, false);

                        databaseReference = firebaseDatabase.getReference("Tenants/" + tenantDetails.id + "/");
                        databaseReference.child("Accounts").child("Bills").child(billId).setValue(billDetails);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();*//**//*
                Intent intent = new Intent(context, AddBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, tenantDetails.id);
                intent.putExtra(EXTRA_MESSAGE2, tenantDetails.room);
                context.startActivity(intent);
            }*//*
        });
*/
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText inputMessageEditText = new EditText(context);
                builder.setView(inputMessageEditText);
                inputMessageEditText.setText("Hi " + tenantList.get(position).name + ". ");
                builder.setTitle("Send Message");
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = inputMessageEditText.getText().toString();
                        msg91.composeMessage("EazyPG", message);
                        msg91.to(tenantList.get(position).phone);
                        String sendStatus = msg91.send();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        first.setText(tenantDetails.getName());
        second.setText(tenantDetails.getRoom());
        third.setText(tenantDetails.getPhone());

        return listViewItemTenant;

    }
}
