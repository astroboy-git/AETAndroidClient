package com.example.aet.activitys;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.aet.R;
import com.example.aet.data.LoginInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.managers.UserManager;

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

	private CheckBox mAgreeClauseCheckBox;

	private Button mOkButton; 

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
		final FrameLayout formLinearLayout = (FrameLayout) findViewById(R.id.formLinearLayout);
		final RadioGroup typeRadioGroup = (RadioGroup) findViewById(R.id.typeRadioGroup);
		final LayoutInflater inflater = LayoutInflater.from(this);
		typeRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.studentRadio:
							formLinearLayout.removeAllViews();
							formLinearLayout.addView(inflater.inflate(
									R.layout.register_student_layout, null));
							initStudentRegister();
							break;
						case R.id.teacherRadio:
							formLinearLayout.removeAllViews();
							formLinearLayout.addView(inflater.inflate(
									R.layout.register_teacher_layout, null));
							initTeacherRegister();
							break;
						case R.id.organizationRadio:
							formLinearLayout.removeAllViews();
							formLinearLayout.addView(inflater.inflate(
									R.layout.register_orgnization_layout, null));
							initOrgizationRegister();
							break;
						default:
							break;
						}
					}
				});
		formLinearLayout.removeAllViews();
		formLinearLayout.addView(inflater.inflate(
				R.layout.register_student_layout, null));
		mAgreeClauseCheckBox = (CheckBox) findViewById(R.id.agreeClauseCheckBox);
		mOkButton = (Button) findViewById(R.id.okButton);
	}

	private String organType = "studio";

	private void initOrgizationRegister() {
		// TODO Auto-generated method stub
		final EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditText);
		final EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		final EditText organNameEditText = (EditText) findViewById(R.id.organNameEditText);
		final RadioGroup organRadioGroup = (RadioGroup) findViewById(R.id.organRadioGroup);
		organRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.studioRadio:
							organType = "studio";
							break;
						case R.id.organizationRadio:
							organType = "organization";
							break;
						default:
							break;
						}
					}
				});
		mOkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone = phoneNumEditText.getEditableText().toString();
				String name = userNameEditText.getEditableText().toString();
				String password = passwordEditText.getEditableText().toString();
				String organName = organNameEditText.getEditableText()
						.toString();
				boolean agree = mAgreeClauseCheckBox.isChecked();
				organizationRegister(phone, name, password, organName,
						organType, agree);
			}
		});

	}

	private void organizationRegister(String phone, String name,
			String password, String organName, String organType, boolean agree) {
		// TODO Auto-generated method stub

	}

	private void initTeacherRegister() {
		// TODO Auto-generated method stub
		final EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditText);
		final EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
		final EditText schoolEditText = (EditText) findViewById(R.id.schoolEditText);
		final EditText subjectEditText = (EditText) findViewById(R.id.subjectEditText);
		mOkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone = phoneNumEditText.getEditableText().toString();
				String userName = userNameEditText.getEditableText().toString();
				String password = passwordEditText.getEditableText().toString();
				String name = nameEditText.getEditableText().toString();
				String school = schoolEditText.getEditableText().toString();
				String subject = subjectEditText.getEditableText().toString();
				boolean agree = mAgreeClauseCheckBox.isChecked();
				teacherRegister(phone, userName, password, name, school,
						subject, agree);
			}
		});
	}

	private void teacherRegister(String phone, String userName,
			String password, String name, String school, String subject,
			boolean agree) {
		// TODO Auto-generated method stub

	}

	private void initStudentRegister() {
		// TODO Auto-generated method stub
		final EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditText);
		final EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
		final EditText schoolEditText = (EditText) findViewById(R.id.schoolEditText);
		final EditText gradeEditText = (EditText) findViewById(R.id.gradeEditText);
		mOkButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone = phoneNumEditText.getEditableText().toString();
				String userName = userNameEditText.getEditableText().toString();
				String password = passwordEditText.getEditableText().toString();
				String name = nameEditText.getEditableText().toString();
				String school = schoolEditText.getEditableText().toString();
				String grade = gradeEditText.getEditableText().toString();
				boolean agree = mAgreeClauseCheckBox.isChecked();
				studentRegister(phone, userName, password, name, school, grade,
						agree);
			}
		});
	}

	private void studentRegister(String phone, String userName,
			String password, String name, String school, String grade,
			boolean agree) {
		// TODO Auto-generated method stub

	}

	private void initLoginView() {
		// TODO Auto-generated method stub
		final EditText phoneNumEditText = (EditText) findViewById(R.id.phoneNumEditText);
		final EditText passWordEditText = (EditText) findViewById(R.id.passWordEditText);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		Button registerButton = (Button) findViewById(R.id.registerButton);
		Button forgetPasswordButton = (Button) findViewById(R.id.forgetPasswordButton);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String phoneNum = phoneNumEditText.getEditableText().toString();
				String password = passWordEditText.getEditableText().toString();
				if (checkLoginInfo(phoneNum, password)) {
					CheckBox checkBox = (CheckBox) findViewById(R.id.autoLoginCheckBox);
					toLogin(phoneNum, password, checkBox.isChecked());
				}
			}

			private boolean checkLoginInfo(String phoneNum, String password) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(phoneNum)) {
					showShortToast(R.string.input_login_num);
					return false;
				} else if (TextUtils.isEmpty(password)) {
					showShortToast(R.string.input_login_password);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	protected void recycleRes() {
	}
}
