package com.example.aet.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import com.example.aet.R;

/**
 * @author swordy
 * @email mryangjian@live.com
 * @since Jan 20, 2014
 * @version 1.0
 */
public class SimulateData extends BaseInfo{
	public static final String TAG = "SimulateData";
	
	public static ArrayList<AddressInfo> getAddresses() {
		ArrayList<AddressInfo> data = new ArrayList<AddressInfo>();

		String[] addrs = { "浙江", "江苏", "上海", "北京" };
		for (int i = 0; i != addrs.length; i++) {
			AddressInfo addr = new AddressInfo();
			addr.id = UUID.randomUUID().toString();
			addr.name = addrs[i];
			data.add(addr);
		}

		Collections.sort(data, new Comparator<AddressInfo>() {

			@Override
			public int compare(AddressInfo arg1, AddressInfo arg2) {
				return arg1.name.compareTo(arg2.name);
			}
		});

		return data;
	}

	public static ArrayList<AtelierShortInfo> getAtelierShortInfos() {
		ArrayList<AtelierShortInfo> data = new ArrayList<AtelierShortInfo>();

		int[] thumbIds = { R.drawable.atelier_logo_laoying,
				R.drawable.atelier_logo_xiaoji, R.drawable.atelier_logo_laoshu,
				R.drawable.atelier_logo_xiaomao };

		for (int i = 0; i != thumbIds.length * 4; i++) {
			AtelierShortInfo info = new AtelierShortInfo();

			info.name = "小画室" + i;
			info.number = 100 + i;
			info.isHonesty = (i % 2 == 0);
			info.logoId = thumbIds[i % thumbIds.length];
			info.isFocused = (i % 3 != 0);

			data.add(info);
		}

		Collections.sort(data, new Comparator<AtelierShortInfo>() {

			@Override
			public int compare(AtelierShortInfo arg1, AtelierShortInfo arg2) {
				int result = 0;
				
				if (arg1.isHonesty == arg2.isHonesty) {
					result = arg1.name.compareTo(arg2.name);
				} else {
					result = arg1.isHonesty ? -1 : 1;
				}

				return result;
			}
		});

		return data;
	}

}
