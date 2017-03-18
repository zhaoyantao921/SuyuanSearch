package com.shou.zhao.suyuansearch.makeorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shou.zhao.suyuansearch.R;
import com.shou.zhao.suyuansearch.dao.Type;

import java.util.ArrayList;
import java.util.List;

public class ActivityTypeList extends AppCompatActivity {
    private List<Type> typelist =new ArrayList<>();

    private TypeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list_layout);
        initType();
        //要展示订单
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("溯源商店");
        setSupportActionBar(toolbar);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_fresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
                swipeRefreshLayout.setRefreshing(false);
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
    private void initType() {
        typelist =(ArrayList<Type>)getIntent().getSerializableExtra("typelist");
        Log.d("传递过来的参数",typelist.toString());
        adapter=new TypeAdapter(ActivityTypeList.this,typelist);
        //GridLayoutManager layoutmanager=new GridLayoutManager(this,3);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutmanager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(adapter);
    }

    private void refreshList() {
        /*更新列表*/
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initType();//更新数据源
                        adapter.notifyDataSetChanged();//跟新适配器
                    }
                });
            }
        }).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
           /* case R.id.backup:
                break;*/
            case  R.id.delete:
                finish();
                break;
          /*  case R.id.setting:
                break;*/
            default:
                break;
        }
        return true;
    }
}
