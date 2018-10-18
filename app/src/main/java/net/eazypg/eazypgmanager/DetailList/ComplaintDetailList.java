package net.eazypg.eazypgmanager.DetailList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailsClasses.ComplaintDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class ComplaintDetailList extends RecyclerView.Adapter<ComplaintDetailList.MyViewHolder>{

    FirebaseStorage storage;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    List<ComplaintDetails> complaintDetailsList;
    public Context context;

    String id, tenantId;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView complaintImageView;
        public TextView complaintIDTextView;
        public TextView statusTextView;
        public TextView availabilityTextView;
        public TextView descriptionTextView;
        public TextView secondLevelTextView;
        public TextView thirdLevelTextView;
        public TextView firstLevelTextView;
        public TextView nameTextView;
        public TextView roomNoTextView;

        public boolean loaded;

        public MyViewHolder(View view) {
            super(view);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            firebaseDatabase = FirebaseDatabase.getInstance();

            nameTextView = view.findViewById(R.id.nameTextView);
            roomNoTextView = view.findViewById(R.id.roomNoTextView);
            complaintImageView = view.findViewById(R.id.complaintImageView);
            complaintIDTextView = view.findViewById(R.id.complaintIDTextView);
            statusTextView = view.findViewById(R.id.statusTextView);
            availabilityTextView = view.findViewById(R.id.availabilityTextView);
            descriptionTextView = view.findViewById(R.id.descriptionTextView);
            secondLevelTextView = view.findViewById(R.id.secondLevelTextView);
            thirdLevelTextView = view.findViewById(R.id.thirdLevelTextView);
            firstLevelTextView = view.findViewById(R.id.firstLevelTextView);

        }
    }

    public ComplaintDetailList(List<ComplaintDetails> complaintDetailsList, Context context) {
        this.complaintDetailsList = complaintDetailsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaint_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final ComplaintDetails complaintDetails1 = complaintDetailsList.get(position);

        storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Complaints").child(complaintDetails1.imageName);

        final long ONE_MEGABYTE = 1024 * 1024;

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.complaintImageView.setImageBitmap(bitmap);

                holder.loaded = true;

            }
        });

        holder.nameTextView.setText(complaintDetails1.name);
        holder.roomNoTextView.setText(complaintDetails1.roomNo);
        holder.complaintIDTextView.setText(complaintDetails1.complaintId);
        holder.statusTextView.setText(complaintDetails1.status);
        holder.availabilityTextView.setText(complaintDetails1.availabilityTime);
        holder.descriptionTextView.setText(complaintDetails1.description);
        holder.firstLevelTextView.setText(complaintDetails1.firstLevel);
        holder.secondLevelTextView.setText(complaintDetails1.secondLevel);
        holder.thirdLevelTextView.setText(complaintDetails1.thirdLevel);

        holder.complaintImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView imageView = new ImageView(context);

                holder.complaintImageView.buildDrawingCache(true);

                Bitmap bitmap = Bitmap.createBitmap(holder.complaintImageView.getDrawingCache(true));
                imageView.setImageBitmap(bitmap);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        1000,
                        1000));
                builder.setCancelable(true);

                builder.show();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = complaintDetails1.uploadId;
                tenantId = complaintDetails1.tenantId;

                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_complaints, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.acknowledged :

                                holder.statusTextView.setText("Acknowledged (By Management)");

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Acknowledged (By Management)");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Acknowledged (By Management)").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.verified:

                                holder.statusTextView.setText("Verified (By Staff)");

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Verified (By Staff)");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Verified (By Staff)").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.tenantTime:

                                holder.statusTextView.setText("Tenant Availability Time Requested");

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Tenant Availability Time Requested");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Tenant Availability Time Requested").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.moreDetails:

                                holder.statusTextView.setText("More Details/Time Required");

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("More Details/Time Required");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("More Details/Time Required").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.servicePerson:

                                holder.statusTextView.setText("Service Person Assigned");

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Service Person Assigned");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference1.setValue("Service Person Assigned").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;

                            case R.id.complaintResolved:

                                holder.statusTextView.setText("Resolved");

                                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
                                databaseReference.setValue("Resolved");

                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + tenantId + "/My Complaints/" + complaintDetails1.firstLevel + "/" + id + "/status/");
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