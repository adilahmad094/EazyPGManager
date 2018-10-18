package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddCreditDetailList extends RecyclerView.Adapter<AddCreditDetailList.MyHolder>{

    List<TenantDetails> tenantDetailsList;
    Context context;

    public AddCreditDetailList(List<TenantDetails> tenantDetailsList, Context context) {
        this.tenantDetailsList = tenantDetailsList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tenantDetailsList.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tenant_credit_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.nameTextView.setText(tenantDetailsList.get(position).name);
        holder.roomNumberTextView.setText(tenantDetailsList.get(position).room);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        holder.saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String amount = holder.creditsEditText.getText().toString();

                String creditsId;

                CashflowDetails cashflowDetails;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateString = dateFormat.format(date);

                DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + tenantDetailsList.get(position).room + "/Tenant/CurrentTenants/" + tenantDetailsList.get(position).id);
                creditsId = databaseReference1.push().getKey();

                cashflowDetails = new CashflowDetails(creditsId, amount, "Credits", "", "", "", dateString, true);
                /*databaseReference1.child("Accounts").child("Credits").child(creditsId).setValue(cashflowDetails);

                DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantDetailsList.get(position).id);

                cashflowDetails = new CashflowDetails(creditsId, amount, "Credits", "", "", "", dateString, true);
                databaseReference2.child("Accounts").child("Credits").child(creditsId).setValue(cashflowDetails);

                DatabaseReference databaseReference3 = firebaseDatabase.getReference("Tenants/" + tenantDetailsList.get(position).id);

                cashflowDetails = new CashflowDetails(creditsId, amount, "Credits", "", "", "", dateString, true);
                databaseReference3.child("Accounts").child("Credits").child(creditsId).setValue(cashflowDetails);*/

                final DatabaseReference databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + tenantDetailsList.get(position).room + "/Tenant/CurrentTenants/" + tenantDetailsList.get(position).id);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String prevCredits = dataSnapshot.child("Credits").getValue(String.class);

                        String credits = amount;

                        if(prevCredits != null){
                            credits = Integer.toString(Integer.parseInt(prevCredits) + Integer.parseInt(amount));
                        }

                        databaseReference.child("Credits").setValue(credits);

                        DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantDetailsList.get(position).id);
                        databaseReference2.child("Credits").setValue(credits);

                        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Tenants/" + tenantDetailsList.get(position).id);
                        databaseReference3.child("credits").setValue(credits);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference databaseReference4 = firebaseDatabase.getReference("Tenants/" + tenantDetailsList.get(position).id);

                cashflowDetails = new CashflowDetails(creditsId, amount, "Credits", "", "", "", dateString, true);
                databaseReference4.child("Passbook").child("Credits").child(creditsId).setValue(cashflowDetails);

                holder.creditsEditText.setText("");
            }
        });
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView roomNumberTextView, nameTextView;
        public EditText creditsEditText;

        public FloatingActionButton saveFab;

        public MyHolder(View itemView){
            super(itemView);

            roomNumberTextView = itemView.findViewById(R.id.roomNumberTextView);
            nameTextView = itemView.findViewById(R.id.tenantNameTextView);

            creditsEditText = itemView.findViewById(R.id.addCreditsEditText);

            saveFab = itemView.findViewById(R.id.saveFab);
        }
    }
}
