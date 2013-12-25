package com.iawu.network;

import java.io.InputStream;

import org.apache.http.HttpEntity;

import com.iawu.tools.Log;




public abstract class ImgSceneBase  extends NetSceneBase<InputStream>
{
	public ImgSceneBase() {
		super(false);
		// TODO Auto-generated constructor stub
	}
	private static final String TAG = "ImgSceneBase";
	@Override
	protected InputStream doReceive(HttpEntity httpEntity) {
		 // 通过HttpEntiy.getContent得到一个输入流   
        InputStream inputStream=null;
		try {
			inputStream = httpEntity.getContent();
		} catch (Exception e) {
			Log.Exception(TAG, e);
		}  
        return inputStream;
	}


}
