package com.iawu.transaction.Login;

import android.util.Log;

import com.iawu.model.BaseJsonItem;
import com.iawu.network.NomalJsonSceneBase;
import com.iawu.network.OnSceneCallBack;
import com.iawu.network.ThreadPoolUtils;
import com.iawu.network.UrlFactory;

public class LoginScene extends NomalJsonSceneBase{
	public static final String TAG="LoginScene";
	
	public LoginScene(){
		super();
	}
	
	public void doScene(OnSceneCallBack callBack,String name,String pwd){
		
		SetCallBack(callBack);
		
		targetUrl=UrlFactory.GetUrlOld(
				"DoLogin",
				"pwd",pwd,
				"name",name
				);
		ThreadPoolUtils.execute(this);
		Log.i(TAG,targetUrl);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		// TODO Auto-generated method stub
		return new LoginResultItems();
	}

}
