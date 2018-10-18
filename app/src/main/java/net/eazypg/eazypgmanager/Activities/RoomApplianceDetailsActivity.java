package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.RoomApplianceDetailList;
import net.eazypg.eazypgmanager.DetailList.RoomsDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.RoomApplianceDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class RoomApplianceDetailsActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<RoomApplianceDetails> roomApplianceDetailsList;

    ListView listViewRoomAppliance;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_appliance_details);

        Fabric.with(this, new Crashlytics());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomApplianceDetailsActivity.this, RoomsActivity.class));
                finish();
            }
        });

        roomApplianceDetailsList = new ArrayList<>();
        listViewRoomAppliance = findViewById(R.id.listViewRoomAppliance);

        Intent intent = getIntent();

        String roomNo = intent.getStringExtra(RoomsDetailList.EXTRA_MESSAGE);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomNo + "/Appliance/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomApplianceDetailsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    RoomApplianceDetails roomApplianceDetails = snapshot.getValue(RoomApplianceDetails.class);
                    roomApplianceDetailsList.add(roomApplianceDetails);
                }

                RoomApplianceDetailList adapter = new RoomApplianceDetailList(RoomApplianceDetailsActivity.this, roomApplianceDetailsList);
                listViewRoomAppliance.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
