package com.example.aet.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.aet.R;
import com.example.aet.data.LoginInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.managers.UserManager;

public class LauncerActivity extends BaseActivity {

	private static final int MSG_PRO_TO_AUTOLOGIN = 0x00010101;

	private static final int MSG_PRO_TO_INITDATA = 0x000100102;

	private static final int MSG_UI_LOGIN_SUCCESS = 0x00010201;

	private static final int MSG_UI_FINISH_INITDATA = 0x00010202;

	private static final int TO_LOGIN_CODE = 0x01;

	private ProgressBar loadingProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.activity_launcher);
	}

	@Override
	protected void initView(int layoutResID) {
		// TODO Auto-generated method stub
		setContentView(layoutResID);
		initLaucherView();
	}

	private void initLaucherView() {
		// TODO Auto-generated method stub
		final LinearLayout userActionLayout = (LinearLayout) findViewById(R.id.userActionLayout);
		loadingProgressBar = (ProgressBar)findViewById(R.id.loadingProgressBar);
		userActionLayout.setVisibility(View.GONE);
		if (UserManager.getInstance(this).isLogin()) {
			loadingProgressBar.setVisibility(View.VISIBLE);
			sendProEmptyMsg(MSG_PRO_TO_INITDATA);
			return;
		}
		final LoginInfo loginInfo = UserManager.getInstance(this)
				.getLoginInfo();
		if (loginInfo != null) {
			if (loginInfo.isAutoLogin()) {
				loadingProgressBar.setVisibility(View.VISIBLE);
				sendProEmptyMsg(MSG_PRO_TO_AUTOLOGIN);
			} else {
				toUserCenterActivity(UserCenterActivity.ACTION_TO_LOGIN);
			}
		} else {
			userActionLayout.setVisibility(View.VISIBLE);
			Button loginButton = (Button) findViewById(R.id.loginButton);
			loginButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					toUserCenterActivity(UserCenterActivity.ACTION_TO_LOGIN);
				}
			});
			Button registerButton = (Button) findViewById(R.id.registerButton);
			registerButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					toUserCenterActivity(UserCenterActivity.ACTION_TO_REGISTER);
				}
			});
		}
	}

	@Override
	protected boolean handleUiMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_UI_LOGIN_SUCCESS:
			sendProEmptyMsg(MSG_PRO_TO_INITDATA);
			break;
		case MSG_UI_FINISH_INITDATA:
			loadingProgressBar.setVisibility(View.GONE);
			startHomeActivity();
			break;
		}
		return super.handleUiMsg(msg);
	}

	private void startHomeActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected boolean handlerProThreadMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_PRO_TO_AUTOLOGIN:
			RequestResult result = UserManager.getInstance(this).doAutologin();
			if (result.getResultCode() == 200) {
				sleep(3);
				sendUiEmptyMsg(MSG_UI_LOGIN_SUCCESS);
			}
			break;
		case MSG_PRO_TO_INITDATA:
			sleep(2);
			sendUiEmptyMsg(MSG_UI_FINISH_INITDATA);
			break;
		}
		return super.handlerProThreadMsg(msg);
	}

	protected void toUserCenterActivity(int action) {
		// TODO Auto-generated method stub
		Intent userCenterIntent = new Intent(this, UserCenterActivity.class);
		userCenterIntent.putExtra("action", action);
		startActivityForResult(userCenterIntent, TO_LOGIN_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && requestCode == TO_LOGIN_CODE) {
			initLaucherView();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
