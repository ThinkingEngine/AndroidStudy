package com.chengsheng.cala.htcm.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.chengsheng.cala.htcm.constant.GlobalConstant;

public class NetWorkUtils {


    public static int getNetworkState(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            if(networkInfo.getType() == (ConnectivityManager.TYPE_WIFI)){
                return GlobalConstant.NETWORK_WIFI;
            }else if(networkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)){
                return GlobalConstant.NETWORK_MOBILE;
            }
        }else{
            return GlobalConstant.NETWORK_NONE;
        }

        return GlobalConstant.NETWORK_NONE;
    }

}
