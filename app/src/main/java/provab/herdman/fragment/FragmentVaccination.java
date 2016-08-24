package provab.herdman.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import provab.herdman.R;
import provab.herdman.activity.MyApplication;
import provab.herdman.adapter.GridDiseaseAdapter;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.beans.DiseaseBean;
import provab.herdman.constants.CommonData;
import provab.herdman.constants.GlobalVar;
import provab.herdman.enume.SubStatus;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class FragmentVaccination extends Fragment {

    LinearLayout villageLayout;
    LinearLayout vaccinDateLayout;
    LinearLayout diseaseLayout;
    LinearLayout vaccineBrandLayout;
    LinearLayout routeLayout;
    TextView village;
    TextView vaccinDate;
    TextView disease;
    TextView vaccineBrand;
    TextView route;
    EditText batchNumber;
    TextView OwnerName;
    TextView IdNo;
    LinearLayout inseminator;
    TextView insem_text;
    String Code ;




    EditText dose;
    Button vaccineSubmit;
    Button vaccineCancel;
    ImageView animal_status;
    TextView ownerId,bms;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_vaccination, container, false);
        findViews(v);
        return v;
    }


    public void findViews(View view){


            villageLayout=(LinearLayout)view.findViewById(R.id.villageLayout);
            vaccinDateLayout=(LinearLayout)view.findViewById(R.id.vaccinDateLayout);
            diseaseLayout=(LinearLayout)view.findViewById(R.id.diseaseLayout);
            vaccineBrandLayout=(LinearLayout)view.findViewById(R.id.vaccineBrandLayout);
            routeLayout=(LinearLayout)view.findViewById(R.id.routeLayout);
            batchNumber=(EditText)view.findViewById(R.id.batchNumber);
            village=(TextView)view.findViewById(R.id.village);
            vaccinDate=(TextView)view.findViewById(R.id.vaccineDate);
            disease=(TextView)view.findViewById(R.id.disease);
            dose = (EditText)view.findViewById(R.id.dose);
             vaccineSubmit = (Button)view.findViewById(R.id.vaccineSubmit) ;
            bms = (TextView)view.findViewById(R.id.bmscode);

            vaccineBrand=(TextView)view.findViewById(R.id.vaccineBrand);
            animal_status = (ImageView)view.findViewById(R.id.animal_status);
            OwnerName = (TextView)view.findViewById(R.id.ownersName);
            IdNo = (TextView)view.findViewById(R.id.idNumber);
            inseminator = (LinearLayout)view.findViewById(R.id.inseminatorLayout);
            insem_text = (TextView)view.findViewById(R.id.inseminator);





            ownerId = (TextView)view.findViewById(R.id.ownerid);
            vaccinDate.setText(CommonData.getInstance().getDefaultDate());

            ownerId.setText(GlobalVar.OWNERS_CODE);
            bms.setText(GlobalVar.VILLAGE_CODE);




                route=(TextView)view.findViewById(R.id.route);


                inseminator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogShowInseminator = showInseminator();
                        dialogShowInseminator.show();



                    }
                });

             vaccineSubmit.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     String Multiple_number =  IdNo.getText().toString();

                     String [] arr = Multiple_number.split(",");



                    for(int i=0;i<arr.length;i++){

                        DatabaseHelper.getDatabaseHelperInstance(getActivity()).Insert_Vaccination(arr[i],vaccinDate.getText().toString(),disease.getText().toString(),vaccineBrand.getText().toString(),batchNumber.getText().toString(),dose.getText().toString(),"",route.getText().toString(),insem_text.getText().toString());

                    }


                     Toast.makeText(getActivity(),"Saved Successfully",Toast.LENGTH_LONG).show();





                 }
             });




















              vaccinDateLayout.setOnClickListener(new View.OnClickListener() {
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





                    diseaseLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogShowDisease = showDisease();
                        dialogShowDisease.show();
                    }
                });
                vaccineBrandLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (disease.getText().toString().equals(getActivity().getResources().getString(R.string.vaccination_select_disease))) {
                            Toast.makeText(getActivity(), "PLEASE SELECT DISEASE", Toast.LENGTH_LONG).show();
                        } else {
                            Dialog dialogShowDisease = showVaccination();
                            dialogShowDisease.show();
                        }
                    }
                });
                routeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogShowDisease = showRoute();
                        dialogShowDisease.show();
                    }
                });

                villageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogShowLot=showSelectLotDialog();
                        dialogShowLot.show();
                    }
                });


                 OwnerName.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {


                         showSelectOwnerDialog().show();


                     }
                 });

        IdNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showSelectIDDialog().show();
                Multiselect();

            }
        });



          animal_status.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  ShowDiseas diseas = new ShowDiseas();
                  diseas.show(getActivity().getSupportFragmentManager(),"");




              }
          });

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
                insem_text.setText(((TextView) view).getText());
                inseminator.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }












    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {

            vaccinDate.setText(GlobalVar.dialogeFormat.format(date));

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };



    private Dialog showSelectOwnerDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getCodeAndNameFromOwner(GlobalVar.VILLAGE_CODE);
        System.out.println("OWNER ARRAY LIST COUNT = "+lotList.size());
        SpinnerAdapter adapter= new SpinnerAdapter(getActivity(),R.layout.spinner_content , lotList);
        title.setText("Select Id");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobalVar.OWNERS_CODE=view.getTag().toString();
                /*   ownerInfoSpinner.setText(((TextView) view).getText());
                ownerInfoSpinner.setTag(view.getTag().toString());
                GlobalVar.OWNERS_CODE=view.getTag().toString();
                GlobalVar.OWNERS_NAME=((TextView) view).getText().toString();*/
                dialog.dismiss();
            }
        });
        return dialog;
    }






    public void Multiselect(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getIdFromDetails(GlobalVar.VILLAGE_CODE,GlobalVar.OWNERS_CODE);
    final    ArrayList<String> catch_data = new ArrayList<String>();
        final String[] colors = new String[lotList.size()];
        final   boolean[] checkedColors = new boolean[lotList.size()];
        for (int i = 0; i < lotList.size(); i++) {
            colors[i] = lotList.get(i);
        }
        for (int i = 0; i < lotList.size(); i++) {
            checkedColors[i] = false;
        }
        final List<String> colorsList = Arrays.asList(colors);
        builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedColors[which] = isChecked;
                String currentItem = colorsList.get(which);
            }
        });
        builder.setCancelable(false);
        builder.setTitle("Select the Task");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i<checkedColors.length; i++){
                    boolean checked = checkedColors[i];
                    if (checked) {
                        catch_data.add(colorsList.get(i));
                    }
                }
                String data = TextUtils.join(",",catch_data);

                IdNo.setText(data);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }










    private Dialog showAnimalStatus() {

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diseasedisplay);
        GridView grid = (GridView)dialog.findViewById(R.id.disease_grid);

        final ArrayList<DiseaseBean> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).Read_Data_vaccine();

        GridDiseaseAdapter Adapter = new GridDiseaseAdapter(getActivity(),lotList);
        grid.setAdapter(Adapter);





        return dialog;
    }




    public void createTable(ArrayList<String> rows,TableLayout tableLayout) {
        try {
            for (int i = 0; i < rows.size(); i++) {
                JSONObject rowContent = new JSONObject(rows.get(i));
                TableRow tr_head = new TableRow(getActivity());
                tr_head.setBackgroundColor(Color.GRAY);        // part1
                tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
                if(i==0){
                    addHeaders(rowContent,tr_head);
                    tableLayout.addView(tr_head, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                    tr_head = new TableRow(getActivity());
                }
                Iterator iterator=rowContent.keys();
                while (iterator.hasNext()){
                    String key=iterator.next().toString();
                    TextView columnContent = new TextView(getActivity());
                    columnContent.setText(rowContent.getString(key));
                    columnContent.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    columnContent.setPadding(5, 5, 5, 5);
                    columnContent.setTextColor(Color.WHITE);
                    columnContent.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    tr_head.addView(columnContent);
                }

                tableLayout.addView(tr_head, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void addHeaders(JSONObject obj,TableRow tr_head){
        Iterator iterator=obj.keys();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            TextView columnContent = new TextView(getActivity());
            columnContent.setText(key);
            columnContent.setTextColor(Color.BLACK);
            columnContent.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            columnContent.setPadding(5, 5, 5, 5);
            tr_head.addView(columnContent);
        }
    }

    private Dialog showDisease() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList= DatabaseHelper.getDatabaseHelperInstance(getActivity()).getDisease();
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select ID");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                disease.setText(lotList.get(position));
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showRoute() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getRoute();
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Route");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                route.setText(lotList.get(position));
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showVaccination() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getnameCodeMedicineLedger(disease.getText().toString());

        Log.e("Code",lotList.toString());


        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Vaccination");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vaccineBrand.setText(((TextView) view).getText());
                vaccineBrand.setTag(view.getTag().toString());

                try {

                    JSONObject object = new JSONObject(lotList.get(position));
                    DatabaseHelper.getDatabaseHelperInstance(getActivity()).getDose(object.getString(vaccineBrand.getText().toString()));
                    dose.setText(DatabaseHelper.getDatabaseHelperInstance(getActivity()).getDose(object.getString(vaccineBrand.getText().toString())));


                } catch (JSONException e) {
                    e.printStackTrace();
                }














                dialog.dismiss();
            }
        });
        return dialog;
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
                village.setText(((TextView) view).getText());
                village.setTag(view.getTag().toString());
                GlobalVar.VILLAGE_CODE=view.getTag().toString();
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
