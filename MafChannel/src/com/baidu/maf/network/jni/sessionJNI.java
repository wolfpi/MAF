/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.maf.network.jni;

public class sessionJNI {
  public final static native void LoginInfo_T_auto_login_set(long jarg1, LoginInfo_T jarg1_, boolean jarg2);
  public final static native boolean LoginInfo_T_auto_login_get(long jarg1, LoginInfo_T jarg1_);
  public final static native void LoginInfo_T_backup_ip_set(long jarg1, LoginInfo_T jarg1_, String jarg2);
  public final static native String LoginInfo_T_backup_ip_get(long jarg1, LoginInfo_T jarg1_);
  public final static native void LoginInfo_T_sign_hash_set(long jarg1, LoginInfo_T jarg1_, int jarg2);
  public final static native int LoginInfo_T_sign_hash_get(long jarg1, LoginInfo_T jarg1_);
  public final static native long new_LoginInfo_T();
  public final static native void delete_LoginInfo_T(long jarg1);
  public final static native void LoginResult_T_ack_code_set(long jarg1, LoginResult_T jarg1_, long jarg2);
  public final static native long LoginResult_T_ack_code_get(long jarg1, LoginResult_T jarg1_);
  public final static native void LoginResult_T_channelkey_set(long jarg1, LoginResult_T jarg1_, String jarg2);
  public final static native String LoginResult_T_channelkey_get(long jarg1, LoginResult_T jarg1_);
  public final static native long new_LoginResult_T();
  public final static native void delete_LoginResult_T(long jarg1);
  public final static native void DirectMessage_T_buf_set(long jarg1, DirectMessage_T jarg1_, byte[] jarg2);
  public final static native byte[] DirectMessage_T_buf_get(long jarg1, DirectMessage_T jarg1_);
  public final static native void DirectMessage_T_ulen_set(long jarg1, DirectMessage_T jarg1_, long jarg2);
  public final static native long DirectMessage_T_ulen_get(long jarg1, DirectMessage_T jarg1_);
  public final static native void DirectMessage_T_seq_set(long jarg1, DirectMessage_T jarg1_, int jarg2);
  public final static native int DirectMessage_T_seq_get(long jarg1, DirectMessage_T jarg1_);
  public final static native long new_DirectMessage_T();
  public final static native void delete_DirectMessage_T(long jarg1);
  public final static native void Channelinfo_channelKey_set(long jarg1, Channelinfo jarg1_, String jarg2);
  public final static native String Channelinfo_channelKey_get(long jarg1, Channelinfo jarg1_);
  public final static native void Channelinfo_extraInfo_set(long jarg1, Channelinfo jarg1_, String jarg2);
  public final static native String Channelinfo_extraInfo_get(long jarg1, Channelinfo jarg1_);
  public final static native void Channelinfo_deviceToken_set(long jarg1, Channelinfo jarg1_, String jarg2);
  public final static native String Channelinfo_deviceToken_get(long jarg1, Channelinfo jarg1_);
  public final static native void Channelinfo_network_set(long jarg1, Channelinfo jarg1_, int jarg2);
  public final static native int Channelinfo_network_get(long jarg1, Channelinfo jarg1_);
  public final static native void Channelinfo_apn_set(long jarg1, Channelinfo jarg1_, int jarg2);
  public final static native int Channelinfo_apn_get(long jarg1, Channelinfo jarg1_);
  public final static native void Channelinfo_osversion_set(long jarg1, Channelinfo jarg1_, String jarg2);
  public final static native String Channelinfo_osversion_get(long jarg1, Channelinfo jarg1_);
  public final static native void Channelinfo_sdkversion_set(long jarg1, Channelinfo jarg1_, String jarg2);
  public final static native String Channelinfo_sdkversion_get(long jarg1, Channelinfo jarg1_);
  public final static native long new_Channelinfo();
  public final static native void delete_Channelinfo(long jarg1);
  public final static native void IpList_ip1_set(long jarg1, IpList jarg1_, String jarg2);
  public final static native String IpList_ip1_get(long jarg1, IpList jarg1_);
  public final static native void IpList_ip2_set(long jarg1, IpList jarg1_, String jarg2);
  public final static native String IpList_ip2_get(long jarg1, IpList jarg1_);
  public final static native void IpList_ip3_set(long jarg1, IpList jarg1_, String jarg2);
  public final static native String IpList_ip3_get(long jarg1, IpList jarg1_);
  public final static native void IpList_ip4_set(long jarg1, IpList jarg1_, String jarg2);
  public final static native String IpList_ip4_get(long jarg1, IpList jarg1_);
  public final static native void IpList_ip5_set(long jarg1, IpList jarg1_, String jarg2);
  public final static native String IpList_ip5_get(long jarg1, IpList jarg1_);
  public final static native long new_IpList();
  public final static native void delete_IpList(long jarg1);
  public final static native void delete_ICallback(long jarg1);
  public final static native long new_ICallback();
  public final static native void ICallback_director_connect(ICallback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void ICallback_change_ownership(ICallback obj, long cptr, boolean take_or_release);
  public final static native void IEvtCallback_notify__SWIG_0(long jarg1, IEvtCallback jarg1_, byte[] jarg2, int jarg3, int jarg4);
  public final static native void IEvtCallback_notify__SWIG_1(long jarg1, IEvtCallback jarg1_, int jarg2, long jarg3, long jarg4, LoginResult_T jarg4_);
  public final static native void IEvtCallback_onError(long jarg1, IEvtCallback jarg1_, long jarg2, int jarg3, byte[] jarg4, int jarg5);
  public final static native long new_IEvtCallback();
  public final static native void delete_IEvtCallback(long jarg1);
  public final static native void IEvtCallback_director_connect(IEvtCallback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void IEvtCallback_change_ownership(IEvtCallback obj, long cptr, boolean take_or_release);
  public final static native void ILogCallback_d(long jarg1, ILogCallback jarg1_, byte[] jarg2, int jarg3);
  public final static native void ILogCallback_i(long jarg1, ILogCallback jarg1_, byte[] jarg2, int jarg3);
  public final static native void ILogCallback_w(long jarg1, ILogCallback jarg1_, byte[] jarg2, int jarg3);
  public final static native void ILogCallback_e(long jarg1, ILogCallback jarg1_, byte[] jarg2, int jarg3);
  public final static native void delete_ILogCallback(long jarg1);
  public final static native long new_ILogCallback();
  public final static native void ILogCallback_director_connect(ILogCallback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void ILogCallback_change_ownership(ILogCallback obj, long cptr, boolean take_or_release);
  public final static native long new_HiCoreSession();
  public final static native void delete_HiCoreSession(long jarg1);
  public final static native boolean HiCoreSession_initSession(long jarg1, HiCoreSession jarg1_);
  public final static native boolean HiCoreSession_deinitSession(long jarg1, HiCoreSession jarg1_);
  public final static native void HiCoreSession_set_notify_callback(long jarg1, HiCoreSession jarg1_, long jarg2, IEvtCallback jarg2_);
  public final static native int HiCoreSession_connect(long jarg1, HiCoreSession jarg1_);
  public final static native int HiCoreSession_disconnect(long jarg1, HiCoreSession jarg1_, boolean jarg2);
  public final static native boolean HiCoreSession_postMessage(long jarg1, HiCoreSession jarg1_, byte[] jarg2, int jarg3, int jarg4);
  public final static native void HiCoreSession_networkChanged(long jarg1, HiCoreSession jarg1_, int jarg2);
  public final static native void HiCoreSession_sendKeepAlive(long jarg1, HiCoreSession jarg1_);
  public final static native void HiCoreSession_dumpSelf(long jarg1, HiCoreSession jarg1_);
  public final static native long new_HiCoreEnv();
  public final static native void delete_HiCoreEnv(long jarg1);
  public final static native boolean HiCoreEnv_initEnv(long jarg1, HiCoreEnv jarg1_, String jarg2);
  public final static native boolean HiCoreEnv_deinitEnv(long jarg1, HiCoreEnv jarg1_);
  public final static native void HiCoreEnv_initIpist(long jarg1, HiCoreEnv jarg1_, long jarg2, IpList jarg2_);
  public final static native void HiCoreEnv_initChannelInfo(long jarg1, HiCoreEnv jarg1_, long jarg2, Channelinfo jarg2_);
  public final static native long HiCoreEnv_createSession(long jarg1, HiCoreEnv jarg1_, String jarg2);
  public final static native void HiCoreEnv_destroySession(long jarg1, HiCoreEnv jarg1_, long jarg2, HiCoreSession jarg2_);
  public final static native void HiCoreEnv_enableLog(long jarg1, HiCoreEnv jarg1_, int jarg2);
  public final static native void HiCoreEnv_set_log_callback(long jarg1, HiCoreEnv jarg1_, long jarg2, ILogCallback jarg2_);
  public final static native long IEvtCallback_SWIGUpcast(long jarg1);

  public static void SwigDirector_IEvtCallback_notify__SWIG_0(IEvtCallback self, byte[] data, int len, int seq) {
    self.notify(data, len, seq);
  }
  public static void SwigDirector_IEvtCallback_notify__SWIG_1(IEvtCallback self, int state, long err, long res) {
    self.notify(LoginState_T.swigToEnum(state), err, (res == 0) ? null : new LoginResult_T(res, false));
  }
  public static void SwigDirector_IEvtCallback_onError(IEvtCallback self, long err, int arg1, byte[] data, int len) {
    self.onError(err, arg1, data, len);
  }
  public static void SwigDirector_ILogCallback_d(ILogCallback self, byte[] data, int len) {
    self.d(data, len);
  }
  public static void SwigDirector_ILogCallback_i(ILogCallback self, byte[] data, int len) {
    self.i(data, len);
  }
  public static void SwigDirector_ILogCallback_w(ILogCallback self, byte[] data, int len) {
    self.w(data, len);
  }
  public static void SwigDirector_ILogCallback_e(ILogCallback self, byte[] data, int len) {
    self.e(data, len);
  }

  private final static native void swig_module_init();
  static {
    swig_module_init();
  }
}