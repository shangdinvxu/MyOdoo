package tarce.model.greendaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by Daniel.Xu on 2017/2/10.
 */
@Entity
public class UserLogin {
    @Id
    private Long id;
    @Index(unique = true)
    private String userName ;
    private String password ;
    @Generated(hash = 675672463)
    public UserLogin(Long id, String userName, String password) {
        this.id = id;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
