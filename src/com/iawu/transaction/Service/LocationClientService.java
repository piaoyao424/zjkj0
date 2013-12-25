package com.iawu.transaction.Service;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.iawu.xq.iawuAPP;

public class LocationClientService {
	private BMapManager BDMapManager = null;
	//定位客户端 用来接收百度服务器那边回传回来的坐标
	private LocationClient client = null;
	//百度定位的监听接口 上面的client会调用的
	private BDLocationListener locationListener = null;
	//定位采用的参数
	private LocationClientOption bMapClientOption = null;
	//缓存的坐标信息
	private BDLocation location = null;
	// 间隔时间
	private final int deltaTime = 4 * 60 * 1000;

	private static LocationClientService instance = new LocationClientService();

	public static LocationClientService getInstance() {
		return instance;
	}

	private LocationClientService() {
		//初始化，传一个context进去，最好是不会被释放掉的application
		BDMapManager = new BMapManager(iawuAPP.getInstance());
		//init第一个参数要填申请的api key
		BDMapManager.init("54199DF0BEA75A3602418FD08FBB2310131E5612", null);
		
		client = new LocationClient(iawuAPP.getInstance());
		locationListener = new BDLocationListener() {

			@Override
			public void onReceivePoi(BDLocation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onReceiveLocation(BDLocation arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					// ClassTools.getInstance().put(ClassTools.locateInfo,
					// arg0);
					location = arg0;
				}
			}
		};

		bMapClientOption = new LocationClientOption();
		bMapClientOption.setOpenGps(true);//打开gps开关
		bMapClientOption.setPriority(LocationClientOption.GpsFirst);//优先打开gps
		bMapClientOption.setCoorType("bd09ll");// 返回的定位结果是bd09ll,默认值gcj02
		bMapClientOption.setScanSpan(deltaTime);// 设置发起定位请求的间隔时间为4000ms
		bMapClientOption.disableCache(false);// 禁止启用缓存定位
		client.setLocOption(bMapClientOption);

		client.registerLocationListener(locationListener);
	}

	public BDLocation getLocation() {
		return location;
	}

	public BMapManager getMapManager(){
		return BDMapManager;
	}
	public void start() {
		client.start();
	}

	public void stop() {
		client.stop();
	}

	public void requestLocation() {
		client.requestLocation();
	}

	public boolean isStarted() {
		return client.isStarted();
	}

	Thread update = null;

	public void startUpdateLocation() {
		if (update == null || !update.isAlive()) {
			update = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						while (true) {
							if (!client.isStarted())
								client.start();
							
//							CallTaxiManager.getInstance().UpdatePosition(location.getLongitude(),
//									location.getLatitude());

							Thread.sleep(deltaTime*2);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public void stopUpdateLocation(){
		if (update != null && update.isAlive())
			update.interrupt();
			
		update = null;
	}
}
