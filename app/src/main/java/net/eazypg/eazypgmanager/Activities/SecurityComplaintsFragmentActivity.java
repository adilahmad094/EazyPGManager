package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;

import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class SecurityComplaintsFragmentActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;

    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_complaints_fragment);

        Fabric.with(this, new Crashlytics());


        tabLayout = findViewById(R.id.tabLayoutID);
        pager = findViewById(R.id.viewPagerID);

        backButton = findViewById(R.id.imageView3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecurityComplaintsFragmentActivity.this,ComplaintActivity.class));
                finish();

            }
        });

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new SecurityUnresolvedFragment(), "Unresolved");
        adapter.addFragment(new SecurityResolvedFragment(), "Resolved");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SecurityComplaintsFragmentActivity.this,ComplaintActivity.class));
        finish();
    }
}
