package tarce.myodoo.greendaoUtils;

import java.util.List;

import greendao.UserLoginDao;
import tarce.model.greendaoBean.UserLogin;


/**
 * Created by Daniel.Xu on 2017/2/10.
 */

public class UserLoginUtils {
    private UserLoginDao userLoginDao ;
    public UserLoginUtils() {
        this.userLoginDao = GreenDaoManager.getInstance().getmDaoSession().getUserLoginDao();
    }

    public void insertUser(UserLogin userLogin){
        userLoginDao.insertOrReplace(userLogin);
    }

    public List<UserLogin> searchAll(){
        List<UserLogin> userLogins = userLoginDao.loadAll();
        return userLogins;
    }

    public void deleteOne(UserLogin someOne){
        userLoginDao.delete(someOne);
    }

}
