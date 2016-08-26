package provab.herdman.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by ptblr-1021 on 28/10/15.
 */


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        sContext = getApplicationContext();
    }



    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }
}