package com.iawu.xq;

import com.iawu.xq.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadFooterBar {
	View v;
	ListMoreListener listener;
	TextView infoText;
	ProgressBar loading_bar;
	Context context;
	
	public LoadFooterBar(Context context,ListMoreListener listener)
	{
		v=LayoutInflater.from(context).inflate(R.layout.load_foot, null);
		infoText=(TextView) v.findViewById(R.id.load_info_text);
		loading_bar=(ProgressBar) v.findViewById(R.id.loading_bar);
		this.listener=listener;
		ShowNoMore();
	}
	
	//显示点击更多
	public void ShowMore()
	{
		v.setOnClickListener(vListener);
		v.setVisibility(View.VISIBLE);
		loading_bar.setVisibility(View.GONE);
		infoText.setText("点击显示更多");
	}
	
	boolean isLoading = false;
	public void ShowLoading()
	{
		v.setOnClickListener(null);
		v.setVisibility(View.VISIBLE);
		loading_bar.setVisibility(View.VISIBLE);
		infoText.setText("加载数据中…");
	
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int i = 0;
				isLoading = true;
				while (isLoading && i<50){
					try {
						Thread.sleep(200);
						++i;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (50 == i)
					loadingHandler.sendEmptyMessage(i);
			}
		}).start();*/
	}
	
	Handler loadingHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			isLoading = false;
			
			loading_bar.setVisibility(View.GONE);
			infoText.setText("网络不通畅，请检查网络再试！");
			
			/*postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ShowMore();
				}
			}, 2000);*/
		}
	};
	
	//没有更多
	public void ShowNoMore()
	{
		isLoading = false;
		v.setVisibility(View.GONE);
	}

	public View GetView()
	{
		return v;
	}
	
	OnClickListener vListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(listener!=null)
			{
				listener.ClickMore();
			}
		}
	};
	
	public void setListner(ListMoreListener l){
		listener = l;
	}
}
