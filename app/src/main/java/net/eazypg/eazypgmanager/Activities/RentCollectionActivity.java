package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.RentCollectionPaidDetailList;
import net.eazypg.eazypgmanager.DetailList.RentCollectionUnpaidDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class RentCollectionActivity extends AppCompatActivity {

    TextView numberBillPaidTextView, numberBillNotPaidTextView;
    RecyclerView rentPaidRecyclerView, rentUnpaidRecyclerView;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    List<TenantDetails> tenantList = new ArrayList<>();
    List<TenantDetails> tenantPaidList = new ArrayList<>();
    List<TenantDetails> tenantUnpaidList = new ArrayList<>();

    int paid = 0, unpaid = 0;

    RentCollectionPaidDetailList rentCollectionPaidDetailList;
    RentCollectionUnpaidDetailList rentCollectionUnpaidDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_collection);

        Fabric.with(this, new Crashlytics());


        final Context context = getApplicationContext();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        numberBillPaidTextView = findViewById(R.id.numberBillPaidTextView);
        numberBillNotPaidTextView = findViewById(R.id.numberBillNotPaidTextView);

        rentPaidRecyclerView = findViewById(R.id.rentPaidRecyclerView);
        rentUnpaidRecyclerView = findViewById(R.id.rentUnpaidRecyclerView);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                    Log.e("LO", "onDataChange: " + tenantDetails.name);
                    tenantList.add(tenantDetails);
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateStr = dateFormat.format(date);

                final String dateString = dateStr.substring(6,10) + "-" + dateStr.substring(3,5);

                final DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/");
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tenantPaidList.clear();
                        tenantUnpaidList.clear();

                        Log.e("LOLOL", "onDataChange: " + tenantList.size());
                        for(int i = 0; i < tenantList.size(); i++){
                            paid = 0;
                            unpaid = 0;
                            CashflowDetails status = dataSnapshot.child(tenantList.get(i).id).child("Accounts").child("Rent").child(dateString).getValue(CashflowDetails.class);
                            if(status != null){
                                tenantPaidList.add(tenantList.get(i));
                                paid++;
                            }else{
                                tenantUnpaidList.add(tenantList.get(i));
                                unpaid++;
                            }
                        }

                        numberBillPaidTextView.setText(Integer.toString(paid));
                        numberBillNotPaidTextView.setText(Integer.toString(unpaid));

                        rentCollectionPaidDetailList = new RentCollectionPaidDetailList(tenantList, tenantPaidList, context);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        rentPaidRecyclerView.setLayoutManager(layoutManager);
                        rentPaidRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        rentPaidRecyclerView.setAdapter(rentCollectionPaidDetailList);

                        rentCollectionUnpaidDetailList = new RentCollectionUnpaidDetailList(tenantList, tenantUnpaidList, context);
                        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
                        rentUnpaidRecyclerView.setLayoutManager(layoutManager2);
                        rentUnpaidRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        rentUnpaidRecyclerView.setAdapter(rentCollectionUnpaidDetailList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
