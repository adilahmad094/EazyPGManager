package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class FineActivity extends AppCompatActivity {

    EditText fineAmountEditText;
    Button fineOkButton;

    String fineAmount, name;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);

        Fabric.with(this, new Crashlytics());

        Intent intent = getIntent();
        final String tenantId = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE);
        final String tenantRoom = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        fineAmountEditText = findViewById(R.id.fineEditText);

        fineOkButton = findViewById(R.id.fineOkButton);

        fineOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fineAmount = fineAmountEditText.getText().toString();

                Toast.makeText(FineActivity.this, "This is fine", Toast.LENGTH_SHORT).show();


                Log.e("Fine", fineAmount + "");

                databaseReference = firebaseDatabase.getReference("Tenants/" + tenantId);
                String fineId = databaseReference.push().getKey();
                FineDetails fineDetails = new FineDetails(fineId, fineAmount, false);

                final DatabaseReference databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + tenantRoom + "/Tenant/CurrentTenants/" + tenantId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String prevFine = dataSnapshot.child("Fine").getValue(String.class);


                        String fine = fineAmount;

                        if(prevFine != null){
                            fine = Integer.toString(Integer.parseInt(prevFine) + Integer.parseInt(fineAmount));
                        }

                        DatabaseReference databaseReference5 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + tenantRoom + "/Tenant/CurrentTenants/" + tenantId);
                        databaseReference5.child("Fine").setValue(fine);

                        DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantId);
                        databaseReference2.child("Fine").setValue(fine);

                        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Tenants/" + tenantId);
                        databaseReference3.child("fine").setValue(fine).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference databaseReference4 = firebaseDatabase.getReference("Tenants/" + tenantId);

                databaseReference4.child("Accounts").child("Fines").child(fineId).setValue(fineDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });
            }
        });
    }
}
