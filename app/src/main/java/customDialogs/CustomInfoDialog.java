package customDialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import zw.co.qbit.thi_app.R;


public class CustomInfoDialog {

    public void showCustomDialog(Context context,String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_info_dialog, null);
        TextView txt_msg = dialogView.findViewById(R.id.txt_msg);
        BootstrapButton btn_close = dialogView.findViewById(R.id.btn_close);
        txt_msg.setText(msg);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
//        RelativeLayout mainLayout = (RelativeLayout) dialogView.findViewById(R.id.mainLayout);
//        mainLayout.setBackgroundResource(R.drawable.my_progress_one);
//        AnimationDrawable frameAnimation = (AnimationDrawable)mainLayout.getBackground();
//        frameAnimation.start();
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }


}
