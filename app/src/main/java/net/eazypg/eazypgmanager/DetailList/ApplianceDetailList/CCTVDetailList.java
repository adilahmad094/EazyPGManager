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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailCCTV;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.CCTVDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CCTVDetailList extends ArrayAdapter<ApplianceDetailCCTV> {

    private Activity context;
    private List<ApplianceDetailCCTV> CCTVList;

    public CCTVDetailList(Activity context, List<ApplianceDetailCCTV> CCTVList) {
        super(context, R.layout.appliance_row, CCTVList);

        this.context = context;
        this.CCTVList = CCTVList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/CCTV");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemCCTV = inflater.inflate(R.layout.appliance_row, null, true);

        final ApplianceDetailCCTV applianceDetailCCTV = CCTVList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    CCTVDetails cctvDetails = snapshot.getValue(CCTVDetails.class);

                    String id = cctvDetails.id;
                    ids.add(id);

                    String room = cctvDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemCCTV.setOnLongClickListener(new View.OnLongClickListener() {
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


        listViewItemCCTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final TextView cctvCustomTitle;

                final TextView CCTVDays;
                final EditText CCTVChanel,CCTVCompanyName,CCTVModel,CCTVNight,CCTVResolution,CCTVRoomNo;
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
                CCTVLayout.setVisibility(View.VISIBLE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                CCTVChanel = viewDialog.findViewById(R.id.CCTVChannelEditText);
                CCTVCompanyName = viewDialog.findViewById(R.id.CCTVCompanyNameEditText);
                CCTVDays = viewDialog.findViewById(R.id.CCTVDaysEditText);
                CCTVModel = viewDialog.findViewById(R.id.CCTVModelEditText);
                CCTVNight = viewDialog.findViewById(R.id.CCTVNightEditText);
                CCTVResolution = viewDialog.findViewById(R.id.CCTVResolutionEditText);
                CCTVRoomNo = viewDialog.findViewById(R.id.CCTVRoomNumberEditText);


                final View titleView = inflater.inflate(R.layout.custom_titlecctv, null);
                cctvCustomTitle = titleView.findViewById(R.id.editcctvCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(cctvCustomTitle);

                builder.setView(view);

                CCTVRoomNo.setText(applianceDetailCCTV.roomNo);
                CCTVCompanyName.setText(applianceDetailCCTV.brand);
                CCTVModel.setText(applianceDetailCCTV.model);
                CCTVChanel.setText(applianceDetailCCTV.noOfChannels);
                CCTVDays.setText(applianceDetailCCTV.timeSinceInstallation);
                CCTVResolution.setText(applianceDetailCCTV.recordingResolution);
                CCTVNight.setText(applianceDetailCCTV.nightVision);

                final Calendar calendarCCTV = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateCCTV = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarCCTV.set(Calendar.YEAR, i);
                        calendarCCTV.set(Calendar.MONTH, i1);
                        calendarCCTV.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        CCTVDays.setText(sdf.format(calendarCCTV.getTime()));
                    }
                };

                CCTVDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateCCTV, calendarCCTV
                                .get(Calendar.YEAR), calendarCCTV.get(Calendar.MONTH),
                                calendarCCTV.get(Calendar.DAY_OF_MONTH)).show();
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

                            String channelCCTV = CCTVChanel.getText().toString();
                            String brandCCTV = CCTVCompanyName.getText().toString();
                            String daysCCTV = CCTVDays.getText().toString();
                            String modelCCTV = CCTVModel.getText().toString();
                            String nightCCTV = CCTVNight.getText().toString();
                            String resolutionCCTV = CCTVResolution.getText().toString();
                            String roomNoCCTV = CCTVRoomNo.getText().toString();
                            String uidCCTV = ids.get(position);

                            if (roomNoCCTV.equals("") && brandCCTV.equals("") && daysCCTV.equals("")) {

                                Toast.makeText(context, "Room number , Brand and Date of Installation Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {

                                CCTVDetails cctvDetails = new CCTVDetails(uidCCTV, roomNoCCTV, brandCCTV, modelCCTV, daysCCTV, nightCCTV, channelCCTV, resolutionCCTV);
                                databaseReference.child(uidCCTV).setValue(cctvDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        TextView first = listViewItemCCTV.findViewById(R.id.firstTextView);
        TextView second = listViewItemCCTV.findViewById(R.id.secondTextView);
        TextView third = listViewItemCCTV.findViewById(R.id.thirdTextView);
        TextView head = listViewItemCCTV.findViewById(R.id.textViewHead);
        TextView subHead1 = listViewItemCCTV.findViewById(R.id.textViewSubHead1);
        TextView subHead2 = listViewItemCCTV.findViewById(R.id.textViewSubHead2);

        first.setText(applianceDetailCCTV.getRoomNo());
        second.setText(applianceDetailCCTV.getBrand());
        third.setText(applianceDetailCCTV.getTimeSinceInstallation());

        return listViewItemCCTV;
    }

}
