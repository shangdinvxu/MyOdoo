package tarce.myodoo.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by zouwansheng on 2017/12/18.
 */

public class MainProcessBean extends SectionEntity<ProcessBean> {
    public MainProcessBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MainProcessBean(ProcessBean processBean) {
        super(processBean);
    }
}
