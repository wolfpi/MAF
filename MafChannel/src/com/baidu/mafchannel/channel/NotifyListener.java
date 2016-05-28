package com.baidu.mafchannel.channel;

import android.content.Context;
import com.baidu.mafchannel.message.NotifyMessage;

/**
 * Created by hanxin on 2016/5/23.
 */
public interface NotifyListener {
    void process(Context context, NotifyMessage notify);
}
