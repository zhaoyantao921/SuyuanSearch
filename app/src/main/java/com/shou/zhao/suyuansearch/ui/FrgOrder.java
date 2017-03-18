package com.shou.zhao.suyuansearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shou.zhao.suyuansearch.R;
import com.shou.zhao.suyuansearch.dao.Type;
import com.shou.zhao.suyuansearch.makeorder.ActivityTypeList;
import com.shou.zhao.suyuansearch.net.GetRypeList;

import java.io.Serializable;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by zhao on 2017/3/10.
 */

public class FrgOrder extends Fragment {

    //private static final String TAG = FrgOrder.class.getName();

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.main_order, null);
        view.findViewById(R.id.enter_buy_system).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("商店信息更新中");
                sweetAlertDialog.show();
                sweetAlertDialog.setCancelable(true);
                /*这里要获取养殖种类列表*/
                new GetRypeList(new GetRypeList.SuccessCallback() {
                    @Override
                    public void onSuccess(List<Type> typeList) {
                        Log.d("种类JSON",typeList.toString());
                        sweetAlertDialog.setTitleText("更新成功").setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.dismiss();
                        //需要把参数传过去
                        Intent intent=new Intent(getActivity(), ActivityTypeList.class);
                        intent.putExtra("typelist", (Serializable) typeList);
                        startActivity(intent);
                    }
                }, new GetRypeList.FailCallback() {
                    @Override
                    public void onFail() {
                        sweetAlertDialog.setTitleText("更新失败").setConfirmText("ERROR").
                                changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}

