package com.shou.zhao.suyuansearch.makeorder;

import com.shou.zhao.suyuansearch.net.Config;
import com.shou.zhao.suyuansearch.net.HttpMethod;
import com.shou.zhao.suyuansearch.net.NetConnection;

/**
 * Created by zhao on 2017/3/13.
 * ordersum
 * customer
 * orderadress
 * orderphome
 * ordertypeid
 * allprice
 *
 *
 * http://202.121.66.53:8080/addNewOrderApi?ordersum=2&customer=%E8%B5%B5%E5%BD%A6%E6%B6%9B&orderadress=%E6%B5%B7%E6%B4%8B%E5%A4%A7%E5%AD%A6&orderphome=15001859306&ordertypeid=1&allprice=1000
 */

class PushOrder {
    public PushOrder(final String ordersum,
                     final String customer,
                     final String orderadress,
                     final String orderphome,
                     final String ordertypeid,
                     final String allprice,
                     final SuccessCallback successCallback,
                     final FailCallback failCallback) {

        new NetConnection(Config.PULL_ORDER, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if(result.isEmpty()) {
                   if (failCallback!=null){
                       failCallback.onFail();
                   }
                }else {
                    if (successCallback!=null)
                    successCallback.onSuccess(result);
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

                if(failCallback!=null){
                    failCallback.onFail();
                }
            }
        },"ordersum",ordersum,"customer",customer,"orderadress",orderadress,"orderphome",orderphome,"ordertypeid",ordertypeid,"allprice",allprice);

    }
    public  static interface SuccessCallback{
        void onSuccess(String result);
    }
    public static interface FailCallback{
        void onFail();
    }

}
