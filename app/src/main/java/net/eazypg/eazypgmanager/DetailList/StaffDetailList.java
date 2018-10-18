package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.StaffDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

public class StaffDetailList extends ArrayAdapter<StaffDetails> {

    private Activity context;
    private List<StaffDetails> staffList;
    FloatingActionButton callButton;
    TextView staffDetailView;

    public StaffDetailList(Activity context, List<StaffDetails> staffList) {
        super(context, R.layout.staff_row, staffList);

        this.context = context;
        this.staffList = staffList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Staff/");
    List<String> ids = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemStaff = inflater.inflate(R.layout.staff_row, null, true);
        callButton = listViewItemStaff.findViewById(R.id.callButton);
        final StaffDetails staffDetails = staffList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    StaffDetails staffDetails1 = snapshot.getValue(StaffDetails.class);
                    String id = staffDetails1.staffId;
                    ids.add(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemStaff.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure you want to delete item?");
                builder.setIcon(R.drawable.ic_warning_black_24dp);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Deleting...", true);

                        String id = ids.get(position);

                        databaseReference.child(id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();

                return true;
            }
        });

        listViewItemStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View viewDialog = inflater.inflate(R.layout.dialog_staff, null);

                final EditText first, second, third, fourth;

                final TextView fifth;

                final View titleView = inflater.inflate(R.layout.custom_title3, null);
                staffDetailView = titleView.findViewById(R.id.staffCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(staffDetailView);
                first = viewDialog.findViewById(R.id.staffNameEditText);
                second = viewDialog.findViewById(R.id.jobDescEditText);
                third = viewDialog.findViewById(R.id.contactEditText);
                fourth = viewDialog.findViewById(R.id.salaryEditText);
                fifth = viewDialog.findViewById(R.id.dateOfJoiningEditText);

                first.setText(staffDetails.name);
                second.setText(staffDetails.jobDesc);
                third.setText(staffDetails.contact);
                fourth.setText(staffDetails.salary);
                fifth.setText(staffDetails.dateOfJoining);

                builder.setView(viewDialog);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Saving...", true);

                        String nameStaff = first.getText().toString();
                        String jobDesc = second.getText().toString();
                        String contact = third.getText().toString();
                        String salary = fourth.getText().toString();
                        String dateOfJoining = fifth.getText().toString();
                        ;
                        String uidStaff = ids.get(position);

                        if (nameStaff.equals("")) {

                            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } else {

                            StaffDetails staffDetails1 = new StaffDetails(uidStaff, salary, contact, nameStaff, jobDesc, dateOfJoining);
                            databaseReference.child(uidStaff).setValue(staffDetails1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.show();
            }
        });

        TextView first = listViewItemStaff.findViewById(R.id.firstTextView);
        TextView second = listViewItemStaff.findViewById(R.id.secondTextView);
        final TextView third = listViewItemStaff.findViewById(R.id.thirdTextView);
        //TextView fourth = listViewItemStaff.findViewById(R.id.fourthTextView);
        //TextView fifth = listViewItemStaff.findViewById(R.id.fifthTextView);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+staffDetails.contact));
                    context.startActivity(callIntent);
                }
                catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call failed", Toast.LENGTH_SHORT).show();
                }
                catch (SecurityException e) {
                    Toast.makeText(context, "Call failed!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        first.setText(staffDetails.getName());
        second.setText(staffDetails.getJobDesc());
        third.setText(staffDetails.getSalary());
        //fourth.setText(staffDetails.getSalary());
        //fifth.setText(staffDetails.getDateOfJoining());

        return listViewItemStaff;
    }

}
