package provab.herdman.adapter;

import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import provab.herdman.R;
import provab.herdman.activity.SelectCategoryActivity;
import provab.herdman.beans.ActionBean;
import provab.herdman.beans.Test;

public class ActionListAdapter extends ArrayAdapter<Test> {

    // declaring our ArrayList of items
    private ArrayList<Test> objects;
    Context context;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public ActionListAdapter(Context context, int textViewResourceId, ArrayList<Test> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */


    @Override
    public int getCount() {
        return objects.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;
        LayoutInflater inflater = null;
        LayoutInflater inflater1 = null;


        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.list_item, null);


        final Test i = objects.get(position);
        TextView farmer_name = (TextView) v.findViewById(R.id.farmervalue);
        TextView Mobile = (TextView) v.findViewById(R.id.mobilevalue);

        LinearLayout dynamic = (LinearLayout) v.findViewById(R.id.add_dynamic);


        //  Log.e("Gss",i.getTask());

        //Log.e("Diff",i.getLinkedHashMap().toString());

        farmer_name.setText(i.getFarmer_name());
        Mobile.setText(i.getPhone_no());
        System.out.println("KEys" + i.getLinkedHashMap().keySet().size());

        Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    call(i.getPhone_no());




            }
        });


        System.out.println("Data" + i.getLinkedHashMap().toString());

        for (Map.Entry<String, ArrayList<String>> entry : i.getLinkedHashMap().entrySet()) {
            ArrayList<String> data = i.getLinkedHashMap().get(entry.getKey());
            inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View s = inflater1.inflate(R.layout.list_item_sub, null);
            TextView task = (TextView) s.findViewById(R.id.taskvalue);
            task.setText(entry.getKey());

            LinearLayout linearChild = (LinearLayout) s.findViewById(R.id.linearChild);


            for (int K = 0; K < data.size(); K++) {

                TextView textView = new TextView(context);
                textView.setText(data.get(K));
                linearChild.addView(textView);
            }
            dynamic.addView(s);


        }


        return v;

    }

    private void call(String phoneNo) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNo));
        try {


            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }else {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + phoneNo));
                context.startActivity(callIntent);
            }



            //.startActivity(in);
        }

        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(context,"yourActivity is not founded",Toast.LENGTH_SHORT).show();
        }
    }



}