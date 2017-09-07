package tarce.model;

import java.util.List;

/**
 * Created by rose.zou on 2017/5/31.
 * 点击添加完成添加员工接口返回
 */

public class AddworkBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"amount_of_money":0.08,"worker":{"worker_id":556,"name":"仲倩"},"line_state":"online","worker_time_line_ids":[{"start_time":"2017-05-31 01:31:23","worker_id":556,"end_time":false,"state":"online"}],"unit_price":0.08,"worker_id":2075,"xishu":1},{"amount_of_money":0.08,"worker":{"worker_id":491,"name":"仲想"},"line_state":"online","worker_time_line_ids":[{"start_time":"2017-05-31 01:31:23","worker_id":491,"end_time":false,"state":"online"}],"unit_price":0.08,"worker_id":2076,"xishu":1}],"res_msg":"","res_code":1}
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
         * res_data : [{"amount_of_money":0.08,"worker":{"worker_id":556,"name":"仲倩"},"line_state":"online","worker_time_line_ids":[{"start_time":"2017-05-31 01:31:23","worker_id":556,"end_time":false,"state":"online"}],"unit_price":0.08,"worker_id":2075,"xishu":1},{"amount_of_money":0.08,"worker":{"worker_id":491,"name":"仲想"},"line_state":"online","worker_time_line_ids":[{"start_time":"2017-05-31 01:31:23","worker_id":491,"end_time":false,"state":"online"}],"unit_price":0.08,"worker_id":2076,"xishu":1}]
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
            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            /**
             * amount_of_money : 0.08
             * worker : {"worker_id":556,"name":"仲倩"}
             * line_state : online
             * worker_time_line_ids : [{"start_time":"2017-05-31 01:31:23","worker_id":556,"end_time":false,"state":"online"}]
             * unit_price : 0.08
             * worker_id : 2075
             * xishu : 1.0
             */

            private String error;
            private double amount_of_money;
            private WorkerBean worker;
            private String line_state;
            private double unit_price;
            private int worker_id;
            private double xishu;
            private List<WorkerTimeLineIdsBean> worker_time_line_ids;

            public double getAmount_of_money() {
                return amount_of_money;
            }

            public void setAmount_of_money(double amount_of_money) {
                this.amount_of_money = amount_of_money;
            }

            public WorkerBean getWorker() {
                return worker;
            }

            public void setWorker(WorkerBean worker) {
                this.worker = worker;
            }

            public String getLine_state() {
                return line_state;
            }

            public void setLine_state(String line_state) {
                this.line_state = line_state;
            }

            public double getUnit_price() {
                return unit_price;
            }

            public void setUnit_price(double unit_price) {
                this.unit_price = unit_price;
            }

            public int getWorker_id() {
                return worker_id;
            }

            public void setWorker_id(int worker_id) {
                this.worker_id = worker_id;
            }

            public double getXishu() {
                return xishu;
            }

            public void setXishu(double xishu) {
                this.xishu = xishu;
            }

            public List<WorkerTimeLineIdsBean> getWorker_time_line_ids() {
                return worker_time_line_ids;
            }

            public void setWorker_time_line_ids(List<WorkerTimeLineIdsBean> worker_time_line_ids) {
                this.worker_time_line_ids = worker_time_line_ids;
            }

            public static class WorkerBean {
                /**
                 * worker_id : 556
                 * name : 仲倩
                 */

                private int worker_id;
                private String name;

                public int getWorker_id() {
                    return worker_id;
                }

                public void setWorker_id(int worker_id) {
                    this.worker_id = worker_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class WorkerTimeLineIdsBean {
                /**
                 * start_time : 2017-05-31 01:31:23
                 * worker_id : 556
                 * end_time : false
                 * state : online
                 */

                private String start_time;
                private int worker_id;
                private boolean end_time;
                private String state;

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public int getWorker_id() {
                    return worker_id;
                }

                public void setWorker_id(int worker_id) {
                    this.worker_id = worker_id;
                }

                public boolean isEnd_time() {
                    return end_time;
                }

                public void setEnd_time(boolean end_time) {
                    this.end_time = end_time;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
        }
    }
}
