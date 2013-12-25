package com.iawu.transaction.Service.extmodel;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

public class TaxiPairInfo  implements SerializableSFSType {
	public String SID;
	public String TID;//taxi id
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public TaxiPairInfo()
	{
		
	}
}
