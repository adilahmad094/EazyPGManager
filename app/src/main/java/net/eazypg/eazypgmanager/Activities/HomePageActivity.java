package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.Adapter.MyAdapter;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class HomePageActivity extends AppCompatActivity {

    CardView profileCard;
    ImageView notifications;
    CardView accounts;
    CardView rentBill;
    CardView addBill;
    CardView tenant;
    CardView room;
    CardView feedback;
    CardView staff;
    CardView food;
    CardView complaints;
    ImageView appliances;

    float rating;

    private  int someVarA;
    private String someVarB;

    MyAdapter adapter;

    TextView pgNameTextView;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2;

    RatingBar overallRating;

    List<Float> ratings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Fabric.with(this, new Crashlytics());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(19,65,62));
        }

        /*new Instabug.Builder(this, "537d24596329c641d3508bb1cf515eb4")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build();*/

        ratings = new ArrayList<>();

        checkConnection();

        overallRating = findViewById(R.id.overallRating);

        profileCard = findViewById(R.id.profileCard);
        notifications = findViewById(R.id.notificationImage);
        accounts = findViewById(R.id.accountsCardView);
        rentBill = findViewById(R.id.rentBillCollectionCard);
        addBill = findViewById(R.id.addBillButton);
        tenant = findViewById(R.id.tenantImageView);
        room = findViewById(R.id.roomCardView);
        feedback = findViewById(R.id.feedbackCardView);
        staff = findViewById(R.id.staffImageView);
        food = findViewById(R.id.foodButton);
        appliances = findViewById(R.id.appliancesCardView);
        complaints = findViewById(R.id.complaintsCardView);

        pgNameTextView = findViewById(R.id.pgNameTextView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PG Details/");
        databaseReference2 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Feedback/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("pgName").getValue(String.class) != null)
                    pgNameTextView.setText(dataSnapshot.child("pgName").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.child("Ratings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ratings.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    ratings.add(snapshot.getValue(Float.class));
                }

                float sum = 0;

                for (float f : ratings) {

                    sum += f;

                }

                float finalRating = sum/ratings.size();

                overallRating.setRating(finalRating);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, TenantActivity.class));
                finish();
            }
        });

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this , RoomsActivity.class));
                finish();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, FeedbackActivity.class));
                finish();
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, FoodActivity.class));
                finish();
            }
        });

        appliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, ApplianceActivity.class));
                finish();
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, StaffActivity.class));
                finish();
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, MyPGActivity.class));
                finish();
            }
        });

        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, AccountsFragmentActivity.class));
                finish();
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this , LogFragmentActivity.class));
                finish();
            }
        });

       rentBill.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(HomePageActivity.this , RentBillCollectionActivity.class));
               finish();
           }
       });

        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, AddBillActivity.class));
                finish();
            }
        });

        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, ComplaintActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("someVarA", someVarA);
        outState.putString("someVarB", someVarB);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        someVarA = savedInstanceState.getInt("someVarA");
        someVarB = savedInstanceState.getString("someVarB");
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {

            final Snackbar snackbar = Snackbar.make(findViewById(R.id.homeLayout), "Not connected to network", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Okay", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();

        }

    }

}