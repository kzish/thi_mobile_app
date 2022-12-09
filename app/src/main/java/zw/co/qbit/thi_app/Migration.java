/*******************************************************************************
 * Copyright (c) 2022.
 ******************************************************************************/

package zw.co.qbit.thi_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import daoModels.DaoMaster;
import daoModels.DaoSession;
import daoModels.tbl_attachmentsDao;
import daoModels.tbl_signature_modelDao;
import database.MyDaoMaster;
import models.*;

public class Migration {

    public static Context context;

    public static void dumpData() {
        tbl_attachments[] attachments = thi.daoSession.getTbl_attachmentsDao().loadAll().toArray(new tbl_attachments[0]);
        tbl_grid_header_model[] grid_header_models = thi.daoSession.getTbl_grid_header_modelDao().loadAll().toArray(new tbl_grid_header_model[0]);
        tbl_grower_model[] grower_models = thi.daoSession.getTbl_grower_modelDao().loadAll().toArray(new tbl_grower_model[0]);
        tbl_info_model[] info_models = thi.daoSession.getTbl_info_modelDao().loadAll().toArray(new tbl_info_model[0]);
        tbl_land_model[] land_models = thi.daoSession.getTbl_land_modelDao().loadAll().toArray(new tbl_land_model[0]);
        tbl_leaf_model[] leaf_models = thi.daoSession.getTbl_leaf_modelDao().loadAll().toArray(new tbl_leaf_model[0]);
        tbl_media_model[] media_models = thi.daoSession.getTbl_media_modelDao().loadAll().toArray(new tbl_media_model[0]);
        tbl_signature_model[] signature_models = thi.daoSession.getTbl_signature_modelDao().loadAll().toArray(new tbl_signature_model[0]);
        tbl_task_model[] task_models = thi.daoSession.getTbl_task_modelDao().loadAll().toArray(new tbl_task_model[0]);
        tbl_user_model[] user_models = thi.daoSession.getTbl_user_modelDao().loadAll().toArray(new tbl_user_model[0]);
        tbl_worksheet_model[] worksheet_models = thi.daoSession.getTbl_worksheet_modelDao().loadAll().toArray(new tbl_worksheet_model[0]);

        Gson gson = new Gson();
        String json_attachments = gson.toJson(attachments);
        String json_grid_header_models = gson.toJson(grid_header_models);
        String json_grower_models = gson.toJson(grower_models);
        String json_info_models = gson.toJson(info_models);
        String json_land_models = gson.toJson(land_models);
        String json_leaf_models = gson.toJson(leaf_models);
        String json_media_models = gson.toJson(media_models);
        String json_signature_models = gson.toJson(signature_models);
        String json_task_models = gson.toJson(task_models);
        String json_user_models = gson.toJson(user_models);
        String json_worksheet_models = gson.toJson(worksheet_models);

        writeToFile(json_attachments, context, "json_attachments.txt");
        writeToFile(json_grid_header_models, context, "json_grid_header_models.txt");
        writeToFile(json_grower_models, context, "json_grower_models.txt");
        writeToFile(json_info_models, context, "json_info_models.txt");
        writeToFile(json_land_models, context, "json_land_models.txt");
        writeToFile(json_leaf_models, context, "json_leaf_models.txt");
        writeToFile(json_media_models, context, "json_media_models.txt");
        writeToFile(json_signature_models, context, "json_signature_models.txt");
        writeToFile(json_task_models, context, "json_task_models.txt");
        writeToFile(json_user_models, context, "json_user_models.txt");
        writeToFile(json_worksheet_models, context, "json_worksheet_models.txt");

        Log.e("kz", "Migration data dump completed");
    }

    public static void loadData() {
        String json_attachments = readFromFile(context, "json_attachments.txt");
        String json_grid_header_models = readFromFile(context, "json_grid_header_models.txt");
        String json_grower_models = readFromFile(context, "json_grower_models.txt");
        String json_info_models = readFromFile(context, "json_info_models.txt");
        String json_land_models = readFromFile(context, "json_land_models.txt");
        String json_leaf_models = readFromFile(context, "json_leaf_models.txt");
        String json_media_models = readFromFile(context, "json_media_models.txt");
        String json_signature_models = readFromFile(context, "json_signature_models.txt");
        String json_task_models = readFromFile(context, "json_task_models.txt");
        String json_user_models = readFromFile(context, "json_user_models.txt");
        String json_worksheet_models = readFromFile(context, "json_worksheet_models.txt");

        Gson gson = new Gson();
        tbl_attachments[] attachments = gson.fromJson(json_attachments, tbl_attachments[].class);
        tbl_grid_header_model[] grid_header_models = gson.fromJson(json_grid_header_models, tbl_grid_header_model[].class);
        tbl_grower_model[] grower_models = gson.fromJson(json_grower_models, tbl_grower_model[].class);
        tbl_info_model[] info_models = gson.fromJson(json_info_models, tbl_info_model[].class);
        tbl_land_model[] land_models = gson.fromJson(json_land_models, tbl_land_model[].class);
        tbl_leaf_model[] leaf_models = gson.fromJson(json_leaf_models, tbl_leaf_model[].class);
        tbl_media_model[] media_models = gson.fromJson(json_media_models, tbl_media_model[].class);
        tbl_signature_model[] signature_models = gson.fromJson(json_signature_models, tbl_signature_model[].class);
        tbl_task_model[] task_models = gson.fromJson(json_task_models, tbl_task_model[].class);
        tbl_user_model[] user_models = gson.fromJson(json_user_models, tbl_user_model[].class);
        tbl_worksheet_model[] worksheet_models = gson.fromJson(json_worksheet_models, tbl_worksheet_model[].class);

        for (tbl_attachments item : attachments) {
            thi.daoSession.getTbl_attachmentsDao().insert(item);
        }

        for (tbl_grid_header_model item : grid_header_models) {
            thi.daoSession.getTbl_grid_header_modelDao().insert(item);
        }

        for (tbl_grower_model item : grower_models) {
            thi.daoSession.getTbl_grower_modelDao().insert(item);
        }

        for (tbl_info_model item : info_models) {
            thi.daoSession.getTbl_info_modelDao().insert(item);
        }

        for (tbl_land_model item : land_models) {
            thi.daoSession.getTbl_land_modelDao().insert(item);
        }

        for (tbl_leaf_model item : leaf_models) {
            thi.daoSession.getTbl_leaf_modelDao().insert(item);
        }

        for (tbl_media_model item : media_models) {
            thi.daoSession.getTbl_media_modelDao().insert(item);
        }

        for (tbl_signature_model item : signature_models) {
            thi.daoSession.getTbl_signature_modelDao().insert(item);
        }

        for (tbl_task_model item : task_models) {
            thi.daoSession.getTbl_task_modelDao().insert(item);
        }

        for (tbl_user_model item : user_models) {
            thi.daoSession.getTbl_user_modelDao().insert(item);
        }

        for (tbl_worksheet_model item : worksheet_models) {
            thi.daoSession.getTbl_worksheet_modelDao().insert(item);
        }
    }


    private static void writeToFile(String data, Context context, String file_name) {
        try {

            String directory = Environment.getExternalStorageDirectory() + "/thiDumps";
            File thiDumps = new File(directory);
            if (!thiDumps.exists()) thiDumps.mkdirs();

            File file_dump = new File(directory, file_name);
            if (file_dump.exists()) file_dump.delete();

            FileWriter writer = new FileWriter(file_dump);
            writer.append(data);
            writer.flush();
            writer.close();

            Log.e("kz", "data dump: " + file_name);
        } catch (IOException e) {
            Log.e("kz", "File write failed: " + e.toString());
        }
    }


    private static String readFromFile(Context context, String file_name) {

        String data = "";
        String directory = Environment.getExternalStorageDirectory() + "/thiDumps/";
        file_name = directory + file_name;

        File file = new File(file_name);
        if (file.exists()) {
            try {
                String line = "";
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    stringBuilder.append("\n").append(line);
                }
                br.close();
                data = stringBuilder.toString();
            } catch (FileNotFoundException e) {
                Log.e("kz", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("kz", "Can not read file: " + e.toString());
            }
        }

        return data;
    }
}
