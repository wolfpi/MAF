/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.maf.network.jni;

public final class LoginState_T {
  public final static LoginState_T LS_UNLOGIN = new LoginState_T("LS_UNLOGIN", 0);
  public final static LoginState_T LS_LOGGEDIN = new LoginState_T("LS_LOGGEDIN");
  public final static LoginState_T LS_RETRYING = new LoginState_T("LS_RETRYING");
  public final static LoginState_T LS_RETRYCOUNTING = new LoginState_T("LS_RETRYCOUNTING");
  public final static LoginState_T LS_OFFLINE = new LoginState_T("LS_OFFLINE");
  public final static LoginState_T LS_NULL = new LoginState_T("LS_NULL");
  public final static LoginState_T LS_LOGGING = new LoginState_T("LS_LOGGING");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static LoginState_T swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + LoginState_T.class + " with value " + swigValue);
  }

  private LoginState_T(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private LoginState_T(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private LoginState_T(String swigName, LoginState_T swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static LoginState_T[] swigValues = { LS_UNLOGIN, LS_LOGGEDIN, LS_RETRYING, LS_RETRYCOUNTING, LS_OFFLINE, LS_NULL, LS_LOGGING };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

