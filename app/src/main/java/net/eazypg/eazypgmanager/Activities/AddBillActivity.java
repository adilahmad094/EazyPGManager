package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;

import net.eazypg.eazypgmanager.R;

import io.fabric.sdk.android.Fabric;


public class AddBillActivity extends AppCompatActivity {

    public static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    CardView electricityBillCardView, wifiBillCardView, gasBillCardView, otherBillCardView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        Fabric.with(this, new Crashlytics());

        electricityBillCardView = findViewById(R.id.electricityBillCardView);
        wifiBillCardView = findViewById(R.id.wifiBillCardView);
        gasBillCardView = findViewById(R.id.gasBillCardView);
        otherBillCardView = findViewById(R.id.otherBillCardView);
        backButton = findViewById(R.id.backButton);

        electricityBillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBillActivity.this, AddBillRoomActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Electricity");
                startActivity(intent);
            }
        });

        wifiBillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBillActivity.this, AddBillRoomActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Wifi");
                startActivity(intent);
            }
        });

        gasBillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBillActivity.this, AddBillRoomActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Gas");
                startActivity(intent);
            }
        });

        otherBillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBillActivity.this, AddBillRoomActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Other");
                startActivity(intent);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AddBillActivity.this , HomePageActivity.class));
                finish();

            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddBillActivity.this , HomePageActivity.class));
        finish();
    }

}
