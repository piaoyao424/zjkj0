package com.iawu.transaction.Login;

import org.json.JSONObject;

import com.iawu.model.BaseJsonItem;
import com.iawu.tools.CommonConvert;
import com.iawu.tools.Log;
import com.iawu.transaction.Login.LoginResultItem;
import com.iawu.xq.iawuAPP;

public class LoginResultItems extends BaseJsonItem{
	private static String TAG="LoginResultItems";
	
	public LoginResultItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		// TODO Auto-generated method stub
		LoginResultItems items=this;
		item = new LoginResultItem();
		try {
			items.status = result.getInt("status");
			items.info = result.getString("info");
			// 有数据
			if (items.status == 1) {
				CommonConvert convert=new CommonConvert(result);
				item.username = convert.getString("username");
				item.userid = convert.getString("userid");
				item.status=convert.getInt("status");
			}
		} catch (Exception e) {
			items.status=-1;
			items.info=e.toString();
			iawuAPP.getInstance();
			iawuAPP.ReportError(TAG+"error:\n"+e.toString()+"\nresult:\n"+result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
