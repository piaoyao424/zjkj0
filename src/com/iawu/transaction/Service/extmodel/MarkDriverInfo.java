package com.iawu.transaction.Service.extmodel;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

public class MarkDriverInfo  implements SerializableSFSType{
	//0是出租车，1是货车
	 int type;
	 //编码后的值
	 String LonLat;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLonLat() {
		return LonLat;
	}
	public void setLonLat(String lonLat) {
		LonLat = lonLat;
	}
	public MarkDriverInfo()
	{
		
	}
}
