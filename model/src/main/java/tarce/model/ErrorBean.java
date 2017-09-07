package tarce.model;

import java.io.Serializable;
import java.util.List;

import tarce.model.inventory.PickingDetailBean;

/**
 * Created by zouwansheng on 2017/9/6.
 */

public class ErrorBean implements Serializable{
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

    public static class DataBean implements Serializable{
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
