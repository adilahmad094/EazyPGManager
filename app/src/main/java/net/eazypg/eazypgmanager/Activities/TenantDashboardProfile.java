package net.eazypg.eazypgmanager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.R;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;


public class TenantDashboardProfile extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView nameTextView, emailTextView, phoneTextView;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    EditText dobEditText, bloodGroupEditText, currentAddressEditText, permanentAddressEditText;
    EditText nationalityEditText, motherTongueEditText, aadharEditText, universityEditText, yearEditText;
    EditText fatherNameEditText, fatherOfficeAddressEditText, fatherOccupationEditText, fatherMobileEditText;
    EditText motherNameEditText, motherMobileEditText, guardianNameEditText, guardianMobileEditText;
    EditText guardianOfficeAddressEditText;

    String dob, bloodGroup, currentAddress, permanentAddress,
            nationality, motherTongue, aadhar, university, year,
            fatherName, fatherOfficeAddress, fatherOccupation, fatherMobile,
            motherName, motherMobile, guardianName, guardianMobile,
            guardianOfficeAddress;
    String id, selfieName;

    StorageReference storageReference;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_dashboard_profile);
        Fabric.with(this, new Crashlytics());


        Intent intent = getIntent();
        id = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE3);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Documents");

        final long ONE_MEGABYTE = 1024 * 1024;

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please Wait..", true);

        if(storageReference.child(id).child("Selfie") == null)
        {
            progressDialog.dismiss();

        }

        storageReference.child(id).child("Selfie").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
                progressDialog.dismiss();
            }
        });

        dobEditText = findViewById(R.id.dobEditText);
        bloodGroupEditText = findViewById(R.id.bloodGroupEditText);
        currentAddressEditText = findViewById(R.id.currentAddressEditText);
        permanentAddressEditText = findViewById(R.id.permanentAddressEditText);
        nationalityEditText = findViewById(R.id.nationalityEditText);
        motherTongueEditText = findViewById(R.id.motherTongueEditText);
        aadharEditText = findViewById(R.id.aadharEditText);
        universityEditText = findViewById(R.id.universityEditText);
        yearEditText = findViewById(R.id.yearEditText);
        fatherNameEditText = findViewById(R.id.fatherNameEditText);
        fatherOfficeAddressEditText = findViewById(R.id.fatherOfficeAddressEditText);
        fatherOccupationEditText = findViewById(R.id.fatherOccupationEditText);
        fatherMobileEditText = findViewById(R.id.fatherMobileEditText);
        motherNameEditText = findViewById(R.id.motherNameEditText);
        motherMobileEditText = findViewById(R.id.motherMobileEditText);
        guardianNameEditText = findViewById(R.id.guardianNameEditText);
        guardianMobileEditText = findViewById(R.id.guardianMobileEditText);
        guardianOfficeAddressEditText = findViewById(R.id.guardianOfficeAddressEditText);

        dobEditText.setEnabled(false);
        aadharEditText.setEnabled(false);
        bloodGroupEditText.setEnabled(false);
        currentAddressEditText.setEnabled(false);
        permanentAddressEditText.setEnabled(false);
        nationalityEditText.setEnabled(false);
        motherTongueEditText.setEnabled(false);
        universityEditText.setEnabled(false);
        yearEditText.setEnabled(false);
        fatherNameEditText.setEnabled(false);
        fatherOccupationEditText.setEnabled(false);
        fatherOfficeAddressEditText.setEnabled(false);
        fatherMobileEditText.setEnabled(false);
        motherNameEditText.setEnabled(false);
        motherMobileEditText.setEnabled(false);
        guardianNameEditText.setEnabled(false);
        guardianMobileEditText.setEnabled(false);
        guardianOfficeAddressEditText.setEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Tenants/" + id + "/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nameTextView.setText(dataSnapshot.child("name").getValue(String.class));
                phoneTextView.setText(dataSnapshot.child("phone").getValue(String.class));
                emailTextView.setText(dataSnapshot.child("email").getValue(String.class));

                selfieName = dataSnapshot.child("My Docs").child("Selfie").getValue(String.class);

                dob = dataSnapshot.child("Personal Detail").child("dob").getValue(String.class);
                aadhar = dataSnapshot.child("Personal Detail").child("aadhar").getValue(String.class);
                bloodGroup = dataSnapshot.child("Personal Detail").child("blood").getValue(String.class);
                currentAddress = dataSnapshot.child("Personal Detail").child("currentAddress").getValue(String.class);
                permanentAddress = dataSnapshot.child("Personal Detail").child("permanentAddress").getValue(String.class);
                nationality = dataSnapshot.child("Personal Detail").child("nationality").getValue(String.class);
                motherTongue = dataSnapshot.child("Personal Detail").child("motherTongue").getValue(String.class);
                university = dataSnapshot.child("Personal Detail").child("university").getValue(String.class);
                year = dataSnapshot.child("Personal Detail").child("year").getValue(String.class);
                fatherName = dataSnapshot.child("Personal Detail").child("fatherName").getValue(String.class);
                fatherOccupation = dataSnapshot.child("Personal Detail").child("fatherOccupation").getValue(String.class);
                fatherOfficeAddress = dataSnapshot.child("Personal Detail").child("officeAddress").getValue(String.class);
                fatherMobile = dataSnapshot.child("Personal Detail").child("fatherPhone").getValue(String.class);
                motherName = dataSnapshot.child("Personal Detail").child("motherName").getValue(String.class);
                motherMobile = dataSnapshot.child("Personal Detail").child("motherPhone").getValue(String.class);
                guardianName = dataSnapshot.child("Personal Detail").child("localGuardianName").getValue(String.class);
                guardianMobile = dataSnapshot.child("Personal Detail").child("localGuardianPhone").getValue(String.class);
                guardianOfficeAddress = dataSnapshot.child("Personal Detail").child("localGuardianAddress").getValue(String.class);

                dobEditText.setText(dob);
                aadharEditText.setText(aadhar);
                bloodGroupEditText.setText(bloodGroup);
                currentAddressEditText.setText(currentAddress);
                permanentAddressEditText.setText(permanentAddress);
                nationalityEditText.setText(nationality);
                motherTongueEditText.setText(motherTongue);
                universityEditText.setText(university);
                yearEditText.setText(year);
                fatherNameEditText.setText(fatherName);
                fatherOccupationEditText.setText(fatherOccupation);
                fatherOfficeAddressEditText.setText(fatherOfficeAddress);
                fatherMobileEditText.setText(fatherMobile);
                motherNameEditText.setText(motherName);
                motherMobileEditText.setText(motherMobile);
                guardianNameEditText.setText(guardianName);
                guardianMobileEditText.setText(guardianMobile);
                guardianOfficeAddressEditText.setText(guardianOfficeAddress);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
