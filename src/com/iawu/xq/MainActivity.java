package com.iawu.xq;

import com.iawu.xq.R;
import com.iawu.transaction.Service.CallTaxiNotification;
import com.iawu.ui.view.BaseView;
import com.iawu.uikit.ViewContainer;
import com.umeng.update.UmengUpdateAgent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {
	ViewContainer vContainer = null;
	public MainTabBar tabBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		setContentView(R.layout.main_activity);
		CallTaxiNotification.getInstance().LoginApp();
		init();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getCurrentBaseView().OnViewShow();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		getCurrentBaseView().OnViewHide();
		super.onPause();
	}

	public void init() {
		tabBar = new MainTabBar(this);
		vContainer = (ViewContainer) findViewById(R.id.home_center_container);
		vContainer.SetViewFactory(tabBar);
		tabBar.switchTab(0);
	}

	public void GoToView(int index) {
		vContainer.FlipToView(index);
		setCurrentTitle(getCurrentBaseView().GetTitle());
	}

	public BaseView getCurrentBaseView() {
		return (BaseView) vContainer.getCurrentView().getTag();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// tabBar.switchTab(0);
		finish();
	}

	public OnActivityResultListener baseListener;
	public OnActivityResultListener bankCardListener;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		// case BasicInfoView.TAKE_SMALL_PICTURE:
		// case BasicInfoView.CHOOSE_BIG_PICTURE:
		// case BasicInfoView.CROP_SMALL_PICTURE:
		// case BasicInfoView.CHOOSE_SMALL_PICTURE:
		// if (baseListener != null)
		// baseListener.onActivityResult(requestCode, resultCode, data);
		// break;
		// case MyGoal.SAVEBANKCARD:
		// if (bankCardListener != null)
		// bankCardListener
		// .onActivityResult(requestCode, resultCode, data);
		case 0:
			break;
		}
	}

	/**
	 * 菜单项
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem exit = menu.add(0, 0, 0, "退出");
		exit.setIcon(R.drawable.home_tab_exit_icon);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getGroupId() == 0 && item.getItemId() == 0)
			new AlertDialog.Builder(this)
					.setMessage("退出惠车宝？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								

									CallTaxiNotification.getInstance()
											.ExitApp();
									iawuAPP.getInstance().exit();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).show();
		return super.onOptionsItemSelected(item);
	}

}
