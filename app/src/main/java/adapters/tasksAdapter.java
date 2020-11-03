package adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import java.util.List;
import java.util.jar.Attributes;

import attachments.AttachmentsActivity;
import models.tbl_task_model;
import syncPackages.syncUploadTask;
import zw.co.qbit.thi_app.HomeActivity;
import zw.co.qbit.thi_app.MarkLandsActivity;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.SubDivisionIndex;
import zw.co.qbit.thi_app.TabActivity;
import zw.co.qbit.thi_app.thi;

/**
 * Created by soyuz on 12/24/2017.
 */

public class tasksAdapter extends BaseAdapter {
    Activity activity;
    List<tbl_task_model> tasks;
    String tag  = "tasksAdapter";
    public tasksAdapter(Activity act, List<tbl_task_model> tasks_)
    {
        tasks=tasks_;
        activity =act;
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
        final View v  = inflater.inflate(R.layout.task_item, viewGroup, false);
        final TextView txt_grower_name =(TextView)v.findViewById(R.id.txt_grower_name);
        final TextView txt_percentage =(TextView)v.findViewById(R.id.txt_percentage);
        final TextView txt_farmaddress = (TextView)v.findViewById(R.id.farmAddress);
        final tbl_task_model g= tasks.get(i);
        txt_grower_name.setText(g.grower_name +"\n("+g.land_name+ ")");
        txt_percentage.setText(g.claim_stage+"%");
       // txt_farmaddress.setText(g.directions_to_farm);
        //source code


        final BootstrapProgressBar pbar =(BootstrapProgressBar)v.findViewById(R.id.progress_bar);

        pbar.setProgress(Integer.parseInt("0"+g.claim_stage));


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(activity, v);
                popup.getMenu().add(g.grower_name + " menu");
                popup.getMenuInflater().inflate(R.menu.menu_tasks_popup, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int menuId = item.getItemId();
                        switch (menuId) {

                            case R.id.menu_edit:
                                Intent inspections = new Intent(activity,SubDivisionIndex.class);
                                inspections.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                inspections.putExtra("land_id", g.land_id);
                                inspections.putExtra("grower_id", g.grower_id);
                                inspections.putExtra("farm_address", g.directions_to_farm);
                                Log.e(tag,"..........saint............ growerid"+g.grower_id);
                                activity.startActivity(inspections);
                                break;
                            case R.id.menu_delete:
                                new AlertDialog.Builder(activity)
                                        .setTitle("Confirm Delete")
                                        .setMessage("Do you really want to delete this inspection?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                String grower_id="";
                                                String land_id = "";

                                                deleteInspection(land_id,grower_id);

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();


                                break;
                            case R.id.menu_upload:
                                HomeActivity.pd.showCustomDialog(HomeActivity.activity,"Uploading inspection please wait...");
                                syncUploadTask.sync(activity,g);
                                break;

                            case R.id.menu_add_images:
                                Intent ac = new Intent(HomeActivity.activity, AttachmentsActivity.class);
                                ac.putExtra("land_id",g.land_id);
                                ac.putExtra("grower_id",g.grower_id);
                                HomeActivity.activity.startActivity(
                                        ac
                                );
                                break;

                            case R.id.menu_mark_boundary:
                                Intent mb = new Intent(HomeActivity.activity, MarkLandsActivity.class);
                                mb.putExtra("land_id",g.land_id);
                                mb.putExtra("grower_id",g.grower_id);
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

                //return false;
            }
        });

        return v;
    }


    void deleteInspection(String land_id,String grower_id)
    {}

}
