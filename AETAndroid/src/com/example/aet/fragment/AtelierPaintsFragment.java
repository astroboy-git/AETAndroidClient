package com.example.aet.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.aet.R;

/**
 * 作品
 * 
 * @author swordy
 * 
 */
public class AtelierPaintsFragment extends BaseFragment {

	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_atelier_paints, null);
		initView(v);
		return v;
	}

	protected void initView(View v) {
		mListView = (ListView) v.findViewById(R.id.list);

		updateListView();
	}

	private void updateListView() {
		String[] from = { "thumb", "img1", "img2", "img3" };
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i != from.length; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(from[0], R.drawable.atelier_paints_item_thumb);
			item.put(from[1], R.drawable.atelier_paints_item_img1);
			item.put(from[2], R.drawable.atelier_paints_item_img2);
			item.put(from[3], R.drawable.atelier_paints_item_img3);
			data.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
				R.layout.fragment_atelier_paints_item, from, new int[] {
						R.id.imgThumb, R.id.img1, R.id.img2, R.id.img3 });
		mListView.setAdapter(adapter);
	}
}
