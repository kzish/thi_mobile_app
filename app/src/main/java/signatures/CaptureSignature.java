package signatures;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import daoModels.tbl_signature_modelDao;
import daoModels.tbl_task_modelDao;
import models.tbl_signature_model;
import models.tbl_task_model;
import zw.co.qbit.thi_app.R;
import zw.co.qbit.thi_app.thi;


public class CaptureSignature extends Activity {

    LinearLayout mContent;
    signature mSignature;
    Button mClear, mGetSign, mCancel;
    public static String tempDir;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    View mView;
    File mypath;
    String tag="capture signature";

    private String uniqueId;
    String grower_id, land_id;
    //private EditText yourName;
    String signature_url;
    String signatory;
    EditText txt_name;
    private int STORAGE_PERMISSION_RC = 112;
    tbl_signature_model sig = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signature);
        signatory = getIntent().getStringExtra("sig");
        land_id = getIntent().getStringExtra("land_id");
        grower_id = getIntent().getStringExtra("grower_id");
        //TextView lblname= (TextView) findViewById(R.id.lblname);
        //lblname.setText("");

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_name.setText("Inspector");
        if (signatory.equals("1")) {
            txt_name.setVisibility(View.INVISIBLE);
        }else if( signatory.equals("4")){
            txt_name.setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.txt3)).setText(R.string.draw_subdivision_diagram);
        }
        tempDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        prepareDirectory();
        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        current = uniqueId + ".png";
        mypath = new File(directory, current);


        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = (Button) findViewById(R.id.clear);
        mGetSign = (Button) findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button) findViewById(R.id.cancel);
        mView = mContent;

        //yourName = (EditText) findViewById(R.id.yourName);

        mClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });

        mGetSign.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureSignature();
                if (signatory.equals("3")) {
                    if (txt_name.getText().toString().equals("")) {
                        txt_name.setError("Enter name of inspector");
                        return;
                    }

                }
                if (!error) {
                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);
                    Bundle b = new Bundle();
                    b.putString("status", "done");
                    b.putString("signatory", signatory);
                    b.putString("signature_url", signature_url);




                    try {
                        sig = thi.daoSession.getTbl_signature_modelDao()
                                .queryBuilder()
                                .where(
                                        tbl_signature_modelDao.Properties.Grower_id.eq(grower_id),
                                        tbl_signature_modelDao.Properties.Land_id.eq(land_id)
                                )
                                .list().get(0);
                    } catch (Exception ex) {

                    }

                    if (sig == null) {
                        sig = new tbl_signature_model();
                        sig.land_id = land_id;
                        sig.grower_id = grower_id;
                    }
                    if (signatory.equals("3")) {

                        sig.second_inspector_name = txt_name.getText().toString();

                    }
                    if (signatory.equals("1")) {
                        if (globals.globals.checkExternalMedia()) {

                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        CaptureSignature.this.getContentResolver()
                                        , Uri.parse(signature_url));
                                sig.grower_signature_uri = globals.globals.createDirectoryAndSaveFile(bitmap,globals.globals.getId()+".jpg");
                                AddSignaturesActivity.imggrowersignature.setImageURI(Uri.parse(signature_url));


                            }catch(Exception ex) {
                                Log.e(tag, "...............line 164 " + ex);
                            }

                        } else {
                            Toast.makeText(thi.CTX, "Enable permision to save file.", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (signatory.equals("2")) {
                        if (globals.globals.checkExternalMedia()) {


                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        CaptureSignature.this.getContentResolver()
                                        , Uri.parse(signature_url));
                                sig.inspector_signature_uri = globals.globals.createDirectoryAndSaveFile(bitmap,globals.globals.getId()+".jpg");
                                AddSignaturesActivity.imgfirstinspectorsignature.setImageURI(Uri.parse(signature_url));

                            }catch(Exception ex) {
                                Log.e(tag, "...............line 183 " + ex);
                            }



                        } else {
                            Toast.makeText(thi.CTX, "Enable permision to save file.", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (signatory.equals("3")) {
                        if (globals.globals.checkExternalMedia()) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        CaptureSignature.this.getContentResolver()
                                        , Uri.parse(signature_url));
                                sig.second_inspector_siginature_uri = globals.globals.createDirectoryAndSaveFile(bitmap,globals.globals.getId()+".jpg");
                                AddSignaturesActivity.imgsecondinspectorsignature.setImageURI(Uri.parse(signature_url));


                            }catch(Exception ex) {
                                Log.e(tag, "...............line 164 " + ex);
                            }



                        } else {
                            Toast.makeText(thi.CTX, "Enable permision to save file.", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (signatory.equals("4")) {
                        if (globals.globals.checkExternalMedia()) {

                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        CaptureSignature.this.getContentResolver()
                                        , Uri.parse(signature_url));
                                sig.draw_map_uri = globals.globals.createDirectoryAndSaveFile(bitmap,globals.globals.getId()+".jpg");
                                AddSignaturesActivity.imgSubDivisionDiagram.setImageURI(Uri.parse(signature_url));

                            }catch(Exception ex) {
                                Log.e(tag, "...............line 183 " + ex);
                            }



                        } else {
                            Toast.makeText(thi.CTX, "Enable permision to save file.", Toast.LENGTH_LONG).show();
                        }
                    }


                    thi.daoSession.getTbl_signature_modelDao().insertOrReplace(sig);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            tbl_task_model t = thi.daoSession.getTbl_task_modelDao()
                                    .queryBuilder()
                                    .where(
                                            tbl_task_modelDao.Properties.Land_id.eq(sig.land_id),
                                            tbl_task_modelDao.Properties.Grower_id.eq(sig.grower_id)
                                    ).unique();
                            if(
                                    sig.grower_signature_uri!=null &&
                                    sig.inspector_signature_uri!=null &&
                                    sig.second_inspector_siginature_uri!=null &&
                                    sig.draw_map_uri != null

                                    ){
                                t.claim_stage = 100;
                                thi.daoSession.getTbl_task_modelDao().insertOrReplace(t);
                            }
                        }
                    });



                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

    private boolean captureSignature() {

        boolean error = false;
        String errorMessage = "";


//        if(yourName.getText().toString().equalsIgnoreCase("")){
//            errorMessage = errorMessage + "Please enter your Name\n";
//            error = true;
//        }

        if (error) {
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }

        return error;
    }

    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate = (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:", String.valueOf(todaysDate));
        return (String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:", String.valueOf(currentTime));
        return (String.valueOf(currentTime));

    }


    private boolean prepareDirectory() {
        try {
            if (makedirs()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs() {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()) {
            File[] files = tempdir.listFiles();
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }


        //function to saave the image
        public void save(View v) {

            if (!globals.globals.checkExternalMedia()) {
                Toast.makeText(thi.CTX, "Enable to save signature\nEnable permission to save files", Toast.LENGTH_LONG).show();
                return;
            }
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
                ;
            }
            Canvas canvas = new Canvas(mBitmap);
            try {
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                signature_url = url;
                Log.v("log_tag", "url: " + url);
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_RC) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                Toast.makeText(this, "No permission to read external storage.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    //int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_RC);
            return;
        }

    }


}

