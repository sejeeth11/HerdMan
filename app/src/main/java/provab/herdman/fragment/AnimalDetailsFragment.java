package provab.herdman.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import provab.herdman.R;
import provab.herdman.constants.AnimalDetailsData;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 5/18/2016.
 */
public class AnimalDetailsFragment  extends Fragment {

  //  TextView

  TextView LotCode,statuspic,
    OwnerCode,
    OwnerName,
    Age,
    BreedName,
    Status,
    BreedingStatus,
    OpenPeriod,
    DryDays,
    MilkYield,
    AvgYield,
    PeakYield;
    ImageView AnimalImage;

    ArrayList<String> arra;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_animal_details, container, false);
        System.out.println(GlobalVar.ID_NUMBER);

          arra =  DatabaseHelper.getDatabaseHelperInstance(getActivity()).getAnimalDetails(GlobalVar.ID_NUMBER);


        findViews(v);
        return v;
    }

    public void findViews(View view){
        statuspic = (TextView)view.findViewById(R.id.statuspic);
        LotCode = (TextView)view.findViewById(R.id.lotcode);
        AnimalImage = (ImageView)view.findViewById(R.id.animalImage);
        OwnerCode = (TextView)view.findViewById(R.id.ownercode);
        OwnerName = (TextView)view.findViewById(R.id.ownername);
        Age = (TextView)view.findViewById(R.id.age);
        BreedName = (TextView)view.findViewById(R.id.breedname);
        Status = (TextView)view.findViewById(R.id.status);
        BreedingStatus = (TextView)view.findViewById(R.id.breedingstatus);
        OpenPeriod = (TextView)view.findViewById(R.id.openperiod);
        DryDays = (TextView)view.findViewById(R.id.drydays);
        MilkYield = (TextView)view.findViewById(R.id.milkyield);
        AvgYield = (TextView)view.findViewById(R.id.averageyield);
        PeakYield = (TextView)view.findViewById(R.id.peakyield);


        statuspic.setText(arra.get(0));
        File imgFile = new  File(arra.get(1));


        LotCode.setText(arra.get(2));
        OwnerCode.setText(arra.get(3));
        OwnerName.setText(arra.get(4));
        Age.setText(arra.get(5));
        BreedName.setText(arra.get(6));
        Status.setText(arra.get(7));
        BreedingStatus.setText(arra.get(8));
        OpenPeriod.setText(arra.get(9));
        DryDays.setText(arra.get(10));
        MilkYield.setText(arra.get(11));
        AvgYield.setText(arra.get(12));
        PeakYield.setText(arra.get(13));




        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            AnimalImage.setImageBitmap(myBitmap);
        }



    }
}
