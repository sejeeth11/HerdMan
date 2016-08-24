package provab.herdman.constants;

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
