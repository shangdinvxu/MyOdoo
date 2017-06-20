package tarce.model.inventory;

import com.google.zxing.common.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rose.zou on 2017/6/2.
 * 等待品检返回的数据
 */

public class QcFeedbaskBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":520,"qc_rate":0,"name":"QCF/201705270103","qc_fail_qty":0,"feedback_id":723,"qc_note":"","state":"draft","production_id":{"order_id":43156,"display_name":"MO170507258","product_id":{"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":160,"qc_rate":0,"name":"QCF/201705250063","qc_fail_qty":0,"feedback_id":683,"qc_note":"","state":"draft","production_id":{"order_id":47705,"display_name":"MO1705231848","product_id":{"product_id":71790,"product_name":"MA05-成品(狼 )-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250078","qc_fail_qty":0,"feedback_id":698,"qc_note":"","state":"draft","production_id":{"order_id":47839,"display_name":"MO170524062","product_id":{"product_id":71771,"product_name":"W120-成品-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":300,"qc_rate":0,"name":"QCF/201705250064","qc_fail_qty":0,"feedback_id":684,"qc_note":"","state":"draft","production_id":{"order_id":47651,"display_name":"MO1705231794","product_id":{"product_id":71766,"product_name":"DV183-成品(别墅组合-书房家具 )-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250080","qc_fail_qty":0,"feedback_id":700,"qc_note":"","state":"draft","production_id":{"order_id":47655,"display_name":"MO1705231798","product_id":{"product_id":71770,"product_name":"W110-成品-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250081","qc_fail_qty":0,"feedback_id":701,"qc_note":"","state":"draft","production_id":{"order_id":47654,"display_name":"MO1705231797","product_id":{"product_id":71769,"product_name":"P220S-成品-裱纸 -秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250079","qc_fail_qty":0,"feedback_id":699,"qc_note":"","state":"draft","production_id":{"order_id":47653,"display_name":"MO1705231796","product_id":{"product_id":71768,"product_name":"P210S-成品-裱纸 -秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":300,"qc_rate":0,"name":"QCF/201705250092","qc_fail_qty":0,"feedback_id":712,"qc_note":"","state":"draft","production_id":{"order_id":46828,"display_name":"MO170523972","product_id":{"product_id":48308,"product_name":"MA03-5-成品(雷龙)-RT-ENG"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250052","qc_fail_qty":0,"feedback_id":672,"qc_note":"","state":"draft","production_id":{"order_id":45982,"display_name":"MO170523126","product_id":{"product_id":48302,"product_name":"MA03-3-成品(霸王龙)-RT-ENG-库存用完换MA13"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":600,"qc_rate":0,"name":"QCF/201705250085","qc_fail_qty":0,"feedback_id":705,"qc_note":"","state":"draft","production_id":{"order_id":45981,"display_name":"MO170523125","product_id":{"product_id":48299,"product_name":"MA03-2-成品(剑龙)-RT-ENG"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":300,"qc_rate":0,"name":"QCF/201705250082","qc_fail_qty":0,"feedback_id":702,"qc_note":"","state":"draft","production_id":{"order_id":45980,"display_name":"MO170523124","product_id":{"product_id":48296,"product_name":"MA03-1-成品(三角龙)-RT-ENG"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":17000,"qc_rate":0,"name":"QCF/201705200064","qc_fail_qty":0,"feedback_id":443,"qc_note":"","state":"draft","production_id":{"order_id":45564,"display_name":"MO170520006","product_id":{"product_id":67352,"product_name":"DZ403成品(马槽)-法国DAKTARI"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":40,"qc_rate":0,"name":"QCF/201705230045","qc_fail_qty":0,"feedback_id":547,"qc_note":"","state":"draft","production_id":{"order_id":29323,"display_name":"MO/2017041629280","product_id":{"product_id":51449,"product_name":"BYH_514w-八音盒_514w-AM4XX-八音盒亚克力展架Y(新款八音盒-有声五个装)-外贸无LOGO版"}}}],"res_msg":"","res_code":1}
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
         * res_data : [{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":520,"qc_rate":0,"name":"QCF/201705270103","qc_fail_qty":0,"feedback_id":723,"qc_note":"","state":"draft","production_id":{"order_id":43156,"display_name":"MO170507258","product_id":{"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":160,"qc_rate":0,"name":"QCF/201705250063","qc_fail_qty":0,"feedback_id":683,"qc_note":"","state":"draft","production_id":{"order_id":47705,"display_name":"MO1705231848","product_id":{"product_id":71790,"product_name":"MA05-成品(狼 )-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250078","qc_fail_qty":0,"feedback_id":698,"qc_note":"","state":"draft","production_id":{"order_id":47839,"display_name":"MO170524062","product_id":{"product_id":71771,"product_name":"W120-成品-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":300,"qc_rate":0,"name":"QCF/201705250064","qc_fail_qty":0,"feedback_id":684,"qc_note":"","state":"draft","production_id":{"order_id":47651,"display_name":"MO1705231794","product_id":{"product_id":71766,"product_name":"DV183-成品(别墅组合-书房家具 )-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250080","qc_fail_qty":0,"feedback_id":700,"qc_note":"","state":"draft","production_id":{"order_id":47655,"display_name":"MO1705231798","product_id":{"product_id":71770,"product_name":"W110-成品-秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250081","qc_fail_qty":0,"feedback_id":701,"qc_note":"","state":"draft","production_id":{"order_id":47654,"display_name":"MO1705231797","product_id":{"product_id":71769,"product_name":"P220S-成品-裱纸 -秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250079","qc_fail_qty":0,"feedback_id":699,"qc_note":"","state":"draft","production_id":{"order_id":47653,"display_name":"MO1705231796","product_id":{"product_id":71768,"product_name":"P210S-成品-裱纸 -秘鲁minimudo"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":300,"qc_rate":0,"name":"QCF/201705250092","qc_fail_qty":0,"feedback_id":712,"qc_note":"","state":"draft","production_id":{"order_id":46828,"display_name":"MO170523972","product_id":{"product_id":48308,"product_name":"MA03-5-成品(雷龙)-RT-ENG"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":200,"qc_rate":0,"name":"QCF/201705250052","qc_fail_qty":0,"feedback_id":672,"qc_note":"","state":"draft","production_id":{"order_id":45982,"display_name":"MO170523126","product_id":{"product_id":48302,"product_name":"MA03-3-成品(霸王龙)-RT-ENG-库存用完换MA13"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":600,"qc_rate":0,"name":"QCF/201705250085","qc_fail_qty":0,"feedback_id":705,"qc_note":"","state":"draft","production_id":{"order_id":45981,"display_name":"MO170523125","product_id":{"product_id":48299,"product_name":"MA03-2-成品(剑龙)-RT-ENG"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":300,"qc_rate":0,"name":"QCF/201705250082","qc_fail_qty":0,"feedback_id":702,"qc_note":"","state":"draft","production_id":{"order_id":45980,"display_name":"MO170523124","product_id":{"product_id":48296,"product_name":"MA03-1-成品(三角龙)-RT-ENG"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":17000,"qc_rate":0,"name":"QCF/201705200064","qc_fail_qty":0,"feedback_id":443,"qc_note":"","state":"draft","production_id":{"order_id":45564,"display_name":"MO170520006","product_id":{"product_id":67352,"product_name":"DZ403成品(马槽)-法国DAKTARI"}}},{"qc_test_qty":0,"qc_img":[],"qc_fail_rate":0,"qty_produced":40,"qc_rate":0,"name":"QCF/201705230045","qc_fail_qty":0,"feedback_id":547,"qc_note":"","state":"draft","production_id":{"order_id":29323,"display_name":"MO/2017041629280","product_id":{"product_id":51449,"product_name":"BYH_514w-八音盒_514w-AM4XX-八音盒亚克力展架Y(新款八音盒-有声五个装)-外贸无LOGO版"}}}]
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

        public static class ResDataBean implements Serializable{
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
             * state : draft
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

            public static class ProductionIdBean implements Serializable{
                public Object getOrder_id() {
                    if (order_id instanceof Boolean){
                        order_id = 0;
                    }else if (order_id instanceof Double){
                        order_id = new Double((Double) order_id).intValue();
                    }
                    return order_id;
                }

                public void setOrder_id(Object order_id) {
                    this.order_id = order_id;
                }

                public Object getDisplay_name() {
                    if (display_name instanceof Boolean){
                        display_name = "";
                    }
                    return display_name;
                }

                public void setDisplay_name(Object display_name) {
                    this.display_name = display_name;
                }

                /**
                 * order_id : 43156
                 * display_name : MO170507258
                 * product_id : {"product_id":49921,"product_name":"W150成品(RT通用+中文3C贴纸)-RT-CN"}
                 */

                private Object order_id;
                private Object display_name;
                private ProductIdBean product_id;



                public ProductIdBean getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(ProductIdBean product_id) {
                    this.product_id = product_id;
                }

                public static class ProductIdBean implements Serializable{
                    public Object getProduct_id() {
                        if (product_id instanceof Boolean){
                            product_id = 0;
                        }
                        return product_id;
                    }

                    public void setProduct_id(Object product_id) {
                        this.product_id = product_id;
                    }

                    public Object getProduct_name() {
                        if (product_name instanceof Boolean){
                            product_name = "";
                        }
                        return product_name;
                    }

                    public void setProduct_name(Object product_name) {
                        this.product_name = product_name;
                    }

                    /**
                     * product_id : 49921
                     * product_name : W150成品(RT通用+中文3C贴纸)-RT-CN
                     */

                    private Object product_id;
                    private Object product_name;
                }
            }
        }
    }
}
