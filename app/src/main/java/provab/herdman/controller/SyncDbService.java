package provab.herdman.controller;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import provab.herdman.activity.MyApplication;
import provab.herdman.constants.CommonData;
import provab.herdman.constants.Links;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.SessionManager;

/**
 * Created by ptblr1035 on 29/8/16.
 */
public class SyncDbService extends Service implements WebInterface {
 //   private String mProjectId = null;
    private int serviceStartId = 0;
    int flag = 1;



    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceStartId = startId;


        try {



            getDataFromBundle();

            SynctheUserDetails();



        } catch (Exception ne) {
            ne.printStackTrace();
        }
        return START_NOT_STICKY;
    }



       public void SynctheUserDetails(){


           System.out.println("Status"+DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).getallUser());

           if(DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).getallUser().equalsIgnoreCase("0")){
               RequestParams params = new RequestParams();
               params.put("requestType", 1);
               WebServiceSyncController wc = new WebServiceSyncController(this.getApplicationContext(), this);
               wc.sendRequest(Links.GET_REGISTERED_USERS, params,1);
           }else{

               
           }











       }























            private void getDataFromBundle() {
                try {

                   String Details;
                    final String[] Production = new String[1];
                    final String[] Reproduction = new String[1];


                    final  SessionManager manager = new SessionManager(getApplicationContext());

                            Details = DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).getDetailss(manager.getPrefData("UserCode"));
                            Log.e("Message Details",Details);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Production[0] = DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).Production_Tabel(manager.getPrefData("UserCode"));
                            Log.e("Message Production", Production[0]);
                        }
                    }, 1000);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Reproduction[0] = DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).SyncCattleRegistration(manager.getPrefData("UserCode"));
                            Log.e("Message Reproduction", Reproduction[0]);
                        }
                    }, 2000);



                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject object = new JSONObject();
                            try {
                                object.put("reproduction",CommonData.getInstance().getReArraycommon());
                                object.put("details",CommonData.getInstance().getDetailsarray());
                                object.put("production",CommonData.getInstance().getProductionArray());

                                RequestParams params1 = new RequestParams();
                                params1.put("Json",String.valueOf(object.toString()));
                                ConnectionDetector detector = new ConnectionDetector(getApplicationContext());

                                if(detector.isConnectingToInternet()){

                                    Send_data(Links.SERVER_PASS_DATA,params1);

                                }else{


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, 3000);


                } catch (NullPointerException ne) {

                    ne.printStackTrace();

                } finally {
              stopSelf();
        }

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




//    private void sendTaskVisitorWS() {
//        DebugLog.logTrace("sendTaskVisitorWS");
//
//        RequestParams params = new RequestParams();
//        params.add("contractor_id", userId);
//        params.add("task_id", mProjectId);
//        params.add("crew_id", String.valueOf(viewCrewBeanList.get(0).getContractorId()));
//        WebServiceController wc = new WebServiceController(this, this, false);
//        wc.sendRequest(URL.WS_TASK_ADD_CREW_TO_TASK, params);
//    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




    @Override
    public void getResponse(String response, int flag) throws JSONException {


        final JSONObject responseObject = new JSONObject(response);

        if (flag == 1) {
            JSONArray registeredUserList = responseObject.getJSONArray("User");

            Log.e("Calling","1");

            Log.e("Calling",responseObject.toString());

            DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).addRegisteredUsers(registeredUserList);


            System.out.println("Sync "+DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).masterSyncStatus());






            if (!DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).masterSyncStatus()) {

                //   Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();

                RequestParams params = new RequestParams();
                WebServiceSyncController wc = new WebServiceSyncController(this.getApplicationContext(), this);
                params.put("Userid", 0);
                params.put("requestType", 2);
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
                        DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).addMasterData(responseObject.getJSONObject("OneTimeMasterTable"));

                         Toast.makeText(getApplicationContext()," Data Synced Successfully Continue Login ",Toast.LENGTH_LONG).show();

                        stopSelf(serviceStartId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }





    }

    @Override
    public void failureResponse(int statusCode) throws JSONException {

    }










}