package com.example.aet.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
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
@SuppressLint("NewApi")
public class HomeActivity extends BaseActivity implements OnQueryTextListener {

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
		setContentView(layoutResID);
		initActionBar();
		initHomeView();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

	private void initActionBar() {
		// TODO Auto-generated method stub
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
		if (!TextUtils.isEmpty(category)) {
			Intent intent = new Intent();
			intent.putExtra("category", category);
			if ("文化机构".equals(category)) {
				intent.setClass(this, OrganListActivity.class);
				startActivity(intent);
			} else if ("画室".equals(category)) {
				intent.setClass(this, AtelierListActivity.class);
				startActivity(intent);
			} else {
				showShortToast(R.string.not_open);
			}
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

}
