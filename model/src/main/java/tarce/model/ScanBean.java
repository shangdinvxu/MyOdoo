package tarce.model;

import java.util.List;

/**
 * Created by zouwansheng on 2017/12/20.
 */

public class ScanBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"feedback":{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":120,"qc_rate":0,"name":"QCF/201709130028","qc_fail_qty":0,"feedback_id":8986,"qc_note":"","state":"qc_ing","production_id":{"order_id":87077,"display_name":"MO170908120","product_id":{"area_name":false,"product_id":74182,"product_name":"F135-成品(英国红茶馆){宁波启路}"}}}}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

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
         * feedback : {"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":120,"qc_rate":0,"name":"QCF/201709130028","qc_fail_qty":0,"feedback_id":8986,"qc_note":"","state":"qc_ing","production_id":{"order_id":87077,"display_name":"MO170908120","product_id":{"area_name":false,"product_id":74182,"product_name":"F135-成品(英国红茶馆){宁波启路}"}}}
         */

        private FeedbackBean feedback;

        public FeedbackBean getFeedback() {
            return feedback;
        }

        public void setFeedback(FeedbackBean feedback) {
            this.feedback = feedback;
        }

        public static class FeedbackBean {
            /**
             * qc_test_qty : 0.0
             * qc_img : []
             * qc_fail_rate : 0.0
             * qty_produced : 120.0
             * qc_rate : 0.0
             * name : QCF/201709130028
             * qc_fail_qty : 0.0
             * feedback_id : 8986
             * qc_note :
             * state : qc_ing
             * production_id : {"order_id":87077,"display_name":"MO170908120","product_id":{"area_name":false,"product_id":74182,"product_name":"F135-成品(英国红茶馆){宁波启路}"}}
             */

            private double qc_test_qty;
            private double qc_fail_rate;
            private double qty_produced;
            private double qc_rate;
            private Object name;
            private double qc_fail_qty;
            private Object feedback_id;
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

            public Object getName() {
                if (name instanceof Boolean){
                    name = "";
                }
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public double getQc_fail_qty() {
                return qc_fail_qty;
            }

            public void setQc_fail_qty(double qc_fail_qty) {
                this.qc_fail_qty = qc_fail_qty;
            }

            public Object getFeedback_id() {
                if (feedback_id instanceof Boolean){
                    feedback_id = -1;
                }
                return feedback_id;
            }

            public void setFeedback_id(Object feedback_id) {
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
                 * order_id : 87077
                 * display_name : MO170908120
                 * product_id : {"area_name":false,"product_id":74182,"product_name":"F135-成品(英国红茶馆){宁波启路}"}
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
                     * area_name : false
                     * product_id : 74182
                     * product_name : F135-成品(英国红茶馆){宁波启路}
                     */

                    private Object area_name;
                    private int product_id;
                    private String product_name;

                    public Object getArea_name() {
                        if (area_name instanceof Boolean){
                            area_name = "";
                        }
                        return area_name;
                    }

                    public void setArea_name(Object area_name) {
                        this.area_name = area_name;
                    }

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
