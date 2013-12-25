package com.iawu.xq;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iawu.xq.MainActivity;
import com.iawu.xq.R;
import com.iawu.uikit.IViewFactory;

public class MainTabBar implements IViewFactory {
	MainActivity root;

	final static int viewNum = 4;
	int mCurFocus = -1;
	int lastIndex = -1;

	LinearLayout[] linears = new LinearLayout[viewNum];
	TextView[] texts = new TextView[viewNum];
	ImageView[] images = new ImageView[viewNum];
	int[] texts_id = new int[] { R.id.tab_textview_home,
			R.id.tab_textview_calltaxi, R.id.tab_textview_goal,
			R.id.tab_textview_setting };
	int[] images_id = new int[] { R.id.tab_image_home, R.id.tab_image_calltaxi,
			R.id.tab_image_goal, R.id.tab_image_setting };
	int[] linears_id = new int[] { R.id.tab_linear_home,
			R.id.tab_linear_calltaxi, R.id.tab_linear_goal,
			R.id.tab_linear_setting };

	public MainTabBar(MainActivity root) {
		this.root = root;
		initTab();
	}

	private void initTab() {
		for (int i = 0; i < viewNum; i++) {
			linears[i] = (LinearLayout) root.findViewById(linears_id[i]);
			linears[i].setOnClickListener(listener);
			images[i] = (ImageView) root.findViewById(images_id[i]);
			texts[i] = (TextView) root.findViewById(texts_id[i]);
		}
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			for (int i = 0; i < viewNum; i++)
				if (v.getId() == linears_id[i]) {
					switchTab(i);
				}
		}
	};

	public void switchTab(int index) {
		setTab(index);
		root.GoToView(index);
		if (index == 2){}
		//	((GoalView)root.getCurrentBaseView()).FlipToView(1);
	}

	int[] icon_normal_id = { R.drawable.home_tab_home_normal,
			R.drawable.home_tab_call_normal, R.drawable.home_tab_grade_normal,
			R.drawable.home_tab_cog_normal };

	int[] icon_selected_id = { R.drawable.home_tab_home_selected,
			R.drawable.home_tab_call_selected,
			R.drawable.home_tab_grade_selected,
			R.drawable.home_tab_cog_selected };

	public void setTab(int index) {
		if (index == mCurFocus)
			return;
		lastIndex = mCurFocus;
		mCurFocus = index;
		Resources r = root.getResources();
		if (lastIndex >= 0)
			initTabDraw(lastIndex);

		images[index].setImageResource(icon_normal_id[index]);

		texts[index].setTextColor(r.getColor(R.color.white));
		linears[index]
				.setBackgroundResource(R.drawable.main_tabbar_selected_bg);
	}

	private void initTabDraw(int index) {
		Resources r = root.getResources();
		images[index].setImageResource(icon_selected_id[index]);
		texts[index].setTextColor(r.getColor(R.color.tab_gray));
		linears[index].setBackgroundResource(0);
	}

	public void tabReset(int index) {
		if (mCurFocus >= 0) {
			initTabDraw(mCurFocus);
			mCurFocus = -1;
			lastIndex = -1;
		}
	}

	@Override
	public View CreateView(int index) {
		// TODO Auto-generated method stub
		View curView = null;
		switch (index) {
		case 0:
			//curView = new HomeView(root).GetView();
			break;
		case 1:
			//curView = new HomeView(root).GetView();
			break;
		case 2:
			//curView = new GoalView(root).GetView();
			break;
		case 3:
			break;

		default:
			break;
		}
		return curView;
	}

}
