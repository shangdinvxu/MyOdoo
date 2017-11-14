package tarce.model.inventory;

import tarce.model.ErrorBean;

/**
 * Created by zouwansheng on 2017/11/11.
 */

public class ChangeWeightBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"area_id":{"area_name":false,"area_id":false},"product_spec":"客供文件，18.6x23.1cm，128g铜板纸 正面彩印反面黑白 ","weight":28,"product_product_id":74345,"default_code":"43.1JP294.194","qty_available":0,"categ_id":"43彩纸/彩卡/剪纸/特种纸","image_medium":"http://192.168.88.135:8069/linkloving_app_api/get_product_image?product_id=77144&model=product.template","virtual_available":0,"product_id":77144,"inner_code":false,"inner_spec":false,"type":"product","product_name":"\tJP294-彩纸(救生船)-Gerardo's toys "},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

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
         * res_data : {"area_id":{"area_name":false,"area_id":false},"product_spec":"客供文件，18.6x23.1cm，128g铜板纸 正面彩印反面黑白 ","weight":28,"product_product_id":74345,"default_code":"43.1JP294.194","qty_available":0,"categ_id":"43彩纸/彩卡/剪纸/特种纸","image_medium":"http://192.168.88.135:8069/linkloving_app_api/get_product_image?product_id=77144&model=product.template","virtual_available":0,"product_id":77144,"inner_code":false,"inner_spec":false,"type":"product","product_name":"\tJP294-彩纸(救生船)-Gerardo's toys "}
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

        public static class ResDataBean {
            /**
             * area_id : {"area_name":false,"area_id":false}
             * product_spec : 客供文件，18.6x23.1cm，128g铜板纸 正面彩印反面黑白
             * weight : 28.0
             * product_product_id : 74345
             * default_code : 43.1JP294.194
             * qty_available : 0.0
             * categ_id : 43彩纸/彩卡/剪纸/特种纸
             * image_medium : http://192.168.88.135:8069/linkloving_app_api/get_product_image?product_id=77144&model=product.template
             * virtual_available : 0.0
             * product_id : 77144
             * inner_code : false
             * inner_spec : false
             * type : product
             * product_name : 	JP294-彩纸(救生船)-Gerardo's toys
             */

            private AreaIdBean area_id;
            private String product_spec;
            private double weight;
            private int product_product_id;
            private String default_code;
            private double qty_available;
            private String categ_id;
            private String image_medium;
            private double virtual_available;
            private int product_id;
            private boolean inner_code;
            private boolean inner_spec;
            private String type;
            private String product_name;

            public AreaIdBean getArea_id() {
                return area_id;
            }

            public void setArea_id(AreaIdBean area_id) {
                this.area_id = area_id;
            }

            public String getProduct_spec() {
                return product_spec;
            }

            public void setProduct_spec(String product_spec) {
                this.product_spec = product_spec;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
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

            public boolean isInner_code() {
                return inner_code;
            }

            public void setInner_code(boolean inner_code) {
                this.inner_code = inner_code;
            }

            public boolean isInner_spec() {
                return inner_spec;
            }

            public void setInner_spec(boolean inner_spec) {
                this.inner_spec = inner_spec;
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

            public static class AreaIdBean {
                /**
                 * area_name : false
                 * area_id : false
                 */

                private boolean area_name;
                private boolean area_id;

                public boolean isArea_name() {
                    return area_name;
                }

                public void setArea_name(boolean area_name) {
                    this.area_name = area_name;
                }

                public boolean isArea_id() {
                    return area_id;
                }

                public void setArea_id(boolean area_id) {
                    this.area_id = area_id;
                }
            }
        }
    }
}
