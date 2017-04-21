package tarce.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class GetSaleListResponse implements Serializable {

    public static class TResult implements Serializable{

        public static class TRes_data  implements Serializable{

            public static class TPost_area_id  implements Serializable{

                private	Integer	area_id;	/*247*/
                private	String	area_name;	/*A3M3*/

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
            public static class TPack_operation_product_ids  implements Serializable{

                public static class TProduct_id  implements Serializable{

                    public static class TArea_id  implements Serializable{

                        private	Integer	area_id;	/*247*/
                        private	String	area_name;	/*A3M3*/

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
                    private	Integer	id;	/*49509*/
                    private	TArea_id	area_id;	/*TArea_id*/
                    private	Integer	qty_available;	/*3*/
                    private	String	name;	/*[52.500000.003] S-102白色油墨*/
                    private	String	default_code;	/*52.500000.003*/

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
                private	Integer	product_qty;	/*100*/
                private	TProduct_id	product_id;	/*TProduct_id*/
                private	Integer	pack_id;	/*3250*/
                private	Boolean	sale_note;	/*false*/
                private	Integer	qty_done;	/*100*/

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

                public void setSale_note(Boolean value){
                    this.sale_note = value;
                }
                public Boolean getSale_note(){
                    return this.sale_note;
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

            private	TPost_area_id	post_area_id;	/*TPost_area_id*/
            private	String	origin;	/*PO2016122001211*/
            private	String	state;	/*done*/
            private	Integer	picking_id;	/*900*/
            private	String	min_date;	/*2016-12-20 03:02:01*/
            private	Object	delivery_rule;	/*Object*/
            private	String	name;	/*WHIN2016122000790*/
            private	Boolean	qc_note;	/*false*/
            private	String	parnter_id;	/*苏州始丰丝印器材有限公司*/
            private	String	post_img;	/*http://erp.robotime.com/linkloving_app_api/get_worker_image?worker_id=900&model=stock.picking&field=post_img*/
            private	String	picking_type_code;	/*incoming*/
            private	String	qc_img;	/*http://erp.robotime.com/linkloving_app_api/get_worker_image?worker_id=900&model=stock.picking&field=qc_img*/

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

            public void setDelivery_rule(Object value){
                this.delivery_rule = value;
            }
            public Object getDelivery_rule(){
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
