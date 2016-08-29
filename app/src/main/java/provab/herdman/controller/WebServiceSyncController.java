package provab.herdman.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import provab.herdman.constants.Links;
import provab.herdman.customelements.SyncDialog;

/**
 * Created by PTBLR-1057 on 5/30/2016.
 */
public class WebServiceSyncController {



    Context context;
   // SyncDialog pDialog;
    ProgressDialog progresss ;

    WebInterface myInterface;

    public WebServiceSyncController(Context context, Object obj) {
        this.context = context;
        this.myInterface = (WebInterface) obj;
        this.progresss = new ProgressDialog(context);



    }




    public void sendRequest(String url, RequestParams params , final int flag) {


        Log.e("Params",params.toString());




        ConnectionDetector cd = new ConnectionDetector(context);
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent == true) {

            showProgress();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(120000);
            client.post(url, params, new AsyncHttpResponseHandler() {


                @Override
                public void onProgress(int bytesWritten, int totalSize) {
                    super.onProgress(bytesWritten, totalSize);

                       Log.e("Updation ",bytesWritten+"");


                        progresss.setProgress(bytesWritten);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    progresss.dismiss();
                    String response = "";
                    try {
                        response = new String(responseBody, "UTF-8");
                        Log.e("Success response", response);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        myInterface.getResponse(response , flag);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    progresss.dismiss();
                    try {
                        myInterface.failureResponse(statusCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            });


        }else {

            showIntenetErrorDialog();
        }
    }



    public void getRequest(String url ,final int flag){

        ConnectionDetector cd = new ConnectionDetector(context);
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent == true) {

            showProgress();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(60000);
            client.get(url, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    progresss.dismiss();

                    String response = "";
                    try {
                        response = new String(responseBody, "UTF-8");
                        Log.d("Success response", response);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        myInterface.getResponse(response , flag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    progresss.dismiss();

                    try {
                        myInterface.failureResponse(statusCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            showIntenetErrorDialog();
        }
    }

    private void showProgress() {
        // TODO Auto-generated method stub

        progresss.setCancelable(false);
        progresss.setMessage("Loading Data...");
        progresss.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progresss.show();




        //pDialog.setCancelable(false);

      //  pDialog.show();
    }

    public void showIntenetErrorDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        alertDialogBuilder.setTitle("");
        alertDialogBuilder
                .setMessage("No Internet Connection !")
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
