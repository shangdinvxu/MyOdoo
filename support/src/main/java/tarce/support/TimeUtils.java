package tarce.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Daniel.Xu on 2017/6/1.
 */

public class TimeUtils {

   private static SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC时间格式

    private static  SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当地时间格式
    /**
     * 当地时间 ---> UTC时间
     * @return
     */
    public static String Local2UTC(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    /**
     * UTC时间 ---> 当地时间
     * @param utcTime   UTC时间
     * @return
     */
    public static String utc2Local(String utcTime) {
        if (utcFormater == null)
        utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC时间格式
        if (localFormater == null)
        localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当地时间格式

        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }
}