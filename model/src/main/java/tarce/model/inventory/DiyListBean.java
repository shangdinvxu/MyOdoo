package tarce.model.inventory;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by zouwansheng on 2017/8/30.
 *
 */

public class DiyListBean implements Serializable{

    public static class ProductBean implements Serializable{
        public int product_id;
        public String product_name;
        public String image_medium;
        public ArearBean area;
        public String product_spec;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getImage_medium() {
            return image_medium;
        }

        public void setImage_medium(String image_medium) {
            this.image_medium = image_medium;
        }

        public ArearBean getArea() {
            return area;
        }

        public void setArea(ArearBean area) {
            this.area = area;
        }

        public String getProduct_spec() {
            return product_spec;
        }

        public void setProduct_spec(String product_spec) {
            this.product_spec = product_spec;
        }

        public static class ArearBean implements Serializable{
            public int area_id;
            public String area_name;

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }
        }
    }
    public double product_qty;
    public double theoretical_qty;
    public ProductBean product;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public double getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(double product_qty) {
        this.product_qty = product_qty;
    }

    public double getTheoretical_qty() {
        return theoretical_qty;
    }

    public void setTheoretical_qty(double theoretical_qty) {
        this.theoretical_qty = theoretical_qty;
    }
}
