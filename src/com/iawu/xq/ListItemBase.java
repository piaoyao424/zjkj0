package com.iawu.xq;

import android.view.View;

public abstract class ListItemBase {
	protected int layoutId;
	/**
	 * 对传递进来的view进行布局，该view由getItemLayout()在外部生成
	 * 
	 * @param view
	 */
	public abstract void initView(View view);

	/**
	 * 返回布局文件
	 * 
	 * @return
	 */
	public int getItemLayout(){
		return layoutId;
	}
}
