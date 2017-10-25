package tarce.model.inventory;

import java.io.Serializable;
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
    /**
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 223, in get_mrp_production\n    'display_name': production.display_name,\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 980, in determine_value\n    self.compute_value(recs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 935, in compute_value\n    self._compute_value(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 927, in _compute_value\n    getattr(records, self.compute)()\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 1513, in _compute_display_name\n    names = dict(self.name_get())\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 1532, in name_get\n    result.append((record.id, convert(record[name], record)))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3051, in _prefetch_field\n    result = records.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 2991, in read\n    self._read_from_database(stored, inherited)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3119, in _read_from_database\n    cr.execute(query_str, params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 141, in wrapper\n    return f(self, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 218, in execute\n    res = self._obj.execute(query, params)\nProgrammingError: column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n\n","exception_type":"internal_error","message":"column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n","name":"psycopg2.ProgrammingError","arguments":["column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n"]}}
     */

    private ErrorBean error;

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

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
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
            public Object getOrigin() {
                return origin;
            }

            public void setOrigin(Object origin) {
                this.origin = origin;
            }

            public ProduceLineBean getProduction_line_id() {
                return production_line_id;
            }

            public void setProduction_line_id(ProduceLineBean production_line_id) {
                this.production_line_id = production_line_id;
            }

            public boolean isAgainProduct() {
                return againProduct;
            }

            public void setAgainProduct(boolean againProduct) {
                this.againProduct = againProduct;
            }

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

            private boolean againProduct = false;
            private ProduceLineBean production_line_id;
            private Object origin;
            private String in_charge_name;
            private String state;
            private ProcessIdBean process_id;
            private String display_name;
            private double product_qty;
            private int order_id;
            private String date_planned_start;
            private String product_name;



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

            public static class ProduceLineBean implements Serializable {
                private Object name;
                private Object production_line_id;

                public Object getName() {
                    if (name instanceof Boolean){
                        name = "";
                    }
                    return name;
                }

                public void setName(Object name) {
                    this.name = name;
                }

                public Object getProduction_line_id() {
                    if (production_line_id instanceof Boolean){
                        production_line_id = 0;
                    }
                    return production_line_id;
                }

                public void setProduction_line_id(Object production_line_id) {
                    this.production_line_id = production_line_id;
                }
            }
            public static class ProcessIdBean {
                public Object getProcess_id() {
                    if (process_id instanceof Boolean){
                        process_id = 0;
                    }
                    return process_id;
                }

                public void setProcess_id(Object process_id) {
                    this.process_id = process_id;
                }

                public Object getName() {
                    if (name instanceof Boolean){
                        name = "";
                    }
                    return name;
                }

                public void setName(Object name) {
                    this.name = name;
                }

                /**
                 * process_id : 8
                 * name : 全检
                 */

                private Object process_id;
                private Object name;


            }
        }
    }

    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_app_api/controllers/controllers.py\", line 223, in get_mrp_production\n    'display_name': production.display_name,\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 980, in determine_value\n    self.compute_value(recs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 935, in compute_value\n    self._compute_value(records)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 927, in _compute_value\n    getattr(records, self.compute)()\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 1513, in _compute_display_name\n    names = dict(self.name_get())\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 1532, in name_get\n    result.append((record.id, convert(record[name], record)))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 5177, in __getitem__\n    return self._fields[key].__get__(self, type(self))\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 869, in __get__\n    self.determine_value(record)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/fields.py\", line 971, in determine_value\n    record._prefetch_field(self)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3051, in _prefetch_field\n    result = records.read([f.name for f in fs], load='_classic_write')\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 2991, in read\n    self._read_from_database(stored, inherited)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/models.py\", line 3119, in _read_from_database\n    cr.execute(query_str, params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 141, in wrapper\n    return f(self, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/sql_db.py\", line 218, in execute\n    res = self._obj.execute(query, params)\nProgrammingError: column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n\n","exception_type":"internal_error","message":"column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n","name":"psycopg2.ProgrammingError","arguments":["column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n"]}
         */

        private String message;
        private int code;
        private DataBean data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * name : psycopg2.ProgrammingError
             * arguments : ["column mrp_production.material_light does not exist\nLINE 1: ...roduction\".\"produce_area_id\" as \"produce_area_id\",\"mrp_produ...\n                                                             ^\n"]
             */

            private String debug;
            private String exception_type;
            private String message;
            private String name;
            private List<String> arguments;

            public String getDebug() {
                return debug;
            }

            public void setDebug(String debug) {
                this.debug = debug;
            }

            public String getException_type() {
                return exception_type;
            }

            public void setException_type(String exception_type) {
                this.exception_type = exception_type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getArguments() {
                return arguments;
            }

            public void setArguments(List<String> arguments) {
                this.arguments = arguments;
            }
        }
    }
}
