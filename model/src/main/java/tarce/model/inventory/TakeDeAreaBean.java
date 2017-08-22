package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zouzou on 2017/6/23.
 * 提交入库信息
 */

public class TakeDeAreaBean implements Serializable{
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"origin":"PO2017050405186","sale_note":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6101&model=stock.picking&field=qc_img","complete_rate":0,"parnter_id":"浙江佳龙电子有限公司（上海办事处）","name":"WHIN2017050501689","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6101&model=stock.picking&field=post_img","min_date":"2017-05-10 07:47:04","qc_note":false,"state":"qc_check","picking_type_code":"incoming","has_attachment":false,"post_area_id":{"area_id":84,"area_name":"A1A3"},"pack_operation_product_ids":[{"pack_id":112644,"qty_done":5000,"product_id":{"default_code":"22.041002.003","qty_available":0,"area_id":{"area_id":242,"area_name":"A3L2"},"id":48381,"name":"[22.041002.003] SS-12F46 MDxx0、VXX0控制盒两档拨动开关"},"product_qty":5000}],"delivery_rule":null,"picking_id":6101},"res_msg":"","res_code":1}
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

    public static class ResultBean implements Serializable{
        /**
         * res_data : {"origin":"PO2017050405186","sale_note":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6101&model=stock.picking&field=qc_img","complete_rate":0,"parnter_id":"浙江佳龙电子有限公司（上海办事处）","name":"WHIN2017050501689","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6101&model=stock.picking&field=post_img","min_date":"2017-05-10 07:47:04","qc_note":false,"state":"qc_check","picking_type_code":"incoming","has_attachment":false,"post_area_id":{"area_id":84,"area_name":"A1A3"},"pack_operation_product_ids":[{"pack_id":112644,"qty_done":5000,"product_id":{"default_code":"22.041002.003","qty_available":0,"area_id":{"area_id":242,"area_name":"A3L2"},"id":48381,"name":"[22.041002.003] SS-12F46 MDxx0、VXX0控制盒两档拨动开关"},"product_qty":5000}],"delivery_rule":null,"picking_id":6101}
         * res_msg :
         * res_code : 1
         */

        private TakeDelListBean.ResultBean.ResDataBean res_data;
        private String res_msg;
        private int res_code;

        public TakeDelListBean.ResultBean.ResDataBean getRes_data() {
            return res_data;
        }

        public void setRes_data(TakeDelListBean.ResultBean.ResDataBean res_data) {
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

        public static class ResDataBean implements Serializable{
            public Object getOrigin() {
                if (origin instanceof Boolean){
                    origin = "";
                }
                return origin;
            }

            public void setOrigin(Object origin) {
                this.origin = origin;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            /**
             * origin : PO2017050405186
             * sale_note : false
             * qc_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6101&model=stock.picking&field=qc_img
             * complete_rate : 0
             * parnter_id : 浙江佳龙电子有限公司（上海办事处）
             * name : WHIN2017050501689
             * post_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6101&model=stock.picking&field=post_img
             * min_date : 2017-05-10 07:47:04
             * qc_note : false
             * state : qc_check
             * picking_type_code : incoming
             * has_attachment : false
             * post_area_id : {"area_id":84,"area_name":"A1A3"}
             * pack_operation_product_ids : [{"pack_id":112644,"qty_done":5000,"product_id":{"default_code":"22.041002.003","qty_available":0,"area_id":{"area_id":242,"area_name":"A3L2"},"id":48381,"name":"[22.041002.003] SS-12F46 MDxx0、VXX0控制盒两档拨动开关"},"product_qty":5000}]
             * delivery_rule : null
             * picking_id : 6101
             */

            private String phone;
            private Object origin;
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
            private String qc_result;

            public String getQc_result() {
                return qc_result;
            }

            public void setSale_note(boolean sale_note) {
                this.sale_note = sale_note;
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
                    if (area_id instanceof Boolean){
                        area_name = "";
                    }
                    return area_name;
                }

                public void setArea_name(Object area_name) {
                    this.area_name = area_name;
                }

                /**
                 * area_id : 84
                 * area_name : A1A3
                 */

                private Object area_id;
                private Object area_name;


            }

            public static class PackOperationProductIdsBean implements Serializable{
                /**
                 * pack_id : 112644
                 * qty_done : 5000.0
                 * product_id : {"default_code":"22.041002.003","qty_available":0,"area_id":{"area_id":242,"area_name":"A3L2"},"id":48381,"name":"[22.041002.003] SS-12F46 MDxx0、VXX0控制盒两档拨动开关"}
                 * product_qty : 5000.0
                 */

                private int pack_id;
                private double qty_done;
                private ProductIdBean product_id;
                private double product_qty;
                private int rejects_qty;

                public int getRejects_qty() {
                    return rejects_qty;
                }

                public void setRejects_qty(int rejects_qty) {
                    this.rejects_qty = rejects_qty;
                }

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
                    /**
                     * default_code : 22.041002.003
                     * qty_available : 0.0
                     * area_id : {"area_id":242,"area_name":"A3L2"}
                     * id : 48381
                     * name : [22.041002.003] SS-12F46 MDxx0、VXX0控制盒两档拨动开关
                     */

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
                         * area_id : 242
                         * area_name : A3L2
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
                            if (area_id instanceof Boolean){
                                area_name = "";
                            }
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
