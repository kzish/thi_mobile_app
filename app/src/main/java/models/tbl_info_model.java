package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by soyuz on 8/14/2017.
 */

@Entity
public class tbl_info_model {
    @Id(autoincrement = true)
    public Long id;
    public String lastSync;
    @Generated(hash = 1878025382)
    public tbl_info_model(Long id, String lastSync) {
        this.id = id;
        this.lastSync = lastSync;
    }
    @Generated(hash = 828794780)
    public tbl_info_model() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLastSync() {
        return this.lastSync;
    }
    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

   



}
