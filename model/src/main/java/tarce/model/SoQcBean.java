package tarce.model;

import java.io.Serializable;
import java.util.List;

import tarce.model.inventory.QcFeedbaskBean;

/**
 * Created by zouwansheng on 2017/12/21.
 */

public class SoQcBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"sale_id":3938,"soname":"SO1706213110","feedback":[{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1254&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1020,"qc_rate":7.8431372549019605,"name":"QCF/201708090019","qc_fail_qty":0,"feedback_id":7018,"qc_note":"","state":"qc_success","production_id":{"order_id":74621,"display_name":"MO170803163","product_id":{"product_id":73028,"product_name":"MJ212-成品(比萨斜塔){Jarir Bookstore}"}}},{"qc_test_qty":120,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1249&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1020,"qc_rate":11.76470588235294,"name":"QCF/201708090014","qc_fail_qty":0,"feedback_id":7013,"qc_note":"","state":"qc_success","production_id":{"order_id":74617,"display_name":"MO170803159","product_id":{"product_id":73018,"product_name":"MJ202-成品(海港大桥){Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1259&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708070052","qc_fail_qty":0,"feedback_id":6822,"qc_note":"","state":"qc_success","production_id":{"order_id":74574,"display_name":"MO170803116","product_id":{"product_id":73052,"product_name":"JPD460-成品(迪拜酒店)-盒装{Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1223&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708080113","qc_fail_qty":0,"feedback_id":6935,"qc_note":"","state":"qc_success","production_id":{"order_id":74464,"display_name":"MO170803005","product_id":{"product_id":73007,"product_name":"JP232-成品(吉普车)-简装{Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1221&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708090013","qc_fail_qty":0,"feedback_id":6968,"qc_note":"","state":"qc_success","production_id":{"order_id":70889,"display_name":"MO170717177","product_id":{"product_id":73005,"product_name":"BA503S裱纸成品(哥德堡号){Jarir Bookstore}"}}}]},{"sale_id":-1,"soname":"soname","feedback":[{"qc_test_qty":48,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1245&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":180,"qc_rate":26.666666666666668,"name":"QCF/201708100002","qc_fail_qty":0,"feedback_id":7030,"qc_note":"","state":"qc_success","production_id":{"order_id":73842,"display_name":"MO170728144","product_id":{"product_id":46399,"product_name":"F199S成品(小鸟屋子)-上色简装{RT-ENG}"}}}]}],"res_msg":"","res_code":1}
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
         * res_data : [{"sale_id":3938,"soname":"SO1706213110","feedback":[{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1254&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1020,"qc_rate":7.8431372549019605,"name":"QCF/201708090019","qc_fail_qty":0,"feedback_id":7018,"qc_note":"","state":"qc_success","production_id":{"order_id":74621,"display_name":"MO170803163","product_id":{"product_id":73028,"product_name":"MJ212-成品(比萨斜塔){Jarir Bookstore}"}}},{"qc_test_qty":120,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1249&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1020,"qc_rate":11.76470588235294,"name":"QCF/201708090014","qc_fail_qty":0,"feedback_id":7013,"qc_note":"","state":"qc_success","production_id":{"order_id":74617,"display_name":"MO170803159","product_id":{"product_id":73018,"product_name":"MJ202-成品(海港大桥){Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1259&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708070052","qc_fail_qty":0,"feedback_id":6822,"qc_note":"","state":"qc_success","production_id":{"order_id":74574,"display_name":"MO170803116","product_id":{"product_id":73052,"product_name":"JPD460-成品(迪拜酒店)-盒装{Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1223&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708080113","qc_fail_qty":0,"feedback_id":6935,"qc_note":"","state":"qc_success","production_id":{"order_id":74464,"display_name":"MO170803005","product_id":{"product_id":73007,"product_name":"JP232-成品(吉普车)-简装{Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1221&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708090013","qc_fail_qty":0,"feedback_id":6968,"qc_note":"","state":"qc_success","production_id":{"order_id":70889,"display_name":"MO170717177","product_id":{"product_id":73005,"product_name":"BA503S裱纸成品(哥德堡号){Jarir Bookstore}"}}}]},{"sale_id":-1,"soname":"soname","feedback":[{"qc_test_qty":48,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1245&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":180,"qc_rate":26.666666666666668,"name":"QCF/201708100002","qc_fail_qty":0,"feedback_id":7030,"qc_note":"","state":"qc_success","production_id":{"order_id":73842,"display_name":"MO170728144","product_id":{"product_id":46399,"product_name":"F199S成品(小鸟屋子)-上色简装{RT-ENG}"}}}]}]
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
             * sale_id : 3938
             * soname : SO1706213110
             * feedback : [{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1254&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1020,"qc_rate":7.8431372549019605,"name":"QCF/201708090019","qc_fail_qty":0,"feedback_id":7018,"qc_note":"","state":"qc_success","production_id":{"order_id":74621,"display_name":"MO170803163","product_id":{"product_id":73028,"product_name":"MJ212-成品(比萨斜塔){Jarir Bookstore}"}}},{"qc_test_qty":120,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1249&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1020,"qc_rate":11.76470588235294,"name":"QCF/201708090014","qc_fail_qty":0,"feedback_id":7013,"qc_note":"","state":"qc_success","production_id":{"order_id":74617,"display_name":"MO170803159","product_id":{"product_id":73018,"product_name":"MJ202-成品(海港大桥){Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1259&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708070052","qc_fail_qty":0,"feedback_id":6822,"qc_note":"","state":"qc_success","production_id":{"order_id":74574,"display_name":"MO170803116","product_id":{"product_id":73052,"product_name":"JPD460-成品(迪拜酒店)-盒装{Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1223&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708080113","qc_fail_qty":0,"feedback_id":6935,"qc_note":"","state":"qc_success","production_id":{"order_id":74464,"display_name":"MO170803005","product_id":{"product_id":73007,"product_name":"JP232-成品(吉普车)-简装{Jarir Bookstore}"}}},{"qc_test_qty":80,"qc_img":["http://192.168.2.44:8089/linkloving_app_api/get_worker_image?worker_id=1221&model=qc.feedback.img&field=qc_img"],"qc_fail_rate":0,"qty_produced":1005,"qc_rate":7.960199004975125,"name":"QCF/201708090013","qc_fail_qty":0,"feedback_id":6968,"qc_note":"","state":"qc_success","production_id":{"order_id":70889,"display_name":"MO170717177","product_id":{"product_id":73005,"product_name":"BA503S裱纸成品(哥德堡号){Jarir Bookstore}"}}}]
             */

            private int sale_id;
            private String soname;
            private List<QcFeedbaskBean.ResultBean.ResDataBean> feedback;

            public int getSale_id() {
                return sale_id;
            }

            public void setSale_id(int sale_id) {
                this.sale_id = sale_id;
            }

            public String getSoname() {
                if ("".equals(soname)){
                    soname ="暂无源单据";
                }
                return soname;
            }

            public void setSoname(String soname) {
                this.soname = soname;
            }

            public List<QcFeedbaskBean.ResultBean.ResDataBean> getFeedback() {
                return feedback;
            }

            public void setFeedback(List<QcFeedbaskBean.ResultBean.ResDataBean> feedback) {
                this.feedback = feedback;
            }
        }
    }
}
