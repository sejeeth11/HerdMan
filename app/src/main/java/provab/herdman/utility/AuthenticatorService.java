package provab.herdman.utility;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by PTBLR-1057 on 5/21/2016.
 */
public class AuthenticatorService extends Service {
    private Authenticator mAuthenticator;
    public AuthenticatorService() {
    }

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}