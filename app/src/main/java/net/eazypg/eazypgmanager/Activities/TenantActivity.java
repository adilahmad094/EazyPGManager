package net.eazypg.eazypgmanager.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.DetailsClasses.UnderProcessTenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;


public class TenantActivity extends AppCompatActivity {


    ListView listView;
    View emptyList;
    List<TenantDetails> tenantDetailsList;

    RadioGroup radioGroup;
    RadioButton radioButton;

    String eazyPGId;

    EditText roomEditText;
    TextView custom_title;

    CheckBox acCheckBox, washroomCheckBox, balconyCheckBox, ventilationCheckBox, largeRoomCheckBox, cornerRoomCheckBox;

    String tagString = "";
    String floors;
    EditText floorsEditText;

    String thisRoom;

    boolean flag;

    EditText input;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    ImageView qrImage;
    Button addTenant;

    Button previousTenants , ok , cancel;
    EditText name, phone, room, rentAmount , email;

    TextView dateOfJoining, totalBedTextView, vacantBedTextView;

    Snackbar snackbar;
    View view;

    List<String> rooms;

    String pgName, roomType;
    long tenantNumber;
    int roomTypeNumber;

    LayoutInflater inflater;

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);

        Fabric.with(this, new Crashlytics());


        Toolbar toolbar = findViewById(R.id.tenantToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        input = new EditText(this);

        totalBedTextView = findViewById(R.id.totalBedTextView);
        vacantBedTextView = findViewById(R.id.vacantBedTextView);

        listView = findViewById(R.id.listViewTenant);
        emptyList = findViewById(R.id.emptyListTenant);
        listView.setEmptyView(emptyList);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference();

        previousTenants = findViewById(R.id.previousTenants);
        rooms = new ArrayList<>();

        inflater = getLayoutInflater();

        listView = findViewById(R.id.listViewTenant);
        addTenant = findViewById(R.id.addTenant);
        view = findViewById(R.id.tenantLayout);

        name = findViewById(R.id.tenantNameEditText);
        phone = findViewById(R.id.tenantPhoneEditText);
        room = findViewById(R.id.tenantRoomEditText);
        dateOfJoining = findViewById(R.id.tenantDateEditText);
        rentAmount = findViewById(R.id.tenantRentEditText);

        backButton = findViewById(R.id.imageView3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TenantActivity.this,HomePageActivity.class));
                finish();
            }
        });

        snackbar = Snackbar.make(view, "Tap item to view.", Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(TenantActivity.this, R.color.DarkGreen));
        snackbar.show();

        tenantDetailsList = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Tenants/CurrentTenants/");
        databaseReference2 = firebaseDatabase.getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tenantDetailsList.clear();

                for (DataSnapshot dataSnapshotTenant : dataSnapshot.getChildren()) {

                    TenantDetails tenantDetails = dataSnapshotTenant.getValue(TenantDetails.class);
                    tenantDetailsList.add(tenantDetails);

                }

                TenantDetailList adapter = new TenantDetailList(TenantActivity.this, tenantDetailsList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eazyPGId = dataSnapshot.child("EazyPGID").getValue(String.class);

                if (dataSnapshot.child("PG Details").child("maxOccupancy").getValue(String.class) != null && !dataSnapshot.child("PG Details").child("maxOccupancy").getValue(String.class).isEmpty()) {
                    String maxOccupancy = dataSnapshot.child("PG Details").child("maxOccupancy").getValue(String.class);
                    totalBedTextView.setText(maxOccupancy);
                    vacantBedTextView.setText(Integer.toString(Integer.parseInt(maxOccupancy) - tenantDetailsList.size()));
                }
                else {
                    totalBedTextView.setText("Tap to add");
                    vacantBedTextView.setText("Null");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        totalBedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalBedTextView.getText().toString().equalsIgnoreCase("Tap to add")) {

                    if(input.getParent()!=null) {
                        ((ViewGroup) input.getParent()).removeView(input);
                    }

                    input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);

                    AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                    builder.setTitle("Total Rooms")
                            .setIcon(R.drawable.ic_edit_black_24dp)
                            .setMessage("Enter total number of Tenants : ");

                    builder.setView(input);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            databaseReference2.child("PG Details").child("maxOccupancy").setValue(input.getText().toString());
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
            }
        });

        databaseReference2.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rooms.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    rooms.add(snapshot.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference1 = FirebaseDatabase.getInstance().getReference("PG/" + firebaseUser.getUid() + "/PG Details/");

        addTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // final View viewDialog = inflater.inflate(R.layout.dialog_tenant, null);
                final Dialog dialog = new Dialog(TenantActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = dialog.getWindow();
                dialog.setContentView(R.layout.dialog_tenant);
                name = dialog.findViewById(R.id.tenantNameEditText);
                phone = dialog.findViewById(R.id.tenantPhoneEditText);
                room = dialog.findViewById(R.id.tenantRoomEditText);
                dateOfJoining = dialog.findViewById(R.id.dateOfJoining);
                rentAmount = dialog.findViewById(R.id.tenantRentEditText);
                ok = dialog.findViewById(R.id.okButton);
                cancel = dialog.findViewById(R.id.cancelButton);

                window.setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        pgName = dataSnapshot.child("pgName").getValue(String.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


                final Calendar calendar = Calendar.getInstance();


                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        dateOfJoining.setText(sdf.format(calendar.getTime()));
                    }
                };

                dateOfJoining.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(TenantActivity.this, date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        thisRoom = room.getText().toString().trim();

                        for (String room1 : rooms) {

                            if (room1.equalsIgnoreCase(room.getText().toString())) {
                                flag = true;
                                break;
                            }

                        }

                        if (flag) {

                            dialog.cancel();
                            dialog.dismiss();

                            databaseReference2 = firebaseDatabase.getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Rooms/" + room.getText().toString());

                            databaseReference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    roomType = dataSnapshot.child("Room Type").getValue(String.class);

                                    databaseReference2.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                            tenantNumber = dataSnapshot.child("Tenant").child("CurrentTenants").getChildrenCount();

                                            Log.e("TenantNumber", tenantNumber + "");

                                        }

                                        @Override
                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    Log.e("RoomType", roomType + "");

                                    switch (roomType) {

                                        case "One Bed" : roomTypeNumber = 1;    break;
                                        case "Two Bed" : roomTypeNumber = 2;    break;
                                        case "Three Bed" : roomTypeNumber = 3;  break;

                                    }

                                    if (tenantNumber < roomTypeNumber) {

                                        databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if (dataSnapshot.child("Tenants").child("UnderProcess").hasChild(phone.getText().toString())) {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                                                    builder.setMessage("Tenant with this number is already invited");
                                                    builder.setTitle("Error");
                                                    builder.setNeutralButton("Ok", null);
                                                    builder.show();

                                                }
                                                else {
                                                    UnderProcessTenantDetails tenantDetails = new UnderProcessTenantDetails(name.getText().toString(), phone.getText().toString(), room.getText().toString(), dateOfJoining.getText().toString(), rentAmount.getText().toString(), false);
                                                    databaseReference2.child("Tenants").child("UnderProcess").child(phone.getText().toString()).setValue(tenantDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                                                            builder.setTitle("Tenant Invited");
                                                            builder.setMessage("An invitation message with link and details has been sent to " + name.getText().toString() + ".");
                                                            builder.setNeutralButton("Ok", null);
                                                            builder.show();

                                                            //ToDo: Firebase Dynamic link will be sent to tenant using MSG91

                                                            MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                                                            msg91.composeMessage("EazyPG", "Hi " + name.getText().toString() + ". Welcome to " + pgName + ". Please enter 10 digit code " + eazyPGId + " in your PG ID. Get your EazyPG App now https://goo.gl/M3jEhQ");
                                                            msg91.to(phone.getText().toString());
                                                            String sendStatus = msg91.send();


                                                        }
                                                    });


                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else {

                                        Toast.makeText(TenantActivity.this, "Room is full", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                            builder.setTitle("Room not available");
                            builder.setTitle("Room number " + room.getText().toString() + " is not in the list. Do you want to add it?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    final View viewDialog = inflater.inflate(R.layout.dialog_room, null);
                                    radioGroup = viewDialog.findViewById(R.id.radioGroup);
                                    roomEditText = viewDialog.findViewById(R.id.roomNoEditText);

                                    final View titleView = inflater.inflate(R.layout.custom_titleroom, null);
                                    custom_title = titleView.findViewById(R.id.roomCustomTitle);

                                    roomEditText.setText(thisRoom);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                                    builder.setCustomTitle(custom_title);
                                    builder.setView(viewDialog);

                                    tagString = "";

                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            int selectedButtonId = radioGroup.getCheckedRadioButtonId();
                                            radioButton = viewDialog.findViewById(selectedButtonId);

                                            floorsEditText = viewDialog.findViewById(R.id.floorEditText);

                                            acCheckBox = viewDialog.findViewById(R.id.acCheckBox);
                                            washroomCheckBox = viewDialog.findViewById(R.id.washroomCheckBox);
                                            balconyCheckBox = viewDialog.findViewById(R.id.balconyCheckBox);
                                            ventilationCheckBox = viewDialog.findViewById(R.id.ventilationCheckBox);
                                            largeRoomCheckBox = viewDialog.findViewById(R.id.largeRoomCheckBox);
                                            cornerRoomCheckBox = viewDialog.findViewById(R.id.cornerRoomCheckBox);

                                            switch (floorsEditText.getText().toString()) {

                                                case "1" :
                                                    floors = "1st";
                                                    break;

                                                case "2" :
                                                    floors = "2nd";
                                                    break;

                                                case "3" :
                                                    floors = "3rd";
                                                    break;

                                                case "4" :
                                                    floors = "4th";
                                                    break;

                                                case "5" :
                                                    floors = "5th";
                                                    break;

                                                case "6" :
                                                    floors = "6th";
                                                    break;

                                                case "7" :
                                                    floors = "7th";
                                                    break;

                                                case "8" :
                                                    floors = "8th";
                                                    break;

                                                case "9" :
                                                    floors = "9th";
                                                    break;
                                            }

                                            if (acCheckBox.isChecked())
                                            {
                                                tagString += "AC";
                                            }
                                            if (washroomCheckBox.isChecked())
                                            {
                                                tagString += "Washroom";
                                            }
                                            if (balconyCheckBox.isChecked())
                                            {
                                                tagString += "Balcony";
                                            }
                                            if (ventilationCheckBox.isChecked())
                                            {
                                                tagString += "Ventilation";
                                            }
                                            if (largeRoomCheckBox.isChecked())
                                            {
                                                tagString += "Large Room";
                                            }
                                            if (cornerRoomCheckBox.isChecked())
                                            {
                                                tagString += "Corner Room";
                                            }


                                            final String room = roomEditText.getText().toString();

                                            if(selectedButtonId == -1 || room.isEmpty())
                                            {
                                                Toast.makeText(TenantActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                                            }
                                            else {

                                                String roomType = radioButton.getText().toString();
                                                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());
                                                databaseReference1.child("Rooms").child(room).child("Tags").setValue(tagString);

                                                Log.e("Floors", floors);

                                                databaseReference1.child("Rooms").child(room).child("Floors").setValue(floors);

                                                databaseReference1.child("Rooms").child(room).child("Room Type").setValue(roomType).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                                                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                if (dataSnapshot.child("Tenants").child("UnderProcess").hasChild(phone.getText().toString())) {

                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                                                                    builder.setMessage("Tenant with this number is already invited");
                                                                    builder.setTitle("Error");
                                                                    builder.setNeutralButton("Ok", null);
                                                                    builder.show();

                                                                }
                                                                else {
                                                                    UnderProcessTenantDetails tenantDetails = new UnderProcessTenantDetails(name.getText().toString(), phone.getText().toString(), room, dateOfJoining.getText().toString(), rentAmount.getText().toString(), false);
                                                                    databaseReference2.child("Tenants").child("UnderProcess").child(phone.getText().toString()).setValue(tenantDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(TenantActivity.this);
                                                                            builder.setTitle("Tenant Invited");
                                                                            builder.setMessage("An invitation message with link and details has been sent to " + name.getText().toString() + ".");
                                                                            builder.setNeutralButton("Ok", null);
                                                                            builder.show();

                                                                            //ToDo: Firebase Dynamic link will be sent to tenant using MSG91

                                                                            MSG91 msg91 = new MSG91("163776AiifTBEVMZl5aae0bce");
                                                                            msg91.composeMessage("EazyPG", "Hi " + name.getText().toString() + ". Welcome to " + pgName + ". Please enter 10 digit code " + eazyPGId + " in your PG ID. Get your EazyPG App now https://goo.gl/M3jEhQ");
                                                                            msg91.to(phone.getText().toString());
                                                                            String sendStatus = msg91.send();


                                                                        }
                                                                    });


                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                });
                                            }

                                        }
                                    });

                                    builder.setNegativeButton("Cancel", null);
                                    builder.show();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();

                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        previousTenants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(TenantActivity.this, PreviousTenantsActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(TenantActivity.this, HomePageActivity.class));
        finish();
    }

}
