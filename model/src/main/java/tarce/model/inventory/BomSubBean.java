package tarce.model.inventory;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by rose.zou on 2017/6/6.
 * bom结构里面一个小类
 */

public class BomSubBean implements ExpandableListItem{
    public String code;
    private double qty;

    public Object process_id;
    private int id;
    public String product_specs;
    private boolean is_highlight;
    public String name;
    private int level;
    private String uuid;
    private int product_tmpl_id;
    private int product_id;
    public List<BomBottomBean> bom_ids;

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
    public Object getProcess_id() {
        if (process_id instanceof Boolean){
            process_id = "";
        }
        return process_id;
    }

    public void setProcess_id(Object process_id) {
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

    public boolean is_highlight() {
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

    public List<BomBottomBean> getBom_ids() {
        return bom_ids;
    }

    public void setBom_ids(List<BomBottomBean> bom_ids) {
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

    public static class BomBottomBean implements ExpandableListItem{

        public String code;
        private double qty;

        public Object process_id;
        private int id;
        public String product_specs;
        private boolean is_highlight;
        public String name;
        private int level;
        private String uuid;
        private int product_tmpl_id;
        private int product_id;

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
        public Object getProcess_id() {
            if (process_id instanceof Boolean){
                process_id = "";
            }
            return process_id;
        }

        public void setProcess_id(Object process_id) {
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

        public boolean is_highlight() {
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

        public List<SixBomBottomBean> getBom_ids() {
            return bom_ids;
        }

        public void setBom_ids(List<SixBomBottomBean> bom_ids) {
            this.bom_ids = bom_ids;
        }

        public List<SixBomBottomBean> bom_ids;
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

        public static class SixBomBottomBean{
            public String code;
            private double qty;

            public Object process_id;
            private int id;
            public String product_specs;
            private boolean is_highlight;
            public String name;
            private int level;
            private String uuid;
            private int product_tmpl_id;
            private int product_id;
            public List<?> bom_ids;

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
            public Object getProcess_id() {
                if (process_id instanceof Boolean){
                    process_id = "";
                }
                return process_id;
            }

            public void setProcess_id(Object process_id) {
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

            public boolean is_highlight() {
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

            public List<?> getBom_ids() {
                return bom_ids;
            }

            public void setBom_ids(List<?> bom_ids) {
                this.bom_ids = bom_ids;
            }

        }
    }
}
