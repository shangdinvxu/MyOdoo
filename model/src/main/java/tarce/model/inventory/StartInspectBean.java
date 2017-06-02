package tarce.model.inventory;

import java.util.List;

/**
 * Created by rose.zou on 2017/6/2.
 * 开始品检返回数据
 */

public class StartInspectBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":520,"qc_rate":0,"name":"QCF/201705270103","qc_fail_qty":0,"feedback_id":723,"qc_note":"","state":"qc_ing","production_id":{"order_id":43156,"display_name":"MO170507258","product_id":{"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}}},"res_msg":"","res_code":1}
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
         * res_data : {"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":520,"qc_rate":0,"name":"QCF/201705270103","qc_fail_qty":0,"feedback_id":723,"qc_note":"","state":"qc_ing","production_id":{"order_id":43156,"display_name":"MO170507258","product_id":{"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}}}
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
             * qc_test_qty : 0.0
             * qc_img : []
             * qc_fail_rate : 0.0
             * qty_produced : 520.0
             * qc_rate : 0.0
             * name : QCF/201705270103
             * qc_fail_qty : 0.0
             * feedback_id : 723
             * qc_note :
             * state : qc_ing
             * production_id : {"order_id":43156,"display_name":"MO170507258","product_id":{"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}}
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
            private List<?> qc_img;

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

            public List<?> getQc_img() {
                return qc_img;
            }

            public void setQc_img(List<?> qc_img) {
                this.qc_img = qc_img;
            }

            public static class ProductionIdBean {
                /**
                 * order_id : 43156
                 * display_name : MO170507258
                 * product_id : {"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}
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
                     * product_id : 49921
                     * product_name : W150成品(RT通用+中文3C贴纸)-RT-CN
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
