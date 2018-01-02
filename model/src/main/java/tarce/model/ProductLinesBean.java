package tarce.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouwansheng on 2017/10/18.
 */

public class ProductLinesBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"production_line_id":[11,"全检-1号"],"__domain":["&",["production_line_id","=",11],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":1},{"production_line_id":[12,"全检-2号"],"__domain":["&",["production_line_id","=",12],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":2},{"production_line_id":[13,"全检-3号"],"__domain":["&",["production_line_id","=",13],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":1},{"production_line_id":[17,"全检-7号"],"__domain":["&",["production_line_id","=",17],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":1}],"res_msg":"","res_code":1}
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
         * res_data : [{"production_line_id":[11,"全检-1号"],"__domain":["&",["production_line_id","=",11],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":1},{"production_line_id":[12,"全检-2号"],"__domain":["&",["production_line_id","=",12],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":2},{"production_line_id":[13,"全检-3号"],"__domain":["&",["production_line_id","=",13],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":1},{"production_line_id":[17,"全检-7号"],"__domain":["&",["production_line_id","=",17],"|",["in_charge_id","=",2117],["create_uid","=",2117],["state","=","progress"],["feedback_on_rework","=",null],["process_id","=",8],["production_line_id","!=",false]],"production_line_id_count":1}]
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
             * production_line_id : [11,"全检-1号"]
             * production_line_id_count : 1
             */

            private int position = -1;

            private int production_line_id_count;
            private Object production_line_id;

            public int getProduction_line_id_count() {
                return production_line_id_count;
            }

            public void setProduction_line_id_count(int production_line_id_count) {
                this.production_line_id_count = production_line_id_count;
            }

            public Object getProduction_line_id() {
                if (production_line_id instanceof Boolean){
                    List<Double> integerList = new ArrayList<>();
                    integerList.add(-1000.0);
                    integerList.add(1000.0);
                    production_line_id = integerList;
                }else if (production_line_id instanceof List){
                    production_line_id = (List<Object>)production_line_id;
                }
                return production_line_id;
            }

            public void setProduction_line_id(Object production_line_id) {
                this.production_line_id = production_line_id;
            }
        }
    }
}
