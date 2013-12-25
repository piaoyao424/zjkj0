package com.iawu.network;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.iawu.tools.Log;
import com.iawu.xq.iawuAPP;

public abstract class JsonSceneBase  extends NetSceneBase<JSONObject> {
	public JsonSceneBase(boolean isPost) {
		super(isPost);
		 
	}
	private static final String TAG = "JsonSceneBase";
	@Override
	protected JSONObject doReceive(HttpEntity httpEntity) {
		JSONObject result =null;
		byte[] bytes = null;
		String str="";
		try {
			bytes = EntityUtils.toByteArray(httpEntity);
			str=new String(bytes);
			result= new JSONObject(str);
		} catch (Exception e) {
			 Log.Exception(TAG, e);
			 iawuAPP.getInstance();
			iawuAPP.ReportError(TAG+"error:\n"+e.toString()+"\ntargetUrl:\n"+targetUrl+"\nstr:\n"+str);
		}
		return result;
	}
}
