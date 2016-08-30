package provab.herdman.constants;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ptblr1035 on 23/8/16.
 */
public class CommonData {
    private static CommonData ourInstance = new CommonData();

    public static CommonData getInstance() {
        return ourInstance;
    }
    public JSONArray reproductionarray;
    JSONArray detailsarray;

    public JSONArray getDetailsarray() {
        return detailsarray;
    }

    public void setDetailsarray(JSONArray detailsarray) {
        this.detailsarray = detailsarray;
    }

    public JSONArray getReArraycommon() {
        return reproductionarray;
    }

    public void setReproductioncommon(JSONArray arraycommon) {
        reproductionarray = arraycommon;
    }

    private CommonData() {
    }

    public String getDefaultDate(){

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        return formattedDate;


    }








}
