package tarce.model.inventory;

import tarce.model.ErrorBean;

/**
 * Created by rose.zou on 2017/6/19.
 */

public class CommonBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_msg":"","res_code":1}
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
         * res_msg :
         * res_code : 1
         */

        private String res_msg;
        private int res_code;

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
        private ResDataBean res_data;

        public ResDataBean getRes_data() {
            return res_data;
        }

        public void setRes_data(ResDataBean res_data) {
            this.res_data = res_data;
        }

        public static class ResDataBean {
            /**
             * error : 数据提交异常
             */

            private String error;

            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }
        }
    }
}
