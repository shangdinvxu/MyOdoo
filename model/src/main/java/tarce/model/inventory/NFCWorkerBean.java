package tarce.model.inventory;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by zouzou on 2017/6/28.
 * 所有员工数据
 */

public class NFCWorkerBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"employees":[{"employee_id":301,"work_email":"andy.guo@robotime.com","name":"郭华根"}],"name":"董事长","department_id":18}],"res_msg":"","res_code":1}
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

    public static class ResultBean implements ExpandableListItem{
        /**
         * res_data : [{"employees":[{"employee_id":301,"work_email":"andy.guo@robotime.com","name":"郭华根"}],"name":"董事长","department_id":18}]
         * res_msg :
         * res_code : 1
         */

        private String res_msg;
        private int res_code;
        public List<ResDataBean> res_data;

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

        private boolean mExpanded = false;
        @Override
        public List<?> getChildItemList() {
            return res_data;
        }

        @Override
        public boolean isExpanded() {
            return mExpanded;
        }

        @Override
        public void setExpanded(boolean isExpanded) {
            mExpanded = isExpanded();
        }

        public static class ResDataBean implements ExpandableListItem{
            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            /**
             * employees : [{"employee_id":301,"work_email":"andy.guo@robotime.com","name":"郭华根"}]
             * name : 董事长
             * department_id : 18
             */
            public int parent_id;
            public String name;
            public int department_id;
            public List<EmployeesBean> employees;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getDepartment_id() {
                return department_id;
            }

            public void setDepartment_id(int department_id) {
                this.department_id = department_id;
            }

            public List<EmployeesBean> getEmployees() {
                return employees;
            }

            public void setEmployees(List<EmployeesBean> employees) {
                this.employees = employees;
            }

            private boolean mExpanded = false;
            @Override
            public List<?> getChildItemList() {
                return employees;
            }

            @Override
            public boolean isExpanded() {
                return mExpanded;
            }

            @Override
            public void setExpanded(boolean isExpanded) {
                mExpanded = isExpanded();
            }

            public static class EmployeesBean {
                /**
                 * employee_id : 301
                 * work_email : andy.guo@robotime.com
                 * name : 郭华根
                 */

                public int employee_id;
                public String work_email;
                public String name;

                public int getEmployee_id() {
                    return employee_id;
                }

                public void setEmployee_id(int employee_id) {
                    this.employee_id = employee_id;
                }

                public String getWork_email() {
                    return work_email;
                }

                public void setWork_email(String work_email) {
                    this.work_email = work_email;
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
