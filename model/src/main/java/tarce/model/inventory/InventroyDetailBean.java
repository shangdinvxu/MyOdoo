package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zouzou on 2017/7/4.
 */

public class InventroyDetailBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"location_name":"原料库A3","name":"INV: 丝印制程品-JP234Y-美国Can you imagine","state":"done","date":"2017-07-04 07:13:12","line_ids":[{"product":{"area":{"area_id":false,"area_name":false},"product_spec":"丝印内容待确认，丝印之前与业务确认","image_medium":"http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=74563&model=product.product","id":71866,"product_name":"[SY.0JP234.001] 丝印制程品-JP234Y-美国Can you imagine"},"product_qty":2000,"theoretical_qty":66,"id":324664}],"id":5390},"res_msg":"","res_code":1}
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
         * res_data : {"location_name":"原料库A3","name":"INV: 丝印制程品-JP234Y-美国Can you imagine","state":"done","date":"2017-07-04 07:13:12","line_ids":[{"product":{"area":{"area_id":false,"area_name":false},"product_spec":"丝印内容待确认，丝印之前与业务确认","image_medium":"http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=74563&model=product.product","id":71866,"product_name":"[SY.0JP234.001] 丝印制程品-JP234Y-美国Can you imagine"},"product_qty":2000,"theoretical_qty":66,"id":324664}],"id":5390}
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
             * location_name : 原料库A3
             * name : INV: 丝印制程品-JP234Y-美国Can you imagine
             * state : done
             * date : 2017-07-04 07:13:12
             * line_ids : [{"product":{"area":{"area_id":false,"area_name":false},"product_spec":"丝印内容待确认，丝印之前与业务确认","image_medium":"http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=74563&model=product.product","id":71866,"product_name":"[SY.0JP234.001] 丝印制程品-JP234Y-美国Can you imagine"},"product_qty":2000,"theoretical_qty":66,"id":324664}]
             * id : 5390
             */

            private String location_name;
            private String name;
            private String state;
            private String date;
            private int id;
            private List<LineIdsBean> line_ids;

            public String getLocation_name() {
                return location_name;
            }

            public void setLocation_name(String location_name) {
                this.location_name = location_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<LineIdsBean> getLine_ids() {
                return line_ids;
            }

            public void setLine_ids(List<LineIdsBean> line_ids) {
                this.line_ids = line_ids;
            }

            public static class LineIdsBean implements Serializable{
                /**
                 * product : {"area":{"area_id":false,"area_name":false},"product_spec":"丝印内容待确认，丝印之前与业务确认","image_medium":"http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=74563&model=product.product","id":71866,"product_name":"[SY.0JP234.001] 丝印制程品-JP234Y-美国Can you imagine"}
                 * product_qty : 2000.0
                 * theoretical_qty : 66.0
                 * id : 324664
                 */

                private ProductBean product;
                private double product_qty;
                private double theoretical_qty;
                private int id;

                public ProductBean getProduct() {
                    return product;
                }

                public void setProduct(ProductBean product) {
                    this.product = product;
                }

                public double getProduct_qty() {
                    return product_qty;
                }

                public void setProduct_qty(double product_qty) {
                    this.product_qty = product_qty;
                }

                public double getTheoretical_qty() {
                    return theoretical_qty;
                }

                public void setTheoretical_qty(double theoretical_qty) {
                    this.theoretical_qty = theoretical_qty;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public static class ProductBean implements Serializable{
                    /**
                     * area : {"area_id":false,"area_name":false}
                     * product_spec : 丝印内容待确认，丝印之前与业务确认
                     * image_medium : http://192.168.2.4:8069/linkloving_app_api/get_product_image?product_id=74563&model=product.product
                     * id : 71866
                     * product_name : [SY.0JP234.001] 丝印制程品-JP234Y-美国Can you imagine
                     */

                    private AreaBean area;
                    private String product_spec;
                    private String image_medium;
                    private int id;
                    private String product_name;

                    public AreaBean getArea() {
                        return area;
                    }

                    public void setArea(AreaBean area) {
                        this.area = area;
                    }

                    public String getProduct_spec() {
                        return product_spec;
                    }

                    public void setProduct_spec(String product_spec) {
                        this.product_spec = product_spec;
                    }

                    public String getImage_medium() {
                        return image_medium;
                    }

                    public void setImage_medium(String image_medium) {
                        this.image_medium = image_medium;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getProduct_name() {
                        return product_name;
                    }

                    public void setProduct_name(String product_name) {
                        this.product_name = product_name;
                    }

                    public static class AreaBean implements Serializable{
                        public Object getArea_id() {
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

                        /**
                         * area_id : false
                         * area_name : false
                         */

                        private Object area_id;
                        private Object area_name;


                    }
                }
            }
        }
    }
}
