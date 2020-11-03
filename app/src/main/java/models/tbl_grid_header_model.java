package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by soyuz on 6/5/2017.
 */
@Entity
public class tbl_grid_header_model {

    @Id(autoincrement = true) //I totally don't care about value of this field
    public Long id;
    @Index(unique = true)
    public String grower_id;
    @Index(unique = true)
    public String land_id;

    public String dateOfInspection;
    public String topped;
    public double total_area_of_field_damaged;
    public String stage_of_development;
    public String variety;
    public String intended_treatment_of_land;
    public String signature_grower;
    public String signature_inspector;
    public String signature_inspector2;
    public String subdivision_map;



    @Generated(hash = 454055977)
    public tbl_grid_header_model(Long id, String grower_id, String land_id,
            String dateOfInspection, String topped, double total_area_of_field_damaged,
            String stage_of_development, String variety, String intended_treatment_of_land,
            String signature_grower, String signature_inspector, String signature_inspector2,
            String subdivision_map) {
        this.id = id;
        this.grower_id = grower_id;
        this.land_id = land_id;
        this.dateOfInspection = dateOfInspection;
        this.topped = topped;
        this.total_area_of_field_damaged = total_area_of_field_damaged;
        this.stage_of_development = stage_of_development;
        this.variety = variety;
        this.intended_treatment_of_land = intended_treatment_of_land;
        this.signature_grower = signature_grower;
        this.signature_inspector = signature_inspector;
        this.signature_inspector2 = signature_inspector2;
        this.subdivision_map = subdivision_map;
    }
    @Generated(hash = 1371341719)
    public tbl_grid_header_model() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getDateOfInspection() {
        return this.dateOfInspection;
    }
    public void setDateOfInspection(String dateOfInspection) {
        this.dateOfInspection = dateOfInspection;
    }
    public String getTopped() {
        return this.topped;
    }
    public void setTopped(String topped) {
        this.topped = topped;
    }
    public double getTotal_area_of_field_damaged() {
        return this.total_area_of_field_damaged;
    }
    public void setTotal_area_of_field_damaged(double total_area_of_field_damaged) {
        this.total_area_of_field_damaged = total_area_of_field_damaged;
    }
    public String getStage_of_development() {
        return this.stage_of_development;
    }
    public void setStage_of_development(String stage_of_development) {
        this.stage_of_development = stage_of_development;
    }
    public String getVariety() {
        return this.variety;
    }
    public void setVariety(String variety) {
        this.variety = variety;
    }
    public String getIntended_treatment_of_land() {
        return this.intended_treatment_of_land;
    }
    public void setIntended_treatment_of_land(String intended_treatment_of_land) {
        this.intended_treatment_of_land = intended_treatment_of_land;
    }
    public String getSignature_grower() {
        return signature_grower;
    }

    public void setSignature_grower(String signature_grower) {
        this.signature_grower = signature_grower;
    }

    public String getSignature_inspector() {
        return signature_inspector;
    }

    public void setSignature_inspector(String signature_inspector) {
        this.signature_inspector = signature_inspector;
    }

    public String getSignature_inspector2() {
        return signature_inspector2;
    }

    public void setSignature_inspector2(String signature_inspector2) {
        this.signature_inspector2 = signature_inspector2;
    }

    public String getSubdivision_map() {
        return subdivision_map;
    }

    public void setSubdivision_map(String subdivision_map) {
        this.subdivision_map = subdivision_map;
    }
   

}
