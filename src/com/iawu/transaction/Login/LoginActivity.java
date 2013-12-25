package com.iawu.transaction.Login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iawu.xq.BaseActivity;
import com.iawu.xq.ClassTools;
import com.iawu.xq.MainActivity;
import com.iawu.xq.R;
import com.iawu.xq.iawuAPP;
import com.iawu.msgcenter.MsgCenter;
import com.iawu.msgcenter.MsgConst;
import com.iawu.network.NetConst;
import com.iawu.network.NetSceneBase;
import com.iawu.network.OnSceneCallBack;
import com.iawu.transaction.Login.LoginResultItem;
import com.iawu.transaction.Login.LoginResultItems;
import com.iawu.transaction.Service.CallTaxiNotification;
import com.iawu.transaction.Service.LocationClientService;

public class LoginActivity extends BaseActivity implements OnSceneCallBack,OnClickListener {
	final int[] bns_id = { R.id.loginview_login_button,
			R.id.loginview_regist_button };
	int[] editTexts_id = { R.id.loginview_name_edittext,
			R.id.loginview_pwd_edittext };
	Button[] bns = new Button[2];
	EditText[] editTexts = new EditText[2];
	CheckBox rememberPwdBox;
	SharedPreferences setting = null;
	String nameStr = null;
	String pwdStr = null;
	boolean boxIsChecked = false;
	
	LinearLayout verifyLinearLayout;
	EditText verifyEditText;
	ImageView verifyImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		setCurrentTitle("登录");
		
		init();
		initPersonalInfo();
	}

	private void init() {
		for (int i = 0; i < 2; ++i) {
			bns[i] = (Button) findViewById(bns_id[i]);
			bns[i].setOnClickListener(this);
			editTexts[i] = (EditText) findViewById(editTexts_id[i]);
		}
		
		verifyLinearLayout = (LinearLayout) findViewById(R.id.verifycode_layout);
		verifyEditText = (EditText) findViewById(R.id.verifycode_edittext);
		verifyImageView = (ImageView) findViewById(R.id.verifycode_imageview);
		verifyImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				freshVerifyCode();
			}
		});

		rememberPwdBox = (CheckBox) findViewById(
				R.id.loginview_remember_pwd);
	}
	
	private void initPersonalInfo(){
		setting = getSharedPreferences("calltaxicfg", MODE_PRIVATE);
		boxIsChecked = setting.getBoolean("rememberPWD", false);
		nameStr = setting.getString("nameStr", "");
		
		if (boxIsChecked)
			pwdStr = setting.getString("pwdStr", "");
		else
			pwdStr = "";
		
		if (boxIsChecked)
		{
			rememberPwdBox.setChecked(true);
			editTexts[0].setText(nameStr);
			editTexts[1].setText(pwdStr);
		}
		else
		{
			rememberPwdBox.setChecked(false);
			editTexts[0].setText(nameStr);
			editTexts[1].setText("");
		}
		
	}
	
	private void savePersonalInfo(){
		if (rememberPwdBox.isChecked())
		{
			setting.edit()
				   .putBoolean("rememberPWD", true)
				   .putString("nameStr", nameStr)
				   .putString("pwdStr", pwdStr)
				   .commit();
		}
		else {
			setting.edit()
				   .putBoolean("rememberPWD", false)
				   .putString("nameStr", nameStr)
				   .putString("pwdStr", "")
				   .commit();
			 }
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loginview_login_button:
			doLogin();
			break;
		case R.id.loginview_regist_button:
			// TODO 跳转到注册页面
			break;
		}
	}

	LoginScene loginScene;
	ProgressDialog progress;
	
	private void doLogin() {
		nameStr = editTexts[0].getText().toString().trim();
		pwdStr = editTexts[1].getText().toString().trim();
		
		if (FailedCount >= 3)
			if (!checkVerifyCode()){
				Alert("验证码错误！");
				freshVerifyCode();
				return;
			}
		
		if (nameStr.length() <= 0) {
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return;
		} else if (!isPhone(nameStr)) {
			Toast.makeText(this, "无效手机号", Toast.LENGTH_SHORT).show();
			return;
		} else if (pwdStr.length() <= 0) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		} else if (!isGoodPWD(pwdStr)) {
			Toast.makeText(this, "无效密码", Toast.LENGTH_SHORT).show();
			editTexts[1].setText("");
			return;
		}
		
		ShowProgress("登录中", "请稍候……");
		
//		bns[0].setOnClickListener(onLoadingListener);
//		bns[1].setOnClickListener(onLoadingListener);
		loginScene = new LoginScene();
		loginScene.doScene(this, nameStr, pwdStr);
	}
	
//	OnClickListener onLoadingListener = new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			ShowProgress("登录中", "请稍候……");
//		}
//	};
	
	private boolean isPhone(String name) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	private boolean isGoodPWD(String pwd) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_]{6,18}$");
		Matcher m = p.matcher(pwd);
		return m.matches();
	}

	int FailedCount = 0;
	private void showVerifictionCode(){
		++FailedCount;
		if (FailedCount >= 3)
		{
			verifyLinearLayout.setVisibility(View.VISIBLE);
			freshVerifyCode();
		}
	}
	
	private void freshVerifyCode(){
		verifyImageView.setImageBitmap(verificationCode.getInstance().createBitmap());
		verifyEditText.setText("");
	}
	
	private boolean checkVerifyCode(){
		String inputCode = verifyEditText.getText().toString().trim();
		String sysCode = verificationCode.getInstance().getCode();
		return inputCode.equalsIgnoreCase(sysCode);
	}
	
	@Override
	public void OnFailed(int status, String info, NetSceneBase netScene) {
		// TODO Auto-generated method stub
		HideProgress();
		ErrorAlert(status, info);
		showVerifictionCode();
		
		if(status!=NetConst.NetConnectError)
		{
			//清空密码
			editTexts[1].setText("");
		}
	}

	@Override
	public void OnSuccess(Object data, NetSceneBase netScene) {
		// TODO Auto-generated method stub
		HideProgress();
		savePersonalInfo();
		LoginResultItems items = (LoginResultItems) data;
		LoginResultItem item = items.item;
		Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
		MsgCenter.getInstance().PostMsg(MsgConst.LOGIN_SUCCESS, this);
		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
		//得到InputMethodManager的实例
		if (imm.isActive()) {
		//如果开启
			imm.hideSoftInputFromWindow(editTexts[1].getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
		//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
		
		ClassTools.getInstance();
		ClassTools.isRequesting =  false;
		// TODO 返回成功后跳转到HOME页面
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity((new Intent(LoginActivity.this, MainActivity.class)));
				iawuAPP.getInstance().ClearOtherActivity();
			}
		}, 200);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage("退出屋聯愛家？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				CallTaxiNotification.getInstance().ExitApp();
				if (LocationClientService.getInstance().getMapManager() != null)
					LocationClientService.getInstance().getMapManager().destroy();
				iawuAPP.getInstance().exit();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();
	}
}
