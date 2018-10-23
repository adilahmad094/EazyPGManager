package net.eazypg.eazypgmanager.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    DatabaseReference databaseReference;

    String id;

    boolean loaded;

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

        downloadButton = view.findViewById(R.id.downloadButton);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Documents");

        final long ONE_MEGABYTE = 1024 * 1024;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Tenants/" + id + "/My Docs/");

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Loading", "Please wait while documents are downloading..", true);

        if (id != null) {

            progressDialog.dismiss();

                storageReference.child(id).child("Aadhar Front").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        aadharFrontImageView.setImageBitmap(bitmap);

                        progressDialog.dismiss();
                        loaded = true;
                    }
                });


                storageReference.child(id).child("Aadhar Back").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        aadharBackImageView.setImageBitmap(bitmap);

                        progressDialog.dismiss();
                        loaded = true;
                    }
                });


                storageReference.child(id).child("CollegeID Back").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        collegeIdBackImageView.setImageBitmap(bitmap);

                        progressDialog.dismiss();
                        loaded = true;
                    }
                });

                storageReference.child(id).child("CollegeID Front").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        collegeIdFrontImageView.setImageBitmap(bitmap);

                        progressDialog.dismiss();
                        loaded = true;

                    }
                });


        }



        aadharFrontCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loaded) {

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

            }
        });

        aadharBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loaded) {

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

            }
        });

        collegeIDFrontCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loaded) {

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

            }
        });

        collegeIDBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loaded) {

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
            }
        });


        return view;
    }
}
