package provab.herdman.fragment;

import android.app.Dialog;
import android.content.res.Resources;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import provab.herdman.R;
import provab.herdman.activity.MyApplication;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.constants.GlobalVar;
import provab.herdman.enume.SubStatus;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class FragmentNaturalService extends Fragment {

    TextView villageCode;
    TextView ownerCode;
    TextView ownerName;
    TextView animalId;
    TextView heatDate;
    TextView serviceType;
    TextView sireEarTagNo;
    TextView inseminator;
    Button naturalServiceSubmit;
    Button naturalServiceCancel;
    EditText totalDose;
    ImageView animalStatusIcon;

    LinearLayout datePickLayout;
    LinearLayout servicePickLayout;
    LinearLayout sireEarTagNoLayout;
    LinearLayout inseminatorLayout;
    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            heatDate.setText(GlobalVar.dialogeFormat.format(date));
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
        View v = inflater.inflate(R.layout.fragment_naturalservice, container, false);
        findViews(v);
        return v;
    }

    public void findViews(View view) {
        villageCode = (TextView) view.findViewById(R.id.villageCode);
        ownerCode = (TextView) view.findViewById(R.id.ownerCode);
        ownerName = (TextView) view.findViewById(R.id.ownersName);
        animalId = (TextView) view.findViewById(R.id.idNumber);
        heatDate = (TextView) view.findViewById(R.id.datePick);
        serviceType = (TextView) view.findViewById(R.id.serviceType);
        sireEarTagNo = (TextView) view.findViewById(R.id.sireEarTagNo);
        inseminator = (TextView) view.findViewById(R.id.inseminator);
        naturalServiceSubmit = (Button) view.findViewById(R.id.naturalServiceSubmit);
        naturalServiceCancel = (Button) view.findViewById(R.id.naturalServiceCancel);
        animalStatusIcon = (ImageView) view.findViewById(R.id.animalStatusIcon);


        datePickLayout = (LinearLayout) view.findViewById(R.id.datePickLayout);
        servicePickLayout = (LinearLayout) view.findViewById(R.id.servicePickLayout);
        sireEarTagNoLayout = (LinearLayout) view.findViewById(R.id.sireEarTagNoLayout);
        inseminatorLayout = (LinearLayout) view.findViewById(R.id.inseminatorLayout);
        totalDose = (EditText) view.findViewById(R.id.totalDose);

        villageCode.setText(GlobalVar.VILLAGE_CODE);
        ownerCode.setText(GlobalVar.OWNERS_CODE);
        animalId.setText(GlobalVar.ID_NUMBER);
        ownerName.setText(GlobalVar.OWNERS_NAME);


        datePickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "" + DatabaseHelper.getDatabaseHelperInstance(getActivity()).getHeatDate(GlobalVar.ID_NUMBER), Toast.LENGTH_SHORT).show();
                String dateInString = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getHeatDate(GlobalVar.ID_NUMBER);
                try {
                    Date minDate = GlobalVar.databaseFormat.parse(dateInString);
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener)
                            .setInitialDate(new Date())
                            .setMinDate(minDate)
                            .setMaxDate(new Date())
                                    //.setIs24HourTime(true)
                                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        servicePickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowService = showSelectService();
                dialogShowService.show();
            }
        });
        sireEarTagNoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceType.getTag() != null) {
                    Dialog dialogShowSireTag = showSireTagList();
                    dialogShowSireTag.show();
                } else {
                    Toast.makeText(getActivity(), "YOU HAVE NOT SELECTED SERVICE TYPE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        inseminatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showInseminator();
                dialogShowInseminator.show();
            }
        });
        naturalServiceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = getActivity().getResources();
                if (heatDate.getText().toString().equals(resources.getString(R.string.select_date_of_heat))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT HEAT DATE", Toast.LENGTH_LONG).show();
                    return;
                }
                if (serviceType.getTag() == null || serviceType.getText().toString().equals(resources.getString(R.string.select_service_type))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT SERVICE TYPE", Toast.LENGTH_LONG).show();
                    return;
                }
                if (sireEarTagNo.getTag() == null || sireEarTagNo.getText().toString().equals(resources.getString(R.string.select_sire_ear_tag_no))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT SIRE EAR TAG NO", Toast.LENGTH_LONG).show();
                    return;
                }
                if (inseminator.getTag() == null || inseminator.getText().toString().equals(resources.getString(R.string.select_inseminator))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT INSEMINATOR", Toast.LENGTH_LONG).show();
                    return;
                }
                if (totalDose.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "PLEASE ENTER DOSE", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    long status = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).saveAINaturalService(animalId.getText().toString(), GlobalVar.databaseFormat.format(GlobalVar.dialogeFormat.parse(heatDate.getText().toString())), serviceType.getTag().toString(), sireEarTagNo.getTag().toString(), inseminator.getTag().toString(), totalDose.getText().toString(), "M");
                    if (status >= 0) {
                        Toast.makeText(getActivity(), "SUBMITTED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), "TRANSACTION FAILED", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        naturalServiceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        animalStatusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialogShowAnimalStatus = showAnimalStatus();
                dialogShowAnimalStatus.show();


            }
        });
    }

    private Dialog showSelectService() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getServiceIdAndName();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Service");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                serviceType.setText(((TextView) view).getText());
                serviceType.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSireTagList() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSireEarTagList(GlobalVar.ID_NUMBER, serviceType.getTag().toString());
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Sire Tag");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sireEarTagNo.setText(((TextView) view).getText());
                sireEarTagNo.setTag(view.getTag().toString());
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
                inseminator.setText(((TextView) view).getText());
                inseminator.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }


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





        JSONObject animalStatusObject = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getAnimalStatus(animalId.getText().toString());

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

            HashMap<String, ArrayList<Double>> lactationCurve = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getLactationCurve(animalId.getText().toString());
            System.out.println(lactationCurve.toString());

            int yAxisMax = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getYAxis(animalId.getText().toString());



            drawChart(chartContainer, yAxisMax, lactationCurve);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dialog;
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
            lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getMilkAnimalStatus(animalId.getText().toString());
        }else if (subStatus==SubStatus.BREEDING){
            lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getBreedingAnimalStatus(animalId.getText().toString());
        }else if(subStatus==SubStatus.HEALTH){
//            lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getMilkAnimalStatus(animalId.getText().toString());
        }
        if(lotList!=null) {
            createTable(lotList, tableLayout);
        }
        return dialog;
    }

}
