package net.eazypg.eazypgmanager.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import net.eazypg.eazypgmanager.R;

import java.util.Random;

import io.fabric.sdk.android.Fabric;


public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    TextView signupToLogin;

    private EditText etUserEmail,etUserLocality,etUserContact,etUserName;
    private ImageView btnSignUp;

    LocationManager locationManager;
    LocationListener listener;

    private String userEmail, userLocality, userContact, userName;

    @Override
    public void onBackPressed() {
       startActivity(new Intent(SignupActivity.this,LoginActivity.class));
       finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Fabric.with(this, new Crashlytics());


        btnSignUp = findViewById(R.id.btnSignUp);
        etUserEmail = findViewById(R.id.emailEditText);       //Test
        etUserLocality = findViewById(R.id.localityEditText); //Test
        etUserContact = findViewById(R.id.contactEditText);       //Test
        etUserName = findViewById(R.id.usernameEditText);      //Test
        signupToLogin = findViewById(R.id.signupToLoginTextView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PG/");

        mFirebaseAuth=FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userEmail = etUserEmail.getText().toString();
                userLocality = etUserLocality.getText().toString();
                userContact = etUserContact.getText().toString();
                userName = etUserName.getText().toString();

                if (!userEmail.isEmpty() && !userLocality.isEmpty() && !userContact.isEmpty() && !userName.isEmpty()) {
                    final ProgressDialog progressDialog = ProgressDialog.show(SignupActivity.this, "","Creating User..", true);

                    String password = passwordGeneration();
                    mFirebaseAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                int count = 0;
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Complaints").child("ComplaintCount").setValue(String.format("%04d",count));

                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Breakfast").child("Yes").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Breakfast").child("No").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Breakfast").child("Maybe").setValue("0");

                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Lunch").child("Yes").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Lunch").child("No").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Lunch").child("Maybe").setValue("0");

                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Dinner").child("Yes").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Dinner").child("No").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Dinner").child("Maybe").setValue("0");

                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Upcoming Meal").child("Yes").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Upcoming Meal").child("No").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Meals Saved").child("Upcoming Meal").child("Maybe").setValue("0");

                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Food").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Comfort").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Management").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Hygiene").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Room").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Others").setValue("0");
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("Feedback").child("Final Rating").setValue("0");

                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("PG Details").child("email").setValue(etUserEmail.getText().toString());
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("PG Details").child("pgContact").setValue(etUserContact.getText().toString());
                                databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child("PG Details").child("landmark").setValue(etUserLocality.getText().toString());

                                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignupActivity.this, new OnSuccessListener<InstanceIdResult>() {
                                    @Override
                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                        String refreshedToken = instanceIdResult.getToken();

                                        databaseReference = firebaseDatabase.getReference("PG/" + mFirebaseAuth.getCurrentUser().getUid());
                                        databaseReference.child("Token").child("tokenId").setValue(refreshedToken);

                                    }
                                });
                                progressDialog.dismiss();

                                final ProgressDialog progressDialog = ProgressDialog.show(SignupActivity.this, "","Loading..", true);

                                mFirebaseAuth.sendPasswordResetEmail(userEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){

                                                    etUserEmail.setText("");
                                                    etUserContact.setText("");
                                                    etUserName.setText("");
                                                    etUserLocality.setText("");
                                                    etUserName.requestFocus(1);

                                                    mFirebaseAuth.signOut();

                                                    progressDialog.dismiss();



                                                    final AlertDialog dialog = new AlertDialog.Builder(SignupActivity.this)
                                                            .setTitle("Welcome " + etUserName.getText().toString())
                                                            .setMessage("Check your Email to reset password.")
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                                    finish();
                                                                }
                                                            }).setIcon(R.drawable.ic_check_green_24dp)
                                                            .show();
                                                    dialog.setCanceledOnTouchOutside(false);


                                                }
                                                else if (!task.isSuccessful()){
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SignupActivity.this, "Email Not sent", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(SignupActivity.this, "Signup failed.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
                else {
                    if (userName.isEmpty()){
                        etUserName.setError("Field cannot be empty!");
                    }

                    if(userEmail.isEmpty()){
                        etUserEmail.setError("Field cannot be empty!");
                    }
                    if (userLocality.isEmpty()){
                        etUserLocality.setError("Field cannot be empty!");
                    }
                    if (userContact.isEmpty()){
                        etUserContact.setError("Field cannot be empty!");
                    }
                }

            }
        });

        signupToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    public static String passwordGeneration(){
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String values = Capital_chars + Small_chars +
                numbers + symbols;

        // Using random method
        Random rndm_method = new Random();

        char[] password = new char[8];

        for (int i = 0; i < 8; i++)
        {
            
            password[i] =
                    values.charAt(rndm_method.nextInt(values.length()));

        }
        return password.toString();
    }

}
