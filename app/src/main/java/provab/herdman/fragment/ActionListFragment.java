package provab.herdman.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import provab.herdman.R;
import provab.herdman.activity.AnimalMainActivity;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.beans.ActionBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/19/2016.
 */
public class ActionListFragment extends Fragment implements View.OnClickListener{
    Button loginButton;
    LinearLayout lotInfoLayout;
    LinearLayout ownerInfoLayout;
    LinearLayout detailsInfoLayout;
    TextView lotInfoSpinner;
    TextView ownerInfoSpinner;
    TextView detailsInfoSpinner;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_action_list, container, false);
       // findViews(v);

        loginButton = (Button)v. findViewById(R.id.loginButton);
        lotInfoLayout = (LinearLayout) v.findViewById(R.id.lotInfoLayout);
        ownerInfoLayout = (LinearLayout)v. findViewById(R.id.ownerInfoLayout);
        detailsInfoLayout = (LinearLayout)v. findViewById(R.id.detailsInfoLayout);
        lotInfoSpinner = (TextView) v.findViewById(R.id.lotInfoSpinner);
        ownerInfoSpinner = (TextView)v. findViewById(R.id.ownerInfoSpinner);
        detailsInfoSpinner = (TextView) v.findViewById(R.id.detailsInfoSpinner);

        lotInfoLayout.setOnClickListener(this);
        ownerInfoLayout.setOnClickListener(this);
        detailsInfoLayout.setOnClickListener(this);
        loginButton.setOnClickListener(this);

      //  DatabaseHelper.getDatabaseHelperInstance(getActivity()).create_table();

        System.out.println("Lot Array "+DatabaseHelper.getDatabaseHelperInstance(getActivity()).get_Task().size());


        return v;
    }



    private Dialog showSelectLotDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getLotNumberAndName();
        System.out.println("LOT ARRAY LIST COUNT = "+lotList.size());
        SpinnerAdapter adapter= new SpinnerAdapter(getActivity(),R.layout.spinner_content , lotList);
        title.setText("Select Lot");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lotInfoSpinner.setText(((TextView) view).getText());
                lotInfoSpinner.setTag(view.getTag().toString());
                GlobalVar.VILLAGE_CODE=view.getTag().toString();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSelectOwnerDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getCodeAndNameFromOwner(lotInfoSpinner.getTag().toString());
        System.out.println("OWNER ARRAY LIST COUNT = "+lotList.size());
        SpinnerAdapter adapter= new SpinnerAdapter(getActivity(),R.layout.spinner_content , lotList);
        title.setText("Select Owner");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ownerInfoSpinner.setText(((TextView) view).getText());
                ownerInfoSpinner.setTag(view.getTag().toString());
                GlobalVar.OWNERS_CODE=view.getTag().toString();
                GlobalVar.OWNERS_NAME=((TextView) view).getText().toString();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSelectIDDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).get_Task();
        System.out.println("Lot Array "+lotList.size());

        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
      //  final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getIdFromDetails(lotInfoSpinner.getTag().toString(),ownerInfoSpinner.getTag().toString());
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Task");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailsInfoSpinner.setText(lotList.get(position));
                GlobalVar.ID_NUMBER=lotList.get(position);
                dialog.dismiss();
            }
        });
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:

                ArrayList<ActionBean> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getTask_details(GlobalVar.OWNERS_CODE,GlobalVar.VILLAGE_CODE);

                 System.out.println("Responce"+lotList.size());

                AnimalMainActivity activity = (AnimalMainActivity)getActivity();
                activity.replaceFragment(new ActionListDetails(lotList));


                break;
            case R.id.lotInfoLayout:
                Dialog dialogShowLot = showSelectLotDialog();
                dialogShowLot.show();
                break;
            case R.id.ownerInfoLayout:
                if(lotInfoSpinner.getTag()!=null){
                    Dialog dialogShowOwner = showSelectOwnerDialog();
                    dialogShowOwner.show();
                }else{
                   // Toast.makeText(AnimalDetailsLogin.this, "PLEASE SELECT LOT BEFORE SELECTING OWNER", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detailsInfoLayout:
                if(ownerInfoSpinner.getTag()!=null) {
                    Dialog dialogShowDetails = showSelectIDDialog();
                    dialogShowDetails.show();
                    System.out.println("SEE THE PARAMETERS FOR ID FETCH ="+lotInfoSpinner.getTag().toString()+"  "+ownerInfoSpinner.getTag().toString());
                }else{
                 //   Toast.makeText(AnimalDetailsLogin.this, "PLEASE SELECT OWNER BEFORE SELECTING ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
