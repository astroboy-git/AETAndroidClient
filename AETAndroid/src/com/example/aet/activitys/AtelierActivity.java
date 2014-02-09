package com.example.aet.activitys;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.aet.R;
import com.example.aet.data.AddressInfo;
import com.example.aet.data.SimulateData;
import com.example.aet.fragment.AtelierListFragment;
import com.example.aet.widget.PageIndicator;

/**
 * 画室
 * 
 * @author swordy
 * @email mryangjian@live.com
 * @since Jan 20, 2014
 * @version 1.0
 */
public class AtelierActivity extends BaseActivity {
	private static final String TAG = "AETAndroid.AtelierActivity";

	private TabWidget mTabWidget;

	private ViewPager mViewPager;

	private PageIndicator mPageIndicator;

	private LinkedList<AtelierListFragment> mAtelierList;

	private ArrayList<AddressInfo> mAddrs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(R.layout.activity_atelier);
	}

	@Override
	protected void initView(int layoutResID) {
		setContentView(layoutResID);

		mTabWidget = (TabWidget) findViewById(R.id.tabWidget1);
		mViewPager = (ViewPager) findViewById(R.id.viewPager1);
		mPageIndicator = (PageIndicator) findViewById(R.id.pageIndicator1);

		initTabs();

		LayoutParams params = new LayoutParams(20, 20);
		mPageIndicator.setItemLayout(R.layout.atelier_page_indicator, params);
		mPageIndicator.add(mAddrs.size());

		mAtelierList = new LinkedList<AtelierListFragment>();

		mViewPager.setAdapter(new AtelierListAdapter(
				getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(mPageChangeListener);

		mTabWidget.setCurrentTab(0);
		mViewPager.setCurrentItem(0);
		mPageIndicator.setCurrentItem(0);
	}

	private void initTabs() {
		mTabWidget.setStripEnabled(false);

		LayoutInflater inflater = LayoutInflater.from(this);
		mAddrs = getAddresses();
		int count = mAddrs.size();
		for (int i = 0; i != count; i++) {
			View v = inflater.inflate(R.layout.atelier_tab_item, null);

			TextView name = (TextView) v.findViewById(R.id.txvName);

			AddressInfo addr = mAddrs.get(i);
			v.setTag(i);
			name.setText(addr.name);

			mTabWidget.addView(v);
			v.setOnClickListener(mTabClickListener);
		}
	}

	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int index) {
			mTabWidget.setCurrentTab(index);
			mPageIndicator.setCurrentItem(index);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// do nothing
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// do nothing
		}
	};

	private OnClickListener mTabClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int index = (Integer) v.getTag();
			mViewPager.setCurrentItem(index);
		}
	};

	private class AtelierListAdapter extends FragmentStatePagerAdapter {

		public AtelierListAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {

			if (index >= mAtelierList.size() || mAtelierList.get(index) == null) {
				AtelierListFragment fragment = AtelierListFragment
						.create(mAddrs.get(index));
				mAtelierList.add(fragment);
			}

			return mAtelierList.get(index);
		}

		@Override
		public int getCount() {
			return mAddrs.size();
		}

	};

	private ArrayList<AddressInfo> getAddresses() {
		return SimulateData.getAddresses();
	}
}
