package tarce.model.inventory;

import java.util.List;

/**
 * Created by zouzou on 2017/6/27.
 * 获取退料数量的接口
 */

public class GetReturnMaterBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"product_tmpl_id":47943,"product_id":"[CY.0JP225.001] 冲压制程品-JP225-木板(狮子)-2-1+2","return_qty":50}],"res_msg":"","res_code":1}
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
         * res_data : [{"product_tmpl_id":47943,"product_id":"[CY.0JP225.001] 冲压制程品-JP225-木板(狮子)-2-1+2","return_qty":50}]
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

        public static class ResDataBean {
            /**
             * product_tmpl_id : 47943
             * product_id : [CY.0JP225.001] 冲压制程品-JP225-木板(狮子)-2-1+2
             * return_qty : 50.0
             */

            private int product_tmpl_id;
            private String product_id;
            private double return_qty;

            public int getProduct_tmpl_id() {
                return product_tmpl_id;
            }

            public void setProduct_tmpl_id(int product_tmpl_id) {
                this.product_tmpl_id = product_tmpl_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public double getReturn_qty() {
                return return_qty;
            }

            public void setReturn_qty(double return_qty) {
                this.return_qty = return_qty;
            }
        }
    }
}
