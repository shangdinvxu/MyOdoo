package tarce.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel.Xu on 2017/5/3.
 */

public class LoadActionBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"linkloving_mrp_extend.mrp_production_action_qc_success":{"needaction_enabled":true,"needaction_counter":0},"linkloving_mrp_extend.menu_mrp_waiting_warehouse_inspection":{"needaction_enabled":true,"needaction_counter":9},"linkloving_mrp_extend.menu_mrp_prepare_material_ing":{"needaction_enabled":true,"needaction_counter":3}},"res_msg":"","res_code":1}
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
         * res_data : {"linkloving_mrp_extend.mrp_production_action_qc_success":{"needaction_enabled":true,"needaction_counter":0},"linkloving_mrp_extend.menu_mrp_waiting_warehouse_inspection":{"needaction_enabled":true,"needaction_counter":9},"linkloving_mrp_extend.menu_mrp_prepare_material_ing":{"needaction_enabled":true,"needaction_counter":3}}
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

            @SerializedName("linkloving_mrp_extend.mrp_production_action_qc_success")
            private LinklovingMrpExtend linkloving_mrp_extend_mrp_production_action_qc_success;
            @SerializedName("linkloving_mrp_extend.menu_mrp_waiting_warehouse_inspection")
            private LinklovingMrpExtend linkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection;
            @SerializedName("linkloving_mrp_extend.menu_mrp_prepare_material_ing")
            private LinklovingMrpExtend linkloving_mrp_extend_menu_mrp_prepare_material_ing;
            @SerializedName("linkloving_mrp_extend.menu_mrp_waiting_material")
            private LinklovingMrpExtend linkloving_mrp_extend_menu_mrp_waiting_material;

            public LinklovingMrpExtend getLinkloving_mrp_extend_menu_mrp_waiting_material() {
                return linkloving_mrp_extend_menu_mrp_waiting_material;
            }

            public void setLinkloving_mrp_extend_menu_mrp_waiting_material(LinklovingMrpExtend linkloving_mrp_extend_menu_mrp_waiting_material) {
                this.linkloving_mrp_extend_menu_mrp_waiting_material = linkloving_mrp_extend_menu_mrp_waiting_material;
            }

            public LinklovingMrpExtend getLinkloving_mrp_extend_mrp_production_action_qc_success() {
                return linkloving_mrp_extend_mrp_production_action_qc_success;
            }

            public void setLinkloving_mrp_extend_mrp_production_action_qc_success(LinklovingMrpExtend linkloving_mrp_extend_mrp_production_action_qc_success) {
                this.linkloving_mrp_extend_mrp_production_action_qc_success = linkloving_mrp_extend_mrp_production_action_qc_success;
            }

            public LinklovingMrpExtend getLinkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection() {
                return linkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection;
            }

            public void setLinkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection(LinklovingMrpExtend linkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection) {
                this.linkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection = linkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection;
            }

            public LinklovingMrpExtend getLinkloving_mrp_extend_menu_mrp_prepare_material_ing() {
                return linkloving_mrp_extend_menu_mrp_prepare_material_ing;
            }

            public void setLinkloving_mrp_extend_menu_mrp_prepare_material_ing(LinklovingMrpExtend linkloving_mrp_extend_menu_mrp_prepare_material_ing) {
                this.linkloving_mrp_extend_menu_mrp_prepare_material_ing = linkloving_mrp_extend_menu_mrp_prepare_material_ing;
            }


            public static class LinklovingMrpExtend {
                /**
                 * needaction_enabled : true
                 * needaction_counter : 0
                 */

                private boolean needaction_enabled;
                private int needaction_counter;

                public boolean isNeedaction_enabled() {
                    return needaction_enabled;
                }

                public void setNeedaction_enabled(boolean needaction_enabled) {
                    this.needaction_enabled = needaction_enabled;
                }

                public int getNeedaction_counter() {
                    return needaction_counter;
                }

                public void setNeedaction_counter(int needaction_counter) {
                    this.needaction_counter = needaction_counter;
                }
            }

        }
    }
}
