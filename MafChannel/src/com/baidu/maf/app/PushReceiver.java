package com.baidu.maf.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baidu.im.frame.pb.ObjInformMessage.InformMessage;
import com.baidu.maf.com.Constant;
import com.baidu.maf.com.MafContext;
import com.baidu.maf.util.LogUtil;
import com.baidu.maf.util.NotificationUtil;
import com.baidu.maf.util.ServiceControlUtil;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;

public class PushReceiver extends BroadcastReceiver {
	
	final static private String TAG = "push";
	private MafContext mafContext;

	public PushReceiver(MafContext mafContext) {
		this.mafContext = mafContext;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		LogUtil.i(TAG, "on Receive in context");
		if(intent.getAction().equals("com.baidu.im.sdk.push")) {

	    int appId = mafContext.getAppId();
	    int appIdInIntent = intent.getIntExtra(Constant.sdkBDAppId, -1);
	    
	    LogUtil.i(TAG, String.valueOf(appId) + String.valueOf(appIdInIntent));
	    if(appIdInIntent == appId)
	    {
	    try {
			InformMessage informMessage = InformMessage.parseFrom(intent.getByteArrayExtra(Constant.sdkBDData));
			if(ServiceControlUtil.showInSeperateProcess(context) && NotificationUtil.isNotificationEnable())
			{
			     //NotificationUtil.showNormal(context, informMessage,chattype,toID,fromID);
				NotificationUtil.showNormal(context, informMessage,appId);
			     LogUtil.i("push", "show OK....");
			}
			
		} catch (InvalidProtocolBufferMicroException e) {
			//e.printStackTrace();
		}
	    }
		}
	    
	}
}
