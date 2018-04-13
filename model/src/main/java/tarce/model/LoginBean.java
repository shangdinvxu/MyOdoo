package tarce.model;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class LoginBean {
    private String login ;
    private String app_version;

    public LoginBean(String login, String password, String db, String app_version) {
        this.db = db;
        this.login = login;
        this.password = password;
        this.app_version = app_version;
    }

    private String password ;
    private String db ;



}
