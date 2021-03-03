package com.wd.winddots;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.room.Room;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.winddots.data.AppDatabase;
import com.wd.winddots.data.DataTestBean;
import com.wd.winddots.data.DataTestDao;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.SpHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MyApplication extends Application {

    public static String USER_ID = "";
    public static String ENTERPRISE_ID = "";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Fresco.initialize(this);

        JMessageClient.init(this);
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        JMessageClient.registerEventReceiver(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        USER_ID = SpHelper.getInstance(getApplicationContext()).getString("id");
        USER_ID = SpHelper.getInstance(getApplicationContext()).getEnterpriseId();
        Logg.d(USER_ID);

        ShortcutBadger.applyCount(this,0);

        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, "wx2cfb08abb06edfdf", true);

        mWxApi.registerApp("你的appId");


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).imageDownloader(
                new BaseImageDownloader(this, 60000, 60000)) // connectTimeout超时时间
                .build();
        ImageLoader.getInstance().init(config);

        Phoenix.config().imageLoader(new com.guoxiaoxing.phoenix.core.listener.ImageLoader() {
            @Override
            public void loadImage(Context context, ImageView imageView, String imagePath, int type) {
                Glide.with(context)
                        .load(imagePath)
                        .into(imageView);
            }
        });


    new Thread(new Runnable() {
        @Override
        public void run() {
            AppDatabase db = AppDatabase.getDatabaseInstance(context);
            DataTestDao dataTestDao = db.dataTestDao();

            DataTestBean dataTestBean = new DataTestBean();
            dataTestBean.testId = "111";
            dataTestBean.testFirstName = "老周";
            dataTestBean.testLastName = "老王";

            dataTestDao.insertAll(dataTestBean);
        }
    }).start();


    }

    public static Context getContext() {
        return context;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        JMessageClient.unRegisterEventReceiver(this);
    }


    /**
     * 接收到新消息
     * @param event
     */
    public void onEvent(MessageEvent event){
        Message message = event.getMessage();
        String messageJson = message.toJson();

        Log.e("net666","全局接收到新消息：" + messageJson);
        String targetType = message.getTargetType().toString();//分类


        String contentType = message.getContentType().toString();//消息类型

        String contentJson = message.getContent().toJson();
        Logg.d("全局接收到新消息 contentType：" + contentType);
        long createTime = message.getCreateTime();
        // TODO: 2020/4/22 接收私人消息
        if ("single".equals(targetType)){
            String fromID = message.getFromID();
            String targetID = message.getTargetID();
            PrivateChatHistoryBean.MessageListBean messageListBean = new PrivateChatHistoryBean.MessageListBean();
            messageListBean.setBody(contentJson);
            messageListBean.setFromId(fromID);
            messageListBean.setTargetId(targetID);
            messageListBean.setJCreateTime(createTime);
            if ("image".equals(contentType)){
                messageListBean.setMsgType("image");
            } else if ("single_chat".equals(contentType)){
                messageListBean.setMsgType("single_chat");
            } else if ("text".equals(contentType)){
                messageListBean.setMsgType("text");
            }else if ("custom".equals(contentType)){

                Map body = message.getContent().getStringExtras();
                String messageType = (String) body.get("msgType");
                if ("image".equals(messageType)){
                    messageListBean.setMsgType("image_message");
                    String imageUrl = (String) body.get("imageUrl");
                    body.put("text",imageUrl);
                    Log.e("net666",String.valueOf(body));
                    Gson gson = new Gson();
                    String bodyString = gson.toJson(body);
                    messageListBean.setBody(bodyString);
                }else if ("voice".equals(messageType)){
                    messageListBean.setMsgType("voice_message");
                    String voiceUrl = (String) body.get("voiceUrl");
                    String timeLength = (String) body.get("timeLong");
                    body.put("text",voiceUrl);
                    body.put("timeLong",timeLength);
                    Gson gson = new Gson();
                    messageListBean.setAudioLength(Integer.parseInt(timeLength));
                    messageListBean.setVoiceUrl(voiceUrl);
                    String bodyString = gson.toJson(body);
                    messageListBean.setBody(bodyString);
                }




            }
            Logg.d("全局接收到新消息：111");
            EventBus.getDefault().postSticky(messageListBean);
        }




        // TODO: 2020/4/22 接收群聊消息
        else if ("group".equals(targetType)){
            String fromID = message.getFromID();
            String targetID = message.getTargetID();
            String userName = message.getFromUser().getUserName();// 发送消息用户的id
            String type = message.getContent().getContentType().toString();

            Logg.d("全局接收到新消息 userName: " + userName);
            GroupChatHistoryBean.MessageListBean messageListBean = new GroupChatHistoryBean.MessageListBean();
            messageListBean.setBody(contentJson);
            messageListBean.setFromId(fromID);
            messageListBean.setTargetId(targetID);
            messageListBean.setJCreateTime(createTime);
            if ("image".equals(contentType)){
                messageListBean.setMsgType("image");
            } else if ("single_chat".equals(contentType)){
                messageListBean.setMsgType("single_chat");
            } else if ("text".equals(contentType)){
                messageListBean.setMsgType("text");
            } else if ("custom".equals(contentType)){
                Map body = message.getContent().getStringExtras();
                String messageType = (String) body.get("msgType");
                if ("image".equals(messageType)){
                    String imageUrl = (String) body.get("imageUrl");
                    body.put("text",imageUrl);
                    messageListBean.setMsgType("image_message_group");
                    Gson gson = new Gson();
                    String bodyString = gson.toJson(body);
                    messageListBean.setBody(bodyString);
                }else if ("voice".equals(messageType)){
                    messageListBean.setMsgType("voice_message_group");
                    String voiceUrl = (String) body.get("voiceUrl");
                    String timeLength = (String) body.get("timeLong");
                    body.put("text",voiceUrl);
                    body.put("timeLong",timeLength);
                    Gson gson = new Gson();
                    messageListBean.setAudioLength(Integer.parseInt(timeLength));
                    messageListBean.setVoiceUrl(voiceUrl);
                    String bodyString = gson.toJson(body);
                    messageListBean.setBody(bodyString);
                }else {
                    messageListBean.setMsgType("custom_message");
                }
            }
            Log.e("ssss","全局接收到群聊新消息：222");
            EventBus.getDefault().postSticky(messageListBean);
        }
    }
}
