package com.iawu.transaction.Service.extmodel;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

public class CallTaxiInfo implements SerializableSFSType{

	 //请求时的type(预约出租车 0、预约货车 1、现在叫出租车 2、现在叫货车 3)
	 int type;
	 //加价
	 String addmoney;
	 //等待时间
	 String waittime;
	 double lon;
	 double lat;
	 String phone;
	 //需要时间
	 String needtime;
	 //出发地
	 String startloc; 
	//目的地
	 String endloc; 
	//货车
	String cargo; 
	//语音链接
	String voicelink;
	//用户妮称
	String nickname;
		 
		 
	///////////////////////////不用管的变量/////////////////////////////
	//请求client的ID，
	String CID;
	//请求的server的id
	 String SID;
	//userid
	 String UID;
	//请求的时间
	 long requestTime;
	 //编码后的值
	 String LonLat;
	 
	 String LoginID;
	
	 public String getLoginID() {
		return LoginID;
	}
	public void setLoginID(String loginID) {
		LoginID = loginID;
	}
	public CallTaxiInfo()
		{
			
		}
	 public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAddmoney() {
		return addmoney;
	}
	public void setAddmoney(String addmoney) {
		this.addmoney = addmoney;
	}
	public String getWaittime() {
		return waittime;
	}
	public void setWaittime(String waittime) {
		this.waittime = waittime;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getLonLat() {
		return LonLat;
	}
	public void setLonLat(String lonLat) {
		LonLat = lonLat;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNeedtime() {
		return needtime;
	}
	public void setNeedtime(String needtime) {
		this.needtime = needtime;
	}
	public String getStartloc() {
		return startloc;
	}
	public void setStartloc(String startloc) {
		this.startloc = startloc;
	}
	public String getEndloc() {
		return endloc;
	}
	public void setEndloc(String endloc) {
		this.endloc = endloc;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getVoicelink() {
		return voicelink;
	}
	public void setVoicelink(String voicelink) {
		this.voicelink = voicelink;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCID() {
		return CID;
	}
	public void setCID(String cID) {
		CID = cID;
	}
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public long getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}
	
	  
	   	
}
