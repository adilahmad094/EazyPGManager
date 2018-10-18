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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailHeater;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.HeaterDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HeaterDetailList extends ArrayAdapter<ApplianceDetailHeater> {

    private Activity context;
    private List<ApplianceDetailHeater> HeaterList;

    public HeaterDetailList(Activity context, List<ApplianceDetailHeater> HeaterList) {
        super(context, R.layout.appliance_row, HeaterList);

        this.context = context;
        this.HeaterList = HeaterList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Heater");
    List<String> ids = new ArrayList<>();
    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemHeater = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemHeater.findViewById(R.id.firstTextView);
        TextView second = listViewItemHeater.findViewById(R.id.secondTextView);
        TextView third = listViewItemHeater.findViewById(R.id.thirdTextView);
        TextView head = listViewItemHeater.findViewById(R.id.textViewHead);
        TextView subHead1 = listViewItemHeater.findViewById(R.id.textViewSubHead1);
        TextView subHead2 = listViewItemHeater.findViewById(R.id.textViewSubHead2);

        final ApplianceDetailHeater applianceDetailHeater= HeaterList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    HeaterDetails heaterDetails = snapshot.getValue(HeaterDetails.class);

                    String id = heaterDetails.id;
                    ids.add(id);

                    String room = heaterDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemHeater.setOnLongClickListener(new View.OnLongClickListener() {
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
                        }else {
                            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();

                return true;
            }
        });


        listViewItemHeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final EditText HeaterCompanyName, HeaterModel, HeaterPower, HeaterRoomNo,HeaterWeight;
                final TextView heaterCustomTitle;
                final TextView HeaterDays;

                HeaterCompanyName = viewDialog.findViewById(R.id.heaterCompanyNameEditText);
                HeaterDays = viewDialog.findViewById(R.id.heaterDaysEditText);
                HeaterModel = viewDialog.findViewById(R.id.heaterModelEditText);
                HeaterPower = viewDialog.findViewById(R.id.heaterPowerEditText);
                HeaterRoomNo = viewDialog.findViewById(R.id.heaterRoomNumberEditText);
                HeaterWeight = viewDialog.findViewById(R.id.heaterWeightEditText);

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
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.VISIBLE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);


                final View titleView = inflater.inflate(R.layout.custom_titleheater, null);
                heaterCustomTitle = titleView.findViewById(R.id.editHeaterCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(heaterCustomTitle);

                builder.setView(view);

                HeaterCompanyName.setText(applianceDetailHeater.brand);
                HeaterDays.setText(applianceDetailHeater.inputPower);
                HeaterModel.setText(applianceDetailHeater.model);
                HeaterPower.setText(applianceDetailHeater.inputPower);
                HeaterRoomNo.setText(applianceDetailHeater.roomNo);
                HeaterWeight.setText(applianceDetailHeater.weight);


                final Calendar calendarHeater = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateHeater = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarHeater.set(Calendar.YEAR, i);
                        calendarHeater.set(Calendar.MONTH, i1);
                        calendarHeater.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        HeaterDays.setText(sdf.format(calendarHeater.getTime()));
                    }
                };

                HeaterDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateHeater, calendarHeater
                                .get(Calendar.YEAR), calendarHeater.get(Calendar.MONTH),
                                calendarHeater.get(Calendar.DAY_OF_MONTH)).show();
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

                            String brandHeater = HeaterCompanyName.getText().toString();
                            String daysHeater = HeaterDays.getText().toString();
                            String modelHeater = HeaterModel.getText().toString();
                            String powerHeater = HeaterPower.getText().toString();
                            String roomNoHeater = HeaterRoomNo.getText().toString();
                            String weightHeater = HeaterWeight.getText().toString();
                            String uidHeater = ids.get(position);

                            if (roomNoHeater.equals("")) {

                                Toast.makeText(context, "Room number Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {

                                HeaterDetails heaterDetails = new HeaterDetails(uidHeater, roomNoHeater, brandHeater, modelHeater, daysHeater, powerHeater, weightHeater);
                                databaseReference.child(uidHeater).setValue(heaterDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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


        first.setText(applianceDetailHeater.getRoomNo());
        second.setText(applianceDetailHeater.getBrand());
        third.setText(applianceDetailHeater.getInputPower());
        subHead2.setText("Power");

        return listViewItemHeater;
    }

}
