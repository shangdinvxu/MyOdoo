package tarce.model.inventory;

import java.util.List;

/**
 * Created by rose.zou on 2017/5/22.
 * 点击领料之后新页面详情bean
 */

public class PickingDetailBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":[{"origin":"MO170514126:MO170514126","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170514127","product_qty":1200,"order_id":44420,"date_planned_start":"2017-07-13 19:03:26","product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2"},{"origin":"SO2017042602002:MO/2017042740662","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042740663","product_qty":1400,"order_id":40718,"date_planned_start":"2017-06-26 10:04:59","product_name":"[QJ.0JP204.001] 全检制程品-JP204-木板(蝴蝶)-2-1+2"},{"origin":"SO2017032301355:MO/2017042136906","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042136907","product_qty":792,"order_id":36956,"date_planned_start":"2017-06-20 03:40:12","product_name":"[QJ.0DV181.001] 全检制程品-DV181A(组合别墅-浴室)-RT-3-1"},{"origin":false,"in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520025","product_qty":2470,"order_id":45583,"date_planned_start":"2017-05-20 02:37:30","product_name":"[QJ.0JP234.001] 全检制程品-JP234-木板(坦克)-2-1+2"},{"origin":"MO/2017041932237:MO/2017041932237, MO/2017050341713:MO/2017050341714","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520024","product_qty":4524,"order_id":45582,"date_planned_start":"2017-05-20 02:35:39","product_name":"[QJ.0F1370.003] 全检制程品-F137(美国公路加油站)-RT-4-3+4"},{"origin":"MO/2017041932237:MO/2017041932237, MO/2017050341713:MO/2017050341714","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520023","product_qty":4449,"order_id":45581,"date_planned_start":"2017-05-20 02:35:03","product_name":"[QJ.0F1370.001] 全检制程品-F137(美国公路加油站)-RT-4-1+2"},{"origin":"MO/2017042538395:MO/2017042538395","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042538402","product_qty":4130,"order_id":38454,"date_planned_start":"2017-05-20 02:34:36","product_name":"[QJ.0F1440.003] 全检制程品-F144(马来西亚唐人街)-RT-4-3+4"},{"origin":"MO/2017042538395:MO/2017042538395","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042538396","product_qty":4130,"order_id":38448,"date_planned_start":"2017-05-20 02:34:03","product_name":"[QJ.0F1440.001] 全检制程品-F144(马来西亚唐人街)-RT-4-1+2"},{"origin":false,"in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520022","product_qty":2500,"order_id":45580,"date_planned_start":"2017-05-20 02:28:44","product_name":"[QJ.0JP240.001] 全检制程品-JP240-木板(三轮车)-2-1+2"},{"origin":false,"in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520021","product_qty":1600,"order_id":45579,"date_planned_start":"2017-05-20 02:27:27","product_name":"[QJ.0JP150.001] 全检制程品-JP150-木板(跑车)"},{"origin":"SO2017042501957:MO/2017042538541, SO2017042702038:MO/2017042740598, SO2017042602002:MO/2017042740765, SO2017032201337:MO/2017041831359, SO2017032801432:MO/2017041730962, SO2017041801817:MO/2017041831959","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520020","product_qty":1698,"order_id":45578,"date_planned_start":"2017-05-20 02:25:45","product_name":"[QJ.0MJ401.003] 全检制程品-MJ401(伦敦塔桥)-RT-4-3+4"},{"origin":"SO2017042501957:MO/2017042538541, SO2017042702038:MO/2017042740598, SO2017042602002:MO/2017042740765, SO2017032201337:MO/2017041831359, SO2017032801432:MO/2017041730962, SO2017041801817:MO/2017041831959","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520017","product_qty":1655,"order_id":45575,"date_planned_start":"2017-05-20 02:24:42","product_name":"[QJ.0MJ401.001] 全检制程品-MJ401(伦敦塔桥)-RT-4-1+2"},{"origin":"SO2017030601020:MO/2017041629473, MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906, MO/2017042137377:MO/2017042137378","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520014","product_qty":805,"order_id":45572,"date_planned_start":"2017-05-20 02:18:15","product_name":"[QJ.0DV181.005] 全检制程品-DV181B(组合别墅-浴室家具)-RT-2-2"},{"origin":"MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906, MO/2017042137377:MO/2017042137378","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520011","product_qty":820,"order_id":45569,"date_planned_start":"2017-05-20 02:17:13","product_name":"[QJ.0DV181.004] 全检制程品-DV181B(组合别墅-浴室家具)-RT-2-1"},{"origin":"MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906, MO/2017042137377:MO/2017042137378","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520008","product_qty":805,"order_id":45566,"date_planned_start":"2017-05-20 02:15:29","product_name":"[QJ.0DV181.003] 全检制程品-DV181A(组合别墅-浴室)-RT-3-3"},{"origin":"SO2017030601020:MO/2017041629473, MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520007","product_qty":808,"order_id":45565,"date_planned_start":"2017-05-20 02:11:25","product_name":"[QJ.0DV181.002] 全检制程品-DV181A(组合别墅-浴室)-RT-3-2"}],"res_msg":"","res_code":1}
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
         * res_data : [{"origin":"MO170514126:MO170514126","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170514127","product_qty":1200,"order_id":44420,"date_planned_start":"2017-07-13 19:03:26","product_name":"[QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2"},{"origin":"SO2017042602002:MO/2017042740662","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042740663","product_qty":1400,"order_id":40718,"date_planned_start":"2017-06-26 10:04:59","product_name":"[QJ.0JP204.001] 全检制程品-JP204-木板(蝴蝶)-2-1+2"},{"origin":"SO2017032301355:MO/2017042136906","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042136907","product_qty":792,"order_id":36956,"date_planned_start":"2017-06-20 03:40:12","product_name":"[QJ.0DV181.001] 全检制程品-DV181A(组合别墅-浴室)-RT-3-1"},{"origin":false,"in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520025","product_qty":2470,"order_id":45583,"date_planned_start":"2017-05-20 02:37:30","product_name":"[QJ.0JP234.001] 全检制程品-JP234-木板(坦克)-2-1+2"},{"origin":"MO/2017041932237:MO/2017041932237, MO/2017050341713:MO/2017050341714","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520024","product_qty":4524,"order_id":45582,"date_planned_start":"2017-05-20 02:35:39","product_name":"[QJ.0F1370.003] 全检制程品-F137(美国公路加油站)-RT-4-3+4"},{"origin":"MO/2017041932237:MO/2017041932237, MO/2017050341713:MO/2017050341714","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520023","product_qty":4449,"order_id":45581,"date_planned_start":"2017-05-20 02:35:03","product_name":"[QJ.0F1370.001] 全检制程品-F137(美国公路加油站)-RT-4-1+2"},{"origin":"MO/2017042538395:MO/2017042538395","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042538402","product_qty":4130,"order_id":38454,"date_planned_start":"2017-05-20 02:34:36","product_name":"[QJ.0F1440.003] 全检制程品-F144(马来西亚唐人街)-RT-4-3+4"},{"origin":"MO/2017042538395:MO/2017042538395","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO/2017042538396","product_qty":4130,"order_id":38448,"date_planned_start":"2017-05-20 02:34:03","product_name":"[QJ.0F1440.001] 全检制程品-F144(马来西亚唐人街)-RT-4-1+2"},{"origin":false,"in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520022","product_qty":2500,"order_id":45580,"date_planned_start":"2017-05-20 02:28:44","product_name":"[QJ.0JP240.001] 全检制程品-JP240-木板(三轮车)-2-1+2"},{"origin":false,"in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520021","product_qty":1600,"order_id":45579,"date_planned_start":"2017-05-20 02:27:27","product_name":"[QJ.0JP150.001] 全检制程品-JP150-木板(跑车)"},{"origin":"SO2017042501957:MO/2017042538541, SO2017042702038:MO/2017042740598, SO2017042602002:MO/2017042740765, SO2017032201337:MO/2017041831359, SO2017032801432:MO/2017041730962, SO2017041801817:MO/2017041831959","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520020","product_qty":1698,"order_id":45578,"date_planned_start":"2017-05-20 02:25:45","product_name":"[QJ.0MJ401.003] 全检制程品-MJ401(伦敦塔桥)-RT-4-3+4"},{"origin":"SO2017042501957:MO/2017042538541, SO2017042702038:MO/2017042740598, SO2017042602002:MO/2017042740765, SO2017032201337:MO/2017041831359, SO2017032801432:MO/2017041730962, SO2017041801817:MO/2017041831959","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520017","product_qty":1655,"order_id":45575,"date_planned_start":"2017-05-20 02:24:42","product_name":"[QJ.0MJ401.001] 全检制程品-MJ401(伦敦塔桥)-RT-4-1+2"},{"origin":"SO2017030601020:MO/2017041629473, MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906, MO/2017042137377:MO/2017042137378","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520014","product_qty":805,"order_id":45572,"date_planned_start":"2017-05-20 02:18:15","product_name":"[QJ.0DV181.005] 全检制程品-DV181B(组合别墅-浴室家具)-RT-2-2"},{"origin":"MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906, MO/2017042137377:MO/2017042137378","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520011","product_qty":820,"order_id":45569,"date_planned_start":"2017-05-20 02:17:13","product_name":"[QJ.0DV181.004] 全检制程品-DV181B(组合别墅-浴室家具)-RT-2-1"},{"origin":"MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906, MO/2017042137377:MO/2017042137378","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520008","product_qty":805,"order_id":45566,"date_planned_start":"2017-05-20 02:15:29","product_name":"[QJ.0DV181.003] 全检制程品-DV181A(组合别墅-浴室)-RT-3-3"},{"origin":"SO2017030601020:MO/2017041629473, MO/2017041831501:MO/2017041831501, SO2017032301355:MO/2017042136906","in_charge_name":"陈小娟","state":"finish_prepare_material","process_id":{"process_id":8,"name":"全检"},"display_name":"MO170520007","product_qty":808,"order_id":45565,"date_planned_start":"2017-05-20 02:11:25","product_name":"[QJ.0DV181.002] 全检制程品-DV181A(组合别墅-浴室)-RT-3-2"}]
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
             * origin : MO170514126:MO170514126
             * in_charge_name : 陈小娟
             * state : finish_prepare_material
             * process_id : {"process_id":8,"name":"全检"}
             * display_name : MO170514127
             * product_qty : 1200.0
             * order_id : 44420
             * date_planned_start : 2017-07-13 19:03:26
             * product_name : [QJ.0JP217.001] 全检制程品-JP217-木板(松鼠)-2-1+2
             */
            private String origin;
            private String in_charge_name;
            private String state;
            private ProcessIdBean process_id;
            private String display_name;
            private double product_qty;
            private int order_id;
            private String date_planned_start;
            private String product_name;

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
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
                 * process_id : 8
                 * name : 全检
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
