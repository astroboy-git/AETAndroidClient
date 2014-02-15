package com.example.aet.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aet.R;

/**
 * 辉煌成就
 * 
 * @author swordy
 * 
 */
public class AtelierEclatFragment extends BaseFragment {

	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_atelier_eclats, null);
		initView(v);
		return v;
	}

	@Override
	protected void initView(View v) {
		mListView = (ListView) v.findViewById(R.id.list);
		updateListView();
	}

	private void updateListView() {
		String[] from = { "year" };
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int year = 2014;
		for (int i = 0; i != 3; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(from[0], year--);
			data.add(item);
		}

		EclatAdapter adapter = new EclatAdapter(getActivity(), data,
				R.layout.fragment_atelier_eclats_item, from,
				new int[] { R.id.txvYear });
		mListView.setAdapter(adapter);
	}

	private class EclatAdapter extends SimpleAdapter {

		public EclatAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);

			TextView year = (TextView) v.findViewById(R.id.txvYear);
			TextView txv1 = (TextView) v.findViewById(R.id.txv1);
			if (position == 0) {
				year.setTextColor(0xff3fc39c);
				txv1.setBackgroundColor(0xff3fc39c);
			} else {
				year.setTextColor(0xffa1a1a1);
				txv1.setBackgroundColor(0xff999b9a);
			}

			return v;
		}

	}
}
