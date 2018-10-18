package net.eazypg.eazypgmanager.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;


public class FeedbackActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;

    List<String> ids;

    CircleImageView foodButton, comfortButton, managementButton, hygieneButton, roomButton, othersButton;
    ConstraintLayout foodConstraintLayout, comfortConstraintLayout, managementConstraintLayout, hygieneConstraintLayout, roomConstraintLayout, othersConstraintLayout;
    RatingBar foodRating, comfortRating, managementRating, hygieneRating, roomRating, othersRating;

    EditText input1;
    EditText input2;
    EditText input3;
    EditText input4;
    EditText input5;
    EditText input6;

    ImageView backButton;

    float foodRatingNumber, comfortRatingNumber, managementRatingNumber, hygieneRatingNumber, roomRatingNumber, othersRatingNumber;
    float finalRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Fabric.with(this, new Crashlytics());


        ids = new ArrayList<>();

        input1 = new EditText(this);
        input2 = new EditText(this);
        input3 = new EditText(this);
        input4 = new EditText(this);
        input5 = new EditText(this);
        input6 = new EditText(this);

        foodRating = findViewById(R.id.foodRating);
        comfortRating = findViewById(R.id.comfortRating);
        managementRating = findViewById(R.id.managementRating);
        hygieneRating = findViewById(R.id.hygieneRating);
        roomRating = findViewById(R.id.roomRating);
        othersRating = findViewById(R.id.othersRating);

        backButton = findViewById(R.id.backButton);

        foodButton = findViewById(R.id.foodButton);
        comfortButton = findViewById(R.id.comfortButton);
        managementButton = findViewById(R.id.managementButton);
        hygieneButton = findViewById(R.id.hygieneButton);
        roomButton = findViewById(R.id.roomButton);
        othersButton = findViewById(R.id.othersButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Tenants/CurrentTenants/");
        databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Feedback/");

        final ProgressDialog progressDialog = ProgressDialog.show(FeedbackActivity.this, "Loading", "Please wait..", true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ids.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                    ids.add(tenantDetails.id);

                }

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeedbackRating feedbackRating = snapshot.child("Food").getValue(FeedbackRating.class);

                    if (feedbackRating != null) {

                        sum += Float.parseFloat(feedbackRating.rating);
                        count++;
                    }



                }

                foodRatingNumber = sum/count;

                foodRating.setRating(foodRatingNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeedbackRating feedbackRating = snapshot.child("Comfort").getValue(FeedbackRating.class);
                    if (feedbackRating != null) {

                        sum += Float.parseFloat(feedbackRating.rating);
                        count++;
                    }


                }

                comfortRatingNumber = sum/count;

                comfortRating.setRating(comfortRatingNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeedbackRating feedbackRating = snapshot.child("Management").getValue(FeedbackRating.class);
                    if (feedbackRating != null) {

                        sum += Float.parseFloat(feedbackRating.rating);
                        count++;
                    }


                }

                managementRatingNumber = sum/count;

                managementRating.setRating(managementRatingNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeedbackRating feedbackRating = snapshot.child("Hygiene").getValue(FeedbackRating.class);
                    if (feedbackRating != null) {

                        sum += Float.parseFloat(feedbackRating.rating);
                        count++;
                    }


                }

                hygieneRatingNumber = sum/count;

                hygieneRating.setRating(hygieneRatingNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeedbackRating feedbackRating = snapshot.child("Room").getValue(FeedbackRating.class);
                    if (feedbackRating != null) {

                        sum += Float.parseFloat(feedbackRating.rating);
                        count++;
                    }

                }

                roomRatingNumber = sum/count;

                roomRating.setRating(roomRatingNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FeedbackRating feedbackRating = snapshot.child("Others").getValue(FeedbackRating.class);
                    if (feedbackRating != null) {

                        sum += Float.parseFloat(feedbackRating.rating);
                        count++;
                    }

                }

                othersRatingNumber = sum/count;

                othersRating.setRating(othersRatingNumber);

                finalRating = (foodRatingNumber + comfortRatingNumber + managementRatingNumber + hygieneRatingNumber + roomRatingNumber + othersRatingNumber)/6;

                databaseReference2.child("Final Rating").setValue(Float.toString(finalRating));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1 = firebaseDatabase.getReference("Tenants/");

                if(input1.getParent()!=null) {
                    ((ViewGroup) input1.getParent()).removeView(input1);
                }

                input1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(60) });
                input1.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Food Feedback")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter Question: ");

                input1.setText(R.string.food_feedback);

                builder.setView(input1);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (String id : ids) {

                            String newRef = databaseReference.push().getKey();

                            Notifs notifs = new Notifs("Others", input1.getText().toString());
                            databaseReference1.child(id).child("Notification").child(newRef).setValue(notifs);

                        }

                        Toast.makeText(FeedbackActivity.this, "Feedback Question Sent", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        comfortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference1 = firebaseDatabase.getReference("Tenants/");

                if(input2.getParent()!=null) {
                    ((ViewGroup) input2.getParent()).removeView(input2);
                }

                input2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(60) });
                input2.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Comfort Feedback")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter Question: ");

                input2.setText(R.string.comfort_feedback);

                builder.setView(input2);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (String id : ids) {

                            String newRef = databaseReference.push().getKey();
                            Notifs notifs= new Notifs("Comfort", input2.getText().toString());
                            databaseReference1.child(id).child("Notification").child(newRef).setValue(notifs);
                        }

                        Toast.makeText(FeedbackActivity.this, "Feedback Question Sent", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference1 = firebaseDatabase.getReference("Tenants/");

                if(input3.getParent()!=null) {
                    ((ViewGroup) input3.getParent()).removeView(input3);
                }

                input3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(60) });
                input3.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Management Feedback")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter Question: ");

                input3.setText(R.string.management_feedback);

                builder.setView(input3);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (String id : ids) {

                            String newRef = databaseReference.push().getKey();
                            Notifs notifs = new Notifs("Management", input3.getText().toString());
                            databaseReference1.child(id).child("Notification").child(newRef).setValue(notifs);
                        }

                        Toast.makeText(FeedbackActivity.this, "Feedback Question Sent", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        hygieneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1 = firebaseDatabase.getReference("Tenants/");

                if(input4.getParent()!=null) {
                    ((ViewGroup) input4.getParent()).removeView(input4);
                }

                input4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(60) });
                input4.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Hygiene Feedback")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter Question: ");

                input4.setText(R.string.hygiene_feedback);
                builder.setView(input4);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (String id : ids) {

                            String newRef = databaseReference.push().getKey();
                            Notifs notifs = new Notifs("Hygiene", input4.getText().toString());
                            databaseReference1.child(id).child("Notification").child(newRef).setValue(notifs);
                        }
                        Toast.makeText(FeedbackActivity.this, "Feedback Question Sent", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1 = firebaseDatabase.getReference("Tenants/");

                if(input5.getParent()!=null) {
                    ((ViewGroup) input5.getParent()).removeView(input5);
                }

                input5.setFilters(new InputFilter[] { new InputFilter.LengthFilter(60) });
                input5.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Room Feedback")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter Question: ");

                input5.setText(R.string.room_feedback);

                builder.setView(input5);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (String id : ids) {

                            String newRef = databaseReference.push().getKey();
                            Notifs notifs = new Notifs("Room", input5.getText().toString());
                            databaseReference1.child(id).child("Notification").child(newRef).setValue(notifs);
                        }
                        Toast.makeText(FeedbackActivity.this, "Feedback Question Sent", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference1 = firebaseDatabase.getReference("Tenants/");

                if(input6.getParent()!=null) {
                    ((ViewGroup) input6.getParent()).removeView(input6);
                }

                input6.setFilters(new InputFilter[] { new InputFilter.LengthFilter(60) });
                input6.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Others Feedback")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter Question: ");

                input6.setText(R.string.others_feedback);
                builder.setView(input6);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (String id : ids) {

                            String newRef = databaseReference.push().getKey();
                            Notifs notifs = new Notifs("Others", input6.getText().toString());
                            databaseReference1.child(id).child("Notification").child(newRef).setValue(notifs);
                        }
                        Toast.makeText(FeedbackActivity.this, "Feedback Question Sent", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedbackActivity.this,HomePageActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FeedbackActivity.this,HomePageActivity.class));
        finish();
    }
}
