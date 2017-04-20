package tarce.model;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class loginBean {
    private String login ;

    public loginBean(String login, String password, String db) {
        this.db = db;
        this.login = login;
        this.password = password;
    }

    private String password ;
    private String db ;




}
