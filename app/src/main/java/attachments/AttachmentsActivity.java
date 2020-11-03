package attachments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.util.ArrayList;
import java.util.List;

import adapters.attachments_adapter;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import models.tbl_attachments;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.thi;

import static net.alhazmy13.mediapicker.Video.VideoPicker.EXTRA_VIDEO_PATH;

public class AttachmentsActivity extends AppCompatActivity {
    static ListView list_pics;
    Toolbar toolbar;

    TextView txt_images;

    static Activity act;

    static String land_id;
    static String grower_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachments);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        land_id = getIntent().getStringExtra("land_id");
        grower_id = getIntent().getStringExtra("grower_id");
        list_pics = (ListView) findViewById(R.id.list_pics);
        act = this;


        txt_images = (TextView) findViewById(R.id.txt_images);
        txt_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance().setMaxCount(1)
                        //.setActivityTheme(R.style.AppTheme)
                        .pickPhoto(AttachmentsActivity.this);
            }
        });


        loadAttachments();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAttachments();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    List<String> photos = new ArrayList<>();
                    photos.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

                    for (String n : photos) {
                        tbl_attachments attach = new tbl_attachments();
                        attach.fileType = "image";
                        attach.grower_id = grower_id;
                        attach.land_id = land_id;
                        attach.filepath = n;
                        getFileName(attach);
                    }
                }
                break;

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public static void loadAttachments() {

        attachments_adapter a_image = new attachments_adapter("image", grower_id,land_id, act);
        list_pics.setAdapter(a_image);
        a_image.notifyDataSetChanged();

    }


    public static void getFileName(final tbl_attachments attach) {
        final EditText txtUrl = new EditText(act);

        txtUrl.setHint("...");

        new AlertDialog.Builder(act)
                .setTitle("Add filename")
                .setMessage("the name of the file...")
                .setView(txtUrl)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        if (!url.equals("")) {
                            attach.filename = url;
                            attach.isSynced = false;
                            thi.daoSession.getTbl_attachmentsDao().insertOrReplace(attach);
                            loadAttachments();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }


}
