package com.iawu.network;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public abstract class HttpSceneBase extends NetSceneBase<String> {
	public HttpSceneBase(boolean isPost) {
		super(isPost);
	}

	@Override
	protected String doReceive(HttpEntity httpEntity) 
    {
		byte[] bytes=null;
		try {
			bytes = EntityUtils.toByteArray(httpEntity);
		} catch (IOException e) {
			 
		}
		if(bytes==null)
			return "";
		String str=new String(bytes);
		return str;
	}

	



	 

	 

}
