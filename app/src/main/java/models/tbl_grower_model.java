package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by soyuz on 6/2/2017.
 */

@Entity
public class tbl_grower_model {

            @Id
            public String grower_id;
            public String growers_name;
            public String physical_adress;
            public String mobile_number;
            public String business_number;
            public String home_number;
            public String email_adress;
            public String timb_growers_number;
            public String timb_registered_name;
            public String grower_contract_adress;
            public String contracted_to;
            public String no_claim_bnus_rate;
            public String farm_name;
            @Generated(hash = 1301977575)
            public tbl_grower_model(String grower_id, String growers_name,
                    String physical_adress, String mobile_number, String business_number,
                    String home_number, String email_adress, String timb_growers_number,
                    String timb_registered_name, String grower_contract_adress,
                    String contracted_to, String no_claim_bnus_rate, String farm_name) {
                this.grower_id = grower_id;
                this.growers_name = growers_name;
                this.physical_adress = physical_adress;
                this.mobile_number = mobile_number;
                this.business_number = business_number;
                this.home_number = home_number;
                this.email_adress = email_adress;
                this.timb_growers_number = timb_growers_number;
                this.timb_registered_name = timb_registered_name;
                this.grower_contract_adress = grower_contract_adress;
                this.contracted_to = contracted_to;
                this.no_claim_bnus_rate = no_claim_bnus_rate;
                this.farm_name = farm_name;
            }
            @Generated(hash = 596633520)
            public tbl_grower_model() {
            }
            public String getGrower_id() {
                return this.grower_id;
            }
            public void setGrower_id(String grower_id) {
                this.grower_id = grower_id;
            }
            public String getGrowers_name() {
                return this.growers_name;
            }
            public void setGrowers_name(String growers_name) {
                this.growers_name = growers_name;
            }
            public String getPhysical_adress() {
                return this.physical_adress;
            }
            public void setPhysical_adress(String physical_adress) {
                this.physical_adress = physical_adress;
            }
            public String getMobile_number() {
                return this.mobile_number;
            }
            public void setMobile_number(String mobile_number) {
                this.mobile_number = mobile_number;
            }
            public String getBusiness_number() {
                return this.business_number;
            }
            public void setBusiness_number(String business_number) {
                this.business_number = business_number;
            }
            public String getHome_number() {
                return this.home_number;
            }
            public void setHome_number(String home_number) {
                this.home_number = home_number;
            }
            public String getEmail_adress() {
                return this.email_adress;
            }
            public void setEmail_adress(String email_adress) {
                this.email_adress = email_adress;
            }
            public String getTimb_growers_number() {
                return this.timb_growers_number;
            }
            public void setTimb_growers_number(String timb_growers_number) {
                this.timb_growers_number = timb_growers_number;
            }
            public String getTimb_registered_name() {
                return this.timb_registered_name;
            }
            public void setTimb_registered_name(String timb_registered_name) {
                this.timb_registered_name = timb_registered_name;
            }
            public String getGrower_contract_adress() {
                return this.grower_contract_adress;
            }
            public void setGrower_contract_adress(String grower_contract_adress) {
                this.grower_contract_adress = grower_contract_adress;
            }
            public String getContracted_to() {
                return this.contracted_to;
            }
            public void setContracted_to(String contracted_to) {
                this.contracted_to = contracted_to;
            }
            public String getNo_claim_bnus_rate() {
                return this.no_claim_bnus_rate;
            }
            public void setNo_claim_bnus_rate(String no_claim_bnus_rate) {
                this.no_claim_bnus_rate = no_claim_bnus_rate;
            }
            public String getFarm_name() {
                return this.farm_name;
            }
            public void setFarm_name(String farm_name) {
                this.farm_name = farm_name;
            }


}
