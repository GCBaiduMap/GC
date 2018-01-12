package gc.com.gcmapapp.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.utils.ScreenUtils;

/**
 * Created by Calvin on 12/01/2018.
 */

public class WelcomeDialog extends Dialog {
    private static WelcomeDialog instance = null;
    private Context context;
    private LinearLayout mIntegralLayout;
    TextView nameTV;
    TextView contentTV;
    Button btnClose;
    public WelcomeDialog(@NonNull Context context) {
        super(context);
    }

    public WelcomeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        setContentView(R.layout.dialog_show_welcome_info);
        mIntegralLayout = findViewById(R.id.container_linearLayout);
        nameTV = findViewById(R.id.tv1);
        contentTV = findViewById(R.id.tv2);
        btnClose =findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    protected WelcomeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static  WelcomeDialog createDialog(
            Context context) {
        instance = new WelcomeDialog(context, R.style.CoordinationInfoDialogTheme);
        android.view.WindowManager.LayoutParams p = instance.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (ScreenUtils.getScreenHeight(context) - ScreenUtils.getStatusHeight(context));
        p.width = (int) (ScreenUtils.getScreenWidth(context));
        instance.getWindow().setAttributes(p);
        return instance;
    }

    public void setContent(String name, String textContent){
        nameTV.setText("欢迎 "+name);
        contentTV.setText("当前项目:"+textContent);
        startAnimation();
    }

    private void startAnimation(){
        float screenHeight = ScreenUtils.getScreenHeight(context);
        float startTranslationY = ScreenUtils.getScreenHeight(context) /4;
        float endTranslationY = ScreenUtils.getScreenHeight(context) /10;
//        LogUtil.log("ShowIntegralDialog", "height:" + ScreenUtils.getScreenHeight(context));
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mIntegralLayout, "translationY",
                -screenHeight,0, -endTranslationY,0);
//        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mIntegralTv, "alpha",
//                1.0f, 0f);

        /**
         * anim1，anim2,anim3同时执行
         * anim4接着执行
         */
        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new LinearInterpolator());
        animSet.play(anim1);
        animSet.setDuration(2000);
        animSet.start();
        animSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

                //ShowIntegralDialog.this.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
}
