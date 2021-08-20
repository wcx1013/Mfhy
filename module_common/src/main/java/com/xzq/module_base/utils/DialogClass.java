package com.xzq.module_base.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xzq.module_base.R;

public class DialogClass{
    public static Dialog showDialogUpdate(Context context, String msg) {
        View dialogcontentView = View.inflate(context, R.layout.layout_newversion, null);
        TextView textView = dialogcontentView.findViewById(R.id.tv_msg);
        textView.setText(msg);
        Dialog dialog = new Dialog(context, R.style.BaseDialog);
        dialog.setContentView(dialogcontentView);
        return dialog;
    }

//    public static AlertDialog showDialogTip(Context base, String text, View.OnClickListener listener) {
//        View dialogcontentView = View.inflate(base, R.layout.dialog_tip, null);
//        final TextView dialogmessage = dialogcontentView.findViewById(R.id.tv_msg);
//        dialogmessage.setText(text);
//        final AlertDialog dialog = new AlertDialog.Builder(base)
//                .setView(dialogcontentView)
//                .setCancelable(false)
//                .show();
//        dialogcontentView.findViewById(R.id.sure).setOnClickListener(v -> {
//            dialog.dismiss();
//            if (listener != null) {
//                listener.onClick(v);
//            }
//        });
//        dialogcontentView.findViewById(R.id.cancel).setOnClickListener(v -> {
//            dialog.dismiss();
//
//        });
//        Window window = dialog.getWindow();
//        window.setBackgroundDrawable(new ColorDrawable(0));
//        final WindowManager.LayoutParams attributes = window.getAttributes();
////        attributes.width = DensityUtild.getPhoneWidth(base) * 3 / 4;
//        attributes.width = AdaptScreenUtils.pt2Px(300);
//        window.setAttributes(attributes);
//        return dialog;
//    }
}
