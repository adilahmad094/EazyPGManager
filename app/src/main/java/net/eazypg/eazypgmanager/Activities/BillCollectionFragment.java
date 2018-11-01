package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.BillDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class BillCollectionFragment extends Fragment {
    View view;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Context context;

    String typeOfBills;

    public static final String EXTRA_MESSAGE = "";

    CardView electricityUnpaidButton, gasUnpaidButton, wifiUnpaidButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Fabric.with(context, new Crashlytics());
        view = inflater.inflate(R.layout.activity_bill_collection, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        context = getContext();

        electricityUnpaidButton = view.findViewById(R.id.electricityUnpaidButton);
        wifiUnpaidButton = view.findViewById(R.id.wifiUnpaidButton);
        gasUnpaidButton = view.findViewById(R.id.gasUnpaidButton);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PG Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                typeOfBills = dataSnapshot.child("typeOfBills").getValue(String.class);

                if (typeOfBills != null) {

                    if (typeOfBills.contains("Electricity")){
                        electricityUnpaidButton.setVisibility(View.VISIBLE);
                    }

                    if (typeOfBills.contains("Gas")) {
                        gasUnpaidButton.setVisibility(View.VISIBLE);
                    }

                    if (typeOfBills.contains("WiFi")) {
                        wifiUnpaidButton.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        electricityUnpaidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UnpaidBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Electricity");
                startActivity(intent);

            }
        });

        gasUnpaidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UnpaidBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Gas");
                startActivity(intent);

            }
        });

        wifiUnpaidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UnpaidBillActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Wifi");
                startActivity(intent);

            }
        });

        return view;
    }
}