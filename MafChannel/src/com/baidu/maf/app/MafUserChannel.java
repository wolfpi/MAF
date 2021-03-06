package com.baidu.maf.app;

import com.baidu.maf.listener.ClientStatusListener;

/**
 * Created by hanxin on 2016/5/15.
 */
public interface MafUserChannel extends  MafChannel, MessageProcedureSender{
    /**
     *
     * <b>使用用户名登录</b>
     * <p>
     * 可直接调用该方法替代调用connect方法，该方法应自动进行connect如果当前端未连接， login完成后，即将该设备与该用户进行了绑定，同一设备只能绑定一个用户。
     * 当login时的userID与已登录userID不同时，自动调用上个用户的logout，确保同一时间只能有一个用户登录
     * </p>
     *
     * @since 1.0
     *
     * @param userID 用户ID
     * @param userToken 用户Token，使用用户ID从IM+服务器端获取
     */
    public void login(String userID, String userToken);

    /**
     *
     * <b>用户注销</b>
     * <p>
     * 用户注销不等同于disconnect，注销后同样会收到发往指定端的通知
     * </p>
     *
     * @since 1.0
     *
     */
    public void logout();

    /**
     *
     * <b>用户发送上行数据</b>
     *
     * <p>
     * 在收到业务方服务器的回调时候，会调用listener的receive方法
     * </p>
     *
     * @param procedure 需要发送的消息过程
     */
    public void send(MafMessageProcedure procedure) throws  Exception;

    /**
     *
     * <b>设置用户状态变更回调</b>
     *
     * <p>
     * 当用户状态变更的时候，会调研此方法
     * </p>
     *
     * @param listener 回调的接口
     */
    public void setClientStatusListener(ClientStatusListener listener);
}
