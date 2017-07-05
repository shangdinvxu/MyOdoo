package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockListBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"area_id":{"area_name":false,"area_id":false},"product_spec":"印刷和激光都是外加工（分别是不同供应商），来料为半成品由工厂包装，配件：双面胶（工厂用来粘贺卡），彩卡，信封（放彩卡和彩纸中间），彩纸（放产品最后面），以上一起装PE复合自封袋，烫银不干胶标签（贴PE袋外面，产品正面）。30个装一个内箱，客户选3款后再装一个外箱发货。","product_product_id":46537,"default_code":"99.0HS310.000","qty_available":1800,"categ_id":"HS贺卡","image_medium":"http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=48204&model=product.template","virtual_available":1800,"product_id":48204,"inner_code":"星夜·追梦人","inner_spec":"HS310","type":"product","product_name":"  HS310-成品(星夜·追梦人){RT-CN}"},],"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * res_data : [{"area_id":{"area_name":false,"area_id":false},"product_spec":"印刷和激光都是外加工（分别是不同供应商），来料为半成品由工厂包装，配件：双面胶（工厂用来粘贺卡），彩卡，信封（放彩卡和彩纸中间），彩纸（放产品最后面），以上一起装PE复合自封袋，烫银不干胶标签（贴PE袋外面，产品正面）。30个装一个内箱，客户选3款后再装一个外箱发货。","product_product_id":46537,"default_code":"99.0HS310.000","qty_available":1800,"categ_id":"HS贺卡","image_medium":"http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=48204&model=product.template","virtual_available":1800,"product_id":48204,"inner_code":"星夜·追梦人","inner_spec":"HS310","type":"product","product_name":"  HS310-成品(星夜·追梦人){RT-CN}"},]
         * res_msg :
         * res_code : 1
         */

        private String res_msg;
        private int res_code;
        private List<ResDataBean> res_data;

        public String getRes_msg() {
            return res_msg;
        }

        public void setRes_msg(String res_msg) {
            this.res_msg = res_msg;
        }

        public int getRes_code() {
            return res_code;
        }

        public void setRes_code(int res_code) {
            this.res_code = res_code;
        }

        public List<ResDataBean> getRes_data() {
            return res_data;
        }

        public void setRes_data(List<ResDataBean> res_data) {
            this.res_data = res_data;
        }

        public static class ResDataBean implements Serializable{
            /**
             * area_id : {"area_name":false,"area_id":false}
             * product_spec : 印刷和激光都是外加工（分别是不同供应商），来料为半成品由工厂包装，配件：双面胶（工厂用来粘贺卡），彩卡，信封（放彩卡和彩纸中间），彩纸（放产品最后面），以上一起装PE复合自封袋，烫银不干胶标签（贴PE袋外面，产品正面）。30个装一个内箱，客户选3款后再装一个外箱发货。
             * product_product_id : 46537
             * default_code : 99.0HS310.000
             * qty_available : 1800.0
             * categ_id : HS贺卡
             * image_medium : http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=48204&model=product.template
             * virtual_available : 1800.0
             * product_id : 48204
             * inner_code : 星夜·追梦人
             * inner_spec : HS310
             * type : product
             * product_name :   HS310-成品(星夜·追梦人){RT-CN}
             */

            private AreaIdBean area_id;
            private Object product_spec;
            private int product_product_id;
            private String default_code;
            private double qty_available;
            private String categ_id;
            private String image_medium;
            private double virtual_available;
            private int product_id;
            private Object inner_code;

            public Object getProduct_spec() {
                if (product_spec instanceof Boolean){
                    product_spec = "";
                }
                return product_spec;
            }

            public void setProduct_spec(Object product_spec) {
                this.product_spec = product_spec;
            }

            public Object getInner_code() {
                if (inner_code instanceof Boolean){
                    inner_code = "";
                }
                return inner_code;
            }

            public void setInner_code(Object inner_code) {
                this.inner_code = inner_code;
            }

            public Object getInner_spec() {
                if (inner_spec instanceof Boolean){
                    inner_spec = "";
                }
                return inner_spec;
            }

            public void setInner_spec(Object inner_spec) {
                this.inner_spec = inner_spec;
            }

            private Object inner_spec;
            private String type;
            private String product_name;

            public AreaIdBean getArea_id() {
                return area_id;
            }

            public void setArea_id(AreaIdBean area_id) {
                this.area_id = area_id;
            }

            public int getProduct_product_id() {
                return product_product_id;
            }

            public void setProduct_product_id(int product_product_id) {
                this.product_product_id = product_product_id;
            }

            public String getDefault_code() {
                return default_code;
            }

            public void setDefault_code(String default_code) {
                this.default_code = default_code;
            }

            public double getQty_available() {
                return qty_available;
            }

            public void setQty_available(double qty_available) {
                this.qty_available = qty_available;
            }

            public String getCateg_id() {
                return categ_id;
            }

            public void setCateg_id(String categ_id) {
                this.categ_id = categ_id;
            }

            public String getImage_medium() {
                return image_medium;
            }

            public void setImage_medium(String image_medium) {
                this.image_medium = image_medium;
            }

            public double getVirtual_available() {
                return virtual_available;
            }

            public void setVirtual_available(double virtual_available) {
                this.virtual_available = virtual_available;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public static class AreaIdBean implements Serializable{
                public Object getArea_name() {
                    if (area_name instanceof Boolean){
                        area_name = "";
                    }
                    return area_name;
                }

                public void setArea_name(Object area_name) {
                    this.area_name = area_name;
                }

                public Object getArea_id() {
                    if (area_id instanceof Boolean){
                        area_id = 0;
                    }
                    return area_id;
                }

                public void setArea_id(Object area_id) {
                    this.area_id = area_id;
                }

                /**
                 * area_name : false
                 * area_id : false
                 */

                private Object area_name;
                private Object area_id;

            }
        }
    }
}
