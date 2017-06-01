package tarce.myodoo.bean;

/**
 * Created by rose.zou on 2017/6/1.
 * 为了人员管理，增加员工状态
 */

public class WorkingStateBean {

    private String state;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public WorkingStateBean(String name, String state) {
        this.name = name;
        this.state = state;
    }
}
