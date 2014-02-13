package com.example.aet.activitys;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TabWidget;

import com.example.aet.R;
import com.example.aet.widget.PageIndicator;

public class BaseSlideTabActivity extends SlideTabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setTabNames(String[] names) {
		TabWidget tabWidget = getTabWidget();

		if (tabWidget == null || names == null) {
			return;
		}
		tabWidget.setStripEnabled(false);

		int count = names.length;

		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>(
				count);

		for (int i = 0; i != count; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", names[i]);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.atelier_tab_item, new String[] { "name" },
				new int[] { R.id.txvName });

		setTabAdapter(adapter);
	}

	public void setIndicatorCount(int count) {
		PageIndicator indicator = getIndicator();

		if (indicator == null || count == 0) {
			return;
		}

		indicator.setItemLayout(R.layout.atelier_page_indicator,
				new LayoutParams(20, 20));
		indicator.add(count);
	}

	protected void updateTabs(TabWidget tabWidget, String[] names) {
		if (tabWidget == null || names == null) {
			return;
		}
		tabWidget.setStripEnabled(false);

		int count = names.length;

		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>(
				count);

		for (int i = 0; i != count; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", names[i]);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.atelier_tab_item, new String[] { "name" },
				new int[] { R.id.txvName });

		setTabAdapter(adapter);
	}

}
