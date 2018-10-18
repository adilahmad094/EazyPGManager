package net.eazypg.eazypgmanager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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

import net.eazypg.eazypgmanager.Activities.FoodDetails.FridayFoodDetails;
import net.eazypg.eazypgmanager.Activities.FoodDetails.MondayFoodDetails;
import net.eazypg.eazypgmanager.Activities.FoodDetails.SaturdayFoodDetails;
import net.eazypg.eazypgmanager.Activities.FoodDetails.SundayFoodDetails;
import net.eazypg.eazypgmanager.Activities.FoodDetails.ThursdayFoodDetails;
import net.eazypg.eazypgmanager.Activities.FoodDetails.TuesdayFoodDetails;
import net.eazypg.eazypgmanager.Activities.FoodDetails.WednesdayFoodDetails;
import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class FoodActivity extends AppCompatActivity {

    EditText mondayBreakfastEditText, mondayLunchEditText, mondayDinnerEditText;
    EditText tuesdayBreakfastEditText, tuesdayLunchEditText, tuesdayDinnerEditText;
    EditText wednesdayBreakfastEditText, wednesdayLunchEditText, wednesdayDinnerEditText;
    EditText thursdayBreakfastEditText, thursdayLunchEditText, thursdayDinnerEditText;
    EditText fridayBreakfastEditText, fridayLunchEditText, fridayDinnerEditText;
    EditText saturdayBreakfastEditText, saturdayLunchEditText, saturdayDinnerEditText;
    EditText sundayBreakfastEditText, sundayLunchEditText, sundayDinnerEditText;

    TextInputLayout mondayb , mondayl, mondayd, tuesdayb, tuesdayl , tuesdayd , wednesdayb, wednesdayl , wednesdayd , thursdayb , thursdayl , thursdayd, fridayb , fridayl, fridayd , saturdayb , saturdayl , saturdayd, sundayb , sundayl ,sundayd;

    CardView mondayCard;
    Button saveFoodButton , editFoodButton;
    HorizontalScrollView scrollView;

    ImageView backButton;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    TextView confirmedTextView, maybeTextView, leavesTextView;
    TextView savedBreakFastTextView, savedLunchTextView, savedDinnerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Fabric.with(this, new Crashlytics());

        backButton = findViewById(R.id.imageView3);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        scrollView = findViewById(R.id.horizontalScrollView);
        scrollView.smoothScrollBy(0,0);
        mondayCard = findViewById(R.id.mondayCardView);


        mondayCard.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        confirmedTextView = findViewById(R.id.confirmedTextView);
        maybeTextView = findViewById(R.id.maybeTextView);
        leavesTextView = findViewById(R.id.leavesTextView);

        editFoodButton = findViewById(R.id.editFoodButton);

        savedBreakFastTextView = findViewById(R.id.savedBreakFastTextView);
        savedLunchTextView = findViewById(R.id.savedLunchTextView);
        savedDinnerTextView = findViewById(R.id.savedDinnerTextView);

        {
            mondayBreakfastEditText = findViewById(R.id.mondayBreakfastEditText);
            mondayLunchEditText = findViewById(R.id.mondayLunchEditText);
            mondayDinnerEditText = findViewById(R.id.mondayDinnerEditText);

            tuesdayBreakfastEditText = findViewById(R.id.tuesdayBreakfastEditText);
            tuesdayLunchEditText = findViewById(R.id.tuesdayLunchEditText);
            tuesdayDinnerEditText = findViewById(R.id.tuesdayDinnerEditText);

            wednesdayBreakfastEditText = findViewById(R.id.wednesdayBreakfastEditText);
            wednesdayLunchEditText = findViewById(R.id.wednesdayLunchEditText);
            wednesdayDinnerEditText = findViewById(R.id.wednesdayDinnerEditText);

            thursdayBreakfastEditText = findViewById(R.id.thursdayBreakfastEditText);
            thursdayLunchEditText = findViewById(R.id.thursdayLunchEditText);
            thursdayDinnerEditText = findViewById(R.id.thursdayDinnerEditText);

            fridayBreakfastEditText = findViewById(R.id.fridayBreakfastEditText);
            fridayLunchEditText = findViewById(R.id.fridayLunchEditText);
            fridayDinnerEditText = findViewById(R.id.fridayDinnerEditText);

            saturdayBreakfastEditText = findViewById(R.id.saturdayBreakfastEditText);
            saturdayLunchEditText = findViewById(R.id.saturdayLunchEditText);
            saturdayDinnerEditText = findViewById(R.id.saturdayDinnerEditText);

            sundayBreakfastEditText = findViewById(R.id.sundayBreakfastEditText);
            sundayLunchEditText = findViewById(R.id.sundayLunchEditText);
            sundayDinnerEditText = findViewById(R.id.sundayDinnerEditText);

            saveFoodButton = findViewById(R.id.saveFoodButton);

            mondayb   = findViewById(R.id.textInputLayoutMondayBreakfast);
            mondayl    = findViewById(R.id.textInputLayoutMondayLunch);
            mondayd    = findViewById(R.id.textInputLayoutMondayDinner);
            tuesdayb   = findViewById(R.id.textInputLayoutTuesdayBreakfast);
            tuesdayl   = findViewById(R.id.textInputlayoutTuesdayLunch);
            tuesdayd    = findViewById(R.id.textInputLayoutTuesdayDinner);
            wednesdayb   = findViewById(R.id.textInputWednesdayBreakfast);
            wednesdayl   = findViewById(R.id.textInputWednesdayLunch);
            wednesdayd   = findViewById(R.id.textInputwednesdayDinner);
            thursdayb   = findViewById(R.id.textInputThursdayBreakfast);
            thursdayl   = findViewById(R.id.textInputThursdayLunch);
            thursdayd   = findViewById(R.id.textInputThursdayDinner);
            fridayb     =   findViewById(R.id.textInputFridayBreakfast);
            fridayl     = findViewById(R.id.textInputFridayLunch);
            fridayd      = findViewById(R.id.textInputFridayDinner);
            saturdayb   = findViewById(R.id.textInputSaturdayBreakfast);
            saturdayl   = findViewById(R.id.textInputSaturdayLunch);
            saturdayd   = findViewById(R.id.textInputSaturdayDinner);
            sundayb    = findViewById(R.id.textInputSundayBreakfast);
            sundayl    = findViewById(R.id.textInputSundayLunch);
            sundayd    = findViewById(R.id.textInputSundayDinner);
        }



        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Food Details/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MondayFoodDetails mondayFoodDetails = dataSnapshot.child("Monday").getValue(MondayFoodDetails.class);
                TuesdayFoodDetails tuesdayFoodDetails = dataSnapshot.child("Tuesday").getValue(TuesdayFoodDetails.class);
                WednesdayFoodDetails wednesdayFoodDetails = dataSnapshot.child("Wednesday").getValue(WednesdayFoodDetails.class);
                ThursdayFoodDetails thursdayFoodDetails = dataSnapshot.child("Thursday").getValue(ThursdayFoodDetails.class);
                FridayFoodDetails fridayFoodDetails = dataSnapshot.child("Friday").getValue(FridayFoodDetails.class);
                SaturdayFoodDetails saturdayFoodDetails = dataSnapshot.child("Saturday").getValue(SaturdayFoodDetails.class);
                SundayFoodDetails sundayFoodDetails = dataSnapshot.child("Sunday").getValue(SundayFoodDetails.class);

                {
                    if (mondayFoodDetails != null) {

                        mondayBreakfastEditText.setText(mondayFoodDetails.breakfast);
                        mondayLunchEditText.setText(mondayFoodDetails.lunch);
                        mondayDinnerEditText.setText(mondayFoodDetails.dinner);
                    }

                    if (tuesdayFoodDetails != null) {

                        tuesdayBreakfastEditText.setText(tuesdayFoodDetails.breakfast);
                        tuesdayLunchEditText.setText(tuesdayFoodDetails.lunch);
                        tuesdayDinnerEditText.setText(tuesdayFoodDetails.dinner);

                    }

                    if (wednesdayFoodDetails != null) {

                        wednesdayBreakfastEditText.setText(wednesdayFoodDetails.breakfast);
                        wednesdayLunchEditText.setText(wednesdayFoodDetails.lunch);
                        wednesdayDinnerEditText.setText(wednesdayFoodDetails.dinner);

                    }

                    if (thursdayFoodDetails != null) {

                        thursdayBreakfastEditText.setText(thursdayFoodDetails.breakfast);
                        thursdayLunchEditText.setText(thursdayFoodDetails.lunch);
                        thursdayDinnerEditText.setText(thursdayFoodDetails.dinner);

                    }

                    if (fridayFoodDetails != null) {

                        fridayBreakfastEditText.setText(fridayFoodDetails.breakfast);
                        fridayLunchEditText.setText(fridayFoodDetails.lunch);
                        fridayDinnerEditText.setText(fridayFoodDetails.dinner);

                    }

                    if (saturdayFoodDetails != null) {

                        saturdayBreakfastEditText.setText(saturdayFoodDetails.breakfast);
                        saturdayLunchEditText.setText(saturdayFoodDetails.lunch);
                        saturdayDinnerEditText.setText(saturdayFoodDetails.dinner);

                    }

                    if (sundayFoodDetails != null) {

                        sundayBreakfastEditText.setText(sundayFoodDetails.breakfast);
                        sundayLunchEditText.setText(sundayFoodDetails.lunch);
                        sundayDinnerEditText.setText(sundayFoodDetails.dinner);

                    }
                }   // Week food retrieved

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Meals Saved/");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                confirmedTextView.setText(dataSnapshot.child("Upcoming Meal").child("Yes").getValue(String.class));
                maybeTextView.setText(dataSnapshot.child("Upcoming Meal").child("Maybe").getValue(String.class));
                leavesTextView.setText(dataSnapshot.child("Upcoming Meal").child("No").getValue(String.class));

                savedBreakFastTextView.setText(dataSnapshot.child("Breakfast").child("No").getValue(String.class));
                savedDinnerTextView.setText(dataSnapshot.child("Dinner").child("No").getValue(String.class));
                savedLunchTextView.setText(dataSnapshot.child("Lunch").child("No").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mondayb.setHintAnimationEnabled(false);
        mondayl.setHintAnimationEnabled(false);
        mondayd.setHintAnimationEnabled(false);
        tuesdayb.setHintAnimationEnabled(false);
        tuesdayl.setHintAnimationEnabled(false);
        tuesdayd.setHintAnimationEnabled(false);
        wednesdayb.setHintAnimationEnabled(false);
        wednesdayl.setHintAnimationEnabled(false);
        wednesdayd.setHintAnimationEnabled(false);
        thursdayb.setHintAnimationEnabled(false);
        thursdayl.setHintAnimationEnabled(false);
        thursdayd.setHintAnimationEnabled(false);
        fridayb.setHintAnimationEnabled(false);
        fridayl.setHintAnimationEnabled(false);
        fridayd.setHintAnimationEnabled(false);
        saturdayb.setHintAnimationEnabled(false);
        saturdayl.setHintAnimationEnabled(false);
        saturdayd.setHintAnimationEnabled(false);
        sundayb.setHintAnimationEnabled(false);
        sundayl.setHintAnimationEnabled(false);
        sundayd.setHintAnimationEnabled(false);


        mondayb.setEnabled(false);
        mondayl.setEnabled(false);
        mondayd.setEnabled(false);
        tuesdayb.setEnabled(false);
        tuesdayl.setEnabled(false);
        tuesdayd.setEnabled(false);
        wednesdayb.setEnabled(false);
        wednesdayl.setEnabled(false);
        wednesdayd.setEnabled(false);
        thursdayb.setEnabled(false);
        thursdayl.setEnabled(false);
        thursdayd.setEnabled(false);
        fridayb.setEnabled(false);
        fridayl.setEnabled(false);
        fridayd.setEnabled(false);
        saturdayb.setEnabled(false);
        saturdayl.setEnabled(false);
        saturdayd.setEnabled(false);
        sundayb.setEnabled(false);
        sundayl.setEnabled(false);
        sundayd.setEnabled(false);


        editFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mondayb.setHintAnimationEnabled(true);
                mondayl.setHintAnimationEnabled(true);
                mondayd.setHintAnimationEnabled(true);
                tuesdayb.setHintAnimationEnabled(true);
                tuesdayl.setHintAnimationEnabled(true);
                tuesdayd.setHintAnimationEnabled(true);
                wednesdayb.setHintAnimationEnabled(true);
                wednesdayl.setHintAnimationEnabled(true);
                wednesdayd.setHintAnimationEnabled(true);
                thursdayb.setHintAnimationEnabled(true);
                thursdayl.setHintAnimationEnabled(true);
                thursdayd.setHintAnimationEnabled(true);
                fridayb.setHintAnimationEnabled(true);
                fridayl.setHintAnimationEnabled(true);
                fridayd.setHintAnimationEnabled(true);
                saturdayb.setHintAnimationEnabled(true);
                saturdayl.setHintAnimationEnabled(true);
                saturdayd.setHintAnimationEnabled(true);
                sundayb.setHintAnimationEnabled(true);
                sundayl.setHintAnimationEnabled(true);
                sundayd.setHintAnimationEnabled(true);

                mondayb.setEnabled(true);
                mondayl.setEnabled(true);
                mondayd.setEnabled(true);
                tuesdayb.setEnabled(true);
                tuesdayl.setEnabled(true);
                tuesdayd.setEnabled(true);
                wednesdayb.setEnabled(true);
                wednesdayl.setEnabled(true);
                wednesdayd.setEnabled(true);
                thursdayb.setEnabled(true);
                thursdayl.setEnabled(true);
                thursdayd.setEnabled(true);
                fridayb.setEnabled(true);
                fridayl.setEnabled(true);
                fridayd.setEnabled(true);
                saturdayb.setEnabled(true);
                saturdayl.setEnabled(true);
                saturdayd.setEnabled(true);
                sundayb.setEnabled(true);
                sundayl.setEnabled(true);
                sundayd.setEnabled(true);


                editFoodButton.setBackgroundResource(R.drawable.rectangle);
                saveFoodButton.setBackgroundResource(R.drawable.whiterounded_corner);

            }
        });

        saveFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mondayb.setHintAnimationEnabled(false);
                mondayl.setHintAnimationEnabled(false);
                mondayd.setHintAnimationEnabled(false);
                tuesdayb.setHintAnimationEnabled(false);
                tuesdayl.setHintAnimationEnabled(false);
                tuesdayd.setHintAnimationEnabled(false);
                wednesdayb.setHintAnimationEnabled(false);
                wednesdayl.setHintAnimationEnabled(false);
                wednesdayd.setHintAnimationEnabled(false);
                thursdayb.setHintAnimationEnabled(false);
                thursdayl.setHintAnimationEnabled(false);
                thursdayd.setHintAnimationEnabled(false);
                fridayb.setHintAnimationEnabled(false);
                fridayl.setHintAnimationEnabled(false);
                fridayd.setHintAnimationEnabled(false);
                saturdayb.setHintAnimationEnabled(false);
                saturdayl.setHintAnimationEnabled(false);
                saturdayd.setHintAnimationEnabled(false);
                sundayb.setHintAnimationEnabled(false);
                sundayl.setHintAnimationEnabled(false);
                sundayd.setHintAnimationEnabled(false);


                mondayb.setEnabled(false);
                mondayl.setEnabled(false);
                mondayd.setEnabled(false);
                tuesdayb.setEnabled(false);
                tuesdayl.setEnabled(false);
                tuesdayd.setEnabled(false);
                wednesdayb.setEnabled(false);
                wednesdayl.setEnabled(false);
                wednesdayd.setEnabled(false);
                thursdayb.setEnabled(false);
                thursdayl.setEnabled(false);
                thursdayd.setEnabled(false);
                fridayb.setEnabled(false);
                fridayl.setEnabled(false);
                fridayd.setEnabled(false);
                saturdayb.setEnabled(false);
                saturdayl.setEnabled(false);
                saturdayd.setEnabled(false);
                sundayb.setEnabled(false);
                sundayl.setEnabled(false);
                sundayd.setEnabled(false);


                editFoodButton.setBackgroundResource(R.drawable.whiterounded_corner);
                saveFoodButton.setBackgroundResource(R.drawable.rectangle);




                final ProgressDialog progressDialog = ProgressDialog.show(FoodActivity.this, "Saving", "Saving Food Details..", true);

                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Food Details/");

                MondayFoodDetails mondayFoodDetails = new MondayFoodDetails(mondayBreakfastEditText.getText().toString(), mondayLunchEditText.getText().toString(), mondayDinnerEditText.getText().toString());
                databaseReference.child("Monday").setValue(mondayFoodDetails);

                TuesdayFoodDetails tuesdayFoodDetails = new TuesdayFoodDetails(tuesdayBreakfastEditText.getText().toString(), tuesdayLunchEditText.getText().toString(), tuesdayDinnerEditText.getText().toString());
                databaseReference.child("Tuesday").setValue(tuesdayFoodDetails);

                WednesdayFoodDetails wednesdayFoodDetails = new WednesdayFoodDetails(wednesdayBreakfastEditText.getText().toString(), wednesdayLunchEditText.getText().toString(), wednesdayDinnerEditText.getText().toString());
                databaseReference.child("Wednesday").setValue(wednesdayFoodDetails);

                ThursdayFoodDetails thursdayFoodDetails = new ThursdayFoodDetails(thursdayBreakfastEditText.getText().toString(), thursdayLunchEditText.getText().toString(), thursdayDinnerEditText.getText().toString());
                databaseReference.child("Thursday").setValue(thursdayFoodDetails);

                FridayFoodDetails fridayFoodDetails = new FridayFoodDetails(fridayBreakfastEditText.getText().toString(), fridayLunchEditText.getText().toString(), fridayDinnerEditText.getText().toString());
                databaseReference.child("Friday").setValue(fridayFoodDetails);

                SaturdayFoodDetails saturdayFoodDetails = new SaturdayFoodDetails(saturdayBreakfastEditText.getText().toString(), saturdayLunchEditText.getText().toString(), saturdayDinnerEditText.getText().toString());
                databaseReference.child("Saturday").setValue(saturdayFoodDetails);

                SundayFoodDetails sundayFoodDetails = new SundayFoodDetails(sundayBreakfastEditText.getText().toString(), sundayLunchEditText.getText().toString(), sundayDinnerEditText.getText().toString());
                databaseReference.child("Sunday").setValue(sundayFoodDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        progressDialog.dismiss();
                        Toast.makeText(FoodActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodActivity.this,HomePageActivity.class));
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FoodActivity.this , HomePageActivity.class));
        finish();
    }
}
