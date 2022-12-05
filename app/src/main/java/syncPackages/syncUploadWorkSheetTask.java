package syncPackages;

import static zw.co.qbit.thi_app.HomeActivity.loadAllTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.greendao.query.DeleteQuery;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import daoModels.tbl_grid_header_modelDao;
import daoModels.tbl_leaf_modelDao;
import daoModels.tbl_signature_modelDao;
import daoModels.tbl_task_modelDao;
import daoModels.tbl_worksheet_modelDao;
import globals.globals;
import models.tbl_grid_header_model;
import models.tbl_leaf_model;
import models.tbl_signature_model;
import models.tbl_task_model;
import models.tbl_user_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.AppSingleton;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.thi;

/**
 * Created by soyuz on 4/17/2017.
 */
/* this method will submit the inspection to the server **/
public class syncUploadWorkSheetTask {


    static String tag = "syncUploadWorkSheetTask";
    static String REQUEST_tag = tag;

    public static boolean completed = false;
    static String message = "";

    public boolean sync(Context context, final tbl_worksheet_model wk, final tbl_user_model user) {
        try {

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    globals.server_url + "/mobileapi/uploadInspectionWorkSheet.aspx",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            HomeActivity.pd.hide();
                            Log.e("kz", "worksheet upload response............" + response);
                            if (response.equals("0")) {
                                Log.e("kz", "upload worksheet failed");
                            } else {
                                Log.e("kz", "worksheet uploaded");
                            }
                            completed = true;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("kz", error.getMessage());
                    completed = true;
                }
            }) {
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    JSONObject worksheet = new JSONObject();
                    try {
                        worksheet.put("worksheet_client_id", wk._id);
                        worksheet.put("undamaged", wk.getPercentageUndamaged());
                        worksheet.put("missing_stand", wk.getPercentageMissing());
                        worksheet.put("broken_base", wk.getPercentageBrokenAtBase());
                        worksheet.put("broke_half_way", wk.getPercentageBrokenHalfWay());
                        worksheet.put("remarks", wk.remarks);
                        worksheet.put("grower_id", wk.grower_id);
                        worksheet.put("hectares", wk.hectares);
                        worksheet.put("subdivision", wk.sub_division);
                        worksheet.put("land_id", wk.land_id);
                        worksheet.put("inspector_name", user.username);
                    } catch (Exception ex) {
                        Log.e("kz", "................line 240" + ex);
                    }
                    params.put("worksheet", worksheet.toString());
                    return params;
                }

                ;
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2, 1.0f));
            AppSingleton.getInstance(context).addToRequestQueue(strReq, REQUEST_tag);
        } catch (Exception ex) {
            Log.e("kz", ex.getMessage());
            return false;
        }
        return true;
    }

}

