package tarce.model;

import java.util.List;

/**
 * Created by zouwansheng on 2018/1/16.
 */

public class AllEmployeeBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"parent_id":{"name":"","id":""},"mobile_phone":"","name":"Administrator","work_phone":"","image":"http://192.168.2.44:8069/web/image?model=hr.employee&id=254&field=image_medium&unique=20161124065115","work_email":"","job_id":{"name":"","id":""},"department_id":{"name":"","id":""}},{"parent_id":{"name":"郭华根","id":301},"mobile_phone":"15295603983","name":"仲传丽","work_phone":"","image":"http://192.168.2.44:8069/web/image?model=hr.employ,"res_msg":"","res_code":1}
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
         * res_data : [{"parent_id":{"name":"","id":""},"mobile_phone":"","name":"Administrator","work_phone":"","image":"http://192.168.2.44:8069/web/image?model=hr.employee&id=254&field=image_medium&unique=20161124065115","work_email":"","job_id":{"name":"","id":""},"department_id":{"name":"","id":""}},{"parent_id":{"name":"郭华根","id":301},"mobile_phone":"15295603983","name":"仲传丽","work_phone":"","image":"http://192.168.2.44:8069/web/image?model=hr.me":"管控部","id":24}},ttp://192.168.2.44:8069/web/image?model=hr.eeld=image_medium&unique=20170725022420","work_email":"dong.zhang@robotime.com","job_id":{"name":"","id":""},"department_id":{"name":"若贝尔工厂","id":15}},{"paren
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
             * parent_id : {"name":"","id":""}
             * mobile_phone :
             * name : Administrator
             * work_phone :
             * image : http://192.168.2.44:8069/web/image?model=hr.employee&id=254&field=image_medium&unique=20161124065115
             * work_email :
             * job_id : {"name":"","id":""}
             * department_id : {"name":"","id":""}
             */

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            /**
             * line_id : 8
             * line_name : 压机-1
             */


            private int position = -1;
            private Object id;

            public Object getId() {
                if (id instanceof Boolean){
                    id = 0;
                }
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }
            public Object partner_id;

            public Object getPartner_id() {
                return partner_id;
            }

            public void setPartner_id(Object partner_id) {
                this.partner_id = partner_id;
            }

            private ParentIdBean parent_id;
            private String mobile_phone;
            private String name;
            private String work_phone;
            private String image;
            private String work_email;
            private JobIdBean job_id;
            private DepartmentIdBean department_id;

            public ParentIdBean getParent_id() {
                if (partner_id instanceof Boolean){
                    partner_id = 0;
                }
                return parent_id;
            }

            public void setParent_id(ParentIdBean parent_id) {
                this.parent_id = parent_id;
            }

            public String getMobile_phone() {
                return mobile_phone;
            }

            public void setMobile_phone(String mobile_phone) {
                this.mobile_phone = mobile_phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getWork_phone() {
                return work_phone;
            }

            public void setWork_phone(String work_phone) {
                this.work_phone = work_phone;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getWork_email() {
                return work_email;
            }

            public void setWork_email(String work_email) {
                this.work_email = work_email;
            }

            public JobIdBean getJob_id() {
                return job_id;
            }

            public void setJob_id(JobIdBean job_id) {
                this.job_id = job_id;
            }

            public DepartmentIdBean getDepartment_id() {
                return department_id;
            }

            public void setDepartment_id(DepartmentIdBean department_id) {
                this.department_id = department_id;
            }

            public static class ParentIdBean {
                /**
                 * name :
                 * id :
                 */

                private String name;
                private Object id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getId() {
                    if (id instanceof String){
                        id = 0;
                    }
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }
            }

            public static class JobIdBean {
                /**
                 * name :
                 * id :
                 */

                private String name;
                private String id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class DepartmentIdBean {
                /**
                 * name :
                 * id :
                 */

                private String name;
                private String id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
