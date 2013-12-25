package com.iawu.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.iawu.xq.iawuAPP;

public class UrlFactory {
	//登录和连接用的域名 连许东
	public static final String rootUrl = "http://192.168.1.55/mobile.php";

	public static String GetUrlOld(String data, String... args) {
		String result = UrlFactory.rootUrl;
		result += ("?" + "ostype" + "=" + "" + 1);
		result += ("&" + "version" + "=" + "" + iawuAPP.getInstance()
				.GetVersionCode());

		result += ("&" + "mod" + "=" + "" + data);
		if (args == null || args.length <= 0)
			return result;

		int count = args.length / 2;
		for (int i = 0; i < count; i++) {
			String argvalue = URLEncoder.encode(args[i * 2 + 1]);
			result += ("&" + args[i * 2] + "=" + argvalue);
		}

		return result;
	}

	//服务器 java积分查询用 连张文军
	public static final String javaRoot = "http://192.168.1.52:8080/hcbweb";

	public static String GetUrlNew(String data, String action, String... args) {
		String result = UrlFactory.javaRoot;
		result += ("/" + data);
		result += ("/" + action);
		result += ("?" + "ostype" + "=" + "" + 1);
		result += ("&" + "version" + "=" + "" + iawuAPP.getInstance()
				.GetVersionCode());

		if (args == null || args.length <= 0)
			return result;

		int count = args.length / 2;
		String argvalue = "";
		for (int i = 0; i < count; i++) {
			try {
				argvalue = URLEncoder.encode(args[i * 2 + 1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result += ("&" + args[i * 2] + "=" + argvalue);
		}

		return result;
	}
	//发送银行信息编码。gwj添加， 20130813
	public static String GetUrlNew_Bank(String data, String action, String... args) {
		String result = UrlFactory.javaRoot;
		result += ("/" + data);
		result += ("/" + action);
		result += ("?" + "ostype" + "=" + "" + 1);
		result += ("&" + "version" + "=" + "" + iawuAPP.getInstance()
				.GetVersionCode());

		if (args == null || args.length <= 0)
			return result;

		int count = args.length / 2;
		String argvalue = "";
		for (int i = 0; i < count; i++) {
			try {
				argvalue = URLEncoder.encode(args[i * 2 + 1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result += ("&" + args[i * 2] + "=" + argvalue);
		}

		return result;
	}
}