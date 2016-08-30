package provab.herdman.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.logging.Logger;

import provab.herdman.R;
import provab.herdman.beans.ActionBean;
import provab.herdman.constants.Links;
import provab.herdman.controller.WebInterface;
import provab.herdman.controller.WebServiceSyncController;
import provab.herdman.fragment.ActionListDetails;
import provab.herdman.fragment.AnimalDetailsFragment;
import provab.herdman.fragment.AnimalPropertyFragment;
import provab.herdman.fragment.EntryFragment;
import provab.herdman.utility.DatabaseHelper;
import provab.herdman.utility.HerdManContentProvider;
import provab.herdman.utility.SessionManager;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class SelectCategoryActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener, WebInterface {

    DrawerLayout drawer;
    LinearLayout selectVillage;
    LinearLayout selectFarmer;
    LinearLayout selectAnimal;
    LinearLayout animalId;

    LinearLayout newAnimal;
    Toolbar toolbar;
    TextView tootlBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tootlBarTitle = (TextView) toolbar.findViewById(R.id.title_Ctv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        selectVillage = (LinearLayout) findViewById(R.id.selectVillage);
        selectFarmer = (LinearLayout) findViewById(R.id.selectFarmer);
        selectAnimal = (LinearLayout) findViewById(R.id.selectAnimal);
        animalId = (LinearLayout)findViewById(R.id.AnimalId);

        newAnimal = (LinearLayout) findViewById(R.id.newAnimal);
        selectVillage.setOnClickListener(this);
        selectFarmer.setOnClickListener(this);
        selectAnimal.setOnClickListener(this);
        newAnimal.setOnClickListener(this);
        animalId.setOnClickListener(this);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(SelectCategoryActivity.this);

        Menu navMenu = navigationView.getMenu();
        for (int i = 0; i < navMenu.size(); i++) {
            MenuItem item = navMenu.getItem(i);
            //applyFontToMenuItem(item);
        }
        View headerView = navigationView.getHeaderView(0);
        LinearLayout headerNav = (LinearLayout) headerView.findViewById(R.id.navHeader);

        //exportDB();
    }

    public void findViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AnimalId:
                Intent intent1 = new Intent(SelectCategoryActivity.this, SearchId.class);
                startActivity(intent1);
                break;
            case R.id.selectFarmer:
                Intent intent2 = new Intent(SelectCategoryActivity.this, FarmerDetailsLogin.class);
                startActivity(intent2);
                break;
            case R.id.selectAnimal:
                Intent intent3 = new Intent(SelectCategoryActivity.this, AnimalDetailsLogin.class);
                intent3.putExtra("Key","1");
                startActivity(intent3);
                break;
            case R.id.newAnimal:
                Intent intent4 = new Intent(SelectCategoryActivity.this, AnimalRegistration.class);
                startActivity(intent4);
                break;
        }
    }


    private Account createDummyAccount(Context context) {
        Account dummyAccount = new Account("HerdManAccount", "com.provab.herdman");
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(dummyAccount, null, null);
        ContentResolver.setSyncAutomatically(dummyAccount, HerdManContentProvider.AUTHORITY, true);
        return dummyAccount;
    }


    @Override
    protected void onResume() {
        super.onResume();


//        ContentResolver.requestSync(createDummyAccount(this), HerdManContentProvider.AUTHORITY, Bundle.EMPTY);
    }

    /*@Override
    public void getResponse(String response) throws JSONException {
        final JSONObject responseObject = new JSONObject(response);
        if (responseObject.has("response") && (responseObject.getString("response").equalsIgnoreCase("yes")) && (responseObject.getInt("requestType") == GET_THIRD_AND_FOURTH_MASTERS)) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        DatabaseHelper.getDatabaseHelperInstance(SelectCategoryActivity.this).saveThirdAndFourthTypeMaster(responseObject.getJSONObject("GetMasterData"));
//                        DatabaseHelper.getDatabaseHelperInstance(SelectCategoryActivity.this).getThirdTypeTableMaxId1();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            try {
                writeToSD();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //int count = fmanager.getBackStackEntryCount();
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        showFragment(id);
        return true;
    }




    private void showFragment(int id) {
        Fragment frag = null;
        switch (id) {
            case R.id.nav_entry:
                break;
            case R.id.nav_action:
                 Intent intent = new Intent(SelectCategoryActivity.this,AnimalMainActivity.class);
                 intent.putExtra("Hint","3");
                 startActivity(intent);
                // ArrayList<ActionBean> lotList= DatabaseHelper.getDatabaseHelperInstance(SelectCategoryActivity.this).getTask_details("","");
                //frag = new ActionListDetails(lotList);
                break;
            case R.id.nav_alarm:

                break;
            case R.id.nav_animal_details:
            //   frag=new AnimalDetailsFragment();
                Intent intents = new Intent(SelectCategoryActivity.this,AnimalDetailsLogin.class);
                intents.putExtra("Hint","3");
                intents.putExtra("Key","0");
                startActivity(intents);
                break;

            case R.id.nav_report:

                break;

            case R.id.nav_logout:
                SessionManager manager = new SessionManager(SelectCategoryActivity.this);
                manager.logOut();
                break;
        }

    }









    @Override
    public void getResponse(String response, int flag) throws JSONException {
        Log.e("REs",response);
    }

    @Override
    public void failureResponse(int statusCode) throws JSONException {

    }



    /*@Override
    public void failureResponse(int statusCode) throws JSONException {

    }*/
}
