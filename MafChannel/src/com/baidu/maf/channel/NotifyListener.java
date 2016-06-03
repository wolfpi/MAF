package com.baidu.maf.channel;

import android.content.Context;
import com.baidu.maf.message.NotifyMessage;

/**
 * Created by hanxin on 2016/5/23.
 */
public interface NotifyListener {
    void process(Context context, NotifyMessage notify);
}
