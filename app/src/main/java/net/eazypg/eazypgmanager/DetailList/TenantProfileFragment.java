package net.eazypg.eazypgmanager.DetailList;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import net.eazypg.eazypgmanager.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class TenantProfileFragment extends Fragment {

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
    String id, selfieName, parentPhone;

    StorageReference storageReference;
    FirebaseStorage storage;

    ConstraintLayout callParentConstraintLayout;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.activity_tenant_dashboard_profile, container, false);

        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE3);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Documents");

        final long ONE_MEGABYTE = 1024 * 1024;

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Loading", "Please Wait..", true);

        if (selfieName != null) {

            storageReference.child(id).child(selfieName).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    circleImageView.setImageBitmap(bitmap);
                    progressDialog.dismiss();
                }
            });

        }
        else {

            progressDialog.dismiss();
        }

        phoneTextView = view.findViewById(R.id.phoneTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        nameTextView = view.findViewById(R.id.nameTextView);
        dobEditText = view.findViewById(R.id.dobEditText);
        bloodGroupEditText = view.findViewById(R.id.bloodGroupEditText);
        currentAddressEditText = view.findViewById(R.id.currentAddressEditText);
        permanentAddressEditText = view.findViewById(R.id.permanentAddressEditText);
        nationalityEditText = view.findViewById(R.id.nationalityEditText);
        motherTongueEditText = view.findViewById(R.id.motherTongueEditText);
        aadharEditText = view.findViewById(R.id.aadharEditText);
        universityEditText = view.findViewById(R.id.universityEditText);
        yearEditText = view.findViewById(R.id.yearEditText);
        fatherNameEditText = view.findViewById(R.id.fatherNameEditText);
        fatherOfficeAddressEditText = view.findViewById(R.id.fatherOfficeAddressEditText);
        fatherOccupationEditText = view.findViewById(R.id.fatherOccupationEditText);
        fatherMobileEditText = view.findViewById(R.id.fatherMobileEditText);
        motherNameEditText = view.findViewById(R.id.motherNameEditText);
        motherMobileEditText = view.findViewById(R.id.motherMobileEditText);
        guardianNameEditText = view.findViewById(R.id.guardianNameEditText);
        guardianMobileEditText = view.findViewById(R.id.guardianMobileEditText);
        guardianOfficeAddressEditText = view.findViewById(R.id.guardianOfficeAddressEditText);

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
        databaseReference = firebaseDatabase.getReference("Tenants/" + id);

        callParentConstraintLayout = view.findViewById(R.id.callParentConstraintLayout);

        callParentConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Clicked", "Clicked");

                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+parentPhone));
                    startActivity(callIntent);
                }
                catch (ActivityNotFoundException activityException) {
                    Toast.makeText(getContext(), "Call failed", Toast.LENGTH_SHORT).show();
                }
                catch (SecurityException e) {
                    Toast.makeText(getContext(), "Call failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nameTextView.setText(dataSnapshot.child("name").getValue(String.class));
                phoneTextView.setText(dataSnapshot.child("phone").getValue(String.class));
                emailTextView.setText(dataSnapshot.child("email").getValue(String.class));

                parentPhone = dataSnapshot.child("Personal Detail").child("fatherPhone").getValue(String.class);

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



        return view;
    }
}
