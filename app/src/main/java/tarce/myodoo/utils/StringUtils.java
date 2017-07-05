package tarce.myodoo.utils;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class StringUtils {
    public static String switchString(String string){
        String transtring = string ;
        if (string.equals("assigned")){
            transtring = "可用";
        }else  if (string.equals("confirmed")){
            transtring = "等待可用";
        }else  if (string.equals("done")){
            transtring = "完成";
        }else  if (string.equals("partially_available")){
            transtring = "部分可用";
        }else  if (string.equals("delivery_once")){
            transtring = "一次性发齐货";
        }else if (string.equals("create_backorder")){
            transtring = "允许分批,并产生欠单";
        }else if (string.equals("cancel_backorder")){
            transtring = "允许分批,不产生欠单";
        }else if (string.equals("waiting_in")){
            transtring = "待入库";
        }else if (string.equals("qc_check")){
            transtring = "品检";
        }else if (string.equals("validate")){
            transtring = "等待采购检验";
        }else if (string.equals("waiting")){
            transtring = "等待其他作业";
        }
        return transtring;
    }
    /**
     * 判断是否为空string
     * */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 将Double类型数据转换成String,去掉小数点。先转成int
     * @param i
     * */
    public static String doubleToString(double i){
        return String.valueOf(new Double(i).intValue());
    }
    /**
     * double转int
     * */
    public static int doubleToInt(double i){
        return new Double(i).intValue();
    }
}
