package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by soyuz on 5/25/2017.
 */
@Entity
public class tbl_user_model {

    @Id(autoincrement = true)
    public Long id;//local id here on the phone
    public String username;
    public String password;
    public String loggedin;
    @Generated(hash = 1551347390)
    public tbl_user_model(Long id, String username, String password,
            String loggedin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.loggedin = loggedin;
    }
    @Generated(hash = 1644154549)
    public tbl_user_model() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLoggedin() {
        return this.loggedin;
    }
    public void setLoggedin(String loggedin) {
        this.loggedin = loggedin;
    }


}
