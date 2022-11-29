package adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.widget.PopupMenu;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import daoModels.tbl_leaf_modelDao;
import daoModels.tbl_worksheet_modelDao;
import models.tbl_leaf_model;
import models.tbl_worksheet_model;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.SubDivisionIndex;
import zw.co.qbit.thi_app.TabActivity;
import zw.co.qbit.thi_app.thi;

/**
 * Created by soyuz on 12/26/2017.
 */

public class worksheetAdapter extends BaseAdapter {

    String tag = "worksheetAdapter";
    List<tbl_worksheet_model> sheets;
    Activity activity;
    String land_id;
    String grower_id;

    public worksheetAdapter(Activity act, String land_id, String grower_id) {
        this.activity = act;
        this.land_id = land_id;
        this.grower_id = grower_id;
        this.sheets = thi.daoSession.getTbl_worksheet_modelDao()
                .queryBuilder().whereOr(
                        tbl_worksheet_modelDao.Properties.Land_id.eq(this.land_id),
                        tbl_worksheet_modelDao.Properties.Land_id.eq(-1)
                ).list();
    }

    @Override
    public int getCount() {
        return sheets.size();
    }

    @Override
    public Object getItem(int i) {
        return sheets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return sheets.get(i)._id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        final View v = inflater.inflate(R.layout.worksheet_item, viewGroup, false);
        final tbl_worksheet_model w = sheets.get(i);

        TextView txt_subdivision = (TextView) v.findViewById(R.id.txt_subdivision);
        txt_subdivision.setText(w.sub_division + "");
        final Intent ws = new Intent(activity, TabActivity.class);



        ws.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        if (w.sub_division == 0) {
            txt_subdivision.setText("add subdivision");

        } else {
            txt_subdivision.setText("subdivision " + w.sub_division);
        }


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (w.sub_division == 0)
                {
                    ws.putExtra("new", "1");
                    ws.putExtra("subdivision", "0");
                    ws.putExtra("land_id", land_id);
                    ws.putExtra("grower_id", grower_id);
                    ws.putExtra("worksheet_id", "0");
                    activity.startActivity(ws);
                }

                else
                {
                    final PopupMenu popup = new PopupMenu(activity, v);
                    popup.getMenuInflater().inflate(R.menu.menu_worksheet_popup, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            int menuId = item.getItemId();
                            switch (menuId) {

                                case R.id.menu_edit:
                                    if (w.sub_division == 0) {
                                        ws.putExtra("new", "1");
                                    } else {
                                        ws.putExtra("new", "0");
                                    }
                                    ws.putExtra("subdivision", w.sub_division+"");
                                    ws.putExtra("land_id", land_id);
                                    ws.putExtra("grower_id", grower_id);
                                    ws.putExtra("worksheet_id", w._id);
                                    activity.startActivity(ws);

                                    break;
                                case R.id.menu_delete:
                                    new AlertDialog.Builder(activity)
                                            .setTitle("Confirm Delete")
                                            .setMessage("Do you really want to delete this worksheet\nand its gridsheets?")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {

                                                    //delete the worksheet
                                                    tbl_worksheet_model wsm = thi.daoSession.getTbl_worksheet_modelDao()
                                                            .queryBuilder().where(
                                                                    tbl_worksheet_modelDao.Properties.Land_id.eq(land_id),
                                                                    tbl_worksheet_modelDao.Properties.Grower_id.eq(grower_id),
                                                                    tbl_worksheet_modelDao.Properties.Sub_division.eq(w.sub_division)
                                                            ).unique();

                                                    thi.daoSession.getTbl_worksheet_modelDao().delete(wsm);
                                                    //delete the gridsheets
                                                    List<tbl_leaf_model> leafs = thi.daoSession.getTbl_leaf_modelDao()
                                                            .queryBuilder().where(
                                                                    tbl_leaf_modelDao.Properties.Land_id.eq(land_id),
                                                                    tbl_leaf_modelDao.Properties.Grower_id.eq(grower_id),
                                                                    tbl_leaf_modelDao.Properties.Subdivision.eq(w.sub_division)
                                                            ).list();

                                                    for (tbl_leaf_model l : leafs) {
                                                        thi.daoSession.getTbl_leaf_modelDao().delete(l);
                                                    }
                                                    SubDivisionIndex.refreshList();


                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, null).show();


                                    break;


                                default:
                                    break;


                            }

                            return true;
                        }
                    });
                    popup.show();

                }



                }//.onclick
            });
        return v;
    }
}
