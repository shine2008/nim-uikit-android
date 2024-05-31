// Copyright (c) 2022 NetEase, Inc. All rights reserved.
// Use of this source code is governed by a MIT license that can be
// found in the LICENSE file.

package com.netease.yunxin.kit.chatkit.ui.normal.page;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.netease.nimlib.sdk.v2.conversation.enums.V2NIMConversationType;
import com.netease.nimlib.sdk.v2.message.enums.V2NIMMessageType;
import com.netease.yunxin.kit.chatkit.ui.R;
import com.netease.yunxin.kit.chatkit.ui.common.ChatUserCache;
import com.netease.yunxin.kit.chatkit.ui.common.ChatUtils;
import com.netease.yunxin.kit.chatkit.ui.custom.MultiForwardAttachment;
import com.netease.yunxin.kit.chatkit.ui.model.ChatMessageBean;
import com.netease.yunxin.kit.chatkit.ui.normal.ChatMessageForwardConfirmDialog;
import com.netease.yunxin.kit.chatkit.ui.normal.factory.PinViewHolderFactory;
import com.netease.yunxin.kit.chatkit.ui.page.ChatPinBaseActivity;
import com.netease.yunxin.kit.chatkit.ui.view.input.ActionConstants;
import com.netease.yunxin.kit.common.utils.SizeUtils;
import com.netease.yunxin.kit.corekit.im2.utils.RouterConstant;
import com.netease.yunxin.kit.corekit.route.XKitRouter;
import java.util.ArrayList;

/** 标准皮肤，Pin列表页面。 */
public class ChatPinActivity extends ChatPinBaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    changeStatusBarColor(R.color.color_eef1f4);
  }

  @Override
  protected void initView() {
    super.initView();
    pinAdapter.setViewHolderFactory(new PinViewHolderFactory());
  }

  // 跳转到聊天页面
  public void jumpToChat(ChatMessageBean messageInfo) {
    String router = RouterConstant.PATH_CHAT_TEAM_PAGE;
    if (mSessionType == V2NIMConversationType.V2NIM_CONVERSATION_TYPE_P2P) {
      router = RouterConstant.PATH_CHAT_P2P_PAGE;
    }

    XKitRouter.withKey(router)
        .withParam(RouterConstant.KEY_MESSAGE_INFO, messageInfo.getMessageData())
        .withParam(RouterConstant.CHAT_KRY, mSessionId)
        .withContext(this)
        .navigate();
    finish();
  }

  // 列表分割线
  @Override
  public RecyclerView.ItemDecoration getItemDecoration() {
    return new RecyclerView.ItemDecoration() {

      final int lRPadding = SizeUtils.dp2px(20);
      final int topPadding = SizeUtils.dp2px(12);

      @Override
      public void getItemOffsets(
          @NonNull Rect outRect,
          @NonNull View view,
          @NonNull RecyclerView parent,
          @NonNull RecyclerView.State state) {
        outRect.set(lRPadding, topPadding, lRPadding, 0);
      }
    };
  }

  // 点击自定义消息
  @Override
  protected void clickCustomMessage(ChatMessageBean messageBean) {
    if (messageBean.getMessageData().getMessage().getMessageType()
        == V2NIMMessageType.V2NIM_MESSAGE_TYPE_CUSTOM) {
      if (messageBean.getMessageData().getAttachment() instanceof MultiForwardAttachment) {
        XKitRouter.withKey(RouterConstant.PATH_CHAT_FORWARD_PAGE)
            .withContext(this)
            .withParam(RouterConstant.KEY_MESSAGE, messageBean.getMessageData())
            .navigate();
      }
    }
  }

  @Override
  protected void goToForwardPage() {
    ChatUtils.startForwardSelector(
        this, RouterConstant.PATH_FORWARD_SELECTOR_PAGE, false, forwardLauncher);
  }

  @Override
  protected void showForwardConfirmDialog(ArrayList<String> conversationIds) {
    if (forwardMessage == null) {
      return;
    }
    String sendName =
        TextUtils.isEmpty(mSessionName)
            ? ChatUserCache.getInstance()
                .getFriendInfo(forwardMessage.getMessageData().getMessage().getSenderId())
                .getAvatarName()
            : mSessionName;
    ChatMessageForwardConfirmDialog confirmDialog =
        ChatMessageForwardConfirmDialog.createForwardConfirmDialog(
            conversationIds, sendName, true, ActionConstants.POP_ACTION_TRANSMIT);
    confirmDialog.setCallback(
        (input) -> {
          if (forwardMessage != null) {
            for (String conversationId : conversationIds) {
              viewModel.sendForwardMessage(
                  forwardMessage.getMessageData().getMessage(), input, conversationId);
            }
          }
        });
    confirmDialog.show(getSupportFragmentManager(), TAG);
  }
}
