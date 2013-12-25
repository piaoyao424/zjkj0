package com.iawu.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.iawu.algorithm.MD5;

public final class Util {
	private static final String TAG = "BTCore.Util";
	
	public static final int PAGE_LOAD_MIN_NUM=10;
	
	public static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
	public static final String PHOTO_DEFAULT_EXT = ".jpg";

	public static final long MILLSECONDS_OF_SECOND = 1000;
	public static final long MILLSECONDS_OF_MINUTE = 60 * MILLSECONDS_OF_SECOND;
	public static final long MILLSECONDS_OF_HOUR = 60 * MILLSECONDS_OF_MINUTE;
	public static final long MILLSECONDS_OF_DAY = 24 * MILLSECONDS_OF_HOUR;
	public static final long SECOND_OF_MINUTE = 60;
	public static final long MINUTE_OF_HOUR = 60;
	public static final long MAX_32BIT_VALUE = 0x00000000FFFFFFFFL;

	public static final int MIN_ACCOUNT_LENGTH = 6;
	public static final int MAX_ACCOUNT_LENGTH = 20;
	// public static final int MIN_PASSWORD_LENGTH = 6; //update the pwd length
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 1;

	public static final int BIT_OF_KB = 10;
	public static final int BIT_OF_MB = 20;
	public static final int BYTE_OF_KB = 1 << BIT_OF_KB;
	public static final int BYTE_OF_MB = 1 << BIT_OF_MB;

	public static final int MASK_4BIT = 0x0F;
	public static final int MASK_8BIT = 0xFF;
	public static final int MASK_16BIT = 0xFFFF;
	public static final int MASK_32BIT = 0xFFFFFFFF;

	public static final int BEGIN_TIME = 22;
	public static final int END_TIME = 8;
	public static final int DAY = 0;

	public static final String TAIWAN = "zh_TW";
	public static final String HONGKONG = "zh_HK";
	public static final String CHINA = "zh_CN";
	public static final String ENGLISH = "en";
	public static final String LANGUAGE_DEFAULT = "language_default";

	public Util() {
		
	}

	/**
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	*/
	public static float dip2px(Context context, float dpValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return dpValue * scale + 0.5f;
	}

	/**
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	*/
	public static int px2dip(Context context, float pxValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * will drop some value
	 * 
	 * @param value
	 * @return
	 */
	public static String escapeSqlValue(String value) {
		if (value != null) {
			value = value.replace("\\[", "[[]");
			value = value.replace("%", "");
			value = value.replace("\\^", "");
			value = value.replace("'", "");
			value = value.replace("\\{", "");
			value = value.replace("\\}", "");
			value = value.replace("\"", "");
		}

		return value;
	}

	public static String listToString(List<String> srcList, String seperator) {
		if (srcList == null) {
			return "";
		}
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < srcList.size(); i++) {
			if (i == srcList.size() - 1) {
				result.append(srcList.get(i).trim());
			} else {
				result.append(srcList.get(i).trim() + seperator);
			}
		}
		return result.toString();
	}

	public static List<String> stringsToList(final String[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		final List<String> result = new ArrayList<String>();
		for (int i = 0; i < src.length; i++) {
			result.add(src[i]);
		}
		return result;
	}

	public static int getIntRandom(final int upLimit, final int downLimit) {
		Assert.assertTrue(upLimit > downLimit);
		final Random r = new Random(System.currentTimeMillis());
		return r.nextInt(upLimit - downLimit + 1) + downLimit;
	}

	// use to detect it is day or night
	public static boolean isDayTimeNow() {
		int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);
		final long cDawn = 6;
		final long cAfternoon = 18;
		return hour >= cDawn && hour < cAfternoon;
	}

	public static boolean isNightTime(int hour, int start, int end) {
		if (start > end) {
			return (hour >= start || hour <= end) ? true : false;
		} else if (start < end) {
			return (hour <= end && hour >= start) ? true : false;
		}
		return true;
	}

	public static int getTimeZoneOffset() {
		TimeZone timeZone = TimeZone.getDefault();
		return (int) (timeZone.getRawOffset() / MILLSECONDS_OF_HOUR);
	}

	@Deprecated
	public static String getTimeZone() {
		String timeZone = getTimeZoneDef();
		int index = timeZone.indexOf('+');
		if (index == -1) {
			index = timeZone.indexOf('-');
		}
		if (index == -1) {
			return "";
		}
		timeZone = timeZone.substring(index, index + 3);
		if (timeZone.charAt(1) == '0') {
			timeZone = timeZone.substring(0, 1) + timeZone.substring(2, 3);
		}
		return timeZone;
	}

	public static String getTimeZoneDef() {
		TimeZone timeZone = TimeZone.getDefault();
		boolean daylightTime = false;
		int style = TimeZone.LONG;
		if (style == TimeZone.SHORT || style == TimeZone.LONG) {
			boolean useDaylight = daylightTime && timeZone.useDaylightTime();
			int offset = timeZone.getRawOffset();
			if (useDaylight && timeZone instanceof SimpleTimeZone) {
				offset += ((SimpleTimeZone) timeZone).getDSTSavings();
			}
			offset /= SECOND_OF_MINUTE * 1000;
			char sign = '+';
			if (offset < 0) {
				sign = '-';
				offset = -offset;
			}
			return String.format("GMT%s%02d:%02d", sign, offset / MINUTE_OF_HOUR, offset % MINUTE_OF_HOUR);
		}
		throw new IllegalArgumentException();
	}

	public static String formatUnixTime(final long timestamp) {
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isAlpha(final char ch) {
		return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'));
	}

	public static boolean isNum(final char ch) {
		return (ch >= '0' && ch <= '9');
	}

	public static boolean isValidQQNum(String account) {
		if (account == null || account.length() <= 0) {
			return false;
		}

		account = account.trim();
		try {

			final long uin = Long.valueOf(account);
			return (uin > 0 && uin <= MAX_32BIT_VALUE);

		} catch (final NumberFormatException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isValidEmail(final String account) {
		if (account == null || account.length() <= 0) {
			return false;
		}

		return account.trim().matches("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
	}
	
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static boolean isValidAccount(String account) {

		if (account == null) {
			return false;
		}

		account = account.trim();

		// length > 6
		if (account.length() < MIN_ACCOUNT_LENGTH || account.length() > MAX_ACCOUNT_LENGTH) {
			return false;
		}

		// begin with alphabet
		if (!Util.isAlpha(account.charAt(0))) {
			return false;
		}

		// [a-z][A-Z][0-9][-_]
		for (int i = 0; i < account.length(); i++) {
			final char ch = account.charAt(i);
			if (!Util.isAlpha(ch) && !Util.isNum(ch) && ch != '-' && ch != '_') {
				return false;
			}
		}

		return true;
	}

	public static boolean isValidPassword(final String pass) {
		if (pass == null) {
			return false;
		}
		if (pass.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}
		if (pass.length() >= MAX_PASSWORD_LENGTH) {
			return true;
		}
		try {
			Integer.parseInt(pass);
			return false;
		} catch (final NumberFormatException e) {
			return true;
		}
	}

	public static BitmapFactory.Options getImageOptions(final String path) {
		Assert.assertTrue(path != null && !path.equals(""));

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		try {
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
		}
		return options;
	}

	public static void saveBitmapToImage(final Bitmap bitmap, final int quality, final Bitmap.CompressFormat format, final String dirPath, final String bitName, final boolean recycle) throws IOException {
		Assert.assertTrue(dirPath != null && bitName != null);

		Log.d(TAG, "saving to " + dirPath + bitName);

		final File file = new File(dirPath + bitName);
		file.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
			bitmap.compress(format, quality, fOut);
			fOut.flush();

		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void saveBitmapToImage(final Bitmap bitmap, final int quality, final Bitmap.CompressFormat format, final String pathName, final boolean recycle) throws IOException {

		Assert.assertTrue(!IsEmpty(pathName));

		Log.d(TAG, "saving to " + pathName);

		final File file = new File(pathName);
		file.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
			bitmap.compress(format, quality, fOut);
			fOut.flush();

		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
				// options.inJustDecodeBounds = false;
				// Log.i(TAG, "bitmap required size=" + width + "x" + height + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
				// return BitmapFactory.decodeFile(path, options);
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}

			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;

			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}

			Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
			if (scale != null) {
				bm.recycle();
				bm = scale;
			}

			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}

				bm.recycle();
				bm = cropped;
				Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
			}
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}

	public static boolean createThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String dirPath, final String thumbName) {
		final Bitmap bm = extractThumbNail(origPath, height, width, false);
		if (bm == null) {
			return false;
		}

		try {
			Util.saveBitmapToImage(bm, quality, format, dirPath, thumbName, true);

		} catch (final IOException e) {
			Log.e(TAG, "create thumbnail from orig failed: " + thumbName);
			return false;
		}

		return true;
	}

	public static boolean rotate(final String origPath, final int degree, final Bitmap.CompressFormat format, final int quality, final String dirPath, final String thumbName) {
		Bitmap bmp = BitmapFactory.decodeFile(origPath);
		if (bmp == null) {
			Log.e(TAG, "rotate: create bitmap fialed");
			return false;
		}
		float width = bmp.getWidth();
		float height = bmp.getHeight();

		Matrix m = new Matrix();
		m.setRotate(degree, width / 2, height / 2);
		Bitmap resultBmp = Bitmap.createBitmap(bmp, 0, 0, (int) width, (int) height, m, true);
		bmp.recycle();
		try {
			Util.saveBitmapToImage(resultBmp, quality, format, dirPath, thumbName, true);
		} catch (IOException e) {
			Log.e(TAG, "create thumbnail from orig failed: " + thumbName);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * Rounded corner bitmaps on Android Posted on June 16, 2009 by ruibm
	 */
	public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final boolean shouldRecyle, final float roundPx) {
		Assert.assertNotNull(bitmap);
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = 0xFFC0C0C0;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		if (shouldRecyle) {
			bitmap.recycle();
		}
		return output;
	}

	public static String getSizeKB(final long bytes) {
		final float cRound = 10;

		// > 1MB
		if ((bytes >> BIT_OF_MB) > 0) {
			return getSizeMB(bytes);
		}

		// > 0.5K
		if ((bytes >> (BIT_OF_KB - 1)) > 0) {
			final float bytesInKB = (Math.round(bytes * cRound / BYTE_OF_KB)) / cRound;
			return "" + bytesInKB + "KB";
		}

		return "" + bytes + "B";
	}

	public static String getSizeMB(final long bytes) {
		final float cRound = 10;

		final float bytesInMB = (Math.round(bytes * cRound / BYTE_OF_MB)) / cRound;
		return "" + bytesInMB + "MB";
	}

	public static String dumpArray(final Object[] stack) {
		final StringBuilder sb = new StringBuilder();
		for (final Object ste : stack) {
			sb.append(ste);
			sb.append(",");
		}
		return sb.toString();
	}

	public static String dumpHex(final byte[] privateKey) {
		if (privateKey == null) {
			return "(null)";
		}

		final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		final int j = privateKey.length;
		final int cHexWidth = 3;
		final char[] str = new char[j * cHexWidth + j / 16];
		int k = 0;
		for (int i = 0; i < j; i++) {
			final byte byte0 = privateKey[i];
			str[k++] = ' ';
			str[k++] = hexDigits[byte0 >>> 4 & MASK_4BIT];
			str[k++] = hexDigits[byte0 & MASK_4BIT];

			if (i % 16 == 0 && i > 0) {
				str[k++] = '\n';
			}
		}
		return new String(str);
	}

	public static Intent getInstallPackIntent(final String filePath, final Context context) {
		Assert.assertTrue(filePath != null && !filePath.equals(""));
		final Intent i = new Intent(Intent.ACTION_VIEW);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
		return i;
	}

	public static void installPack(final String filePath, final Context context) {
		context.startActivity(getInstallPackIntent(filePath, context));
	}

	private static final long[] VIRBRATOR_PATTERN = new long[] { 300, 200, 300, 200 };

	public static void shake(final Context context, final boolean shake) {
		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		if (vibrator == null) {
			return;
		}
		if (shake) {
			vibrator.vibrate(VIRBRATOR_PATTERN, -1);
		} else {
			vibrator.cancel();
		}
	}

	public static MediaPlayer playSound(final Context context, final int pathId, final MediaPlayer.OnCompletionListener l) {
		try {
			final String path = context.getString(pathId);
			final AssetFileDescriptor afd = context.getAssets().openFd(path);
			final MediaPlayer player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			afd.close();
			player.prepare();

			player.setLooping(false);
			player.start();

			// release
			player.setOnCompletionListener(l);

			return player;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void playSound(final Context context, final int pathId) {
		playSound(context, pathId, new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	public static long nowSecond() {
		return System.currentTimeMillis() / MILLSECONDS_OF_SECOND;
	}

	public static String formatSecToMin(int second) {
		return String.format("%d:%02d", second / SECOND_OF_MINUTE, second % SECOND_OF_MINUTE);
	}

	public static long nowMilliSecond() {
		return System.currentTimeMillis();
	}

	public static long secondsToNow(final long before) {
		return (System.currentTimeMillis() / MILLSECONDS_OF_SECOND - before);
	}

	public static long milliSecondsToNow(final long before) {
		return (System.currentTimeMillis() - before);
	}

	public static long ticksToNow(final long before) {
		return (SystemClock.elapsedRealtime() - before);
	}

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public static long currentTicks() {
		return SystemClock.elapsedRealtime();
	}

	public static long currentDayInMills() {
		return (nowMilliSecond() / MILLSECONDS_OF_DAY) * MILLSECONDS_OF_DAY;
	}

	public static long currentWeekInMills() {
		final GregorianCalendar now = new GregorianCalendar();
		final GregorianCalendar week = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		week.setTimeZone(GMT);
		final int offset = now.get(Calendar.DAY_OF_WEEK) - now.getFirstDayOfWeek();
		week.add(Calendar.DAY_OF_YEAR, -offset);
		return week.getTimeInMillis();
	}

	public static long currentMonthInMills() {
		final GregorianCalendar now = new GregorianCalendar();
		final GregorianCalendar month = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
		month.setTimeZone(GMT);
		return month.getTimeInMillis();
	}

	public static long currentYearInMills() {
		final GregorianCalendar now = new GregorianCalendar();
		final GregorianCalendar year = new GregorianCalendar(now.get(Calendar.YEAR), 1, 1);
		year.setTimeZone(GMT);
		return year.getTimeInMillis();
	}

	public static int getSystemVersion(final Context context, final int def) {
		if (context == null) {
			return def;
		}

		final int ver = android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.SYS_PROP_SETTING_VERSION, def);
		return ver;
	}

	public static String getDeviceId(final Context context) {
		if (context == null) {
			return null;
		}

		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm == null) {
				return null;
			}
			final String deviceId = tm.getDeviceId();
			return deviceId == null ? null : deviceId.trim();

		} catch (final SecurityException e) {
			Log.e(TAG, "getDeviceId failed, security exception");

		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}

		return null;
	}

	public static String getLine1Number(final Context context) {
		if (context == null) {
			return null;
		}

		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm == null) {
				Log.e(TAG, "get line1 number failed, null tm");
				return null;
			}

		} catch (final SecurityException e) {
			Log.e(TAG, "getLine1Number failed, security exception");

		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}

		return null;
	}

	/**
	 * lock screen check
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isLockScreen(final Context context) {
		try {
			final KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			return km.inKeyguardRestrictedInputMode();

		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}

		return false;
	}

	public static boolean isTopActivity(final Context context) {
		final String ctxName = context.getClass().getName();
		final String topName = getTopActivityName(context);
		Log.d(TAG, "top activity=" + topName + ", context=" + ctxName);
		return topName.equalsIgnoreCase(ctxName);
	}

	public static boolean isServiceRunning(final Context context, final String service) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
		for (final RunningServiceInfo i : list) {
			if (i == null || i.service == null) {
				continue;
			}

			if (i.service.getClassName().toString().equals(service)) {
				Log.w(TAG, "service " + service + " is running");
				return true;
			}
		}

		Log.w(TAG, "service " + service + " is not running");
		return false;
	}

	public static boolean isProcessRunning(final Context context, final String process) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
		for (final RunningAppProcessInfo i : list) {
			if (i == null || i.processName == null) {
				continue;
			}

			if (i.processName.equals(process)) {
				Log.w(TAG, "process " + process + " is running");
				return true;
			}
		}

		Log.w(TAG, "process " + process + " is not running");
		return false;
	}

	public static String getTopActivityName(final Context context) {
		try {
			final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			final ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			final String topName = cn.getClassName();
			return topName;

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return "(null)";
	}

	public static boolean isTopApplication(final Context context) {
		try {
			final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			final ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			final String topName = cn.getClassName();
			final String pkgName = context.getPackageName();
			Log.d(TAG, "top activity=" + topName + ", context=" + pkgName);
			return topName.contains(pkgName);

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isIntentAvailable(final Context context, final Intent intent) {
		final PackageManager packageManager = context.getPackageManager();
		final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public static void dumpMemoryUsage() {
		Log.w(TAG, "memory usage: h=" + getSizeKB(Debug.getGlobalAllocSize()) + "|" + getSizeKB(Debug.getGlobalFreedSize()) + ", e=" + getSizeKB(Debug.getGlobalExternalAllocSize()) + "|" + getSizeKB(Debug.getGlobalExternalFreedSize())
				+ ", n=" + getSizeKB(Debug.getNativeHeapAllocatedSize()) + "|" + getSizeKB(Debug.getNativeHeapFreeSize()) + "|" + getSizeKB(Debug.getNativeHeapSize()));
	}

	public static void freeBitmapMap(final Map<String, Bitmap> bitmaps) {
		final Iterator<Entry<String, Bitmap>> iter = bitmaps.entrySet().iterator();
		while (iter.hasNext()) {
			final Entry<String, Bitmap> entry = iter.next();
			final Bitmap bm = entry.getValue();
			if (bm != null) {
				bm.recycle();
			}
		}

		bitmaps.clear();
	}

	public static void selectPicture(final Context context, final int menuValue) {
		final Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		innerIntent.setType("image/*");
		final Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		((Activity) context).startActivityForResult(wrapperIntent, menuValue);
	}

	/**
	 * default value of null
	 * 
	 * @param object
	 * @return
	 */
	public static int nullAsNil(final Integer object) {
		return object == null ? 0 : object;
	}

	public static long nullAsNil(final Long object) {
		return object == null ? 0L : object;
	}

	public static String nullAsNil(final String object) {
		return object == null ? "" : object;
	}

	public static boolean nullAsTrue(final Boolean object) {
		return object == null ? true : object;
	}

	public static boolean nullAsFalse(final Boolean object) {
		return object == null ? false : object;
	}

	public static int nullAs(final Integer object, final int def) {
		return object == null ? def : object;
	}

	public static long nullAs(final Long object, final long def) {
		return object == null ? def : object;
	}

	public static boolean nullAs(final Boolean object, final boolean def) {
		return object == null ? def : object;
	}

	public static String nullAs(final String object, final String def) {
		return object == null ? def : object;
	}

	/**
	 * auto integer or long
	 * 
	 * @param object
	 * @param def
	 * @return
	 */
	public static int nullAsInt(final Object object, final int def) {
		if (object == null) {
			return def;
		}

		if (object instanceof Integer) {
			return (Integer) object;
		}

		if (object instanceof Long) {
			return ((Long) object).intValue();
		}

		return def;
	}

	public static String unescape (String s)
	{
		if(Util.IsEmpty(s))
			return "";
	    while (true)
	    {
	        int n=s.indexOf("&#");
	        if (n<0) break;
	        int m=s.indexOf(";",n+2);
	        if (m<0) break;
	        try
	        {
	            s=s.substring(0,n)+(char)(Integer.parseInt(s.substring(n+2,m)))+
	                s.substring(m+1);
	        }
	        catch (Exception e)
	        {
	            return s;
	        }
	    }
	    s=s.replace("&quot;","\"");
	    s=s.replace("&lt;","<");
	    s=s.replace("&gt;",">");
	    s=s.replace("&amp;","&");
	    return s;
	}
	
	public static boolean IsEmpty(final String object) {
		if ((object == null) || (object.length() <= 0)) {
			return true;
		}
		return false;
	}

	public static boolean IsEmpty(final byte[] object) {
		if ((object == null) || (object.length <= 0)) {
			return true;
		}
		return false;
	}

	public static int getInt(final String string, final int def) {
		try {
			return string == null ? def : Integer.parseInt(string);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return def;
	}

	public static long getLong(final String string, final long def) {
		try {
			return string == null ? def : Long.parseLong(string);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return def;
	}

	public static int getHex(final String string, int def) {
		if (string == null) {
			return def;
		}

		try {
			return (int) (Long.decode(string) & MAX_32BIT_VALUE);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return def;
	}

	/**
	 * ini parser
	 */
	public static Map<String, String> parseIni(final String ini) {
		if (ini == null || ini.length() <= 0) {
			return null;
		}

		final Map<String, String> values = new HashMap<String, String>();

		final String[] lines = ini.split("\n");
		for (final String line : lines) {
			if (line == null || line.length() <= 0) {
				continue;
			}

			final String[] kv = line.trim().split("=", 2);
			if (kv == null || kv.length < 2) {
				continue;
			}

			// key
			final String key = kv[0];
			final String value = kv[1];
			if (key == null || key.length() <= 0 || !key.matches("^[a-zA-Z0-9_]*")) {
				continue;
			}

			values.put(key, value);
		}

		final Iterator<Map.Entry<String, String>> iter = values.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry<String, String> entry = iter.next();
			Log.v(TAG, "key=" + entry.getKey() + " value=" + entry.getValue());
		}
		return values;
	}

	/**
	 * XML parser
	 * 
	 * @param xml
	 * @param tag
	 * @param encode
	 * @return
	 */
	public static Map<String, String> parseXml(final String xml, final String tag, final String encode) {
		if (xml == null || xml.length() <= 0) {
			return null;
		}

		final Map<String, String> values = new HashMap<String, String>();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		if (builder == null) {
			Log.e(TAG, "new Document Builder failed");
			return null;
		}

		Document dom = null;
		try {
			final InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes()));
			if (encode != null) {
				is.setEncoding(encode);
			}
			dom = builder.parse(is);
			dom.normalize();

		} catch (final DOMException e) {
			e.printStackTrace();

		} catch (final SAXException e) {
			e.printStackTrace();
			return null;

		} catch (final IOException e) {
			e.printStackTrace();
			return null;

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}

		if (dom == null) {
			Log.e(TAG, "new Document failed");
			return null;
		}

		final Element root = dom.getDocumentElement();
		if (root == null) {
			Log.e(TAG, "getDocumentElement failed");
			return null;
		}

		if (tag != null && tag.equals(root.getNodeName())) { // fix 4.0 xml parse error
			putAllNode(values, "", root, 0);

		} else {
			final NodeList items = root.getElementsByTagName(tag);
			if (items.getLength() <= 0) {
				Log.e(TAG, "parse item null");
				return null;
			}
			if (items.getLength() > 1) {
				Log.w(TAG, "parse items more than one");
			}
			putAllNode(values, "", items.item(0), 0);
		}

		final Iterator<Map.Entry<String, String>> iter = values.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry<String, String> entry = iter.next();
			Log.v(TAG, "key=" + entry.getKey() + " value=" + entry.getValue());
		}
		return values;
	}

	private static void putAllNode(final Map<String, String> values, String prefix, final Node node, final int indexOf) {
		if (node.getNodeName().equals("#text")) {
			values.put(prefix, node.getNodeValue());

		} else if (node.getNodeName().equals("#cdata-section")) {
			values.put(prefix, node.getNodeValue());

		} else {
			prefix += "." + node.getNodeName() + ((indexOf > 0) ? indexOf : "");
			values.put(prefix, node.getNodeValue());

			// process attributes
			final NamedNodeMap properties = node.getAttributes();
			if (properties != null) {
				for (int i = 0; i < properties.getLength(); i++) {
					final Node p = properties.item(i);
					values.put(prefix + ".$" + p.getNodeName(), p.getNodeValue());
				}
			}

			// process sub node
			final HashMap<String, Integer> conflicts = new HashMap<String, Integer>();
			final NodeList items = node.getChildNodes();
			for (int i = 0; i < items.getLength(); i++) {
				final Node s = items.item(i);
				final int no = Util.nullAsNil(conflicts.get(s.getNodeName()));
				putAllNode(values, prefix, s, no);
				conflicts.put(s.getNodeName(), no + 1);
			}
		}
	}

	/**
	 * @param from
	 * @param to
	 */
	public static void transClickToSelect(final View from, final View to) {
		from.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(final View v, final MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					to.setSelected(true);
					break;

				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_OUTSIDE:
					to.setSelected(false);
					break;

				case MotionEvent.ACTION_MOVE:
					to.setSelected(from.isPressed());
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * MD5 password
	 */
	private static final int NEW_QQ_PASSWORD_MAX = 16;

	public static String getCutPasswordMD5(String rawPsw) {
		if (rawPsw == null) {
			rawPsw = "";
		}

		if (rawPsw.length() <= NEW_QQ_PASSWORD_MAX) {
			return getFullPasswordMD5(rawPsw);
		}

		return getFullPasswordMD5(rawPsw.substring(0, NEW_QQ_PASSWORD_MAX));
	}

	public static String getFullPasswordMD5(final String psw) {
		return MD5.getMessageDigest(psw.getBytes());
	}

	public static Element getRootElementFromXML(byte[] buf) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		if (builder == null) {
			Log.e(TAG, "new Document Builder failed");
			return null;
		}

		Document dom = null;
		try {
			dom = builder.parse(new ByteArrayInputStream(buf));
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		if (dom == null) {
			Log.e(TAG, "new Document failed");
			return null;
		}

		return dom.getDocumentElement();
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * encodeByteArray �� decodeString �ɶ�ʹ��
	 * 
	 * @param
	 * @return
	 */
	public static String encodeHexString(final byte[] ba) {
		StringBuilder out = new StringBuilder("");
		if (ba != null) {
			for (int i = 0; i < ba.length; i++) {
				out.append(String.format("%02x", ba[i] & MASK_8BIT));
			}
		}

		return out.toString();
	}

	public static byte[] decodeHexString(final String in) {
		if (in == null || in.length() <= 0) {
			return new byte[0];
		}

		try {
			byte[] buf = new byte[in.length() / 2];
			final int cRadix = 16;
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (Integer.parseInt(in.substring(i * 2, (i * 2 + 2)), cRadix) & MASK_8BIT);
			}
			return buf;

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return new byte[0];
	}

	private static final int TCP_HDR_FIX = 52;
	private static final int TCP_HDR_FIX_TOO = 40; // a balance value to control the netstat approach to real
	private static final int TCP_TX_FIX_BYTES = 60 * 2 + TCP_HDR_FIX; // connection over tcp
	private static final int TCP_RX_FIX_BYTES = TCP_HDR_FIX * 3; // disconnectiton over tcp
	private static final int MTU = 1514 - TCP_HDR_FIX;

	public static int guessHttpSendLength(final int i) {
		return TCP_TX_FIX_BYTES + TCP_HDR_FIX + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessHttpRecvLength(final int i) {
		return TCP_RX_FIX_BYTES + TCP_HDR_FIX + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessHttpContinueRecvLength(final int i) {
		return TCP_HDR_FIX + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessTcpConnectLength() {
		return TCP_TX_FIX_BYTES;
	}

	public static int guessTcpDisconnectLength() {
		return TCP_RX_FIX_BYTES;
	}

	public static int guessTcpSendLength(final int i) {
		return TCP_HDR_FIX_TOO + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessTcpRecvLength(final int i) {
		return TCP_HDR_FIX_TOO + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int[] splitToIntArray(final String text) {
		if (text == null) {
			return null;
		}

		final String[] va = text.split(":");

		final List<Integer> o = new ArrayList<Integer>();
		for (final String v : va) {
			if (v == null || v.length() <= 0) {
				continue;
			}

			try {
				final int i = Integer.valueOf(v);
				o.add(i);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, "invalid port num, ignore");
			}
		}

		final int[] oa = new int[o.size()];
		for (int i = 0; i < oa.length; i++) {
			oa[i] = o.get(i);
		}
		return oa;
	}

	public static int UnZipFolder(String zipFileString, String outPathString) {

		try {
			android.util.Log.v("XZip", "UnZipFolder(String, String)");
			java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
			java.util.zip.ZipEntry zipEntry;
			String szName = "";

			while ((zipEntry = inZip.getNextEntry()) != null) {
				szName = zipEntry.getName();

				if (zipEntry.isDirectory()) {

					// get the folder name of the widget
					szName = szName.substring(0, szName.length() - 1);
					java.io.File folder = new java.io.File(outPathString + java.io.File.separator + szName);
					folder.mkdirs();

				} else {

					java.io.File file = new java.io.File(outPathString + java.io.File.separator + szName);
					file.createNewFile();
					// get the output stream of the file
					java.io.FileOutputStream out = new java.io.FileOutputStream(file);
					int len;
					byte[] buffer = new byte[1024];
					// read (len) bytes into buffer
					while ((len = inZip.read(buffer)) != -1) {
						// write (len) byte from buffer at the position 0
						out.write(buffer, 0, len);
						out.flush();
					}
					out.close();
				}
			}// end of while

			inZip.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
		return 0;
	}

	public static String getStack() {
		StackTraceElement[] stes = new Throwable().getStackTrace();
		if ((stes == null) || (stes.length < 2)) {
			return "";
		}

		String t = "";

		for (int i = 1; i < stes.length; i++) {
			if (!stes[i].getClassName().contains("com.btten")) {
				break;
			}
			t += "[" + stes[i].getClassName().substring("com.btten.".length()) + ":" + stes[i].getMethodName() + "]";
		}
		return t;
	}

	private final static int SDCARD_WARNING_PENCENT = 95;
	private final static int SDCARD_MIN_SIZE = 50 * 1024 * 1024;

	public static boolean checkSDCardFull() {
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			return false;
		}
		File sdcardDir = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(sdcardDir.getPath());
		long blockCount = sf.getBlockCount();
		long availCount = sf.getAvailableBlocks();
		if (blockCount <= 0) {
			return false;
		}
		if (blockCount - availCount < 0) {
			return false;
		}
		int per = (int) ((blockCount - availCount) * 100 / blockCount);
		long availSize = ((long) sf.getBlockSize()) * sf.getFreeBlocks();
		Log.d(TAG, "checkSDCardFull per:" + per + " blockCount:" + blockCount + " availCount:" + availCount + " availSize:" + availSize);
		if (SDCARD_WARNING_PENCENT > per) {
			return false;
		}
		if (availSize > SDCARD_MIN_SIZE) {
			return false;
		}
		return true;
	}

	public static String GetHostIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements();) {
					InetAddress inetAddress = ipAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
		} catch (Exception e) {
		}
		return null;
	}

	public static boolean checkPermission(final Context context, final String perm) {
		Assert.assertNotNull(context);
		final String pkgName = context.getPackageName();
		final boolean ret = PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(perm, pkgName);
		Log.d(TAG, pkgName + " has " + (ret ? "permission " : "no permission ") + perm);
		return ret;
	}

	public static boolean jump(final Context context, final String url) {
		final Intent jump = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		if (!isIntentAvailable(context, jump)) {
			Log.e(TAG, "jump to url failed, " + url);
			return false;
		}

		context.startActivity(jump);
		return true;
	}

	// unicode ranges and valid/invalid characters
	private static final char LOWER_RANGE = 0x20;
	private static final char UPPER_RANGE = 0x7f;
	private static final char[] VALID_CHARS = { 0x9, 0xA, 0xD };

	private static final char[] INVALID = { '<', '>', '"', '\'', '&' };
	private static final String[] VALID = { "&lt;", "&gt;", "&quot;", "&apos;", "&amp;" };

	/**
	 * Escape a string such that it is safe to use in an XML document.
	 * 
	 * @param str
	 *            the string to escape
	 */
	public static String escapeStringForXml(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer out = new StringBuffer();
		int len = str.length();
		for (int i = 0; i < len; ++i) {
			char c = str.charAt(i);

			if ((c < LOWER_RANGE && c != VALID_CHARS[0] && c != VALID_CHARS[1] && c != VALID_CHARS[2]) || (c > UPPER_RANGE)) {
				// character out of range, escape with character value
				out.append("&#");
				out.append(Integer.toString(c));
				out.append(';');
			} else {
				boolean valid = true;
				// check for invalid characters (e.g., "<", "&", etc)
				for (int j = INVALID.length - 1; j >= 0; --j) {
					if (INVALID[j] == c) {
						valid = false;
						out.append(VALID[j]);
						break;
					}
				}
				// if character is valid, don't escape
				if (valid) {
					out.append(c);
				}
			}
		}
		return out.toString();
	}
	/*	将传入的int型数字转化为Date型，并与当前系统时间进行对比。
	 * 若在15分钟内则显示“刚刚”
	 * 若在一小时内显示多少分钟前
	 * 若在一天内，显示多少小时前
	 * 若在一周内，几天前
	 * 本年内，几月几日
	 * 其他则为几年几月几日
	 * 
	 * */
	public static String convertTime(long time){
		Calendar calendar=Calendar.getInstance();
		Date date=new Timestamp(time*1000);
		Date ndate=calendar.getTime();

		long mitime=ndate.getTime()-date.getTime();
		long minutes=mitime/1000/60;
		long hours=mitime/1000/3600;
		long days=mitime/1000/3600/24;

		 
		if(minutes<15&&hours==0&&days==0) return "刚刚";
		else if(minutes<60&&hours==0&&days==0) return minutes+"分钟前";
		else if(days==0) return hours+"小时前";
		else if(days==1) return "昨天";
		else if(days==2) return "前天";
		else if(days<7) return days+"天前";
		
		else if(date.getYear()==ndate.getYear()) return (date.getMonth()+1)+"月"+date.getDate()+"日";
		else return (date.getYear()+1900)+"年"+date.getMonth()+"月"+date.getDay()+"日";
		
	}

	public static boolean isEmail(String str){
	//	String strPattern = "^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$";
		Pattern p = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	//字母，数字，下划线，6——18位.用户名和密码都采用此验证方法
	public static boolean isUsername(String name){
		
		Pattern p = Pattern.compile("^[a-zA-Z0-9_]{6,18}$",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name);
		return m.matches();
	}
	
	public static String getAppVersionName(Context context) {      
	    String versionName = "";       
	    try {      
	        // ---get the package info---      
	        PackageManager pm = context.getPackageManager();      
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(),0);      
	        versionName = pi.versionName;      
	        if (versionName == null || versionName.length() <= 0) {      
	            return "";      
	        }      
	    } catch (Exception e) {      
	        Log.e("VersionInfo",e.toString());      
	    }      
	    return versionName;      
	}    
    public static  byte[] Bitmap2Bytes(Bitmap bm) {
    	try {
    		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    	        return baos.toByteArray(); 
		} catch (Exception e) {
			return null;
		}
  	      
    }
    //替换特殊字符，解决TextView排版不整齐的缺陷
    public static String StringFilter(String str) throws PatternSyntaxException{
        str=str.replaceAll("【","[").replaceAll("】","]").replaceAll("！","!").replaceAll("：", ":");//替换中文标号
        String regEx="[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
     return m.replaceAll("").trim();
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){   
        // 获得图片的宽高   
        int width = bm.getWidth();   
        int height = bm.getHeight();   
        // 计算缩放比例   
        float scaleWidth = ((float) newWidth) / width;   
        float scaleHeight = ((float) newHeight) / height;   
        // 取得想要缩放的matrix参数   
        Matrix matrix = new Matrix();   
        matrix.postScale(scaleWidth, scaleHeight);   
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);   
        return newbm;   
    }  
    
    public static String bitmaptoString(Bitmap bitmap){
	    //将Bitmap转换成字符串
	    String string=null;
	    if(bitmap==null){
	    	return "";
	    }
	    ByteArrayOutputStream bStream=new ByteArrayOutputStream();
	    bitmap.compress(CompressFormat.PNG,100,bStream);
	    byte[]bytes=bStream.toByteArray();
	    string=Base64.encodeToString(bytes,Base64.DEFAULT);
	    return string;
    }
}
