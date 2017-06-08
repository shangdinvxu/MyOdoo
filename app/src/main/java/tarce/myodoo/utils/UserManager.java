package tarce.myodoo.utils;


import java.util.ArrayList;
import java.util.List;

import tarce.model.LoginResponse;

/**
 * Created by rose.zou 2017/6/8
 *
 * 存放user信息的单例
 */
public class UserManager {


    private LoginResponse userInfoBean;
    private List<LoginResponse.ResultBean.ResDataBean.GroupsBean> groups;
    private List<String> permissionList = new ArrayList<>();

    public LoginResponse getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(LoginResponse userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public List<String> getGrops(){
        groups = userInfoBean.getResult().getRes_data().getGroups();
        for (int i = 0; i < groups.size(); i++) {
            permissionList.add(groups.get(i).getName());
        }
        return permissionList;
    }
    //私有构造函数 避免外接创建多个实例
    private UserManager(){
        userInfoBean = null;
    }

    private static UserManager singleton = new UserManager();

    //公有静态获取实例方法
    public static UserManager getSingleton() {
        return singleton;
    }


    /**
     * 更新用户信息
     *
     * @param infoBean
     * */
    public void reFreshUserInfo(LoginResponse infoBean){
        userInfoBean.setResult(infoBean.getResult());
  //      userInfoBean.setClubId(infoBean.getClubId());
        /*userInfoBean.setCreatedTime(infoBean.getCreatedTime());
        userInfoBean.setId(infoBean.getId());
        userInfoBean.setMobileNumber(infoBean.getMobileNumber());
        userInfoBean.setOpenId(infoBean.getOpenId());
        userInfoBean.setPassword(infoBean.getPassword());
        userInfoBean.setSignature(infoBean.getSignature());
     //   userInfoBean.setThirdAccount(infoBean.getThirdAccount());
        userInfoBean.setThirdType(infoBean.getThirdType());
        userInfoBean.setToken(infoBean.getToken());
        userInfoBean.setUserImg(infoBean.getUserImg());
        userInfoBean.setUserName(infoBean.getUserName());
        userInfoBean.setUserRole(infoBean.getUserRole());*/
    }


}
