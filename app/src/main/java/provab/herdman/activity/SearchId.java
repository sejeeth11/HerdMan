package provab.herdman.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.adapter.CustomSearchAdapter;
import provab.herdman.beans.SearchBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;

public class SearchId extends AppCompatActivity implements SearchView.OnQueryTextListener {



    ListView Id_list;
    CustomSearchAdapter Adapter;
    ArrayList<SearchBean>data;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_id);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

       setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Id");

        Id_list = (ListView)findViewById(R.id.list_ids);
         data =  DatabaseHelper.getDatabaseHelperInstance(SearchId.this).getAll_ids();
        Adapter = new CustomSearchAdapter(SearchId.this,R.layout.id_row,data);
        Id_list.setAdapter(Adapter);
        Id_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GlobalVar.ID_NUMBER = data.get(position).getIdno();


                Intent intent = new Intent(SearchId.this, AnimalMainActivity.class);
                intent.putExtra("Hint","1");
                intent.putExtra("ID", GlobalVar.ID_NUMBER);
                startActivity(intent);

            }
        });

    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Adapter.filter(newText);

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }


}



