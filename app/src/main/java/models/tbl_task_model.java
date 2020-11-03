package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by soyuz on 6/22/2017.
 */

@Entity
public class tbl_task_model {
            @Id
            public String task_id;//same as strike id in strike alert table
            public String grower_id ;
            public String grower_name ;
            public String land_area ;
            public String grower_number;
            public String land_id ;
            public String land_name ;
            public int claim_stage ;
            public String claim_submitted;
            public String directions_to_farm;
            @Generated(hash = 1066756771)
            public tbl_task_model(String task_id, String grower_id, String grower_name,
                    String land_area, String grower_number, String land_id,
                    String land_name, int claim_stage, String claim_submitted,
                    String directions_to_farm) {
                this.task_id = task_id;
                this.grower_id = grower_id;
                this.grower_name = grower_name;
                this.land_area = land_area;
                this.grower_number = grower_number;
                this.land_id = land_id;
                this.land_name = land_name;
                this.claim_stage = claim_stage;
                this.claim_submitted = claim_submitted;
                this.directions_to_farm = directions_to_farm;
            }
            @Generated(hash = 1921302978)
            public tbl_task_model() {
            }
            public String getTask_id() {
                return this.task_id;
            }
            public void setTask_id(String task_id) {
                this.task_id = task_id;
            }
            public String getGrower_id() {
                return this.grower_id;
            }
            public void setGrower_id(String grower_id) {
                this.grower_id = grower_id;
            }
            public String getGrower_name() {
                return this.grower_name;
            }
            public void setGrower_name(String grower_name) {
                this.grower_name = grower_name;
            }
            public String getLand_area() {
                return this.land_area;
            }
            public void setLand_area(String land_area) {
                this.land_area = land_area;
            }
            public String getGrower_number() {
                return this.grower_number;
            }
            public void setGrower_number(String grower_number) {
                this.grower_number = grower_number;
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
            public int getClaim_stage() {
                return this.claim_stage;
            }
            public void setClaim_stage(int claim_stage) {
                this.claim_stage = claim_stage;
            }
            public String getClaim_submitted() {
                return this.claim_submitted;
            }
            public void setClaim_submitted(String claim_submitted) {
                this.claim_submitted = claim_submitted;
            }
            public String getDirections_to_farm() {
                return this.directions_to_farm;
            }
            public void setDirections_to_farm(String directions_to_farm) {
                this.directions_to_farm = directions_to_farm;
            }


}
