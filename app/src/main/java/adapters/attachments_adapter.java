package adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import daoModels.tbl_attachmentsDao;
import models.tbl_attachments;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.thi;

import static attachments.AttachmentsActivity.loadAttachments;


/**
 * Created by soyuz on 12/2/2017.
 */

public class attachments_adapter extends BaseAdapter {

    List<tbl_attachments> attachments;
    String file_type;
    String land_id;
    String grower_id;
    Activity activity;
    public attachments_adapter(String ftype,String grower_id,String land_id,Activity a)
    {

        file_type=ftype;
        this.grower_id=grower_id;
        this.land_id=land_id;



        if(file_type.equals("image")) {

            attachments = thi.daoSession.
                    getTbl_attachmentsDao().queryBuilder().
                    where(
                            tbl_attachmentsDao.Properties.Land_id.eq(land_id),
                            tbl_attachmentsDao.Properties.Grower_id.eq(grower_id),
                            tbl_attachmentsDao.Properties.FileType.eq("image")
                    ).list();

        }

        activity = a;
    }

    @Override
    public int getCount() {
        return attachments.size();
    }

    @Override
    public Object getItem(int position) {
        return  attachments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return attachments.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View v = inflater.inflate(R.layout.attachment_item, parent, false);

        final tbl_attachments attach = attachments.get(position);
        ImageView img_icon =(ImageView)v.findViewById(R.id.img_icon);
        TextView txt_filename =(TextView) v.findViewById(R.id.txt_filename);
        if(attach.filepath.equals("0000"))
        {
            txt_filename.setTextColor(Color.rgb(255,0,0));
        }
        txt_filename.setText(attach.filename);


        if(attach.filepath.equals("0000")) img_icon.setVisibility(View.INVISIBLE);

            if (attach.fileType.equals("image"))
                img_icon.setImageDrawable(activity.getDrawable(R.drawable.ic_picture));

        if(attach.isSynced)
        {
            img_icon.setImageDrawable(activity.getDrawable(R.drawable.ic_synced_gree));
        }

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                        final PopupMenu popup = new PopupMenu(activity, v);
                        popup.getMenuInflater().inflate(R.menu.attachment_item_menu, popup.getMenu());


                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                int menuId = item.getItemId();
                                switch (menuId) {

                                    case R.id.menu_open:
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                        Uri data = Uri.parse("file:///"+attach.filepath);

                                        if(attach.fileType.equals("image"))
                                        intent.setDataAndType(data, "image/*");

                                        else if(attach.fileType.equals("video"))
                                            intent.setDataAndType(data, "video/*");

                                        else if(attach.fileType.equals("audio"))
                                            intent.setDataAndType(data, "audio/*");

                                        else {


                                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl("file:///"+attach.filepath));
                                            intent.setDataAndType(data, mimeType);
                                        }


                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        thi.CTX.startActivity(intent);
                                        Log.e("saint", attach.getFilepath());
                                        break;
                                    case R.id.menu_delete:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setTitle("Confirm Delete");
                                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                thi.daoSession.getTbl_attachmentsDao().delete(attach);
                                                dialog.dismiss();
                                                loadAttachments();
                                            }
                                        });
                                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //TODO
                                                dialog.dismiss();
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();


                                        break;


                                    default:
                                        break;


                                }

                                return true;
                            }
                        });


                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                popup.show();//showing popup menu
                            }
                        });
               // return false;
            }
        });


        return v;
    }
    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

}
