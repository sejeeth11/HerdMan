package provab.herdman.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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




            SessionManager manager = new SessionManager(getApplicationContext());
            String ReproductionJSOn = DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).SyncCattleRegistration(manager.getPrefData("UserCode"));
     //       String DetailsJSon = DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).getDetailss(manager.getPrefData("UserCode"));

//
//
//
//
//
//
//
//
//
//
// mSession = new SessionManager(this);
//            userId = mSession.getPrefData(SessionManager.KEY_USER_ID);
            getDataFromBundle(ReproductionJSOn);
        } catch (Exception ne) {
            ne.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    private void getDataFromBundle(String json) {
        try {

            String TotalData = "";




            try {
              JSONObject object = new JSONObject();
              object.put("reproduction", CommonData.getInstance().getReArraycommon());
              object.put("details",CommonData.getInstance().getDetailsarray());

                Log.e("Object",object.toString());



                RequestParams params1 = new RequestParams();
                params1.put("Json",json);
                ConnectionDetector detector = new ConnectionDetector(getApplicationContext());


                if(detector.isConnectingToInternet()) {
                    Send_data(Links.SERVER_PASS_DATA, params1);
                }else{

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



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
                    Log.e("Success response", response);


                    if(flag == 1) {

                        SessionManager manager = new SessionManager(getApplicationContext());
                        String DetailsJSon = DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).getDetailss(manager.getPrefData("UserCode"));
                        getDataFromBundle(DetailsJSon);
                        flag = 2;


                    }else{

                    }



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


        stopSelf(serviceStartId);
    }

    @Override
    public void failureResponse(int statusCode) throws JSONException {

    }
}