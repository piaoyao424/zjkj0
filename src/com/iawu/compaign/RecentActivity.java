package com.iawu.compaign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.iawu.xq.BaseActivity;
import com.iawu.xq.R;
import com.iawu.network.NetSceneBase;
import com.iawu.network.OnSceneCallBack;

public class RecentActivity extends BaseActivity implements OnScrollListener{
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recent_activity);
		setCurrentTitle("近期活动");
		setBackKeyListner(backListener);
		setRefreshKeyListner(refreshListener);
		
		init();
	}
	
	RecentAdapter recAdapter;
	public void init(){
		ShowProgress("读取数据中", "请稍候……");
		ActivtiyItemScene scene=new ActivtiyItemScene();
		scene.doScene(this.mainCallBack,"1");
		
		list=(ListView)findViewById(R.id.recent_activity_list);
		list.setOnItemClickListener(itemClickListener);
		recAdapter=new RecentAdapter(this);
		list.setAdapter(recAdapter);
	}
	OnItemClickListener itemClickListener=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> view, View arg1, int position,
				long arg3) {
			ActivityItem item=(ActivityItem)recAdapter.getItem(position);
			Intent intent = new Intent(RecentActivity.this, DetailActivity.class);
			intent.putExtra("id", item.id);
			startActivity(intent);
		}
	};

	OnSceneCallBack mainCallBack=new OnSceneCallBack() {
		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			ActivityItems items=(ActivityItems)data;
			if (page == 1){
				recAdapter.setItem(items);
			}
			else
				recAdapter.addItem(items);
		}
		
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			// TODO Auto-generated method stub
			HideProgress();
			ErrorAlert(status,info);
		}
	};
	
	int page=1;
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(firstVisibleItem+visibleItemCount==totalItemCount){
			page++;
			ActivtiyItemScene mainScene=new ActivtiyItemScene();
			mainScene.doScene(this.mainCallBack,String.valueOf(page));
		}
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
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
			ActivtiyItemScene scene=new ActivtiyItemScene();
			scene.doScene(RecentActivity.this.mainCallBack,""+(page = 1));
		}
	};
}
