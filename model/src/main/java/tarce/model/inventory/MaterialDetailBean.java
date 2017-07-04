package tarce.model.inventory;


import java.util.List;

/**
 * Created by rose.zou on 2017/5/24.
 * 延误的详细信息
 */

public class MaterialDetailBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"origin":false,"in_charge_name":"倪春梅","state":"prepare_material_ing","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO/2017041831592","product_qty":300,"order_id":31638,"date_planned_start":"2017-05-12 13:01:37","product_name":"[98.0AC401.158] AC401-成品-阿联酋Lifestyle"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515101","product_qty":1,"order_id":44534,"date_planned_start":"2017-05-20 02:41:12","product_name":"[98.PIN001.106] PIN001-成品(制作匹诺曹)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515106","product_qty":1,"order_id":44539,"date_planned_start":"2017-05-20 02:42:06","product_name":"[98.PIN002.106] PIN002-成品(匹诺曹问世)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515115","product_qty":1,"order_id":44548,"date_planned_start":"2017-05-20 02:42:26","product_name":"[98.PIN003.106] PIN003-成品(变卖上学)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515122","product_qty":1,"order_id":44555,"date_planned_start":"2017-05-20 02:43:05","product_name":"[98.PIN004.106] PIN004-成品(逃学马戏团)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515125","product_qty":1,"order_id":44558,"date_planned_start":"2017-05-20 02:43:32","product_name":"[98.PIN005.106] PIN005-成品(带坏匹诺曹)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515126","product_qty":1,"order_id":44559,"date_planned_start":"2017-05-20 02:44:00","product_name":"[98.PIN006.106] PIN006-成品(种金币)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515127","product_qty":1,"order_id":44560,"date_planned_start":"2017-05-20 02:44:25","product_name":"[98.PIN007.106] PIN007-成品(匹诺曹被抓)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515128","product_qty":1,"order_id":44561,"date_planned_start":"2017-05-20 02:45:06","product_name":"[98.PIN008.106] PIN008-成品(玩耍国)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515129","product_qty":1,"order_id":44562,"date_planned_start":"2017-05-20 02:45:33","product_name":"[98.PIN009.106] PIN009-成品(真正的小男孩)-意大利3DINO"},{"origin":false,"in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO/2017042438061","product_qty":5000,"order_id":38113,"date_planned_start":"2017-05-21 02:46:49","product_name":"[98.0D2100.113] D210-成品-亚马逊wowood"}],"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;

    public OrderDetailBean.ErrorBean getError() {
        return error;
    }

    public void setError(OrderDetailBean.ErrorBean error) {
        this.error = error;
    }

    private OrderDetailBean.ErrorBean error;

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
         * res_data : [{"origin":false,"in_charge_name":"倪春梅","state":"prepare_material_ing","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO/2017041831592","product_qty":300,"order_id":31638,"date_planned_start":"2017-05-12 13:01:37","product_name":"[98.0AC401.158] AC401-成品-阿联酋Lifestyle"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515101","product_qty":1,"order_id":44534,"date_planned_start":"2017-05-20 02:41:12","product_name":"[98.PIN001.106] PIN001-成品(制作匹诺曹)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515106","product_qty":1,"order_id":44539,"date_planned_start":"2017-05-20 02:42:06","product_name":"[98.PIN002.106] PIN002-成品(匹诺曹问世)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515115","product_qty":1,"order_id":44548,"date_planned_start":"2017-05-20 02:42:26","product_name":"[98.PIN003.106] PIN003-成品(变卖上学)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515122","product_qty":1,"order_id":44555,"date_planned_start":"2017-05-20 02:43:05","product_name":"[98.PIN004.106] PIN004-成品(逃学马戏团)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515125","product_qty":1,"order_id":44558,"date_planned_start":"2017-05-20 02:43:32","product_name":"[98.PIN005.106] PIN005-成品(带坏匹诺曹)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515126","product_qty":1,"order_id":44559,"date_planned_start":"2017-05-20 02:44:00","product_name":"[98.PIN006.106] PIN006-成品(种金币)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515127","product_qty":1,"order_id":44560,"date_planned_start":"2017-05-20 02:44:25","product_name":"[98.PIN007.106] PIN007-成品(匹诺曹被抓)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515128","product_qty":1,"order_id":44561,"date_planned_start":"2017-05-20 02:45:06","product_name":"[98.PIN008.106] PIN008-成品(玩耍国)-意大利3DINO"},{"origin":"SO1705152388:WH: Stock -> CustomersMTO","in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO170515129","product_qty":1,"order_id":44562,"date_planned_start":"2017-05-20 02:45:33","product_name":"[98.PIN009.106] PIN009-成品(真正的小男孩)-意大利3DINO"},{"origin":false,"in_charge_name":"倪春梅","state":"waiting_material","process_id":{"process_id":16,"name":"动态包装"},"display_name":"MO/2017042438061","product_qty":5000,"order_id":38113,"date_planned_start":"2017-05-21 02:46:49","product_name":"[98.0D2100.113] D210-成品-亚马逊wowood"}]
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

        public static class ResDataBean{
            /**
             * origin : false
             * in_charge_name : 倪春梅
             * state : prepare_material_ing
             * process_id : {"process_id":16,"name":"动态包装"}
             * display_name : MO/2017041831592
             * product_qty : 300.0
             * order_id : 31638
             * date_planned_start : 2017-05-12 13:01:37
             * product_name : [98.0AC401.158] AC401-成品-阿联酋Lifestyle
             */

            private Object origin;
            private String in_charge_name;
            private String state;
            private ProcessIdBean process_id;
            private String display_name;
            private double product_qty;
            private int order_id;
            private String date_planned_start;
            private String product_name;

            public Object getOrigin() {
                return origin;
            }

            public void setOrigin(Object origin) {
                this.origin = origin;
            }
            public String getIn_charge_name() {
                return in_charge_name;
            }

            public void setIn_charge_name(String in_charge_name) {
                this.in_charge_name = in_charge_name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public ProcessIdBean getProcess_id() {
                return process_id;
            }

            public void setProcess_id(ProcessIdBean process_id) {
                this.process_id = process_id;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }

            public double getProduct_qty() {
                return product_qty;
            }

            public void setProduct_qty(double product_qty) {
                this.product_qty = product_qty;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getDate_planned_start() {
                return date_planned_start;
            }

            public void setDate_planned_start(String date_planned_start) {
                this.date_planned_start = date_planned_start;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public static class ProcessIdBean {
                /**
                 * process_id : 16
                 * name : 动态包装
                 */

                private int process_id;
                private String name;

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
            }
        }
    }
}
