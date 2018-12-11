package net.eazypg.eazypgmanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import net.eazypg.eazypgmanager.DetailList.RoomsDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.ACDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.CCTVDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.D2HDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.DishwasherDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.FanDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.GeyserDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.HeaterDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.InductionDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.IronDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.MicrowaveDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.OtherApplianceDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RODetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RefrigeratorDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RouterDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.TVDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.WashingMachineDetails;
import net.eazypg.eazypgmanager.DetailsClasses.RoomApplianceDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;


public class RoomsActivity extends AppCompatActivity {

    ImageView addRoom;
    TextView custom_title;
    EditText roomEditText, roomTypeEditText;

    EditText floorsEditText;

    View view;
    LayoutInflater inflater;
    String roomType = "";

    RadioGroup radioGroup;
    RadioButton radioButton;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;

    CheckBox acCheckBox, washroomCheckBox, balconyCheckBox, ventilationCheckBox, largeRoomCheckBox, cornerRoomCheckBox;

    List<String> rooms;
    List<String> roomTypeList;
    List<Long> noOfTenantInRoom;
    List<String> tagList;
    List<String> floorList;

    List<TenantDetails> tenantList;

    List<ACDetails> acList;
    List<FanDetails> fanList;
    List<GeyserDetails> geyserList;
    List<WashingMachineDetails> washingMachineList;
    List<RODetails> roList;
    List<DishwasherDetails> dishwasherList;
    List<MicrowaveDetails> microwaveList;
    List<RefrigeratorDetails> refrigeratorList;
    List<TVDetails> tvList;
    List<CCTVDetails> cctvList;
    List<IronDetails> ironList;
    List<InductionDetails> inductionList;
    List<RouterDetails> routerList;
    List<HeaterDetails> heaterList;
    List<D2HDetails> d2HList;
    List<OtherApplianceDetails> otherList;

    String tagString="";
    String floors;

    List<RoomApplianceDetails> roomApplianceDetailsList = new ArrayList<>();

    ListView listView;
    View emptyList;

    TextView totalRoomsTextView, vacantRoomsTextView, semiVacantTextView;

    ImageView backButton;

    int totalRoom, vacantRooms, semiVacantRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Fabric.with(this, new Crashlytics());

        addRoom = findViewById(R.id.addRoom);

        inflater = getLayoutInflater();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
        databaseReference1 = firebaseDatabase.getReference();
        databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PG Details/");

        totalRoomsTextView = findViewById(R.id.totalRoomsTextView);
        vacantRoomsTextView = findViewById(R.id.vacantRoomsTextView);
        semiVacantTextView = findViewById(R.id.semiVacantTextView);

        rooms = new ArrayList<>();
        roomTypeList = new ArrayList<>();
        noOfTenantInRoom = new ArrayList<>();
        tagList = new ArrayList<>();
        floorList = new ArrayList<>();
        tenantList = new ArrayList<>();

        acList = new ArrayList<>();
        fanList = new ArrayList<>();
        geyserList = new ArrayList<>();
        washingMachineList = new ArrayList<>();
        roList = new ArrayList<>();
        dishwasherList = new ArrayList<>();
        microwaveList = new ArrayList<>();
        refrigeratorList = new ArrayList<>();
        tvList = new ArrayList<>();
        cctvList = new ArrayList<>();
        ironList = new ArrayList<>();
        inductionList = new ArrayList<>();
        routerList = new ArrayList<>();
        heaterList = new ArrayList<>();
        d2HList = new ArrayList<>();
        otherList = new ArrayList<>();

        listView = findViewById(R.id.listViewRooms);
        emptyList = findViewById(R.id.emptyListRooms);
        listView.setEmptyView(emptyList);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomsActivity.this, HomePageActivity.class));
                finish();
            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                totalRoom = 0;

                if (dataSnapshot.child("noOfRooms").getValue(String.class) != null) {

                    if (!dataSnapshot.child("noOfRooms").getValue(String.class).isEmpty())
                    totalRoom = Integer.parseInt(dataSnapshot.child("noOfRooms").getValue(String.class));

                    totalRoomsTextView.setText(Integer.toString(totalRoom));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for(String str : rooms){
            getRoomDetails(str);
            getTenantDetails(str);
        }

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/");

        Collections.sort(rooms, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                roomTypeList.clear();
                tagList.clear();
                rooms.clear();
                floorList.clear();

                /*Adding tags in the room rows*/
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String room = snapshot.getKey();
                    rooms.add(room);


                    String roomType = snapshot.child("Room Type").getValue(String.class);
                    roomTypeList.add(roomType);

                    String tags = snapshot.child("Tags").getValue(String.class);
                    tagList.add(tags);

                    String floors = snapshot.child("Floors").getValue(String.class);
                    floorList.add(floors);
                }



                vacantRooms = totalRoom - rooms.size();

                for(int i = 0; i < rooms.size(); i++){

                    final String room = rooms.get(i);

                    final List<TenantDetails> roomTenantList = new ArrayList<>();
                    final DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + rooms.get(i) + "/Tenant/CurrentTenants/");

                    final int finalI = i;
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            long noOfTenant = dataSnapshot.getChildrenCount();
                            noOfTenantInRoom.add(noOfTenant);

                            roomTenantList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                                roomTenantList.add(tenantDetails);
                            }

                            if(roomTenantList.size()==0)
                                vacantRooms++;

                            int maxBed = 0;

                            switch (roomTypeList.get(finalI)) {

                                case "One Bed" : maxBed = 1;    break;
                                case "Two Bed" : maxBed = 2;    break;
                                case "Three Bed" : maxBed = 3;    break;
                                case "Four Bed" : maxBed = 4;    break;
                                case "Five Bed" : maxBed = 5;    break;
                                case "Six Bed" : maxBed = 6;    break;
                                case "Seven Bed" : maxBed = 7;    break;
                                case "Eight Bed" : maxBed = 8;    break;
                                case "Nine Bed" : maxBed = 9;    break;


                            }

                            if (roomTenantList.size() < maxBed) {
                                semiVacantRooms++;
                            }


                            semiVacantTextView.setText(Integer.toString(semiVacantRooms));
                            vacantRoomsTextView.setText(Integer.toString(vacantRooms));


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                RoomsDetailList adapter = new RoomsDetailList(RoomsActivity.this, rooms, roomTypeList, tagList, floorList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View viewDialog = inflater.inflate(R.layout.dialog_room, null);

                roomEditText = viewDialog.findViewById(R.id.roomNoEditText);
                roomTypeEditText = viewDialog.findViewById(R.id.roomTypeEditText);
                floorsEditText = viewDialog.findViewById(R.id.floorEditText);

                acCheckBox = viewDialog.findViewById(R.id.acCheckBox);
                washroomCheckBox = viewDialog.findViewById(R.id.washroomCheckBox);
                balconyCheckBox = viewDialog.findViewById(R.id.balconyCheckBox);
                ventilationCheckBox = viewDialog.findViewById(R.id.ventilationCheckBox);
                largeRoomCheckBox = viewDialog.findViewById(R.id.largeRoomCheckBox);
                cornerRoomCheckBox = viewDialog.findViewById(R.id.cornerRoomCheckBox);

                tagString = "";



                final View titleView = inflater.inflate(R.layout.custom_titleroom, null);
                custom_title = titleView.findViewById(R.id.roomCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(RoomsActivity.this);
                builder.setCustomTitle(custom_title);
                builder.setView(viewDialog);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

                        switch (roomTypeEditText.getText().toString()) {

                            case "1" : roomType = "One Bed"; break;
                            case "2" : roomType = "Two Bed"; break;
                            case "3" : roomType = "Three Bed"; break;
                            case "4" : roomType = "Four Bed"; break;
                            case "5" : roomType = "Five Bed"; break;
                            case "6" : roomType = "Six Bed"; break;
                            case "7" : roomType = "Seven Bed"; break;
                            case "8" : roomType = "Eight Bed"; break;
                            case "9" : roomType = "Nine Bed"; break;

                        }

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


                        if(roomType.length() < 1 || room.isEmpty())
                        {
                            Toast.makeText(RoomsActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());
                            databaseReference1.child("Rooms").child(room).child("Room Type").setValue(roomType);
                            databaseReference1.child("Rooms").child(room).child("Floors").setValue(floors);
                            databaseReference1.child("Rooms").child(room).child("Tags").setValue(tagString);

                            getRoomDetails(room);
                            getTenantDetails(room);

                        }

                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
            }

        });

    }

    private void getTenantDetails(final String room){
        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenantList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                    tenantList.add(tenantDetails);
                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for(int i = 0; i < tenantList.size(); i++){
                    if (tenantList.get(i).room.equals(room)) {

                        databaseReference1.child("Rooms").child(tenantList.get(i).room).child("Tenant").child("CurrentTenants").child(tenantList.get(i).id).setValue(tenantList.get(i));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRoomDetails(final String room) {

        roomApplianceDetailsList.clear();

        /*Adding Room Appliance Details*/

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/AC/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                acList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ACDetails acDetails = snapshot.getValue(ACDetails.class);
                    acList.add(acDetails);
                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < acList.size(); i++) {
                    if (acList.get(i).roomNo.equals(room)) {
                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(acList.get(i).id, "AC", acList.get(i).brand, acList.get(i).lastServiceDate);
                        databaseReference1.child("Rooms").child(acList.get(i).roomNo).child("Appliance").child(acList.get(i).id).setValue(roomApplianceDetails);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Fan/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fanList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FanDetails fanDetails = snapshot.getValue(FanDetails.class);
                    fanList.add(fanDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < fanList.size(); i++) {

                    if (fanList.get(i).roomNo.equals(room)) {
                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(fanList.get(i).id, "Fan", fanList.get(i).brand, fanList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(fanList.get(i).roomNo).child("Appliance").child(fanList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Geyser/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                geyserList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    GeyserDetails geyserDetails = snapshot.getValue(GeyserDetails.class);
                    geyserList.add(geyserDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < geyserList.size(); i++) {

                    if (geyserList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(geyserList.get(i).id, "Geyser", geyserList.get(i).brand, geyserList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(geyserList.get(i).roomNo).child("Appliance").child(geyserList.get(i).id).setValue(roomApplianceDetails);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Washing Machine/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                washingMachineList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    WashingMachineDetails washingMachineDetails = snapshot.getValue(WashingMachineDetails.class);
                    washingMachineList.add(washingMachineDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < washingMachineList.size(); i++) {

                    if (washingMachineList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(washingMachineList.get(i).id, "Washing Machine", washingMachineList.get(i).brand, washingMachineList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(washingMachineList.get(i).roomNo).child("Appliance").child(washingMachineList.get(i).id).setValue(roomApplianceDetails);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/RO/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                roList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    RODetails roDetails = snapshot.getValue(RODetails.class);
                    roList.add(roDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < roList.size(); i++) {

                    if (roList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(roList.get(i).id, "RO", roList.get(i).brand, roList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(roList.get(i).roomNo).child("Appliance").child(roList.get(i).id).setValue(roomApplianceDetails);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Dishwasher/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dishwasherList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    DishwasherDetails dishwasherDetails = snapshot.getValue(DishwasherDetails.class);
                    dishwasherList.add(dishwasherDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < dishwasherList.size(); i++) {

                    if (dishwasherList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(dishwasherList.get(i).id, "Dishwasher", dishwasherList.get(i).brand, dishwasherList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(dishwasherList.get(i).roomNo).child("Appliance").child(dishwasherList.get(i).id).setValue(roomApplianceDetails);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Microwave/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                microwaveList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MicrowaveDetails microwaveDetails = snapshot.getValue(MicrowaveDetails.class);
                    microwaveList.add(microwaveDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < microwaveList.size(); i++) {

                    if (microwaveList.get(i).roomNo.equals(room)) {
                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(microwaveList.get(i).id, "Microwave", microwaveList.get(i).brand, microwaveList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(microwaveList.get(i).roomNo).child("Appliance").child(microwaveList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Refrigerator/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                refrigeratorList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    RefrigeratorDetails refrigeratorDetails = snapshot.getValue(RefrigeratorDetails.class);
                    refrigeratorList.add(refrigeratorDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < refrigeratorList.size(); i++) {

                    if (refrigeratorList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(refrigeratorList.get(i).id, "Refrigerator", refrigeratorList.get(i).brand, refrigeratorList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(refrigeratorList.get(i).roomNo).child("Appliance").child(refrigeratorList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/TV/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tvList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TVDetails tvDetails = snapshot.getValue(TVDetails.class);
                    tvList.add(tvDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < tvList.size(); i++) {

                    if (tvList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(tvList.get(i).id, "TV", tvList.get(i).brand, tvList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(tvList.get(i).roomNo).child("Appliance").child(tvList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/CCTV/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cctvList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    CCTVDetails cctvDetails = snapshot.getValue(CCTVDetails.class);
                    cctvList.add(cctvDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < cctvList.size(); i++) {

                    if (cctvList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(cctvList.get(i).id, "CCTV", cctvList.get(i).brand, cctvList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(cctvList.get(i).roomNo).child("Appliance").child(cctvList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Iron/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ironList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    IronDetails ironDetails = snapshot.getValue(IronDetails.class);
                    ironList.add(ironDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < ironList.size(); i++) {

                    if (ironList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(ironList.get(i).id, "Iron", ironList.get(i).brand, ironList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(ironList.get(i).roomNo).child("Appliance").child(ironList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Induction/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                inductionList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    InductionDetails inductionDetails = snapshot.getValue(InductionDetails.class);
                    inductionList.add(inductionDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < inductionList.size(); i++) {

                    if (inductionList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(inductionList.get(i).id, "Induction", inductionList.get(i).brand, inductionList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(inductionList.get(i).roomNo).child("Appliance").child(inductionList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Router/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                routerList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    RouterDetails routerDetails = snapshot.getValue(RouterDetails.class);
                    routerList.add(routerDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < routerList.size(); i++) {

                    if (routerList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(routerList.get(i).id, "Router", routerList.get(i).brand, routerList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(routerList.get(i).roomNo).child("Appliance").child(routerList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Heater/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                heaterList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    HeaterDetails heaterDetails = snapshot.getValue(HeaterDetails.class);
                    heaterList.add(heaterDetails);

                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < heaterList.size(); i++) {

                    if (heaterList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(heaterList.get(i).id, "Heater", heaterList.get(i).brand, heaterList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(heaterList.get(i).roomNo).child("Appliance").child(heaterList.get(i).id).setValue(roomApplianceDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/D2H/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                d2HList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    D2HDetails d2HDetails = snapshot.getValue(D2HDetails.class);
                    d2HList.add(d2HDetails);
                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < d2HList.size(); i++) {

                    if (d2HList.get(i).roomNo.equals(room)) {
                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(d2HList.get(i).id, "D2H", d2HList.get(i).brand, d2HList.get(i).timeSinceInstallation);
                        databaseReference1.child("Rooms").child(d2HList.get(i).roomNo).child("Appliance").child(d2HList.get(i).id).setValue(roomApplianceDetails);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Other/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                otherList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OtherApplianceDetails otherApplianceDetails = snapshot.getValue(OtherApplianceDetails.class);
                    otherList.add(otherApplianceDetails);
                }

                databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                for (int i = 0; i < otherList.size(); i++) {

                    if (otherList.get(i).roomNo.equals(room)) {

                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(otherList.get(i).id, "Other", otherList.get(i).brand);
                        databaseReference1.child("Rooms").child(otherList.get(i).roomNo).child("Appliance").child(otherList.get(i).id).setValue(roomApplianceDetails);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RoomsActivity.this , HomePageActivity.class));
        finish();
    }
}