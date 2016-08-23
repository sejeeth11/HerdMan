package provab.herdman.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.adapter.GridDiseaseAdapter;
import provab.herdman.beans.DiseaseBean;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by ptblr1035 on 12/8/16.
 */
public class ShowDiseas extends DialogFragment {


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
        View v=inflater.inflate(R.layout.diseasedisplay, container, false);

        ListView grid = (ListView) v.findViewById(R.id.disease_grid);

        final ArrayList<DiseaseBean> lotList= DatabaseHelper.getDatabaseHelperInstance(getActivity()).Read_Data_vaccine();
        Log.e("Size",lotList.size()+"");


        LayoutInflater inflaters = getActivity().getLayoutInflater();
        View header = inflaters.inflate(R.layout.disease_header, grid, false);
        grid.addHeaderView(header, null, false);


        GridDiseaseAdapter Adapter = new GridDiseaseAdapter(getActivity(),lotList);
        grid.setAdapter(Adapter);

        return v;
    }















}
