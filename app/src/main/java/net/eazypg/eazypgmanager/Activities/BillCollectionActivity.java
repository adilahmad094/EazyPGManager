package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.BillDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class BillCollectionActivity extends AppCompatActivity {

    RecyclerView billRecyclerView;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    List<TenantDetails> tenantList = new ArrayList<>();
    List<BillDetails> electricityBillList = new ArrayList<>();
    List<BillDetails> wifiBillList = new ArrayList<>();
    List<BillDetails> gasBillList = new ArrayList<>();
    List<BillDetails> otherBillList = new ArrayList<>();
    List<BillDetails> billList = new ArrayList<>();

    BillCollectionDetailList billCollectionDetailList;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_collection);
        Fabric.with(this, new Crashlytics());



        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        billRecyclerView = findViewById(R.id.billRecyclerView);

        context = getApplicationContext();

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
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
                        billList.clear();
                        gasBillList.clear();
                        wifiBillList.clear();
                        electricityBillList.clear();
                        otherBillList.clear();

                        for(int i = 0; i < tenantList.size(); i++){
                            BillDetails billDetails = dataSnapshot.child(tenantList.get(i).id).child("Accounts").child("Bills").child(dateString).getValue(BillDetails.class);
                            if(billDetails != null){
                                billList.add(billDetails);
                            }
                        }

                        for(int i = 0; i < billList.size(); i++){
                            BillDetails billDetails = billList.get(i);
                            switch (billDetails.getCategory()) {
                                case "Electricity":
                                    electricityBillList.add(billDetails);
                                    break;
                                case "Wifi":
                                    wifiBillList.add(billDetails);
                                    break;
                                case "Gas":
                                    gasBillList.add(billDetails);
                                    break;
                                case "Other":
                                    otherBillList.add(billDetails);
                                    break;
                            }
                        }

                        billCollectionDetailList = new BillCollectionDetailList(electricityBillList, wifiBillList, gasBillList, otherBillList, tenantList, context);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                        billRecyclerView.setLayoutManager(layoutManager);
                        billRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        billRecyclerView.setAdapter(billCollectionDetailList);
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
