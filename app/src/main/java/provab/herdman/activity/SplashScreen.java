package provab.herdman.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import provab.herdman.R;
import provab.herdman.utility.SessionManager;



public class SplashScreen extends AppCompatActivity {

    private static int TIME_OUT = 3000;
    private static final int PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(checkPermissions()){

                gotToNext();


        }
        else{
            requestPermissions();
        }




    }


    private void gotToNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManager manager = new SessionManager(SplashScreen.this);
                manager.checkLogin();

                finish();

            }
        }, TIME_OUT);


    }



    private boolean checkPermissions() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA);



        if ( result1==PackageManager.PERMISSION_GRANTED && result2==PackageManager.PERMISSION_GRANTED && result3==PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }



    private void requestPermissions() {
        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PERMISSIONS);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS: {

                if (grantResults.length > 0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                        gotToNext();
                    }
                    else{
                        finish();
                    }

                }
            }
        }


    }

    private void sendSms(String phoneNumber, String message){

    }
















}
