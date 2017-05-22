package tarce.model.inventory;

import java.util.List;

/**
 * Created by rose.zou on 2017/5/22.
 */

public class ReworkRukuBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":40,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":44,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":6,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":7,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":50,"qc_img":[],"qc_fail_rate":0,"qty_produced":566,"qc_rate":8.8339222614841,"name":"QCF/201705190003","qc_fail_qty":0,"feedback_id":379,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":42201,"display_name":"MO/2017050442143","product_id":{"product_id":49790,"product_name":"W120成品(RT通用+中文3C贴纸)-RT-CN"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":291,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44615,"display_name":"MO170515182","product_id":{"product_id":47498,"product_name":"JP255-成品(鸡)-RT-CN"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":218,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44611,"display_name":"MO170515178","product_id":{"product_id":47162,"product_name":"JP232-成品(吉普车)-简装-RT-ENG"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":209,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44610,"display_name":"MO170515177","product_id":{"product_id":47174,"product_name":"JP232-成品(吉普车)-RT-CN"}}},{"qc_test_qty":120,"qc_img":[],"qc_fail_rate":0,"qty_produced":1150,"qc_rate":10.434782608695652,"name":"QCF/201705190002","qc_fail_qty":0,"feedback_id":294,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44608,"display_name":"MO170515175","product_id":{"product_id":46953,"product_name":"JP218-成品(副栉龙)-RT-CN"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":219,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44181,"display_name":"MO170512142","product_id":{"product_id":46949,"product_name":"JP218-成品(副栉龙)-简装-RT-ENG"}}},{"qc_test_qty":60,"qc_img":[],"qc_fail_rate":0,"qty_produced":100,"qc_rate":60,"name":"QCF/201705190078","qc_fail_qty":0,"feedback_id":370,"qc_note":"","state":"qc_success","production_id":{"order_id":44705,"display_name":"MO170516004","product_id":{"product_id":49789,"product_name":"W120-成品-RT-ENG"}}},{"qc_test_qty":60,"qc_img":[],"qc_fail_rate":0,"qty_produced":240,"qc_rate":25,"name":"QCF/201705190066","qc_fail_qty":0,"feedback_id":358,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44646,"display_name":"MO170515213","product_id":{"product_id":46856,"product_name":"JP213-成品(天鹅)-简装-RT-ENG"}}},{"qc_test_qty":120,"qc_img":[],"qc_fail_rate":0,"qty_produced":1822,"qc_rate":6.586169045005488,"name":"QCF/201705190045","qc_fail_qty":0,"feedback_id":337,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":42211,"display_name":"MO/2017050442153","product_id":{"product_id":46862,"product_name":"JP213-成品(天鹅)-RT-CN"}}},{"qc_test_qty":20,"qc_img":[],"qc_fail_rate":0,"qty_produced":2250,"qc_rate":0.8888888888888888,"name":"QCF/201705190028","qc_fail_qty":0,"feedback_id":407,"qc_note":"","state":"qc_success","production_id":{"order_id":44634,"display_name":"MO170515201","product_id":{"product_id":46277,"product_name":"F140-成品(土耳其别墅)-RT-CN"}}},{"qc_test_qty":30,"qc_img":[],"qc_fail_rate":0,"qty_produced":4367,"qc_rate":0.6869704602702084,"name":"QCF/201705200032","qc_fail_qty":0,"feedback_id":411,"qc_note":"","state":"qc_success","production_id":{"order_id":43204,"display_name":"MO170507306","product_id":{"product_id":44830,"product_name":"AM304-成品(旋转木马)-RT-CN"}}}],"res_msg":"","res_code":1}
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
         * res_data : [{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":40,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":44,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":6,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":7,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}},{"qc_test_qty":50,"qc_img":[],"qc_fail_rate":0,"qty_produced":566,"qc_rate":8.8339222614841,"name":"QCF/201705190003","qc_fail_qty":0,"feedback_id":379,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":42201,"display_name":"MO/2017050442143","product_id":{"product_id":49790,"product_name":"W120成品(RT通用+中文3C贴纸)-RT-CN"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":291,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44615,"display_name":"MO170515182","product_id":{"product_id":47498,"product_name":"JP255-成品(鸡)-RT-CN"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":218,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44611,"display_name":"MO170515178","product_id":{"product_id":47162,"product_name":"JP232-成品(吉普车)-简装-RT-ENG"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":209,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44610,"display_name":"MO170515177","product_id":{"product_id":47174,"product_name":"JP232-成品(吉普车)-RT-CN"}}},{"qc_test_qty":120,"qc_img":[],"qc_fail_rate":0,"qty_produced":1150,"qc_rate":10.434782608695652,"name":"QCF/201705190002","qc_fail_qty":0,"feedback_id":294,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44608,"display_name":"MO170515175","product_id":{"product_id":46953,"product_name":"JP218-成品(副栉龙)-RT-CN"}}},{"qc_test_qty":1,"qc_img":[],"qc_fail_rate":0,"qty_produced":0,"qc_rate":0,"name":false,"qc_fail_qty":0,"feedback_id":219,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44181,"display_name":"MO170512142","product_id":{"product_id":46949,"product_name":"JP218-成品(副栉龙)-简装-RT-ENG"}}},{"qc_test_qty":60,"qc_img":[],"qc_fail_rate":0,"qty_produced":100,"qc_rate":60,"name":"QCF/201705190078","qc_fail_qty":0,"feedback_id":370,"qc_note":"","state":"qc_success","production_id":{"order_id":44705,"display_name":"MO170516004","product_id":{"product_id":49789,"product_name":"W120-成品-RT-ENG"}}},{"qc_test_qty":60,"qc_img":[],"qc_fail_rate":0,"qty_produced":240,"qc_rate":25,"name":"QCF/201705190066","qc_fail_qty":0,"feedback_id":358,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":44646,"display_name":"MO170515213","product_id":{"product_id":46856,"product_name":"JP213-成品(天鹅)-简装-RT-ENG"}}},{"qc_test_qty":120,"qc_img":[],"qc_fail_rate":0,"qty_produced":1822,"qc_rate":6.586169045005488,"name":"QCF/201705190045","qc_fail_qty":0,"feedback_id":337,"qc_note":"点击输入品检批注","state":"qc_success","production_id":{"order_id":42211,"display_name":"MO/2017050442153","product_id":{"product_id":46862,"product_name":"JP213-成品(天鹅)-RT-CN"}}},{"qc_test_qty":20,"qc_img":[],"qc_fail_rate":0,"qty_produced":2250,"qc_rate":0.8888888888888888,"name":"QCF/201705190028","qc_fail_qty":0,"feedback_id":407,"qc_note":"","state":"qc_success","production_id":{"order_id":44634,"display_name":"MO170515201","product_id":{"product_id":46277,"product_name":"F140-成品(土耳其别墅)-RT-CN"}}},{"qc_test_qty":30,"qc_img":[],"qc_fail_rate":0,"qty_produced":4367,"qc_rate":0.6869704602702084,"name":"QCF/201705200032","qc_fail_qty":0,"feedback_id":411,"qc_note":"","state":"qc_success","production_id":{"order_id":43204,"display_name":"MO170507306","product_id":{"product_id":44830,"product_name":"AM304-成品(旋转木马)-RT-CN"}}}]
         * res_msg :
         * res_code : 1
         */

        private String res_msg;
        private int res_code;
        private List<ResDataBean> res_data;

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

        public List<ResDataBean> getRes_data() {
            return res_data;
        }

        public void setRes_data(List<ResDataBean> res_data) {
            this.res_data = res_data;
        }

        public static class ResDataBean {
            /**
             * qc_test_qty : 1.0
             * qc_img : []
             * qc_fail_rate : 0.0
             * qty_produced : 0.0
             * qc_rate : 0.0
             * name : false
             * qc_fail_qty : 0.0
             * feedback_id : 40
             * qc_note : 点击输入品检批注
             * state : qc_success
             * production_id : {"order_id":false,"display_name":false,"product_id":{"product_id":false,"product_name":false}}
             */

            private double qc_test_qty;
            private double qc_fail_rate;
            private double qty_produced;
            private double qc_rate;
            private boolean name;
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

            public boolean isName() {
                return name;
            }

            public void setName(boolean name) {
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
                 * order_id : false
                 * display_name : false
                 * product_id : {"product_id":false,"product_name":false}
                 */

                private boolean order_id;
                private boolean display_name;
                private ProductIdBean product_id;

                public boolean isOrder_id() {
                    return order_id;
                }

                public void setOrder_id(boolean order_id) {
                    this.order_id = order_id;
                }

                public boolean isDisplay_name() {
                    return display_name;
                }

                public void setDisplay_name(boolean display_name) {
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
                     * product_id : false
                     * product_name : false
                     */

                    private boolean product_id;
                    private boolean product_name;

                    public boolean isProduct_id() {
                        return product_id;
                    }

                    public void setProduct_id(boolean product_id) {
                        this.product_id = product_id;
                    }

                    public boolean isProduct_name() {
                        return product_name;
                    }

                    public void setProduct_name(boolean product_name) {
                        this.product_name = product_name;
                    }
                }
            }
        }
    }
}
