package gc.com.gcmapapp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;


import java.lang.ref.WeakReference;

import gc.com.gcmapapp.http.ProgressCancelListener;

public class SimpleLoadDialog {

    private ProgressDialog load = null;



    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    private final WeakReference<Context> reference;

    public SimpleLoadDialog(Context context, ProgressCancelListener mProgressCancelListener,
                            boolean cancelable) {
        super();
        this.reference = new WeakReference<Context>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void create(){
        if (load == null) {
            context  = reference.get();
            load=new ProgressDialog(context);
            load.setMessage("Loading");

//                    = new Dialog(context, R.style.loadstyle);
//            View dialogView = LayoutInflater.from(context).inflate(
//                    R.layout.custom_sload_layout, null);
            load.setCanceledOnTouchOutside(false);
            load.setCancelable(cancelable);
//            load.setContentView(dialogView);
            load.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(mProgressCancelListener!=null)
                        mProgressCancelListener.onCancelProgress();
                }
            });
            Window dialogWindow = load.getWindow();
            dialogWindow.setGravity(Gravity.CENTER_VERTICAL
                    | Gravity.CENTER_HORIZONTAL);
        }
        if (!load.isShowing()&&context!=null) {
            load.show();
        }
    }

    public void show(){
        create();
    }


    public  void dismiss() {
        context  = reference.get();
        if (load != null&&load.isShowing()&&!((Activity) context).isFinishing()) {
//            String name = Thread.currentThread().getName();
            load.dismiss();
            load = null;
        }
    }


}
