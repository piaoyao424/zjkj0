package com.iawu.xq;

import com.iawu.xq.R;
import com.iawu.xq.SplashScreen;
import com.iawu.network.NetSceneBase;
import com.iawu.network.OnSceneCallBack;
import com.iawu.transaction.Login.LoginActivity;
import com.iawu.transaction.Login.LoginScene;
import com.umeng.analytics.MobclickAgent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends BaseActivity{
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splashscreen);
	        
	        //自动报错
	        MobclickAgent.onError(this);
	        
	        //更新配置
	        MobclickAgent.updateOnlineConfig(this);
	        
	        DelayLoad();
	 }
	 
	 Intent intent = null;
	 private void DelayLoad()
	    {
	    	new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (IsFirstStart()){ 
						intent = new Intent(SplashScreen.this, WelcomActivity.class);
						startActivity(intent);
						finish();
						overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
					}
					else{
						if (AccountManager.getInstance().IsLogin()){
							SharedPreferences settings = getSharedPreferences("xqcfg", MODE_PRIVATE);
							String nameStr = settings.getString("nameStr", "");
							String pwdStr = settings.getString("pwdStr", "");
							
							(new LoginScene()).doScene(new OnSceneCallBack() {
								
								@Override
								public void OnSuccess(Object data, NetSceneBase netScene) {
									// TODO Auto-generated method stub
									intent = new Intent(SplashScreen.this, MainActivity.class);
									startActivity(intent);
									finish();
									overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
								}
								
								@Override
								public void OnFailed(int status, String info, NetSceneBase netScene) {
									// TODO Auto-generated method stub
									intent = new Intent(SplashScreen.this, LoginActivity.class);
									startActivity(intent);
									finish();
									overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
								}
							}, nameStr, pwdStr);
							
						}
						else{
							intent = new Intent(SplashScreen.this, LoginActivity.class);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
						}
					}
				}
			},1000);
	    }
	 
	private boolean IsFirstStart()
	{
		 boolean isfirststart = false;
		 
		 SharedPreferences settings = getSharedPreferences("xqcfg", MODE_PRIVATE);
		 isfirststart = settings.getBoolean("IsFirstStart", true);
		 
		 return isfirststart;
	}
}
