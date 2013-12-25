package com.iawu.transaction.Service.extmodel;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

public class AcceptInfo implements SerializableSFSType{

	

	//司机车牌号 
	String carnum;
	//司机手机号 
	String driverphone;
	//司机姓名 
	String drivername;
	
	int drivertype;

	public int getDrivertype() {
		return drivertype;
	}

	public void setDrivertype(int drivertype) {
		this.drivertype = drivertype;
	}

	//当前订单的id
	String orderid;
	
	//当前order的type
	int ordertype;
		
	//*******************下面是司机的id号，上层不用管理***********/
	String tid;
	
	public AcceptInfo()
	{
		
	}
	
	public int getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}

	public String getCarnum() {
		return carnum;
	}

	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}

	public String getDriverphone() {
		return driverphone;
	}

	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}
