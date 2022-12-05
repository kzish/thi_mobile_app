package globals;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;

import zw.co.qbit.thi_app.thi;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by soyuz on 4/15/2017.
 */

public class globals {
    public static String server_url="http://167.86.73.213/thi";//online
//    public static String server_url="http://192.168.100.150:56744";//local centric data main
//    public static String server_url="http://192.168.1.103:56744";//home

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }


    //set shared preferences
    public static void savePref(String valueKey, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(thi.CTX);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(valueKey, value);
        edit.commit();
    }

    //get the shred prefs
    public static String readPrefs(String valueKey, String valueDefault) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(thi.CTX);
        return prefs.getString(valueKey, valueDefault);
    }


    public static String getDate()
    {
        DateTime dt = new DateTime();
        DateTimeFormatter fmt =  DateTimeFormat.forPattern("MM/dd/yyyy");
        String str = fmt.print(dt);
        return str;
    }

    public static String getTime()
    {
        DateTime dt = new DateTime();
        DateTimeFormatter fmt =  DateTimeFormat.forPattern("HH:mm");
        String str = fmt.print(dt);
        return str;
    }


    public static String getId() {

        // return String.format("%02d", new Random().nextInt(100));
        return UUID.randomUUID().toString();
    }

    public static class InputFilterMinMax implements InputFilter {

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
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
    public static class InputFilterMinMaxDecimal implements InputFilter {

        private double min, max;

        public InputFilterMinMaxDecimal(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMaxDecimal(String min, String max) {
            this.min = Double.parseDouble(min);
            this.max = Double.parseDouble(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                double input = Double.parseDouble(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(double a, double b, double c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public static String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagBytes,Base64.DEFAULT);
    }


    public static boolean checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        return mExternalStorageAvailable && mExternalStorageWriteable;
    }

    public static String createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/thiData");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/thiData/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/thiData/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("kz",e+"................................ createDirectoryAndSaveFile");
            return "";
        }

        Log.e("kz","........."+Environment.getExternalStorageDirectory() + "/thiData/"+fileName);
        return "synced=false;"+Environment.getExternalStorageDirectory() + "/thiData/"+fileName;
        //added synced=false; to flag the file as needing to be synced
    }


}
