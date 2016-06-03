package com.baidu.maf.channel;

import com.baidu.maf.com.MafContext;

/**
 * Created by hanxin on 2016/5/25.
 */
public interface Processer {
    void process(MafContext context) throws Exception;
}
