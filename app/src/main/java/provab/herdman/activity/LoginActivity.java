package provab.herdman.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import provab.herdman.R;
import provab.herdman.beans.UserInfo;
import provab.herdman.constants.GlobalVar;
import provab.herdman.constants.Links;
import provab.herdman.controller.WebInterface;
import provab.herdman.controller.WebServiceSyncController;
import provab.herdman.scan.ScannerFragment;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.SessionManager;

/**
 * Created by PTBLR-1057 on 5/17/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WebInterface,QrCodeCallBack {

    public static final int GET_REGISTERED_USERS_LIST = 1;
    public static final int GET_MASTER_DATA = 2;
    public static final int GET_THIRD_AND_FOURTH_MASTERS = 3;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    Button loginButton;
    EditText userName;
    EditText userPassword;
    UserInfo userInfo;
    SessionManager sessionManager;
    Button changepassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = SessionManager.getSessionInstance();
        findView();

    }



    public void findView() {

        loginButton = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.userPassword);
        changepassword = (Button)findViewById(R.id.change_password);


        loginButton.setOnClickListener(this);
        changepassword.setOnClickListener(this);

        RequestParams params = new RequestParams();
        params.put("requestType", GET_REGISTERED_USERS_LIST);
        WebServiceSyncController wc = new WebServiceSyncController(this, this);
        wc.sendRequest(Links.GET_REGISTERED_USERS, params,1);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:


                SessionManager manager = new SessionManager(LoginActivity.this);


                if ((userName.getText().toString().trim().equals("")) || userPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "BOTH THE FIELDS ARE MANDATORY", Toast.LENGTH_LONG).show();
                    return;
                }



                DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext());
                userInfo = databaseHelper.getUser(userName.getText().toString().trim(), userPassword.getText().toString().trim());


                if (userInfo != null) {
                    System.out.println("My Id " + userInfo.getUsercode());

                    JSONObject tableNameMaxId = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getThirdTypeTableMaxId();
                    if (tableNameMaxId != null) {
                        Log.e("tablemaxid", tableNameMaxId.toString());

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);


                        if (!preferences.getString("Sync_Login", "").equalsIgnoreCase("")) {
                            if (userInfo.getQrcode()) {
                                launchActivity();
                            } else {
                                sessionManager.createLoginSession(userInfo.getUid(), userInfo.getPassword(), userInfo.getGroop(), userInfo.getHerd(), userInfo.getCompanycode(), userInfo.getApptype(), userInfo.getUpdatedby(), userInfo.getUpdatedat(), userInfo.getUsercode(), userInfo.getQrcode());
                                Intent intent = new Intent(LoginActivity.this, SelectCategoryActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        } else {


                            RequestParams params = new RequestParams();
                            params.put("requestType", GET_THIRD_AND_FOURTH_MASTERS);
                            params.put("Userid", userInfo.getUsercode());
                            params.put("Tablename", tableNameMaxId.toString());
                            WebServiceSyncController wc = new WebServiceSyncController(this, this);
                            wc.sendRequest(Links.GET_THIRD_AND_FOURTH_TYPE_MASTER, params, 3);
                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("Sync_Login", "1");
                            editor.commit();


                        }


                    }


                } else {

                if(DatabaseHelper.getDatabaseHelperInstance(LoginActivity.this).getallUser().equalsIgnoreCase("0")){

                    Toast.makeText(this, "Please Enable the Netconnection and Sync Server", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Invalid  crediential", Toast.LENGTH_LONG).show();
                }


                }
                break;



            case R.id.change_password:

                startActivity(new Intent(LoginActivity.this,ChangePasswordActivity.class));

                break;



        }
    }

    public void launchActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);

        } else {
            DialogFragment dialogFrag = new ScannerFragment();
            dialogFrag.show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DialogFragment dialogFrag = new ScannerFragment();
                    dialogFrag.show(getSupportFragmentManager(), "dialog");
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }



    @Override
    public void getResponse(String response ,int flag ) throws JSONException {
        final JSONObject responseObject = new JSONObject(response);

        if (flag == 1) {
            JSONArray registeredUserList = responseObject.getJSONArray("User");

            Log.e("Calling","1");

            Log.e("Calling",responseObject.toString());

            DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).addRegisteredUsers(registeredUserList);


            System.out.println("Sync "+DatabaseHelper.getDatabaseHelperInstance(this).masterSyncStatus());





            if (!DatabaseHelper.getDatabaseHelperInstance(this).masterSyncStatus()) {

             //   Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();

                RequestParams params = new RequestParams();
                WebServiceSyncController wc = new WebServiceSyncController(this, this);
                params.put("Userid", 0);
                params.put("requestType", GET_MASTER_DATA);
                wc.sendRequest(Links.GET_ONE_TIME_PERMANENT_MASTER, params,2);
            }





        } else if (flag == 2) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Log.e("Calling","2");
                        Log.e("Calling",responseObject.toString());
                        //System.out.println("FALG "+"THREE");
                        DatabaseHelper.getDatabaseHelperInstance(LoginActivity.this).addMasterData(responseObject.getJSONObject("OneTimeMasterTable"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        } else if (flag == 3) {


            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {

                        Log.e("Calling","2");

                        Log.e("Calling",responseObject.toString());

                        DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).saveThirdAndFourthTypeMaster(responseObject.getJSONObject("GetMasterData"));

                        try {
                            writeToSD();
                        }catch (IOException e){
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();


            sessionManager.createLoginSession(userInfo.getUid(), userInfo.getPassword(), userInfo.getGroop(), userInfo.getHerd(), userInfo.getCompanycode(), userInfo.getApptype(), userInfo.getUpdatedby(), userInfo.getUpdatedat(), userInfo.getUsercode(), userInfo.getQrcode());

            if (userInfo.getQrcode()) {
                launchActivity();
            } else {

                Intent intent = new Intent(LoginActivity.this, SelectCategoryActivity.class);
                        startActivity(intent);
                        finish();

            }
        }else if(flag == 5){
            DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).saveThirdAndFourthTypeMaster(responseObject.getJSONObject("GetMasterData"));
            Toast.makeText(getApplicationContext(),"Synced Successfully",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void failureResponse(int statusCode) throws JSONException {

        Toast.makeText(this, "FAILED TO FETCH USERS = " + statusCode, Toast.LENGTH_LONG).show();

    }











    @Override
    public void setQrCodeResult(String idNumber) {
//        Toast.makeText(LoginActivity.this, ""+DatabaseHelper.getDatabaseHelperInstance(this).getDetails("310001930018"), Toast.LENGTH_SHORT).show();

     //   idNumber = "789654324354";

        JSONObject detailsJsonObject=DatabaseHelper.getDatabaseHelperInstance(this).getDetails(idNumber);



//        Log.e("Not Null",String.valueOf(detailsJsonObject.toString()));


        if (detailsJsonObject!=null){

            sessionManager.createLoginSession(userInfo.getUid(), userInfo.getPassword(), userInfo.getGroop(), userInfo.getHerd(), userInfo.getCompanycode(), userInfo.getApptype(), userInfo.getUpdatedby(), userInfo.getUpdatedat(), userInfo.getUsercode(), userInfo.getQrcode());
            try{
                GlobalVar.ID_NUMBER=detailsJsonObject.getString("IdNo");
                GlobalVar.VILLAGE_CODE=detailsJsonObject.getString("LotNo");
                GlobalVar.OWNERS_NAME=detailsJsonObject.getString("name");
                GlobalVar.OWNERS_CODE=detailsJsonObject.getString("Ownercode");
            /*    Intent intent=new Intent(this,AnimalMainActivity.class);
                startActivity(intent);*/
                DatabaseHelper databaseHelpers = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext());
                userInfo = databaseHelpers.getUser(userName.getText().toString().trim(), userPassword.getText().toString().trim());

                Intent intent=new Intent(this,AnimalMainActivity.class);
                intent.putExtra("idNumber",idNumber);
                intent.putExtra("Hint","1");

                startActivity(intent);
                finish();

            }catch (JSONException e){
                e.printStackTrace();
            }


        }else{

            sessionManager.createLoginSession(userInfo.getUid(), userInfo.getPassword(), userInfo.getGroop(), userInfo.getHerd(), userInfo.getCompanycode(), userInfo.getApptype(), userInfo.getUpdatedby(), userInfo.getUpdatedat(), userInfo.getUsercode(), userInfo.getQrcode());
            Intent intent=new Intent(this,VillageMainActivity.class);
            intent.putExtra("idNumber",idNumber);
            intent.putExtra("Hint","1");
            startActivity(intent);
            finish();


        }
    }
    private void writeToSD() throws IOException {
        String DB_PATH;
        String DB_NAME = "HerdMan";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DB_PATH = getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        } else {
            DB_PATH = getFilesDir().getPath() + getPackageName() + "/databases/";
        }
        File sd = Environment.getExternalStorageDirectory();

        if (sd.canWrite()) {
            String currentDBPath = DB_NAME;
            String backupDBPath = "MyDB.db";
            File currentDB = new File(DB_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        }
    }
}
