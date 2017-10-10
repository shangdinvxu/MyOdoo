package tarce.model.inventory;

import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by rose.zou on 2017/6/7.
 * 提交备料信息
 */

public class FinishPrepareMaBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"origin":false,"cur_location":null,"product_qty":400,"display_name":"MO170524093","prepare_material_area_id":{"area_id":82,"area_name":"A1A1"},"state":"finish_prepare_material","in_charge_name":"张秀","product_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG","order_id":47870,"qty_produced":0,"stock_move_lines":[{"quantity_ready":0,"area_id":{"area_id":251,"area_name":"A3N3"},"virtual_available":30749,"product_id":"[41.200000.013] POF袋[280*210]","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":49345,"product_type":"material","quantity_available":0,"qty_available":73974,"quantity_done":400,"id":86792,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":222,"area_name":"A3F2"},"virtual_available":300,"product_id":"[49.1F1230.100] F123-说明书(日本甜品店)-RT-ENG","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":46083,"product_type":"material","quantity_available":0,"qty_available":700,"quantity_done":400,"id":86793,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":222,"area_name":"A3F2"},"virtual_available":163921,"product_id":"[40.1F1XX0.001] PET[80x80mmx22丝]","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":48901,"product_type":"material","quantity_available":0,"qty_available":202752,"quantity_done":400,"id":86794,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":108222.00200000001,"product_id":"[CY.100000.001] 砂纸[小]冲压制成品","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":50611,"product_type":"semi-finished","quantity_available":400,"qty_available":88846,"quantity_done":0,"id":86795,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":30,"product_id":"[QJ.0F1230.001] 全检制程品-F123(日本甜品店)-RT-3-1+2","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":61239,"product_type":"semi-finished","quantity_available":400,"qty_available":830,"quantity_done":0,"id":86796,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":218,"area_name":"A3E2"},"virtual_available":2230,"product_id":"[43.3F1230.001] F123-剪纸(日本甜品店)-RT","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":46072,"product_type":"material","quantity_available":0,"qty_available":2630,"quantity_done":400,"id":86797,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":0,"product_id":"[QJ.0F1230.003] 全检制程品-F123(日本甜品店)-RT-3-3","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":61242,"product_type":"semi-finished","quantity_available":400,"qty_available":800,"quantity_done":0,"id":86798,"over_picking_qty":0}],"process_id":{"process_id":7,"name":"静态包装","is_rework":false},"bom_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG","prepare_material_img":"http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=47870&model=mrp.production&field=prepare_material_img","remark":"","sale_remark":"","date_planned_start":"2017-05-24 11:48:52","product_id":{"product_ll_type":"semi-finished","area_id":{"area_id":false,"area_name":false},"product_specs":false,"product_id":61244,"product_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG"},"production_order_type":"stockup","feedback_on_rework":{"feedback_id":false,"name":null},"is_bom_update":false,"is_pending":false},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;
    /**
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/opt/odoo/odoo10/odoo/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/opt/odoo/odoo10/odoo/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 2839, in new_finish_prepare_material\n    'picking_material_date': fields.datetime.now()})\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 282, in write\n    track_self.message_track(tracked_fields, initial_values)\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 485, in message_track\n    tracking = self._message_track_get_changes(tracked_fields, initial_values)\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 435, in _message_track_get_changes\n    result[record.id] = record._message_track(tracked_fields, initial_values[record.id])\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 455, in _message_track\n    new_value = getattr(self, col_name)\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 3054, in _prefetch_field\n    result = self.read([f.name for f in fs], load='_classic_write')\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 3002, in read\n    values[name] = field.convert_to_read(record[name], record, use_name_get)\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 865, in __get__\n    value = record._cache[self]\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 5526, in __getitem__\n    return value.get() if isinstance(value, SpecialValue) else value\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 48, in get\n    raise self.exception\nAccessError: (u'\\u7531\\u4e8e\\u5b89\\u5168\\u9650\\u5236\\uff0c\\u8bf7\\u6c42\\u7684\\u64cd\\u4f5c\\u65e0\\u6cd5\\u5b8c\\u6210\\u3002\\u8bf7\\u8054\\u7cfb\\u4f60\\u7684\\u7cfb\\u7edf\\u7ba1\\u7406\\u5458\\u3002\\n\\n(\\u5355\\u636e\\u7c7b\\u578b: mrp.production, \\u64cd\\u4f5c: read)', None)\n","exception_type":"access_error","message":"由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)\nNone","name":"odoo.exceptions.AccessError","arguments":["由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)",null]}}
     */

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

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ResultBean {
        /**
         * res_data : {"origin":false,"cur_location":null,"product_qty":400,"display_name":"MO170524093","prepare_material_area_id":{"area_id":82,"area_name":"A1A1"},"state":"finish_prepare_material","in_charge_name":"张秀","product_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG","order_id":47870,"qty_produced":0,"stock_move_lines":[{"quantity_ready":0,"area_id":{"area_id":251,"area_name":"A3N3"},"virtual_available":30749,"product_id":"[41.200000.013] POF袋[280*210]","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":49345,"product_type":"material","quantity_available":0,"qty_available":73974,"quantity_done":400,"id":86792,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":222,"area_name":"A3F2"},"virtual_available":300,"product_id":"[49.1F1230.100] F123-说明书(日本甜品店)-RT-ENG","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":46083,"product_type":"material","quantity_available":0,"qty_available":700,"quantity_done":400,"id":86793,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":222,"area_name":"A3F2"},"virtual_available":163921,"product_id":"[40.1F1XX0.001] PET[80x80mmx22丝]","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":48901,"product_type":"material","quantity_available":0,"qty_available":202752,"quantity_done":400,"id":86794,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":108222.00200000001,"product_id":"[CY.100000.001] 砂纸[小]冲压制成品","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":50611,"product_type":"semi-finished","quantity_available":400,"qty_available":88846,"quantity_done":0,"id":86795,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":30,"product_id":"[QJ.0F1230.001] 全检制程品-F123(日本甜品店)-RT-3-1+2","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":61239,"product_type":"semi-finished","quantity_available":400,"qty_available":830,"quantity_done":0,"id":86796,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":218,"area_name":"A3E2"},"virtual_available":2230,"product_id":"[43.3F1230.001] F123-剪纸(日本甜品店)-RT","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":46072,"product_type":"material","quantity_available":0,"qty_available":2630,"quantity_done":400,"id":86797,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":0,"product_id":"[QJ.0F1230.003] 全检制程品-F123(日本甜品店)-RT-3-3","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":61242,"product_type":"semi-finished","quantity_available":400,"qty_available":800,"quantity_done":0,"id":86798,"over_picking_qty":0}],"process_id":{"process_id":7,"name":"静态包装","is_rework":false},"bom_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG","prepare_material_img":"http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=47870&model=mrp.production&field=prepare_material_img","remark":"","sale_remark":"","date_planned_start":"2017-05-24 11:48:52","product_id":{"product_ll_type":"semi-finished","area_id":{"area_id":false,"area_name":false},"product_specs":false,"product_id":61244,"product_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG"},"production_order_type":"stockup","feedback_on_rework":{"feedback_id":false,"name":null},"is_bom_update":false,"is_pending":false}
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
            public Object getOrigin() {
                return origin;
            }

            public void setOrigin(Object origin) {
                this.origin = origin;
            }

            /**
             * origin : false
             * cur_location : null
             * product_qty : 400.0
             * display_name : MO170524093
             * prepare_material_area_id : {"area_id":82,"area_name":"A1A1"}
             * state : finish_prepare_material
             * in_charge_name : 张秀
             * product_name : [67.0F1230.100] F123-木板(日本甜品店)-RT-ENG
             * order_id : 47870
             * qty_produced : 0.0
             * stock_move_lines : [{"quantity_ready":0,"area_id":{"area_id":251,"area_name":"A3N3"},"virtual_available":30749,"product_id":"[41.200000.013] POF袋[280*210]","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":49345,"product_type":"material","quantity_available":0,"qty_available":73974,"quantity_done":400,"id":86792,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":222,"area_name":"A3F2"},"virtual_available":300,"product_id":"[49.1F1230.100] F123-说明书(日本甜品店)-RT-ENG","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":46083,"product_type":"material","quantity_available":0,"qty_available":700,"quantity_done":400,"id":86793,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":222,"area_name":"A3F2"},"virtual_available":163921,"product_id":"[40.1F1XX0.001] PET[80x80mmx22丝]","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":48901,"product_type":"material","quantity_available":0,"qty_available":202752,"quantity_done":400,"id":86794,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":108222.00200000001,"product_id":"[CY.100000.001] 砂纸[小]冲压制成品","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":50611,"product_type":"semi-finished","quantity_available":400,"qty_available":88846,"quantity_done":0,"id":86795,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":30,"product_id":"[QJ.0F1230.001] 全检制程品-F123(日本甜品店)-RT-3-1+2","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":61239,"product_type":"semi-finished","quantity_available":400,"qty_available":830,"quantity_done":0,"id":86796,"over_picking_qty":0},{"quantity_ready":0,"area_id":{"area_id":218,"area_name":"A3E2"},"virtual_available":2230,"product_id":"[43.3F1230.001] F123-剪纸(日本甜品店)-RT","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":46072,"product_type":"material","quantity_available":0,"qty_available":2630,"quantity_done":400,"id":86797,"over_picking_qty":0},{"quantity_ready":0,"virtual_available":0,"product_id":"[QJ.0F1230.003] 全检制程品-F123(日本甜品店)-RT-3-3","suggest_qty":412,"product_uom_qty":400,"order_id":47870,"return_qty":0,"product_tmpl_id":61242,"product_type":"semi-finished","quantity_available":400,"qty_available":800,"quantity_done":0,"id":86798,"over_picking_qty":0}]
             * process_id : {"process_id":7,"name":"静态包装","is_rework":false}
             * bom_name : [67.0F1230.100] F123-木板(日本甜品店)-RT-ENG
             * prepare_material_img : http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=47870&model=mrp.production&field=prepare_material_img
             * remark :
             * sale_remark :
             * date_planned_start : 2017-05-24 11:48:52
             * product_id : {"product_ll_type":"semi-finished","area_id":{"area_id":false,"area_name":false},"product_specs":false,"product_id":61244,"product_name":"[67.0F1230.100] F123-木板(日本甜品店)-RT-ENG"}
             * production_order_type : stockup
             * feedback_on_rework : {"feedback_id":false,"name":null}
             * is_bom_update : false
             * is_pending : false
             */

            private Object origin;
            private Object cur_location;
            private double product_qty;
            private String display_name;
            private PrepareMaterialAreaIdBean prepare_material_area_id;
            private String state;
            private String in_charge_name;
            private String product_name;
            private int order_id;
            private double qty_produced;
            private ProcessIdBean process_id;
            private String bom_name;
            private String prepare_material_img;
            private String remark;
            private String sale_remark;
            private String date_planned_start;
            private ProductIdBean product_id;
            private String production_order_type;
            private FeedbackOnReworkBean feedback_on_rework;
            private boolean is_bom_update;
            private boolean is_pending;
            private List<StockMoveLinesBean> stock_move_lines;



            public Object getCur_location() {
                return cur_location;
            }

            public void setCur_location(Object cur_location) {
                this.cur_location = cur_location;
            }

            public double getProduct_qty() {
                return product_qty;
            }

            public void setProduct_qty(double product_qty) {
                this.product_qty = product_qty;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }

            public PrepareMaterialAreaIdBean getPrepare_material_area_id() {
                return prepare_material_area_id;
            }

            public void setPrepare_material_area_id(PrepareMaterialAreaIdBean prepare_material_area_id) {
                this.prepare_material_area_id = prepare_material_area_id;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getIn_charge_name() {
                return in_charge_name;
            }

            public void setIn_charge_name(String in_charge_name) {
                this.in_charge_name = in_charge_name;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
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

            public String getPrepare_material_img() {
                return prepare_material_img;
            }

            public void setPrepare_material_img(String prepare_material_img) {
                this.prepare_material_img = prepare_material_img;
            }

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

            public String getDate_planned_start() {
                return date_planned_start;
            }

            public void setDate_planned_start(String date_planned_start) {
                this.date_planned_start = date_planned_start;
            }

            public ProductIdBean getProduct_id() {
                return product_id;
            }

            public void setProduct_id(ProductIdBean product_id) {
                this.product_id = product_id;
            }

            public String getProduction_order_type() {
                return production_order_type;
            }

            public void setProduction_order_type(String production_order_type) {
                this.production_order_type = production_order_type;
            }

            public FeedbackOnReworkBean getFeedback_on_rework() {
                return feedback_on_rework;
            }

            public void setFeedback_on_rework(FeedbackOnReworkBean feedback_on_rework) {
                this.feedback_on_rework = feedback_on_rework;
            }

            public boolean isIs_bom_update() {
                return is_bom_update;
            }

            public void setIs_bom_update(boolean is_bom_update) {
                this.is_bom_update = is_bom_update;
            }

            public boolean isIs_pending() {
                return is_pending;
            }

            public void setIs_pending(boolean is_pending) {
                this.is_pending = is_pending;
            }

            public List<StockMoveLinesBean> getStock_move_lines() {
                return stock_move_lines;
            }

            public void setStock_move_lines(List<StockMoveLinesBean> stock_move_lines) {
                this.stock_move_lines = stock_move_lines;
            }

            public static class PrepareMaterialAreaIdBean {
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
                        area_name = "";
                    }
                    return area_name;
                }

                public void setArea_name(Object area_name) {
                    this.area_name = area_name;
                }
            }

            public static class ProcessIdBean {
                /**
                 * process_id : 7
                 * name : 静态包装
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

            public static class ProductIdBean {
                /**
                 * product_ll_type : semi-finished
                 * area_id : {"area_id":false,"area_name":false}
                 * product_specs : false
                 * product_id : 61244
                 * product_name : [67.0F1230.100] F123-木板(日本甜品店)-RT-ENG
                 */

                private String product_ll_type;
                private AreaIdBean area_id;

                public Object getProduct_specs() {
                    return product_specs;
                }

                public void setProduct_specs(Object product_specs) {
                    this.product_specs = product_specs;
                }

                private Object product_specs;
                private Object product_id;
                private String product_name;

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

                public Object getProduct_id() {
                    if (product_id instanceof Boolean){
                        product_id = 0;
                    }
                    return product_id;
                }

                public void setProduct_id(Object product_id) {
                    this.product_id = product_id;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public static class AreaIdBean {
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

                    /**
                     * area_id : false
                     * area_name : false
                     */

                    private Object area_id;
                    private Object area_name;


                }
            }

            public static class FeedbackOnReworkBean {
                public Object getFeedback_id() {
                    if (feedback_id instanceof Boolean){
                        feedback_id = 0;
                    }
                    return feedback_id;
                }

                public void setFeedback_id(Object feedback_id) {
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

            public static class StockMoveLinesBean {
                /**
                 * quantity_ready : 0.0
                 * area_id : {"area_id":251,"area_name":"A3N3"}
                 * virtual_available : 30749.0
                 * product_id : [41.200000.013] POF袋[280*210]
                 * suggest_qty : 412.0
                 * product_uom_qty : 400.0
                 * order_id : 47870
                 * return_qty : 0.0
                 * product_tmpl_id : 49345
                 * product_type : material
                 * quantity_available : 0.0
                 * qty_available : 73974.0
                 * quantity_done : 400.0
                 * id : 86792
                 * over_picking_qty : 0.0
                 */

                private double quantity_ready;
                private AreaIdBeanX area_id;
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

                public double getQuantity_ready() {
                    return quantity_ready;
                }

                public void setQuantity_ready(double quantity_ready) {
                    this.quantity_ready = quantity_ready;
                }

                public AreaIdBeanX getArea_id() {
                    return area_id;
                }

                public void setArea_id(AreaIdBeanX area_id) {
                    this.area_id = area_id;
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

                public static class AreaIdBeanX {
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

                    /**
                     * area_id : false
                     * area_name : false
                     */

                    private Object area_id;
                    private Object area_name;
                }
            }
        }
    }

    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/opt/odoo/odoo10/odoo/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/opt/odoo/odoo10/odoo/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/opt/odoo/odoo10/odoo/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 2839, in new_finish_prepare_material\n    'picking_material_date': fields.datetime.now()})\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 282, in write\n    track_self.message_track(tracked_fields, initial_values)\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 485, in message_track\n    tracking = self._message_track_get_changes(tracked_fields, initial_values)\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 435, in _message_track_get_changes\n    result[record.id] = record._message_track(tracked_fields, initial_values[record.id])\n  File \"/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py\", line 455, in _message_track\n    new_value = getattr(self, col_name)\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 3054, in _prefetch_field\n    result = self.read([f.name for f in fs], load='_classic_write')\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 3002, in read\n    values[name] = field.convert_to_read(record[name], record, use_name_get)\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 865, in __get__\n    value = record._cache[self]\n  File \"/opt/odoo/odoo10/odoo/odoo/models.py\", line 5526, in __getitem__\n    return value.get() if isinstance(value, SpecialValue) else value\n  File \"/opt/odoo/odoo10/odoo/odoo/fields.py\", line 48, in get\n    raise self.exception\nAccessError: (u'\\u7531\\u4e8e\\u5b89\\u5168\\u9650\\u5236\\uff0c\\u8bf7\\u6c42\\u7684\\u64cd\\u4f5c\\u65e0\\u6cd5\\u5b8c\\u6210\\u3002\\u8bf7\\u8054\\u7cfb\\u4f60\\u7684\\u7cfb\\u7edf\\u7ba1\\u7406\\u5458\\u3002\\n\\n(\\u5355\\u636e\\u7c7b\\u578b: mrp.production, \\u64cd\\u4f5c: read)', None)\n","exception_type":"access_error","message":"由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)\nNone","name":"odoo.exceptions.AccessError","arguments":["由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)",null]}
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
             File "/opt/odoo/odoo10/odoo/odoo/http.py", line 638, in _handle_exception
             return super(JsonRequest, self)._handle_exception(exception)
             File "/opt/odoo/odoo10/odoo/odoo/http.py", line 675, in dispatch
             result = self._call_function(**self.params)
             File "/opt/odoo/odoo10/odoo/odoo/http.py", line 331, in _call_function
             return checked_call(self.db, *args, **kwargs)
             File "/opt/odoo/odoo10/odoo/odoo/service/model.py", line 119, in wrapper
             return f(dbname, *args, **kwargs)
             File "/opt/odoo/odoo10/odoo/odoo/http.py", line 324, in checked_call
             result = self.endpoint(*a, **kw)
             File "/opt/odoo/odoo10/odoo/odoo/http.py", line 933, in __call__
             return self.method(*args, **kw)
             File "/opt/odoo/odoo10/odoo/odoo/http.py", line 504, in response_wrap
             response = f(*args, **kw)
             File "/opt/odoo/odoo10/odoo/linklovingaddons/linkloving_app_api/controllers/controllers.py", line 2839, in new_finish_prepare_material
             'picking_material_date': fields.datetime.now()})
             File "/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py", line 282, in write
             track_self.message_track(tracked_fields, initial_values)
             File "/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py", line 485, in message_track
             tracking = self._message_track_get_changes(tracked_fields, initial_values)
             File "/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py", line 435, in _message_track_get_changes
             result[record.id] = record._message_track(tracked_fields, initial_values[record.id])
             File "/opt/odoo/odoo10/odoo/addons/mail/models/mail_thread.py", line 455, in _message_track
             new_value = getattr(self, col_name)
             File "/opt/odoo/odoo10/odoo/odoo/fields.py", line 869, in __get__
             self.determine_value(record)
             File "/opt/odoo/odoo10/odoo/odoo/fields.py", line 971, in determine_value
             record._prefetch_field(self)
             File "/opt/odoo/odoo10/odoo/odoo/models.py", line 3054, in _prefetch_field
             result = self.read([f.name for f in fs], load='_classic_write')
             File "/opt/odoo/odoo10/odoo/odoo/models.py", line 3002, in read
             values[name] = field.convert_to_read(record[name], record, use_name_get)
             File "/opt/odoo/odoo10/odoo/odoo/models.py", line 5177, in __getitem__
             return self._fields[key].__get__(self, type(self))
             File "/opt/odoo/odoo10/odoo/odoo/fields.py", line 865, in __get__
             value = record._cache[self]
             File "/opt/odoo/odoo10/odoo/odoo/models.py", line 5526, in __getitem__
             return value.get() if isinstance(value, SpecialValue) else value
             File "/opt/odoo/odoo10/odoo/odoo/fields.py", line 48, in get
             raise self.exception
             AccessError: (u'\u7531\u4e8e\u5b89\u5168\u9650\u5236\uff0c\u8bf7\u6c42\u7684\u64cd\u4f5c\u65e0\u6cd5\u5b8c\u6210\u3002\u8bf7\u8054\u7cfb\u4f60\u7684\u7cfb\u7edf\u7ba1\u7406\u5458\u3002\n\n(\u5355\u636e\u7c7b\u578b: mrp.production, \u64cd\u4f5c: read)', None)

             * exception_type : access_error
             * message : 由于安全限制，请求的操作无法完成。请联系你的系统管理员。

             (单据类型: mrp.production, 操作: read)
             None
             * name : odoo.exceptions.AccessError
             * arguments : ["由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)",null]
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
