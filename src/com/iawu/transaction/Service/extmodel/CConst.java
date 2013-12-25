package com.iawu.transaction.Service.extmodel;

public  class CConst {
	
	public static final String HeartBit="H";
	public static final String RequestNearbyResult="NearByR";
	public static final String RequestNearby="NearBy";
	public static final String RequestMark="Mark";
	public static final String RequestUpdate="Update";
	public static final String RequestUnMark="UnMark";
	public static final String RequestCallTaxi="CallTaxi";
	public static final String RequestCallInfo="CallInfo";
	public static final String RequestAccept="Accept";
	public static final String RequestGrabResult="GrabResult";
	public static final String RequestTaxiPairInfo="TaxiPairInfo";
	public static final String RequestAnswer="Answer";
	public static final long RequestDelayTime=1000*100;//100秒
	public static final long ServerRequestDelayTime=1000*95;//95秒 服务器是短一点
	

	/*
	 // sfsClient.connect("192.168.0.131", 9933);
			 sfsClient.connect("192.168.1.101", 9933);
			//	sfsClient.connect("110.76.44.81", 9933);
	 * */
	//public static final String CallTaxiServerIP="www.360guanggu.com";
	public static final String CallTaxiServerIP="59.175.148.125";
//	public static final String CallTaxiServerIP="192.168.1.52";
	public static final int CallTaxiServerPort=9933;
	
	
	public static String GenSID()
	{
		return java.util.UUID.randomUUID().toString();
	}
	
}
