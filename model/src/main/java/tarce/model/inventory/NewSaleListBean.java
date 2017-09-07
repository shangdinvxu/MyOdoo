package tarce.model.inventory;

import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by zws on 2017/8/10.
 */

public class NewSaleListBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"waiting":2,"able_to":0,"waiting_data":[],"able_to_data":[{"origin":"SO1706092974","sale_note":"亚风已付","carrier_tracking_ref":false,"complete_rate":35,"pack_operation_product_ids":[{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1200.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46044,"name":"[99.0F1200.000] F120-成品(日本茶屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1210.000","qty_available":27,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46055,"name":"[99.0F1210.000] F121-成品(日本居酒屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1220.000","qty_available":57,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46066,"name":"[99.0F1220.000] F122-成品(日本杂货铺){RT-CN}"},"product_qty":0}],"number_of_packages":0,"emergency":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7848&model=stock.picking&field=qc_img","weight":0,"min_date":"2017-06-09 06:04:11","phone":"15868816989","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7848&model=stock.picking&field=post_img","shipping_weight":0,"post_area_id":{"area_id":null,"area_name":null},"location_id":"WHSY/原料库A3","qc_note":false,"parnter_id":"北京田玉兴, 姚梦薇,浙江省 金华市 婺城区仙瀑路398号云飞制伞内米马仓库","name":"WHOUT170609541","creater":"Administrator","picking_type":{"picking_type_name":"交货单","picking_type_id":4},"move_type":"direct","tracking_number":false,"qc_result":"no_result","priority":"1","back_order_id":"","state":"confirmed","carrier":false,"picking_type_code":"outgoing","has_attachment":false,"group_id":"SO1706092974","delivery_rule":"create_backorder","picking_id":7848},{"origin":"SO1706092989","sale_note":"包送货上门","carrier_tracking_ref":false,"complete_rate":15,"pack_operation_product_ids":[{"origin_qty":1,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0W1200.000","qty_available":0,"area_id":{"area_id":116,"area_name":"A1J2"},"id":49790,"name":"[99.0W1200.000] W120成品(RT通用+中文3C贴纸){RT-CN}"},"product_qty":0},{"origin_qty":26,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"},"product_qty":0},{"origin_qty":5,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0JP206.000","qty_available":0,"area_id":{"area_id":123,"area_name":"A1L1"},"id":46744,"name":"[99.0JP206.000] JP206-成品(蜜蜂){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0JP224.000","qty_available":0,"area_id":{"area_id":128,"area_name":"A1M2"},"id":47039,"name":"[99.0JP224.000] JP224-成品(袋鼠){RT-CN}"},"product_qty":0},{"origin_qty":30,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1200.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46044,"name":"[99.0F1200.000] F120-成品(日本茶屋){RT-CN}"},"product_qty":0},{"origin_qty":120,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0JP260.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":47509,"name":"[99.0JP260.000] JP260-成品(摩托车){RT-CN}"},"product_qty":0},{"origin_qty":90,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1340.000","qty_available":0,"area_id":{"area_id":128,"area_name":"A1M2"},"id":46213,"name":"[99.0F1340.000] F134-成品(英国服装店){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1260.000","qty_available":240,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46113,"name":"[99.0F1260.000] F126-成品(法国花屋){RT-CN}"},"product_qty":0}],"number_of_packages":0,"emergency":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=8038&model=stock.picking&field=qc_img","weight":0,"min_date":"2017-06-09 09:48:12","phone":"13716188191","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=8038&model=stock.picking&field=post_img","shipping_weight":0,"post_area_id":{"area_id":null,"area_name":null},"location_id":"WHSY/原料库A3","qc_note":false,"parnter_id":"北京田玉兴, 田玉兴,北京市朝阳区王四营南花园村中华文化和谐文化园","name":"WHOUT170626664","creater":"Administrator","picking_type":{"picking_type_name":"交货单","picking_type_id":4},"move_type":"direct","tracking_number":false,"qc_result":"no_result","priority":"1","back_order_id":"WHOUT170612609","state":"confirmed","carrier":false,"picking_type_code":"outgoing","has_attachment":false,"group_id":"SO1706092989","delivery_rule":"create_backorder","picking_id":8038}]},"res_msg":"","res_code":1}
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
         * res_data : {"waiting":2,"able_to":0,"waiting_data":[],"able_to_data":[{"origin":"SO1706092974","sale_note":"亚风已付","carrier_tracking_ref":false,"complete_rate":35,"pack_operation_product_ids":[{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1200.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46044,"name":"[99.0F1200.000] F120-成品(日本茶屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1210.000","qty_available":27,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46055,"name":"[99.0F1210.000] F121-成品(日本居酒屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1220.000","qty_available":57,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46066,"name":"[99.0F1220.000] F122-成品(日本杂货铺){RT-CN}"},"product_qty":0}],"number_of_packages":0,"emergency":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7848&model=stock.picking&field=qc_img","weight":0,"min_date":"2017-06-09 06:04:11","phone":"15868816989","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7848&model=stock.picking&field=post_img","shipping_weight":0,"post_area_id":{"area_id":null,"area_name":null},"location_id":"WHSY/原料库A3","qc_note":false,"parnter_id":"北京田玉兴, 姚梦薇,浙江省 金华市 婺城区仙瀑路398号云飞制伞内米马仓库","name":"WHOUT170609541","creater":"Administrator","picking_type":{"picking_type_name":"交货单","picking_type_id":4},"move_type":"direct","tracking_number":false,"qc_result":"no_result","priority":"1","back_order_id":"","state":"confirmed","carrier":false,"picking_type_code":"outgoing","has_attachment":false,"group_id":"SO1706092974","delivery_rule":"create_backorder","picking_id":7848},{"origin":"SO1706092989","sale_note":"包送货上门","carrier_tracking_ref":false,"complete_rate":15,"pack_operation_product_ids":[{"origin_qty":1,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0W1200.000","qty_available":0,"area_id":{"area_id":116,"area_name":"A1J2"},"id":49790,"name":"[99.0W1200.000] W120成品(RT通用+中文3C贴纸){RT-CN}"},"product_qty":0},{"origin_qty":26,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"},"product_qty":0},{"origin_qty":5,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0JP206.000","qty_available":0,"area_id":{"area_id":123,"area_name":"A1L1"},"id":46744,"name":"[99.0JP206.000] JP206-成品(蜜蜂){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0JP224.000","qty_available":0,"area_id":{"area_id":128,"area_name":"A1M2"},"id":47039,"name":"[99.0JP224.000] JP224-成品(袋鼠){RT-CN}"},"product_qty":0},{"origin_qty":30,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1200.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46044,"name":"[99.0F1200.000] F120-成品(日本茶屋){RT-CN}"},"product_qty":0},{"origin_qty":120,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0JP260.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":47509,"name":"[99.0JP260.000] JP260-成品(摩托车){RT-CN}"},"product_qty":0},{"origin_qty":90,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1340.000","qty_available":0,"area_id":{"area_id":128,"area_name":"A1M2"},"id":46213,"name":"[99.0F1340.000] F134-成品(英国服装店){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1260.000","qty_available":240,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46113,"name":"[99.0F1260.000] F126-成品(法国花屋){RT-CN}"},"product_qty":0}],"number_of_packages":0,"emergency":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=8038&model=stock.picking&field=qc_img","weight":0,"min_date":"2017-06-09 09:48:12","phone":"13716188191","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=8038&model=stock.picking&field=post_img","shipping_weight":0,"post_area_id":{"area_id":null,"area_name":null},"location_id":"WHSY/原料库A3","qc_note":false,"parnter_id":"北京田玉兴, 田玉兴,北京市朝阳区王四营南花园村中华文化和谐文化园","name":"WHOUT170626664","creater":"Administrator","picking_type":{"picking_type_name":"交货单","picking_type_id":4},"move_type":"direct","tracking_number":false,"qc_result":"no_result","priority":"1","back_order_id":"WHOUT170612609","state":"confirmed","carrier":false,"picking_type_code":"outgoing","has_attachment":false,"group_id":"SO1706092989","delivery_rule":"create_backorder","picking_id":8038}]}
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
             * waiting : 2
             * able_to : 0
             * waiting_data : []
             * able_to_data : [{"origin":"SO1706092974","sale_note":"亚风已付","carrier_tracking_ref":false,"complete_rate":35,"pack_operation_product_ids":[{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1200.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46044,"name":"[99.0F1200.000] F120-成品(日本茶屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1210.000","qty_available":27,"area_id":{"area_id":136,"area_name":"A1P2"},
             */

            private List<AbleToDataBean> waiting_data;
            private List<AbleToDataBean> able_to_data;

            public List<AbleToDataBean> getWaiting_data() {
                return waiting_data;
            }

            public void setWaiting_data(List<AbleToDataBean> waiting_data) {
                this.waiting_data = waiting_data;
            }

            public List<AbleToDataBean> getAble_to_data() {
                return able_to_data;
            }

            public void setAble_to_data(List<AbleToDataBean> able_to_data) {
                this.able_to_data = able_to_data;
            }

            public static class AbleToDataBean {
                /**
                 * origin : SO1706092974
                 * sale_note : 亚风已付
                 * carrier_tracking_ref : false
                 * complete_rate : 35
                 * pack_operation_product_ids : [{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1200.000","qty_available":0,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46044,"name":"[99.0F1200.000] F120-成品(日本茶屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1210.000","qty_available":27,"area_id":{"area_id":136,"area_name":"A1P2"},"id":46055,"name":"[99.0F1210.000] F121-成品(日本居酒屋){RT-CN}"},"product_qty":0},{"origin_qty":60,"pack_id":-1,"qty_done":0,"product_id":{"default_code":"99.0F1220.000","qty_available":57,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46066,"name":"[99.0F1220.000] F122-成品(日本杂货铺){RT-CN}"},"product_qty":0}]
                 * number_of_packages : 0
                 * emergency : false
                 * qc_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7848&model=stock.picking&field=qc_img
                 * weight : 0.0
                 * min_date : 2017-06-09 06:04:11
                 * phone : 15868816989
                 * post_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=7848&model=stock.picking&field=post_img
                 * shipping_weight : 0.0
                 * post_area_id : {"area_id":null,"area_name":null}
                 * location_id : WHSY/原料库A3
                 * qc_note : false
                 * parnter_id : 北京田玉兴, 姚梦薇,浙江省 金华市 婺城区仙瀑路398号云飞制伞内米马仓库
                 * name : WHOUT170609541
                 * creater : Administrator
                 * picking_type : {"picking_type_name":"交货单","picking_type_id":4}
                 * move_type : direct
                 * tracking_number : false
                 * qc_result : no_result
                 * prr : false
                 * pickiniority : 1
                 * back_order_id :
                 * state : confirmed
                 * carrieg_type_code : outgoing
                 * has_attachment : false
                 * group_id : SO1706092974
                 * delivery_rule : create_backorder
                 * picking_id : 7848
                 */

                private String origin;
                private boolean sale_note;
                private String carrier_tracking_ref;
                private int complete_rate;
                private int number_of_packages;
                private boolean emergency;
                private String qc_img;
                private double weight;
                private String min_date;
                private String phone;
                private String post_img;
                private double shipping_weight;
                private PostAreaIdBean post_area_id;
                private String location_id;
                private boolean qc_note;
                private String parnter_id;
                private String name;
                private String creater;
                private PickingTypeBean picking_type;
                private String move_type;
                private int tracking_number;
                private String qc_result;
                private String priority;
                private String back_order_id;
                private String state;
                private String carrier;
                private String picking_type_code;
                private boolean has_attachment;
                private Object group_id;
                private Object delivery_rule;
                private int picking_id;
                private List<PackOperationProductIdsBean> pack_operation_product_ids;
                private String partner_id;

                public String getPartner_id() {
                    return partner_id;
                }

                public void setPartner_id(String partner_id) {
                    this.partner_id = partner_id;
                }

                public Object getDelivery_rule() {
                    if (delivery_rule == null){
                        delivery_rule = "";
                    }
                    return delivery_rule;
                }

                public void setDelivery_rule(Object delivery_rule) {
                    this.delivery_rule = delivery_rule;
                }

                public boolean isSale_note() {
                    return sale_note;
                }

                public void setSale_note(boolean sale_note) {
                    this.sale_note = sale_note;
                }

                public boolean isQc_note() {
                    return qc_note;
                }

                public void setQc_note(boolean qc_note) {
                    this.qc_note = qc_note;
                }

                public String getCarrier_tracking_ref() {
                    return carrier_tracking_ref;
                }

                public void setCarrier_tracking_ref(String carrier_tracking_ref) {
                    this.carrier_tracking_ref = carrier_tracking_ref;
                }

                public String getCarrier() {
                    return carrier;
                }

                public void setCarrier(String carrier) {
                    this.carrier = carrier;
                }

                public Object getGroup_id() {
                    if (group_id instanceof Boolean){
                        group_id = "";
                    }
                    return group_id;
                }

                public void setGroup_id(Object group_id) {
                    this.group_id = group_id;
                }


                public int getTracking_number() {
                    return tracking_number;
                }

                public void setTracking_number(int tracking_number) {
                    this.tracking_number = tracking_number;
                }

                public String getOrigin() {
                    return origin;
                }

                public void setOrigin(String origin) {
                    this.origin = origin;
                }

                public int getComplete_rate() {
                    return complete_rate;
                }

                public void setComplete_rate(int complete_rate) {
                    this.complete_rate = complete_rate;
                }

                public int getNumber_of_packages() {
                    return number_of_packages;
                }

                public void setNumber_of_packages(int number_of_packages) {
                    this.number_of_packages = number_of_packages;
                }

                public boolean isEmergency() {
                    return emergency;
                }

                public void setEmergency(boolean emergency) {
                    this.emergency = emergency;
                }

                public String getQc_img() {
                    return qc_img;
                }

                public void setQc_img(String qc_img) {
                    this.qc_img = qc_img;
                }

                public double getWeight() {
                    return weight;
                }

                public void setWeight(double weight) {
                    this.weight = weight;
                }

                public String getMin_date() {
                    return min_date;
                }

                public void setMin_date(String min_date) {
                    this.min_date = min_date;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getPost_img() {
                    return post_img;
                }

                public void setPost_img(String post_img) {
                    this.post_img = post_img;
                }

                public double getShipping_weight() {
                    return shipping_weight;
                }

                public void setShipping_weight(double shipping_weight) {
                    this.shipping_weight = shipping_weight;
                }

                public PostAreaIdBean getPost_area_id() {
                    return post_area_id;
                }

                public void setPost_area_id(PostAreaIdBean post_area_id) {
                    this.post_area_id = post_area_id;
                }

                public String getLocation_id() {
                    return location_id;
                }

                public void setLocation_id(String location_id) {
                    this.location_id = location_id;
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

                public String getCreater() {
                    return creater;
                }

                public void setCreater(String creater) {
                    this.creater = creater;
                }

                public PickingTypeBean getPicking_type() {
                    return picking_type;
                }

                public void setPicking_type(PickingTypeBean picking_type) {
                    this.picking_type = picking_type;
                }

                public String getMove_type() {
                    return move_type;
                }

                public void setMove_type(String move_type) {
                    this.move_type = move_type;
                }


                public String getQc_result() {
                    return qc_result;
                }

                public void setQc_result(String qc_result) {
                    this.qc_result = qc_result;
                }

                public String getPriority() {
                    return priority;
                }

                public void setPriority(String priority) {
                    this.priority = priority;
                }

                public String getBack_order_id() {
                    return back_order_id;
                }

                public void setBack_order_id(String back_order_id) {
                    this.back_order_id = back_order_id;
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

                public static class PostAreaIdBean {
                    /**
                     * area_id : null
                     * area_name : null
                     */

                    private	Object	area_id;	/*Object*/
                    private	Object	area_name;	/*Object*/

                    public void setArea_id(Object value){
                        this.area_id = value;
                    }
                    public Object getArea_id(){
                        if (area_id instanceof Boolean){
                            area_id = 0;
                        }
                        return this.area_id;
                    }

                    public void setArea_name(Object value){
                        this.area_name = value;
                    }
                    public Object getArea_name(){
                        if (area_name instanceof String){
                            area_name = "";
                        }
                        return this.area_name;
                    }
                }

                public static class PickingTypeBean {
                    /**
                     * picking_type_name : 交货单
                     * picking_type_id : 4
                     */

                    private String picking_type_name;
                    private int picking_type_id;

                    public String getPicking_type_name() {
                        return picking_type_name;
                    }

                    public void setPicking_type_name(String picking_type_name) {
                        this.picking_type_name = picking_type_name;
                    }

                    public int getPicking_type_id() {
                        return picking_type_id;
                    }

                    public void setPicking_type_id(int picking_type_id) {
                        this.picking_type_id = picking_type_id;
                    }
                }

                public static class PackOperationProductIdsBean {
                    /**
                     * origin_qty : 60.0
                     * pack_id : -1
                     * qty_done : 0
                     * product_id : {"default_code":"99.0F1230.000","qty_available":0,"area_id":{"area_id":132,"area_name":"A1N2"},"id":46078,"name":"[99.0F1230.000] F123-成品(日本甜品店){RT-CN}"}
                     * product_qty : 0
                     */

                    private double origin_qty;
                    private int pack_id;
                    private int qty_done;
                    private ProductIdBean product_id;
                    private int product_qty;

                    public double getOrigin_qty() {
                        return origin_qty;
                    }

                    public void setOrigin_qty(double origin_qty) {
                        this.origin_qty = origin_qty;
                    }

                    public int getPack_id() {
                        return pack_id;
                    }

                    public void setPack_id(int pack_id) {
                        this.pack_id = pack_id;
                    }

                    public int getQty_done() {
                        return qty_done;
                    }

                    public void setQty_done(int qty_done) {
                        this.qty_done = qty_done;
                    }

                    public ProductIdBean getProduct_id() {
                        return product_id;
                    }

                    public void setProduct_id(ProductIdBean product_id) {
                        this.product_id = product_id;
                    }

                    public int getProduct_qty() {
                        return product_qty;
                    }

                    public void setProduct_qty(int product_qty) {
                        this.product_qty = product_qty;
                    }

                    public static class ProductIdBean {
                        /**
                         * default_code : 99.0F1230.000
                         * qty_available : 0.0
                         * area_id : {"area_id":132,"area_name":"A1N2"}
                         * id : 46078
                         * name : [99.0F1230.000] F123-成品(日本甜品店){RT-CN}
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

                        public static class AreaIdBean {
                            /**
                             * area_id : 132
                             * area_name : A1N2
                             */

                            private	Object	area_id;	/*Object*/
                            private	Object	area_name;	/*Object*/

                            public void setArea_id(Object value){
                                this.area_id = value;
                            }
                            public Object getArea_id(){
                                if (area_id instanceof Boolean){
                                    area_id = 0;
                                }
                                return this.area_id;
                            }

                            public void setArea_name(Object value){
                                this.area_name = value;
                            }
                            public Object getArea_name(){
                                if (area_name instanceof String){
                                    area_name = "";
                                }
                                return this.area_name;
                            }
                        }
                    }
                }
            }
        }
    }
}
