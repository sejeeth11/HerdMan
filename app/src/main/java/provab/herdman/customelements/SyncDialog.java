package provab.herdman.customelements;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import provab.herdman.R;

/**
 * Created by PTBLR-1057 on 5/25/2016.
 */
public class SyncDialog extends Dialog implements
        android.view.View.OnClickListener {


    Context activityContext;

    public SyncDialog(Context context) {
        super(context, R.style.syncDialog);
        this.activityContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sync_dialog);
    }


    @Override
    public void onClick(View v) {

    }
}
