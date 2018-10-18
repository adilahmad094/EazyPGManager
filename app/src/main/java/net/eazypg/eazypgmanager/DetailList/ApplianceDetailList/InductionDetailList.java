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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailInduction;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.InductionDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InductionDetailList extends ArrayAdapter<ApplianceDetailInduction> {

    private Activity context;
    private List<ApplianceDetailInduction> InductionList;

    public InductionDetailList(Activity context, List<ApplianceDetailInduction> InductionList) {
        super(context, R.layout.appliance_row, InductionList);

        this.context = context;
        this.InductionList = InductionList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Induction");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemInduction = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemInduction.findViewById(R.id.firstTextView);
        TextView second = listViewItemInduction.findViewById(R.id.secondTextView);
        TextView third = listViewItemInduction.findViewById(R.id.thirdTextView);

        final ApplianceDetailInduction applianceDetailInduction = InductionList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    InductionDetails inductionDetails = snapshot.getValue(InductionDetails.class);

                    String id = inductionDetails.id;
                    ids.add(id);

                    String room = inductionDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemInduction.setOnLongClickListener(new View.OnLongClickListener() {
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


        listViewItemInduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final EditText InductionCompanyName, InductionModel, InductionNoCooktop, InductionPower, InductionRoomNo, InductionType;
                final TextView inductionCustomTitle;
                final TextView InductionDays;

                InductionCompanyName = viewDialog.findViewById(R.id.inductionCompanyNameEditText);
                InductionDays = viewDialog.findViewById(R.id.inductionDaysEditText);
                InductionModel = viewDialog.findViewById(R.id.inductionModelEditText);
                InductionNoCooktop = viewDialog.findViewById(R.id.inductionNumberCooktopEditText);
                InductionPower = viewDialog.findViewById(R.id.inductionPowerEditText);
                InductionRoomNo = viewDialog.findViewById(R.id.inductionRoomNumberEditText);
                InductionType = viewDialog.findViewById(R.id.inductionControlTypeEditText);

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
                inductionLayout.setVisibility(View.VISIBLE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);


                final View titleView = inflater.inflate(R.layout.custom_titleinduction, null);
                inductionCustomTitle = titleView.findViewById(R.id.editInductionCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(inductionCustomTitle);

                builder.setView(view);

                InductionCompanyName.setText(applianceDetailInduction.brand);
                InductionDays.setText(applianceDetailInduction.timeSinceInstallation);
                InductionModel.setText(applianceDetailInduction.model);
                InductionNoCooktop.setText(applianceDetailInduction.noOfCooktops);
                InductionPower.setText(applianceDetailInduction.inputPower);
                InductionRoomNo.setText(applianceDetailInduction.roomNo);
                InductionType.setText(applianceDetailInduction.controlType);

                final Calendar calendarInduction = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateInduction = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarInduction.set(Calendar.YEAR, i);
                        calendarInduction.set(Calendar.MONTH, i1);
                        calendarInduction.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        InductionDays.setText(sdf.format(calendarInduction.getTime()));
                    }
                };

                InductionDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateInduction, calendarInduction
                                .get(Calendar.YEAR), calendarInduction.get(Calendar.MONTH),
                                calendarInduction.get(Calendar.DAY_OF_MONTH)).show();
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

                            String brandInduction = InductionCompanyName.getText().toString();
                            String daysInduction = InductionDays.getText().toString();
                            String modelInduction = InductionModel.getText().toString();
                            String noCooktopInduction = InductionNoCooktop.getText().toString();
                            String powerInduction = InductionPower.getText().toString();
                            String roomNoInduction = InductionRoomNo.getText().toString();
                            String typeInduction = InductionType.getText().toString();
                            String uidInduction = ids.get(position);

                            if (brandInduction.equals("")) {

                                Toast.makeText(context, "Brand Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {

                                InductionDetails inductionDetails = new InductionDetails(uidInduction, roomNoInduction, brandInduction, modelInduction, daysInduction, powerInduction, typeInduction, noCooktopInduction);
                                databaseReference.child(uidInduction).setValue(inductionDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        first.setText(applianceDetailInduction.getRoomNo());
        second.setText(applianceDetailInduction.getBrand());
        third.setText(applianceDetailInduction.getTimeSinceInstallation());


        return listViewItemInduction;
    }

}
