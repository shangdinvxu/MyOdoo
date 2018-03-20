package tarce.model;

import java.io.Serializable;

/**
 * Created by zouwansheng on 2018/1/31.
 */

public  class ImageBean implements Serializable {
    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ImageBean(String image_url) {
        this.image_url = image_url;
    }
}
