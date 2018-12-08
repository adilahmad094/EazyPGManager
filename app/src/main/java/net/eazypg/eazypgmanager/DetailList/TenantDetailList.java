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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
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

import net.eazypg.eazypgmanager.Activities.PaymentActivity;
import net.eazypg.eazypgmanager.Activities.TenantDashboardFragmentActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

public class TenantDetailList extends ArrayAdapter<TenantDetails> implements Filterable {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText input;

    private Activity context;
    private List<TenantDetails> tenantList;
    private List<TenantDetails> displayTenants;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";
    public static final String EXTRA_MESSAGE5 = "com.example.myfirstapp.MESSAGE5";

    public String id;
    private LinearLayout callButton, paymentButton;
    private LinearLayout messageButton;
    private TextView rentAmount;

    public TenantDetailList(Activity context, List<TenantDetails> tenantList) {
        super(context, R.layout.tenant_row, tenantList);

        this.context = context;
        this.tenantList = tenantList;
        this.displayTenants = tenantList;
    }

    List<String> ids = new ArrayList<>();

    @Override
    public int getCount() {

        return displayTenants.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();

        View listViewItemTenant = inflater.inflate(R.layout.tenant_row, null, true);

        callButton = listViewItemTenant.findViewById(R.id.callLL);
        paymentButton = listViewItemTenant.findViewById(R.id.paymentLL);
        messageButton = listViewItemTenant.findViewById(R.id.textLL);
        rentAmount = listViewItemTenant.findViewById(R.id.rentAmount);

        input = new EditText(context);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final TenantDetails tenantDetails = displayTenants.get(position);

        rentAmount.setText(tenantDetails.rentAmount);

        listViewItemTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TenantDashboardFragmentActivity.class);
                intent.putExtra(EXTRA_MESSAGE3, tenantDetails.getId());
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
                intent.putExtra(EXTRA_MESSAGE, tenantDetails.getId());
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

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText inputMessageEditText = new EditText(context);
                builder.setView(inputMessageEditText);
                inputMessageEditText.setText("Hi " + displayTenants.get(position).name + ". ");
                builder.setTitle("Send Message");
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = inputMessageEditText.getText().toString();
                        msg91.composeMessage("EazyPG", message);
                        msg91.to(displayTenants.get(position).phone);
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

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<TenantDetails> FilteredArrList = new ArrayList<>();

                if (tenantList == null) {
                    tenantList = new ArrayList<>(displayTenants); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = tenantList.size();
                    results.values = tenantList;

                } else {

                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < tenantList.size(); i++) {
                        String data = tenantList.get(i).name;
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(new TenantDetails(tenantList.get(i).name,tenantList.get(i).phone, tenantList.get(i).room, tenantList.get(i).dateOfJoining, tenantList.get(i).rentAmount, tenantList.get(i).pgId, tenantList.get(i).id));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                displayTenants = (ArrayList<TenantDetails>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
