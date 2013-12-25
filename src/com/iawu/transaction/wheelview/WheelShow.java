package com.iawu.transaction.wheelview;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.iawu.xq.R;
import com.iawu.tools.Util;

public class WheelShow extends TextView implements OnClickListener {
	private Context context;
	private Dialog dialog;
	private Calendar calendar;
	private static int START_YEAR = 1900, END_YEAR = 2100;
	private OnClickListener listener;

	boolean isShowed = false;

	public WheelShow(Context context) {
		super(context);
		init(context);
	}

	public WheelShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WheelShow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		this.setClickable(true);
		this.setOnClickListener(this);
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if (isShowed) {
					WheelShow.this.setClickable(true);
					isShowed = false;
				}
			}
		});
	}

	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	
	static public final long MillisDayX7 = 7*24*3600*1000;
	
	// 添加大小月月份并将其转换为list,方便之后的判断
	private String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	private String[] months_little = { "4", "6", "9", "11" };

	private List<String> list_big = Arrays.asList(months_big);
	private List<String> list_little = Arrays.asList(months_little);

	public void initDateTimePicker(OnClickListener listener, boolean start) {

		this.listener = listener;

		calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);

		if (start) {
			this.setText(Util.formatUnixTime(System.currentTimeMillis()-MillisDayX7));
			}
		else {
			// 设置日期的显示
			this.setText(year + "-" + decimal.format(month + 1) + "-"
					+ decimal.format(day));
		}

		// dialog.setTitle("请选择日期:");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);

		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		wv_day.setLabel("日");

		// 时
		/*
		 * final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		 * wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		 * wv_hours.setCyclic(true); wv_hours.setCurrentItem(hour);
		 * 
		 * // 分 final WheelView wv_mins = (WheelView)
		 * view.findViewById(R.id.mins); wv_mins.setAdapter(new
		 * NumericWheelAdapter(0, 59, "%02d")); wv_mins.setCyclic(true);
		 * wv_mins.setCurrentItem(minute);
		 */

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				int day = wv_day.getCurrentItem();
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else {
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
				wv_day.setCurrentItem(day);
			}
		};

		OnWheelChangedListener wheelListener_day = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else {
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小
		float textSize = 0;

		textSize = Util.dip2px(context, 20);

		wv_day.TEXT_SIZE = textSize;
		/*
		 * wv_hours.TEXT_SIZE = textSize; wv_mins.TEXT_SIZE = textSize;
		 */
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		// 确定
		btn_sure.setOnClickListener(this);
		// 取消
		btn_cancel.setOnClickListener(this);

		// 设置dialog的布局,并显示
		dialog.setContentView(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_datetime_sure:
			// 如果是个数,则显示为"02"的样式
			String parten = "00";
			DecimalFormat decimal = new DecimalFormat(parten);
			// 设置日期的显示
			this.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
					+ decimal.format((wv_month.getCurrentItem() + 1)) + "-"
					+ decimal.format((wv_day.getCurrentItem() + 1)));
			// + decimal.format(wv_hours.getCurrentItem()) + ":"
			// + decimal.format(wv_mins.getCurrentItem()));

			if (listener != null)
				listener.onClick(this);
			dialog.dismiss();
			break;
		case R.id.btn_datetime_cancel:
			dialog.dismiss();
			break;

		default:
			show();
			break;
		}
	}

	private void show() {
		isShowed = true;
		this.setClickable(false);

		calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		wv_month.setCurrentItem(month);

		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			} else {
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
			}
		}
		wv_day.setCurrentItem(day - 1);
		/*
		 * int hour = calendar.get(Calendar.HOUR_OF_DAY); int minute =
		 * calendar.get(Calendar.MINUTE);
		 */

		dialog.show();
	}
}
