package gridsheetfragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

import daoModels.tbl_leaf_modelDao;
import daoModels.tbl_task_modelDao;
import daoModels.tbl_worksheet_modelDao;
import models.tbl_leaf_model;
import models.tbl_task_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.thi;

public class tab1Fragment extends Fragment {
    public static View view;
    ProgressDialog pd;
    TextView txt_batch;
    String batch = "1";
    public static String subdivision;
    public static String worksheet_id;

    BootstrapEditText
            et133, et233, et333, et433, et533,
            et132, et232, et332, et432, et532,
            et131, et231, et331, et431, et531,
            et130, et230, et330, et430, et530,
            et129, et229, et329, et429, et529,
            et128, et228, et328, et428, et528,
            et127, et227, et327, et427, et527,
            et126, et226, et326, et426, et526,
            et125, et225, et325, et425, et525,
            et124, et224, et324, et424, et524,
            et123, et223, et323, et423, et523,
            et122, et222, et322, et422, et522,
            et121, et221, et321, et421, et521,
            et120, et220, et320, et420, et520,
            et119, et219, et319, et419, et519,
            et118, et218, et318, et418, et518,
            et117, et217, et317, et417, et517,
            et116, et216, et316, et416, et516,
            et115, et215, et315, et415, et515,
            et114, et214, et314, et414, et514,
            et113, et213, et313, et413, et513,
            et112, et212, et312, et412, et512,
            et111, et211, et311, et411, et511,
            et110, et210, et310, et410, et510,
            et19, et29, et39, et49, et59,
            et18, et28, et38, et48, et58,
            et17, et27, et37, et47, et57,
            et16, et26, et36, et46, et56,
            et15, et25, et35, et45, et55,
            et14, et24, et34, et44, et54,
            et13, et23, et33, et43, et53,
            et12, et22, et32, et42, et52,
            et11, et21, et31, et41, et51;
    private String tag = "tab1 frgament";


    public tab1Fragment() {
        // Required empty public constructor
    }

    public static tab1Fragment newInstance(String grower_id_) {
        tab1Fragment fragment = new tab1Fragment();

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gridsheet, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveGridSheetAsync sgc = new saveGridSheetAsync();
            sgc.execute();
            return true;
        }
        if (id == R.id.menu_view_grid) {
            new viewGridSheetAsync().execute();
            return true;
        }

        if (id == R.id.menu_clear) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to clear this grid sheet?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            clearGridSheet(null);

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();


            return true;
        }

        if (id == R.id.menu_delete) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to delete this grid sheet?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            deleteGridSheet();

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        subdivision = getActivity().getIntent().getExtras().getString("subdivision");
        worksheet_id = getActivity().getIntent().getStringExtra("worksheet_id");
        Log.e(tag, "worksheet_id.............." + worksheet_id);
        Log.e(tag, "subdivision.............." + subdivision);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //gvPlants = (GridView)view.findViewById(R.id.gvGrid);


        pd = new ProgressDialog(getActivity());

        et133 = (BootstrapEditText) view.findViewById(R.id.et133);
        et233 = (BootstrapEditText) view.findViewById(R.id.et233);
        et333 = (BootstrapEditText) view.findViewById(R.id.et333);
        et433 = (BootstrapEditText) view.findViewById(R.id.et433);
        et533 = (BootstrapEditText) view.findViewById(R.id.et533);
        et132 = (BootstrapEditText) view.findViewById(R.id.et132);
        et232 = (BootstrapEditText) view.findViewById(R.id.et232);
        et332 = (BootstrapEditText) view.findViewById(R.id.et332);
        et432 = (BootstrapEditText) view.findViewById(R.id.et432);
        et532 = (BootstrapEditText) view.findViewById(R.id.et532);
        et131 = (BootstrapEditText) view.findViewById(R.id.et131);
        et231 = (BootstrapEditText) view.findViewById(R.id.et231);
        et331 = (BootstrapEditText) view.findViewById(R.id.et331);
        et431 = (BootstrapEditText) view.findViewById(R.id.et431);
        et531 = (BootstrapEditText) view.findViewById(R.id.et531);
        et130 = (BootstrapEditText) view.findViewById(R.id.et130);
        et230 = (BootstrapEditText) view.findViewById(R.id.et230);
        et330 = (BootstrapEditText) view.findViewById(R.id.et330);
        et430 = (BootstrapEditText) view.findViewById(R.id.et430);
        et530 = (BootstrapEditText) view.findViewById(R.id.et530);
        et129 = (BootstrapEditText) view.findViewById(R.id.et129);
        et229 = (BootstrapEditText) view.findViewById(R.id.et229);
        et329 = (BootstrapEditText) view.findViewById(R.id.et329);
        et429 = (BootstrapEditText) view.findViewById(R.id.et429);
        et529 = (BootstrapEditText) view.findViewById(R.id.et529);
        et128 = (BootstrapEditText) view.findViewById(R.id.et128);
        et228 = (BootstrapEditText) view.findViewById(R.id.et228);
        et328 = (BootstrapEditText) view.findViewById(R.id.et328);
        et428 = (BootstrapEditText) view.findViewById(R.id.et428);
        et528 = (BootstrapEditText) view.findViewById(R.id.et528);
        et127 = (BootstrapEditText) view.findViewById(R.id.et127);
        et227 = (BootstrapEditText) view.findViewById(R.id.et227);
        et327 = (BootstrapEditText) view.findViewById(R.id.et327);
        et427 = (BootstrapEditText) view.findViewById(R.id.et427);
        et527 = (BootstrapEditText) view.findViewById(R.id.et527);
        et126 = (BootstrapEditText) view.findViewById(R.id.et126);
        et226 = (BootstrapEditText) view.findViewById(R.id.et226);
        et326 = (BootstrapEditText) view.findViewById(R.id.et326);
        et426 = (BootstrapEditText) view.findViewById(R.id.et426);
        et526 = (BootstrapEditText) view.findViewById(R.id.et526);
        et125 = (BootstrapEditText) view.findViewById(R.id.et125);
        et225 = (BootstrapEditText) view.findViewById(R.id.et225);
        et325 = (BootstrapEditText) view.findViewById(R.id.et325);
        et425 = (BootstrapEditText) view.findViewById(R.id.et425);
        et525 = (BootstrapEditText) view.findViewById(R.id.et525);
        et124 = (BootstrapEditText) view.findViewById(R.id.et124);
        et224 = (BootstrapEditText) view.findViewById(R.id.et224);
        et324 = (BootstrapEditText) view.findViewById(R.id.et324);
        et424 = (BootstrapEditText) view.findViewById(R.id.et424);
        et524 = (BootstrapEditText) view.findViewById(R.id.et524);
        et123 = (BootstrapEditText) view.findViewById(R.id.et123);
        et223 = (BootstrapEditText) view.findViewById(R.id.et223);
        et323 = (BootstrapEditText) view.findViewById(R.id.et323);
        et423 = (BootstrapEditText) view.findViewById(R.id.et423);
        et523 = (BootstrapEditText) view.findViewById(R.id.et523);
        et122 = (BootstrapEditText) view.findViewById(R.id.et122);
        et222 = (BootstrapEditText) view.findViewById(R.id.et222);
        et322 = (BootstrapEditText) view.findViewById(R.id.et322);
        et422 = (BootstrapEditText) view.findViewById(R.id.et422);
        et522 = (BootstrapEditText) view.findViewById(R.id.et522);
        et121 = (BootstrapEditText) view.findViewById(R.id.et121);
        et221 = (BootstrapEditText) view.findViewById(R.id.et221);
        et321 = (BootstrapEditText) view.findViewById(R.id.et321);
        et421 = (BootstrapEditText) view.findViewById(R.id.et421);
        et521 = (BootstrapEditText) view.findViewById(R.id.et521);
        et120 = (BootstrapEditText) view.findViewById(R.id.et120);
        et220 = (BootstrapEditText) view.findViewById(R.id.et220);
        et320 = (BootstrapEditText) view.findViewById(R.id.et320);
        et420 = (BootstrapEditText) view.findViewById(R.id.et420);
        et520 = (BootstrapEditText) view.findViewById(R.id.et520);
        et119 = (BootstrapEditText) view.findViewById(R.id.et119);
        et219 = (BootstrapEditText) view.findViewById(R.id.et219);
        et319 = (BootstrapEditText) view.findViewById(R.id.et319);
        et419 = (BootstrapEditText) view.findViewById(R.id.et419);
        et519 = (BootstrapEditText) view.findViewById(R.id.et519);
        et118 = (BootstrapEditText) view.findViewById(R.id.et118);
        et218 = (BootstrapEditText) view.findViewById(R.id.et218);
        et318 = (BootstrapEditText) view.findViewById(R.id.et318);
        et418 = (BootstrapEditText) view.findViewById(R.id.et418);
        et518 = (BootstrapEditText) view.findViewById(R.id.et518);
        et117 = (BootstrapEditText) view.findViewById(R.id.et117);
        et217 = (BootstrapEditText) view.findViewById(R.id.et217);
        et317 = (BootstrapEditText) view.findViewById(R.id.et317);
        et417 = (BootstrapEditText) view.findViewById(R.id.et417);
        et517 = (BootstrapEditText) view.findViewById(R.id.et517);
        et116 = (BootstrapEditText) view.findViewById(R.id.et116);
        et216 = (BootstrapEditText) view.findViewById(R.id.et216);
        et316 = (BootstrapEditText) view.findViewById(R.id.et316);
        et416 = (BootstrapEditText) view.findViewById(R.id.et416);
        et516 = (BootstrapEditText) view.findViewById(R.id.et516);
        et115 = (BootstrapEditText) view.findViewById(R.id.et115);
        et215 = (BootstrapEditText) view.findViewById(R.id.et215);
        et315 = (BootstrapEditText) view.findViewById(R.id.et315);
        et415 = (BootstrapEditText) view.findViewById(R.id.et415);
        et515 = (BootstrapEditText) view.findViewById(R.id.et515);
        et114 = (BootstrapEditText) view.findViewById(R.id.et114);
        et214 = (BootstrapEditText) view.findViewById(R.id.et214);
        et314 = (BootstrapEditText) view.findViewById(R.id.et314);
        et414 = (BootstrapEditText) view.findViewById(R.id.et414);
        et514 = (BootstrapEditText) view.findViewById(R.id.et514);
        et113 = (BootstrapEditText) view.findViewById(R.id.et113);
        et213 = (BootstrapEditText) view.findViewById(R.id.et213);
        et313 = (BootstrapEditText) view.findViewById(R.id.et313);
        et413 = (BootstrapEditText) view.findViewById(R.id.et413);
        et513 = (BootstrapEditText) view.findViewById(R.id.et513);
        et112 = (BootstrapEditText) view.findViewById(R.id.et112);
        et212 = (BootstrapEditText) view.findViewById(R.id.et212);
        et312 = (BootstrapEditText) view.findViewById(R.id.et312);
        et412 = (BootstrapEditText) view.findViewById(R.id.et412);
        et512 = (BootstrapEditText) view.findViewById(R.id.et512);
        et111 = (BootstrapEditText) view.findViewById(R.id.et111);
        et211 = (BootstrapEditText) view.findViewById(R.id.et211);
        et311 = (BootstrapEditText) view.findViewById(R.id.et311);
        et411 = (BootstrapEditText) view.findViewById(R.id.et411);
        et511 = (BootstrapEditText) view.findViewById(R.id.et511);
        et110 = (BootstrapEditText) view.findViewById(R.id.et110);
        et210 = (BootstrapEditText) view.findViewById(R.id.et210);
        et310 = (BootstrapEditText) view.findViewById(R.id.et310);
        et410 = (BootstrapEditText) view.findViewById(R.id.et410);
        et510 = (BootstrapEditText) view.findViewById(R.id.et510);

        et19 = (BootstrapEditText) view.findViewById(R.id.et19);
        et29 = (BootstrapEditText) view.findViewById(R.id.et29);
        et39 = (BootstrapEditText) view.findViewById(R.id.et39);
        et49 = (BootstrapEditText) view.findViewById(R.id.et49);
        et59 = (BootstrapEditText) view.findViewById(R.id.et59);
        et18 = (BootstrapEditText) view.findViewById(R.id.et18);
        et28 = (BootstrapEditText) view.findViewById(R.id.et28);
        et38 = (BootstrapEditText) view.findViewById(R.id.et38);
        et48 = (BootstrapEditText) view.findViewById(R.id.et48);
        et58 = (BootstrapEditText) view.findViewById(R.id.et58);
        et17 = (BootstrapEditText) view.findViewById(R.id.et17);
        et27 = (BootstrapEditText) view.findViewById(R.id.et27);
        et37 = (BootstrapEditText) view.findViewById(R.id.et37);
        et47 = (BootstrapEditText) view.findViewById(R.id.et47);
        et57 = (BootstrapEditText) view.findViewById(R.id.et57);
        et16 = (BootstrapEditText) view.findViewById(R.id.et16);
        et26 = (BootstrapEditText) view.findViewById(R.id.et26);
        et36 = (BootstrapEditText) view.findViewById(R.id.et36);
        et46 = (BootstrapEditText) view.findViewById(R.id.et46);
        et56 = (BootstrapEditText) view.findViewById(R.id.et56);
        et15 = (BootstrapEditText) view.findViewById(R.id.et15);
        et25 = (BootstrapEditText) view.findViewById(R.id.et25);
        et35 = (BootstrapEditText) view.findViewById(R.id.et35);
        et45 = (BootstrapEditText) view.findViewById(R.id.et45);
        et55 = (BootstrapEditText) view.findViewById(R.id.et55);
        et14 = (BootstrapEditText) view.findViewById(R.id.et14);
        et24 = (BootstrapEditText) view.findViewById(R.id.et24);
        et34 = (BootstrapEditText) view.findViewById(R.id.et34);
        et44 = (BootstrapEditText) view.findViewById(R.id.et44);
        et54 = (BootstrapEditText) view.findViewById(R.id.et54);
        et13 = (BootstrapEditText) view.findViewById(R.id.et13);
        et23 = (BootstrapEditText) view.findViewById(R.id.et23);
        et33 = (BootstrapEditText) view.findViewById(R.id.et33);
        et43 = (BootstrapEditText) view.findViewById(R.id.et43);
        et53 = (BootstrapEditText) view.findViewById(R.id.et53);
        et12 = (BootstrapEditText) view.findViewById(R.id.et12);
        et22 = (BootstrapEditText) view.findViewById(R.id.et22);
        et32 = (BootstrapEditText) view.findViewById(R.id.et32);
        et42 = (BootstrapEditText) view.findViewById(R.id.et42);
        et52 = (BootstrapEditText) view.findViewById(R.id.et52);
        et11 = (BootstrapEditText) view.findViewById(R.id.et11);
        et21 = (BootstrapEditText) view.findViewById(R.id.et21);
        et31 = (BootstrapEditText) view.findViewById(R.id.et31);
        et41 = (BootstrapEditText) view.findViewById(R.id.et41);
        et51 = (BootstrapEditText) view.findViewById(R.id.et51);


        setFilterClass sf = new setFilterClass();
        sf.execute();


        new viewGridSheetAsync().execute();

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

    class setFilterClass extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            setFilters();
            return null;
        }
    }

    void setFilters() {
        et133.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et233.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et333.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et433.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et533.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et132.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et232.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et332.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et432.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et532.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et131.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et231.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et331.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et431.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et531.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et130.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et230.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et330.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et430.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et530.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et129.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et229.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et329.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et429.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et529.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et128.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et228.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et328.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et428.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et528.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et127.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et227.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et327.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et427.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et527.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et126.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et226.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et326.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et426.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et526.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et125.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et225.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et325.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et425.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et525.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et124.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et224.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et324.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et424.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et524.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et123.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et223.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et323.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et423.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et523.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et122.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et222.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et322.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et422.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et522.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et121.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et221.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et321.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et421.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et521.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et120.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et220.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et320.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et420.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et520.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et119.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et219.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et319.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et419.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et519.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et118.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et218.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et318.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et418.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et518.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et117.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et217.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et317.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et417.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et517.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et116.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et216.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et316.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et416.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et516.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et115.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et215.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et315.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et415.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et515.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et114.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et214.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et314.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et414.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et514.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et113.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et213.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et313.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et413.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et513.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et112.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et212.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et312.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et412.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et512.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et111.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et211.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et311.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et411.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et511.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et110.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et210.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et310.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et410.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et510.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et19.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et29.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et39.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et49.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et59.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et18.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et28.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et38.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et48.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et58.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et17.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et27.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et37.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et47.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et57.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et16.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et26.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et36.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et46.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et56.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et15.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et25.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et35.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et45.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et55.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et14.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et24.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et34.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et44.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et54.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et13.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et23.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et33.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et43.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et53.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et12.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et22.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et32.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et42.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et52.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et11.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et21.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et31.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et41.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        et51.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});

    }


    class viewGridSheetAsync extends AsyncTask<String, String, String> {
        String[][][] leaves;
        public String land_id;
        public String grower_id;
        public String worksheet_id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            viewGridSheet(leaves);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                leaves = new String[100][100][100];
                grower_id = getActivity().getIntent().getExtras().getString("grower_id");
                land_id = getActivity().getIntent().getExtras().getString("land_id");

                tbl_leaf_modelDao ldao = thi.daoSession.getTbl_leaf_modelDao();
                int batch_id = 1;

                for (int plant_id = 1; plant_id <= 5; plant_id++) {
                    for (int leaf_id = 1; leaf_id <= 33; leaf_id++) {

                        String cell_damage = "";
                        tbl_leaf_model l = ldao.queryBuilder().where(
                                tbl_leaf_modelDao.Properties.Subdivision.eq(subdivision),
                                tbl_leaf_modelDao.Properties.Grower_id.eq(grower_id),
                                tbl_leaf_modelDao.Properties.Land_id.eq(land_id),
                                tbl_leaf_modelDao.Properties.Batch_id.eq(batch_id),
                                tbl_leaf_modelDao.Properties.Plant_id.eq(plant_id),
                                //tbl_leaf_modelDao.Properties.Worksheet_id.eq(worksheet_id),
                                tbl_leaf_modelDao.Properties.Leaf_number.eq(leaf_id)


                        ).unique();
                        if (l != null) {
                            cell_damage = l.percentage_damage;
                            leaves[leaf_id][plant_id][batch_id] = cell_damage;
                        }
                    }

                }


            } catch (Exception ex) {
                Log.e(tag, "...............line 600" + ex);
            }
            return null;
        }
    }

    public void viewGridSheet(String[][][] leaves) {

        try {
            //row1 from bottom
            et11.setText(leaves[1][1][1]);
            et21.setText(leaves[1][2][1]);
            et31.setText(leaves[1][3][1]);
            et41.setText(leaves[1][4][1]);
            et51.setText(leaves[1][5][1]);


            //row2 from bottom
            et12.setText(leaves[2][1][1]);
            et22.setText(leaves[2][2][1]);
            et32.setText(leaves[2][3][1]);
            et42.setText(leaves[2][4][1]);
            et52.setText(leaves[2][5][1]);

            //row3 from bottom
            et13.setText(leaves[3][1][1]);
            et23.setText(leaves[3][2][1]);
            et33.setText(leaves[3][3][1]);
            et43.setText(leaves[3][4][1]);
            et53.setText(leaves[3][5][1]);


            //row4 from bottom
            et14.setText(leaves[4][1][1]);
            et24.setText(leaves[4][2][1]);
            et34.setText(leaves[4][3][1]);
            et44.setText(leaves[4][4][1]);
            et54.setText(leaves[4][5][1]);

            //row5 from bottom
            et15.setText(leaves[5][1][1]);
            et25.setText(leaves[5][2][1]);
            et35.setText(leaves[5][3][1]);
            et45.setText(leaves[5][4][1]);
            et55.setText(leaves[5][5][1]);

            //row6 from bottom
            et16.setText(leaves[6][1][1]);
            et26.setText(leaves[6][2][1]);
            et36.setText(leaves[6][3][1]);
            et46.setText(leaves[6][4][1]);
            et56.setText(leaves[6][5][1]);

            //row7 from bottom
            et17.setText(leaves[7][1][1]);
            et27.setText(leaves[7][2][1]);
            et37.setText(leaves[7][3][1]);
            et47.setText(leaves[7][4][1]);
            et57.setText(leaves[7][5][1]);

            //row2 from bottom
            et18.setText(leaves[8][1][1]);
            et28.setText(leaves[8][2][1]);
            et38.setText(leaves[8][3][1]);
            et48.setText(leaves[8][4][1]);
            et58.setText(leaves[8][5][1]);


            //row2 from bottom
            et19.setText(leaves[9][1][1]);
            et29.setText(leaves[9][2][1]);
            et39.setText(leaves[9][3][1]);
            et49.setText(leaves[9][4][1]);
            et59.setText(leaves[9][5][1]);


            //row2 from bottom
            et110.setText(leaves[10][1][1]);
            et210.setText(leaves[10][2][1]);
            et310.setText(leaves[10][3][1]);
            et410.setText(leaves[10][4][1]);
            et510.setText(leaves[10][5][1]);


            //row2 from bottom
            et111.setText(leaves[11][1][1]);
            et211.setText(leaves[11][2][1]);
            et311.setText(leaves[11][3][1]);
            et411.setText(leaves[11][4][1]);
            et511.setText(leaves[11][5][1]);


            //row2 from bottom
            et112.setText(leaves[12][1][1]);
            et212.setText(leaves[12][2][1]);
            et312.setText(leaves[12][3][1]);
            et412.setText(leaves[12][4][1]);
            et512.setText(leaves[12][5][1]);


            //row2 from bottom
            et113.setText(leaves[13][1][1]);
            et213.setText(leaves[13][2][1]);
            et313.setText(leaves[13][3][1]);
            et413.setText(leaves[13][4][1]);
            et513.setText(leaves[13][5][1]);


            //row2 from bottom
            et114.setText(leaves[14][1][1]);
            et214.setText(leaves[14][2][1]);
            et314.setText(leaves[14][3][1]);
            et414.setText(leaves[14][4][1]);
            et514.setText(leaves[14][5][1]);


            //row2 from bottom
            et115.setText(leaves[15][1][1]);
            et215.setText(leaves[15][2][1]);
            et315.setText(leaves[15][3][1]);
            et415.setText(leaves[15][4][1]);
            et515.setText(leaves[15][5][1]);


            //row2 from bottom
            et116.setText(leaves[16][1][1]);
            et216.setText(leaves[16][2][1]);
            et316.setText(leaves[16][3][1]);
            et416.setText(leaves[16][4][1]);
            et516.setText(leaves[16][5][1]);


            //row2 from bottom
            et117.setText(leaves[17][1][1]);
            et217.setText(leaves[17][2][1]);
            et317.setText(leaves[17][3][1]);
            et417.setText(leaves[17][4][1]);
            et517.setText(leaves[17][5][1]);


            //row2 from bottom
            et118.setText(leaves[18][1][1]);
            et218.setText(leaves[18][2][1]);
            et318.setText(leaves[18][3][1]);
            et418.setText(leaves[18][4][1]);
            et518.setText(leaves[18][5][1]);


            //row2 from bottom
            et119.setText(leaves[19][1][1]);
            et219.setText(leaves[19][2][1]);
            et319.setText(leaves[19][3][1]);
            et419.setText(leaves[19][4][1]);
            et519.setText(leaves[19][5][1]);


            //row2 from bottom
            et120.setText(leaves[20][1][1]);
            et220.setText(leaves[20][2][1]);
            et320.setText(leaves[20][3][1]);
            et420.setText(leaves[20][4][1]);
            et520.setText(leaves[20][5][1]);


            //row2 from bottom
            et121.setText(leaves[21][1][1]);
            et221.setText(leaves[21][2][1]);
            et321.setText(leaves[21][3][1]);
            et421.setText(leaves[21][4][1]);
            et521.setText(leaves[21][5][1]);


            //row2 from bottom
            et122.setText(leaves[22][1][1]);
            et222.setText(leaves[22][2][1]);
            et322.setText(leaves[22][3][1]);
            et422.setText(leaves[22][4][1]);
            et522.setText(leaves[22][5][1]);


            //row2 from bottom
            et123.setText(leaves[23][1][1]);
            et223.setText(leaves[23][2][1]);
            et323.setText(leaves[23][3][1]);
            et423.setText(leaves[23][4][1]);
            et523.setText(leaves[23][5][1]);


            //row2 from bottom
            et124.setText(leaves[24][1][1]);
            et224.setText(leaves[24][2][1]);
            et324.setText(leaves[24][3][1]);
            et424.setText(leaves[24][4][1]);
            et524.setText(leaves[24][5][1]);


            //row2 from bottom
            et125.setText(leaves[25][1][1]);
            et225.setText(leaves[25][2][1]);
            et325.setText(leaves[25][3][1]);
            et425.setText(leaves[25][4][1]);
            et525.setText(leaves[25][5][1]);


            //row2 from bottom
            et126.setText(leaves[26][1][1]);
            et226.setText(leaves[26][2][1]);
            et326.setText(leaves[26][3][1]);
            et426.setText(leaves[26][4][1]);
            et526.setText(leaves[26][5][1]);


            //row2 from bottom
            et127.setText(leaves[27][1][1]);
            et227.setText(leaves[27][2][1]);
            et327.setText(leaves[27][3][1]);
            et427.setText(leaves[27][4][1]);
            et527.setText(leaves[27][5][1]);


            //row2 from bottom
            et128.setText(leaves[28][1][1]);
            et228.setText(leaves[28][2][1]);
            et328.setText(leaves[28][3][1]);
            et428.setText(leaves[28][4][1]);
            et528.setText(leaves[28][5][1]);


            //row2 from bottom
            et129.setText(leaves[29][1][1]);
            et229.setText(leaves[29][2][1]);
            et329.setText(leaves[29][3][1]);
            et429.setText(leaves[29][4][1]);
            et529.setText(leaves[29][5][1]);


            //row2 from bottom
            et130.setText(leaves[30][1][1]);
            et230.setText(leaves[30][2][1]);
            et330.setText(leaves[30][3][1]);
            et430.setText(leaves[30][4][1]);
            et530.setText(leaves[30][5][1]);


            //row2 from bottom
            et131.setText(leaves[31][1][1]);
            et231.setText(leaves[31][2][1]);
            et331.setText(leaves[31][3][1]);
            et431.setText(leaves[31][4][1]);
            et531.setText(leaves[31][5][1]);


            //row2 from bottom
            et132.setText(leaves[32][1][1]);
            et232.setText(leaves[32][2][1]);
            et332.setText(leaves[32][3][1]);
            et432.setText(leaves[32][4][1]);
            et532.setText(leaves[32][5][1]);


            //row2 from bottom
            et133.setText(leaves[33][1][1]);
            et233.setText(leaves[33][2][1]);
            et333.setText(leaves[33][3][1]);
            et433.setText(leaves[33][4][1]);
            et533.setText(leaves[33][5][1]);
        } catch (Exception ex) {
            Log.e(tag, "..............line 866" + ex);
        }

    }


    class saveGridSheetAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SetSnackBar("saving...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("err")) {
                SetSnackBar("Error...");
            } else {
                SetSnackBar("Saved...");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                saveGridSheet(null);
            } catch (Exception ex) {
                Log.e(tag, "....................." + ex + "");
                return "err";
            }
            return "ok";
        }
    }

    public void saveGridSheet(View v) {

        try {
            String grower_id = getActivity().getIntent().getExtras().getString("grower_id");
            String land_id = getActivity().getIntent().getExtras().getString("land_id");

            tbl_worksheet_model w = thi
                    .daoSession
                    .getTbl_worksheet_modelDao()
                    .queryBuilder()
                    .where(
                            tbl_worksheet_modelDao.Properties.Land_id.eq(land_id),
                            tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id),
                            tbl_worksheet_modelDao.Properties.Sub_division.eq(subdivision)
                    ).list().get(0);
            Log.e(tag, "worksheet id yepanpa.............." + w._id);

            String worksheet_id = w._id + "";


            tbl_leaf_model leaf;

            leaf = new tbl_leaf_model();
            leaf.grower_id = Integer.parseInt(grower_id);
            leaf.land_id = Integer.parseInt(land_id);
            leaf.batch_id = Integer.parseInt(batch);
            leaf.subdivision = Integer.parseInt("0" + subdivision);
            leaf.worksheet_id = Integer.parseInt("0" + worksheet_id);


            leaf.plant_id = 1;
            leaf.leaf_number = 1;
            leaf.percentage_damage = (et11.getText().toString().equals("") ? "-1" : et11.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 1;
            leaf.percentage_damage = (et21.getText().toString().equals("") ? "-1" : et21.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 1;
            leaf.percentage_damage = (et31.getText().toString().equals("") ? "-1" : et31.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 1;
            leaf.percentage_damage = (et41.getText().toString().equals("") ? "-1" : et41.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 1;
            leaf.percentage_damage = (et51.getText().toString().equals("") ? "-1" : et51.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 2;
            leaf.percentage_damage = (et12.getText().toString().equals("") ? "-1" : et12.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 2;
            leaf.percentage_damage = (et22.getText().toString().equals("") ? "-1" : et22.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 2;
            leaf.percentage_damage = (et32.getText().toString().equals("") ? "-1" : et32.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 2;
            leaf.percentage_damage = (et42.getText().toString().equals("") ? "-1" : et42.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 2;
            leaf.percentage_damage = (et52.getText().toString().equals("") ? "-1" : et52.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 3;
            leaf.percentage_damage = (et13.getText().toString().equals("") ? "-1" : et13.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 3;
            leaf.percentage_damage = (et23.getText().toString().equals("") ? "-1" : et23.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 3;
            leaf.percentage_damage = (et33.getText().toString().equals("") ? "-1" : et33.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 3;
            leaf.percentage_damage = (et43.getText().toString().equals("") ? "-1" : et43.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 3;
            leaf.percentage_damage = (et53.getText().toString().equals("") ? "-1" : et53.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 1;
            leaf.leaf_number = 4;
            leaf.percentage_damage = (et14.getText().toString().equals("") ? "-1" : et14.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 4;
            leaf.percentage_damage = (et24.getText().toString().equals("") ? "-1" : et24.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 4;
            leaf.percentage_damage = (et34.getText().toString().equals("") ? "-1" : et34.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 4;
            leaf.percentage_damage = (et44.getText().toString().equals("") ? "-1" : et44.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 4;
            leaf.percentage_damage = (et54.getText().toString().equals("") ? "-1" : et54.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 1;
            leaf.leaf_number = 5;
            leaf.percentage_damage = (et15.getText().toString().equals("") ? "-1" : et15.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 5;
            leaf.percentage_damage = (et25.getText().toString().equals("") ? "-1" : et25.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 5;
            leaf.percentage_damage = (et35.getText().toString().equals("") ? "-1" : et35.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 5;
            leaf.percentage_damage = (et45.getText().toString().equals("") ? "-1" : et45.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 5;
            leaf.percentage_damage = (et55.getText().toString().equals("") ? "-1" : et55.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 6;
            leaf.percentage_damage = (et16.getText().toString().equals("") ? "-1" : et16.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 6;
            leaf.percentage_damage = (et26.getText().toString().equals("") ? "-1" : et26.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 6;
            leaf.percentage_damage = (et36.getText().toString().equals("") ? "-1" : et36.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 6;
            leaf.percentage_damage = (et46.getText().toString().equals("") ? "-1" : et46.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 6;
            leaf.percentage_damage = (et56.getText().toString().equals("") ? "-1" : et56.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 7;
            leaf.percentage_damage = (et17.getText().toString().equals("") ? "-1" : et17.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 2;
            leaf.leaf_number = 7;
            leaf.percentage_damage = (et27.getText().toString().equals("") ? "-1" : et27.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 3;
            leaf.leaf_number = 7;
            leaf.percentage_damage = (et37.getText().toString().equals("") ? "-1" : et37.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 4;
            leaf.leaf_number = 7;
            leaf.percentage_damage = (et47.getText().toString().equals("") ? "-1" : et47.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 5;
            leaf.leaf_number = 7;
            leaf.percentage_damage = (et57.getText().toString().equals("") ? "-1" : et57.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 8;
            leaf.percentage_damage = (et18.getText().toString().equals("") ? "-1" : et18.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 8;
            leaf.percentage_damage = (et28.getText().toString().equals("") ? "-1" : et28.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 8;
            leaf.percentage_damage = (et38.getText().toString().equals("") ? "-1" : et38.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 8;
            leaf.percentage_damage = (et48.getText().toString().equals("") ? "-1" : et48.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 8;
            leaf.percentage_damage = (et58.getText().toString().equals("") ? "-1" : et58.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 9;
            leaf.percentage_damage = (et19.getText().toString().equals("") ? "-1" : et19.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 9;
            leaf.percentage_damage = (et29.getText().toString().equals("") ? "-1" : et29.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 9;
            leaf.percentage_damage = (et39.getText().toString().equals("") ? "-1" : et39.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 9;
            leaf.percentage_damage = (et49.getText().toString().equals("") ? "-1" : et49.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 9;
            leaf.percentage_damage = (et59.getText().toString().equals("") ? "-1" : et59.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 1;
            leaf.leaf_number = 10;
            leaf.percentage_damage = (et110.getText().toString().equals("") ? "-1" : et110.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 10;
            leaf.percentage_damage = (et210.getText().toString().equals("") ? "-1" : et210.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 10;
            leaf.percentage_damage = (et310.getText().toString().equals("") ? "-1" : et310.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 10;
            leaf.percentage_damage = (et410.getText().toString().equals("") ? "-1" : et410.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 10;
            leaf.percentage_damage = (et510.getText().toString().equals("") ? "-1" : et510.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 11;
            leaf.percentage_damage = (et111.getText().toString().equals("") ? "-1" : et111.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 11;
            leaf.percentage_damage = (et211.getText().toString().equals("") ? "-1" : et211.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 11;
            leaf.percentage_damage = (et311.getText().toString().equals("") ? "-1" : et311.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 11;
            leaf.percentage_damage = (et411.getText().toString().equals("") ? "-1" : et411.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 11;
            leaf.percentage_damage = (et511.getText().toString().equals("") ? "-1" : et511.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 12;
            leaf.percentage_damage = (et112.getText().toString().equals("") ? "-1" : et112.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 12;
            leaf.percentage_damage = (et212.getText().toString().equals("") ? "-1" : et212.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 12;
            leaf.percentage_damage = (et312.getText().toString().equals("") ? "-1" : et312.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 12;
            leaf.percentage_damage = (et412.getText().toString().equals("") ? "-1" : et412.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 12;
            leaf.percentage_damage = (et512.getText().toString().equals("") ? "-1" : et512.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 13;
            leaf.percentage_damage = (et113.getText().toString().equals("") ? "-1" : et113.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 13;
            leaf.percentage_damage = (et213.getText().toString().equals("") ? "-1" : et213.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 13;
            leaf.percentage_damage = (et313.getText().toString().equals("") ? "-1" : et313.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 13;
            leaf.percentage_damage = (et413.getText().toString().equals("") ? "-1" : et413.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 13;
            leaf.percentage_damage = (et513.getText().toString().equals("") ? "-1" : et513.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 14;
            leaf.percentage_damage = (et114.getText().toString().equals("") ? "-1" : et114.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 14;
            leaf.percentage_damage = (et214.getText().toString().equals("") ? "-1" : et214.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 14;
            leaf.percentage_damage = (et314.getText().toString().equals("") ? "-1" : et314.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 14;
            leaf.percentage_damage = (et414.getText().toString().equals("") ? "-1" : et414.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 14;
            leaf.percentage_damage = (et514.getText().toString().equals("") ? "-1" : et514.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 15;
            leaf.percentage_damage = (et115.getText().toString().equals("") ? "-1" : et115.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 15;
            leaf.percentage_damage = (et215.getText().toString().equals("") ? "-1" : et215.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 15;
            leaf.percentage_damage = (et315.getText().toString().equals("") ? "-1" : et315.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 15;
            leaf.percentage_damage = (et415.getText().toString().equals("") ? "-1" : et415.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 15;
            leaf.percentage_damage = (et515.getText().toString().equals("") ? "-1" : et515.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 16;
            leaf.percentage_damage = (et116.getText().toString().equals("") ? "-1" : et116.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 16;
            leaf.percentage_damage = (et216.getText().toString().equals("") ? "-1" : et216.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 16;
            leaf.percentage_damage = (et316.getText().toString().equals("") ? "-1" : et316.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 16;
            leaf.percentage_damage = (et416.getText().toString().equals("") ? "-1" : et416.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 16;
            leaf.percentage_damage = (et516.getText().toString().equals("") ? "-1" : et516.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 17;
            leaf.percentage_damage = (et117.getText().toString().equals("") ? "-1" : et117.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 17;
            leaf.percentage_damage = (et217.getText().toString().equals("") ? "-1" : et217.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 17;
            leaf.percentage_damage = (et317.getText().toString().equals("") ? "-1" : et317.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 17;
            leaf.percentage_damage = (et417.getText().toString().equals("") ? "-1" : et417.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 17;
            leaf.percentage_damage = (et517.getText().toString().equals("") ? "-1" : et517.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 18;
            leaf.percentage_damage = (et118.getText().toString().equals("") ? "-1" : et118.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 18;
            leaf.percentage_damage = (et218.getText().toString().equals("") ? "-1" : et218.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 18;
            leaf.percentage_damage = (et318.getText().toString().equals("") ? "-1" : et318.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 18;
            leaf.percentage_damage = (et418.getText().toString().equals("") ? "-1" : et418.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 18;
            leaf.percentage_damage = (et518.getText().toString().equals("") ? "-1" : et518.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 19;
            leaf.percentage_damage = (et119.getText().toString().equals("") ? "-1" : et119.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 19;
            leaf.percentage_damage = (et219.getText().toString().equals("") ? "-1" : et219.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 19;
            leaf.percentage_damage = (et319.getText().toString().equals("") ? "-1" : et319.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 19;
            leaf.percentage_damage = (et419.getText().toString().equals("") ? "-1" : et419.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 19;
            leaf.percentage_damage = (et519.getText().toString().equals("") ? "-1" : et519.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 20;
            leaf.percentage_damage = (et120.getText().toString().equals("") ? "-1" : et120.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 20;
            leaf.percentage_damage = (et220.getText().toString().equals("") ? "-1" : et220.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 20;
            leaf.percentage_damage = (et320.getText().toString().equals("") ? "-1" : et320.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 20;
            leaf.percentage_damage = (et420.getText().toString().equals("") ? "-1" : et420.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 20;
            leaf.percentage_damage = (et520.getText().toString().equals("") ? "-1" : et520.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 21;
            leaf.percentage_damage = (et121.getText().toString().equals("") ? "-1" : et121.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 21;
            leaf.percentage_damage = (et221.getText().toString().equals("") ? "-1" : et221.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 21;
            leaf.percentage_damage = (et321.getText().toString().equals("") ? "-1" : et321.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 21;
            leaf.percentage_damage = (et421.getText().toString().equals("") ? "-1" : et421.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 21;
            leaf.percentage_damage = (et521.getText().toString().equals("") ? "-1" : et521.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 22;
            leaf.percentage_damage = (et122.getText().toString().equals("") ? "-1" : et122.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 22;
            leaf.percentage_damage = (et222.getText().toString().equals("") ? "-1" : et222.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 22;
            leaf.percentage_damage = (et322.getText().toString().equals("") ? "-1" : et322.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 22;
            leaf.percentage_damage = (et422.getText().toString().equals("") ? "-1" : et422.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 22;
            leaf.percentage_damage = (et522.getText().toString().equals("") ? "-1" : et522.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 23;
            leaf.percentage_damage = (et123.getText().toString().equals("") ? "-1" : et123.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 23;
            leaf.percentage_damage = (et223.getText().toString().equals("") ? "-1" : et223.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 23;
            leaf.percentage_damage = (et323.getText().toString().equals("") ? "-1" : et323.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 23;
            leaf.percentage_damage = (et423.getText().toString().equals("") ? "-1" : et423.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 23;
            leaf.percentage_damage = (et523.getText().toString().equals("") ? "-1" : et523.getText().toString());
            saveLeaf(leaf);

            leaf.plant_id = 1;
            leaf.leaf_number = 24;
            leaf.percentage_damage = (et124.getText().toString().equals("") ? "-1" : et124.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 24;
            leaf.percentage_damage = (et224.getText().toString().equals("") ? "-1" : et224.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 24;
            leaf.percentage_damage = (et324.getText().toString().equals("") ? "-1" : et324.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 24;
            leaf.percentage_damage = (et424.getText().toString().equals("") ? "-1" : et424.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 24;
            leaf.percentage_damage = (et524.getText().toString().equals("") ? "-1" : et524.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 25;
            leaf.percentage_damage = (et125.getText().toString().equals("") ? "-1" : et125.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 25;
            leaf.percentage_damage = (et225.getText().toString().equals("") ? "-1" : et225.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 25;
            leaf.percentage_damage = (et325.getText().toString().equals("") ? "-1" : et325.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 25;
            leaf.percentage_damage = (et425.getText().toString().equals("") ? "-1" : et425.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 25;
            leaf.percentage_damage = (et525.getText().toString().equals("") ? "-1" : et525.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 26;
            leaf.percentage_damage = (et126.getText().toString().equals("") ? "-1" : et126.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 26;
            leaf.percentage_damage = (et226.getText().toString().equals("") ? "-1" : et226.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 26;
            leaf.percentage_damage = (et326.getText().toString().equals("") ? "-1" : et326.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 26;
            leaf.percentage_damage = (et426.getText().toString().equals("") ? "-1" : et426.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 26;
            leaf.percentage_damage = (et526.getText().toString().equals("") ? "-1" : et526.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 27;
            leaf.percentage_damage = (et127.getText().toString().equals("") ? "-1" : et127.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 27;
            leaf.percentage_damage = (et227.getText().toString().equals("") ? "-1" : et227.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 27;
            leaf.percentage_damage = (et327.getText().toString().equals("") ? "-1" : et327.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 27;
            leaf.percentage_damage = (et427.getText().toString().equals("") ? "-1" : et427.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 27;
            leaf.percentage_damage = (et527.getText().toString().equals("") ? "-1" : et527.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 28;
            leaf.percentage_damage = (et128.getText().toString().equals("") ? "-1" : et128.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 28;
            leaf.percentage_damage = (et228.getText().toString().equals("") ? "-1" : et228.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 28;
            leaf.percentage_damage = (et328.getText().toString().equals("") ? "-1" : et328.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 28;
            leaf.percentage_damage = (et428.getText().toString().equals("") ? "-1" : et428.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 28;
            leaf.percentage_damage = (et528.getText().toString().equals("") ? "-1" : et528.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 29;
            leaf.percentage_damage = (et129.getText().toString().equals("") ? "-1" : et129.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 29;
            leaf.percentage_damage = (et229.getText().toString().equals("") ? "-1" : et229.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 29;
            leaf.percentage_damage = (et329.getText().toString().equals("") ? "-1" : et329.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 29;
            leaf.percentage_damage = (et429.getText().toString().equals("") ? "-1" : et429.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 29;
            leaf.percentage_damage = (et529.getText().toString().equals("") ? "-1" : et529.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 30;
            leaf.percentage_damage = (et130.getText().toString().equals("") ? "-1" : et130.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 30;
            leaf.percentage_damage = (et230.getText().toString().equals("") ? "-1" : et230.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 30;
            leaf.percentage_damage = (et330.getText().toString().equals("") ? "-1" : et330.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 30;
            leaf.percentage_damage = (et430.getText().toString().equals("") ? "-1" : et430.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 30;
            leaf.percentage_damage = (et530.getText().toString().equals("") ? "-1" : et530.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 31;
            leaf.percentage_damage = (et131.getText().toString().equals("") ? "-1" : et131.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 31;
            leaf.percentage_damage = (et231.getText().toString().equals("") ? "-1" : et231.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 31;
            leaf.percentage_damage = (et331.getText().toString().equals("") ? "-1" : et331.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 31;
            leaf.percentage_damage = (et431.getText().toString().equals("") ? "-1" : et431.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 31;
            leaf.percentage_damage = (et531.getText().toString().equals("") ? "-1" : et531.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 32;
            leaf.percentage_damage = (et132.getText().toString().equals("") ? "-1" : et132.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 32;
            leaf.percentage_damage = (et232.getText().toString().equals("") ? "-1" : et232.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 32;
            leaf.percentage_damage = (et332.getText().toString().equals("") ? "-1" : et332.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 32;
            leaf.percentage_damage = (et432.getText().toString().equals("") ? "-1" : et432.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 32;
            leaf.percentage_damage = (et532.getText().toString().equals("") ? "-1" : et532.getText().toString());
            saveLeaf(leaf);


            leaf.plant_id = 1;
            leaf.leaf_number = 33;
            leaf.percentage_damage = (et133.getText().toString().equals("") ? "-1" : et133.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 2;
            leaf.leaf_number = 33;
            leaf.percentage_damage = (et233.getText().toString().equals("") ? "-1" : et233.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 3;
            leaf.leaf_number = 33;
            leaf.percentage_damage = (et333.getText().toString().equals("") ? "-1" : et333.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 4;
            leaf.leaf_number = 33;
            leaf.percentage_damage = (et433.getText().toString().equals("") ? "-1" : et433.getText().toString());
            saveLeaf(leaf);
            leaf.plant_id = 5;
            leaf.leaf_number = 33;
            leaf.percentage_damage = (et533.getText().toString().equals("") ? "-1" : et533.getText().toString());
            saveLeaf(leaf);


            //updating to 80 percent for saving the grid sheet
            tbl_task_modelDao tbl_task_modelDao_ = thi.daoSession.getTbl_task_modelDao();
            List<tbl_task_model> tasks = tbl_task_modelDao_.queryBuilder()
                    .where(
                            tbl_task_modelDao.Properties.Land_id.eq(land_id)
                            , tbl_task_modelDao.Properties.Grower_id.eq(grower_id))
                    .list();
            if (tasks != null && tasks.size() > 0) {
                tbl_task_model task = tasks.get(0);
                task.claim_stage = 80;
                tbl_task_modelDao_.insertOrReplace(task);
            }

        } catch (Exception ex) {
            Log.e(tag, ".............line 1695 " + ex);
        }

    }


    void deleteGridSheet() {

        String land_id = getActivity().getIntent().getStringExtra("land_id");
        String grower_id = getActivity().getIntent().getStringExtra("grower_id");
        String worksheet_id = getActivity().getIntent().getStringExtra("worksheet_id");

        clearGridSheet(null);
        tbl_leaf_modelDao tbl_leaf_modelDao_ = thi.daoSession.getTbl_leaf_modelDao();
        DeleteQuery<tbl_leaf_model> tableDeleteQuery = tbl_leaf_modelDao_.queryBuilder()
                .where(
                        tbl_leaf_modelDao.Properties.Land_id.eq(land_id),
                        tbl_leaf_modelDao.Properties.Batch_id.eq(batch),
                        tbl_leaf_modelDao.Properties.Worksheet_id.eq(worksheet_id)
                )
                .buildDelete();
        tableDeleteQuery.executeDeleteWithoutDetachingEntities();
        thi.daoSession.clear();

    }

    public void saveLeaf(tbl_leaf_model leaf) {

        tbl_leaf_model newLeaf = new tbl_leaf_model();
        newLeaf.worksheet_id = leaf.worksheet_id;
        newLeaf.subdivision = leaf.subdivision;
        newLeaf.grower_id = leaf.grower_id;
        newLeaf.land_id = leaf.land_id;
        newLeaf.batch_id = leaf.batch_id;
        newLeaf.plant_id = leaf.plant_id;
        newLeaf.leaf_number = leaf.leaf_number;
        newLeaf.percentage_damage = leaf.percentage_damage;
        try {
            String land_id = getActivity().getIntent().getStringExtra("land_id");
            String grower_id = getActivity().getIntent().getStringExtra("grower_id");
            String worksheet_id = getActivity().getIntent().getStringExtra("worksheet_id");

            tbl_leaf_modelDao tbl_leaf_modelDao_ = thi.daoSession.getTbl_leaf_modelDao();
            DeleteQuery<tbl_leaf_model> tableDeleteQuery = tbl_leaf_modelDao_.queryBuilder()
                    .where(
                            tbl_leaf_modelDao.Properties.Subdivision.eq(subdivision),
                            tbl_leaf_modelDao.Properties.Grower_id.eq(grower_id),
                            tbl_leaf_modelDao.Properties.Land_id.eq(leaf.land_id),
                            tbl_leaf_modelDao.Properties.Batch_id.eq(leaf.batch_id),
                            tbl_leaf_modelDao.Properties.Plant_id.eq(leaf.plant_id),
                            tbl_leaf_modelDao.Properties.Worksheet_id.eq(leaf.worksheet_id),
                            tbl_leaf_modelDao.Properties.Leaf_number.eq(leaf.leaf_number)
                    )
                    .buildDelete();
            tableDeleteQuery.executeDeleteWithoutDetachingEntities();
            thi.daoSession.getTbl_leaf_modelDao().insert(newLeaf);
        } catch (Exception ex) {
            Log.e(tag, "....line 1745: " + ex);
        }


    }

    public static void SetSnackBar(String message) {
        try {

            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } catch (Exception ex) {
            Log.e("SnackBarTfragment", "" + ex.getMessage());
        }
    }


    public void clearGridSheet(View v) {

        String land_id = getActivity().getIntent().getStringExtra("land_id");
        String grower_id = getActivity().getIntent().getStringExtra("grower_id");
        //function to view the grid
        tbl_leaf_model leaf = new tbl_leaf_model();
        leaf.grower_id = Integer.parseInt(grower_id);
        leaf.land_id = Integer.parseInt(land_id);
        leaf.batch_id = Integer.parseInt(batch);


        //row1 from bottom
        et11.setText("");
        et21.setText("");
        et31.setText("");
        et41.setText("");
        et51.setText("");


        //row2 from bottom
        et12.setText("");
        et22.setText("");
        et32.setText("");
        et42.setText("");
        et52.setText("");

        //row3 from bottom
        et13.setText("");
        et23.setText("");
        et33.setText("");
        et43.setText("");
        et53.setText("");


        //row4 from bottom
        et14.setText("");
        et24.setText("");
        et34.setText("");
        et44.setText("");
        et54.setText("");

        //row5 from bottom
        et15.setText("");
        et25.setText("");
        et35.setText("");
        et45.setText("");
        et55.setText("");

        //row6 from bottom
        et16.setText("");
        et26.setText("");
        et36.setText("");
        et46.setText("");
        et56.setText("");

        //row7 from bottom
        et17.setText("");
        et27.setText("");
        et37.setText("");
        et47.setText("");
        et57.setText("");

        //row2 from bottom
        et18.setText("");
        et28.setText("");
        et38.setText("");
        et48.setText("");
        et58.setText("");


        //row2 from bottom
        et19.setText("");
        et29.setText("");
        et39.setText("");
        et49.setText("");
        et59.setText("");


        //row2 from bottom
        et110.setText("");
        et210.setText("");
        et310.setText("");
        et410.setText("");
        et510.setText("");


        //row2 from bottom
        et111.setText("");
        et211.setText("");
        et311.setText("");
        et411.setText("");
        et511.setText("");


        //row2 from bottom
        et112.setText("");
        et212.setText("");
        et312.setText("");
        et412.setText("");
        et512.setText("");


        //row2 from bottom
        et113.setText("");
        et213.setText("");
        et313.setText("");
        et413.setText("");
        et513.setText("");


        //row2 from bottom
        et114.setText("");
        et214.setText("");
        et314.setText("");
        et414.setText("");
        et514.setText("");


        //row2 from bottom
        et115.setText("");
        et215.setText("");
        et315.setText("");
        et415.setText("");
        et515.setText("");


        //row2 from bottom
        et116.setText("");
        et216.setText("");
        et316.setText("");
        et416.setText("");
        et516.setText("");


        //row2 from bottom
        et117.setText("");
        et217.setText("");
        et317.setText("");
        et417.setText("");
        et517.setText("");


        //row2 from bottom
        et118.setText("");
        et218.setText("");
        et318.setText("");
        et418.setText("");
        et518.setText("");


        //row2 from bottom
        et119.setText("");
        et219.setText("");
        et319.setText("");
        et419.setText("");
        et519.setText("");


        //row2 from bottom
        et120.setText("");
        et220.setText("");
        et320.setText("");
        et420.setText("");
        et520.setText("");


        //row2 from bottom
        et121.setText("");
        et221.setText("");
        et321.setText("");
        et421.setText("");
        et521.setText("");


        //row2 from bottom
        et122.setText("");
        et222.setText("");
        et322.setText("");
        et422.setText("");
        et522.setText("");


        //row2 from bottom
        et123.setText("");
        et223.setText("");
        et323.setText("");
        et423.setText("");
        et523.setText("");


        //row2 from bottom
        et124.setText("");
        et224.setText("");
        et324.setText("");
        et424.setText("");
        et524.setText("");


        //row2 from bottom
        et125.setText("");
        et225.setText("");
        et325.setText("");
        et425.setText("");
        et525.setText("");


        //row2 from bottom
        et126.setText("");
        et226.setText("");
        et326.setText("");
        et426.setText("");
        et526.setText("");


        //row2 from bottom
        et127.setText("");
        et227.setText("");
        et327.setText("");
        et427.setText("");
        et527.setText("");


        //row2 from bottom
        et128.setText("");
        et228.setText("");
        et328.setText("");
        et428.setText("");
        et528.setText("");


        //row2 from bottom
        et129.setText("");
        et229.setText("");
        et329.setText("");
        et429.setText("");
        et529.setText("");


        //row2 from bottom
        et130.setText("");
        et230.setText("");
        et330.setText("");
        et430.setText("");
        et530.setText("");


        //row2 from bottom
        et131.setText("");
        et231.setText("");
        et331.setText("");
        et431.setText("");
        et531.setText("");


        //row2 from bottom
        et132.setText("");
        et232.setText("");
        et332.setText("");
        et432.setText("");
        et532.setText("");


        //row2 from bottom
        et133.setText("");
        et233.setText("");
        et333.setText("");
        et433.setText("");
        et533.setText("");

    }


    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

}
