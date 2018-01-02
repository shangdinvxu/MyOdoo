package tarce.model;

import java.util.List;

/**
 * Created by rose.zou on 2017/5/18.
 */

public class GetProcessBean {

    /**
     * {"jsonrpc": "2.0", "id": null, "result": {"res_data": [{"process_id": 4, "name": "\u4e1d\u5370"}, {"process_id": 3, "name": "\u88f1\u7eb8"}, {"process_id": 5, "name": "\u51b2\u538b"}, {"process_id": 6, "name": "\u6fc0\u5149"}, {"process_id": 8, "name": "\u5168\u68c0"}, {"process_id": 11, "name": "\u7535\u5b50\u7ec4\u88c5"}, {"process_id": 16, "name": "\u52a8\u6001\u5305\u88c5"}, {"process_id": 7, "name": "\u9759\u6001\u5305\u88c5"}, {"process_id": 10, "name": "\u59d4\u5916\u52a0\u5de5"},
     * {"process_id": 17, "name": "\u6837\u54c1\u5c55\u67dc\u7ec4\u88c5"},
     * {"process_id": 21, "name": "\u8fd4\u5de5"}], "res_msg": "", "res_code": 1}}
     * */
    private String id;
    private String jsonrpc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public TestRSubBean getResult() {
        return result;
    }

    public void setResult(TestRSubBean result) {
        this.result = result;
    }

    private TestRSubBean result;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class TestRSubBean{
        private List<ListSubBean> res_data;

        public List<ListSubBean> getRes_data() {
            return res_data;
        }

        public void setRes_data(List<ListSubBean> res_data) {
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

        private String res_msg;
        private int res_code;



        public static class ListSubBean{
            public ListSubBean(int process_id, String name) {
                this.process_id = process_id;
                this.name = name;
            }

            public int getProcess_id() {
               return process_id;
           }

           public void setProcess_id(int process_id) {
               this.process_id = process_id;
           }

           public String getName() {
               return name;
           }

           public void setName(String name) {
               this.name = name;
           }

           private int process_id;
           private String name;
       }
    }
}
