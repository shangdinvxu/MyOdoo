package tarce.model.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by zouwansheng on 2017/11/11.
 */

public class OutsourceBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"create_uid":[190,"张秀"],"create_date":"2017-11-11 01:00:08","write_date":"2017-11-11 01:00:08","__last_update":"2017-11-11 01:00:08","qty_produced":1,"company_id":[1,"若贝尔科技沭阳有限公司"],"write_uid":[190,"张秀"],"state":"draft","product_id":[45436,"[67.0D2200.001] 塑封半成品-D220木板(声控头部霸王龙)"],"production_id":[88268,"MO170915037"],"display_name":"OSPO/201711110004","id":12,"name":"OSPO/201711110004"},{"create_uid":[190,"张秀"],"create_date":"2017-11-11 01:18:25","write_date":"2017-11-11 01:22:03","__last_update":"2017-11-11 01:22:03","qty_produced":20,"company_id":[1,"若贝尔科技沭阳有限公司"],"write_uid":[1,"Administrator"],"state":"draft","product_id":[47259,"[99.0JP236.001] JP236-成品(防爆装甲车){RT-CN}"],"production_id":[88050,"MO170913214"],"display_name":"OSPO/201711110005","id":13,"name":"OSPO/201711110005"}],"res_msg":"","res_code":1}
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
         * res_data : [{"create_uid":[190,"张秀"],"create_date":"2017-11-11 01:00:08","write_date":"2017-11-11 01:00:08","__last_update":"2017-11-11 01:00:08","qty_produced":1,"company_id":[1,"若贝尔科技沭阳有限公司"],"write_uid":[190,"张秀"],"state":"draft","product_id":[45436,"[67.0D2200.001] 塑封半成品-D220木板(声控头部霸王龙)"],"production_id":[88268,"MO170915037"],"display_name":"OSPO/201711110004","id":12,"name":"OSPO/201711110004"},{"create_uid":[190,"张秀"],"create_date":"2017-11-11 01:18:25","write_date":"2017-11-11 01:22:03","__last_update":"2017-11-11 01:22:03","qty_produced":20,"company_id":[1,"若贝尔科技沭阳有限公司"],"write_uid":[1,"Administrator"],"state":"draft","product_id":[47259,"[99.0JP236.001] JP236-成品(防爆装甲车){RT-CN}"],"production_id":[88050,"MO170913214"],"display_name":"OSPO/201711110005","id":13,"name":"OSPO/201711110005"}]
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

        public static class ResDataBean implements Serializable{
            public Object getPo_user_id() {
                if (po_user_id instanceof Boolean){
                    po_user_id = "暂无";
                }
                return po_user_id;
            }

            public void setPo_user_id(Object po_user_id) {
                this.po_user_id = po_user_id;
            }

            public List<Object> getOutsourcing_supplier_id() {
                return outsourcing_supplier_id;
            }

            public void setOutsourcing_supplier_id(List<Object> outsourcing_supplier_id) {
                this.outsourcing_supplier_id = outsourcing_supplier_id;
            }


            public List<Object> getProcess_id() {
                return process_id;
            }

            public void setProcess_id(List<Object> process_id) {
                this.process_id = process_id;
            }

            public Object getEmployee_id() {
                if (employee_id instanceof Boolean){
                    List<String> integerList = new ArrayList<>();
                    integerList.add("");
                    integerList.add("");
                    employee_id = integerList;
                }
                return employee_id;
            }

            public void setEmployee_id(Object employee_id) {
                this.employee_id = employee_id;
            }

            /**
             * create_uid : [190,"张秀"]
             * create_date : 2017-11-11 01:00:08
             * write_date : 2017-11-11 01:00:08
             * __last_update : 2017-11-11 01:00:08
             * qty_produced : 1.0
             * company_id : [1,"若贝尔科技沭阳有限公司"]
             * write_uid : [190,"张秀"]
             * state : draft
             * product_id : [45436,"[67.0D2200.001] 塑封半成品-D220木板(声控头部霸王龙)"]
             * production_id : [88268,"MO170915037"]
             * display_name : OSPO/201711110004
             * id : 12
             * name : OSPO/201711110004
             */

            private List<Object> process_id;
            private Object employee_id;
            private List<Object> outsourcing_supplier_id;
            private Object po_user_id;
            private String create_date;
            private String write_date;
            private String __last_update;
            private double qty_produced;
            private String state;
            private String display_name;
            private int id;
            private String name;
            private List<Object> create_uid;
            private List<Object> company_id;
            private List<Object> write_uid;
            private List<Object> product_id;
            private List<Object> production_id;

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getWrite_date() {
                return write_date;
            }

            public void setWrite_date(String write_date) {
                this.write_date = write_date;
            }

            public String get__last_update() {
                return __last_update;
            }

            public void set__last_update(String __last_update) {
                this.__last_update = __last_update;
            }

            public double getQty_produced() {
                return qty_produced;
            }

            public void setQty_produced(double qty_produced) {
                this.qty_produced = qty_produced;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<Object> getCreate_uid() {
                return create_uid;
            }

            public void setCreate_uid(List<Object> create_uid) {
                this.create_uid = create_uid;
            }

            public List<Object> getCompany_id() {
                return company_id;
            }

            public void setCompany_id(List<Object> company_id) {
                this.company_id = company_id;
            }

            public List<Object> getWrite_uid() {
                return write_uid;
            }

            public void setWrite_uid(List<Object> write_uid) {
                this.write_uid = write_uid;
            }

            public List<Object> getProduct_id() {
                return product_id;
            }

            public void setProduct_id(List<Object> product_id) {
                this.product_id = product_id;
            }

            public List<Object> getProduction_id() {
                return production_id;
            }

            public void setProduction_id(List<Object> production_id) {
                this.production_id = production_id;
            }
        }
    }
}
