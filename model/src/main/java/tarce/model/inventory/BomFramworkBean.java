package tarce.model.inventory;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by rose.zou on 2017/6/6.
 * bom结构数据
 */

public class BomFramworkBean{
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"product_tmpl_id":49963,"process_id":"静态包装","code":"98.0MA031.100","uuid":"deb16375-4a83-11e7-8196-00909e9aabf6","name":"MA03-1-成品(三角龙)-RT-ENG","bom_id":3799,"bom_ids":[{"code":"CY.100000.001","product_specs":"改为40x40mm 静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","bom_ids":[{"code":"4C.100000.001","bom_ids":[],"qty":0.025,"process_id":false,"id":52252,"product_specs":"静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","is_highlight":false,"name":"砂纸（大）","level":1,"uuid":"dea4e1d4-4a83-11e7-b7de-00909e9aabf6","product_tmpl_id":73077,"product_id":70406}],"qty":1,"process_id":"冲压","id":25033,"name":"砂纸[小]冲压制成品","is_highlight":false,"uuid":"dea5e8f5-4a83-11e7-a4b7-00909e9aabf6","level":0,"product_tmpl_id":52278,"product_id":50611},{"code":"41.200000.015","product_specs":"短边开口，打孔，厚2丝.MA.PXX0 1~2片板塑封用","bom_ids":[],"qty":1,"process_id":false,"id":25034,"name":"POF袋[130*225]","is_highlight":false,"uuid":"dea6b887-4a83-11e7-bb04-00909e9aabf6","level":0,"product_tmpl_id":51002,"product_id":49335},{"code":"43.1MA031.000","product_specs":"182*112mm，128g铜版纸，正面彩印背面黑白","bom_ids":[],"qty":1,"process_id":false,"id":25035,"name":"MA03-1三角龙彩纸-RT-ENG","is_highlight":false,"uuid":"dea79e54-4a83-11e7-a4eb-00909e9aabf6","level":0,"product_tmpl_id":49961,"product_id":48294},{"code":"42.1MAXXX.100","product_specs":"生产尺寸44*24.5*20cm，五层瓦楞，印刷唛头；1片240、2片120、3片80","bom_ids":[],"qty":0.009,"process_id":false,"id":25036,"name":"MAXXX-大纸箱[240片装]-简装","is_highlight":false,"uuid":"dea86961-4a83-11e7-9d98-00909e9aabf6","level":0,"product_tmpl_id":50043,"product_id":48376},{"code":"QJ.0MA031.001","product_specs":false,"bom_ids":[{"code":"CY.0MA031.001","bom_ids":[{"code":"BZ.0MA031.001","bom_ids":[{"code":"KB.1C0000.002","bom_ids":[],"qty":1,"process_id":false,"id":48900,"product_specs":"370*230*3mm ，双杨单清，[ 双椴单清-AC]，经过挑板后再进行卡板","is_highlight":false,"name":"卡板制程品(370*230*3mm 双杨单清)","level":3,"uuid":"deadb1f5-4a83-11e7-986c-00909e9aabf6","product_tmpl_id":62583,"product_id":60330},{"code":"45.1MA030.000","bom_ids":[],"qty":1,"process_id":false,"id":48901,"product_specs":"Triceratops-三角龙，370*230*3mm*0.5,冲出2PCS","is_highlight":false,"name":"MA03-裱纸(三角龙)-P1","level":3,"uuid":"deae8411-4a83-11e7-901f-00909e9aabf6","product_tmpl_id":49976,"product_id":48309}],"qty":0.5,"process_id":"裱纸","id":48855,"product_specs":false,"is_highlight":false,"name":"裱纸制程品-MA03-1-木板(三角龙)-RT","level":2,"uuid":"deaf7b8a-4a83-11e7-a105-00909e9aabf6","product_tmpl_id":72406,"product_id":69763}],"qty":1,"process_id":"冲压","id":48984,"product_specs":false,"is_highlight":false,"name":"冲压制程品-MA03-1-木板(三角龙)-RT-2-1+2","level":1,"uuid":"deb06623-4a83-11e7-b89f-00909e9aabf6","product_tmpl_id":72336,"product_id":69709}],"qty":1,"process_id":"全检","id":48980,"name":"全检制程品-MA03-1-木板(三角龙)-RT-2-1+2","is_highlight":false,"uuid":"deb160b3-4a83-11e7-8a77-00909e9aabf6","level":0,"product_tmpl_id":72359,"product_id":69731}],"product_id":49963},"res_msg":"","res_code":1}
     */

    private String jsonrpc;
    private Object id;
    private ResultBean result;

    public String getJsonrpc(){
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc){
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
         * res_data : {"product_tmpl_id":49963,"process_id":"静态包装","code":"98.0MA031.100","uuid":"deb16375-4a83-11e7-8196-00909e9aabf6","name":"MA03-1-成品(三角龙)-RT-ENG","bom_id":3799,"bom_ids":[{"code":"CY.100000.001","product_specs":"改为40x40mm 静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","bom_ids":[{"code":"4C.100000.001","bom_ids":[],"qty":0.025,"process_id":false,"id":52252,"product_specs":"静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","is_highlight":false,"name":"砂纸（大）","level":1,"uuid":"dea4e1d4-4a83-11e7-b7de-00909e9aabf6","product_tmpl_id":73077,"product_id":70406}],"qty":1,"process_id":"冲压","id":25033,"name":"砂纸[小]冲压制成品","is_highlight":false,"uuid":"dea5e8f5-4a83-11e7-a4b7-00909e9aabf6","level":0,"product_tmpl_id":52278,"product_id":50611},{"code":"41.200000.015","product_specs":"短边开口，打孔，厚2丝.MA.PXX0 1~2片板塑封用","bom_ids":[],"qty":1,"process_id":false,"id":25034,"name":"POF袋[130*225]","is_highlight":false,"uuid":"dea6b887-4a83-11e7-bb04-00909e9aabf6","level":0,"product_tmpl_id":51002,"product_id":49335},{"code":"43.1MA031.000","product_specs":"182*112mm，128g铜版纸，正面彩印背面黑白","bom_ids":[],"qty":1,"process_id":false,"id":25035,"name":"MA03-1三角龙彩纸-RT-ENG","is_highlight":false,"uuid":"dea79e54-4a83-11e7-a4eb-00909e9aabf6","level":0,"product_tmpl_id":49961,"product_id":48294},{"code":"42.1MAXXX.100","product_specs":"生产尺寸44*24.5*20cm，五层瓦楞，印刷唛头；1片240、2片120、3片80","bom_ids":[],"qty":0.009,"process_id":false,"id":25036,"name":"MAXXX-大纸箱[240片装]-简装","is_highlight":false,"uuid":"dea86961-4a83-11e7-9d98-00909e9aabf6","level":0,"product_tmpl_id":50043,"product_id":48376},{"code":"QJ.0MA031.001","product_specs":false,"bom_ids":[{"code":"CY.0MA031.001","bom_ids":[{"code":"BZ.0MA031.001","bom_ids":[{"code":"KB.1C0000.002","bom_ids":[],"qty":1,"process_id":false,"id":48900,"product_specs":"370*230*3mm ，双杨单清，[ 双椴单清-AC]，经过挑板后再进行卡板","is_highlight":false,"name":"卡板制程品(370*230*3mm 双杨单清)","level":3,"uuid":"deadb1f5-4a83-11e7-986c-00909e9aabf6","product_tmpl_id":62583,"product_id":60330},{"code":"45.1MA030.000","bom_ids":[],"qty":1,"process_id":false,"id":48901,"product_specs":"Triceratops-三角龙，370*230*3mm*0.5,冲出2PCS","is_highlight":false,"name":"MA03-裱纸(三角龙)-P1","level":3,"uuid":"deae8411-4a83-11e7-901f-00909e9aabf6","product_tmpl_id":49976,"product_id":48309}],"qty":0.5,"process_id":"裱纸","id":48855,"product_specs":false,"is_highlight":false,"name":"裱纸制程品-MA03-1-木板(三角龙)-RT","level":2,"uuid":"deaf7b8a-4a83-11e7-a105-00909e9aabf6","product_tmpl_id":72406,"product_id":69763}],"qty":1,"process_id":"冲压","id":48984,"product_specs":false,"is_highlight":false,"name":"冲压制程品-MA03-1-木板(三角龙)-RT-2-1+2","level":1,"uuid":"deb06623-4a83-11e7-b89f-00909e9aabf6","product_tmpl_id":72336,"product_id":69709}],"qty":1,"process_id":"全检","id":48980,"name":"全检制程品-MA03-1-木板(三角龙)-RT-2-1+2","is_highlight":false,"uuid":"deb160b3-4a83-11e7-8a77-00909e9aabf6","level":0,"product_tmpl_id":72359,"product_id":69731}],"product_id":49963}
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

        public static class ResDataBean implements ExpandableListItem {
            /**
             * product_tmpl_id : 49963
             * process_id : 静态包装
             * code : 98.0MA031.100
             * uuid : deb16375-4a83-11e7-8196-00909e9aabf6
             * name : MA03-1-成品(三角龙)-RT-ENG
             * bom_id : 3799
             * bom_ids : [{"code":"CY.100000.001","product_specs":"改为40x40mm 静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","bom_ids":[{"code":"4C.100000.001","bom_ids":[],"qty":0.025,"process_id":false,"id":52252,"product_specs":"静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","is_highlight":false,"name":"砂纸（大）","level":1,"uuid":"dea4e1d4-4a83-11e7-b7de-00909e9aabf6","product_tmpl_id":73077,"product_id":70406}],"qty":1,"process_id":"冲压","id":25033,"name":"砂纸[小]冲压制成品","is_highlight":false,"uuid":"dea5e8f5-4a83-11e7-a4b7-00909e9aabf6","level":0,"product_tmpl_id":52278,"product_id":50611},{"code":"41.200000.015","product_specs":"短边开口，打孔，厚2丝.MA.PXX0 1~2片板塑封用","bom_ids":[],"qty":1,"process_id":false,"id":25034,"name":"POF袋[130*225]","is_highlight":false,"uuid":"dea6b887-4a83-11e7-bb04-00909e9aabf6","level":0,"product_tmpl_id":51002,"product_id":49335},{"code":"43.1MA031.000","product_specs":"182*112mm，128g铜版纸，正面彩印背面黑白","bom_ids":[],"qty":1,"process_id":false,"id":25035,"name":"MA03-1三角龙彩纸-RT-ENG","is_highlight":false,"uuid":"dea79e54-4a83-11e7-a4eb-00909e9aabf6","level":0,"product_tmpl_id":49961,"product_id":48294},{"code":"42.1MAXXX.100","product_specs":"生产尺寸44*24.5*20cm，五层瓦楞，印刷唛头；1片240、2片120、3片80","bom_ids":[],"qty":0.009,"process_id":false,"id":25036,"name":"MAXXX-大纸箱[240片装]-简装","is_highlight":false,"uuid":"dea86961-4a83-11e7-9d98-00909e9aabf6","level":0,"product_tmpl_id":50043,"product_id":48376},{"code":"QJ.0MA031.001","product_specs":false,"bom_ids":[{"code":"CY.0MA031.001","bom_ids":[{"code":"BZ.0MA031.001","bom_ids":[{"code":"KB.1C0000.002","bom_ids":[],"qty":1,"process_id":false,"id":48900,"product_specs":"370*230*3mm ，双杨单清，[ 双椴单清-AC]，经过挑板后再进行卡板","is_highlight":false,"name":"卡板制程品(370*230*3mm 双杨单清)","level":3,"uuid":"deadb1f5-4a83-11e7-986c-00909e9aabf6","product_tmpl_id":62583,"product_id":60330},{"code":"45.1MA030.000","bom_ids":[],"qty":1,"process_id":false,"id":48901,"product_specs":"Triceratops-三角龙，370*230*3mm*0.5,冲出2PCS","is_highlight":false,"name":"MA03-裱纸(三角龙)-P1","level":3,"uuid":"deae8411-4a83-11e7-901f-00909e9aabf6","product_tmpl_id":49976,"product_id":48309}],"qty":0.5,"process_id":"裱纸","id":48855,"product_specs":false,"is_highlight":false,"name":"裱纸制程品-MA03-1-木板(三角龙)-RT","level":2,"uuid":"deaf7b8a-4a83-11e7-a105-00909e9aabf6","product_tmpl_id":72406,"product_id":69763}],"qty":1,"process_id":"冲压","id":48984,"product_specs":false,"is_highlight":false,"name":"冲压制程品-MA03-1-木板(三角龙)-RT-2-1+2","level":1,"uuid":"deb06623-4a83-11e7-b89f-00909e9aabf6","product_tmpl_id":72336,"product_id":69709}],"qty":1,"process_id":"全检","id":48980,"name":"全检制程品-MA03-1-木板(三角龙)-RT-2-1+2","is_highlight":false,"uuid":"deb160b3-4a83-11e7-8a77-00909e9aabf6","level":0,"product_tmpl_id":72359,"product_id":69731}]
             * product_id : 49963
             */

            private int product_tmpl_id;
            public String process_id;
            public String code;
            private String uuid;
            public String name;
            private int bom_id;
            private int product_id;
            public List<BomIdsBeanX> bom_ids;

            public int getProduct_tmpl_id() {
                return product_tmpl_id;
            }

            public void setProduct_tmpl_id(int product_tmpl_id) {
                this.product_tmpl_id = product_tmpl_id;
            }

            public String getProcess_id() {
                return process_id;
            }

            public void setProcess_id(String process_id) {
                this.process_id = process_id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getBom_id() {
                return bom_id;
            }

            public void setBom_id(int bom_id) {
                this.bom_id = bom_id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public List<BomIdsBeanX> getBom_ids() {
                return bom_ids;
            }

            public void setBom_ids(List<BomIdsBeanX> bom_ids) {
                this.bom_ids = bom_ids;
            }

            public boolean mExpanded = false;
            @Override
            public List<?> getChildItemList() {
                return bom_ids;
            }

            @Override
            public boolean isExpanded() {
                return mExpanded;
            }

            @Override
            public void setExpanded(boolean isExpanded){
                    mExpanded = isExpanded;
            }

            public static class BomIdsBeanX implements ExpandableListItem{
                /**
                 * code : CY.100000.001
                 * product_specs : 改为40x40mm 静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌
                 * bom_ids : [{"code":"4C.100000.001","bom_ids":[],"qty":0.025,"process_id":false,"id":52252,"product_specs":"静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌 ","is_highlight":false,"name":"砂纸（大）","level":1,"uuid":"dea4e1d4-4a83-11e7-b7de-00909e9aabf6","product_tmpl_id":73077,"product_id":70406}]
                 * qty : 1.0
                 * process_id : 冲压
                 * id : 25033
                 * name : 砂纸[小]冲压制成品
                 * is_highlight : false
                 * uuid : dea5e8f5-4a83-11e7-a4b7-00909e9aabf6
                 * level : 0
                 * product_tmpl_id : 52278
                 * product_id : 50611
                 */

                public String code;
                public String product_specs;
                private double qty;
                private String process_id;
                private int id;
                public String name;
                private boolean is_highlight;
                private String uuid;
                private int level;
                private int product_tmpl_id;
                private int product_id;
                public List<BomIdsBean> bom_ids;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getProduct_specs() {
                    return product_specs;
                }

                public void setProduct_specs(String product_specs) {
                    this.product_specs = product_specs;
                }

                public double getQty() {
                    return qty;
                }

                public void setQty(double qty) {
                    this.qty = qty;
                }

                public String getProcess_id() {
                    return process_id;
                }

                public void setProcess_id(String process_id) {
                    this.process_id = process_id;
                }

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

                public boolean isIs_highlight() {
                    return is_highlight;
                }

                public void setIs_highlight(boolean is_highlight) {
                    this.is_highlight = is_highlight;
                }

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getProduct_tmpl_id() {
                    return product_tmpl_id;
                }

                public void setProduct_tmpl_id(int product_tmpl_id) {
                    this.product_tmpl_id = product_tmpl_id;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public List<BomIdsBean> getBom_ids() {
                    return bom_ids;
                }

                public void setBom_ids(List<BomIdsBean> bom_ids) {
                    this.bom_ids = bom_ids;
                }

                private boolean mExpand = false;
                @Override
                public List<?> getChildItemList() {
                    return bom_ids;
                }

                @Override
                public boolean isExpanded() {
                    return mExpand;
                }

                @Override
                public void setExpanded(boolean isExpanded) {
                    mExpand = isExpanded;
                }

                public static class BomIdsBean implements ExpandableListItem{
                    /**
                     * code : 4C.100000.001
                     * bom_ids : []
                     * qty : 0.025
                     * process_id : false
                     * id : 52252
                     * product_specs : 静电直砂,280x230mm ，240(目)颗粒 美国诺顿品牌
                     * is_highlight : false
                     * name : 砂纸（大）
                     * level : 1
                     * uuid : dea4e1d4-4a83-11e7-b7de-00909e9aabf6
                     * product_tmpl_id : 73077
                     * product_id : 70406
                     */

                    public String code;
                    private double qty;
                    private boolean process_id;
                    private int id;
                    public String product_specs;
                    private boolean is_highlight;
                    public String name;
                    private int level;
                    private String uuid;
                    private int product_tmpl_id;
                    private int product_id;
                    public List<BomSubBean> bom_ids;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public double getQty() {
                        return qty;
                    }

                    public void setQty(double qty) {
                        this.qty = qty;
                    }

                    public boolean isProcess_id() {
                        return process_id;
                    }

                    public void setProcess_id(boolean process_id) {
                        this.process_id = process_id;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getProduct_specs() {
                        return product_specs;
                    }

                    public void setProduct_specs(String product_specs) {
                        this.product_specs = product_specs;
                    }

                    public boolean isIs_highlight() {
                        return is_highlight;
                    }

                    public void setIs_highlight(boolean is_highlight) {
                        this.is_highlight = is_highlight;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public String getUuid() {
                        return uuid;
                    }

                    public void setUuid(String uuid) {
                        this.uuid = uuid;
                    }

                    public int getProduct_tmpl_id() {
                        return product_tmpl_id;
                    }

                    public void setProduct_tmpl_id(int product_tmpl_id) {
                        this.product_tmpl_id = product_tmpl_id;
                    }

                    public int getProduct_id() {
                        return product_id;
                    }

                    public void setProduct_id(int product_id) {
                        this.product_id = product_id;
                    }

                    public List<BomSubBean> getBom_ids() {
                        return bom_ids;
                    }

                    public void setBom_ids(List<BomSubBean> bom_ids) {
                        this.bom_ids = bom_ids;
                    }

                    private boolean mExpand = false;
                    @Override
                    public List<?> getChildItemList() {
                        return bom_ids;
                    }

                    @Override
                    public boolean isExpanded() {
                        return mExpand;
                    }

                    @Override
                    public void setExpanded(boolean isExpanded) {
                        mExpand = isExpanded;
                    }
                }
            }
        }
    }
}
