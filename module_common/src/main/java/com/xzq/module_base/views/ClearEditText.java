package com.xzq.module_base.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.xzq.module_base.R;


/**
 * Created by Shaomeng on 2017/12/18.
 * 带删除功能的Edittext
 */

public class ClearEditText extends androidx.appcompat.widget.AppCompatEditText {

    private Drawable mDeleteImage;// 删除按钮
    private boolean showDeleteButton = false;//当前是否显示删除按钮
    private int padding;
    private GestureDetector gestureDetector;

    public ClearEditText(Context context) {
        super(context);
        init(context);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void setDrawables(String text) {
        Drawable deleteImage = !TextUtils.isEmpty(text) ? mDeleteImage : null;
        showDeleteButton = !TextUtils.isEmpty(text);
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], deleteImage, drawables[3]);//添加drawable ， position = right
    }

    private int inputType = 0;

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        inputType = getInputType();
        if (mDeleteImage == null) {
            mDeleteImage = context.getResources().getDrawable(R.drawable.ic_edt_clear);
        }
        padding = getCompoundDrawablePadding();
        if (padding <= 0) {
            padding = dp2px(context, 10);
            setCompoundDrawablePadding(padding);
        }
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String s = ((TextView) v).getText().toString();
                    setDrawables(s);
                } else {
                    setDrawables(null);
                }
            }
        });

        addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hasFocus())
                    setDrawables(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                String str = s.toString();
//                //当输入方式为numberDecimal的时候限制小数点后面最多两位小数
//                if (inputType == 8194 && str.contains(".")) {
//                    String[] st = str.split("\\.");
//                    if (st.length > 1 && st[1].length() > 2) {
//                        ToastUtils.showToastAtCenter("最多两位小数");
//                        String text = st[0] + "." + st[1].charAt(0) + st[1].charAt(1);
//                        setText(text);
//                        setSelection(text.length());
//                    }
//                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    private final class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            //如果删除按钮显示，并且输入框有内容
            if (showDeleteButton && !TextUtils.isEmpty(getText())) {
                if (event.getX() > (getWidth() - getTotalPaddingRight() - padding) &&
                        event.getX() < (getWidth() - getPaddingRight() + padding))
                    //只有在这区域能触发清除内容的效果
                    if (getText() != null) {
                        getText().clear();
                    }
            }
            return super.onSingleTapUp(event);
        }
    }
}
