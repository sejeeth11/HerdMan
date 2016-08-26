package provab.herdman.activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/19/2016.
 */
public class VillageActivity extends AppCompatActivity implements View.OnClickListener{


    Button loginButton;
    LinearLayout lotInfoLayout;
    LinearLayout ownerInfoLayout;
    LinearLayout detailsInfoLayout;
    TextView lotInfoSpinner;
    TextView ownerInfoSpinner;
    TextView detailsInfoSpinner;
    String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        lotInfoLayout = (LinearLayout) findViewById(R.id.lotInfoLayout);
        ownerInfoLayout = (LinearLayout) findViewById(R.id.ownerInfoLayout);
        detailsInfoLayout = (LinearLayout) findViewById(R.id.detailsInfoLayout);
        lotInfoSpinner = (TextView) findViewById(R.id.lotInfoSpinner);
        ownerInfoSpinner = (TextView) findViewById(R.id.ownerInfoSpinner);
        detailsInfoSpinner = (TextView) findViewById(R.id.detailsInfoSpinner);

        Intent intent = getIntent();
        flag = intent.getStringExtra("Key");

        lotInfoLayout.setOnClickListener(this);
        ownerInfoLayout.setOnClickListener(this);
        detailsInfoLayout.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    private Dialog showSelectLotDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(this).getLotNumberAndName();
        System.out.println("LOT ARRAY LIST COUNT = "+lotList.size());
        SpinnerAdapter adapter= new SpinnerAdapter(this,R.layout.spinner_content , lotList);
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(this).getCodeAndNameFromOwner(lotInfoSpinner.getTag().toString());
        System.out.println("OWNER ARRAY LIST COUNT = "+lotList.size());
        SpinnerAdapter adapter= new SpinnerAdapter(this,R.layout.spinner_content , lotList);
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(this).getIdFromDetails(lotInfoSpinner.getTag().toString(),ownerInfoSpinner.getTag().toString());
        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.spinner_content, lotList);
        title.setText("Select ID");
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

                Intent intent = new Intent(VillageActivity.this, VillageMainActivity.class);
             
                startActivity(intent);
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
                    Toast.makeText(VillageActivity.this, "PLEASE SELECT LOT BEFORE SELECTING OWNER", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detailsInfoLayout:
                if(ownerInfoSpinner.getTag()!=null) {
                    Dialog dialogShowDetails = showSelectIDDialog();
                    dialogShowDetails.show();
                    System.out.println("SEE THE PARAMETERS FOR ID FETCH ="+lotInfoSpinner.getTag().toString()+"  "+ownerInfoSpinner.getTag().toString());
                }else{
                    Toast.makeText(VillageActivity.this, "PLEASE SELECT OWNER BEFORE SELECTING ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
