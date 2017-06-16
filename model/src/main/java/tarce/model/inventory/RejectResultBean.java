package tarce.model.inventory;

import java.util.List;

/**
 * Created by rose.zou on 2017/6/2.
 * 品检通过  不通过
 */

public class RejectResultBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"qc_test_qty":100,"qc_img":["http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=20&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":50,"qty_produced":200,"qc_rate":50,"name":"QCF/201705250081","qc_fail_qty":50,"feedback_id":701,"qc_note":"可以","state":"qc_success","production_id":{"order_id":47654,"display_name":"MO1705231797","product_id":{"product_id":71769,"product_name":"P220S-成品-裱纸 -秘鲁minimudo"}}},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;
    /**
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 1117, in inspection_result\n    if not feedback:\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_mrp_extend/models/models.py\", line 1365, in action_qc_success\n    if self.production_id.state in ['waiting_rework', 'waiting_inspection_finish']:\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3054, in _prefetch_field\n    result = self.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3002, in read\n    values[name] = field.convert_to_read(record[name], record, use_name_get)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 865, in __get__\n    value = record._cache[self]\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5526, in __getitem__\n    return value.get() if isinstance(value, SpecialValue) else value\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 48, in get\n    raise self.exception\nAccessError: (u'\\u7531\\u4e8e\\u5b89\\u5168\\u9650\\u5236\\uff0c\\u8bf7\\u6c42\\u7684\\u64cd\\u4f5c\\u65e0\\u6cd5\\u5b8c\\u6210\\u3002\\u8bf7\\u8054\\u7cfb\\u4f60\\u7684\\u7cfb\\u7edf\\u7ba1\\u7406\\u5458\\u3002\\n\\n(\\u5355\\u636e\\u7c7b\\u578b: mrp.production, \\u64cd\\u4f5c: read)', None)\n","exception_type":"access_error","message":"由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)\nNone","name":"odoo.exceptions.AccessError","arguments":["由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)",null]}}
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
         * res_data : {"qc_test_qty":100,"qc_img":["http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=20&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":50,"qty_produced":200,"qc_rate":50,"name":"QCF/201705250081","qc_fail_qty":50,"feedback_id":701,"qc_note":"可以","state":"qc_success","production_id":{"order_id":47654,"display_name":"MO1705231797","product_id":{"product_id":71769,"product_name":"P220S-成品-裱纸 -秘鲁minimudo"}}}
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
            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            /**
             * qc_test_qty : 100.0
             * qc_img : ["http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=20&model=qc.feedback.img&field=qc_img"]
             * qc_fail_rate : 50.0
             * qty_produced : 200.0
             * qc_rate : 50.0
             * name : QCF/201705250081
             * qc_fail_qty : 50.0
             * feedback_id : 701
             * qc_note : 可以
             * state : qc_success
             * production_id : {"order_id":47654,"display_name":"MO1705231797","product_id":{"product_id":71769,"product_name":"P220S-成品-裱纸 -秘鲁minimudo"}}
             */

            private String error;
            private double qc_test_qty;
            private double qc_fail_rate;
            private double qty_produced;
            private double qc_rate;
            private String name;
            private double qc_fail_qty;
            private int feedback_id;
            private String qc_note;
            private String state;
            private ProductionIdBean production_id;
            private List<String> qc_img;

            public double getQc_test_qty() {
                return qc_test_qty;
            }

            public void setQc_test_qty(double qc_test_qty) {
                this.qc_test_qty = qc_test_qty;
            }

            public double getQc_fail_rate() {
                return qc_fail_rate;
            }

            public void setQc_fail_rate(double qc_fail_rate) {
                this.qc_fail_rate = qc_fail_rate;
            }

            public double getQty_produced() {
                return qty_produced;
            }

            public void setQty_produced(double qty_produced) {
                this.qty_produced = qty_produced;
            }

            public double getQc_rate() {
                return qc_rate;
            }

            public void setQc_rate(double qc_rate) {
                this.qc_rate = qc_rate;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getQc_fail_qty() {
                return qc_fail_qty;
            }

            public void setQc_fail_qty(double qc_fail_qty) {
                this.qc_fail_qty = qc_fail_qty;
            }

            public int getFeedback_id() {
                return feedback_id;
            }

            public void setFeedback_id(int feedback_id) {
                this.feedback_id = feedback_id;
            }

            public String getQc_note() {
                return qc_note;
            }

            public void setQc_note(String qc_note) {
                this.qc_note = qc_note;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public ProductionIdBean getProduction_id() {
                return production_id;
            }

            public void setProduction_id(ProductionIdBean production_id) {
                this.production_id = production_id;
            }

            public List<String> getQc_img() {
                return qc_img;
            }

            public void setQc_img(List<String> qc_img) {
                this.qc_img = qc_img;
            }

            public static class ProductionIdBean {
                /**
                 * order_id : 47654
                 * display_name : MO1705231797
                 * product_id : {"product_id":71769,"product_name":"P220S-成品-裱纸 -秘鲁minimudo"}
                 */

                private int order_id;
                private String display_name;
                private ProductIdBean product_id;

                public int getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(int order_id) {
                    this.order_id = order_id;
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

                public static class ProductIdBean {
                    /**
                     * product_id : 71769
                     * product_name : P220S-成品-裱纸 -秘鲁minimudo
                     */

                    private int product_id;
                    private String product_name;

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
                }
            }
        }
    }

    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 1117, in inspection_result\n    if not feedback:\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_mrp_extend/models/models.py\", line 1365, in action_qc_success\n    if self.production_id.state in ['waiting_rework', 'waiting_inspection_finish']:\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3054, in _prefetch_field\n    result = self.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3002, in read\n    values[name] = field.convert_to_read(record[name], record, use_name_get)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 865, in __get__\n    value = record._cache[self]\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5526, in __getitem__\n    return value.get() if isinstance(value, SpecialValue) else value\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 48, in get\n    raise self.exception\nAccessError: (u'\\u7531\\u4e8e\\u5b89\\u5168\\u9650\\u5236\\uff0c\\u8bf7\\u6c42\\u7684\\u64cd\\u4f5c\\u65e0\\u6cd5\\u5b8c\\u6210\\u3002\\u8bf7\\u8054\\u7cfb\\u4f60\\u7684\\u7cfb\\u7edf\\u7ba1\\u7406\\u5458\\u3002\\n\\n(\\u5355\\u636e\\u7c7b\\u578b: mrp.production, \\u64cd\\u4f5c: read)', None)\n","exception_type":"access_error","message":"由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)\nNone","name":"odoo.exceptions.AccessError","arguments":["由于安全限制，请求的操作无法完成。请联系你的系统管理员。\n\n(单据类型: mrp.production, 操作: read)",null]}
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
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 638, in _handle_exception
             return super(JsonRequest, self)._handle_exception(exception)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 675, in dispatch
             result = self._call_function(**self.params)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 331, in _call_function
             return checked_call(self.db, *args, **kwargs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py", line 119, in wrapper
             return f(dbname, *args, **kwargs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 324, in checked_call
             result = self.endpoint(*a, **kw)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 933, in __call__
             return self.method(*args, **kw)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 504, in response_wrap
             response = f(*args, **kw)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py", line 1117, in inspection_result
             if not feedback:
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_mrp_extend/models/models.py", line 1365, in action_qc_success
             if self.production_id.state in ['waiting_rework', 'waiting_inspection_finish']:
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 869, in __get__
             self.determine_value(record)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 971, in determine_value
             record._prefetch_field(self)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 3054, in _prefetch_field
             result = self.read([f.name for f in fs], load='_classic_write')
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 3002, in read
             values[name] = field.convert_to_read(record[name], record, use_name_get)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 5177, in __getitem__
             return self._fields[key].__get__(self, type(self))
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 865, in __get__
             value = record._cache[self]
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 5526, in __getitem__
             return value.get() if isinstance(value, SpecialValue) else value
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 48, in get
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
