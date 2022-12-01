/*******************************************************************************
 * Copyright (c) 2022.
 ******************************************************************************/

package syncPackages;

import static zw.co.qbit.thi_app.HomeActivity.loadAllTasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

import daoModels.tbl_grid_header_modelDao;
import daoModels.tbl_leaf_modelDao;
import daoModels.tbl_signature_modelDao;
import daoModels.tbl_task_modelDao;
import daoModels.tbl_worksheet_modelDao;
import models.tbl_grid_header_model;
import models.tbl_leaf_model;
import models.tbl_signature_model;
import models.tbl_task_model;
import models.tbl_user_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.thi;

public class DeleteInspection {

    private static void _doDeleteInspection(final tbl_task_model task) {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                HomeActivity.pd.showCustomDialog(HomeActivity.activity, "Deleting Inspection please wait...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                HomeActivity.pd.hide();
                loadAllTasks();
                Log.e("kz", "onPostExecute");
            }

            @Override
            protected String doInBackground(String... strings) {

                //delete the worksheet
                final DeleteQuery<tbl_worksheet_model> tableDeleteQuery = thi.daoSession.queryBuilder(tbl_worksheet_model.class)
                        .where(tbl_worksheet_modelDao.Properties.Grower_id.eq(task.grower_id)
                                , tbl_worksheet_modelDao.Properties.Land_id.eq(task.land_id))
                        .buildDelete();
                tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                thi.daoSession.clear();

                //delete the gridsheet leaves
                final DeleteQuery<tbl_leaf_model> tableDeleteQuery1 = thi.daoSession.queryBuilder(tbl_leaf_model.class)
                        .where(tbl_leaf_modelDao.Properties.Grower_id.eq(task.grower_id)
                                , tbl_leaf_modelDao.Properties.Land_id.eq(task.land_id))
                        .buildDelete();
                tableDeleteQuery1.executeDeleteWithoutDetachingEntities();
                thi.daoSession.clear();

                //delete the gridsheet header
                final DeleteQuery<tbl_grid_header_model> tableDeleteQuery3 = thi.daoSession.queryBuilder(tbl_grid_header_model.class)
                        .where(tbl_grid_header_modelDao.Properties.Grower_id.eq(task.grower_id)
                                , tbl_grid_header_modelDao.Properties.Land_id.eq(task.land_id))
                        .buildDelete();
                tableDeleteQuery3.executeDeleteWithoutDetachingEntities();
                thi.daoSession.clear();

                //delete the signatures TODO also delete the signatures from the file
                final DeleteQuery<tbl_signature_model> tableDeleteQuery4 = thi.daoSession.queryBuilder(tbl_signature_model.class)
                        .where(tbl_signature_modelDao.Properties.Grower_id.eq(task.grower_id)
                                , tbl_signature_modelDao.Properties.Land_id.eq(task.land_id))
                        .buildDelete();
                tableDeleteQuery4.executeDeleteWithoutDetachingEntities();
                thi.daoSession.clear();

                //delete the tasks
                final DeleteQuery<tbl_task_model> tableDeleteQuery5 = thi.daoSession.queryBuilder(tbl_task_model.class)
                        .where(tbl_task_modelDao.Properties.Grower_id.eq(task.grower_id)
                                , tbl_task_modelDao.Properties.Land_id.eq(task.land_id))
                        .buildDelete();
                tableDeleteQuery5.executeDeleteWithoutDetachingEntities();
                thi.daoSession.clear();

                return "";
            }
        }.execute();
    }

    public static void delete(final tbl_task_model task) {

        new AlertDialog.Builder(HomeActivity.activity)
                .setMessage("Are you sure you want to delete this inspection?").
                setTitle("Delete Inspection?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _doDeleteInspection(task);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                }).create()
                .show();
    }
}
