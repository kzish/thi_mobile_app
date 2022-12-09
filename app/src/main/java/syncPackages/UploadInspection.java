/*******************************************************************************
 * Copyright (c) 2022.
 ******************************************************************************/

package syncPackages;

import static zw.co.qbit.thi_app.HomeActivity.loadAllTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import daoModels.tbl_grid_header_modelDao;
import daoModels.tbl_leaf_modelDao;
import daoModels.tbl_signature_modelDao;
import daoModels.tbl_worksheet_modelDao;
import models.tbl_grid_header_model;
import models.tbl_leaf_model;
import models.tbl_signature_model;
import models.tbl_task_model;
import models.tbl_user_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.thi;

public class UploadInspection {

    private static String message="";

    public static void upload(final tbl_task_model task) {

        if (task.claim_stage != 100) {
            HomeActivity.ed.showCustomDialog(HomeActivity.activity, "This inspection is not completed");
            HomeActivity.pd.hide();
            return;
        }

        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                HomeActivity.pd.showCustomDialog(HomeActivity.activity, "Uploading please wait...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                HomeActivity.pd.hide();
                loadAllTasks();
                Log.e("kz", "onPostExecute");
                Log.e("kz", message);
                if(!message.equals("")){
                    HomeActivity.ed.showCustomDialog(HomeActivity.activity, message);
                }
            }

            @Override
            protected String doInBackground(String... strings) {

                tbl_user_model user = thi.daoSession.getTbl_user_modelDao().loadAll().get(0);
                if (user == null) {
                    message = "User is null";
                    return message;
                }
                //TODO: send the signatures uncomment below
                tbl_signature_model sig = thi.daoSession.getTbl_signature_modelDao()
                        .queryBuilder()
                        .where(
                                tbl_signature_modelDao.Properties.Land_id.eq(task.land_id),
                                tbl_signature_modelDao.Properties.Grower_id.eq(task.grower_id)
                        ).unique();
//                if (sig == null) {
//                    message = "Signature is empty";
//                    return message;
//                }

                List<tbl_worksheet_model> myworksheets = thi.daoSession.getTbl_worksheet_modelDao()
                        .queryBuilder()
                        .where(tbl_worksheet_modelDao.Properties.Land_id.eq(task.land_id))
                        .list();

                if (myworksheets.size() == 0) {
                    message = "There are no worksheets";
                    return message;
                }
                tbl_grid_header_model gh = thi.daoSession.getTbl_grid_header_modelDao()
                        .queryBuilder()
                        .where(
                                tbl_grid_header_modelDao.Properties.Land_id.eq(task.land_id),
                                tbl_grid_header_modelDao.Properties.Grower_id.eq(task.grower_id)
                        )
                        .unique();

                if (gh == null) {
                    message = "Grid header not saved";
                    return message;
                }

                for (tbl_worksheet_model worksheet : myworksheets) {
                    List<tbl_leaf_model> myleaves = thi.daoSession.getTbl_leaf_modelDao()
                            .queryBuilder()
                            .where(
                                    tbl_leaf_modelDao.Properties.Worksheet_id.eq(worksheet._id)//only thi worksheet leaves
                            )
                            .list();

                    syncUploadLeavesTask leavesTask1 = new syncUploadLeavesTask();
                    leavesTask1.sync(HomeActivity.activity, myleaves);

                    while (
                            !leavesTask1.completed
                    ) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    syncUploadWorkSheetTask syncWKTask = new syncUploadWorkSheetTask();
                    syncWKTask.sync(HomeActivity.activity, worksheet, user);

                    while (
                            !syncWKTask.completed
                    ) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                syncUploadTaskGridHeader syncGHTask = new syncUploadTaskGridHeader();
                syncGHTask.sync(HomeActivity.activity, task, gh, user, sig);

                while (
                        !syncGHTask.completed
                ) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        }.execute();
    }
}
