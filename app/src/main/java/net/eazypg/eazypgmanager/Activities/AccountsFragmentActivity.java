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


public class AccountsFragmentActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_fragment);

        Fabric.with(this, new Crashlytics());

        tabLayout = findViewById(R.id.tabLayoutID);
        pager = findViewById(R.id.viewPagerID);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new PassbookFragment(), "Passbook");
        adapter.addFragment(new ExpenseFragment(), "Expenses");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        backButton = findViewById(R.id.imageView3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountsFragmentActivity.this, HomePageActivity.class));
                finish();
            }
        });




    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AccountsFragmentActivity.this,HomePageActivity.class));
        finish();
    }
}
