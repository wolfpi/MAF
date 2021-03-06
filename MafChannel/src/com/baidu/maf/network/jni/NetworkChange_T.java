/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.maf.network.jni;

public final class NetworkChange_T {
  public final static NetworkChange_T NW_CONNECTED = new NetworkChange_T("NW_CONNECTED", 0);
  public final static NetworkChange_T NW_DISCONNECTED = new NetworkChange_T("NW_DISCONNECTED");
  public final static NetworkChange_T NW_UNKNOWN = new NetworkChange_T("NW_UNKNOWN");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static NetworkChange_T swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + NetworkChange_T.class + " with value " + swigValue);
  }

  private NetworkChange_T(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private NetworkChange_T(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private NetworkChange_T(String swigName, NetworkChange_T swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static NetworkChange_T[] swigValues = { NW_CONNECTED, NW_DISCONNECTED, NW_UNKNOWN };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

