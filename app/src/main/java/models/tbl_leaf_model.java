package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by soyuz on 6/5/2017.
 */


@Entity
public class tbl_leaf_model {

    @Id(autoincrement = true)
    public Long id;
    public int worksheet_id;//link this grid to the worksheet
    public int subdivision, grower_id, land_id, batch_id, plant_id, leaf_number;
    public String percentage_damage;
    public int uploaded;
    @Generated(hash = 1495072564)
    public tbl_leaf_model(Long id, int worksheet_id, int subdivision, int grower_id,
            int land_id, int batch_id, int plant_id, int leaf_number,
            String percentage_damage, int uploaded) {
        this.id = id;
        this.worksheet_id = worksheet_id;
        this.subdivision = subdivision;
        this.grower_id = grower_id;
        this.land_id = land_id;
        this.batch_id = batch_id;
        this.plant_id = plant_id;
        this.leaf_number = leaf_number;
        this.percentage_damage = percentage_damage;
        this.uploaded = uploaded;
    }
    @Generated(hash = 2043713932)
    public tbl_leaf_model() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getWorksheet_id() {
        return this.worksheet_id;
    }
    public void setWorksheet_id(int worksheet_id) {
        this.worksheet_id = worksheet_id;
    }
    public int getSubdivision() {
        return this.subdivision;
    }
    public void setSubdivision(int subdivision) {
        this.subdivision = subdivision;
    }
    public int getGrower_id() {
        return this.grower_id;
    }
    public void setGrower_id(int grower_id) {
        this.grower_id = grower_id;
    }
    public int getLand_id() {
        return this.land_id;
    }
    public void setLand_id(int land_id) {
        this.land_id = land_id;
    }
    public int getBatch_id() {
        return this.batch_id;
    }
    public void setBatch_id(int batch_id) {
        this.batch_id = batch_id;
    }
    public int getPlant_id() {
        return this.plant_id;
    }
    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }
    public int getLeaf_number() {
        return this.leaf_number;
    }
    public void setLeaf_number(int leaf_number) {
        this.leaf_number = leaf_number;
    }
    public String getPercentage_damage() {
        return this.percentage_damage;
    }
    public void setPercentage_damage(String percentage_damage) {
        this.percentage_damage = percentage_damage;
    }
    public int getUploaded() {
        return this.uploaded;
    }
    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }




}
