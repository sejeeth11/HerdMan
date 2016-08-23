package provab.herdman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.beans.MultiSelectItem;

/**
 * Created by PTBLR-1057 on 6/13/2016.
 */
public class SpinnerMultiSelect extends BaseAdapter {

    Context parentContext;
    LayoutInflater layoutInflater;
    ArrayList<MultiSelectItem> spinnerJsonArray;
    public SpinnerMultiSelect(Context ctx, int txtViewResourceId, ArrayList<MultiSelectItem> spinnerJsonArray) {
        this.parentContext=ctx;
        layoutInflater=LayoutInflater.from(parentContext);
        this.spinnerJsonArray=spinnerJsonArray;
    }


    @Override
    public int getCount() {
        return spinnerJsonArray.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerJsonArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


      @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Planet to display
          MultiSelectItem planet = spinnerJsonArray.get(position);

        // The child views in each row.
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.spinner_content_multiple, null);

            // Find the child views.
            textView = (TextView) convertView
                    .findViewById(R.id.rowTextView);
            checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
            // Optimization: Tag the row with it's child views, so we don't
            // have to
            // call findViewById() later when we reuse the row.
            convertView.setTag(new SelectViewHolder(textView, checkBox));
            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    MultiSelectItem planet = (MultiSelectItem) cb.getTag();
                    planet.setChecked(cb.isChecked());
                }
            });
        }
        // Reuse existing row view
        else {
            // Because we use a ViewHolder, we avoid having to call
            // findViewById().
            SelectViewHolder viewHolder = (SelectViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the planet in onClick() when the CheckBox is toggled.
        checkBox.setTag(planet);
        // Display planet data
        checkBox.setChecked(planet.isChecked());
        textView.setText(planet.getName());
        return convertView;
    }

    public static class SelectViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public SelectViewHolder() {
        }

        public SelectViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

}

