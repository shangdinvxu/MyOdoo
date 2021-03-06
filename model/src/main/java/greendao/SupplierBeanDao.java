package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import tarce.model.greendaoBean.SupplierBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SUPPLIER_BEAN".
*/
public class SupplierBeanDao extends AbstractDao<SupplierBean, Long> {

    public static final String TABLENAME = "SUPPLIER_BEAN";

    /**
     * Properties of entity SupplierBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Comment = new Property(0, String.class, "comment", false, "COMMENT");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Partner_id = new Property(2, Long.class, "partner_id", true, "_id");
        public final static Property Phone = new Property(3, String.class, "phone", false, "PHONE");
        public final static Property X_qq = new Property(4, String.class, "x_qq", false, "X_QQ");
    }


    public SupplierBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SupplierBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUPPLIER_BEAN\" (" + //
                "\"COMMENT\" TEXT," + // 0: comment
                "\"NAME\" TEXT," + // 1: name
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 2: partner_id
                "\"PHONE\" TEXT," + // 3: phone
                "\"X_QQ\" TEXT);"); // 4: x_qq
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUPPLIER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SupplierBean entity) {
        stmt.clearBindings();
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(1, comment);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Long partner_id = entity.getPartner_id();
        if (partner_id != null) {
            stmt.bindLong(3, partner_id);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(4, phone);
        }
 
        String x_qq = entity.getX_qq();
        if (x_qq != null) {
            stmt.bindString(5, x_qq);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SupplierBean entity) {
        stmt.clearBindings();
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(1, comment);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Long partner_id = entity.getPartner_id();
        if (partner_id != null) {
            stmt.bindLong(3, partner_id);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(4, phone);
        }
 
        String x_qq = entity.getX_qq();
        if (x_qq != null) {
            stmt.bindString(5, x_qq);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2);
    }    

    @Override
    public SupplierBean readEntity(Cursor cursor, int offset) {
        SupplierBean entity = new SupplierBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // comment
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // partner_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // phone
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // x_qq
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SupplierBean entity, int offset) {
        entity.setComment(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPartner_id(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setPhone(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setX_qq(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SupplierBean entity, long rowId) {
        entity.setPartner_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SupplierBean entity) {
        if(entity != null) {
            return entity.getPartner_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SupplierBean entity) {
        return entity.getPartner_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
