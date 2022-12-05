package syncPackages;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import daoModels.tbl_land_modelDao;
import globals.globals;
import models.tbl_land_model;
import zw.co.qbit.thi_app.AppSingleton;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.thi;

/**
 * Created by soyuz on 4/17/2017.
 */
//this method will be invoked manually or by a service to upload the local timesheet to the server
    //get updates of timesheet comments and stff from the server
public class syncGetLands {

    static String tag            = "syncGetLands";
    public static boolean isBusy = false;
    static DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    static String REQUEST_TAG=tag;
    public static boolean sync()
    {
        isBusy=true;
        try
        {
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    globals.server_url+"/mobileapi/getLands.aspx",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            
                            Log.e("kz","response.............."+response);
                             {
                                try {
                                    tbl_land_modelDao tbl_land_model_ = thi.daoSession.getTbl_land_modelDao();
                                    List<tbl_land_model> myGrowers = new ArrayList<>();
                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        tbl_land_model l = new tbl_land_model();
                                        l.land_id=jsonobject.getString("land_id")+"";
                                        l.land_name=jsonobject.getString("land_name")+"";
                                        l.grower_id=jsonobject.getString("land_id")+"";
                                        l.land_area=jsonobject.getString("land_area")+"";
                                        l.date_planted=jsonobject.getString("date_planted")+"";
                                        l.irrigated=jsonobject.getString("irrigated")+"";
                                        l.amendments=jsonobject.getString("amendments")+"";
                                        l.land_primed=jsonobject.getString("land_primed")+"";
                                        l.latitude=jsonobject.getString("latitude")+"";
                                        l.longitude=jsonobject.getString("longitude")+"";
                                        l.boundaryCoordinates=jsonobject.getString("boundaryCoordinates")+"";
                                        //l.scannedImage=jsonobject.getString("scannedImage");
                                        // l.cover_amount=jsonobject.getString("cover_amount");
                                        // l.cover_level=jsonobject.getString("cover_level");
                                        // l.no_claim_bonus_percentage=jsonobject.getString("no_claim_bonus_percentage");
                                        // l.hail_plus=jsonobject.getString("hail_plus");
                                        l.isSynced=false;
                                        tbl_land_model_.insertOrReplace(l);
                                    }

                                } catch (Exception ex) {
                                    Log.e("kz","Exception..............."+ex+"");
                                }
                            }

                            }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("kz","error.............."+error);
                   // Toast.makeText(MainActivity.act,"soyuz "+error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
            AppSingleton.getInstance(thi.CTX).addToRequestQueue(strReq, REQUEST_TAG);

        }catch (Exception ex)
        {
            Log.e("kz","exception................."+ex);
            isBusy=false;
            return false;
        }
        isBusy=false;
        HomeActivity.loadAllTasks();
        return true;
    }



}
