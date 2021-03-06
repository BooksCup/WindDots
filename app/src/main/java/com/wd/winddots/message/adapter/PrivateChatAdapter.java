package com.wd.winddots.message.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.message.bean.PrivateChatMsgModeBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.message.record.VoiceImageView;
import com.wd.winddots.message.record.audio.AudioPlayManager;
import com.wd.winddots.message.record.audio.IAudioPlayListener;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.utils.glide.CornerTransform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PrivateChatAdapter extends BaseQuickAdapter<PrivateChatHistoryBean.MessageListBean, BaseViewHolder> {


    private int mCurrentPlayAnimPosition = -1;//当前播放动画的位置

    private PrivateChatHistoryBean.MessageListBean playingBean;

    //private int playingPosition = -1;

    private final CornerTransform mTransformation;


    private String meAvatar = "";
    private String elseAvatar = "";
    private String userId;

    public String getMeAvatar() {
        return meAvatar;
    }

    public void setMeAvatar(String meAvatar) {
        this.meAvatar = meAvatar;
    }

    public String getElseAvatar() {
        return elseAvatar;
    }

    public void setElseAvatar(String elseAvatar) {
        this.elseAvatar = elseAvatar;
    }

    public final MsgDataManager dataManager;
    public final CompositeSubscription compositeSubscription;

    public Activity mActivity;

    //头像数据
    public void setPrivateChatHistoryBean(PrivateChatHistoryBean privateChatHistoryBean) {
        List<PrivateChatHistoryBean.AvatarMapBean> avatarMap = privateChatHistoryBean.getAvatarMap();
        if (avatarMap.size() > 0){
            String meIcon = "";
            String elseIcon = "";
//            PrivateChatHistoryBean.AvatarMapBean bean = avatarMap.get(0);
//            if (userId.equals(bean.getUserId())){
//                meAvatar = bean.getUserAvatar() + Utils.OSSImageSize(100);
//                elseAvatar = avatarMap.get(1).getUserAvatar()+ Utils.OSSImageSize(100);
//            }else {
//                elseAvatar = bean.getUserAvatar()+ Utils.OSSImageSize(100);
//                meAvatar = avatarMap.get(1).getUserAvatar()+ Utils.OSSImageSize(100);
//            }


        }
//        int avatarMapSize = avatarMap.size();
//        for (int i = 0; i < avatarMapSize; i++) {
//            PrivateChatHistoryBean.AvatarMapBean avatarMapBean = avatarMap.get(i);
//            String userAvatar = avatarMapBean.getUserAvatar();
//            String userId = avatarMapBean.getUserId();
//
//            if (MyApplication.USER_ID.equals(userId)) {
//                meAvatar = userAvatar;
//            } else {
//                elseAvatar = userAvatar;
//            }
//        }
    }

    public PrivateChatAdapter(Context context, int layoutResId, @Nullable List<PrivateChatHistoryBean.MessageListBean> data,String userId1) {
        super(layoutResId, data);
        userId = userId1;
        mTransformation = new CornerTransform(context, CommonUtil.dip2px(context, 3));
        //只是绘制左上角和右上角圆角
        mTransformation.setExceptCorner(false, false, false, false);


        dataManager = new MsgDataManager();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void convert(BaseViewHolder helper, final PrivateChatHistoryBean.MessageListBean item) {

        helper.addOnClickListener(R.id.item_private_chat_else_msg_mode_chat_ll);

        RelativeLayout elseLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_else_layout);
        RelativeLayout meLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_me_layout);

        RelativeLayout meTcModeLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_me_text_chat_mode_layout);//气泡模式布局
        RelativeLayout elseTcModeLayout = (RelativeLayout) helper.getView(R.id.item_private_chat_else_text_chat_mode_layout);//气泡模式布局


        ImageView elseIvModeImgSrc = (ImageView) helper.getView(R.id.item_private_chat_else_msg_mode_text_iv);
        ImageView meIvModeImgSrc = (ImageView) helper.getView(R.id.item_private_chat_me_msg_mode_text_iv);


        ImageView elseIvHeader = (ImageView) helper.getView(R.id.item_private_chat_else_header_iv);
        ImageView meIvHeader = (ImageView) helper.getView(R.id.item_private_chat_me_header_iv);


        TextView elseTvModeText = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_text_tv);
        TextView meTvModeText = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_text_tv);
        LinearLayout elseModeChatLayout = (LinearLayout) helper.getView(R.id.item_private_chat_else_msg_mode_chat_ll);
        LinearLayout meModeChatLayout = (LinearLayout) helper.getView(R.id.item_private_chat_me_msg_mode_chat_ll);

        TextView elseTvModeChatTitle = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_title_tv);
        TextView elseTvModeChatContent = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_content_tv);
        TextView elseTvModeChatTime = (TextView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_time_tv);
        ImageView elseIvModeChatImg = (ImageView) helper.getView(R.id.item_private_chat_else_msg_mode_chat_img_iv);

        TextView meTvModeChatTitle = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_title_tv);
        TextView meTvModeChatContent = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_content_tv);
        TextView meTvModeChatTime = (TextView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_time_tv);
        ImageView meIvModeChatImg = (ImageView) helper.getView(R.id.item_private_chat_me_msg_mode_chat_img_iv);

        TextView tvTime = (TextView) helper.getView(R.id.item_private_chat_title_time_tv);
//        helper.setText(R.id.item_private_chat_title_time_tv, item.getCreateTime());

        RelativeLayout meVoiceLyout = helper.getView(R.id.ll_voice);
        final VoiceImageView meVoiceIv = helper.getView(R.id.iv_mevoice);
        TextView meTvAudioLength = helper.getView(R.id.tv__me_audio_length);

        RelativeLayout elseVoiceLyout = helper.getView(R.id.ll_else_voice);
        final VoiceImageView elseVoiceIv = helper.getView(R.id.iv_elsevoice);
        TextView elseTvAudioLength = helper.getView(R.id.tv__else_audio_length);




        meIvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(mContext, UserInfoActivity.class);
               intent.putExtra("id", SpHelper.getInstance(mContext).getUserId());
               mActivity.startActivity(intent);
            }
        });

        elseIvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("id", item.getFromId());
                mActivity.startActivity(intent);
            }
        });

        String fromId = item.getFromId();

        String msgType = item.getMsgType();

        String body = item.getBody();

        String showTime = item.getShowTime();

        if (TextUtils.isEmpty(showTime)) {
            tvTime.setVisibility(View.GONE);
        } else {
            tvTime.setVisibility(View.VISIBLE);
            if ("single_chat".equals(msgType)) {
                tvTime.setText(subTimeMin(item.getCreateTime()));
            } else {
                tvTime.setText(showTime);
            }
        }


        if (SpHelper.getInstance(mContext.getApplicationContext()).getString("id").equals(fromId)) {// TODO: 2020/4/16 自己发送的消息
            meLayout.setVisibility(View.VISIBLE);
            elseLayout.setVisibility(View.GONE);

            GlideApp.with(mContext)
                    .load(meAvatar)
                    .transform(mTransformation)
                    .into(meIvHeader);


            if ("image".equals(msgType) ) {//图片
                meTcModeLayout.setVisibility(View.GONE);
                meIvModeImgSrc.setVisibility(View.VISIBLE);
                if (item.getImageUri() != null){
                    GlideApp.with(mContext)
                            .load(item.getImageUri())
                            .into(meIvModeImgSrc);
                }else {
                    getMessageImage(meIvModeImgSrc, item);
                }


            }else if ("image_message".equals(item.getMsgType())){
                meTcModeLayout.setVisibility(View.GONE);
                meIvModeImgSrc.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                GlideApp.with(mContext)
                        .load(textBean.getText() + Utils.OSSImageSize(200))
                        .into(meIvModeImgSrc);

            }else if ("voice_message".equals(item.getMsgType())){
                meIvModeImgSrc.setVisibility(View.GONE);
                meTcModeLayout.setVisibility(View.VISIBLE);
                meVoiceLyout.setVisibility(View.VISIBLE);
                meTvModeText.setVisibility(View.GONE);
                meTvAudioLength.setText(item.getAudioLength() + "'");
                final VoiceImageView ivAudio = helper.getView(R.id.iv_mevoice);
                if (item.getVoiceUri() != null){
                    meVoiceLyout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (playingBean == item){
                                item.setPlayStatus("stop");
                                ivAudio.stopPlay();
                                playingBean = null;
                                AudioPlayManager.getInstance().stopPlay();
                            }else {

                                if (playingBean != null){
                                    AudioPlayManager.getInstance().stopPlay();
                                }
                                playingBean = item;
                                AudioPlayManager.getInstance().startPlay(mContext, item.getVoiceUri(), new IAudioPlayListener() {
                                    @Override
                                    public void onStart(Uri var1) {
                                        //mView.startPlayAnim(position);
                                        ivAudio.startPlay();
                                    }
                                    @Override
                                    public void onStop(Uri var1) {
                                        //mView.stopPlayAnim();
                                        item.setPlayStatus("stop");
                                        playingBean = null;
                                        ivAudio.stopPlay();
                                    }

                                    @Override
                                    public void onComplete(Uri var1) {
                                        //mView.stopPlayAnim();
                                        item.setPlayStatus("stop");
                                        playingBean = null;
                                        ivAudio.stopPlay();
                                    }
                                });
                            }
                        }
                    });
                }else {
                    Gson gson = new Gson();
                    final PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                    meTvAudioLength.setText(textBean.getTimeLong());
                    meVoiceLyout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            play(mContext,textBean.getText(),item,meVoiceIv);
                        }
                    });
                }
            } else {// 气泡
                meIvModeImgSrc.setVisibility(View.GONE);
                meTcModeLayout.setVisibility(View.VISIBLE);

                if ("text".equals(msgType)) {//文本
                    meModeChatLayout.setVisibility(View.GONE);
                    meTvModeText.setVisibility(View.VISIBLE);
                    meVoiceLyout.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                    if (textBean != null) {
                        meTvModeText.setText(textBean.getText());
                    }
                } else if ("single_chat".equals(msgType)) {//chat
                    meTvModeText.setVisibility(View.GONE);
                    meModeChatLayout.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.ChatBean chatBean = gson.fromJson(body, PrivateChatMsgModeBean.ChatBean.class);
                    if (chatBean != null) {
                        PrivateChatMsgModeBean.ChatBean.ExtrasBean chatExtras = chatBean.getExtras();
                        String title = chatExtras.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            meTvModeChatTitle.setVisibility(View.VISIBLE);
                            meTvModeChatTitle.setText(title);
                        } else {
                            meTvModeChatTitle.setVisibility(View.GONE);
                        }
                        String content = chatExtras.getContent();
                        if (!TextUtils.isEmpty(content)) {
                            meTvModeChatContent.setVisibility(View.VISIBLE);
                            meTvModeChatContent.setText(content);
                        } else {
                            meTvModeChatContent.setVisibility(View.GONE);
                        }
                        String startTime = chatExtras.getStartTime();
                        String endTime = chatExtras.getEndTime();
                        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                            meTvModeChatTime.setVisibility(View.VISIBLE);
                            meTvModeChatTime.setText(subTimeDate(startTime) + " ～ " + subTimeDate(endTime));
                        } else {
                            meTvModeChatTime.setVisibility(View.GONE);
                        }
                        meIvModeChatImg.setVisibility(View.GONE);

                    }
                }
            }
        } else {// TODO: 2020/4/16 别人发送的消息
            meLayout.setVisibility(View.GONE);
            elseLayout.setVisibility(View.VISIBLE);


            GlideApp.with(mContext)
                    .load(elseAvatar)
                    .transform(mTransformation)
                    .into(elseIvHeader);

            if ("image".equals(msgType)) {//图片
                elseTcModeLayout.setVisibility(View.GONE);
                elseIvModeImgSrc.setVisibility(View.VISIBLE);
                getMessageImage(elseIvModeImgSrc, item);
            } else if ("image_message".equals(item.getMsgType())){
                elseTcModeLayout.setVisibility(View.GONE);
                elseIvModeImgSrc.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                GlideApp.with(mContext)
                        .load(textBean.getText() + Utils.OSSImageSize(200))
                        .into(elseIvModeImgSrc);

            }else if("voice_message".equals(item.getMsgType())){
                elseModeChatLayout.setVisibility(View.GONE);
                elseTvModeText.setVisibility(View.GONE);
                elseVoiceLyout.setVisibility(View.VISIBLE);
                if (item.getVoiceUri() != null){
                    elseTvAudioLength.setText(item.getAudioLength() + "'");
                    final VoiceImageView ivAudio = helper.getView(R.id.iv_elsevoice);
                    helper.addOnClickListener(R.id.ll_else_voice);
                    elseVoiceLyout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (playingBean == item){
                                item.setPlayStatus("stop");
                                ivAudio.stopPlay();
                                playingBean = null;
                                AudioPlayManager.getInstance().stopPlay();
                            }else {

                                if (playingBean != null){
                                    AudioPlayManager.getInstance().stopPlay();
                                }
                                playingBean = item;
                                AudioPlayManager.getInstance().startPlay(mContext, item.getVoiceUri(), new IAudioPlayListener() {
                                    @Override
                                    public void onStart(Uri var1) {
                                        //mView.startPlayAnim(position);
                                        ivAudio.startPlay();
                                    }
                                    @Override
                                    public void onStop(Uri var1) {
                                        //mView.stopPlayAnim();
                                        item.setPlayStatus("stop");
                                        playingBean = null;
                                        ivAudio.stopPlay();
                                    }

                                    @Override
                                    public void onComplete(Uri var1) {
                                        //mView.stopPlayAnim();
                                        item.setPlayStatus("stop");
                                        playingBean = null;
                                        ivAudio.stopPlay();
                                    }
                                });
                            }
                        }
                    });
                }else {
                    Gson gson = new Gson();
                    final PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                    elseTvAudioLength.setText(textBean.getTimeLong());
                    elseVoiceLyout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            play(mContext,textBean.getText(),item,elseVoiceIv);
                        }
                    });
                }

            } else {
                elseIvModeImgSrc.setVisibility(View.GONE);
                elseTcModeLayout.setVisibility(View.VISIBLE);

                if ("text".equals(msgType)) {//文本
                    elseModeChatLayout.setVisibility(View.GONE);
                    elseTvModeText.setVisibility(View.VISIBLE);
                    //elseVoiceLyout.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.TextBean textBean = gson.fromJson(body, PrivateChatMsgModeBean.TextBean.class);
                    if (textBean != null) {
                        elseTvModeText.setText(textBean.getText());
                    }
                } else if ("single_chat".equals(msgType)) {//chat
                    elseTvModeText.setVisibility(View.GONE);
                    elseModeChatLayout.setVisibility(View.VISIBLE);

                    Gson gson = new Gson();
                    PrivateChatMsgModeBean.ChatBean chatBean = gson.fromJson(body, PrivateChatMsgModeBean.ChatBean.class);
                    if (chatBean != null) {
                        PrivateChatMsgModeBean.ChatBean.ExtrasBean chatExtras = chatBean.getExtras();
                        String title = chatExtras.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            elseTvModeChatTitle.setVisibility(View.VISIBLE);
                            elseTvModeChatTitle.setText(title);
                        } else {
                            elseTvModeChatTitle.setVisibility(View.GONE);
                        }
                        String content = chatExtras.getContent();
                        if (!TextUtils.isEmpty(content)) {
                            elseTvModeChatContent.setVisibility(View.VISIBLE);
                            elseTvModeChatContent.setText(content);
                        } else {
                            elseTvModeChatContent.setVisibility(View.GONE);
                        }
                        String startTime = chatExtras.getStartTime();
                        String endTime = chatExtras.getEndTime();
                        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                            elseTvModeChatTime.setVisibility(View.VISIBLE);
                            elseTvModeChatTime.setText(subTimeDate(startTime) + " ～ " + subTimeDate(endTime));
                        } else {
                            elseTvModeChatTime.setVisibility(View.GONE);
                        }
                        elseIvModeChatImg.setVisibility(View.GONE);

                    }
                }
            }
        }
    }

    // 极光获取图片连接
    private void getMessageImage(final ImageView ivImage, final PrivateChatHistoryBean.MessageListBean item) {
        String body = item.getBody();
        Gson gson = new Gson();
        PrivateChatMsgModeBean.ImageBean imageBean = gson.fromJson(body, PrivateChatMsgModeBean.ImageBean.class);

        // TODO: 2020/4/17 实时接收的消息和加载列表消息的图片body不一样 getMedia_id为实时的 getMediaId为加载的
        String mediaId = imageBean.getMediaId();
        if (TextUtils.isEmpty(mediaId)) {
            mediaId = imageBean.getMedia_id();
        }

        // 网络加载过的图片就直接获取
        if (!TextUtils.isEmpty(item.getRealImgUrl())) {
            GlideApp.with(mContext)
                    .load(item.getRealImgUrl())
                    .error(R.mipmap.default_img)
                    .skipMemoryCache(true)//默认为false
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .centerCrop()
                    .into(ivImage);
            return;
        }

        compositeSubscription.add(dataManager.getImage(mediaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String data) {
                        //真实图片地址加载出过后就直接设置值 之后就不需要网络加载了
                        item.setRealImgUrl(data);
                        GlideApp.with(mContext)
                                .load(data)
                                .error(R.mipmap.default_img)
                                .skipMemoryCache(true)//默认为false
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .centerCrop()
                                .into(ivImage);
                    }
                }));
    }


    // 截取时间到日期
    private String subTimeDate(String time) {
        String subTime = time.substring(0, time.indexOf(" "));
        return subTime;
    }

    // 截取时间到分钟
    private String subTimeMin(String time) {
        String subTime = time.substring(0, time.lastIndexOf(":"));
        return subTime;
    }


    private static final int BUFFER_SIZE =  1024; // 8k ~ 32K



     public void play(final Context context, final String urlStr, final PrivateChatHistoryBean.MessageListBean item, final VoiceImageView ivAudio){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //需要在子线程中处理的逻辑
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(false);
                    urlConnection.setConnectTimeout(10 * 1000);
                    urlConnection.setReadTimeout(10 * 1000);
                    urlConnection.setRequestProperty("Connection", "Keep-Alive");
                    urlConnection.setRequestProperty("Charset", "UTF-8");
                    urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                    urlConnection.connect();
                    long bytetotal = urlConnection.getContentLength();
                    long bytesum = 0;
                    int byteread = 0;
                    in = urlConnection.getInputStream();
                    File dir = com.nostra13.universalimageloader.utils.StorageUtils.getCacheDirectory(context);
                    String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
                    File file = new File(dir, apkName);
                    out = new FileOutputStream(file);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((byteread = in.read(buffer)) != -1) {
                        bytesum += byteread;
                        out.write(buffer, 0, byteread);
                    }
                    if (playingBean == item){
                        item.setPlayStatus("stop");
                        ivAudio.stopPlay();
                        playingBean = null;
                        AudioPlayManager.getInstance().stopPlay();
                    }else {

                        if (playingBean != null){
                            AudioPlayManager.getInstance().stopPlay();
                        }
                        playingBean = item;
                        AudioPlayManager.getInstance().startPlay(mContext, Uri.parse(file.toString()), new IAudioPlayListener() {
                            @Override
                            public void onStart(Uri var1) {
                                //mView.startPlayAnim(position);
                                ivAudio.startPlay();
                            }
                            @Override
                            public void onStop(Uri var1) {
                                //mView.stopPlayAnim();
                                item.setPlayStatus("stop");
                                playingBean = null;
                                ivAudio.stopPlay();
                            }

                            @Override
                            public void onComplete(Uri var1) {
                                //mView.stopPlayAnim();
                                item.setPlayStatus("stop");
                                playingBean = null;
                                ivAudio.stopPlay();
                            }
                        });
                    }

                } catch (Exception e) {
                    Log.e("net666", "download audio file error");
                    Log.e("net666", String.valueOf(e));
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ignored) {

                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception ignored) {
                            Log.e("net666", "download audio file error");
                            Log.e("net666", String.valueOf(ignored));
                        }
                    }
                }
            }
        }).start();

    }
}
