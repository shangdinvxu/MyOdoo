package tarce.myodoo.bean;

/**
 * Created by Daniel.Xu on 2017/5/4.
 */

public class AvailabilityBean {
    public AvailabilityBean(String name,int per, int number) {
        this.name = name;
        this.per = per;
        this.number = number;
    }

    private int per ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    int number ;
}
