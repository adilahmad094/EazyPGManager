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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailRefrigerator;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RefrigeratorDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RefrigeratorDetailList extends ArrayAdapter<ApplianceDetailRefrigerator> {

    private Activity context;
    private List<ApplianceDetailRefrigerator> RefrigeratorList;

    public RefrigeratorDetailList(Activity context, List<ApplianceDetailRefrigerator> RefrigeratorList) {
        super(context, R.layout.appliance_row, RefrigeratorList);

        this.context = context;
        this.RefrigeratorList = RefrigeratorList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Refrigerator");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemRefrigerator = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemRefrigerator.findViewById(R.id.firstTextView);
        TextView second = listViewItemRefrigerator.findViewById(R.id.secondTextView);
        TextView third = listViewItemRefrigerator.findViewById(R.id.thirdTextView);
        TextView head = listViewItemRefrigerator.findViewById(R.id.textViewHead);
        TextView subHead1 = listViewItemRefrigerator.findViewById(R.id.textViewSubHead1);
        TextView subHead2 = listViewItemRefrigerator.findViewById(R.id.textViewSubHead2);

        final ApplianceDetailRefrigerator applianceDetailRefrigerator= RefrigeratorList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    RefrigeratorDetails refrigeratorDetails = snapshot.getValue(RefrigeratorDetails.class);

                    String id = refrigeratorDetails.id;
                    ids.add(id);

                    String room = refrigeratorDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listViewItemRefrigerator.setOnLongClickListener(new View.OnLongClickListener() {
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

        listViewItemRefrigerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final EditText FridgeCapacity, FridgeCompanyName, FridgeModel, FridgeRoomNo, FridgeType, FridgeRating;
                final TextView refCustomTitle;
                final TextView FridgeDays;

                FridgeCapacity = viewDialog.findViewById(R.id.fridgeCapacityEditText);
                FridgeCompanyName = viewDialog.findViewById(R.id.fridgeCompanyNameEditText);
                FridgeDays = viewDialog.findViewById(R.id.fridgeDaysEditText);
                FridgeModel = viewDialog.findViewById(R.id.fridgeModelEditText);
                FridgeRoomNo = viewDialog.findViewById(R.id.fridgeRoomNumberEditText);
                FridgeType = viewDialog.findViewById(R.id.fridgeTypeEditText);
                FridgeRating = viewDialog.findViewById(R.id.fridgeRatingEditText);

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
                fridgeLayout.setVisibility(View.VISIBLE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);


                final View titleView = inflater.inflate(R.layout.custom_titleref, null);
                refCustomTitle = titleView.findViewById(R.id.editrefCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(refCustomTitle);

                builder.setView(view);

                FridgeCapacity.setText(applianceDetailRefrigerator.capacity);
                FridgeCompanyName.setText(applianceDetailRefrigerator.brand);
                FridgeDays.setText(applianceDetailRefrigerator.timeSinceInstallation);
                FridgeModel.setText(applianceDetailRefrigerator.model);
                FridgeRating.setText(applianceDetailRefrigerator.starRating);
                FridgeRoomNo.setText(applianceDetailRefrigerator.roomNo);
                FridgeType.setText(applianceDetailRefrigerator.typeOfRefrigerator);

                final Calendar calendarFridge = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateFridge = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarFridge.set(Calendar.YEAR, i);
                        calendarFridge.set(Calendar.MONTH, i1);
                        calendarFridge.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        FridgeDays.setText(sdf.format(calendarFridge.getTime()));
                    }
                };

                FridgeDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateFridge, calendarFridge
                                .get(Calendar.YEAR), calendarFridge.get(Calendar.MONTH),
                                calendarFridge.get(Calendar.DAY_OF_MONTH)).show();
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

                            String capacityFridge = FridgeCapacity.getText().toString();
                            String brandFridge = FridgeCompanyName.getText().toString();
                            String daysFridge = FridgeDays.getText().toString();
                            String modelFridge = FridgeModel.getText().toString();
                            String roomNoFridge = FridgeRoomNo.getText().toString();
                            String typeFridge = FridgeType.getText().toString();
                            String ratingFridge = FridgeRating.getText().toString();
                            String uidFridge = ids.get(position);

                            if (brandFridge.equals("")) {

                                Toast.makeText(context, "Brand Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {

                                RefrigeratorDetails refrigeratorDetails = new RefrigeratorDetails(uidFridge, roomNoFridge, brandFridge, modelFridge, daysFridge, capacityFridge, typeFridge, ratingFridge);
                                databaseReference.child(uidFridge).setValue(refrigeratorDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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


        first.setText(applianceDetailRefrigerator.getRoomNo());
        second.setText(applianceDetailRefrigerator.getBrand());
        third.setText(applianceDetailRefrigerator.getTypeOfRefrigerator());
        subHead2.setText("Type");
        return listViewItemRefrigerator;
    }

}
