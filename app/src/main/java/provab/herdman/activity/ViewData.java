package provab.herdman.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.adapter.ViewDataAdapter;
import provab.herdman.beans.DetailsBean;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.SessionManager;

public class ViewData extends AppCompatActivity {


    ListView viewdata_list;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_data);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Details");
        viewdata_list = (ListView)findViewById(R.id.view_data_list);
        SessionManager manager = new SessionManager(ViewData.this);


        ArrayList<DetailsBean> details_data = DatabaseHelper.getDatabaseHelperInstance(ViewData.this).details(manager.getPrefData("UserCode"));
        ViewDataAdapter adapter = new ViewDataAdapter(ViewData.this,R.layout.list_item,details_data);
        viewdata_list.setAdapter(adapter);







    }
}
