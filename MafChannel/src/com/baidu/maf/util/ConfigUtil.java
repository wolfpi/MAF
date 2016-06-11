package com.baidu.maf.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.text.TextUtils;

import com.baidu.maf.com.Constant;

import java.util.List;


public class ConfigUtil {

    public static final String TAG = "ConfigUtil";

    private static List<Pair<String, Integer>> listIP;

    private static boolean  mLoaded = false;
    
    public static void load() {
        JSONArray config;
        try {
            String json = null;

            // sdk can not be config when in online mode.
            if (Constant.buildMode != Constant.BuildMode.Online) {
                json = FileUtil.readStringFromFileInSdkFolder("config.txt");
            }
            if (TextUtils.isEmpty(json)) {
                config = generateConfig();
            } else {
                JSONTokener jsonParser = new JSONTokener(json);
                config = (JSONArray) jsonParser.nextValue();
            }

            for (int i = 0; i < config.length(); i++){
                JSONObject object = config.getJSONObject(i);
                Pair<String, Integer> ipPair = new Pair<>(object.getString("ip"), object.getInt("port"));
                listIP.add(ipPair);
            }
        } catch (JSONException e) {
            LogUtil.e(TAG, e);
            throw new RuntimeException(e);
        }
    }

    public static List<Pair<String, Integer>> getIPList() {
    	if(!mLoaded)
    	{
    		ConfigUtil.load();
    		mLoaded = true;
    	}
        return listIP;
    }

    /**
     * Generate default configuration.
     * 
     * @return
     */
    private static JSONArray generateConfig() {
        JSONArray config = new JSONArray();
        List<Constant.EChannelInfo> channelInfoList = Constant.EChannelInfo.getIPList();
        for (Constant.EChannelInfo channelInfo : channelInfoList){
            JSONObject object = new JSONObject();
            try {
                object.put("ip", channelInfo.getIp());
                object.put("port", channelInfo.getPort());
                config.put(object);
            } catch (JSONException e) {
                LogUtil.e(TAG, e);
                throw new RuntimeException(e);
            }
        }
        // sdk can not be config when in online mode.
        if (Constant.buildMode != Constant.BuildMode.Online) {
            FileUtil.writeStringToFileInSdkFolder("config.txt", config.toString());
        }
        return config;
    }
}
