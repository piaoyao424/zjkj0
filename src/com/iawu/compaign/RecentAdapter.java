package com.iawu.compaign;

import java.util.ArrayList;

import com.iawu.xq.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentAdapter extends BaseAdapter{
	Activity context;
	View view;
	TextView title,textEnd;
	ImageView imageNew;
	ArrayList<ActivityItem> items;
	public RecentAdapter(Activity context) {
		this.context=context;
		items=new ArrayList<ActivityItem>();
	}
	@Override
	public int getCount() {
		return items.size();
	}
	
	public void setItem(ActivityItems items){
		if (items == null || items.items == null) return;
		
		this.items = items.items;
		this.notifyDataSetChanged();
	}
	
	public void addItem(ActivityItems listItems){
		for(int i=0;i<listItems.items.size();i++){
			ActivityItem item=new ActivityItem();
			item.begin=listItems.items.get(i).begin;
			item.end=listItems.items.get(i).end;
			item.id=listItems.items.get(i).id;
			item.state=listItems.items.get(i).state;
			item.title=listItems.items.get(i).title;
			items.add(item);
		}
		this.notifyDataSetChanged();
	}
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	ViewHolder holder;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(context).
					inflate(R.layout.recent_activity_list_item, null);
			holder=new ViewHolder();
			holder.num= (TextView) convertView.findViewById(R.id.recent_activity_listitem_num);
			holder.finished=(TextView)convertView.findViewById(R.id.recent_activity_text_end);
			holder.imgNew=(ImageView)convertView.findViewById(R.id.recent_activity_listitem_new);
			holder.begin=(TextView)convertView.findViewById(R.id.recent_activity_time_begin);
			holder.end=(TextView)convertView.findViewById(R.id.recent_activity_time_end);
			holder.title=(TextView)convertView.findViewById(R.id.recent_activity_listitem_title);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.num.setText((position+1)+"");
		holder.title.setText(items.get(position).title);
		holder.begin.setText(items.get(position).begin);
		holder.end.setText(items.get(position).end);
		switch(items.get(position).state){
		case 0://新活动
			holder.imgNew.setVisibility(View.VISIBLE);
			holder.finished.setVisibility(View.INVISIBLE);
			break;
		case 1://活动生效
			break;
		case 2://活动结束
			holder.imgNew.setVisibility(View.INVISIBLE);
			holder.finished.setVisibility(View.VISIBLE);
			break;
		case 3://活动作废
			break;
		}
		return convertView;
	}
	class ViewHolder{
		TextView title,finished,begin, end, num;
		ImageView imgNew;
	}
}
