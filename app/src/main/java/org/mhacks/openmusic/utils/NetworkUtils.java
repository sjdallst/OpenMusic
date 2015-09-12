package org.mhacks.openmusic.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

@EBean
public class NetworkUtils {

	@SystemService
	ConnectivityManager mConnectivityManager;

	public boolean isConnected() {
		NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
