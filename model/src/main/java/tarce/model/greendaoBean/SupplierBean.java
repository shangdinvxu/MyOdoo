package tarce.model.greendaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by  zouzou on 2017/6/21.
 * comment: ""
 name: " 沭阳县颜集镇华绿园林苗木场"
 partner_id: 2685
 phone: ""
 x_qq: ""
 */
@Entity
public class SupplierBean {
    private String comment;
    private String name;
    @Id(autoincrement = true)
    private Long partner_id;
    private String phone;
    private String x_qq;
    @Generated(hash = 534939553)
    public SupplierBean(String comment, String name, Long partner_id, String phone,
            String x_qq) {
        this.comment = comment;
        this.name = name;
        this.partner_id = partner_id;
        this.phone = phone;
        this.x_qq = x_qq;
    }
    @Generated(hash = 1501921620)
    public SupplierBean() {
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getPartner_id() {
        return this.partner_id;
    }
    public void setPartner_id(Long partner_id) {
        this.partner_id = partner_id;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getX_qq() {
        return this.x_qq;
    }
    public void setX_qq(String x_qq) {
        this.x_qq = x_qq;
    }

}
