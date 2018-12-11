package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.Activities.RoomApplianceDetailsActivity;
import net.eazypg.eazypgmanager.Activities.RoomClickActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomsDetailList extends ArrayAdapter<String> {

    private Activity context;
    private List<String> roomList;
    private TextView third , fourth;
    private ListView dialogListView;
    private List<String> roomTypeList;
    private List<TenantDetails> tenantList = new ArrayList<>();
    private List<String> tagList = new ArrayList<>();
    public List<String> floorsList = new ArrayList<>();

    TextView floorsTextView, acTextView, nonAcTextView, washroomTextView, balconyTextView, ventilationTextView, largeRoomTextView, cornerRoomTextView;
    ImageView tenantImage1, tenantImage2, tenantImage3, tenantImage4, tenantImage5, tenantImage6, tenantImage7, tenantImage8, tenantImage9;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";

    private Button applianceButton, tenantButton;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    String tagString;
    boolean isTenant = true;


    public RoomsDetailList(Activity context, List<String> roomList, List<String> roomTypeList, List<String> tagList, List<String> floorList) {
        super(context, R.layout.room_row, roomList);

        this.context = context;
        this.roomList = roomList;
        this.roomTypeList = roomTypeList;
        this.tagList = tagList;
        this.floorsList = floorList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /*Retrieving Room Details*/

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItemRoom = inflater.inflate(R.layout.room_row, null, true);

        third = listViewItemRoom.findViewById(R.id.thirdTextView);
        fourth = listViewItemRoom.findViewById(R.id.fourthTextView);

        tenantImage1 = listViewItemRoom.findViewById(R.id.tenantImage1);
        tenantImage2 = listViewItemRoom.findViewById(R.id.tenantImage2);
        tenantImage3 = listViewItemRoom.findViewById(R.id.tenantImage3);
        tenantImage4 = listViewItemRoom.findViewById(R.id.tenantImage4);
        tenantImage5 = listViewItemRoom.findViewById(R.id.tenantImage5);
        tenantImage6 = listViewItemRoom.findViewById(R.id.tenantImage6);
        tenantImage7 = listViewItemRoom.findViewById(R.id.tenantImage7);
        tenantImage8 = listViewItemRoom.findViewById(R.id.tenantImage8);
        tenantImage9 = listViewItemRoom.findViewById(R.id.tenantImage9);

        acTextView = listViewItemRoom.findViewById(R.id.acTextView);
        nonAcTextView = listViewItemRoom.findViewById(R.id.nonAcTextView);
        washroomTextView = listViewItemRoom.findViewById(R.id.washroomTextView);
        balconyTextView = listViewItemRoom.findViewById(R.id.balconyTextView);
        ventilationTextView = listViewItemRoom.findViewById(R.id.ventilationTextView);
        largeRoomTextView = listViewItemRoom.findViewById(R.id.largeRoomTextView);
        cornerRoomTextView = listViewItemRoom.findViewById(R.id.cornerRoomTextView);
        floorsTextView = listViewItemRoom.findViewById(R.id.floorsTextView);

        databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomList.get(position) + "/Tenant/CurrentTenants");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long count = dataSnapshot.getChildrenCount();

                switch ((int)count) {

                    case 1:
                        tenantImage1.setAlpha(1.0f);
                        break;

                    case 2:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        break;

                    case 3:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        break;

                    case 4:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        tenantImage4.setAlpha(1.0f);
                        break;

                    case 5:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        tenantImage4.setAlpha(1.0f);
                        tenantImage5.setAlpha(1.0f);
                        break;

                    case 6:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        tenantImage4.setAlpha(1.0f);
                        tenantImage5.setAlpha(1.0f);
                        tenantImage6.setAlpha(1.0f);
                        break;

                    case 7:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        tenantImage4.setAlpha(1.0f);
                        tenantImage5.setAlpha(1.0f);
                        tenantImage6.setAlpha(1.0f);
                        tenantImage7.setAlpha(1.0f);
                        break;

                    case 8:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        tenantImage4.setAlpha(1.0f);
                        tenantImage5.setAlpha(1.0f);
                        tenantImage6.setAlpha(1.0f);
                        tenantImage7.setAlpha(1.0f);
                        tenantImage8.setAlpha(1.0f);
                        break;

                    case 9:
                        tenantImage1.setAlpha(1.0f);
                        tenantImage2.setAlpha(1.0f);
                        tenantImage3.setAlpha(1.0f);
                        tenantImage4.setAlpha(1.0f);
                        tenantImage5.setAlpha(1.0f);
                        tenantImage6.setAlpha(1.0f);
                        tenantImage7.setAlpha(1.0f);
                        tenantImage8.setAlpha(1.0f);
                        tenantImage9.setAlpha(1.0f);
                        break;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        int maxBed = 0;

        switch (roomTypeList.get(position)) {

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

        switch (maxBed) {

            case 1:
                tenantImage1.setVisibility(View.VISIBLE);
                break;

            case 2:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                break;

            case 3:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);

                break;

            case 4:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);
                tenantImage4.setVisibility(View.VISIBLE);

                break;

            case 5:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);
                tenantImage4.setVisibility(View.VISIBLE);
                tenantImage5.setVisibility(View.VISIBLE);

                break;

            case 6:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);
                tenantImage4.setVisibility(View.VISIBLE);
                tenantImage5.setVisibility(View.VISIBLE);
                tenantImage6.setVisibility(View.VISIBLE);

                break;

            case 7:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);
                tenantImage4.setVisibility(View.VISIBLE);
                tenantImage5.setVisibility(View.VISIBLE);
                tenantImage6.setVisibility(View.VISIBLE);
                tenantImage7.setVisibility(View.VISIBLE);

                break;

            case 8:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);
                tenantImage4.setVisibility(View.VISIBLE);
                tenantImage5.setVisibility(View.VISIBLE);
                tenantImage6.setVisibility(View.VISIBLE);
                tenantImage7.setVisibility(View.VISIBLE);
                tenantImage8.setVisibility(View.VISIBLE);

                break;

            case 9:
                tenantImage1.setVisibility(View.VISIBLE);
                tenantImage2.setVisibility(View.VISIBLE);
                tenantImage3.setVisibility(View.VISIBLE);
                tenantImage4.setVisibility(View.VISIBLE);
                tenantImage5.setVisibility(View.VISIBLE);
                tenantImage6.setVisibility(View.VISIBLE);
                tenantImage7.setVisibility(View.VISIBLE);
                tenantImage8.setVisibility(View.VISIBLE);
                tenantImage9.setVisibility(View.VISIBLE);

                break;
        }

        tagString = tagList.get(position);

        if(tagString != null) {
            if (tagString.contains("AC")) {
                acTextView.setVisibility(View.VISIBLE);
            } else {
                nonAcTextView.setVisibility(View.VISIBLE);
            }

            if (tagString.contains("Washroom")) {
                washroomTextView.setVisibility(View.VISIBLE);
            }

            if (tagString.contains("Balcony")) {
                balconyTextView.setVisibility(View.VISIBLE);
            }

            if (tagString.contains("Ventilation")) {
                ventilationTextView.setVisibility(View.VISIBLE);
            }
            if (tagString.contains("Large Room")) {
                largeRoomTextView.setVisibility(View.VISIBLE);
            }
            if (tagString.contains("Corner Room")) {
                cornerRoomTextView.setVisibility(View.VISIBLE);
            }

        }
        //rentTextView.setVisibility(View.VISIBLE);

        applianceButton = listViewItemRoom.findViewById(R.id.appliancesButton);

        third.setText(roomList.get(position));
        fourth.setText(roomTypeList.get(position));

        floorsTextView.setText(floorsList.get(position));

        applianceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RoomApplianceDetailsActivity.class);
                String message = roomList.get(position);
                intent.putExtra(EXTRA_MESSAGE, message);
                context.startActivity(intent);
            }
        });


        listViewItemRoom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete room");
                builder.setMessage("Are you sure you want to delete the room " + roomList.get(position) + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading", "Please wait..", true);

                        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomList.get(position));

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                isTenant = dataSnapshot.hasChild("Tenant");

                                progressDialog.dismiss();

                                if (!isTenant) {
                                    databaseReference.setValue(null);
                                    Toast.makeText(context, "Room Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                    builder1.setTitle("Error");
                                    builder1.setMessage("Cannot delete room. The room has tenants.");
                                    builder1.show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();


                return true;
            }
        });


        listViewItemRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PG Details/");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String rentDueDate = dataSnapshot.child("rentDueDate").getValue(String.class);
                        String billDueDate = dataSnapshot.child("billDueDate").getValue(String.class);

                        Intent intent = new Intent(context, RoomClickActivity.class);
                        String roomNumber = roomList.get(position);
                        String roomType = roomTypeList.get(position);
                        intent.putExtra(EXTRA_MESSAGE, roomNumber);
                        intent.putExtra(EXTRA_MESSAGE2, roomType);
                        intent.putExtra(EXTRA_MESSAGE3, rentDueDate);
                        intent.putExtra(EXTRA_MESSAGE4, billDueDate);

                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return listViewItemRoom;
    }
}
