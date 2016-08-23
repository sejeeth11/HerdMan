package provab.herdman.utility;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import provab.herdman.activity.MyApplication;

/**
 * Created by PTBLR-1057 on 5/21/2016.
 */
public class HerdManSyncService extends Service {

    private static HerdManSynchAdapter herdManSynchAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock){
            if(herdManSynchAdapter==null){
                herdManSynchAdapter=new HerdManSynchAdapter(MyApplication.getContext(),true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return herdManSynchAdapter.getSyncAdapterBinder();
    }
}
