package net.eazypg.eazypgmanager.Activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.DetailList.RoomsDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.BillDetails;
import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class RoomClickActivity extends AppCompatActivity {

    TextView roomNumberTextView, tenant1NameTextView, tenant1RentPaidOrUnpaidTextView, tenant1ElectricityBillPaidOrUnpaid
            ,tenant1WifiBillPaidOrUnpaid, tenant1GasBillPaidOrUnpaid, tenant2NameTextView, tenant2RentPaidOrUnpaidTextView
            ,tenant2ElectricityBillPaidOrUnpaid ,tenant2WifiBillPaidOrUnpaid, tenant2GasBillPaidOrUnpaid, tenant3NameTextView
            ,tenant3RentPaidOrUnpaidTextView, tenant3ElectricityBillPaidOrUnpaid
            ,tenant3WifiBillPaidOrUnpaid, tenant3GasBillPaidOrUnpaid;

    EditText tenant1MessageEditText, tenant2MessageEditText, tenant3MessageEditText;

    Button tenant1CallButton, tenant1MessageButton, tenant1AddFineButton, tenant2CallButton, tenant2MessageButton, tenant2AddFineButton, tenant3CallButton, tenant3MessageButton, tenant3AddFineButton;

    CardView tenant1CardView, tenant2CardView, tenant3CardView;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    List<TenantDetails> tenantList = new ArrayList<>();
    List<BillDetails> billsList = new ArrayList<>();
    List<BillDetails> billsList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_click);

        Fabric.with(this, new Crashlytics());


        Intent intent = getIntent();
        String roomNumber = intent.getStringExtra(RoomsDetailList.EXTRA_MESSAGE);
        String roomType = intent.getStringExtra(RoomsDetailList.EXTRA_MESSAGE2);
        final String rentDueDate = intent.getStringExtra(RoomsDetailList.EXTRA_MESSAGE3);
        final String billDueDate = intent.getStringExtra(RoomsDetailList.EXTRA_MESSAGE4);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        roomNumberTextView = findViewById(R.id.roomNumberTextView);
        tenant1NameTextView = findViewById(R.id.tenant1NameTextView);
        tenant1RentPaidOrUnpaidTextView = findViewById(R.id.tenant1RentPaidOrUnpaidTextView);
        tenant1ElectricityBillPaidOrUnpaid = findViewById(R.id.tenant1ElectricityBillPaidOrUnpaid);
        tenant1WifiBillPaidOrUnpaid = findViewById(R.id.tenant1WifiBillPaidOrUnpaid);
        tenant1GasBillPaidOrUnpaid = findViewById(R.id.tenant1GasBillPaidOrUnpaid);

        tenant1CallButton = findViewById(R.id.tenant1CallButton);
        tenant1MessageButton = findViewById(R.id.tenant1MessageButton);
        tenant1AddFineButton = findViewById(R.id.tenant1AddFineButton);

        tenant1MessageEditText = new EditText(this);

        tenant1CardView = findViewById(R.id.tenant1CardView);

        tenant2NameTextView = findViewById(R.id.tenant2NameTextView);
        tenant2RentPaidOrUnpaidTextView = findViewById(R.id.tenant2RentPaidOrUnpaidTextView);
        tenant2ElectricityBillPaidOrUnpaid = findViewById(R.id.tenant2ElectricityBillPaidOrUnpaid);
        tenant2WifiBillPaidOrUnpaid = findViewById(R.id.tenant2WifiBillPaidOrUnpaid);
        tenant2GasBillPaidOrUnpaid = findViewById(R.id.tenant2GasBillPaidOrUnpaid);

        tenant2CallButton = findViewById(R.id.tenant2CallButton);
        tenant2MessageButton = findViewById(R.id.tenant2MessageButton);
        tenant2AddFineButton = findViewById(R.id.tenant2AddFineButton);

        tenant2MessageEditText = new EditText(this);

        tenant2CardView = findViewById(R.id.tenant2CardView);

        tenant3NameTextView = findViewById(R.id.tenant3NameTextView);
        tenant3RentPaidOrUnpaidTextView = findViewById(R.id.tenant3RentPaidOrUnpaidTextView);
        tenant3ElectricityBillPaidOrUnpaid = findViewById(R.id.tenant3ElectricityBillPaidOrUnpaid);
        tenant3WifiBillPaidOrUnpaid = findViewById(R.id.tenant3WifiBillPaidOrUnpaid);
        tenant3GasBillPaidOrUnpaid = findViewById(R.id.tenant3GasBillPaidOrUnpaid);

        tenant3CallButton = findViewById(R.id.tenant3CallButton);
        tenant3MessageButton = findViewById(R.id.tenant3MessageButton);
        tenant3AddFineButton = findViewById(R.id.tenant3AddFineButton);

        tenant3MessageEditText = new EditText(this);

        tenant3CardView = findViewById(R.id.tenant3CardView);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String dateStr = dateFormat.format(date);

        final String month = dateStr.substring(3,5);
        final String year = dateStr.substring(6,10);

        final String dateString = dateStr.substring(6,10) + "-" + dateStr.substring(3,5);

        roomNumberTextView.setText(roomNumber);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomNumber + "/Tenant/CurrentTenants/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                    tenantList.add(tenantDetails);
                }

                if(tenantList.size() == 0){
                    tenant1CardView.setVisibility(View.GONE);
                    tenant2CardView.setVisibility(View.GONE);
                    tenant3CardView.setVisibility(View.GONE);
                }

                if(tenantList.size() == 1){
                    tenant1NameTextView.setText(tenantList.get(0).name);

                    tenant1CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+tenantList.get(0).phone));
                                startActivity(callIntent);
                            }
                            catch (ActivityNotFoundException activityException) {
                                Toast.makeText(RoomClickActivity.this, "Call failed", Toast.LENGTH_SHORT).show();
                            }
                            catch (SecurityException e) {
                                Toast.makeText(RoomClickActivity.this, "Call failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tenant1MessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tenant1MessageEditText.getParent() != null){
                                ((ViewGroup) tenant1MessageEditText.getParent()).removeView(tenant1MessageEditText);
                            }

                            String message = "Hey " + tenantList.get(0).name + ". Please pay your rent/bill to avoid fine. Download the EazyPGTenant App" +
                                    "to know more. " + " Ignore if already paid."; //TODO: Add Tenant App Link

                            tenant1MessageEditText.setText(message);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomClickActivity.this);
                            builder.setTitle("Send Message");
                            builder.setNegativeButton("Cancel", null);
                            builder.setView(tenant1MessageEditText);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");

                                    String message = tenant1MessageEditText.getText().toString();
                                    msg91.composeMessage("EazyPG", message);
                                    msg91.to(tenantList.get(0).phone);
                                    String sendStatus = msg91.send();
                                }
                            });
                        }
                    });

                    tenant1AddFineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RoomClickActivity.this, FineRentBillActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, tenantList.get(0).id);
                            intent.putExtra(EXTRA_MESSAGE2, tenantList.get(0).room);
                            startActivity(intent);
                        }
                    });

                    DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Passbook/Rent/" + dateString);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CashflowDetails status = dataSnapshot.getValue(CashflowDetails.class);

                            if(status != null){
                                tenant1RentPaidOrUnpaidTextView.setText("Rent paid on " + status.date);
                            }else{
                                tenant1RentPaidOrUnpaidTextView.setText("Rent due by " + rentDueDate + "/" + month + "/" + year);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference3 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Passbook/Bills/" + dateString);
                    databaseReference3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList.get(i).category.equals("Electricity") && billsList.get(i).paidOrUnpaid){
                                    tenant1ElectricityBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Wifi") && billsList.get(i).paidOrUnpaid){
                                    tenant1WifiBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Gas") && billsList.get(i).paidOrUnpaid){
                                    tenant1GasBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference30 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Accounts/Bills/" + dateString);
                    databaseReference30.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList2.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList2.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList2.get(i).category.equals("Electricity") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1ElectricityBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Wifi") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1WifiBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Gas") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1GasBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    tenant2CardView.setVisibility(View.GONE);
                    tenant3CardView.setVisibility(View.GONE);

                }else if(tenantList.size() == 2){
                    tenant1NameTextView.setText(tenantList.get(0).name);

                    tenant2NameTextView.setText(tenantList.get(1).name);

                    tenant1CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+tenantList.get(0).phone));
                                startActivity(callIntent);
                            }
                            catch (ActivityNotFoundException activityException) {
                                Toast.makeText(RoomClickActivity.this, "Call failed", Toast.LENGTH_SHORT).show();
                            }
                            catch (SecurityException e) {
                                Toast.makeText(RoomClickActivity.this, "Call failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tenant2CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+tenantList.get(1).phone));
                                startActivity(callIntent);
                            }
                            catch (ActivityNotFoundException activityException) {
                                Toast.makeText(RoomClickActivity.this, "Call failed", Toast.LENGTH_SHORT).show();
                            }
                            catch (SecurityException e) {
                                Toast.makeText(RoomClickActivity.this, "Call failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tenant1MessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tenant1MessageEditText.getParent() != null){
                                ((ViewGroup) tenant1MessageEditText.getParent()).removeView(tenant1MessageEditText);
                            }

                            String message = "Hey " + tenantList.get(0).name + ". Please pay your rent/bill to avoid fine. Download the EazyPGTenant App" +
                                    "to know more. " + "Ignore if already paid."; // Add Tenant App Link

                            tenant1MessageEditText.setText(message);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomClickActivity.this);
                            builder.setTitle("Send Message");
                            builder.setNegativeButton("Cancel", null);
                            builder.setView(tenant1MessageEditText);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");

                                    String message = tenant1MessageEditText.getText().toString();
                                    msg91.composeMessage("EazyPG", message);
                                    msg91.to(tenantList.get(0).phone);
                                    String sendStatus = msg91.send();
                                }
                            });
                        }
                    });

                    tenant2MessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tenant2MessageEditText.getParent() != null){
                                ((ViewGroup) tenant2MessageEditText.getParent()).removeView(tenant2MessageEditText);
                            }

                            String message = "Hey " + tenantList.get(1).name + ". Please pay your rent/bill to avoid fine. Download the EazyPGTenant App" +
                                    "to know more. " + "Ignore if already paid."; // Add Tenant App Link

                            tenant2MessageEditText.setText(message);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomClickActivity.this);
                            builder.setTitle("Send Message");
                            builder.setNegativeButton("Cancel", null);
                            builder.setView(tenant1MessageEditText);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");

                                    String message = tenant1MessageEditText.getText().toString();
                                    msg91.composeMessage("EazyPG", message);
                                    msg91.to(tenantList.get(1).phone);
                                    String sendStatus = msg91.send();
                                }
                            });
                        }
                    });

                    tenant1AddFineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RoomClickActivity.this, FineRentBillActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, tenantList.get(0).id);
                            intent.putExtra(EXTRA_MESSAGE2, tenantList.get(0).room);
                            startActivity(intent);
                        }
                    });

                    tenant2AddFineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RoomClickActivity.this, FineRentBillActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, tenantList.get(1).id);
                            intent.putExtra(EXTRA_MESSAGE2, tenantList.get(1).room);
                            startActivity(intent);
                        }
                    });

                    DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Passbook/Rent/" + dateString);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CashflowDetails status = dataSnapshot.getValue(CashflowDetails.class);

                            if(status != null){
                                tenant1RentPaidOrUnpaidTextView.setText("Rent paid on " + status.date);
                            }else{
                                tenant1RentPaidOrUnpaidTextView.setText("Rent due by " + rentDueDate + "/" + month + "/" + year);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference4 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(1).id + "/Passbook/Rent/" + dateString);
                    databaseReference4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CashflowDetails status = dataSnapshot.getValue(CashflowDetails.class);

                            if(status != null){
                                tenant2RentPaidOrUnpaidTextView.setText("Rent " + status.date);
                            }else{
                                tenant2RentPaidOrUnpaidTextView.setText("Rent due by " + rentDueDate + "/" + month + "/" + year);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference3 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Passbook/Bills/" + dateString);
                    databaseReference3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList.get(i).category.equals("Electricity") && billsList.get(i).paidOrUnpaid){
                                    tenant1ElectricityBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Wifi") && billsList.get(i).paidOrUnpaid){
                                    tenant1WifiBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Gas") && billsList.get(i).paidOrUnpaid){
                                    tenant1GasBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference30 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Accounts/Bills/" + dateString);
                    databaseReference30.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList2.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList2.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList2.get(i).category.equals("Electricity") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1ElectricityBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Wifi") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1WifiBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Gas") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1GasBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference5 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(1).id + "/Passbook/Bills/" + dateString);
                    databaseReference5.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList.get(i).category.equals("Electricity") && billsList.get(i).paidOrUnpaid){
                                    tenant2ElectricityBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Wifi") && billsList.get(i).paidOrUnpaid){
                                    tenant2WifiBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Gas") && billsList.get(i).paidOrUnpaid){
                                    tenant2GasBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference31 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(1).id + "/Accounts/Bills/" + dateString);
                    databaseReference31.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList2.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList2.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList2.get(i).category.equals("Electricity") && !billsList2.get(i).paidOrUnpaid){
                                    tenant2ElectricityBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Wifi") && !billsList2.get(i).paidOrUnpaid){
                                    tenant2WifiBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Gas") && !billsList2.get(i).paidOrUnpaid){
                                    tenant2GasBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    tenant3CardView.setVisibility(View.GONE);

                }else if(tenantList.size() == 3){
                    tenant1NameTextView.setText(tenantList.get(0).name);

                    tenant2NameTextView.setText(tenantList.get(1).name);

                    tenant3NameTextView.setText(tenantList.get(2).name);

                    tenant1CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+tenantList.get(0).phone));
                                startActivity(callIntent);
                            }
                            catch (ActivityNotFoundException activityException) {
                                Toast.makeText(RoomClickActivity.this, "Call failed", Toast.LENGTH_SHORT).show();
                            }
                            catch (SecurityException e) {
                                Toast.makeText(RoomClickActivity.this, "Call failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tenant2CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+tenantList.get(1).phone));
                                startActivity(callIntent);
                            }
                            catch (ActivityNotFoundException activityException) {
                                Toast.makeText(RoomClickActivity.this, "Call failed", Toast.LENGTH_SHORT).show();
                            }
                            catch (SecurityException e) {
                                Toast.makeText(RoomClickActivity.this, "Call failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tenant3CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:"+tenantList.get(2).phone));
                                startActivity(callIntent);
                            }
                            catch (ActivityNotFoundException activityException) {
                                Toast.makeText(RoomClickActivity.this, "Call failed", Toast.LENGTH_SHORT).show();
                            }
                            catch (SecurityException e) {
                                Toast.makeText(RoomClickActivity.this, "Call failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    tenant1MessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tenant1MessageEditText.getParent() != null){
                                ((ViewGroup) tenant1MessageEditText.getParent()).removeView(tenant1MessageEditText);
                            }

                            String message = "Hey " + tenantList.get(0).name + ". Please pay your rent/bill to avoid fine. Download the EazyPGTenant App" +
                                    "to know more. " + "Ignore if already paid."; // Add Tenant App Link

                            tenant1MessageEditText.setText(message);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomClickActivity.this);
                            builder.setTitle("Send Message");
                            builder.setNegativeButton("Cancel", null);
                            builder.setView(tenant1MessageEditText);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");

                                    String message = tenant1MessageEditText.getText().toString();
                                    msg91.composeMessage("EazyPG", message);
                                    msg91.to(tenantList.get(0).phone);
                                    String sendStatus = msg91.send();
                                }
                            });
                        }
                    });

                    tenant2MessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tenant2MessageEditText.getParent() != null){
                                ((ViewGroup) tenant2MessageEditText.getParent()).removeView(tenant2MessageEditText);
                            }

                            String message = "Hey " + tenantList.get(1).name + ". Please pay your rent/bill to avoid fine. Download the EazyPGTenant App" +
                                    "to know more. " + "Ignore if already paid."; // Add Tenant App Link

                            tenant2MessageEditText.setText(message);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomClickActivity.this);
                            builder.setTitle("Send Message");
                            builder.setNegativeButton("Cancel", null);
                            builder.setView(tenant1MessageEditText);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");

                                    String message = tenant1MessageEditText.getText().toString();
                                    msg91.composeMessage("EazyPG", message);
                                    msg91.to(tenantList.get(1).phone);
                                    String sendStatus = msg91.send();
                                }
                            });
                        }
                    });

                    tenant3MessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(tenant3MessageEditText.getParent() != null){
                                ((ViewGroup) tenant3MessageEditText.getParent()).removeView(tenant3MessageEditText);
                            }

                            String message = "Hey " + tenantList.get(2).name + ". Please pay your rent/bill to avoid fine. Download the EazyPGTenant App" +
                                    "to know more. " + "Ignore if already paid."; // Add Tenant App Link

                            tenant3MessageEditText.setText(message);
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomClickActivity.this);
                            builder.setTitle("Send Message");
                            builder.setNegativeButton("Cancel", null);
                            builder.setView(tenant1MessageEditText);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");

                                    String message = tenant1MessageEditText.getText().toString();
                                    msg91.composeMessage("EazyPG", message);
                                    msg91.to(tenantList.get(2).phone);
                                    String sendStatus = msg91.send();
                                }
                            });
                        }
                    });

                    tenant1AddFineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RoomClickActivity.this, FineRentBillActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, tenantList.get(0).id);
                            intent.putExtra(EXTRA_MESSAGE2, tenantList.get(0).room);
                            startActivity(intent);
                        }
                    });

                    tenant2AddFineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RoomClickActivity.this, FineRentBillActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, tenantList.get(1).id);
                            intent.putExtra(EXTRA_MESSAGE2, tenantList.get(1).room);
                            startActivity(intent);
                        }
                    });

                    tenant3AddFineButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(RoomClickActivity.this, FineRentBillActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, tenantList.get(2).id);
                            intent.putExtra(EXTRA_MESSAGE2, tenantList.get(2).room);
                            startActivity(intent);
                        }
                    });

                    DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Passbook/Rent/" + dateString);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CashflowDetails status = dataSnapshot.getValue(CashflowDetails.class);

                            if(status != null){
                                tenant1RentPaidOrUnpaidTextView.setText("Rent " + status.date);
                            }else{
                                tenant1RentPaidOrUnpaidTextView.setText("Rent due by " + rentDueDate + "/" + month + "/" + year);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference4 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(1).id + "/Passbook/Rent/" + dateString);
                    databaseReference4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CashflowDetails status = dataSnapshot.getValue(CashflowDetails.class);

                            if(status != null){
                                tenant2RentPaidOrUnpaidTextView.setText("Rent " + status.date);
                            }else{
                                tenant2RentPaidOrUnpaidTextView.setText("Rent due by " + rentDueDate + "/" + month + "/" + year);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference3 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Passbook/Bills/" + dateString);
                    databaseReference3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList.get(i).category.equals("Electricity") && billsList.get(i).paidOrUnpaid){
                                    tenant1ElectricityBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Wifi") && billsList.get(i).paidOrUnpaid){
                                    tenant1WifiBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Gas") && billsList.get(i).paidOrUnpaid){
                                    tenant1GasBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference30 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(0).id + "/Accounts/Bills/" + dateString);
                    databaseReference30.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList2.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList2.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList2.get(i).category.equals("Electricity") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1ElectricityBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Wifi") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1WifiBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Gas") && !billsList2.get(i).paidOrUnpaid){
                                    tenant1GasBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference5 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(1).id + "/Passbook/Bills/" + dateString);
                    databaseReference5.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList.get(i).category.equals("Electricity") && billsList.get(i).paidOrUnpaid){
                                    tenant2ElectricityBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Wifi") && billsList.get(i).paidOrUnpaid){
                                    tenant2WifiBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Gas") && billsList.get(i).paidOrUnpaid){
                                    tenant2GasBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference31 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(1).id + "/Accounts/Bills/" + dateString);
                    databaseReference31.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList2.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList2.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList2.get(i).category.equals("Electricity") && !billsList2.get(i).paidOrUnpaid){
                                    tenant2ElectricityBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Wifi") && !billsList2.get(i).paidOrUnpaid){
                                    tenant2WifiBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Gas") && !billsList2.get(i).paidOrUnpaid){
                                    tenant2GasBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference6 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(2).id + "/Passbook/Rent/" + dateString);
                    databaseReference6.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CashflowDetails status = dataSnapshot.getValue(CashflowDetails.class);

                            if(status != null){
                                tenant3RentPaidOrUnpaidTextView.setText("Rent " + status.date);
                            }else{
                                tenant3RentPaidOrUnpaidTextView.setText("Rent due by " + rentDueDate + "/" + month + "/" + year);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference7 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(2).id + "/Passbook/Bills/" + dateString);
                    databaseReference7.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList.get(i).category.equals("Electricity") && billsList.get(i).paidOrUnpaid){
                                    tenant3ElectricityBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Wifi") && billsList.get(i).paidOrUnpaid){
                                    tenant3WifiBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }else if(billsList.get(i).category.equals("Gas") && billsList.get(i).paidOrUnpaid){
                                    tenant3GasBillPaidOrUnpaid.setText("Bill paid on " + billsList.get(i).datePaid);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference databaseReference32 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantList.get(2).id + "/Accounts/Bills/" + dateString);
                    databaseReference32.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            billsList2.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                BillDetails billDetails = snapshot.getValue(BillDetails.class);
                                billsList2.add(billDetails);
                            }

                            for(int i = 0; i < billsList.size(); i++){
                                if(billsList2.get(i).category.equals("Electricity") && !billsList2.get(i).paidOrUnpaid){
                                    tenant3ElectricityBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Wifi") && !billsList2.get(i).paidOrUnpaid){
                                    tenant3WifiBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }else if(billsList2.get(i).category.equals("Gas") && !billsList2.get(i).paidOrUnpaid){
                                    tenant3GasBillPaidOrUnpaid.setText("Bill due by " + billDueDate + "/" + month + "/" + year);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
