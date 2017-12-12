package gc.com.gcmapapp.http;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

public class HttpUtil {

    /**
     * 构造方法私有
     */
    private HttpUtil() {


    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    /**
     * 获取单例
     */
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 添加线程管理并订阅
     * @param ob
     * @param subscriber
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber,final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        //数据预处理
        ob.compose(RxHelper.handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                }) .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
