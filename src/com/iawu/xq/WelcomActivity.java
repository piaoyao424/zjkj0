package com.iawu.xq;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.iawu.adapter.ViewFlowAdapter;
import com.iawu.xq.R;
import com.iawu.transaction.Service.CallTaxiNotification;
import com.iawu.uikit.BttenFlipper;

public class WelcomActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomeactivity);
		init();
	}

	private void init() {
		BttenFlipper viewFlow = (BttenFlipper) findViewById(R.id.viewFlow);
		viewFlow.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		viewFlow.setAdapter(new ViewFlowAdapter(this));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (IsFirstStart())
			CallTaxiNotification.getInstance().ExitApp();
	}

	private boolean IsFirstStart() {
		boolean isfirststart = true;

		SharedPreferences settings = getSharedPreferences("calltaxicfg",
				MODE_PRIVATE);
		isfirststart = settings.getBoolean("IsFirstStart", true);

		return isfirststart;
	}
}
