package net.eazypg.eazypgmanager.Activities;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import io.fabric.sdk.android.Fabric;


public class MyPGActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, databaseReference1;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    Button setLocationButton;

    TextInputLayout pgNameTIL;
    TextInputLayout bioTIL;
    TextInputLayout genderTIL;
    TextInputLayout landmarkTIL;
    TextInputLayout  lastEntryTIL;
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


    TextInputEditText pgName;
    TextInputEditText bio;
    TextInputEditText gender;
    TextInputEditText landmark;
    TextInputEditText lastEntry;
    TextInputEditText addressLine1;
    TextInputEditText addressLine2;
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
    Snackbar snackbar;

    boolean electricityIsChecked;
    boolean wifiIsChecked;
    boolean gasIsChecked;
    String typesOfBills = "";

    View view;
    ImageView backButton;
    LocationManager locationManager;
    LocationListener listener;

    CheckBox electricityCheckBox, wifiCheckBox, gasCheckBox;

    LinearLayout billTakeLinearLayout;

    TextView customTitle;

    TextView billFirstTextView;
    FloatingActionButton saveButton , editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pg);

        Fabric.with(this, new Crashlytics());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

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

        billFirstTextView = findViewById(R.id.billFirstTextView);

        pgName = findViewById(R.id.pgNameTextView);
        bio = findViewById(R.id.bioTextView);
        gender = findViewById(R.id.genderTextView);
        landmark = findViewById(R.id.landmarkTextView);
        lastEntry = findViewById(R.id.lastEntryTimeTextView);
        addressLine1 = findViewById(R.id.addressLine1);
        addressLine2 = findViewById(R.id.addressLine2);
        maxOccupancy = findViewById(R.id.maxOccupancyTextView);
        bathroom = findViewById(R.id.noOfBathroomTextView);
        room = findViewById(R.id.noOfRoomTextView);
        ownerName = findViewById(R.id.ownerNameTextView);
        contact = findViewById(R.id.pgContactTextView);
        staffCount = findViewById(R.id.staffCountTextView);
        rentDueDate = findViewById(R.id.rentDueDateEditText);
        billDueDate = findViewById(R.id.billDueDateEditText);
        electricityUnitCost = findViewById(R.id.electricityUnitCostEditText);

        city = findViewById(R.id.cityEditText);
        pincode = findViewById(R.id.pincodeEditText);
        gasUnitCOst = findViewById(R.id.gasRateEditText);
        messType = findViewById(R.id.messTypeEditText);
        messRate = findViewById(R.id.messRateEditText);
        personalContact = findViewById(R.id.ownerPersonalContactTextView);
        personalEmail = findViewById(R.id.personalEmailEditText);


        pgNameTIL = findViewById(R.id.pgNameTIL);
        bioTIL = findViewById(R.id.bioTIL);
        genderTIL = findViewById(R.id.genderTIL);
        landmarkTIL = findViewById(R.id.nearestLandmarkTIL);
        lastEntryTIL = findViewById(R.id.lastEntryTIL);
        address1TIL = findViewById(R.id.addressLine1TIL);
        address2TIL = findViewById(R.id.addressLine2TIL);
        maxOccupancyTIL = findViewById(R.id.maxOccupancyTIL);
        bathroomTIL = findViewById(R.id.bathroomTID);
        roomTIL = findViewById(R.id.roomTIL);
        ownerNameTIL = findViewById(R.id.ownerNameTIL);
        contactTIL = findViewById(R.id.pgContactTIL);
        staffCountTIL = findViewById(R.id.numberOfStaffTIL);
        billTIL = findViewById(R.id.billTIL);
        rentTIL = findViewById(R.id.rentTIL);
        electrcityTIL = findViewById(R.id.electricityUnitTIL);
        cityTIL = findViewById(R.id.cityTIL);
        pincodeTIL = findViewById(R.id.pincodeTIL);
        gasUnitCostTIL = findViewById(R.id.gasTIL);
        messTypeTIL = findViewById(R.id.messTypeTIL);
        messRateTIL = findViewById(R.id.messRateTIL);
        personalContactTIL = findViewById(R.id.personalContactTIL);
        personalEmailTIL = findViewById(R.id.personalEmailTIL);

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
        editButton = findViewById(R.id.editButton);

        view = findViewById(R.id.myPgLayout);


        pgNameTIL.setAnimationCacheEnabled(false);
        bioTIL.setAnimationCacheEnabled(false);
        genderTIL.setAnimationCacheEnabled(false);
        landmarkTIL.setAnimationCacheEnabled(false);
        lastEntryTIL.setAnimationCacheEnabled(false);
        address1TIL.setAnimationCacheEnabled(false);
        address2TIL.setAnimationCacheEnabled(false);
        maxOccupancyTIL.setAnimationCacheEnabled(false);
        bathroomTIL.setAnimationCacheEnabled(false);
        roomTIL.setAnimationCacheEnabled(false);
        ownerNameTIL.setAnimationCacheEnabled(false);
        contactTIL.setAnimationCacheEnabled(false);
        staffCountTIL.setAnimationCacheEnabled(false);
        billTIL.setAnimationCacheEnabled(false);
        rentTIL.setAnimationCacheEnabled(false);
        electrcityTIL.setAnimationCacheEnabled(false);
        cityTIL.setAnimationCacheEnabled(false);
        pincodeTIL.setAnimationCacheEnabled(false);
        gasUnitCostTIL.setAnimationCacheEnabled(false);
        messTypeTIL.setAnimationCacheEnabled(false);
        messRateTIL.setAnimationCacheEnabled(false);
        personalContactTIL.setAnimationCacheEnabled(false);
        personalEmailTIL.setAnimationCacheEnabled(false);

        pgNameTIL.setEnabled(false);
        bioTIL.setEnabled(false);
        genderTIL.setEnabled(false);
        landmarkTIL.setEnabled(false);
        address1TIL.setEnabled(false);
        address2TIL.setEnabled(false);
        maxOccupancyTIL.setEnabled(false);
        bathroomTIL.setEnabled(false);
        roomTIL.setEnabled(false);
        ownerNameTIL.setEnabled(false);
        contactTIL.setEnabled(false);
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //databaseReference.keepSynced(true);
                String name1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("pgName").getValue(String.class);
                String bio1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("bio").getValue(String.class);
                String gender1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("gender").getValue(String.class);
                String landmark1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("landmark").getValue(String.class);
                String lastEntryTime1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("lastEntryTime").getValue(String.class);
                String address1 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("addressLine1").getValue(String.class);
              //  String address2 = dataSnapshot.child(firebaseUser.getUid()).child("PG Details").child("addressLine2").getValue(String.class);
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


                pgName.setText(name1);
                bio.setText(bio1);
                gender.setText(gender1);
                landmark.setText(landmark1);
                lastEntry.setText(lastEntryTime1);
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
                addressLine1.setText(address1);
              //  addressLine2.setText(address2);
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
                editButton.hide();

                pgNameTIL.setAnimationCacheEnabled(false);
                bioTIL.setAnimationCacheEnabled(false);
                genderTIL.setAnimationCacheEnabled(false);
                landmarkTIL.setAnimationCacheEnabled(false);
                lastEntryTIL.setAnimationCacheEnabled(false);
                address1TIL.setAnimationCacheEnabled(false);
                address2TIL.setAnimationCacheEnabled(false);
                maxOccupancyTIL.setAnimationCacheEnabled(false);
                bathroomTIL.setAnimationCacheEnabled(false);
                roomTIL.setAnimationCacheEnabled(false);
                ownerNameTIL.setAnimationCacheEnabled(false);
                contactTIL.setAnimationCacheEnabled(false);
                staffCountTIL.setAnimationCacheEnabled(false);
                billTIL.setAnimationCacheEnabled(false);
                rentTIL.setAnimationCacheEnabled(false);
                electrcityTIL.setAnimationCacheEnabled(false);
                cityTIL.setAnimationCacheEnabled(false);
                pincodeTIL.setAnimationCacheEnabled(false);
                gasUnitCostTIL.setAnimationCacheEnabled(false);
                messTypeTIL.setAnimationCacheEnabled(false);
                messRateTIL.setAnimationCacheEnabled(false);
                personalContactTIL.setAnimationCacheEnabled(false);
                personalEmailTIL.setAnimationCacheEnabled(false);

                pgNameTIL.setEnabled(false);
                bioTIL.setEnabled(false);
                genderTIL.setEnabled(false);
                landmarkTIL.setEnabled(false);
                address1TIL.setEnabled(false);
                address2TIL.setEnabled(false);
                maxOccupancyTIL.setEnabled(false);
                bathroomTIL.setEnabled(false);
                roomTIL.setEnabled(false);
                ownerNameTIL.setEnabled(false);
                contactTIL.setEnabled(false);
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



            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pgName.setEnabled(true);

                editButton.hide();
                saveButton.show();

                pgNameTIL.setAnimationCacheEnabled(true);
                bioTIL.setAnimationCacheEnabled(true);
                genderTIL.setAnimationCacheEnabled(true);
                landmarkTIL.setAnimationCacheEnabled(true);
                lastEntryTIL.setAnimationCacheEnabled(true);
                address1TIL.setAnimationCacheEnabled(true);
                address2TIL.setAnimationCacheEnabled(true);
                maxOccupancyTIL.setAnimationCacheEnabled(true);
                bathroomTIL.setAnimationCacheEnabled(true);
                roomTIL.setAnimationCacheEnabled(true);
                ownerNameTIL.setAnimationCacheEnabled(true);
                contactTIL.setAnimationCacheEnabled(true);
                staffCountTIL.setAnimationCacheEnabled(true);
                billTIL.setAnimationCacheEnabled(true);
                rentTIL.setAnimationCacheEnabled(true);
                electrcityTIL.setAnimationCacheEnabled(true);
                cityTIL.setAnimationCacheEnabled(true);
                pincodeTIL.setAnimationCacheEnabled(true);
                gasUnitCostTIL.setAnimationCacheEnabled(true);
                messTypeTIL.setAnimationCacheEnabled(true);
                messRateTIL.setAnimationCacheEnabled(true);
                personalContactTIL.setAnimationCacheEnabled(true);
                personalEmailTIL.setAnimationCacheEnabled(true);

                pgNameTIL.setEnabled(true);
                bioTIL.setEnabled(true);
                genderTIL.setEnabled(true);
                landmarkTIL.setEnabled(true);
                address1TIL.setEnabled(true);
                address2TIL.setEnabled(true);
                maxOccupancyTIL.setEnabled(true);
                bathroomTIL.setEnabled(true);
                roomTIL.setEnabled(true);
                ownerNameTIL.setEnabled(true);
                contactTIL.setEnabled(true);
                staffCountTIL.setEnabled(true);
                billTIL.setEnabled(true);
                rentTIL.setEnabled(true);
                electrcityTIL.setEnabled(true);
                cityTIL.setEnabled(true);
                pincodeTIL.setEnabled(true);
                messTypeTIL.setEnabled(true);
                messRateTIL.setEnabled(true);
                personalContactTIL.setEnabled(true);
                personalEmailTIL.setEnabled(true);

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
        String bioString = bio.getText().toString().trim();
        String address1String = addressLine1.getText().toString().trim();
       // String address2String = addressLine2.getText().toString().trim();
        String ownerNameString = ownerName.getText().toString().trim();
        String contactString = contact.getText().toString().trim();
        String landmarkString = landmark.getText().toString().trim();
        String lastEntryString = lastEntry.getText().toString().trim();
        String genderString = gender.getText().toString().trim();
        String maxOccupancyString = maxOccupancy.getText().toString().trim();
        String staffCountString = staffCount.getText().toString().trim();
        String roomString = room.getText().toString().trim();
        String bathroomString = bathroom.getText().toString().trim();
        String rentDueDateString = rentDueDate.getText().toString().trim();
        String billDueDateString = billDueDate.getText().toString().trim();
        String electricityUnitCostString = electricityUnitCost.getText().toString().trim();
        typesOfBills = billFirstTextView.getText().toString().trim();

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

        PG pg = new PG(pgNameString, bioString, address1String, ownerNameString, contactString, landmarkString, lastEntryString, genderString, maxOccupancyString, staffCountString, roomString, bathroomString, rentDueDateString, billDueDateString, electricityUnitCostString, firebaseUser.getEmail(), City, Pincode, gasCost, typeMess, rateMess, contactPerson, emailPerson, typesOfBills, electricityIsChecked, wifiIsChecked, gasIsChecked);
        databaseReference.child("PG Details").setValue(pg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                snackbar = Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(MyPGActivity.this, R.color.colorPrimaryDark));
                snackbar.show();
                saveButton.hide();
                editButton.hide();
                saveButton.postDelayed(new Runnable() {
                    public void run() {
                        editButton.show();
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
                editButton.hide();
                saveButton.postDelayed(new Runnable() {
                    public void run() {
                        editButton.show();
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