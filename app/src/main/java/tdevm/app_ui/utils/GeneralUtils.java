package tdevm.app_ui.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralUtils {


    public static String parseTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        return date.toString();
    }


    public static String parseStringDouble(String value) {
        Double d = Double.parseDouble(value);
        double lowest = (d * 0.01);
        lowest = Double.parseDouble(new DecimalFormat("##.##").format(lowest));
        return String.valueOf(lowest);
    }
}
