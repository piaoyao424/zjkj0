package com.iawu.xq;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

import com.iawu.tools.Log;
import com.umeng.analytics.MobclickAgent;

public class CrashReportHandler implements UncaughtExceptionHandler {

	public static void attach(Context context) {
		Thread.setDefaultUncaughtExceptionHandler(
			new CrashReportHandler(context)
		);
	}
	
	///////////////////////////////////////////// implementation
	
	private CrashReportHandler(Context context) 
	{
		m_context=context;
	}
	
	@Override
	public void uncaughtException(Thread thread,Throwable exception) {
		boolean isDebug =Log.IsDebug();
		 
		StringWriter stackTrace=new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		if(isDebug)
		{
			//	Intent intent=new Intent(m_context,CrashReportActivity.class);
			//	intent.putExtra(CrashReportActivity.EXTRA_STACKTRACE,stackTrace.toString());
			//	m_context.startActivity(intent);
		}
		 
		MobclickAgent.reportError(m_context,"crashhandler:"+stackTrace.toString());
		//MobclickAgent.reportError(m_context,"mytest");
		Log.e("CrashReportHandler", stackTrace.toString());
		 
		//Process.killProcess(Process.);
		System.exit(10);
	}

	private Context m_context;
}
