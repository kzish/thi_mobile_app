package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by soyuz on 6/2/2017.
 */

@Entity
public class tbl_land_model {


            @Id
            public String land_id;
            public String land_name;
            @Index(unique = true)
            public String grower_id;
            public String land_area;
            public String date_planted;
            public String irrigated;
            public String amendments;
            public String land_primed;
            public String latitude;
            public String longitude;
            public String boundaryCoordinates;
            public String scannedImage;
            /*public String cover_amount;
            public String cover_level;
            public String no_claim_bonus_percentage;
            public String hail_plus;*/
            public boolean isSynced;
            @Generated(hash = 2001606153)
            public tbl_land_model(String land_id, String land_name, String grower_id,
                    String land_area, String date_planted, String irrigated,
                    String amendments, String land_primed, String latitude,
                    String longitude, String boundaryCoordinates, String scannedImage,
                    boolean isSynced) {
                this.land_id = land_id;
                this.land_name = land_name;
                this.grower_id = grower_id;
                this.land_area = land_area;
                this.date_planted = date_planted;
                this.irrigated = irrigated;
                this.amendments = amendments;
                this.land_primed = land_primed;
                this.latitude = latitude;
                this.longitude = longitude;
                this.boundaryCoordinates = boundaryCoordinates;
                this.scannedImage = scannedImage;
                this.isSynced = isSynced;
            }
            @Generated(hash = 1774549687)
            public tbl_land_model() {
            }
            public String getLand_id() {
                return this.land_id;
            }
            public void setLand_id(String land_id) {
                this.land_id = land_id;
            }
            public String getLand_name() {
                return this.land_name;
            }
            public void setLand_name(String land_name) {
                this.land_name = land_name;
            }
            public String getGrower_id() {
                return this.grower_id;
            }
            public void setGrower_id(String grower_id) {
                this.grower_id = grower_id;
            }
            public String getLand_area() {
                return this.land_area;
            }
            public void setLand_area(String land_area) {
                this.land_area = land_area;
            }
            public String getDate_planted() {
                return this.date_planted;
            }
            public void setDate_planted(String date_planted) {
                this.date_planted = date_planted;
            }
            public String getIrrigated() {
                return this.irrigated;
            }
            public void setIrrigated(String irrigated) {
                this.irrigated = irrigated;
            }
            public String getAmendments() {
                return this.amendments;
            }
            public void setAmendments(String amendments) {
                this.amendments = amendments;
            }
            public String getLand_primed() {
                return this.land_primed;
            }
            public void setLand_primed(String land_primed) {
                this.land_primed = land_primed;
            }
            public String getLatitude() {
                return this.latitude;
            }
            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
            public String getLongitude() {
                return this.longitude;
            }
            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
            public String getBoundaryCoordinates() {
                return this.boundaryCoordinates;
            }
            public void setBoundaryCoordinates(String boundaryCoordinates) {
                this.boundaryCoordinates = boundaryCoordinates;
            }
            public String getScannedImage() {
                return this.scannedImage;
            }
            public void setScannedImage(String scannedImage) {
                this.scannedImage = scannedImage;
            }
            public boolean getIsSynced() {
                return this.isSynced;
            }
            public void setIsSynced(boolean isSynced) {
                this.isSynced = isSynced;
            }


}
