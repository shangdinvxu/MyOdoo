package tarce.model.inventory;

import java.util.List;

/**
 * Created by zouzou on 2017/6/22.
 * 获取备料反馈原因
 */

public class GetFeedbackBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:11:40","__last_update":"2017-06-22 07:11:40","write_uid":[1,"Administrator"],"content":"沟沟壑壑更多撒啊","write_date":"2017-06-22 07:11:40","display_name":"沟沟壑壑更多撒啊","id":2},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:12:08","__last_update":"2017-06-22 07:12:08","write_uid":[1,"Administrator"],"content":"12345","write_date":"2017-06-22 07:12:08","display_name":"12345","id":3},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:21:31","__last_update":"2017-06-22 07:21:31","write_uid":[1,"Administrator"],"content":"安安安安安安安安安安安安安","write_date":"2017-06-22 07:21:31","display_name":"安安安安安安安安安安安安安","id":4},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:21:41","__last_update":"2017-06-22 07:21:41","write_uid":[1,"Administrator"],"content":"阿巴巴爸爸啊擦擦擦擦擦去吧","write_date":"2017-06-22 07:21:41","display_name":"阿巴巴爸爸啊擦擦擦擦擦去吧","id":5},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:27:31","__last_update":"2017-06-22 07:27:31","write_uid":[1,"Administrator"],"content":"aaa","write_date":"2017-06-22 07:27:31","display_name":"aaa","id":6},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:53:10","__last_update":"2017-06-22 07:53:10","write_uid":[1,"Administrator"],"content":"N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪","write_date":"2017-06-22 07:53:10","display_name":"N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪","id":7},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 09:30:43","__last_update":"2017-06-22 09:30:43","write_uid":[1,"Administrator"],"content":"pppp","write_date":"2017-06-22 09:30:43","display_name":"pppp","id":8}],"res_msg":"","res_code":1}
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
         * res_data : [{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:11:40","__last_update":"2017-06-22 07:11:40","write_uid":[1,"Administrator"],"content":"沟沟壑壑更多撒啊","write_date":"2017-06-22 07:11:40","display_name":"沟沟壑壑更多撒啊","id":2},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:12:08","__last_update":"2017-06-22 07:12:08","write_uid":[1,"Administrator"],"content":"12345","write_date":"2017-06-22 07:12:08","display_name":"12345","id":3},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:21:31","__last_update":"2017-06-22 07:21:31","write_uid":[1,"Administrator"],"content":"安安安安安安安安安安安安安","write_date":"2017-06-22 07:21:31","display_name":"安安安安安安安安安安安安安","id":4},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:21:41","__last_update":"2017-06-22 07:21:41","write_uid":[1,"Administrator"],"content":"阿巴巴爸爸啊擦擦擦擦擦去吧","write_date":"2017-06-22 07:21:41","display_name":"阿巴巴爸爸啊擦擦擦擦擦去吧","id":5},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:27:31","__last_update":"2017-06-22 07:27:31","write_uid":[1,"Administrator"],"content":"aaa","write_date":"2017-06-22 07:27:31","display_name":"aaa","id":6},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 07:53:10","__last_update":"2017-06-22 07:53:10","write_uid":[1,"Administrator"],"content":"N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪","write_date":"2017-06-22 07:53:10","display_name":"N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪N你N男安安安安安安安安那安南啊哈哈嘎嘎嘎嘎感到可惜那些没怎么做怎么做你在哪在哪","id":7},{"create_uid":[1,"Administrator"],"create_date":"2017-06-22 09:30:43","__last_update":"2017-06-22 09:30:43","write_uid":[1,"Administrator"],"content":"pppp","write_date":"2017-06-22 09:30:43","display_name":"pppp","id":8}]
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
             * create_uid : [1,"Administrator"]
             * create_date : 2017-06-22 07:11:40
             * __last_update : 2017-06-22 07:11:40
             * write_uid : [1,"Administrator"]
             * content : 沟沟壑壑更多撒啊
             * write_date : 2017-06-22 07:11:40
             * display_name : 沟沟壑壑更多撒啊
             * id : 2
             */

            private String create_date;
            private String __last_update;
            private String content;
            private String write_date;
            private String display_name;
            private int id;

            public List<Object> getCreate_uid() {
                return create_uid;
            }

            public void setCreate_uid(List<Object> create_uid) {
                this.create_uid = create_uid;
            }

            public List<Object> getWrite_uid() {
                return write_uid;
            }

            public void setWrite_uid(List<Object> write_uid) {
                this.write_uid = write_uid;
            }

            private List<Object> create_uid;
            private List<Object> write_uid;

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String get__last_update() {
                return __last_update;
            }

            public void set__last_update(String __last_update) {
                this.__last_update = __last_update;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getWrite_date() {
                return write_date;
            }

            public void setWrite_date(String write_date) {
                this.write_date = write_date;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
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
