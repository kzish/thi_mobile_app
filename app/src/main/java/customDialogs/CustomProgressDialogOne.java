package customDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zw.co.qbit.thi_app.R;


public class CustomProgressDialogOne  {
    private AlertDialog b;
    public void showCustomDialog(Context context,String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog_one, null);
        TextView txt_msg = dialogView.findViewById(R.id.txt_msg);
        txt_msg.setText(msg);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
//        RelativeLayout mainLayout = (RelativeLayout) dialogView.findViewById(R.id.mainLayout);
//        mainLayout.setBackgroundResource(R.drawable.my_progress_one);
//        AnimationDrawable frameAnimation = (AnimationDrawable)mainLayout.getBackground();
//        frameAnimation.start();
        b = dialogBuilder.create();
        b.show();
    }


    public void hide()
    {
        b.dismiss();
    }


}
