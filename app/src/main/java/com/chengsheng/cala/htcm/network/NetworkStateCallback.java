package com.chengsheng.cala.htcm.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

public class NetworkStateCallback extends ConnectivityManager.NetworkCallback {

    private Context context;

    public NetworkStateCallback(Context context){
        super();
        this.context = context;
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Toast.makeText(context,"当前无网络！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        Toast.makeText(context,"当前网络不可用！",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }
}
