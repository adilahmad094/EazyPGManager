package net.eazypg.eazypgmanager.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.eazypg.eazypgmanager.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 2000;
    boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.rgb(0,0,0));
            }
        }

        openOther();


    }

    public void openOther() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                isConnected = ConnectivityReceiver.isConnected();

                if (!isConnected) {

                    final Snackbar snackbar = Snackbar.make(findViewById(R.id.homeLayout), "Not connected to network", Snackbar.LENGTH_INDEFINITE);

                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openOther();
                            snackbar.dismiss();
                            isConnected = ConnectivityReceiver.isConnected();
                        }
                    });
                    snackbar.show();

                }
                else {

                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();

                }

            }

        }, SPLASH_TIME_OUT);

    }

}
