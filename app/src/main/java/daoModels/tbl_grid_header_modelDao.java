package daoModels;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import models.tbl_grid_header_model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TBL_GRID_HEADER_MODEL".
*/
public class tbl_grid_header_modelDao extends AbstractDao<tbl_grid_header_model, Long> {

    public static final String TABLENAME = "TBL_GRID_HEADER_MODEL";

    /**
     * Properties of entity tbl_grid_header_model.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Grower_id = new Property(1, String.class, "grower_id", false, "GROWER_ID");
        public final static Property Land_id = new Property(2, String.class, "land_id", false, "LAND_ID");
        public final static Property DateOfInspection = new Property(3, String.class, "dateOfInspection", false, "DATE_OF_INSPECTION");
        public final static Property Topped = new Property(4, String.class, "topped", false, "TOPPED");
        public final static Property Total_area_of_field_damaged = new Property(5, double.class, "total_area_of_field_damaged", false, "TOTAL_AREA_OF_FIELD_DAMAGED");
        public final static Property Stage_of_development = new Property(6, String.class, "stage_of_development", false, "STAGE_OF_DEVELOPMENT");
        public final static Property Variety = new Property(7, String.class, "variety", false, "VARIETY");
        public final static Property Intended_treatment_of_land = new Property(8, String.class, "intended_treatment_of_land", false, "INTENDED_TREATMENT_OF_LAND");
        public final static Property Signature_grower = new Property(9, String.class, "signature_grower", false, "SIGNATURE_GROWER");
        public final static Property Signature_inspector = new Property(10, String.class, "signature_inspector", false, "SIGNATURE_INSPECTOR");
        public final static Property Signature_inspector2 = new Property(11, String.class, "signature_inspector2", false, "SIGNATURE_INSPECTOR2");
        public final static Property Subdivision_map = new Property(12, String.class, "subdivision_map", false, "SUBDIVISION_MAP");
        public final static Property Uploaded = new Property(13, int.class, "uploaded", false, "UPLOADED");
    }


    public tbl_grid_header_modelDao(DaoConfig config) {
        super(config);
    }
    
    public tbl_grid_header_modelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TBL_GRID_HEADER_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"GROWER_ID\" TEXT," + // 1: grower_id
                "\"LAND_ID\" TEXT," + // 2: land_id
                "\"DATE_OF_INSPECTION\" TEXT," + // 3: dateOfInspection
                "\"TOPPED\" TEXT," + // 4: topped
                "\"TOTAL_AREA_OF_FIELD_DAMAGED\" REAL NOT NULL ," + // 5: total_area_of_field_damaged
                "\"STAGE_OF_DEVELOPMENT\" TEXT," + // 6: stage_of_development
                "\"VARIETY\" TEXT," + // 7: variety
                "\"INTENDED_TREATMENT_OF_LAND\" TEXT," + // 8: intended_treatment_of_land
                "\"SIGNATURE_GROWER\" TEXT," + // 9: signature_grower
                "\"SIGNATURE_INSPECTOR\" TEXT," + // 10: signature_inspector
                "\"SIGNATURE_INSPECTOR2\" TEXT," + // 11: signature_inspector2
                "\"SUBDIVISION_MAP\" TEXT," + // 12: subdivision_map
                "\"UPLOADED\" INTEGER NOT NULL );"); // 13: uploaded
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TBL_GRID_HEADER_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, tbl_grid_header_model entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String grower_id = entity.getGrower_id();
        if (grower_id != null) {
            stmt.bindString(2, grower_id);
        }
 
        String land_id = entity.getLand_id();
        if (land_id != null) {
            stmt.bindString(3, land_id);
        }
 
        String dateOfInspection = entity.getDateOfInspection();
        if (dateOfInspection != null) {
            stmt.bindString(4, dateOfInspection);
        }
 
        String topped = entity.getTopped();
        if (topped != null) {
            stmt.bindString(5, topped);
        }
        stmt.bindDouble(6, entity.getTotal_area_of_field_damaged());
 
        String stage_of_development = entity.getStage_of_development();
        if (stage_of_development != null) {
            stmt.bindString(7, stage_of_development);
        }
 
        String variety = entity.getVariety();
        if (variety != null) {
            stmt.bindString(8, variety);
        }
 
        String intended_treatment_of_land = entity.getIntended_treatment_of_land();
        if (intended_treatment_of_land != null) {
            stmt.bindString(9, intended_treatment_of_land);
        }
 
        String signature_grower = entity.getSignature_grower();
        if (signature_grower != null) {
            stmt.bindString(10, signature_grower);
        }
 
        String signature_inspector = entity.getSignature_inspector();
        if (signature_inspector != null) {
            stmt.bindString(11, signature_inspector);
        }
 
        String signature_inspector2 = entity.getSignature_inspector2();
        if (signature_inspector2 != null) {
            stmt.bindString(12, signature_inspector2);
        }
 
        String subdivision_map = entity.getSubdivision_map();
        if (subdivision_map != null) {
            stmt.bindString(13, subdivision_map);
        }
        stmt.bindLong(14, entity.getUploaded());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, tbl_grid_header_model entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String grower_id = entity.getGrower_id();
        if (grower_id != null) {
            stmt.bindString(2, grower_id);
        }
 
        String land_id = entity.getLand_id();
        if (land_id != null) {
            stmt.bindString(3, land_id);
        }
 
        String dateOfInspection = entity.getDateOfInspection();
        if (dateOfInspection != null) {
            stmt.bindString(4, dateOfInspection);
        }
 
        String topped = entity.getTopped();
        if (topped != null) {
            stmt.bindString(5, topped);
        }
        stmt.bindDouble(6, entity.getTotal_area_of_field_damaged());
 
        String stage_of_development = entity.getStage_of_development();
        if (stage_of_development != null) {
            stmt.bindString(7, stage_of_development);
        }
 
        String variety = entity.getVariety();
        if (variety != null) {
            stmt.bindString(8, variety);
        }
 
        String intended_treatment_of_land = entity.getIntended_treatment_of_land();
        if (intended_treatment_of_land != null) {
            stmt.bindString(9, intended_treatment_of_land);
        }
 
        String signature_grower = entity.getSignature_grower();
        if (signature_grower != null) {
            stmt.bindString(10, signature_grower);
        }
 
        String signature_inspector = entity.getSignature_inspector();
        if (signature_inspector != null) {
            stmt.bindString(11, signature_inspector);
        }
 
        String signature_inspector2 = entity.getSignature_inspector2();
        if (signature_inspector2 != null) {
            stmt.bindString(12, signature_inspector2);
        }
 
        String subdivision_map = entity.getSubdivision_map();
        if (subdivision_map != null) {
            stmt.bindString(13, subdivision_map);
        }
        stmt.bindLong(14, entity.getUploaded());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public tbl_grid_header_model readEntity(Cursor cursor, int offset) {
        tbl_grid_header_model entity = new tbl_grid_header_model( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // grower_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // land_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // dateOfInspection
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // topped
            cursor.getDouble(offset + 5), // total_area_of_field_damaged
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // stage_of_development
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // variety
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // intended_treatment_of_land
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // signature_grower
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // signature_inspector
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // signature_inspector2
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // subdivision_map
            cursor.getInt(offset + 13) // uploaded
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, tbl_grid_header_model entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGrower_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLand_id(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDateOfInspection(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTopped(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTotal_area_of_field_damaged(cursor.getDouble(offset + 5));
        entity.setStage_of_development(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setVariety(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIntended_treatment_of_land(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSignature_grower(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSignature_inspector(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSignature_inspector2(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSubdivision_map(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUploaded(cursor.getInt(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(tbl_grid_header_model entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(tbl_grid_header_model entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(tbl_grid_header_model entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
