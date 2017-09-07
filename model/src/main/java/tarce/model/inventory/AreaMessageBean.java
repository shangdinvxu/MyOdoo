package tarce.model.inventory;

import java.util.List;

import tarce.model.ErrorBean;

/**
 * Created by rose.zou on 2017/5/25.
 * 位置信息
 */

public class AreaMessageBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"area_id":82,"area_name":"A1A1"},{"area_id":83,"area_name":"A1A2"},{"area_id":84,"area_name":"A1A3"},{"area_id":85,"area_name":"A1A4"},{"area_id":86,"area_name":"A1B1"},{"area_id":87,"area_name":"A1B2"},{"area_id":88,"area_name":"A1B3"},{"area_id":89,"area_name":"A1B4"},{"area_id":90,"area_name":"A1C1"},{"area_id":91,"area_name":"A1C2"},{"area_id":92,"area_name":"A1C3"},{"area_id":93,"area_name":"A1C4"},{"area_id":94,"area_name":"A1D1"},{"area_id":95,"area_name":"A1D2"},{"area_id":97,"area_name":"A1D3"},{"area_id":98,"area_name":"A1D4"},{"area_id":99,"area_name":"A1E1"},{"area_id":100,"area_name":"A1E2"},{"area_id":101,"area_name":"A1E3"},{"area_id":102,"area_name":"A1E4"},{"area_id":103,"area_name":"A1F1"},{"area_id":104,"area_name":"A1F2"},{"area_id":105,"area_name":"A1F3"},{"area_id":106,"area_name":"A1F4"},{"area_id":107,"area_name":"A1G1"},{"area_id":108,"area_name":"A1G2"},{"area_id":109,"area_name":"A1G3"},{"area_id":110,"area_name":"A1G4"},{"area_id":111,"area_name":"A1H1"},{"area_id":112,"area_name":"A1H2"},{"area_id":113,"area_name":"A1H3"},{"area_id":114,"area_name":"A1H4"},{"area_id":115,"area_name":"A1J1"},{"area_id":116,"area_name":"A1J2"},{"area_id":117,"area_name":"A1J3"},{"area_id":118,"area_name":"A1J4"},{"area_id":119,"area_name":"A1K1"},{"area_id":120,"area_name":"A1K2"},{"area_id":121,"area_name":"A1K3"},{"area_id":122,"area_name":"A1K4"},{"area_id":123,"area_name":"A1L1"},{"area_id":124,"area_name":"A1L2"},{"area_id":125,"area_name":"A1L3"},{"area_id":126,"area_name":"A1L4"},{"area_id":127,"area_name":"A1M1"},{"area_id":128,"area_name":"A1M2"},{"area_id":129,"area_name":"A1M3"},{"area_id":130,"area_name":"A1M4"},{"area_id":131,"area_name":"A1N1"},{"area_id":132,"area_name":"A1N2"},{"area_id":133,"area_name":"A1N3"},{"area_id":134,"area_name":"A1N4"},{"area_id":135,"area_name":"A1P1"},{"area_id":136,"area_name":"A1P2"},{"area_id":137,"area_name":"A1P3"},{"area_id":138,"area_name":"A1P4"},{"area_id":139,"area_name":"A2A1"},{"area_id":201,"area_name":"A3A1"}],"res_msg":"","res_code":1}
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
         * res_data : [{"area_id":82,"area_name":"A1A1"},{"area_id":83,"area_name":"A1A2"},{"area_id":84,"area_name":"A1A3"},{"area_id":85,"area_name":"A1A4"},{"area_id":86,"area_name":"A1B1"},{"area_id":87,"area_name":"A1B2"},{"area_id":88,"area_name":"A1B3"},{"area_id":89,"area_name":"A1B4"},{"area_id":90,"area_name":"A1C1"},{"area_id":91,"area_name":"A1C2"},{"area_id":92,"area_name":"A1C3"},{"area_id":93,"area_name":"A1C4"},{"area_id":94,"area_name":"A1D1"},{"area_id":95,"area_name":"A1D2"},{"area_id":97,"area_name":"A1D3"},{"area_id":98,"area_name":"A1D4"},{"area_id":99,"area_name":"A1E1"},{"area_id":100,"area_name":"A1E2"},{"area_id":101,"area_name":"A1E3"},{"area_id":102,"area_name":"A1E4"},{"area_id":103,"area_name":"A1F1"},{"area_id":104,"area_name":"A1F2"},{"area_id":105,"area_name":"A1F3"},{"area_id":106,"area_name":"A1F4"},{"area_id":107,"area_name":"A1G1"},{"area_id":108,"area_name":"A1G2"},{"area_id":109,"area_name":"A1G3"},{"area_id":110,"area_name":"A1G4"},{"area_id":111,"area_name":"A1H1"},{"area_id":112,"area_name":"A1H2"},{"area_id":113,"area_name":"A1H3"},{"area_id":114,"area_name":"A1H4"},{"area_id":115,"area_name":"A1J1"},{"area_id":116,"area_name":"A1J2"},{"area_id":117,"area_name":"A1J3"},{"area_id":118,"area_name":"A1J4"},{"area_id":119,"area_name":"A1K1"},{"area_id":120,"area_name":"A1K2"},{"area_id":121,"area_name":"A1K3"},{"area_id":122,"area_name":"A1K4"},{"area_id":123,"area_name":"A1L1"},{"area_id":124,"area_name":"A1L2"},{"area_id":125,"area_name":"A1L3"},{"area_id":126,"area_name":"A1L4"},{"area_id":127,"area_name":"A1M1"},{"area_id":128,"area_name":"A1M2"},{"area_id":129,"area_name":"A1M3"},{"area_id":130,"area_name":"A1M4"},{"area_id":131,"area_name":"A1N1"},{"area_id":132,"area_name":"A1N2"},{"area_id":133,"area_name":"A1N3"},{"area_id":134,"area_name":"A1N4"},{"area_id":135,"area_name":"A1P1"},{"area_id":136,"area_name":"A1P2"},{"area_id":137,"area_name":"A1P3"},{"area_id":138,"area_name":"A1P4"},{"area_id":139,"area_name":"A2A1"},{"area_id":201,"area_name":"A3A1"}]
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
             * area_id : 82
             * area_name : A1A1
             */

            private int area_id;
            private String area_name;

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }
        }
    }
}
