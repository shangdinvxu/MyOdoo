package tarce.myodoo.greendaoUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import greendao.SupplierBeanDao;
import tarce.model.greendaoBean.SupplierBean;

/**
 * Created by zouzou on 2017/6/26.
 *
 */

public class SupplierBeanUtils {
    private SupplierBeanDao supplierBeanDao;

    public SupplierBeanUtils(){
        supplierBeanDao = GreenDaoManager.getInstance().getmDaoSession().getSupplierBeanDao();
    }

    public List<SupplierBean> searchByName(String name){
        Query<SupplierBean> supplierBeanQuery = supplierBeanDao.queryBuilder().where(SupplierBeanDao.Properties.Name.like("%"+name+"%")).build();
        List<SupplierBean> supplierBeanList = supplierBeanQuery.list();
        return supplierBeanList;
    }
}
