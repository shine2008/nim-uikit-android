// Copyright (c) 2022 NetEase, Inc. All rights reserved.
// Use of this source code is governed by a MIT license that can be
// found in the LICENSE file.

package com.netease.yunxin.app.im.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.netease.yunxin.app.im.main.SettingKitConfig;

public class DataUtils {

  private static String appKey = null;
  private static String aMapServerKey = null;
  private static int serverConfigType = -1;
  private static String serverConfig = null;
  private static Boolean serverConfigSwitch = null;

  private static SettingKitConfig kitConfig = null;

  private static Boolean localConversation = null;

  /** read appKey from manifest */
  public static String readAppKey(Context context) {
    if (appKey != null) {
      return appKey;
    }
    if (context != null) {

      try {
        ApplicationInfo appInfo =
            context
                .getPackageManager()
                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        if (appInfo != null) {
          String keyStr =
              getServerConfigType(context) == Constant.CHINA_CONFIG
                  ? Constant.CONFIG_APPKEY_KEY
                  : Constant.CONFIG_APPKEY_KEY_OVERSEA;
          appKey = appInfo.metaData.getString(keyStr);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return appKey;
  }

  /**
   * 获取高德地图web server KEY 用于高德地图消息展示获取位置图片
   *
   * @param context
   * @return
   */
  public static String readAMapAppKey(Context context) {
    if (aMapServerKey != null) {
      return aMapServerKey;
    }
    if (context != null) {

      try {
        ApplicationInfo appInfo =
            context
                .getPackageManager()
                .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        if (appInfo != null) {
          aMapServerKey = appInfo.metaData.getString(Constant.CONFIG_AMAP_SERVER_KEY);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return aMapServerKey;
  }

  // 获取是否采用海外节点配置
  public static int getServerConfigType(Context context) {
    if (serverConfigType < 0) {
      serverConfigType =
          getConfigShared(context).getInt(Constant.SERVER_CONFIG, Constant.CHINA_CONFIG);
    }
    return serverConfigType;
  }

  // 获取私有化配置开关
  public static boolean getServerPrivateConfigSwitch(Context context) {
    if (serverConfigSwitch == null) {
      SharedPreferences sharedPreferences =
          context.getSharedPreferences(
              Constant.SERVER_PRIVATE_CONFIG_SWITCH_FILE, Context.MODE_MULTI_PROCESS);
      serverConfigSwitch = sharedPreferences.getBoolean(Constant.SERVER_CONFIG_SWITCH_PARAM, false);
    }
    return serverConfigSwitch;
  }

  // 获取本地会话配置开关
  public static boolean getLocalConversationConfigSwitch(Context context) {
    if (localConversation == null) {
      SharedPreferences sharedPreferences =
          context.getSharedPreferences(
              Constant.CONVERSATION_CONFIG_FILE, Context.MODE_MULTI_PROCESS);
      localConversation = sharedPreferences.getBoolean(Constant.CONVERSATION_LOCAL_CONFIG, true);
    }
    return localConversation;
  }

  // 保存私有化配置开关
  public static void saveLocalConversationConfigSwitch(Context context, boolean configSwitch) {
    SharedPreferences.Editor editor =
        context
            .getSharedPreferences(Constant.CONVERSATION_CONFIG_FILE, Context.MODE_MULTI_PROCESS)
            .edit();
    editor.putBoolean(Constant.CONVERSATION_LOCAL_CONFIG, configSwitch);
    localConversation = configSwitch;
    editor.commit();
  }

  // 保存私有化配置开关
  public static void saveServerPrivateConfigSwitch(Context context, boolean configSwitch) {
    SharedPreferences.Editor editor =
        context
            .getSharedPreferences(
                Constant.SERVER_PRIVATE_CONFIG_SWITCH_FILE, Context.MODE_MULTI_PROCESS)
            .edit();
    editor.putBoolean(Constant.SERVER_CONFIG_SWITCH_PARAM, configSwitch);
    serverConfigSwitch = configSwitch;
    editor.commit();
  }

  // 获取私有化配置内容
  public static String getServerConfig(Context context) {
    if (TextUtils.isEmpty(serverConfig)) {
      SharedPreferences sharedPreferences =
          context.getSharedPreferences(
              Constant.SERVER_PRIVATE_CONFIG_FILE, Context.MODE_MULTI_PROCESS);
      serverConfig = sharedPreferences.getString(Constant.SERVER_CONFIG_PARAM, "");
    }
    return serverConfig;
  }

  // 保存私有化配置
  public static void saveServerConfig(Context context, String content) {
    SharedPreferences.Editor editor =
        context
            .getSharedPreferences(Constant.SERVER_PRIVATE_CONFIG_FILE, Context.MODE_MULTI_PROCESS)
            .edit();
    editor.putString(Constant.SERVER_CONFIG_PARAM, content);
    serverConfig = content;
    editor.commit();
  }

  public static SharedPreferences getConfigShared(Context context) {
    SharedPreferences sharedPreferences =
        context.getSharedPreferences(Constant.SERVER_CONFIG_FILE, Context.MODE_MULTI_PROCESS);
    return sharedPreferences;
  }

  public static SettingKitConfig getSettingKitConfig() {
    if (kitConfig == null) {
      kitConfig = new SettingKitConfig();
    }
    return kitConfig;
  }

  public static float getSizeToM(long size) {
    return size / (1024.0f * 1024.0f);
  }
}
