package provab.herdman.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import provab.herdman.beans.ActionBean;
import provab.herdman.beans.CattleBean;
import provab.herdman.beans.DetailsBean;
import provab.herdman.beans.DiseaseBean;
import provab.herdman.beans.MedicineBean;
import provab.herdman.beans.MultiSelectItem;
import provab.herdman.beans.PurchaseDetailsBean;
import provab.herdman.beans.ReproductionBean;
import provab.herdman.beans.SearchBean;
import provab.herdman.beans.UserInfo;
import provab.herdman.constants.AnimalDetailsData;
import provab.herdman.constants.CommonData;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BREED_TABLE_NAME = "Breed";
    private static final String CALVING_TYPE_OPTION_TABLE_NAME = "CalvingTypeOption";
    private static final String CALVING_TYPE_TABLE_NAME = "Calvingtype";
    private static final String DATABASE_NAME = "HerdMan";
    private static final int DATABASE_VERSION = 1;
    private static final String DETAILS_TABLE_NAME = "Details";
    private static final String DEWORMING_TABLE_NAME = "Deworming";
    private static final String DIED_DETAILS_TABLE_NAME = "DiedDetails";
    private static final String DISEASE_TEST_TABLE_NAME = "DiseaseTest";
    private static final String DISPOSAL_TABLE_NAME = "Disposal";
    private static final String FARMBRED_TABLE_NAME = "Farmbred";
    private static final String FARM_DETAILS_TABLE_NAME = "FarmDetail";
    private static final String HERD_TABLE_NAME = "Herd";
    private static final String INSURANCE_TABLE_NAME = "Insurance";
    private static final String LOT_TABLE_NAME = "Lot";
    private static final String MEDICINE_LEDGER_TABLE_NAME = "Medicineledger";
    private static final String OLD_DETAILS_TABLE_NAME = "OldDetails";
    private static final String OPTION_TABLE_NAME = "Option";
    private static final String OWNER_TABLE_NAME = "Owner";
    private static final String PARAMETER_TABLE_NAME = "Parameter";
    private static final String PDII_TABLE_NAME = "PDII";
    private static final String PDI_TABLE_NAME = "PDI";
    private static final String PRODUCTION_TABLE_NAME = "Production";
    private static final String PURCHASE_DETAILS_TABLE_NAME = "PurchaseDetails";
    private static final String REPRODUCTION_TABLE_NAME = "Reproduction";
    private static final String SERVICES_TABLE_NAME = "Services";
    private static final String SEX_TABLE_NAME = "Sex";
    private static final String SIRE_TABLE_NAME = "Sire";
    private static final String SMS_LABEL_TABLE_NAME = "SMSLabel";
    private static final String SMS_LANGUAGE_TABLE_NAME = "SMSLanguage";
    private static final String SMS_SETTINGS = "SMSSetting";
    private static final String SYNC_STATUS = "SyncStatus";

    private static final String SOURCE_DETAILS_TABLE_NAME = "SourceDetails";
    private static final String SPECIES_TABLE_NAME = "Species";
    private static final String STAFF_TABLE_NAME = "Staff";
    private static final String STATUS_TABLE_NAME = "Status";
    private static final String TREATMENT_DETAILS_TABLE_NAME = "TreatmentDetails";
    private static final String TREATMENT_FOLLOW_TABLE_NAME = "TreatmentFollow";
    private static final String TREATMENT_TABLE_NAME = "Treatment";
    private static final String UNIT_TABLE_NAME = "Unit";
    private static final String USER_TABLE_FIELD_APPTYPE = "AppType";
    private static final String USER_TABLE_FIELD_COMPANYCODE = "CompanyCode";
    private static final String USER_TABLE_FIELD_GROUP = "GROUPS";
    private static final String USER_TABLE_FIELD_HERD = "HERD";
    private static final String USER_TABLE_FIELD_PASSWORD = "PASSWORD";
    private static final String USER_TABLE_FIELD_QRCODE = "QRCode";
    private static final String USER_TABLE_FIELD_UPDATEDAT = "UpdatedAt";
    private static final String USER_TABLE_FIELD_UPDATEDBY = "UpdatedBy";
    private static final String USER_TABLE_FIELD_USERCODE = "UserCode";
    private static final String USER_TABLE_FIELD_UUID = "UID";
    private static final String USER_TABLE_NAME = "User";
    private static final String VACCINE_DETAILS_TABLE_NAME = "VaccineDetails";
    static DatabaseHelper databaseHelper;
    String TABLE_BREED_COLUMN_BREED_CODE;
    String TABLE_BREED_COLUMN_NAME;
    String TABLE_BREED_COLUMN_SPECIES_CODE;
    String TABLE_CALVING_TYPE_COLUMN_CALVING_TYPE_CODE;
    String TABLE_CALVING_TYPE_COLUMN_NAME;
    String TABLE_CALVING_TYPE_OPTION_COLUMN_CALVING_TYPE;
    String TABLE_CALVING_TYPE_OPTION_COLUMN_CODE;
    String TABLE_CALVING_TYPE_OPTION_COLUMN_NAME;
    String TABLE_DETAILS_COLUMN_ALLOW_USE;
    String TABLE_DETAILS_COLUMN_BREED;
    String TABLE_DETAILS_COLUMN_BREED_STATUS;
    String TABLE_DETAILS_COLUMN_CALVING_DATE;
    String TABLE_DETAILS_COLUMN_DOB;
    String TABLE_DETAILS_COLUMN_HEAT_DATE;
    String TABLE_DETAILS_COLUMN_HERDNO;
    String TABLE_DETAILS_COLUMN_HLANAME;
    String TABLE_DETAILS_COLUMN_IDNO;
    String TABLE_DETAILS_COLUMN_LOTNO;
    String TABLE_DETAILS_COLUMN_NAME;
    String TABLE_DETAILS_COLUMN_PB_FLAG;
    String TABLE_DETAILS_COLUMN_REGISTRATION_DATE;
    String TABLE_DETAILS_COLUMN_SEX_FLAF;
    String TABLE_DETAILS_COLUMN_SPECIES;
    String TABLE_DETAILS_COLUMN_STATUS;
    String TABLE_DETAILS_COLUMN_UID;
    String TABLE_FARMBRED_COLUMN_FARMBRED;
    String TABLE_FARMBRED_COLUMN_ID;
    String TABLE_HERD_COLUMN_HERD_NAME;
    String TABLE_HERD_COLUMN_HERD_NO;
    String TABLE_LOT_COLUMN_HERD_NO;
    String TABLE_LOT_COLUMN_LOTNO;
    String TABLE_LOT_COLUMN_NAME;
    String TABLE_OPTION_COLUMN_FIELD_NAME;
    String TABLE_OPTION_COLUMN_FIELD_TYPE;
    String TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE1;
    String TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE2;
    String TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE3;
    String TABLE_OWNER_COLUMN_CODE;
    String TABLE_OWNER_COLUMN_LOT_NUMBER;
    String TABLE_OWNER_COLUMN_NAME;
    String TABLE_PDII_COLUMN_ID;
    String TABLE_PDII_COLUMN_PD;
    String TABLE_PDI_COLUMN_ID;
    String TABLE_PDI_COLUMN_PD;
    String TABLE_PRODUCTION_COLUMN_DATE;
    String TABLE_PRODUCTION_COLUMN_DAYS_COUNT;
    String TABLE_PRODUCTION_COLUMN_DAYS_TOTAL;
    String TABLE_PRODUCTION_COLUMN_EVENING_FAT;
    String TABLE_PRODUCTION_COLUMN_EVENING_MILK;
    String TABLE_PRODUCTION_COLUMN_EVENING_SCC;
    String TABLE_PRODUCTION_COLUMN_IDNO;
    String TABLE_PRODUCTION_COLUMN_LACTATION_TOTAL;
    String TABLE_PRODUCTION_COLUMN_MORING_FAT;
    String TABLE_PRODUCTION_COLUMN_MORNING_MILK;
    String TABLE_PRODUCTION_COLUMN_MORNING_SCC;
    String TABLE_PRODUCTION_COLUMN_NIGHT_MILK;
    String TABLE_REPRODUCTION_COLUMN_CALF_SEX;
    String TABLE_REPRODUCTION_COLUMN_CALVING_TYPE;
    String TABLE_REPRODUCTION_COLUMN_COMMENTS;
    String TABLE_REPRODUCTION_COLUMN_DATE_OF_CALVING;
    String TABLE_REPRODUCTION_COLUMN_DATE_OF_DRY;
    String TABLE_REPRODUCTION_COLUMN_DRY_HEAT_SEQUENCE;
    String TABLE_REPRODUCTION_COLUMN_DRY_REASON;
    String TABLE_REPRODUCTION_COLUMN_DRY_TREATMENT;
    String TABLE_REPRODUCTION_COLUMN_DTOFHEAT;
    String TABLE_REPRODUCTION_COLUMN_ENTRY;
    String TABLE_REPRODUCTION_COLUMN_IDNO;
    String TABLE_REPRODUCTION_COLUMN_INSIM;
    String TABLE_REPRODUCTION_COLUMN_PARITY;
    String TABLE_REPRODUCTION_COLUMN_PDI;
    String TABLE_REPRODUCTION_COLUMN_PDII;
    String TABLE_REPRODUCTION_COLUMN_PD_DATE;
    String TABLE_REPRODUCTION_COLUMN_REPRODUCTION_PROBLEM;
    String TABLE_REPRODUCTION_COLUMN_SERVICE;
    String TABLE_REPRODUCTION_COLUMN_SIREID;
    String TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE;
    String TABLE_SERVICES_COLUMN_SERVICE_ID;
    String TABLE_SERVICES_COLUMN_SERVICE_NAME;
    String TABLE_SEX_COLUMN_NAME;
    String TABLE_SEX_COLUMN_SEX_CODE;
    String TABLE_SIRE_COLUMN_SIRE_ID;
    String TABLE_SIRE_COLUMN_SIRE_NAME;
    String TABLE_SPECIES_COLUMN_NAME;
    String TABLE_SPECIES_COLUMN_SPECIES_CODE;
    String TABLE_STAFF_COLUMN_ID;
    String TABLE_STAFF_COLUMN_NAME;
    Context context;
    String[] fourthTypeTables;
    String[] thirdAndFourthTypeTables;
    String TASK ;
    String Doctor;
    String Farmer;
    String FromDays;
    String ToDays;
    String OrderNo;



  /*  CREATE TABLE [SMSSetting](
            [Task] [nvarchar](200) NULL,
            [Doctor] [bit] NULL,
            [Farmer] [bit] NULL,
            [FromDays] [int] NULL,
            [ToDays] [int] NULL,
            [OrderNo] [int] NULL
    )*/

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.thirdAndFourthTypeTables = new String[]{FARM_DETAILS_TABLE_NAME, HERD_TABLE_NAME, LOT_TABLE_NAME,
                STAFF_TABLE_NAME, OWNER_TABLE_NAME, SMS_LANGUAGE_TABLE_NAME, SOURCE_DETAILS_TABLE_NAME,
                SIRE_TABLE_NAME, BREED_TABLE_NAME, PARAMETER_TABLE_NAME,
                SMS_LABEL_TABLE_NAME, MEDICINE_LEDGER_TABLE_NAME,
                REPRODUCTION_TABLE_NAME, DETAILS_TABLE_NAME,VACCINE_DETAILS_TABLE_NAME,
                DEWORMING_TABLE_NAME, DISEASE_TEST_TABLE_NAME, PRODUCTION_TABLE_NAME,
                TREATMENT_TABLE_NAME, TREATMENT_DETAILS_TABLE_NAME, TREATMENT_FOLLOW_TABLE_NAME,
                DIED_DETAILS_TABLE_NAME, INSURANCE_TABLE_NAME, OPTION_TABLE_NAME, OLD_DETAILS_TABLE_NAME};


        this.fourthTypeTables = new String[]{DETAILS_TABLE_NAME,REPRODUCTION_TABLE_NAME, VACCINE_DETAILS_TABLE_NAME,
                DEWORMING_TABLE_NAME, DISEASE_TEST_TABLE_NAME, PRODUCTION_TABLE_NAME, TREATMENT_TABLE_NAME,
                TREATMENT_DETAILS_TABLE_NAME, TREATMENT_FOLLOW_TABLE_NAME, DIED_DETAILS_TABLE_NAME, INSURANCE_TABLE_NAME};

        this.TASK = "Task";
        this.Doctor = "Doctor";
        this.Farmer = "Farmer";
        this.FromDays = "FromDays";
        this.ToDays = "ToDays";
        this.OrderNo = "OrderNo";
        this.TABLE_LOT_COLUMN_LOTNO = "LotNo";
        this.TABLE_LOT_COLUMN_NAME = "name";
        this.TABLE_LOT_COLUMN_HERD_NO = "Herdno";
        this.TABLE_OWNER_COLUMN_CODE = "Code";
        this.TABLE_OWNER_COLUMN_NAME = "name";
        this.TABLE_OWNER_COLUMN_LOT_NUMBER = "LotNo";
        this.TABLE_DETAILS_COLUMN_IDNO = "IdNo";
        this.TABLE_DETAILS_COLUMN_LOTNO = "LotNo";
        this.TABLE_DETAILS_COLUMN_NAME = "Name";
        this.TABLE_DETAILS_COLUMN_BREED_STATUS = "BreedingStatus";
        this.TABLE_DETAILS_COLUMN_STATUS = STATUS_TABLE_NAME;
        this.TABLE_DETAILS_COLUMN_HEAT_DATE = "HeatDate";
        this.TABLE_DETAILS_COLUMN_CALVING_DATE = "CalvingDate";
        this.TABLE_DETAILS_COLUMN_HERDNO = "HerdNo";
        this.TABLE_DETAILS_COLUMN_HLANAME = "HLAname";
        this.TABLE_DETAILS_COLUMN_DOB = "DtOfBirth";
        this.TABLE_DETAILS_COLUMN_SEX_FLAF = "SexFlg";
        this.TABLE_DETAILS_COLUMN_SPECIES = SPECIES_TABLE_NAME;
        this.TABLE_DETAILS_COLUMN_BREED = BREED_TABLE_NAME;
        this.TABLE_DETAILS_COLUMN_PB_FLAG = "PBFlg";
        this.TABLE_DETAILS_COLUMN_REGISTRATION_DATE = "Registration_Date";
        this.TABLE_DETAILS_COLUMN_UID = USER_TABLE_FIELD_UUID;
        this.TABLE_DETAILS_COLUMN_ALLOW_USE = "AllowUser";
        this.TABLE_SERVICES_COLUMN_SERVICE_ID = "ID";
        this.TABLE_SERVICES_COLUMN_SERVICE_NAME = "Service";
        this.TABLE_SEX_COLUMN_SEX_CODE = "SexCode";
        this.TABLE_SEX_COLUMN_NAME = "Name";
        this.TABLE_SIRE_COLUMN_SIRE_ID = "SireId";
        this.TABLE_SIRE_COLUMN_SIRE_NAME = "Name";
        this.TABLE_STAFF_COLUMN_ID = "id";
        this.TABLE_STAFF_COLUMN_NAME = "Name";
        this.TABLE_PDI_COLUMN_ID = "ID";
        this.TABLE_PDI_COLUMN_PD = "PD";
        this.TABLE_PDII_COLUMN_ID = "ID";
        this.TABLE_PDII_COLUMN_PD = "PD";
        this.TABLE_OPTION_COLUMN_FIELD_TYPE = "Field_Type";
        this.TABLE_OPTION_COLUMN_FIELD_NAME = "Field_Name";
        this.TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE1 = "Dry off Reasons";
        this.TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE2 = "Dry Treatment";
        this.TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE3 = "Calving Problems";
        this.TABLE_CALVING_TYPE_COLUMN_CALVING_TYPE_CODE = "CalvingTypeCode";
        this.TABLE_CALVING_TYPE_COLUMN_NAME = "Name";
        this.TABLE_CALVING_TYPE_OPTION_COLUMN_CODE = "Code";
        this.TABLE_CALVING_TYPE_OPTION_COLUMN_NAME = "Name";
        this.TABLE_CALVING_TYPE_OPTION_COLUMN_CALVING_TYPE = "CalvingType";
        this.TABLE_PRODUCTION_COLUMN_DATE = HTTP.DATE_HEADER;
        this.TABLE_PRODUCTION_COLUMN_IDNO = "IdNo";
        this.TABLE_PRODUCTION_COLUMN_DAYS_TOTAL = "Days_total";
        this.TABLE_PRODUCTION_COLUMN_MORNING_MILK = "Mor";
        this.TABLE_PRODUCTION_COLUMN_EVENING_MILK = "Eve";
        this.TABLE_PRODUCTION_COLUMN_MORING_FAT = "MFAT";
        this.TABLE_PRODUCTION_COLUMN_EVENING_FAT = "EFAT";
        this.TABLE_PRODUCTION_COLUMN_NIGHT_MILK = "Night";
        this.TABLE_PRODUCTION_COLUMN_MORNING_SCC = "MSNF";
        this.TABLE_PRODUCTION_COLUMN_EVENING_SCC = "ESNF";
        this.TABLE_PRODUCTION_COLUMN_LACTATION_TOTAL = "Lactation_Total";
        this.TABLE_PRODUCTION_COLUMN_DAYS_COUNT = "Days_Count";
        this.TABLE_HERD_COLUMN_HERD_NO = "HerdNo";
        this.TABLE_HERD_COLUMN_HERD_NAME = "name";
        this.TABLE_SPECIES_COLUMN_SPECIES_CODE = "SpeciesCode";
        this.TABLE_SPECIES_COLUMN_NAME = "Name";
        this.TABLE_FARMBRED_COLUMN_ID = "ID";
        this.TABLE_FARMBRED_COLUMN_FARMBRED = FARMBRED_TABLE_NAME;
        this.TABLE_BREED_COLUMN_SPECIES_CODE = "Speciescode";
        this.TABLE_BREED_COLUMN_BREED_CODE = "BreedCode";
        this.TABLE_BREED_COLUMN_NAME = "Name";
        this.TABLE_REPRODUCTION_COLUMN_IDNO = "IdNo";
        this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT = "DtOfHeat";
        this.TABLE_REPRODUCTION_COLUMN_SERVICE = "Service";
        this.TABLE_REPRODUCTION_COLUMN_SIREID = "SireId";
        this.TABLE_REPRODUCTION_COLUMN_INSIM = "Insim";
        this.TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE = "Total_Dose";
        this.TABLE_REPRODUCTION_COLUMN_ENTRY = "ENTRY";
        this.TABLE_REPRODUCTION_COLUMN_PDI = "PD1";
        this.TABLE_REPRODUCTION_COLUMN_PDII = "PD2";
        this.TABLE_REPRODUCTION_COLUMN_PD_DATE = "Pddate";
        this.TABLE_REPRODUCTION_COLUMN_CALVING_TYPE = "C_Type";
        this.TABLE_REPRODUCTION_COLUMN_CALF_SEX = SEX_TABLE_NAME;
        this.TABLE_REPRODUCTION_COLUMN_DATE_OF_CALVING = "DtOfCalving";
        this.TABLE_REPRODUCTION_COLUMN_REPRODUCTION_PROBLEM = "RP";
        this.TABLE_REPRODUCTION_COLUMN_COMMENTS = "Comments";
        this.TABLE_REPRODUCTION_COLUMN_DATE_OF_DRY = "DtOfDry";
        this.TABLE_REPRODUCTION_COLUMN_DRY_REASON = "Dry_Reson";
        this.TABLE_REPRODUCTION_COLUMN_DRY_TREATMENT = "Dry_Treatment";
        this.TABLE_REPRODUCTION_COLUMN_PARITY = "Parity";
        this.TABLE_REPRODUCTION_COLUMN_DRY_HEAT_SEQUENCE = "HeatSeq";
        this.context = context;
    }

    public static DatabaseHelper getDatabaseHelperInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User(UID TEXT PRIMARY KEY, PASSWORD TEXT, GROUPS TEXT, HERD TEXT, CompanyCode TEXT ,AppType TEXT, UpdatedBy TEXT, UpdatedAt TEXT, UserCode TEXT ,QRCode TEXT)");
        db.execSQL("CREATE TABLE [Reproduction] (\n\t[CompanyCode]\tnvarchar(5) COLLATE NOCASE,\n\t[IdNo]\tnvarchar(20) COLLATE NOCASE,\n\t[Parity]\tinteger,\n\t[HeatSeq]\tinteger,\n\t[Service]\tinteger,\n\t[DtOfHeat]\tdatetime,\n\t[Insim]\tnvarchar(20) COLLATE NOCASE,\n\t[SireId]\tnvarchar(20) COLLATE NOCASE,\n\t[PD1]\tinteger,\n\t[RemPD1]\tnvarchar(35) COLLATE NOCASE,\n\t[PD2]\tinteger,\n\t[RemPD2]\tnvarchar(35) COLLATE NOCASE,\n\t[Pddate]\tdatetime,\n\t[DtOfCalving]\tdatetime,\n\t[DtOfDry]\tdatetime,\n\t[Dry_Reson]\tnvarchar(50) COLLATE NOCASE,\n\t[Dry_Treatment]\tnvarchar(50) COLLATE NOCASE,\n\t[Flg]\tnvarchar(20) COLLATE NOCASE,\n\t[Calf]\tbit,\n\t[Sex]\tnvarchar(20) COLLATE NOCASE,\n\t[C_Type]\tnvarchar(10) COLLATE NOCASE,\n\t[ROP]\tbit,\n\t[Comments]\tnvarchar(100) COLLATE NOCASE,\n\t[RP]\tnvarchar COLLATE NOCASE,\n\t[ENTRY]\tnvarchar(50) COLLATE NOCASE,\n\t[Total_Dose]\tinteger,\n\t[Abortion_Seq]\tinteger DEFAULT 0,\n\t[Vaccine]\tinteger,\n\t[CretationDate]\tdatetime COLLATE NOCASE DEFAULT (CURRENT_TIMESTAMP),\n\t[SyncStatus]\tinteger default  NULL,\n\t[SyncID] \tinteger,\n\t[UID]\tinteger default NULL\n\n)");





        db.execSQL("CREATE TABLE Calvingtype(CalvingTypeCode TEXT, Name TEXT)");
        db.execSQL("CREATE TABLE Disposal(DisposalCode TEXT, Name TEXT)");
        db.execSQL("CREATE TABLE Farmbred(ID TEXT, Farmbred TEXT)");
        db.execSQL("CREATE TABLE PDI(ID TEXT, PD TEXT)");
        db.execSQL("CREATE TABLE PDII(ID TEXT, PD TEXT)");
        db.execSQL("CREATE TABLE [Services](\n\t[ID] [int] NOT NULL,\n\t[Service] [nvarchar](20) NOT NULL\n)");
        db.execSQL("CREATE TABLE Sex(SexCode TEXT, Name TEXT)");
        db.execSQL("CREATE TABLE Species(SpeciesCode TEXT, Name TEXT)");
        db.execSQL("CREATE TABLE Status(StatusCode TEXT, Name TEXT)");
        db.execSQL("CREATE TABLE Unit(Code TEXT, Name TEXT)");
        db.execSQL("CREATE TABLE [FarmDetail] (\n\t[own_first]\tnvarchar(30) COLLATE NOCASE,\n\t[own_middle]\tnvarchar(30) COLLATE NOCASE,\n\t[own_last]\tnvarchar(30) COLLATE NOCASE,\n\t[com_name]\tnvarchar(30) COLLATE NOCASE,\n\t[com_add]\tnvarchar COLLATE NOCASE,\n\t[tel_no]\tnvarchar(12) COLLATE NOCASE,\n\t[EmailID1]\tnvarchar(30) COLLATE NOCASE,\n\t[EmailID2]\tnvarchar(30) COLLATE NOCASE,\n\t[UID]\tinteger,\n\t[AllowUSer]\tbit,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [Herd] (\n\t[CompanyCode]\tinteger DEFAULT 1,\n\t[EIDNO]\tinteger,\n\t[HerdNo]\tnvarchar(30) COLLATE NOCASE,\n\t[name]\tnvarchar(50) COLLATE NOCASE,\n\t[Note]\tnvarchar COLLATE NOCASE,\n\t[SyncServer]\tbit,\n\t[staffid]\tnvarchar(50) COLLATE NOCASE,\n\t[ContactPerson]\tnvarchar(100) COLLATE NOCASE,\n\t[MobileNo]\tnvarchar(20) COLLATE NOCASE,\n\t[ISsuspend]\tbit,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [Lot] (\n\t[Herdno]\tnvarchar(30) COLLATE NOCASE,\n\t[LotNo]\tnvarchar(30) COLLATE NOCASE,\n\t[name]\tnvarchar(50) COLLATE NOCASE,\n\t[Note]\tnvarchar COLLATE NOCASE,\n\t[ISsuspend]\tbit,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [Staff] (\n\t[id]\tnvarchar(50) COLLATE NOCASE,\n\t[Status]\tnvarchar(30) COLLATE NOCASE,\n\t[Name]\tnvarchar(50) COLLATE NOCASE,\n\t[Address]\tnvarchar(50) COLLATE NOCASE,\n\t[telno]\tnvarchar(12) COLLATE NOCASE,\n\t[Qualification]\tnvarchar(50) COLLATE NOCASE,\n\t[MaxBalance]\tnumeric,\n\t[Payment_Type]\tnvarchar(50) COLLATE NOCASE,\n\t[Basic]\tnumeric,\n\t[ISSendSMS]\tbit,\n\t[LanguageCode]\tinteger,\n\t[SyncID]\tinteger NOT NULL,\n\t[VOFLAG]\tbit,\n\t[UID]\tinteger,\n\t[AllowUSer]\tbit DEFAULT 0,\n\t[Suspend]\tbit\n\n)");
        db.execSQL("CREATE TABLE [Owner] (\n\t[HerdNo]\tnvarchar(50) COLLATE NOCASE,\n\t[LotNo]\tnvarchar(50) COLLATE NOCASE,\n\t[Code]\tnvarchar(40) COLLATE NOCASE,\n\t[name]\tnvarchar(50) COLLATE NOCASE,\n\t[Middle_Name]\tnvarchar(50) COLLATE NOCASE,\n\t[Last_Name]\tnvarchar(50) COLLATE NOCASE,\n\t[Land]\tnvarchar(50) COLLATE NOCASE,\n\t[Phno]\tnvarchar(11) COLLATE NOCASE,\n\t[Education]\tnvarchar(20) COLLATE NOCASE,\n\t[Address]\tnvarchar(200) COLLATE NOCASE,\n\t[Irrigation]\tnvarchar(20) COLLATE NOCASE,\n\t[Address1]\tnvarchar COLLATE NOCASE,\n\t[State]\tnvarchar(20) COLLATE NOCASE,\n\t[District]\tnvarchar(20) COLLATE NOCASE,\n\t[Taluka]\tnvarchar(20) COLLATE NOCASE,\n\t[Village]\tnvarchar(20) COLLATE NOCASE,\n\t[Mobile_No]\tinteger,\n\t[Photo]\tnvarchar COLLATE NOCASE,\n\t[ISSendSMS]\tbit,\n\t[LanguageCode]\tinteger,\n\t[ai_person_mobile]\tnvarchar(20) COLLATE NOCASE,\n\t[Manager_mobile]\tnvarchar(20) COLLATE NOCASE,\n\t[Cofs29]\tnvarchar(20) COLLATE NOCASE,\n\t[Co4]\tnvarchar(20) COLLATE NOCASE,\n\t[VOCode]\tnvarchar(50) COLLATE NOCASE,\n\t[paravetCode]\tnvarchar(50) COLLATE NOCASE,\n\t[IsSuspend]\tbit,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [SMSLanguage] (\n\t[KeyID]\tinteger NOT NULL,\n\t[Language]\tnvarchar(50) COLLATE NOCASE,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [SourceDetails] (\n\t[AgentId]\tinteger,\n\t[Name]\tnvarchar(35) COLLATE NOCASE,\n\t[Address]\tnvarchar COLLATE NOCASE,\n\t[TelNo]\tnvarchar(50) COLLATE NOCASE,\n\t[Stn]\tnvarchar(50) COLLATE NOCASE,\n\t[Suspend]\tbit NOT NULL,\n\t[UID]\tinteger,\n\t[AllowUSer]\tbit,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [Sire] (\n\t[SireId]\tnvarchar(40) COLLATE NOCASE,\n\t[Name]\tnvarchar(40) COLLATE NOCASE,\n\t[SourceId]\tfloat,\n\t[Breed]\tinteger,\n\t[DtOfBirth]\tdatetime,\n\t[BirthWt]\tnumeric,\n\t[Cbflg]\tinteger,\n\t[FarmId]\tnvarchar(50) COLLATE NOCASE,\n\t[SID]\tnvarchar(40) COLLATE NOCASE,\n\t[SireIndex]\tnvarchar(40) COLLATE NOCASE,\n\t[MID]\tnvarchar(40) COLLATE NOCASE,\n\t[MotherMilk]\tnvarchar(40) COLLATE NOCASE,\n\t[Min_Stock]\tinteger,\n\t[NAFlag]\tnvarchar(1) COLLATE NOCASE,\n\t[UID]\tinteger,\n\t[AllowUSer]\tbit DEFAULT 0,\n\t[Suspend]\tsmallint,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [Breed] (\n\t[Speciescode]\tinteger NOT NULL,\n\t[BreedCode]\tinteger,\n\t[Name]\tnvarchar(30) NOT NULL COLLATE NOCASE,\n\t[BreedType]\tnvarchar(15) COLLATE NOCASE,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [Parameter] (\n\t[Breed]\tinteger,\n\t[Heat]\tinteger,\n\t[HeatVar]\tinteger,\n\t[PD1]\tinteger,\n\t[PD1Var]\tinteger,\n\t[PD2]\tinteger,\n\t[PD2Var]\tinteger,\n\t[Dried]\tinteger,\n\t[DriedVar]\tinteger,\n\t[InsuranceVar]\tinteger,\n\t[MilkInterval]\tinteger,\n\t[HeiferMaleAge]\tinteger,\n\t[HeiferFemaleAge]\tinteger,\n\t[MilkProd]\tnumeric,\n\t[Age]\tnumeric,\n\t[NoAI]\tnumeric,\n\t[Lactation]\tnumeric,\n\t[Firstheat]\tnumeric,\n\t[hundays]\tnumeric,\n\t[STRAWSTOCK]\tnumeric,\n\t[SIREPREGRATE]\tnumeric,\n\t[REPBRED]\tinteger,\n\t[ConCalv]\tinteger,\n\t[CALVINTER]\tinteger,\n\t[LACLEN]\tinteger,\n\t[SNF]\tnumeric,\n\t[FAT]\tnumeric,\n\t[PROTEIN]\tnumeric,\n\t[LACTOSE]\tnumeric,\n\t[Exp_First_Service]\tinteger,\n\t[AVG_Yield]\tnumeric,\n\t[Peak_Yield]\tnumeric,\n\t[Peak_Days]\tinteger,\n\t[Open_Period]\tinteger,\n\t[Dry_Period]\tinteger,\n\t[tfdmilk]\tinteger,\n\t[TF_Days]\tinteger,\n\t[Breed_Type]\tnvarchar(50) COLLATE NOCASE,\n\t[CalvDays]\tinteger,\n\t[SyncID]\tinteger NOT NULL\n\n)");
        db.execSQL("CREATE TABLE [SMSLabel] (\n\t[English]\tnvarchar(200) COLLATE NOCASE,\n\t[Tamil]\tnvarchar COLLATE NOCASE,\n\t[KAnnada]\tnvarchar COLLATE NOCASE,\n\t[Marati]\tnvarchar COLLATE NOCASE,\n\t[SyncID]\tinteger NOT NULL\n\n)");

        db.execSQL("CREATE TABLE [Medicineledger] (\n\t[CompanyCode]\tnvarchar(50) COLLATE NOCASE,\n\t[Name]\tnvarchar(50) COLLATE NOCASE,\n\t[ShortName]\tnvarchar(50) COLLATE NOCASE,\n\t[CompanyName]\tnvarchar(50) COLLATE NOCASE,\n\t[Type]\tnvarchar(30) COLLATE NOCASE,\n\t[GroupCode]\tnvarchar(20) COLLATE NOCASE,\n\t[Route]\tnvarchar(10) COLLATE NOCASE,\n\t[Dose]\tnvarchar(10) COLLATE NOCASE,\n\t[Units]\tnvarchar(5) COLLATE NOCASE,\n\t[Min_Stock]\tnumeric,\n\t[Suspend]\tbit,\n\t[UID]\tinteger,\n\t[AllowUSer]\tbit,\n\t[WithdrawPeriod]\tinteger,\n\t[Code]\tinteger,\n\t[SyncID]\tinteger NOT NULL\n\n)");




        db.execSQL("CREATE TABLE [Details] (\n\t[CompanyCode]\tnvarchar(5) COLLATE NOCASE,\n\t[HerdNo]\tnvarchar(50) COLLATE NOCASE,\n\t[LotNo]\tnvarchar(50) COLLATE NOCASE,\n\t[IdNo]\tnvarchar(50) COLLATE NOCASE,\n\t[SexFlg]\tinteger,\n\t[Species]\tinteger,\n\t[Breed]\tinteger,\n\t[Status]\tinteger,\n\t[DtOfBirth]\tdatetime,\n\t[Name]\tnvarchar(50) COLLATE NOCASE,\n\t[AgeFlg]\tinteger,\n\t[BirthWt]\tnumeric,\n\t[SalvFlg]\tbit,\n\t[GroupFlg]\tnvarchar(20) COLLATE NOCASE,\n\t[PBFlg]\tinteger,\n\t[CatCalfFlg]\tnvarchar(20) COLLATE NOCASE,\n\t[HLAname]\tnvarchar(50) COLLATE NOCASE,\n\t[SensorNo]\tnvarchar(25) COLLATE NOCASE,\n\t[Photo]\tnvarchar(50) COLLATE NOCASE,\n\t[Parity]\tinteger,\n\t[Disposalflg]\tinteger,\n\t[sel_cancel]\tbit,\n\t[Insurance_No]\tnvarchar(50) COLLATE NOCASE,\n\t[AI_Tagno]\tnvarchar(50) COLLATE NOCASE,\n\t[Current_Parity]\tinteger,\n\t[Registration_Date]\tdatetime,\n\t[Market_Value]\tnumeric,\n\t[No_rings]\tinteger,\n\t[Rearing_Purpose]\tnvarchar(50) COLLATE NOCASE,\n\t[Color]\tnvarchar(50) COLLATE NOCASE,\n\t[Horn_distance]\tinteger,\n\t[InsurNo]\tinteger,\n\t[Policy_period]\tinteger,\n\t[Transaction_Date]\tdatetime,\n\t[Hypothecation]\tnvarchar(50) COLLATE NOCASE,\n\t[Hypothecation_No]\tnvarchar(50) COLLATE NOCASE,\n\t[Doctor]\tnvarchar(50) COLLATE NOCASE,\n\t[Send_CMS]\tnvarchar(10) COLLATE NOCASE,\n\t[ImageContent]\tblob(2147483647),\n\t[Insurance_Flag]\tchar(10) COLLATE NOCASE,\n\t[BreedingStatus]\tnvarchar(20) COLLATE NOCASE,\n\t[HeatDate]\tdatetime,\n\t[HeatSeq]\tinteger,\n\t[AbortionSeq]\tinteger,\n\t[PDDate]\tdatetime,\n\t[PDI]\tinteger,\n\t[PDII]\tinteger,\n\t[CalvingDate]\tdatetime,\n\t[DryDate]\tdatetime,\n\t[MilkDate]\tdatetime,\n\t[LastMilk]\tfloat,\n\t[TotalMilk]\tnumeric,\n\t[SelectFlag]\tinteger,\n\t[SelectRemarks]\tnvarchar COLLATE NOCASE,\n\t[SelectColor]\tnvarchar(50) COLLATE NOCASE,\n\t[LastSire]\tnvarchar(50) COLLATE NOCASE,\n\t[DisposalRemarks]\tnvarchar(50) COLLATE NOCASE,\n\t[Sire]\tnvarchar(30) COLLATE NOCASE,\n\t[Dam]\tnvarchar(30) COLLATE NOCASE,\n\t[paternalSire]\tnvarchar(30) COLLATE NOCASE,\n\t[PaternalDam]\tnvarchar(30) COLLATE NOCASE,\n\t[NewownerCode]\tnvarchar(50) COLLATE NOCASE,\n\t[OwnerID]\tnvarchar(50) COLLATE NOCASE,\n\t[VirtualLot]\tnvarchar(30) COLLATE NOCASE,\n\t[UID]\tinteger,\n\t[AllowUser]\tbit,\n\t[ISSuspend]\tbit,\n\t[SyncID]\tinteger,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +

                ")");



       // db.execSQL("CREATE TABLE [VaccineDetails] (\n\t[DoneBy]\tnvarchar(20) COLLATE NOCASE,\n\t\n\t[Idno]\tnvarchar(20) COLLATE NOCASE,\n\t[vaccinename]\tnvarchar(50) COLLATE NOCASE,\n\t[VACCINE]\tnvarchar(50) COLLATE NOCASE,\n\t[date]\tdatetime,\n\t[source]\tnvarchar(50) COLLATE NOCASE,\n\t[Batchno]\tnvarchar(50) COLLATE NOCASE,\n\t[Dose]\tnumeric,\n\t[Daterev]\tdatetime,\n\t[ROUTE]\tnvarchar(50) COLLATE NOCASE,\n\t[Cost]\tnumeric,\n\t[CreatedDate]\tdatetime COLLATE NOCASE DEFAULT (CURRENT_TIMESTAMP),\n\t[SyncID]\tinteger  NULL\n\n)");

        db.execSQL("CREATE TABLE [VaccineDetails] (\n\t[DoneBy]\tnvarchar(20) COLLATE NOCASE,\n\t\n\t[Idno]\tnvarchar(20) COLLATE NOCASE,\n\t[vaccinename]\tnvarchar(50) COLLATE NOCASE,\n\t[VACCINE]\tnvarchar(50) COLLATE NOCASE,\n\t[date]\tdatetime,\n\t[source]\tnvarchar(50) COLLATE NOCASE,\n\t[Batchno]\tnvarchar(50) COLLATE NOCASE,\n\t[Dose]\tnumeric,\n\t[Daterev]\tdatetime,\n\t[ROUTE]\tnvarchar(50) COLLATE NOCASE,\n\t[Cost]\tnumeric,\n\t[CreatedDate]\tdatetime COLLATE NOCASE DEFAULT (CURRENT_TIMESTAMP),\n\t[SyncID]\tinteger default  NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +
                "\n)");

        db.execSQL("CREATE TABLE [Deworming] (\n" +
                "\t[DoneBy]\tnvarchar(20) COLLATE NOCASE,\n\t[Idno]\tnvarchar(20) COLLATE NOCASE,\n\t[Deworm]\tnvarchar(50) COLLATE NOCASE,\n\t[Dewormer]\tnvarchar(50) COLLATE NOCASE,\n\t[date]\tdatetime,\n\t[source]\tnvarchar(50) COLLATE NOCASE,\n\t[batchno]\tnvarchar(50) COLLATE NOCASE,\n\t[Dose]\tnumeric,\n\t[Daterev]\tdatetime,\n\t[ROUTE]\tnvarchar(50) COLLATE NOCASE,\n\t[Cost]\tnumeric,\n\t[SyncID]\tinteger default NULL,\n\t[SyncStatus]\tinteger default  NULL\n\n)");


        db.execSQL("CREATE TABLE [DiseaseTest] (\n\t[Idno]\tnvarchar(20) COLLATE NOCASE,\n\t[date]\tdatetime,\n\t[doctor]\tnvarchar(50) COLLATE NOCASE,\n\t[Disease]\tnvarchar(50) COLLATE NOCASE,\n\t[testname]\tnvarchar(50) COLLATE NOCASE,\n\t[labname]\tnvarchar(50) COLLATE NOCASE,\n\t[observation]\tnvarchar COLLATE NOCASE,\n\t[Result]\tnvarchar COLLATE NOCASE,\n\t[daterev]\tdatetime,\n\t[SyncID]\tinteger NOT NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +
                ")");

        db.execSQL("CREATE TABLE [Production] (\n\t[IdNo]\tnvarchar(20) COLLATE NOCASE,\n\t[Date]\tdatetime,\n\t[Parity]\tinteger,\n\t[Mor]\tnumeric DEFAULT 0,\n\t[Eve]\tnumeric DEFAULT 0,\n\t[Night]\tnumeric DEFAULT 0,\n\t[MidNight]\tnumeric DEFAULT 0,\n\t[IdCode]\tnvarchar(6) COLLATE NOCASE,\n\t[Fat]\tnumeric,\n\t[Snf]\tnumeric,\n\t[Lactose]\tnumeric,\n\t[Protein]\tnumeric,\n\t[FatC]\tnumeric,\n\t[SnfC]\tnumeric,\n\t[LactoseC]\tnumeric,\n\t[ProteinC]\tnumeric,\n\t[CumTotal]\tnumeric,\n\t[Lactation_Total]\tnumeric,\n\t[Days_Count]\tinteger,\n\t[Solidsc]\tnumeric,\n\t[Solids]\tnumeric,\n\t[EFAT]\tnumeric,\n\t[ESNF]\tnumeric,\n\t[NFAT]\tnumeric,\n\t[NSNF]\tnumeric,\n\t[MFAT]\tnumeric,\n\t[MSNF]\tnumeric,\n\t[CLR]\tnumeric,\n\t[CFU]\tnumeric,\n\t[Acidity]\tnumeric,\n\t[OfficialMilk]\tnumeric,\n\t[FDFlag]\tnvarchar(1) COLLATE NOCASE,\n\t[Days_total]\tnumeric,\n\t[SyncID]\tinteger\n,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +
                ")");

        db.execSQL("CREATE TABLE [Treatment] (\n\t[IdNo]\tnvarchar(20) COLLATE NOCASE,\n\t[FromDate]\tdatetime,\n\t[ToDate]\tdatetime,\n\t[seq]\tinteger,\n\t[Num]\tinteger,\n\t[DtOfTreat]\tdatetime,\n\t[Complain]\tnvarchar(150) COLLATE NOCASE,\n\t[Temp]\tinteger,\n\t[Pulse]\tinteger,\n\t[Resp]\tinteger,\n\t[System]\tnvarchar COLLATE NOCASE,\n\t[Diagnosis]\tnvarchar COLLATE NOCASE,\n\t[DiagFlg]\tnvarchar(50) COLLATE NOCASE,\n\t[Observ]\tnvarchar(150) COLLATE NOCASE,\n\t[LabTest]\tnvarchar(150) COLLATE NOCASE,\n\t[rx]\tnvarchar(150) COLLATE NOCASE,\n\t[AntiBio]\tnvarchar(25) COLLATE NOCASE,\n\t[days]\tinteger,\n\t[AntiBioFlg]\tnvarchar(2) COLLATE NOCASE,\n\t[OCFlg]\tnvarchar(20) COLLATE NOCASE,\n\t[Doctor]\tnvarchar(50) COLLATE NOCASE,\n\t[Cost]\tnumeric,\n\t[Follow_UP_Date]\tdatetime,\n\t[SyncID]\tinteger default NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +

                ")");

        db.execSQL("CREATE TABLE [TreatmentDetails] (\n\t[idno]\tnvarchar(50) COLLATE NOCASE,\n\t[date]\tdatetime,\n\t[no]\tinteger,\n\t[genname]\tnvarchar(50) COLLATE NOCASE,\n\t[tradename]\tnvarchar(50) COLLATE NOCASE,\n\t[doserate]\tnumeric,\n\t[totdose]\tnumeric,\n\t[route]\tnvarchar(10) COLLATE NOCASE,\n\t[batchno]\tnvarchar(40) COLLATE NOCASE,\n\t[SyncID]\tinteger NOT NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +

                ")");


        db.execSQL("CREATE TABLE [TreatmentFollow] (\n\t[IDNO]\tnvarchar(20) COLLATE NOCASE,\n\t[Date]\tdatetime,\n\t[FollowDate]\tdatetime,\n\t[SyncID]\tinteger NOT NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +

                ")");



        db.execSQL("CREATE TABLE [DiedDetails] (\n\t[CompanyCode]\tnvarchar(5) COLLATE NOCASE,\n\t[Herdno]\tnvarchar(50) COLLATE NOCASE,\n\t[Lotno]\tnvarchar(50) COLLATE NOCASE,\n\t[OldIdno]\tnvarchar(20) COLLATE NOCASE,\n\t[Idno]\tnvarchar(50) COLLATE NOCASE,\n\t[Date]\tdatetime,\n\t[Remark]\tnvarchar(100) COLLATE NOCASE,\n\t[Flag]\tnvarchar(20) COLLATE NOCASE,\n\t[Reason]\tnvarchar(25) COLLATE NOCASE,\n\t[SoldTo]\tnvarchar(50) COLLATE NOCASE,\n\t[Sprice]\tnumeric,\n\t[SyncID]\tinteger default NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +
                ")");




        db.execSQL("CREATE TABLE [Insurance] (\n\t[CompanyCode]\tnvarchar(5) COLLATE NOCASE,\n\t[Idno]\tnvarchar(20) COLLATE NOCASE,\n\t[PolicyNo]\tnvarchar(30) COLLATE NOCASE,\n\t[Value]\tnumeric,\n\t[CompanyName]\tnvarchar(30) COLLATE NOCASE,\n\t[IssueDate]\tdatetime,\n\t[DueDate]\tdatetime,\n\t[AgentName]\tnvarchar(50) COLLATE NOCASE,\n\t[ClaimDate]\tdatetime,\n\t[Reason]\tnvarchar(100) COLLATE NOCASE,\n\t[Status]\tnvarchar(10) COLLATE NOCASE,\n\t[SettlementDate]\tdatetime,\n\t[AmtRecd]\tnumeric,\n\t[SyncID]\tinteger default NULL,\n" +
                "\t[SyncStatus]\tinteger default  NULL\n" +
                "\n" +
                ")");

        db.execSQL("CREATE TABLE [CalvingTypeOption](\n [Code] [int] NULL,\n [Name] [nvarchar](50) NULL,\n [CalvingType] [int] NULL\n)");

        db.execSQL("CREATE TABLE [Option](\n[Days] [nvarchar](50) NULL,\n [Field_Type] [nvarchar](255) NULL,\n [Field_Name] [nvarchar](255) NULL,\n [SyncID] [int] IDENTITY(1,1) NOT NULL\n)");

        db.execSQL("CREATE TABLE [OldDetails](\n [IdNo] [nvarchar](50) NULL,\n [Date] [datetime] NULL,\n [Parity] [int] NULL,\n [milk] [money] NULL,\n [Fat] [money] NULL,\n [Snf] [money] NULL,\n [Lactose] [money] NULL,\n [Protein] [money] NULL,\n [CumTotal] [money] NULL,\n [Days_Total] [money] NULL,\n [Lactation_Total] [money] NULL,\n [Days_Count] [int] NULL,\n [FatC] [money] NULL,\n [SnfC] [money] NULL,\n [LactoseC] [money] NULL,\n [ProteinC] [money] NULL,\n [Solidsc] [money] NULL,\n [Solids] [money] NULL,\n [SyncID] [int] IDENTITY(1,1) NOT NULL\n)");

        db.execSQL("CREATE TABLE [PurchaseDetails](\n [CompanyCode] [nvarchar](5) NULL,\n [IdNo] [nvarchar](20) NULL,\n [DtOfPurch] [datetime] NULL,\n [SourceId] [int] NULL,\n [Cost] [money] NULL,\n [AgeFlg] [nvarchar](2) NULL,\n [Status] [nvarchar](3) NULL,\n [LactationP] [money] NULL,\n [FatP] [money] NULL,\n [ParityP] [int] NULL,\n [LactationC] [money] NULL,\n [FatC] [money] NULL,\n [DtOfCalvC] [datetime] NULL,\n [ParityC] [int] NULL\n)");

        db.execSQL("CREATE TRIGGER AfterUpdateReproduction  AFTER  Update \nON Reproduction\nBEGIN\nUpdate details SET Status=6 WHERE IDNO=new.idno AND Status is null  AND new.PD2=3 ;\n\nUpdate details  SET Status=1,BreedingStatus='Pregnant',HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=new.heatseq,\nPDDate=new.pddate,PDI=new.pd1,PDII=new.pd2  WHERE IDNO=new.idno AND Status=6 and new.pd2=3;\n\nUpdate details  SET Status=3,BreedingStatus='Pregnant',HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=new.heatseq,\nPDDate=new.pddate,PDI=new.pd1,PDII=new.pd2 WHERE IDNO=new.idno AND Status=2 and new.pd2=3;\n\nUpdate details \tSET Status=5,BreedingStatus='Pregnant',HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=new.heatseq,\nPDDate=new.pddate,PDI=new.pd1,PDII=new.pd2 WHERE IDNO=new.idno AND Status=4 and new.pd2=3;\n\nUpdate details SET BreedingStatus='Aborted', HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=new.heatseq,\nAbortionSeq=new.abortion_seq,PDDate=new.pddate,PDI=new.pd1,PDII=new.pd2 WHERE IDNO=new.idno  and new.pd2=4;\n\nUpdate details  SET AbortionSeq=new.abortion_seq,PDDate=new.pddate,PDI=new.pd1,PDII=new.pd2\nWHERE IDNO=new.idno and new.pd2<>3;\n\n\nUpdate details SET Status=4,CalvingDate=new.dtofcalving,breedingstatus='Open Unbred'  WHERE IDNO=new.idno AND Status=1 AND New.dtofcalving<>'';\n\nUpdate details SET Status=5,CalvingDate=new.dtofcalving,breedingstatus='Open Unbred' WHERE IDNO=new.idno AND Status=3 AND New.dtofcalving<>'';\n\n\n\nUpdate details SET Status=2, DryDate=new.DtofDry WHERE IDNO=new.idno AND Status=4 AND New.dtofDry<>'';\n\nUpdate details SET Status=3,DryDate=new.DtofDry WHERE IDNO=new.idno AND Status=5 AND New.dtofDry<>'';\n\n\n\nEND");

        db.execSQL("CREATE TRIGGER AfterInsertAI  AFTER  Insert  \nON Reproduction\n\n\n\n"+
                " BEGIN\n\n\nUpdate details  SET Parity=1,HeatSeq=1,Current_Parity=1,HeatDate=new.dtofheat," +
                "BreedingStatus='Open Bred',LastSire=new.sireid\nWHERE IDNO=new.idno AND " +
                "BreedingStatus='Open Bred' AND Current_Parity  ISnull AND Parity isnull  AND (new.PD2<>3  or " +
                "new.PD2 IS NULL);\n\nUpdate details  SET Current_Parity=(SELECT Current_Parity FROM DETAILS WHERE  " +
                "IDNO=new.IDNO AND BreedingStatus='Open Bred') ,\nHeatSeq=(SELECT HeatSeq FROM DETAILS WHERE  " +
                "IDNO=new.IDNO AND BreedingStatus='Open Bred') +1,HeatDate=new.dtofheat,BreedingStatus='Open Bred'," +
                "LastSire=new.sireid\nWHERE IDNO=new.idno AND BreedingStatus='Open Bred' AND (new.PD2<>3  or " +
                "new.PD2 IS NULL) ;\n\nUpdate details SET  Current_Parity=(SELECT Current_Parity " +
                "FROM DETAILS WHERE  IDNO=new.IDNO AND BreedingStatus='Open Unbred') +1\n," +
                "HeatSeq=1,HeatDate=new.dtofheat,BreedingStatus='Open Bred',LastSire=new.sireid\n" +
                "WHERE IDNO=new.idno AND BreedingStatus='Open Unbred' AND (new.PD2<>3  or new.PD2 IS NULL);\n\n" +
                "UPDATE   Reproduction SET Parity=(SELECT Current_Parity FROM DETAILS WHERE  IDNO=new.IDNO) ," +
                "HeatSeq=(SELECT HeatSeq FROM DETAILS WHERE  IDNO=new.IDNO) \nWHERE  dtofheat=new.dtofheat " +
                "and IDNO=new.IDNO AND (new.PD2<>3  or new.PD2 IS NULL);\n\n\n\n\nUpdate details SET Status=6 " +
                "WHERE IDNO=new.idno AND Status is null  AND new.PD2=3 ;\n\nUpdate details  SET " +
                "Status=1,BreedingStatus='Pregnant',HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=new.heatseq,Current_Parity=new.parity,\n" +
                "PDDate=new.pddate,PDI=new.pd1,PDII=new.pd2  WHERE IDNO=new.idno AND Status=6 and new.pd2=3;\n\n" +
                "Update details  SET Status=3,BreedingStatus='Pregnant',HeatDate=new.dtofheat," +
                "LastSire=new.sireid,HeatSeq=new.heatseq,Current_Parity=new.parity,\nPDDate=new.pddate,PDI=new.pd1,PDII=new.pd2 WHERE IDNO=new.idno AND Status=2 and new.pd2=3;\n\nUpdate details \tSET Status=5,BreedingStatus='Pregnant'," +
                "HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=new.heatseq,Current_Parity=new.parity,\nPDDate=new.pddate,PDI=new.pd1,PDII=new.pd2 WHERE IDNO=new.idno AND Status=4 and " +
                "new.pd2=3;\n\nUpdate details SET BreedingStatus='Aborted', HeatDate=new.dtofheat,LastSire=new.sireid,HeatSeq=" +
                "new.heatseq,Current_Parity=new.parity,\nAbortionSeq=new.abortion_seq,PDDate=new.pddate,PDI=new.pd1," +
                "PDII=new.pd2 WHERE IDNO=new.idno  and new.pd2=4;\n\n\n\nUpdate details SET " +
                "Status=4,CalvingDate=new.dtofcalving,breedingstatus='Open Unbred'  WHERE IDNO=new.idno AND Status=1 AND " +
                "New.dtofcalving<>'';\n\nUpdate details SET Status=5,CalvingDate=new.dtofcalving,breedingstatus='Open Unbred' " +
                "WHERE IDNO=new.idno AND Status=3 AND New.dtofcalving<>'';\n\n\n\nUpdate details SET Status=2, " +
                "DryDate=new.DtofDry WHERE IDNO=new.idno AND Status=4 AND New.dtofDry<>'';\n\nUpdate details SET Status=3," +
                "DryDate=new.DtofDry WHERE IDNO=new.idno AND Status=5 AND New.dtofDry<>'';\n\n\n\nEND");



        db.execSQL("CREATE TRIGGER AfterInsertMilk  AFTER  Insert   \nON Production\nBEGIN\nUpdate production  SET\nParity=(SELECT  \nCase When HeatDate<Calvingdate THEN Current_Parity\n WHEN  HeatDate>Calvingdate AND Parity<Current_Parity  THEN Current_Parity -1\n WHEN  HeatDate>Calvingdate AND Parity=Current_Parity  THEN Current_Parity  \n END AS  [Parity] \nFROM Details WHERE IDNO=new.IDno)\nWHERE IDNO=new.IDno;\nUpdate details \nSET \nMilkDate=new.Date,\nLastmilk=new.Days_Total,\nTotalMilk=(Select Sum(lactation_total) From Production where IDNO=new.IDno)\nWHERE IDNO=new.idno ;\n\nEND");
        db.execSQL("CREATE TABLE [CalfShedule](\n" +
                " [Idno] [int] NULL,\n" +
                " [BreedID] [int] NULL,\n" +
                " [Day] [int] NULL,\n" +
                " [Task] [nvarchar](200) NULL,\n" +
                " [taskDate] [nvarchar](50) NULL\n" +
                ")");
        db.execSQL("CREATE TABLE [MedicineFollow](\n" +
                " [IDNO] [nvarchar](20) NULL,\n" +
                " [Date] [datetime] NULL,\n" +
                " [MedicineName] [nvarchar](20) NULL,\n" +
                " [FollowDate] [datetime] NULL\n" +
                ")");

        db.execSQL("CREATE TABLE [SMSSetting](\n" +
                "[Task] [nvarchar](200) NULL,\n" +
                "[Doctor] [bit] NULL,\n" +
                "[Farmer] [bit] NULL,\n" +
                "[FromDays] [int] NULL,\n" +
                "[ToDays] [int] NULL,\n" +
                "[OrderNo] [int] NULL\n" +
                ")");
        db.execSQL("Update details  set Disposalflg='' where Disposalflg='null'");
        db.execSQL("CREATE TABLE [WeightGainDet](\n" +
                " [IdNo] [nvarchar](50) NULL,\n" +
                " [Date] [datetime] NULL,\n" +
                " [ChestGirth] [smallmoney] NULL,\n" +
                " [Weight] [smallmoney] NULL,\n" +
                " [Length] [smallmoney] NULL,\n" +
                " [Weightgain] [nvarchar](50) NULL,\n" +
                " [autono] [int] NULL\n" +
                ")");
        db.execSQL("CREATE TABLE [MedPurchaseDetails](\n" +
                " [CompanyCode] [nvarchar](4) NULL,\n" +
                " [TranYear] [nvarchar](20) NULL,\n" +
                " [Form_type] [nvarchar](10) NULL,\n" +
                " [LedgerCode] [nvarchar](30) NULL,\n" +
                " [VoucherNo] [nvarchar](30) NULL,\n" +
                " [PID] [nvarchar](30) NULL,\n" +
                " [Itemcode] [nvarchar](30) NULL,\n" +
                " [Date] [datetime] NULL,\n" +
                " [Quantity] [smallmoney] NULL,\n" +
                " [Rate] [smallmoney] NULL,\n" +
                " [Mode] [nvarchar](10) NULL,\n" +
                " [Batch_no] [nvarchar](20) NULL,\n" +
                " [Expiry_Date] [datetime] NULL,\n" +
                " [Discount] [smallmoney] NULL,\n" +
                " [Pack] [float] NULL,\n" +
                " [SyncID] [bigint] IDENTITY(1,1) NOT NULL\n" +
                ")");

    }




    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    public void insertUsers() {
    }

    public void addRegisteredUsers(JSONArray usersListJsonArray) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            int i = 0;
            while (i < usersListJsonArray.length()) {
                try {
                    JSONObject userObject = usersListJsonArray.getJSONObject(i);
                    String UID = userObject.getString(USER_TABLE_FIELD_UUID);
                    if (userPresentInDB(UID)) {

                        values.put(USER_TABLE_FIELD_PASSWORD, userObject.getString(USER_TABLE_FIELD_PASSWORD));
                        values.put(USER_TABLE_FIELD_GROUP, userObject.getString("GROUP"));
                        values.put(USER_TABLE_FIELD_HERD, userObject.getString(USER_TABLE_FIELD_HERD));
                        values.put(USER_TABLE_FIELD_COMPANYCODE, userObject.getString(USER_TABLE_FIELD_COMPANYCODE));
                        values.put(USER_TABLE_FIELD_APPTYPE, userObject.getString(USER_TABLE_FIELD_APPTYPE));
                        values.put(USER_TABLE_FIELD_UPDATEDBY, userObject.getString(USER_TABLE_FIELD_UPDATEDBY));
                        values.put(USER_TABLE_FIELD_UPDATEDAT, userObject.getString(USER_TABLE_FIELD_UPDATEDAT));
                        values.put(USER_TABLE_FIELD_USERCODE, userObject.getString(USER_TABLE_FIELD_USERCODE));
                        values.put(USER_TABLE_FIELD_QRCODE, userObject.optString(USER_TABLE_FIELD_QRCODE));


                        String[] strArr = new String[DATABASE_VERSION];
                        strArr[0] = String.valueOf(UID);
                        db.update(USER_TABLE_NAME, values, "UID = ?", strArr);

                    }else{

                        values.put(USER_TABLE_FIELD_UUID, userObject.getString(USER_TABLE_FIELD_UUID));
                        values.put(USER_TABLE_FIELD_PASSWORD, userObject.getString(USER_TABLE_FIELD_PASSWORD));
                        values.put(USER_TABLE_FIELD_GROUP, userObject.getString("GROUP"));
                        values.put(USER_TABLE_FIELD_HERD, userObject.getString(USER_TABLE_FIELD_HERD));
                        values.put(USER_TABLE_FIELD_COMPANYCODE, userObject.getString(USER_TABLE_FIELD_COMPANYCODE));
                        values.put(USER_TABLE_FIELD_APPTYPE, userObject.getString(USER_TABLE_FIELD_APPTYPE));
                        values.put(USER_TABLE_FIELD_UPDATEDBY, userObject.getString(USER_TABLE_FIELD_UPDATEDBY));
                        values.put(USER_TABLE_FIELD_UPDATEDAT, userObject.getString(USER_TABLE_FIELD_UPDATEDAT));
                        values.put(USER_TABLE_FIELD_USERCODE, userObject.getString(USER_TABLE_FIELD_USERCODE));
                        values.put(USER_TABLE_FIELD_QRCODE, userObject.getString(USER_TABLE_FIELD_QRCODE));
                        db.insert(USER_TABLE_NAME, null, values);
                    }
                    i += DATABASE_VERSION;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public boolean userPresentInDB(String uid) {
        String[] strArr = new String[DATABASE_VERSION];
        strArr[0] = uid;
        if (getReadableDatabase().rawQuery("SELECT UID FROM User WHERE UID=?", strArr).getCount() > 0) {
            return true;
        }
        return false;
    }



    public UserInfo getUser(String userName, String password) {
        Cursor cursor = getReadableDatabase().query(USER_TABLE_NAME, new String[]{USER_TABLE_FIELD_UUID, USER_TABLE_FIELD_PASSWORD, USER_TABLE_FIELD_GROUP, USER_TABLE_FIELD_HERD, USER_TABLE_FIELD_COMPANYCODE, USER_TABLE_FIELD_APPTYPE, USER_TABLE_FIELD_UPDATEDBY, USER_TABLE_FIELD_UPDATEDAT, USER_TABLE_FIELD_USERCODE, USER_TABLE_FIELD_QRCODE}, "UID=? AND PASSWORD=?", new String[]{userName, password}, null, null, null, null);

        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        return new UserInfo(cursor.getString(0), cursor.getString(DATABASE_VERSION), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), Boolean.parseBoolean(cursor.getString(9)));
    }

    public void addMasterData(JSONObject responseObject) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            Iterator<?> keys = responseObject.keys();
            while (keys.hasNext()) {
                String tableName = (String) keys.next();

                System.out.println("Table Name"+ tableName);



                JSONArray tableContent = responseObject.getJSONArray(tableName);

                for (int i = 0; i < tableContent.length(); i += DATABASE_VERSION) {
                    db.insert(tableName, null, getRow(tableContent.getJSONObject(i)));
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
    public ContentValues getRow(JSONObject row) {
        ContentValues keyValuePair = null;
        try {
            Iterator<?> keys = row.keys();
            int i = 0;
            keyValuePair = new ContentValues();
            while (keys.hasNext()) {
                String columnName = (String) keys.next();
                keyValuePair.put(columnName, row.getString(columnName));
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return keyValuePair;
    }

    public int getContactsCount() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT  * FROM User", null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public JSONObject getThirdTypeTableMaxId() {
        JSONObject tableNameMaxId = new JSONObject();
        SQLiteDatabase db = getReadableDatabase();
        String[] columnName = new String[DATABASE_VERSION];
        columnName[0] = "SyncID";
        String[] arr$ = this.thirdAndFourthTypeTables;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$ += DATABASE_VERSION) {
            String tableName = arr$[i$];
            Cursor cursor = db.query(tableName, columnName, null, null, null, null, "SyncID DESC", "1");
            cursor.moveToFirst();
            try {
                if (cursor.getCount() > 0) {
                    tableNameMaxId.put(tableName, cursor.getInt(cursor.getColumnIndex("SyncID")));
                } else {
                    tableNameMaxId.put(tableName, 0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        System.out.println(tableNameMaxId);
        return tableNameMaxId;
    }

    public void saveThirdAndFourthTypeMaster(JSONObject thirdAndFourthMasterJsonObject) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            Iterator<?> keys = thirdAndFourthMasterJsonObject.keys();
            while (keys.hasNext()) {
                String tableName = (String) keys.next();
                JSONArray tableContent = thirdAndFourthMasterJsonObject.getJSONArray(tableName);
                for (int i = 0; i < tableContent.length(); i += DATABASE_VERSION) {
                    db.insert(tableName, null, getRow(tableContent.getJSONObject(i)));
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<String> getLotNumberAndName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(LOT_TABLE_NAME, new String[]{TABLE_LOT_COLUMN_LOTNO, TABLE_LOT_COLUMN_NAME}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            //System.out.println("LOT CURSOR COUNT = " + cursor.getCount());
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_LOT_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_LOT_COLUMN_LOTNO)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public ArrayList<String> getCodeAndNameFromOwner(String lotNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(OWNER_TABLE_NAME, new String[]{TABLE_OWNER_COLUMN_CODE, TABLE_OWNER_COLUMN_NAME}, TABLE_DETAILS_COLUMN_LOTNO + "=?", new String[]{lotNumber}, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            System.out.println("OWNER CURSOR COUNT = " + cursor.getCount());
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_OWNER_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_OWNER_COLUMN_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }
    public ArrayList<String> getIdFromDetails(String lotNumber, String ownerCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> idNo = null;
        try {
            cursor = db.query(DETAILS_TABLE_NAME, new String[]{TABLE_DETAILS_COLUMN_IDNO}, TABLE_DETAILS_COLUMN_LOTNO + "=? AND " + TABLE_DETAILS_COLUMN_NAME + "=?", new String[]{lotNumber, ownerCode}, null, null, null, null);
            idNo = new ArrayList<String>();
            while (cursor.moveToNext()) {
                idNo.add(cursor.getString(cursor.getColumnIndex(TABLE_DETAILS_COLUMN_IDNO)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return idNo;
    }




    public ArrayList<String> get_Task() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(SMS_SETTINGS, new String[]{TASK}, null ,null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                lotNumberName.add(cursor.getString(cursor.getColumnIndex(TASK)));

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }









    public JSONObject getBreedingStatusAndStatus(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        JSONObject breedingStatusAndStatus = null;
        try {
            cursor = db.query(DETAILS_TABLE_NAME, new String[]{TABLE_DETAILS_COLUMN_BREED_STATUS, TABLE_DETAILS_COLUMN_STATUS}, TABLE_DETAILS_COLUMN_IDNO + "= ? ", new String[]{idNumber}, null, null, null, null);
            System.out.println("OWNER CURSOR COUNT = " + cursor.getCount());
            try {
                if (cursor.moveToFirst()) {
                    breedingStatusAndStatus = new JSONObject();
                    breedingStatusAndStatus.put("breedingStatus", cursor.getString(cursor.getColumnIndex(TABLE_DETAILS_COLUMN_BREED_STATUS)));
                    breedingStatusAndStatus.put("status", cursor.getInt(cursor.getColumnIndex(TABLE_DETAILS_COLUMN_STATUS)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return breedingStatusAndStatus;
    }


    public String getHeatDate(String idNumber) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        String heatDate = null;
        try {
            String str = DETAILS_TABLE_NAME;
            String[] strArr = new String[DATABASE_VERSION];
            strArr[0] = this.TABLE_DETAILS_COLUMN_HEAT_DATE;
            String str2 = this.TABLE_DETAILS_COLUMN_IDNO + "= ? ";
            String[] strArr2 = new String[DATABASE_VERSION];
            strArr2[0] = idNumber;
            cursor = db.query(str, strArr, str2, strArr2, null, null, null, null);
            if (cursor.moveToFirst()) {
                heatDate = cursor.getString(cursor.getColumnIndex(this.TABLE_DETAILS_COLUMN_HEAT_DATE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return heatDate;
    }

    public ArrayList<String> getServiceIdAndName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {

            cursor = db.query(SERVICES_TABLE_NAME, new String[]{TABLE_SERVICES_COLUMN_SERVICE_ID, TABLE_SERVICES_COLUMN_SERVICE_NAME}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_SERVICES_COLUMN_SERVICE_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_SERVICES_COLUMN_SERVICE_ID)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }


        public ArrayList<String> getSireEarTagList(String idNo, String serviceId) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = null;
            ArrayList<String> lotNumberName = null;
            try {
                String query = "SELECT SireId,Name FROM Sire WHERE Cbflg IN (SELECT species FROM Details WHERE IDNO = '" + idNo + "') and NAFlag = '" + serviceId + "' order by Name ASC";
                cursor = db.rawQuery(query, null);
                lotNumberName = new ArrayList<String>();
                while (cursor.moveToNext()) {
                    JSONObject lotAndName = new JSONObject();
                    try {
                        lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_SIRE_COLUMN_SIRE_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_SIRE_COLUMN_SIRE_ID)));
                        lotNumberName.add(lotAndName.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return lotNumberName;
        }

    public boolean masterSyncStatus() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        int columnCount = 0;
        try {
            cursor = db.query(SEX_TABLE_NAME, new String[]{this.TABLE_SEX_COLUMN_SEX_CODE, this.TABLE_SEX_COLUMN_NAME}, null, null, null, null, null);
            columnCount = cursor.getCount();
            if (columnCount > 0) {
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }

    public ArrayList<String> getInseminator() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            String query = "SELECT id,Name FROM Staff WHERE Status IN ('Doctor','Inseminator') order by Name ASC";
            cursor = db.rawQuery(query, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_STAFF_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_STAFF_COLUMN_ID)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public ArrayList<String> getPDI() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(PDI_TABLE_NAME, new String[]{TABLE_PDI_COLUMN_ID, TABLE_PDI_COLUMN_PD}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_PDI_COLUMN_PD)), cursor.getString(cursor.getColumnIndex(TABLE_PDI_COLUMN_ID)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public ArrayList<String> getPDII() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(PDII_TABLE_NAME, new String[]{TABLE_PDII_COLUMN_ID, TABLE_PDII_COLUMN_PD}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_PDII_COLUMN_PD)), cursor.getString(cursor.getColumnIndex(TABLE_PDII_COLUMN_ID)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    /*Select Field_Name From [Option] where Field_Type='Dry off Reasons'*/

    public ArrayList<String> getDryReason() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(OPTION_TABLE_NAME, new String[]{TABLE_OPTION_COLUMN_FIELD_NAME}, TABLE_OPTION_COLUMN_FIELD_TYPE + "= ? ", new String[]{TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE1}, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                try {
                    lotNumberName.add(cursor.getString(cursor.getColumnIndex(TABLE_OPTION_COLUMN_FIELD_NAME)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    /*Prashanth Murdeshawar: SELECT [Field_Name]  FROM [Option] WHERE [Field_type]='Dry Treatment'*/

    public ArrayList<MultiSelectItem> getDryTreatment() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<MultiSelectItem> lotNumberName = null;
        try {
            cursor = db.query(OPTION_TABLE_NAME, new String[]{TABLE_OPTION_COLUMN_FIELD_NAME}, TABLE_OPTION_COLUMN_FIELD_TYPE + "= ? ", new String[]{TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE2}, null, null, null, null);
            lotNumberName = new ArrayList<MultiSelectItem>();
            while (cursor.moveToNext()) {
                try {
                    MultiSelectItem multiSelectItem = new MultiSelectItem(cursor.getString(cursor.getColumnIndex(TABLE_OPTION_COLUMN_FIELD_NAME)), false);
                    lotNumberName.add(multiSelectItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

     /*SELECT [CalvingTypeCode],[Name]  FROM [CalvingType]*/


    public ArrayList<String> getCalvingType() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(CALVING_TYPE_TABLE_NAME, new String[]{TABLE_CALVING_TYPE_COLUMN_NAME, TABLE_CALVING_TYPE_COLUMN_CALVING_TYPE_CODE}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_CALVING_TYPE_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_CALVING_TYPE_COLUMN_CALVING_TYPE_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public ArrayList<String> getCalfSex(String calvingTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(CALVING_TYPE_OPTION_TABLE_NAME, new String[]{TABLE_CALVING_TYPE_OPTION_COLUMN_NAME, TABLE_CALVING_TYPE_OPTION_COLUMN_CODE}, TABLE_CALVING_TYPE_OPTION_COLUMN_CALVING_TYPE + "= ? ", new String[]{calvingTypeId}, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_CALVING_TYPE_OPTION_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_CALVING_TYPE_OPTION_COLUMN_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    /*SELECT [Field_Name]
    FROM [Common].[Option] WHERE [Field_type]='Calving Problems'*/

    public ArrayList<MultiSelectItem> getFieldName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<MultiSelectItem> lotNumberName = null;
        try {
            cursor = db.query(OPTION_TABLE_NAME, new String[]{TABLE_OPTION_COLUMN_FIELD_NAME}, TABLE_OPTION_COLUMN_FIELD_TYPE + "= ? ", new String[]{TABLE_OPTION_COLUMN_FIELD_TYPE_VALUE3}, null, null, null, null);
            lotNumberName = new ArrayList<MultiSelectItem>();
            while (cursor.moveToNext()) {
                try {
                    MultiSelectItem multiSelectItem = new MultiSelectItem(cursor.getString(cursor.getColumnIndex(TABLE_OPTION_COLUMN_FIELD_NAME)), false);
                    lotNumberName.add(multiSelectItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }
    public String getMaxDateFromProduction(String idNumber) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        String heatDate = null;
        try {
            String str = PRODUCTION_TABLE_NAME;
            String[] strArr = new String[DATABASE_VERSION];
            strArr[0] = this.TABLE_PRODUCTION_COLUMN_DATE;
            String str2 = this.TABLE_PRODUCTION_COLUMN_IDNO + "=?";
            String[] strArr2 = new String[DATABASE_VERSION];
            strArr2[0] = idNumber;
            cursor = db.query(str, strArr, str2, strArr2, null, null, null, null);
            if (cursor.moveToFirst()) {
                heatDate = cursor.getString(cursor.getColumnIndex(this.TABLE_PRODUCTION_COLUMN_DATE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return heatDate;
    }

    public String getTotalDaysFromProduction(String idNumber, String lastMilkDate) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        String heatDate = null;
        try {
            String str = PRODUCTION_TABLE_NAME;
            String[] strArr = new String[DATABASE_VERSION];
            strArr[0] = this.TABLE_PRODUCTION_COLUMN_DAYS_TOTAL;
            cursor = db.query(str, strArr, this.TABLE_PRODUCTION_COLUMN_IDNO + "=? AND " + this.TABLE_PRODUCTION_COLUMN_DATE + "=?", new String[]{idNumber, lastMilkDate}, null, null, null, null);
            if (cursor.moveToFirst()) {
                heatDate = cursor.getString(cursor.getColumnIndex(this.TABLE_PRODUCTION_COLUMN_DAYS_TOTAL));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return heatDate;
    }

    public String getCalvingDateFromDetails(String idNumber) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        String heatDate = null;
        try {
            String str = DETAILS_TABLE_NAME;
            String[] strArr = new String[DATABASE_VERSION];
            strArr[0] = this.TABLE_DETAILS_COLUMN_CALVING_DATE;
            String str2 = this.TABLE_DETAILS_COLUMN_IDNO + "= ? ";
            String[] strArr2 = new String[DATABASE_VERSION];
            strArr2[0] = idNumber;
            cursor = db.query(str, strArr, str2, strArr2, null, null, null, null);
            if (cursor.moveToFirst()) {
                heatDate = cursor.getString(cursor.getColumnIndex(this.TABLE_DETAILS_COLUMN_CALVING_DATE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return heatDate;
    }


    /*Select Herdno,Name From Herd order by HerdNo*/
    public ArrayList<String> getHerds() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(HERD_TABLE_NAME, new String[]{TABLE_HERD_COLUMN_HERD_NAME, TABLE_HERD_COLUMN_HERD_NO}, null, null, null, null, TABLE_HERD_COLUMN_HERD_NO + " DESC");
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_HERD_COLUMN_HERD_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_HERD_COLUMN_HERD_NO)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    /*Select Lotno,Name From Lot WHERE Herdno=Herdno order by Lotno*/
    public ArrayList<String> getLot(String herdNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(LOT_TABLE_NAME, new String[]{TABLE_LOT_COLUMN_NAME, TABLE_LOT_COLUMN_LOTNO}, TABLE_LOT_COLUMN_HERD_NO + "= ? ", new String[]{herdNumber}, null, null, TABLE_LOT_COLUMN_HERD_NO + " DESC");
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_LOT_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_LOT_COLUMN_LOTNO)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

     /*SELECT Code,name  FROM Owner WHERE Lotno='L1' Order by Name*/

    public ArrayList<String> getOwner(String lotNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(OWNER_TABLE_NAME, new String[]{TABLE_OWNER_COLUMN_NAME, TABLE_OWNER_COLUMN_CODE}, TABLE_OWNER_COLUMN_LOT_NUMBER + "= ? ", new String[]{lotNumber}, null, null, TABLE_OWNER_COLUMN_NAME + " DESC");
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_OWNER_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_OWNER_COLUMN_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public ArrayList<String> getSex() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(SEX_TABLE_NAME, new String[]{TABLE_SEX_COLUMN_NAME, TABLE_SEX_COLUMN_SEX_CODE}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_SEX_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_SEX_COLUMN_SEX_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    /*SELECT [SpeciesCode],[Name] FROM [Species]*/
    public ArrayList<String> getSpecies() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(SPECIES_TABLE_NAME, new String[]{TABLE_SPECIES_COLUMN_NAME, TABLE_SPECIES_COLUMN_SPECIES_CODE}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_SPECIES_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_SPECIES_COLUMN_SPECIES_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }




   public  ArrayList<ActionBean> getTask_details(String Ownercode ,String LotNumber){

       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = null;
       JSONObject animalStatusObject=null;
       ArrayList<ActionBean> Bean_list = new ArrayList<ActionBean>();
       try {

           String query;

           if(!Ownercode.equals("") || !LotNumber.equals("")){


               query = "SELECT  * from (\n" +
                       " SELECT   Details.IdNo as [IDNO], Owner.Code as [Farmer code], Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name [Farmer Name],SMSSetting.Task as [Task],Details.IdNo,SMSSetting.OrderNo,Details.LotNo as lotno\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                      AND (Details.Status IN (4, 5))\n" +
                       "            \n" +
                       "GROUP BY SMSSetting.OrderNo,Details.milkDate,  Details.IdNo, Owner.name,Parameter.MilkInterval,[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "HAVING     SMSSetting.Task='Expected Milking'\n" +
                       "AND [Parameter].[MilkInterval]>0\n" +
                       "AND Julianday('now')-julianday(details.Calvingdate)  <305\n" +
                       "AND Julianday('now')-julianday(details.Calvingdate) % [Parameter].[MilkInterval] =0\n" +
                       "Union ALL \n" +
                       " --**************END Exp Milking**********************************\n" +
                       "--**************                    **********************************      \n" +
                       "--**************Start check Heat**********************************\n" +
                       "--**************                    **********************************\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "--**************Start check Heat**********************************\n" +
                       "--**************                    **********************************\n" +
                       "\n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "\n" +
                       "\n" +
                       " FROM         Details INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0) AND (Details.BreedingStatus IN ('Aborted', 'Open Bred')) \n" +
                       "                      GROUP BY Details.BreedingStatus,SMSSetting.OrderNo,Details.HeatDate, Details.IdNo, Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg, \n" +
                       "                      Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (21- SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (21 + SMSSetting.ToDays) \n" +
                       "                      THEN [Details].[HeatDate] END > 0)\n" +
                       "                      AND  SMSSetting.Task='Check for Heat'\n" +
                       "                      AND (Details.BreedingStatus IN ('Aborted', 'Open Bred'))\n" +
                       "\n" +
                       "Union ALL \n" +
                       "--**************END Check For Heat**********************************\n" +
                       "--**************                  **********************************\n" +
                       "\n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0) AND (Details.BreedingStatus IN ('Open Unbred'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.BreedingStatus,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.calvingdate)  >= ([Parameter].[Heat] - SMSSetting.FromDays) AND \n" +
                       "\n" +
                       "                     Julianday('now')-julianday(details.calvingdate)  <= ([Parameter].[Heat] + SMSSetting.toDays) THEN [Details].[CalvingDate] END > 0) AND \n" +
                       "                      (SMSSetting.Task = 'Check for First Heat')\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Unbred'))\n" +
                       "--**************Start Non Return**********************************\n" +
                       "--**************                    **********************************     \n" +
                       " Union ALL                    \n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0) AND (Details.PDI = 0) AND (Details.PDII = 0)\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Bred', 'Aborted'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.BreedingStatus,Details.HeatDate, Details.IdNo,\n" +
                       "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (42 - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (42+ SMSSetting.toDays) OR\n" +
                       "                      Julianday('now')-julianday(details.heatdate)  >= (63 - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (63 + SMSSetting.toDays) \n" +
                       "                      THEN [Details].[HeatDate] END > 0)   \n" +
                       "                       AND  SMSSetting.Task='Check for Non Return'  \n" +
                       "                       AND (Details.BreedingStatus IN ('Open Bred'))                  \n" +
                       "                    \n" +
                       "--**************END Non Return**********************************\n" +
                       "--**************                    **********************************       \n" +
                       "--**************Start PD1 **********************************\n" +
                       "--**************                    **********************************   \n" +
                       "  Union ALL                 \n" +
                       "SELECT   Distinct Details.IdNo || ' '  ||  '+', Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI IS NULL) AND (Details.PDII IS NULL) OR\n" +
                       "                      (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI = 0) AND (Details.PDII = 0)\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.HeatDate, Details.IdNo,Details.BreedingStatus,Parameter.PD1, \n" +
                       "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= ([Parameter].[PD1] - SMSSetting.FromDays) AND  Julianday('now')-julianday(details.heatdate)\n" +
                       "                      < ([Parameter].[PD1] + SMSSetting.toDays) THEN [Details].HeatDate END > 0) \n" +
                       "                       AND  SMSSetting.Task='Check PD1'\n" +
                       "                        AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       "Union ALL \n" +
                       "--**************END PD1**********************************\n" +
                       "--**************                    **********************************       \n" +
                       "--**************Start PD2 **********************************\n" +
                       "--**************                    **********************************                         \n" +
                       "SELECT   Distinct Details.IdNo || ' '  ||  '++', Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI IS NULL) AND (Details.PDII IS NULL) OR\n" +
                       "                      (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI IN (0, 1)) AND (Details.PDII = 0)\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.HeatDate, Details.IdNo,Details.BreedingStatus,Parameter.PD2, \n" +
                       "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "Owner.Phno,SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate)>= ([Parameter].[PD2] - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)\n" +
                       "                       <= ([Parameter].[PD2] + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0) \n" +
                       "\t\t\t\t\t\tAND  SMSSetting.Task='Check PD2'\n" +
                       "\t\t\t\t\t\t AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       " Union ALL \n" +
                       " --**************END PD2**********************************\n" +
                       "--**************                    **********************************   \n" +
                       "--**************Start Exp Dry **********************************\n" +
                       "--************** \n" +
                       "\n" +
                       " SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, \n" +
                       " Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
                       " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                      AND (Details.Status IN (5))\n" +
                       "GROUP BY [Parameter].[Dry_Period],Details.Status,SMSSetting.OrderNo,Details.LotNo,Details.milkDate,  Details.IdNo, Owner.name,[Parameter].[Concalv],[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= ([Parameter].[Concalv]-[Parameter].[Dry_Period]) - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(details.heatdate) <= ([Parameter].[Concalv]-[Parameter].[Dry_Period]) + SMSSetting.toDays THEN MAX(Details.HeatDate) ELSE '' END > 0)      \n" +
                       "                       AND  SMSSetting.Task='Dry Off'  \n" +
                       "                       AND (Details.Status IN (5))\n" +
                       "Union ALL\n" +
                       "    \n" +
                       "--**************END EXP Dry **********************************\n" +
                       "--**************    \n" +
                       "--**************Start Exp Calving **********************************\n" +
                       "--**************                    **********************************   \n" +
                       "\n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, \n" +
                       "SMSSetting.Task,\n" +
                       "Details.IdNo\n" +
                       ",SMSSetting.OrderNo,Details.LotNo\n" +
                       "\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                       AND                       (Details.BreedingStatus IN ('Pregnant'))\n" +
                       "GROUP BY [Parameter].[CalvDays],SMSSetting.OrderNo,Details.LotNo,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat,Parameter.ConCalv,[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (([Parameter].[ConCalv] - [Parameter].[CalvDays]) - SMSSetting.FromDays) AND \n" +
                       "Julianday('now')-julianday(details.heatdate) <= (([Parameter].[ConCalv] - [Parameter].[CalvDays]) + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0)\n" +
                       "                      \n" +
                       "                       AND  SMSSetting.Task='Expected Calving'\n" +
                       "                       AND (Details.BreedingStatus IN ('Pregnant'))\n" +
                       " Union ALL  \n" +
                       " \n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, \n" +
                       "SMSSetting.Task,\n" +
                       "Details.IdNo\n" +
                       ",SMSSetting.OrderNo,Details.LotNo\n" +
                       "\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                       AND                       (Details.BreedingStatus IN ('Pregnant'))\n" +
                       "GROUP BY [Parameter].[CalvDays],SMSSetting.OrderNo,Details.LotNo,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat,Parameter.ConCalv,[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (([Parameter].[ConCalv] ) - SMSSetting.FromDays) AND \n" +
                       "Julianday('now')-julianday(details.heatdate) <= (([Parameter].[ConCalv] ) + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0)\n" +
                       "                      \n" +
                       "                       AND  SMSSetting.Task='Expected Calving'\n" +
                       "                       AND (Details.BreedingStatus IN ('Pregnant'))\n" +
                       " Union ALL                        \n" +
                       " --**************END Exp Calving**********************************\n" +
                       "--**************                    **********************************       \n" +
                       " --**************Vaccination Shedule**********************************\n" +
                       "SELECT   Distinct Details.IdNo || ' '  || VaccineDetails.vaccinename AS [IDNO], Owner.Code, \n" +
                       "Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task ,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM        VaccineDetails INNER JOIN\n" +
                       "                      Details ON VaccineDetails.Idno = Details.IdNo INNER JOIN\n" +
                       "                      Option ON VaccineDetails.vaccinename = Option.Field_name INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,Option.days, Option.Field_name,VaccineDetails.vaccinename,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(VaccineDetails.Date) >= (Option.Days  * 30) - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(VaccineDetails.Date) \n" +
                       "\n" +
                       " <= ( Option.Days * 30) + SMSSetting.toDays THEN Julianday('now')-julianday(VaccineDetails.Date) ELSE '+ ' END > 0)\n" +
                       " AND  SMSSetting.Task='Due for Vaccination'\n" +
                       "\n" +
                       "Union ALL\n" +
                       "--**************Deworming Shedule**********************************\n" +
                       "SELECT   Distinct Details.IdNo || ' '  || Deworming.Deworm AS [IDNO], Owner.Code, Owner.Phno,\n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM        Deworming INNER JOIN\n" +
                       "                      Details ON Deworming.Idno = Details.IdNo INNER JOIN\n" +
                       "                      Option ON Deworming.Deworm = Option.Field_Name INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "GROUP BY SMSSetting.OrderNo,   Details.IdNo, Owner.name,Option.Days, Option.Field_Name,Deworming.Deworm,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      (CASE WHEN \n" +
                       "Julianday('now')-julianday(Deworming.Date) >= (Option.Days * 30) - SMSSetting.FromDays AND\n" +
                       "Julianday('now')-julianday(Deworming.Date)  <= (Option.Days * 30) + SMSSetting.toDays THEN \n" +
                       "Julianday('now')-julianday(Deworming.Date) ELSE '+ ' END > 0)\n" +
                       "              AND  SMSSetting.Task='Due for Deworming'         \n" +
                       "Union ALL                      \n" +
                       "--**************Medicine followup Shedule**********************************\n" +
                       "SELECT   Distinct Details.IdNo || ' '  || MedicineFollow.MedicineName AS [IDNO], Owner.Code, \n" +
                       "Owner.Phno,Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                     MedicineFollow ON Details.IdNo =MedicineFollow.IDNO INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) AND \n" +
                       "Julianday('now')-julianday(MedicineFollow.Date) >= - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(MedicineFollow.Date)  <= 2\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,MedicineFollow.MedicineName,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "                       HAVING  SMSSetting.Task='Medicine Follow Up'\n" +
                       "Union ALL\n" +
                       " --**************Treatmnt Shedule**********************************                     \n" +
                       "SELECT   Distinct Details.IdNo , Owner.Code, Owner.Phno,\n" +
                       " Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,\n" +
                       " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                     TreatmentFollow ON Details.IdNo =TreatmentFollow.IDNO INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) AND \n" +
                       "Julianday('now')-julianday(TreatmentFollow.Date) >= - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(TreatmentFollow.Date)  <= 2\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "   HAVING  SMSSetting.Task='Treatment Follow Up'\n" +
                       "Union ALL\n" +
                       "--**************Calf Shedule**********************************\n" +
                       "SELECT   Distinct  Details.IdNo || ' '  || CalfShedule.Task, Owner.Code, Owner.Phno,\n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
                       " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      CalfShedule ON Details.Breed = CalfShedule.BreedID \n" +
                       "                      INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL)\n" +
                       "                      AND \n" +
                       "Julianday('now')-julianday(Details.DtOfBirth) >= - CalfShedule.Day - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(Details.DtOfBirth) <= CalfShedule.Day + SMSSetting.FromDays\t\t\t\t  \n" +
                       "\t\t\t\t\t  \n" +
                       "\t\t\t\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,CalfShedule.Task,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "   HAVING  SMSSetting.Task='Calf Shedule'\n" +
                       "\n" +
                       "Union ALL\n" +
                       "--**************Weight gain Shedule**********************************\n" +
                       "SELECT   Distinct  Details.IdNo , Owner.Code, Owner.Phno, \n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,\n" +
                       "Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                     WeightGainDet ON Details.IdNo =WeightGainDet.IdNo\n" +
                       "                       INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL)\n" +
                       "GROUP BY SMSSetting.OrderNo,  Details.IdNo, Owner.name,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      \n" +
                       "(Julianday('now')-julianday(WeightGainDet.Date) >= 30 - SMSSetting.FromDays) AND \n" +
                       "(Julianday('now')-julianday(WeightGainDet.Date)  <= 30 + SMSSetting.toDays)\n" +
                       "   AND  SMSSetting.Task='Due for Body Weight'\n" +
                       "\n" +
                       "Union ALL\n" +
                       "--**************Diseases Testing**********************************Due for Diseases Testing\n" +
                       "SELECT   Distinct  Details.IdNo || ' '  || DiseaseTest.Disease, Owner.Code, Owner.Phno, \n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
                       "Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM        DiseaseTest INNER JOIN\n" +
                       "                      Details ON DiseaseTest.Idno = Details.IdNo INNER JOIN\n" +
                       "                      Option ON DiseaseTest.Disease = Option.Field_Name  INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.milkDate,  Details.IdNo, Owner.name, Option.Days, Option.Field_Name,DiseaseTest.Disease,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "HAVING      (CASE WHEN \n" +
                       "Julianday('now')-julianday(DiseaseTest.Date)>= (Option.Days * 30) - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(DiseaseTest.Date) <= (Option.Days * 30) + SMSSetting.toDays THEN \n" +
                       "Julianday('now')-julianday(DiseaseTest.Date) ELSE '+ ' END > 0)\n" +
                       "  AND  SMSSetting.Task='Due for Diseases Testing'\n" +
                       ") as U \n" +
                       "ORDER BY U.[Farmer Name],U.OrderNo, U.IdNo\n" + "WHERE \n" +

                       "U.Orderno>0\n" +
                       "\n" +
                       "IF Task<>\"\" then\n" +
                       "AND U.Task in('Check for Heat')\n" +
                       "IF Farmercode<>\"\"\n" +
                       " and Farmercode in ("+Ownercode+")\n" +
                       "IF lotno<>\"\"\n" +
                       " and lotno=("+LotNumber+")\n" +
                       "ENDIF";

           }else{


               query = "SELECT  * from (\n" +
                       " SELECT   Details.IdNo as [IDNO], Owner.Code as [Farmer code], Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name [Farmer Name],SMSSetting.Task as [Task],Details.IdNo,SMSSetting.OrderNo,Details.LotNo as lotno\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                      AND (Details.Status IN (4, 5))\n" +
                       "            \n" +
                       "GROUP BY SMSSetting.OrderNo,Details.milkDate,  Details.IdNo, Owner.name,Parameter.MilkInterval,[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "HAVING     SMSSetting.Task='Expected Milking'\n" +
                       "AND [Parameter].[MilkInterval]>0\n" +
                       "AND Julianday('now')-julianday(details.Calvingdate)  <305\n" +
                       "AND Julianday('now')-julianday(details.Calvingdate) % [Parameter].[MilkInterval] =0\n" +
                       "Union ALL \n" +
                       " --**************END Exp Milking**********************************\n" +
                       "--**************                    **********************************      \n" +
                       "--**************Start check Heat**********************************\n" +
                       "--**************                    **********************************\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "--**************Start check Heat**********************************\n" +
                       "--**************                    **********************************\n" +
                       "\n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "\n" +
                       "\n" +
                       " FROM         Details INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0) AND (Details.BreedingStatus IN ('Aborted', 'Open Bred')) \n" +
                       "                      GROUP BY Details.BreedingStatus,SMSSetting.OrderNo,Details.HeatDate, Details.IdNo, Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg, \n" +
                       "                      Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (21- SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (21 + SMSSetting.ToDays) \n" +
                       "                      THEN [Details].[HeatDate] END > 0)\n" +
                       "                      AND  SMSSetting.Task='Check for Heat'\n" +
                       "                      AND (Details.BreedingStatus IN ('Aborted', 'Open Bred'))\n" +
                       "\n" +
                       "Union ALL \n" +
                       "--**************END Check For Heat**********************************\n" +
                       "--**************                  **********************************\n" +
                       "\n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0) AND (Details.BreedingStatus IN ('Open Unbred'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.BreedingStatus,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.calvingdate)  >= ([Parameter].[Heat] - SMSSetting.FromDays) AND \n" +
                       "\n" +
                       "                     Julianday('now')-julianday(details.calvingdate)  <= ([Parameter].[Heat] + SMSSetting.toDays) THEN [Details].[CalvingDate] END > 0) AND \n" +
                       "                      (SMSSetting.Task = 'Check for First Heat')\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Unbred'))\n" +
                       "--**************Start Non Return**********************************\n" +
                       "--**************                    **********************************     \n" +
                       " Union ALL                    \n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0) AND (Details.PDI = 0) AND (Details.PDII = 0)\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Bred', 'Aborted'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.BreedingStatus,Details.HeatDate, Details.IdNo,\n" +
                       "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (42 - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (42+ SMSSetting.toDays) OR\n" +
                       "                      Julianday('now')-julianday(details.heatdate)  >= (63 - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)  <= (63 + SMSSetting.toDays) \n" +
                       "                      THEN [Details].[HeatDate] END > 0)   \n" +
                       "                       AND  SMSSetting.Task='Check for Non Return'  \n" +
                       "                       AND (Details.BreedingStatus IN ('Open Bred'))                  \n" +
                       "                    \n" +
                       "--**************END Non Return**********************************\n" +
                       "--**************                    **********************************       \n" +
                       "--**************Start PD1 **********************************\n" +
                       "--**************                    **********************************   \n" +
                       "  Union ALL                 \n" +
                       "SELECT   Distinct Details.IdNo || ' '  ||  '+', Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI IS NULL) AND (Details.PDII IS NULL) OR\n" +
                       "                      (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI = 0) AND (Details.PDII = 0)\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.HeatDate, Details.IdNo,Details.BreedingStatus,Parameter.PD1, \n" +
                       "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "Owner.Phno,SMSSetting.Task,Owner.Code,Details.LotNo\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= ([Parameter].[PD1] - SMSSetting.FromDays) AND  Julianday('now')-julianday(details.heatdate)\n" +
                       "                      < ([Parameter].[PD1] + SMSSetting.toDays) THEN [Details].HeatDate END > 0) \n" +
                       "                       AND  SMSSetting.Task='Check PD1'\n" +
                       "                        AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       "Union ALL \n" +
                       "--**************END PD1**********************************\n" +
                       "--**************                    **********************************       \n" +
                       "--**************Start PD2 **********************************\n" +
                       "--**************                    **********************************                         \n" +
                       "SELECT   Distinct Details.IdNo || ' '  ||  '++', Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI IS NULL) AND (Details.PDII IS NULL) OR\n" +
                       "                      (Details.Disposalflg IS NULL OR\n" +
                       "                      Details.Disposalflg = 0) AND (Details.PDI IN (0, 1)) AND (Details.PDII = 0)\n" +
                       "                      AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.HeatDate, Details.IdNo,Details.BreedingStatus,Parameter.PD2, \n" +
                       "Owner.name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "Owner.Phno,SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate)>= ([Parameter].[PD2] - SMSSetting.FromDays) AND Julianday('now')-julianday(details.heatdate)\n" +
                       "                       <= ([Parameter].[PD2] + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0) \n" +
                       "\t\t\t\t\t\tAND  SMSSetting.Task='Check PD2'\n" +
                       "\t\t\t\t\t\t AND (Details.BreedingStatus IN ('Open Bred'))\n" +
                       " Union ALL \n" +
                       " --**************END PD2**********************************\n" +
                       "--**************                    **********************************   \n" +
                       "--**************Start Exp Dry **********************************\n" +
                       "--************** \n" +
                       "\n" +
                       " SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, \n" +
                       " Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
                       " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                      AND (Details.Status IN (5))\n" +
                       "GROUP BY [Parameter].[Dry_Period],Details.Status,SMSSetting.OrderNo,Details.LotNo,Details.milkDate,  Details.IdNo, Owner.name,[Parameter].[Concalv],[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= ([Parameter].[Concalv]-[Parameter].[Dry_Period]) - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(details.heatdate) <= ([Parameter].[Concalv]-[Parameter].[Dry_Period]) + SMSSetting.toDays THEN MAX(Details.HeatDate) ELSE '' END > 0)      \n" +
                       "                       AND  SMSSetting.Task='Dry Off'  \n" +
                       "                       AND (Details.Status IN (5))\n" +
                       "Union ALL\n" +
                       "    \n" +
                       "--**************END EXP Dry **********************************\n" +
                       "--**************    \n" +
                       "--**************Start Exp Calving **********************************\n" +
                       "--**************                    **********************************   \n" +
                       "\n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, \n" +
                       "SMSSetting.Task,\n" +
                       "Details.IdNo\n" +
                       ",SMSSetting.OrderNo,Details.LotNo\n" +
                       "\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                       AND                       (Details.BreedingStatus IN ('Pregnant'))\n" +
                       "GROUP BY [Parameter].[CalvDays],SMSSetting.OrderNo,Details.LotNo,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat,Parameter.ConCalv,[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (([Parameter].[ConCalv] - [Parameter].[CalvDays]) - SMSSetting.FromDays) AND \n" +
                       "Julianday('now')-julianday(details.heatdate) <= (([Parameter].[ConCalv] - [Parameter].[CalvDays]) + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0)\n" +
                       "                      \n" +
                       "                       AND  SMSSetting.Task='Expected Calving'\n" +
                       "                       AND (Details.BreedingStatus IN ('Pregnant'))\n" +
                       " Union ALL  \n" +
                       " \n" +
                       "SELECT   Distinct Details.IdNo, Owner.Code, Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, \n" +
                       "SMSSetting.Task,\n" +
                       "Details.IdNo\n" +
                       ",SMSSetting.OrderNo,Details.LotNo\n" +
                       "\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      Parameter ON Details.Breed = Parameter.Breed INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "                       AND                       (Details.BreedingStatus IN ('Pregnant'))\n" +
                       "GROUP BY [Parameter].[CalvDays],SMSSetting.OrderNo,Details.LotNo,Details.CalvingDate,  Details.IdNo, Owner.name,Parameter.Heat,Parameter.ConCalv,[Details].[HeatDate],Details.BreedingStatus, \n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(details.heatdate) >= (([Parameter].[ConCalv] ) - SMSSetting.FromDays) AND \n" +
                       "Julianday('now')-julianday(details.heatdate) <= (([Parameter].[ConCalv] ) + SMSSetting.toDays) THEN [Details].[HeatDate] END > 0)\n" +
                       "                      \n" +
                       "                       AND  SMSSetting.Task='Expected Calving'\n" +
                       "                       AND (Details.BreedingStatus IN ('Pregnant'))\n" +
                       " Union ALL                        \n" +
                       " --**************END Exp Calving**********************************\n" +
                       "--**************                    **********************************       \n" +
                       " --**************Vaccination Shedule**********************************\n" +
                       "SELECT   Distinct Details.IdNo || ' '  || VaccineDetails.vaccinename AS [IDNO], Owner.Code, \n" +
                       "Owner.Phno, Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task ,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM        VaccineDetails INNER JOIN\n" +
                       "                      Details ON VaccineDetails.Idno = Details.IdNo INNER JOIN\n" +
                       "                      Option ON VaccineDetails.vaccinename = Option.Field_name INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,Option.days, Option.Field_name,VaccineDetails.vaccinename,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      (CASE WHEN Julianday('now')-julianday(VaccineDetails.Date) >= (Option.Days  * 30) - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(VaccineDetails.Date) \n" +
                       "\n" +
                       " <= ( Option.Days * 30) + SMSSetting.toDays THEN Julianday('now')-julianday(VaccineDetails.Date) ELSE '+ ' END > 0)\n" +
                       " AND  SMSSetting.Task='Due for Vaccination'\n" +
                       "\n" +
                       "Union ALL\n" +
                       "--**************Deworming Shedule**********************************\n" +
                       "SELECT   Distinct Details.IdNo || ' '  || Deworming.Deworm AS [IDNO], Owner.Code, Owner.Phno,\n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM        Deworming INNER JOIN\n" +
                       "                      Details ON Deworming.Idno = Details.IdNo INNER JOIN\n" +
                       "                      Option ON Deworming.Deworm = Option.Field_Name INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "GROUP BY SMSSetting.OrderNo,   Details.IdNo, Owner.name,Option.Days, Option.Field_Name,Deworming.Deworm,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      (CASE WHEN \n" +
                       "Julianday('now')-julianday(Deworming.Date) >= (Option.Days * 30) - SMSSetting.FromDays AND\n" +
                       "Julianday('now')-julianday(Deworming.Date)  <= (Option.Days * 30) + SMSSetting.toDays THEN \n" +
                       "Julianday('now')-julianday(Deworming.Date) ELSE '+ ' END > 0)\n" +
                       "              AND  SMSSetting.Task='Due for Deworming'         \n" +
                       "Union ALL                      \n" +
                       "--**************Medicine followup Shedule**********************************\n" +
                       "SELECT   Distinct Details.IdNo || ' '  || MedicineFollow.MedicineName AS [IDNO], Owner.Code, \n" +
                       "Owner.Phno,Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                     MedicineFollow ON Details.IdNo =MedicineFollow.IDNO INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) AND \n" +
                       "Julianday('now')-julianday(MedicineFollow.Date) >= - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(MedicineFollow.Date)  <= 2\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,MedicineFollow.MedicineName,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "                       HAVING  SMSSetting.Task='Medicine Follow Up'\n" +
                       "Union ALL\n" +
                       " --**************Treatmnt Shedule**********************************                     \n" +
                       "SELECT   Distinct Details.IdNo , Owner.Code, Owner.Phno,\n" +
                       " Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,\n" +
                       " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                     TreatmentFollow ON Details.IdNo =TreatmentFollow.IDNO INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL) AND \n" +
                       "Julianday('now')-julianday(TreatmentFollow.Date) >= - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(TreatmentFollow.Date)  <= 2\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "   HAVING  SMSSetting.Task='Treatment Follow Up'\n" +
                       "Union ALL\n" +
                       "--**************Calf Shedule**********************************\n" +
                       "SELECT   Distinct  Details.IdNo || ' '  || CalfShedule.Task, Owner.Code, Owner.Phno,\n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
                       " Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                      CalfShedule ON Details.Breed = CalfShedule.BreedID \n" +
                       "                      INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +
                       "WHERE     (Details.Disposalflg IS NULL)\n" +
                       "                      AND \n" +
                       "Julianday('now')-julianday(Details.DtOfBirth) >= - CalfShedule.Day - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(Details.DtOfBirth) <= CalfShedule.Day + SMSSetting.FromDays\t\t\t\t  \n" +
                       "\t\t\t\t\t  \n" +
                       "\t\t\t\n" +
                       "GROUP BY SMSSetting.OrderNo, Details.IdNo, Owner.name,CalfShedule.Task,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "   HAVING  SMSSetting.Task='Calf Shedule'\n" +
                       "\n" +
                       "Union ALL\n" +
                       "--**************Weight gain Shedule**********************************\n" +
                       "SELECT   Distinct  Details.IdNo , Owner.Code, Owner.Phno, \n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name,SMSSetting.Task,\n" +
                       "Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM         Details INNER JOIN\n" +
                       "                     WeightGainDet ON Details.IdNo =WeightGainDet.IdNo\n" +
                       "                       INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +

                       "WHERE     (Details.Disposalflg IS NULL)\n" +
                       "GROUP BY SMSSetting.OrderNo,  Details.IdNo, Owner.name,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "HAVING      \n" +
                       "(Julianday('now')-julianday(WeightGainDet.Date) >= 30 - SMSSetting.FromDays) AND \n" +
                       "(Julianday('now')-julianday(WeightGainDet.Date)  <= 30 + SMSSetting.toDays)\n" +
                       "   AND  SMSSetting.Task='Due for Body Weight'\n" +
                       "\n" +
                       "Union ALL\n" +
                       "--**************Diseases Testing**********************************Due for Diseases Testing\n" +
                       "SELECT   Distinct  Details.IdNo || ' '  || DiseaseTest.Disease, Owner.Code, Owner.Phno, \n" +
                       "Owner.name || ' '  || Owner.middle_name || ' '  || Owner.Last_name, SMSSetting.Task,\n" +
                       "Details.IdNo,SMSSetting.OrderNo,Details.LotNo\n" +
                       "FROM        DiseaseTest INNER JOIN\n" +
                       "                      Details ON DiseaseTest.Idno = Details.IdNo INNER JOIN\n" +
                       "                      Option ON DiseaseTest.Disease = Option.Field_Name  INNER JOIN\n" +
                       "                      Owner ON Details.Name = Owner.Code CROSS JOIN\n" +
                       "                      SMSSetting\n" +

                       "WHERE     (Details.Disposalflg IS NULL) OR\n" +
                       "                      (Details.Disposalflg = 0)\n" +
                       "\n" +
                       "GROUP BY SMSSetting.OrderNo,Details.milkDate,  Details.IdNo, Owner.name, Option.Days, Option.Field_Name,DiseaseTest.Disease,\n" +
                       "                      Owner.Phno,Owner.Middle_Name, Owner.Last_Name, Owner.Middle_Name, Owner.Last_Name,Details.Disposalflg,\n" +
                       "                      SMSSetting.Task,Owner.Code\n" +
                       "\n" +
                       "HAVING      (CASE WHEN \n" +
                       "Julianday('now')-julianday(DiseaseTest.Date)>= (Option.Days * 30) - SMSSetting.FromDays AND \n" +
                       "Julianday('now')-julianday(DiseaseTest.Date) <= (Option.Days * 30) + SMSSetting.toDays THEN \n" +
                       "Julianday('now')-julianday(DiseaseTest.Date) ELSE '+ ' END > 0)\n" +
                       "  AND  SMSSetting.Task='Due for Diseases Testing'\n" +

                       ") as U \n" +
                       "ORDER BY U.[Farmer Name],U.OrderNo, U.IdNo\n" +
                       "\n";
           }




           cursor = db.rawQuery(query, null);

          // System.out.println("No of coloumn"+cursor.getCount());
           //System.out.println("No of coloumn"+cursor.getCount());

           animalStatusObject=new JSONObject();



           if (cursor.moveToFirst()) {
               do {

                //  System.out.println("Data"+cursor.getString(0));
                   ActionBean contact = new ActionBean();
                   contact.setIdno(cursor.getString(0));
                   contact.setFarmercode(cursor.getString(1));
                   contact.setPhoneno(cursor.getString(2));
                   contact.setFarmername(cursor.getString(3));
                   contact.setTask(cursor.getString(4));
                   contact.setIdno1(cursor.getString(5));
                   contact.setOrderno(cursor.getString(6));
                   contact.setLotno(cursor.getString(7));
                   Bean_list.add(contact);



                 //  contactList.add(contact);
               } while (cursor.moveToNext());
           }




         /*  if(cursor.moveToFirst()) {
               try {

                   int columnLength=cursor.getColumnCount();
                   System.out.println("No of coloumn"+columnLength);
                   for (int i=0;i<cursor.getColumnCount();i++){
                       animalStatusObject.put(cursor.getColumnName(i),cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))));
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }*/
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           if (cursor != null) {
               cursor.close();
           }
       }
       return Bean_list;
   }
    /*SELECT [BreedCode],[Name]  FROM [Breed] WHERE SpeciesCode=SPeciesCode*/
    public ArrayList<String> getBreed(String speciesCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(BREED_TABLE_NAME, new String[]{TABLE_BREED_COLUMN_NAME, TABLE_BREED_COLUMN_BREED_CODE}, TABLE_BREED_COLUMN_SPECIES_CODE + "= ? ", new String[]{speciesCode}, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_BREED_COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_BREED_COLUMN_BREED_CODE)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }


    /*SELECT [ID],[Farmbred]  FROM [Farmbred]*/
    public ArrayList<String> getFarmbred() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            cursor = db.query(FARMBRED_TABLE_NAME, new String[]{TABLE_FARMBRED_COLUMN_FARMBRED, TABLE_FARMBRED_COLUMN_ID}, null, null, null, null, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_FARMBRED_COLUMN_FARMBRED)), cursor.getString(cursor.getColumnIndex(TABLE_FARMBRED_COLUMN_ID)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    /*Select IDNO,lotno,name from Details where idno=''*/
    public JSONObject getDetails(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        JSONObject detailsObject = null;
        try {
            String query = "SELECT Details.IdNo, Details.LotNo,Details.Name as Ownercode, Owner.Name, Owner.Middle_Name, Owner.Last_Name FROM Details INNER JOIN Owner ON Details.Name = Owner.Code WHERE Details.Idno='" + idNumber + "'";
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {

                detailsObject = new JSONObject();
                detailsObject.put("IdNo", cursor.getString(cursor.getColumnIndex("IdNo")));
                detailsObject.put("LotNo", cursor.getString(cursor.getColumnIndex("LotNo")));
                detailsObject.put("Ownercode", cursor.getString(cursor.getColumnIndex("Ownercode")));
                detailsObject.put("name", cursor.getString(cursor.getColumnIndex("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return detailsObject;
    }




    public long saveAINaturalService(String idNumber, String heatDate, String serviceType, String sireEarTagNo, String inseminator, String totalDose, String EntryFrom) {
        long status = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(this.TABLE_REPRODUCTION_COLUMN_IDNO, idNumber);
            values.put(this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT, heatDate);
            values.put(this.TABLE_REPRODUCTION_COLUMN_SERVICE, serviceType);
            values.put(this.TABLE_REPRODUCTION_COLUMN_SIREID, sireEarTagNo);
            values.put(this.TABLE_REPRODUCTION_COLUMN_INSIM, inseminator);
            values.put(this.TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE, totalDose);
            values.put(this.TABLE_REPRODUCTION_COLUMN_ENTRY, EntryFrom);
            values.put(this.TABLE_REPRODUCTION_COLUMN_PDII, Integer.valueOf(0));
            status = db.insert(REPRODUCTION_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public long savePDEntry(String firstPDResult, String secondPDResult, String pdDate, String idNumber) {
        Cursor cursor = null;
        int status = 0;
        try {
            cursor = getWritableDatabase().rawQuery("UPDATE Reproduction SET " + this.TABLE_REPRODUCTION_COLUMN_PDI + " = '" + firstPDResult + "' ," + this.TABLE_REPRODUCTION_COLUMN_PDII + "= '" + secondPDResult + "' ," + this.TABLE_REPRODUCTION_COLUMN_PD_DATE + "= '" + pdDate + "' WHERE " + this.TABLE_REPRODUCTION_COLUMN_IDNO + " = '" + idNumber + "' AND " + this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT + " IN (SELECT MAX(DtOfHeat) as mdt from Reproduction Where IdNo='" + idNumber + "' )", null);
            System.out.println("UPDATION STATUS = " + cursor.moveToFirst() + "  " + cursor.getCount());
            status = cursor != null ? 0 : -1;

            if (cursor != null) {
                cursor.close();
            }

        }catch (Exception e) {
            e.printStackTrace();

            if (cursor != null) {
                cursor.close();
            }

        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return (long) status;
    }

    public long saveCalving(String idNumber, String calvingType, String calfSex, String calvingDate, String reproductionProblems, String comments) {
        Cursor cursor = null;
        int status = 0;
        try {
            cursor = getWritableDatabase().rawQuery("UPDATE Reproduction SET " + this.TABLE_REPRODUCTION_COLUMN_CALVING_TYPE + " = '" + calvingType + "' ," + this.TABLE_REPRODUCTION_COLUMN_CALF_SEX + "= '" + calfSex + "' ," + this.TABLE_REPRODUCTION_COLUMN_DATE_OF_CALVING + "= '" + calvingDate + "' ," + this.TABLE_REPRODUCTION_COLUMN_REPRODUCTION_PROBLEM + "= '" + reproductionProblems + "' ," + this.TABLE_REPRODUCTION_COLUMN_COMMENTS + "= '" + comments + "' WHERE " + this.TABLE_REPRODUCTION_COLUMN_IDNO + " = '" + idNumber + "' AND " + this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT + " IN (SELECT MAX(DtOfHeat) as mdt from Reproduction Where IdNo='" + idNumber + "' )", null);
            System.out.println("UPDATION STATUS = " + cursor.moveToFirst() + "  " + cursor.getCount());
            status = cursor != null ? 0 : -1;
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return (long) status;
    }

    public long saveDry(String idNumber, String dryDate, String dryReason, String dryTreatment) {
        Cursor cursor = null;
        int status = 0;
        try {
            cursor = getWritableDatabase().rawQuery("UPDATE Reproduction SET " + this.TABLE_REPRODUCTION_COLUMN_PDII + " = '" + 3 + "' ," + this.TABLE_REPRODUCTION_COLUMN_DATE_OF_DRY + "= '" + dryDate + "' ," + this.TABLE_REPRODUCTION_COLUMN_DRY_REASON + "= '" + dryReason + "' ," + this.TABLE_REPRODUCTION_COLUMN_DRY_TREATMENT + "= '" + dryTreatment + "' WHERE " + this.TABLE_REPRODUCTION_COLUMN_IDNO + " = '" + idNumber + "' AND " + this.TABLE_REPRODUCTION_COLUMN_PDII + " = '" + 3 + "' AND " + this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT + " in (SELECT MAx(DtofHeat) as mdt from [Reproduction] Where Idno='" + idNumber + "' and  isdate(DtofCalving)='" + DATABASE_VERSION + "')", null);
            System.out.println("UPDATION STATUS = " + cursor.moveToFirst() + "  " + cursor.getCount());
            status = cursor != null ? 0 : -1;
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return (long) status;
    }

    public String getMaxDate(String idNumber) {
        Cursor cursor = null;
        String maxDate = null;
        try {
            cursor = getWritableDatabase().rawQuery("SELECT MAX(Date) from Production where IdNo='" + idNumber + "'", null);
            if (cursor.moveToFirst()) {
                maxDate = cursor.getString(cursor.getColumnIndex("MAX(Date)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return maxDate;
    }

    public String getMaxCalvingDate(String idNumber) {
        Cursor cursor = null;
        String maxCalvingDate = null;
        try {
            cursor = getWritableDatabase().rawQuery("SELECT MAX(CalvingDate) from Details where IdNo='" + idNumber + "'", null);
            if (cursor.moveToFirst()) {
                maxCalvingDate = cursor.getString(cursor.getColumnIndex("MAX(CalvingDate)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return maxCalvingDate;
    }

    public int getTotalDays(String idNumber, String maxDate) {
        Cursor cursor = null;
        int lastMilk = 0;
        try {
            cursor = getWritableDatabase().rawQuery("SELECT MAX(Days_total) from Production where IdNo='" + idNumber + "' AND Date='" + maxDate + "'", null);
            if (cursor.moveToFirst()) {
                lastMilk = cursor.getInt(cursor.getColumnIndex("MAX(Days_total)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return lastMilk;
    }

    public long saveMilking(String idNumber, String milkingDate, String morningMilk, String eveningMilk, String nightMilk, String mornigFat, String eveningFat, String morningSsc, String eveningSsc, String daysTotal, String lactationTotal, String daysCount) {
        SQLiteDatabase db = getWritableDatabase();
        long insertStatus = 0;
        try {

            int deleteStatus = db.delete(PRODUCTION_TABLE_NAME, this.TABLE_PRODUCTION_COLUMN_IDNO + " = ? AND " + this.TABLE_PRODUCTION_COLUMN_DATE + " = ? ", new String[]{idNumber, milkingDate});

            ContentValues values = new ContentValues();
            values.put(this.TABLE_PRODUCTION_COLUMN_IDNO, idNumber);
            values.put(this.TABLE_PRODUCTION_COLUMN_DATE, milkingDate);
            values.put(this.TABLE_PRODUCTION_COLUMN_MORNING_MILK, morningMilk);
            values.put(this.TABLE_PRODUCTION_COLUMN_EVENING_MILK, eveningMilk);
            values.put(this.TABLE_PRODUCTION_COLUMN_NIGHT_MILK, nightMilk);
            values.put(this.TABLE_PRODUCTION_COLUMN_MORING_FAT, mornigFat);
            values.put(this.TABLE_PRODUCTION_COLUMN_EVENING_FAT, eveningFat);
            values.put(this.TABLE_PRODUCTION_COLUMN_MORNING_SCC, morningSsc);
            values.put(this.TABLE_PRODUCTION_COLUMN_EVENING_SCC, eveningSsc);
            values.put(this.TABLE_PRODUCTION_COLUMN_DAYS_TOTAL, daysTotal);
            values.put(this.TABLE_PRODUCTION_COLUMN_LACTATION_TOTAL, lactationTotal);
            values.put(this.TABLE_PRODUCTION_COLUMN_DAYS_COUNT, daysCount);

            insertStatus = db.insert(PRODUCTION_TABLE_NAME, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertStatus > 0 ? 1 : -1;
    }



    public JSONObject getAnimalStatus(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        JSONObject animalStatusObject=null;
        try {
            String query = "SELECT     BreedingStatus, Species  || Status as StatusPic,\n" +
                    "CASE WHEN BreedingStatus = 'Open Unbred' OR\n" +
                    "                      BreedingStatus = 'Open Bred' THEN \n" +
                    "\t\t\t\t\t  Julianday('Now') -Julianday(calvingDate) END AS [Open Period], \n" +
                    "CASE WHEN BreedingStatus = 'Pregnant' THEN \n" +
                    "Julianday(Calvingdate) -Julianday(Heatdate) END AS [Calving to Conception], \n" +
                    "CASE WHEN BreedingStatus = 'Open Bred' OR\n" +
                    "                      BreedingStatus = 'Pregnant' THEN  strftime(\"%d-%m-%Y\", HeatDate) END AS [Last Heat Date], \n" +
                    "CASE WHEN BreedingStatus = 'Open Bred' OR\n" +
                    "                      BreedingStatus = 'Pregnant' THEN HeatSeq END AS [Numbar of AI], \n" +
                    "CASE WHEN BreedingStatus = 'Open Bred' OR\n" +
                    "                      BreedingStatus = 'Pregnant' THEN LastSire END AS [Last Sire], \n" +
                    "CASE WHEN BreedingStatus = 'Open Bred' THEN \n" +
                    " strftime(\"%d-%m-%Y\", Date(Heatdate,'+21 day'))\n" +
                    "                      END AS [Expected Heat], \n" +
                    "CASE WHEN BreedingStatus = 'Pregnant' THEN \n" +
                    "strftime(\"%d-%m-%Y\",Date(Heatdate,'+300 day')) END AS [Expected Calving Date], \n" +
                    "CASE WHEN BreedingStatus = 'Pregnant' THEN \n" +
                    "strftime(\"%d-%m-%Y\",PDDate) END AS [PD Check Date], \n" +
                    "CASE WHEN Status = 2 THEN \n" +
                    "Julianday('Now') -Julianday(Drydate) END AS [Dry Days], \n" +
                    "CASE WHEN Status = 4 OR\n" +
                    "                      Status = 5 THEN \n" +
                    "Julianday('Now') -Julianday(Calvingdate) END AS [Days in Milk], \n" +
                    "CASE WHEN Status = 4 OR\n" +
                    "                      Status = 5 THEN TotalMilk END AS [Milk Yield(kg)], \n" +
                    "CASE WHEN Status = 4 OR\n" +
                    "                      Status = 5 THEN \n" +
                    "\t\t\t\t\t  TotalMilk / Julianday('Now') -Julianday(Calvingdate) END AS [Avg Yield(kg)], \n" +
                    "CASE WHEN Status = 4 OR\n" +
                    "                      Status = 5 THEN strftime(\"%d-%m-%Y\",Date(Calvingdate,'+300 day')) END AS [Expected Dry Date]\n" +
                    "FROM         Details \n" +
                    "WHERE     (Details.IdNo = '"+idNumber+"')";


            cursor = db.rawQuery(query, null);
            animalStatusObject=new JSONObject();
            if(cursor.moveToFirst()) {
                try {
                    int columnLength=cursor.getColumnCount();
                    for (int i=0;i<cursor.getColumnCount();i++){
                        animalStatusObject.put(cursor.getColumnName(i),cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return animalStatusObject;
    }



    public int getYAxis(String idNumber) {
        Cursor cursor = null;
        int lastMilk = 0;
        try {
            cursor = getWritableDatabase().rawQuery("SELECT MAX(Days_total) from Production where IdNo='" + idNumber + "'", null);
            if (cursor.moveToFirst()) {
                lastMilk = cursor.getInt(cursor.getColumnIndex("MAX(Days_total)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return lastMilk;
    }



    public HashMap<String,ArrayList<Double>> getLactationCurve(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        HashMap<String,ArrayList<Double>> lactationCurve=null;
        try {
            String query = "SELECT     abs(JulianDay(Production.Date) - JulianDay(Details.CalvingDate)) AS [Days],\n" +
                    "                      Production.Days_Total\n" +
                    "FROM         Production INNER JOIN\n" +
                    "                      Details ON Production.IdNo = Details.IdNo\n" +
                    "WHERE     (Production.IdNo = '"+idNumber+"')\n" +
                    "ORDER BY Production.Date";
            cursor = db.rawQuery(query, null);
            try {
                lactationCurve=new HashMap<String,ArrayList<Double>>();
                ArrayList<Double> daysArray=new ArrayList<Double>();
                ArrayList<Double> daysTotalArray=new ArrayList<Double>();
                while (cursor.moveToNext()) {
                    daysArray.add( cursor.getDouble(cursor.getColumnIndex("Days")));
                    daysTotalArray.add(cursor.getDouble(cursor.getColumnIndex("Days_total")));
                }
                lactationCurve.put("Days", daysArray);
                lactationCurve.put("Days_Total",daysTotalArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lactationCurve;
    }

    public ArrayList<String> getMilkAnimalStatus(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
//            cursor = db.query(LOT_TABLE_NAME, new String[]{TABLE_LOT_COLUMN_LOTNO, TABLE_LOT_COLUMN_NAME}, null, null, null, null, null);
            String query = "SELECT     abs(JulianDay(Production.Date) -JulianDay(Details.CalvingDate)) AS [Days],  strftime(\"%d-%m-%Y\", Production.Date) as ProductionDate, Production.Mor, \n" +
                    "                      Production.Eve,Production.Days_Total, Production.Lactation_Total\n" +
                    "FROM         Production INNER JOIN\n" +
                    "                      Details ON Production.IdNo = Details.IdNo\n" +
                    "WHERE     (Production.IdNo = '"+idNumber+"')\n" +
                    "ORDER BY Production.Date";
            cursor = db.rawQuery(query, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put("Days", cursor.getString(cursor.getColumnIndex("Days")));
                    lotAndName.put("ProductionDate", cursor.getString(cursor.getColumnIndex("ProductionDate")));
                    lotAndName.put("Mor", cursor.getString(cursor.getColumnIndex("Mor")));
                    lotAndName.put("Eve", cursor.getString(cursor.getColumnIndex("Eve")));
                    lotAndName.put("Days_total", cursor.getString(cursor.getColumnIndex("Days_total")));
                    lotAndName.put("Lactation_Total", cursor.getString(cursor.getColumnIndex("Lactation_Total")));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }


    public ArrayList<String> getBreedingAnimalStatus(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
//            cursor = db.query(LOT_TABLE_NAME, new String[]{TABLE_LOT_COLUMN_LOTNO, TABLE_LOT_COLUMN_NAME}, null, null, null, null, null);


            String query="SELECT      strftime(\"%d-%m-%Y\", Reproduction.DtOfHeat) as [Service Date],Reproduction.HeatSeq as [Heat Seq],Sire.name [SireName],PDI.PD AS [PD 1], PDII.PD AS [PD 2]\n" +
                    "FROM         Reproduction  LEFT OUTER JOIN\n" +
                    "                      PDI ON Reproduction.PD1 = PDI.ID  LEFT OUTER JOIN\n" +
                    "                      PDII ON Reproduction.PD2 = PDII.ID LEFT OUTER JOIN\n" +
                    "       Sire ON Reproduction.SireId = Sire.sireid\n" +
                    "                      WHERE IdNo ='"+idNumber+"' \n" +
                    "                      AND Parity in (SELECT max(parity) from reproduction WHERe IdNo='"+idNumber+"')\n" +
                    "  \n" +
                    "                      order by Reproduction.DtOfHeat";

            /*String query = "SELECT      strftime(\"%d-%m-%Y\", Reproduction.DtOfHeat) as [Service Date],Reproduction.HeatSeq as [Heat Seq],Sireid,PDI.PD AS [PD 1], PDII.PD AS [PD 2]\n" +
                    "FROM         Reproduction INNER JOIN\n" +
                    "                      PDI ON Reproduction.PD1 = PDI.ID INNER JOIN\n" +
                    "                      PDII ON Reproduction.PD2 = PDII.ID \n" +
                    "                      WHERE IdNo ='"+idNumber+"' \n" +
                    "                      AND Parity in (SELECT max(parity) from Reproduction WHERe IdNo='"+idNumber+"')\n" +
                    "                      order by Reproduction.DtOfHeat";*/
            cursor = db.rawQuery(query, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put("Service Date", cursor.getString(cursor.getColumnIndex("Service Date")));
                    lotAndName.put("Heat Seq", cursor.getString(cursor.getColumnIndex("Heat Seq")));
                    lotAndName.put("SireId", cursor.getString(cursor.getColumnIndex("SireName")));
                    lotAndName.put("PD 1", cursor.getString(cursor.getColumnIndex("PD 1")));
                    lotAndName.put("PD 2", cursor.getString(cursor.getColumnIndex("PD 2")));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }


    public ArrayList<String> getHealthAnimalStatus(String idNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
//            cursor = db.query(LOT_TABLE_NAME, new String[]{TABLE_LOT_COLUMN_LOTNO, TABLE_LOT_COLUMN_NAME}, null, null, null, null, null);
            String query = "Select 'Vaccination',strftime(\"%d-%m-%Y\", Date) as [Date],vaccinename + '-' + VACCINE From VaccineDetails WHERE Idno='3'\n" +
                    "Union ALL\n" +
                    "Select 'Deworming',strftime(\"%d-%m-%Y\", Date) as [Date],Deworm + '-' + Dewormer From Deworming WHERE Idno='3'\n" +
                    "Union ALL\n" +
                    "Select 'Treatment',strftime(\"%d-%m-%Y\", FromDate) as [Date], Complain + '-' + Diagnosis From Treatment WHERE Idno='3'\n" +
                    "\n" +
                    "Order By [Date]";
            cursor = db.rawQuery(query, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put("Service Date", cursor.getString(cursor.getColumnIndex("Service Date")));
                    lotAndName.put("Heat Seq", cursor.getString(cursor.getColumnIndex("Heat Seq")));
                    lotAndName.put("SireId", cursor.getString(cursor.getColumnIndex("SireId")));
                    lotAndName.put("PD 1", cursor.getString(cursor.getColumnIndex("PD 1")));
                    lotAndName.put("PD 2", cursor.getString(cursor.getColumnIndex("PD 2")));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public boolean isCattlePresent(String cattleId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;
        int columnCount = 0;
        try {
            String str = DETAILS_TABLE_NAME;
            String[] strArr = new String[DATABASE_VERSION];
            strArr[0] = this.TABLE_DETAILS_COLUMN_IDNO;
            String str2 = this.TABLE_DETAILS_COLUMN_IDNO + " = ? ";
            String[] strArr2 = new String[DATABASE_VERSION];
            strArr2[0] = cattleId;
            cursor = db.query(str, strArr, str2, strArr2, null, null, null, null);
            columnCount = cursor.getCount();
            if (columnCount > 0) {
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }


    public ArrayList<String> getSireEarTagListHardCoded(String idNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<String> lotNumberName = null;
        try {
            String query = "SELECT SireId,Name FROM Sire WHERE Cbflg ='"+2+"' and NAFlag ='"+2+"' order by Name ASC";
            cursor = db.rawQuery(query, null);
            lotNumberName = new ArrayList<String>();
            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {
                    lotAndName.put(cursor.getString(cursor.getColumnIndex(TABLE_SIRE_COLUMN_SIRE_NAME)), cursor.getString(cursor.getColumnIndex(TABLE_SIRE_COLUMN_SIRE_ID)));
                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lotNumberName;
    }

    public long saveRegistrationFirst(String IDNo, String Herdno, String Lotno, String Name, String HlaName, String DTOFBIRTH, String SexFlg, String Species, String BREED, String Pbflg, String Registration_Date, String uid, String allowuser) {
        long status = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(this.TABLE_DETAILS_COLUMN_IDNO, IDNo);
            values.put(this.TABLE_DETAILS_COLUMN_HERDNO, Herdno);
            values.put(this.TABLE_DETAILS_COLUMN_LOTNO, Lotno);
            values.put(this.TABLE_DETAILS_COLUMN_NAME, Name);
            values.put(this.TABLE_DETAILS_COLUMN_HLANAME, HlaName);
            values.put(this.TABLE_DETAILS_COLUMN_DOB, DTOFBIRTH);
            values.put(this.TABLE_DETAILS_COLUMN_SEX_FLAF, SexFlg);
            values.put(this.TABLE_DETAILS_COLUMN_SPECIES, Species);
            values.put(this.TABLE_DETAILS_COLUMN_BREED, BREED);
            values.put(this.TABLE_DETAILS_COLUMN_PB_FLAG, Pbflg);
            values.put(this.TABLE_DETAILS_COLUMN_REGISTRATION_DATE, Registration_Date);
            values.put(this.TABLE_DETAILS_COLUMN_UID, uid);
            values.put(this.TABLE_DETAILS_COLUMN_ALLOW_USE, allowuser);
            status = db.insert(DETAILS_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public void saveBreeding() {
    }

    public int getConCalv(String breedId) {
        Cursor cursor = null;
        int lastMilk = 0;
        try {
            cursor = getWritableDatabase().rawQuery("Select ConCalv FROM Parameter where breed='" + breedId + "'", null);
            if (cursor.moveToFirst()) {
                lastMilk = cursor.getInt(cursor.getColumnIndex("ConCalv"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return lastMilk;
    }

    public int getLacLen() {
        Cursor cursor = null;
        int lastMilk = 0;
        try {
            cursor = getWritableDatabase().rawQuery("select LACLEN from Parameter", null);
            if (cursor.moveToFirst()) {
                lastMilk = cursor.getInt(cursor.getColumnIndex("LACLEN"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return lastMilk;
    }

    public void saveRegistration(CattleBean cattleBean) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        saveRegistrationFirst(cattleBean.getAnimalId(), cattleBean.getHerdId(), cattleBean.getLotId(), cattleBean.getOwnerId(), cattleBean.getAnimalName(), cattleBean.getBirthDate(), cattleBean.getSexId(), cattleBean.getSpeciesId(), cattleBean.getBreedId(), cattleBean.getFarmIdBreed(), cattleBean.getRegistrationDate(), SessionManager.getSessionInstance().getPrefData(USER_TABLE_FIELD_UUID), "1");
        try {
            ContentValues values;

            if (cattleBean.getCalvingDryPregnant() == DATABASE_VERSION) {

                values = new ContentValues();
                values.put(this.TABLE_REPRODUCTION_COLUMN_IDNO, cattleBean.getAnimalId());
                values.put(this.TABLE_REPRODUCTION_COLUMN_PARITY, cattleBean.getNumberOfCalvingDays());
                values.put(this.TABLE_REPRODUCTION_COLUMN_DRY_HEAT_SEQUENCE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT, cattleBean.getCalvingHeateDate());
                values.put(this.TABLE_REPRODUCTION_COLUMN_SERVICE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_SIREID, cattleBean.getCalvingSire());
                values.put(this.TABLE_REPRODUCTION_COLUMN_INSIM, cattleBean.getCalvingInsim());
                values.put(this.TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PDI, "3");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PDII, "3");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PD_DATE, cattleBean.getCalvingDate());
                values.put(this.TABLE_REPRODUCTION_COLUMN_DATE_OF_CALVING, cattleBean.getCalvingDate());
                values.put(this.TABLE_REPRODUCTION_COLUMN_CALVING_TYPE, cattleBean.getCalvingNormalOrAbNormal());
                values.put(this.TABLE_REPRODUCTION_COLUMN_CALF_SEX, cattleBean.getCalfSex());
                values.put(this.TABLE_REPRODUCTION_COLUMN_ENTRY, "M");
                values.put(this.TABLE_REPRODUCTION_COLUMN_ENTRY, "0");
                values.put("SyncStatus","0");




                System.out.println("SEE THE STATUS = " + db.insert(REPRODUCTION_TABLE_NAME, null, values));

            } else if (cattleBean.getCalvingDryPregnant() == 2) {

                values = new ContentValues();
                values.put(this.TABLE_REPRODUCTION_COLUMN_IDNO, cattleBean.getAnimalId());
                values.put(this.TABLE_REPRODUCTION_COLUMN_PARITY, cattleBean.getNumberOfCalvingDays());
                values.put(this.TABLE_REPRODUCTION_COLUMN_DRY_HEAT_SEQUENCE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT, cattleBean.getCalvingHeateDate());
                values.put(this.TABLE_REPRODUCTION_COLUMN_SERVICE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_SIREID, cattleBean.getCalvingSire());
                values.put(this.TABLE_REPRODUCTION_COLUMN_INSIM, cattleBean.getCalvingInsim());
                values.put(this.TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PDI, "3");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PDII, "3");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PD_DATE, cattleBean.getDryDate());
                values.put(this.TABLE_REPRODUCTION_COLUMN_DATE_OF_CALVING, cattleBean.getCalvingDate());
                values.put(this.TABLE_REPRODUCTION_COLUMN_CALVING_TYPE, cattleBean.getCalvingNormalOrAbNormal());
                values.put(this.TABLE_REPRODUCTION_COLUMN_CALF_SEX, cattleBean.getCalfSex());
                values.put(this.TABLE_REPRODUCTION_COLUMN_ENTRY, "M");
                values.put(this.TABLE_REPRODUCTION_COLUMN_DATE_OF_DRY, cattleBean.getDryDate());
                values.put("SyncStatus","0");



                System.out.println("SEE THE STATUS = " + db.insert(REPRODUCTION_TABLE_NAME, null, values));

            }

           // values.put(this.TABLE_REPRODUCTION_COLUMN_DATE_OF_DRY, cattleBean.getDryDate());

            if (cattleBean.getPregnantOrAi() == DATABASE_VERSION) {

                int parseInt;
                values = new ContentValues();
                values.put(this.TABLE_REPRODUCTION_COLUMN_IDNO, cattleBean.getAnimalId());
                String str = this.TABLE_REPRODUCTION_COLUMN_PARITY;
                if (cattleBean.getCalvingDryPregnant() == 3) {
                    parseInt = Integer.parseInt(cattleBean.getNumberOfCalvingDays());
                } else {
                    parseInt = Integer.parseInt(cattleBean.getNumberOfCalvingDays()) + DATABASE_VERSION;
                }
                values.put(str, Integer.valueOf(parseInt));
                values.put(this.TABLE_REPRODUCTION_COLUMN_DRY_HEAT_SEQUENCE, cattleBean.getHeatSequence());
                values.put(this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT, cattleBean.pregnantHeatDate);
                values.put(this.TABLE_REPRODUCTION_COLUMN_SERVICE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_SIREID, cattleBean.getPregnantSire());
                values.put(this.TABLE_REPRODUCTION_COLUMN_INSIM, cattleBean.getPregnantInsim());
                values.put(this.TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PDI, "3");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PDII, "3");
                values.put(this.TABLE_REPRODUCTION_COLUMN_PD_DATE, cattleBean.pregnantDate);
                values.put(this.TABLE_REPRODUCTION_COLUMN_ENTRY, "M");
               // values.put("SyncStatus","0");


                System.out.println("SEE THE STATUS = " + db.insert(REPRODUCTION_TABLE_NAME, null, values));

            } else if (cattleBean.getPregnantOrAi() == 2) {

                values = new ContentValues();
                values.put(this.TABLE_REPRODUCTION_COLUMN_IDNO, cattleBean.getAnimalId());
                values.put(this.TABLE_REPRODUCTION_COLUMN_PARITY, Integer.valueOf(cattleBean.getCalvingDryPregnant() == 3 ? Integer.parseInt(cattleBean.getNumberOfCalvingDays()) : Integer.parseInt(cattleBean.getNumberOfCalvingDays()) + DATABASE_VERSION));
                values.put(this.TABLE_REPRODUCTION_COLUMN_DRY_HEAT_SEQUENCE, cattleBean.getHeatSequence());
                values.put(this.TABLE_REPRODUCTION_COLUMN_DTOFHEAT, cattleBean.pregnantHeatDate);
                values.put(this.TABLE_REPRODUCTION_COLUMN_SERVICE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_SIREID, cattleBean.getPregnantSire());
                values.put(this.TABLE_REPRODUCTION_COLUMN_INSIM, cattleBean.getPregnantInsim());
                values.put(this.TABLE_REPRODUCTION_COLUMN_TOTAL_DOSE, "1");
                values.put(this.TABLE_REPRODUCTION_COLUMN_ENTRY, "M");
                System.out.println("SEE THE STATUS = " + db.insert(REPRODUCTION_TABLE_NAME, null, values));
            }





            db.setTransactionSuccessful();




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public ArrayList<String> getDisease() {

        Cursor cursor = null;
        ArrayList<String> idNo = new ArrayList();
        try {
            cursor = getWritableDatabase().rawQuery("Select Field_Name from Option WHERE Field_Type='Vaccinations'", null);
            while (cursor.moveToNext()) {
                try {
                    idNo.add(cursor.getString(cursor.getColumnIndex("Field_Name")));
                } catch (Throwable th2) {
                    ArrayList<String> arrayList = idNo;
                }
            }
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
            return idNo;
    }



    public ArrayList<String> getnameCodeMedicineLedger(String diseaseId) {
        ArrayList<String> lotNumberName = new ArrayList();
        Cursor cursor = null;
        try {

            String Query = "Select Name,CompanyName,CODE  FROM [Medicineledger] where  type='Vaccine'\n";

            cursor = getWritableDatabase().rawQuery(Query, null);




            while (cursor.moveToNext()) {
                JSONObject lotAndName = new JSONObject();
                try {



                    lotAndName.put(cursor.getString(0), cursor.getString(2));



                    lotNumberName.add(lotAndName.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
                return lotNumberName;

    }

    public ArrayList<String> getRoute() {
        Cursor cursor = null;
        ArrayList<String> idNo = new ArrayList();
        try {
            cursor = getWritableDatabase().rawQuery("Select Field_Name from Option WHERE Field_Type='Route'", null);

            while (cursor.moveToNext()) {
                try {
                    idNo.add(cursor.getString(cursor.getColumnIndex("Field_Name")));
                } catch (Throwable th2) {

                    ArrayList<String> arrayList = idNo;

                }
            }
        }

        finally {
            if (cursor != null) {
                cursor.close();
            }
        }

            return idNo;
        }






    public ArrayList<String> getAnimalDetails(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        JSONObject animalStatusObject=null;

        String Query = "SELECT     'Status Pic',Species  || Status as StatusPic FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "SELECT     'Image',Photo as StatusPic FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "SELECT    'Lot Code' as [Label], Lot.lotno ||  '-'  || Lot.name  FROM Details\n" +
                "INNER JOIN\n" +
                "                      Lot ON Details.Lotno = Lot.Lotno\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "\n" +
                "SELECT    'Owner Code' as [Label], Owner.Code  FROM Details\n" +
                "INNER JOIN\n" +
                "                      Owner ON Details.NAme = Owner.Code\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "\n" +
                "SELECT    'Owner Name' as [Label], Owner.NAme  ||  ' '  || Owner.middle_name  ||  ' '   ||  Owner.Last_Name FROM Details\n" +
                "INNER JOIN\n" +
                "                      Owner ON Details.NAme = Owner.Code\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "select 'Age',\n" +
                "    case\n" +
                "        when date(DtofBirth, '+' ||\n" +
                "            strftime('%Y', 'now') - strftime('%Y', DtofBirth) ||\n" +
                "            ' years') >= date('now')\n" +
                "        then strftime('%Y', 'now') - strftime('%Y', DtofBirth)\n" +
                "        else strftime('%Y', 'now') - strftime('%Y', DtofBirth) - 1\n" +
                "    end\n" +
                "    as age\n" +
                "from Details      WHERE IdNo ='"+id+"' \n" +
                "\n" +
                "Union ALL\n" +
                "\n" +
                "SELECT    'Breed Name' as [Label], Breed.name  FROM Details\n" +
                "INNER JOIN\n" +
                "                      Breed ON Details.Breed = Breed.BreedCode\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "\n" +
                "SELECT    'Status' as [Label], Status.name  FROM Details\n" +
                "INNER JOIN\n" +
                "                      Status ON Details.Status = Status.StatusCode\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "\n" +
                "SELECT    'Breeding Status' as [Label], Details.BreedingStatus  FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "SELECT    'Open Period' as [Label], cast(Julianday('Now') -Julianday(calvingDate)  as INT) FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND (BreedingStatus = 'Open Unbred' OR\n" +
                "                      BreedingStatus = 'Open Bred' )\n" +
                "Union ALL\n" +
                "SELECT    'Conception to Calving' , cast(Julianday('CalvingDate') -Julianday(HeatDate)  as INT)  FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND BreedingStatus = 'Pregnant' \n" +
                "Union ALL\n" +
                "SELECT    'No of A.I' , [Heatseq]  FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (BreedingStatus = 'Open Bred'  or BreedingStatus = 'Pregnant' )\n" +
                "Union ALL\n" +
                "\n" +
                "SELECT    'Last Sire' as [Label], [LastSire]  FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (BreedingStatus = 'Open Bred'  or BreedingStatus = 'Pregnant' )\n" +
                "Union ALL\n" +
                "SELECT    'Last Heat Date' as [Label], [HeatDate]  FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (BreedingStatus = 'Open Bred')\n" +
                "Union ALL\n" +
                "SELECT    'Expected Heat Date' as [Label],  strftime(\"%d-%m-%Y\", Date(Heatdate,'+21 day')) FROM Details\n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (BreedingStatus = 'Open Bred')\n" +
                "Union ALL\n" +
                "SELECT    'Gestation Days' as [Label], cast(Julianday('Now')  - Julianday('HeatDate')   as INT) FROM Details \n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (BreedingStatus = 'Pregnant')\n" +
                "Union ALL\n" +
                "SELECT    'Expected Calving Date' as [Label], strftime(\"%d-%m-%Y\",Date(Heatdate,'+300 day')) FROM Details \n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (BreedingStatus = 'Pregnant')\n" +
                "Union ALL\n" +
                "SELECT    'Dry Days' as [Label], strftime(\"%d-%m-%Y\",Date(CalvingDate,'+300 day')) FROM Details \n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (Status = 2 or Status = 3)\n" +
                "Union ALL\n" +
                "SELECT    'Days in Milk' as [Label],cast( Julianday('Now')  - Julianday('CalvingDate')   as INT)  FROM Details \n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (Status = 4 or Status = 5)\n" +
                "Union ALL\n" +
                "SELECT    'Milk Yield(kg)' as [Label], cast(Sum(Lactation_Total) AS INT)  FROM Production \n" +
                "WHERE     (Production.IdNo = '"+id+"')\n" +
                "Union ALL\n" +
                "SELECT    'Avg Yield(kg)' as [Label], cast(Sum(Lactation_Total) /(Julianday('Now')  - Julianday('CalvingDate'))  as INT)  FROM Production \n" +
                "WHERE     (Production.IdNo = '"+id+"')\n" +
                "Union All\n" +
                "SELECT    'Peak Yield(kg)' as [Label], cast(MAx(Days_Total)  as INT)  FROM production \n" +
                "WHERE     (production.IdNo = '"+id+"')\n" +
                "Union All\n" +
                "SELECT    'Expected Dry Date' as [Label], strftime(\"%d-%m-%Y\",Date(Calvingdate,'+300 day')) FROM Details \n" +
                "WHERE     (Details.IdNo = '"+id+"')\n" +
                "AND  (Status = 4 or Status = 5)\n";

           cursor = db.rawQuery(Query, null);


        animalStatusObject=new JSONObject();


         System.out.println("No of coloumn"+cursor.getCount());
         System.out.println("No of coloumn"+cursor.getCount());

           ArrayList<String> data = new ArrayList<String>();


        if (cursor.moveToFirst()) {
            do {

                data.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }


        db.close();

     return  data;

    }





    public   void Save_Purchase(String IdNo ,String Dateofpurchased , String Cost , String Sourceid) {


        deletePurchaseDetais(IdNo,Dateofpurchased,Cost,Sourceid);



    }
    public void insertPurchaseDetails(String IdNo ,String Dateofpurchased , String Cost , String Sourceid){
        SQLiteDatabase db =this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("IDNo",IdNo);
        values.put("DTOFPURCH",Dateofpurchased);
        values.put("COST",Cost);
        values.put("Sourceid",Sourceid);
        db.insert("[PurchaseDetails]",null,values);
       // db.close();
    }


    public void deletePurchaseDetais(String IdNo ,String Dateofpurchased , String Cost , String Sourceid){
        SQLiteDatabase db =this.getWritableDatabase();
        db.beginTransaction();
        String sqlstr;
//        sqlstr = "Delete from [PurchaseDetails]";
//        sqlstr = (sqlstr + (" where IDNo=\'"
//                + (IdNo + "\'")));
//        db.execSQL(sqlstr);

        String wherClass = "IDNo = ?";
        String []whereArg = {IdNo};
        db.delete("[PurchaseDetails]",wherClass,whereArg);
      //  db.close();
        insertPurchaseDetails(IdNo,Dateofpurchased,Cost,Sourceid);
    }

  public void save_parent_detail(String AnimalId,String sire , String dam,String paternalsire , String paternaldam){

      SQLiteDatabase db = this.getWritableDatabase();
      db.beginTransaction();
      String where = "IDNo = ?";
      String[] whereArgs = new String[] {String.valueOf(AnimalId)};
      ContentValues values = new ContentValues();
      values.put("[Sire]",AnimalId);
      values.put("[Dam]",dam);
      values.put("[paternalSire]",paternalsire);
      values.put("[PaternalDam]",paternaldam);
      values.put("[SyncStatus]","0");


      db.update("[Details]",values,where,whereArgs);


  }


  public void save_Insurance_details(String Idno,String PolicyNo,String Value,String CompanyName,String IssueDate,String DueDate,String AgentName,String ClaimDate,String Reason,String Status,String SettlementDate,String AmtRecd ){
      delete_insurance_details(Idno);
      add_insurance_details(Idno,PolicyNo,Value,CompanyName,IssueDate,DueDate,AgentName,ClaimDate,Reason,Status,SettlementDate,AmtRecd);
  }


 public void delete_insurance_details(String IdNo){

     SQLiteDatabase db =this.getWritableDatabase();
     db.beginTransaction();
     String sqlstr;
     String wherClass = "IDNo = ?";
     String []whereArg = {IdNo};
     db.delete("[Insurance]",wherClass,whereArg);


 }

    public void add_insurance_details(String Idno,String PolicyNo,String Value, String CompanyName,String IssueDate,String DueDate, String AgentName,String ClaimDate,String Reason, String Status,String SettlementDate,String AmtRecd  ){

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String where = "IDNo = ?";
        String[] whereArgs = new String[] {String.valueOf(Idno)};
        ContentValues values = new ContentValues();
        values.put("[Idno]",Idno);
        values.put("[PolicyNo]",PolicyNo); //
        values.put("[Value]",Value);
        values.put("[CompanyName]",CompanyName); //
        values.put("[IssueDate]",IssueDate);
        values.put("[DueDate]",DueDate);
        values.put("[AgentName]",AgentName);//
        values.put("[ClaimDate]",ClaimDate);
        values.put("[Reason]",Reason);
        values.put("[Status]",Status);
        values.put("[SettlementDate]",SettlementDate);
        values.put("[AmtRecd]",AmtRecd);
        db.insert("[Insurance]",null,values);

    }


    public void addOtherDetails(String idno ,String marketvalue , String Noofrings ,String rearingpurpose , String color ,String horndistance,String doctor ){
         SQLiteDatabase db = this.getWritableDatabase();
         db.beginTransaction();
         String where = "IDNo = ?";
         String[] whereArgs = new String[] {String.valueOf(idno)};
         ContentValues values = new ContentValues();
         values.put("Market_Value",marketvalue);
         values.put("No_rings",Noofrings);
         values.put("Rearing_Purpose",rearingpurpose);
         values.put("Color",color);
         values.put("Horn_distance",horndistance);
         values.put("Doctor",doctor);
         values.put("SyncStatus","0");





         db.update("[Details]",values,where,whereArgs);
 }


 public ArrayList<SearchBean> getAll_ids(){

     SQLiteDatabase db = this.getWritableDatabase();
     Cursor cursor = null;
     JSONObject animalStatusObject=null;


     String Query = "SELECT   Details.IdNo , Owner.Code as [Ownercode], Owner.Phno, Owner.name || Owner.middle_name || Owner.Last_name  as [FarmerNAme]\n" +
             "FROM         Details INNER JOIN\n" +
             "                     \n" +
             "                      Owner ON Details.Name = Owner.Code\n" +
             "       Order by Owner.name,Idno";



     animalStatusObject=new JSONObject();

     cursor = db.rawQuery(Query, null);


     System.out.println("No of coloumn"+cursor.getCount());
     System.out.println("No of coloumn"+cursor.getCount());

     ArrayList<SearchBean> data = new ArrayList<SearchBean>();


     if (cursor.moveToFirst()) {
         do {
           SearchBean bean = new SearchBean();

             bean.setIdno(cursor.getString(0));
             bean.setOwnercode(cursor.getString(1));
             bean.setPhoneno(cursor.getString(2));
             bean.setFarmername(cursor.getString(3));

             data.add(bean);

         } while (cursor.moveToNext());
     }


     db.close();

     return  data;
 }

  public String getDose(String strt){
      ArrayList<String> lotNumberName = new ArrayList<String>();
      Cursor cursor = null;
      try {
          String Query = "Select dose as SumQun FROM [Medicineledger] where Code= "+strt;
          cursor = getWritableDatabase().rawQuery(Query, null);

          System.out.println("COLOUMN "+cursor.getCount());

            while (cursor.moveToNext()) {

          }

      }


      finally {
          if (cursor != null) {
              cursor.close();
          }
      }






      return strt;
  }


    public void Insert_Vaccination(String id, String Date,String cmbvaccinationtext,String cmbvaccine,String batch,String dose,String rate ,String route,String doneby){

        Log.e("[Idno]",id);
        Log.e("[Date]",Date); //
        Log.e("vaccinename",cmbvaccinationtext);
        Log.e("VACCINE",cmbvaccine); //
        Log.e("source",dose);
        Log.e("Batchno",batch);
        Log.e("Dose",dose);//
        Log.e("cost",rate);
        Log.e("ROUTE",route);
        Log.e("[DoneBy]",doneby);


        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        Cursor cursor = null;

        try {


         String where = "IDNo = ?";
         String[] whereArgs = new String[] {String.valueOf(id)};
         ContentValues values = new ContentValues();
         values.put("[Idno]",id);
         values.put("[Date]",Date); //
         values.put("vaccinename",cmbvaccinationtext);
         values.put("VACCINE",cmbvaccine); //
         values.put("source",dose);
         values.put("Batchno",batch);
         values.put("Dose",dose);//
         values.put("cost",rate);
         values.put("ROUTE",route);
         values.put("[DoneBy]",doneby);
         values.put("[SyncStatus]",0);
         long ids = db.insert("[VaccineDetails]",null,values);

            String Query = "Select * from " + "[VaccineDetails]";
          cursor = db.rawQuery(Query, null);




            Log.e("Count",cursor.getCount()+"");
            Log.e("Count",ids+"");
            Log.e("Count",id+"");



           // checkInsertedTable(id);


     }

     finally {
        if (cursor != null) {
            cursor.close();
        }
    }



    }





    private void checkInsertedTable(String  id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        Cursor cursor = null;

        String Query = "Select *  FROM [VaccineDetails] where [Idno]= "+id;

        cursor = db.rawQuery(Query, null);
        Log.e("Row",cursor.getCount()+"" );
        if (cursor.moveToFirst()) {
            do {
                Log.e("Row",cursor.getString(0)+"") ;

            } while (cursor.moveToNext());
        }









    }






    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "provab.herdman" + "/databases/" + "HerdMan";
        String backupDBPath = "New.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Insert_Deworming(String id, String Date,String cmbvaccinationtext,String cmbvaccine,String batch,String dose,String rate ,String route,String doneby){


        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        Cursor cursor = null;
        try {
            String where = "IDNo = ?";
            String[] whereArgs = new String[] {String.valueOf(id)};
            ContentValues values = new ContentValues();
            values.put("[Idno]",id);
            values.put("[Date]",Date); //
            values.put("vaccinename",cmbvaccinationtext);
            values.put("VACCINE",cmbvaccine); //
            values.put("source",dose);
            values.put("Batchno",batch);
            values.put("Dose",dose);//
            values.put("cost",rate);
            values.put("ROUTE",route);
            values.put("[DoneBy]",doneby);
            db.insert("[Deworming]",null,values);

        }

        finally {
            if (cursor != null) {
                cursor.close();
            }
        }



    }



    public ArrayList<DiseaseBean> Read_Data_vaccine(){

          ArrayList<DiseaseBean> beans = new ArrayList<DiseaseBean>();


          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = null;
          JSONObject animalStatusObject=null;

          String Query = "SELECT max([vaccinename]) as [Diseases],max([date]) as [Date],COUNT(Idno) as[Animals]  FROM [VaccineDetails]\n" +
                  "  group by [vaccinename],[date]";


          cursor = db.rawQuery(Query, null);


          System.out.println("No of coloumn"+cursor.getCount());
          System.out.println("No of coloumn"+cursor.getCount());

          ArrayList<SearchBean> data = new ArrayList<SearchBean>();


          if (cursor.moveToFirst()) {
              do {
                  DiseaseBean bean = new DiseaseBean();
                  bean.setDiseases(cursor.getString(0));
                  bean.setDate(cursor.getString(1));
                  bean.setAnimals(cursor.getString(2));
                  beans.add(bean);

              } while (cursor.moveToNext());
          }


          db.close();



          return  beans;


      }


 public ArrayList<String> get_all_reason(){


     SQLiteDatabase db = this.getWritableDatabase();
     Cursor cursor = null;
     JSONObject animalStatusObject=null;
     ArrayList<String> data = new ArrayList<String>();
     String Query = "SELECT Field_name\n" +
             "  FROM [Option] where field_type='Cull Reasons'";

     cursor = db.rawQuery(Query, null);

     if (cursor.moveToFirst()) {
         do {

             data.add(cursor.getString(0));

         } while (cursor.moveToNext());
     }


     db.close();

     return data;


 }




 public void insert_disposal(String IdNo ,String flag ,String date ,String soldto,String Sprice,String Reason,String remark ){

     SQLiteDatabase db = this.getWritableDatabase();
     db.beginTransaction();
     Cursor cursor1 = null;
     Cursor cursor2 = null;
     Cursor cursor3 = null;
     JSONObject animalStatusObject=null;
     String sqlstr;
     String wherClass = "IDNo = ?";
     String []whereArg = {IdNo};
     db.delete("[DiedDetails]",wherClass,whereArg);

     ContentValues values = new ContentValues();
     values.put("oldidno",IdNo);
     values.put("Idno",IdNo); //
     values.put("flag",flag);
     values.put("[Date]",date); //
     values.put("Soldto",soldto);
     values.put("SPrice",Sprice);
     values.put("Reason",Reason);//
     values.put("[remark]",remark);
     db.insert("[DiedDetails]",null,values);
     long a = db.insert("[DiedDetails]",null,values);
     String where = "IDNo = ?";
     String[] whereArgs = new String[] {String.valueOf(IdNo)};
     ContentValues valuess = new ContentValues();
     valuess.put("DisposalFlg","3");
     db.update("[Details]",valuess,where,whereArgs);
     long b =  db.update("[Details]",valuess,where,whereArgs);


    }




  public  void insert_BodyWeight(String IdNo,String date ,String chestgirth ,String Weight,String Length ,String weightgain ,String autono ){

      SQLiteDatabase db = this.getWritableDatabase();
      db.beginTransaction();
      JSONObject animalStatusObject=null;
      String sqlstr;
      String wherClass = "IDNo = ?";
      String []whereArg = {IdNo};
      db.delete("[WeightGainDet]",wherClass,whereArg);

      ContentValues values = new ContentValues();

      values.put("[IdNo]",IdNo); //
      values.put("[Date]",date); //
      values.put("[ChestGirth]",chestgirth);
      values.put("[Weight]",Weight);
      values.put("[Length]",Length);//
      values.put("[Weightgain]",weightgain);
      values.put("[autono]",autono);
      db.insert("[WeightGainDet]",null,values);




}





public  ArrayList<String> getSystemdata(){

    ArrayList<String> beans = new ArrayList<String>();


    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = null;
    JSONObject animalStatusObject=null;
    String Query = "select field_name from option where field_type='System'";
    cursor = db.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
        do {
            beans.add(cursor.getString(0));
        } while (cursor.moveToNext());
    }


    db.close();



    return  beans;
}



        public  ArrayList<MedicineBean> getmedicinetype(){

            ArrayList<MedicineBean> datsas = new ArrayList<MedicineBean>();

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = null;
            JSONObject animalStatusObject=null;
            String Query = "Select Name,CompanyName,CODE  FROM [Medicineledger]";
            cursor = db.rawQuery(Query, null);
            if (cursor.moveToFirst()) {
                do {
                  MedicineBean bean  = new MedicineBean();
                    bean.setName(cursor.getString(0));
                    bean.setCompanyname(cursor.getString(1));
                    bean.setCode(cursor.getString(2));
                    datsas.add(bean);
                    // datsas.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }


            db.close();



            return  datsas;


        }




   public void Update_Sync_Flag(String TableName ,String FlagValue , String ColoumnName){



       SQLiteDatabase db = this.getWritableDatabase();
       db.beginTransaction();
       ContentValues values = new ContentValues();
       values.put("SyncStatus",FlagValue);
       db.update(TableName,values,null,null);
       db.endTransaction();







   }



     public String SyncCattleRegistration(String Uid){
         ArrayList<ReproductionBean> arrayList = new ArrayList<ReproductionBean>();


         SQLiteDatabase db = this.getWritableDatabase();





         Cursor cursor = null;
         JSONObject animalStatusObject= null;



         String Query = "select * from reproduction\n";
         cursor = db.rawQuery(Query, null);


         if (cursor.moveToFirst()) {
             do {

                     ReproductionBean bean = new ReproductionBean();
                     bean.setCompanycode(String.valueOf(cursor.getString(0)));
                     bean.setIdno(String.valueOf(cursor.getString(1)));
                     bean.setParity(String.valueOf(cursor.getString(2)));
                     bean.setHeatseq(String.valueOf(cursor.getString(3)));
                     bean.setService(String.valueOf(cursor.getString(4)));
                     bean.setDtofheat(String.valueOf(cursor.getString(5)));
                     bean.setInsim(String.valueOf(cursor.getString(6)));
                     bean.setSireid(String.valueOf(cursor.getString(7)));
                     bean.setPd1(String.valueOf(cursor.getString(8)));
                     bean.setRmpd1(String.valueOf(cursor.getString(9)));
                     bean.setPd2(String.valueOf(cursor.getString(10)));
                     bean.setRmpd2(String.valueOf(cursor.getString(11)));
                     bean.setPddate(String.valueOf(cursor.getString(12)));
                     bean.setDateofcalving(String.valueOf(cursor.getString(13)));
                     bean.setDtofdry(String.valueOf(cursor.getString(14)));
                     bean.setDryreason(String.valueOf(cursor.getString(15)));
                     bean.setDrytreatement(String.valueOf(cursor.getString(16)));
                     bean.setFlg(String.valueOf(cursor.getString(17)));
                     bean.setCalf(String.valueOf(cursor.getString(18)));
                     bean.setSex(String.valueOf(cursor.getString(19)));
                     bean.setCtype(String.valueOf(cursor.getString(20)));
                     bean.setRop(String.valueOf(cursor.getString(21)));
                     bean.setComments(String.valueOf(cursor.getString(22)));
                     bean.setRp(String.valueOf(cursor.getString(23)));
                     bean.setEntry(String.valueOf(cursor.getString(24)));
                     bean.setTotaldose(String.valueOf(cursor.getString(25)));
                     bean.setAbortionseq(String.valueOf(cursor.getString(26)));
                     bean.setVaccine(String.valueOf(cursor.getString(27)));
                     bean.setCreationdate(String.valueOf(cursor.getString(28)));
                     bean.setSyncId(String.valueOf(cursor.getString(29)));
                     bean.setUID(Uid);

                  arrayList.add(bean);


             } while (cursor.moveToNext());
         }


         db.close();




     JSONArray ReproductionArray = new JSONArray();
     JSONObject Master = new JSONObject();


         try {

             for(int k=0;k<arrayList.size();k++){



                 if(arrayList.get(k).getSyncId().equalsIgnoreCase("0")) {

                     JSONObject reproductiondata = new JSONObject();
                     Log.e("IdNo", arrayList.get(k).getIdno());

                     reproductiondata.put("CompanyCode", arrayList.get(k).getCompanycode());
                     reproductiondata.put("IdNo", arrayList.get(k).getIdno());
                     reproductiondata.put("Parity", arrayList.get(k).getParity());
                     reproductiondata.put("HeatSeq", arrayList.get(k).getHeatseq());
                     reproductiondata.put("Service", arrayList.get(k).getService());
                     reproductiondata.put("DtOfHeat", arrayList.get(k).getDtofheat());
                     reproductiondata.put("Insim", arrayList.get(k).getInsim());
                     reproductiondata.put("SireId", arrayList.get(k).getSireid());
                     reproductiondata.put("PD1", arrayList.get(k).getPd1());
                     reproductiondata.put("RemPD1", arrayList.get(k).getRmpd1());
                     reproductiondata.put("PD2", arrayList.get(k).getPd2());
                     reproductiondata.put("RemPD2", arrayList.get(k).getRmpd2());
                     reproductiondata.put("Pddate", arrayList.get(k).getPddate());
                     reproductiondata.put("DtOfCalving", arrayList.get(k).getDateofcalving());
                     reproductiondata.put("DtOfDry", arrayList.get(k).getDtofdry());
                     reproductiondata.put("Dry_Reson", arrayList.get(k).getDryreason());
                     reproductiondata.put("Dry_Treatment", arrayList.get(k).getDrytreatement());
                     reproductiondata.put("Flg", arrayList.get(k).getFlg());
                     reproductiondata.put("Calf", arrayList.get(k).getCalf());
                     reproductiondata.put("Sex", arrayList.get(k).getSex());
                     reproductiondata.put("C_Type", arrayList.get(k).getCtype());
                     reproductiondata.put("ROP", arrayList.get(k).getRop());
                     reproductiondata.put("Comments", arrayList.get(k).getComments());
                     reproductiondata.put("RP", arrayList.get(k).getRp());
                     reproductiondata.put("ENTRY", arrayList.get(k).getEntry());
                     reproductiondata.put("Total_Dose", arrayList.get(k).getTotaldose());
                     reproductiondata.put("Abortion_Seq", arrayList.get(k).getAbortionseq());
                     reproductiondata.put("Vaccine", arrayList.get(k).getVaccine());
                     reproductiondata.put("CretationDate", arrayList.get(k).getCreationdate());
                     reproductiondata.put("UID", arrayList.get(k).getUID());
                     ReproductionArray.put(reproductiondata);
                 }

                 }
             CommonData.getInstance().setReproductioncommon(ReproductionArray);


             Master.put("reproduction",ReproductionArray);
             //GetMasterData.put("GetMasterData",Master);
         } catch (JSONException e) {
             e.printStackTrace();
         }

         return Master.toString();


     }




  public String getDetailss(String UID){


      ArrayList<DetailsBean> arrayList = new ArrayList<DetailsBean>();


      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = null;
      JSONObject animalStatusObject= null;

      String Query = "select * from details";
      cursor = db.rawQuery(Query, null);


      if (cursor.moveToFirst()) {
          do {
              DetailsBean bean = new DetailsBean();


              bean.setCompanyCode(String.valueOf(cursor.getString(0)));
              bean.setHerdNo(String.valueOf(cursor.getString(1)));
              bean.setLotNo(String.valueOf(cursor.getString(2)));
              bean.setIdNo(String.valueOf(cursor.getString(3)));
              bean.setSexFlg(String.valueOf(cursor.getString(4)));
              bean.setSpecies(String.valueOf(cursor.getString(5)));
              bean.setBreed(String.valueOf(cursor.getString(6)));
              bean.setStatus(String.valueOf(cursor.getString(7)));
              bean.setDtOfBirth(String.valueOf(cursor.getString(8)));
              bean.setName(String.valueOf(cursor.getString(9)));
              bean.setAgeFlg(String.valueOf(cursor.getString(10)));
              bean.setBirthWt(String.valueOf(cursor.getString(11)));
              bean.setSalvFlg(String.valueOf(cursor.getString(12)));
              bean.setGroupFlg(String.valueOf(cursor.getString(13)));
              bean.setPBFlg(String.valueOf(cursor.getString(14)));
              bean.setCatCalfFlg(String.valueOf(cursor.getString(15)));
              bean.setHLAname(String.valueOf(cursor.getString(16)));
              bean.setSensorNo(String.valueOf(cursor.getString(17)));
              bean.setPhoto(String.valueOf(cursor.getString(18)));
              bean.setParity(String.valueOf(cursor.getString(19)));
              bean.setDisposalflg(String.valueOf(cursor.getString(20)));
              bean.setSel_cancel(String.valueOf(cursor.getString(21)));
              bean.setInsurance_No(String.valueOf(cursor.getString(22)));
              bean.setAI_Tagno(String.valueOf(cursor.getString(23)));
              bean.setCurrent_Parity(String.valueOf(cursor.getString(24)));
              bean.setRegistration_Date(String.valueOf(cursor.getString(25)));
              bean.setMarket_Value(String.valueOf(cursor.getString(26)));
              bean.setNo_rings(String.valueOf(cursor.getString(27)));
              bean.setRearing_Purpose(String.valueOf(cursor.getString(28)));
              bean.setColor(String.valueOf(cursor.getString(29)));
              bean.setHorn_distance(String.valueOf(cursor.getString(30)));
              bean.setInsurNo(String.valueOf(cursor.getString(31)));
              bean.setPolicy_period(String.valueOf(cursor.getString(32)));
              bean.setTransaction_Date(String.valueOf(cursor.getString(33)));
              bean.setHypothecation(String.valueOf(cursor.getString(34)));
              bean.setHypothecation_No(String.valueOf(cursor.getString(35)));
              bean.setDoctor(String.valueOf(cursor.getString(36)));
              bean.setSend_CMS(String.valueOf(cursor.getString(37)));
              bean.setImageContent(String.valueOf(cursor.getString(38)));
              bean.setInsurance_Flag(String.valueOf(cursor.getString(39)));
              bean.setBreedingStatus(String.valueOf(cursor.getString(40)));
              bean.setHeatDate(String.valueOf(cursor.getString(41)));
              bean.setHeatSeq(String.valueOf(cursor.getString(42)));
              bean.setAbortionSeq(String.valueOf(cursor.getString(43)));
              bean.setPDDate(String.valueOf(cursor.getString(44)));
              bean.setPDI(String.valueOf(cursor.getString(45)));
              bean.setPDII(String.valueOf(cursor.getString(46)));
              bean.setCalvingDate(String.valueOf(cursor.getString(47)));
              bean.setDryDate(String.valueOf(cursor.getString(48)));
              bean.setMilkDate(String.valueOf(cursor.getString(49)));
              bean.setLastMilk(String.valueOf(cursor.getString(50)));
              bean.setTotalMilk(String.valueOf(cursor.getString(51)));
              bean.setSelectFlag(String.valueOf(cursor.getString(52)));
              bean.setSelectRemarks(String.valueOf(cursor.getString(53)));
              bean.setSelectColor(String.valueOf(cursor.getString(54)));
              bean.setLastSire(String.valueOf(cursor.getString(55)));
              bean.setDisposalRemarks(String.valueOf(cursor.getString(56)));
              bean.setSire(String.valueOf(cursor.getString(57)));
              bean.setDam(String.valueOf(cursor.getString(58)));
              bean.setPaternalSire(String.valueOf(cursor.getString(59)));
              bean.setPaternalDam(String.valueOf(cursor.getString(60)));
              bean.setNewownerCode(String.valueOf(cursor.getString(61)));
              bean.setOwnerID(String.valueOf(cursor.getString(62)));
              bean.setVirtualLot(String.valueOf(cursor.getString(63)));
              bean.setUID(UID);

              bean.setAllowUser(String.valueOf(cursor.getString(65)));
              bean.setISSuspend(String.valueOf(cursor.getString(66)));
              bean.setSyncID(String.valueOf(cursor.getString(68)));
              arrayList.add(bean);





          } while (cursor.moveToNext());
      }


      db.close();


      JSONArray detailsArrayy = new JSONArray();
      JSONObject Master = new JSONObject();
      JSONObject GetMasterData = new JSONObject();

      try {

          for(int k=0;k<arrayList.size();k++){

              if(arrayList.get(k).getSyncID().equalsIgnoreCase("0")) {

                  JSONObject Detailsdata = new JSONObject();
                  Detailsdata.put("CompanyCode", arrayList.get(k).getCompanyCode());
                  Detailsdata.put("HerdNo", arrayList.get(k).getHerdNo());
                  Detailsdata.put("LotNo", arrayList.get(k).getLotNo());
                  Detailsdata.put("IdNo", arrayList.get(k).getIdNo());
                  Detailsdata.put("SexFlg", arrayList.get(k).getSexFlg());
                  Detailsdata.put("Species", arrayList.get(k).getSpecies());
                  Detailsdata.put("Breed", arrayList.get(k).getBreed());
                  Detailsdata.put("Status", arrayList.get(k).getStatus());
                  Detailsdata.put("DtOfBirth", arrayList.get(k).getDtOfBirth());
                  Detailsdata.put("Name", arrayList.get(k).getName());
                  Detailsdata.put("AgeFlg", arrayList.get(k).getAgeFlg());
                  Detailsdata.put("BirthWt", arrayList.get(k).getBirthWt());
                  Detailsdata.put("SalvFlg", arrayList.get(k).getSalvFlg());
                  Detailsdata.put("GroupFlg", arrayList.get(k).getGroupFlg());
                  Detailsdata.put("PBFlg", arrayList.get(k).getPBFlg());
                  Detailsdata.put("CatCalfFlg", arrayList.get(k).getCatCalfFlg());
                  Detailsdata.put("HLAname", arrayList.get(k).getHLAname());
                  Detailsdata.put("SensorNo", arrayList.get(k).getSensorNo());
                  Detailsdata.put("Photo", arrayList.get(k).getPhoto());
                  Detailsdata.put("Parity", arrayList.get(k).getParity());
                  Detailsdata.put("Disposalflg", arrayList.get(k).getDisposalflg());
                  Detailsdata.put("sel_cancel", arrayList.get(k).getSel_cancel());
                  Detailsdata.put("Insurance_No", arrayList.get(k).getInsurance_No());
                  Detailsdata.put("AI_Tagno", arrayList.get(k).getAI_Tagno());
                  Detailsdata.put("Current_Parity", arrayList.get(k).getCurrent_Parity());
                  Detailsdata.put("Registration_Date", arrayList.get(k).getRegistration_Date());
                  Detailsdata.put("Market_Value", arrayList.get(k).getMarket_Value());
                  Detailsdata.put("No_rings", arrayList.get(k).getNo_rings());
                  Detailsdata.put("Rearing_Purpose", arrayList.get(k).getRearing_Purpose());
                  Detailsdata.put("Color", arrayList.get(k).getColor());
                  Detailsdata.put("Horn_distance", arrayList.get(k).getHorn_distance());
                  Detailsdata.put("InsurNo", arrayList.get(k).getInsurNo());
                  Detailsdata.put("Policy_period", arrayList.get(k).getPolicy_period());
                  Detailsdata.put("Transaction_Date", arrayList.get(k).getTransaction_Date());
                  Detailsdata.put("Hypothecation", arrayList.get(k).getHypothecation());
                  Detailsdata.put("Hypothecation_No", arrayList.get(k).getHypothecation_No());
                  Detailsdata.put("Doctor", arrayList.get(k).getDoctor());
                  Detailsdata.put("Send_CMS", arrayList.get(k).getSend_CMS());
                  Detailsdata.put("ImageContent", arrayList.get(k).getImageContent());
                  Detailsdata.put("Insurance_Flag", arrayList.get(k).getInsurance_Flag());
                  Detailsdata.put("BreedingStatus", arrayList.get(k).getBreedingStatus());
                  Detailsdata.put("HeatDate", arrayList.get(k).getHeatDate());
                  Detailsdata.put("HeatSeq", arrayList.get(k).getHeatSeq());
                  Detailsdata.put("AbortionSeq", arrayList.get(k).getAbortionSeq());
                  Detailsdata.put("PDDate", arrayList.get(k).getPDDate());
                  Detailsdata.put("PDI", arrayList.get(k).getPDI());
                  Detailsdata.put("PDII", arrayList.get(k).getPDII());
                  Detailsdata.put("CalvingDate", arrayList.get(k).getCalvingDate());
                  Detailsdata.put("DryDate", arrayList.get(k).getDryDate());
                  Detailsdata.put("MilkDate", arrayList.get(k).getMilkDate());
                  Detailsdata.put("LastMilk", arrayList.get(k).getLastMilk());
                  Detailsdata.put("TotalMilk", arrayList.get(k).getTotalMilk());
                  Detailsdata.put("SelectFlag", arrayList.get(k).getSelectFlag());
                  Detailsdata.put("SelectRemarks", arrayList.get(k).getSelectRemarks());
                  Detailsdata.put("SelectColor", arrayList.get(k).getSelectColor());
                  Detailsdata.put("LastSire", arrayList.get(k).getLastSire());
                  Detailsdata.put("DisposalRemarks", arrayList.get(k).getDisposalRemarks());
                  Detailsdata.put("Sire", arrayList.get(k).getSire());
                  Detailsdata.put("Dam", arrayList.get(k).getDam());
                  Detailsdata.put("paternalSire", arrayList.get(k).getPaternalSire());
                  Detailsdata.put("PaternalDam", arrayList.get(k).getPaternalDam());
                  Detailsdata.put("NewownerCode", arrayList.get(k).getNewownerCode());
                  Detailsdata.put("OwnerID", arrayList.get(k).getOwnerID());
                  Detailsdata.put("VirtualLot", arrayList.get(k).getVirtualLot());
                  Detailsdata.put("UID", arrayList.get(k).getUID());
                  Detailsdata.put("AllowUser", arrayList.get(k).getAllowUser());
                  Detailsdata.put("ISSuspend", arrayList.get(k).getISSuspend());
                  //Detailsdata.put("SyncID",arrayList.get(k).getSyncID());
                  detailsArrayy.put(Detailsdata);
              }
          }


          CommonData.getInstance().setReproductioncommon(detailsArrayy);





          Master.put("details",detailsArrayy);

          //GetMasterData.put("GetMasterData",Master);
      } catch (JSONException e) {
          e.printStackTrace();
      }

      return Master.toString();


  }


    public String PurchaseData(){


        ArrayList<PurchaseDetailsBean> datsas = new ArrayList<PurchaseDetailsBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        JSONObject animalStatusObject=null;
        String Query = "select * from purchasedetails";

        cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                PurchaseDetailsBean bean  = new PurchaseDetailsBean();
                bean.setCompanycode(cursor.getString(0));
                bean.setIdno(cursor.getString(1));
                bean.setDtofpurch(cursor.getString(2));
                bean.setSourceid(cursor.getString(3));
                bean.setSourceid(cursor.getString(4));
                bean.setCost(cursor.getString(5));
                bean.setAgeflg(cursor.getString(6));
                bean.setStatus(cursor.getString(7));
                bean.setLactationP(cursor.getString(8));
                bean.setParityp(cursor.getString(9));
                bean.setFatp(cursor.getString(10));
                bean.setLactationc(cursor.getString(11));
                bean.setParityc(cursor.getString(12));
                bean.setFatc(cursor.getString(13));
                bean.setDtofcalvc(cursor.getString(14));
                bean.setParityc(cursor.getString(15));



                datsas.add(bean);
                // datsas.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        db.close();













        return "";




        }






























}