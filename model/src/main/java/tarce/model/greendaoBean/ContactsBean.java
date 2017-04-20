package tarce.model.greendaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Daniel.Xu on 2017/2/21.
 */
@Entity
public class ContactsBean {
    private String comment;
    private String phone;
    @Id(autoincrement = true)
    private Long partner_id;
    private String name;
    private String x_qq;
    @Generated(hash = 1063711758)
    public ContactsBean(String comment, String phone, Long partner_id, String name,
            String x_qq) {
        this.comment = comment;
        this.phone = phone;
        this.partner_id = partner_id;
        this.name = name;
        this.x_qq = x_qq;
    }
    @Generated(hash = 747317112)
    public ContactsBean() {
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Long getPartner_id() {
        return this.partner_id;
    }
    public void setPartner_id(Long partner_id) {
        this.partner_id = partner_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getX_qq() {
        return this.x_qq;
    }
    public void setX_qq(String x_qq) {
        this.x_qq = x_qq;
    }



}
