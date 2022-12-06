package zw.co.qbit.thi_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import adapters.worksheetAdapter;
import daoModels.tbl_grid_header_modelDao;
import daoModels.tbl_task_modelDao;
import daoModels.tbl_worksheet_modelDao;
import gridsheetfragment.worksheetFragment;
import models.tbl_grid_header_model;
import models.tbl_task_model;
import models.tbl_worksheet_model;
import signatures.AddSignaturesActivity;

public class SubDivisionIndex extends AppCompatActivity {

    private worksheetFragment.OnFragmentInteractionListener mListener;
    View view;
    static List<landsObject> lands__;
    public static SearchableSpinner spinnerGrowerLand;
    static Activity activity;
    public static GridView gridView;
    static worksheetAdapter worksheetAdapter_;

    public static String grower_id;
    public static String land_id;
    public static String directions_to_farm;

    static Spinner spinner_development_stage;

    TextView mFarmAddress;
    static CheckBox chkTopped;
    static BootstrapEditText txt_variety,
            txt_total_area,
            txt_intended_treatment;

    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub_division_index, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            new saveGridHeaderAsync().execute();
            return true;
        }
        if (id == R.id.action_add_signatures) {
            Intent sigs = new Intent(SubDivisionIndex.this, AddSignaturesActivity.class);
            sigs.putExtra("grower_id", grower_id);
            sigs.putExtra("land_id", land_id);
            startActivity(sigs);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = SubDivisionIndex.this;
        setContentView(R.layout.activity_sub_division_index);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grower_id = getIntent().getStringExtra("grower_id");
        land_id = getIntent().getStringExtra("land_id");

        directions_to_farm = getIntent().getStringExtra("farm_address");
        spinnerGrowerLand = (SearchableSpinner) findViewById(R.id.spinnerGrowerLand);
        gridView = (GridView) findViewById(R.id.gridview);
        mFarmAddress = findViewById(R.id.farmAddress);
        mFarmAddress.setText("Address: " + directions_to_farm);
        chkTopped = (CheckBox) findViewById(R.id.txt_topped);
        txt_variety = (BootstrapEditText) findViewById(R.id.txt_variety);
        txt_total_area = (BootstrapEditText) findViewById(R.id.txt_total_area);
        txt_intended_treatment = (BootstrapEditText) findViewById(R.id.txt_intended_treatment);
        spinner_development_stage = (Spinner) findViewById(R.id.txt_stage_of_development);
        fillSpinnerGrowerLand();
        refreshList();

        long i = thi.daoSession.getTbl_grid_header_modelDao()
                .queryBuilder()
                .where(
                        tbl_grid_header_modelDao.Properties.Grower_id.eq(grower_id),
                        tbl_grid_header_modelDao.Properties.Land_id.eq(land_id)
                )
                .count();
        Log.e("kz", "count: " + i);
    }

    static class landsObject {
        public String land_id;
        public int position;
    }

    public static void refreshList() {
        tbl_worksheet_model w = thi.daoSession.getTbl_worksheet_modelDao()
                .queryBuilder().where(
                        tbl_worksheet_modelDao.Properties.Land_id.eq(-1)
                ).unique();
        if (w == null) {
            w = new tbl_worksheet_model();
            w.land_id = -1;
            w.grower_id = Integer.parseInt(grower_id);
            thi.daoSession.getTbl_worksheet_modelDao().insertOrReplace(w);
        }
        worksheetAdapter_ = new worksheetAdapter(activity, land_id, grower_id);
        gridView.setAdapter(worksheetAdapter_);
        worksheetAdapter_.notifyDataSetChanged();
        new getGridHeaderAsync1().execute();
    }

    public void fillSpinnerGrowerLand() {
        try {
            List<String> spinnerArray = new ArrayList<String>();
            List<tbl_task_model> mTasks = thi.daoSession.getTbl_task_modelDao()
                    .queryBuilder()
                    .where(
                            tbl_task_modelDao.Properties.Grower_id.eq(grower_id),
                            tbl_task_modelDao.Properties.Land_id.eq(land_id)
                    ).list();
            lands__ = new ArrayList<>();

            int loop = 0;
            for (tbl_task_model t : mTasks) {
                spinnerArray.add(t.land_name + " (" + t.land_area + ") hactares");
                landsObject gg = new landsObject();
                gg.land_id = t.land_id;
                gg.position = loop;
                lands__.add(gg);
                loop++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(thi.CTX, R.layout.spinner_view_black, spinnerArray);
            adapter.setDropDownViewResource(R.layout.spinner_view_black);
            spinnerGrowerLand.setAdapter(adapter);
        } catch (Exception ex) {
            Log.e("kz", "line 130.............." + ex);
        }
    }

    class saveGridHeaderAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SetSnackBar("saving...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("ok")) {
                SetSnackBar("saved...");
            } else {
                SetSnackBar("error saving information...");
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            if (saveGridHeader()) {
                return "ok";
            } else {
                return "err";
            }
        }
    }

    public boolean saveGridHeader() {
        try {
            boolean exist = true;
            tbl_grid_header_model gh = null;
            try {
                gh = thi.daoSession.getTbl_grid_header_modelDao()
                        .queryBuilder()
                        .where(
                                tbl_grid_header_modelDao.Properties.Grower_id.eq(grower_id),
                                tbl_grid_header_modelDao.Properties.Land_id.eq(land_id)
                        )
                        .unique();
            } catch (Exception ex) {
                Log.i("kz", ex.getMessage());
            }
            if (gh == null) {
                exist = false;
                gh = new tbl_grid_header_model();
            }
            //save the grid header
            gh.grower_id = grower_id;
            gh.land_id = land_id;
            gh.topped = chkTopped.isChecked() ? "true" : "false";
            gh.total_area_of_field_damaged = Double.parseDouble(txt_total_area.getText().toString().equals("") ? "0" : txt_total_area.getText().toString());
            gh.stage_of_development = spinner_development_stage.getSelectedItem().toString();
            gh.variety = txt_variety.getText().toString();
            gh.intended_treatment_of_land = txt_intended_treatment.getText().toString();
            gh.dateOfInspection = DateTime.now().toString("yyyy-MM-dd");
            if(exist) {
                thi.daoSession.getTbl_grid_header_modelDao().update(gh);
            } else {
                thi.daoSession.getTbl_grid_header_modelDao().insert(gh);
            }
            return true;
        } catch (Exception ex) {
            Log.e("kz", "..................line 250" + ex);
            return false;
        }

    }

    static class getGridHeaderAsync1 extends AsyncTask<String, String, String> {
        tbl_grid_header_model mgh;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mgh != null) {
                showGridHeaderInformation(mgh);
                Log.e("kz g:", mgh.grower_id);
                Log.e("kz l:", mgh.land_id);
                Log.e("kz i:", mgh.id + "");
            } else {
                Log.e("kz", "mgh is null");
                Log.e("kz grower:", grower_id);
                Log.e("kz land:", land_id);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                mgh = thi.daoSession.getTbl_grid_header_modelDao()
                        .queryBuilder()
                        .where(
                                tbl_grid_header_modelDao.Properties.Land_id.eq(land_id),
                                tbl_grid_header_modelDao.Properties.Grower_id.eq(grower_id)
                        )
                        .unique();
            } catch (Exception ex) {
                Log.e("kz", ex.getMessage());
            }
            return "";
        }
    }

    public static void showGridHeaderInformation(tbl_grid_header_model gh) {
        try {
            if (gh == null) {
                chkTopped.setChecked(false);
                txt_total_area.setText("");
                txt_variety.setText("");
                txt_intended_treatment.setText("");
                return;
            } else {
                chkTopped.setChecked(gh.topped.equals("true"));
                txt_total_area.setText(gh.total_area_of_field_damaged + "");
                txt_variety.setText(gh.variety);
                txt_intended_treatment.setText(gh.intended_treatment_of_land);
            }

            //set the development stage spinner
            for (int i = 0; i < spinner_development_stage.getCount(); i++) {
                if (spinner_development_stage.getItemAtPosition(i).toString().equals(gh.stage_of_development)) {
                    spinner_development_stage.setSelection(i);
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e("kz", "............. line 304" + ex);
        }

    }

    public void SetSnackBar(String message) {
        try {
            CoordinatorLayout rootlayout = (CoordinatorLayout) findViewById(R.id.main_content);
            Snackbar snackbar = Snackbar.make(rootlayout, message, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } catch (Exception ex) {
            Log.e("kz", "....................line 473 " + ex);
        }
    }

}
