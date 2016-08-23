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
import org.w3c.dom.Text;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.beans.DiseaseBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;


/**
 * Created by ptblr-1167 on 6/5/16.
 */
public class GridDiseaseAdapter extends BaseAdapter {

    ArrayList<DiseaseBean> bean = new ArrayList<DiseaseBean>();
    Context cxt ;



    public GridDiseaseAdapter(Context context, ArrayList<DiseaseBean> entryList) {
        this.cxt = context;
        this.bean = entryList;

    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {

                viewholder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item, parent, false);
                viewholder.disease = (TextView) convertView.findViewById(R.id.disease);
                viewholder.date = (TextView) convertView.findViewById(R.id.date);
                viewholder.Animals = (TextView) convertView.findViewById(R.id.animals);
                convertView.setTag(viewholder);

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.disease.setText(""+bean.get(position).getDiseases());

        String value[] = bean.get(position).getDate().split(" ");


        viewholder.date.setText(" "+value[0]);

        viewholder.Animals.setText(" "+bean.get(position).getAnimals());

        return convertView;
    }

    static class ViewHolder {
        TextView disease;
        TextView date;
        TextView Animals;
    }


}