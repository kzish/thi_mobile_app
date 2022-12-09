package zw.co.qbit.thi_app;

import android.app.Application;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.multidex.MultiDex;

import org.greenrobot.greendao.database.Database;

import daoModels.DaoMaster;
import daoModels.DaoSession;
import database.MyDaoMaster;

/**
 * Created by soyuz on 4/26/2017.
 */

public class thi extends Application {
    public static Context CTX;
    public static DaoSession daoSession;
    //public static SQLiteDatabase db;
    public static Database db;
    //public static DaoMaster.DevOpenHelper helper_master;//does not contain migrations
    public static MyDaoMaster.DevOpenHelper helper;//use MyDaoMaster which contains migrations

    @Override
    public void onCreate() {
        super.onCreate();
        CTX = getApplicationContext();
        //helper_master = new DaoMaster.DevOpenHelper(this, "thi", null);
        helper = new MyDaoMaster.DevOpenHelper(this.CTX, "thi", null);
        //db = helper.getWritableDatabase();
        db = helper.getWritableDb();
        daoSession = new MyDaoMaster(db).newSession();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
