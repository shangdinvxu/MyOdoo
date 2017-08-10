package tarce.model;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

import java.io.Serializable;
import java.util.List;


/***
 *
 * Json To JavaBean
 * @author www.json123.com
 *
 */
public class GetSaleResponse implements Serializable{

    /**
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 2124, in change_stock_picking_state\n    map(x, pack_list, qty_done_map)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 2122, in x\n    a.qty_done = b\nAttributeError: 'NoneType' object has no attribute 'qty_done'\n","exception_type":"internal_error","message":"'NoneType' object has no attribute 'qty_done'","name":"exceptions.AttributeError","arguments":["'NoneType' object has no attribute 'qty_done'"]}}
     */

    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class TResult implements Serializable{

        public static class TRes_data implements Serializable{


            public static class TPost_area_id implements Serializable{

                private	Integer	area_id;	/*210*/
                private	String	area_name;	/*A3C2*/

                public void setArea_id(Integer value){
                    this.area_id = value;
                }
                public Integer getArea_id(){
                    return this.area_id;
                }

                public void setArea_name(String value){
                    this.area_name = value;
                }
                public String getArea_name(){
                    return this.area_name;
                }

            }
            public static class TPack_operation_product_ids implements Serializable{

                public static class TProduct_id implements Serializable{

                    public static class TArea_id implements Serializable{

                        private	Integer	area_id;	/*210*/
                        private	String	area_name;	/*A3C2*/

                        public void setArea_id(Integer value){
                            this.area_id = value;
                        }
                        public Integer getArea_id(){
                            return this.area_id;
                        }

                        public void setArea_name(String value){
                            this.area_name = value;
                        }
                        public String getArea_name(){
                            return this.area_name;
                        }

                    }
                    private	Integer	id;	/*49457*/
                    private	TArea_id	area_id;	/*TArea_id*/
                    private	Integer	qty_available;	/*280*/
                    private	String	name;	/*[47.1PZ005.000] PZ1005-三角彩盒(世界地图)-RT-CN*/
                    private	String	default_code;	/*47.1PZ005.000*/

                    public void setId(Integer value){
                        this.id = value;
                    }
                    public Integer getId(){
                        return this.id;
                    }

                    public void setArea_id(TArea_id value){
                        this.area_id = value;
                    }
                    public TArea_id getArea_id(){
                        return this.area_id;
                    }

                    public void setQty_available(Integer value){
                        this.qty_available = value;
                    }
                    public Integer getQty_available(){
                        return this.qty_available;
                    }

                    public void setName(String value){
                        this.name = value;
                    }
                    public String getName(){
                        return this.name;
                    }

                    public void setDefault_code(String value){
                        this.default_code = value;
                    }
                    public String getDefault_code(){
                        return this.default_code;
                    }

                }
                private	Integer	product_qty;	/*900*/
                private	TProduct_id	product_id;	/*TProduct_id*/
                private	Integer	pack_id;	/*2788*/
                private	Integer	qty_done;/*900*/
                private double reserved_qty;

                public double getReserved_qty() {
                    return reserved_qty;
                }

                public void setReserved_qty(double reserved_qty) {
                    this.reserved_qty = reserved_qty;
                }

                public int getOrigin_qty() {
                    return origin_qty;
                }

                public void setOrigin_qty(int origin_qty) {
                    this.origin_qty = origin_qty;
                }

                private int origin_qty;

                public void setProduct_qty(Integer value){
                    this.product_qty = value;
                }
                public Integer getProduct_qty(){
                    return this.product_qty;
                }

                public void setProduct_id(TProduct_id value){
                    this.product_id = value;
                }
                public TProduct_id getProduct_id(){
                    return this.product_id;
                }

                public void setPack_id(Integer value){
                    this.pack_id = value;
                }
                public Integer getPack_id(){
                    return this.pack_id;
                }

                public void setQty_done(Integer value){
                    this.qty_done = value;
                }
                public Integer getQty_done(){
                    return this.qty_done;
                }

            }
            private	List<TPack_operation_product_ids>	pack_operation_product_ids;	/*List<TPack_operation_product_ids>*/
            public void setPack_operation_product_ids(List<TPack_operation_product_ids> value){
                this.pack_operation_product_ids = value;
            }
            public List<TPack_operation_product_ids> getPack_operation_product_ids(){
                return this.pack_operation_product_ids;
            }

            private	Integer	complete_rate;	/*0*/
            private	TPost_area_id	post_area_id;	/*TPost_area_id*/
            private	String	origin;	/*PO2016121401111*/

            private	Object	sale_note;	/*false*/
            private	String	state;	/*done*/
            private	Integer	picking_id;	/*781*/
            private	String	min_date;	/*2016-12-14 06:59:24*/

            public Object getDelivery_rule() {
                if (delivery_rule instanceof String){
                    delivery_rule = String.valueOf(delivery_rule);
                }
                return delivery_rule;
            }

            public void setDelivery_rule(Object delivery_rule) {
                this.delivery_rule = delivery_rule;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            private String phone;
            private	Object	delivery_rule;	/*Object*/
            private	String	name;	/*WHIN2016121500693*/
            private	Boolean	qc_note;	/*false*/
            private	String	parnter_id;	/*海盐宏胜包装材料厂*/
            private	String	picking_type_code;	/*incoming*/
            private	String	post_img;	/*http://192.168.88.106:8069/linkloving_app_api/get_worker_image?worker_id=781&model=stock.picking&field=post_img*/
            private	String	qc_img;	/*http://192.168.88.106:8069/linkloving_app_api/get_worker_image?worker_id=781&model=stock.picking&field=qc_img*/


            public void setComplete_rate(Integer value){
                this.complete_rate = value;
            }
            public Integer getComplete_rate(){
                return this.complete_rate;
            }

            public void setPost_area_id(TPost_area_id value){
                this.post_area_id = value;
            }
            public TPost_area_id getPost_area_id(){
                return this.post_area_id;
            }

            public void setOrigin(String value){
                this.origin = value;
            }
            public String getOrigin(){
                return this.origin;
            }

            public Object getSale_note() {
                if (sale_note instanceof Boolean){
                    sale_note = "";
                }
                return sale_note;
            }

            public void setSale_note(Object sale_note) {
                this.sale_note = sale_note;
            }

            public void setState(String value){
                this.state = value;
            }
            public String getState(){
                return this.state;
            }

            public void setPicking_id(Integer value){
                this.picking_id = value;
            }
            public Integer getPicking_id(){
                return this.picking_id;
            }

            public void setMin_date(String value){
                this.min_date = value;
            }
            public String getMin_date(){
                return this.min_date;
            }


            public void setName(String value){
                this.name = value;
            }
            public String getName(){
                return this.name;
            }

            public void setQc_note(Boolean value){
                this.qc_note = value;
            }
            public Boolean getQc_note(){
                return this.qc_note;
            }

            public void setParnter_id(String value){
                this.parnter_id = value;
            }
            public String getParnter_id(){
                return this.parnter_id;
            }

            public void setPicking_type_code(String value){
                this.picking_type_code = value;
            }
            public String getPicking_type_code(){
                return this.picking_type_code;
            }

            public void setPost_img(String value){
                this.post_img = value;
            }
            public String getPost_img(){
                return this.post_img;
            }

            public void setQc_img(String value){
                this.qc_img = value;
            }
            public String getQc_img(){
                return this.qc_img;
            }

            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            private String error;

        }
        private	TRes_data	res_data;	/*TRes_data*/
        private	Integer	res_code;	/*1*/
        private	String	res_msg;	/**/

        public void setRes_data(TRes_data value){
            this.res_data = value;
        }
        public TRes_data getRes_data(){
            return this.res_data;
        }

        public void setRes_code(Integer value){
            this.res_code = value;
        }
        public Integer getRes_code(){
            return this.res_code;
        }

        public void setRes_msg(String value){
            this.res_msg = value;
        }
        public String getRes_msg(){
            return this.res_msg;
        }

    }
    private	Object	id;	/*Object*/
    private	TResult	result;	/*TResult*/
    private	String	jsonrpc;	/*2.0*/

    public void setId(Object value){
        this.id = value;
    }
    public Object getId(){
        return this.id;
    }

    public void setResult(TResult value){
        this.result = value;
    }
    public TResult getResult(){
        return this.result;
    }

    public void setJsonrpc(String value){
        this.jsonrpc = value;
    }
    public String getJsonrpc(){
        return this.jsonrpc;
    }

    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 2124, in change_stock_picking_state\n    map(x, pack_list, qty_done_map)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 2122, in x\n    a.qty_done = b\nAttributeError: 'NoneType' object has no attribute 'qty_done'\n","exception_type":"internal_error","message":"'NoneType' object has no attribute 'qty_done'","name":"exceptions.AttributeError","arguments":["'NoneType' object has no attribute 'qty_done'"]}
         */

        private String message;
        private int code;
        private DataBean data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * debug : Traceback (most recent call last):
             * exception_type : internal_error
             * message : 'NoneType' object has no attribute 'qty_done'
             * name : exceptions.AttributeError
             * arguments : ["'NoneType' object has no attribute 'qty_done'"]
             */

            private String debug;
            private String exception_type;
            private String message;
            private String name;
            private List<String> arguments;

            public String getDebug() {
                return debug;
            }

            public void setDebug(String debug) {
                this.debug = debug;
            }

            public String getException_type() {
                return exception_type;
            }

            public void setException_type(String exception_type) {
                this.exception_type = exception_type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getArguments() {
                return arguments;
            }

            public void setArguments(List<String> arguments) {
                this.arguments = arguments;
            }
        }
    }
}

