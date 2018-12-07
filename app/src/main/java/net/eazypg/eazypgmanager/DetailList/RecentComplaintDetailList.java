package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.eazypg.eazypgmanager.DetailsClasses.ComplaintDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecentComplaintDetailList extends RecyclerView.Adapter<RecentComplaintDetailList.MyViewHolder> {

    List<ComplaintDetails> complaintDetailsList;
    DateFormat dateFormat;

    String id, tenantId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    Context context;

    FirebaseUser firebaseUser;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView, complaintIdTextView, categoryTextView, subCategoryTextView, subSubCategoryTextView, tenantNameTextView, roomNumberTenantTextView, descriptionTextView;

        public MyViewHolder(View view) {
            super(view);

            dateTextView = view.findViewById(R.id.dateTextView);
            complaintIdTextView = view.findViewById(R.id.complaintIdTextView);
            categoryTextView = view.findViewById(R.id.categoryTextView);
            subCategoryTextView = view.findViewById(R.id.subCategoryTextView);
            subSubCategoryTextView = view.findViewById(R.id.subSubCategoryTextView);
            tenantNameTextView = view.findViewById(R.id.tenantNameTextView);
            roomNumberTenantTextView = view.findViewById(R.id.roomNumberTenantTextView);
            descriptionTextView = view.findViewById(R.id.descriptionTextView);

        }
    }

    public RecentComplaintDetailList(List<ComplaintDetails> complaintDetailsList, Context context) {
        this.complaintDetailsList = complaintDetailsList;

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        this.context = context;
    }

    @Override
    public RecentComplaintDetailList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_complaint_row, parent, false);

        return new RecentComplaintDetailList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecentComplaintDetailList.MyViewHolder holder, int position) {

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        final ComplaintDetails complaintDetails = complaintDetailsList.get(position);

        holder.dateTextView.setText(dateFormat.format(complaintDetails.date));
        holder.complaintIdTextView.setText(complaintDetails.complaintId);
        holder.categoryTextView.setText(complaintDetails.firstLevel);
        holder.subCategoryTextView.setText(complaintDetails.secondLevel);
        holder.subSubCategoryTextView.setText(complaintDetails.thirdLevel);
        holder.tenantNameTextView.setText(complaintDetails.name);
        holder.roomNumberTenantTextView.setText(complaintDetails.roomNo);
        holder.descriptionTextView.setText(complaintDetails.description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = complaintDetails.uploadId;
                tenantId = complaintDetails.tenantId;

                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_complaints, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.acknowledged :

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Acknowledged (By Management)");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Acknowledged (By Management)").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.verified:

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Verified (By Staff)");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Verified (By Staff)").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.tenantTime:

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Tenant Availability Time Requested");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Tenant Availability Time Requested").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.moreDetails:

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("More Details/Time Required");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("More Details/Time Required").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.servicePerson:

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Service Person Assigned");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Service Person Assigned").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.complaintResolved:

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Resolved");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Resolved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                        }

                        return true;
                    }
                });

                popup.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return complaintDetailsList.size();
    }
}
