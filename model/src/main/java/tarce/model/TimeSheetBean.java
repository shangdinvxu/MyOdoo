package tarce.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zouwansheng on 2018/1/16.
 */

public class TimeSheetBean implements Serializable{
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"work_type_id":{"id":1,"name":"熟练工"},"hour_spent":2.3,"id":15,"to_partner":{"id":2117,"name":"陈小娟"},"from_partner":{"id":3424,"name":"张秀"}},"res_msg":"","res_code":1}
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

    public static class ResultBean implements Serializable{
        /**
         * res_data : {"work_type_id":{"id":1,"name":"熟练工"},"hour_spent":2.3,"id":15,"to_partner":{"id":2117,"name":"陈小娟"},"from_partner":{"id":3424,"name":"张秀"}}
         * res_msg :
         * res_code : 1
         */

        private List<ResDataBean> res_data;
        private String res_msg;
        private int res_code;

        public List<ResDataBean> getRes_data() {
            return res_data;
        }

        public void setRes_data(List<ResDataBean> res_data) {
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

        public static class ResDataBean implements Serializable{
            /**
             * work_type_id : {"id":1,"name":"熟练工"}
             * hour_spent : 2.3
             * id : 15
             * to_partner : {"id":2117,"name":"陈小娟"}
             * from_partner : {"id":3424,"name":"张秀"}
             */

            private WorkTypeIdBean work_type_id;
            private double hour_spent;
            private int id;
            private ToPartnerBean to_partner;
            private FromPartnerBean from_partner;

            public WorkTypeIdBean getWork_type_id() {
                return work_type_id;
            }

            public void setWork_type_id(WorkTypeIdBean work_type_id) {
                this.work_type_id = work_type_id;
            }

            public double getHour_spent() {
                return hour_spent;
            }

            public void setHour_spent(double hour_spent) {
                this.hour_spent = hour_spent;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public ToPartnerBean getTo_partner() {
                return to_partner;
            }

            public void setTo_partner(ToPartnerBean to_partner) {
                this.to_partner = to_partner;
            }

            public FromPartnerBean getFrom_partner() {
                return from_partner;
            }

            public void setFrom_partner(FromPartnerBean from_partner) {
                this.from_partner = from_partner;
            }

            public static class WorkTypeIdBean implements Serializable{
                /**
                 * id : 1
                 * name : 熟练工
                 */

                private int id;
                private String name;

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
            }

            public static class ToPartnerBean implements Serializable{
                /**
                 * id : 2117
                 * name : 陈小娟
                 */

                private int id;
                private String name;

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
            }

            public static class FromPartnerBean implements Serializable{
                /**
                 * id : 3424
                 * name : 张秀
                 */

                private int id;
                private String name;

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
            }
        }
    }
}
