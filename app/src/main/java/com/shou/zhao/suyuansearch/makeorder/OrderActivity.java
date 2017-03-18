package com.shou.zhao.suyuansearch.makeorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shou.zhao.suyuansearch.R;
import com.shou.zhao.suyuansearch.net.Config;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderActivity extends AppCompatActivity {

    private String typeID;
    private String typeName;
    private String typePicture;
    private Float typePrice;
    private TextView tv_item_id;
    private TextView tv_item_name;
    private TextView tv_item_price;
    private TextView tv_item_sale_way;

    private TextInputEditText input_name;
    private TextInputEditText input_phone;
    private TextInputEditText input_address;
    private TextInputEditText input_sum;

    private ImageView im_item_picture;

    private CheckBox checkBox_if_box;
    private CheckBox checkBox_pay_recevice;
    private CheckBox checkBox_pay_online;

    private Button button_buy;

    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("订购");
        setSupportActionBar(toolbar);

        initview();//初始化视图
        initcheckbox();//设置checkbox
        initTypeItemPick();//得到传递值

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_fresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initTypeItemPick();

                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();

            }
        });

        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //购买动作
                if(TextUtils.isEmpty(input_name.getText())){
                    Toast.makeText(OrderActivity.this,"姓名不能为空",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(input_phone.getText())){
                    Toast.makeText(OrderActivity.this,"联系方式不能为空",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(input_address.getText())){
                    Toast.makeText(OrderActivity.this,"地址不能为空",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(input_sum.getText())){
                    Toast.makeText(OrderActivity.this,"数量不能为空",Toast.LENGTH_LONG).show();
                }else if(input_name.getText()!=null
                        &&input_address.getText()!=null
                        &&input_phone.getText()!=null
                        &&input_sum.getText()!=null
                        &&checkBox_pay_recevice.isChecked()==true
                        &&checkBox_if_box.isChecked()==true){

                    Log.d("预订信息",typeName+";"+input_sum.getText().toString()+"箱；总价："+typePrice*Integer.parseInt(input_sum.getText().toString())+"");
                    final SweetAlertDialog fDialog=new SweetAlertDialog(OrderActivity.this,SweetAlertDialog.WARNING_TYPE);
                    fDialog.setTitleText("订单信息");
                    fDialog.setConfirmText("确认订单");
                    fDialog.setContentText("预订信息"+typeName+";"+input_sum.getText().toString()+"箱；总价："+typePrice*Integer.parseInt(input_sum.getText().toString())+"");
                    fDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            fDialog.dismiss();
                            funOrder();
                        }
                    });
                    fDialog.setCancelText("取消订单").setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            fDialog.dismiss();
                            input_name.setText(null);
                            input_phone.setText(null);
                            input_address.setText(null);
                            input_sum.setText(null);
                            finish();
                        }
                    });
                    fDialog.show();
                }
            }

            private void funOrder() {
                String ordersum=input_sum.getText().toString();
                String customer=input_name.getText().toString();
                String orderadress=input_address.getText().toString();
                String orderphome=input_phone.getText().toString();
                String ordertypeid=typeID;
                String allprice=typePrice*Integer.parseInt(input_sum.getText().toString())+"";
                final SweetAlertDialog pDialog = new SweetAlertDialog(OrderActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.show();
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                    }
                });
                pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        pDialog.dismiss();
                    }
                });
                pDialog.setCancelable(false);
                new PushOrder(ordersum, customer, orderadress, orderphome, ordertypeid, allprice, new PushOrder.SuccessCallback() {
                    @Override
                    public void onSuccess(final String result) {

                        input_name.setText(null);
                        input_phone.setText(null);
                        input_address.setText(null);
                        input_sum.setText(null);

                        pDialog.setTitleText("生成订单二维码后请截屏保存")
                                .setContentText(result)
                                .setConfirmText("生成订单二维码")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        /*final View view_web = LayoutInflater.from(OrderActivity.this).inflate(R.layout.layout_qrcode,null);
                                        wb= (WebView) view_web.findViewById(R.id.web_view);
                                        wb.getSettings().setJavaScriptEnabled(true);
                                        wb.setWebViewClient(new WebViewClient());
                                        wb.loadUrl("https://cli.im/api/qrcode/code?text="+result+"&mhid=t0rGXl7onJghMHcvLd1cOqg");
                                        sweetAlertDialog.setCancelable(true);
                                        sweetAlertDialog.setContentView(view_web);*/
                                        Intent intent=new Intent(OrderActivity.this,QRcodeActivity.class);
                                        intent.putExtra("code",result);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    }
                }, new PushOrder.FailCallback() {
                    @Override
                    public void onFail() {
                        pDialog.setTitleText("失败")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });


            }
        });
        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"确认退出？",Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
            }
        });
    }


    private void initview() {
        tv_item_id=(TextView)findViewById(R.id.pick_item_id);
        tv_item_name=(TextView)findViewById(R.id.pick_item_name);
        tv_item_price=(TextView)findViewById(R.id.pick_item_price);
        im_item_picture=(ImageView)findViewById(R.id.pick_item_picture);
        tv_item_sale_way=(TextView) findViewById(R.id.pick_item_sale_way);
        checkBox_if_box=(CheckBox)findViewById(R.id.checkBox_buy_way);
        checkBox_pay_recevice=(CheckBox)findViewById(R.id.checkBox_pay_recevice);
        checkBox_pay_online=(CheckBox)findViewById(R.id.checkBox_pay_online);

        input_name=(TextInputEditText)findViewById(R.id.input_name);
        input_phone=(TextInputEditText)findViewById(R.id.input_phone);
        input_address=(TextInputEditText)findViewById(R.id.input_address);
        input_sum=(TextInputEditText)findViewById(R.id.input_num);

        button_buy=(Button)findViewById(R.id.pick_item_buy);
    }

    private void initcheckbox() {
        checkBox_pay_recevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox_pay_online.setChecked(false);
            }
        });
        checkBox_pay_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox_pay_recevice.setChecked(true);
            }
        });
        checkBox_if_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==false){
                    Toast.makeText(OrderActivity.this,"此商品暂时支持支按箱购买",Toast.LENGTH_LONG).show();
                    checkBox_if_box.setChecked(true);
                }else {
                    checkBox_if_box.setChecked(true);
                }
            }
        });
        checkBox_pay_online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Toast.makeText(OrderActivity.this,"此商品暂时支持货到付款",Toast.LENGTH_LONG).show();
                    checkBox_pay_online.setChecked(false);
                    checkBox_pay_recevice.setChecked(true);
                }
            }
        });


    }
    /*初始化选择的item信息*/
    private void initTypeItemPick() {
        final SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("商店信息加载中");
        sweetAlertDialog.show();
        sweetAlertDialog.setCancelable(false);
        typeID=getIntent().getStringExtra("typeID");
        typePrice=getIntent().getFloatExtra("typePrice",10);
        typeName=getIntent().getStringExtra("typeName");
        typePicture=getIntent().getStringExtra("typePicture");
        tv_item_sale_way.setText("20KG/箱");//要从服务器上获取数据
        tv_item_price.setText(typePrice.toString()+"/斤");
        tv_item_id.setText(typeID);
        tv_item_name.setText(typeName);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("图片加载",Config.API_GET_IMAGE+typePicture.toString());
                            Glide.with(OrderActivity.this).load(Config.API_GET_IMAGE+typePicture.toString()).centerCrop().thumbnail(0.1f).error(R.drawable.error_pic).into(im_item_picture);
                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            sweetAlertDialog.setTitleText("图片架加载失败").setConfirmText("ERROR").
                    changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
        sweetAlertDialog.setTitleText("加载成功").setConfirmText("OK")
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_order,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            /*case R.id.backup:
                HttpUtils.doGetAsyn(Config.API_AREA_BY_ID, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        System.out.print("----------------------------");
                        Log.d("父类id=54：",result.toString());
                    }
                },"parentid","24");

                break;*/
            case  R.id.delete:
                finish();
                break;
            /*case R.id.setting:
                finish();
                break;*/
            default:
                break;
        }
        return true;
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

      /*  if ((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()) {
            wb.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }else */
      if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出订购", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }else if((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()){
          wb.goBack(); //goBack()表示返回WebView的上一页面
          return true;
      }
        return super.onKeyDown(keyCode, event);
    }
}
