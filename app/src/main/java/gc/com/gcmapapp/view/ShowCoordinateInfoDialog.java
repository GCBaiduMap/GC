package gc.com.gcmapapp.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import gc.com.gcmapapp.R;
import gc.com.gcmapapp.activity.CoordinationActivity;
import gc.com.gcmapapp.adapter.CoordinationAdapter;
import gc.com.gcmapapp.bean.CoordinationInfo;
import gc.com.gcmapapp.utils.ScreenUtils;


public class ShowCoordinateInfoDialog extends Dialog {
	private static ShowCoordinateInfoDialog instance = null;
	private Context context;
	private OnAnimationEnd mOnAnimationEnd;
	private ListView coordinationLv;
	List<CoordinationInfo> coordinationInfos;
	private CoordinationAdapter coordinationAdapter;
	private RelativeLayout cooridnationContainer;
	Button btnClose;
	private ShowCoordinateInfoDialog(final Context context, int theme) {
		super(context, theme);
		this.context = context;
		setContentView(R.layout.dialog_show_coordinate_info);
		//getWindow().getAttributes().gravity = Gravity.CENTER;
		coordinationLv = findViewById(R.id.cooridnation_lv);
		cooridnationContainer = findViewById(R.id.cooridnation_container);
		coordinationLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(context, CoordinationActivity.class);
				intent.putExtra("img_id", coordinationInfos.get(i).getId());
				intent.putExtra("getDetail_1_value", coordinationInfos.get(i).getDetail_1_value());
				intent.putExtra("getDetail_2_key", coordinationInfos.get(i).getDetail_2_key());
				intent.putExtra("getDetail_2_value", coordinationInfos.get(i).getDetail_2_value());
				intent.putExtra("getDetail_3_key", coordinationInfos.get(i).getDetail_3_key());
				intent.putExtra("getDetail_3_value", coordinationInfos.get(i).getDetail_3_value());
				intent.putExtra("getDetail_4_key", coordinationInfos.get(i).getDetail_4_key());
				intent.putExtra("getDetail_4_value", coordinationInfos.get(i).getDetail_4_value());
				intent.putExtra("getDetail_5_key", coordinationInfos.get(i).getDetail_5_key());
				intent.putExtra("getDetail_5_value", coordinationInfos.get(i).getDetail_5_value());
				intent.putExtra("getDetail_6_key", coordinationInfos.get(i).getDetail_6_key());
				intent.putExtra("getDetail_6_value", coordinationInfos.get(i).getDetail_6_value());
				intent.putExtra("getDetail_7_key", coordinationInfos.get(i).getDetail_7_key());
				intent.putExtra("getDetail_7_value", coordinationInfos.get(i).getDetail_7_value());
//				intent.putExtra("getDetail_8_key", coordinationInfos.get(i).getDetail_8_key());
//				intent.putExtra("getDetail_8_value", coordinationInfos.get(i).getDetail_8_value());
				context.startActivity(intent);
				dismiss();
			}
		});
		setCancelable(true);
		setCanceledOnTouchOutside(false);

		btnClose = findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public static  ShowCoordinateInfoDialog createDialog(
			Context context) {
	
		instance = new ShowCoordinateInfoDialog(context, R.style.CoordinationInfoDialogTheme);
		android.view.WindowManager.LayoutParams p = instance.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.height = (int) (ScreenUtils.getScreenHeight(context) - ScreenUtils.getStatusHeight(context));
		p.width = (int) (ScreenUtils.getScreenWidth(context));    
		instance.getWindow().setAttributes(p); 
		return instance;
	}


	public  void setCoordinations(List<CoordinationInfo> coordinations) {
		this.coordinationInfos = coordinations;
		coordinationAdapter = new CoordinationAdapter(context, coordinationInfos);
		coordinationLv.setAdapter(coordinationAdapter);
		startAnimation();
	}
	
	private void startAnimation(){
		float screenHeight = ScreenUtils.getScreenHeight(context) * 3/4;
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(cooridnationContainer, "translationY",
				screenHeight,0);
//        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mIntegralTv, "alpha",
//                1.0f, 0f);
          
        /** 
         * anim1，anim2,anim3同时执行 
         * anim4接着执行 
         */  
        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new LinearInterpolator());
        animSet.play(anim1);
        animSet.setDuration(500);
        animSet.start(); 
        animSet.addListener(new AnimatorListener() {
			
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
				if(mOnAnimationEnd != null)
					mOnAnimationEnd.onEnd();
				//ShowIntegralDialog.this.dismiss();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	public void setOnAnimationEnd(OnAnimationEnd mOnAnimationEnd) {
		this.mOnAnimationEnd = mOnAnimationEnd;
	}



	public interface OnAnimationEnd{
		void onEnd();
	}
}
