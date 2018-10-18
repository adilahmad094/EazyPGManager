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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailGeyser;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.GeyserDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GeyserDetailList extends ArrayAdapter<ApplianceDetailGeyser> {

    private Activity context;
    private List<ApplianceDetailGeyser> geyserList;

    public GeyserDetailList(Activity context, List<ApplianceDetailGeyser> geyserList) {
        super(context, R.layout.appliance_row, geyserList);

        this.context = context;
        this.geyserList = geyserList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Geyser");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemGeyser = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemGeyser.findViewById(R.id.firstTextView);
        TextView second = listViewItemGeyser.findViewById(R.id.secondTextView);
        TextView third = listViewItemGeyser.findViewById(R.id.thirdTextView);

        final ApplianceDetailGeyser applianceDetailGeyser = geyserList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    GeyserDetails geyserDetails = snapshot.getValue(GeyserDetails.class);

                    String id = geyserDetails.id;
                    ids.add(id);

                    String room = geyserDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemGeyser.setOnLongClickListener(new View.OnLongClickListener() {
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


        listViewItemGeyser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final EditText GeyserRoomNo, GeyserCompanyName, GeyserModel, GeyserCapacity, GeyserPower, GeyserRating;
                final TextView geyserCustomTitle;
                final TextView GeyserDays;

                GeyserRoomNo = viewDialog.findViewById(R.id.geyserRoomNumberEditText);
                GeyserCompanyName = viewDialog.findViewById(R.id.geyserCompanyNameEditText);
                GeyserModel = viewDialog.findViewById(R.id.geyserModelEditText);
                GeyserDays = viewDialog.findViewById(R.id.geyserDaysEditText);
                GeyserCapacity = viewDialog.findViewById(R.id.geyserCapacityEditText);
                GeyserPower = viewDialog.findViewById(R.id.geyserPowerEditText);
                GeyserRating = viewDialog.findViewById(R.id.geyserRatingEditText);

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
                geyserLayout.setVisibility(View.VISIBLE);
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
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);


                final View titleView = inflater.inflate(R.layout.custom_titlegeyser, null);
                geyserCustomTitle = titleView.findViewById(R.id.editgeyserCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(geyserCustomTitle);

                builder.setView(view);

                GeyserCapacity.setText(applianceDetailGeyser.capacity);
                GeyserCompanyName.setText(applianceDetailGeyser.brand);
                GeyserDays.setText(applianceDetailGeyser.timeSinceInstallation);
                GeyserModel.setText(applianceDetailGeyser.model);
                GeyserPower.setText(applianceDetailGeyser.inputPower);
                GeyserRating.setText(applianceDetailGeyser.starRating);
                GeyserRoomNo.setText(applianceDetailGeyser.roomNo);

                final Calendar calendarGeyser = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateGeyser = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarGeyser.set(Calendar.YEAR, i);
                        calendarGeyser.set(Calendar.MONTH, i1);
                        calendarGeyser.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        GeyserDays.setText(sdf.format(calendarGeyser.getTime()));
                    }
                };

                GeyserDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateGeyser, calendarGeyser
                                .get(Calendar.YEAR), calendarGeyser.get(Calendar.MONTH),
                                calendarGeyser.get(Calendar.DAY_OF_MONTH)).show();
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

                            String roomNoGeyser = GeyserRoomNo.getText().toString();
                            String brandGeyser = GeyserCompanyName.getText().toString();
                            String modelGeyser = GeyserModel.getText().toString();
                            String daysGeyser = GeyserDays.getText().toString();
                            String capacityGeyser = GeyserCapacity.getText().toString();
                            String powerGeyser = GeyserPower.getText().toString();
                            String ratingGeyser = GeyserRating.getText().toString();
                            String uidGeyser = ids.get(position);

                            if (roomNoGeyser.equals("")) {

                                Toast.makeText(context, "Room number Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {

                                GeyserDetails geyserDetails = new GeyserDetails(uidGeyser, roomNoGeyser, brandGeyser, modelGeyser, daysGeyser, capacityGeyser, powerGeyser, ratingGeyser);
                                databaseReference.child(uidGeyser).setValue(geyserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        first.setText(applianceDetailGeyser.getRoomNo());
        second.setText(applianceDetailGeyser.getBrand());
        third.setText(applianceDetailGeyser.getTimeSinceInstallation());

        return listViewItemGeyser;
    }


}
