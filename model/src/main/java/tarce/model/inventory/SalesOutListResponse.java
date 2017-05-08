package tarce.model.inventory;

import java.io.Serializable;
import java.util.List;

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
                    return this.area_id;
                }

                public void setArea_name(Object value){
                    this.area_name = value;
                }
                public Object getArea_name(){
                    return this.area_name;
                }

            }
            private	Integer	complete_rate;	/*0*/
            private	TPost_area_id	post_area_id;	/*TPost_area_id*/
            private	String	origin;	/*SO2017042001881*/
            private	Boolean	sale_note;	/*false*/
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

            public void setOrigin(String value){
                this.origin = value;
            }
            public String getOrigin(){
                return this.origin;
            }

            public void setSale_note(Boolean value){
                this.sale_note = value;
            }
            public Boolean getSale_note(){
                return this.sale_note;
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
