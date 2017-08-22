package tarce.model.inventory;

import com.google.gson.annotations.SerializedName;


import java.util.List;

/**
 * Created by rose.zou on 2017/5/18.
 */

public class GetNumProcess {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : {"res_data":{"3":[{"count":2,"state":"delay"}],"4":[{"count":11,"state":"delay"}],"5":[{"count":12,"state":"delay"}],"6":[],"7":[],"8":[],"10":[],"11":[],"16":[],"17":[],"21":[]},"res_msg":"","res_code":1}
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
         * res_data : {"3":[{"count":2,"state":"delay"}],"4":[{"count":11,"state":"delay"}],"5":[{"count":12,"state":"delay"}],"6":[],"7":[],"8":[],"10":[],"11":[],"16":[],"17":[],"21":[]}
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

        public static class ResDataBean{
            public List<_$1Bean> get_$1() {
                return _$1;
            }

            public void set_$1(List<_$1Bean> _$1) {
                this._$1 = _$1;
            }

            public List<_$2Bean> get_$2() {
                return _$2;
            }

            public void set_$2(List<_$2Bean> _$2) {
                this._$2 = _$2;
            }

            @SerializedName("1")
            private List<_$1Bean> _$1;
            @SerializedName("2")
            private List<_$2Bean> _$2;
            @SerializedName("3")
            private List<_$3Bean> _$3;
            @SerializedName("4")
            private List<_$4Bean> _$4;
            @SerializedName("5")
            private List<_$5Bean> _$5;
            @SerializedName("21")
            private List<_$21Bean> _$21;
            @SerializedName("6")
            private List<_$6Bean> _$6;
            @SerializedName("7")
            private List<?> _$7;
            @SerializedName("8")
            private List<?> _$8;

            public List<_$21Bean> get_$21() {
                return _$21;
            }

            public void set_$21(List<_$21Bean> _$21) {
                this._$21 = _$21;
            }

            public List<?> get_$9() {
                return _$9;
            }

            public void set_$9(List<?> _$9) {
                this._$9 = _$9;
            }

            @SerializedName("9")
            private List<?> _$9;
            @SerializedName("10")
            private List<?> _$10;
            @SerializedName("11")
            private List<?> _$11;

            public List<?> get_$12() {
                return _$12;
            }

            public void set_$12(List<?> _$12) {
                this._$12 = _$12;
            }

            public List<?> get_$13() {
                return _$13;
            }

            public void set_$13(List<?> _$13) {
                this._$13 = _$13;
            }

            public List<?> get_$14() {
                return _$14;
            }

            public void set_$14(List<?> _$14) {
                this._$14 = _$14;
            }

            public List<?> get_$15() {
                return _$15;
            }

            public void set_$15(List<?> _$15) {
                this._$15 = _$15;
            }

            @SerializedName("12")
            private List<?> _$12;
            @SerializedName("13")
            private List<?> _$13;
            @SerializedName("14")
            private List<?> _$14;
            @SerializedName("15")
            private List<?> _$15;
            @SerializedName("16")
            private List<?> _$16;
            @SerializedName("17")
            private List<?> _$17;
            @SerializedName("23")
            private List<_$23Bean> _$23;

            public List<_$23Bean> get_$23() {
                return _$23;
            }

            public void set_$23(List<_$23Bean> _$23) {
                this._$23 = _$23;
            }

            public List<_$6Bean> get_$6() {
                return _$6;
            }

            public void set_$6(List<_$6Bean> _$6) {
                this._$6 = _$6;
            }

            public List<_$3Bean> get_$3() {
                return _$3;
            }

            public void set_$3(List<_$3Bean> _$3) {
                this._$3 = _$3;
            }

            public List<_$4Bean> get_$4() {
                return _$4;
            }

            public void set_$4(List<_$4Bean> _$4) {
                this._$4 = _$4;
            }

            public List<_$5Bean> get_$5() {
                return _$5;
            }

            public void set_$5(List<_$5Bean> _$5) {
                this._$5 = _$5;
            }


            public List<?> get_$7() {
                return _$7;
            }

            public void set_$7(List<?> _$7) {
                this._$7 = _$7;
            }

            public List<?> get_$8() {
                return _$8;
            }

            public void set_$8(List<?> _$8) {
                this._$8 = _$8;
            }

            public List<?> get_$10() {
                return _$10;
            }

            public void set_$10(List<?> _$10) {
                this._$10 = _$10;
            }

            public List<?> get_$11() {
                return _$11;
            }

            public void set_$11(List<?> _$11) {
                this._$11 = _$11;
            }

            public List<?> get_$16() {
                return _$16;
            }

            public void set_$16(List<?> _$16) {
                this._$16 = _$16;
            }

            public List<?> get_$17() {
                return _$17;
            }

            public void set_$17(List<?> _$17) {
                this._$17 = _$17;
            }


            public static class _$21Bean {
                /**
                 * count : 2
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$23Bean {
                /**
                 * count : 2
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$3Bean {
                /**
                 * count : 2
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$1Bean {
                /**
                 * count : 2
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$2Bean {
                /**
                 * count : 2
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }

            public static class _$4Bean {
                /**
                 * count : 11
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }

            public static class _$5Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$6Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$7Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$8Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$9Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$10Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
            public static class _$11Bean {
                /**
                 * count : 12
                 * state : delay
                 */

                private int count;
                private String state;

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }
        }
    }
}
