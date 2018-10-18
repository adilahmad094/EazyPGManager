package net.eazypg.eazypgmanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.RentCollectionPaidDetailList;
import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class FineRentBillActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    DatabaseReference databaseReference;

    EditText fineAmountEditText;

    String fineAmount, name;

    Button fineOkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine_rent_bill);

        Fabric.with(this, new Crashlytics());


        Intent intent = getIntent();
        final String tenantId = intent.getStringExtra(RentCollectionPaidDetailList.EXTRA_MESSAGE);
        final String tenantRoom = intent.getStringExtra(RentCollectionPaidDetailList.EXTRA_MESSAGE2);

        fineAmountEditText = findViewById(R.id.fineEditText);
        fineOkButton = findViewById(R.id.fineOkButton);




        fineOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fineAmount = fineAmountEditText.getText().toString();

                databaseReference = firebaseDatabase.getReference("Tenants/" + tenantId + "/");
                String fineId = databaseReference.push().getKey();
                FineDetails fineDetails = new FineDetails(fineId, fineAmount, false);

                final DatabaseReference databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + tenantRoom + "/Tenant/CurrentTenants/" + tenantId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String prevFine = dataSnapshot.child("Fine").getValue(String.class);
                        name = dataSnapshot.child("name").getValue(String.class);

                        String fine = fineAmount;

                        if(prevFine != null && fine != null){
                            fine = Integer.toString(Integer.parseInt(prevFine) + Integer.parseInt(fineAmount));
                        }

                        databaseReference.child("Fine").setValue(fine);

                        DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantId);
                        databaseReference2.child("Fine").setValue(fine);

                        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Tenants/" + tenantId);
                        databaseReference3.child("fine").setValue(fine);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference databaseReference4 = firebaseDatabase.getReference("Tenants/" + tenantId);

                databaseReference4.child("Accounts").child("Fines").child(fineId).setValue(fineDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(FineRentBillActivity.this);
                        builder.setTitle("Fine added");
                        builder.setMessage("Rs. " + fineAmount + " is added in " + name + "'s account");
                        builder.setCancelable(false);
                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(FineRentBillActivity.this, RentBillCollectionFragment.class));
                                finish();

                            }
                        });
                        builder.show();
                    }
                });
            }
        });
    }
}
