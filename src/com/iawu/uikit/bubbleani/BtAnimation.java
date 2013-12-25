package com.iawu.uikit.bubbleani;
 

import android.view.View;
import android.view.animation.AnimationUtils;

//自定义的动画基类
public abstract class BtAnimation {
	
	   //正在运行中的动画的个数
		private static int mRunningCount=0;
	
		public static boolean IsHasAnimationRuning()
		{
			return mRunningCount!=0;
		}
		
		View view;
		private Runnable mDynamicsRunnable;
		public BtAnimation(View view)
		{
			this.view=view;	
		}
		//动画开始的时间，按ms计算
		long mAniStartTime=0;
		long mAniLastTime=0;
		public void Start()
		{
			if(mIsRunning)
				return;
			mIsRunning=true;
			increase();
			 
			 if (mDynamicsRunnable == null) {
		            mDynamicsRunnable = new Runnable() {
		                @Override
						public void run() {
		                	long currentTime= AnimationUtils.currentAnimationTimeMillis();
		                	
		                	doAnimation(currentTime-mAniStartTime,allDuration,currentTime-mAniLastTime);
		                	
		                	if((!IsNeedStop())&&mIsRunning)
		                	{
		                		 view.postDelayed(mDynamicsRunnable,frequency);
		                	}else
		                	{
		                		doAnimationEnd();
		                		mIsRunning=false;
		                		decrease();
		                		view.removeCallbacks(mDynamicsRunnable);
		            			mDynamicsRunnable=null;
		                		 
		                	}
		                	mAniLastTime=currentTime;
		                	
		                }
		            };
			 }
			 mAniStartTime=AnimationUtils.currentAnimationTimeMillis();
			 mAniLastTime=mAniStartTime;
			 view.post(mDynamicsRunnable);
		}
		//long dTime 是指距离上次的时间，cuTime是指经过了多少时间
		protected abstract void doAnimation(long cuTime,long allTime,long dTime) ;
		
		protected abstract void doAnimationEnd() ;
		
		//是否到了停下来的时候
		protected abstract boolean IsNeedStop();
		
		public void Stop()
		{
			if(!mIsRunning)
				return;
			mIsRunning=false;
			decrease();
			view.removeCallbacks(mDynamicsRunnable);
			mDynamicsRunnable=null;
		}
		
		
		private boolean mIsRunning=false;
		public boolean IsRunning()
		{
			return mIsRunning;
		}
		
		//默认频率是10次 秒
		int frequency=100;
	
		//动画要运行的时间
		long allDuration=300;
		public void SetDuration(long duration)
		{
			allDuration=duration;
		}
		
		//如果不想把动画加入count计算，请传入false,
		public void SetNeedCount(boolean isNeed)
		{
			isNeedCount=isNeed;
		}
		//设置是否加入 count计算中
		boolean isNeedCount=true;
		
		//保证一个实例只会加一次
		boolean isincrease=false;
		private void increase()
		{
			if(isincrease)
				return;
			//不计算的话 就直接返回
			if(!isNeedCount)
				return;
			
			mRunningCount++;
			isincrease=true;
		}
		private void decrease()
		{
			if(!isincrease)
				return;
			
			//不计算的话 就直接返回
			if(!isNeedCount)
				return;
			
			mRunningCount--;
			isincrease=false;
		}

}
