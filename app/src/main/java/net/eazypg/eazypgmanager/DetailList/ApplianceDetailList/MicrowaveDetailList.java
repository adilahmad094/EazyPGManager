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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailMicrowave;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.MicrowaveDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MicrowaveDetailList extends ArrayAdapter<ApplianceDetailMicrowave> {

    private Activity context;
    private List<ApplianceDetailMicrowave> MicrowaveList;

    public MicrowaveDetailList(Activity context, List<ApplianceDetailMicrowave> MicrowaveList) {
        super(context, R.layout.appliance_row, MicrowaveList);

        this.context = context;
        this.MicrowaveList = MicrowaveList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Microwave");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
        View listViewItemMicrowave = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemMicrowave.findViewById(R.id.firstTextView);
        TextView second = listViewItemMicrowave.findViewById(R.id.secondTextView);
        TextView third = listViewItemMicrowave.findViewById(R.id.thirdTextView);
        TextView head = listViewItemMicrowave.findViewById(R.id.textViewHead);
        TextView subHead1 = listViewItemMicrowave.findViewById(R.id.textViewSubHead1);
        TextView subHead2 = listViewItemMicrowave.findViewById(R.id.textViewSubHead2);

        final ApplianceDetailMicrowave applianceDetailMicrowave= MicrowaveList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MicrowaveDetails microwaveDetails = snapshot.getValue(MicrowaveDetails.class);

                    String id = microwaveDetails.id;
                    ids.add(id);

                    String room = microwaveDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemMicrowave.setOnLongClickListener(new View.OnLongClickListener() {
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


        listViewItemMicrowave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText MicrowaveCapacity, MicrowaveCompanyName, MicrowaveModel, MicrowaveType, MicrowaveRoomNo;
                final TextView MicrowaveDays;
                final TextView microCustomTitle;


                MicrowaveCapacity = viewDialog.findViewById(R.id.microwaveCapacityEditText);
                MicrowaveCompanyName = viewDialog.findViewById(R.id.microwaveCompanyNameEditText);
                MicrowaveDays = viewDialog.findViewById(R.id.microwaveDaysEditText);
                MicrowaveModel = viewDialog.findViewById(R.id.microwaveModelEditText);
                MicrowaveType = viewDialog.findViewById(R.id.microwaveTypeEditText);
                MicrowaveRoomNo = viewDialog.findViewById(R.id.microwaveRoomNumberEditText);

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
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.VISIBLE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);


                final View titleView = inflater.inflate(R.layout.custom_microtitle, null);
                microCustomTitle = titleView.findViewById(R.id.editmicroCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(microCustomTitle);

                builder.setView(view);

                MicrowaveCapacity.setText(applianceDetailMicrowave.capacity);
                MicrowaveCompanyName.setText(applianceDetailMicrowave.brand);
                MicrowaveDays.setText(applianceDetailMicrowave.timeSinceInstallation);
                MicrowaveModel.setText(applianceDetailMicrowave.model);
                MicrowaveRoomNo.setText(applianceDetailMicrowave.roomNo);
                MicrowaveType.setText(applianceDetailMicrowave.controlType);

                final Calendar calendarMicrowave = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateMicrowave = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarMicrowave.set(Calendar.YEAR, i);
                        calendarMicrowave.set(Calendar.MONTH, i1);
                        calendarMicrowave.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        MicrowaveDays.setText(sdf.format(calendarMicrowave.getTime()));
                    }
                };

                MicrowaveDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateMicrowave, calendarMicrowave
                                .get(Calendar.YEAR), calendarMicrowave.get(Calendar.MONTH),
                                calendarMicrowave.get(Calendar.DAY_OF_MONTH)).show();
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

                            String capacityMicrowave = MicrowaveCapacity.getText().toString();
                            String brandMicrowave = MicrowaveCompanyName.getText().toString();
                            String daysMicrowave = MicrowaveDays.getText().toString();
                            String modelMicrowave = MicrowaveModel.getText().toString();
                            String typeMicrowave = MicrowaveType.getText().toString();
                            String roomNoMicrowave = MicrowaveRoomNo.getText().toString();
                            String uidMicrowave = ids.get(position);

                            if (brandMicrowave.equals("")) {

                                Toast.makeText(context, "Brand Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {
                                MicrowaveDetails microwaveDetails = new MicrowaveDetails(uidMicrowave, roomNoMicrowave, brandMicrowave, modelMicrowave, daysMicrowave, capacityMicrowave, typeMicrowave);
                                databaseReference.child(uidMicrowave).setValue(microwaveDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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


        first.setText(applianceDetailMicrowave.getRoomNo());
        second.setText(applianceDetailMicrowave.getBrand());
        third.setText(applianceDetailMicrowave.getCapacity());
        subHead2.setText("Capacity");

        return listViewItemMicrowave;
    }

}
