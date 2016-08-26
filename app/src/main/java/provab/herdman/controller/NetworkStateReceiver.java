package provab.herdman.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;



public class NetworkStateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                                     (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            Log.e("internet", isConnected + "");

            if (isConnected) {
           /*     Intent in = new Intent();
                in.setAction("SERVICE_ACTION");
                in.putExtra("type", "1");
                context.sendBroadcast(in);*/
            }
        
   }
}