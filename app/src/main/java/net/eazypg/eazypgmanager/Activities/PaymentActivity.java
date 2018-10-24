package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.PaymentDetailList;
import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.PaymentDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class PaymentActivity extends AppCompatActivity {

    ListView listView;
    List<PaymentDetails> paymentList;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    TextView addPaymentTitle;

    Snackbar snackbar;
    View view;
    View emptyList;

    LayoutInflater inflater;

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Fabric.with(this, new Crashlytics());


        Toolbar toolbar = findViewById(R.id.paymentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

        listView = findViewById(R.id.listViewPayment);
        emptyList = findViewById(R.id.emptyListPayment);
        listView.setEmptyView(emptyList);

        paymentList = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE);

        databaseReference = firebaseDatabase.getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Tenants/CurrentTenants/" + id + "/Passbook");
       
        databaseReference.child("History").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                paymentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    PaymentDetails paymentDetails = snapshot.getValue(PaymentDetails.class);
                    paymentList.add(paymentDetails);

                }
                PaymentDetailList adapter = new PaymentDetailList(PaymentActivity.this, paymentList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
