package tarce.model;

import java.util.List;

/**
 * Created by zouwansheng on 2017/12/19.
 */

public class SoOriginBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"origin_name":"","origin_count":5,"origin_id":0},{"origin_name":"SO1707103363","origin_count":2,"origin_id":4191},{"origin_name":"SO1706233157","origin_count":1,"origin_id":3985},{"origin_name":"SO1705262682","origin_count":11,"origin_id":3510},{"origin_name":"SO2017041901854","origin_count":1,"origin_id":2682}],"res_msg":"","res_code":1}
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
         * res_data : [{"origin_name":"","origin_count":5,"origin_id":0},{"origin_name":"SO1707103363","origin_count":2,"origin_id":4191},{"origin_name":"SO1706233157","origin_count":1,"origin_id":3985},{"origin_name":"SO1705262682","origin_count":11,"origin_id":3510},{"origin_name":"SO2017041901854","origin_count":1,"origin_id":2682}]
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
             * origin_name :
             * origin_count : 5
             * origin_id : 0
             */

            private String origin_name;
            private int origin_count;
            private int origin_id;
            private boolean have;

            public boolean isHave() {
                return have;
            }

            public void setHave(boolean have) {
                this.have = have;
            }

            public String getOrigin_name() {
                if (origin_name.equals("")){
                    origin_name = "暂无源单据";
                }
                return origin_name;
            }

            public void setOrigin_name(String origin_name) {
                this.origin_name = origin_name;
            }

            public int getOrigin_count() {
                return origin_count;
            }

            public void setOrigin_count(int origin_count) {
                this.origin_count = origin_count;
            }

            public int getOrigin_id() {
                return origin_id;
            }

            public void setOrigin_id(int origin_id) {
                this.origin_id = origin_id;
            }
        }
    }
}
