package provab.herdman.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import provab.herdman.R;
import provab.herdman.activity.MyApplication;
import provab.herdman.beans.MedicineBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.enume.SubStatus;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class TreatmentFragment extends Fragment {

    TextView ownerId;
    ImageView animal_status;
    LinearLayout Dose1,Dose2,Dose3,Dose4,Dose5;
    EditText Medicinenamedose1,Typedoseone,batchnodoseone,Routedoseone,Dosedoseone,FollowUpdateonedose1,Followupdatetwodose1;
    EditText Medicinenamedose2,Typedosetwo,batchnodosetwo,Routedosetwo,Dosedosetwo,FollowUpdateonedose2,Followupdatetwodose2;
    EditText Medicinenamedose3,Typedosethree,batchnodosethree,Routedosethree,Dosedosethree,FollowUpdateonedose3,Followupdatetwodose3;
    EditText Medicinenamedose4,Typedosefour,batchnodosefour,Routedosefour,Dosedosefour,FollowUpdateonedose4,Followupdatetwodose4;
    EditText Medicinenamedose5,Typedosefive,batchnodosefive,Routedosefive,Dosedosefive,FollowUpdateonedose5,Followupdatetwodose5;


    EditText complain,doctor,systems,diagnosis,temp,pulse,respiration,obersvation,labtest,note,treatmentcost;
    Button Submit;
    ImageView Plus,Minus;
    int i = 1;
    EditText date_treatment;
    int FLAG = 1;
    int MedicineNameFlag = 1;







    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_treatment_entry, container, false);
        findViews(v);
        return v;
    }


    public void findViews(View view){


        ownerId = (TextView)view.findViewById(R.id.idnos);
        Dose1 = (LinearLayout)view.findViewById(R.id.medicineone);
        Dose2 = (LinearLayout)view.findViewById(R.id.medicinetwo);
        Dose3 = (LinearLayout)view.findViewById(R.id.medicinethree);
        Dose4 = (LinearLayout)view.findViewById(R.id.medicinefour);
        Dose5 = (LinearLayout)view.findViewById(R.id.medicinefive);
        Plus = (ImageView)view.findViewById(R.id.plus);
        Minus = (ImageView)view.findViewById(R.id.minus);
        date_treatment = (EditText)view.findViewById(R.id.date_treatement);
        complain = (EditText)view.findViewById(R.id.complaint);
        doctor = (EditText)view.findViewById(R.id.doctor);
        systems = (EditText)view.findViewById(R.id.system);
        diagnosis = (EditText)view.findViewById(R.id.diagnosis);
        pulse = (EditText)view.findViewById(R.id.pulse);
        respiration = (EditText)view.findViewById(R.id.respiration);
        obersvation = (EditText)view.findViewById(R.id.observation);
        labtest = (EditText)view.findViewById(R.id.labtest);
        note = (EditText)view.findViewById(R.id.note);
        treatmentcost = (EditText)view.findViewById(R.id.treatmentcost);
        ownerId.setText(GlobalVar.ID_NUMBER);

        temp = (EditText)view.findViewById(R.id.temp);
        Submit = (Button)view.findViewById(R.id.treatmentSubmit) ;




        Medicinenamedose1 =  (EditText)view.findViewById(R.id.Medicinenamedose1);
        Typedoseone  = (EditText)view.findViewById(R.id.Typedoseone);
        batchnodoseone= (EditText)view.findViewById(R.id.batchnodoseone);
        Routedoseone = (EditText)view.findViewById(R.id.Routedoseone);
        Dosedoseone = (EditText)view.findViewById(R.id.Dosedoseone);
        FollowUpdateonedose1 = (EditText)view.findViewById(R.id.FollowUpdateonedose1);
        Followupdatetwodose1 = (EditText)view.findViewById(R.id.Followupdatetwodose1);


        Medicinenamedose2 =  (EditText)view.findViewById(R.id.Medicinenamedose2);
        Typedosetwo  = (EditText)view.findViewById(R.id.Typedosetwo);
        batchnodosetwo= (EditText)view.findViewById(R.id.batchnodosetwo);
        Routedosetwo = (EditText)view.findViewById(R.id.Routedosetwo);
        Dosedosetwo = (EditText)view.findViewById(R.id.Dosedosetwo);
        FollowUpdateonedose2 = (EditText)view.findViewById(R.id.FollowUpdateonedose2);
        Followupdatetwodose2 = (EditText)view.findViewById(R.id.Followupdatetwodose2);



        Medicinenamedose3 =  (EditText)view.findViewById(R.id.Medicinenamedose3);
        Typedosethree  = (EditText)view.findViewById(R.id.Typedosethree);
        batchnodosethree= (EditText)view.findViewById(R.id.batchnodosethree);
        Routedosethree = (EditText)view.findViewById(R.id.Routedosethree);
        Dosedosethree = (EditText)view.findViewById(R.id.Dosedosethree);
        FollowUpdateonedose3 = (EditText)view.findViewById(R.id.FollowUpdateonedose3);
        Followupdatetwodose3 = (EditText)view.findViewById(R.id.Followupdatetwodose3);



        Medicinenamedose4 =  (EditText)view.findViewById(R.id.Medicinenamedose4);
        Typedosefour  = (EditText)view.findViewById(R.id.Typedosefour);
        batchnodosefour = (EditText)view.findViewById(R.id.batchnodosefour);
        Routedosefour = (EditText)view.findViewById(R.id.Routedosefour);
        Dosedosefour = (EditText)view.findViewById(R.id.Dosedosefour);
        FollowUpdateonedose4 = (EditText)view.findViewById(R.id.FollowUpdateonedose4);
        Followupdatetwodose4 = (EditText)view.findViewById(R.id.Followupdatetwodose4);



        Medicinenamedose5 =  (EditText)view.findViewById(R.id.Medicinenamedose5);
        Typedosefive  = (EditText)view.findViewById(R.id.Typedosefive);
        batchnodosefive = (EditText)view.findViewById(R.id.batchnodosefive);
        Routedosefive = (EditText)view.findViewById(R.id.Routedosefive);
        Dosedosefive = (EditText)view.findViewById(R.id.Dosedosefive);
        FollowUpdateonedose5 = (EditText)view.findViewById(R.id.FollowUpdateonedose5);
        Followupdatetwodose5 = (EditText)view.findViewById(R.id.Followupdatetwodose5);




        Medicinenamedose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineNameFlag = 1;

                medicinetype().show();

            }
        });
        Medicinenamedose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineNameFlag = 2;

                medicinetype().show();

            }
        });
        Medicinenamedose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineNameFlag = 3;

                medicinetype().show();

            }
        });
        Medicinenamedose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineNameFlag = 4;

                medicinetype().show();

            }
        });
        Medicinenamedose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineNameFlag = 5;

                medicinetype().show();

            }
        });


        FollowUpdateonedose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 1;
                call_date_picker();

            }
        });

        Followupdatetwodose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 2;
                call_date_picker();
            }
        });




        FollowUpdateonedose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 4;
                call_date_picker();

            }
        });

        Followupdatetwodose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 5;
                call_date_picker();
            }
        });



        FollowUpdateonedose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 6;
                call_date_picker();

            }
        });

        Followupdatetwodose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 7;
                call_date_picker();
            }
        });




        FollowUpdateonedose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 8;
                call_date_picker();

            }
        });

        Followupdatetwodose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 9;
                call_date_picker();
            }
        });







        FollowUpdateonedose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 10;
                call_date_picker();

            }
        });

        Followupdatetwodose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG = 11;
                call_date_picker();
            }
        });


































        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        date_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FLAG = 3;


                call_date_picker();


            }
        });

        ownerId.setText(GlobalVar.OWNERS_CODE);
        animal_status = (ImageView)view.findViewById(R.id.animal_status);

        animal_status.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Dialog dialogShowAnimalStatus = showAnimalStatus();
             dialogShowAnimalStatus.show();
         }
     });




    Plus.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(i>0){
              if(i==5){

              }else{
                  ++i;

              }


            }



            if(i == 2){

                Dose2.setVisibility(View.VISIBLE);

            }else if(i==3){

                Dose2.setVisibility(View.VISIBLE);
                Dose3.setVisibility(View.VISIBLE);


            }else if(i==4){

                Dose2.setVisibility(View.VISIBLE);
                Dose3.setVisibility(View.VISIBLE);
                Dose4.setVisibility(View.VISIBLE);

            }else if(i==5){

                Dose2.setVisibility(View.VISIBLE);
                Dose3.setVisibility(View.VISIBLE);
                Dose4.setVisibility(View.VISIBLE);
                Dose5.setVisibility(View.VISIBLE);

            }







        }
    });


     Minus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(i>0){
                if(i==0){

                }else{
                    --i;
                }


             }




             if(i == 2){

                 Dose2.setVisibility(View.VISIBLE);
                 Dose3.setVisibility(View.GONE);
                 Dose4.setVisibility(View.GONE);
                 Dose5.setVisibility(View.GONE);

             }else if(i==3){

                 Dose2.setVisibility(View.VISIBLE);
                 Dose3.setVisibility(View.VISIBLE);
                 Dose4.setVisibility(View.GONE);
                 Dose5.setVisibility(View.GONE);

             }else if(i==4){

                 Dose2.setVisibility(View.VISIBLE);
                 Dose3.setVisibility(View.VISIBLE);
                 Dose4.setVisibility(View.VISIBLE);
                 Dose5.setVisibility(View.GONE);

             }else if(i==5){

                 Dose2.setVisibility(View.VISIBLE);
                 Dose3.setVisibility(View.VISIBLE);
                 Dose4.setVisibility(View.VISIBLE);
                 Dose5.setVisibility(View.VISIBLE);

             }else{


                 Dose2.setVisibility(View.GONE);
                 Dose3.setVisibility(View.GONE);
                 Dose4.setVisibility(View.GONE);
                 Dose5.setVisibility(View.GONE);

             }















      //      System.out.println("My minus values  "+i);





         }
     });








    }



    public void call_date_picker(){
        new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .setMaxDate(new Date())
                //.setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                .setIndicatorColor(Color.parseColor("#6c9c48"))
                .build()
                .show();
    }






    private Dialog showsysttems() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSystemdata();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, lotList);



        title.setText("Select Systems");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                dialog.dismiss();
            }
        });
        return dialog;
    }





    private Dialog medicinetype() {

        final ArrayList<String> data = new ArrayList<String>();


        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<MedicineBean> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getmedicinetype();

        for(int i=0;i<lotList.size();i++){
            data.add(lotList.get(i).getName());

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, data);

        title.setText("Select MedicineType");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(MedicineNameFlag == 1){

                    Medicinenamedose1.setText(data.get(position));

                }else if(MedicineNameFlag == 2){
                    Medicinenamedose2.setText(data.get(position));

                }else if(MedicineNameFlag == 3){

                    Medicinenamedose3.setText(data.get(position));
                }else if(MedicineNameFlag == 4){

                    Medicinenamedose4.setText(data.get(position));
                }else if(MedicineNameFlag == 5){
                    Medicinenamedose5.setText(data.get(position));
                }




                dialog.dismiss();
            }
        });
        return dialog;
    }








    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date){
            if(FLAG==1){
                FollowUpdateonedose1.setText(GlobalVar.dialogeFormat.format(date));
            }else if(FLAG == 2){
                Followupdatetwodose1.setText(GlobalVar.dialogeFormat.format(date));
            }else if(FLAG == 4){
                FollowUpdateonedose2.setText(GlobalVar.dialogeFormat.format(date));
            }else if(FLAG == 5){
                Followupdatetwodose2.setText(GlobalVar.dialogeFormat.format(date));
            }else if(FLAG == 6){
                FollowUpdateonedose3.setText(GlobalVar.dialogeFormat.format(date));
            }else if(FLAG == 7){

                Followupdatetwodose3.setText(GlobalVar.dialogeFormat.format(date));

            }else if(FLAG == 8){

                FollowUpdateonedose4.setText(GlobalVar.dialogeFormat.format(date));

            }else if(FLAG == 9){

                FollowUpdateonedose4.setText(GlobalVar.dialogeFormat.format(date));

            }else if(FLAG == 10){

                FollowUpdateonedose5.setText(GlobalVar.dialogeFormat.format(date));

            }else if(FLAG == 11){

                FollowUpdateonedose5.setText(GlobalVar.dialogeFormat.format(date));

            } else {
                date_treatment.setText(GlobalVar.dialogeFormat.format(date));
            }






















        }
        // Optional cancel listener
        @Override
        public void onDateTimeCancel(){
        }
    };
    private Dialog showAnimalStatus() {

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.animal_status_dialog);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ImageView statusImageView = (ImageView) dialog.findViewById(R.id.statusImageView);
        LinearLayout statusContainer = (LinearLayout) dialog.findViewById(R.id.statusContainer);
        LinearLayout chartContainer = (LinearLayout) dialog.findViewById(R.id.chart_container);
        Button milk = (Button) dialog.findViewById(R.id.milk);
        Button breeding = (Button) dialog.findViewById(R.id.breeding);
        Button health = (Button) dialog.findViewById(R.id.health);


        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowAnimalStatusMilk = showMilkingDialog(SubStatus.MILKIN);
                dialogShowAnimalStatusMilk.show();


            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        breeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowAnimalStatusMilk = showMilkingDialog(SubStatus.BREEDING);
                dialogShowAnimalStatusMilk.show();



            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "YOU HAVE NOT ADDED DATA IN SERVER", Toast.LENGTH_LONG).show();
            }
        });


        JSONObject animalStatusObject = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getAnimalStatus(GlobalVar.ID_NUMBER);

        System.out.println("OBject Data "+animalStatusObject.toString());


        Iterator<?> keys = animalStatusObject.keys();
        try {
            if (animalStatusObject != null) {
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (!keys.equals("StatusPic")) {
                        View child = getActivity().getLayoutInflater().inflate(R.layout.animal_status_dynamic_layout, null);
                        if (!animalStatusObject.isNull(key)) {
                            ((TextView) child.findViewById(R.id.lable)).setText(key + "   :   ");
                            ((TextView) child.findViewById(R.id.lableValue)).setText(animalStatusObject.getString(key));
                            statusContainer.addView(child);
                        }
                    } else {
                        String picId = animalStatusObject.getString(key).trim();
                        if (picId.equals("28")) {
                            statusImageView.setImageResource(R.drawable.bullbuffalo28);
                        } else if (picId.equals("18")) {
                            statusImageView.setImageResource(R.drawable.bullcow18);
                        } else if (picId.equals("27")) {
                            statusImageView.setImageResource(R.drawable.calfbuffalo27);
                        } else if (picId.equals("17")) {
                            statusImageView.setImageResource(R.drawable.calfcow17);
                        } else if (picId.equals("22")) {
                            statusImageView.setImageResource(R.drawable.drybuffalo22);
                        } else if (picId.equals("12")) {
                            statusImageView.setImageResource(R.drawable.drycow12);
                        } else if (picId.equals("26")) {
                            statusImageView.setImageResource(R.drawable.heiferbuffalo26);
                        } else if (picId.equals("16")) {
                            statusImageView.setImageResource(R.drawable.heifercow16);
                        } else if (picId.equals("24")) {
                            statusImageView.setImageResource(R.drawable.milkingbuffalo24);
                        } else if (picId.equals("14")) {
                            statusImageView.setImageResource(R.drawable.milkingcow14);
                        } else if (picId.equals("23")) {
                            statusImageView.setImageResource(R.drawable.pregnant_drybuffalo23);
                        } else if (picId.equals("13")) {
                            statusImageView.setImageResource(R.drawable.pregnant_drycow13);
                        } else if (picId.equals("25")) {
                            statusImageView.setImageResource(R.drawable.pregnant_milkingbuffalo25);
                        } else if (picId.equals("25")) {
                            statusImageView.setImageResource(R.drawable.pregnant_milkingcow15);
                        } else if (picId.equals("21")) {
                            statusImageView.setImageResource(R.drawable.pregnantbuffalo21);
                        } else if (picId.equals("11")) {
                            statusImageView.setImageResource(R.drawable.pregnantcow11);
                        }
                    }
                }
            }

            HashMap<String, ArrayList<Double>> lactationCurve = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getLactationCurve(GlobalVar.ID_NUMBER);
            System.out.println(lactationCurve.toString());

            int yAxisMax = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getYAxis(GlobalVar.ID_NUMBER);



            drawChart(chartContainer, yAxisMax, lactationCurve);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dialog;
    }



    private Dialog showMilkingDialog(SubStatus subStatus) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.animal_status_milk_dialog);
        final TableLayout tableLayout= (TableLayout) dialog.findViewById(R.id.tableLayout);
        final ImageView ivClose= (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        ArrayList<String> lotList =null;
        if (subStatus==SubStatus.MILKIN) {
            lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getMilkAnimalStatus(ownerId.getText().toString());
        }else if (subStatus==SubStatus.BREEDING){
            lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getBreedingAnimalStatus(ownerId.getText().toString());
        }else if(subStatus==SubStatus.HEALTH){
//            lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getMilkAnimalStatus(animalId.getText().toString());
        }
        if(lotList!=null) {
            createTable(lotList, tableLayout);
        }
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

    private void drawChart(LinearLayout chartContainer, int yAxisMax, HashMap<String, ArrayList<Double>> lactationCurve) {

        Double income[] = new Double[lactationCurve.get("Days").size()];
        Double expense[] = new Double[lactationCurve.get("Days").size()];

        lactationCurve.get("Days").toArray(income);
        lactationCurve.get("Days_Total").toArray(expense);

        XYSeries expenseSeries = new XYSeries("Total Days");



        for (int i = 0; i < lactationCurve.get("Days").size(); i++) {

            if(income[i]<300) {
                expenseSeries.add(income[i], expense[i]);
            }
        }


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(expenseSeries);

        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.GREEN);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2f);
        expenseRenderer.setDisplayChartValues(true);
        expenseRenderer.setPointStyle(PointStyle.SQUARE);
        expenseRenderer.setStroke(BasicStroke.SOLID);
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setChartTitle("Lactation Curve");
        multiRenderer.setXTitle("Days");
        multiRenderer.setYTitle("Days Total");
        multiRenderer.setChartTitleTextSize(28);
        multiRenderer.setAxisTitleTextSize(24);
        multiRenderer.setLabelsTextSize(24);
        multiRenderer.setZoomButtonsVisible(false);
        multiRenderer.setPanEnabled(false, false);
        multiRenderer.setClickEnabled(false);
        multiRenderer.setZoomEnabled(false, false);
        multiRenderer.setShowGridY(true);
        multiRenderer.setShowGridX(true);
        multiRenderer.setFitLegend(true);
        multiRenderer.setShowGrid(true);
        multiRenderer.setZoomEnabled(false);
        multiRenderer.setExternalZoomEnabled(false);
        multiRenderer.setAntialiasing(true);
        multiRenderer.setInScroll(false);
        multiRenderer.setLegendHeight(30);
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        multiRenderer.setYLabels(15);
        multiRenderer.setXLabels(30);
        multiRenderer.setYAxisMax(yAxisMax);
        multiRenderer.setXAxisMax(300);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setMarginsColor(getResources().getColor(R.color.colorPrimaryDark));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(1f);
        multiRenderer.setPointSize(4f);
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        multiRenderer.addSeriesRenderer(expenseRenderer);

        chartContainer.removeAllViews();
        GraphicalView mChart = ChartFactory.getLineChartView(getActivity(), dataset, multiRenderer);
        chartContainer.addView(mChart);
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

}
