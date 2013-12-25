package com.iawu.msgcenter;

public class MsgConst {
	//所有事件的申明
	//得到最热商品成功
	
	
	public static int MSG_START=1;
	public static final int GET_HOT_SUCCESS=MSG_START++;
	public static final int GET_NEW_SUCCESS=MSG_START++;
	public static final int LOGIN_SUCCESS=MSG_START++;
    public static final int REGIST_SUCCESS=MSG_START++;
	public static final int SHOP_RESERVE_CHANGE=MSG_START++;
	public static final int GOOD_RESERVE_CHANGE=MSG_START++;
	//店铺评论成功
	public static final int SHOP_COMMENT_SUCCESS=MSG_START++;
	//商品评论成功
	public static final int GOOD_COMMENT_SUCCESS=MSG_START++;
	
	
	
	public static final int MSG_END=MSG_START++;
	
	
	
	
	 
	
}
