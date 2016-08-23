package provab.herdman.utility;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by PTBLR-1057 on 5/21/2016.
 */
public class HerdManSynchAdapter extends AbstractThreadedSyncAdapter {

    public HerdManSynchAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public HerdManSynchAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        System.out.println("HAMSINI N RAO");
    }
}
