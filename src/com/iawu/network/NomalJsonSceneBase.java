package com.iawu.network;

import org.json.JSONObject;

import com.iawu.model.BaseJsonItem;
import com.iawu.tools.Log;

public abstract class NomalJsonSceneBase extends JsonSceneBase {
	public NomalJsonSceneBase() {
		super(false);
	}
	
	@Override
	protected void onFailed(int statusCode) {
		OnSceneCallBack callBack = GetCallBack();
		if(callBack!=null)
		{
			callBack.OnFailed(statusCode, "",this);
		}
		
		Log.d(this.getClass().getName(), "onFailed:" + statusCode);
	}

	@Override
	protected void onSuccess(JSONObject result) {
		
		OnSceneCallBack callBack=GetCallBack();
		if(callBack==null)
			return;

		Object obj=null;
		
		if(result!=null)
		{
			obj=ParaseJson(result,callBack);
		}else //连转成json都会失败
		{
			callBack.OnFailed(-1, "",this);
			return;
		}
		if(obj==null)
		{
			return;
		}
		callBack.OnSuccess(obj,this);
		Log.d(this.getClass().getName(), "onSuccess");
	}
	 
	protected Object ParaseJson(JSONObject result, OnSceneCallBack callBack) {
		BaseJsonItem items=CreateJsonItems();
		if(items.CreateFromJson(result) && items.status==1)
			return items;
		if(items!=null && items.status!=1)
		{
			//服务器返回错误
			if(items.info==null)
				items.info="";
			callBack.OnFailed(items.status, items.info,this);
			return null;
		}
		callBack.OnFailed(0, "",this);
		return null;
	}
	protected abstract BaseJsonItem CreateJsonItems();
}
