package com.iawu.uikit;

import java.util.HashMap;
import java.util.Map;

import com.iawu.ui.view.BaseView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ViewContainer extends FrameLayout {

	private IViewFactory mViewFactory = null;
	public Map<Integer, View> viewMap = null;
	private View currentView = null;
	private int cuIndex = 0;

	public ViewContainer(Context context) {
		super(context);
		Init();
	}

	public ViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init();
	}

	public ViewContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Init();
	}

	@SuppressLint("UseSparseArrays")
	private void Init() {
		viewMap = new HashMap<Integer, View>();
		cuIndex = -1;
		currentView = null;
	}

	public void SetViewFactory(IViewFactory viewFactory) {
		mViewFactory = viewFactory;
	}

	public View getCurrentView() {
		return currentView;
	}

	public void FlipToView(int index) {
		// 已经是当前页面了
		if (index == cuIndex)
			return;

		cuIndex = index;

		// 把已有view进行移出，优化效率
		// this.removeAllViewsInLayout();
		// 已经生成过view,直接

		if (viewMap.containsKey(index)) {
			View v = viewMap.get(index);

			if (currentView != null) {
				BaseView baseview = (BaseView) currentView.getTag();
				baseview.GetView().setVisibility(View.INVISIBLE);
				baseview.OnViewHide();
			}

			BaseView cubaseview = (BaseView) v.getTag();
			cubaseview.GetView().setVisibility(View.VISIBLE);
			cubaseview.OnViewShow();

			currentView = v;
			return;
		}

		// 让工厂去生成view
		View v = mViewFactory.CreateView(index);
		viewMap.put(index, v);
		LayoutParams newLayout = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(v, newLayout);

		if (currentView != null) {
			BaseView baseview = (BaseView) currentView.getTag();
			baseview.GetView().setVisibility(View.INVISIBLE);
			baseview.OnViewHide();
		}

		BaseView cubaseview = (BaseView) v.getTag();
		cubaseview.GetView().setVisibility(View.VISIBLE);
		cubaseview.OnViewShow();

		currentView = v;
	}
}
