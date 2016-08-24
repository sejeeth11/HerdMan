package provab.herdman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

import provab.herdman.R;
import provab.herdman.activity.AnimalMainActivity;
import provab.herdman.activity.MyApplication;
import provab.herdman.adapter.GridAdapter;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class EntryFragment extends Fragment {

    GridView gridView;
    GridAdapter adapter;
    String[] entryList = {"A.I Natural Service", "PD Entry", "Calving", "Milk Record", "Dry off","Body Weight", "Treatment", "Vaccination", "Deworming", "Disposal", "DeHoring"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entery, container, false);
        findViews(v);
        return v;
    }

    public void findViews(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);
        setGridData();

        HashMap<String, ArrayList<Double>> lactationCurve = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getLactationCurve(GlobalVar.OWNERS_CODE);



       // System.out.println(lactationCurve.toString());

        int yAxisMax = DatabaseHelper.getDatabaseHelperInstance(MyApplication.getContext()).getYAxis(GlobalVar.OWNERS_CODE);

    }

    public void setGridData() {

        gridView.setVisibility(View.VISIBLE);
        GridAdapter adapter = new GridAdapter(getActivity(), entryList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment frag = null;
                switch (position) {
                    case 0:
                        frag = new FragmentNaturalService();
                        break;
                    case 1:
                        frag = new FragmentPDEntry();
                        break;
                    case 2:
                        frag = new FragmentCalving();
                        break;
                    case 3:
                        frag = new FragmentMilking();
                        break;
                    case 4:
                        frag = new DryOffFragment();
                        break;
                    case 5:
                        frag = new FragmentBodyWeight();
                        break;
                    case 6:
                        frag = new TreatmentFragment();
                        break;
                    case 7:
                        frag = new FragmentVaccination();
                        break;
                    case 8:
                        frag = new FragmentDeworming();
                        break;
                    case 9:
                        frag = new DisposalFragment();
                        break;
                    case 10:
                        frag = new FeedingFragment();
                        break;
                }
                if (frag != null) {
                    ((AnimalMainActivity) getActivity()).replaceFragment(frag);
                }
            }
        });
    }
}
