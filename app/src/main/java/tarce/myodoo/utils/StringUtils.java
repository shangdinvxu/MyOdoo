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
        }
        return transtring;

    }
}
