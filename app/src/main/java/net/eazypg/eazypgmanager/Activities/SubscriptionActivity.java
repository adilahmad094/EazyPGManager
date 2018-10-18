package net.eazypg.eazypgmanager.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class SubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        Fabric.with(this, new Crashlytics());

    }
}
