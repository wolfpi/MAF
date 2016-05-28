package com.baidu.mafchannel.app;


/**
 * Created by hanxin on 2016/5/15.
 */
public interface MafChannel {
        /**
     *
     * <b>���ý���֪ͨ</b>
     * <p>
     * Ĭ��Ϊ������iOS SDK��ʵ�ָ÷�����Ӧ��ʾ�û�ʹ��ϵͳ���÷�ʽ
     * </p>
     *
     * @since 1.0
     *
     */
    public void enableNotification();
    /**
     *
     * <b>��ֹ����֪ͨ</b>
     * <p>
     * iOS SDK��ʵ�ָ÷�����Ӧ��ʾ�û�ʹ��ϵͳ���÷�ʽ
     * </p>
     * <br>
     * �ر�֪ͨ��IM+��SDK�����ᴦ��֪ͨ��Ϣ�������ز�Ӱ��message������
     *
     * @since 1.0
     *
     */
    public void disableNotification();
    /**
     *
     * <b>��ѯ�Ƿ����֪ͨ��Ϣ</b>
     *
     * @since 1.0
     *
     * @return �Ƿ����
     */
    public boolean isNotificationEnabled();

    /**
     *
     * <b>���������ʱ��</b>
     * <p>
     * iOS SDK��ʵ�ָ÷�����ָ��ʱ���ڴ��������ģʽ��֪ͨ����ʱ��ȥ��֪ͨ����ʾ�������Լ���ʾɾ���� ���������Ϊ0��Ϊ�ر������ģʽ
     * </p>
     *
     * @since 1.0
     *
     * @param startHour ��ʼʱ��-Сʱ
     * @param startMinute ��ʼʱ��-����
     * @param endHour ����ʱ��-Сʱ
     * @param endMinute ����ʱ��-����
     */
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute);

    /**
     *
     * <b>����֪ͨ�ص�</b>
     *
     * @since 1.0
     *
     * @param notifyListener ���õ�֪ͨ�ص�
     */
    public void setNotifyListener(MafNotifyListener notifyListener);
}
