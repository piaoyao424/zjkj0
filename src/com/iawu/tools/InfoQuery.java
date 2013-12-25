package com.iawu.tools;

import java.util.List;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.iawu.xq.iawuAPP;

 

public class InfoQuery {
	private final static String TAG = "InfoQuery";
	
   

	 //判断网络是否可用，目前对3G和WIFI可用。后续版本若需对网络细化，直接使用checkNetConnectType接口
	 public static Boolean checkNetWorkUsable(Context contextW)
	 {
		 boolean ret = false;
		 NetWorkConnectType netType = checkNetConnectType(contextW);
	     
		 if(NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_WIFI == netType)
		 {
			 ret = true;
		 }
		 else if(NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_3G == netType)
		 {
			 ret = true;			 		 
		 }
		 else if(NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_2G == netType )
		 {
			 ret = true;
		 }
		 
		 return ret;
	 }
	//当前是否3G/2G连接
	 public static Boolean checkIs3Gand2G(Context contextW)
	 {
		 boolean ret = false;
		 		 
		 NetWorkConnectType netType = checkNetConnectType(contextW);
	     
		 if(NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_2G == netType||NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_3G == netType)
		 {
			 ret = true;
		 }
		 return ret;
	 }
	 
	 public static Boolean checkIs2G()
	 {
		 boolean ret = false;
		 Context contextW=iawuAPP.getInstance();
		 NetWorkConnectType netType = checkNetConnectType(contextW);
		 
		 if(NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_2G == netType)
		 {
			 ret = true;
		 }
		 return ret;
	 }
	 
 
	 
	 public enum NetWorkConnectType {    
		 CURRENT_NETWORK_CONNECTION_TYPE_NONETWORK,
		 CURRENT_NETWORK_CONNECTION_TYPE_WIFI,
		 CURRENT_NETWORK_CONNECTION_TYPE_2G,
		 CURRENT_NETWORK_CONNECTION_TYPE_3G,
	 }

	  
	 static public int checkNetConnectTypeInt(Context contextW)
	 {
		 NetWorkConnectType type=checkNetConnectType(contextW);
		 if(type==NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_NONETWORK)
		 {
			 return -1;
		 }else if(type==NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_WIFI)
		 {
			 return 1;
		 }else if(type==NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_2G)
		 {
			 return 2;
		 }else if(type==NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_3G)
		 {
			 return 3;
		 }
		 return -1;
	 }
	 //返回值1(wifi),2(2g),3(3g),-1(未联网)
	 static public NetWorkConnectType checkNetConnectType(Context contextW)
	 {
		 try
		 {
			 
		 ConnectivityManager conMan = (ConnectivityManager) contextW.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo activeNetInfo = conMan.getActiveNetworkInfo();
		 if(activeNetInfo == null)//无物理网络连接
			 return NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_NONETWORK;
		 
		 NetworkInfo wifiInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		 
	     if(wifiInfo != null && wifiInfo.getState() == State.CONNECTED)
	    	 return NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_WIFI;
		 
	     //if(activeNetInfo.isRoaming())//漫游的3G卡禁止使用，避免产生天价漫游费
	    //	 return NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_NONETWORK;
		 
	     int netType = activeNetInfo.getSubtype(); 
		 // 电信的3g使用NETWORK_TYPE_EVDO_*,联通NETWORK_TYPE_UMTS或者HSDPA
		 if(netType == TelephonyManager.NETWORK_TYPE_UMTS ||
				 netType == TelephonyManager.NETWORK_TYPE_EVDO_0 ||
				 netType == TelephonyManager.NETWORK_TYPE_EVDO_A ||
				// netType == TelephonyManager.NETWORK_TYPE_EVDO_B ||
				 netType == TelephonyManager.NETWORK_TYPE_HSDPA  ||
				 netType == TelephonyManager.NETWORK_TYPE_HSPA   ||
				 netType == TelephonyManager.NETWORK_TYPE_HSUPA
				 ){
			 return NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_3G;
		 }
		 
		 //NETWORK_TYPE_1xRTT：CDMA2000 1xRTT最高153.6kbps，尽管获得3G技术的官方资格，但是通常被认为是2.5G或者 2.75G
		 //NETWORK_TYPE_CDMA:IS95A or IS95B 14.4kbps and 76.8kbps
		 //NETWORK_TYPE_EDGE:EDGE 2.5G
		 //NETWORK_TYPE_GPRS:2.5G
		 if(netType == TelephonyManager.NETWORK_TYPE_1xRTT ||
				 netType == TelephonyManager.NETWORK_TYPE_CDMA ||
				 netType == TelephonyManager.NETWORK_TYPE_EDGE ||
				 netType == TelephonyManager.NETWORK_TYPE_GPRS
				 ){
		 return NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_2G;
		 }		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 //剩下其他网络和未知网络，默认不许使用
		 return NetWorkConnectType.CURRENT_NETWORK_CONNECTION_TYPE_NONETWORK;
	 }
	 
	 
 
	 
	 public String getPhoneNum(ContextWrapper context)
	 {
		 TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	     return tm.getLine1Number();
	 }
	 public String getDeviceId(ContextWrapper context){
		 TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	     
		 String deviceid = tm.getDeviceId();
		// String tel = tm.getLine1Number();
		 String imei = tm.getSimSerialNumber();
		 String imsi = tm.getSubscriberId();
		 
		 if(deviceid!=null)
			 return deviceid;
		 if(imei!=null)
			 return imei;
		 if(imsi!=null)
			 return imsi;
		 return ""+System.currentTimeMillis();
		 
	 }
	 
	 public String getDeviceGUID(ContextWrapper context){
		 String deviceid="";
		 TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			 if(null != tm)
				 deviceid = tm.getDeviceId()+ tm.getSubscriberId();
			 else
				 deviceid = " "+System.currentTimeMillis();
		 return deviceid;
	 }
	 
		 
	 
	 
	//wifi是否连接
	 static public boolean GetIsWifiAvailable(Context context)
	 {
		if(null == context)
			return false;
		WifiManager wifi;
		wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if ( wifi.isWifiEnabled() )
		{
			wifi.startScan();
			List<ScanResult> tmpList = wifi.getScanResults();
			if ( 0 < tmpList.size())
			{
				return true;
			}
		}
		return false;
	 }

	//wifi是否连接
	 static public boolean GetIsWifiOpened(Context context)
	 {
		if(null == context)
			return false;
		WifiManager wifi;
		wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		return wifi.isWifiEnabled();
	 }
	 
	 static public boolean GetIsConnected(Context context)
	 {
		 if ( null == context )
		 {
			 return false;
		 }
		 ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 if (connectivity == null) 
		 {
		 	return false;
		 } 
		 else 
		 {
			 NetworkInfo info = connectivity.getActiveNetworkInfo();
			 if(info == null)
			 {
				 return false;
			 }
			 else
			 {
				 if(info.isAvailable())
				 {
					 return true;
		         }		         
			 }
		        return false;
		    }
	 }
}
