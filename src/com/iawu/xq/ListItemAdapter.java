package com.iawu.xq;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListItemAdapter extends BaseAdapter {
	protected ListItemBase[] items;
	protected LayoutInflater inflater;

	public ListItemAdapter(Activity context) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (items == null)
			return 0;
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(items[position].getItemLayout(), null);
		
		items[position].initView(convertView);
		
		return convertView;
	}
	
	public void setItems(ListItemBase[] items){
		this.items = items;
	}
	
	public void addMore(ListItemBase[] items){
		ListItemBase[] temp = new ListItemBase[this.items.length+items.length];
		System.arraycopy(this.items, 0, temp, 0, this.items.length);
		System.arraycopy(items, 0, temp, this.items.length, items.length);
		this.items = temp;
	}
}
