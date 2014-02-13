package com.example.aet.activitys;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.aet.R;
import com.example.aet.data.AddressInfo;
import com.example.aet.data.SimulateData;
import com.example.aet.fragment.AtelierListFragment;

/**
 * 
 * 画室列表
 * 
 * @author swordy
 * 
 */
public class AtelierListActivity extends BaseSlideTabActivity {

	private LinkedList<AtelierListFragment> mAtelierList;

	private ArrayList<AddressInfo> mAddrs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(R.layout.activity_atelier_list);
		
		mAddrs = getAddresses();
		String[] names = { "上海", "北京", "江苏", "浙江" };
		setTabNames(names);
		setIndicatorCount(names.length);
		
		mAtelierList = new LinkedList<AtelierListFragment>();
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
}
