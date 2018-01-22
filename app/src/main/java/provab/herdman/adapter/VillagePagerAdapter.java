package provab.herdman.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import provab.herdman.fragment.BreedingDetailsFragment;
import provab.herdman.fragment.CattleRegistration;
import provab.herdman.fragment.InsuranceDetailsFragment;
import provab.herdman.fragment.OtherDetailsFragment;
import provab.herdman.fragment.ParentDetailsFragment;
import provab.herdman.fragment.PurchaseDetailsFragment;

/**
 * Created by PTBLR-1057 on 6/14/2016.
 */
public class VillagePagerAdapter extends FragmentStatePagerAdapter {



   // String[] registrationForms={"Registration","Breeding","Purchase","Parent Details","Insurance Details","Other Details"};
   String[] registrationForms={"Registration","Breeding"};

    public VillagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag=new CattleRegistration();
                break;
            case 1:
                frag=new BreedingDetailsFragment();
                break;
            case 2:
                frag=new PurchaseDetailsFragment();
                break;
            case 3:
                frag=new ParentDetailsFragment();
                break;
            case 4:
                frag=new InsuranceDetailsFragment();
                break;
            case 5:
                frag=new OtherDetailsFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return registrationForms.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  registrationForms[position];
    }
}
