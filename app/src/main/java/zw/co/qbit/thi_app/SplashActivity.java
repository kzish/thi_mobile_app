package zw.co.qbit.thi_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customDialogs.CustomErrorDialog;
import customDialogs.CustomProgressDialogOne;
import daoModels.tbl_user_modelDao;
import globals.globals;
import models.tbl_user_model;

public class SplashActivity extends AppCompatActivity {

    BootstrapEditText txt_username;
    BootstrapEditText txt_password;
    BootstrapButton btn_login;
    String tag = "splashActivity";

    CustomProgressDialogOne pd;
    CustomErrorDialog ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txt_username = (BootstrapEditText) findViewById(R.id.txt_username);
        txt_password = (BootstrapEditText) findViewById(R.id.txt_password);
        btn_login = (BootstrapButton) findViewById(R.id.btn_login);
        pd = new CustomProgressDialogOne();
        ed = new CustomErrorDialog();

        List<tbl_user_model> users = thi.daoSession.getTbl_user_modelDao().loadAll();
        if (users.size() > 0) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    }


    public void _login(View v) {
        if (txt_username.getText().toString().equals("")) {
            txt_username.setError("username");
            return;
        }
        if (txt_password.getText().toString().equals("")) {
            txt_password.setError("password");
            return;
        }

        pd.showCustomDialog(SplashActivity.this, "logging in please wait...");
        btn_login.setEnabled(false);

        final tbl_user_model user = new tbl_user_model();
        user.username = txt_username.getText().toString();
        user.password = txt_password.getText().toString();
        user.loggedin = "true";

        Log.e("kz", user.username);
        Log.e("kz", user.password);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                globals.server_url + "/mobileapi/login.aspx",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        pd.hide();
                        Log.e("kz", "response..........." + response);
                        btn_login.setEnabled(true);
                        if (response.equals("0")) {


                            ed.showCustomDialog(SplashActivity.this, "invalid credentials...");


                        } else if (response.equals("1")) {

                            tbl_user_modelDao userModelDao_ = thi.daoSession.getTbl_user_modelDao();
                            tbl_user_model u = new tbl_user_model();
                            u.id = 1L;
                            u.username = txt_username.getText().toString();
                            u.password = txt_password.getText().toString();
                            u.loggedin = "true";
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                            finish();
                            userModelDao_.insertOrReplace(u);


                        } else {
                            ed.showCustomDialog(SplashActivity.this, "generic error...");
                        }
                        btn_login.setEnabled(true);
                        pd.hide();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Log.e("kz", "error..........." + error);
                ed.showCustomDialog(SplashActivity.this, "network error please try again...");
                btn_login.setEnabled(true);
            }
        }) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user.username);
                params.put("password", user.password);
                return params;
            }

            ;
        };
        strReq.setShouldCache(false);
        AppSingleton.getInstance(thi.CTX).addToRequestQueue(strReq, "login");
    }
}
