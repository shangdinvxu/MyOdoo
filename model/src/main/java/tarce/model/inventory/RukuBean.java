package tarce.model.inventory;

import java.util.List;

/**
 * Created by rose.zou on 2017/6/2.
 * 点击入库 返回数据
 */

public class RukuBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"qc_test_qty":10,"qc_img":["http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=16&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":50,"qty_produced":200,"qc_rate":5,"name":"QCF/201705250078","qc_fail_qty":5,"feedback_id":698,"qc_note":"呜呜呜呜","state":"alredy_post_inventory","production_id":{"order_id":47839,"display_name":"MO170524062","product_id":{"product_id":71771,"product_name":"W120-成品-秘鲁minimudo"}}},"res_msg":"","res_code":1}
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
         * res_data : {"qc_test_qty":10,"qc_img":["http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=16&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":50,"qty_produced":200,"qc_rate":5,"name":"QCF/201705250078","qc_fail_qty":5,"feedback_id":698,"qc_note":"呜呜呜呜","state":"alredy_post_inventory","production_id":{"order_id":47839,"display_name":"MO170524062","product_id":{"product_id":71771,"product_name":"W120-成品-秘鲁minimudo"}}}
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
             * qc_test_qty : 10.0
             * qc_img : ["http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=16&model=qc.feedback.img&field=qc_img"]
             * qc_fail_rate : 50.0
             * qty_produced : 200.0
             * qc_rate : 5.0
             * name : QCF/201705250078
             * qc_fail_qty : 5.0
             * feedback_id : 698
             * qc_note : 呜呜呜呜
             * state : alredy_post_inventory
             * production_id : {"order_id":47839,"display_name":"MO170524062","product_id":{"product_id":71771,"product_name":"W120-成品-秘鲁minimudo"}}
             */

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
                 * order_id : 47839
                 * display_name : MO170524062
                 * product_id : {"product_id":71771,"product_name":"W120-成品-秘鲁minimudo"}
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
                     * product_id : 71771
                     * product_name : W120-成品-秘鲁minimudo
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
}
