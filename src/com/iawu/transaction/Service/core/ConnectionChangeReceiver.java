package com.iawu.transaction.Service.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionChangeReceiver extends BroadcastReceiver {   
    @Override   
    public void onReceive( Context context, Intent intent ) {   
    	String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d("ConnectionChangeReceiver", "netChanged");
            ConnectivityManager connectivityManager = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo   info = connectivityManager.getActiveNetworkInfo();  
            if(info != null && info.isAvailable()) {
                String name = info.getTypeName();
                Log.d("ConnectionChangeReceiver", "currentnet：" + name);
                 
            } else {
            	 
                Log.d("ConnectionChangeReceiver", "no network");
            }
        } 
      }   
}  