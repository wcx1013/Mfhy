package butterknife.internal;

import android.view.View;

/**
 * A {@linkplain View.OnClickListener click listener} that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
  static boolean enabled = true;

  private static final Runnable ENABLE_AGAIN = () -> enabled = true;

  @Override public final void onClick(View v) {
    if (enabled) {
      enabled = false;
      //modify by xzq 延时时间改为500ms
      //注意：延时恢复按钮到可点击状态是全局的，
      //当触发第一个点击事件后，会屏蔽后面的点击事件，直到延时完毕
      //v.post(ENABLE_AGAIN);
      v.postDelayed(ENABLE_AGAIN,500);
      doClick(v);
    }
  }

  public abstract void doClick(View v);
}
