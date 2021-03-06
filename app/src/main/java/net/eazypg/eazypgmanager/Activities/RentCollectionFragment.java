package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.RentCollectionPaidDetailList;
import net.eazypg.eazypgmanager.DetailList.RentCollectionUnpaidDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ThisMonthRentDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class RentCollectionFragment extends Fragment {
    View view;

    TextView numberBillPaidTextView, numberBillNotPaidTextView;
    RecyclerView rentUnpaidRecyclerView;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    List<TenantDetails> unpaidTenants = new ArrayList<>();

    long paid = 0, unpaid = 0;

    RentCollectionUnpaidDetailList rentCollectionUnpaidDetailList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Fabric.with(getContext(), new Crashlytics());

        view = inflater.inflate(R.layout.activity_rent_collection, container, false);

        final Context context = getContext();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        final String dateStr = dateFormat.format(date);

        final String dateString = dateStr.substring(6,10) + "-" + dateStr.substring(3,5);


        numberBillPaidTextView = view.findViewById(R.id.numberBillPaidTextView);
        numberBillNotPaidTextView = view.findViewById(R.id.numberBillNotPaidTextView);

        rentUnpaidRecyclerView = view.findViewById(R.id.rentUnpaidRecyclerView);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                paid = dataSnapshot.child("ThisMonth").child(dateString).child("Rent").getChildrenCount();
                unpaid = dataSnapshot.child("CurrentTenants").getChildrenCount() - paid;

                numberBillPaidTextView.setText(Long.toString(paid));
                numberBillNotPaidTextView.setText(Long.toString(unpaid));

                unpaidTenants.clear();

                for (DataSnapshot snapshot : dataSnapshot.child("CurrentTenants").getChildren()) {

                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);

                    if (dataSnapshot.child("ThisMonth").child(dateString).child("Rent").hasChild(tenantDetails.id)) {
                        // rent paid
                    }
                    else {
                        unpaidTenants.add(tenantDetails);
                    }

                }

                rentCollectionUnpaidDetailList = new RentCollectionUnpaidDetailList(unpaidTenants, context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                rentUnpaidRecyclerView.setLayoutManager(layoutManager);
                rentUnpaidRecyclerView.setItemAnimator(new DefaultItemAnimator());
                rentUnpaidRecyclerView.setAdapter(rentCollectionUnpaidDetailList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
