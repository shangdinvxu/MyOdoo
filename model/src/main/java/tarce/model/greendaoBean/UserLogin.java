package tarce.model.greendaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by Daniel.Xu on 2017/2/10.
 */
@Entity
public class UserLogin {
    @Index(unique = true)
    private String userName ;
    private String password ;
    @Generated(hash = 534347389)
    public UserLogin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    @Generated(hash = 180802810)
    public UserLogin() {
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
