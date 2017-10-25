package tarce.model.inventory;

import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by rose.zou on 2017/6/19.
 * 点击返回按键
 */

public class DoUnreservBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"origin":"SO1706123000","sale_note":"亚风已付","qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7939&model=stock.picking&field=qc_img","complete_rate":100,"parnter_id":"上海巧磁文化创意有限公司, 李春梅,上海市浦东新区世纪大道2000号上海科技馆6号门","name":"WHOUT170612619","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7939&model=stock.picking&field=post_img","min_date":"2017-06-12 07:01:54","qc_note":false,"state":"confirmed","picking_type_code":"outgoing","has_attachment":false,"post_area_id":{"area_id":null,"area_name":null},"pack_operation_product_ids":[],"delivery_rule":"create_backorder","picking_id":7939},"res_msg":"","res_code":1}
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
         * res_data : {"origin":"SO1706123000","sale_note":"亚风已付","qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7939&model=stock.picking&field=qc_img","complete_rate":100,"parnter_id":"上海巧磁文化创意有限公司, 李春梅,上海市浦东新区世纪大道2000号上海科技馆6号门","name":"WHOUT170612619","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7939&model=stock.picking&field=post_img","min_date":"2017-06-12 07:01:54","qc_note":false,"state":"confirmed","picking_type_code":"outgoing","has_attachment":false,"post_area_id":{"area_id":null,"area_name":null},"pack_operation_product_ids":[],"delivery_rule":"create_backorder","picking_id":7939}
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
            public Object getOrigin() {
                if (origin instanceof Boolean){
                    origin = "";
                }
                return origin;
            }

            public void setOrigin(Object origin) {
                this.origin = origin;
            }

            /**
             * origin : SO1706123000
             * sale_note : 亚风已付
             * qc_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7939&model=stock.picking&field=qc_img
             * complete_rate : 100
             * parnter_id : 上海巧磁文化创意有限公司, 李春梅,上海市浦东新区世纪大道2000号上海科技馆6号门
             * name : WHOUT170612619
             * post_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7939&model=stock.picking&field=post_img
             * min_date : 2017-06-12 07:01:54
             * qc_note : false
             * state : confirmed
             * picking_type_code : outgoing
             * has_attachment : false
             * post_area_id : {"area_id":null,"area_name":null}
             * pack_operation_product_ids : []
             * delivery_rule : create_backorder
             * picking_id : 7939
             */

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
            private boolean qc_note;
            private String state;
            private String picking_type_code;
            private boolean has_attachment;
            private PostAreaIdBean post_area_id;
            private String delivery_rule;
            private int picking_id;
            private List<?> pack_operation_product_ids;



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

            public boolean isQc_note() {
                return qc_note;
            }

            public void setQc_note(boolean qc_note) {
                this.qc_note = qc_note;
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

            public String getDelivery_rule() {
                return delivery_rule;
            }

            public void setDelivery_rule(String delivery_rule) {
                this.delivery_rule = delivery_rule;
            }

            public int getPicking_id() {
                return picking_id;
            }

            public void setPicking_id(int picking_id) {
                this.picking_id = picking_id;
            }

            public List<?> getPack_operation_product_ids() {
                return pack_operation_product_ids;
            }

            public void setPack_operation_product_ids(List<?> pack_operation_product_ids) {
                this.pack_operation_product_ids = pack_operation_product_ids;
            }

            public static class PostAreaIdBean {
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
