package tarce.model.greendaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by Daniel.Xu on 2017/1/16.
 */
@Entity
public class LoginResponseBean {
    @Index
    private int user_id;
    private String name;
    private String groupsName ;
    @Index
    private int  groupsId ;
    @Generated(hash = 476369623)
    public LoginResponseBean(int user_id, String name, String groupsName,
                             int groupsId) {
        this.user_id = user_id;
        this.name = name;
        this.groupsName = groupsName;
        this.groupsId = groupsId;
    }
    @Generated(hash = 679347832)
    public LoginResponseBean() {
    }
    public int getUser_id() {
        return this.user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroupsName() {
        return this.groupsName;
    }
    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }
    public int getGroupsId() {
        return this.groupsId;
    }
    public void setGroupsId(int groupsId) {
        this.groupsId = groupsId;
    }

}
