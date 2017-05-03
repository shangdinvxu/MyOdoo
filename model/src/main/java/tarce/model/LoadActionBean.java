package tarce.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel.Xu on 2017/5/3.
 */

public class LoadActionBean {
    public static class TResult {

        public static class TRes_data {

            public static class TLinkloving_mrp_extend_menu_mrp_waiting_inventory_material {

                private Boolean needaction_enabled;	/*true*/
                private Integer needaction_counter;	/*0*/

                public void setNeedaction_enabled(Boolean value) {
                    this.needaction_enabled = value;
                }

                public Boolean getNeedaction_enabled() {
                    return this.needaction_enabled;
                }

                public void setNeedaction_counter(Integer value) {
                    this.needaction_counter = value;
                }

                public Integer getNeedaction_counter() {
                    return this.needaction_counter;
                }

            }

            public static class TLinkloving_mrp_extend_menu_mrp_prepare_material_ing {

                private Boolean needaction_enabled;	/*true*/
                private Integer needaction_counter;	/*3*/

                public void setNeedaction_enabled(Boolean value) {
                    this.needaction_enabled = value;
                }

                public Boolean getNeedaction_enabled() {
                    return this.needaction_enabled;
                }

                public void setNeedaction_counter(Integer value) {
                    this.needaction_counter = value;
                }

                public Integer getNeedaction_counter() {
                    return this.needaction_counter;
                }

            }

            public static class TLinkloving_mrp_extend_menu_mrp_waiting_post_inventory {

                private Boolean needaction_enabled;	/*true*/
                private Integer needaction_counter;	/*2*/

                public void setNeedaction_enabled(Boolean value) {
                    this.needaction_enabled = value;
                }

                public Boolean getNeedaction_enabled() {
                    return this.needaction_enabled;
                }

                public void setNeedaction_counter(Integer value) {
                    this.needaction_counter = value;
                }

                public Integer getNeedaction_counter() {
                    return this.needaction_counter;
                }

            }
            @SerializedName("linkloving_mrp_extend.menu_mrp_prepare_material_ing")
            private TLinkloving_mrp_extend_menu_mrp_prepare_material_ing linkloving_mrp_extend_menu_mrp_prepare_material_ing;	/*TLinkloving_mrp_extend_menu_mrp_prepare_material_ing*/
            @SerializedName("linkloving_mrp_extend.menu_mrp_waiting_post_inventory")
            private TLinkloving_mrp_extend_menu_mrp_waiting_post_inventory linkloving_mrp_extend_menu_mrp_waiting_post_inventory;	/*TLinkloving_mrp_extend_menu_mrp_waiting_post_inventory*/
            @SerializedName("linkloving_mrp_extend.menu_mrp_waiting_inventory_material")
            private TLinkloving_mrp_extend_menu_mrp_waiting_inventory_material linkloving_mrp_extend_menu_mrp_waiting_inventory_material;	/*TLinkloving_mrp_extend.menu_mrp_waiting_inventory_material*/

            public void setLinkloving_mrp_extend_menu_mrp_waiting_inventory_material(TLinkloving_mrp_extend_menu_mrp_waiting_inventory_material value) {
                this.linkloving_mrp_extend_menu_mrp_waiting_inventory_material = value;
            }

            public TLinkloving_mrp_extend_menu_mrp_waiting_inventory_material getLinkloving_mrp_extend_menu_mrp_waiting_inventory_material() {
                return this.linkloving_mrp_extend_menu_mrp_waiting_inventory_material;
            }

            public void setLinkloving_mrp_extend_menu_mrp_prepare_material_ing(TLinkloving_mrp_extend_menu_mrp_prepare_material_ing value) {
                this.linkloving_mrp_extend_menu_mrp_prepare_material_ing = value;
            }

            public TLinkloving_mrp_extend_menu_mrp_prepare_material_ing getLinkloving_mrp_extend_menu_mrp_prepare_material_ing() {
                return this.linkloving_mrp_extend_menu_mrp_prepare_material_ing;
            }

            public void setLinkloving_mrp_extend_menu_mrp_waiting_post_inventory(TLinkloving_mrp_extend_menu_mrp_waiting_post_inventory value) {
                this.linkloving_mrp_extend_menu_mrp_waiting_post_inventory = value;
            }

            public TLinkloving_mrp_extend_menu_mrp_waiting_post_inventory getLinkloving_mrp_extend_menu_mrp_waiting_post_inventory() {
                return this.linkloving_mrp_extend_menu_mrp_waiting_post_inventory;
            }

        }

        private TRes_data res_data;	/*TRes_data*/
        private Integer res_code;	/*1*/
        private String res_msg;	/**/

        public void setRes_data(TRes_data value) {
            this.res_data = value;
        }

        public TRes_data getRes_data() {
            return this.res_data;
        }

        public void setRes_code(Integer value) {
            this.res_code = value;
        }

        public Integer getRes_code() {
            return this.res_code;
        }

        public void setRes_msg(String value) {
            this.res_msg = value;
        }

        public String getRes_msg() {
            return this.res_msg;
        }

    }

    private Object id;	/*Object*/
    private TResult result;	/*TResult*/
    private String jsonrpc;	/*2.0*/

    public void setId(Object value) {
        this.id = value;
    }

    public Object getId() {
        return this.id;
    }

    public void setResult(TResult value) {
        this.result = value;
    }

    public TResult getResult() {
        return this.result;
    }

    public void setJsonrpc(String value) {
        this.jsonrpc = value;
    }

    public String getJsonrpc() {
        return this.jsonrpc;
    }
}
