package tarce.model;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class LoginResponse {


    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"lang":"zh-Hans","user_ava":"http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=1&model=res.users&field=image_medium&time=1493832026.0","user_id":1,"name":"Administrator","login_success":true,"groups":[{"id":6131,"name":"group_account_invoice"},{"id":6133,"name":"group_account_manager"},{"id":6132,"name":"group_account_user"},{"id":4377,"name":"group_analytic_accounting"},{"id":2162,"name":"group_erp_manager"},{"id":2167,"name":"group_no_one"},{"id":2168,"name":"group_partner_manager"},{"id":2169,"name":"group_portal"},{"id":2163,"name":"group_system"},{"id":2164,"name":"group_user"},{"id":10918,"name":"group_hr_attendance"},{"id":10917,"name":"group_hr_manager"},{"id":10916,"name":"group_hr_user"},{"id":11635,"name":"group_hr_attendance_manager"},{"id":11634,"name":"group_hr_attendance_user"},{"id":14723,"name":"group_hr_expense_manager"},{"id":14722,"name":"group_hr_expense_user"},{"id":11780,"name":"group_hr_holidays_manager"},{"id":11779,"name":"group_hr_holidays_user"},{"id":12196,"name":"group_hr_payroll_manager"},{"id":12195,"name":"group_hr_payroll_user"},{"id":74418,"name":"group_hr_expense_president"},{"id":82912,"name":"group_charge_inspection"},{"id":82910,"name":"group_charge_produce"},{"id":82911,"name":"group_charge_warehouse"},{"id":10214,"name":"group_mrp_manager"},{"id":10213,"name":"group_mrp_user"},{"id":4819,"name":"group_product_pricelist"},{"id":4817,"name":"group_sale_pricelist"},{"id":4820,"name":"group_uom"},{"id":11159,"name":"group_project_manager"},{"id":11158,"name":"group_project_user"},{"id":10625,"name":"group_manage_vendor_price"},{"id":10623,"name":"group_purchase_manager"},{"id":10622,"name":"group_purchase_user"},{"id":7408,"name":"group_delivery_invoice_address"},{"id":7409,"name":"group_show_price_subtotal"},{"id":7128,"name":"group_sale_manager"},{"id":7126,"name":"group_sale_salesman"},{"id":7127,"name":"group_sale_salesman_all_leads"},{"id":8320,"name":"group_adv_location"},{"id":8317,"name":"group_stock_manager"},{"id":8314,"name":"group_stock_multi_locations"},{"id":8316,"name":"group_stock_user"},{"id":8741,"name":"group_inventory_valuation"},{"id":96547,"name":"group_website_designer"},{"id":96546,"name":"group_website_publisher"}],"partner_id":3},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;
    /**
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 98, in login\n    uid = request.session.authenticate(request.session.db, request.jsonrequest['login'], request.jsonrequest['password'])\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 1045, in authenticate\n    if uid: self.get_context()\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 1080, in get_context\n    self.context = request.env['res.users'].context_get() or {}\n  File \"<decorator-gen-30>\", line 2, in context_get\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/tools/cache.py\", line 87, in lookup\n    value = d[key] = self.method(*args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/addons/base/res/res_users.py\", line 421, in context_get\n    res = getattr(user, k) or False\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 980, in determine_value\n    self.compute_value(recs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 935, in compute_value\n    self._compute_value(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 929, in _compute_value\n    self.compute(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 580, in _compute_related\n    record[self.name] = other[field.name]\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3051, in _prefetch_field\n    result = records.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 2991, in read\n    self._read_from_database(stored, inherited)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3119, in _read_from_database\n    cr.execute(query_str, params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 141, in wrapper\n    return f(self, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 218, in execute\n    res = self._obj.execute(query, params)\nProgrammingError: column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n\n","exception_type":"internal_error","message":"column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n","name":"psycopg2.ProgrammingError","arguments":["column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n"]}}
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
         * res_data : {"lang":"zh-Hans","user_ava":"http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=1&model=res.users&field=image_medium&time=1493832026.0","user_id":1,"name":"Administrator","login_success":true,"groups":[{"id":6131,"name":"group_account_invoice"},{"id":6133,"name":"group_account_manager"},{"id":6132,"name":"group_account_user"},{"id":4377,"name":"group_analytic_accounting"},{"id":2162,"name":"group_erp_manager"},{"id":2167,"name":"group_no_one"},{"id":2168,"name":"group_partner_manager"},{"id":2169,"name":"group_portal"},{"id":2163,"name":"group_system"},{"id":2164,"name":"group_user"},{"id":10918,"name":"group_hr_attendance"},{"id":10917,"name":"group_hr_manager"},{"id":10916,"name":"group_hr_user"},{"id":11635,"name":"group_hr_attendance_manager"},{"id":11634,"name":"group_hr_attendance_user"},{"id":14723,"name":"group_hr_expense_manager"},{"id":14722,"name":"group_hr_expense_user"},{"id":11780,"name":"group_hr_holidays_manager"},{"id":11779,"name":"group_hr_holidays_user"},{"id":12196,"name":"group_hr_payroll_manager"},{"id":12195,"name":"group_hr_payroll_user"},{"id":74418,"name":"group_hr_expense_president"},{"id":82912,"name":"group_charge_inspection"},{"id":82910,"name":"group_charge_produce"},{"id":82911,"name":"group_charge_warehouse"},{"id":10214,"name":"group_mrp_manager"},{"id":10213,"name":"group_mrp_user"},{"id":4819,"name":"group_product_pricelist"},{"id":4817,"name":"group_sale_pricelist"},{"id":4820,"name":"group_uom"},{"id":11159,"name":"group_project_manager"},{"id":11158,"name":"group_project_user"},{"id":10625,"name":"group_manage_vendor_price"},{"id":10623,"name":"group_purchase_manager"},{"id":10622,"name":"group_purchase_user"},{"id":7408,"name":"group_delivery_invoice_address"},{"id":7409,"name":"group_show_price_subtotal"},{"id":7128,"name":"group_sale_manager"},{"id":7126,"name":"group_sale_salesman"},{"id":7127,"name":"group_sale_salesman_all_leads"},{"id":8320,"name":"group_adv_location"},{"id":8317,"name":"group_stock_manager"},{"id":8314,"name":"group_stock_multi_locations"},{"id":8316,"name":"group_stock_user"},{"id":8741,"name":"group_inventory_valuation"},{"id":96547,"name":"group_website_designer"},{"id":96546,"name":"group_website_publisher"}],"partner_id":3}
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

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public int getAllow_produced_qty_rate() {
                return allow_produced_qty_rate;
            }

            public void setAllow_produced_qty_rate(int allow_produced_qty_rate) {
                this.allow_produced_qty_rate = allow_produced_qty_rate;
            }

            /**
             * lang : zh-Hans
             * user_ava : http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=1&model=res.users&field=image_medium&time=1493832026.0
             * user_id : 1
             * name : Administrator
             * login_success : true
             * groups : [{"id":6131,"name":"group_account_invoice"},{"id":6133,"name":"group_account_manager"},{"id":6132,"name":"group_account_user"},{"id":4377,"name":"group_analytic_accounting"},{"id":2162,"name":"group_erp_manager"},{"id":2167,"name":"group_no_one"},{"id":2168,"name":"group_partner_manager"},{"id":2169,"name":"group_portal"},{"id":2163,"name":"group_system"},{"id":2164,"name":"group_user"},{"id":10918,"name":"group_hr_attendance"},{"id":10917,"name":"group_hr_manager"},{"id":10916,"name":"group_hr_user"},{"id":11635,"name":"group_hr_attendance_manager"},{"id":11634,"name":"group_hr_attendance_user"},{"id":14723,"name":"group_hr_expense_manager"},{"id":14722,"name":"group_hr_expense_user"},{"id":11780,"name":"group_hr_holidays_manager"},{"id":11779,"name":"group_hr_holidays_user"},{"id":12196,"name":"group_hr_payroll_manager"},{"id":12195,"name":"group_hr_payroll_user"},{"id":74418,"name":"group_hr_expense_president"},{"id":82912,"name":"group_charge_inspection"},{"id":82910,"name":"group_charge_produce"},{"id":82911,"name":"group_charge_warehouse"},{"id":10214,"name":"group_mrp_manager"},{"id":10213,"name":"group_mrp_user"},{"id":4819,"name":"group_product_pricelist"},{"id":4817,"name":"group_sale_pricelist"},{"id":4820,"name":"group_uom"},{"id":11159,"name":"group_project_manager"},{"id":11158,"name":"group_project_user"},{"id":10625,"name":"group_manage_vendor_price"},{"id":10623,"name":"group_purchase_manager"},{"id":10622,"name":"group_purchase_user"},{"id":7408,"name":"group_delivery_invoice_address"},{"id":7409,"name":"group_show_price_subtotal"},{"id":7128,"name":"group_sale_manager"},{"id":7126,"name":"group_sale_salesman"},{"id":7127,"name":"group_sale_salesman_all_leads"},{"id":8320,"name":"group_adv_location"},{"id":8317,"name":"group_stock_manager"},{"id":8314,"name":"group_stock_multi_locations"},{"id":8316,"name":"group_stock_user"},{"id":8741,"name":"group_inventory_valuation"},{"id":96547,"name":"group_website_designer"},{"id":96546,"name":"group_website_publisher"}]
             * partner_id : 3
             */

            private int allow_produced_qty_rate;
            private String company;
            private String error;
            private String lang;
            private String user_ava;
            private int user_id;
            private String name;
            private boolean login_success;
            private int partner_id;
            private List<GroupsBean> groups;

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public String getUser_ava() {
                return user_ava;
            }

            public void setUser_ava(String user_ava) {
                this.user_ava = user_ava;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isLogin_success() {
                return login_success;
            }

            public void setLogin_success(boolean login_success) {
                this.login_success = login_success;
            }

            public int getPartner_id() {
                return partner_id;
            }

            public void setPartner_id(int partner_id) {
                this.partner_id = partner_id;
            }

            public List<GroupsBean> getGroups() {
                return groups;
            }

            public void setGroups(List<GroupsBean> groups) {
                this.groups = groups;
            }

            public static class GroupsBean {
                /**
                 * id : 6131
                 * name : group_account_invoice
                 */

                private int id;
                private String name;

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
            }
        }
    }

    public static class   ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 98, in login\n    uid = request.session.authenticate(request.session.db, request.jsonrequest['login'], request.jsonrequest['password'])\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 1045, in authenticate\n    if uid: self.get_context()\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 1080, in get_context\n    self.context = request.env['res.users'].context_get() or {}\n  File \"<decorator-gen-30>\", line 2, in context_get\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/tools/cache.py\", line 87, in lookup\n    value = d[key] = self.method(*args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/addons/base/res/res_users.py\", line 421, in context_get\n    res = getattr(user, k) or False\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 980, in determine_value\n    self.compute_value(recs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 935, in compute_value\n    self._compute_value(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 929, in _compute_value\n    self.compute(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 580, in _compute_related\n    record[self.name] = other[field.name]\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3051, in _prefetch_field\n    result = records.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 2991, in read\n    self._read_from_database(stored, inherited)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3119, in _read_from_database\n    cr.execute(query_str, params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 141, in wrapper\n    return f(self, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 218, in execute\n    res = self._obj.execute(query, params)\nProgrammingError: column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n\n","exception_type":"internal_error","message":"column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n","name":"psycopg2.ProgrammingError","arguments":["column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n"]}
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
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py", line 98, in login
             uid = request.session.authenticate(request.session.db, request.jsonrequest['login'], request.jsonrequest['password'])
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 1045, in authenticate
             if uid: self.get_context()
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 1080, in get_context
             self.context = request.env['res.users'].context_get() or {}
             File "<decorator-gen-30>", line 2, in context_get
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/tools/cache.py", line 87, in lookup
             value = d[key] = self.method(*args, **kwargs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/addons/base/res/res_users.py", line 421, in context_get
             res = getattr(user, k) or False
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 869, in __get__
             self.determine_value(record)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 980, in determine_value
             self.compute_value(recs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 935, in compute_value
             self._compute_value(records)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 929, in _compute_value
             self.compute(records)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 580, in _compute_related
             record[self.name] = other[field.name]
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 5177, in __getitem__
             return self._fields[key].__get__(self, type(self))
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 869, in __get__
             self.determine_value(record)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py", line 971, in determine_value
             record._prefetch_field(self)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 3051, in _prefetch_field
             result = records.read([f.name for f in fs], load='_classic_write')
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 2991, in read
             self._read_from_database(stored, inherited)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py", line 3119, in _read_from_database
             cr.execute(query_str, params)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py", line 141, in wrapper
             return f(self, *args, **kwargs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py", line 218, in execute
             res = self._obj.execute(query, params)
             ProgrammingError: column res_partner.customer_status does not exist
             LINE 1: ...e","res_partner"."credit_limit" as "credit_limit","res_partn...
             ^


             * exception_type : internal_error
             * message : column res_partner.customer_status does not exist
             LINE 1: ...e","res_partner"."credit_limit" as "credit_limit","res_partn...
             ^

             * name : psycopg2.ProgrammingError
             * arguments : ["column res_partner.customer_status does not exist\nLINE 1: ...e\",\"res_partner\".\"credit_limit\" as \"credit_limit\",\"res_partn...\n                                                             ^\n"]
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
