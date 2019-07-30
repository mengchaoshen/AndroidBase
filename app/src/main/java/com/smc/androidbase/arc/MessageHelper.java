package com.smc.androidbase.arc;

import android.content.Context;

import com.arcvideo.arcrtcsdk.ArcRtcInvite;
import com.google.gson.Gson;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/9/6
 * @description
 */

public class MessageHelper {

    private static Context mApplicationContext;

    public static void setApplicationContext(Context appContext) {
        mApplicationContext = appContext;
    }

    /**
     * 发送邀请消息，一般在onSession()-CREATE回调中调用，也就是自己创建房间成功后，邀请对方
     * @param toAccount 消息接收者的userId
     * @param sessionId 视频会议房间号，onSession()-CREATE回调中可以调用方法获取到
     * @return
     */
    public static int sendInviteMessage(String toAccount, String sessionId) {
        //UserBean是对方的基本信息
//        UserBean userBean = AcsConnectApplication.getCurrentUserBean();
        //build message
        //设置自己的昵称，头像等，如果不需要也可以传空
        //MessageBean.MSG_TYPE_INVITE消息自定义为邀请消息
        MessageBean messageBean = new MessageBean(
                sessionId, "", "", MessageBean.MSG_TYPE_INVITE);
//        messageBean.setIconurl(userBean.getHeadPicUrl());
        //将需要发送的信息，转化为json字符串
        String msgJson = new Gson().toJson(messageBean);
        //调用sendMessage发送消息
        return ArcRtcInvite.Factory.create(mApplicationContext).sendMessage(toAccount, msgJson);
    }

    /**
     * 发送邀请的回复消息，一般在收到对方的邀请时调用
     *
     * @param toAccount 消息接收者的userId
     * @param result    回复内容，result：1代表接受，0代表拒绝
     * @param sessionId 视频会议房间号，onSession()-CREATE回调中可以调用方法获取到
     * @return
     */
    public static int sendReplyMessage(String toAccount, String result, String sessionId) {
        if (null == mApplicationContext) {
            throw new RuntimeException("please setApplicationContext() before invoke this method");
        }
//        UserBean userBean = AcsConnectApplication.getCurrentUserBean();
        //build message
        //设置自己的昵称，头像等，如果不需要也可以传空
        MessageBean messageBean = new MessageBean(
                sessionId, "", "", MessageBean.MSG_TYPE_REPLY);
//        messageBean.setIconurl(userBean.getHeadPicUrl());
        messageBean.setResult(result);
        //将需要发送的信息，转化为json字符串
        String msgJson = new Gson().toJson(messageBean);
        //调用sendMessage发送消息
        return ArcRtcInvite.Factory.create(mApplicationContext).sendMessage(toAccount, msgJson);
    }

    /**
     * 发送挂断消息，一般是在邀请别人，但是需要挂断时调用
     * @param toAccount 消息接收者的userId
     * @param sessionId 视频会议房间号，onSession()-CREATE回调中可以调用方法获取到
     * @return
     */
    public static int sendHangUpMessage(String toAccount, String sessionId) {
//        UserBean userBean = AcsConnectApplication.getCurrentUserBean();
        //build message
        //设置自己的昵称，头像等，如果不需要也可以传空
        MessageBean messageBean = new MessageBean(
                sessionId, "", "", MessageBean.MSG_TYPE_HANG_UP);
//        messageBean.setIconurl(userBean.getHeadPicUrl());
        //将需要发送的信息，转化为json字符串
        String msgJson = new Gson().toJson(messageBean);
        //调用sendMessage发送消息
        return ArcRtcInvite.Factory.create(mApplicationContext).sendMessage(toAccount, msgJson);
    }

    public static int sendMessage(String toAccount, String message) {
        //UserBean是对方的基本信息
//        UserBean userBean = AcsConnectApplication.getCurrentUserBean();
        //build message
        //设置自己的昵称，头像等，如果不需要也可以传空
        //MessageBean.MSG_TYPE_INVITE消息自定义为邀请消息
        MessageBean messageBean = new MessageBean(
                "12312", "123", "我是发送方", "10001");
//        messageBean.setIconurl(userBean.getHeadPicUrl());
        //将需要发送的信息，转化为json字符串
        String msgJson = new Gson().toJson(messageBean);
        //调用sendMessage发送消息
        return ArcRtcInvite.Factory.create(mApplicationContext).sendMessage(toAccount, msgJson);
    }
}
