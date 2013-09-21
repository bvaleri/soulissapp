package it.angelic.soulissclient.net;

import it.angelic.soulissclient.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.util.Log;

public class StaticUtils {
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e(Constants.TAG, "There was an IO error", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/*
	 * public static String convertStreamToString(InputStream is) {
	 * 
	 * if (is != null) { Writer writer = new StringWriter();
	 * 
	 * char[] buffer = new char[1024]; try { Reader reader = new
	 * BufferedReader(new InputStreamReader(is, "UTF-8")); int n; while ((n =
	 * reader.read(buffer)) != -1) { writer.write(buffer, 0, n); } } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } finally { try { is.close(); }
	 * catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * return writer.toString(); } else { return ""; } }
	 */

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String openHTMLString(Context context, int id) {
		InputStream is = context.getResources().openRawResource(id);

		return StaticUtils.convertStreamToString(is);
	}
}
