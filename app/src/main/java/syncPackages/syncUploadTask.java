package syncPackages;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Looper;
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

/**
 * Created by soyuz on 4/17/2017.
 */
/* this method will submitt the entire inspection to the server **/
public class syncUploadTask {


    static String tag            = "syncUploadTask";
    public static boolean isBusy = false;
    static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    static String REQUEST_tag=tag;

    boolean sigUploaded=false;
    boolean leavesUploaded=false;
    boolean gridheaderUploaded=false;

    public static boolean sync(Context context , final tbl_task_model t)
    {
        isBusy=true;
        try
        {

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    globals.server_url+"/mobileapi/uploadInspection.aspx",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            HomeActivity.pd.hide();

                            if (response.equals("0")) {
                                Log.e(tag,"response:............."+response);
                               HomeActivity.ed.showCustomDialog(HomeActivity.activity,"Inspection Not Submitted...");
                            } else {
                                String grower_id = t.grower_id;
                                String land_id   = t.land_id;
                                HomeActivity.id.showCustomDialog(HomeActivity.activity,"Inspection Submitted...");
                                //after inspection submitted now we remove all the submitted items to clear space and to make room for the next submittion

                                //delete the worksheet
                                final DeleteQuery<tbl_worksheet_model> tableDeleteQuery = thi.daoSession.queryBuilder(tbl_worksheet_model.class)
                                        .where(tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id)
                                                ,tbl_worksheet_modelDao.Properties.Land_id.eq(land_id))
                                        .buildDelete();
                                tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                                thi.daoSession.clear();

                                //delete the gridsheet leaves
                                final DeleteQuery<tbl_leaf_model> tableDeleteQuery1 = thi.daoSession.queryBuilder(tbl_leaf_model.class)
                                        .where(tbl_leaf_modelDao.Properties.Grower_id.eq(grower_id)
                                                ,tbl_leaf_modelDao.Properties.Land_id.eq(land_id))
                                        .buildDelete();
                                tableDeleteQuery1.executeDeleteWithoutDetachingEntities();
                                thi.daoSession.clear();


                                //delete the gridsheet header
                                final DeleteQuery<tbl_grid_header_model> tableDeleteQuery3 = thi.daoSession.queryBuilder(tbl_grid_header_model.class)
                                        .where(tbl_grid_header_modelDao.Properties.Grower_id.eq(grower_id)
                                                ,tbl_grid_header_modelDao.Properties.Land_id.eq(land_id))
                                        .buildDelete();
                                tableDeleteQuery3.executeDeleteWithoutDetachingEntities();
                                thi.daoSession.clear();


                                //delete the signatures
                                final DeleteQuery<tbl_signature_model> tableDeleteQuery4 = thi.daoSession.queryBuilder(tbl_signature_model.class)
                                        .where(tbl_signature_modelDao.Properties.Grower_id.eq(grower_id)
                                                ,tbl_signature_modelDao.Properties.Land_id.eq(land_id))
                                        .buildDelete();
                                tableDeleteQuery4.executeDeleteWithoutDetachingEntities();
                                thi.daoSession.clear();


                                //delete the tasks
                                final DeleteQuery<tbl_task_model> tableDeleteQuery5 = thi.daoSession.queryBuilder(tbl_task_model.class)
                                        .where(tbl_task_modelDao.Properties.Grower_id.eq(grower_id)
                                                ,tbl_task_modelDao.Properties.Land_id.eq(land_id))
                                        .buildDelete();
                                tableDeleteQuery5.executeDeleteWithoutDetachingEntities();
                                thi.daoSession.clear();

                                loadAllTasks();

                            }


                        }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(tag, ".......................Error: line 139" + error);
                    //Toast.makeText(thi.CTX,error+"",Toast.LENGTH_LONG).show();
                    HomeActivity.ed.showCustomDialog(HomeActivity.activity,"Error occurred...");
                    HomeActivity.pd.hide();
                }
            }){
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                    Map<String, String> params   = new HashMap<String, String>();


                    if(t.claim_stage!=100)
                    {
                        HomeActivity.ed.showCustomDialog(HomeActivity.activity,"This form is not completed");
                        return null;
                    }


                    tbl_land_model land   = thi.daoSession.getTbl_land_modelDao()
                            .queryBuilder()
                            .where(
                                    tbl_land_modelDao.Properties.Land_id.eq(t.land_id),
                                    tbl_land_modelDao.Properties.Grower_id.eq(t.grower_id)
                            ).unique();


                    tbl_user_model user = thi.daoSession.getTbl_user_modelDao().loadAll().get(0);


                    tbl_signature_model sig = thi.daoSession.getTbl_signature_modelDao()
                            .queryBuilder()
                            .where(
                                    tbl_signature_modelDao.Properties.Land_id.eq(t.land_id),
                                    tbl_signature_modelDao.Properties.Grower_id.eq(t.grower_id)
                            ).unique();



                    List<tbl_worksheet_model> myworksheets    = thi.daoSession.getTbl_worksheet_modelDao()
                            .queryBuilder()
                            .where(tbl_worksheet_modelDao.Properties.Land_id.eq(t.land_id))
                            .list();

                    List<tbl_leaf_model> myleaves   = thi.daoSession.getTbl_leaf_modelDao()
                            .queryBuilder()
                            .where(
                                    tbl_leaf_modelDao.Properties.Land_id.eq(t.land_id),
                                    tbl_leaf_modelDao.Properties.Grower_id.eq(t.grower_id)
                            )
                            .list();

                    tbl_grid_header_model gh = thi.daoSession.getTbl_grid_header_modelDao()
                            .queryBuilder()
                            .where(
                                    tbl_grid_header_modelDao.Properties.Land_id.eq(t.land_id),
                                    tbl_grid_header_modelDao.Properties.Grower_id.eq(t.grower_id)
                            )
                            .unique();
                    //then get the signatures afterwards

                    JSONObject gridHeader = new JSONObject();

                    try {

                          gridHeader.put("variety",gh.variety);
                          gridHeader.put("topped",gh.topped.equals("true")?"1":"0");
                          gridHeader.put("total_area_of_damage",gh.total_area_of_field_damaged);
                          gridHeader.put("intended_treatment",gh.intended_treatment_of_land);
                          gridHeader.put("development_stage",gh.stage_of_development);
                          gridHeader.put("signature_grower",gh.signature_grower);
                          gridHeader.put("signature_inspector",gh.signature_inspector);
                          gridHeader.put("signature_2ndInspector",gh.signature_inspector2);
                          gridHeader.put( "subdivision_map" , gh.subdivision_map);
                          gridHeader.put("date",gh.dateOfInspection);
                          gridHeader.put("inspector_name",user.username);
                          gridHeader.put("grid_client_id",gh.id);
                          gridHeader.put("grower_id",gh.grower_id);
                          gridHeader.put("land_id",gh.land_id);
                          gridHeader.put("second_inspector_name",sig.second_inspector_name);
                    }catch(Exception ex)
                    {
                        Log.e(tag,"line 106"+ ex);
                    }



                    JSONArray jsonWorksheets = new JSONArray();
                    for(tbl_worksheet_model m : myworksheets) {
                        try {
                            JSONObject obj = new JSONObject();
                            obj.put("worksheet_client_id", m._id);
                            obj.put("undamaged", m.getPercentageUndamaged());
                            obj.put("missing_stand", m.getPercentageMissing());
                            obj.put("broken_base", m.getPercentageBrokenAtBase());
                            obj.put("broke_half_way", m.getPercentageBrokenHalfWay());
                            obj.put("remarks", m.remarks);
                            obj.put("grower_id", m.grower_id);
                            obj.put("hectares", m.hectares);
                            obj.put("subdivision", m.sub_division);
                            obj.put("land_id", m.land_id);
                            obj.put("inspector_name", user.username);
                            jsonWorksheets.put(obj);
                        } catch (Exception ex) {
                            Log.e(tag, "................line 240" + ex);
                        }
                    }


                        JSONArray jsonLeaves = new JSONArray();
                        for(tbl_leaf_model l : myleaves) {
                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("grower_id", l.grower_id);
                                obj.put("land_id", l.land_id);
                                obj.put("batch_id", l.batch_id);
                                obj.put("plant_id", l.plant_id);
                                obj.put("leaf_number", l.leaf_number);
                                obj.put("percentage_damage", l.percentage_damage);
                                obj.put("worksheet_id_fk", l.worksheet_id);
                                obj.put("subdivision", l.subdivision);
                                jsonLeaves.put(obj);
                            } catch (Exception ex) {
                                Log.e(tag, "line 130" + ex.getMessage());
                            }
                        }


                        //now get the signatures


                    String worksheets_= jsonWorksheets.toString();
                    String leaves_    = jsonLeaves.toString();
                    String header_    = gridHeader.toString();

                    Bitmap sig_grower      =null;
                    Bitmap sig_1stinspector=null;
                    Bitmap sig_2ndinspector=null;
                    Bitmap draw_map = null;
                    try {
                        sig_grower= MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.grower_signature_uri));
                        sig_1stinspector= MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.inspector_signature_uri));
                        sig_2ndinspector= MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.second_inspector_siginature_uri));
                        draw_map = MediaStore.Images.Media.getBitmap(thi.CTX.getContentResolver(), Uri.parse(sig.draw_map_uri));
                    }catch  (Exception ex)
                    {
                       Log.e(tag,".......................line 281 "+ex);
                    }
                    params.put("worksheets"           , worksheets_);
                    params.put("leaves"               , leaves_);
                    params.put("gridheader"           , header_);
                    params.put("grower_signature"     , globals.imageToString(sig_grower));
                    params.put("inspector_signature"  , globals.imageToString(sig_1stinspector));
                    params.put("inspector_signature2" , globals.imageToString(sig_2ndinspector));
                    params.put("sub-division map" , globals.imageToString(draw_map));
                    params.put("inspector2_name"      , sig.second_inspector_name);
                    return params;
                };
            };


            //DefaultRetryPolicy df= new DefaultRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier));
            strReq.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 2, 1.0f));
            //AppSingleton.getInstance(this.CTX).addToRequestQueue(strReq, REQUEST_tag);
            AppSingleton.getInstance(context).addToRequestQueue(strReq, REQUEST_tag);

            }catch (Exception ex)
            {
                Log.e(tag,"...................... line 301 "+ ex);
                isBusy=false;
                return false;
            }
            isBusy=false;
            return true;
    }

}

