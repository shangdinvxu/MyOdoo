package tarce.model;

import android.widget.HeterogeneousExpandableList;

import java.util.List;

/**
 * Created by zouwansheng on 2017/9/8.
 */

public class ProjectDetailBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"create_uid":"王亚飞peter","remark":"asd","create_date":"2017-09-08","line_ids":[{"name":"全检制程品-MA02-5-木板(大象)-RT","location":"库存调整","quantity_available":0,"product_qty":100,"quantity_done":0,"id":1533,"reserve":0}],"picking_cause":"asdfas","delivery_date":"2017-09-27"},"res_msg":"","res_code":1}
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
         * res_data : {"create_uid":"王亚飞peter","remark":"asd","create_date":"2017-09-08","line_ids":[{"name":"全检制程品-MA02-5-木板(大象)-RT","location":"库存调整","quantity_available":0,"product_qty":100,"quantity_done":0,"id":1533,"reserve":0}],"picking_cause":"asdfas","delivery_date":"2017-09-27"}
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
             * create_uid : 王亚飞peter
             * remark : asd
             * create_date : 2017-09-08
             * line_ids : [{"name":"全检制程品-MA02-5-木板(大象)-RT","location":"库存调整","quantity_available":0,"product_qty":100,"quantity_done":0,"id":1533,"reserve":0}]
             * picking_cause : asdfas
             * delivery_date : 2017-09-27
             */

            private String create_uid;
            private Object remark;
            private String create_date;
            private String picking_cause;
            private String delivery_date;
            private List<LineIdsBean> line_ids;

            public String getCreate_uid() {
                return create_uid;
            }

            public void setCreate_uid(String create_uid) {
                this.create_uid = create_uid;
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

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getPicking_cause() {
                return picking_cause;
            }

            public void setPicking_cause(String picking_cause) {
                this.picking_cause = picking_cause;
            }

            public String getDelivery_date() {
                return delivery_date;
            }

            public void setDelivery_date(String delivery_date) {
                this.delivery_date = delivery_date;
            }

            public List<LineIdsBean> getLine_ids() {
                return line_ids;
            }

            public void setLine_ids(List<LineIdsBean> line_ids) {
                this.line_ids = line_ids;
            }

            public static class LineIdsBean {
                /**
                 * name : 全检制程品-MA02-5-木板(大象)-RT
                 * location : 库存调整
                 * quantity_available : 0.0
                 * product_qty : 100.0
                 * quantity_done : 0.0
                 * id : 1533
                 * reserve : 0
                 */

                private String name;
                private Object location;
                private double quantity_available;
                private double product_qty;
                private double quantity_done;
                private int id;
                private double reserve;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getLocation() {
                    if (location instanceof Boolean){
                        location = "";
                    }
                    return location;
                }

                public void setLocation(Object location) {
                    this.location = location;
                }

                public double getQuantity_available() {
                    return quantity_available;
                }

                public void setQuantity_available(double quantity_available) {
                    this.quantity_available = quantity_available;
                }

                public double getProduct_qty() {
                    return product_qty;
                }

                public void setProduct_qty(double product_qty) {
                    this.product_qty = product_qty;
                }

                public double getQuantity_done() {
                    return quantity_done;
                }

                public void setQuantity_done(double quantity_done) {
                    this.quantity_done = quantity_done;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public double getReserve() {
                    return reserve;
                }

                public void setReserve(double reserve) {
                    this.reserve = reserve;
                }
            }
        }
    }
}
