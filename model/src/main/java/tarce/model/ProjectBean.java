package tarce.model;

import java.util.List;

/**
 * Created by zouwansheng on 2017/9/8.
 */

public class ProjectBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"finish_data":[{"create_uid":"刘艳菊julia","create_date":"2017-09-08","id":144,"delivery_date":"2017-09-15","name":"PD1504855630"},{"create_uid":"刘艳菊julia","create_date":"2017-09-08","id":147,"delivery_date":"2017-09-15","name":"PD1504857014"},{"create_uid":"陈小娟","create_date":"2017-09-08","id":152,"delivery_date":"2017-09-15","name":"PD1504858993"}],"waiting_data":[{"create_uid":"王亚飞peter","create_date":"2017-09-08","id":150,"delivery_date":"2017-09-27","name":"PD1504857730"}]},"res_msg":"","res_code":1}
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
         * res_data : {"finish_data":[{"create_uid":"刘艳菊julia","create_date":"2017-09-08","id":144,"delivery_date":"2017-09-15","name":"PD1504855630"},{"create_uid":"刘艳菊julia","create_date":"2017-09-08","id":147,"delivery_date":"2017-09-15","name":"PD1504857014"},{"create_uid":"陈小娟","create_date":"2017-09-08","id":152,"delivery_date":"2017-09-15","name":"PD1504858993"}],"waiting_data":[{"create_uid":"王亚飞peter","create_date":"2017-09-08","id":150,"delivery_date":"2017-09-27","name":"PD1504857730"}]}
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
            private List<WaitingDataBean> finish_data;
            private List<WaitingDataBean> waiting_data;

            public List<WaitingDataBean> getFinish_data() {
                return finish_data;
            }

            public void setFinish_data(List<WaitingDataBean> finish_data) {
                this.finish_data = finish_data;
            }

            public List<WaitingDataBean> getWaiting_data() {
                return waiting_data;
            }

            public void setWaiting_data(List<WaitingDataBean> waiting_data) {
                this.waiting_data = waiting_data;
            }

            public static class WaitingDataBean {
                /**
                 * create_uid : 王亚飞peter
                 * create_date : 2017-09-08
                 * id : 150
                 * delivery_date : 2017-09-27
                 * name : PD1504857730
                 */

                private String create_uid;
                private String create_date;
                private int id;
                private String delivery_date;
                private String name;
                private String picking_state;

                public String getPicking_state() {
                    return picking_state;
                }

                public void setPicking_state(String picking_state) {
                    this.picking_state = picking_state;
                }

                public String getCreate_uid() {
                    return create_uid;
                }

                public void setCreate_uid(String create_uid) {
                    this.create_uid = create_uid;
                }

                public String getCreate_date() {
                    return create_date;
                }

                public void setCreate_date(String create_date) {
                    this.create_date = create_date;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getDelivery_date() {
                    return delivery_date;
                }

                public void setDelivery_date(String delivery_date) {
                    this.delivery_date = delivery_date;
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
}
