package tarce.model.inventory;

import java.util.List;

/**
 * Created by zouzou on 2017/6/23.
 * 点击入库的接口
 */

public class WaitingInBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"origin":"PO1705220872","sale_note":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6861&model=stock.picking&field=qc_img","complete_rate":0,"parnter_id":"沭阳汇丰包装有限公司","name":"WHIN170524248","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6861&model=stock.picking&field=post_img","min_date":"2017-06-01 09:25:49","qc_note":"点击输入品检批注","state":"waiting_in","picking_type_code":"incoming","has_attachment":false,"post_area_id":{"area_id":null,"area_name":null},"pack_operation_product_ids":[{"pack_id":133195,"qty_done":303,"product_id":{"default_code":"42.BB1412.128","qty_available":303,"area_id":{"area_id":null,"area_name":null},"id":51059,"name":"[42.BB1412.128] BB1412-外箱[20装]-俄罗斯TK"},"product_qty":303},{"pack_id":133196,"qty_done":252,"product_id":{"default_code":"42.BB1413.128","qty_available":252,"area_id":{"area_id":null,"area_name":null},"id":51073,"name":"[42.BB1413.128] BB1413-外箱[20装]-俄罗斯TK"},"product_qty":252},{"pack_id":133197,"qty_done":31,"product_id":{"default_code":"42.BB1414.128","qty_available":31,"area_id":{"area_id":null,"area_name":null},"id":51062,"name":"[42.BB1414.128] BB1414-外箱[40装]-俄罗斯TK"},"product_qty":31},{"pack_id":133198,"qty_done":41,"product_id":{"default_code":"42.BB2061.128","qty_available":41,"area_id":{"area_id":null,"area_name":null},"id":51080,"name":"[42.BB2061.128] BB2061-外箱[40装]-俄罗斯TK"},"product_qty":41},{"pack_id":133199,"qty_done":26,"product_id":{"default_code":"42.BB2062.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51069,"name":"[42.BB2062.128] BB2062-外箱[40装]-俄罗斯TK"},"product_qty":26},{"pack_id":133200,"qty_done":26,"product_id":{"default_code":"42.BB2063.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51077,"name":"[42.BB2063.128] BB2063-外箱[40装]-俄罗斯TK"},"product_qty":26},{"pack_id":133201,"qty_done":26,"product_id":{"default_code":"42.BB2064.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51082,"name":"[42.BB2064.128] BB2064-外箱[40装]-俄罗斯TK"},"product_qty":26}],"delivery_rule":null,"picking_id":6861}],"res_msg":"","res_code":1}
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
         * res_data : [{"origin":"PO1705220872","sale_note":false,"qc_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6861&model=stock.picking&field=qc_img","complete_rate":0,"parnter_id":"沭阳汇丰包装有限公司","name":"WHIN170524248","post_img":"http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6861&model=stock.picking&field=post_img","min_date":"2017-06-01 09:25:49","qc_note":"点击输入品检批注","state":"waiting_in","picking_type_code":"incoming","has_attachment":false,"post_area_id":{"area_id":null,"area_name":null},"pack_operation_product_ids":[{"pack_id":133195,"qty_done":303,"product_id":{"default_code":"42.BB1412.128","qty_available":303,"area_id":{"area_id":null,"area_name":null},"id":51059,"name":"[42.BB1412.128] BB1412-外箱[20装]-俄罗斯TK"},"product_qty":303},{"pack_id":133196,"qty_done":252,"product_id":{"default_code":"42.BB1413.128","qty_available":252,"area_id":{"area_id":null,"area_name":null},"id":51073,"name":"[42.BB1413.128] BB1413-外箱[20装]-俄罗斯TK"},"product_qty":252},{"pack_id":133197,"qty_done":31,"product_id":{"default_code":"42.BB1414.128","qty_available":31,"area_id":{"area_id":null,"area_name":null},"id":51062,"name":"[42.BB1414.128] BB1414-外箱[40装]-俄罗斯TK"},"product_qty":31},{"pack_id":133198,"qty_done":41,"product_id":{"default_code":"42.BB2061.128","qty_available":41,"area_id":{"area_id":null,"area_name":null},"id":51080,"name":"[42.BB2061.128] BB2061-外箱[40装]-俄罗斯TK"},"product_qty":41},{"pack_id":133199,"qty_done":26,"product_id":{"default_code":"42.BB2062.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51069,"name":"[42.BB2062.128] BB2062-外箱[40装]-俄罗斯TK"},"product_qty":26},{"pack_id":133200,"qty_done":26,"product_id":{"default_code":"42.BB2063.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51077,"name":"[42.BB2063.128] BB2063-外箱[40装]-俄罗斯TK"},"product_qty":26},{"pack_id":133201,"qty_done":26,"product_id":{"default_code":"42.BB2064.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51082,"name":"[42.BB2064.128] BB2064-外箱[40装]-俄罗斯TK"},"product_qty":26}],"delivery_rule":null,"picking_id":6861}]
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
             * origin : PO1705220872
             * sale_note : false
             * qc_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6861&model=stock.picking&field=qc_img
             * complete_rate : 0
             * parnter_id : 沭阳汇丰包装有限公司
             * name : WHIN170524248
             * post_img : http://192.168.2.4:8069/linkloving_app_api/get_worker_image?worker_id=6861&model=stock.picking&field=post_img
             * min_date : 2017-06-01 09:25:49
             * qc_note : 点击输入品检批注
             * state : waiting_in
             * picking_type_code : incoming
             * has_attachment : false
             * post_area_id : {"area_id":null,"area_name":null}
             * pack_operation_product_ids : [{"pack_id":133195,"qty_done":303,"product_id":{"default_code":"42.BB1412.128","qty_available":303,"area_id":{"area_id":null,"area_name":null},"id":51059,"name":"[42.BB1412.128] BB1412-外箱[20装]-俄罗斯TK"},"product_qty":303},{"pack_id":133196,"qty_done":252,"product_id":{"default_code":"42.BB1413.128","qty_available":252,"area_id":{"area_id":null,"area_name":null},"id":51073,"name":"[42.BB1413.128] BB1413-外箱[20装]-俄罗斯TK"},"product_qty":252},{"pack_id":133197,"qty_done":31,"product_id":{"default_code":"42.BB1414.128","qty_available":31,"area_id":{"area_id":null,"area_name":null},"id":51062,"name":"[42.BB1414.128] BB1414-外箱[40装]-俄罗斯TK"},"product_qty":31},{"pack_id":133198,"qty_done":41,"product_id":{"default_code":"42.BB2061.128","qty_available":41,"area_id":{"area_id":null,"area_name":null},"id":51080,"name":"[42.BB2061.128] BB2061-外箱[40装]-俄罗斯TK"},"product_qty":41},{"pack_id":133199,"qty_done":26,"product_id":{"default_code":"42.BB2062.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51069,"name":"[42.BB2062.128] BB2062-外箱[40装]-俄罗斯TK"},"product_qty":26},{"pack_id":133200,"qty_done":26,"product_id":{"default_code":"42.BB2063.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51077,"name":"[42.BB2063.128] BB2063-外箱[40装]-俄罗斯TK"},"product_qty":26},{"pack_id":133201,"qty_done":26,"product_id":{"default_code":"42.BB2064.128","qty_available":26,"area_id":{"area_id":null,"area_name":null},"id":51082,"name":"[42.BB2064.128] BB2064-外箱[40装]-俄罗斯TK"},"product_qty":26}]
             * delivery_rule : null
             * picking_id : 6861
             */

            private String origin;
            private boolean sale_note;
            private String qc_img;
            private int complete_rate;
            private String parnter_id;
            private String name;
            private String post_img;
            private String min_date;
            private String qc_note;
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

            public boolean isSale_note() {
                return sale_note;
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

            public static class PackOperationProductIdsBean {
                /**
                 * pack_id : 133195
                 * qty_done : 303.0
                 * product_id : {"default_code":"42.BB1412.128","qty_available":303,"area_id":{"area_id":null,"area_name":null},"id":51059,"name":"[42.BB1412.128] BB1412-外箱[20装]-俄罗斯TK"}
                 * product_qty : 303.0
                 */

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

                public static class ProductIdBean {
                    /**
                     * default_code : 42.BB1412.128
                     * qty_available : 303.0
                     * area_id : {"area_id":null,"area_name":null}
                     * id : 51059
                     * name : [42.BB1412.128] BB1412-外箱[20装]-俄罗斯TK
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
