package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.Activities.RoomApplianceDetailsActivity;
import net.eazypg.eazypgmanager.Activities.RoomClickActivity;
import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomsDetailList extends ArrayAdapter<String> {

    private Activity context;
    private List<String> roomList;
    private TextView third , fourth, rentTextView, numberOfTenantsTextView;
    private ListView dialogListView;
    private List<String> roomTypeList;
    private Map<String, List<TenantDetails>> roomTenantMap;
    private List<TenantDetails> tenantList = new ArrayList<>();

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";
    public static final String EXTRA_MESSAGE4 = "com.example.myfirstapp.MESSAGE4";

    private Button applianceButton, tenantButton;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    DatabaseReference databaseReference;

    public RoomsDetailList(Activity context, List<String> roomList, List<String> roomTypeList, Map<String, List<TenantDetails>> roomTenantMap) {
        super(context, R.layout.room_row, roomList);

        this.context = context;
        this.roomList = roomList;
        this.roomTypeList = roomTypeList;
        this.roomTenantMap = roomTenantMap;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItemRoom = inflater.inflate(R.layout.room_row, null, true);

        third = listViewItemRoom.findViewById(R.id.thirdTextView);
        fourth = listViewItemRoom.findViewById(R.id.fourthTextView);
        rentTextView = listViewItemRoom.findViewById(R.id.rentTextView);
        numberOfTenantsTextView = listViewItemRoom.findViewById(R.id.numberOfTenantsTextView);

        rentTextView.setVisibility(View.VISIBLE);

        applianceButton = listViewItemRoom.findViewById(R.id.appliancesButton);

        third.setText(roomList.get(position));
        fourth.setText(roomTypeList.get(position));

        applianceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RoomApplianceDetailsActivity.class);
                String message = roomList.get(position);
                intent.putExtra(EXTRA_MESSAGE, message);
                context.startActivity(intent);
            }
        });

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Rooms/" + roomList.get(position) + "/Tenant/CurrentTenants/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tenantList.clear();

                String noOfTenant = "x " + Long.toString(dataSnapshot.getChildrenCount());

                numberOfTenantsTextView.setText(noOfTenant);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    rentTextView.setText(snapshot.child("rentAmount").getValue(String.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listViewItemRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PG Details/");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String rentDueDate = dataSnapshot.child("rentDueDate").getValue(String.class);
                        String billDueDate = dataSnapshot.child("billDueDate").getValue(String.class);

                        Intent intent = new Intent(context, RoomClickActivity.class);
                        String roomNumber = roomList.get(position);
                        String roomType = roomTypeList.get(position);
                        intent.putExtra(EXTRA_MESSAGE, roomNumber);
                        intent.putExtra(EXTRA_MESSAGE2, roomType);
                        intent.putExtra(EXTRA_MESSAGE3, rentDueDate);
                        intent.putExtra(EXTRA_MESSAGE4, billDueDate);

                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return listViewItemRoom;
    }
}
