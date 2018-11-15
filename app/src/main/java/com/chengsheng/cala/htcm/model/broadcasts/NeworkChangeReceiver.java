package com.chengsheng.cala.htcm.model.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.chengsheng.cala.htcm.network.NetWorkUtils;

public class NeworkChangeReceiver extends BroadcastReceiver {
    private int netState;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ConnectivityManager.EXTRA_NO_CONNECTIVITY)){
            netState = NetWorkUtils.getNetworkState(context);
        }

        Toast.makeText(context,"当前网络状态"+netState,Toast.LENGTH_SHORT).show();

    }
}
