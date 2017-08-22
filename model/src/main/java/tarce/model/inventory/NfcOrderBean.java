package tarce.model.inventory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zws on 2017/8/17.
 */

public class NfcOrderBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\service\\model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"D:\\workspace\\odoo10\\odoo\\linklovingaddons\\linkloving_user_auth\\controllers\\controllers.py\", line 102, in auth_warehouse_manager\n    raise UserError(u'此卡未绑定任何用户')\nUserError: (u'\\u6b64\\u5361\\u672a\\u7ed1\\u5b9a\\u4efb\\u4f55\\u7528\\u6237', None)\n","exception_type":"user_error","message":"此卡未绑定任何用户\nNone","name":"odoo.exceptions.UserError","arguments":["此卡未绑定任何用户",null]}}
     */

    private String jsonrpc;
    private Object id;
    private ErrorBean error;
    /**
     * id : null
     * result : {"res_data":{"error":"非仓库人员,请勿打卡"},"res_msg":"","res_code":-1}
     */

    private ResultBean result;

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

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }


    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\service\\model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"D:\\workspace\\odoo10\\odoo\\odoo\\http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"D:\\workspace\\odoo10\\odoo\\linklovingaddons\\linkloving_user_auth\\controllers\\controllers.py\", line 102, in auth_warehouse_manager\n    raise UserError(u'此卡未绑定任何用户')\nUserError: (u'\\u6b64\\u5361\\u672a\\u7ed1\\u5b9a\\u4efb\\u4f55\\u7528\\u6237', None)\n","exception_type":"user_error","message":"此卡未绑定任何用户\nNone","name":"odoo.exceptions.UserError","arguments":["此卡未绑定任何用户",null]}
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
             *
             * exception_type : user_error
             * message : 此卡未绑定任何用户
             None
             * name : odoo.exceptions.UserError
             * arguments : ["此卡未绑定任何用户",null]
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

    public static class ResultBean {
        /**
         * res_data : {"error":"非仓库人员,请勿打卡"}
         * res_msg :
         * res_code : -1
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
            /**
             * error : 非仓库人员,请勿打卡
             */

            private String error;

            public String getErrorX() {
                return error;
            }

            public void setErrorX(String error) {
                this.error = error;
            }

            /**
             * card_num : 8804449A
             * employee_id : 259
             * work_email : zhenhua.liu@robotime.com
             * name : 刘振华
             */

            private String card_num;
            private int employee_id;
            private String work_email;
            private String name;

            public String getCard_num() {
                return card_num;
            }

            public void setCard_num(String card_num) {
                this.card_num = card_num;
            }

            public int getEmployee_id() {
                return employee_id;
            }

            public void setEmployee_id(int employee_id) {
                this.employee_id = employee_id;
            }

            public String getWork_email() {
                return work_email;
            }

            public void setWork_email(String work_email) {
                this.work_email = work_email;
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
