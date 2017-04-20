package tarce.model;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/2/17.
 */

public class GetGroupByListresponse {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"picking_type_id_count":1364,"__domain":[["picking_type_id","=",1]],"picking_type_id":[1,"工厂仓库: 收货"]},{"picking_type_id_count":1341,"__domain":[["picking_type_id","=",4]],"picking_type_id":[4,"工厂仓库: 交货单"]}],"res_msg":"","res_code":1}
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
         * res_data : [{"picking_type_id_count":1364,"__domain":[["picking_type_id","=",1]],"picking_type_id":[1,"工厂仓库: 收货"]},{"picking_type_id_count":1341,"__domain":[["picking_type_id","=",4]],"picking_type_id":[4,"工厂仓库: 交货单"]}]
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
             * picking_type_id_count : 1364
             * __domain : [["picking_type_id","=",1]]
             * picking_type_id : [1,"工厂仓库: 收货"]
             */

            private int picking_type_id_count;
            private List<List<String>> __domain;
            private List<Object> picking_type_id;

            public int getPicking_type_id_count() {
                return picking_type_id_count;
            }

            public void setPicking_type_id_count(int picking_type_id_count) {
                this.picking_type_id_count = picking_type_id_count;
            }

            public List<List<String>> get__domain() {
                return __domain;
            }

            public void set__domain(List<List<String>> __domain) {
                this.__domain = __domain;
            }

            public List<Object> getPicking_type_id() {
                return picking_type_id;
            }

            public void setPicking_type_id(List<Object> picking_type_id) {
                this.picking_type_id = picking_type_id;
            }
        }
    }
}
