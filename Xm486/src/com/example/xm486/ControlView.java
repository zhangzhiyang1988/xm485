package com.example.xm486;

import com.example.xm486.MainActivity.SetConstanse;
import com.example.xm486.mode.ButtonInfo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ControlView extends Dialog implements android.view.View.OnClickListener,OnLongClickListener,
OnTouchListener{

	private ButtonInfo buttonInfo;
	private View view;
	private Button plus;
	private Button subtract;
	private Button start;
	private Button stop;
	private Button other;
	private TextView showValue;
	private TextView valueName;
	private boolean isLongClick;
	private LinearLayout ll_control2;
	private boolean isShotClick=true;
	private int value;
	private SetConstanse setConstanse;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				int plusValue=value+5;
				if(plusValue<=100){
					value=value+5;
				}else {
					value=100;
				}
				setConstanse.setConstanse(value);
				showValue.setText(value+"");
			}else if (msg.what==2) {
				int subValue=value-5;
				if(subValue>=0){
				value=value-5;}else {
					value=0;
				}
				setConstanse.setConstanse(value);
				showValue.setText(value+"");
			}
			
			super.handleMessage(msg);
		}
		
	};
	public ControlView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ControlView(Context context, LayoutInflater inflater,int constanceValue,SetConstanse setConstanse) {
		super(context);
		view = inflater.inflate(R.layout.dialoglayout, null);
		setContentView(view);
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
		int height = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
		layoutParams.width = 2 * width / 3;
		layoutParams.height = 2 * height / 3;
		dialogWindow.setAttributes(layoutParams);
		setCanceledOnTouchOutside(false);
		value=constanceValue;
		init();
		this.setConstanse=setConstanse;
	}
	public void setState(String str1,String str2,String str3,String str4,boolean showSpeed){
			start.setText(str1);
			stop.setText(str2);
			if(str3==null){
				other.setVisibility(View.GONE);
			}else {
				other.setText(str3);
			}
			if(showSpeed){
				ll_control2.setVisibility(View.VISIBLE);
				valueName.setText(str4);
			}
		
	}
	private void init() {
		start=(Button) view.findViewById(R.id.bt_start);
		stop=(Button) view.findViewById(R.id.bt_stop);
		other=(Button) view.findViewById(R.id.bt_other);
		plus=(Button) view.findViewById(R.id.bt_plus);
		subtract=(Button) view.findViewById(R.id.bt_subtract);
		showValue=(TextView) view.findViewById(R.id.tv_value);
		valueName=(TextView) view.findViewById(R.id.tv_speed_name);
		ll_control2=(LinearLayout) view.findViewById(R.id.ll_control2);
		showValue.setText(value+"");
		plus.setOnLongClickListener(this);
		plus.setOnTouchListener(this);
		subtract.setOnLongClickListener(this);
		subtract.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.bt_plus:

			isLongClick=true;
			isShotClick=false;
			ControlThread thread_plus=new ControlThread(1);
			thread_plus.start();
			break;

		case R.id.bt_subtract:
			isLongClick=true;
			isShotClick=false;
			ControlThread thread_subtract=new ControlThread(2);
			thread_subtract.start();
			break;
		}
		return false;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			switch (v.getId()) {
			case R.id.bt_plus:
				isShotClick=true;
				if(isLongClick==false&&isShotClick&&value<100){
					value++;
					setConstanse.setConstanse(value);
					showValue.setText(value+"");
				}
				isLongClick=false;
				break;

			case R.id.bt_subtract:
				isShotClick=true;
				if(isLongClick==false&&isShotClick&&value>0){
					value--;
					setConstanse.setConstanse(value);
					showValue.setText(value+"");
				}
				isLongClick=false;
				break;
			}
			break;

		default:
			break;
		} 
		return false;
	}
	
	class ControlThread extends Thread{
		private int controlType;
		public ControlThread(int controlType){
			this.controlType=controlType;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isLongClick){
				try {
					Thread.sleep(300);
					handler.sendEmptyMessage(controlType);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.run();
		}
	}

}
