package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.readystatesoftware.viewbadger.BadgeView;

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

    long currentBedroomCount, currentFacilitiesCount, currentMessCount, currentSecurityCount;
    long prevBedroomCount, prevFacilitiesCount, prevMessCount, prevSecurityCount;

    RecyclerView recyclerView;
    RecentComplaintDetailList adapter;
    Context context ;

    View bedroomComplaintView, foodComplaintView, facilityComplaintView, securityComplaintView;

    public static final String complaintPreference = "compPref" ;

    List<ComplaintDetails> complaintDetailsList;

    CardView bedroomComplaint, foodComplaint, facilityComplaint, securityComplaint;

    HorizontalScrollView horizontalScrollView;

    DateFormat dateFormat;

    CardView emptyView;

    SharedPreferences sharedPreferences;

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

        emptyView = findViewById(R.id.noComplaint);

        complaintDetailsList = new ArrayList<>();

        bedroomComplaint = findViewById(R.id.bedroomComplaint);
        facilityComplaint = findViewById(R.id.facilityComplaint);
        foodComplaint = findViewById(R.id.foodComplaint);
        securityComplaint = findViewById(R.id.securityComplaint);

        firstComplaintCard = findViewById(R.id.firstComplaintCard);

        backButton = findViewById(R.id.backButton);

        sharedPreferences = getSharedPreferences(complaintPreference, Context.MODE_PRIVATE);
        prevBedroomCount = sharedPreferences.getLong("BedroomCount", 0);
        prevFacilitiesCount = sharedPreferences.getLong("FacilitiesCount", 0);
        prevMessCount = sharedPreferences.getLong("MessCount", 0);
        prevSecurityCount = sharedPreferences.getLong("SecurityCount", 0);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaint/Bedroom/");

        DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/Bedroom/");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentBedroomCount = dataSnapshot.getChildrenCount();
                Log.e("Bedroom", currentBedroomCount + "");

                if (currentBedroomCount != prevBedroomCount) {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, bedroomComplaint);
                    badgeView.setText(Long.toString(currentBedroomCount - prevBedroomCount));
                    badgeView.show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("BedroomCount", currentBedroomCount);
                    editor.apply();

                }
                else {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, bedroomComplaint);
                    badgeView.setText("0");
                    badgeView.show();
                }

                complaintDetailsList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (!complaintDetails1.status.equals("Resolved")){
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

                currentMessCount = dataSnapshot.getChildrenCount();
                Log.e("Mess", currentMessCount + "");

                if (currentMessCount != prevMessCount) {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, foodComplaint);
                    badgeView.setText(Long.toString(currentMessCount - prevMessCount));
                    badgeView.show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("MessCount", currentMessCount);
                    editor.apply();
                }
                else {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, foodComplaint);
                    badgeView.setText("0");
                    badgeView.show();

                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (!complaintDetails1.status.equals("Resolved")) {
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

                currentFacilitiesCount = dataSnapshot.getChildrenCount();
                Log.e("Facilities", currentFacilitiesCount + "");

                if (currentFacilitiesCount != prevFacilitiesCount) {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, facilityComplaint);
                    badgeView.setText(Long.toString(currentFacilitiesCount - prevFacilitiesCount));
                    badgeView.show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("FacilitiesCount", currentFacilitiesCount);
                    editor.apply();

                }
                else {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, facilityComplaint);
                    badgeView.setText("0");
                    badgeView.show();

                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (!complaintDetails1.status.equals("Resolved")) {
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

                currentSecurityCount = dataSnapshot.getChildrenCount();

                Log.e("Security", currentSecurityCount + "");


                if (currentSecurityCount != prevSecurityCount) {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, securityComplaint);
                    badgeView.setText(Long.toString(currentSecurityCount - prevSecurityCount));
                    badgeView.show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("SecurityCount", currentSecurityCount);
                    editor.apply();

                }
                else {

                    BadgeView badgeView = new BadgeView(ComplaintActivity.this, securityComplaint);
                    badgeView.setText("0");
                    badgeView.show();

                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    ComplaintDetails complaintDetails1 = snapshot.getValue(ComplaintDetails.class);

                    if (!complaintDetails1.status.equals("Resolved")) {
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



        /*if(complaintDetailsList.size() == 0)
        {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else
        {

        }*/

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ComplaintActivity.this, HomePageActivity.class));
        finish();
    }

    public void getComplaints() {

        if (complaintDetailsList.size() == 0) {

            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {

            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        Collections.sort(complaintDetailsList, Collections.<ComplaintDetails>reverseOrder());

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        if (complaintDetailsList.size() < 5) {

            adapter = new RecentComplaintDetailList(complaintDetailsList, ComplaintActivity.this);

        }
        else {

            adapter = new RecentComplaintDetailList(complaintDetailsList.subList(0, 5), ComplaintActivity.this);

        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ComplaintActivity.this, LinearLayout.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}