package com.baidu.mafchannel.app;

import com.baidu.mafchannel.message.Message;

/**
 * Created by hanxin on 2016/5/15.
 */
public interface MafUserChannel extends  MafChannel{
    /**
     *
     * <b>ʹ���û�����¼</b>
     * <p>
     * ��ֱ�ӵ��ø÷����������connect�������÷���Ӧ�Զ�����connect�����ǰ��δ���ӣ� login��ɺ󣬼������豸����û������˰󶨣�ͬһ�豸ֻ�ܰ�һ���û���
     * ��loginʱ��userID���ѵ�¼userID��ͬʱ���Զ������ϸ��û���logout��ȷ��ͬһʱ��ֻ����һ���û���¼
     * </p>
     *
     * @since 1.0
     *
     * @param userID �û�ID
     * @param userToken �û�Token��ʹ���û�ID��IM+�������˻�ȡ
     */
    public void login(String userID, String userToken);

    /**
     *
     * <b>�û�ע��</b>
     * <p>
     * �û�ע������ͬ��disconnect��ע����ͬ�����յ�����ָ���˵�֪ͨ
     * </p>
     *
     * @since 1.0
     *
     */
    public void logout();

    /**
     *
     * <b>�û�������������</b>
     *
     * <p>
     * ���յ�ҵ�񷽷������Ļص�ʱ�򣬻����listener��receive����
     * </p>
     *
     * @param message ��Ҫ���͵���Ϣ
     * @param listener ��Ϣ�ظ�ʱ��Ļص�����
     */
    public void sendMessage(Message message, MafMessageListener listener);
}
