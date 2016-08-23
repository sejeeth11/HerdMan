package provab.herdman.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import provab.herdman.R;
import provab.herdman.activity.AnimalRegistration;
import provab.herdman.activity.VillageMainActivity;
import provab.herdman.adapter.SpinnerAdapter;
import provab.herdman.beans.CattleBean;
import provab.herdman.constants.GlobalVar;
import provab.herdman.utility.DatabaseHelper;

/**
 * Created by PTBLR-1057 on 6/14/2016.
 */
public class CattleRegistration extends Fragment {

    final int PIC_CROP = 3;
    public int RESULT_LOAD_IMAGE = 100;
    LinearLayout herdNameLayout;
    LinearLayout lotNameLayout;
    LinearLayout ownerNameLayout;
    //    LinearLayout idNumberLayout;
    LinearLayout registrationDateLayout;
    LinearLayout sexLayout;
    LinearLayout speciesLayout;
    LinearLayout breedLayout;
    LinearLayout farmBreedLayout;
    TextView herdName;
    TextView lotName;
    TextView ownerName;
    EditText etIdNumber;
    TextView registrationDate;
    TextView sex;
    TextView species;
    TextView breed;
    TextView farmBreed;
    TextView cattleError;
    ImageView animalImage;
    RadioGroup birthDateGroup;
    EditText animalName;
    Button animalImageSelector;
    String picturePath;
    Button nextButton;
    boolean isInvalidId;
    EditText birthDateDateEdiText;
    //    EditText ageEdiText;
    Button next;
    Button previous;
    EditText ageMonth;
    EditText ageYear;
    private int REQUEST_IMAGE_CAPTURE = 2;

    Activity activity_village;
    Activity activity_animal;
    boolean flag=false;

    private SlideDateTimeListener listener1 = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            registrationDate.setText(GlobalVar.dialogeFormat.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };
    private SlideDateTimeListener listener2 = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            birthDateDateEdiText.setText(GlobalVar.dobDialog.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof VillageMainActivity) {
            this.activity_village =  activity;
            flag=true;
        }
        else if(activity instanceof AnimalRegistration){
            this.activity_animal =  activity;
            flag=false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cattle_registration, container, false);
        findViews(v);

        if(flag) {
            String idNumber = ((VillageMainActivity) getActivity()).getId();
            if (idNumber != null) {
                etIdNumber.setText("" + idNumber);
                etIdNumber.setSelection(etIdNumber.getText().length());
            }
        }

        return v;
    }

    public void findViews(View view) {
        herdNameLayout = (LinearLayout) view.findViewById(R.id.herdNameLayout);
        lotNameLayout = (LinearLayout) view.findViewById(R.id.lotNameLayout);
        ownerNameLayout = (LinearLayout) view.findViewById(R.id.ownerNameLayout);
//        idNumberLayout = (LinearLayout) view.findViewById(R.id.idNumberLayout);
        registrationDateLayout = (LinearLayout) view.findViewById(R.id.registrationDateLayout);
        sexLayout = (LinearLayout) view.findViewById(R.id.sexLayout);
        speciesLayout = (LinearLayout) view.findViewById(R.id.speciesLayout);
        breedLayout = (LinearLayout) view.findViewById(R.id.breedLayout);
        farmBreedLayout = (LinearLayout) view.findViewById(R.id.farmBreedLayout);
        herdName = (TextView) view.findViewById(R.id.herdName);
        lotName = (TextView) view.findViewById(R.id.lotName);
        ownerName = (TextView) view.findViewById(R.id.ownerName);
        etIdNumber = (EditText) view.findViewById(R.id.idNumber);
        registrationDate = (TextView) view.findViewById(R.id.registrationDate);
        sex = (TextView) view.findViewById(R.id.sex);
        species = (TextView) view.findViewById(R.id.species);
        breed = (TextView) view.findViewById(R.id.breed);
        farmBreed = (TextView) view.findViewById(R.id.farmBreed);
        cattleError = (TextView) view.findViewById(R.id.cattleError);
        animalImage = (ImageView) view.findViewById(R.id.animalImage);
        animalImageSelector = (Button) view.findViewById(R.id.animalImageSelector);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        next = (Button) view.findViewById(R.id.next);
        previous = (Button) view.findViewById(R.id.previous);
        birthDateGroup = (RadioGroup) view.findViewById(R.id.birthDateGroup);
        birthDateDateEdiText = (EditText) view.findViewById(R.id.birthDateDateEdiText);

        ageMonth = (EditText) view.findViewById(R.id.ageMonth);
        ageYear = (EditText) view.findViewById(R.id.ageYear);

        birthDateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.birthDate) {
                    birthDateDateEdiText.setEnabled(true);
                    ageMonth.setEnabled(false);
                    ageYear.setEnabled(false);
                } else if (checkedId == R.id.age) {
                    birthDateDateEdiText.setEnabled(false);
                    ageMonth.setEnabled(true);
                    ageYear.setEnabled(true);
                }
            }
        });

        birthDateDateEdiText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener2)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                                    //.setIs24HourTime(true)
                                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();
                }
            }
        });




        etIdNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showSelectIDDialog().show();

            }
        });











        etIdNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    new validator().execute(etIdNumber.getText().toString().trim());
                }
            }
        });

        animalName = (EditText) view.findViewById(R.id.animalName);

        herdNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showHerds();
                dialogShowInseminator.show();
            }
        });

        lotNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (herdName.getTag() != null) {
                    Dialog dialogShowInseminator = showLot();
                    dialogShowInseminator.show();
                } else {
                    Toast.makeText(getActivity(), "YOU HAVE NOT SELECTED THE HERD", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ownerNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lotName.getTag() != null) {
                    Dialog dialogShowInseminator = showOwner();
                    dialogShowInseminator.show();
                } else {
                    Toast.makeText(getActivity(), "YOU HAVE NOT SELECTED THE LOT", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrationDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                            .setListener(listener1)
                            .setInitialDate(new Date())
                            .setMaxDate(new Date())
                                    //.setIs24HourTime(true)
                                    //.setTheme(SlideDateTimePicker.HOLO_DARK)
                            .setIndicatorColor(Color.parseColor("#6c9c48"))
                            .build()
                            .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sexLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showSex();
                dialogShowInseminator.show();
            }
        });
        speciesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showSpecies();
                dialogShowInseminator.show();
            }
        });

        breedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (species.getTag() != null) {
                    Dialog dialogShowInseminator = showBreed();
                    dialogShowInseminator.show();
                } else {
                    Toast.makeText(getActivity(), "YOU HAVE NOT SELECTED SPECIES", Toast.LENGTH_SHORT).show();
                }
            }
        });
        farmBreedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogShowInseminator = showFarmBred();
                dialogShowInseminator.show();
            }
        });
        animalImageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources resources = getActivity().getResources();
                if (isInvalidId) {
                    Toast.makeText(getActivity(), "INVALID ID", Toast.LENGTH_LONG).show();
                    return;
                }

                if (herdName.getTag() == null || herdName.getText().toString().equals(resources.getString(R.string.cattle_registration_select_herd))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT HERD", Toast.LENGTH_LONG).show();
                    return;
                }
                if (lotName.getTag() == null || lotName.getText().toString().equals(resources.getString(R.string.cattle_registration_select_lot))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT LOT", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ownerName.getTag() == null || ownerName.getText().toString().equals(resources.getString(R.string.cattle_registration_select_owner))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT OWNER", Toast.LENGTH_LONG).show();
                    return;
                }

                if (etIdNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "PLEASE ENTER ID NUMBER", Toast.LENGTH_LONG).show();
                    return;
                }


                //////////////BIRTH DATE CALCULATION IS MISSING///////////

                if (registrationDate.getText().toString().equals(resources.getString(R.string.cattle_registration_registration_date))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT REGISTRATION DATE", Toast.LENGTH_LONG).show();
                    return;
                }

                if (sex.getTag() == null || sex.getText().toString().equals(resources.getString(R.string.cattle_registration_sex))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT SEX", Toast.LENGTH_LONG).show();
                    return;
                }

                if (species.getTag() == null || species.getText().toString().equals(resources.getString(R.string.cattle_registration_species))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT SPECIES", Toast.LENGTH_LONG).show();
                    return;
                }

                if (breed.getTag() == null || breed.getText().toString().equals(resources.getString(R.string.cattle_registration_breed))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT BREED", Toast.LENGTH_LONG).show();
                    return;
                }

                if (farmBreed.getTag() == null || farmBreed.getText().toString().equals(resources.getString(R.string.cattle_registration_farm_breed))) {
                    Toast.makeText(getActivity(), "PLEASE SELECT FARM BREED", Toast.LENGTH_LONG).show();
                    return;
                }

               /* if (animalName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "PLEASE ENTER ANIMAL NAME", Toast.LENGTH_LONG).show();
                    return;
                }*/

                CattleBean cattleBean;
                try {

                    if(flag){
                         cattleBean = ((VillageMainActivity) getActivity()).getCattleBean();
                    }
                    else{
                         cattleBean = ((AnimalRegistration) getActivity()).getCattleBean();
                    }
                    cattleBean.setRegistrationDate(GlobalVar.databaseFormat.format(GlobalVar.dialogeFormat.parse(registrationDate.getText().toString())));
                    cattleBean.setHerdId(herdName.getTag().toString());
                    cattleBean.setLotId(lotName.getTag().toString());
                    cattleBean.setOwnerId(ownerName.getTag().toString());
                    cattleBean.setAnimalId(etIdNumber.getText().toString());
                    //////BIRTHDATE MISSING//////
                    int selectedBirthDate = birthDateGroup.getCheckedRadioButtonId();
                    if (selectedBirthDate == R.id.birthDate) {
                        cattleBean.setBirthDate(birthDateDateEdiText.getText().toString());
                    } else if (selectedBirthDate == R.id.age) {
                        StringBuilder dateOfBirth = new StringBuilder();
                        dateOfBirth.append(ageYear.getText().toString().trim() + "-" + ageMonth.getText().toString().trim() + "-30");
                        cattleBean.setBirthDate(dateOfBirth.toString());
                    }
                    cattleBean.setSexId(sex.getTag().toString());
                    cattleBean.setSpeciesId(species.getTag().toString());
                    cattleBean.setBreedId(breed.getTag().toString());
                    cattleBean.setAnimalName(animalName.getText().toString());
                    if (picturePath != null ) {
                        cattleBean.setAnimalImagePath(picturePath);
                    }





                    if(flag){
                        ((VillageMainActivity) getActivity()).swipeViewPagerToNextScreen();
                    }
                    else{
                        ((AnimalRegistration) getActivity()).swipeViewPagerToNextScreen();
                    }



                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag) {
                    ((VillageMainActivity) getActivity()).swipeViewPagerToPreviousScreen();
                }
                else {
                    ((AnimalRegistration) getActivity()).swipeViewPagerToPreviousScreen();
                }
            }
        });
    }
/*    private Dialog showSelectIDDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);



        final ArrayList<String> lotList=DatabaseHelper.getDatabaseHelperInstance(getActivity()).getIdFromDetails(GlobalVar.VILLAGE_CODE,GlobalVar.OWNERS_CODE);

        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select ID");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etIdNumber.setText(lotList.get(position));


              /*  detailsInfoSpinner.setText(lotList.get(position));
                GlobalVar.ID_NUMBER=lotList.get(position);*/
            /*    dialog.dismiss();
            }
        });
        return dialog;
    }*/
    private Dialog showHerds() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getHerds();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Herd");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                herdName.setText(((TextView) view).getText());
                herdName.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showLot() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getLot(herdName.getTag().toString());
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Lot");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lotName.setText(((TextView) view).getText());
                lotName.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showOwner() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getOwner(lotName.getTag().toString());
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Owner");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ownerName.setText(((TextView) view).getText());
                ownerName.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSex() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSex();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Sex");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sex.setText(((TextView) view).getText());
                sex.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showSpecies() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getSpecies();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Species");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                species.setText(((TextView) view).getText());
                species.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog showBreed() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getBreed(species.getTag().toString());
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select Breed");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                breed.setText(((TextView) view).getText());
                breed.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }


    private Dialog showFarmBred() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        final ListView list = (ListView) dialog.findViewById(R.id.dialogListView);
        final TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        final ArrayList<String> lotList = DatabaseHelper.getDatabaseHelperInstance(getActivity()).getFarmbred();
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_content, lotList);
        title.setText("Select FarmBred");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                farmBreed.setText(((TextView) view).getText());
                farmBreed.setTag(view.getTag().toString());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cropOption(final Bitmap bitmap) {
        final CharSequence[] items = {"Crop", "No Thanks"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Crop Option");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Crop")) {
                    Uri tempUri = getImageUri(getActivity(), bitmap);
                    performCrop(tempUri);
                } else if (items[item].equals("No Thanks")) {
                    Uri tempUri = getImageUri(getActivity(), bitmap);
                    picturePath = getRealPathFromURI(tempUri);
                    animalImage.setImageBitmap(bitmap);
                    /*profileImgIv.setImageBitmap(bitmap);*/
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        picturePath = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(picturePath);
    }

    public String getRealPathFromURI(Uri uri) {
        // CALL THIS METHOD TO GET THE ACTUAL PATH
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(getActivity(), "Whoops - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            // Get the cursor
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
//            Toast.makeText(getActivity(), "FILE PATH = " + picturePath, Toast.LENGTH_SHORT).show();
            cursor.close();
            animalImage.setImageBitmap(BitmapFactory
                    .decodeFile(picturePath));
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cropOption(photo);
        } else if (requestCode == PIC_CROP && resultCode == Activity.RESULT_OK && null != data) {
            Bundle extras = data.getExtras();
            Bitmap thePic = extras.getParcelable("data");
            Uri tempUri = getImageUri(getActivity(), thePic);
            picturePath = getRealPathFromURI(tempUri);

            animalImage.setImageBitmap(thePic);

            /*profileImgIv.setImageBitmap(thePic);*/
        }
    }

    public class validator extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean isCattleRegisteres = DatabaseHelper.getDatabaseHelperInstance(getActivity()).isCattlePresent(params[0]);
            return isCattleRegisteres;
        }

        @Override
        protected void onPostExecute(Boolean isCattleRegisteres) {
            super.onPostExecute(isCattleRegisteres);
            if (isCattleRegisteres) {
                cattleError.setVisibility(View.VISIBLE);
                isInvalidId = true;
            } else {
                cattleError.setVisibility(View.GONE);
                isInvalidId = false;
            }
        }
    }

}
