// Copyright (c) 2022 NetEase, Inc. All rights reserved.
// Use of this source code is governed by a MIT license that can be
// found in the LICENSE file.

package com.netease.yunxin.kit.teamkit.ui.utils;

import android.text.TextUtils;
import kotlin.random.Random;

/** 群头像工具类 群头像默认内置头像地址 */
public class TeamIconUtils {

  public static final String[] DEFAULT_ICON_URL_ARRAY =
      new String[] {
        "https://yx-web-nosdn.netease.im/common/2425b4cc058e5788867d63c322feb7ac/groupAvatar1.png",
        "https://yx-web-nosdn.netease.im/common/62c45692c9771ab388d43fea1c9d2758/groupAvatar2.png",
        "https://yx-web-nosdn.netease.im/common/d1ed3c21d3f87a41568d17197760e663/groupAvatar3.png",
        "https://yx-web-nosdn.netease.im/common/e677d8551deb96723af2b40b821c766a/groupAvatar4.png",
        "https://yx-web-nosdn.netease.im/common/fd6c75bb6abca9c810d1292e66d5d87e/groupAvatar5.png",
      };
  public static final String[] DEFAULT_ICON_URL_ARRAY_SQUARE =
      new String[] {
        "https://nim-nosdn.netease.im/MjYxNDkzNzE=/bmltYV8xNDIxMTk0NzAzMzhfMTY4NDgyNzc0MTczNV8yY2FlMjczZS01MDk0LTQ5NWMtODMzMS1mYTBmMTE1NmEyNDQ=",
        "https://nim-nosdn.netease.im/MjYxNDkzNzE=/bmltYV8xNDIxMTk0NzAzMzhfMTY4NDgyNzc0MTczNV9jYWJmNjViNy1kMGM3LTRiNDEtYmVmMi1jYjhiNzRjY2EwY2M=",
        "https://nim-nosdn.netease.im/MjYxNDkzNzE=/bmltYV8xNDIxMTk0NzAzMzhfMTY4NDgyNzc0MTczNV8yMzY1YmY5YS0xNGE1LTQxYTctYTg2My1hMzMyZWE5YzhhOTQ=",
        "https://nim-nosdn.netease.im/MjYxNDkzNzE=/bmltYV8xNDIxMTk0NzAzMzhfMTY4NDgyNzc0MTczNV80NTQxMDhhNy1mNWMzLTQxMzMtOWU3NS1hNThiN2FiNjI5MWY=",
        "https://nim-nosdn.netease.im/MjYxNDkzNzE=/bmltYV8xNDIxMTk0NzAzMzhfMTY4NDgyNzc0MTczNV8wMGVlNWUyOS0wYzg3LTQxMzUtYmVjOS00YjI1MjcxMDhhNTM="
      };

  // 获取默认头像地址
  public static String getDefaultIconUrl(int index, boolean isCircle) {
    if (index < 0 || index > 4) {
      return null;
    }
    return getDefaultIconUrlArray(isCircle)[index];
  }

  // 获取默认头像地址索引
  public static int getDefaultIconUrlIndex(String url, boolean isCircle) {
    String[] array = getDefaultIconUrlArray(isCircle);
    for (int index = 0; index < array.length; index++) {
      if (TextUtils.equals(url, array[index])) {
        return index;
      }
    }
    return -1;
  }

  // 获取默认随机头像地址
  public static String getDefaultRandomIconUrl(boolean isCircle) {
    return getDefaultIconUrlArray(isCircle)[Random.Default.nextInt(0, 5)];
  }

  // 获取默认头像地址数组
  private static String[] getDefaultIconUrlArray(boolean isCircle) {
    return isCircle ? DEFAULT_ICON_URL_ARRAY : DEFAULT_ICON_URL_ARRAY_SQUARE;
  }
}
