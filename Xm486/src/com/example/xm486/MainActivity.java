package com.example.xm486;


import java.io.UnsupportedEncodingException;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.xm486.mode.ButtonInfo;
import com.example.xm486.mode.CameraInfo;
import com.xm.ChnInfo;
import com.xm.DevInfo;
import com.xm.MyConfig.SocketStyle;
import com.xm.NetSdk;
import com.xm.video.MySurfaceView;

public class MainActivity extends Activity implements OnClickListener,OnTouchListener{

	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4;
	private Button bt5;
	private Button bt6;
	private Button bt7;
	private Button bt8;
	private Button bt_change;
	private LayoutInflater inflater;
	private ButtonInfo buttonInfo;
	private CameraInfo cameraInfo;
	private MySurfaceView videoView;
	private RelativeLayout rl_change;
	private RelativeLayout rl_parent;
	private boolean changeFlag;
	private NetSdk mNetSdk = null;
	private int ViewID = 1;
	DevInfo devInfo;
	private int mSocketStyle = SocketStyle.TCPSOCKET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		videoView.init(this, ViewID);
		mNetSdk = NetSdk.getInstance();
		devInfo = new DevInfo();
		int port = 0;
		port = 34567;
		devInfo.Ip = "192.168.1.2";
		devInfo.TCPPort = 34567;
		try {
			devInfo.UserName = "admin".getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		devInfo.PassWord = "";
	}

	private void init() {
		// TODO Auto-generated method stub
		rl_parent= (RelativeLayout) findViewById(R.id.rl_parent);
		rl_parent.setOnTouchListener(this);
		bt1=(Button) findViewById(R.id.bt1);
		bt2=(Button) findViewById(R.id.bt2);
		bt3=(Button) findViewById(R.id.bt3);
		bt4=(Button) findViewById(R.id.bt4);
		bt5=(Button) findViewById(R.id.bt5);
		bt6=(Button) findViewById(R.id.bt6);
		bt7=(Button) findViewById(R.id.bt7);
		bt8=(Button) findViewById(R.id.bt8);
		bt_change=(Button) findViewById(R.id.bt_change);
		videoView=(MySurfaceView) findViewById(R.id.video);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		bt6.setOnClickListener(this);
		bt7.setOnClickListener(this);
		bt8.setOnClickListener(this);
		bt_change.setOnClickListener(this);
		rl_change=(RelativeLayout) findViewById(R.id.rl_change);
		inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt1:
			getDialog("启动", "停止", null, "速度",true,Constance.Value_1,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_1=value;
				}
			});
			break;

		case R.id.bt2:
			getDialog("启动", "停止", null, null,false,Constance.Value_2,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_2=value;
				}
			});
			break;
		case R.id.bt3:

			getDialog("启动","停止", null,"喷涂速度", true,Constance.Value_3,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_3=value;
				}
			});
			break;
		case R.id.bt4:
			getDialog("正", "反", "停","行走速度", true,Constance.Value_4,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_4=value;
				}
			});
			break;
		case R.id.bt5:
			getDialog("启动","停止", null,"速度", true,Constance.Value_5,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_5=value;
				}
			});
			break;
		case R.id.bt6:
			getDialog("启动", "停止", null, "除锈速度",true,Constance.Value_6,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_6=value;
				}
			});
			break;
		case R.id.bt7:
			getDialog("张开", "收缩", "停止", null,false,Constance.Value_7,new SetConstanse() {
				@Override
				public void setConstanse(int value) {
					Constance.Value_7=value;
				}
			});
			break;
		case R.id.bt8:
			getDialog("正", "反", "停","行走速度", true,Constance.Value_8,new SetConstanse() {
				
				@Override
				public void setConstanse(int value) {
					Constance.Value_8=value;
				}
			});
			break;
		case R.id.bt_change:
			if (changeFlag) {
				rl_change.setVisibility(View.GONE);
				changeFlag = false;
			} else {
				Animation anim = AnimationUtils.loadAnimation(this,
						R.anim.camara_anim);
				rl_change.setVisibility(View.VISIBLE);
				rl_change.startAnimation(anim);
				changeFlag = true;
			}

			new Thread() {
				public void run() {
					long mloginid = mNetSdk.onLoginDev(ViewID, devInfo, null,
							mSocketStyle);
					Log.i("----->", "loginID is " + mloginid);
					mNetSdk.SetupAlarmChan(mloginid);
					mNetSdk.SetAlarmMessageCallBack();
					ChnInfo chnInfo = new ChnInfo();
					chnInfo.ChannelNo = 0;
					long playhandle = mNetSdk.onRealPlay(ViewID, mloginid, chnInfo);
					mNetSdk.setDataCallback(playhandle);
					videoView.onPlay();
					mNetSdk.setReceiveCompleteVData(0, true);
				};
			}.start();
			break;

		}
		
	}

	private void getDialog(String string, String string2, String string3,String string4,
			boolean b,int constanceValue,SetConstanse setConstanse) {
		ControlView controlView_bt = new ControlView(this, inflater,
				constanceValue,setConstanse);
		controlView_bt.setState(string, string2, string3,string4, b);
		controlView_bt.show();
		
	}
 
    interface SetConstanse{
    	void setConstanse(int value);
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN&&v.getId()!=R.id.rl_change){
        	if (changeFlag) {
				rl_change.setVisibility(View.GONE);
				changeFlag = false;
			}
        }

		return false;
	}
	
}
