package provab.herdman.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.activity.SelectCategoryActivity;
import provab.herdman.activity.VillageMainActivity;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.beans.CattleBean;
import provab.herdman.constants.CommonData;
import provab.herdman.constants.GlobalVar;
import provab.herdman.constants.Links;
import provab.herdman.controller.ConnectionDetector;
import provab.herdman.controller.WebInterface;
import provab.herdman.controller.WebServiceSyncController;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.SessionManager;

/**
 * Created by PTBLR-1057 on 6/15/2016.
 */
public class BreedingDetailsFragment extends Fragment implements WebInterface{


    RadioGroup milkingDryGroup;
    RadioGroup milkingGroup;
    RadioGroup dryGroup;
    RadioGroup pregnantAIGroup;
    RadioGroup pregnantGroup;
    RadioGroup aiGroup;
    RadioGroup totalAndAvgMilkingGroup;
    RadioGroup calvingNormalAbnormalGroup;

    LinearLayout milkingGroupLayout;
    LinearLayout dryGroupLayout;
    LinearLayout calfSexLayout;

    EditText milkingDateEdiText;
    EditText milkingDaysEditText;
    EditText dryDateEditText;
    EditText dryDaysEditText;
    EditText heatSequence;

    Activity activity;

    LinearLayout pregnantGroupLayout;
    LinearLayout aiGroupLayout;
    LinearLayout milkingDrySireEarTagNoLayout;
    LinearLayout milkingDryInseminatorLayout;
    LinearLayout pregnantAiSireLayout;
    LinearLayout pregnantAiInsimLayout;

    EditText noOfCalvingDays;
    EditText pregnantDateEdiText;
    EditText pregnantDaysEditText;
    EditText aiDateEdiText;
    EditText aiDaysEditText;
    EditText totalMilkYield;
    EditText averageMilkYield;

    TextView milkingDrySireEarTagNo;
    TextView milkingDryInseminator;
    TextView pregnantAiSire;
    TextView pregnantAiInsim;
    TextView calfSex;

    Button next;
    Button previous;

    SessionManager manager;
    public static final String USERCODE="UserCode";
    Activity activity_village;
    Activity activity_animal;
    boolean flag=false;
    String Userid;
    SharedPreferences preferences;
    String FLAGS = "1";




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


    private SlideDateTimeListener listener1 = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            milkingDateEdiText.setText(GlobalVar.dialogeFormat.format(date));
        }

        @Override
        public void onDateTimeCancel() {
        }
    };
    private SlideDateTimeListener listener2 = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            dryDateEditText.setText(GlobalVar.dialogeFormat.format(date));
            String dates[] = dryDateEditText.getText().toString().split(" ");
            String datefinal = dates[0];
            String currentdate = CommonData.getInstance().getDefaultDate();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MM-dd-yyyy");

            try {
                Date date1 = simpleDateFormat.parse(datefinal);
                Date date2 = simpleDateFormat.parse(currentdate);
                String difference = printDifference(date1,date2);
                dryDaysEditText.setText(difference);

            }catch(Exception e){}

        }

        @Override
        public void onDateTimeCancel() {
        }
    };



    private SlideDateTimeListener listener3 = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            pregnantDateEdiText.setText(GlobalVar.dialogeFormat.format(date));
        }

        @Override
        public void onDateTimeCancel() {
        }
    };
    private SlideDateTimeListener listener4 = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            aiDateEdiText.setText(GlobalVar.dialogeFormat.format(date));
        }

        @Override
        public void onDateTimeCancel() {
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_breeding_details, container, false);
        manager = new SessionManager(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Userid = manager.getPrefData(USERCODE);




        findViews(v);
//        milkingDryGroup.check(R.id.milking);
        pregnantAIGroup.check(R.id.yes);

      /*  if(flag) {

            Toast.makeText(getActivity(), "ID VALUE IS AS FOLLOWS = " + ((VillageMainActivity) activity).getId(), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "ID VALUE IS AS FOLLOWS = " + ((AnimalRegistration) activity).getId(), Toast.LENGTH_LONG).show();
        }*/

            return v;
    }

    public void findViews(View view) {
        milkingDryGroup = (RadioGroup) view.findViewById(R.id.milkingDryGroup);
        milkingGroup = (RadioGroup) view.findViewById(R.id.milkingGroup);
        dryGroup = (RadioGroup) view.findViewById(R.id.dryGroup);
        pregnantGroup = (RadioGroup) view.findViewById(R.id.pregnantGroup);
        pregnantAIGroup = (RadioGroup) view.findViewById(R.id.pregnantAIGroup);
        aiGroup = (RadioGroup) view.findViewById(R.id.aiGroup);
        calvingNormalAbnormalGroup = (RadioGroup) view.findViewById(R.id.aiGroup);
        totalAndAvgMilkingGroup = (RadioGroup) view.findViewById(R.id.totalAndAvgMilkingGroup);
        milkingGroupLayout = (LinearLayout) view.findViewById(R.id.milkingGroupLayout);
        dryGroupLayout = (LinearLayout) view.findViewById(R.id.dryGroupLayout);
        pregnantGroupLayout = (LinearLayout) view.findViewById(R.id.pregnantGroupLayout);
        aiGroupLayout = (LinearLayout) view.findViewById(R.id.aiGroupLayout);
        milkingDrySireEarTagNoLayout = (LinearLayout) view.findViewById(R.id.milkingDrySireEarTagNoLayout);
        milkingDryInseminatorLayout = (LinearLayout) view.findViewById(R.id.milkingDryInseminatorLayout);
        pregnantAiSireLayout = (LinearLayout) view.findViewById(R.id.pregnantAiSireLayout);
        pregnantAiInsimLayout = (LinearLayout) view.findViewById(R.id.pregnantAiInsimLayout);
        calfSexLayout = (LinearLayout) view.findViewById(R.id.calfSexLayout );
        milkingDateEdiText = (EditText) view.findViewById(R.id.milkingDateEdiText);
        milkingDaysEditText = (EditText) view.findViewById(R.id.milkingDaysEditText);
        dryDateEditText = (EditText) view.findViewById(R.id.dryDateEditText);
        dryDaysEditText = (EditText) view.findViewById(R.id.dryDaysEditText);
        pregnantDateEdiText = (EditText) view.findViewById(R.id.pregnantDateEdiText);
        pregnantDaysEditText = (EditText) view.findViewById(R.id.pregnantDaysEditText);
        aiDateEdiText = (EditText) view.findViewById(R.id.aiDateEdiText);
        aiDaysEditText = (EditText) view.findViewById(R.id.aiDaysEditText);
        totalMilkYield = (EditText) view.findViewById(R.id.totalMilkYield);
        averageMilkYield = (EditText) view.findViewById(R.id.averageMilkYield);
        noOfCalvingDays = (EditText) view.findViewById(R.id.noOfCalvingDays);
        heatSequence = (EditText) view.findViewById(R.id.heatSequence);
        milkingDrySireEarTagNo = (TextView) view.findViewById(R.id.milkingDrySireEarTagNo);
        milkingDryInseminator = (TextView) view.findViewById(R.id.milkingDryInseminator);
        pregnantAiSire = (TextView) view.findViewById(R.id.pregnantAiSire);
        pregnantAiInsim = (TextView) view.findViewById(R.id.pregnantAiInsim);
        calfSex= (TextView) view.findViewById(R.id.calfSex);
        next = (Button) view.findViewById(R.id.next);
        previous = (Button) view.findViewById(R.id.previous);

        calfSexLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowCalfSex=null;
                if(calvingNormalAbnormalGroup.getCheckedRadioButtonId()==-1){
                    dialogShowCalfSex= showCalfSex("1");
                }else if(calvingNormalAbnormalGroup.getCheckedRadioButtonId()==R.id.calvingNormal){
                    dialogShowCalfSex = showCalfSex("1");
                }else if(calvingNormalAbnormalGroup.getCheckedRadioButtonId()==R.id.calvingAbnormal) {
                    dialogShowCalfSex = showCalfSex("2");
                }
                dialogShowCalfSex.show();
            }
        });

        milkingDrySireEarTagNoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowSireTag = showSireTagList();
                dialogShowSireTag.show();
            }
        });

        milkingDryInseminator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showInseminator();
                dialogShowInseminator.show();
            }
        });

        pregnantAiSire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialogShowSireTag = showSireTagList1();
                dialogShowSireTag.show();

            }
        });

        pregnantAiInsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showInseminator1();
                dialogShowInseminator.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long calvingOrdryDays;
                String calvingOrDryDate;
                long pregnantOrAiDays;
                String pregnantOrAiDate;
                Resources resources = getActivity().getResources();
                final SessionManager manager = new SessionManager(getActivity());


                CattleBean cattleBean = null;
                if(flag){
                    cattleBean= ((VillageMainActivity) getActivity()).getCattleBean();
                }
                else {
                    cattleBean= ((AnimalRegistration) getActivity()).getCattleBean();
                }

                if (noOfCalvingDays.getText().toString().trim().isEmpty()) {
                    cattleBean.setNumberOfCalvingDays("1");
                } else {
                    cattleBean.setNumberOfCalvingDays(noOfCalvingDays.getText().toString());
                }
                try {
                    if (milkingDryGroup.getCheckedRadioButtonId() == -1) {
                        cattleBean.setCalvingDryPregnant(3);
                    } else {
                        if (milkingDryGroup.getCheckedRadioButtonId() == R.id.milking) {
                            if (milkingGroup.getCheckedRadioButtonId()==-1){
                                cattleBean.setCalvingDryPregnant(3);
                            }else{
                                if (milkingGroup.getCheckedRadioButtonId() == R.id.milkingDate) {
                                    if(milkingDateEdiText.getText().toString().isEmpty()){
                                        cattleBean.setCalvingDryPregnant(3);
                                    }else{
                                        calvingOrDryDate = GlobalVar.databaseFormat.format(GlobalVar.dialogeFormat.parse(milkingDateEdiText.getText().toString()));
                                        Date temp=GlobalVar.dialogeFormat.parse(milkingDateEdiText.getText().toString());
                                        Date heateDate= DateUtils.addDays(temp, (DatabaseHelper.getDatabaseHelperInstance(getActivity()).getConCalv(cattleBean.getBreedId())*-1));
                                        cattleBean.setCalvingHeateDate(GlobalVar.databaseFormat.format(heateDate));

                                        calvingOrdryDays = calculateDateDifference(cattleBean.getRegistrationDate(), calvingOrDryDate);
                                        cattleBean.setCalvingDate(calvingOrDryDate);
                                        cattleBean.setCalvingDays(String.valueOf(calvingOrdryDays));
                                        cattleBean.setCalvingDryPregnant(1);
                                    }
                                } else if (milkingGroup.getCheckedRadioButtonId() == R.id.milkingDays) {
                                    if(milkingDaysEditText.getText().toString().isEmpty()){
                                        cattleBean.setCalvingDryPregnant(3);
                                    }else {
                                        calvingOrdryDays = Integer.parseInt(milkingDaysEditText.getText().toString());
                                        /*Date date = new Date();
                                        String currentDate = GlobalVar.databaseFormat.format(date);
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE, (int) calvingOrdryDays * -1);*/
                                        Date registrationDate=GlobalVar.databaseFormat.parse(cattleBean.getRegistrationDate());
                                        Date calvingDateObject=DateUtils.addDays(registrationDate, (int) (calvingOrdryDays * -1));
                                        calvingOrDryDate = GlobalVar.databaseFormat.format(calvingDateObject);

                                        Date temp = GlobalVar.databaseFormat.parse(calvingOrDryDate);
                                        Date heateDate = DateUtils.addDays(temp, (-1*DatabaseHelper.getDatabaseHelperInstance(getActivity()).getConCalv(cattleBean.getBreedId())));
                                        cattleBean.setCalvingHeateDate(GlobalVar.databaseFormat.format(heateDate));

                                        cattleBean.setCalvingDate(calvingOrDryDate);
                                        cattleBean.setCalvingDays(String.valueOf(calvingOrdryDays));
                                        cattleBean.setCalvingDryPregnant(1);
                                    }
                                }
                            }
                        } else if (milkingDryGroup.getCheckedRadioButtonId() == R.id.dry) {
                            if (milkingGroup.getCheckedRadioButtonId()==-1){
                                cattleBean.setCalvingDryPregnant(3);
                            }else {
                                if (dryGroup.getCheckedRadioButtonId() == R.id.dryDate) {
                                    if(dryDateEditText.getText().toString().isEmpty()){
                                        cattleBean.setCalvingDryPregnant(3);
                                    }else {
                                        calvingOrDryDate = GlobalVar.databaseFormat.format(GlobalVar.dialogeFormat.parse(dryDateEditText.getText().toString()));
                                        calvingOrdryDays = calculateDateDifference(cattleBean.getRegistrationDate(), calvingOrDryDate);
                                        cattleBean.setDryDate(calvingOrDryDate);

                                        Date temp = GlobalVar.dialogeFormat.parse(dryDateEditText.getText().toString());
                                        Date calvingDate = DateUtils.addDays(temp, DatabaseHelper.getDatabaseHelperInstance(getActivity()).getLacLen());
                                        cattleBean.setCalvingDate(GlobalVar.databaseFormat.format(calvingDate));
                                        Date heateDate = DateUtils.addDays(temp, (-1*DatabaseHelper.getDatabaseHelperInstance(getActivity()).getConCalv(cattleBean.getBreedId())));
                                        cattleBean.setPregnantHeatDate(GlobalVar.databaseFormat.format(heateDate));

                                        cattleBean.setDryDays(String.valueOf(calvingOrdryDays));
                                        cattleBean.setCalvingDryPregnant(2);
                                    }

                                } else if (dryGroup.getCheckedRadioButtonId() == R.id.dryDays) {
                                    if(dryDaysEditText.getText().toString().isEmpty()){
                                        cattleBean.setCalvingDryPregnant(3);
                                    }else {
                                        calvingOrdryDays = Integer.parseInt(dryDaysEditText.getText().toString());
                                        /*Date date = new Date();
                                        String currentDate = GlobalVar.databaseFormat.format(date);
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE, (int) calvingOrdryDays * -1);
                                        calvingOrDryDate = GlobalVar.databaseFormat.format(new Date(cal.getTimeInMillis()));*/
                                        Date registrationDate=GlobalVar.databaseFormat.parse(cattleBean.getRegistrationDate());
                                        Date calvingDateObject=DateUtils.addDays(registrationDate, (int) (calvingOrdryDays * -1));
                                        calvingOrDryDate = GlobalVar.databaseFormat.format(calvingDateObject);

                                        Date temp = GlobalVar.databaseFormat.parse(calvingOrDryDate);
                                        Date calvingDate = DateUtils.addDays(temp, DatabaseHelper.getDatabaseHelperInstance(getActivity()).getLacLen());
                                        cattleBean.setCalvingDate(GlobalVar.databaseFormat.format(calvingDate));
                                        Date heateDate = DateUtils.addDays(temp, (-1*DatabaseHelper.getDatabaseHelperInstance(getActivity()).getConCalv(cattleBean.getBreedId())));
                                        cattleBean.setPregnantHeatDate(GlobalVar.databaseFormat.format(heateDate));

                                        cattleBean.setDryDate(calvingOrDryDate);
                                        cattleBean.setDryDays(String.valueOf(calvingOrdryDays));
                                        cattleBean.setCalvingDryPregnant(2);
                                    }
                                }
                            }
                        }
                        if(!(totalAndAvgMilkingGroup.getCheckedRadioButtonId()==-1)){
                            if(totalAndAvgMilkingGroup.getCheckedRadioButtonId()==R.id.totalMilkYieldRadio){
                                if(totalMilkYield.getText().toString().isEmpty()){
                                    cattleBean.setTotalMilkinDays("0");
                                }else{
                                    cattleBean.setTotalMilkinDays(totalMilkYield.getText().toString());
                                }
                            }else if((totalAndAvgMilkingGroup.getCheckedRadioButtonId()==R.id.totalMilkYieldRadio)){
                                if(averageMilkYield.getText().toString().isEmpty()){
                                    cattleBean.setAverageMilkingDays("0");
                                }else{
                                    cattleBean.setAverageMilkingDays(averageMilkYield.getText().toString());
                                }
                            }
                        }
                        if (milkingDrySireEarTagNo.getTag() == null || milkingDrySireEarTagNo.getText().toString().equals(resources.getString(R.string.registration_milking_sire))) {
                            if (Integer.parseInt(cattleBean.getSpeciesId()) == 1) {
                                cattleBean.setCalvingSire("999999C");
                            } else {
                                cattleBean.setCalvingSire("999998B");
                            }
                            cattleBean.setCalvingSire(milkingDrySireEarTagNo.getTag().toString());
                        } else {
                            cattleBean.setCalvingSire(milkingDrySireEarTagNo.getTag().toString());
                        }
                        if (milkingDryInseminator.getTag() == null || milkingDryInseminator.getText().toString().equals(resources.getString(R.string.registration_milking_insem))) {
                            cattleBean.setCalvingInsim("9999999");
                        } else {
                            cattleBean.setCalvingInsim(milkingDryInseminator.getTag().toString());
                        }
                        if (calvingNormalAbnormalGroup.getCheckedRadioButtonId() == -1) {
                            cattleBean.setCalvingNormalOrAbNormal("normal");
                        } else {
                            if (calvingNormalAbnormalGroup.getCheckedRadioButtonId() == R.id.calvingNormal) {
                                cattleBean.setCalvingNormalOrAbNormal("normal");
                            } else if (calvingNormalAbnormalGroup.getCheckedRadioButtonId() == R.id.calvingNormal) {
                                cattleBean.setCalvingNormalOrAbNormal("abnormal");
                            }
                        }
                        if(calfSex.getTag()==null ||  calfSex.getText().toString().equals(resources.getString(R.string.registration_breeding_calf_sex))){
                            cattleBean.setCalfSex("");
                        }else{
                            cattleBean.setCalfSex(calfSex.getTag().toString());
                        }
                    }
                    if (pregnantAIGroup.getCheckedRadioButtonId() == -1) {
                        cattleBean.setPregnantOrAi(3);
                    } else {
                        if (pregnantAIGroup.getCheckedRadioButtonId() == R.id.yes) {
                            if(pregnantGroup.getCheckedRadioButtonId()==-1){
                                cattleBean.setPregnantOrAi(3);
                            }else {
                                if (pregnantGroup.getCheckedRadioButtonId() == R.id.pregnantDate) {
                                    if(pregnantDateEdiText.getText().toString().isEmpty()){
                                        cattleBean.setPregnantOrAi(3);
                                    }else{



                                        pregnantOrAiDate = GlobalVar.databaseFormat.format(GlobalVar.dialogeFormat.parse(pregnantDateEdiText.getText().toString()));

                                        pregnantOrAiDays = calculateDateDifference(cattleBean.getRegistrationDate(), pregnantOrAiDate);

                                        cattleBean.setPregnantDate(pregnantOrAiDate);

                                        cattleBean.setPregnantDays(String.valueOf(pregnantOrAiDays));

                                        cattleBean.setPregnantOrAi(1);

                                        cattleBean.setPregnantHeatDate(pregnantOrAiDate);



                                   //     Log.e("Date Differnce",String.valueOf(pregnantOrAiDays));



                                    }
                                } else if (pregnantGroup.getCheckedRadioButtonId() == R.id.pregnantDays) {


                                    if(pregnantDaysEditText.getText().toString().isEmpty()){
                                        cattleBean.setPregnantOrAi(3);
                                    }else{
                                        pregnantOrAiDays = Integer.parseInt(pregnantDaysEditText.getText().toString());
                                        /*Date date = new Date();
                                        String currentDate = GlobalVar.databaseFormat.format(date);
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE, (int) pregnantOrAiDays * -1);
                                        pregnantOrAiDate = GlobalVar.databaseFormat.format(new Date(cal.getTimeInMillis()));*/


                                        Date registrationDateForPregDate=GlobalVar.databaseFormat.parse(cattleBean.getRegistrationDate());
                                        Date pregnantDateObject=DateUtils.addDays(registrationDateForPregDate, (int) (pregnantOrAiDays * -1));
                                        pregnantOrAiDate = GlobalVar.databaseFormat.format(pregnantDateObject);



                                        cattleBean.setPregnantDate(pregnantOrAiDate);

                                        Log.e("Date of Pregnant days",pregnantOrAiDate);


                                        cattleBean.setPregnantDays(String.valueOf(pregnantOrAiDays));
                                        cattleBean.setPregnantOrAi(1);
                                        Date registrationDate = GlobalVar.databaseFormat.parse(cattleBean.getRegistrationDate());
                                        Date heatDate = DateUtils.addDays(registrationDate, (int) (pregnantOrAiDays*-1));
                                        cattleBean.setPregnantHeatDate(GlobalVar.databaseFormat.format(heatDate));




                                    }
                                }
                            }
                        } else if (pregnantAIGroup.getCheckedRadioButtonId() == R.id.no) {
                            if(aiGroup.getCheckedRadioButtonId()==-1){
                                cattleBean.setPregnantOrAi(3);
                            }else{
                                if (aiGroup.getCheckedRadioButtonId() == R.id.aiDate) {
                                    if(aiDateEdiText.getText().toString().isEmpty()){
                                        cattleBean.setPregnantOrAi(3);

                                    }else{
                                        pregnantOrAiDate = GlobalVar.databaseFormat.format(GlobalVar.dialogeFormat.parse(aiDateEdiText.getText().toString()));
                                        pregnantOrAiDays = calculateDateDifference(cattleBean.getRegistrationDate(), pregnantOrAiDate);
                                        cattleBean.setAiDate(pregnantOrAiDate);
                                        cattleBean.setAiDays(String.valueOf(pregnantOrAiDays));
                                        cattleBean.setPregnantOrAi(2);
                                        cattleBean.setPregnantHeatDate(pregnantOrAiDate);
                                    }



                                } else if (aiGroup.getCheckedRadioButtonId() == R.id.aiDays) {
                                    if(aiDaysEditText.getText().toString().isEmpty()){
                                        cattleBean.setPregnantOrAi(3);
                                    }else{
                                        pregnantOrAiDays = Integer.parseInt(aiDaysEditText.getText().toString());
                                        /*Date date = new Date();
                                        String currentDate = GlobalVar.databaseFormat.format(date);
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.DATE, (int) pregnantOrAiDays * -1);
                                        pregnantOrAiDate = GlobalVar.databaseFormat.format(new Date(cal.getTimeInMillis()));*/
                                        Date registrationDateForPregDate=GlobalVar.databaseFormat.parse(cattleBean.getRegistrationDate());
                                        Date pregnantDateObject=DateUtils.addDays(registrationDateForPregDate, (int) (pregnantOrAiDays * -1));
                                        pregnantOrAiDate = GlobalVar.databaseFormat.format(pregnantDateObject);

                                        cattleBean.setAiDate(pregnantOrAiDate);
                                        cattleBean.setAiDays(String.valueOf(pregnantOrAiDays));
                                        cattleBean.setPregnantOrAi(2);
                                        Date registrationDate=GlobalVar.databaseFormat.parse(cattleBean.getRegistrationDate());
                                        Date heatDate=DateUtils.addDays(registrationDate, (int)(-1*pregnantOrAiDays));
                                        cattleBean.setPregnantHeatDate(GlobalVar.databaseFormat.format(heatDate));
                                    }
                                }
                            }
                        }




                        if (pregnantAiSire.getTag() == null || pregnantAiSire.getText().toString().equals(resources.getString(R.string.registration_pregnant_sire))) {
                            if (Integer.parseInt(cattleBean.getSpeciesId()) == 1) {
                                cattleBean.setPregnantSire("999999C");
                            } else {
                                cattleBean.setPregnantSire("999998B");
                            }
                        } else {
                            cattleBean.setPregnantSire(pregnantAiSire.getTag().toString());
                        }
                        if (pregnantAiInsim.getTag() == null || pregnantAiInsim.getText().toString().equals(resources.getString(R.string.registration_pregnant_insim))) {
                            cattleBean.setPregnantInsim("9999999");
                        } else {
                            cattleBean.setPregnantInsim(pregnantAiInsim.getTag().toString());
                        }
                        if (heatSequence.getText().toString().isEmpty()) {
                            cattleBean.setHeatSequence("1");
                        } else {
                            cattleBean.setHeatSequence(heatSequence.getText().toString());
                        }
                    }


                    final String[] Production = new String[1];
                    final String[] Reproduction = new String[1];




                    if(FLAGS.equalsIgnoreCase("1")) {

                        if (!averageMilkYield.getText().toString().equalsIgnoreCase("")) {
                            int days = Integer.parseInt(milkingDaysEditText.getText().toString());
                            int k = days * Integer.parseInt(averageMilkYield.getText().toString());

                            totalMilkYield.setText(k + "");


                        }
                        if (!totalMilkYield.getText().toString().equalsIgnoreCase("")) {
                            int days = Integer.parseInt(milkingDaysEditText.getText().toString());
                            int k = days * Integer.parseInt(totalMilkYield.getText().toString());
                            averageMilkYield.setText(k + "");

                            //   AverageMilkYieldString = k+"";
                        }

                    }else{

                        if (!averageMilkYield.getText().toString().equalsIgnoreCase("")) {
                            int days = Integer.parseInt(dryDaysEditText.getText().toString());
                            int k = days * Integer.parseInt(averageMilkYield.getText().toString());


                            totalMilkYield.setText(k + "");


                        }
                        if (!totalMilkYield.getText().toString().equalsIgnoreCase("")) {
                            int days = Integer.parseInt(dryDaysEditText.getText().toString());
                            int k = days * Integer.parseInt(totalMilkYield.getText().toString());
                            averageMilkYield.setText(k + "");

                            //   AverageMilkYieldString = k+"";
                        }
                        milkingDaysEditText.setText(dryDateEditText.getText().toString());

                    }



                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // DatabaseHelper.getDatabaseHelperInstance(getActivity()).saveRegistration(finalCattleBean);
                            DatabaseHelper.getDatabaseHelperInstance(getActivity()).Insert_Milking_production(preferences.getString("IDSOFANIMAL",""), CommonData.getInstance().getDefaultDate(),noOfCalvingDays.getText().toString(), averageMilkYield.getText().toString(),averageMilkYield.getText().toString(), totalMilkYield.getText().toString(),milkingDaysEditText.getText().toString(),manager.getPrefData("UserCode"));
                        }
                    }, 100);



                    final CattleBean finalCattleBean = cattleBean;
                    final CattleBean finalCattleBean1 = cattleBean;


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {




                            DatabaseHelper.getDatabaseHelperInstance(getActivity()).saveRegistration(finalCattleBean);
                            String  Id =   finalCattleBean1.getCattleBeanInstance().getAnimalId();

                            Log.e("Id s",Id);

                            DatabaseHelper.getDatabaseHelperInstance(getActivity()).save_Insurance_details(Id,"","","","","","","","","","","");

                            DatabaseHelper.getDatabaseHelperInstance(getActivity()).Save_Purchase(Id,"","","");

                            DatabaseHelper.getDatabaseHelperInstance(getActivity()).addOtherDetails(Id,"","","","","","");

                            DatabaseHelper.getDatabaseHelperInstance(getActivity()).save_parent_detail(Id,"","","","");



                        }
                    }, 500);












                    final String[] Details = new String[1];




                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Details[0] = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getDetailss(manager.getPrefData("UserCode"));

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

                                // ConnectionDetector detector = new ConnectionDetector(getActivity());


                                Send_data(Links.SERVER_PASS_DATA,params1);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }, 4000);

                    Toast.makeText(getActivity(),"Save Successfully",Toast.LENGTH_LONG).show();
                    getActivity().finish();


                    Intent intent = new Intent(getActivity(), SelectCategoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);



                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });






        milkingDateEdiText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int daysinMilk = 0;

                if(!milkingDateEdiText.getText().toString().equals("")){

                    String date[] = milkingDateEdiText.getText().toString().split(" ");
                    String datefinal = date[0];
                    String currentdate = CommonData.getInstance().getDefaultDate();
                    SimpleDateFormat simpleDateFormat =
                            new SimpleDateFormat("MM-dd-yyyy");


                    try {
                        Date date1 = simpleDateFormat.parse(datefinal);
                        Date date2 = simpleDateFormat.parse(currentdate);
                        String difference = printDifference(date1,date2);
                        daysinMilk = Integer.parseInt(difference);
                        milkingDaysEditText.setText(difference);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });






        totalMilkYield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int daysinMilk = 0;



                 Log.e("totalMilkYield",s+"");


            }

            @Override
            public void afterTextChanged(Editable s) {

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

        milkingDryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.milking) {
                    FLAGS = "1";
                    milkingGroupLayout.setVisibility(View.VISIBLE);
                    dryGroupLayout.setVisibility(View.GONE);
                } else if (checkedId == R.id.dry) {

                    FLAGS = "2";

                    dryGroupLayout.setVisibility(View.VISIBLE);
                    milkingGroupLayout.setVisibility(View.GONE);
                }
            }

        });
        pregnantAIGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.yes) {
                    pregnantGroupLayout.setVisibility(View.VISIBLE);
                    aiGroupLayout.setVisibility(View.GONE);
                } else if (checkedId == R.id.no) {
                    pregnantGroupLayout.setVisibility(View.GONE);
                    aiGroupLayout.setVisibility(View.VISIBLE);
                }
            }

        });

        milkingDateEdiText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener1)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();
                }
            }
        });

        dryDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener2)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();
                }
            }
        });

        pregnantDateEdiText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener3)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();
                }
            }
        });
        aiDateEdiText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener4)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();
                }
            }
        });

        milkingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.milkingDate) {
                    milkingDateEdiText.setEnabled(true);
                    milkingDaysEditText.setEnabled(false);
                } else if (checkedId == R.id.milkingDays) {
                    milkingDateEdiText.setEnabled(false);
                    milkingDaysEditText.setEnabled(true);
                }
            }
        });


        dryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dryDate) {
                    dryDateEditText.setEnabled(true);
                    dryDaysEditText.setEnabled(false);
                } else if (checkedId == R.id.dryDays) {
                    dryDateEditText.setEnabled(false);
                    dryDaysEditText.setEnabled(true);
                }
            }

        });



        pregnantGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.pregnantDate) {
                    pregnantDateEdiText.setEnabled(true);
                    pregnantDaysEditText.setEnabled(false);
                } else if (checkedId == R.id.pregnantDays) {
                    pregnantDateEdiText.setEnabled(false);
                    pregnantDaysEditText.setEnabled(true);
                }
            }

        });

        aiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.aiDate) {
                    aiDateEdiText.setEnabled(true);
                    aiDaysEditText.setEnabled(false);
                } else if (checkedId == R.id.aiDays) {
                    aiDateEdiText.setEnabled(false);
                    aiDaysEditText.setEnabled(true);
                }
            }
        });

        totalAndAvgMilkingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.totalMilkYieldRadio) {
                    totalMilkYield.setEnabled(true);
                    averageMilkYield.setEnabled(false);
                } else if (checkedId == R.id.averageMilkYieldRadio) {
                    totalMilkYield.setEnabled(false);
                    averageMilkYield.setEnabled(true);
                }
            }
        });
    }



    public String  printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();



        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        return elapsedDays+"";


    }





    public long calculateDateDifference(String firstDate, String secondDate) {
        Date d1 = null;
        Date d2 = null;
        long diffDays = 0;
        try {
            d1 = GlobalVar.databaseFormat.parse(secondDate);
            d2 = GlobalVar.databaseFormat.parse(firstDate);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffDays;
    }

    private Dialog showSireTagList() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);

        final ArrayList<String> lotList;

        if(flag){
            lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSireEarTagListHardCoded(((VillageMainActivity) getActivity()).getCattleBean().getAnimalId());
        }else{
            lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSireEarTagListHardCoded(((AnimalRegistration) getActivity()).getCattleBean().getAnimalId());
        }

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Sire Tag");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                milkingDrySireEarTagNo.setText(((TextView) view).getText());
                milkingDrySireEarTagNo.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showInseminator() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getInseminator();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Sire Tag");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                milkingDryInseminator.setText(((TextView) view).getText());
                milkingDryInseminator.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSireTagList1() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);

        final ArrayList<String> lotList;

        if(flag){
             lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSireEarTagListHardCoded(((VillageMainActivity) getActivity()).getCattleBean().getAnimalId());
        }
        else{
            lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSireEarTagListHardCoded(((AnimalRegistration) getActivity()).getCattleBean().getAnimalId());
        }

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Sire Tag");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pregnantAiSire.setText(((TextView) view).getText());
                pregnantAiSire.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }




    private Dialog showInseminator1() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getInseminator();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Sire Tag");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pregnantAiInsim.setText(((TextView) view).getText());
                pregnantAiInsim.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }


    private Dialog showCalfSex(String calvingType) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getCalfSex(calvingType);
        SpinnerAdapter adapter= new SpinnerAdapter(getActivity(),R.layout.spinner_content , lotList);
        title.setText("Select Calf Sex");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calfSex.setText(((TextView) view).getText());
                calfSex.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }


    @Override
    public void getResponse(String response, int flag) throws JSONException {

        Log.e("Sync",response);



    }



    @Override
    public void failureResponse(int statusCode) throws JSONException {

    }




    public void Send_data(String links,RequestParams params){

          AsyncHttpClient client = new AsyncHttpClient();
          client.setTimeout(200000);
          client.addHeader("content-Type", "application/x-www-form-urlencoded");
          client.post(links, params, new AsyncHttpResponseHandler() {
              @Override
              public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


              String response = "";
              try {
                  response = new String(responseBody, "UTF-8");
                  final String finalResponse = response;


                 // DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Reproduction","1","SyncStatus");

                  DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Reproduction","1","SyncStatus");



                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Production","1","SyncStatus");

                          //  Log.e("Message Reproduction", Reproduction[0]);
                      }
                  }, 500);

                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                         // DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Production","1","SyncStatus");

                          DatabaseHelper.getDatabaseHelperInstance(getActivity()).Update_Sync_Flag("Details","1","SyncStatus");

                          //  Log.e("Message Reproduction", Reproduction[0]);
                      }
                  }, 1000);

                  Log.e("Success response", finalResponse);


                //  DatabaseHelper.getDatabaseHelperInstance(getApplicationContext()).Update_Sync_Flag("Details","1","SyncStatus");


              } catch (UnsupportedEncodingException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }

          }

          @Override
          public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

           //   Log.e("Error",error.getMessage());



          }



      });






  }
























}
