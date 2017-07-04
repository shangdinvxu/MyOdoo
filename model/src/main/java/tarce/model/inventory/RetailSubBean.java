package tarce.model.inventory;

import java.util.List;

/**
 * Created by zouzou on 2017/7/4.
 *零售子页面
 */

public class RetailSubBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"partner_id":3537,"name":"若来（C店）"},{"partner_id":3535,"name":"若态旗舰店"},{"partner_id":3536,"name":"若态若贝尔专卖店"},{"partner_id":2658,"name":"若态木质玩具（京东）"}],"res_msg":"","res_code":1}
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
         * res_data : [{"partner_id":3537,"name":"若来（C店）"},{"partner_id":3535,"name":"若态旗舰店"},{"partner_id":3536,"name":"若态若贝尔专卖店"},{"partner_id":2658,"name":"若态木质玩具（京东）"}]
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
             * partner_id : 3537
             * name : 若来（C店）
             */

            private int partner_id;
            private String name;

            public int getPartner_id() {
                return partner_id;
            }

            public void setPartner_id(int partner_id) {
                this.partner_id = partner_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
