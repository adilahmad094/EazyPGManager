package net.eazypg.eazypgmanager.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.HostFriendDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.GuestDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class HostAFriendLogFragment extends Fragment {

    View view;

    RecyclerView recyclerView;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<GuestDetails> guestDetailsList;

    HostFriendDetailList hostFriendDetailList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Fabric.with(getContext(), new Crashlytics());


        view = inflater.inflate(R.layout.activity_host_friend_log, container, false);

        recyclerView = view.findViewById(R.id.hostFriendLogRecyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        guestDetailsList = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Host Friend/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                guestDetailsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    GuestDetails guestDetails = snapshot.getValue(GuestDetails.class);
                    guestDetailsList.add(guestDetails);
                }

                hostFriendDetailList = new HostFriendDetailList(guestDetailsList , getContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(hostFriendDetailList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
