package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.AddBillRoomElectricityDetailList;
import net.eazypg.eazypgmanager.DetailList.AddBillRoomGasDetailList;
import net.eazypg.eazypgmanager.DetailList.AddBillRoomOtherDetailList;
import net.eazypg.eazypgmanager.DetailList.AddBillRoomWifiDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.BillDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class AddBillRoomActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<String> roomTypeList = new ArrayList<>();
    List<String> roomsList = new ArrayList<>();

    RecyclerView addBillRoomRecyclerView;

    EditText billAmountEditText;

    FloatingActionButton saveAllFab;

    ImageView backButton;

    AddBillRoomElectricityDetailList addBillRoomElectricityDetailList;
    AddBillRoomWifiDetailList addBillRoomWifiDetailList;
    AddBillRoomGasDetailList addBillRoomGasDetailList;
    AddBillRoomOtherDetailList addBillRoomOtherDetailList;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_room);

        Fabric.with(this, new Crashlytics());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        addBillRoomRecyclerView = findViewById(R.id.addBillRoomRecyclerView);

        billAmountEditText = findViewById(R.id.amountEditText);
        saveAllFab = findViewById(R.id.saveAllFab);

        backButton = findViewById(R.id.backButton);

        context = getApplicationContext();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        final String dateString = dateStr.substring(6,10) + "-" + dateStr.substring(3,5);

        Intent intent = getIntent();
        final String billType = intent.getStringExtra(AddBillActivity.EXTRA_MESSAGE);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                roomTypeList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    roomsList.add(snapshot.getKey());
                    String roomType = snapshot.child("Room Type").getValue(String.class);
                    roomTypeList.add(roomType);
                }

                if(billType.equals("Electricity")) {
                    DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PG Details/");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child("electricityUnitCost").getValue(String.class) == null || dataSnapshot.child("electricityUnitCost").getValue(String.class).isEmpty()) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(AddBillRoomActivity.this);
                                builder.setTitle("Error");
                                builder.setMessage("Electricity Unit Cost is not added. Do you want to add it now?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(AddBillRoomActivity.this, MyPGActivity.class));
                                        finish();
                                    }
                                });
                                builder.setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(AddBillRoomActivity.this, AddBillActivity.class));
                                        finish();
                                    }
                                });
                                builder.setCancelable(false);
                                builder.show();
                            } else {

                                String unitCost = dataSnapshot.child("electricityUnitCost").getValue(String.class);

                                addBillRoomElectricityDetailList = new AddBillRoomElectricityDetailList(context, roomsList, roomTypeList, unitCost);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                addBillRoomRecyclerView.setLayoutManager(layoutManager);
                                addBillRoomRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                addBillRoomRecyclerView.setAdapter(addBillRoomElectricityDetailList);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                if(billType.equals("Wifi")) {
                    addBillRoomWifiDetailList = new AddBillRoomWifiDetailList(context, roomsList, roomTypeList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    addBillRoomRecyclerView.setLayoutManager(layoutManager);
                    addBillRoomRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    addBillRoomRecyclerView.setAdapter(addBillRoomWifiDetailList);
                }

                if(billType.equals("Gas")) {
                    addBillRoomGasDetailList = new AddBillRoomGasDetailList(context, roomsList, roomTypeList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    addBillRoomRecyclerView.setLayoutManager(layoutManager);
                    addBillRoomRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    addBillRoomRecyclerView.setAdapter(addBillRoomGasDetailList);
                }

                if(billType.equals("Other")) {
                    addBillRoomOtherDetailList = new AddBillRoomOtherDetailList(context, roomsList, roomTypeList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    addBillRoomRecyclerView.setLayoutManager(layoutManager);
                    addBillRoomRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    addBillRoomRecyclerView.setAdapter(addBillRoomOtherDetailList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveAllFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String amount = billAmountEditText.getText().toString();
                final List<String> tenantIds = new ArrayList<>();

                DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/");
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            TenantDetails tenant = snapshot.getValue(TenantDetails.class);
                            tenantIds.add(tenant.id);
                        }

                        DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/");
                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(int i = 0; i < tenantIds.size(); i++){
                                    DatabaseReference databaseReference3 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantIds.get(i) + "/Accounts/Bills/" + dateString);
                                    String billId = databaseReference3.push().getKey();
                                    BillDetails billDetails = new BillDetails(billId, billType, amount, false, "", dateString);
                                    databaseReference3.child(billId).setValue(billDetails);

                                    DatabaseReference databaseReference4 = firebaseDatabase.getReference("Tenants/" + tenantIds.get(i) + "/Accounts/Bills/" + dateString);
                                    databaseReference4.child(billId).setValue(billDetails);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBillRoomActivity.this);
                                    builder.setTitle("Bill Added");
                                    builder.setMessage("Bill is added to all rooms");
                                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(AddBillRoomActivity.this, AddBillActivity.class));
                                            finish();

                                        }
                                    });
                                    builder.show();
                                }
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
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddBillRoomActivity.this , AddBillActivity.class));
                finish();
            }
        });
    }
}
