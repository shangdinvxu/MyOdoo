package tarce.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Daniel.Xu on 2017/2/8.
 */
public class FindProductByConditionResponse {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"product":{"area":{"id":false,"name":false},"image_medium":"http://192.168.2.111:8069/linkloving_app_api/get_product_image?product_id=48204","id":46537,"product_name":"  HS310-成品(星夜·追梦人)-RT-CN"},"theoretical_qty":-387,"product_qty":0},"res_msg":"","res_code":1}
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
         * res_data : {"product":{"area":{"id":false,"name":false},"image_medium":"http://192.168.2.111:8069/linkloving_app_api/get_product_image?product_id=48204","id":46537,"product_name":"  HS310-成品(星夜·追梦人)-RT-CN"},"theoretical_qty":-387,"product_qty":0}
         * res_msg :
         * res_code : 1
         */

        private ResDataBean res_data;
        private String res_msg;
        private int res_code;

        public ResDataBean getRes_data() {
            return res_data;
        }

        public void setRes_data(ResDataBean res_data) {
            this.res_data = res_data;
        }

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

        public static class ResDataBean implements Serializable{
            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            /**
             * product : {"area":{"id":false,"name":false},"image_medium":"http://192.168.2.111:8069/linkloving_app_api/get_product_image?product_id=48204","id":46537,"product_name":"  HS310-成品(星夜·追梦人)-RT-CN"}
             * theoretical_qty : -387
             * product_qty : 0
             */

            private String error ;
            private ProductBean product;
            private int theoretical_qty;
            private int product_qty;

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public int getTheoretical_qty() {
                return theoretical_qty;
            }

            public void setTheoretical_qty(int theoretical_qty) {
                this.theoretical_qty = theoretical_qty;
            }

            public int getProduct_qty() {
                return product_qty;
            }

            public void setProduct_qty(int product_qty) {
                this.product_qty = product_qty;
            }

            public static class ProductBean implements Serializable{
                /**
                 * area : {"id":false,"name":false}
                 * image_medium : http://192.168.2.111:8069/linkloving_app_api/get_product_image?product_id=48204
                 * id : 46537
                 * product_name :   HS310-成品(星夜·追梦人)-RT-CN
                 */

                private AreaBean area;
                private String image_medium;
                private int id;
                private int product_id;
                private String product_name;
                private String product_spec;

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public String getProduct_spec() {
                    return product_spec;
                }

                public void setProduct_spec(String product_spec) {
                    this.product_spec = product_spec;
                }

                public AreaBean getArea() {
                    return area;
                }

                public void setArea(AreaBean area) {
                    this.area = area;
                }

                public String getImage_medium() {
                    return image_medium;
                }

                public void setImage_medium(String image_medium) {
                    this.image_medium = image_medium;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public static class AreaBean implements Serializable{
                    /**
                     * id : false
                     * name : false
                     */

                    public Object id;
                    public Object name;

                    public Object getId() {
                        if (id instanceof Boolean){
                            id = 0 ;
                        }if (id instanceof Double){
                            id = Integer.parseInt(new DecimalFormat("0").format(id));
                        }
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public Object getName() {
                        if (name instanceof Boolean){
                            name = "";
                        }
                        return name;
                    }

                    public void setName(String name) {
                            this.name = name;
                    }
                }
            }
        }
    }
}
