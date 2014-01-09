package com.example.aet.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aet.R;
import com.example.aet.data.LoginInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.managers.UserManager;

/**
 * 
 * @author Jin Binbin
 * 
 * @2013年11月21日
 * 
 * @Version 1.0
 */
public class HomeActivity extends BaseActivity {

	private static final String TAG = "HomeActivity";

	private static final int MSG_PRO_TO_AUTOLOGIN = 0x00010101;

	private static final int MSG_PRO_TO_INITDATA = 0x000100102;

	private static final int MSG_UI_LOGIN_SUCCESS = 0x00010201;

	private static final int MSG_UI_FINISH_INITDATA = 0x00010202;

	private static final int TO_LOGIN_CODE = 0x0011000;

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
		switch (layoutResID) {
		case R.layout.activity_launcher:
			initLaucherView();
			break;
		case R.layout.activity_home:
			initHomeView();
			break;
		}
	}

	private void initHomeView() {
		// TODO Auto-generated method stub
		final GridView categoryGridView = (GridView) findViewById(R.id.categoryGridView);
		final String[] categorys = getResources().getStringArray(
				R.array.categorys);
		if (categorys != null) {
			categoryGridView.setAdapter(new BaseAdapter() {

				private LayoutInflater mLayoutInflater;

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					// TODO Auto-generated method stub
					if (mLayoutInflater == null) {
						mLayoutInflater = LayoutInflater
								.from(HomeActivity.this);
					}
					TextView itemTitleTextView = null;
					if (convertView == null) {
						convertView = mLayoutInflater.inflate(
								R.layout.category_item_layout, null);
						itemTitleTextView = (TextView) convertView
								.findViewById(R.id.itemTitleTextView);
						convertView.setTag(itemTitleTextView);
					} else {
						itemTitleTextView = (TextView) convertView.getTag();
					}
					itemTitleTextView.setText(categorys[position]);
					return convertView;
				}

				@Override
				public long getItemId(int position) {
					// TODO Auto-generated method stub
					return position;
				}

				@Override
				public Object getItem(int position) {
					// TODO Auto-generated method stub
					return categorys[position];
				}

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return categorys.length;
				}

			});
		}
		categoryGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				toStartActivity(categorys[position]);
			}
		});
	}

	private void toStartActivity(final String category) {
		if(!TextUtils.isEmpty(category)){
			Intent intent=new Intent();
			if("画室".equals(category)||"文化机构".equals(category)){
				intent.setClass(this, OrganListActivity.class);
				intent.putExtra("category", category);
			}
			startActivity(intent);
		}
	}

	private void initLaucherView() {
		// TODO Auto-generated method stub
		final LinearLayout userActionLayout = (LinearLayout) findViewById(R.id.userActionLayout);
		userActionLayout.setVisibility(View.GONE);
		if (UserManager.getInstance(this).isLogin()) {
			sendProEmptyMsg(MSG_PRO_TO_INITDATA);
			return;
		}
		final LoginInfo loginInfo = UserManager.getInstance(this)
				.getLoginInfo();
		if (loginInfo != null) {
			if (loginInfo.isAutoLogin()) {
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
			initView(R.layout.activity_home);
			break;
		}
		return super.handleUiMsg(msg);
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
			initView(R.layout.activity_launcher);
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
