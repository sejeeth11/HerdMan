package provab.herdman.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;



public class NetworkStateReceiver extends BroadcastReceiver {

     Context cxt;


    public void onReceive(Context context, Intent intent) {

        this.cxt = context;

        ConnectivityManager cm =
                                     (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

        Log.e("internet", isConnected + "");

            if (isConnected) {
                dbSync();
            }
        
   }

    private void dbSync() {

        Intent intent = new Intent(cxt, SyncDbService.class);
       // intent.putExtra(UpdateImageInfoService.KEY_IMAGE_ID, imgId);
        cxt.startService(intent);

//        Intent intent = new Intent(context, UpdateImageInfoService.class);
//        intent.putExtra(UpdateImageInfoService.KEY_IMAGE_ID, imgId);
//        context.startService(intent);
    }

}