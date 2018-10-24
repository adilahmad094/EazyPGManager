package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.RentCollectionUnpaidDetailList;
import net.eazypg.eazypgmanager.DetailList.UnpaidBillDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UnpaidBillActivity extends AppCompatActivity {

    String typeOfBill;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    TextView numberBillPaidTextView, numberBillNotPaidTextView;
    RecyclerView unpaidBillRecyclerView;

    List<TenantDetails> unpaidTenants = new ArrayList<>();

    long paid = 0, unpaid = 0;

    Context context;

    UnpaidBillDetailList unpaidBillDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid_bill);

        unpaidBillRecyclerView = findViewById(R.id.UnpaidBillRecyclerView);
        numberBillPaidTextView = findViewById(R.id.numberBillPaidTextView);
        numberBillNotPaidTextView = findViewById(R.id.numberBillNotPaidTextView);

        Intent intent = getIntent();
        typeOfBill = intent.getStringExtra(BillCollectionFragment.EXTRA_MESSAGE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        context = UnpaidBillActivity.this;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        final String dateStr = dateFormat.format(date);

        final String dateString = dateStr.substring(6,10) + "-" + dateStr.substring(3,5);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                paid = dataSnapshot.child("ThisMonth").child(dateString).child("Bill").child(typeOfBill).getChildrenCount();
                unpaid = dataSnapshot.child("CurrentTenants").getChildrenCount() - paid;

                numberBillPaidTextView.setText(Long.toString(paid));
                numberBillNotPaidTextView.setText(Long.toString(unpaid));

                unpaidTenants.clear();

                for (DataSnapshot snapshot : dataSnapshot.child("CurrentTenants").getChildren()) {

                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);

                    if (dataSnapshot.child("ThisMonth").child(dateString).child("Bill").child(typeOfBill).hasChild(tenantDetails.id)) {
                        // rent paid
                    }
                    else {
                        unpaidTenants.add(tenantDetails);
                    }

                }

                unpaidBillDetailList = new UnpaidBillDetailList(unpaidTenants, context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                unpaidBillRecyclerView.setLayoutManager(layoutManager);
                unpaidBillRecyclerView.setItemAnimator(new DefaultItemAnimator());
                unpaidBillRecyclerView.setAdapter(unpaidBillDetailList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
