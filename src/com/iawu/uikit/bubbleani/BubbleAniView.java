package com.iawu.uikit.bubbleani;


import com.iawu.xq.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BubbleAniView extends View {

	public BubbleAniView(Context context) {
		super(context);
		Init();
		 
	}
	public BubbleAniView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init();
	}
	
	private static Bitmap mico_light;
	
	BtTimeAnimation animation;
	long currentTime;
	 Paint paint;
	private void Init() {
		currentTime=0;
		paint=new Paint();
		if(mico_light==null)
		{
			Resources res=getContext().getResources();
			mico_light=BitmapFactory.decodeResource(res, R.drawable.mico_light);
		}
		
		animation=new BtTimeAnimation(this) {
			@Override
			protected void doAnimation(long cuTime) {
				 currentTime=cuTime;
				 BubbleAniView.this.invalidate();
			}
		};
		 
	}
	
	public void Start()
	{
		animation.Start();
	}
	public void Stop()
	{
		animation.Stop();
		invalidate();
	}
	
	public Boolean IsRunning()
	{
		return animation.IsRunning();
	}

	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		if(mico_light==null)
			return;
		
		if(getVisibility()!=View.VISIBLE)
			return;
		if(animation.IsRunning())
		{
			//ÿ100msһ֡ ,ֻ��10֡
			int frame=(int) (currentTime/100);
			
			
			frame=frame%10;
			drawAni(canvas,frame);
		}
	}
	double radios[]={0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4};
	int alphas[]={55,95,135,175,215,255,215,175,135,95};
	
	double radios1[]={0.4,05,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3};
	int alphas1[]={15,55,95,135,175,215,255,215,175,135};
	private void drawAni(Canvas canvas,int cuFrame)
	{
		int w=getWidth();
		int h=getHeight();
		double radio=radios[cuFrame];
		int alpha=alphas[cuFrame];
		drawBubble(w/2,h/2,radio,canvas,alpha);
		
		 radio=radios1[cuFrame];
		 alpha=alphas1[cuFrame];
		drawBubble(w/2,h/2,radio,canvas,alpha);
	}
	
	
	private void drawBubble(int centerx,int centery,double radio,Canvas canvas,int alpha)
	{
		 
		 int bmpw=mico_light.getWidth();
		 int bmph=mico_light.getHeight();
		 
		 int draww=(int) (bmpw*radio);
		 int drawh=(int) (bmph*radio);
		 
		 if(drawh<=0||draww<=0)
			 return;
		 
		 Rect dst = new Rect();
		 dst.left=centerx-(draww/2);
		 dst.right=dst.left+draww;
		 dst.top=centery-(drawh/2);
		 dst.bottom=dst.top+drawh;
		 Rect src = new Rect();// ͼƬ
		 
		 src.left = 0;   //0,0  
	     src.top = 0;
	     src.right =bmpw;
	     src.bottom = bmph;     
	    
	     paint.setAlpha(alpha);
		 canvas.drawBitmap(mico_light, src, dst, paint);
		  
	}
	

}
