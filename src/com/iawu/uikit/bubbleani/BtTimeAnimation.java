package com.iawu.uikit.bubbleani;

import android.view.View;

public abstract class BtTimeAnimation extends BtAnimation {

	public BtTimeAnimation(View view) {
		super(view);
	}

	@Override
	protected  void doAnimation(long cuTime, long allTime,long dTime) 
	{
		doAnimation(cuTime);
	}

	protected  abstract void doAnimation(long cuTime) ;
	
	@Override
	protected  void doAnimationEnd()
	{
		
	}

	@Override
	protected boolean IsNeedStop() {
		return false;
	}

}
