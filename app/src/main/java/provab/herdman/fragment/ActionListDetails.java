package provab.herdman.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import provab.herdman.R;
import provab.herdman.adapter.ActionListAdapter;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.beans.ActionBean;
import provab.herdman.beans.Test;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;


public class ActionListDetails extends Fragment {

    ListView list_details;
    ArrayList<Test> duplicate = new ArrayList<Test>();
    ArrayList<Test> dummy = new ArrayList<Test>();
    ArrayList<ActionBean> all_data = new ArrayList<ActionBean>();
    Button filter;
    ArrayList<String> hint = new ArrayList<String>();
    ActionListAdapter adapter;
    ArrayList<Test> arrayList;
    String VillageCode,OwnerCode;




    public ActionListDetails(ArrayList<ActionBean> all_data) {
        this.all_data = all_data;
       //  this.duplicate = all_data;
        arrayList=new ArrayList<Test>();

    }


    public  ArrayList<ActionBean> main(ArrayList<ActionBean> l) {

        ArrayList<ActionBean> arra = new ArrayList<ActionBean>();

        LinkedHashSet<String> hashSet=new LinkedHashSet<>();
        LinkedHashSet<String> hashSet1 = new LinkedHashSet<>();


        for(int i=0;i<all_data.size();i++) {
            hashSet.add(all_data.get(i).getPhoneno());
            hashSet1.add(all_data.get(i).getPhoneno());

        }

        Iterator itr = hashSet.iterator();
        Iterator itr1 = hashSet1.iterator();


        while(itr.hasNext()) {

            Test test=new Test();
            Object element = itr.next();
            String key=element+"";
            Log.e("phoneno",key);
            test.setPhone_no(key);
            ArrayList<String> arrayListID=new ArrayList<String>();
            ArrayList<String> arrayList_Task = new ArrayList<String>();
            LinkedHashSet<String> taskSet=new LinkedHashSet<>();
            for(int j=0;j<all_data.size();j++) {
                if(key.equals(all_data.get(j).getPhoneno())) {
                    arrayListID.add(all_data.get(j).getIdno());
                    arrayList_Task.add(all_data.get(j).getTask());
                    test.setFarmer_name(all_data.get(j).getFarmername());
                    test.setTask(all_data.get(j).getTask());
                    taskSet.add(all_data.get(j).getTask());
                }
            }
            test.setTask_array(arrayList_Task);
            test.setArrayList(arrayListID);
            test.setTaskSet(taskSet);
            arrayList.add(test);
        }


        for (int k=0;k<arrayList.size();k++)
        {
            LinkedHashSet<String> testset=arrayList.get(k).getTaskSet();
            ArrayList<String> Ids=arrayList.get(k).getArrayList();
            ArrayList<String> tasklist=arrayList.get(k).getTask_array();
            LinkedHashMap<String,ArrayList<String>> listLinkedHashMap=new LinkedHashMap<>();
            for(String task:testset)
            {
                ArrayList<String> al=new ArrayList<>();
                 for (int n=0;n< tasklist.size();n++)
                 {
                       if(tasklist.get(n).equals(task))
                       {
                            al.add(Ids.get(n));
                       }
                 }

                listLinkedHashMap.put(task,al);

            }

             arrayList.get(k).setLinkedHashMap(listLinkedHashMap);
        }


      //  for(int i=0)
        return  arra;



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v  =  inflater.inflate(R.layout.fragment_action_list_details, container, false);
        list_details = (ListView)v.findViewById(R.id.details_list);
        LayoutInflater inflaters = getActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup) inflaters.inflate(R.layout.header, list_details,
                false);
        filter = (Button)header.findViewById(R.id.filter);

        main(all_data);





        adapter = new ActionListAdapter(getActivity(),R.layout.list_item,arrayList);

        list_details.setAdapter(adapter);
        list_details.addHeaderView(header);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog dialog = new MyDialog();
                dialog.show(getActivity().getSupportFragmentManager(),"");

            }
        });

        return v;
    }

      public class  MyDialog extends DialogFragment implements View.OnClickListener{
          Button loginButton;
          LinearLayout lotInfoLayout;
          LinearLayout ownerInfoLayout;
          LinearLayout detailsInfoLayout;
          TextView lotInfoSpinner;
          TextView ownerInfoSpinner;
          TextView detailsInfoSpinner;
          ArrayList<String> catch_data;


          @Override
          public void onStart() {
              super.onStart();
              Dialog dialog = getDialog();
              if (dialog != null) {
                  int width = ViewGroup.LayoutParams.MATCH_PARENT;
                  int height = ViewGroup.LayoutParams.MATCH_PARENT;
                  dialog.getWindow().setLayout(width, height);
              }
          }

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

              return v;
          }






          public void Multiselect(){


              AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
              final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).get_Task();
              catch_data = new ArrayList<String>();
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
                      detailsInfoSpinner.setText(data);


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
                      VillageCode=view.getTag().toString();
                      System.out.println("VillageCode "+VillageCode);
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
                      OwnerCode = view.getTag().toString();

                    System.out.println("OwnerCode "+OwnerCode);


                      /*GlobalVar.OWNERS_CODE=view.getTag().toString();
                      GlobalVar.OWNERS_NAME=((TextView) view).getText().toString();*/
                      dialog.dismiss();





                  }
              });
              return dialog;
          }

       /*   private Dialog showSelectIDDialog() {
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
          }*/





          @Override
          public void onClick(View v) {
              switch (v.getId()) {
                  case R.id.loginButton:
                      dismiss();
                      //all_data.clear();

                     duplicate.addAll(arrayList);



                     arrayList.clear();


                      String farmerName = ownerInfoSpinner.getText().toString();
                      System.out.println(farmerName+"    "+catch_data.toString());




                      for(int i=0;i<duplicate.size();i++){

                              dummy.add(duplicate.get(i));


                      }

                      System.out.println("Dummy "+dummy.size());

                      System.out.println("Filter_Data "+catch_data.size());

                      for(int j=0;j<dummy.size();j++){

                        for(int k=0;k<catch_data.size();k++){

                            if(dummy.get(j).getTask_array().contains(catch_data.get(k))){

                                arrayList.add(dummy.get(j));
                              }
                        }

                      }
                      adapter.notifyDataSetChanged();

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

                      Multiselect();

                      break;
              }
          }

      }









}
