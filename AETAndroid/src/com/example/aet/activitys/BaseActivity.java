package com.example.aet.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aet.R;

/**
 * 
 * @author Jin Binbin
 * 
 * @2013年11月21日
 * 
 * @Version 1.0
 */
public class BaseActivity extends FragmentActivity {

	private Handler mUiHandler;

	private ProHandleThread proHandleThread;

	private Handler mProHandler;

	private boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mUiHandler = new Handler(uiCallback);
		proHandleThread = new ProHandleThread("Pro_Thread");
		proHandleThread.start();
		mProHandler = new Handler(proHandleThread.getLooper(), proHandleThread);
	}

	/**
	 * initView中已经调用了setContentView(layoutResID)，不要重复调用
	 * 
	 * @param layoutResID
	 */
	protected void initView(int layoutResID) {
		setContentView(layoutResID);
		initActionBarView();
	}

	protected Handler getUiHandler() {
		return mUiHandler;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isExit = false;
	}

	protected Handler getProHandler() {
		return mProHandler;
	}

	private Callback uiCallback = new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (isExit) {
				return false;
			}
			return handleUiMsg(msg);
		}
	};

	protected boolean handleUiMsg(Message msg) {
		return false;
	}

	private class ProHandleThread extends HandlerThread implements Callback {

		public ProHandleThread(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			return handlerProThreadMsg(msg);
		}
	}

	protected boolean handlerProThreadMsg(Message msg) {
		return false;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isExit = true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	protected void sendUiEmptyMsg(int what) {
		mUiHandler.removeMessages(what);
		mUiHandler.sendEmptyMessage(what);
	}

	protected void sendUiMsg(Message msg) {
		mUiHandler.removeMessages(msg.what);
		mUiHandler.sendMessage(msg);
	}

	protected void sendProEmptyMsg(int what) {
		mProHandler.removeMessages(what);
		mProHandler.sendEmptyMessage(what);
	}

	protected void sendProMsg(Message msg) {
		mProHandler.removeMessages(msg.what);
		mProHandler.sendMessage(msg);
	}

	protected void recycleRes() {

	}

	protected void showShortToast(int id) {
		showShortToast(getString(id));
	}

	protected void showLongToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	protected void showShortToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	protected void sleep(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 由于ActionBar需要API-11以上，这里用布局实现。
	 */
	protected void initActionBarView() {
		View btnBack = findViewById(R.id.btnBack);
		View btnOption = findViewById(R.id.btnOption);

		View.OnClickListener l = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int id = v.getId();
				switch (id) {
				case R.id.btnBack:
					doBack();
					break;
				case R.id.btnOption:
					doOption();
					break;

				default:
					break;
				}
			}
		};

		if (btnBack != null) {
			btnBack.setOnClickListener(l);
		}
		if (btnOption != null) {
			btnOption.setOnClickListener(l);
		}
	}

	/**
	 * 获取用布局模拟的ActionBar
	 * 
	 * @return
	 */
	protected View getActionBarView() {
		return findViewById(R.id.actionbar);
	}

	/**
	 * ActionBar 左边返回按钮响应事件
	 */
	protected void doBack() {
		finish();
	}

	/**
	 * ActionBar 右边功能按钮响应事件
	 */
	protected void doOption() {

	}

	/**
	 * 动态设置ActionBar标题
	 */
	protected void setTitleText(String title) {
		TextView txvTitle = (TextView) findViewById(R.id.txvTitle);
		if (txvTitle != null) {
			txvTitle.setText(title);
		}
	}
}
