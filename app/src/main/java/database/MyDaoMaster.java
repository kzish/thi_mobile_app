/*******************************************************************************
 * Copyright (c) 2022.
 ******************************************************************************/

package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

import daoModels.DaoMaster;
import daoModels.DaoSession;
import zw.co.qbit.thi_app.Migration;
import zw.co.qbit.thi_app.thi;

public class MyDaoMaster extends DaoMaster {

    public MyDaoMaster(SQLiteDatabase db) {
        super(db);
    }

    public MyDaoMaster(Database db) {
        super(db);
    }

    public static class DevOpenHelper extends DaoMaster.OpenHelper {

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onCreate(Database db) {
            Log.e("kz-greenDAO", "Creating new db");
            super.onCreate(db);//create all tables first
            Migration.context = thi.CTX;
            Migration.loadData();//then load the data
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.e("kz-greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            Migration.context = thi.CTX;
            thi.daoSession = new MyDaoMaster(db).newSession();
            Migration.dumpData();//dump data first
            dropAllTables(db, true);//then drop the tables
            onCreate(db);//now call onCreate
        }
    }
}
