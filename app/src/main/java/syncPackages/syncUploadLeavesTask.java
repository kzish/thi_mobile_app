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

/**
 * Created by soyuz on 4/17/2017.
 */
/* this method will submit the leaves of the inspection to the server **/
public class syncUploadLeavesTask {


    static String tag = "syncUploadLeavesTask";
    static String REQUEST_tag = tag;
    public static boolean completed = false;

    public boolean sync(Context context, final List<tbl_leaf_model> leaves) {
        try {
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    globals.server_url + "/mobileapi/uploadInspectionLeaves.aspx",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("0")) {
                                Log.e(tag, "response:............." + response);
                                Log.e(tag, "leaves not submitted");
                            } else {
                                //set the leaves sync status to true
                                tbl_leaf_modelDao leafDao = thi.daoSession.getTbl_leaf_modelDao();
                                for (tbl_leaf_model leaf : leaves) {
                                    leaf.uploaded = 1;
                                    leafDao.update(leaf);
                                }
                            }
                            completed = true;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(tag, ".......................Error: line 139" + error);
                    completed = true;
                }
            }) {
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    JSONArray jsonLeaves = new JSONArray();
                    for (tbl_leaf_model leaf : leaves) {
                        try {
                            JSONObject obj = new JSONObject();
                            obj.put("grower_id", leaf.grower_id);
                            obj.put("land_id", leaf.land_id);
                            obj.put("batch_id", leaf.batch_id);
                            obj.put("plant_id", leaf.plant_id);
                            obj.put("leaf_number", leaf.leaf_number);
                            obj.put("percentage_damage", leaf.percentage_damage);
                            obj.put("worksheet_id_fk", leaf.worksheet_id);
                            obj.put("subdivision", leaf.subdivision);
                            jsonLeaves.put(obj);
                        } catch (Exception ex) {
                            Log.e(tag, "line 130" + ex.getMessage());
                        }
                    }
                    params.put("leaves", jsonLeaves.toString());
                    return params;
                }
                ;
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2, 1.0f));
            AppSingleton.getInstance(context).addToRequestQueue(strReq, REQUEST_tag);
        } catch (Exception ex) {
            Log.e(tag, "...................... line 301 " + ex);
            return false;
        }
        return true;
    }
}

