package tarce.model;

import java.util.List;

/**
 * Created by zouwansheng on 2017/12/28.
 */

public class LineBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"line_id":8,"line_name":"压机-1"},{"line_id":9,"line_name":"压机-2"},{"line_id":10,"line_name":"压机-3"}],"res_msg":"","res_code":1}
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
         * res_data : [{"line_id":8,"line_name":"压机-1"},{"line_id":9,"line_name":"压机-2"},{"line_id":10,"line_name":"压机-3"}]
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
            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            /**
             * line_id : 8
             * line_name : 压机-1
             */


            private int position = -1;
            private int line_id;
            private String line_name;


            public int getLine_id() {
                return line_id;
            }

            public void setLine_id(int line_id) {
                this.line_id = line_id;
            }

            public String getLine_name() {
                return line_name;
            }

            public void setLine_name(String line_name) {
                this.line_name = line_name;
            }
        }
    }
}
