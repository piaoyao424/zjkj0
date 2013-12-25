package com.iawu.xq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.iawu.xq.R;
import com.iawu.network.NetConst;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class BaseActivity extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	/**
	 * 返回按钮
	 */
	protected ImageButton backKeyImageButton = null;
	/**
	 * 刷新按钮
	 */
	protected ImageButton refreshKeyImageButton = null;
	/**
	 * 标题栏
	 */
	protected TextView titleTextView = null;

	// ViewGroup root_element;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashReportHandler.attach(this);
		iawuAPP.getInstance().addActivity(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateAutoPopup(true);

		// root_element=(ViewGroup)findViewById(R.id.root_element);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		iawuAPP.getInstance().removeActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.in_left_right, R.anim.out_left_right);
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
		overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
	}

	/**
	 * 设置当前标题
	 * 
	 * @param title
	 *            标题文字
	 */
	public void setCurrentTitle(String title) {
		titleTextView = (TextView) findViewById(R.id.title_textview);
		titleTextView.setText(title);
	}

	/**
	 * 设置返回按钮监听和显示: 返回按钮默认隐藏， listner不为null则显示返回按钮， 为null则隐藏返回按钮
	 * 
	 * @param listener
	 *            返回按钮监听
	 */
	public void setBackKeyListner(OnClickListener listener) {
		backKeyImageButton = (ImageButton) findViewById(R.id.back_key_imagebutton);
		if (listener != null) {
			backKeyImageButton.setVisibility(View.VISIBLE);
			backKeyImageButton.setOnClickListener(listener);
		} else {
			backKeyImageButton.setVisibility(View.GONE);
			backKeyImageButton.setOnClickListener(null);
		}
	}

	/**
	 * 设置刷新按钮监听和显示: 刷新按钮默认隐藏， listner不为null则显示返回按钮， 为null则隐藏返回按钮
	 * 
	 * @param listener
	 *            刷新按钮监听
	 */
	public void setRefreshKeyListner(OnClickListener listener) {
		refreshKeyImageButton = (ImageButton) findViewById(R.id.refresh_key_imagebutton);
		if (listener != null) {
			refreshKeyImageButton.setVisibility(View.VISIBLE);
			refreshKeyImageButton.setOnClickListener(listener);
		} else {
			refreshKeyImageButton.setVisibility(View.GONE);
			refreshKeyImageButton.setOnClickListener(null);
		}
	}

	// 提示完后退出
	public void AlertExit(String info) {
		if (info == null)
			info = "";

		if (!this.isFinishing()) {
			@SuppressWarnings("unused")
			AlertDialog dlg = new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage(info)
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									BaseActivity.this.finish();
								}
							}).show();
		}
	}

	public void Alert(String info) {
		if (info == null)
			info = "";
		if (!this.isFinishing()) {
			/*
			 * @SuppressWarnings("unused") AlertDialog dlg = new
			 * AlertDialog.Builder(this) .setTitle("提示") .setMessage(info)
			 * .setPositiveButton("确定", new
			 * android.content.DialogInterface.OnClickListener() {
			 * 
			 * @Override public void onClick(DialogInterface dialog, int which)
			 * {
			 * 
			 * } }).show();
			 */
			Toast.makeText(this, info, 500).show();
		}
	}

	public void ErrorAlertExit(int status, String info) {
		if (status == NetConst.NetConnectError) {
			AlertExit("网络不通畅，请先检查网络再试!");

		} else if (status == NetConst.JsonParaseError) {
			AlertExit("数据解析出错，请稍后再试!");
		} else {
			if (info != null && info.length() > 0) {
				// 服务器返回的错误信息
				AlertExit(info);
			} else {
				AlertExit("遇到错误，请稍后再试!");
			}
		}
	}

	public void ErrorAlert(int status, String info) {
		if (status == NetConst.NetConnectError) {
			Alert("网络不通畅，请先检查网络再试!");

		} else if (status == NetConst.JsonParaseError) {
			Alert("数据解析出错，请稍后再试!");
		} else {
			if (info != null && info.length() > 0) {
				// 服务器返回的错误信息
				Alert(info);
			} else {
				Alert("遇到错误，请稍后再试!");
			}
		}
	}

	ProgressDialog pro = null;

	public void ShowRunning() {
		ShowProgress("获取数据中", "请稍候……");
	}

	public void ShowProgress(String title, String msg) {
		if (pro == null)
			pro = ProgressDialog.show(this, title, msg, true, true);
		else
			pro.show();
	}

	public void HideProgress() {
		if (pro != null) {
			pro.dismiss();
			pro = null;
		}
	}

	/**
	 * animations
	 */
	public void LeftToRight() {
		overridePendingTransition(R.anim.in_left_right, R.anim.out_left_right);
	}

	public void RightToLeft() {
		overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
	}
}
