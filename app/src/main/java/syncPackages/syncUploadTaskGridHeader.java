package syncPackages;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.greendao.query.DeleteQuery;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daoModels.tbl_grid_header_modelDao;
import daoModels.tbl_land_modelDao;
import daoModels.tbl_leaf_modelDao;
import daoModels.tbl_signature_modelDao;
import daoModels.tbl_task_modelDao;
import daoModels.tbl_worksheet_modelDao;
import globals.globals;
import models.tbl_grid_header_model;
import models.tbl_land_model;
import models.tbl_leaf_model;
import models.tbl_signature_model;
import models.tbl_task_model;
import models.tbl_user_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.AppSingleton;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.thi;

import static zw.co.qbit.thi_app.HomeActivity.loadAllTasks;
import static zw.co.qbit.thi_app.SubDivisionIndex.refreshList;

import androidx.annotation.NonNull;

/**
 * Created by soyuz on 4/17/2017.
 */
/* this method will submit the inspection to the server **/
public class syncUploadTaskGridHeader {


    static String tag = "syncUploadTask";
    public static boolean isBusy = false;
    static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    static String REQUEST_tag = tag;

    public static boolean completed = false;
    static String message = "";

    public boolean sync(Context context, final tbl_task_model task, final tbl_grid_header_model gh, final tbl_user_model user, final tbl_signature_model sig) {
        isBusy = true;
        try {

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    globals.server_url + "/mobileapi/uploadInspectionGridHeader.aspx",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            HomeActivity.pd.hide();
                            if (response.equals("0")) {
                                Log.e(tag, "response:............." + response);
                                Log.e(tag, "upload gridheader failed");
                            } else {
                                HomeActivity.id.showCustomDialog(HomeActivity.activity, "Inspection Submitted...");
                                gh.uploaded = 1;
                                task.uploaded = 1;

                                tbl_grid_header_modelDao ghDao = thi.daoSession.getTbl_grid_header_modelDao();
                                ghDao.update(gh);

                                tbl_task_modelDao taskDao = thi.daoSession.getTbl_task_modelDao();
                                taskDao.update(task);
                            }
                            completed = true;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(tag, error.getMessage());
                    completed = true;
                    HomeActivity.ed.showCustomDialog(HomeActivity.activity, "Error uploading inspection...");
                    HomeActivity.pd.hide();
                }
            }) {
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //then get the signatures afterwards
                    JSONObject gridHeader = new JSONObject();
                    try {
                        gridHeader.put("variety", gh.variety);
                        gridHeader.put("topped", gh.topped.equals("true") ? "1" : "0");
                        gridHeader.put("total_area_of_damage", gh.total_area_of_field_damaged);
                        gridHeader.put("intended_treatment", gh.intended_treatment_of_land);
                        gridHeader.put("development_stage", gh.stage_of_development);
                        gridHeader.put("signature_grower", gh.signature_grower);
                        gridHeader.put("signature_inspector", gh.signature_inspector);
                        gridHeader.put("signature_2ndInspector", gh.signature_inspector2);
                        gridHeader.put("subdivision_map", gh.subdivision_map);
                        gridHeader.put("date", gh.dateOfInspection);
                        gridHeader.put("inspector_name", user.username);
                        gridHeader.put("grid_client_id", gh.id);
                        gridHeader.put("grower_id", gh.grower_id);
                        gridHeader.put("land_id", gh.land_id);
                        gridHeader.put("second_inspector_name", sig.second_inspector_name);
                    } catch (Exception ex) {
                        Log.e(tag, ex.getMessage());
                    }

                    //now get the signatures
                    String header_ = gridHeader.toString();
                    Bitmap sig_grower = null;
                    Bitmap sig_1stinspector = null;
                    Bitmap sig_2ndinspector = null;
                    Bitmap draw_map = null;
                    try {
                        sig_grower = MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.grower_signature_uri));
                        sig_1stinspector = MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.inspector_signature_uri));
                        sig_2ndinspector = MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.second_inspector_siginature_uri));
                        draw_map = MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.draw_map_uri));
                    } catch (Exception ex) {
                        Log.e(tag, ex.getMessage());
                    }
                    params.put("gridheader", header_);
                    if (sig_grower != null) {
                        params.put("grower_signature", globals.imageToString(sig_grower));
                    }
                    if (sig_1stinspector != null) {
                        params.put("inspector_signature", globals.imageToString(sig_1stinspector));
                    }
                    if (sig_2ndinspector != null) {
                        params.put("inspector_signature2", globals.imageToString(sig_2ndinspector));
                    }
                    if (draw_map != null) {
                        params.put("sub-division map", globals.imageToString(draw_map));
                    }
                    params.put("inspector2_name", sig.second_inspector_name);
                    return params;
                }

                ;
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2, 1.0f));
            AppSingleton.getInstance(context).addToRequestQueue(strReq, REQUEST_tag);
        } catch (Exception ex) {
            Log.e(tag, ex.getMessage());
            return false;
        }
        return true;
    }

}

