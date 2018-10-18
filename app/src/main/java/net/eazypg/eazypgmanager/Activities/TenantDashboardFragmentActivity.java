package net.eazypg.eazypgmanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.TenantDetailList;
import net.eazypg.eazypgmanager.DetailList.TenantProfileFragment;
import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;

public class TenantDashboardFragmentActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;

    ImageView backButton;

    Button deleteButton;

    EditText input;

    String id, room, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_dashboard_fragment);

        Fabric.with(this, new Crashlytics());


        tabLayout = findViewById(R.id.tabLayoutID);
        pager = findViewById(R.id.viewPagerID);

        Intent intent = getIntent();
        id = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE3);
        room = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE4);
        name = intent.getStringExtra(TenantDetailList.EXTRA_MESSAGE5);

        deleteButton = findViewById(R.id.deleteButton);
        input = new EditText(TenantDashboardFragmentActivity.this);

        backButton = findViewById(R.id.imageView3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TenantDashboardFragmentActivity.this, TenantActivity.class));
                finish();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getParent()!=null) {
                    ((ViewGroup) input.getParent()).removeView(input);
                }

                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(40) });
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(TenantDashboardFragmentActivity.this);
                builder.setTitle("Evict Tenant")
                        .setIcon(R.drawable.ic_edit_black_24dp)
                        .setMessage("Enter the name of Tenant: ");
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (input.getText().toString().equals(name)){

                            final DatabaseReference fromReference = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Tenants/CurrentTenants/");
                            final DatabaseReference toReference = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Tenants/PreviousTenants/");
                            final DatabaseReference toReferenceRoom = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Rooms/" + room + "/Tenant/PreviousTenants");

                            final DatabaseReference roomReference = FirebaseDatabase.getInstance().getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Rooms/" + room + "/Tenant/CurrentTenants");
                            roomReference.child(id).setValue(null);

                            fromReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    toReference.child(id).setValue(dataSnapshot.child(id).getValue());
                                    toReferenceRoom.child(id).setValue(dataSnapshot.child(id).getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(TenantDashboardFragmentActivity.this, "Tenant Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    fromReference.child(id).setValue(null);
                                }
                            });
                            thread.start();

                        }
                        else {
                            Toast.makeText(TenantDashboardFragmentActivity.this, "Invalid Name", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Deny", null);
                builder.create().show();
            }
        });

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new TenantProfileFragment(), "Profile");
        adapter.addFragment(new TenantDocumentsFragment(), "Documents");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TenantDashboardFragmentActivity.this, TenantActivity.class));
        finish();
    }
}
