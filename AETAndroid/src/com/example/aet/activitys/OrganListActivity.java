package com.example.aet.activitys;

import java.util.List;

import com.example.aet.R;
import com.example.aet.data.OrganInfo;
import com.example.aet.managers.RequestManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Jin Binbin
 * 
 * @2013年12月10日
 * 
 * @Version 1.0
 */
public class OrganListActivity extends BaseActivity {

	private static final int MSG_PRO_LOAD_ORGANLIST = 0x10001001;

	private static final int MSG_UI_LOADORGANLIST_FINISH = 0x10002001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.activity_organlist);
	}

	@Override
	protected void initView(int layoutResID) {
		// TODO Auto-generated method stub
		setContentView(layoutResID);
		Intent intent = getIntent();
		final String title = intent.getStringExtra("category");
		final TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
		titleTextView.setText(title);
		sendProMsg(getProHandler().obtainMessage(MSG_PRO_LOAD_ORGANLIST, title));
	}

	@Override
	protected boolean handlerProThreadMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_PRO_LOAD_ORGANLIST:
			String type = (String) msg.obj;
			List<OrganInfo> organInfos = RequestManager.getInstance()
					.getOrganList(type);
			sendUiMsg(getUiHandler().obtainMessage(MSG_UI_LOADORGANLIST_FINISH,
					organInfos));
			break;
		}
		return super.handlerProThreadMsg(msg);
	}

	@Override
	protected boolean handleUiMsg(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_UI_LOADORGANLIST_FINISH:
			initOrganList((List<OrganInfo>) msg.obj);
			break;
		}
		return super.handleUiMsg(msg);
	}

	private void initOrganList(final List<OrganInfo> organInfos) {
		// TODO Auto-generated method stub
		if (organInfos != null) {
			final ListView organListView = (ListView) findViewById(R.id.organListView);
			organListView.setAdapter(new BaseAdapter() {

				private LayoutInflater mLayoutInflater;

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					// TODO Auto-generated method stub
					if (mLayoutInflater == null) {
						mLayoutInflater = LayoutInflater
								.from(OrganListActivity.this);
					}
					ViewHolder viewHolder = null;
					if (convertView == null) {
						viewHolder = new ViewHolder();
						convertView = mLayoutInflater.inflate(
								R.layout.organ_item_layout, null);
						viewHolder.vipImageView = (ImageView) convertView
								.findViewById(R.id.vipLogoImageView);
						viewHolder.nameTextView = (TextView) convertView
								.findViewById(R.id.nameTextView);
						convertView.setTag(viewHolder);
					} else {
						viewHolder = (ViewHolder) convertView.getTag();
					}
					OrganInfo organInfo = organInfos.get(position);
					if (organInfo != null) {
						viewHolder.nameTextView.setText(organInfo.getName());
						if (organInfo.isVip()) {
							viewHolder.vipImageView.setVisibility(View.VISIBLE);
						} else {
							viewHolder.vipImageView.setVisibility(View.GONE);
						}
					}
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
					return organInfos.get(position);
				}

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return organInfos.size();
				}
			});

		}
	}

	private class ViewHolder {
		ImageView vipImageView;
		TextView nameTextView;
	}
}
