package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

import tarce.model.GetSaleResponse;

/**
 * Created by Daniel.Xu on 2017/5/4.
 */

public class SalesOutListResponse implements Serializable {
    public static class TResult implements Serializable{

        public static class TRes_data implements Serializable{

            public static class TPost_area_id implements Serializable{

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
            private	Integer	complete_rate;	/*0*/
            private	TPost_area_id	post_area_id;	/*TPost_area_id*/


            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            private	String	origin;	/*SO2017042001881*/

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            private String phone;
            private	Object	sale_note;	/*false*/
            private	String	state;	/*confirmed*/
            private	Integer	picking_id;	/*5544*/
            private	String	min_date;	/*2017-04-20 07:31:21*/
            private	String	delivery_rule;	/*delivery_once*/
            private	String	name;	/*WHOUT2017042002438*/
            private	Boolean	qc_note;	/*false*/
            private	String	parnter_id;	/*阿里玩具店散单*/
            private	Boolean	has_attachment;	/*false*/
            private	String	post_img;	/*http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=5544&model=stock.picking&field=post_img*/
            private	String	picking_type_code;	/*outgoing*/
            private	String	qc_img;	/*http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=5544&model=stock.picking&field=qc_img*/

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

            public void setHas_attachment(Boolean value){
                this.has_attachment = value;
            }
            public Boolean getHas_attachment(){
                return this.has_attachment;
            }

            public void setPost_img(String value){
                this.post_img = value;
            }
            public String getPost_img(){
                return this.post_img;
            }

            public void setPicking_type_code(String value){
                this.picking_type_code = value;
            }
            public String getPicking_type_code(){
                return this.picking_type_code;
            }

            public void setQc_img(String value){
                this.qc_img = value;
            }
            public String getQc_img(){
                return this.qc_img;
            }
            private	List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids>	pack_operation_product_ids;	/*List<TPack_operation_product_ids>*/
            public void setPack_operation_product_ids(List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> value){
                this.pack_operation_product_ids = value;
            }
            public List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> getPack_operation_product_ids(){
                return this.pack_operation_product_ids;
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
                    private GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.TProduct_id.TArea_id area_id;	/*TArea_id*/
                    private	Integer	qty_available;	/*280*/
                    private	String	name;	/*[47.1PZ005.000] PZ1005-三角彩盒(世界地图)-RT-CN*/
                    private	String	default_code;	/*47.1PZ005.000*/

                    public void setId(Integer value){
                        this.id = value;
                    }
                    public Integer getId(){
                        return this.id;
                    }

                    public void setArea_id(GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.TProduct_id.TArea_id value){
                        this.area_id = value;
                    }
                    public GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.TProduct_id.TArea_id getArea_id(){
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
                private GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.TProduct_id product_id;	/*TProduct_id*/
                private	Integer	pack_id;	/*2788*/
                private	Integer	qty_done;/*900*/

                public int getOrigin_qty() {
                    return origin_qty;
                }

                public void setOrigin_qty(int origin_qty) {
                    this.origin_qty = origin_qty;
                }

                private int origin_qty;

                public void setProduct_qty(Integer value){
                    this.product_qty = value;
                }
                public Integer getProduct_qty(){
                    return this.product_qty;
                }

                public void setProduct_id(GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.TProduct_id value){
                    this.product_id = value;
                }
                public GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.TProduct_id getProduct_id(){
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

        }
        private	List<TRes_data>	res_data;	/*List<TRes_data>*/
        public void setRes_data(List<TRes_data> value){
            this.res_data = value;
        }
        public List<TRes_data> getRes_data(){
            return this.res_data;
        }

        private	Integer	res_code;	/*1*/
        private	String	res_msg;	/**/

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
