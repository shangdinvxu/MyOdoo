package tarce.myodoo.bean;

/**
 * Created by Daniel.Xu on 2017/5/4.
 */

public class AvailabilityBean {
    public AvailabilityBean(int per, int number) {
        this.per = per;
        this.number = number;
    }

    int per ;

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
