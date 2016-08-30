package provab.herdman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.activity.VillageMainActivity;
import provab.herdman.beans.CattleBean;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 6/14/2016.
 */
public class ParentDetailsFragment extends Fragment {

    Button nextButton,pre;
    EditText sire ,dam, paternalsire,paternaldam;
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
        View v=inflater.inflate(R.layout.fragment_parent_details, container, false);
        Id =   bean.getCattleBeanInstance().getAnimalId();
        findViews(v);
        return v;
    }


    public void findViews(View view){

        nextButton = (Button) view.findViewById(R.id.nex);
        sire = (EditText)view.findViewById(R.id.sire) ;
        dam = (EditText)view.findViewById(R.id.dam);
        paternalsire = (EditText) view.findViewById(R.id.psire);
        paternaldam = (EditText)view.findViewById(R.id.pdam);
        pre = (Button)view.findViewById(R.id.pre);
        pre.setOnClickListener(new View.OnClickListener() {
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

     //   []	integer,


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 DatabaseHelper.getDatabaseHelperInstance(getActivity()).save_parent_detail(Id,sire.getText().toString(),dam.getText().toString(),paternalsire.getText().toString(),paternaldam.getText().toString());



                 Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                if(flag){
                    ((VillageMainActivity) getActivity()).swipeViewPagerToNextScreen();
                }
                else{
                    ((AnimalRegistration) getActivity()).swipeViewPagerToNextScreen();
                }
            }
        });
    }

}
