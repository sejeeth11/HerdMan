package provab.herdman.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

import provab.herdman.R;
import provab.herdman.constants.Links;
import provab.herdman.controller.WebServiceSyncController;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.SessionManager;

/**
 * Created by Swati on 02/09/16.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username,old_password,new_password,confirm_password;
    Button change_password;
    TextView toolbar_title;
    public static final int GET_MASTER_DATA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        initialiser();
    }

    private void initialiser() {

        toolbar_title=(TextView) findViewById(R.id.title_Ctv);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText("Change Password");

        username=(EditText) findViewById(R.id.username);
        old_password=(EditText) findViewById(R.id.old_password);
        new_password=(EditText) findViewById(R.id.new_password);
        confirm_password=(EditText) findViewById(R.id.confirm_password);
        change_password=(Button) findViewById(R.id.change_password);
        change_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_password:

                if(!username.getText().toString().isEmpty() && !old_password.getText().toString().isEmpty() &&
                        !new_password.getText().toString().isEmpty() ){

                    if(new_password.getText().toString().equalsIgnoreCase(confirm_password.getText().toString())) {
                        DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext());
                        databaseHelper.changePassword(ChangePasswordActivity.this, username.getText().toString(), old_password.getText().toString(), new_password.getText().toString());

                      Log.e("USer JSon",DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).UserArray());



                     //   Send_data(Links.s)


                    }
                    else{
                        Toast.makeText(this, "Please fill same confirm password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Please fill the above fields", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




    public void Send_data(String links,RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(150000);
        client.addHeader("content-Type", "application/x-www-form-urlencoded");
        client.post(links, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String response = "";
                try {
                    response = new String(responseBody, "UTF-8");

                    DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).Update_Sync_Flag("Reproduction","1","SyncStatus");
                    DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).Update_Sync_Flag("Details","1","SyncStatus");
                    DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).Update_Sync_Flag("Production","1","SyncStatus");


                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.e("Error",error.getMessage());





            }



        });



    }





}
