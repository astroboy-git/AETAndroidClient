package com.example.aet.activitys;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

	private HashMap<Integer, Fragment> mFragments;

	private ArrayList<AddressInfo> mAddrs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(R.layout.activity_atelier);

		mAddrs = getAddresses();
		String[] names = { "简介", "全景", "作评", "辉煌成就" };
		setTabNames(names);

		mFragments = new HashMap<Integer, Fragment>(names.length);
		setPagerAdapter(new AtelierListAdapter(getSupportFragmentManager()));
	}

	@Override
	protected void initView(int layoutResID) {
		setContentView(layoutResID);
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
				// AtelierListFragment fragment = AtelierListFragment
				// .create(mAddrs.get(index));

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
