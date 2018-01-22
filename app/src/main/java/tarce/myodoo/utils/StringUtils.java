package tarce.myodoo.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class StringUtils {
    public static String switchString(String string) {
        String transtring = string;
        if (string.equals("assigned")) {
            transtring = "可用";
        } else if (string.equals("confirmed")) {
            transtring = "等待可用";
        } else if (string.equals("done")) {
            transtring = "完成";
        } else if (string.equals("partially_available")) {
            transtring = "部分可用";
        } else if (string.equals("delivery_once")) {
            transtring = "一次性发齐货";
        } else if (string.equals("create_backorder")) {
            transtring = "允许分批,并产生欠单";
        } else if (string.equals("cancel_backorder")) {
            transtring = "允许分批,不产生欠单";
        } else if (string.equals("waiting_in")) {
            transtring = "待入库";
        } else if (string.equals("qc_check")) {
            transtring = "品检";
        } else if (string.equals("validate")) {
            transtring = "等待采购检验";
        } else if (string.equals("waiting")) {
            transtring = "等待其他作业";
        } else if (string.equals("waiting_out")) {
            transtring = "待出库";
        } else if (string.equals("cancel")) {
            transtring = "已取消";
        } else if (string.equals("picking")) {
            transtring = "等待分拣";
        }else if (string.equals("secondary_operation")){
            transtring = "二次加工中";
        }else if (string.equals("secondary_operation_done")){
            transtring = "二次加工完成";
        }
        return transtring;
    }

    public static String typeSwitch(String string) {
        String transtring = string;
        switch (string) {
            case "procurement_warehousing":
                transtring = "采购入库";
                break;
            case "purchase_return":
                transtring = "采购退货";
                break;
            case "sell_return":
                transtring = "销售退货";
                break;
            case "sell_out":
                transtring = "销售出库";
                break;
            case "manufacturing_orders":
                transtring = "制造入库";
                break;
            case "manufacturing_picking":
                transtring = "制造领料";
                break;
            case "inventory_in":
                transtring = "盘点入库";
                break;
            case "inventory_out":
                transtring = "盘点出库";
                break;
        }
        return transtring;
    }

    /**
     * 判断是否为空string
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 将Double类型数据转换成String,去掉小数点。先转成int
     *
     * @param i
     */
    public static String doubleToString(double i) {
        return String.valueOf(new Double(i).intValue());
    }

    /**
     * double转int
     */
    public static int doubleToInt(double i) {
        return new Double(i).intValue();
    }

    /**
     * 小数点保留两位小数
     */
    public static String twoDouble(double num) {
        String s = "";
        DecimalFormat df = new DecimalFormat("#.##");
        if (num != 0) {
            s = df.format(num) + "";
        }else {
            s = "0.0";
        }
        return s;
    }

    /**
     * 小数点保留4位
     */
    public static String fourDouble(double num) {
        String s = "";
        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale(4, BigDecimal.ROUND_DOWN);
        s = bd + "";
        return s;
    }

    /**
     * 半角转全角
     */
    public static String stringFilter(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static double changeDouble(double i) {
        BigDecimal bd = new BigDecimal(i);
        bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

}
