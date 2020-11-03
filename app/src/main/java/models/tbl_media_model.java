package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by soyuz on 6/30/2017.
 */

@Entity
public class tbl_media_model {

    @Id(autoincrement = true) //I totally don't care about value of this field
    public Long id;//local id here on the phone
    public String image_url;
    public String grower_id;
    public String land_id;
    public String datetime;
    public String notes;
    public String synced;
    @Generated(hash = 440127700)
    public tbl_media_model(Long id, String image_url, String grower_id,
            String land_id, String datetime, String notes, String synced) {
        this.id = id;
        this.image_url = image_url;
        this.grower_id = grower_id;
        this.land_id = land_id;
        this.datetime = datetime;
        this.notes = notes;
        this.synced = synced;
    }
    @Generated(hash = 268568196)
    public tbl_media_model() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImage_url() {
        return this.image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public String getGrower_id() {
        return this.grower_id;
    }
    public void setGrower_id(String grower_id) {
        this.grower_id = grower_id;
    }
    public String getLand_id() {
        return this.land_id;
    }
    public void setLand_id(String land_id) {
        this.land_id = land_id;
    }
    public String getDatetime() {
        return this.datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getNotes() {
        return this.notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getSynced() {
        return this.synced;
    }
    public void setSynced(String synced) {
        this.synced = synced;
    }


}
