package provab.herdman.adapter;

import java.security.Key;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import provab.herdman.R;
import provab.herdman.activity.SelectCategoryActivity;
import provab.herdman.beans.ActionBean;
import provab.herdman.beans.DetailsBean;
import provab.herdman.beans.Test;

public class ViewDataAdapter extends ArrayAdapter<DetailsBean> {




    // declaring our ArrayList of items
    private ArrayList<DetailsBean> objects;
    Context context;


    public ViewDataAdapter(Context context, int textViewResourceId, ArrayList<DetailsBean> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;
        LayoutInflater inflater = null;

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.viewdata_row, null);
        TextView id = (TextView)v.findViewById(R.id.id);
        ImageView imageSuccess = (ImageView)v.findViewById(R.id.success);
        ImageView imagefailure = (ImageView)v.findViewById(R.id.failure);



        final DetailsBean i = objects.get(position);
        id.setText(i.getIdNo());
        if(i.getSyncID().equalsIgnoreCase("0")){
            imagefailure.setVisibility(View.VISIBLE);
        }else{

            imageSuccess.setVisibility(View.VISIBLE);

        }






        return v;

    }


}