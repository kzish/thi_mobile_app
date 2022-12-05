package syncPackages;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.greendao.query.DeleteQuery;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daoModels.tbl_task_modelDao;
import daoModels.tbl_user_modelDao;
import globals.globals;
import models.tbl_grower_model;
import models.tbl_task_model;
import models.tbl_user_model;
import zw.co.qbit.thi_app.AppSingleton;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.thi;

/**
 * Created by soyuz on 4/17/2017.
 */
//this method will be invoked manually or by a service to upload the local timesheet to the server
    //get updates of timesheet comments and stff from the server
public class syncFetchTasks {

    static String TAG            = "syncFetchTasks";
    public static boolean isBusy = false;
    static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    static String REQUEST_TAG=TAG;
    private static String tag = TAG;

    public static boolean sync()
    {
        isBusy=true;
        try
        {
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    globals.server_url+"/mobileapi/syncFetchTasks.aspx",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            HomeActivity.pd.hide();
                            Log.e("kz","response..........."+response);
                            if(response.equals("err")) {
                                HomeActivity.ed.showCustomDialog(HomeActivity.activity,"An error occurred...\nTry again");
                            }

                            else {
                                //clear the current tasks
                                
                                DeleteQuery<tbl_task_model> tableDeleteQuery = thi.daoSession.queryBuilder(tbl_task_model.class)
                                        .buildDelete();
                                tableDeleteQuery.executeDeleteWithoutDetachingEntities();
                                thi.daoSession.clear();
                                try {
                                    tbl_task_modelDao tbl_task_modelDao_ =  thi.daoSession.getTbl_task_modelDao();

                                    List<tbl_grower_model> myGrowers = new ArrayList<>();
                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        tbl_task_model t = new tbl_task_model();
                                        t.grower_id = jsonobject.getString("grower_id");
                                        t.task_id = jsonobject.getString("notification_id");
                                        t.grower_name = jsonobject.getString("registered_grower_name");
                                        t.grower_number = jsonobject.getString("registered_number");
                                        t.land_id = jsonobject.getString("land_id");
                                        t.land_area = jsonobject.getString("land_area");
                                        t.land_name = jsonobject.getString("land_name");
                                        t.directions_to_farm = jsonobject.getString("physical_adress");
                                        t.claim_stage = 50;
                                        tbl_task_modelDao_.insertOrReplace(t);

                                       //[{"land_id":7,"notification_id":589,"registered_grower_name":"farmer1","registered_number":"123","land_id":283,"land_name":"land 1","land_area":"15","growers_name":"farmer1","physical_adress":"2527 glaudina park harare zimbabwe","assigned_inspector_id":31,"date_of_strike":"2017-08-19T00:00:00","username":"insp1"}]



                                    }

                                    HomeActivity.id.showCustomDialog(HomeActivity.activity,"All tasks are up to date");

                                    HomeActivity.loadAllTasks();
                                    HomeActivity.setStats();
                                } catch (Exception ex) {
                                            Log.e("kz","...............line 97 "+ex+"");
                                }
                            }

                            }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    HomeActivity.pd.hide();
                    HomeActivity.ed.showCustomDialog(HomeActivity.activity,"Network error...\ntry again...");
                    Log.e("kz", "..................line 107 :" + error);
                }
            }){
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params   = new HashMap<String, String>();
                    tbl_user_modelDao userModelDao_   = thi.daoSession.getTbl_user_modelDao();
                    tbl_user_model user = userModelDao_.loadAll().get(0);
                    params.put("username", user.username);
                    return params;
                };
            };
            strReq.setShouldCache(false);
            AppSingleton.getInstance(thi.CTX).addToRequestQueue(strReq, REQUEST_TAG);

        }catch (Exception ex)
        {
            Log.e("kz",".................:123: "+ex);
            isBusy=false;
            return false;
        }
        isBusy=false;
        HomeActivity.loadAllTasks();
        return true;
    }



}
