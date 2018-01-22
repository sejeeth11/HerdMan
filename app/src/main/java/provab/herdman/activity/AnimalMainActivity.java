package provab.herdman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import provab.herdman.R;

import provab.herdman.beans.ActionBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.fragment.ActionListDetails;
import provab.herdman.fragment.ActionListFragment;
import provab.herdman.fragment.AnimalDetailsFragment;
import provab.herdman.fragment.AnimalPropertyFragment;
import provab.herdman.fragment.EntryFragment;
import provab.herdman.utility.DatabaseHelper;

public class AnimalMainActivity extends AppCompatActivity {

    //DrawerLayout drawer;
    FragmentManager fmanager;
    Toolbar toolbar;
    CircleImageView profileImgIv;
    TextView tootlBarTitle;
             String hint ,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tootlBarTitle = (TextView) toolbar.findViewById(R.id.title_Ctv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();

        hint = intent.getStringExtra("Hint");
        id = intent.getStringExtra("ID");
       // ArrayList<String> arra =  DatabaseHelper.getDatabaseHelperInstance(AnimalMainActivity.this).getAnimalDetails(id);

        //getSupportActionBar().setIcon(R.drawable.logo_toolbar);
        //getSupportActionBar().setTitle(" Smartmoo-Herdman");



        fmanager = getSupportFragmentManager();

     /*   drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        navigationView.setNavigationItemSelectedListener(this);

        Menu navMenu = navigationView.getMenu();
        for (int i = 0; i < navMenu.size(); i++) {
            MenuItem item = navMenu.getItem(i);
            //applyFontToMenuItem(item);
        }
        View headerView = navigationView.getHeaderView(0);
        LinearLayout headerNav = (LinearLayout) headerView.findViewById(R.id.navHeader);*/




        if(hint.equalsIgnoreCase("1")){
            Fragment frag = new EntryFragment();
            replaceFragmentNoStack(frag);
        }else if(hint.equalsIgnoreCase("3")){
            ArrayList<ActionBean> lotList= DatabaseHelper.getDatabaseHelperInstance(AnimalMainActivity.this).getTask_details("","");
            Fragment frag = new ActionListDetails(lotList);
            replaceFragmentNoStack(frag);
        }else{
            Fragment frag = new AnimalDetailsFragment();
            replaceFragmentNoStack(frag);
        }







//        replaceFragment(frag/*, ServiceSelectedFrag.class.getName()*/);


    }




    public void replaceFragment(Fragment f) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(R.id.container, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void replaceFragment(Fragment f, String tag) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(R.id.container, f);
        ft.addToBackStack(tag);
        ft.commit();
    }

    public void replaceFragmentNoStack(Fragment f) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(R.id.container, f);
        ft.commit();
    }
    @Override
    public void onBackPressed() {
        // your code.
        Intent intent = new Intent(AnimalMainActivity.this,SelectCategoryActivity.class);
        startActivity(intent);
        finish();

    }
}

