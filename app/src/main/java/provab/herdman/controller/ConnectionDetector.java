package provab.herdman.controller;
 
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import provab.herdman.activity.MyApplication;

public class ConnectionDetector {

   static ConnectionDetector connectionDetector;
     
    private Context _context;
     
    public ConnectionDetector(Context context){
        this._context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) 
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }

    public static ConnectionDetector getConnectionDetectorInstance(){
        if(connectionDetector==null){
            connectionDetector=new ConnectionDetector(MyApplication.getContext());
        }
        return  connectionDetector;
    }
}