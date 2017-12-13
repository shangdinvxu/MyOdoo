package tarce.model.inventory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rose.zou on 2017/5/23.
 */

public class LoadProductBean {
    private Object id;	/*Object*/
    private LoadResultBean result;	/*TResult*/
    private String jsonrpc;	/*2.0*/

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public LoadResultBean getResult() {
        return result;
    }

    public void setResult(LoadResultBean result) {
        this.result = result;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public static class LoadResultBean{
       /* "res_data":Object{...},
                "res_msg":"",
                "res_code":1*/
        private Object res_msg;
        private int res_code;
        private ThreeSubResult res_data;

        public Object getRes_msg() {
            return res_msg;
        }

        public void setRes_msg(Object res_msg) {
            this.res_msg = res_msg;
        }

        public int getRes_code() {
            return res_code;
        }

        public void setRes_code(int res_code) {
            this.res_code = res_code;
        }

        public ThreeSubResult getRes_data() {
            return res_data;
        }

        public void setRes_data(ThreeSubResult res_data) {
            this.res_data = res_data;
        }

        public static class ThreeSubResult{
            @SerializedName("linkloving_mrp_extend.menu_mrp_finish_prepare_material")
            private NeedSubBean linkloving_mrp_extend_menu_mrp_finish_prepare_material;
            @SerializedName("linkloving_mrp_extend.menu_mrp_already_picking")
            private NeedSubBean linkloving_mrp_extend_menu_mrp_already_picking;
            @SerializedName("linkloving_mrp_extend.menu_mrp_done")
            private NeedSubBean linkloving_mrp_extend_menu_mrp_done;
            @SerializedName("linkloving_mrp_extend.menu_mrp_rework_ing")
            private NeedSubBean linkloving_mrp_extend_menu_mrp_rework_ing;
            @SerializedName("linkloving_mrp_extend.mrp_production_qc_inspection_fail")
            private NeedSubBean linkloving_mrp_extend_mrp_production_qc_inspection_fail;
            @SerializedName("linkloving_mrp_extend.menu_mrp_waiting_inventory_material")
            private NeedSubBean linkloving_mrp_extend_menu_mrp_waiting_inventory_material;
            @SerializedName("linkloving_mrp_extend.menu_mrp_progress")
            private NeedSubBean linkloving_mrp_extend_menu_mrp_progress;
            @SerializedName("linkloving_mrp_extend.menu_force_cancel_waiting_return")
            private NeedSubBean linkloving_mrp_extend_menu_force_cancel_waiting_return;
            @SerializedName("linkloving_mrp_extend.menu_force_cancel_waiting_warehouse_inspection")
            private NeedSubBean linkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection;

            public NeedSubBean getLinkloving_mrp_extend_menu_force_cancel_waiting_return() {
                return linkloving_mrp_extend_menu_force_cancel_waiting_return;
            }

            public void setLinkloving_mrp_extend_menu_force_cancel_waiting_return(NeedSubBean linkloving_mrp_extend_menu_force_cancel_waiting_return) {
                this.linkloving_mrp_extend_menu_force_cancel_waiting_return = linkloving_mrp_extend_menu_force_cancel_waiting_return;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection() {
                return linkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection;
            }

            public void setLinkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection(NeedSubBean linkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection) {
                this.linkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection = linkloving_mrp_extend_menu_force_cancel_waiting_warehouse_inspection;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_mrp_finish_prepare_material() {
                return linkloving_mrp_extend_menu_mrp_finish_prepare_material;
            }

            public void setLinkloving_mrp_extend_menu_mrp_finish_prepare_material(NeedSubBean linkloving_mrp_extend_menu_mrp_finish_prepare_material) {
                this.linkloving_mrp_extend_menu_mrp_finish_prepare_material = linkloving_mrp_extend_menu_mrp_finish_prepare_material;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_mrp_already_picking() {
                return linkloving_mrp_extend_menu_mrp_already_picking;
            }

            public void setLinkloving_mrp_extend_menu_mrp_already_picking(NeedSubBean linkloving_mrp_extend_menu_mrp_already_picking) {
                this.linkloving_mrp_extend_menu_mrp_already_picking = linkloving_mrp_extend_menu_mrp_already_picking;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_mrp_done() {
                return linkloving_mrp_extend_menu_mrp_done;
            }

            public void setLinkloving_mrp_extend_menu_mrp_done(NeedSubBean linkloving_mrp_extend_menu_mrp_done) {
                this.linkloving_mrp_extend_menu_mrp_done = linkloving_mrp_extend_menu_mrp_done;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_mrp_rework_ing() {
                return linkloving_mrp_extend_menu_mrp_rework_ing;
            }

            public void setLinkloving_mrp_extend_menu_mrp_rework_ing(NeedSubBean linkloving_mrp_extend_menu_mrp_rework_ing) {
                this.linkloving_mrp_extend_menu_mrp_rework_ing = linkloving_mrp_extend_menu_mrp_rework_ing;
            }

            public NeedSubBean getLinkloving_mrp_extend_mrp_production_qc_inspection_fail() {
                return linkloving_mrp_extend_mrp_production_qc_inspection_fail;
            }

            public void setLinkloving_mrp_extend_mrp_production_qc_inspection_fail(NeedSubBean linkloving_mrp_extend_mrp_production_qc_inspection_fail) {
                this.linkloving_mrp_extend_mrp_production_qc_inspection_fail = linkloving_mrp_extend_mrp_production_qc_inspection_fail;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_mrp_waiting_inventory_material() {
                return linkloving_mrp_extend_menu_mrp_waiting_inventory_material;
            }

            public void setLinkloving_mrp_extend_menu_mrp_waiting_inventory_material(NeedSubBean linkloving_mrp_extend_menu_mrp_waiting_inventory_material) {
                this.linkloving_mrp_extend_menu_mrp_waiting_inventory_material = linkloving_mrp_extend_menu_mrp_waiting_inventory_material;
            }

            public NeedSubBean getLinkloving_mrp_extend_menu_mrp_progress() {
                return linkloving_mrp_extend_menu_mrp_progress;
            }

            public void setLinkloving_mrp_extend_menu_mrp_progress(NeedSubBean linkloving_mrp_extend_menu_mrp_progress) {
                this.linkloving_mrp_extend_menu_mrp_progress = linkloving_mrp_extend_menu_mrp_progress;
            }

            public static class NeedSubBean{
                private Boolean needaction_enabled;	/*true*/

                public Boolean getNeedaction_enabled() {
                    return needaction_enabled;
                }

                public void setNeedaction_enabled(Boolean needaction_enabled) {
                    this.needaction_enabled = needaction_enabled;
                }

                public Integer getNeedaction_counter() {
                    return needaction_counter;
                }

                public void setNeedaction_counter(Integer needaction_counter) {
                    this.needaction_counter = needaction_counter;
                }

                private Integer needaction_counter;	/*3*/
            }
        }
    }
}
