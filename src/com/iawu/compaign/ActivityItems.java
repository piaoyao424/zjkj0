package com.iawu.compaign;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.iawu.model.BaseJsonItem;
import com.iawu.tools.CommonConvert;

public class ActivityItems extends BaseJsonItem {
	ArrayList<ActivityItem> items=null;
	@Override
	public boolean CreateFromJson(JSONObject result) {
		try{
			status=result.getInt("STATUS");
			info=result.getString("INFO");
			items=new ArrayList<ActivityItem>();
			if(status==1){
				JSONArray data=result.getJSONArray("DATA");
				for(int i=0;i<data.length();i++){
					JSONObject obj=data.getJSONObject(i);
					CommonConvert convert=new CommonConvert(obj);
					ActivityItem item=new ActivityItem();
					item.id=convert.getString("ACNUM");
					item.title=convert.getString("TITLE");
					item.begin=convert.getString("BEGIN");
					item.end=convert.getString("END");
					item.state=convert.getInt("STATE");
					items.add(item);
				}
			}
		}catch(Exception e){
			status=-1;
			Log.d("mytag",info);
			return false;
		}
		return true;
	}
}
