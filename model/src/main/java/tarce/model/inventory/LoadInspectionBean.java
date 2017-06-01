package tarce.model.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rose.zou on 2017/6/1.
 * 品检页面红色数字
 */

public class LoadInspectionBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"linkloving_mrp_extend.mrp_production_wait_qc_inspection":{"needaction_enabled":true,"needaction_counter":13},"linkloving_mrp_extend.mrp_production_qc_inspecting":{"needaction_enabled":true,"needaction_counter":1}},"res_msg":"","res_code":1}
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
         * res_data : {"linkloving_mrp_extend.mrp_production_wait_qc_inspection":{"needaction_enabled":true,"needaction_counter":13},"linkloving_mrp_extend.mrp_production_qc_inspecting":{"needaction_enabled":true,"needaction_counter":1}}
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
            @SerializedName("linkloving_mrp_extend.mrp_production_wait_qc_inspection")
            private LinklovingMrpExtend linkloving_mrp_extend_mrp_production_wait_qc_inspection; // FIXME check this code

            public LinklovingMrpExtend getLinkloving_mrp_extend_mrp_production_wait_qc_inspection() {
                return linkloving_mrp_extend_mrp_production_wait_qc_inspection;
            }

            public void setLinkloving_mrp_extend_mrp_production_wait_qc_inspection(LinklovingMrpExtend linkloving_mrp_extend_mrp_production_wait_qc_inspection) {
                this.linkloving_mrp_extend_mrp_production_wait_qc_inspection = linkloving_mrp_extend_mrp_production_wait_qc_inspection;
            }

            public LinklovingMrpExtend getLinkloving_mrp_extend_mrp_production_qc_inspecting() {
                return linkloving_mrp_extend_mrp_production_qc_inspecting;
            }

            public void setLinkloving_mrp_extend_mrp_production_qc_inspecting(LinklovingMrpExtend linkloving_mrp_extend_mrp_production_qc_inspecting) {
                this.linkloving_mrp_extend_mrp_production_qc_inspecting = linkloving_mrp_extend_mrp_production_qc_inspecting;
            }

            @SerializedName("linkloving_mrp_extend.mrp_production_qc_inspecting")
            private LinklovingMrpExtend linkloving_mrp_extend_mrp_production_qc_inspecting; // FIXME check this code



            public static class LinklovingMrpExtend {
                /**
                 * needaction_enabled : true
                 * needaction_counter : 13
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
