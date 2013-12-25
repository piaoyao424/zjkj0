package com.iawu.compaign;

import org.json.JSONObject;

import android.util.Log;

import com.iawu.model.BaseJsonItem;
import com.iawu.tools.CommonConvert;

public class DetailItem extends BaseJsonItem {
	String title,begin,end,content;
	int state;
	@Override
	public boolean CreateFromJson(JSONObject result) {
		try{
			status=result.getInt("STATUS");
			info=result.getString("INFO");
			if(status==1){
				CommonConvert convert=new CommonConvert(result);
				this.title=convert.getString("TITLE");
				this.begin=convert.getString("BEGIN");
				this.end=convert.getString("END");
				this.content=convert.getString("CONTENT");
				this.state=convert.getInt("STATE");
			}
		}catch(Exception e){
			status=-1;
			Log.d("mytag",info);
			return false;
		}
		return true;
	}
}
