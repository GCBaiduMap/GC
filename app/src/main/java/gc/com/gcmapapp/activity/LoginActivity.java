package gc.com.gcmapapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gc.com.gcmapapp.R;
import gc.com.gcmapapp.application.Constants;
import gc.com.gcmapapp.bean.Login;
import gc.com.gcmapapp.http.Api;
import gc.com.gcmapapp.http.HttpUtil;
import gc.com.gcmapapp.http.ProgressSubscriber;
import gc.com.gcmapapp.utils.SharePreferenceUtil;
import gc.com.gcmapapp.utils.ToastUtils;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.layout_login)
    FrameLayout login;
    @BindView(R.id.container)
    RelativeLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.layout_login)
    public void login(View view) {
        String host = (String) SharePreferenceUtil.get(getApplicationContext(), Constants.HOST, "");
        if(TextUtils.isEmpty(host)){
            setHost();
            return;
        }
        String userName = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showMessage(context, "用户名为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showMessage(context, "密码为空");
            return;
        }
        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).login(userName, password), new ProgressSubscriber<Login>(this) {
            @Override
            protected void _onNext(Login login) {
                SharePreferenceUtil.put(getApplicationContext(), Constants.TOKEN, login.getToken());
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showMessage(context, message);
            }
        }, lifecycleSubject);
    }

    @OnClick(R.id.ib_setting)
    public void setHost(){
        final EditText editText = new EditText(context);
        new AlertDialog.Builder(context)
                .setTitle("请输入服务器地址")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String host  = editText.getText().toString();
                        if(TextUtils.isEmpty(host)){
                            ToastUtils.showMessage(context, "服务器地址不能为空");
                            return;
                        }
                        if(!host.startsWith("http://")){
                            host = "http://" + host ;
                        }
                         host = host + "/gismgr/";
                        SharePreferenceUtil.put(getApplicationContext(), Constants.HOST, host);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


}

