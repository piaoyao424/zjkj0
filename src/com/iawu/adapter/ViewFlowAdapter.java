package com.iawu.adapter;

import com.iawu.xq.R;
import com.iawu.transaction.Login.LoginActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class ViewFlowAdapter extends BaseAdapter {
	
	int[] imageids=new int[] {R.drawable.welcome_page1,
							  R.drawable.welcome_page2,
							  R.drawable.welcome_page3,
							  R.drawable.welcome_page4,
							  R.drawable.welcome_page5,
							  R.drawable.welcome_page6};
	
	Activity context;
	
	public Activity getContext()
	{
		return context;
	}
	
	public ViewFlowAdapter(Activity context)
	{
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageids.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ImageView image;

		if (position < (imageids.length - 1)) {
			if (convertView != null)
				image = (ImageView) convertView;
			else
				image = new ImageView(context);
			
			image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			image.setScaleType(ScaleType.CENTER_CROP);
			image.setImageResource(imageids[position]);
			
			return image;
		}
		else{
		Button button;
		RelativeLayout rLayout;
		rLayout = (RelativeLayout) LayoutInflater.from(context).inflate(
				R.layout.welcome_button, null);
		button = (Button) rLayout.findViewById(R.id.welcome_button);
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (IsFirstStart()){
				Intent intent = new Intent(context, LoginActivity.class);
				context.startActivity(intent);
				}
				context.finish();
			}
		});

		return rLayout;
		}
	}
	
	private boolean IsFirstStart()
	{
		 boolean isfirststart = false;
		 
		 SharedPreferences settings = context.getSharedPreferences("xqcfg",Context.MODE_PRIVATE);
		 isfirststart = settings.getBoolean("IsFirstStart", true);
		 
		 if (isfirststart)
			 settings.edit().putBoolean("IsFirstStart", false).commit();
		 
		 return isfirststart;
	 }
}
