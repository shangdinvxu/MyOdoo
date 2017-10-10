package tarce.model;

import java.util.List;

/**
 * Created by zouwansheng on 2017/9/27.
 */

public class MaterialRelationBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"input":[{"product_id":13674,"product_name":"激光制程品-木板 3MM DG101/DG102 181x6{RT}"},{"product_id":13675,"product_name":"激光制程品-木板 3MM DG101/DG102 135x6{RT}"},{"product_id":13676,"product_name":"激光制程品-木板 3MM 椅子2 60x19{RT}"}"滚漆制程品-木板 3MM DG101 90x40 3105 U{RT}"},{"product_id":14037,"product_name":"滚漆制程品-木板 3MM 椅子2  U{RT}"}]},"res_msg":"","res_code":1}
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
         * res_data : {"input":[{"product_id":13674,"product_name":"激光制程品-木板 3MM DG101/DG102 181x6{RT}"},{"product_id":13675,"product_name":"激光制程品-木板 3MM DG101/DG102 135x6{RT}"},{"product_id":13676,"product_name":"激光制程品-木板 3MM 椅子2 60x19{RT}"},{"product_id":13677,"product_name":"激光制程品-木板 3MM DG101 90x60{RT}"},{"product_id":13678,"product_name":"滚漆制程品-木板 4MM DG10x 42x10 723 U{RT}"},{"product_id":14142,"product_name":"滚漆制程品-木板 4MM DG101/DG102 185x10 723 U{RT}"},{"product_id":14141,"product_name":"滚漆制程品-木板 4MM DG101/DG102 149x10 723 U{RT}"}]}
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
            private List<InputBean> input;
            private List<InputBean> output;

            public List<InputBean> getInput() {
                return input;
            }

            public void setInput(List<InputBean> input) {
                this.input = input;
            }

            public List<InputBean> getOutput() {
                return output;
            }

            public void setOutput(List<InputBean> output) {
                this.output = output;
            }

            public static class InputBean {
                /**
                 * product_id : 13674
                 * product_name : 激光制程品-木板 3MM DG101/DG102 181x6{RT}
                 */

                private int product_id;
                private String product_name;

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }
            }

//            public static class OutputBean {
//                /**
//                 * product_id : 14031
//                 * product_name : 滚漆制程品-木板 3MM DG101/DG102 181x6 723 U{RT}
//                 */
//
//                private int product_id;
//                private String product_name;
//
//                public int getProduct_id() {
//                    return product_id;
//                }
//
//                public void setProduct_id(int product_id) {
//                    this.product_id = product_id;
//                }
//
//                public String getProduct_name() {
//                    return product_name;
//                }
//
//                public void setProduct_name(String product_name) {
//                    this.product_name = product_name;
//                }
//            }
        }
    }
}
