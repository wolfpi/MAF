package com.baidu.mafchannel.channel;

import com.baidu.mafchannel.com.MafContext;

/**
 * Created by hanxin on 2016/5/25.
 */
public interface Processer {
    void process(MafContext context) throws Exception;
}
