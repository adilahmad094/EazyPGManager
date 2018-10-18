package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.RecentComplaintDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.ComplaintDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class ComplaintActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    RecentComplaintDetailList adapter;
    Context context ;

    ConstraintLayout rootLayout;

    List<ComplaintDetails> complaintDetailsList;

    CardView bedroomComplaint, foodComplaint, facilityComplaint, securityComplaint;

    HorizontalScrollView horizontalScrollView;

    Date date;
    DateFormat dateFormat;

    ImageView backButton;

    CardView firstComplaintCard;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        Fabric.with(this, new Crashlytics());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(33,33,33));
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        horizontalScrollView = findViewById(R.id.horizontalScrollView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        complaintDetailsList = new ArrayList<>();

        bedroomComplaint = findViewById(R.id.bedroomComplaint);
        facilityComplaint = findViewById(R.id.facilityComplaint);
        foodComplaint = findViewById(R.id.foodComplaint);
        securityComplaint = findViewById(R.id.securityComplaint);

        firstComplaintCard = findViewById(R.id.firstComplaintCard);

        backButton = findViewById(R.id.backButton);


        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaint/Bedroom/");

        DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/Bedroom/");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                complaintDetailsList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (complaintDetails1.status.equals("Unresolved")){
                        complaintDetailsList.add(complaintDetails1);
                    }

                }

                flag = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/Mess & Food/");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (complaintDetails1.status.equals("Unresolved")) {
                        complaintDetailsList.add(complaintDetails1);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference3 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/Facilities/");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (complaintDetails1.status.equals("Unresolved")) {
                        complaintDetailsList.add(complaintDetails1);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference4 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/Management & Security/");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (complaintDetails1.status.equals("Unresolved")) {
                        complaintDetailsList.add(complaintDetails1);
                    }
                }

                getComplaints();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bedroomComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ComplaintActivity.this, BedroomComplaintsFragmentActivity.class));
            }
        });

        foodComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ComplaintActivity.this, FoodComplaintsFragmentActivity.class));
            }
        });

        facilityComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ComplaintActivity.this, FacilityComplaintsFragmentActivity.class));
            }
        });

        securityComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ComplaintActivity.this, SecurityComplaintsFragmentActivity.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComplaintActivity.this, HomePageActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ComplaintActivity.this, HomePageActivity.class));
        finish();
    }

    public void getComplaints() {

        Collections.sort(complaintDetailsList, Collections.<ComplaintDetails>reverseOrder());
        int size = complaintDetailsList.size();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        if (complaintDetailsList.size() < 5) {

            adapter = new RecentComplaintDetailList(complaintDetailsList);

        }
        else {
            adapter = new RecentComplaintDetailList(complaintDetailsList.subList(0, 5));

        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ComplaintActivity.this, LinearLayout.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}


