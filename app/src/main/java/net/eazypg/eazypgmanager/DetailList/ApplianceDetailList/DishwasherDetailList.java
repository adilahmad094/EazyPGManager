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

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailDishwasher;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.DishwasherDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DishwasherDetailList extends ArrayAdapter<ApplianceDetailDishwasher> {

    private Activity context;
    private List<ApplianceDetailDishwasher> DishwasherList;

    public DishwasherDetailList(Activity context, List<ApplianceDetailDishwasher> DishwasherList) {
        super(context, R.layout.appliance_row, DishwasherList);

        this.context = context;
        this.DishwasherList = DishwasherList;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG/"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Appliances/Dishwasher");
    List<String> ids = new ArrayList<>();

    List<String> rooms = new ArrayList<>();

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemDishwasher = inflater.inflate(R.layout.appliance_row, null, true);

        TextView first = listViewItemDishwasher.findViewById(R.id.firstTextView);
        TextView second = listViewItemDishwasher.findViewById(R.id.secondTextView);
        TextView third = listViewItemDishwasher.findViewById(R.id.thirdTextView);

        final ApplianceDetailDishwasher applianceDetailDishwasher= DishwasherList.get(position);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    DishwasherDetails dishwasherDetails = snapshot.getValue(DishwasherDetails.class);

                    String id = dishwasherDetails.id;
                    ids.add(id);

                    String room = dishwasherDetails.roomNo;
                    rooms.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewItemDishwasher.setOnLongClickListener(new View.OnLongClickListener() {
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


        listViewItemDishwasher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View viewDialog = inflater.inflate(R.layout.dialog_appliance, null);
                final EditText DishwasherCompanyName,DishwasherCapacity, DishwasherModel, DishwasherRoomNo, DishwasherType;
                final TextView dishwasherCustomTitle;
                final TextView DishwasherDays;

                DishwasherCapacity = viewDialog.findViewById(R.id.dishwasherCapacityEditText);
                DishwasherCompanyName = viewDialog.findViewById(R.id.dishwasherCompanyNameEditText);
                DishwasherDays = viewDialog.findViewById(R.id.dishwasherDaysEditText);
                DishwasherModel = viewDialog.findViewById(R.id.dishwasherModelEditText);
                DishwasherRoomNo = viewDialog.findViewById(R.id.dishwasherRoomNumberEditText);
                DishwasherType = viewDialog.findViewById(R.id.dishwasherTypeEditText);

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
                dishwasherLayout.setVisibility(View.VISIBLE);
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


                final View titleView = inflater.inflate(R.layout.custom_titledish, null);
                dishwasherCustomTitle = titleView.findViewById(R.id.editdishCustomTitle);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCustomTitle(dishwasherCustomTitle);

                builder.setView(view);
                DishwasherCapacity.setText(applianceDetailDishwasher.capacity);
                DishwasherCompanyName.setText(applianceDetailDishwasher.brand);
                DishwasherDays.setText(applianceDetailDishwasher.timeSinceInstallation);
                DishwasherModel.setText(applianceDetailDishwasher.model);
                DishwasherRoomNo.setText(applianceDetailDishwasher.roomNo);
                DishwasherType.setText(applianceDetailDishwasher.typeOfDishwasher);

                final Calendar calendarDishwasher = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateDishwasher = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarDishwasher.set(Calendar.YEAR, i);
                        calendarDishwasher.set(Calendar.MONTH, i1);
                        calendarDishwasher.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        DishwasherDays.setText(sdf.format(calendarDishwasher.getTime()));
                    }
                };

                DishwasherDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context, dateDishwasher, calendarDishwasher
                                .get(Calendar.YEAR), calendarDishwasher.get(Calendar.MONTH),
                                calendarDishwasher.get(Calendar.DAY_OF_MONTH)).show();
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

                            String capacityDishwasher = DishwasherCapacity.getText().toString();
                            String brandDishwasher = DishwasherCompanyName.getText().toString();
                            String daysDishwasher = DishwasherDays.getText().toString();
                            String modelDishwasher = DishwasherModel.getText().toString();
                            String roomNoDishwasher = DishwasherRoomNo.getText().toString();
                            String typeDishwasher = DishwasherType.getText().toString();
                            String uidDishwasher = ids.get(position);

                            if (brandDishwasher.equals("")) {

                                Toast.makeText(context, "Brand Required", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {
                                DishwasherDetails dishwasherDetails = new DishwasherDetails(uidDishwasher, roomNoDishwasher, brandDishwasher, modelDishwasher, daysDishwasher, capacityDishwasher, typeDishwasher);
                                databaseReference.child(uidDishwasher).setValue(dishwasherDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
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


        first.setText(applianceDetailDishwasher.getRoomNo());
        second.setText(applianceDetailDishwasher.getBrand());
        third.setText(applianceDetailDishwasher.getTimeSinceInstallation());

        return listViewItemDishwasher;
    }

}
