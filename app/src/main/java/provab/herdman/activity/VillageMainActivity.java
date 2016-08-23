package provab.herdman.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import provab.herdman.R;
import provab.herdman.adapter.VillagePagerAdapter;
import provab.herdman.beans.CattleBean;
import provab.herdman.customelements.TabLayoutUtils;

/**
 * Created by PTBLR-1057 on 5/19/2016.
 */
public class VillageMainActivity extends AppCompatActivity{

    Toolbar toolbar;
    TextView tootlBarTitle;
    FragmentManager fmanager;
    ViewPager pager;
    TabLayout tabLayout;
    String idNumber;


    CattleBean cattleBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_village);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tootlBarTitle = (TextView) toolbar.findViewById(R.id.title_Ctv);
        cattleBean=CattleBean.getCattleBeanInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.logo_toolbar);
        //getSupportActionBar().setTitle(" Smartmoo-Herdman");
        pager= (ViewPager) findViewById(R.id.view_pager);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        fmanager = getSupportFragmentManager();
        FragmentManager manager=getSupportFragmentManager();
        VillagePagerAdapter adapter=new VillagePagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        TabLayoutUtils.enableTabs(tabLayout, false);


        Bundle  bundle=getIntent().getExtras();
        if(bundle!=null){
            idNumber=bundle.getString("idNumber");
        }


    }

    public String getId(){
        return idNumber;
    }

    /*public void swipeViewPagerToNextScreen(){
        pager.setCurrentItem( pager.getCurrentItem()+1, true);
    }

    public void swipeViewPagerToPreviousScreen(){
        pager.setCurrentItem( pager.getCurrentItem()+1, true);
    }*/

    public void replaceFragment(Fragment f) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(R.id.container, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    public CattleBean getCattleBean() {
        return cattleBean;
    }

    public void swipeViewPagerToNextScreen(){
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
    }

    public void swipeViewPagerToPreviousScreen(){
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }



}
