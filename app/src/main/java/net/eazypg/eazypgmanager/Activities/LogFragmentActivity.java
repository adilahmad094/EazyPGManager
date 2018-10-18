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


public class LogFragmentActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_fragment2);

        Fabric.with(this, new Crashlytics());


        tabLayout = findViewById(R.id.tabLayoutID);
        pager = findViewById(R.id.viewPagerID);

        backButton = findViewById(R.id.imageView3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogFragmentActivity.this,HomePageActivity.class));
                finish();
            }
        });

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HostAFriendLogFragment(), "Host A Friend Log");
        adapter.addFragment(new LateCheckInLogFragment(), "Late Check in Log");
        adapter.addFragment(new NotificationLogFragment(),"Notifications");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LogFragmentActivity.this,HomePageActivity.class));
        finish();
    }
}
