package com.iawu.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

/**
 * @author GongMing
 * 上传图片Bitmap到服务器中
 */
public class UploadUtils {
	
	OnUploadCallBack callback;
	String TAG="UploadUtils";
	
	public UploadUtils(OnUploadCallBack callback,Bitmap bitmap,String data,String aciton,String ...args){
		this.callback=callback;
		doUpload(bitmap,data, aciton ,args);
	}
	
	private void doUpload(Bitmap bitmap,String data, String aciton, String ...args){
		URL url;
		try {
			url = new URL(UrlFactory.GetUrlNew(data, aciton, args));
			Log.i(TAG,url.toString());
			uploadFile(bitmap,url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			callback.onFailure();
			e.printStackTrace();
			return;
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			callback.onFailure();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			callback.onFailure();
			return;
		}
	}
	
	private void uploadFile(Bitmap bitmap,URL url) throws InterruptedException, IOException {
	{
      
		int timeout=30000;
        HttpURLConnection httpUrlConnection = null;
		try {
			httpUrlConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			callback.onFailure();
			return;
		}
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setConnectTimeout(timeout);
        httpUrlConnection.setReadTimeout(timeout);
        httpUrlConnection.setRequestProperty("Content-Type", "binary/octet-stream");
        try {
			httpUrlConnection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			callback.onFailure();
			return;
		}
        OutputStream os = null;
		try {
			os = httpUrlConnection.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			callback.onFailure();
			return;
		}

       //Thread.sleep(100);
		
	    bitmap.compress(CompressFormat.PNG,100,os);
	    os.flush();
	    os.close();
	    
        int responMsg = httpUrlConnection.getResponseCode();
        
        if(responMsg==HttpURLConnection.HTTP_OK){
        	/*InputStream ins = httpUrlConnection.getInputStream();
        	BufferedReader br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
        	StringBuffer str = new StringBuffer("");
        	String temp;
        	while ((temp = br.readLine()) != null){
        		str.append(temp);
        	}
        		Log.i("yexinTAG", str.toString());*/
        		
        	callback.onSuccess();
        }
        else if (responMsg != HttpURLConnection.HTTP_OK)
        {
        	callback.onFailure();
        }
		  
		}
	}
}
