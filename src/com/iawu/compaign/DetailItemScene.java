package com.iawu.compaign;

import android.util.Log;

import com.iawu.model.BaseJsonItem;
import com.iawu.network.NomalJsonSceneBase;
import com.iawu.network.OnSceneCallBack;
import com.iawu.network.ThreadPoolUtils;
import com.iawu.network.UrlFactory;

public class DetailItemScene extends NomalJsonSceneBase{
	@Override
	protected BaseJsonItem CreateJsonItems() {
		// TODO Auto-generated method stub
		return new DetailItem();
	}
	public void doScene(OnSceneCallBack oncallBack,String id){
		SetCallBack(oncallBack);
		targetUrl=UrlFactory.GetUrlNew
				("DiscountActivity","getActivityDetail","acnum",id);
		Log.d("mytag",targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
