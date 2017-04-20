package tarce.model;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class LoginDatabase {

    /**
     * res_data : ["rt_1229"]
     * res_msg :
     * res_code : 1
     */

    private String res_msg;
    private int res_code;
    private List<String> res_data;

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public List<String> getRes_data() {
        return res_data;
    }

    public void setRes_data(List<String> res_data) {
        this.res_data = res_data;
    }
}
