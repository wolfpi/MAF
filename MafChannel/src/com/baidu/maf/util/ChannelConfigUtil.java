package com.baidu.maf.util;


import com.baidu.maf.network.jni.IpList;

import java.util.List;

public class ChannelConfigUtil {
	
	public static IpList readIpList()
	{
		IpList ips = new IpList();
		List<Pair<String, Integer>> listIP = ConfigUtil.getIPList();
		if (listIP.size() > 1){
			ips.setIp1(listIP.get(0).getKey());
		}
		if (listIP.size() > 2){
			ips.setIp2(listIP.get(0).getKey());
		}
		if (listIP.size() > 3){
			ips.setIp3(listIP.get(0).getKey());
		}
		if (listIP.size() > 4){
			ips.setIp4(listIP.get(0).getKey());
		}
		if (listIP.size() > 5){
			ips.setIp5(listIP.get(0).getKey());
		}
		return ips;
	}

	/*
    public static JSONObject readConfig(OutAppConfig outAppConfig, Context context) {

        if (context == null) {
            return null;
        }

        String config;
        if (outAppConfig.getRunMode() == RUN_MODE.PRODUCT) {
            config = HiChannelConfigProduct.jsonStr;
        } else {
            config = HiChannelConfigDev.jsonStr;
        }

        if (config == null || config.length() <= 0) {
            return null;
        }

        JSONObject jsonObj = null;

        try {
            // the config info. should be read from a config file, later
            jsonObj = new JSONObject(config);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public static JSONObject setChannelKey(JSONObject jsonObj, String channelKey, String deviceToken) {

        if (jsonObj == null) {
            return null;
        }
        JSONObject channelObj;
        try {
            channelObj = jsonObj.getJSONObject("channel");
            if (channelObj != null) {
                channelObj.put("key", channelKey);
                channelObj.put("token", deviceToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }*/

}
