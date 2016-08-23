package provab.herdman.adapter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.content.ClipData;
import android.content.Context;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import provab.herdman.R;
import provab.herdman.beans.ActionBean;
import provab.herdman.beans.SearchBean;
import provab.herdman.beans.Test;

public class CustomSearchAdapter extends ArrayAdapter<SearchBean> {

    // declaring our ArrayList of items
    private ArrayList<SearchBean> objectss;
    private ArrayList<SearchBean> arraylist;


    Context context;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public CustomSearchAdapter(Context context, int textViewResourceId, ArrayList<SearchBean> objects) {
        super(context, textViewResourceId, objects);
        this.context=context;
        this.objectss = objects;
        this.arraylist = new ArrayList<SearchBean>();
        this.arraylist.addAll(objectss);

    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */


    @Override
    public int getCount() {
        return objectss.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;
        LayoutInflater inflater = null;
        LayoutInflater inflater1 = null;


        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.id_row, null);
        TextView Id = (TextView)v.findViewById(R.id.id_text);
        TextView FarmerName = (TextView)v.findViewById(R.id.farmervalue);
        TextView MobileValue = (TextView)v.findViewById(R.id.mobilevalue);


        SearchBean i = objectss.get(position);
        Id.setText("Id No "+i.getIdno());
        FarmerName.setText(i.getFarmername());
        MobileValue.setText(i.getPhoneno());


        return v;

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        objectss.clear();
        if (charText.length() == 0) {
            objectss.addAll(arraylist);
        } else {
            for (SearchBean wp : arraylist) {
                if (wp.getIdno().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    objectss.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }













}