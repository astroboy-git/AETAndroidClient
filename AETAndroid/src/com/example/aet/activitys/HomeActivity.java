package com.example.aet.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aet.R;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.activity_home);
	}

	@Override
	protected void initView(int layoutResID) {
		// TODO Auto-generated method stub
		super.initView(layoutResID);
		initHomeView();
	}

	private void initHomeView() {
		// TODO Auto-generated method stub
		final GridView categoryGridView = (GridView) findViewById(R.id.categoryGridView);
		final String[] categorys = getResources().getStringArray(
				R.array.categorys);
		if (categorys != null) {
			categoryGridView
					.setAdapter(new CategoryGridAdapter(categorys, this));
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
	

	class CategoryGridAdapter extends BaseAdapter
	{
		private LayoutInflater mLayoutInflater;
		
		private String[] mCategorys;
		
		public CategoryGridAdapter(String[] categorys,Context context)
		{
			this.mLayoutInflater = LayoutInflater
					.from(context);
			this.mCategorys=categorys;
		}

		@Override
		public View getView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
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
			if(position==getCount()-1)
			{
				convertView.setBackgroundResource(R.drawable.home_grid_item_new);;
			}
			else
			{
				itemTitleTextView.setText(mCategorys[position]);
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
			return mCategorys[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCategorys.length;
		}
	}

	private void toStartActivity(final String category) {
		if (!TextUtils.isEmpty(category)) {
			Intent intent = new Intent();
			intent.putExtra("categroy", category);
			if ("文化机构".equals(category)) {
				intent.setClass(this, AtelierListActivity.class);
				startActivity(intent);
			} else if ("画室".equals(category)) {
				intent.setClass(this, AtelierListActivity.class);
				startActivity(intent);
			} else {
				showShortToast(R.string.not_open);
			}
		}
	}

}
