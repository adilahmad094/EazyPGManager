package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.DetailList.UnderprocessDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.DetailsClasses.UnderProcessTenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class UnderProcessTenants extends AppCompatActivity {

    View emptyList;
    List<UnderProcessTenantDetails> tenantDetailsList;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView underprocessRecyclerView;

    Context context;

    UnderprocessDetailList underprocessDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_process_tenants);

        Fabric.with(this, new Crashlytics());

        Toolbar toolbar = findViewById(R.id.underProcessTenantToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

      /*  emptyList = findViewById(R.id.emptyListUnderProcessTenant);
        listView.setEmptyView(emptyList);
*/
        tenantDetailsList = new ArrayList<>();

        underprocessRecyclerView = findViewById(R.id.UnderProcessTenantRecyclerView);
        context = UnderProcessTenants.this;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/UnderProcess");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tenantDetailsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UnderProcessTenantDetails tenantDetails = snapshot.getValue(UnderProcessTenantDetails.class);
                    tenantDetailsList.add(tenantDetails);

                }

                underprocessDetailList = new UnderprocessDetailList(tenantDetailsList, context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                underprocessRecyclerView.setLayoutManager(layoutManager);
                underprocessRecyclerView.setItemAnimator(new DefaultItemAnimator());
 //               Collections.sort(tenantDetailsList, Collections.<UnderProcessTenantDetails>reverseOrder());
                underprocessRecyclerView.setAdapter(underprocessDetailList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
