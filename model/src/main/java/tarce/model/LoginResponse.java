package tarce.model;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public class LoginResponse {


    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"groups":[{"id":6131,"name":"group_account_invoice"},{"id":6133,"name":"group_account_manager"},{"id":6132,"name":"group_account_user"},{"id":4377,"name":"group_analytic_accounting"},{"id":2162,"name":"group_erp_manager"},{"id":2167,"name":"group_no_one"},{"id":2168,"name":"group_partner_manager"},{"id":2169,"name":"group_portal"},{"id":2163,"name":"group_system"},{"id":2164,"name":"group_user"},{"id":10918,"name":"group_hr_attendance"},{"id":10917,"name":"group_hr_manager"},{"id":10916,"name":"group_hr_user"},{"id":11635,"name":"group_hr_attendance_manager"},{"id":11634,"name":"group_hr_attendance_user"},{"id":14723,"name":"group_hr_expense_manager"},{"id":14722,"name":"group_hr_expense_user"},{"id":11780,"name":"group_hr_holidays_manager"},{"id":11779,"name":"group_hr_holidays_user"},{"id":12196,"name":"group_hr_payroll_manager"},{"id":12195,"name":"group_hr_payroll_user"},{"id":74418,"name":"group_hr_expense_president"},{"id":74930,"name":"group_charge_inspection"},{"id":74928,"name":"group_charge_produce"},{"id":74929,"name":"group_charge_warehouse"},{"id":10214,"name":"group_mrp_manager"},{"id":10213,"name":"group_mrp_user"},{"id":4819,"name":"group_product_pricelist"},{"id":4817,"name":"group_sale_pricelist"},{"id":11159,"name":"group_project_manager"},{"id":11158,"name":"group_project_user"},{"id":10625,"name":"group_manage_vendor_price"},{"id":10623,"name":"group_purchase_manager"},{"id":10622,"name":"group_purchase_user"},{"id":7408,"name":"group_delivery_invoice_address"},{"id":7409,"name":"group_show_price_subtotal"},{"id":7128,"name":"group_sale_manager"},{"id":7126,"name":"group_sale_salesman"},{"id":7127,"name":"group_sale_salesman_all_leads"},{"id":8320,"name":"group_adv_location"},{"id":8317,"name":"group_stock_manager"},{"id":8314,"name":"group_stock_multi_locations"},{"id":8316,"name":"group_stock_user"},{"id":8741,"name":"group_inventory_valuation"}],"user_id":1,"name":"Administrator","login_success":false},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * res_data : {"groups":[{"id":6131,"name":"group_account_invoice"},{"id":6133,"name":"group_account_manager"},{"id":6132,"name":"group_account_user"},{"id":4377,"name":"group_analytic_accounting"},{"id":2162,"name":"group_erp_manager"},{"id":2167,"name":"group_no_one"},{"id":2168,"name":"group_partner_manager"},{"id":2169,"name":"group_portal"},{"id":2163,"name":"group_system"},{"id":2164,"name":"group_user"},{"id":10918,"name":"group_hr_attendance"},{"id":10917,"name":"group_hr_manager"},{"id":10916,"name":"group_hr_user"},{"id":11635,"name":"group_hr_attendance_manager"},{"id":11634,"name":"group_hr_attendance_user"},{"id":14723,"name":"group_hr_expense_manager"},{"id":14722,"name":"group_hr_expense_user"},{"id":11780,"name":"group_hr_holidays_manager"},{"id":11779,"name":"group_hr_holidays_user"},{"id":12196,"name":"group_hr_payroll_manager"},{"id":12195,"name":"group_hr_payroll_user"},{"id":74418,"name":"group_hr_expense_president"},{"id":74930,"name":"group_charge_inspection"},{"id":74928,"name":"group_charge_produce"},{"id":74929,"name":"group_charge_warehouse"},{"id":10214,"name":"group_mrp_manager"},{"id":10213,"name":"group_mrp_user"},{"id":4819,"name":"group_product_pricelist"},{"id":4817,"name":"group_sale_pricelist"},{"id":11159,"name":"group_project_manager"},{"id":11158,"name":"group_project_user"},{"id":10625,"name":"group_manage_vendor_price"},{"id":10623,"name":"group_purchase_manager"},{"id":10622,"name":"group_purchase_user"},{"id":7408,"name":"group_delivery_invoice_address"},{"id":7409,"name":"group_show_price_subtotal"},{"id":7128,"name":"group_sale_manager"},{"id":7126,"name":"group_sale_salesman"},{"id":7127,"name":"group_sale_salesman_all_leads"},{"id":8320,"name":"group_adv_location"},{"id":8317,"name":"group_stock_manager"},{"id":8314,"name":"group_stock_multi_locations"},{"id":8316,"name":"group_stock_user"},{"id":8741,"name":"group_inventory_valuation"}],"user_id":1,"name":"Administrator","login_success":false}
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
            /**
             * groups : [{"id":6131,"name":"group_account_invoice"},{"id":6133,"name":"group_account_manager"},{"id":6132,"name":"group_account_user"},{"id":4377,"name":"group_analytic_accounting"},{"id":2162,"name":"group_erp_manager"},{"id":2167,"name":"group_no_one"},{"id":2168,"name":"group_partner_manager"},{"id":2169,"name":"group_portal"},{"id":2163,"name":"group_system"},{"id":2164,"name":"group_user"},{"id":10918,"name":"group_hr_attendance"},{"id":10917,"name":"group_hr_manager"},{"id":10916,"name":"group_hr_user"},{"id":11635,"name":"group_hr_attendance_manager"},{"id":11634,"name":"group_hr_attendance_user"},{"id":14723,"name":"group_hr_expense_manager"},{"id":14722,"name":"group_hr_expense_user"},{"id":11780,"name":"group_hr_holidays_manager"},{"id":11779,"name":"group_hr_holidays_user"},{"id":12196,"name":"group_hr_payroll_manager"},{"id":12195,"name":"group_hr_payroll_user"},{"id":74418,"name":"group_hr_expense_president"},{"id":74930,"name":"group_charge_inspection"},{"id":74928,"name":"group_charge_produce"},{"id":74929,"name":"group_charge_warehouse"},{"id":10214,"name":"group_mrp_manager"},{"id":10213,"name":"group_mrp_user"},{"id":4819,"name":"group_product_pricelist"},{"id":4817,"name":"group_sale_pricelist"},{"id":11159,"name":"group_project_manager"},{"id":11158,"name":"group_project_user"},{"id":10625,"name":"group_manage_vendor_price"},{"id":10623,"name":"group_purchase_manager"},{"id":10622,"name":"group_purchase_user"},{"id":7408,"name":"group_delivery_invoice_address"},{"id":7409,"name":"group_show_price_subtotal"},{"id":7128,"name":"group_sale_manager"},{"id":7126,"name":"group_sale_salesman"},{"id":7127,"name":"group_sale_salesman_all_leads"},{"id":8320,"name":"group_adv_location"},{"id":8317,"name":"group_stock_manager"},{"id":8314,"name":"group_stock_multi_locations"},{"id":8316,"name":"group_stock_user"},{"id":8741,"name":"group_inventory_valuation"}]
             * user_id : 1
             * name : Administrator
             * login_success : false
             */

            private int user_id;
            private String name;
            private boolean login_success;
            private List<GroupsBean> groups;

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
}
