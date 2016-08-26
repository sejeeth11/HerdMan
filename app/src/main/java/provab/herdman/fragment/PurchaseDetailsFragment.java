package provab.herdman.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.ArrayList;
import java.util.Date;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.activity.VillageMainActivity;
import provab.herdman.beans.CattleBean;
import provab.herdman.constants.CommonData;
import provab.herdman.constants.GlobalVar;
import provab.herdman.controller.WebServiceSyncController;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 6/14/2016.
 */
public class PurchaseDetailsFragment extends Fragment {

    Button next;
    Button previous;
    LinearLayout purchaseDateLayout;
    TextView purchaseDate;
    EditText rate;
    EditText source;
    TextView date_set;
    CattleBean bean;
    String Id;
    boolean flag=false;

    Activity activity_village;
    Activity activity_animal;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_purchase_details, container, false);
       Id =   bean.getCattleBeanInstance().getAnimalId();


        findViews(v);
        return v;
    }

    public void findViews(View view){

        next = (Button) view.findViewById(R.id.next);
        previous  = (Button) view.findViewById(R.id.previous);
        purchaseDateLayout = (LinearLayout) view.findViewById(R.id.purchaseDateLayout);
        purchaseDate = (TextView) view.findViewById(R.id.previous);
        rate = (EditText) view.findViewById(R.id.rate);
        date_set = (TextView)view.findViewById(R.id.purchaseDate);
        date_set.setText(CommonData.getInstance().getDefaultDate());

        source= (EditText) view.findViewById(R.id.source);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatabaseHelper.getDatabaseHelperInstance(getActivity()).Save_Purchase(Id,date_set.getText().toString(),rate.getText().toString(),source.getText().toString());

                //Toast.makeText(getActivity(),"Saved Successfully",Toast.LENGTH_SHORT).show();
                if(flag){
                    ((VillageMainActivity) getActivity()).swipeViewPagerToNextScreen();
                }
                else{
                    ((AnimalRegistration) getActivity()).swipeViewPagerToNextScreen();
                }


            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag){
                    ((VillageMainActivity) getActivity()).swipeViewPagerToPreviousScreen();
                }
                else{
                    ((AnimalRegistration) getActivity()).swipeViewPagerToPreviousScreen();
                }


            }
        });

        purchaseDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {

            date_set.setText(GlobalVar.dialogeFormat.format(date));

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };




}
