package com.example.aet.activitys;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.example.aet.R;
import com.example.aet.data.AddressInfo;
import com.example.aet.data.SimulateData;
import com.example.aet.fragment.AtelierEclatFragment;
import com.example.aet.fragment.AtelierIntroduceFragment;
import com.example.aet.fragment.AtelierPaintsFragment;

/**
 * 
 * 画室
 * 
 * @author swordy
 * 
 */
public class AtelierActivity extends BaseSlideTabActivity {

	public static final String TAG_TITLE = "tag_title";

	private SparseArray<Fragment> mFragments;

	private ArrayList<AddressInfo> mAddrs;

	private String[] mCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(R.layout.activity_atelier);

		// TODO 动态获取地址
		mAddrs = getAddresses();
		mCategories = getResources().getStringArray(R.array.atelier_categories);
		setTabNames(mCategories);

		mFragments = new SparseArray<Fragment>(mCategories.length);
		setPagerAdapter(new AtelierListAdapter(getSupportFragmentManager()));
	}

	@Override
	protected void initView(int layoutResID) {
		super.initView(layoutResID);
		String title = getIntent().getStringExtra(TAG_TITLE);
		setTitleText(title);
	}

	private ArrayList<AddressInfo> getAddresses() {
		return SimulateData.getAddresses();
	}

	private class AtelierListAdapter extends FragmentStatePagerAdapter {

		public AtelierListAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {

			if (index >= mFragments.size() || mFragments.get(index) == null) {
				Fragment fragment = null;
				switch (index) {
				case 0:
					fragment = new AtelierIntroduceFragment();
					break;
				case 1:
					fragment = new AtelierIntroduceFragment();
					break;
				case 2:
					fragment = new AtelierPaintsFragment();
					break;
				case 3:
					fragment = new AtelierEclatFragment();
					break;
				}
				mFragments.put(index, fragment);
			}

			return mFragments.get(index);
		}

		@Override
		public int getCount() {
			return mAddrs.size();
		}

	};
}
