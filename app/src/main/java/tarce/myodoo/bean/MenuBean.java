package tarce.myodoo.bean;

/**
 * Created by Daniel.Xu on 2017/5/2.
 */

public class MenuBean {
    private int number ;
    private String name ;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuBean(String name,int number) {
        this.number = number;
        this.name = name;
    }
}
