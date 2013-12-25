package com.iawu.ui.view;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseView {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected Activity context;

	protected View view;

	public BaseView(Activity context) {
		this.context = context;
		view = LayoutInflater.from(context).inflate(GetLayoutId(), null);
		view.setTag(this);
	}

	public BaseView() {
	}

	public abstract int GetLayoutId();

	/**
	 * 得到真实的view
	 * 
	 * @return
	 */
	public View GetView() {
		return view;
	}

	/**
	 * 进入view时触发
	 */
	public abstract void OnViewShow();

	/**
	 * 退出view时触发
	 */
	public abstract void OnViewHide();

	public void Backward() {
	};

	public void ReFresh() {};

	String title;

	public void SetTitle(String title) {
		if (title == null)
			this.title = "";
		else
			this.title = title;
	}

	public String GetTitle() {
		if (title == null)
			title = "";
		return title;
	}
}
