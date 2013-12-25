package com.iawu.compaign;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.iawu.xq.BaseActivity;
import com.iawu.xq.R;
import com.iawu.network.NetSceneBase;
import com.iawu.network.OnSceneCallBack;

public class DetailActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		setBackKeyListner(backListener);
		setRefreshKeyListner(refreshListener);
		init();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ShowProgress("读取数据中", "请稍候……");
		String id = getIntent().getStringExtra("id");
		DetailItemScene detailScene=new DetailItemScene();
		detailScene.doScene(detailCallBack, id);
	}
	
	public void OnViewShow() {
		
	};
	TextView title, time, end, content;
	public void init(){
	    title=(TextView)findViewById(R.id.activity_detail_title);
	    time=(TextView)findViewById(R.id.activity_detail_time);
	    end=(TextView)findViewById(R.id.activity_detail_end);
	    content=(TextView)findViewById(R.id.activity_detail_content);
	}
	
	OnClickListener backListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};
	
	OnClickListener refreshListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShowProgress("读取数据中", "请稍候……");
			String id = getIntent().getStringExtra("id");
			DetailItemScene detailScene=new DetailItemScene();
			detailScene.doScene(detailCallBack, id);
		}
	};
	
	OnSceneCallBack detailCallBack=new OnSceneCallBack() {
		
		@Override
		public void OnSuccess(Object data, NetSceneBase netScene) {
			HideProgress();
			
			DetailItem item=(DetailItem)data;
			
			title.setText(item.title);
		    time.setText(item.begin+"--"+item.end);
		    content.setText(item.content);
		    switch(item.state){
		    case 0://新建
		    	end.setVisibility(View.INVISIBLE);
		    	break;
		    case 1://活动生效
		    	end.setVisibility(View.INVISIBLE);
		    	break;
		    case 2://活动结束
		    	end.setVisibility(View.VISIBLE);		    	
		    	break;
		    case 3://活动作废
		    	end.setVisibility(View.INVISIBLE);
		    	break;
		    }
		}
		
		@Override
		public void OnFailed(int status, String info, NetSceneBase netScene) {
			// TODO Auto-generated method stub
			HideProgress();
			ErrorAlert(status,info);
		}
	};
}
