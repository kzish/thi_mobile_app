package adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.widget.PopupMenu;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import java.util.List;

import attachments.AttachmentsActivity;
import models.tbl_task_model;
import syncPackages.DeleteInspection;
import syncPackages.UploadInspection;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.MarkLandsActivity;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.SubDivisionIndex;

/**
 * Created by soyuz on 12/24/2017.
 */

public class tasksAdapter extends BaseAdapter {
    Activity activity;
    List<tbl_task_model> tasks;
    String tag = "tasksAdapter";

    public tasksAdapter(Activity act, List<tbl_task_model> tasks_) {
        tasks = tasks_;
        activity = act;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0L;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        final View v = inflater.inflate(R.layout.task_item, viewGroup, false);
        final TextView txt_grower_name = (TextView) v.findViewById(R.id.txt_grower_name);
        final TextView txt_percentage = (TextView) v.findViewById(R.id.txt_percentage);
        final tbl_task_model g = tasks.get(i);
        txt_grower_name.setText(g.grower_name + "\n(" + g.land_name + ")");
        txt_percentage.setText(g.claim_stage + "%");
        final BootstrapProgressBar pbar = (BootstrapProgressBar) v.findViewById(R.id.progress_bar);
        pbar.setProgress(Integer.parseInt("0" + g.claim_stage));
        if (g.claim_stage == 100) {
            pbar.setBackgroundColor(Color.GREEN);
        }
        if (g.uploaded == 1) {
            txt_grower_name.setText(g.grower_name +"\nuploaded");
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(activity, v);
                popup.getMenuInflater().inflate(R.menu.menu_tasks_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int menuId = item.getItemId();
                        switch (menuId) {

                            case R.id.menu_edit:
                                Intent inspections = new Intent(activity, SubDivisionIndex.class);
                                inspections.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                inspections.putExtra("land_id", g.land_id);
                                inspections.putExtra("grower_id", g.grower_id);
                                inspections.putExtra("farm_address", g.directions_to_farm);
                                Log.e(tag, "..........saint............ growerid" + g.grower_id);
                                activity.startActivity(inspections);
                                break;
                            case R.id.menu_upload:
                                UploadInspection.upload(g);
                                break;

                            case R.id.menu_delete:
                                DeleteInspection.delete(g);
                                break;

                            case R.id.menu_add_images:
                                Intent ac = new Intent(HomeActivity.activity, AttachmentsActivity.class);
                                ac.putExtra("land_id", g.land_id);
                                ac.putExtra("grower_id", g.grower_id);
                                HomeActivity.activity.startActivity(
                                        ac
                                );
                                break;

                            case R.id.menu_mark_boundary:
                                Intent mb = new Intent(HomeActivity.activity, MarkLandsActivity.class);
                                mb.putExtra("land_id", g.land_id);
                                mb.putExtra("grower_id", g.grower_id);
                                HomeActivity.activity.startActivity(
                                        mb
                                );
                                break;
                            default:
                                break;
                        }

                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

        return v;
    }

}
