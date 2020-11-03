package zw.co.qbit.thi_app;
import android.app.Application;
import android.content.Context;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import daoModels.DaoMaster;
import daoModels.DaoSession;

/**
 * Created by soyuz on 4/26/2017.
 */

public class thi  extends Application {
    public static Context CTX;
    public static DaoSession daoSession;
    public static DaoMaster daoMaster;
    public static SQLiteDatabase db;
    public static DaoMaster.DevOpenHelper helper;
    @Override
    public void onCreate() {

        super.onCreate();
        CTX=getApplicationContext();
        helper      = new DaoMaster.DevOpenHelper(this, "thi",null);
        db          = helper.getWritableDatabase();
        daoSession  = new DaoMaster(db).newSession();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


}
