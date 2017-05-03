package tarce.myodoo.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Daniel.Xu on 2017/5/2.
 */

public class MainItemBean extends SectionEntity<MenuBean> {
    public MainItemBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MainItemBean(MenuBean menuBean) {
        super(menuBean);
    }
}
