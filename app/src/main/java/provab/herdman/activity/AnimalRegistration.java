package provab.herdman.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import provab.herdman.R;
import provab.herdman.adapter.VillagePagerAdapter;
import provab.herdman.beans.CattleBean;
import provab.herdman.customelements.TabLayoutUtils;

/**
 * Created by PTBLR-1057 on 7/4/2016.
 */
public class AnimalRegistration extends AppCompatActivity {
    Toolbar toolbar;
    TextView tootlBarTitle;
    FragmentManager fmanager;
    ViewPager pager;

    TabLayout tabLayout;
    VillagePagerAdapter adapter;
    String cattleId;
    CattleBean cattleBean;

    public void setCattleRegistrationDate(String cattleRegistrationDate) {
        this.cattleRegistrationDate = cattleRegistrationDate;
    }

    public String getCattleRegistrationDate() {
        return cattleRegistrationDate;
    }

    String cattleRegistrationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_village);

        cattleBean=CattleBean.getCattleBeanInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tootlBarTitle = (TextView) toolbar.findViewById(R.id.title_Ctv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        fmanager = getSupportFragmentManager();
        FragmentManager manager = getSupportFragmentManager();
        adapter = new VillagePagerAdapter(manager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(6);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        TabLayoutUtils.enableTabs(tabLayout,false);
    }

    public String getId() {
        return cattleId;
    }

    public void setId(String id) {
        this.cattleId = id;
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    public CattleBean getCattleBean() {
        return cattleBean;
    }

    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Entered Data will be lost if you exit Registration form")
                .setCancelable(true)
                .setPositiveButton("Exit Registration", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AnimalRegistration.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void swipeViewPagerToNextScreen(){
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }

    public void swipeViewPagerToPreviousScreen(){
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }
}
