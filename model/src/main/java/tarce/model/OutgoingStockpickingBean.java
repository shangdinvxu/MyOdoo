package tarce.model;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/5/4.
 */

public class OutgoingStockpickingBean {


    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"state":{"state":"done","state_count":1310,"__domain":["&",["state","=","done"],["state","=","done"],["picking_type_code","=","outgoing"]]},"complete_rate":[{"complete_rate_count":8,"complete_rate":0},{"complete_rate_count":88,"complete_rate":100},{"complete_rate_count":53,"complete_rate":99}]},"res_msg":"","res_code":1}
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

    public static class ResultBean {
        /**
         * res_data : {"state":{"state":"done","state_count":1310,"__domain":["&",["state","=","done"],["state","=","done"],["picking_type_code","=","outgoing"]]},"complete_rate":[{"complete_rate_count":8,"complete_rate":0},{"complete_rate_count":88,"complete_rate":100},{"complete_rate_count":53,"complete_rate":99}]}
         * res_msg :
         * res_code : 1
         */

        private ResDataBean res_data;
        private String res_msg;
        private int res_code;

        public void setRes_data(ResDataBean res_data) {
            this.res_data = res_data;
        }

        public void setRes_msg(String res_msg) {
            this.res_msg = res_msg;
        }

        public void setRes_code(int res_code) {
            this.res_code = res_code;
        }

        public ResDataBean getRes_data() {
            return res_data;
        }

        public String getRes_msg() {
            return res_msg;
        }

        public int getRes_code() {
            return res_code;
        }

        public static class ResDataBean {
            /**
             * state : {"state":"done","state_count":1310,"__domain":["&",["state","=","done"],["state","=","done"],["picking_type_code","=","outgoing"]]}
             * complete_rate : [{"complete_rate_count":8,"complete_rate":0},{"complete_rate_count":88,"complete_rate":100},{"complete_rate_count":53,"complete_rate":99}]
             */

            private StateBean state;
            private List<CompleteRateBean> complete_rate;

            public void setState(StateBean state) {
                this.state = state;
            }

            public void setComplete_rate(List<CompleteRateBean> complete_rate) {
                this.complete_rate = complete_rate;
            }

            public StateBean getState() {
                return state;
            }

            public List<CompleteRateBean> getComplete_rate() {
                return complete_rate;
            }

            public static class StateBean {
                /**
                 * state : done
                 * state_count : 1310
                 * __domain : ["&",["state","=","done"],["state","=","done"],["picking_type_code","=","outgoing"]]
                 */

                private String state;
                private int state_count;
                private Object __domain;

                public void setState(String state) {
                    this.state = state;
                }

                public void setState_count(int state_count) {
                    this.state_count = state_count;
                }

                public void set__domain(Object __domain) {
                    this.__domain = __domain;
                }

                public String getState() {
                    return state;
                }

                public int getState_count() {
                    return state_count;
                }

                    public Object get__domain() {
                    return __domain;
                }
            }

            public static class CompleteRateBean {
                public int getComplete_rate_count() {
                    return complete_rate_count;
                }

                /**
                 * complete_rate_count : 8
                 * complete_rate : 0
                 */

                private int complete_rate_count;


                public int getComplete_rate() {
                    return complete_rate;
                }

                public void setComplete_rate(int complete_rate) {
                    this.complete_rate = complete_rate;
                }

                private int complete_rate;

                public void setComplete_rate_count(int complete_rate_count) {
                    this.complete_rate_count = complete_rate_count;
                }

            }
        }
    }
}
