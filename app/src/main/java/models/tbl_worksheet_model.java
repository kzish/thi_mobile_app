package models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by soyuz on 6/22/2017.
 */

@Entity
public class tbl_worksheet_model {

    @Id(autoincrement = true)
    public Long _id;
    public int grower_id;
    public int land_id;
    public int sub_division;

    public double undamaged_1,undamaged_2,undamaged_3,undamaged_4;
    public double missing_stand_1,missing_stand_2,missing_stand_3,missing_stand_4;
    public double broken_at_base_1,broken_at_base_2,broken_at_base_3,broken_at_base_4;
    public double broken_half_way_1,broken_half_way_2,broken_half_way_3,broken_half_way_4;
    public String remarks;
    public double hectars_represented_on_this_form;
    public double hectares;





    @Generated(hash = 710369251)
    public tbl_worksheet_model(Long _id, int grower_id, int land_id, int sub_division,
            double undamaged_1, double undamaged_2, double undamaged_3, double undamaged_4,
            double missing_stand_1, double missing_stand_2, double missing_stand_3,
            double missing_stand_4, double broken_at_base_1, double broken_at_base_2,
            double broken_at_base_3, double broken_at_base_4, double broken_half_way_1,
            double broken_half_way_2, double broken_half_way_3, double broken_half_way_4,
            String remarks, double hectars_represented_on_this_form, double hectares) {
        this._id = _id;
        this.grower_id = grower_id;
        this.land_id = land_id;
        this.sub_division = sub_division;
        this.undamaged_1 = undamaged_1;
        this.undamaged_2 = undamaged_2;
        this.undamaged_3 = undamaged_3;
        this.undamaged_4 = undamaged_4;
        this.missing_stand_1 = missing_stand_1;
        this.missing_stand_2 = missing_stand_2;
        this.missing_stand_3 = missing_stand_3;
        this.missing_stand_4 = missing_stand_4;
        this.broken_at_base_1 = broken_at_base_1;
        this.broken_at_base_2 = broken_at_base_2;
        this.broken_at_base_3 = broken_at_base_3;
        this.broken_at_base_4 = broken_at_base_4;
        this.broken_half_way_1 = broken_half_way_1;
        this.broken_half_way_2 = broken_half_way_2;
        this.broken_half_way_3 = broken_half_way_3;
        this.broken_half_way_4 = broken_half_way_4;
        this.remarks = remarks;
        this.hectars_represented_on_this_form = hectars_represented_on_this_form;
        this.hectares = hectares;
    }

    @Generated(hash = 869975876)
    public tbl_worksheet_model() {
    }





    public double getPercentageUndamaged()
    {
        double percentage=0;
        percentage=(undamaged_1+undamaged_2+undamaged_3+undamaged_4);
        percentage=percentage/4;
        return globals.globals.round(percentage,2);
    }

    public double getPercentageMissing()
    {
        double percentage=0;
        percentage=(missing_stand_1+missing_stand_2+missing_stand_3+missing_stand_4);
        percentage=percentage/4;
        return globals.globals.round(percentage,2);
    }

    public double getPercentageBrokenAtBase()
    {
        double percentage=0;
        percentage=(broken_at_base_1+broken_at_base_2+broken_at_base_3+broken_at_base_4);
        percentage=(percentage/4);
        return globals.globals.round(percentage,2);
    }


    public double getPercentageBrokenHalfWay()
    {
        double percentage=0;
        percentage=(broken_half_way_1+broken_half_way_2+broken_half_way_3+broken_half_way_4);
        percentage=(percentage/4);
        return globals.globals.round(percentage,2);
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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

    public int getSub_division() {
        return this.sub_division;
    }

    public void setSub_division(int sub_division) {
        this.sub_division = sub_division;
    }

    public double getUndamaged_1() {
        return this.undamaged_1;
    }

    public void setUndamaged_1(double undamaged_1) {
        this.undamaged_1 = undamaged_1;
    }

    public double getUndamaged_2() {
        return this.undamaged_2;
    }

    public void setUndamaged_2(double undamaged_2) {
        this.undamaged_2 = undamaged_2;
    }

    public double getUndamaged_3() {
        return this.undamaged_3;
    }

    public void setUndamaged_3(double undamaged_3) {
        this.undamaged_3 = undamaged_3;
    }

    public double getUndamaged_4() {
        return this.undamaged_4;
    }

    public void setUndamaged_4(double undamaged_4) {
        this.undamaged_4 = undamaged_4;
    }

    public double getMissing_stand_1() {
        return this.missing_stand_1;
    }

    public void setMissing_stand_1(double missing_stand_1) {
        this.missing_stand_1 = missing_stand_1;
    }

    public double getMissing_stand_2() {
        return this.missing_stand_2;
    }

    public void setMissing_stand_2(double missing_stand_2) {
        this.missing_stand_2 = missing_stand_2;
    }

    public double getMissing_stand_3() {
        return this.missing_stand_3;
    }

    public void setMissing_stand_3(double missing_stand_3) {
        this.missing_stand_3 = missing_stand_3;
    }

    public double getMissing_stand_4() {
        return this.missing_stand_4;
    }

    public void setMissing_stand_4(double missing_stand_4) {
        this.missing_stand_4 = missing_stand_4;
    }

    public double getBroken_at_base_1() {
        return this.broken_at_base_1;
    }

    public void setBroken_at_base_1(double broken_at_base_1) {
        this.broken_at_base_1 = broken_at_base_1;
    }

    public double getBroken_at_base_2() {
        return this.broken_at_base_2;
    }

    public void setBroken_at_base_2(double broken_at_base_2) {
        this.broken_at_base_2 = broken_at_base_2;
    }

    public double getBroken_at_base_3() {
        return this.broken_at_base_3;
    }

    public void setBroken_at_base_3(double broken_at_base_3) {
        this.broken_at_base_3 = broken_at_base_3;
    }

    public double getBroken_at_base_4() {
        return this.broken_at_base_4;
    }

    public void setBroken_at_base_4(double broken_at_base_4) {
        this.broken_at_base_4 = broken_at_base_4;
    }

    public double getBroken_half_way_1() {
        return this.broken_half_way_1;
    }

    public void setBroken_half_way_1(double broken_half_way_1) {
        this.broken_half_way_1 = broken_half_way_1;
    }

    public double getBroken_half_way_2() {
        return this.broken_half_way_2;
    }

    public void setBroken_half_way_2(double broken_half_way_2) {
        this.broken_half_way_2 = broken_half_way_2;
    }

    public double getBroken_half_way_3() {
        return this.broken_half_way_3;
    }

    public void setBroken_half_way_3(double broken_half_way_3) {
        this.broken_half_way_3 = broken_half_way_3;
    }

    public double getBroken_half_way_4() {
        return this.broken_half_way_4;
    }

    public void setBroken_half_way_4(double broken_half_way_4) {
        this.broken_half_way_4 = broken_half_way_4;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getHectars_represented_on_this_form() {
        return this.hectars_represented_on_this_form;
    }

    public void setHectars_represented_on_this_form(double hectars_represented_on_this_form) {
        this.hectars_represented_on_this_form = hectars_represented_on_this_form;
    }

    public double getHectares() {
        return this.hectares;
    }

    public void setHectares(double hectares) {
        this.hectares = hectares;
    }



}
