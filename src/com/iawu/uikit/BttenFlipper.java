package com.iawu.uikit;

import com.iawu.xq.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BttenFlipper extends RelativeLayout {

	Context context;
	BttenViewFlow viewFlow;
	
	public BttenFlipper(Context context ) {
		super(context );
		// TODO Auto-generated constructor stub
		init(context);
	}
	public BttenFlipper(Context context, AttributeSet attrs ) {
		super(context, attrs );
		init(context);
		// TODO Auto-generated constructor stub
	}
	public BttenFlipper(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	LinearLayout dotContainer;
	ImageView[] dotImgs;
	private void init(Context context){
		this.context=context;
		RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		viewFlow=new BttenViewFlow(context);
		addView(viewFlow,rlp);
		
		RelativeLayout.LayoutParams dlp=new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,20);
		dlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		dlp.setMargins(0, 0, 0, 20);
		
		dotContainer=new LinearLayout(context);
		dotContainer.setOrientation(LinearLayout.HORIZONTAL);
		dotContainer.setGravity(Gravity.CENTER_HORIZONTAL);
		this.addView(dotContainer,dlp);
		
		viewFlow.setOnViewChangeListener(new OnViewChangeListener() {
			@Override
			public void onViewChange(int position) {
				// TODO Auto-generated method stub
				if(dotImgs==null)
					return;
				for(int i=0;i<dotImgs.length;i++){
					if(dotImgs[i]==null) return;
					if(position==i){
						dotImgs[i].setImageResource(R.drawable.point_blue);
					}
					else{
						dotImgs[i].setImageResource(R.drawable.point_white);
					}
				}
			}
		});
	}

	public void setAdapter(BaseAdapter adapter){
		viewFlow.setAdapter(adapter);
		dotContainer.removeAllViewsInLayout();
		dotImgs= new ImageView[adapter.getCount()];
		LinearLayout. LayoutParams ip=new LinearLayout.LayoutParams(11,11);
		ip.setMargins(4, 3, 4, 3);
		for(int i=0;i<adapter.getCount();i++){
			dotImgs[i]=new ImageView(context);
			dotImgs[i].setScaleType(ScaleType.FIT_XY);
			dotImgs[i].setLayoutParams(ip);
			
			if(i==0){
				dotImgs[i].setImageResource(R.drawable.point_blue);
			}
			else{
				dotImgs[i].setImageResource(R.drawable.point_white);
			}
			dotContainer.addView(dotImgs[i],ip);
		}
	}
	
	public void startFlip(int times){
		if(viewFlow!=null){
			viewFlow.startFlip(times);
		}
	}

	public void setOnFlipperClickListener(OnFlipperClickListener listener){
		viewFlow.setOnFlipperClickListener(listener);
	}

}
