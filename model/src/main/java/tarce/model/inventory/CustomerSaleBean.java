package tarce.model.inventory;

import java.util.List;

/**
 * Created by zws on 2017/8/9.
 */

public class CustomerSaleBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"comment":"","phone":"","partner_id":4024,"name":"58国际商务中心","x_qq":""},{"comment":"","phone":"","partner_id":3985,hone":"","partner_id":2067,"name":"郑州汪慧丽er_id":2688,"name":"汤涛涛","x_qq":""},{"comment":"","phone":"0512-65953090","partner_id":2697,"name":"王云丰","x_qq":""},{"comment":"","phone":"","partner_id":1702,"name":"上海巧磁文化创意有限
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
        /**,{"comment":"","phone":"","partner_id":2279,"name":"新华文轩商业连锁（北京）有限公司","x_qq":""},{"comment":"","phone":"","partner_id":3528,"name":"步步高商业连锁股份有限公司梅溪湖分公司","x_qq":""},{"comment":"","phone":"","partner_id":4106,"name":"苏州欧软信息科技有限公司金媒苏州交付中心","x_qq":""}]
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
             * comment :
             * phone :
             * partner_id : 4024
             * name : 58国际商务中心
             * x_qq :
             */

            private int able_to_data;

            public int getAble_to_data() {
                return able_to_data;
            }

            public void setAble_to_data(int able_to_data) {
                this.able_to_data = able_to_data;
            }

            public int getWaiting_data() {
                return waiting_data;
            }

            public void setWaiting_data(int waiting_data) {
                this.waiting_data = waiting_data;
            }

            private int waiting_data;
            private String comment;
            private String phone;
            private int partner_id;
            private String name;
            private String x_qq;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getPartner_id() {
                return partner_id;
            }

            public void setPartner_id(int partner_id) {
                this.partner_id = partner_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getX_qq() {
                return x_qq;
            }

            public void setX_qq(String x_qq) {
                this.x_qq = x_qq;
            }
        }
    }
}
