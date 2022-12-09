package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by soyuz on 6/25/2017.
 */

@Entity
public class tbl_signature_model {
            @Id(autoincrement = true) //I totally don't care about value of this field
            public Long id;//local id here on the phone
            public String grower_id;
            public String land_id;
            public String grower_signature_uri;
            public String inspector_signature_uri;
            public String second_inspector_name;
            public String second_inspector_siginature_uri;
            public String draw_map_uri;
          @Generated(hash = 1215268706)
            public tbl_signature_model(Long id, String grower_id, String land_id, String grower_signature_uri,
                    String inspector_signature_uri, String second_inspector_name, String second_inspector_siginature_uri,
                    String draw_map_uri) {
                this.id = id;
                this.grower_id = grower_id;
                this.land_id = land_id;
                this.grower_signature_uri = grower_signature_uri;
                this.inspector_signature_uri = inspector_signature_uri;
                this.second_inspector_name = second_inspector_name;
                this.second_inspector_siginature_uri = second_inspector_siginature_uri;
                this.draw_map_uri = draw_map_uri;
            }
            @Generated(hash = 465136001)
            public tbl_signature_model() {
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
            public String getGrower_signature_uri() {
                return this.grower_signature_uri;
            }
            public void setGrower_signature_uri(String grower_signature_uri) {
                this.grower_signature_uri = grower_signature_uri;
            }
            public String getInspector_signature_uri() {
                return this.inspector_signature_uri;
            }
            public void setInspector_signature_uri(String inspector_signature_uri) {
                this.inspector_signature_uri = inspector_signature_uri;
            }
            public String getSecond_inspector_name() {
                return this.second_inspector_name;
            }
            public void setSecond_inspector_name(String second_inspector_name) {
                this.second_inspector_name = second_inspector_name;
            }
            public String getSecond_inspector_siginature_uri() {
                return this.second_inspector_siginature_uri;
            }
            public void setSecond_inspector_siginature_uri(String second_inspector_siginature_uri) {
                this.second_inspector_siginature_uri = second_inspector_siginature_uri;
            }

    public String getDraw_map_uri() {
        return this.draw_map_uri;
    }

    public void setDraw_map_uri(String draw_map_uri) {
        this.draw_map_uri = draw_map_uri;
    }
}
