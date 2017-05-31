package tarce.model.inventory;

/**
 * Created by rose.zou on 2017/5/31.
 * 扫描二维码自动添加员工接口返回
 */

public class AutoAddworkBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"image":"http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=579&model=hr.employee&field=image","worker_id":579,"name":"仲川","barcode":"03339858","job_name":"工厂激光"},"res_msg":"","res_code":1}
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
         * res_data : {"image":"http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=579&model=hr.employee&field=image","worker_id":579,"name":"仲川","barcode":"03339858","job_name":"工厂激光"}
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
            /**
             * image : http://192.168.2.36:8069/linkloving_app_api/get_worker_image?worker_id=579&model=hr.employee&field=image
             * worker_id : 579
             * name : 仲川
             * barcode : 03339858
             * job_name : 工厂激光
             */

            private String image;
            private int worker_id;
            private String name;
            private String barcode;
            private String job_name;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getWorker_id() {
                return worker_id;
            }

            public void setWorker_id(int worker_id) {
                this.worker_id = worker_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getJob_name() {
                return job_name;
            }

            public void setJob_name(String job_name) {
                this.job_name = job_name;
            }
        }
    }
}
