package com.shou.zhao.suyuansearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhao on 2017/3/16.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    TextView openidTextView;
    TextView nicknameTextView;

    private String nickName;
    private String gender;
    Button loginbutton;
    ImageView userlogo;
    private Tencent mTencent;

    public static QQAuth mQQAuth;
    public static String mAppid;
    public static String openodString;
    public static String nicknameString;

    Bitmap bitmap = null;


    private static final String TAG = "LoginActivity";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject jo = (JSONObject) msg.obj;
                if (jo.has("nickname")) {
                    try {
                        nicknameString = jo.getString("nickname");
                        nicknameTextView.setText(nicknameString);
                        Log.e(TAG, "--" + nicknameString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
               /* Bitmap bitmap = (Bitmap) msg.obj;
                userlogo.setImageBitmap(bitmap);*/
                nicknameTextView.setText(nickName);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        loginbutton = (Button)findViewById(R.id.login_QQ);
        nicknameTextView = (TextView) findViewById(R.id.user_nickname);
        openidTextView = (TextView) findViewById(R.id.user_openid);
        userlogo = (ImageView) findViewById(R.id.user_logo);
        loginbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_QQ:
                Log.d(TAG,"11111111111111111");
                loginQQ();
                break;
            default:
                break;
        }

    }

    //调用QQ登陆
    private void loginQQ() {
        mAppid = "222222";
        mTencent = Tencent.createInstance(mAppid, getApplicationContext());
        mTencent.login(LoginActivity.this, "all", loginListener);

        UserInfo userInfo=new UserInfo(LoginActivity.this,mTencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);

    }

    IUiListener userInfoListener=new IUiListener() {
        @Override
        public void onComplete(Object o) {
            if(o==null){
                return;
            }
           JSONObject jo=(JSONObject) o;
            try {
                System.out.println("json=" + String.valueOf(jo));

                 gender = jo.getString("gender");
                Message message=new Message();
                message.what=1;
                mHandler.sendMessage(message);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };
    IUiListener loginListener = new IUiListener() {
        /**
         * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
         * "pf":"desktop_m_qq-10000144-android-2002-",
         * "query_authority_cost":448,
         * "authority_cost":-136792089,
         * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
         * "expires_in":7776000,
         * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
         * "msg":"",
         * "access_token":"A2455F491478233529D0106D2CE6EB45",
         * "login_cost":499}
         */
        @Override
        public void onComplete(Object response) {
            System.out.println("有数据返回..");
            if (response == null) {
                return;
            }
            try {
                JSONObject jo = (JSONObject) response;

                int ret = jo.getInt("ret");

                System.out.println("json=" + String.valueOf(jo));

                if (ret == 0) {
                    Toast.makeText(LoginActivity.this, "登录成功",
                            Toast.LENGTH_LONG).show();

                    String openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                }

            } catch (Exception e) {

            }

        }

        @Override
        public void onError(UiError uiError) {
        }
        @Override
        public void onCancel() {

        }
    };
}
