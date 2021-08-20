package com.xzq.module_base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xzq.module_base.R;
import com.xzq.module_base.utils.SizeUtils;

/**
 * PrivateDialog
 * Created by xzq on 2020/5/20.
 */
public class PrivateDialog extends Dialog implements View.OnClickListener {

    private PrivateDialog(@NonNull Context context) {
        super(context, R.style.DialogScaleStyle);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_dialog_common));
        window.setGravity(Gravity.CENTER);
        int widthPixels = (int) (SizeUtils.widthPixels(context) * 0.8);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_private, null);
        ViewGroup.LayoutParams layoutParams;
        layoutParams = new ViewGroup.LayoutParams(widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(contentView, layoutParams);
        setCancelable(false);
        setCanceledOnTouchOutside(false);


        TextView tvContent = findViewById(R.id.dlg_tv_content);
        TextView tvCancel = findViewById(R.id.dlg_tv_cancel);
        TextView tvOk = findViewById(R.id.dlg_tv_ok);
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);

        tvContent.setHighlightColor(Color.TRANSPARENT);
        String src = tvContent.getText().toString();
        String target = "《用户协议》";

        int fromIndex = src.indexOf(target);
        int endIndex = fromIndex + target.length();
        SpannableString span = new SpannableString(src);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //AgreementActivity.start(context, "用户协议", AgreementText.REG);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, fromIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.themeColor)),
                fromIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvContent.setText(span);
    }

    public static void show(Context context,
                            OnOkClickListener okClickListener,
                            OnCancelClickListener cancelClickListener) {
        PrivateDialog commonDialog = new PrivateDialog(context);
        commonDialog.setOkClickListener(okClickListener);
        commonDialog.setCancelClickListener(cancelClickListener);
        commonDialog.show();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (v.getId() == R.id.dlg_tv_cancel) {
            if (cancelClickListener != null) {
                cancelClickListener.onDialogCancelClicked();
            }
        } else {
            if (okClickListener != null) {
                okClickListener.onDialogOkClicked();
            }
        }
    }

    private OnOkClickListener okClickListener;
    private OnCancelClickListener cancelClickListener;

    private void setOkClickListener(OnOkClickListener okClickListener) {
        this.okClickListener = okClickListener;
    }

    private void setCancelClickListener(OnCancelClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    public interface OnOkClickListener {
        void onDialogOkClicked();
    }

    public interface OnCancelClickListener {
        void onDialogCancelClicked();
    }
}
