package daoModels;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import models.tbl_media_model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TBL_MEDIA_MODEL".
*/
public class tbl_media_modelDao extends AbstractDao<tbl_media_model, Long> {

    public static final String TABLENAME = "TBL_MEDIA_MODEL";

    /**
     * Properties of entity tbl_media_model.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Image_url = new Property(1, String.class, "image_url", false, "IMAGE_URL");
        public final static Property Grower_id = new Property(2, String.class, "grower_id", false, "GROWER_ID");
        public final static Property Land_id = new Property(3, String.class, "land_id", false, "LAND_ID");
        public final static Property Datetime = new Property(4, String.class, "datetime", false, "DATETIME");
        public final static Property Notes = new Property(5, String.class, "notes", false, "NOTES");
        public final static Property Synced = new Property(6, String.class, "synced", false, "SYNCED");
    }


    public tbl_media_modelDao(DaoConfig config) {
        super(config);
    }
    
    public tbl_media_modelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TBL_MEDIA_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"IMAGE_URL\" TEXT," + // 1: image_url
                "\"GROWER_ID\" TEXT," + // 2: grower_id
                "\"LAND_ID\" TEXT," + // 3: land_id
                "\"DATETIME\" TEXT," + // 4: datetime
                "\"NOTES\" TEXT," + // 5: notes
                "\"SYNCED\" TEXT);"); // 6: synced
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TBL_MEDIA_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, tbl_media_model entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(2, image_url);
        }
 
        String grower_id = entity.getGrower_id();
        if (grower_id != null) {
            stmt.bindString(3, grower_id);
        }
 
        String land_id = entity.getLand_id();
        if (land_id != null) {
            stmt.bindString(4, land_id);
        }
 
        String datetime = entity.getDatetime();
        if (datetime != null) {
            stmt.bindString(5, datetime);
        }
 
        String notes = entity.getNotes();
        if (notes != null) {
            stmt.bindString(6, notes);
        }
 
        String synced = entity.getSynced();
        if (synced != null) {
            stmt.bindString(7, synced);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, tbl_media_model entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(2, image_url);
        }
 
        String grower_id = entity.getGrower_id();
        if (grower_id != null) {
            stmt.bindString(3, grower_id);
        }
 
        String land_id = entity.getLand_id();
        if (land_id != null) {
            stmt.bindString(4, land_id);
        }
 
        String datetime = entity.getDatetime();
        if (datetime != null) {
            stmt.bindString(5, datetime);
        }
 
        String notes = entity.getNotes();
        if (notes != null) {
            stmt.bindString(6, notes);
        }
 
        String synced = entity.getSynced();
        if (synced != null) {
            stmt.bindString(7, synced);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public tbl_media_model readEntity(Cursor cursor, int offset) {
        tbl_media_model entity = new tbl_media_model( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // image_url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // grower_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // land_id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // datetime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // notes
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // synced
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, tbl_media_model entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImage_url(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGrower_id(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLand_id(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDatetime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNotes(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSynced(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(tbl_media_model entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(tbl_media_model entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(tbl_media_model entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}