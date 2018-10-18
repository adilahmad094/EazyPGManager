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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailAC;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.ACDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ACDetailList extends ArrayAdapter<ApplianceDetailAC> {

    private Activity context;
    private List<ApplianceDetailAC> acList;

    ApplianceDetailAC applianceDetailAC;

    public ACDetailList(Activity context, List<ApplianceDetailAC> acList) {
        super(context, R.layout.appliance_row, acList);

        this.context = context;
        this.acList = acList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/AC");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemAC = inflater.inflate(R.layout.appliance_row, null, true);

            applianceDetailAC = acList.get(position);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ids.clear();
                    rooms.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ACDetails acDetails = snapshot.getValue(ACDetails.class);

                        String id = acDetails.id;
                        ids.add(id);

                        String room = acDetails.roomNo;
                        rooms.add(room);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        listViewItemAC.setOnLongClickListener(new View.OnLongClickListener() {
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

        listViewItemAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final TextView acCustomTitle;

                final EditText ACRoomNo,ACCompanyName,ACModel,ACCapacity,ACStarRating,ACType;
                final TextView ACLastServiceDate;
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

                ACLayout.setVisibility(View.VISIBLE);
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
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                final View titleView = inflater.inflate(R.layout.custom_titleac, null);
                acCustomTitle = titleView.findViewById(R.id.editAcCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(acCustomTitle);

                builder.setView(view);

                ACRoomNo = viewDialog.findViewById(R.id.ACRoomNumberEditText);
                ACCompanyName = viewDialog.findViewById(R.id.ACCompanyNameEditText);
                ACModel = viewDialog.findViewById(R.id.ACModelEditText);
                ACCapacity = viewDialog.findViewById(R.id.ACCapacityEditText);
                ACLastServiceDate = viewDialog.findViewById(R.id.ACLastServiceDateEditText);
                ACStarRating = viewDialog.findViewById(R.id.ACStarRatingEditText);
                ACType = viewDialog.findViewById(R.id.ACTypeEditText);

                ACRoomNo.setText(applianceDetailAC.roomNo);
                ACCompanyName.setText(applianceDetailAC.brand);
                ACModel.setText(applianceDetailAC.model);
                ACCapacity.setText(applianceDetailAC.capacity);
                ACLastServiceDate.setText(applianceDetailAC.lastServiceDate);
                ACStarRating.setText(applianceDetailAC.starRating);
                ACType.setText(applianceDetailAC.typeOfRating);

                final Calendar calendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        ACLastServiceDate.setText(sdf.format(calendar.getTime()));
                    }
                };

                ACLastServiceDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
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

                            String roomNoAC = ACRoomNo.getText().toString();
                            String brandAC = ACCompanyName.getText().toString();
                            String modelAC = ACModel.getText().toString();
                            String capacityAC = ACCapacity.getText().toString();
                            String lastServiceDateAC = ACLastServiceDate.getText().toString();
                            String starRatingAC = ACStarRating.getText().toString();
                            String ratingAC = ACType.getText().toString();
                            String uidAC = ids.get(position);

                            if (roomNoAC.equals("")) {

                                Toast.makeText(context, "Room number Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {

                                ACDetails acDetails = new ACDetails(uidAC, roomNoAC, brandAC, modelAC, capacityAC, lastServiceDateAC, starRatingAC, ratingAC);
                                databaseReference.child(uidAC).setValue(acDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        TextView first = listViewItemAC.findViewById(R.id.firstTextView);
        TextView second = listViewItemAC.findViewById(R.id.secondTextView);
        TextView third = listViewItemAC.findViewById(R.id.thirdTextView);
        TextView head = listViewItemAC.findViewById(R.id.textViewHead);
        TextView subHead1 = listViewItemAC.findViewById(R.id.textViewSubHead1);
        TextView subHead2 = listViewItemAC.findViewById(R.id.textViewSubHead2);

        first.setText(applianceDetailAC.getRoomNo());
        second.setText(applianceDetailAC.getBrand());
        third.setText(applianceDetailAC.getLastServiceDate());
        subHead2.setText("Last Service Date");

        return listViewItemAC;
    }


}
