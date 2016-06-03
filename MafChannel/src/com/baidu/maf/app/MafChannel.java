package com.baidu.maf.app;


/**
 * Created by hanxin on 2016/5/15.
 */
public interface MafChannel {
        /**
     *
     * <b>设置接收通知</b>
     * <p>
     * 默认为开启，iOS SDK不实现该方法，应提示用户使用系统设置方式
     * </p>
     *
     * @since 1.0
     *
     */
    public void enableNotification();
    /**
     *
     * <b>禁止接收通知</b>
     * <p>
     * iOS SDK不实现该方法，应提示用户使用系统设置方式
     * </p>
     * <br>
     * 关闭通知后，IM+的SDK将不会处理通知信息，本开关不影响message的推送
     *
     * @since 1.0
     *
     */
    public void disableNotification();
    /**
     *
     * <b>查询是否接收通知消息</b>
     *
     * @since 1.0
     *
     * @return 是否接收
     */
    public boolean isNotificationEnabled();

    /**
     *
     * <b>设置免打扰时段</b>
     * <p>
     * iOS SDK不实现该方法。指定时间内处于免打扰模式，通知到达时将去除通知的提示音，震动以及提示删除。 如果都设置为0则为关闭免打扰模式
     * </p>
     *
     * @since 1.0
     *
     * @param startHour 开始时间-小时
     * @param startMinute 开始时间-分钟
     * @param endHour 结束时间-小时
     * @param endMinute 结束时间-分钟
     */
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute);

    /**
     *
     * <b>设置通知回调</b>
     *
     * @since 1.0
     *
     * @param notifyListener 设置的通知回调
     */
    public void setNotifyListener(MafNotifyListener notifyListener);
}
