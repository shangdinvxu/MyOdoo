package tarce.model.greendaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by Daniel.Xu on 2017/2/6.
 */
@Entity
public class MenuListBean {
    @Index(unique = true)
    private long id;
    private String action;
    private int sequence;
    private String web_icon;
    private String name;
    private int user_id;
    private int parent_id;
    
    @Generated(hash = 297933719)
    public MenuListBean(long id, String action, int sequence, String web_icon,
                        String name, int user_id, int parent_id) {
        this.id = id;
        this.action = action;
        this.sequence = sequence;
        this.web_icon = web_icon;
        this.name = name;
        this.user_id = user_id;
        this.parent_id = parent_id;
    }
    @Generated(hash = 686976349)
    public MenuListBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAction() {
        return this.action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public int getSequence() {
        return this.sequence;
    }
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
    public String getWeb_icon() {
        return this.web_icon;
    }
    public void setWeb_icon(String web_icon) {
        this.web_icon = web_icon;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getUser_id() {
        return this.user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getParent_id() {
        return this.parent_id;
    }
    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
    public void setId(long id) {
        this.id = id;
    }

}
