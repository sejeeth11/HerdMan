package provab.herdman.constants;

/**
 * Created by ptblr-1168 on 21/1/16.
 */
public class Links {




    //MANOJ
   /* public static String LOCAL_URL = "http://192.168.0.107/service/mobile/";
    public static String BASE_URL = LOCAL_URL;
    public static String SITE_URL = "http://192.168.0.107/service/";
    public static String PARTNER_LOCAL_URL="http://192.168.0.107/service/";*/

    //SHRUTHI
    /*public static String LOCAL_URL = "http://192.168.0.184/service/mobile/";
    public static String BASE_URL = LOCAL_URL;
    public static String SITE_URL = "http://192.168.0.184/service/";
    public static String PARTNER_BASE_URL = "http://192.168.0.184/service/";*/

    //SERVER
    public static String SERVER_URL = "http://182.73.72.14/herdman/GetData.asmx/";
    public static String DATA_SEND = "http://182.73.72.14/HerdmanPost/SetData.asmx/";

   // http://182.73.72.14/herdman/GetData.asmx?

    public static String BASE_URL = SERVER_URL;
    public static String SERVER_PASS_DATA = DATA_SEND+"SETJsonData";
    public static String GET_REGISTERED_USERS= BASE_URL + "GetUSer";
    public static String GET_ONE_TIME_PERMANENT_MASTER= BASE_URL + "GetOneTimeMaster";
    public static String GET_THIRD_AND_FOURTH_TYPE_MASTER= BASE_URL + "GetMasterData";
    public static String GET_ANIMAL_DETAILS= BASE_URL + "GetAnimalDetails";
    public static String GET_ANIMALs= BASE_URL + "GetAnimals";

}
