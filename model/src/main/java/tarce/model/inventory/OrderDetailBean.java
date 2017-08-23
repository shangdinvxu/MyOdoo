package tarce.model.inventory;


import java.io.Serializable;
import java.util.List;

/**
 * Created by rose.zou on 2017/5/22.
 * 生产订单详情
 */

public class OrderDetailBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"origin":"MO170514126:MO170514126","prepare_material_area_id":{"area_id":false,"area_name":false},"display_name":"MO170514127","product_id":{"product_ll_type":"semi-finished","area_id":{"area_id":false,"area_name":false},"product_id":66193,"product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2"},"production_order_type":"stockup","order_id":44420,"qty_produced":0,"cur_location":null,"stock_move_lines":[{"quantity_ready":1200,"virtual_available":0,"product_id":"[CY.0JP217.001] 冲压制程品-JP217-木板(松鼠)-2-1+2","suggest_qty":1236,"product_uom_qty":1200,"order_id":44420,"return_qty":0,"product_tmpl_id":46938,"product_type":"semi-finished","quantity_available":0,"qty_available":0,"quantity_done":1200,"id":74113,"over_picking_qty":0}],"state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检","is_rework":false},"bom_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2","feedback_on_rework":{"feedback_id":false,"name":null},"product_qty":1200,"in_charge_name":"陈小娟","date_planned_start":"2017-07-13 19:03:26","product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2","is_pending":false,"prepare_material_img":"http://192.168.88.128:8069/linkloving_app_api/get_worker_image?worker_id=44420&model=mrp.production&field=prepare_material_img"},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    private ErrorBean error;
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
         * res_data : {"origin":"MO170514126:MO170514126","prepare_material_area_id":{"area_id":false,"area_name":false},"display_name":"MO170514127","product_id":{"product_ll_type":"semi-finished","area_id":{"area_id":false,"area_name":false},"product_id":66193,"product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2"},"production_order_type":"stockup","order_id":44420,"qty_produced":0,"cur_location":null,"stock_move_lines":[{"quantity_ready":1200,"virtual_available":0,"product_id":"[CY.0JP217.001] 冲压制程品-JP217-木板(松鼠)-2-1+2","suggest_qty":1236,"product_uom_qty":1200,"order_id":44420,"return_qty":0,"product_tmpl_id":46938,"product_type":"semi-finished","quantity_available":0,"qty_available":0,"quantity_done":1200,"id":74113,"over_picking_qty":0}],"state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检","is_rework":false},"bom_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2","feedback_on_rework":{"feedback_id":false,"name":null},"product_qty":1200,"in_charge_name":"陈小娟","date_planned_start":"2017-07-13 19:03:26","product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2","is_pending":false,"prepare_material_img":"http://192.168.88.128:8069/linkloving_app_api/get_worker_image?worker_id=44420&model=mrp.production&field=prepare_material_img"}
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

        public static class ResDataBean implements Serializable{
            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            /**
             * origin : MO170514126:MO170514126
             * prepare_material_area_id : {"area_id":false,"area_name":false}
             * display_name : MO170514127
             * product_id : {"product_ll_type":"semi-finished","area_id":{"area_id":false,"area_name":false},"product_id":66193,"product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2"}
             * production_order_type : stockup
             * order_id : 44420
             * qty_produced : 0.0
             * cur_location : null
             * stock_move_lines : [{"quantity_ready":1200,"virtual_available":0,"product_id":"[CY.0JP217.001] 冲压制程品-JP217-木板(松鼠)-2-1+2","suggest_qty":1236,"product_uom_qty":1200,"order_id":44420,"return_qty":0,"product_tmpl_id":46938,"product_type":"semi-finished","quantity_available":0,"qty_available":0,"quantity_done":1200,"id":74113,"over_picking_qty":0}]
             * state : finish_prepare_material
             * process_id : {"process_id":8,"name":"全检","is_rework":false}
             * bom_name : [QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2
             * feedback_on_rework : {"feedback_id":false,"name":null}
             * product_qty : 1200.0
             * in_charge_name : 陈小娟
             * date_planned_start : 2017-07-13 19:03:26
             * product_name : [QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2
             * is_pending : false
             * prepare_material_img : http://192.168.88.128:8069/linkloving_app_api/get_worker_image?worker_id=44420&model=mrp.production&field=prepare_material_img
             */

            private String error;
            private String remark;

            public String getSop_file_url() {
                return sop_file_url;
            }

            public void setSop_file_url(String sop_file_url) {
                this.sop_file_url = sop_file_url;
            }

            private String sop_file_url;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSale_remark() {
                return sale_remark;
            }

            public void setSale_remark(String sale_remark) {
                this.sale_remark = sale_remark;
            }

            private String sale_remark;

            public String getBom_remark() {
                return bom_remark;
            }

            public void setBom_remark(String bom_remark) {
                this.bom_remark = bom_remark;
            }

            private String bom_remark;
            private String origin;
            private PrepareMaterialAreaIdBean prepare_material_area_id;
            private String display_name;
            private ProductIdBean product_id;

            public Object getProduction_order_type() {
                if (production_order_type instanceof String){
                    production_order_type = (String)production_order_type;
                }
                return production_order_type;
            }

            public void setProduction_order_type(Object production_order_type) {
                this.production_order_type = production_order_type;
            }

            private Object production_order_type;
            private int order_id;
            private double qty_produced;
            private Object cur_location;
            private String state;
            private ProcessIdBean process_id;
            private String bom_name;
            private FeedbackOnReworkBean feedback_on_rework;
            private double product_qty;
            private String in_charge_name;
            private String date_planned_start;
            private String product_name;
            private boolean is_pending;
            private String prepare_material_img;
            private List<StockMoveLinesBean> stock_move_lines;

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public PrepareMaterialAreaIdBean getPrepare_material_area_id() {
                return prepare_material_area_id;
            }

            public void setPrepare_material_area_id(PrepareMaterialAreaIdBean prepare_material_area_id) {
                this.prepare_material_area_id = prepare_material_area_id;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }

            public ProductIdBean getProduct_id() {
                return product_id;
            }

            public void setProduct_id(ProductIdBean product_id) {
                this.product_id = product_id;
            }


            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public double getQty_produced() {
                return qty_produced;
            }

            public void setQty_produced(double qty_produced) {
                this.qty_produced = qty_produced;
            }

            public Object getCur_location() {
                return cur_location;
            }

            public void setCur_location(Object cur_location) {
                this.cur_location = cur_location;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public ProcessIdBean getProcess_id() {
                return process_id;
            }

            public void setProcess_id(ProcessIdBean process_id) {
                this.process_id = process_id;
            }

            public String getBom_name() {
                return bom_name;
            }

            public void setBom_name(String bom_name) {
                this.bom_name = bom_name;
            }

            public FeedbackOnReworkBean getFeedback_on_rework() {
                return feedback_on_rework;
            }

            public void setFeedback_on_rework(FeedbackOnReworkBean feedback_on_rework) {
                this.feedback_on_rework = feedback_on_rework;
            }

            public double getProduct_qty() {
                return product_qty;
            }

            public void setProduct_qty(double product_qty) {
                this.product_qty = product_qty;
            }

            public String getIn_charge_name() {
                return in_charge_name;
            }

            public void setIn_charge_name(String in_charge_name) {
                this.in_charge_name = in_charge_name;
            }

            public String getDate_planned_start() {
                return date_planned_start;
            }

            public void setDate_planned_start(String date_planned_start) {
                this.date_planned_start = date_planned_start;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public boolean isIs_pending() {
                return is_pending;
            }

            public void setIs_pending(boolean is_pending) {
                this.is_pending = is_pending;
            }

            public String getPrepare_material_img() {
                return prepare_material_img;
            }

            public void setPrepare_material_img(String prepare_material_img) {
                this.prepare_material_img = prepare_material_img;
            }

            public List<StockMoveLinesBean> getStock_move_lines() {
                return stock_move_lines;
            }

            public void setStock_move_lines(List<StockMoveLinesBean> stock_move_lines) {
                this.stock_move_lines = stock_move_lines;
            }

            public static class PrepareMaterialAreaIdBean implements Serializable{
                /**
                 * area_id : false
                 * area_name : false
                 */

                private Object area_id;
                private Object area_name;

                public Object getArea_id() {
                    if (area_id instanceof Boolean){
                        area_id = 0;
                    }
                    return area_id;
                }

                public void setArea_id(Object area_id) {
                    this.area_id = area_id;
                }

                public Object getArea_name() {
                    if (area_name instanceof Boolean){
                        area_name = "0";
                    }
                    return area_name;
                }

                public void setArea_name(Object area_name) {
                    this.area_name = area_name;
                }
            }

            public static class ProductIdBean implements Serializable{
                /**
                 * product_ll_type : semi-finished
                 * area_id : {"area_id":false,"area_name":false}
                 * product_id : 66193
                 * product_name : [QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2
                 */

                private String product_ll_type;
                private AreaIdBean area_id;
                private int product_id;
                private String product_name;
                private Object product_specs;

                public Object getProduct_specs() {
                    if (product_specs instanceof Boolean){
                        return 0;
                    }
                    return product_specs;
                }

                public void setProduct_specs(String product_specs) {
                    this.product_specs = product_specs;
                }

                public String getProduct_ll_type() {
                    return product_ll_type;
                }

                public void setProduct_ll_type(String product_ll_type) {
                    this.product_ll_type = product_ll_type;
                }

                public AreaIdBean getArea_id() {
                    return area_id;
                }

                public void setArea_id(AreaIdBean area_id) {
                    this.area_id = area_id;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public static class AreaIdBean implements Serializable{
                    /**
                     * area_id : false
                     * area_name : false
                     */

                    private Object area_id;
                    private Object area_name;

                    public Object getArea_id() {
                        return area_id;
                    }

                    public void setArea_id(Object area_id) {
                        this.area_id = area_id;
                    }

                    public Object getArea_name() {
                        if (area_name instanceof Boolean){
                            area_name = "";
                        }
                        return area_name;
                    }

                    public void setArea_name(Object area_name) {
                        this.area_name = area_name;
                    }

                }
            }

            public static class ProcessIdBean implements Serializable{
                /**
                 * process_id : 8
                 * name : 全检
                 * is_rework : false
                 */

                private int process_id;
                private String name;
                private boolean is_rework;

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

                public boolean isIs_rework() {
                    return is_rework;
                }

                public void setIs_rework(boolean is_rework) {
                    this.is_rework = is_rework;
                }
            }

            public static class FeedbackOnReworkBean implements Serializable{
                public Object getFeedback_id() {
                    if (feedback_id instanceof Boolean){
                        return 0;
                    }
                    return feedback_id;
                }

                public void setFeedback_id(Object feedback_id){
                    this.feedback_id = feedback_id;
                }

                /**
                 * feedback_id : false
                 * name : null
                 */

                private Object feedback_id;
                private Object name;





                public Object getName() {
                    return name;
                }

                public void setName(Object name) {
                    this.name = name;
                }
            }

            public static class StockMoveLinesBean implements Serializable{
                /**
                 * quantity_ready : 1200.0
                 * virtual_available : 0.0
                 * product_id : [CY.0JP217.001] 冲压制程品-JP217-木板(松鼠)-2-1+2
                 * suggest_qty : 1236.0
                 * product_uom_qty : 1200.0
                 * order_id : 44420
                 * return_qty : 0.0
                 * product_tmpl_id : 46938
                 * product_type : semi-finished
                 * quantity_available : 0.0
                 * qty_available : 0.0
                 * quantity_done : 1200.0
                 * id : 74113
                 * over_picking_qty : 0.0
                 */
                private double quantity_ready;
                private double virtual_available;
                private String product_id;
                private double suggest_qty;
                private double product_uom_qty;
                private int order_id;
                private double return_qty;
                private int product_tmpl_id;
                private String product_type;
                private double quantity_available;
                private double qty_available;
                private double quantity_done;
                private int id;
                private double over_picking_qty;
                private boolean isBlue = false;

                public boolean isBlue() {
                    return isBlue;
                }

                public void setBlue(boolean blue) {
                    isBlue = blue;
                }

                public ProductIdBean.AreaIdBean getArea_id() {
                    return area_id;
                }

                public void setArea_id(ProductIdBean.AreaIdBean area_id) {
                    this.area_id = area_id;
                }

                private ProductIdBean.AreaIdBean  area_id;

                public double getQuantity_ready() {
                    return quantity_ready;
                }

                public void setQuantity_ready(double quantity_ready) {
                    this.quantity_ready = quantity_ready;
                }

                public double getVirtual_available() {
                    return virtual_available;
                }

                public void setVirtual_available(double virtual_available) {
                    this.virtual_available = virtual_available;
                }

                public String getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(String product_id) {
                    this.product_id = product_id;
                }

                public double getSuggest_qty() {
                    return suggest_qty;
                }

                public void setSuggest_qty(double suggest_qty) {
                    this.suggest_qty = suggest_qty;
                }

                public double getProduct_uom_qty() {
                    return product_uom_qty;
                }

                public void setProduct_uom_qty(double product_uom_qty) {
                    this.product_uom_qty = product_uom_qty;
                }

                public int getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(int order_id) {
                    this.order_id = order_id;
                }

                public double getReturn_qty() {
                    return return_qty;
                }

                public void setReturn_qty(double return_qty) {
                    this.return_qty = return_qty;
                }

                public int getProduct_tmpl_id() {
                    return product_tmpl_id;
                }

                public void setProduct_tmpl_id(int product_tmpl_id) {
                    this.product_tmpl_id = product_tmpl_id;
                }

                public String getProduct_type() {
                    return product_type;
                }

                public void setProduct_type(String product_type) {
                    this.product_type = product_type;
                }

                public double getQuantity_available() {
                    return quantity_available;
                }

                public void setQuantity_available(double quantity_available) {
                    this.quantity_available = quantity_available;
                }

                public double getQty_available() {
                    return qty_available;
                }

                public void setQty_available(double qty_available) {
                    this.qty_available = qty_available;
                }

                public double getQuantity_done() {
                    return quantity_done;
                }

                public void setQuantity_done(double quantity_done) {
                    this.quantity_done = quantity_done;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public double getOver_picking_qty() {
                    return over_picking_qty;
                }

                public void setOver_picking_qty(double over_picking_qty) {
                    this.over_picking_qty = over_picking_qty;
                }
            }
        }
    }
    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 223, in get_mrp_production\n    'display_name': production.display_name,\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 980, in determine_value\n    self.compute_value(recs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 935, in compute_value\n    self._compute_value(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 927, in _compute_value\n    getattr(records, self.compute)()\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 1513, in _compute_display_name\n    names = dict(self.name_get())\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 1532, in name_get\n    result.append((record.id, convert(record[name], record)))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3051, in _prefetch_field\n    result = records.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 2991, in read\n    self._read_from_database(stored, inherited)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3119, in _read_from_database\n    cr.execute(query_str, params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 141, in wrapper\n    return f(self, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 218, in execute\n    res = self._obj.execute(query, params)\nProgrammingError: column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n\n","exception_type":"internal_error","message":"column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n","name":"psycopg2.ProgrammingError","arguments":["column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n"]}
         */

        private String message;
        private int code;
        private PickingDetailBean.ErrorBean.DataBean data;

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

        public PickingDetailBean.ErrorBean.DataBean getData() {
            return data;
        }

        public void setData(PickingDetailBean.ErrorBean.DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * name : psycopg2.ProgrammingError
             * arguments : ["column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n"]
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
