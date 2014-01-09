package com.example.aet.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aet.R;
import com.example.aet.data.LoginInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.managers.UserManager;
import com.example.aet.utils.AuthCodeBitmapCreater;

/**
 * 
 * @author Jin Binbin
 * 
 * @2013年12月3日
 * 
 * @Version 1.0
 */
public class UserCenterActivity extends BaseActivity {

	public static final int ACTION_TO_LOGIN = 0x20001000;

	public static final int ACTION_TO_REGISTER = 0x20002000;

	public static final int ACTION_TO_USER = 0x20003000;

	public static final int ACTION_TO_RETRIEVE = 0x20004000;

	private static final int MSG_PRO_TO_LOGIN = 0x20001101;

	private static final int MSG_UI_LOGIN_RESULT = 0x20001201;

	private Bitmap authCodeBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int action = getIntent().getIntExtra("action", ACTION_TO_USER);
		int layoutResID = R.layout.activity_usercenter;
		switch (action) {
		case ACTION_TO_USER:
			layoutResID = R.layout.activity_usercenter;
			break;
		case ACTION_TO_REGISTER:
			layoutResID = R.layout.activity_usercenter_register;
			break;
		case ACTION_TO_LOGIN:
			layoutResID = R.layout.activity_usercenter_login;
			break;
		case ACTION_TO_RETRIEVE:
			layoutResID = R.layout.activity_usercenter_retrieve;
			break;

		}
		initView(layoutResID);
	}

	@Override
	protected void initView(int layoutResID) {
		// TODO Auto-generated method stub
		setContentView(layoutResID);
		switch (layoutResID) {
		case R.layout.activity_usercenter_login:
			initLoginView();
			break;
		case R.layout.activity_usercenter_register:
			initRegisterView();
			break;
		case R.layout.activity_usercenter_retrieve:
			initRetrieveView();
			break;
		case R.layout.activity_usercenter:
			initUserCenterView();
			break;
		}
	}

	private void initUserCenterView() {
		// TODO Auto-generated method stub

	}

	private void initRetrieveView() {
		// TODO Auto-generated method stub

	}

	private void initRegisterView() {
		// TODO Auto-generated method stub
		final LinearLayout formLinearLayout = (LinearLayout) findViewById(R.id.formLinearLayout);
		final Button studentButton = (Button) findViewById(R.id.studentButton);
		final Button teacherButton = (Button) findViewById(R.id.teacherButton);
		final Button organizationButton = (Button) findViewById(R.id.organizationButton);
		final LayoutInflater inflater = LayoutInflater.from(this);
		studentButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				formLinearLayout.removeAllViews();
				formLinearLayout.addView(inflater.inflate(
						R.layout.register_student_layout, null));
			}
		});
		teacherButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				formLinearLayout.removeAllViews();
				formLinearLayout.addView(inflater.inflate(
						R.layout.register_teacher_layout, null));
			}
		});
		organizationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				formLinearLayout.removeAllViews();
				formLinearLayout.addView(inflater.inflate(
						R.layout.register_orgnization_layout, null));
			}
		});
		formLinearLayout.removeAllViews();
		formLinearLayout.addView(inflater.inflate(
				R.layout.register_student_layout, null));

	}

	private void initLoginView() {
		// TODO Auto-generated method stub
		final EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditText);
		final EditText passWordEditText = (EditText) findViewById(R.id.passWordEditText);
		final EditText authCodeEditText = (EditText) findViewById(R.id.authCodeEditText);
		final ImageView authCodeImageView = (ImageView) findViewById(R.id.authCodeImageView);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		Button registerButton = (Button) findViewById(R.id.registerButton);
		Button forgetPasswordButton = (Button) findViewById(R.id.forgetPasswordButton);
		setAuthCode(authCodeImageView);
		authCodeImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setAuthCode(authCodeImageView);
			}
		});
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String phoneNum = phoneNumEditText.getEditableText().toString();
				String password = passWordEditText.getEditableText().toString();
				String authCode = authCodeEditText.getEditableText().toString();
				if (checkLoginInfo(phoneNum, password, authCode)) {
					CheckBox checkBox = (CheckBox) findViewById(R.id.autoLoginCheckBox);
					toLogin(phoneNum, password, checkBox.isChecked());
				}
			}

			private boolean checkLoginInfo(String phoneNum, String password,
					String authCode) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(phoneNum)) {
					showShortToast(R.string.input_login_num);
					return false;
				} else if (TextUtils.isEmpty(password)) {
					showShortToast(R.string.input_login_password);
					return false;
				} else if (TextUtils.isEmpty(authCode)) {
					showShortToast(R.string.input_login_authcode);
					return false;
				}
				if (!authCode.equalsIgnoreCase(AuthCodeBitmapCreater
						.getInstance().getCode())) {
					showShortToast(R.string.input_error_authcode);
					return false;
				}
				return true;
			}
		});
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initView(R.layout.activity_usercenter_register);
			}
		});
		forgetPasswordButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initView(R.layout.activity_usercenter_retrieve);
			}
		});
	}

	private void setAuthCode(final ImageView authCodeImageView) {
		recycleRes();
		authCodeBitmap = AuthCodeBitmapCreater.getInstance()
				.createAuthCodeBitmap(AuthCodeBitmapCreater.NUMS);
		authCodeImageView.setImageBitmap(authCodeBitmap);
	}

	private void toLogin(String phoneNum, String password, boolean isAutoLogin) {
		// TODO Auto-generated method stub
		LoginInfo loginInfo = new LoginInfo(phoneNum, password);
		loginInfo.setAutoLogin(isAutoLogin);
		getProHandler().removeMessages(MSG_PRO_TO_LOGIN);
		getProHandler().sendMessage(
				getProHandler().obtainMessage(MSG_PRO_TO_LOGIN, loginInfo));
	}

	@Override
	protected boolean handlerProThreadMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_PRO_TO_LOGIN:
			RequestResult result = UserManager.getInstance(this).login(
					(LoginInfo) msg.obj);
			getUiHandler().removeMessages(MSG_UI_LOGIN_RESULT);
			getUiHandler().sendMessage(
					getUiHandler().obtainMessage(MSG_UI_LOGIN_RESULT, result));
			break;
		}
		return super.handlerProThreadMsg(msg);
	}

	@Override
	protected boolean handleUiMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_UI_LOGIN_RESULT:
			RequestResult result = (RequestResult) msg.obj;
			if (result != null && result.getResultCode() == 200) {
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
		return super.handleUiMsg(msg);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		recycleRes();
		super.onDestroy();
	}

	protected void recycleRes() {
		if (authCodeBitmap != null && !authCodeBitmap.isRecycled()) {
			authCodeBitmap.recycle();
		}
		authCodeBitmap = null;
	}
}
