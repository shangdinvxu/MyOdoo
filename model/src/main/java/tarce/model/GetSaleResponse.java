package tarce.model;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

import java.io.Serializable;
import java.util.List;


/***
 *
 * Json To JavaBean
 * @author www.json123.com
 *
 */
public class GetSaleResponse implements Serializable{

    public static class TResult implements Serializable{

        public static class TRes_data implements Serializable{


            public static class TPost_area_id implements Serializable{

                private	Integer	area_id;	/*210*/
                private	String	area_name;	/*A3C2*/

                public void setArea_id(Integer value){
                    this.area_id = value;
                }
                public Integer getArea_id(){
                    return this.area_id;
                }

                public void setArea_name(String value){
                    this.area_name = value;
                }
                public String getArea_name(){
                    return this.area_name;
                }

            }
            public static class TPack_operation_product_ids implements Serializable{

                public static class TProduct_id implements Serializable{

                    public static class TArea_id implements Serializable{

                        private	Integer	area_id;	/*210*/
                        private	String	area_name;	/*A3C2*/

                        public void setArea_id(Integer value){
                            this.area_id = value;
                        }
                        public Integer getArea_id(){
                            return this.area_id;
                        }

                        public void setArea_name(String value){
                            this.area_name = value;
                        }
                        public String getArea_name(){
                            return this.area_name;
                        }

                    }
                    private	Integer	id;	/*49457*/
                    private	TArea_id	area_id;	/*TArea_id*/
                    private	Integer	qty_available;	/*280*/
                    private	String	name;	/*[47.1PZ005.000] PZ1005-三角彩盒(世界地图)-RT-CN*/
                    private	String	default_code;	/*47.1PZ005.000*/

                    public void setId(Integer value){
                        this.id = value;
                    }
                    public Integer getId(){
                        return this.id;
                    }

                    public void setArea_id(TArea_id value){
                        this.area_id = value;
                    }
                    public TArea_id getArea_id(){
                        return this.area_id;
                    }

                    public void setQty_available(Integer value){
                        this.qty_available = value;
                    }
                    public Integer getQty_available(){
                        return this.qty_available;
                    }

                    public void setName(String value){
                        this.name = value;
                    }
                    public String getName(){
                        return this.name;
                    }

                    public void setDefault_code(String value){
                        this.default_code = value;
                    }
                    public String getDefault_code(){
                        return this.default_code;
                    }

                }
                private	Integer	product_qty;	/*900*/
                private	TProduct_id	product_id;	/*TProduct_id*/
                private	Integer	pack_id;	/*2788*/
                private	Integer	qty_done;	/*900*/

                public void setProduct_qty(Integer value){
                    this.product_qty = value;
                }
                public Integer getProduct_qty(){
                    return this.product_qty;
                }

                public void setProduct_id(TProduct_id value){
                    this.product_id = value;
                }
                public TProduct_id getProduct_id(){
                    return this.product_id;
                }

                public void setPack_id(Integer value){
                    this.pack_id = value;
                }
                public Integer getPack_id(){
                    return this.pack_id;
                }

                public void setQty_done(Integer value){
                    this.qty_done = value;
                }
                public Integer getQty_done(){
                    return this.qty_done;
                }

            }
            private	List<TPack_operation_product_ids>	pack_operation_product_ids;	/*List<TPack_operation_product_ids>*/
            public void setPack_operation_product_ids(List<TPack_operation_product_ids> value){
                this.pack_operation_product_ids = value;
            }
            public List<TPack_operation_product_ids> getPack_operation_product_ids(){
                return this.pack_operation_product_ids;
            }

            private	Integer	complete_rate;	/*0*/
            private	TPost_area_id	post_area_id;	/*TPost_area_id*/
            private	String	origin;	/*PO2016121401111*/

            private	Object	sale_note;	/*false*/
            private	String	state;	/*done*/
            private	Integer	picking_id;	/*781*/
            private	String	min_date;	/*2016-12-14 06:59:24*/
            private	String	delivery_rule;	/*Object*/
            private	String	name;	/*WHIN2016121500693*/
            private	Boolean	qc_note;	/*false*/
            private	String	parnter_id;	/*海盐宏胜包装材料厂*/
            private	String	picking_type_code;	/*incoming*/
            private	String	post_img;	/*http://192.168.88.106:8069/linkloving_app_api/get_worker_image?worker_id=781&model=stock.picking&field=post_img*/
            private	String	qc_img;	/*http://192.168.88.106:8069/linkloving_app_api/get_worker_image?worker_id=781&model=stock.picking&field=qc_img*/

            public void setComplete_rate(Integer value){
                this.complete_rate = value;
            }
            public Integer getComplete_rate(){
                return this.complete_rate;
            }

            public void setPost_area_id(TPost_area_id value){
                this.post_area_id = value;
            }
            public TPost_area_id getPost_area_id(){
                return this.post_area_id;
            }

            public void setOrigin(String value){
                this.origin = value;
            }
            public String getOrigin(){
                return this.origin;
            }

            public Object getSale_note() {
                if (sale_note instanceof Boolean){
                    sale_note = "";
                }
                return sale_note;
            }

            public void setSale_note(Object sale_note) {
                this.sale_note = sale_note;
            }

            public void setState(String value){
                this.state = value;
            }
            public String getState(){
                return this.state;
            }

            public void setPicking_id(Integer value){
                this.picking_id = value;
            }
            public Integer getPicking_id(){
                return this.picking_id;
            }

            public void setMin_date(String value){
                this.min_date = value;
            }
            public String getMin_date(){
                return this.min_date;
            }

            public void setDelivery_rule(String value){
                this.delivery_rule = value;
            }
            public String getDelivery_rule(){
                return this.delivery_rule;
            }

            public void setName(String value){
                this.name = value;
            }
            public String getName(){
                return this.name;
            }

            public void setQc_note(Boolean value){
                this.qc_note = value;
            }
            public Boolean getQc_note(){
                return this.qc_note;
            }

            public void setParnter_id(String value){
                this.parnter_id = value;
            }
            public String getParnter_id(){
                return this.parnter_id;
            }

            public void setPicking_type_code(String value){
                this.picking_type_code = value;
            }
            public String getPicking_type_code(){
                return this.picking_type_code;
            }

            public void setPost_img(String value){
                this.post_img = value;
            }
            public String getPost_img(){
                return this.post_img;
            }

            public void setQc_img(String value){
                this.qc_img = value;
            }
            public String getQc_img(){
                return this.qc_img;
            }

            public String getError() {
                return error;
            }

            public void setError(String error) {
                this.error = error;
            }

            private String error;

        }
        private	TRes_data	res_data;	/*TRes_data*/
        private	Integer	res_code;	/*1*/
        private	String	res_msg;	/**/

        public void setRes_data(TRes_data value){
            this.res_data = value;
        }
        public TRes_data getRes_data(){
            return this.res_data;
        }

        public void setRes_code(Integer value){
            this.res_code = value;
        }
        public Integer getRes_code(){
            return this.res_code;
        }

        public void setRes_msg(String value){
            this.res_msg = value;
        }
        public String getRes_msg(){
            return this.res_msg;
        }

    }
    private	Object	id;	/*Object*/
    private	TResult	result;	/*TResult*/
    private	String	jsonrpc;	/*2.0*/

    public void setId(Object value){
        this.id = value;
    }
    public Object getId(){
        return this.id;
    }

    public void setResult(TResult value){
        this.result = value;
    }
    public TResult getResult(){
        return this.result;
    }

    public void setJsonrpc(String value){
        this.jsonrpc = value;
    }
    public String getJsonrpc(){
        return this.jsonrpc;
    }

}

