package tarce.model.inventory;

import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by rose.zou on 2017/6/15.
 * 生产中  工序
 */

public class ProductProcessBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"process_count":1,"process_id":3,"name":"裱纸"},{"process_count":7,"process_id":8,"name":"全检"}],"res_msg":"","res_code":1}
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
         * res_data : [{"process_count":1,"process_id":3,"name":"裱纸"},{"process_count":7,"process_id":8,"name":"全检"}]
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
             * process_count : 1
             * process_id : 3
             * name : 裱纸
             */

            private int process_count;
            private int process_id;
            private String name;

            public int getProcess_count() {
                return process_count;
            }

            public void setProcess_count(int process_count) {
                this.process_count = process_count;
            }

            public int getProcess_id() {
                return process_id;
            }

            public void setProcess_id(int process_id) {
                this.process_id = process_id;
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
