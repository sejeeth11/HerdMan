package provab.herdman.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.beans.CattleBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;


/**
 * Created by PTBLR-1057 on 6/14/2016.
 */

public class InsuranceDetailsFragment extends Fragment {

    Button nextButton,prevButton;
    EditText masterpolicyno,Tansactiondetails,HypoNo,agentname,insurancecompany,policyperiod,policyamount,premiumamount,issuedate;
    CattleBean bean;
    String Id;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


   /* (Idno,PolicyNo,Value,CompanyName,IssueDate,DueDate, AgentName,
    ClaimDate,Reason,Status,SettlementDate,AmtRecd)*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_insurance_duplicate, container, false);
        findViews(v);
        return v;
    }

    public void findViews(View view){

        nextButton = (Button) view.findViewById(R.id.nextButton);
        prevButton = (Button)view.findViewById(R.id.prevButton);
        masterpolicyno = (EditText)view.findViewById(R.id.masterpolicyno);
        Tansactiondetails = (EditText)view.findViewById(R.id.tansaction_details);
        HypoNo = (EditText)view.findViewById(R.id.hypo);
        agentname = (EditText)view.findViewById(R.id.agent_name);
        insurancecompany = (EditText)view.findViewById(R.id.insurance_company);
        policyperiod = (EditText)view.findViewById(R.id.policy_period);
        policyamount = (EditText)view.findViewById(R.id.policy_amount);
        premiumamount = (EditText)view.findViewById(R.id.premium_amount);
        issuedate = (EditText)view.findViewById(R.id.issue_date);

        Id =   bean.getCattleBeanInstance().getAnimalId();




       issuedate.setOnClickListener(new View.OnClickListener() {
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





        nextButton.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.getDatabaseHelperInstance(getActivity()).save_Insurance_details(Id,masterpolicyno.getText().toString(),"",insurancecompany.getText().toString(),issuedate.getText().toString(),"","","","","","","");
                ((AnimalRegistration)getActivity()).swipeViewPagerToNextScreen();
            }
        });

    prevButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((AnimalRegistration)getActivity()).swipeViewPagerToPreviousScreen();
        }
    });













    }
    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {

            issuedate.setText(GlobalVar.dialogeFormat.format(date));

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };
}
