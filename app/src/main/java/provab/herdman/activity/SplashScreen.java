package provab.herdman.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import provab.herdman.R;
import provab.herdman.utility.SessionManager;



public class SplashScreen extends AppCompatActivity {

    private static int TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManager manager = new SessionManager(SplashScreen.this);
                manager.checkLogin();

                finish();

            }
        }, TIME_OUT);

    }
}
