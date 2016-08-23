package provab.herdman.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Anees Thyrantakath on 30/9/15.
 */
public class WebServiceController {

    Context context;
    ProgressDialog pDialog;
    WebInterface myInterface;

    public WebServiceController(Context context, Object obj) {
        this.context = context;
        this.myInterface = (WebInterface) obj;
    }


    public void sendRequest(String url, RequestParams params ,final int flag) {


        ConnectionDetector cd = new ConnectionDetector(context);
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent == true) {

            showProgress();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(120000);
            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    pDialog.dismiss();
                    String response = "";
                    try {
                        response = new String(responseBody, "UTF-8");
                        Log.e("Success response",response);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        myInterface.getResponse(response,flag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    pDialog.dismiss();
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

    public void getRequest(String url , final int flag){

        ConnectionDetector cd = new ConnectionDetector(context);
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent == true) {

            showProgress();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(60000);
            client.get(url, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    pDialog.dismiss();
                    String response = "";
                    try {
                        response = new String(responseBody, "UTF-8");
                        Log.d("Success response", response);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        myInterface.getResponse(response,flag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    pDialog.dismiss();
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
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please Wait ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
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
