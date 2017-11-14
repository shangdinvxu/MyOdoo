package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockMoveListBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"name":"INV:--库清零","product_uom_qty":1830,"state":"done","location":"WHSY/--库","location_dest":"虚拟库/库存调整","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:HS贺卡国内成品盘点-6.4","product_uom_qty":1800,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人){RT-CN}","product_uom_qty":1830,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/--库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":64,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":60,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":4,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":5,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":5,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"MO/2017041629174","product_uom_qty":350,"state":"cancel","location":"虚拟库/生产库","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"MO/2017041528283","product_uom_qty":350,"state":"cancel","location":"虚拟库/生产库","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"MO/2017041528286","product_uom_qty":350,"state":"cancel","location":"虚拟库/生产库","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:HS贺卡","product_uom_qty":321,"state":"done","location":"WHSY/原料库A3","location_dest":"虚拟库/库存调整","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":1,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":5,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":60,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:s","product_uom_qty":708,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}}],"res_msg":"","res_code":1}
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
         * res_data : [{"name":"INV:--库清零","product_uom_qty":1830,"state":"done","location":"WHSY/--库","location_dest":"虚拟库/库存调整","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:HS贺卡国内成品盘点-6.4","product_uom_qty":1800,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人){RT-CN}","product_uom_qty":1830,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/--库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":64,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":60,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":4,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:INV:   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":5,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":5,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"done","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"MO/2017041629174","product_uom_qty":350,"state":"cancel","location":"虚拟库/生产库","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"MO/2017041528283","product_uom_qty":350,"state":"cancel","location":"虚拟库/生产库","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"MO/2017041528286","product_uom_qty":350,"state":"cancel","location":"虚拟库/生产库","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:HS贺卡","product_uom_qty":321,"state":"done","location":"WHSY/原料库A3","location_dest":"虚拟库/库存调整","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":1,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":5,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":60,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"INV:s","product_uom_qty":708,"state":"done","location":"虚拟库/库存调整","location_dest":"WHSY/原料库A3","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}},{"name":"[99.0HS310.000]   HS310-成品(星夜·追梦人)-RT-CN","product_uom_qty":30,"state":"cancel","location":"WHSY/原料库A3","location_dest":"业务伙伴库位/客户库","product_id":{"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}}]
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
             * name : INV:--库清零
             * product_uom_qty : 1830.0
             * state : done
             * location : WHSY/--库
             * location_dest : 虚拟库/库存调整
             * product_id : {"product_name":"[99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}","id":48204}
             */

            private String name;
            private double product_uom_qty;
            private String state;
            private String location;
            private String location_dest;
            private ProductIdBean product_id;
            private String write_uid;
            private String write_date;
            private String move_order_type;
            private String picking_id;
            private double quantity_adjusted_qty;

            public String getPicking_id() {
                return picking_id;
            }

            public void setPicking_id(String picking_id) {
                this.picking_id = picking_id;
            }

            public double getQuantity_adjusted_qty() {
                return quantity_adjusted_qty;
            }

            public void setQuantity_adjusted_qty(double quantity_adjusted_qty) {
                this.quantity_adjusted_qty = quantity_adjusted_qty;
            }

            public String getWrite_uid() {
                return write_uid;
            }

            public void setWrite_uid(String write_uid) {
                this.write_uid = write_uid;
            }

            public String getWrite_date() {
                return write_date;
            }

            public void setWrite_date(String write_date) {
                this.write_date = write_date;
            }

            public String getMove_order_type() {
                return move_order_type;
            }

            public void setMove_order_type(String move_order_type) {
                this.move_order_type = move_order_type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getProduct_uom_qty() {
                return product_uom_qty;
            }

            public void setProduct_uom_qty(double product_uom_qty) {
                this.product_uom_qty = product_uom_qty;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getLocation_dest() {
                return location_dest;
            }

            public void setLocation_dest(String location_dest) {
                this.location_dest = location_dest;
            }

            public ProductIdBean getProduct_id() {
                return product_id;
            }

            public void setProduct_id(ProductIdBean product_id) {
                this.product_id = product_id;
            }

            public static class ProductIdBean implements Serializable{
                /**
                 * product_name : [99.0HS310.000]   HS310-成品(星夜·追梦人){RT-CN}
                 * id : 48204
                 */

                private String product_name;
                private int id;

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}
