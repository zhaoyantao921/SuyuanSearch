package com.shou.zhao.suyuansearch.makeorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.shou.zhao.suyuansearch.R;

import java.io.File;
import java.io.FileOutputStream;

public class QRcodeActivity extends AppCompatActivity {

    private String code;
    private WebView wb;
    private Button bt_screen;
    private String filePath;

    private File file_picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        code=getIntent().getStringExtra("code");
        wb= (WebView)findViewById(R.id.web_view);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("https://cli.im/api/qrcode/code?text="+code+"&mhid=t0rGXl7onJghMHcvLd1cOqg");
        bt_screen=(Button)findViewById(R.id.qr_picture);
        bt_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshot();
                if(filePath!=null) {
                    Toast.makeText(QRcodeActivity.this, "截屏已保存在:" + filePath.toString(), Toast.LENGTH_LONG).show();
                    finish();
                    displayImage(filePath);
                }
            }
        });
    }

    private void displayImage(String filepath) {
        Intent i=getImageFileIntent(filepath);
        startActivity(i);
    }
    public static Intent getImageFileIntent(String param ) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    private void screenshot()
    {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        DisplayMetrics metric=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        Rect fame=new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(fame);

        Bitmap bmp1=Bitmap.createBitmap(bmp,0,0+fame.top,width,height-bt_screen.getHeight()-fame.top);

        if (bmp1 != null)
        {
            try {

                // 获取内置SD卡路径

                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                filePath = sdCardPath + File.separator + code+".png";
                file_picture = new File(filePath);
                FileOutputStream os = new FileOutputStream(file_picture);
                bmp1.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }

}
