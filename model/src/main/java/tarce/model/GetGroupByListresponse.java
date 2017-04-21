package tarce.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Daniel.Xu on 2017/2/17.
 */

public class GetGroupByListresponse  implements Serializable {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"states":[{"state":"assigned","state_count":122,"__domain":["&",["state","=","assigned"],["picking_type_id","=",1]]},{"state":"cancel","state_count":177,"__domain":["&",["state","=","cancel"],["picking_type_id","=",1]]},{"state":"confirmed","state_count":1,"__domain":["&",["state","=","confirmed"],["picking_type_id","=",1]]},{"state":"done","state_count":1890,"__domain":["&",["state","=","done"],["picking_type_id","=",1]]},{"state":"draft","state_count":175,"__domain":["&",["state","=","draft"],["picking_type_id","=",1]]},{"state":"qc_check","state_count":25,"__domain":["&",["state","=","qc_check"],["picking_type_id","=",1]]}],"picking_type_id_count":2390,"picking_type_name":"工厂仓库: 收货","picking_type_code":"incoming","picking_type_id":1},{"states":[{"state":"assigned","state_count":6,"__domain":["&",["state","=","assigned"],["picking_type_id","=",4]]},{"state":"cancel","state_count":1247,"__domain":["&",["state","=","cancel"],["picking_type_id","=",4]]},{"state":"confirmed","state_count":3,"__domain":["&",["state","=","confirmed"],["picking_type_id","=",4]]},{"state":"done","state_count":1270,"__domain":["&",["state","=","done"],["picking_type_id","=",4]]},{"state":"draft","state_count":3,"__domain":["&",["state","=","draft"],["picking_type_id","=",4]]},{"state":"partially_available","state_count":6,"__domain":["&",["state","=","partially_available"],["picking_type_id","=",4]]},{"state":"post","state_count":5,"__domain":["&",["state","=","post"],["picking_type_id","=",4]]},{"state":"prepare","state_count":8,"__domain":["&",["state","=","prepare"],["picking_type_id","=",4]]},{"state":"waiting","state_count":20,"__domain":["&",["state","=","waiting"],["picking_type_id","=",4]]},{"state":"waiting_out","state_count":15,"__domain":["&",["state","=","waiting_out"],["picking_type_id","=",4]]}],"picking_type_id_count":2583,"picking_type_name":"工厂仓库: 交货单","picking_type_code":"outgoing","picking_type_id":4},{"states":[{"state":"assigned","state_count":1,"__domain":["&",["state","=","assigned"],["picking_type_id","=",7]]}],"picking_type_id_count":1,"picking_type_name":"公司仓库: 收货","picking_type_code":"incoming","picking_type_id":7},{"states":[{"state":"cancel","state_count":1,"__domain":["&",["state","=","cancel"],["picking_type_id","=",11]]}],"picking_type_id_count":1,"picking_type_name":"公司仓库: 内部调拨","picking_type_code":"internal","picking_type_id":11}],"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public Object getId() {
        return id;
    }

    public ResultBean getResult() {
        return result;
    }

    public static class ResultBean   implements Serializable{
        /**
         * res_data : [{"states":[{"state":"assigned","state_count":122,"__domain":["&",["state","=","assigned"],["picking_type_id","=",1]]},{"state":"cancel","state_count":177,"__domain":["&",["state","=","cancel"],["picking_type_id","=",1]]},{"state":"confirmed","state_count":1,"__domain":["&",["state","=","confirmed"],["picking_type_id","=",1]]},{"state":"done","state_count":1890,"__domain":["&",["state","=","done"],["picking_type_id","=",1]]},{"state":"draft","state_count":175,"__domain":["&",["state","=","draft"],["picking_type_id","=",1]]},{"state":"qc_check","state_count":25,"__domain":["&",["state","=","qc_check"],["picking_type_id","=",1]]}],"picking_type_id_count":2390,"picking_type_name":"工厂仓库: 收货","picking_type_code":"incoming","picking_type_id":1},{"states":[{"state":"assigned","state_count":6,"__domain":["&",["state","=","assigned"],["picking_type_id","=",4]]},{"state":"cancel","state_count":1247,"__domain":["&",["state","=","cancel"],["picking_type_id","=",4]]},{"state":"confirmed","state_count":3,"__domain":["&",["state","=","confirmed"],["picking_type_id","=",4]]},{"state":"done","state_count":1270,"__domain":["&",["state","=","done"],["picking_type_id","=",4]]},{"state":"draft","state_count":3,"__domain":["&",["state","=","draft"],["picking_type_id","=",4]]},{"state":"partially_available","state_count":6,"__domain":["&",["state","=","partially_available"],["picking_type_id","=",4]]},{"state":"post","state_count":5,"__domain":["&",["state","=","post"],["picking_type_id","=",4]]},{"state":"prepare","state_count":8,"__domain":["&",["state","=","prepare"],["picking_type_id","=",4]]},{"state":"waiting","state_count":20,"__domain":["&",["state","=","waiting"],["picking_type_id","=",4]]},{"state":"waiting_out","state_count":15,"__domain":["&",["state","=","waiting_out"],["picking_type_id","=",4]]}],"picking_type_id_count":2583,"picking_type_name":"工厂仓库: 交货单","picking_type_code":"outgoing","picking_type_id":4},{"states":[{"state":"assigned","state_count":1,"__domain":["&",["state","=","assigned"],["picking_type_id","=",7]]}],"picking_type_id_count":1,"picking_type_name":"公司仓库: 收货","picking_type_code":"incoming","picking_type_id":7},{"states":[{"state":"cancel","state_count":1,"__domain":["&",["state","=","cancel"],["picking_type_id","=",11]]}],"picking_type_id_count":1,"picking_type_name":"公司仓库: 内部调拨","picking_type_code":"internal","picking_type_id":11}]
         * res_msg :
         * res_code : 1
         */

        private String res_msg;
        private int res_code;
        private List<ResDataBean> res_data;

        public void setRes_msg(String res_msg) {
            this.res_msg = res_msg;
        }

        public void setRes_code(int res_code) {
            this.res_code = res_code;
        }

        public void setRes_data(List<ResDataBean> res_data) {
            this.res_data = res_data;
        }

        public String getRes_msg() {
            return res_msg;
        }

        public int getRes_code() {
            return res_code;
        }

        public List<ResDataBean> getRes_data() {
            return res_data;
        }

        public static class ResDataBean  implements Serializable{
            /**
             * states : [{"state":"assigned","state_count":122,"__domain":["&",["state","=","assigned"],["picking_type_id","=",1]]},{"state":"cancel","state_count":177,"__domain":["&",["state","=","cancel"],["picking_type_id","=",1]]},{"state":"confirmed","state_count":1,"__domain":["&",["state","=","confirmed"],["picking_type_id","=",1]]},{"state":"done","state_count":1890,"__domain":["&",["state","=","done"],["picking_type_id","=",1]]},{"state":"draft","state_count":175,"__domain":["&",["state","=","draft"],["picking_type_id","=",1]]},{"state":"qc_check","state_count":25,"__domain":["&",["state","=","qc_check"],["picking_type_id","=",1]]}]
             * picking_type_id_count : 2390
             * picking_type_name : 工厂仓库: 收货
             * picking_type_code : incoming
             * picking_type_id : 1
             */

            private int picking_type_id_count;
            private String picking_type_name;
            private String picking_type_code;
            private int picking_type_id;
            private List<StatesBean> states;

            public void setPicking_type_id_count(int picking_type_id_count) {
                this.picking_type_id_count = picking_type_id_count;
            }

            public void setPicking_type_name(String picking_type_name) {
                this.picking_type_name = picking_type_name;
            }

            public void setPicking_type_code(String picking_type_code) {
                this.picking_type_code = picking_type_code;
            }

            public void setPicking_type_id(int picking_type_id) {
                this.picking_type_id = picking_type_id;
            }

            public void setStates(List<StatesBean> states) {
                this.states = states;
            }

            public int getPicking_type_id_count() {
                return picking_type_id_count;
            }

            public String getPicking_type_name() {
                return picking_type_name;
            }

            public String getPicking_type_code() {
                return picking_type_code;
            }

            public int getPicking_type_id() {
                return picking_type_id;
            }

            public List<StatesBean> getStates() {
                return states;
            }

            public static class StatesBean implements Serializable{
                /**
                 * state : assigned
                 * state_count : 122
                 * __domain : ["&",["state","=","assigned"],["picking_type_id","=",1]]
                 */

                private String state;
                private int state_count;
                private List<Object> __domain;

                public void setState(String state) {
                    this.state = state;
                }

                public void setState_count(int state_count) {
                    this.state_count = state_count;
                }

                public void set__domain(List<Object> __domain) {
                    this.__domain = __domain;
                }

                public String getState() {
                    return state;
                }

                public int getState_count() {
                    return state_count;
                }

                public List<Object> get__domain() {
                    return __domain;
                }
            }
        }
    }
}
