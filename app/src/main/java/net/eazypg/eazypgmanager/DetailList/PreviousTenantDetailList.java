package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class PreviousTenantDetailList extends ArrayAdapter<TenantDetails> {

    private Activity context;
    private List<TenantDetails> tenantList;

    public String id;

    FloatingActionButton callButton;

    public PreviousTenantDetailList(Activity context, List<TenantDetails> tenantList) {
        super(context, R.layout.previous_tenant_row, tenantList);

        this.context = context;
        this.tenantList = tenantList;
    }

    DatabaseReference databaseReference;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemTenant = inflater.inflate(R.layout.previous_tenant_row, null, true);

        callButton = listViewItemTenant.findViewById(R.id.callButton);

        final TenantDetails tenantDetails = tenantList.get(position);

        databaseReference = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Tenants/CurrentTenants");

        listViewItemTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View viewDialog = inflater.inflate(R.layout.dialog_previous_tenant, null);

                final EditText name, phone, dateOfJoining, rentAmount;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tenant details");

                name = viewDialog.findViewById(R.id.tenantNameEditText);
                phone = viewDialog.findViewById(R.id.tenantPhoneEditText);
                dateOfJoining = viewDialog.findViewById(R.id.tenantDateEditText);
                rentAmount = viewDialog.findViewById(R.id.tenantRentEditText);

                name.setText(tenantDetails.name);
                phone.setText(tenantDetails.phone);
                dateOfJoining.setText(tenantDetails.dateOfJoining);
                rentAmount.setText(tenantDetails.rentAmount);

                builder.setView(viewDialog);

                builder.setPositiveButton("OK", null);
                builder.show();

            }
        });

        TextView first = listViewItemTenant.findViewById(R.id.firstTextView);
        final TextView third = listViewItemTenant.findViewById(R.id.thirdTextView);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + third.getText().toString()));
                    context.startActivity(callIntent);

                }
                catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call failed", Toast.LENGTH_SHORT).show();
                }
                catch (SecurityException e) {
                    Toast.makeText(context, "Call failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        first.setText(tenantDetails.getName());
        third.setText(tenantDetails.getPhone());

        return listViewItemTenant;

    }
}
