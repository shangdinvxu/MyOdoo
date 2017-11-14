package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by zouzou on 2017/6/23.
 * 收货列表
 */

public class TakeDelListBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{.lable":20,"area_id":{"area_id":222,"area_name":"A3F2"},"id":45039,"name":"[49.1AW306.100] AW306-说明书(独角兽头){done":0,"product_id":{"del},"id":69906,"name":"[46.1A4002.159] A400-彩盒正面不干胶-亚马逊wowood"},"product_qty":1030},{"pack_id":144500,"qty_done":0,"product_il},"pack_operation_product_ids":[{"pack_id":144505,"qty_done":0,"product_id":{"default_code":"51.400000.010","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":72683,"name":"[51.400000.010] 六色颜料(红蓝绿绿黑白)-宁波怡人"},"product_qty":12600},{"pack_id":144506,"qty_done":0,"product_id":{"default_code":"51.400000.008","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":72681,"name":"[51.400000.008] 六色颜料(红蓝蓝绿黑白)-宁波怡人"},"product_qty":12600}],"delivery_rule":null,"picking_id":7813}],"res_msg":"","res_code":1}
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

    public static class ResultBean implements Serializable{
        /**
         * res_data : [{"origin":"PO2017050405266","sale_note":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6140&model=stock.picking&field=qc_img","complete_rate":0,"parnter_id":"DIY工厂-李靖","name":"WHIN2017050501699","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6140&model=stock.picking&field=post_img","min_date":"2017-05-12 07:29:35","qc_note":false,"state":"assigned","picking_type_code":"incoming","{""post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7954&model=stock.picking&field=post_img","y":1030},{_code":"51.400000.008","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":72681,"name":"[51.400000.008] 六色颜料(红蓝蓝绿黑白)-宁波怡人"},"product_qty":12600}],"delivery_rule":null,"picking_id":7813}]
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
            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public Object getRemark() {
                if (remark instanceof Boolean){
                    remark = "";
                }
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            /**
             * origin : PO2017050405266
             * sale_note : false
             * qc_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6140&model=stock.picking&field=qc_img
             * complete_rate : 0
             * parnter_id : DIY工厂-李靖
             * name : WHIN2017050501699
             * post_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6140&model=stock.picking&field=post_img
             * min_date : 2017-05-12 07:29:35
             * qc_note : false
             * state : assigned
             * picking_type_code : incoming
             * has_attachment : false
             * post_area_id : {"area_id":null,"area_name":null}
             * pack_operation_product_ids : [{"pack_id":112704,"qty_done":0,"product_id":{"default_code":"98.DG104A.100","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":70017,"name":"[98.DG104A.100] DG104A-组装好成品(凯西花房)-RT"},"product_qty":5},{"pack_id":112705,"qty_done":0,"product_id":{"default_code":"98.0DG106.100.00","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":52512,"name":"[98.0DG106.100.00] DG106-成品(卡斯的音乐客厅){RT-ENG}"},"product_qty":600},{"pack_id":112706,"qty_done":0,"product_id":{"default_code":"98.0DG105.100.00","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":52507,"name":"[98.0DG105.100.00] DG105-成品(杰森的美味厨房){RT-ENG}"},"product_qty":600}]
             * delivery_rule : null
             * picking_id : 6140
             */


            private Object remark;
            private double weight;
            private String origin;

            public Object getSale_note() {
                if (sale_note instanceof Boolean){
                    sale_note = "";
                }
                return sale_note;
            }

            public void setSale_note(Object sale_note) {
                this.sale_note = sale_note;
            }

            private Object sale_note;
            private String qc_img;
            private int complete_rate;
            private String parnter_id;
            private String name;
            private String post_img;
            private String min_date;

            public String getQc_result() {
                return qc_result;
            }

            public void setQc_result(String qc_result) {
                this.qc_result = qc_result;
            }

            private String qc_result;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            private String phone;

            public Object getQc_note() {
                if (qc_note instanceof Boolean){
                    qc_note = "";
                }
                return qc_note;
            }

            public void setQc_note(Object qc_note) {
                this.qc_note = qc_note;
            }

            private Object qc_note;
            private String state;
            private String picking_type_code;
            private boolean has_attachment;
            private PostAreaIdBean post_area_id;
            private Object delivery_rule;
            private int picking_id;
            private List<PackOperationProductIdsBean> pack_operation_product_ids;

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }


            public String getQc_img() {
                return qc_img;
            }

            public void setQc_img(String qc_img) {
                this.qc_img = qc_img;
            }

            public int getComplete_rate() {
                return complete_rate;
            }

            public void setComplete_rate(int complete_rate) {
                this.complete_rate = complete_rate;
            }

            public String getParnter_id() {
                return parnter_id;
            }

            public void setParnter_id(String parnter_id) {
                this.parnter_id = parnter_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPost_img() {
                return post_img;
            }

            public void setPost_img(String post_img) {
                this.post_img = post_img;
            }

            public String getMin_date() {
                return min_date;
            }

            public void setMin_date(String min_date) {
                this.min_date = min_date;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getPicking_type_code() {
                return picking_type_code;
            }

            public void setPicking_type_code(String picking_type_code) {
                this.picking_type_code = picking_type_code;
            }

            public boolean isHas_attachment() {
                return has_attachment;
            }

            public void setHas_attachment(boolean has_attachment) {
                this.has_attachment = has_attachment;
            }

            public PostAreaIdBean getPost_area_id() {
                return post_area_id;
            }

            public void setPost_area_id(PostAreaIdBean post_area_id) {
                this.post_area_id = post_area_id;
            }

            public Object getDelivery_rule() {
                if (delivery_rule instanceof String){
                    delivery_rule = String.valueOf(delivery_rule);
                }
                return delivery_rule;
            }

            public void setDelivery_rule(Object delivery_rule) {
                this.delivery_rule = delivery_rule;
            }

            public int getPicking_id() {
                return picking_id;
            }

            public void setPicking_id(int picking_id) {
                this.picking_id = picking_id;
            }

            public List<PackOperationProductIdsBean> getPack_operation_product_ids() {
                return pack_operation_product_ids;
            }

            public void setPack_operation_product_ids(List<PackOperationProductIdsBean> pack_operation_product_ids) {
                this.pack_operation_product_ids = pack_operation_product_ids;
            }

            public static class PostAreaIdBean implements Serializable{
                /**
                 * area_id : null
                 * area_name : null
                 */

                private Object area_id;
                private Object area_name;

                public Object getArea_id() {
                    if (area_id instanceof Boolean){
                        area_id = 0;
                    }
                    return area_id;
                }

                public void setArea_id(Object area_id) {
                    this.area_id = area_id;
                }

                public Object getArea_name() {
                    if (area_name instanceof Boolean){
                        area_name = "";
                    }
                    return area_name;
                }

                public void setArea_name(Object area_name) {
                    this.area_name = area_name;
                }
            }

            public static class PackOperationProductIdsBean implements Serializable{
                public int getRejects_qty() {
                    return rejects_qty;
                }

                public void setRejects_qty(int rejects_qty) {
                    this.rejects_qty = rejects_qty;
                }

                /**
                 * pack_id : 112704
                 * qty_done : 0.0
                 * product_id : {"default_code":"98.DG104A.100","qty_available":0,"area_id":{"area_id":null,"area_name":null},"id":70017,"name":"[98.DG104A.100] DG104A-组装好成品(凯西花房)-RT"}
                 * product_qty : 5.0
                 */

                private int rejects_qty;
                private int pack_id;
                private double qty_done;
                private ProductIdBean product_id;
                private double product_qty;

                public int getPack_id() {
                    return pack_id;
                }

                public void setPack_id(int pack_id) {
                    this.pack_id = pack_id;
                }

                public double getQty_done() {
                    return qty_done;
                }

                public void setQty_done(double qty_done) {
                    this.qty_done = qty_done;
                }

                public ProductIdBean getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(ProductIdBean product_id) {
                    this.product_id = product_id;
                }

                public double getProduct_qty() {
                    return product_qty;
                }

                public void setProduct_qty(double product_qty) {
                    this.product_qty = product_qty;
                }

                public static class ProductIdBean implements Serializable{
                    public String getProduct_specs() {
                        return product_specs;
                    }

                    public void setProduct_specs(String product_specs) {
                        this.product_specs = product_specs;
                    }

                    public double getWeight() {
                        return weight;
                    }

                    public void setWeight(double weight) {
                        this.weight = weight;
                    }

                    /**
                     * default_code : 98.DG104A.100
                     * qty_available : 0.0
                     * area_id : {"area_id":null,"area_name":null}
                     * id : 70017
                     * name : [98.DG104A.100] DG104A-组装好成品(凯西花房)-RT
                     */

                    private double weight;
                    private String product_specs;
                    private String default_code;
                    private double qty_available;
                    private AreaIdBean area_id;
                    private int id;
                    private String name;

                    public String getDefault_code() {
                        return default_code;
                    }

                    public void setDefault_code(String default_code) {
                        this.default_code = default_code;
                    }

                    public double getQty_available() {
                        return qty_available;
                    }

                    public void setQty_available(double qty_available) {
                        this.qty_available = qty_available;
                    }

                    public AreaIdBean getArea_id() {
                        return area_id;
                    }

                    public void setArea_id(AreaIdBean area_id) {
                        this.area_id = area_id;
                    }

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

                    public static class AreaIdBean implements Serializable{
                        /**
                         * area_id : null
                         * area_name : null
                         */

                        private Object area_id;
                        private Object area_name;

                        public Object getArea_id() {
                            return area_id;
                        }

                        public void setArea_id(Object area_id) {
                            this.area_id = area_id;
                        }

                        public Object getArea_name() {
                            return area_name;
                        }

                        public void setArea_name(Object area_name) {
                            this.area_name = area_name;
                        }
                    }
                }
            }
        }
    }
}
