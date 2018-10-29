package net.eazypg.eazypgmanager.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.PG;
import net.eazypg.eazypgmanager.R;

import java.util.Calendar;

import io.fabric.sdk.android.Fabric;


public class MyPGActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, databaseReference1;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    Button setLocationButton;

    TextInputLayout pgNameTIL;
    TextInputLayout pgEmailTIL;
    TextInputLayout landmarkTIL;
    TextInputLayout address1TIL;
    TextInputLayout address2TIL;
    TextInputLayout maxOccupancyTIL;
    TextInputLayout bathroomTIL;
    TextInputLayout roomTIL;
    TextInputLayout ownerNameTIL;
    TextInputLayout contactTIL;
    TextInputLayout staffCountTIL;
    TextInputLayout billTIL;
    TextInputLayout rentTIL;
    TextInputLayout electrcityTIL;
    TextInputLayout cityTIL;
    TextInputLayout pincodeTIL;
    TextInputLayout gasUnitCostTIL;
    TextInputLayout messTypeTIL;
    TextInputLayout messRateTIL;
    TextInputLayout personalContactTIL;
    TextInputLayout personalEmailTIL;
    TextInputLayout stateTIL;
    TextInputLayout emergencyStayRateTIL;
    TextInputLayout securityDepositTIL;
    TextInputLayout lockingPeriodTIL;



    TextInputEditText pgName;
    TextInputEditText landmark;
    TextInputEditText maxOccupancy;
    TextInputEditText bathroom;
    TextInputEditText room;
    TextInputEditText ownerName;
    TextInputEditText contact;
    TextInputEditText staffCount;
    TextInputEditText billDueDate;
    TextInputEditText rentDueDate;
    TextInputEditText electricityUnitCost;
    TextInputEditText city;
    TextInputEditText pincode;
    TextInputEditText gasUnitCOst;
    TextInputEditText messType;
    TextInputEditText messRate;
    TextInputEditText personalContact;
    TextInputEditText personalEmail;
    TextInputEditText emergencyStayRate;
    Snackbar snackbar;
    TextInputEditText securityDeposit;
    TextInputEditText lockingPeriod;
    TextInputEditText addressLine1ET;
    TextInputEditText addressLine2ET;
    TextInputEditText stateEditText;

    LinearLayout lastEntryTimeLinearLayout;

    LinearLayout pgAvailableForLinearLayout;
    LinearLayout lastLateCheckInLinearLayout;


    TextView pgAvailableForTextView;
    TextView lastlateCheckInTime;




    boolean electricityIsChecked;
    boolean wifiIsChecked;
    boolean gasIsChecked;
    String typesOfBills = "";
    String tenantsPreferred = "";
    String pgAvailableFor = "";

    View view;
    ImageView backButton;
    LocationManager locationManager;
    LocationListener listener;

    CheckBox electricityCheckBox, wifiCheckBox, gasCheckBox;

    LinearLayout billTakeLinearLayout;
    LinearLayout tenantsPreferredLinearLayout;

    TextView customTitle;

    TextView billFirstTextView;
    TextView tenantsPreferredTextView;

    TextView lastEntryTimeTextView;

    FloatingActionButton saveButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pg);

        Fabric.with(this, new Crashlytics());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        lastlateCheckInTime = findViewById(R.id.lastLateCheckInTimeID);
        lastEntryTimeTextView = findViewById(R.id.lastEntryTimeTextView);

        editButton = findViewById(R.id.editButton);

        pgNameTIL = findViewById(R.id.pgNameTIL);
        landmarkTIL = findViewById(R.id.nearestLandmarkTIL);
        pgEmailTIL = findViewById(R.id.pgEmailTIL);
        address1TIL = findViewById(R.id.addressLine1TIL);
        address2TIL = findViewById(R.id.addressLine2TIL);
        maxOccupancyTIL = findViewById(R.id.maxOccupancyTIL);
        bathroomTIL = findViewById(R.id.bathroomsTIL);
        roomTIL = findViewById(R.id.roomsTIL);
        ownerNameTIL = findViewById(R.id.ownerNameTIL);
        contactTIL = findViewById(R.id.pgContactTIL);
        staffCountTIL = findViewById(R.id.numberOfStaffTIL);
        billTIL = findViewById(R.id.billDueDateTIL);
        rentTIL = findViewById(R.id.rentDueDateTIL);
        electrcityTIL = findViewById(R.id.electricityUnitCostTIL);
        cityTIL = findViewById(R.id.cityTIL);
        pincodeTIL = findViewById(R.id.pincodeTIL);
        gasUnitCostTIL = findViewById(R.id.gasUnitCostTIL);
        messTypeTIL = findViewById(R.id.messTypeTIL);
        messRateTIL = findViewById(R.id.messRateTIL);
        personalContactTIL = findViewById(R.id.personalContactTIL);
        personalEmailTIL = findViewById(R.id.personalEmailTIL);
        stateTIL = findViewById(R.id.stateTIL);
        emergencyStayRateTIL = findViewById(R.id.emergencyStayRateTIL);
        securityDepositTIL = findViewById(R.id.securityDepositTIL);
        lockingPeriodTIL = findViewById(R.id.lockingPeriodTIL);

        lastEntryTimeLinearLayout =  findViewById(R.id.LL13);
        lastEntryTimeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MyPGActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        lastEntryTimeTextView.setText(selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        setLocationButton = findViewById(R.id.setLocationButton);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.rgb(250,250,250));
            }
        }

        if (ActivityCompat.checkSelfPermission(MyPGActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyPGActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        databaseReference = firebaseDatabase.getReference("PG/");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        billTakeLinearLayout = findViewById(R.id.billTakeLinearLayout);
        tenantsPreferredLinearLayout = findViewById(R.id.tenantsPreferredLinearLayout);


        billFirstTextView = findViewById(R.id.billFirstTextView);
        tenantsPreferredTextView = findViewById(R.id.tenantsPreferredTextView);

        lastLateCheckInLinearLayout = findViewById(R.id.linearLayout10);

        pgName = findViewById(R.id.pgNameTextView);

        landmark = findViewById(R.id.landmarkTextView);
        maxOccupancy = findViewById(R.id.maxOccupancyTextView);
        bathroom = findViewById(R.id.noOfBathroomTextView);
        room = findViewById(R.id.noOfRoomTextView);
        ownerName = findViewById(R.id.ownerNameTextView);
        contact = findViewById(R.id.pgContactTextView);
        staffCount = findViewById(R.id.staffCountTextView);
        rentDueDate = findViewById(R.id.rentDueDateEditText);
        billDueDate = findViewById(R.id.billDueDateEditText);
        electricityUnitCost = findViewById(R.id.electricityUnitCostEditText);
        emergencyStayRate = findViewById(R.id.emergencyStayRateEditText);
        securityDeposit = findViewById(R.id.securityDepositEditText);
        lockingPeriod = findViewById(R.id.lockingPeriodEditText);
        addressLine1ET = findViewById(R.id.addressLine1EditText);
        addressLine2ET = findViewById(R.id.addressLine2EditText);
        stateEditText = findViewById(R.id.stateEditText);

        pgAvailableForLinearLayout = findViewById(R.id.LL2);

        city = findViewById(R.id.cityEditText);
        pincode = findViewById(R.id.pincodeEditText);
        gasUnitCOst = findViewById(R.id.gasRateEditText);
        messType = findViewById(R.id.messTypeEditText);
        messRate = findViewById(R.id.messRateEditText);
        personalContact = findViewById(R.id.ownerPersonalContactTextView);
        personalEmail = findViewById(R.id.personalEmailEditText);


        pgNameTIL.setHintAnimationEnabled(false);
        landmarkTIL.setHintAnimationEnabled(false);
        pgEmailTIL.setHintAnimationEnabled(false);
        address1TIL.setHintAnimationEnabled(false);
        address2TIL.setHintAnimationEnabled(false);
        maxOccupancyTIL.setHintAnimationEnabled(false);
        bathroomTIL.setHintAnimationEnabled(false);
        roomTIL.setHintAnimationEnabled(false);
        ownerNameTIL.setHintAnimationEnabled(false);
        contactTIL.setHintAnimationEnabled(false);
        staffCountTIL.setHintAnimationEnabled(false);
        billTIL.setHintAnimationEnabled(false);
        stateTIL.setHintAnimationEnabled(false);
        rentTIL.setHintAnimationEnabled(false);
        electrcityTIL.setHintAnimationEnabled(false);
        cityTIL.setHintAnimationEnabled(false);
        pincodeTIL.setHintAnimationEnabled(false);
        gasUnitCostTIL.setHintAnimationEnabled(false);
        messTypeTIL.setHintAnimationEnabled(false);
        messRateTIL.setHintAnimationEnabled(false);
        personalContactTIL.setHintAnimationEnabled(false);
        personalEmailTIL.setHintAnimationEnabled(false);
        emergencyStayRateTIL.setHintAnimationEnabled(false);
        lockingPeriodTIL.setHintAnimationEnabled(false);
        securityDepositTIL.setHintAnimationEnabled(false);

        tenantsPreferredLinearLayout.setEnabled(false);
        pgAvailableForLinearLayout.setEnabled(false);
        lastEntryTimeLinearLayout.setEnabled(false);
        lastLateCheckInLinearLayout.setEnabled(false);
        billTakeLinearLayout.setEnabled(false);

        pgNameTIL.setEnabled(false);
        landmarkTIL.setEnabled(false);
        address1TIL.setEnabled(false);
        pgEmailTIL.setEnabled(false);
        address2TIL.setEnabled(false);
        maxOccupancyTIL.setEnabled(false);
        bathroomTIL.setEnabled(false);
        roomTIL.setEnabled(false);
        ownerNameTIL.setEnabled(false);
        contactTIL.setEnabled(false);
        staffCountTIL.setEnabled(false);
        stateTIL.setEnabled(false);
        billTIL.setEnabled(false);
        gasUnitCostTIL.setEnabled(false);
        rentTIL.setEnabled(false);
        electrcityTIL.setEnabled(false);
        cityTIL.setEnabled(false);
        pincodeTIL.setEnabled(false);
        messTypeTIL.setEnabled(false);
        messRateTIL.setEnabled(false);
        personalContactTIL.setEnabled(false);
        personalEmailTIL.setEnabled(false);
        emergencyStayRateTIL.setEnabled(false);
        securityDepositTIL.setEnabled(false);
        lockingPeriodTIL.setEnabled(false);

        /*backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPGActivity.this, HomePageActivity.class));
                finish();
            }
        });*/

        /*appliance = findViewById(R.id.applianceTextView);
        rooms = findViewById(R.id.roomTextView);*/

        saveButton = findViewById(R.id.saveButton);

        view = findViewById(R.id.myPgLayout);

        pgAvailableForTextView = findViewById(R.id.pgAvailableForTextView);



        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MyPGActivity.this);
                builder.setTitle("Set Location?");
                builder.setMessage("Are you at your PG's Location?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        {

                            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                            final ProgressDialog progressDialog1 = ProgressDialog.show(MyPGActivity.this, "Getting Required Data", "Please make sure you are connected to network", true);

                            listener = new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {

                                    progressDialog1.dismiss();

                                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseAuth.getCurrentUser().getUid() + "/Attendance/");
                                    databaseReference1.child("Latitude").setValue(Double.toString(location.getLatitude()));
                                    databaseReference1.child("Longitude").setValue(Double.toString(location.getLongitude())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(MyPGActivity.this, "Coordinates Stored", Toast.LENGTH_SHORT).show();
                                            locationManager.removeUpdates(listener);

                                        }
                                    });

                                }

                                @Override
                                public void onStatusChanged(String s, int i, Bundle bundle) {

                                }

                                @Override
                                public void onProviderEnabled(String s) {

                                }

                                @Override
                                public void onProviderDisabled(String s) {

                                }
                            };

                            if (ActivityCompat.checkSelfPermission(MyPGActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyPGActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                                    Manifest.permission.INTERNET}
                                            , 10);
                                }
                                progressDialog1.dismiss();
                                return;
                            }

                            if (ActivityCompat.checkSelfPermission(MyPGActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyPGActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, listener);

                            }


                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MyPGActivity.this, "Go to your PG's location and Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });

        billTakeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = getLayoutInflater().inflate(R.layout.dialog_type_of_bills,null);
                electricityCheckBox = viewDialog.findViewById(R.id.electrictiyCheckBox);
                wifiCheckBox = viewDialog.findViewById(R.id.wifiCheckBox);
                gasCheckBox = viewDialog.findViewById(R.id.gasCheckBox);

                final View titleView = getLayoutInflater().inflate(R.layout.custom_titletypeofbill, null);
                customTitle = titleView.findViewById(R.id.typesOfBillTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(MyPGActivity.this);
                builder.setCustomTitle(customTitle);
                builder.setView(viewDialog);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        electricityIsChecked = false;
                        wifiIsChecked = false;
                        gasIsChecked = false;

                        typesOfBills = "";


                        if(electricityCheckBox.isChecked())
                        {
                            typesOfBills = typesOfBills + "Electricity" + "    ";
                            electricityIsChecked = true;
                        }

                        if(wifiCheckBox.isChecked())
                        {
                            typesOfBills = typesOfBills + "WiFi" + "    ";
                            wifiIsChecked = true;
                        }

                        if(gasCheckBox.isChecked())
                        {
                            typesOfBills = typesOfBills + "Gas" + "    ";
                            gasIsChecked = true;
                        }

                        billFirstTextView.setText(typesOfBills);



                    }
                });

                builder.setNegativeButton("Cancel", null );

                builder.show();



            }
        });

        tenantsPreferredLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(MyPGActivity.this, view);

                getMenuInflater().inflate(R.menu.preferred_tenants_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.studentMenu:
                            {
                                tenantsPreferred = "";
                                tenantsPreferred += "Student";
                                break;
                            }


                            case R.id.workingProffesionalMenu :
                            {
                                tenantsPreferred = "";
                                tenantsPreferred += "Working Proffesional";
                                break;
                            }




                            }

                        tenantsPreferredTextView.setText(tenantsPreferred);


                        return true;
                    }
                });

                popupMenu.show();


            }
        });



        pgAvailableForLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PopupMenu popupMenu = new PopupMenu(MyPGActivity.this, view);

                getMenuInflater().inflate(R.menu.pg_available_for_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.allBoysMenu:
                            {

                                pgAvailableFor = "";
                                pgAvailableFor += "All Boys";
                                break;
                            }


                            case R.id.allGirlsMenu :
                            {

                                pgAvailableFor = "";
                                pgAvailableFor += "All Girls";
                                break;
                            }

                            case R.id.anyMenu :
                            {

                                pgAvailableFor = "";
                                pgAvailableFor += "Any";
                                break;
                            }




                        }
                        pgAvailableForTextView.setText(pgAvailableFor);


                        return true;
                    }
                });



                popupMenu.show();


            }
        });

        lastLateCheckInLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MyPGActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        lastlateCheckInTime.setText(selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //databaseReference.keepSynced(true);
                String name1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("pgName").getValue(String.class);
                String landmark1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("landmark").getValue(String.class);
                String lastEntryTime1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("lastEntryTime").getValue(String.class);
                String maxOccupancy1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("maxOccupancy").getValue(String.class);
                String bathroom1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("noOfBathroom").getValue(String.class);
                String room1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("noOfRooms").getValue(String.class);
                String ownername1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("ownername").getValue(String.class);
                String contact1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("pgContact").getValue(String.class);
                String staffCount1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("staffCount").getValue(String.class);
                String rentDueDate1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("rentDueDate").getValue(String.class);
                String billDueDate1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("billDueDate").getValue(String.class);
                String electricityUnitCost1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("electricityUnitCost").getValue(String.class);
                String City = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("city").getValue(String.class);
                String Pincode = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("pincode").getValue(String.class);
                String gasCost = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("gasUnitCOst").getValue(String.class);
                String MessType = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("messType").getValue(String.class);
                String MessRate = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("messRate").getValue(String.class);
                String PersonalContact = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("personalContact").getValue(String.class);
                String PersonalEmail = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("personalEmail").getValue(String.class);
                String typeOfBills1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("typeOfBills").getValue(String.class);
                String tenantsPreferred1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("tenantsPreferred").getValue(String.class);
                String emergencyStayRate1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Detalis").child("emergencyStayRate").getValue(String.class);
                String securityDeposit1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("securityDeposit").getValue(String.class);
                String lockingPeriod1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("lockingPeriod").getValue(String.class);
                String addressLine1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("addressLine1").getValue(String.class);
                String addressLine2 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("addressLine2").getValue(String.class);
                String state1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("state").getValue(String.class);
                String pgAvailableFor1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("pgAvailableFor").getValue(String.class);
                String lastLateCheckIn1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("lastLateCheckIn").getValue(String.class);

                pgName.setText(name1);
                landmark.setText(landmark1);
                lastEntryTimeTextView.setText(lastEntryTime1);
                maxOccupancy.setText(maxOccupancy1);
                bathroom.setText(bathroom1);
                room.setText(room1);
                ownerName.setText(ownername1);
                contact.setText(contact1);
                staffCount.setText(staffCount1);
                electricityUnitCost.setText(electricityUnitCost1);
                rentDueDate.setText(rentDueDate1);
                billDueDate.setText(billDueDate1);
                billFirstTextView.setText(typeOfBills1);
                emergencyStayRate.setText(emergencyStayRate1);
                tenantsPreferredTextView.setText(tenantsPreferred1);
                securityDeposit.setText(securityDeposit1);
                lockingPeriod.setText(lockingPeriod1);
                addressLine1ET.setText(addressLine1);
                addressLine2ET.setText(addressLine2);
                stateEditText.setText(state1);
                pgAvailableForTextView.setText(pgAvailableFor1);
                lastlateCheckInTime.setText(lastLateCheckIn1);

                city.setText(City);
                pincode.setText(Pincode);
                gasUnitCOst.setText(gasCost);
                messType.setText(MessType);
                messRate.setText(MessRate);
                personalContact.setText(PersonalContact);
                personalEmail.setText(PersonalEmail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyPGActivity.this, "Failed to update!", Toast.LENGTH_SHORT).show();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingPg();
                saveButton.hide();
                editButton.show();


                pgNameTIL.setHintAnimationEnabled(false);
                landmarkTIL.setHintAnimationEnabled(false);
                address1TIL.setHintAnimationEnabled(false);
                pgEmailTIL.setHintAnimationEnabled(false);
                address2TIL.setHintAnimationEnabled(false);
                maxOccupancyTIL.setHintAnimationEnabled(false);
                bathroomTIL.setHintAnimationEnabled(false);
                roomTIL.setHintAnimationEnabled(false);
                ownerNameTIL.setHintAnimationEnabled(false);
                contactTIL.setHintAnimationEnabled(false);
                staffCountTIL.setHintAnimationEnabled(false);
                stateTIL.setHintAnimationEnabled(false);
                billTIL.setHintAnimationEnabled(false);
                rentTIL.setHintAnimationEnabled(false);
                electrcityTIL.setHintAnimationEnabled(false);
                cityTIL.setHintAnimationEnabled(false);
                pincodeTIL.setHintAnimationEnabled(false);
                gasUnitCostTIL.setHintAnimationEnabled(false);
                messTypeTIL.setHintAnimationEnabled(false);
                messRateTIL.setHintAnimationEnabled(false);
                personalContactTIL.setHintAnimationEnabled(false);
                personalEmailTIL.setHintAnimationEnabled(false);
                emergencyStayRateTIL.setHintAnimationEnabled(false);
                securityDepositTIL.setHintAnimationEnabled(false);
                lockingPeriodTIL.setHintAnimationEnabled(false);


                pgAvailableForLinearLayout.setEnabled(false);
                lastEntryTimeLinearLayout.setEnabled(false);
                tenantsPreferredLinearLayout.setEnabled(false);
                lastLateCheckInLinearLayout.setEnabled(false);
                billTakeLinearLayout.setEnabled(false);





                pgNameTIL.setEnabled(false);
                landmarkTIL.setEnabled(false);
                address1TIL.setEnabled(false);
                address2TIL.setEnabled(false);
                maxOccupancyTIL.setEnabled(false);
                bathroomTIL.setEnabled(false);
                roomTIL.setEnabled(false);
                pgEmailTIL.setEnabled(false);
                ownerNameTIL.setEnabled(false);
                gasUnitCostTIL.setEnabled(false);
                contactTIL.setEnabled(false);
                stateTIL.setEnabled(false);
                staffCountTIL.setEnabled(false);
                billTIL.setEnabled(false);
                rentTIL.setEnabled(false);
                electrcityTIL.setEnabled(false);
                cityTIL.setEnabled(false);
                pincodeTIL.setEnabled(false);
                messTypeTIL.setEnabled(false);
                messRateTIL.setEnabled(false);
                personalContactTIL.setEnabled(false);
                personalEmailTIL.setEnabled(false);
                emergencyStayRateTIL.setEnabled(false);
                securityDepositTIL.setEnabled(false);
                lockingPeriodTIL.setEnabled(false);

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pgName.setEnabled(true);

                editButton.hide();
                saveButton.show();

                pgNameTIL.setHintAnimationEnabled(true);
                landmarkTIL.setHintAnimationEnabled(true);
                address1TIL.setHintAnimationEnabled(true);
                pgEmailTIL.setHintAnimationEnabled(true);
                address2TIL.setHintAnimationEnabled(true);
                maxOccupancyTIL.setHintAnimationEnabled(true);
                bathroomTIL.setHintAnimationEnabled(true);
                roomTIL.setHintAnimationEnabled(true);
                ownerNameTIL.setHintAnimationEnabled(true);
                contactTIL.setHintAnimationEnabled(true);
                stateTIL.setHintAnimationEnabled(true);
                staffCountTIL.setHintAnimationEnabled(true);
                billTIL.setHintAnimationEnabled(true);
                rentTIL.setHintAnimationEnabled(true);
                electrcityTIL.setHintAnimationEnabled(true);
                cityTIL.setHintAnimationEnabled(true);
                pincodeTIL.setHintAnimationEnabled(true);
                gasUnitCostTIL.setHintAnimationEnabled(true);
                messTypeTIL.setHintAnimationEnabled(true);
                messRateTIL.setHintAnimationEnabled(true);
                personalContactTIL.setHintAnimationEnabled(true);
                personalEmailTIL.setHintAnimationEnabled(true);
                emergencyStayRateTIL.setHintAnimationEnabled(true);
                securityDepositTIL.setHintAnimationEnabled(true);
                lockingPeriodTIL.setHintAnimationEnabled(true);

                pgAvailableForLinearLayout.setEnabled(true);
                tenantsPreferredLinearLayout.setEnabled(true);
                lastEntryTimeLinearLayout.setEnabled(true);
                lastLateCheckInLinearLayout.setEnabled(true);
                billTakeLinearLayout.setEnabled(true);





                pgNameTIL.setEnabled(true);
                landmarkTIL.setEnabled(true);
                address1TIL.setEnabled(true);
                address2TIL.setEnabled(true);
                pgEmailTIL.setEnabled(true);
                maxOccupancyTIL.setEnabled(true);
                bathroomTIL.setEnabled(true);
                roomTIL.setEnabled(true);
                ownerNameTIL.setEnabled(true);
                contactTIL.setEnabled(true);
                staffCountTIL.setEnabled(true);
                gasUnitCostTIL.setEnabled(true);
                stateTIL.setEnabled(true);
                billTIL.setEnabled(true);
                rentTIL.setEnabled(true);
                electrcityTIL.setEnabled(true);
                cityTIL.setEnabled(true);
                pincodeTIL.setEnabled(true);
                messTypeTIL.setEnabled(true);
                messRateTIL.setEnabled(true);
                personalContactTIL.setEnabled(true);
                personalEmailTIL.setEnabled(true);
                emergencyStayRateTIL.setEnabled(true);
                securityDepositTIL.setEnabled(true);
                lockingPeriodTIL.setEnabled(true);

            }
        });

        saveButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(MyPGActivity.this, "Save", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void addingPg() {

        String pgNameString = pgName.getText().toString().trim();
        String ownerNameString = ownerName.getText().toString().trim();
        String contactString = contact.getText().toString().trim();
        String landmarkString = landmark.getText().toString().trim();
        String lastEntryString = lastEntryTimeTextView.getText().toString().trim();
        String maxOccupancyString = maxOccupancy.getText().toString().trim();
        String staffCountString = staffCount.getText().toString().trim();
        String roomString = room.getText().toString().trim();
        String bathroomString = bathroom.getText().toString().trim();
        String rentDueDateString = rentDueDate.getText().toString().trim();
        String billDueDateString = billDueDate.getText().toString().trim();
        String electricityUnitCostString = electricityUnitCost.getText().toString().trim();
        typesOfBills = billFirstTextView.getText().toString().trim();
        tenantsPreferred = tenantsPreferredTextView.getText().toString().trim();
        String securityDepositString = securityDeposit.getText().toString().trim();
        String lockingPeriodString = lockingPeriod.getText().toString().trim();
        String addressLine1String = addressLine1ET.getText().toString().trim();
        String addressLine2String = addressLine2ET.getText().toString().trim();
        String stateString = stateEditText.getText().toString().trim();
        pgAvailableFor = pgAvailableForTextView.getText().toString().trim();
        String lastLateCheckIn = lastlateCheckInTime.getText().toString().trim();

        String emergencyStayRateString = emergencyStayRate.getText().toString().trim();

        String City = city.getText().toString().trim();
        String Pincode = pincode.getText().toString().trim();
        String gasCost = gasUnitCOst.getText().toString().trim();
        String typeMess = messType.getText().toString().trim();
        String rateMess = messRate.getText().toString().trim();
        String contactPerson = personalContact.getText().toString().trim();
        String emailPerson = personalEmail.getText().toString().trim();

        //Getting current user information
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "PG/"+user.getUid();

        //adding database info for the current user
        databaseReference = FirebaseDatabase.getInstance().getReference(uid);




        final ProgressDialog progressDialog = ProgressDialog.show(MyPGActivity.this,"","Saving..",true);

        PG pg = new PG(pgNameString, ownerNameString, contactString, landmarkString, lastEntryString, maxOccupancyString, staffCountString, roomString, bathroomString, rentDueDateString, billDueDateString, electricityUnitCostString, firebaseUser.getEmail(), City, Pincode, gasCost, typeMess, rateMess, contactPerson, emailPerson, typesOfBills, electricityIsChecked, wifiIsChecked, gasIsChecked, emergencyStayRateString, tenantsPreferred, securityDepositString, lockingPeriodString, addressLine1String, addressLine2String, stateString , pgAvailableFor, lastLateCheckIn);
        databaseReference.child("PG Details").setValue(pg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                snackbar = Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(MyPGActivity.this, R.color.DarkGreen));
                snackbar.show();
                saveButton.hide();
                saveButton.postDelayed(new Runnable() {
                    public void run() {
                        saveButton.show();
                    }
                }, 1800);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                snackbar = Snackbar.make(view, "Failed ! Unable to Save", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(MyPGActivity.this, R.color.red));
                snackbar.show();
                saveButton.hide();
                saveButton.postDelayed(new Runnable() {
                    public void run() {
                        saveButton.show();
                    }
                }, 1800);
            }
        });


    }

    @Override
    public void onBackPressed() {
        //     AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        new AlertDialog.Builder(MyPGActivity.this).setTitle("Save Details")
                .setMessage("Do you want to save details before going to home page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addingPg();
                        startActivity(new Intent(MyPGActivity.this, HomePageActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyPGActivity.this, HomePageActivity.class));
                        finish();
                    }
                })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show();
    }

}