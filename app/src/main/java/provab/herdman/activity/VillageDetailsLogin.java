package provab.herdman.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/19/2016.
 */
public class VillageDetailsLogin   extends AppCompatActivity implements View.OnClickListener {
    Button loginButton;
    LinearLayout villageLayout;
    TextView village;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_details_login);
        loginButton=(Button) findViewById(R.id.loginButton);
        village=(TextView) findViewById(R.id.village);
        villageLayout=(LinearLayout) findViewById(R.id.villageLayout);
        loginButton.setOnClickListener(this);
        villageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowLot = showSelectLotDialog();
                dialogShowLot.show();
            }
        });
    }

    public void findViews(View view){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                Intent intent=new Intent(this,VillageMainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private Dialog showSelectLotDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList= DatabaseHelper.getDatabaseHelperInstance(this).getLotNumberAndName();
        System.out.println("LOT ARRAY LIST COUNT = "+lotList.size());
        SpinnerAdapter adapter= new SpinnerAdapter(this,R.layout.spinner_content , lotList);
        title.setText("Select Lot");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                village.setText(((TextView) view).getText());
                village.setTag(view.getTag().toString());
//                GlobalVar.VILLAGE_CODE=view.getTag().toString();
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
