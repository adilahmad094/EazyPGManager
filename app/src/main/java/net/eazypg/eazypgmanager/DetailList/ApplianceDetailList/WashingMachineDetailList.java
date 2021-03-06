package net.eazypg.eazypgmanager.DetailList.ApplianceDetailList;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailWashingMachine;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.WashingMachineDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WashingMachineDetailList extends ArrayAdapter<ApplianceDetailWashingMachine> {

    private Activity context;
    private List<ApplianceDetailWashingMachine> washingMachineList;

    public WashingMachineDetailList(Activity context, List<ApplianceDetailWashingMachine> washingMachineList) {
        super(context, R.layout.appliance_row, washingMachineList);

        this.context = context;
        this.washingMachineList = washingMachineList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Washing Machine");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemWashingMachine = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemWashingMachine.findViewById(R.id.firstTextView);
        TextView second = listViewItemWashingMachine.findViewById(R.id.secondTextView);
        TextView third = listViewItemWashingMachine.findViewById(R.id.thirdTextView);
        TextView head = listViewItemWashingMachine.findViewById(R.id.textViewHead);
        TextView subHead1 = listViewItemWashingMachine.findViewById(R.id.textViewSubHead1);
        TextView subHead2 = listViewItemWashingMachine.findViewById(R.id.textViewSubHead2);

        final ApplianceDetailWashingMachine applianceDetailWashingMachine = washingMachineList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    WashingMachineDetails washingMachineDetails = snapshot.getValue(WashingMachineDetails.class);

                    String id = washingMachineDetails.id;
                    ids.add(id);

                    String room = washingMachineDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemWashingMachine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure you want to delete item?");
                builder.setIcon(R.drawable.ic_warning_black_24dp);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectivityManager connectivityManager
                                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
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
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Rooms/" + rooms.get(position) + "/Appliance/" + ids.get(position));
                            databaseReference1.setValue(null);
                        }else{
                            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();

                return true;
            }
        });


        listViewItemWashingMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final EditText WashingMachineRoomNo, WashingMachineCompanyName, WashingMachineModel, WashingMachineCapacity, WashingMachinePower, WashingMachineRating, WashingMachineType;
                final TextView WashingMachineDays;
                final TextView washCustomTitle;

                WashingMachineRoomNo = viewDialog.findViewById(R.id.wmRoomNumberEditText);
                WashingMachineCompanyName = viewDialog.findViewById(R.id.wmCompanyNameEditText);
                WashingMachineModel = viewDialog.findViewById(R.id.wmModelEditText);
                WashingMachineDays = viewDialog.findViewById(R.id.wmDaysEditText);
                WashingMachineCapacity = viewDialog.findViewById(R.id.wmCapacityEditText);
                WashingMachinePower = viewDialog.findViewById(R.id.wmPowerEditText);
                WashingMachineRating = viewDialog.findViewById(R.id.wmRatingEditText);
                WashingMachineType = viewDialog.findViewById(R.id.wmTypeEditText);

                RelativeLayout ACLayout, fanLayout, liftLayout, geyserLayout, washingMachineLayout, ROLayout, dishwasherLayout, microwaveLayout,
                        fridgeLayout, TVLayout, CCTVLayout, ironLayout, inductionLayout, routerLayout, heaterLayout, D2HLayout, otherLayout;

                ACLayout = viewDialog.findViewById(R.id.ACLayout);
                fanLayout = viewDialog.findViewById(R.id.fanLayout);
                liftLayout = viewDialog.findViewById(R.id.liftLayout);
                geyserLayout = viewDialog.findViewById(R.id.geyserLayout);
                washingMachineLayout = viewDialog.findViewById(R.id.washingMachineLayout);
                ROLayout = viewDialog.findViewById(R.id.ROLayout);
                dishwasherLayout = viewDialog.findViewById(R.id.dishwasherLayout);
                microwaveLayout = viewDialog.findViewById(R.id.microwaveLayout);
                fridgeLayout = viewDialog.findViewById(R.id.fridgeLayout);
                TVLayout = viewDialog.findViewById(R.id.TVLayout);
                CCTVLayout = viewDialog.findViewById(R.id.CCTVLayout);
                ironLayout = viewDialog.findViewById(R.id.ironLayout);
                inductionLayout = viewDialog.findViewById(R.id.inductionLayout);
                routerLayout = viewDialog.findViewById(R.id.routerLayout);
                heaterLayout = viewDialog.findViewById(R.id.heaterLayout);
                D2HLayout = viewDialog.findViewById(R.id.D2HLayout);
                otherLayout = viewDialog.findViewById(R.id.otherLayout);

                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.VISIBLE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);


                final View titleView = inflater.inflate(R.layout.custom_titlewash, null);
                washCustomTitle = titleView.findViewById(R.id.editWashMCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(washCustomTitle);

                builder.setView(view);

                WashingMachineCapacity.setText(applianceDetailWashingMachine.capacity);
                WashingMachineCompanyName.setText(applianceDetailWashingMachine.brand);
                WashingMachineDays.setText(applianceDetailWashingMachine.timeSinceInstallation);
                WashingMachineModel.setText(applianceDetailWashingMachine.model);
                WashingMachinePower.setText(applianceDetailWashingMachine.inputPower);
                WashingMachineRating.setText(applianceDetailWashingMachine.starRating);
                WashingMachineRoomNo.setText(applianceDetailWashingMachine.roomNo);
                WashingMachineType.setText(applianceDetailWashingMachine.typeOfMachine);

                final Calendar calendarWM = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateWM = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarWM.set(Calendar.YEAR, i);
                        calendarWM.set(Calendar.MONTH, i1);
                        calendarWM.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        WashingMachineDays.setText(sdf.format(calendarWM.getTime()));
                    }
                };

                WashingMachineDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateWM, calendarWM
                                .get(Calendar.YEAR), calendarWM.get(Calendar.MONTH),
                                calendarWM.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                builder.setView(viewDialog);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ConnectivityManager connectivityManager
                                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Saving...", true);

                            String roomNoWashingMachine = WashingMachineRoomNo.getText().toString();
                            String brandWashingMachine = WashingMachineCompanyName.getText().toString();
                            String modelWashingMachine = WashingMachineModel.getText().toString();
                            String daysWashingMachine = WashingMachineDays.getText().toString();
                            String capacityWashingMachine = WashingMachineCapacity.getText().toString();
                            String powerWashingMachine = WashingMachinePower.getText().toString();
                            String ratingWashingMachine = WashingMachineRating.getText().toString();
                            String typeWashingMachine = WashingMachineType.getText().toString();
                            String uidWashingMachine = ids.get(position);

                            if (brandWashingMachine.equals("")) {

                                Toast.makeText(context, "Brand Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {

                                WashingMachineDetails washingMachineDetails = new WashingMachineDetails(uidWashingMachine, roomNoWashingMachine, brandWashingMachine, modelWashingMachine, daysWashingMachine, capacityWashingMachine, powerWashingMachine, ratingWashingMachine, typeWashingMachine);
                                databaseReference.child(uidWashingMachine).setValue(washingMachineDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                        }else{
                            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel",null);

                builder.show();
            }
        });


        first.setText(applianceDetailWashingMachine.getRoomNo());
        second.setText(applianceDetailWashingMachine.getBrand());
        third.setText(applianceDetailWashingMachine.getTypeOfMachine());
        subHead2.setText("Type");

        return listViewItemWashingMachine;
    }

}
