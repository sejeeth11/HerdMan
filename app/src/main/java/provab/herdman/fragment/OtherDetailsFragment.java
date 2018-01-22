package provab.herdman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.activity.VillageMainActivity;
import provab.herdman.beans.CattleBean;
import provab.herdman.constants.CommonData;
import provab.herdman.constants.Links;
import provab.herdman.controller.ConnectionDetector;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.SessionManager;

/**
 * Created by PTBLR-1057 on 6/14/2016.
 */
public class OtherDetailsFragment extends Fragment {

    Button nextButton,prevousbutton;
    EditText marketvalue,noringsonhorn,rearingpurpose,horndistance,color,aitagno,birthweight,placeno,doctor;
    CattleBean bean;
    String Id;
    boolean flag=false;

    Activity activity_village;
    Activity activity_animal;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof VillageMainActivity) {
            this.activity_village =  activity;
            flag=true;
        }
        else if(activity instanceof AnimalRegistration){
            this.activity_animal =  activity;
            flag=false;
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_other_details_duplicate, container, false);
        findViews(v);
        return v;
    }

    public void findViews(View view){
        prevousbutton = (Button)view.findViewById(R.id.prev);
        nextButton = (Button) view.findViewById(R.id.next);
        marketvalue = (EditText)view.findViewById(R.id.Marketvalue);
        noringsonhorn = (EditText)view.findViewById(R.id.noringsonhorn);
        rearingpurpose = (EditText)view.findViewById(R.id.rearingpurpose);
        horndistance = (EditText)view.findViewById(R.id.horndistance);
        color = (EditText)view.findViewById(R.id.color);
        aitagno = (EditText)view.findViewById(R.id.aitagnumber);
        birthweight = (EditText)view.findViewById(R.id.birthweight);
        placeno = (EditText)view.findViewById(R.id.placeno);
        doctor = (EditText)view.findViewById(R.id.doctor);
        Id =   bean.getCattleBeanInstance().getAnimalId();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               final  SessionManager manager = new SessionManager(getActivity());


                DatabaseHelper.getDatabaseHelperInstance(getActivity()).addOtherDetails(Id,marketvalue.getText().toString(),noringsonhorn.getText().toString(),rearingpurpose.getText().toString(),color.getText().toString(),horndistance.getText().toString(),doctor.getText().toString());


                final String[] Details = new String[1];
                final String[] Production = new String[1];
                final String[] Reproduction = new String[1];


              //  final  SessionManager manager = new SessionManager(getApplicationContext());



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Details[0] = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getDetailss(manager.getPrefData("UserCode"));

                        //Production[0] = DatabaseHelper.getDatabaseHelperInstance(getActivity()).Production_Tabel(manager.getPrefData("UserCode"));
                        Log.e("Message Details", Details[0]);
                    }
                }, 1000);




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Production[0] = DatabaseHelper.getDatabaseHelperInstance(getActivity()).Production_Tabel(manager.getPrefData("UserCode"));
                        Log.e("Message Production", Production[0]);
                    }
                }, 2000);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Reproduction[0] = DatabaseHelper.getDatabaseHelperInstance(getActivity()).SyncCattleRegistration(manager.getPrefData("UserCode"));
                        Log.e("Message Reproduction", Reproduction[0]);
                    }
                }, 3000);


                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {


                        JSONObject object = new JSONObject();
                        try {


                            object.put("details",CommonData.getInstance().getDetailsarray());
                            object.put("production",CommonData.getInstance().getProductionArray());
                            object.put("reproduction", CommonData.getInstance().getReArraycommon());



                            Log.e("FUll Data",object.toString());


                            RequestParams params1 = new RequestParams();
                            params1.put("Json",String.valueOf(object.toString()));
                            ConnectionDetector detector = new ConnectionDetector(getActivity());


                                Send_data(Links.SERVER_PASS_DATA,params1);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, 4000);





                Toast.makeText(getActivity(),"Registration Successfull",Toast.LENGTH_LONG).show();

            //    exportDB();


                getActivity().finish();



            }
        });

    }

    public void Send_data(String links,RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(120000);
        client.addHeader("content-Type", "application/x-www-form-urlencoded");

        client.post(links, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = "";

                try {
                    response = new String(responseBody, "UTF-8");
                    Log.e("Success response", response);


                    DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Reproduction","1","SyncStatus");
                    DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Details","1","SyncStatus");
                    DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Production","1","SyncStatus");


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
