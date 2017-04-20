package tarce.myodoo.greendaoUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import greendao.ContactsBeanDao;
import tarce.model.greendaoBean.ContactsBean;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class ContactBeanUtils {
    private  ContactsBeanDao contactsBeanDao;

    public ContactBeanUtils() {
        contactsBeanDao = GreenDaoManager.getInstance().getmDaoSession().getContactsBeanDao();
    }


    public List<ContactsBean> searchByName(String name ){
        Query<ContactsBean> build = contactsBeanDao.queryBuilder().where(ContactsBeanDao.Properties.Name.like("%" + name + "%")).build();
        List<ContactsBean> list = build.list();
        return list;

    }

}
