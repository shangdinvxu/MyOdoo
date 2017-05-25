package tarce.myodoo.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by John on 15/2/6.
 */
public class DateTool {
    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String MONTH_DATE_FORMAT = "yyyy-MM";
    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEVICE_DATETIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT2 = "yyyyMMddHHmmss";


    public static final String YEAR_MONTH_DATE_FORMAT = "yyyyMM";

    public static final String HOUR_DATE_FORMAT = "HH:mm";

    public static final String DEFAULT_DATETIME_FORMAT3 = "yyyy-MM-dd E HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT4 = "MM月dd日 E HH:mm";

    public static final String DEFAULT_DATETIME_FORMAT5 = "yyyy.MM.dd";

    public static final String MONTH_DATE = "MM月dd日";

    public static final String DEFAULT_DATETIME_FORMAT6 = "MM月dd日 HH:mm";

    public static final String FORMAT_E = "MM/dd E";

    public static final String MONTH_DATE2 = "MM-dd";
    public static final String MONTH_DATE3 = "MM/dd";
    public static final String MONTH = "MM月";
    public static final String DEFAULT_DATE_FORMAT_WEEK = "yyyy-MM-dd E";

    public static final String DATE_YEAR_MONTH = "yyyy年MM月";

    /**
     * 1s中的毫秒数
     */
    private static final int MILLIS = 1000;

    /**
     * 一年当中的月份数
     */
    private static final int MONTH_PER_YEAR = 12;

    /**
     * 私有构造方法，禁止对该类进行实例化
     */
    private DateTool() {
    }

    /**
     * 得到系统当前日期时间
     *
     * @return 当前日期时间
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到用缺省方式格式化的当前日期
     *
     * @return 当前日期
     */
    public static String getDate() {
        return getDateTime(DEFAULT_DATE_FORMAT);
    }

    /**
     * 另一种格式
     *
     * @return 当前日期
     */
    public static String getDate_new() {
        return getDateTime(DEFAULT_DATETIME_FORMAT5);
    }

    /**
     * 得到用缺省方式格式化的当前日期月份和年
     *
     * @return 当前日期
     */
    public static String getMothDate() {
        return getDateTime(MONTH_DATE_FORMAT);
    }

    /**
     * 得到用缺省方式格式化的当前日期及时间
     *
     * @return 当前日期及时间
     */
    public static String getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到系统当前日期及时间，并用指定的方式格式化
     *
     * @param pattern 显示格式
     * @return 当前日期及时间
     */
    public static String getDateTime(String pattern) {
        Date datetime = Calendar.getInstance().getTime();
        return getDateTime(datetime, pattern);
    }


    /**
     * 得到用指定方式格式化的日期
     *
     * @param date    需要进行格式化的日期
     * @param pattern 显示格式
     * @return 日期时间字符串
     */
    public static String getDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 得到当前年份
     *
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);

    }

    /**
     * 得到当前月份
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        //用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日
     *
     * @return 当前日
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
     *
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(int days) {
        return add(getNow(), days, Calendar.DATE);
    }

    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
     *
     * @param date 基准日期
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     *
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(int months) {
        return add(getNow(), months, Calendar.MONTH);
    }

    /**
     * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     * 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
     *
     * @param date   基准日期
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * 内部方法。为指定日期增加相应的天数或月数
     *
     * @param date   基准日期
     * @param amount 增加的数量
     * @param field  增加的单位，年，月或者日
     * @return 增加以后的日期
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 通过date对象取得格式为小时:分钟的实符串
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getHourMin(Date date) {
        StringBuffer sf = new StringBuffer();
        sf.append(date.getHours());
        sf.append(":");
        sf.append(date.getMinutes());
        return sf.toString();
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(one);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date d1 = calendar.getTime();
        calendar.clear();
        calendar.setTime(two);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date d2 = calendar.getTime();
        final int MILISECONDS = 24 * 60 * 60 * 1000;
        BigDecimal r = new BigDecimal(new Double((d1.getTime() - d2.getTime()))
                / MILISECONDS);
        return Math.round(r.doubleValue());
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差月份数
     */
    public static int diffMonths(Date one, Date two) {

        Calendar calendar = Calendar.getInstance();

        //得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONTH);
        //得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONTH);

        return (yearOne - yearTwo) * MONTH_PER_YEAR + (monthOne - monthTwo);
    }

    /**
     * 获取某一个日期的年份
     *
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     *
     * @param datestr 需要解析的日期字符串
     * @param pattern 日期字符串的格式，默认为"yyyy-MM-dd"的形式
     * @return 解析后的日期
     */
    public static Date parse(String datestr, String pattern) {
        Date date = null;

        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String parse(Date datestr, String pattern) {
        String date = null;
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.format(datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 返回本月的最后一天
     *
     * @return 本月最后一天的日期
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(getNow());
    }

    /**
     * 返回给定日期中的月份中的最后一天
     *
     * @param date 基准日期
     * @return 该月最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        //减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 计算两个具体日期之间的秒差，第一个日期-第二个日期
     *
     * @param date1
     * @param date2
     * @param onlyTime 是否只计算2个日期的时间差异，忽略日期，true代表只计算时间差
     * @return
     */
    public static long diffSeconds(Date date1, Date date2, boolean onlyTime) {
        if (onlyTime) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            //calendar.set(1984, 5, 24);
            long t1 = calendar.getTimeInMillis();
            calendar.setTime(date2);
            //calendar.set(1984, 5, 24);
            long t2 = calendar.getTimeInMillis();
            return (t1 - t2) / MILLIS;
        } else {
            return (date1.getTime() - date2.getTime()) / MILLIS;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static long diffSeconds(Date date1, Date date2) {
        return diffSeconds(date1, date2, false);
    }

    /**
     * 根据日期确定星期几:1-星期日，2-星期一.....s
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int mydate = cd.get(Calendar.DAY_OF_WEEK);
        return mydate;
    }

//    /**
//     * 将2010-06-01转换为20100601格式
//     * @param date
//     * @return
//     */
//    public static String toVODate(String date) {
//      if (StringUtil.isEmpty(date)) {
//          //return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
//          return "";
//      }
//      Date tdate;
//      try {
//          tdate = new SimpleDateFormat("yyyyMMdd").parse(date);
//      } catch (ParseException e) {
//          e.printStackTrace();
//          throw new SmsException("日期转换异常!");
//      }
//      return DateFormatUtils.format(tdate, "yyyy-MM-dd");
//    }
//
//    /**
//     * 将20100601转换为2010-06-01格式
//     * @param date
//     * @return
//     */
//    public static String toDomainDate(String date) {
//      if (StringUtil.isEmpty(date)) {
//          return "";
//      }
//      Date tdate;
//      try {
//          tdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//      } catch (ParseException e) {
//          throw new BusinessException("上收时间或者启用时间格式不正确!");
//      }
//      return DateFormatUtils.format(tdate, "yyyyMMdd");
//    }

    /**
     * 验证用密码是否在有效期内(跟当前日期比较)
     *
     * @param format    "yyyyMMdd"
     * @param validDate
     * @return
     */
    public static boolean isValidDate(String validDate, String format) {
        Date valid = parse(validDate, format);
        Date now = new Date();
        String nowStr = new SimpleDateFormat(format).format(now);
        try {
            now = new SimpleDateFormat(format).parse(nowStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valid.after(now);
    }


    /*
     * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm
	 */
    public static String getStrTime_ymd_hm(String cc_time) {
        String re_StrTime = "";
        if (TextUtils.isEmpty(cc_time) || "null".equals(cc_time)) {
            return re_StrTime;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getStrTime_ymd_hms(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy.MM.dd
     */
    public static String getStrTime_ymd(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy
     */
    public static String getStrTime_y(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：MM-dd
     */
    public static String getStrTime_md(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：HH:mm
     */
    public static String getStrTime_hm(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：HH:mm:ss
     */
    public static String getStrTime_hms(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：MM-dd HH:mm:ss
     */
    public static String getNewsDetailsDate(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将字符串转为时间戳
     */
    public static String getTime() {
        String re_time = null;
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d;
        d = new Date(currentTime);
        long l = d.getTime();
        String str = String.valueOf(l);
        re_time = str.substring(0, 10);
        return re_time;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy.MM.dd  星期几
     */
    public static String getSection(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  EEEE");
//		对于创建SimpleDateFormat传入的参数：EEEE代表星期，如“星期四”；MMMM代表中文月份，如“十一月”；MM代表月份，如“11”；
//		yyyy代表年份，如“2010”；dd代表天，如“25”
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

//	public static String getTodayDate(){
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		String nowTime=format.format(new Date());
//		return
//	}

    /**
     * 获取前天日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getlastDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.DATE, -1);
        Date lastDate = calendar.getTime();

        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sf.format(lastDate);
    }

    /**
     * 获取当前系统当天日期
     */

    public static String getCurrentDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.DATE, 0);
        Date currenTdate = calendar.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sf.format(currenTdate);
    }

    /**
     * 获取下一天日期
     */
    public static String getNextDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.DATE, 1);

        Date nextDate = calendar.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sf.format(nextDate);
    }


    /**
     * 返回yyyyMM样式
     *
     * @return
     */
    public static String getTravelCalandarCurrentMonth() {
        Date datetime = Calendar.getInstance().getTime();
        SimpleDateFormat sf = new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);
        return sf.format(datetime);
    }

    /**
     * 获取上个月的年份和月份
     *
     * @return 返回yyyyMM样式
     */
    public static String getTravelCalandarLastMonth(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.MONTH, -2);

        Date datetime = calendar.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);
        return sf.format(datetime);
    }

    /**
     * 获取下个月的年份和月份
     *
     * @return 返回yyyyMM样式
     */
    public static String getTravelCalandarNextMonth(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.MONTH, 0);

        Date datetime = calendar.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);
        return sf.format(datetime);
    }


    /**
     * 字符串转换成日期
     * <p/>
     *
     * @param str
     * @return date 返回yyyyMM 样式
     */
    public static Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换成日期
     * <p/>
     *
     * @param str
     * @return date 返回yyyyMM 样式
     */
    public static String strDateToStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat(YEAR_MONTH_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.toString();
    }

    /**
     * 字符串转换成日期
     * <p/>
     *
     * @param str
     * @return date 返回yyyy-MM-dd 样式
     */
    public static String strDateToStrDef(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(date);

    }


    /**
     * 字符串转换成日期
     * <p/>
     *
     * @param str
     * @return date 返回 yyyy-MM-dd
     */
    public static Date strToDefFormatDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Date strGetfFormatDate(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return new Date(System.currentTimeMillis());
        }
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param str xx月xx日
     * @return
     */
    public static String strGetfFormatDate2(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        SimpleDateFormat format1 = new SimpleDateFormat(MONTH_DATE);
        Date date = null;
        String date1 = null;
        try {
            date = format.parse(str);
            date1 = format1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }


    public static String strGetfFormatDate3(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(MONTH_DATE_FORMAT);
        SimpleDateFormat format1 = new SimpleDateFormat(MONTH);

        Date date = null;
        String date1 = null;
        try {
            date = format.parse(str);
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH);
            if (month == 0) {
                date1 = str;
            } else {
                date1 = format1.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }


    /**
     * 字符串转换成日期
     * <p/>
     *
     * @param str
     * @return date 返回 yyyy-MM-dd
     */
    public static Date strToDefFormatDate2(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 比较时间字符串d1和d2
     *
     * @param d1
     * @param d2
     * @return 小于0说明d1时间比d2早；等于0说明d1和d2相等
     */
    public static int timeComparator(String d1, String d2) {
        Date date1 = DateTool.strToDefFormatDate(d1);
        Date date2 = DateTool.strToDefFormatDate(d2);

        return date1.compareTo(date2);
    }

    public static String getMothDate(String mm) {
        return getDateTime(mm);
    }

    public static String getStringMonth(String da) {
        try {
            return da.substring(da.indexOf("-") + 1, da.lastIndexOf("-"));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 保养订单时间格式化
     *
     * @param str yyyy-MM-dd E HH:mm:ss
     * @return MM月dd日 E HH:mm
     */
    public static String Dataformat3(String str, String pattern) {
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        if (StringUtils.isNullOrEmpty(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        SimpleDateFormat format2 = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT4, Locale.CHINA);
        String dTime = null;
        try {
            Date date = format.parse(str);
            dTime = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dTime;
    }

    /**
     * 格式化时间
     *
     * @param str      时间
     * @param pattern1 时间格式
     * @param pattern2 时间要转换的格式
     * @return
     */
    public static String Dataformat3(String str, String pattern1, String pattern2) {
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern1, Locale.CHINA);
        SimpleDateFormat format2 = new SimpleDateFormat(pattern2, Locale.CHINA);
        String dTime = null;
        try {
            Date date = format.parse(str);
            dTime = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dTime;
    }


}