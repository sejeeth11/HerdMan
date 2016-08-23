package provab.herdman.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import provab.herdman.R;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;


/**
 * Created by ptblr-1167 on 6/5/16.
 */
public class GridAdapter extends BaseAdapter {

    public static final String AI_NATURAL_SERVICE = "A.I Natural Service";
    public static final String PD_ENTRY = "PD Entry";
    public static final String CALVING = "Calving";
    public static final String MILKING = "Milk Record";
    public static final String BODY_WEIGHT = "Body Weight";
    public static final String TREATMENT = "Treatment";
    public static final String VACCINATION = "Vaccination";
    public static final String DEWORMING = "Deworming";
    public static final String DISPOSAL = "Disposal";
    public static final String DRY_OFF = "Dry off";
    public static final String FEEDING = "Feeding";
    JSONObject breedingStatusAndStatus;
    int status;
    String breedingStatus;
    private Activity context;
    private String[] entryList;
    private int[] entryIcon = {R.drawable.natural_service_icon, R.drawable.pd_entry_icon, R.drawable.calving_icon, R.drawable.dryoff_icon,R.drawable.milking_icon,
            R.drawable.body_weight_icon, R.drawable.treatment_icon, R.drawable.vaccination_icon, R.drawable.deworming_icon,
            R.drawable.disposal_icon,  R.drawable.feeding_icon};


    public GridAdapter(Activity context, String[] entryList) {
        this.context = context;
        this.entryList = entryList;
        breedingStatusAndStatus = DatabaseHelper.getDatabaseHelperInstance(this.context).getBreedingStatusAndStatus(GlobalVar.ID_NUMBER);
        try {
            status=breedingStatusAndStatus.getInt("status");
            breedingStatus=breedingStatusAndStatus.getString("breedingStatus");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return entryList.length;
    }

    @Override
    public Object getItem(int position) {
        return entryList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            if(isEnabled(position)) {
                viewholder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_entry_adapter, parent, false);
                viewholder.entryIcon = (ImageView) convertView.findViewById(R.id.entryIcon);
                viewholder.entryText = (TextView) convertView.findViewById(R.id.entryText);
                convertView.setTag(viewholder);
            }else{
                viewholder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_entry_adapter_disabled, parent, false);
                viewholder.entryIcon = (ImageView) convertView.findViewById(R.id.entryIcon);
                viewholder.entryText = (TextView) convertView.findViewById(R.id.entryText);
                convertView.setTag(viewholder);
            }
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.entryIcon.setImageResource(entryIcon[position]);
        viewholder.entryText.setText(entryList[position]);
        return convertView;
    }
    @Override
    public boolean isEnabled(int position) {

        boolean enableOrDisable = false;
            if (entryList[position].equals(AI_NATURAL_SERVICE)) {
                if (status == 2 || status == 4 || status == 6) {
                    enableOrDisable = true;
                }
            } else if (entryList[position].equals(CALVING)) {
                if (status == 1 || status == 3 || status == 5) {
                    enableOrDisable = true;
                }
            } else if (entryList[position].equals(DRY_OFF)) {
                if (status == 4 || status == 5) {
                    enableOrDisable = true;
                }
            } else if (entryList[position].equals(MILKING)) {
                if (status == 4 || status == 5) {
                    enableOrDisable = true;
                }
            } else if (entryList[position].equals(PD_ENTRY)) {
                if (breedingStatus.equalsIgnoreCase("Open Bred")) {
                    enableOrDisable = true;
                }
            }else{
                enableOrDisable = true;
            }

    return enableOrDisable;
}

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    static class ViewHolder {
        ImageView entryIcon;
        TextView entryText;
    }


}
