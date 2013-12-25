package com.iawu.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.text.format.DateFormat;

import com.iawu.algorithm.MD5;
import com.iawu.algorithm.TypeTransform;

/**
 * 
 * @author kirozhao
 * 
 */
public class Log {
	private static final String TAG = "BTCore.Log";

	public static final String SDCARD_LOG_PATH = "/sdcard/btten/";
	public static final String MM_LOG = "mm.log";
	public static final String PUSH_LOG = "push.log";
	public static final String SYS_LOG = "sys.log";
	// public static final String CRASH_LOG = "crash.log";

	public static final int LEVEL_VERBOSE = 0;
	public static final int LEVEL_DEBUG = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_WARNING = 3;
	public static final int LEVEL_ERROR = 4;
	public static final int LEVEL_NONE = 5;

	private static int level = LEVEL_VERBOSE;

	private static String path;
	private static PrintStream p;
	private static long logCreateTime = 0;
	private static byte[] desKey = null;

	
	protected Log() {

	}

	// removed in 4.0
	// public static native boolean setLogLevel(int level);

	// public static voidW setOutputStream(OutputStream os) {
	// Log.p = new PrintStream(os);
	// }

	public static void setOutputPath(String dirPath,String path,int clientver) {
		
		String logType="normallog";
		String username="bttenlog";
		 
		
		if (path == null || path.length() == 0 || username == null || username.length() == 0) {
			return;
		}

		try {
			{
				File dir = new File(dirPath);
				//判断文件夹是否存在,如果不存在则创建文件夹
				if (!dir.exists()) {
					dir.mkdir();
				}
			}
			path= dirPath+path;
			Log.path = path;
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}

			Log.p = new PrintStream(new BufferedOutputStream(new FileOutputStream(path)));
			// Log.p = new PrintStream(path);
			if (file.length() == 0) { // new file
				logCreateTime = System.currentTimeMillis();
				LogHelper.initLogHeader(p, logType, username, logCreateTime, clientver);
				genDesKey(username, logCreateTime);
			} else {
				initCreateTime();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reset() {
		path = null;
		p = null;
		// username = null;
		desKey = null;
	}

	private static void initCreateTime() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			reader.readLine();
			logCreateTime = Long.parseLong(reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setLevel(final int level, final boolean jni) {
		Log.level = level;
		android.util.Log.w(TAG, "new log level: " + level);

		if (jni) {
			// setLogLevel(level);
			android.util.Log.e(TAG, "no jni log level support");
		}
	}

	public static int getLevel() {
		return level;
	}

	public static Boolean IsDebug()
	{
		if(level <= LEVEL_DEBUG)
			return true;
		return false;
	}
	public static void e(final String tag, final String msg) {
		if (level <= LEVEL_ERROR) {
			android.util.Log.e(tag, msg);
			LogHelper.writeToStream(p, desKey, "E/" + tag, msg+"\n");
		}
	}
	public static void Exception(final String tag, Exception ex) {
		 e(tag,ex.getMessage()+":\n"+ex.getStackTrace()+"\n");
	}

	public static void w(final String tag, final String msg) {
		if (level <= LEVEL_WARNING) {
			android.util.Log.w(tag, msg);
			LogHelper.writeToStream(p, desKey, "W/" + tag, msg+"\n");
		}
	}

	public static void i(final String tag, final String msg) {
		if (level <= LEVEL_INFO) {
			android.util.Log.i(tag, msg);
			LogHelper.writeToStream(p, desKey, "I/" + tag, msg+"\n");
		}
	}

	public static void d(final String tag, final String msg) {
		if (level <= LEVEL_DEBUG) {
			android.util.Log.d(tag, msg);
			LogHelper.writeToStream(p, desKey, "D/" + tag, msg+"\n");
		}
	}

	public static void v(final String tag, final String msg) {
		if (level <= LEVEL_VERBOSE) {
			android.util.Log.v(tag, msg);
			LogHelper.writeToStream(p, desKey, "V/" + tag, msg+"\n");
		}
	}

	private static void genDesKey(String username, long logCreateTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(username);
		sb.append(logCreateTime);
		sb.append("dfdhgc");
		desKey = (MD5.getMessageDigest(sb.toString().getBytes())).substring(7, 21).getBytes();
	}

	private static final String SYS_INFO;
	static {
		final StringBuilder sb = new StringBuilder();
		sb.append("VERSION.RELEASE:[" + android.os.Build.VERSION.RELEASE);
		sb.append("]\nVERSION.CODENAME:[" + android.os.Build.VERSION.CODENAME);
		sb.append("]\nVERSION.INCREMENTAL:[" + android.os.Build.VERSION.INCREMENTAL);
		sb.append("]\nBOARD:[" + android.os.Build.BOARD);
		sb.append("]\nDEVICE:[" + android.os.Build.DEVICE);
		sb.append("]\nDISPLAY:[" + android.os.Build.DISPLAY);
		sb.append("]\nFINGERPRINT:[" + android.os.Build.FINGERPRINT);
		sb.append("]\nHOST:[" + android.os.Build.HOST);
		sb.append("]\nMANUFACTURER:[" + android.os.Build.MANUFACTURER);
		sb.append("]\nMODEL:[" + android.os.Build.MODEL);
		sb.append("]\nPRODUCT:[" + android.os.Build.PRODUCT);
		sb.append("]\nTAGS:[" + android.os.Build.TAGS);
		sb.append("]\nTYPE:[" + android.os.Build.TYPE);
		sb.append("]\nUSER:[" + android.os.Build.USER + "]");

		SYS_INFO = sb.toString();
	}

	public static String getSysInfo() {
		return SYS_INFO;
	}
}

final class LogHelper {

	private LogHelper() {
	}

	public static void writeToStream(final PrintStream stream, final String tag, final String msg) {
		if (stream == null || Util.IsEmpty(tag) || Util.IsEmpty(msg)) {
			return;
		}

		String plainText = Calendar.getInstance().getTime().toGMTString() + " " + tag + " " + msg;
		try {
			stream.write(plainText.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		stream.flush();
	}

	private static final String FORMAT = "MM-dd kk:mm:ss";
	private static final boolean IsEncryption = false;

	// DES encryption, 'LV' to serial
	public static void writeToStream(final PrintStream stream, byte[] desKey, final String tag, final String msg) {

		if (stream == null || Util.IsEmpty(desKey) || Util.IsEmpty(tag) || Util.IsEmpty(msg)) {
			return;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(DateFormat.format(FORMAT, System.currentTimeMillis()));
		sb.append(" ").append(tag).append(" ").append(msg);

		String plainText = sb.toString();

		//������
		if(!IsEncryption)
		{
			try {
				stream.write(plainText.getBytes());
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			stream.flush();
			return;
		}
		
		try {
			// DES.DESEncrypt(cipherText, plainText.getBytes(), desKey);

			// SecureRandom sr = new SecureRandom();
			// ��ԭʼ�ܳ���ݴ���DESKeySpec����
			DESKeySpec dks = new DESKeySpec(desKey);
			// ����һ���ܳ׹�����Ȼ�������DESKeySpecת����
			// һ��SecretKey����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher����ʵ����ɼ��ܲ���
			Cipher cipher = Cipher.getInstance("DES");
			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			// ���ڣ���ȡ��ݲ�����
			// ��ʽִ�м��ܲ���
			byte[] cipherText = cipher.doFinal(plainText.getBytes());

			stream.write(TypeTransform.intToByteArrayLH(cipherText.length));
			stream.write(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		stream.flush();
	}

	public static void initLogHeader(final PrintStream stream, final String logType, final String username, final long logCreateTime, final int clientver) {
		if (stream == null || Util.IsEmpty(username) || logCreateTime == 0) {
			return;
		}

		int count = 1;
		stream.println((count++) + " " + logType);
		stream.println((count++) + " " + username);
		stream.println((count++) + " " + logCreateTime);
		stream.println((count++) + " " + Integer.toHexString(clientver));
		stream.println((count++) + " " + android.os.Build.VERSION.RELEASE);
		stream.println((count++) + " " + android.os.Build.VERSION.CODENAME);
		stream.println((count++) + " " + android.os.Build.VERSION.INCREMENTAL);
		stream.println((count++) + " " + android.os.Build.BOARD);
		stream.println((count++) + " " + android.os.Build.DEVICE);
		stream.println((count++) + " " + android.os.Build.DISPLAY);
		stream.println((count++) + " " + android.os.Build.FINGERPRINT);
		// stream.println((count++) + " " + android.os.Build.HARDWARE); // for 1.6 compatible
		stream.println((count++) + " " + android.os.Build.HOST);
		stream.println((count++) + " " + android.os.Build.MANUFACTURER);
		stream.println((count++) + " " + android.os.Build.MODEL);
		stream.println((count++) + " " + android.os.Build.PRODUCT);
		// stream.println((count++) + " " + android.os.Build.RADIO); // for 1.6 compatible
		// p.println((count++) + " " + android.os.Build.SERIAL); // throw exception
		stream.println((count++) + " " + android.os.Build.TAGS);
		stream.println((count++) + " " + android.os.Build.TYPE);
		stream.println((count++) + " " + android.os.Build.USER);
	//	stream.println(Log.getSysInfo());
		stream.println(); // newline ���� end of log header
		stream.flush();
	}
}
