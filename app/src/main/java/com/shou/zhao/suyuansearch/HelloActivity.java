package com.shou.zhao.suyuansearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import static com.shou.zhao.suyuansearch.R.layout.activity_hello;

public class HelloActivity extends Activity {

    private ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_hello);
      /* View view= LayoutInflater.from(this).inflate(activity_hello,null);
        //YoYo.with(Techniques.Tada).duration(500).delay(200).playOn(layout_qrcode.findViewById(R.id.op_huanshui));
        YoYo.with(Techniques.Flash).duration(500).delay(100).playOn(view.findViewById(R.id.imagehello));*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkNetworkState();
            }
        },2000);
    }

    private boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            setNetwork();//不可用
        } else {
            isNetworkAvailable();//可用
        }

        return flag;
    }
    private void setNetwork(){
        Toast.makeText(this, "wifi is closed!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.hellosuyuan);
        builder.setTitle("网络提示信息");
        builder.setMessage("网络不可用，如果继续，请先设置网络！");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                /**
                 * 判断手机系统的版本！如果API大于10 就是3.0+
                 * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                 */
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

    private void isNetworkAvailable(){
       // NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        NetworkInfo.State net=manager.getActiveNetworkInfo().getState();
        //判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
        if(net == NetworkInfo.State.CONNECTED || net == NetworkInfo.State.CONNECTING){
            Toast.makeText(this, "可用网络，已连接", Toast.LENGTH_SHORT).show();
            loadAdmob();
        }

    }

    /**
     * 在wifi状态下 加载admob广告
     */
    private void loadAdmob(){
        Intent intent = new Intent(HelloActivity.this, MainActivity.class);
        /*3.16*/
        Intent intent1=new Intent(HelloActivity.this,LoginActivity.class);

        startActivity(intent);
        HelloActivity.this.finish();
    }
}
