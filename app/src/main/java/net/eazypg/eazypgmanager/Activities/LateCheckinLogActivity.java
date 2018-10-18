package net.eazypg.eazypgmanager.Activities;

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

import net.eazypg.eazypgmanager.DetailList.LateCheckinDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.LateCheckInDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class LateCheckinLogActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<LateCheckInDetails> lateCheckInDetailsList;

    LateCheckinDetailList lateCheckinDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_late_checkin_log);

        Fabric.with(this, new Crashlytics());


        recyclerView = findViewById(R.id.lateCheckinRecyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        lateCheckInDetailsList = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Late Check-In/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                lateCheckInDetailsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    LateCheckInDetails lateCheckInDetails = snapshot.getValue(LateCheckInDetails.class);
                    lateCheckInDetailsList.add(lateCheckInDetails);
                }

                lateCheckinDetailList = new LateCheckinDetailList(lateCheckInDetailsList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(lateCheckinDetailList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
