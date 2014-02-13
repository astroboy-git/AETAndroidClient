package com.example.aet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aet.R;

/**
 * 画室简介
 * @author swordy
 *
 */
public class AtelierIntroduceFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_atelier_introduce, null);
		initView(v);
		return v;
	}
	
	@Override
	protected void initView(View v){
		
	}
}
