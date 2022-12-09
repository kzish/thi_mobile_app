package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by soyuz on 12/1/2017.
 */
@Entity
public class tbl_attachments {
    @Id(autoincrement = true)
    public Long id;
    public String filename;
    public String filepath;
    public String land_id;
    public String grower_id;
    public String fileType;
    public boolean isSynced;
    @Generated(hash = 537576304)
    public tbl_attachments(Long id, String filename, String filepath,
            String land_id, String grower_id, String fileType, boolean isSynced) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.land_id = land_id;
        this.grower_id = grower_id;
        this.fileType = fileType;
        this.isSynced = isSynced;
    }
    @Generated(hash = 2146524557)
    public tbl_attachments() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilepath() {
        return this.filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getLand_id() {
        return this.land_id;
    }
    public void setLand_id(String land_id) {
        this.land_id = land_id;
    }
    public String getGrower_id() {
        return this.grower_id;
    }
    public void setGrower_id(String grower_id) {
        this.grower_id = grower_id;
    }
    public String getFileType() {
        return this.fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public boolean getIsSynced() {
        return this.isSynced;
    }
    public void setIsSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }
}
