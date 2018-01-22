package provab.herdman.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import provab.herdman.R;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.adapter.SpinnerAdapterString;
import provab.herdman.beans.ReportByBean;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by Swati on 29/08/16.
 */
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener{

    TextView toolbar_title,alarm_text,report_text,filter_text,value_text,route_text,hmb_text,farmer_text;
    LinearLayout report_by_layout,filter_layout,value_layout,route_layout,hmb_layout,farmer_layout;
    Button apply;
    ArrayList<String> Reported_by_array=new ArrayList<>();
    String herd_number,lot_number,name;
    Dialog show_farmer_dialog,show_hmb_dialog;
    ArrayList<ReportByBean> AnimalList=new ArrayList<ReportByBean>();
    ArrayList<String> HMBList=new ArrayList<String>();
    ArrayList<String> FarmerList=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        initialiser();
    }

    private void initialiser() {
        toolbar_title=(TextView) findViewById(R.id.title_Ctv);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText("Alarm");

        Bundle b=getIntent().getExtras();
        if(b!=null){
            herd_number=b.getString("herd_number");
            lot_number=b.getString("lot_number");
            name=b.getString("name");
            Log.e("Printing Data", "herd_number"+herd_number+" lot_number"+lot_number+" name"+name);
        }

        report_text=(TextView) findViewById(R.id.report_text);
        filter_text=(TextView) findViewById(R.id.filter_text);
        value_text=(TextView) findViewById(R.id.value_text);
        route_text=(TextView) findViewById(R.id.route_text);
        hmb_text=(TextView) findViewById(R.id.hmb_text);
        farmer_text=(TextView) findViewById(R.id.farmer_text);

        report_by_layout=(LinearLayout) findViewById(R.id.report_by_layout);
        filter_layout=(LinearLayout) findViewById(R.id.filter_layout);
        value_layout=(LinearLayout) findViewById(R.id.value_layout);
        route_layout=(LinearLayout) findViewById(R.id.route_layout);
        hmb_layout=(LinearLayout) findViewById(R.id.hmb_layout);
        farmer_layout=(LinearLayout) findViewById(R.id.farmer_layout);

        report_by_layout.setOnClickListener(this);
        filter_layout.setOnClickListener(this);
        value_layout.setOnClickListener(this);
        route_layout.setOnClickListener(this);
        hmb_layout.setOnClickListener(this);
        farmer_layout.setOnClickListener(this);

        apply=(Button) findViewById(R.id.apply);
        apply.setOnClickListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_by_layout:

                Dialog dialogShowLot = showSelectDialog();
                dialogShowLot.show();

                break;

            case R.id.route_layout:

                Dialog show_herd_dialog = showHerds();
                show_herd_dialog.show();

                break;

            case R.id.hmb_layout:

                if(!report_text.getText().toString().equalsIgnoreCase("Select Report By")) {
                    show_hmb_dialog.show();
                }else{
                    Toast.makeText(AlarmActivity.this, "Please select Report By.", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.farmer_layout:
                if(!report_text.getText().toString().equalsIgnoreCase("Select Report By")) {
                show_farmer_dialog.show();
                }else{
                    Toast.makeText(AlarmActivity.this, "Please select Report By.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    private Dialog showHMB(final ArrayList<String> hmb_array) {
        final Dialog dialog = new Dialog(AlarmActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        SpinnerAdapterString adapter = new SpinnerAdapterString(AlarmActivity.this, R.layout.spinner_content, hmb_array);
        title.setText("Select HMB");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hmb_text.setText(hmb_array.get(position));
               // hmb_text.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSelectDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);

        final ArrayList<String> optionsList= new ArrayList<>();
        optionsList.add("Open Animals");
        optionsList.add("Open Unbred");
        optionsList.add("Open Bred");
        optionsList.add("Service Period");
        optionsList.add("Heat Interval");
        optionsList.add("No of AI");
        optionsList.add("Milk Yield");
        optionsList.add("Average Yield");
        optionsList.add("Peak Yield");

        System.out.println("LOT ARRAY LIST COUNT = "+optionsList.size());
        SpinnerAdapterString adapter= new SpinnerAdapterString(this,R.layout.spinner_content , optionsList);
        title.setText("Select Option");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                report_text.setText(((TextView) view).getText());
                dialog.dismiss();

                if(position==0){
                    AnimalList.clear();
                    HMBList.clear();
                    FarmerList.clear();
                    AnimalList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).OpenAnimal();
                       for(int i=0; i<AnimalList.size();i++){
                           Log.e("From database11",AnimalList.get(i).getFarmer()+String.valueOf(i));
                           HMBList.add(AnimalList.get(i).getHMB());
                           FarmerList.add(AnimalList.get(i).getFarmer());
                        }
                    show_hmb_dialog = showHMB(HMBList);
                    show_farmer_dialog = showFarmer(FarmerList);
                }
                else if(position==1){
                    AnimalList.clear();
                    HMBList.clear();
                    FarmerList.clear();
                    AnimalList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).OpenAnimal();
                    for(int i=0; i<AnimalList.size();i++){
                        Log.e("From database11",AnimalList.get(i).getFarmer()+String.valueOf(i));
                        HMBList.add(AnimalList.get(i).getHMB());
                        FarmerList.add(AnimalList.get(i).getFarmer());
                    }
                    show_hmb_dialog = showHMB(HMBList);
                    show_farmer_dialog = showFarmer(FarmerList);
                }
                else if(position==2){
                    AnimalList.clear();
                    HMBList.clear();
                    FarmerList.clear();
                    AnimalList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).OpenAnimal();
                    for(int i=0; i<AnimalList.size();i++){
                        Log.e("From database11",AnimalList.get(i).getFarmer()+String.valueOf(i));
                        HMBList.add(AnimalList.get(i).getHMB());
                        FarmerList.add(AnimalList.get(i).getFarmer());
                    }
                    show_hmb_dialog = showHMB(HMBList);
                    show_farmer_dialog = showFarmer(FarmerList);
                }
                else if(position==3){
                    AnimalList.clear();
                    HMBList.clear();
                    FarmerList.clear();

                    AnimalList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).DryPeriod();
                    for(int i=0; i<AnimalList.size();i++){
                        Log.e("From database11",AnimalList.get(i).getFarmer()+String.valueOf(i));
                        HMBList.add(AnimalList.get(i).getHMB());
                        FarmerList.add(AnimalList.get(i).getFarmer());
                    }
                    show_hmb_dialog = showHMB(HMBList);
                    show_farmer_dialog = showFarmer(FarmerList);

                }
                else if(position==4){

                }
                else if(position==5){
                    AnimalList.clear();
                    HMBList.clear();
                    FarmerList.clear();

                    AnimalList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).NoOfAI();
                    for(int i=0; i<AnimalList.size();i++){
                        Log.e("From database11",AnimalList.get(i).getFarmer()+String.valueOf(i));
                        HMBList.add(AnimalList.get(i).getHMB());
                        FarmerList.add(AnimalList.get(i).getFarmer());
                    }
                    show_hmb_dialog = showHMB(HMBList);
                    show_farmer_dialog = showFarmer(FarmerList);
                }
                else if(position==6){

                }
                else if(position==7){
                    AnimalList.clear();
                    HMBList.clear();
                    FarmerList.clear();

                    AnimalList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).AverageYield();
                    for(int i=0; i<AnimalList.size();i++){
                        Log.e("From database11",AnimalList.get(i).getFarmer()+String.valueOf(i));
                        HMBList.add(AnimalList.get(i).getHMB());
                        FarmerList.add(AnimalList.get(i).getFarmer());
                    }
                    show_hmb_dialog = showHMB(HMBList);
                    show_farmer_dialog = showFarmer(FarmerList);

                }
                else if(position==8){

                }
            }
        });
        return dialog;
    }
    private Dialog showHerds() {
        final Dialog dialog = new Dialog(AlarmActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(AlarmActivity.this).getHerds();
        SpinnerAdapter adapter = new SpinnerAdapter(AlarmActivity.this, R.layout.spinner_content, lotList);
        title.setText("Select Herd");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                route_text.setText(((TextView) view).getText());
                route_text.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }
    private Dialog showFarmer(final ArrayList<String> farmer_array) {
        final Dialog dialog = new Dialog(AlarmActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        SpinnerAdapterString adapter = new SpinnerAdapterString(AlarmActivity.this, R.layout.spinner_content, farmer_array);
        title.setText("Select Lot");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                farmer_text.setText(farmer_array.get(position));
                // hmb_text.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
