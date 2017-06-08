package tarce.model;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class LoginBean {
    private String login ;

    public LoginBean(String login, String password, String db) {
        this.db = db;
        this.login = login;
        this.password = password;
    }

    private String password ;
    private String db ;



}
