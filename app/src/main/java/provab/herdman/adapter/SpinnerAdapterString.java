package provab.herdman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import provab.herdman.R;

/**
 * Created by PTBLR-1057 on 6/10/2016.
 */
public class SpinnerAdapterString extends BaseAdapter {

    Context parentContext;
    LayoutInflater layoutInflater;
    ArrayList<String> spinnerJsonArray;
    public SpinnerAdapterString(Context ctx, int txtViewResourceId, ArrayList<String> spinnerJsonArray) {
        this.parentContext=ctx;
        layoutInflater=LayoutInflater.from(parentContext);
        this.spinnerJsonArray=spinnerJsonArray;
    }

    /*@Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }*/

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    @Override
    public int getCount() {
        return spinnerJsonArray.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        JSONObject currentObject=null;
        View spinnerView = layoutInflater.inflate(R.layout.spinner_content, parent, false);
        try {
            String currentString=spinnerJsonArray.get(position);
            TextView spinnerLable = (TextView) spinnerView.findViewById(R.id.spinnerText);
            spinnerLable.setText(currentString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spinnerView;
    }


}
