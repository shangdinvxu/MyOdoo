package tarce.model;

import java.util.List;

/**
 * Created by rose.zou on 2017/6/1.
 * 改变员工的状态返回数据
 */

public class ChangeStateBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"amount_of_money":0.2,"worker":{"worker_id":578,"name":"彭飞"},"line_state":"online","worker_time_line_ids":[{"start_time":"2017-05-09 02:26:52","worker_id":578,"end_time":"2017-05-09 02:27:26","state":"online"},{"start_time":"2017-05-09 02:27:26","worker_id":578,"end_time":"2017-06-01 05:24:31","state":"outline"},{"start_time":"2017-06-01 05:24:31","worker_id":578,"end_time":"2017-06-01 05:34:10","state":"online"},{"start_time":"2017-06-01 05:34:10","worker_id":578,"end_time":"2017-06-01 05:36:16","state":"outline"},{"start_time":"2017-06-01 05:36:16","worker_id":578,"end_time":"2017-06-01 05:44:25","state":"online"},{"start_time":"2017-06-01 05:44:25","worker_id":578,"end_time":"2017-06-01 05:45:59","state":"offline"},{"start_time":"2017-06-01 05:45:59","worker_id":578,"end_time":"2017-06-01 05:46:03","state":"online"},{"start_time":"2017-06-01 05:46:03","worker_id":578,"end_time":"2017-06-01 05:49:28","state":"offline"},{"start_time":"2017-06-01 05:49:28","worker_id":578,"end_time":"2017-06-01 05:51:48","state":"online"},{"start_time":"2017-06-01 05:51:48","worker_id":578,"end_time":"2017-06-01 05:51:51","state":"outline"},{"start_time":"2017-06-01 05:51:51","worker_id":578,"end_time":"2017-06-01 05:56:47","state":"online"},{"start_time":"2017-06-01 05:56:47","worker_id":578,"end_time":"2017-06-01 05:56:49","state":"outline"},{"start_time":"2017-06-01 05:56:49","worker_id":578,"end_time":false,"state":"online"}],"unit_price":0.2,"worker_id":533,"xishu":1},"res_msg":"","res_code":1}
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
         * res_data : {"amount_of_money":0.2,"worker":{"worker_id":578,"name":"彭飞"},"line_state":"online","worker_time_line_ids":[{"start_time":"2017-05-09 02:26:52","worker_id":578,"end_time":"2017-05-09 02:27:26","state":"online"},{"start_time":"2017-05-09 02:27:26","worker_id":578,"end_time":"2017-06-01 05:24:31","state":"outline"},{"start_time":"2017-06-01 05:24:31","worker_id":578,"end_time":"2017-06-01 05:34:10","state":"online"},{"start_time":"2017-06-01 05:34:10","worker_id":578,"end_time":"2017-06-01 05:36:16","state":"outline"},{"start_time":"2017-06-01 05:36:16","worker_id":578,"end_time":"2017-06-01 05:44:25","state":"online"},{"start_time":"2017-06-01 05:44:25","worker_id":578,"end_time":"2017-06-01 05:45:59","state":"offline"},{"start_time":"2017-06-01 05:45:59","worker_id":578,"end_time":"2017-06-01 05:46:03","state":"online"},{"start_time":"2017-06-01 05:46:03","worker_id":578,"end_time":"2017-06-01 05:49:28","state":"offline"},{"start_time":"2017-06-01 05:49:28","worker_id":578,"end_time":"2017-06-01 05:51:48","state":"online"},{"start_time":"2017-06-01 05:51:48","worker_id":578,"end_time":"2017-06-01 05:51:51","state":"outline"},{"start_time":"2017-06-01 05:51:51","worker_id":578,"end_time":"2017-06-01 05:56:47","state":"online"},{"start_time":"2017-06-01 05:56:47","worker_id":578,"end_time":"2017-06-01 05:56:49","state":"outline"},{"start_time":"2017-06-01 05:56:49","worker_id":578,"end_time":false,"state":"online"}],"unit_price":0.2,"worker_id":533,"xishu":1}
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
             * amount_of_money : 0.2
             * worker : {"worker_id":578,"name":"彭飞"}
             * line_state : online
             * worker_time_line_ids : [{"start_time":"2017-05-09 02:26:52","worker_id":578,"end_time":"2017-05-09 02:27:26","state":"online"},{"start_time":"2017-05-09 02:27:26","worker_id":578,"end_time":"2017-06-01 05:24:31","state":"outline"},{"start_time":"2017-06-01 05:24:31","worker_id":578,"end_time":"2017-06-01 05:34:10","state":"online"},{"start_time":"2017-06-01 05:34:10","worker_id":578,"end_time":"2017-06-01 05:36:16","state":"outline"},{"start_time":"2017-06-01 05:36:16","worker_id":578,"end_time":"2017-06-01 05:44:25","state":"online"},{"start_time":"2017-06-01 05:44:25","worker_id":578,"end_time":"2017-06-01 05:45:59","state":"offline"},{"start_time":"2017-06-01 05:45:59","worker_id":578,"end_time":"2017-06-01 05:46:03","state":"online"},{"start_time":"2017-06-01 05:46:03","worker_id":578,"end_time":"2017-06-01 05:49:28","state":"offline"},{"start_time":"2017-06-01 05:49:28","worker_id":578,"end_time":"2017-06-01 05:51:48","state":"online"},{"start_time":"2017-06-01 05:51:48","worker_id":578,"end_time":"2017-06-01 05:51:51","state":"outline"},{"start_time":"2017-06-01 05:51:51","worker_id":578,"end_time":"2017-06-01 05:56:47","state":"online"},{"start_time":"2017-06-01 05:56:47","worker_id":578,"end_time":"2017-06-01 05:56:49","state":"outline"},{"start_time":"2017-06-01 05:56:49","worker_id":578,"end_time":false,"state":"online"}]
             * unit_price : 0.2
             * worker_id : 533
             * xishu : 1.0
             */

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
                 * worker_id : 578
                 * name : 彭飞
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
                 * start_time : 2017-05-09 02:26:52
                 * worker_id : 578
                 * end_time : 2017-05-09 02:27:26
                 * state : online
                 */

                private String start_time;
                private int worker_id;
                private String end_time;
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

                public String getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(String end_time) {
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
