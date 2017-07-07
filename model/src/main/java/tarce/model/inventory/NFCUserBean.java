package tarce.model.inventory;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/6/30.
 */

public class NFCUserBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"card_num":"","employee_id":254,"work_email":"","name":"Administrator"},{"card_num":"","employee_id":594,"work_email":"qiao.dan@robotime.com","name":"乔丹"},],"res_msg":"","res_code":1}
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
         * res_data : [{"card_num":"","employee_id":254,"work_email":"","name":"Administrator"},{"card_num":"","employee_id":594,"work_email":"qiao.dan@robotime.com","name":"乔丹"},]
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
             * card_num :
             * employee_id : 254
             * work_email :
             * name : Administrator
             */

            private String card_num;
            private int employee_id;
            private String work_email;
            private String name;

            public String getCard_num() {
                return card_num;
            }

            public void setCard_num(String card_num) {
                this.card_num = card_num;
            }

            public int getEmployee_id() {
                return employee_id;
            }

            public void setEmployee_id(int employee_id) {
                this.employee_id = employee_id;
            }

            public String getWork_email() {
                return work_email;
            }

            public void setWork_email(String work_email) {
                this.work_email = work_email;
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
