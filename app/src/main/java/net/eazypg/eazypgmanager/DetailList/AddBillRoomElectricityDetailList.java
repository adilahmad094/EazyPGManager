package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.BillDetails;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBillRoomElectricityDetailList extends RecyclerView.Adapter<AddBillRoomElectricityDetailList.MyHolder>{

    List<String> roomsList;
    List<String> roomsTypeList;
    String unitCost;
    Context context;

    public AddBillRoomElectricityDetailList(Context context, List<String> roomsList, List<String> roomsTypeList, String unitCost) {
        this.roomsList = roomsList;
        this.roomsTypeList = roomsTypeList;
        this.unitCost = unitCost;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_electricity_bill_room_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final String roomNumber = roomsList.get(position);
        final String roomType = roomsTypeList.get(position);

        holder.roomNumberTextView.setText(roomNumber);
        holder.roomTypeTextView.setText(roomType);
        holder.costPerUnit.setText(unitCost);

        //final List<TenantDetails> tenantList = new ArrayList<>();
        final List<TenantDetails> tenantRoomList = new ArrayList<>();

        holder.saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                final Date date = new Date();
                final String dateStr = dateFormat.format(date);

                final String dateString = dateStr.substring(6,10) + "-" + dateStr.substring(3,5);

                final String unitsThisMonth = holder.unitsEditText.getText().toString();

                if(!unitsThisMonth.equals("")){
                    DatabaseReference databaseReference5 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomNumber + "/Meter Reading/" + dateString);
                    databaseReference5.setValue(unitsThisMonth);

                    final DatabaseReference databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomNumber + "/Tenant/CurrentTenants/");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //tenantList.clear();
                            tenantRoomList.clear();

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                                tenantRoomList.add(tenantDetails);
                            }

                            if(tenantRoomList.size() == 0){
                                Toast.makeText(context, "No Tenants In Room.", Toast.LENGTH_SHORT).show();
                            }

                            String prevDate = dateString;

                            switch (dateStr.substring(3, 5)) {
                                case "01":
                                    prevDate = Integer.toString(Integer.parseInt(dateStr.substring(6, 10)) - 1) + "-12";
                                    break;
                                case "02":
                                    prevDate = dateStr.substring(6, 10) + "-01";
                                    break;
                                case "03":
                                    prevDate = dateStr.substring(6, 10) + "-02";
                                    break;
                                case "04":
                                    prevDate = dateStr.substring(6, 10) + "-03";
                                    break;
                                case "05":
                                    prevDate = dateStr.substring(6, 10) + "-04";
                                    break;
                                case "06":
                                    prevDate = dateStr.substring(6, 10) + "-05";
                                    break;
                                case "07":
                                    prevDate = dateStr.substring(6, 10) + "-06";
                                    break;
                                case "08":
                                    prevDate = dateStr.substring(6, 10) + "-07";
                                    break;
                                case "09":
                                    prevDate = dateStr.substring(6, 10) + "-08";
                                    break;
                                case "10":
                                    prevDate = dateStr.substring(6, 10) + "-09";
                                    break;
                                case "11":
                                    prevDate = dateStr.substring(6, 10) + "-10";
                                    break;
                                case "12":
                                    prevDate = dateStr.substring(6, 10) + "-11";
                                    break;
                            }

                            final DatabaseReference databaseReference4 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomNumber + "/Meter Reading/" + prevDate);
                            databaseReference4.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String prevReading = dataSnapshot.getValue(String.class);

                                    String billAmount = "0";
                                    if(prevReading != null && unitCost != null){
                                        billAmount = Float.toString((Float.parseFloat(unitsThisMonth) - Float.parseFloat(prevReading)) * Float.parseFloat(unitCost));
                                    }else if(unitCost != null){
                                        billAmount = Float.toString((Float.parseFloat(unitsThisMonth)) * Float.parseFloat(unitCost));
                                    }else if(unitCost == null){
                                        billAmount = Float.toString((Float.parseFloat(unitsThisMonth)) * 10);
                                    }

                                    for(int i = 0; i < tenantRoomList.size(); i++){

                                        String bill = Float.toString(Float.parseFloat(billAmount) / tenantRoomList.size()); //Ask this

                                        TenantDetails tenantDetails = tenantRoomList.get(i);
                                        String billId = databaseReference.push().getKey();
                                        BillDetails billDetails = new BillDetails(billId, "Electricity", bill, false, "", dateString);
                                    /*DatabaseReference databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomNumber + "/Tenant/CurrentTenants/" + tenantDetails.id);
                                    databaseReference1.child("Accounts").child("Bills").child(dateString).child(billId).setValue(billDetails);*/

                                        DatabaseReference databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/" + tenantDetails.id);
                                        databaseReference2.child("Accounts").child("Bills").child(dateString).child(billId).setValue(billDetails);

                                        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Tenants/" + tenantDetails.id);
                                        databaseReference3.child("Accounts").child("Bills").child(dateString).child(billId).setValue(billDetails);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(context, "Bill Added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Amount Required!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView roomNumberTextView, roomTypeTextView;
        public EditText unitsEditText;
        public FloatingActionButton saveFab;
        public TextView costPerUnit;

        public MyHolder(View itemView){
            super(itemView);

            roomNumberTextView = itemView.findViewById(R.id.thirdTextView);
            roomTypeTextView = itemView.findViewById(R.id.fourthTextView);
            costPerUnit = itemView.findViewById(R.id.costPerUnitTextView);
            unitsEditText = itemView.findViewById(R.id.numberOfUnitsEditText);

            saveFab = itemView.findViewById(R.id.saveFab);
        }
    }

}
