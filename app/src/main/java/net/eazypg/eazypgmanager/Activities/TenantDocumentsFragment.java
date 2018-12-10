package net.eazypg.eazypgmanager.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class TenantDocumentsFragment extends Fragment {

    View view;

    CardView aadharFrontCardView, aadharBackCardView, collegeIDFrontCardView, collegeIDBackCardView;

    ImageView aadharFrontImageView, aadharBackImageView, collegeIdFrontImageView, collegeIdBackImageView;

    Button downloadButton;

    StorageReference storageReference;
    FirebaseStorage storage;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReference1;

    String id;
    Context context;

    TextView aadharFrontStatus, aadharBackStatus, collegeIDFrontStatus, collegeIDBackStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Fabric.with(getContext(), new Crashlytics());

        view = inflater.inflate(R.layout.activity_tenant_documents, container, false);

        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE3);

        aadharFrontCardView = view.findViewById(R.id.aadharFrontCardView);
        aadharBackCardView = view.findViewById(R.id.aadharbackCardView);
        collegeIDFrontCardView = view.findViewById(R.id.collegeIDFrontCardView);
        collegeIDBackCardView = view.findViewById(R.id.collegeIDBackCardView);

        aadharFrontImageView = view.findViewById(R.id.aadharFrontImageView);
        aadharBackImageView = view.findViewById(R.id.aadharBackImageView);
        collegeIdFrontImageView = view.findViewById(R.id.collegeIdFrontImageView);
        collegeIdBackImageView = view.findViewById(R.id.collegeIdBackImageView);

        aadharFrontStatus = view.findViewById(R.id.aadharFrontStatus);
        aadharBackStatus = view.findViewById(R.id.aadharBackStatus);
        collegeIDFrontStatus = view.findViewById(R.id.collegeIDFrontStatus);
        collegeIDBackStatus = view.findViewById(R.id.collegeIDBackStatus);

        downloadButton = view.findViewById(R.id.downloadButton);
        context = getContext();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Documents");

        final long ONE_MEGABYTE = 1024 * 1024;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Tenants/" + id + "/Personal Detail/Documents");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                aadharFrontStatus.setText(dataSnapshot.child("Aadhar Front").getValue(String.class));
                aadharBackStatus.setText(dataSnapshot.child("Aadhar Back").getValue(String.class));
                collegeIDFrontStatus.setText(dataSnapshot.child("CollegeID Front").getValue(String.class));
                collegeIDBackStatus.setText(dataSnapshot.child("CollegeID Back").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        aadharFrontStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aadharFrontStatus.getText().toString().equalsIgnoreCase("Pending")) {

                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_status, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {

                                case R.id.acceptImage:

                                    databaseReference.child("Aadhar Front").setValue("Verified");
                                    break;

                                case R.id.rejectImage:

                                    databaseReference.child("Aadhar Front").setValue("Rejected");
                                    break;
                            }

                            return true;
                        }
                    });

                    popup.show();

                }

                else if (aadharFrontStatus.getText().toString().equalsIgnoreCase("Verified")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("To change the status from verified to reject, write to research@eazypg.net. Do you want to send the mail now?");
                    builder.setNegativeButton("Not yet", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"research@eazypg.net"});
                            i.putExtra(Intent.EXTRA_SUBJECT, "Change Document Status");
                            String text = "Hello Admin, I want to change my tenant document status to Rejected. \nPG Id: " + firebaseUser.getUid() + "\nTenant Id: " + id + "\nDocument Name: Aadhar Front";
                            i.putExtra(Intent.EXTRA_TEXT, text);
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();

                }

                else if (aadharFrontStatus.getText().toString().equalsIgnoreCase("Rejected")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("Wait till tenant upload the new document.");
                    builder.setNeutralButton("Ok", null);
                    builder.show();

                }
            }
        });

        aadharBackStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aadharBackStatus.getText().toString().equalsIgnoreCase("Pending")) {

                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_status, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {

                                case R.id.acceptImage:

                                    databaseReference.child("Aadhar Back").setValue("Verified");
                                    break;

                                case R.id.rejectImage:

                                    databaseReference.child("Aadhar Back").setValue("Rejected");
                                    break;
                            }

                            return true;
                        }
                    });

                    popup.show();

                }

                else if (aadharBackStatus.getText().toString().equalsIgnoreCase("Verified")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("To change the status from verified to reject, write to research@eazypg.net. Do you want to send the mail now?");
                    builder.setNegativeButton("Not yet", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"research@eazypg.net"});
                            i.putExtra(Intent.EXTRA_SUBJECT, "Change Document Status");
                            String text = "Hello Admin, I want to change my tenant document status to Rejected. \nPG Id: " + firebaseUser.getUid() + "\nTenant Id: " + id + "\nDocument Name: Aadhar Front";
                            i.putExtra(Intent.EXTRA_TEXT, text);

                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();

                }

                else if (aadharBackStatus.getText().toString().equalsIgnoreCase("Rejected")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("Wait till tenant upload the new document.");
                    builder.setNeutralButton("Ok", null);
                    builder.show();

                }

            }
        });

        collegeIDFrontStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (collegeIDFrontStatus.getText().toString().equalsIgnoreCase("Pending")) {

                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_status, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {

                                case R.id.acceptImage:

                                    databaseReference.child("CollegeID Front").setValue("Verified");
                                    break;

                                case R.id.rejectImage:

                                    databaseReference.child("CollegeID Front").setValue("Rejected");
                                    break;
                            }

                            return true;
                        }
                    });

                    popup.show();

                }

                else if (collegeIDFrontStatus.getText().toString().equalsIgnoreCase("Verified")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("To change the status from verified to reject, write to research@eazypg.net. Do you want to send the mail now?");
                    builder.setNegativeButton("Not yet", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"research@eazypg.net"});
                            i.putExtra(Intent.EXTRA_SUBJECT, "Change Document Status");
                            String text = "Hello Admin, I want to change my tenant document status to Rejected. \nPG Id: " + firebaseUser.getUid() + "\nTenant Id: " + id + "\nDocument Name: CollegeID Front";
                            i.putExtra(Intent.EXTRA_TEXT, text);
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();

                }

                else if (collegeIDFrontStatus.getText().toString().equalsIgnoreCase("Rejected")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("Wait till tenant upload the new document.");
                    builder.setNeutralButton("Ok", null);
                    builder.show();

                }

            }
        });

        collegeIDBackStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (collegeIDBackStatus.getText().toString().equalsIgnoreCase("Pending")) {

                    PopupMenu popup = new PopupMenu(getContext(), v);
                    popup.getMenuInflater()
                            .inflate(R.menu.menu_status, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {

                                case R.id.acceptImage:

                                    databaseReference.child("CollegeID Back").setValue("Verified");
                                    break;

                                case R.id.rejectImage:

                                    databaseReference.child("CollegeID Back").setValue("Rejected");
                                    break;
                            }

                            return true;
                        }
                    });

                    popup.show();

                }

                else if (collegeIDBackStatus.getText().toString().equalsIgnoreCase("Verified")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("To change the status from verified to reject, write to research@eazypg.net. Do you want to send the mail now?");
                    builder.setNegativeButton("Not yet", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"research@eazypg.net"});
                            i.putExtra(Intent.EXTRA_SUBJECT, "Change Document Status");
                            String text = "Hello Admin, I want to change my tenant document status to Rejected. \nPG Id: " + firebaseUser.getUid() + "\nTenant Id: " + id + "\nDocument Name: CollegeID Back";
                            i.putExtra(Intent.EXTRA_TEXT, text);

                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();

                }

                else if (collegeIDBackStatus.getText().toString().equalsIgnoreCase("Rejected")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Change Status");
                    builder.setMessage("Wait till tenant upload the new document.");
                    builder.setNeutralButton("Ok", null);
                    builder.show();

                }

            }
        });


        storageReference.child(id).child("Aadhar Front").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                aadharFrontImageView.setImageBitmap(bitmap);

            }
        });


        storageReference.child(id).child("Aadhar Back").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                aadharBackImageView.setImageBitmap(bitmap);

            }
        });


        storageReference.child(id).child("CollegeID Back").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                collegeIdBackImageView.setImageBitmap(bitmap);
            }
        });

        storageReference.child(id).child("CollegeID Front").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                collegeIdFrontImageView.setImageBitmap(bitmap);

            }
        });

        aadharFrontCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    final Dialog builder = new Dialog(getContext());
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    ImageView imageView = new ImageView(getContext());

                    aadharFrontImageView.buildDrawingCache(true);

                    Bitmap bitmap = Bitmap.createBitmap(aadharFrontImageView.getDrawingCache(true));
                    imageView.setImageBitmap(bitmap);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            1000,
                            1000));
                    builder.setCancelable(true);

                    builder.show();



            }
        });

        aadharBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Dialog builder = new Dialog(getContext());
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    ImageView imageView = new ImageView(getContext());

                    aadharBackImageView.buildDrawingCache(true);

                    Bitmap bitmap = Bitmap.createBitmap(aadharBackImageView.getDrawingCache(true));
                    imageView.setImageBitmap(bitmap);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            1000,
                            1000));
                    builder.setCancelable(true);

                    builder.show();

                }

        });

        collegeIDFrontCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Dialog builder = new Dialog(getActivity());
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    ImageView imageView = new ImageView(getContext());

                    collegeIdFrontImageView.buildDrawingCache(true);

                    Bitmap bitmap = Bitmap.createBitmap(collegeIdFrontImageView.getDrawingCache(true));
                    imageView.setImageBitmap(bitmap);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            1000,
                            1000));
                    builder.setCancelable(true);

                    builder.show();



            }
        });

        collegeIDBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Dialog builder = new Dialog(getActivity());
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    ImageView imageView = new ImageView(getContext());

                    collegeIdBackImageView.buildDrawingCache(true);

                    Bitmap bitmap = Bitmap.createBitmap(collegeIdBackImageView.getDrawingCache(true));
                    imageView.setImageBitmap(bitmap);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            1000,
                            1000));
                    builder.setCancelable(true);

                    builder.show();

            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = firebaseDatabase.getReference("Tenants/" + id + "/Personal Detail/Documents/pdf");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child("pdfurl").exists()) {

                            String url = dataSnapshot.child("pdfurl").getValue(String.class);
                            downloadFile(context, "PoliceVerificationForm", ".pdf", Environment.getExternalStorageDirectory() + "/EazyPG/", url);

                        }
                        else {
                            Toast.makeText(context, "URL not found", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


        return view;
    }

    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }
}
