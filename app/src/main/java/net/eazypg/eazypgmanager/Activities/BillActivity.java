package net.eazypg.eazypgmanager.Activities;/*
package com.example.EazyPG.owner.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.EazyPG.owner.DetailList.TenantDetailList;
import com.example.EazyPG.owner.DetailsClasses.BillDetails;
import com.example.ainesh.eazypg_owner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BillActivity extends AppCompatActivity {

    EditText billCategoryEditText, billAmountEditText;
    Button billOkButton;

    String billCategory, billAmount;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent intent = getIntent();
        final String tenantId = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE);
        final String tenantRoom = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        billCategoryEditText = findViewById(R.id.billCategoryEditText);
        billAmountEditText = findViewById(R.id.billAmountEditText);

        billOkButton = findViewById(R.id.billOkButton);

        billOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billAmount = billAmountEditText.getText().toString();
                billCategory = billCategoryEditText.getText().toString();

                Toast.makeText(BillActivity.this, billAmount + " " + billCategory, Toast.LENGTH_SHORT).show();

                databaseReference = firebaseDatabase.getReference("Tenants/" + tenantId + "/");
                String billId = databaseReference.push().getKey();
                BillDetails billDetails = new BillDetails(billId, billCategory, billAmount, false, "");

                databaseReference.child("Accounts").child("Bills").child(billId).setValue(billDetails);

                DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantId);
                databaseReference1.child("Accounts").child("Bills").child(billId).setValue(billDetails);

                DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + tenantRoom + "/Tenant/CurrentTenants" + tenantId);
                databaseReference2.child("Accounts").child("Bills").child(billId).setValue(billDetails);

                DatabaseReference databaseReference3 = firebaseDatabase.getReference("Tenants/" + tenantId);
                databaseReference3.child("Passbook").child("Bills").child(billId).setValue(billDetails);
            }
        });
    }
}
*/
