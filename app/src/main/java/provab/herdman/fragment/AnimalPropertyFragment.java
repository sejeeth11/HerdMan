package provab.herdman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import provab.herdman.R;

/**
 * Created by PTBLR-1057 on 5/19/2016.
 */
public class AnimalPropertyFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_animal_property, container, false);
        findViews(v);
        return v;
    }

    public void findViews(View view){

    }
}
