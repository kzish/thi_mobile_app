package signatures;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import daoModels.tbl_signature_modelDao;
import globals.globals;
import models.tbl_signature_model;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.thi;

public class AddSignaturesActivity extends AppCompatActivity {

    public static ImageView imggrowersignature, imgfirstinspectorsignature, imgsecondinspectorsignature,imgSubDivisionDiagram;
    String tag = "AddSignaturesActivity";
    Toolbar toolbar;
    String grower_id, land_id;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_signatures);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        grower_id = getIntent().getStringExtra("grower_id");
        land_id = getIntent().getStringExtra("land_id");

        imggrowersignature = (ImageView) findViewById(R.id.imggrowersignature);
        imgfirstinspectorsignature = (ImageView) findViewById(R.id.imgfirstinspectorsignature);
        imgsecondinspectorsignature = (ImageView) findViewById(R.id.imgsecondinspectorsignature);
        imgSubDivisionDiagram = (ImageView) findViewById(R.id.subdivisiondiagram);


        imggrowersignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(AddSignaturesActivity.this, CaptureSignature.class);
                sig.putExtra("grower_id", grower_id);
                sig.putExtra("land_id", land_id);
                sig.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sig.putExtra("sig", "1");
                //getActivity().startActivity(sig);
                if (globals.checkExternalMedia())
                    startActivityForResult(sig, 1);
                else
                    Toast.makeText(thi.CTX, "Unable to capture signature\nEnable permission to save files", Toast.LENGTH_SHORT).show();


            }
        });


        imgfirstinspectorsignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(AddSignaturesActivity.this, CaptureSignature.class);
                sig.putExtra("grower_id", grower_id);
                sig.putExtra("land_id", land_id);
                sig.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sig.putExtra("sig", "2");
                //getActivity().startActivity(sig);
                if (globals.checkExternalMedia())
                    startActivityForResult(sig, 1);
                else
                    Toast.makeText(thi.CTX, "Unable to capture signature\nEnable permission to save files", Toast.LENGTH_SHORT).show();

            }
        });

        imgsecondinspectorsignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(AddSignaturesActivity.this, CaptureSignature.class);
                sig.putExtra("grower_id", grower_id);
                sig.putExtra("land_id", land_id);
                sig.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sig.putExtra("sig", "3");
                //getActivity().startActivity(sig);
                if (globals.checkExternalMedia())
                    startActivityForResult(sig, 1);
                else
                    Toast.makeText(thi.CTX, "Unable to capture signature\nEnable permission to save files", Toast.LENGTH_SHORT).show();

            }
        });

        imgSubDivisionDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sig = new Intent(AddSignaturesActivity.this, CaptureSignature.class);
                sig.putExtra("grower_id", grower_id);
                sig.putExtra("land_id", land_id);
                sig.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sig.putExtra("sig", "4");
                //getActivity().startActivity(sig);
                if (globals.checkExternalMedia())
                    startActivityForResult(sig, 1);
                else
                    Toast.makeText(thi.CTX, "Unable to capture signature\nEnable permission to save files", Toast.LENGTH_SHORT).show();

            }
        });

        getSignatures();

    }


    //get the signature from the datatbse
    private void getSignatures() {
        //clear the signatures first by resetting the original bitmap
        String uri = "@drawable/ic_signature";  // where myresource (without the extension) is the file

        int imageResource = thi.CTX.getResources().getIdentifier(uri, null, thi.CTX.getPackageName());

        Drawable res = thi.CTX.getResources().getDrawable(imageResource);
        imggrowersignature.setImageDrawable(res);//default sugnature icon
        imgfirstinspectorsignature.setImageDrawable(res);//default sugnature icon
        imgsecondinspectorsignature.setImageDrawable(res);//default sugnature icon
        imgSubDivisionDiagram.setImageDrawable(res);

        try {

            tbl_signature_model sig = thi.daoSession.getTbl_signature_modelDao()
                    .queryBuilder()
                    .where(
                            tbl_signature_modelDao.Properties.Land_id.eq(land_id),
                            tbl_signature_modelDao.Properties.Grower_id.eq(grower_id)
                    )
                    .unique();
            if (sig != null) {
                imggrowersignature.setImageURI(Uri.parse("file://"+sig.grower_signature_uri.replace("synced=true;", "").replace("synced=false;", "")));
                imgfirstinspectorsignature.setImageURI(Uri.parse("file://"+sig.inspector_signature_uri.replace("synced=true;", "").replace("synced=false;", "")));
                imgsecondinspectorsignature.setImageURI(Uri.parse("file://"+sig.second_inspector_siginature_uri.replace("synced=true;", "").replace("synced=false;", "")));
                imgSubDivisionDiagram.setImageURI(Uri.parse("file://"+sig.draw_map_uri.replace("synced=true;","").replace("synced=false;","")));

                Log.e("kz","/..........sig. "+sig.grower_signature_uri.replace("synced=true;", "").replace("synced=false;", ""));
                Log.e("kz","/..........sig. "+sig.inspector_signature_uri.replace("synced=true;", "").replace("synced=false;", ""));
                Log.e("kz","/..........sig. "+sig.second_inspector_siginature_uri.replace("synced=true;", "").replace("synced=false;", ""));
            }
        } catch (Exception ex) {
            Log.e("kz", "line 139................... " + ex);
        }

    }

}
