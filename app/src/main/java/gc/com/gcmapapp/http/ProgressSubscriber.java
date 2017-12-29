package gc.com.gcmapapp.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import gc.com.gcmapapp.view.SimpleLoadDialog;
import rx.Subscriber;

public  abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{


    private SimpleLoadDialog dialogHandler;
    private boolean isShowDialog = true;
    private Context mContext;

    public ProgressSubscriber(Context context, boolean cancelable) {
        this.mContext = context;
        this.isShowDialog = true;
        dialogHandler = new SimpleLoadDialog(context,this,cancelable);
    }

    public ProgressSubscriber(Context context) {
        dismissProgressDialog();
        this.mContext = context;
        this.isShowDialog = true;
        dialogHandler = new SimpleLoadDialog(context,this,true);
    }
//


    @Override
    public void onCompleted() {

    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (dialogHandler != null && isShowDialog) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.show();
        }
    }

    @Override
    public void onNext(T t) {
        Log.i("onNextTTTT","TTTTTTT====");
        dismissProgressDialog();
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
//            dialogHandler=null;
        }
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!isNetworkConnected()) { //这里自行替换判断网络的代码
            _onError("Net work is not available!");
        } else if (e instanceof ApiException) {
//            Log.i("Observableresult1",e.getMessage());
            _onError(e.getMessage());
        } else {
            _onError("无法连接服务器");
        }
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);

      public boolean isNetworkConnected() {
          if (mContext != null) {
              ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                      .getSystemService(Context.CONNECTIVITY_SERVICE);
              NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
              if (mNetworkInfo != null) {
                  return mNetworkInfo.isAvailable();
              }
          }
          return false;
      }
}
