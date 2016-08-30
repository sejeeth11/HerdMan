package provab.herdman.fragment;

import android.app.Activity;
import android.os.Bundle;
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

import java.io.UnsupportedEncodingException;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.activity.VillageMainActivity;
import provab.herdman.beans.CattleBean;
import provab.herdman.constants.Links;
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


                SessionManager manager = new SessionManager(getActivity());


                DatabaseHelper.getDatabaseHelperInstance(getActivity()).addOtherDetails(Id,marketvalue.getText().toString(),noringsonhorn.getText().toString(),rearingpurpose.getText().toString(),color.getText().toString(),horndistance.getText().toString(),doctor.getText().toString());
                String JSOn = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getDetailss(manager.getPrefData("UserCode"));

                Log.e("Message",JSOn);
                RequestParams params1 = new RequestParams();
                params1.put("Json",JSOn);



                Send_data(Links.SERVER_PASS_DATA,params1);

                Toast.makeText(getActivity(),"Registration Successfull",Toast.LENGTH_LONG).show();
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
