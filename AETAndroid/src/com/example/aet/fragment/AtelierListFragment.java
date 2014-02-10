package com.example.aet.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.example.aet.R;
import com.example.aet.data.AddressInfo;
import com.example.aet.data.AtelierShortInfo;
import com.example.aet.data.SimulateData;

/**
 * @author swordy
 * @email mryangjian@live.com
 * @since Jan 20, 2014
 * @version 1.0
 */
public class AtelierListFragment extends Fragment {
	private static final String TAG = "AETAndroid.AtelierFragment";

	private static final String TAG_ADDRESS = "tag_address";

	private AddressInfo mAddress;

	private ListView mListView;

	public static AtelierListFragment create(AddressInfo addr) {
		AtelierListFragment fragment = new AtelierListFragment();
		Bundle args = new Bundle();
		args.putSerializable(TAG_ADDRESS, addr);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle args = getArguments();
		mAddress = (AddressInfo) args.getSerializable(TAG_ADDRESS);
		View v = inflater.inflate(R.layout.fragment_atelier_list, null);
		mListView = (ListView) v.findViewById(R.id.listView1);
		updateAtelierList();
		return v;
	}

	private void updateAtelierList() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		String item_tags[] = { "logo", "honesty", "name", "number", "focus",
				"item_bg" };

		ArrayList<AtelierShortInfo> ateliers = SimulateData
				.getAtelierShortInfos();

		for (AtelierShortInfo info : ateliers) {
			HashMap<String, Object> item = new HashMap<String, Object>(
					item_tags.length);

			item.put(item_tags[0], info.logoId);
			item.put(item_tags[1], info.isHonesty);
			item.put(item_tags[2], info.name);
			item.put(item_tags[3], info.number);
			item.put(item_tags[4], info.isFocused);
			item.put(item_tags[5], info.isFocused);

			data.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
				R.layout.fragment_atelier_list_item, item_tags, new int[] {
						R.id.imgLogo, R.id.imgHonesty, R.id.txvName,
						R.id.txvNumber, R.id.imgFocus, R.id.layItem });

		adapter.setViewBinder(mViewBinder);

		mListView.setAdapter(adapter);

	}

	private ViewBinder mViewBinder = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Object data, String text) {
			int id = view.getId();

			switch (id) {
			case R.id.imgLogo:
				((ImageView) view).setImageResource((Integer) data);
				break;
			case R.id.imgHonesty:
				view.setSelected((Boolean) data);
				break;
			case R.id.txvName:
				((TextView) view).setText(text);
				break;
			case R.id.txvNumber:
				((TextView) view).setText(text);
				break;
			case R.id.imgFocus:
				view.setSelected((Boolean) data);
				view.setOnClickListener(mClickListener);
				break;
			case R.id.layItem:
				view.setSelected((Boolean) data);
				break;
			}

			return true;
		}

		private View.OnClickListener mClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int id = v.getId();

				switch (id) {
				case R.id.imgFocus:
					Toast.makeText(getActivity(), "关注", 1).show();
					break;
				}
			}
		};
	};

}
