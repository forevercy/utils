package com.cytmxk.utils.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;

public class UriUtils {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static Map<String, String> getQueryParameter (String url) {
		Uri uri = Uri.parse(url);
		Map<String, String> map = new HashMap<String, String>();
		Set<String> set = uri.getQueryParameterNames();
		for (String key : set) {
			map.put(key, uri.getQueryParameter(key));
		}
		return map;
	}
}
