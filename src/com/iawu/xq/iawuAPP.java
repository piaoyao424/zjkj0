package com.iawu.xq;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import com.iawu.msgcenter.MsgCenter;
import com.iawu.tools.Log;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;

public class iawuAPP extends Application {
	static iawuAPP mainApp = null;
	public static ArrayList<Activity> activityList = null;
	String pName = "com.btten.xq";
	
	public iawuAPP() {
		mainApp = this;
		MsgCenter.getInstance();
		activityList = new ArrayList<Activity>();
		Log.setOutputPath(Log.SDCARD_LOG_PATH, Log.SYS_LOG, 0);
	}

	public static iawuAPP getInstance() {
		return mainApp;
	}

	public int GetVersionCode() {
		int versionCode = 0;
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName,
					PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
		} catch (Exception e) {
		}
		return versionCode;
	}

	public String GetVersion() {
		
		String versionName = null;
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName,
					PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
		} catch (Exception e) {
		}
		return versionName;
	}

	public String GetVersionStr() {
		return "" + GetVersionCode();
	}

	public String GetAnonymousName() {
		return "未命名";
	}

	public int GetDpType() {
		Activity top = GetTopActivity();
		if (top == null)
			return 1;

		DisplayMetrics dm = new DisplayMetrics();
		top.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		screenWidth /= 3;
		if (screenWidth > 200)
			return 2;
		if (screenWidth > 150)
			return 1;
		return 0;
	}

	// 是否要强制升�?
	public boolean IsNeedForceUpdate(Context context) {
		String min_verson_code = MobclickAgent.getConfigParams(context,
				"min_verson_code");
		// 没有得到配置
		if (min_verson_code == "" || min_verson_code.length() <= 0)
			return false;
		int cuVersonCode = GetVersionCode();
		// 得不到当前配�?
		if (cuVersonCode <= 0)
			return false;
		int i_min_verson_code = 0;
		try {
			i_min_verson_code = Integer.parseInt(min_verson_code);
		} catch (Exception e) {
			// 得到远程配置出错
			return false;
		}
		// 比小�?��版本还要小，去死吧！，强制更�?
		if (cuVersonCode < i_min_verson_code) {
			return true;
		}
		return false;

	}

	// 上报错误
	public static void ReportError(String error) {
		Activity topactivity = GetTopActivity();
		if (topactivity == null)
			return;
		MobclickAgent.reportError(topactivity, error);
	}

	// 服务器是否在维护
	public String GetRepairInfo(Context context) {
		String Str_is_in_repair = MobclickAgent.getConfigParams(context,
				"in_repair_info");
		if (Str_is_in_repair != null
				&& Str_is_in_repair.equalsIgnoreCase("off"))
			Str_is_in_repair = null;

		if (Str_is_in_repair != null && Str_is_in_repair.length() > 0) {
			RepairInfo info = new RepairInfo();
			if (!info.Parase(Str_is_in_repair)) {
				return null;
			}
			// 不在维修时间
			if (!info.IsInTime()) {
				return null;
			}
			return info.info;

		}

		return Str_is_in_repair;
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate() {
		super.onCreate();
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "xq/Cache");

		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPoolSize(4)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// .memoryCacheSize(20000000) // 20 Mb
				.memoryCache(new FIFOLimitedMemoryCache(5 * 1024 * 1024))
				.discCache(
						new TotalSizeLimitedDiscCache(cacheDir,
								new Md5FileNameGenerator(), 20 * 1024 * 1024))
				// 能缓�?0M文件
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator());

		if (Log.IsDebug()) {
			builder.enableLogging();
		}

		ImageLoaderConfiguration config = builder.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

	}

	private class RepairInfo {
		Date startTime;
		Date endTime;
		String info;

		public RepairInfo() {

		}

		/*
		 * <repair><starttime>2012-01-09 02:03:04</starttime> endtime>2012-01-09
		 * 02:03:04</endtime> <info>维修信息</info> </repair>
		 */
		public Boolean Parase(String repairInfo) {
			final Map<String, String> values = com.iawu.tools.Util.parseXml(
					repairInfo, "repair", null);
			if (values == null)
				return false;
			String strStartTime = values.get(".repair.starttime");
			String strEndTime = values.get(".repair.endtime");
			info = values.get(".repair.info");
			if (info == null || info.length() <= 0)
				return false;

			startTime = getTime(strStartTime);
			endTime = getTime(strEndTime);

			if (startTime == null || endTime == null)
				return false;
			return true;
		}

		// 是否在维�?
		public Boolean IsInTime() {
			Date now = new Date();
			if (startTime == null || endTime == null)
				return false;

			if (now.getTime() >= startTime.getTime()
					&& now.getTime() <= endTime.getTime())
				return true;
			return false;
		}

		@SuppressLint("SimpleDateFormat")
		private Date getTime(String str) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = format.parse(str);
			} catch (Exception e) {
				return null;
			}
			return date;
		}
	}

	public static String GetSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}
	


	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public void exit() {
		for (int j = 0; j < activityList.size(); j++) {
			activityList.get(j).finish();
		}
		System.exit(0);
	}
	
	// 得到顶部分的Activity
	public static  Activity GetTopActivity() {
		if (activityList.size() <= 0)
			return null;
		return activityList.get(activityList.size() - 1);
	}
	
	/**
	 * 清空页面缓存，保留当前activity
	 */
	public  void ClearOtherActivity(){
		if (activityList.size() <= 1)
			return;
		for (int i=0; i < activityList.size()-1; i++)
			activityList.get(i).finish();
	}
	
}
