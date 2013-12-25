package com.iawu.uikit;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Scroller;

public class BttenViewFlow extends AdapterView<BaseAdapter> {

	private int mCurScreen;
	private static final int SNAP_VELOCITY = 600;
	private Scroller scroller;
	private VelocityTracker tracker;
	private float mLastMotionX;
	private OnViewChangeListener onViewChangeListener;
	Context context;
	private AdapterDataSetObserver mDataSetObserver;

	public BttenViewFlow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init(context);
	}

	public BttenViewFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	public BttenViewFlow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		mCurScreen = 0;
		scroller = new Scroller(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		scrollTo(mCurScreen * width, 0);
	}

	public void setOnViewChangeListener(OnViewChangeListener changeListener) {
		this.onViewChangeListener = changeListener;
	}

	public void snapToScreen(int pos) {

		if (pos >= getChildCount()) {
			pos = getChildCount() - 1;
		}

		if (getScrollX() != (pos * getWidth())) {
			int destina = pos * getWidth() - getScrollX();

			scroller.startScroll(getScrollX(), 0, destina, 0);
			mCurScreen = pos;
			invalidate();

			if (onViewChangeListener != null) {
				onViewChangeListener.onViewChange(pos);
			}
		}

	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		}
	}

	static long lasttime, nowtime;

	private int deltalX = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		float x = event.getX();
		System.out.println("onTouchEvent--" + x);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lasttime = AnimationUtils.currentAnimationTimeMillis();
			if (tracker == null) {
				tracker = VelocityTracker.obtain();
				tracker.addMovement(event);
			}
			if (!scroller.isFinished())
				scroller.abortAnimation();
			mLastMotionX = x;

			break;

		case MotionEvent.ACTION_MOVE:
			deltalX = (int) (mLastMotionX - x);

			if (tracker != null) {
				tracker.addMovement(event);
			}
			mLastMotionX = x;
			if (mCurScreen <= (getChildCount() - 1))
				scrollBy(deltalX, 0);

			break;

		case MotionEvent.ACTION_UP:
			// 响应时间
			nowtime = AnimationUtils.currentAnimationTimeMillis();
			if (nowtime - lasttime > 120 || nowtime - lasttime <= 0) {
			} else {

//				click.onFlipperClick(mCurScreen);
//				break;

			}

			float velocityX = 0;
			if (tracker != null) {
				tracker.addMovement(event);
				tracker.computeCurrentVelocity(1000);
				velocityX = tracker.getXVelocity();
			}

			// 滑动足够多的距离才能确认为有效动作
			if (velocityX > SNAP_VELOCITY) {
				if (mCurScreen > 0) {
					snapToScreen(mCurScreen - 1);

				} else if (mCurScreen == 0) {
					// 从第一个跳到最后一个画面
					// snapToScreen(mAdapter.getCount() - 1);
					snapToScreen(0);
				}

			} else if (velocityX < -SNAP_VELOCITY) {
				if (mCurScreen < (getChildCount() - 1)) {
					snapToScreen(mCurScreen + 1);

				} else if (mCurScreen == (getChildCount() - 1)) {
					// 最后一个画面跳到第一个画面
					// snapToScreen(0);
					snapToScreen(mCurScreen);
				}
			} else {
				// 避免缓慢滑动到一半时同时显示两张图片
				if (getScrollX() > mCurScreen * getWidth() + 0.5 * getWidth()) {
					snapToScreen(mCurScreen + 1);
				} else {
					snapToScreen(mCurScreen);
				}
			}

			if (tracker != null) {
				tracker.recycle();
				tracker = null;
			}
			break;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private void snapToDestination() {
		int screenWidth = getWidth();

		int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);

	}

	private boolean canMove(int deltalX) {

		/*
		 * if(getScrollX() <= 0 && deltalX <0){ return false; }
		 * 
		 * if(getScrollX() >= (getChildCount()-1)*getWidth() && deltalX > 0){
		 * return false; } return true;
		 */
		if (getScrollX() >= 0
				&& getScrollX() <= (getChildCount() - 1) * getWidth()
				&& deltalX > 0)
			return true;

		return false;
	}

	BaseAdapter mAdapter;

	@Override
	public View getSelectedView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelection(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAdapter(BaseAdapter adapter) {
		// TODO Auto-generated method stub
		if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataSetObserver);
			removeAllViewsInLayout();
		} else {
			mAdapter = adapter;
			mDataSetObserver = new AdapterDataSetObserver();
			mAdapter.registerDataSetObserver(mDataSetObserver);
			for (int i = 0; i < mAdapter.getCount(); i++) {

				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
				addViewInLayout(mAdapter.getView(i, null, this), i, lp);
			}
			invalidate();
			requestLayout();
		}

	}

	Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			if (msg.what == start) {

				snapToScreen(mCurScreen + 1);

				sendMsg();
			}
		}

	};

	int intervalTime = 0;

	public void startFlip(int times) {
		intervalTime = times;
		handler.removeMessages(start);
		sendMsg();
	}

	int start = 0x1200;

	private void sendMsg() {
		Message msg = new Message();
		msg.what = start;
		handler.sendMessageDelayed(msg, intervalTime);

	}

	@Override
	public BaseAdapter getAdapter() {
		// TODO Auto-generated method stub
		return mAdapter;
	}

	class AdapterDataSetObserver extends DataSetObserver {

		@Override
		public void onChanged() {
			BttenViewFlow.this.removeAllViewsInLayout();

			// TODO Auto-generated method stub

			for (int i = 0; i < mAdapter.getCount(); i++) {
				LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT);
				BttenViewFlow.this.addViewInLayout(
						mAdapter.getView(i, null, BttenViewFlow.this), i, lp);
			}
			BttenViewFlow.this.invalidate();
			BttenViewFlow.this.requestLayout();
			// super.onChanged();
		}

		@Override
		public void onInvalidated() {
			// TODO Auto-generated method stub
			super.onInvalidated();
		}

	}

	OnFlipperClickListener click;

	public void setOnFlipperClickListener(OnFlipperClickListener click) {
		this.click = click;
	}

}
