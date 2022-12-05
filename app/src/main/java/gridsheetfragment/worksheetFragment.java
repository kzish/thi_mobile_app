package gridsheetfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import daoModels.tbl_task_modelDao;
import daoModels.tbl_worksheet_modelDao;
import models.tbl_task_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.thi;

public class worksheetFragment extends Fragment {

    static String tag = "worksheetFragment";
    BootstrapEditText txt_grower_name, txt_land_name, txt_sub_div;
    BootstrapEditText txt_undamaged_1, txt_undamaged_2, txt_undamaged_3, txt_undamaged_4;
    BootstrapEditText txt_missing_1, txt_missing_2, txt_missing_3, txt_missing_4;
    BootstrapEditText txt_broken_base_1, txt_broken_base_2, txt_broken_base_3, txt_broken_base_4;
    BootstrapEditText txt_broken_half_way_1, txt_broken_half_way_2, txt_broken_half_way_3, txt_broken_half_way_4;
    BootstrapEditText txt_remarks, txt_hectares;
    TextView lbl_undamaged_total, lbl_missing_total, lbl_broken_at_base_total, lbl_broken_half_way_total;
    Toolbar toolbar;
    public static View view;
    static Activity activity;
    static String subdivision;


    public worksheetFragment() {

    }


    public static worksheetFragment newInstance(String grower_id_) {
        worksheetFragment fragment = new worksheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_worksheetfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            new saveWorksheetClassAsync().execute();
            return true;
        }


        if (id == R.id.menu_clear) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to clear this grid sheet?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            clearWorkSheet();

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();


            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = getActivity();
        view = inflater.inflate(R.layout.fragment_worksheet, container, false);

        subdivision = getActivity().getIntent().getStringExtra("subdivision");

        txt_grower_name = (BootstrapEditText) view.findViewById(R.id.txt_grower_name);
        txt_land_name = (BootstrapEditText) view.findViewById(R.id.txt_land_name);
        txt_sub_div = (BootstrapEditText) view.findViewById(R.id.txt_sub_div);
        txt_hectares = (BootstrapEditText) view.findViewById(R.id.txt_hectares);
        txt_hectares.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "8")});
        txt_undamaged_1 = (BootstrapEditText) view.findViewById(R.id.txt_undamaged_1);
        txt_undamaged_2 = (BootstrapEditText) view.findViewById(R.id.txt_undamaged_2);
        txt_undamaged_3 = (BootstrapEditText) view.findViewById(R.id.txt_undamaged_3);
        txt_undamaged_4 = (BootstrapEditText) view.findViewById(R.id.txt_undamaged_4);


        txt_missing_1 = (BootstrapEditText) view.findViewById(R.id.txt_missing_1);
        txt_missing_2 = (BootstrapEditText) view.findViewById(R.id.txt_missing_2);
        txt_missing_3 = (BootstrapEditText) view.findViewById(R.id.txt_missing_3);
        txt_missing_4 = (BootstrapEditText) view.findViewById(R.id.txt_missing_4);


        txt_broken_base_1 = (BootstrapEditText) view.findViewById(R.id.txt_broken_base_1);
        txt_broken_base_2 = (BootstrapEditText) view.findViewById(R.id.txt_broken_base_2);
        txt_broken_base_3 = (BootstrapEditText) view.findViewById(R.id.txt_broken_base_3);
        txt_broken_base_4 = (BootstrapEditText) view.findViewById(R.id.txt_broken_base_4);


        txt_broken_half_way_1 = (BootstrapEditText) view.findViewById(R.id.txt_broken_half_way_1);
        txt_broken_half_way_2 = (BootstrapEditText) view.findViewById(R.id.txt_broken_half_way_2);
        txt_broken_half_way_3 = (BootstrapEditText) view.findViewById(R.id.txt_broken_half_way_3);
        txt_broken_half_way_4 = (BootstrapEditText) view.findViewById(R.id.txt_broken_half_way_4);

        txt_remarks = (BootstrapEditText) view.findViewById(R.id.txt_remarks);

        lbl_undamaged_total = (TextView) view.findViewById(R.id.lbl_undamaged_total);
        lbl_missing_total = (TextView) view.findViewById(R.id.lbl_missing_total);
        lbl_broken_at_base_total = (TextView) view.findViewById(R.id.lbl_broken_at_base_total);
        lbl_broken_half_way_total = (TextView) view.findViewById(R.id.lbl_broken_half_way_total);


        //set filters
        txt_undamaged_1.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_undamaged_2.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_undamaged_3.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_undamaged_4.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});


        txt_missing_1.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_missing_2.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_missing_3.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_missing_4.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});


        txt_broken_base_1.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_broken_base_2.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_broken_base_3.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_broken_base_4.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});


        txt_broken_half_way_1.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_broken_half_way_2.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_broken_half_way_3.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});
        txt_broken_half_way_4.setFilters(new InputFilter[]{new globals.globals.InputFilterMinMaxDecimal("1", "100")});

        new populateDetailsAsync().execute();
        return view;
    }


    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    class saveWorksheetClassAsync extends AsyncTask<String, String, String> {
        tbl_worksheet_model w;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SetSnackBar("saving...");
        }

        @Override
        protected String doInBackground(String... params) {
            w = saveWorkSheet();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (w != null) {
                lbl_undamaged_total.setText(w.getPercentageUndamaged() + " %");
                lbl_missing_total.setText(w.getPercentageMissing() + " %");
                lbl_broken_at_base_total.setText(w.getPercentageBrokenAtBase() + " %");
                lbl_broken_half_way_total.setText(w.getPercentageBrokenHalfWay() + " %");
                SetSnackBar("Saved...");
            }
            else {
                SetSnackBar("Error Saving...");
            }

        }
    }

    public tbl_worksheet_model saveWorkSheet() {
        tbl_worksheet_model w = null;
        try {

            String land_id = getActivity().getIntent().getStringExtra("land_id");
            String grower_id = getActivity().getIntent().getStringExtra("grower_id");



            w = thi.daoSession.getTbl_worksheet_modelDao().queryBuilder()
                    .where(
                            tbl_worksheet_modelDao.Properties.Sub_division.eq(subdivision),
                            tbl_worksheet_modelDao.Properties.Land_id.eq(land_id),
                            tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id)
                    )
                    .unique();

            if (w == null) {
                w = new tbl_worksheet_model();
                w.land_id = Integer.parseInt(land_id);
                w.grower_id = Integer.parseInt(grower_id);

            }
            w.sub_division = Integer.parseInt(txt_sub_div.getText().toString().equals("") ? "1" : txt_sub_div.getText().toString());
            w.hectares = Double.parseDouble(txt_hectares.getText().toString());
            w.undamaged_1 = Double.parseDouble(txt_undamaged_1.getText().toString().equals("") ? "0" : txt_undamaged_1.getText().toString());
            w.undamaged_2 = Double.parseDouble(txt_undamaged_2.getText().toString().equals("") ? "0" : txt_undamaged_2.getText().toString());
            w.undamaged_3 = Double.parseDouble(txt_undamaged_3.getText().toString().equals("") ? "0" : txt_undamaged_3.getText().toString());
            w.undamaged_4 = Double.parseDouble(txt_undamaged_4.getText().toString().equals("") ? "0" : txt_undamaged_4.getText().toString());
            w.missing_stand_1 = Double.parseDouble(txt_missing_1.getText().toString().equals("") ? "0" : txt_missing_1.getText().toString());
            w.missing_stand_2 = Double.parseDouble(txt_missing_2.getText().toString().equals("") ? "0" : txt_missing_2.getText().toString());
            w.missing_stand_3 = Double.parseDouble(txt_missing_3.getText().toString().equals("") ? "0" : txt_missing_3.getText().toString());
            w.missing_stand_4 = Double.parseDouble(txt_missing_4.getText().toString().equals("") ? "0" : txt_missing_4.getText().toString());
            w.broken_at_base_1 = Double.parseDouble(txt_broken_base_1.getText().toString().equals("") ? "0" : txt_broken_base_1.getText().toString());
            w.broken_at_base_2 = Double.parseDouble(txt_broken_base_2.getText().toString().equals("") ? "0" : txt_broken_base_2.getText().toString());
            w.broken_at_base_3 = Double.parseDouble(txt_broken_base_3.getText().toString().equals("") ? "0" : txt_broken_base_3.getText().toString());
            w.broken_at_base_4 = Double.parseDouble(txt_broken_base_4.getText().toString().equals("") ? "0" : txt_broken_base_4.getText().toString());
            w.broken_half_way_1 = Double.parseDouble(txt_broken_half_way_1.getText().toString().equals("") ? "0" : txt_broken_half_way_1.getText().toString());
            w.broken_half_way_2 = Double.parseDouble(txt_broken_half_way_2.getText().toString().equals("") ? "0" : txt_broken_half_way_2.getText().toString());
            w.broken_half_way_3 = Double.parseDouble(txt_broken_half_way_3.getText().toString().equals("") ? "0" : txt_broken_half_way_3.getText().toString());
            w.broken_half_way_4 = Double.parseDouble(txt_broken_half_way_4.getText().toString().equals("") ? "0" : txt_broken_half_way_4.getText().toString());
            w.remarks = txt_remarks.getText().toString();
            //worksheetModelDao_.delete(w);//prevent duplicates
            thi.daoSession.getTbl_worksheet_modelDao().insertOrReplace(w);
            subdivision=w.sub_division+"";
            tab1Fragment.subdivision=w.sub_division+"";
            tab2Fragment.subdivision=w.sub_division+"";
            tab3Fragment.subdivision=w.sub_division+"";
            tab4Fragment.subdivision=w.sub_division+"";

        } catch (Exception ex) {
            SetSnackBar("Error");
            Log.e("kz", "...........line 279 " + ex);
            return w;
        }
        return w;

    }

    //populat ethe details async
    class populateDetailsAsync extends AsyncTask<String, String, String> {
        tbl_task_model t;
        Long num_worksheets;
        tbl_worksheet_model w;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            populateDetails(t, num_worksheets, w);
        }

        @Override
        protected String doInBackground(String... strings) {

            String land_id = getActivity().getIntent().getStringExtra("land_id");
            String grower_id = getActivity().getIntent().getStringExtra("grower_id");

            t = thi.daoSession.getTbl_task_modelDao()
                    .queryBuilder()
                    .where(
                            tbl_task_modelDao.Properties.Grower_id.eq(grower_id),
                            tbl_task_modelDao.Properties.Land_id.eq(land_id)
                    ).unique();


            num_worksheets = thi.daoSession.getTbl_worksheet_modelDao()
                    .queryBuilder()
                    .where(
                            tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id),
                            tbl_worksheet_modelDao.Properties.Land_id.eq(land_id)
                    ).count() + 1;

            //else populate the fields
            String subdivision = getActivity().getIntent().getStringExtra("subdivision");
            if (subdivision == null)
                return null;

            w = thi.daoSession.getTbl_worksheet_modelDao().queryBuilder()
                    .where(
                            tbl_worksheet_modelDao.Properties.Sub_division.eq(subdivision),
                            tbl_worksheet_modelDao.Properties.Land_id.eq(land_id),
                            tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id)
                    )
                    .unique();

            return null;
        }
    }


    //fill details
    public void populateDetails(tbl_task_model t, Long num_worksheets, tbl_worksheet_model w) {

        try {
            String new_ = getActivity().getIntent().getStringExtra("new");

            txt_grower_name.setText(t.grower_name);
            txt_land_name.setText(t.land_name);

            if (new_.equals("1")) {
                //clear all fields
                txt_sub_div.setText(num_worksheets + "");
                Log.e("kz", num_worksheets + "");
                txt_undamaged_1.setText("");
                txt_undamaged_2.setText("");
                txt_undamaged_3.setText("");
                txt_undamaged_4.setText("");


                txt_missing_1.setText("");
                txt_missing_2.setText("");
                txt_missing_3.setText("");
                txt_missing_4.setText("");


                txt_broken_base_1.setText("");
                txt_broken_base_2.setText("");
                txt_broken_base_3.setText("");
                txt_broken_base_4.setText("");


                txt_broken_half_way_1.setText("");
                txt_broken_half_way_2.setText("");
                txt_broken_half_way_3.setText("");
                txt_broken_half_way_4.setText("");

                txt_remarks.setText("");

                lbl_undamaged_total.setText("0%");
                lbl_missing_total.setText("0%");
                lbl_broken_at_base_total.setText("0%");
                lbl_broken_half_way_total.setText("0%");


                String land_id = getActivity().getIntent().getStringExtra("land_id");
                String grower_id = getActivity().getIntent().getStringExtra("grower_id");

                //since this is a new worksheet we must save it
                w = thi.daoSession.getTbl_worksheet_modelDao().queryBuilder()
                        .where(
                                tbl_worksheet_modelDao.Properties.Sub_division.eq(subdivision),
                                tbl_worksheet_modelDao.Properties.Land_id.eq(land_id),
                                tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id)
                        )
                        .unique();

                if (w == null) {
                    w = new tbl_worksheet_model();
                    w.land_id = Integer.parseInt(land_id);
                    w.grower_id = Integer.parseInt(grower_id);
                }
                w.hectares=8;
                w.sub_division = Integer.parseInt(txt_sub_div.getText().toString().equals("") ? "1" : txt_sub_div.getText().toString());
                subdivision=w.sub_division+"";
                thi.daoSession.getTbl_worksheet_modelDao().insertOrReplace(w);
                tab1Fragment.subdivision=w.sub_division+"";
                tab2Fragment.subdivision=w.sub_division+"";
                tab3Fragment.subdivision=w.sub_division+"";
                tab4Fragment.subdivision=w.sub_division+"";

            } else {

                if (w == null) return;

                txt_sub_div.setText("" + w.sub_division);
                txt_hectares.setText(w.hectares + "");
                txt_undamaged_1.setText("" + w.undamaged_1);
                txt_undamaged_2.setText("" + w.undamaged_2);
                txt_undamaged_3.setText("" + w.undamaged_3);
                txt_undamaged_4.setText("" + w.undamaged_4);


                txt_missing_1.setText("" + w.missing_stand_1);
                txt_missing_2.setText("" + w.missing_stand_2);
                txt_missing_3.setText("" + w.missing_stand_3);
                txt_missing_4.setText("" + w.missing_stand_4);


                txt_broken_base_1.setText("" + w.broken_at_base_1);
                txt_broken_base_2.setText("" + w.broken_at_base_2);
                txt_broken_base_3.setText("" + w.broken_at_base_3);
                txt_broken_base_4.setText("" + w.broken_at_base_4);


                txt_broken_half_way_1.setText("" + w.broken_half_way_1);
                txt_broken_half_way_2.setText("" + w.broken_half_way_2);
                txt_broken_half_way_3.setText("" + w.broken_half_way_3);
                txt_broken_half_way_4.setText("" + w.broken_half_way_4);


                lbl_undamaged_total.setText(w.getPercentageUndamaged() + " %");
                lbl_missing_total.setText(w.getPercentageMissing() + " %");
                lbl_broken_at_base_total.setText(w.getPercentageBrokenAtBase() + " %");
                lbl_broken_half_way_total.setText(w.getPercentageBrokenHalfWay() + " %");

                txt_remarks.setText(w.remarks);
            }

        } catch (Exception x) {
            Log.e("kz", ".................. " + x);
            SetSnackBar("Error");
        }
    }


    //clear detials
    public void clearWorkSheet() {
        txt_hectares.setText("8");
        txt_undamaged_1.setText("");
        txt_undamaged_2.setText("");
        txt_undamaged_3.setText("");
        txt_undamaged_4.setText("");


        txt_missing_1.setText("");
        txt_missing_2.setText("");
        txt_missing_3.setText("");
        txt_missing_4.setText("");


        txt_broken_base_1.setText("");
        txt_broken_base_2.setText("");
        txt_broken_base_3.setText("");
        txt_broken_base_4.setText("");


        txt_broken_half_way_1.setText("");
        txt_broken_half_way_2.setText("");
        txt_broken_half_way_3.setText("");
        txt_broken_half_way_4.setText("");


        lbl_undamaged_total.setText("0%");
        lbl_missing_total.setText("0%");
        lbl_broken_at_base_total.setText("0%");
        lbl_broken_half_way_total.setText("0%");

        txt_remarks.setText("");
    }


    public static void SetSnackBar(String message) {
        try {
            RelativeLayout rootlayout = (RelativeLayout) view.findViewById(R.id.main_content);
            Snackbar snackbar = Snackbar.make(rootlayout, message, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } catch (Exception ex) {
            Log.e("kz", "" + ex.getMessage());
        }
    }

}
