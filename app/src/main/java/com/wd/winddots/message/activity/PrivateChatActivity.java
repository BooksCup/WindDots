package com.wd.winddots.message.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.subscriptions.CompositeSubscription;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonEmptyActivity;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.message.bean.PrivateChatHistoryBean;
import com.wd.winddots.listener.SoftKeyBoardListener;
import com.wd.winddots.message.presenter.impl.PrivateChatPresenterImpl;
import com.wd.winddots.message.presenter.view.PrivateChatView;
import com.wd.winddots.message.adapter.PrivateChatAdapter;
import com.wd.winddots.message.record.RecordAudioButton;
import com.wd.winddots.message.record.RecordVoicePopWindow;
import com.wd.winddots.message.record.audio.AudioPlayManager;
import com.wd.winddots.message.record.audio.AudioRecordManager;
import com.wd.winddots.message.record.audio.IAudioPlayListener;
import com.wd.winddots.message.record.audio.IAudioRecordListener;
import com.wd.winddots.net.ElseDataManager;

import com.wd.winddots.utils.CommonUtil;
import com.wd.winddots.utils.Logg;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.dialog.ConfirmDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrivateChatActivity extends CommonEmptyActivity<PrivateChatView, PrivateChatPresenterImpl>
        implements PrivateChatView,
        BaseQuickAdapter.UpFetchListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener {


    private static final int MAX_VOICE_TIME = 30;//声音最大时间
    private static final String AUDIO_DIR_NAME = "audio_cache";
    private File mAudioDir;
    private List<File> mListData = new ArrayList<>();
    private RecordVoicePopWindow mRecordVoicePopWindow;//提示
    private RelativeLayout mRoot;
    private int playingPosition = -1;


    private static final int REQUEST_CODE_CAMARE = 10000;
    private static final int REQUEST_CODE_STORAGE = 10010;
    private static final int REQUEST_CODE_TAKE_PHOTO = 10001;
    private static final int REQUEST_CODE_RECORD_AUDIO = 10002;

    //用于保存拍照图片的uri
    private Uri mCameraUri;

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private String mCameraImagePath;

    private String mTitle;
    private String mId;
    private String mToId;
    private String mToAvatar;
    private String mTargetId;
    private String mInfo;
    List<PrivateChatHistoryBean.MessageListBean> mMessageList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PrivateChatAdapter mAdapter;
    private TextView mTvHeaderLoadMore;
    private EditText mEdtMessage;
    private TextView mTvSend;
    private TextView mNameTv;

    long beforeJCreateTime = 0;
    private RelativeLayout mSendLayout;
    private LinearLayout mSelectLayout;
    private ImageView mIvSelectPhoto;
    private ImageView mIvKeyboard;
    private RelativeLayout mInputLayout;


    private ImageView mIconImageView;
    private TextView mInfoTextView;
    private RecordAudioButton mVoiceRecord;

    private long loadJCreateTime;// TODO: 2020/4/22  根据当前最上面一条消息的时间做参数来获取更多消息

    private int target = 0;

    private RecyclerView mBottomRecyclerView;
    private List<ChatBottomBean.ChatBottomItem> mBottomItemList = new ArrayList<>();
    private BottomItemAdapter mBottomAdapter;

    public ElseDataManager elseDataManager;
    public CompositeSubscription compositeSubscription;

    /*
    * 判断是相机权限还是相册权限
    * */
    private int statusCode;

    @Override
    public PrivateChatPresenterImpl initPresenter() {
        return new PrivateChatPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_private_chat);

        elseDataManager = new ElseDataManager();
        compositeSubscription = new CompositeSubscription();

        mAudioDir = new File(mContext.getExternalCacheDir(), AUDIO_DIR_NAME);
        if (!mAudioDir.exists()) {//判断文件夹是否存在，不存在则创建
            mAudioDir.mkdirs();
        }

        initAudioManager();


        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("title");
            String convrId = intent.getStringExtra("id");
            if (StringUtils.isNullOrEmpty(convrId)) {
                mId = "";
            } else {
                mId = convrId;
            }
            mTargetId = intent.getStringExtra("targetId");
            mToAvatar = intent.getStringExtra("avatar");
            mInfo = intent.getStringExtra("info");
        }
        String title = intent.getStringExtra("title");


        mRoot = findViewById(R.id.rl_root);

        mRecyclerView = ((RecyclerView) findViewById(R.id.activity_private_chat_rv));

        mInputLayout = ((RelativeLayout) findViewById(R.id.activity_chat_input_layout));

        mIconImageView = findViewById(R.id.iv_icon);
        mInfoTextView = findViewById(R.id.activity_common_right_text);
        mNameTv = findViewById(R.id.tv_title);

        GlideApp.with(mContext).load(mToAvatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mIconImageView);
        mInfoTextView.setText(mInfo);
        mNameTv.setText(title);

        mSendLayout = ((RelativeLayout) findViewById(R.id.activity_chat_send_layout));
        mSelectLayout = ((LinearLayout) findViewById(R.id.activity_chat_select_layout));
        mEdtMessage = ((EditText) findViewById(R.id.activity_chat_send_edt));
        mTvSend = ((TextView) findViewById(R.id.activity_chat_send_tv));
        mIvSelectPhoto = ((ImageView) findViewById(R.id.activity_chat_select_photo_iv));
        mIvKeyboard = ((ImageView) findViewById(R.id.activity_chat_keyboard_iv));
        mVoiceRecord = findViewById(R.id.voice_record);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new PrivateChatAdapter(this, R.layout.item_private_chat, mMessageList, SpHelper.getInstance(mContext).getUserId());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setElseAvatar(mToAvatar);
        mAdapter.setMeAvatar(SpHelper.getInstance(mContext).getAvatar());
        mAdapter.mActivity = this;

//        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
//                if (R.id.ll_voice == view.getId()) {
//                    Uri audioUri = mMessageList.get(position).getVoiceUri();
//                    //Uri audioUri = Uri.fromFile(item);
//                    Log.d("P_startPlayRecord", audioUri.toString());
//                    AudioPlayManager.getInstance().startPlay(mContext, audioUri, new IAudioPlayListener() {
//                        @Override
//                        public void onStart(Uri var1) {
//                            mAdapter.startPlayAnim(position);
//                        }
//
//                        @Override
//                        public void onStop(Uri var1) {
//                            mAdapter.stopPlayAnim();
//                        }
//
//                        @Override
//                        public void onComplete(Uri var1) {
//                            mAdapter.stopPlayAnim();
//                        }
//                    });
//                }
//            }
//        });


        // TODO: 2020/4/22 下拉加载更多聊天记录
        mAdapter.setUpFetchEnable(true);
        mAdapter.setUpFetchListener(this);


        mBottomRecyclerView = findViewById(R.id.bottom_list);
        GridLayoutManager manager1 = new GridLayoutManager(mContext, 7);
        mBottomRecyclerView.setLayoutManager(manager1);
        mBottomAdapter = new BottomItemAdapter(R.layout.item_chat_bottom, mBottomItemList);
        mBottomRecyclerView.setAdapter(mBottomAdapter);

        ItemTouchHelper mItemTouchHelper1 = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                //mFastRecyclerView.bringToFront();


                int[] location = new int[2];

                int x = location[0];
                int y = location[1];


                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mBottomItemList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mBottomItemList, i, i - 1);
                    }
                }
                mBottomAdapter.notifyItemMoved(fromPosition, toPosition);


                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }


            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    //mFastRecyclerView.bringToFront();
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                    int[] location = new int[2];
                    viewHolder.itemView.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];

                    int X = Utils.dip2px(mContext, x);
                    int Y = Utils.dip2px(mContext, y);
                    Log.e("net666", "itemView--->" + x + "  " + "itemView--->" + y);


                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);

                List<Map> applicationImages = new ArrayList<>();
                for (int i = 0; i < mBottomItemList.size(); i++) {
                    ChatBottomBean.ChatBottomItem item = mBottomItemList.get(i);
                    Map map = new HashMap();
                    map.put("id", item.getId());
                    applicationImages.add(map);
                }
                RequestBody body = Utils.list2requestBody(applicationImages);
                presenter.chatBottomItemSort(SpHelper.getInstance(mContext).getUserId(), body);


            }
        });
        mItemTouchHelper1.attachToRecyclerView(mBottomRecyclerView);


        ImageView visitingCardIcon = findViewById(R.id.iv_visiting_card);
        visitingCardIcon.setOnClickListener(this);

        ImageView camareIv = ((ImageView) findViewById(R.id.iv_camare));
        camareIv.setOnClickListener(this);

        ImageView backIv = ((ImageView) findViewById(R.id.activity_chat_back));
        backIv.setOnClickListener(this);


        mVoiceRecord.setOnVoiceButtonCallBack(new RecordAudioButton.OnVoiceButtonCallBack() {
            @Override
            public void onStartRecord() {
                startRecord();
            }

            @Override
            public void onStopRecord() {
                stopRecord();
            }

            @Override
            public void onWillCancelRecord() {
                willCancelRecord();
            }

            @Override
            public void onContinueRecord() {
                continueRecord();
            }
        });
    }


    @Override
    public void initListener() {
        super.initListener();

        mAdapter.setOnItemClickListener(this);
        mTvSend.setOnClickListener(this);
        mIvKeyboard.setOnClickListener(this);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mEdtMessage.requestFocus();
                //mSelectLayout.setVisibility(View.GONE);
                mBottomRecyclerView.setVisibility(View.GONE);
                mSendLayout.setVisibility(View.VISIBLE);
                mVoiceRecord.setVisibility(View.GONE);

                //mRecyclerView.scrollToPosition(mAdapter.getData().size());//弹出软键盘 列表滚动到最下方
                if (target < 10) {
                    mRecyclerView.scrollToPosition(mAdapter.getData().size() - 1);
                } else {
                    mRecyclerView.scrollToPosition(mAdapter.getData().size());
                }
            }

            @Override
            public void keyBoardHide(int height) {
                mSendLayout.setVisibility(View.GONE);
                //mSelectLayout.setVisibility(View.VISIBLE);
                mBottomRecyclerView.setVisibility(View.VISIBLE);
                mVoiceRecord.setVisibility(View.GONE);
                mEdtMessage.clearFocus();
            }
        });

        mIvSelectPhoto.setOnClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mBottomAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_chat_keyboard_iv:
                clickKeyboard();
                break;
            case R.id.activity_chat_send_tv:
                sendTextMessage();
                break;
            case R.id.activity_chat_select_photo_iv://选择图片
                statusCode = 100;
                checkStorage(100);
                break;
            case R.id.iv_visiting_card://名片
                onVisitingCardDidClick();
                break;
            case R.id.iv_camare://拍照
                statusCode =REQUEST_CODE_CAMARE;
                checkStorage(REQUEST_CODE_CAMARE);
                break;
            case R.id.activity_chat_back://底部返回
                back();
                break;
        }
    }

    private void back() {
        finish();
    }


    // 是否是Android 10以上手机
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    private void checkStorage(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (code == 100){
                    sendImgMessage();
                }else {
                    onCamareDidClick();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
            }
        } else {

            if (code == 100){
                sendImgMessage();
            }else {
                onCamareDidClick();
            }

        }
    }

    private void onCamareDidClick() {

        String[] cameraPermissions = new String[]{Manifest.permission.CAMERA};

        requestPermissions(this, cameraPermissions, REQUEST_CODE_CAMARE);

    }


    private void onVisitingCardDidClick() {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.putExtra("id", mTargetId);

        startActivity(intent);
    }


    private boolean isKeyboardShow;

    private void clickKeyboard() {
        if (isKeyboardShow) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.hideSoftInputFromWindow(mEdtMessage.getWindowToken(), 0);
            isKeyboardShow = false;
            mSendLayout.setVisibility(View.GONE);
            //mSelectLayout.setVisibility(View.VISIBLE);
            mBottomRecyclerView.setVisibility(View.VISIBLE);
            mVoiceRecord.setVisibility(View.GONE);
            mEdtMessage.clearFocus();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
            //imm.showSoftInput(mEdtMessage,1);
            mSendLayout.setVisibility(View.VISIBLE);
            //mSelectLayout.setVisibility(View.GONE);
            mBottomRecyclerView.setVisibility(View.GONE);
            mVoiceRecord.setVisibility(View.GONE);
            mEdtMessage.requestFocus();
            isKeyboardShow = true;
        }
    }


    @Override
    public void initData() {
        super.initData();
        presenter.getChatBottomItem(SpHelper.getInstance(mContext).getUserId(), 1);
        presenter.initMessage(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"), mTargetId, mId, 0);
    }


    /**
     * 下拉加载更多聊天数据
     */
    @Override
    public void onUpFetch() {
        Logg.d("onUpFetch：" + loadJCreateTime);
        presenter.loadMoreMessage(SpHelper.getInstance(mContext).getUserId(), mTargetId, mId, loadJCreateTime);
    }

    @Override
    public void initChatMessageHistorySuccess(PrivateChatHistoryBean bean) {
        mAdapter.setPrivateChatHistoryBean(bean);
        final List<PrivateChatHistoryBean.MessageListBean> messageList = bean.getMessageList();
        //消息倒序
        Collections.reverse(messageList);

        mAdapter.setNewData(messageList);
        //显示最新消息
        int size = messageList.size();

        // TODO: 2020/4/16 设置时间可见不可见
        for (int i = 0; i < size; i++) {
            PrivateChatHistoryBean.MessageListBean messageListBean = messageList.get(i);
            long jCreateTime = messageListBean.getJCreateTime();
            if (Math.abs(jCreateTime - beforeJCreateTime) > 3 * 60 * 1000) {
                messageListBean.setShowTime(CommonUtil.descriptiveData(messageListBean.getjCreateTime()));
            }
            beforeJCreateTime = jCreateTime;
        }


        if (size > 0) {
            target = size;
            mRecyclerView.scrollToPosition(messageList.size());
        }

        loadJCreateTime = messageList.get(0).getJCreateTime();

        if (size == 10) {
            if (mTvHeaderLoadMore == null) {
                mTvHeaderLoadMore = (TextView) LayoutInflater.from(mContext).inflate(R.layout.header_chat_loadmore, null, false);
            }

            mAdapter.addHeaderView(mTvHeaderLoadMore, 0);
//            mTvHeaderLoadMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    presenter.loadMoreMessage(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"), mId, messageList.get(0).getJCreateTime());
//                }
//            });
        }
    }

    @Override
    public void initChatMessageHistoryError(String errMsg) {

    }

    @Override
    public void initChatMessageHistoryComplete() {

    }

    @Override
    public void loadMoreChatMessageHistorySuccess(PrivateChatHistoryBean bean) {
        final List<PrivateChatHistoryBean.MessageListBean> messageList = bean.getMessageList();
        //消息倒序
        Collections.reverse(messageList);
        mAdapter.addData(0, messageList);
        mAdapter.removeAllHeaderView();

        int size = messageList.size();

        beforeJCreateTime = 0;

        // TODO: 2020/4/16 设置时间显示可见不可见
        for (int i = 0; i < size; i++) {
            PrivateChatHistoryBean.MessageListBean messageListBean = messageList.get(i);
            long jCreateTime = messageListBean.getJCreateTime();
            if (Math.abs(jCreateTime - beforeJCreateTime) > 3 * 60 * 1000) {
                messageListBean.setShowTime(CommonUtil.descriptiveData(messageListBean.getjCreateTime()));
            }
            beforeJCreateTime = jCreateTime;
        }

        loadJCreateTime = messageList.get(0).getJCreateTime();

        if (size == 10) {
            if (mTvHeaderLoadMore == null) {
                mTvHeaderLoadMore = (TextView) LayoutInflater.from(mContext).inflate(R.layout.header_chat_loadmore, null, false);
            }

            mAdapter.addHeaderView(mTvHeaderLoadMore, 0);
//            mTvHeaderLoadMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    presenter.loadMoreMessage(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"), mId, messageList.get(0).getJCreateTime());
//                }
//            });
        }
    }

    @Override
    public void loadMoreChatMessageHistoryError(String errMsg) {

    }

    @Override
    public void loadMoreChatMessageHistoryComplete() {

    }

    @Override
    public void postMessageSuccess(String msgType) {
        mEdtMessage.setText("");
    }

    @Override
    public void postMessageError(String msgType, String errMsg) {

    }

    @Override
    public void postMessageComplete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlayManager.getInstance().stopPlay();
    }

    /**
     * 消息条目点击
     *
     * @param adapter  the adpater
     * @param view     The itemView within the RecyclerView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AudioPlayManager.getInstance().stopPlay();
        if (adapter == mBottomAdapter) {
            ChatBottomBean.ChatBottomItem item = mBottomItemList.get(position);
            switch (item.getType()) {
                case "back":
                    finish();
                    break;
                case "camera":
                    statusCode = REQUEST_CODE_CAMARE;
                    checkStorage(REQUEST_CODE_CAMARE);
                    break;
                case "card":
                    onVisitingCardDidClick();
                    break;
                case "picture":
                    statusCode = 100;
                    checkStorage(100);
                    break;
                case "voice":
                    //sendImgMessage();
//                    mBottomRecyclerView.setVisibility(View.GONE);
//                    mVoiceRecord.setVisibility(View.VISIBLE);
                    String[] cameraPermissions = new String[]{Manifest.permission.RECORD_AUDIO};
                    requestPermissions(this, cameraPermissions, REQUEST_CODE_RECORD_AUDIO);
                    break;
                case "emoji":
                    //File item = mListData.get(position);
                    break;
                case "add":
//                    String url = "http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/ab626d39f9d447d7b9259fbb0a94d1df.voice";
//                    Player.play(mContext,url);
                    break;
            }
            return;
        }
        List<PrivateChatHistoryBean.MessageListBean> data = mAdapter.getData();
    }


    /**
     * 条目 child 点击
     *
     * @param adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {

        List<PrivateChatHistoryBean.MessageListBean> data = ((PrivateChatAdapter) adapter).getData();
        final PrivateChatHistoryBean.MessageListBean messageListBean = data.get(position);
        String id = messageListBean.getId();
        switch (view.getId()) {
            case R.id.item_private_chat_else_msg_mode_chat_ll:// 组合讨论消息
                Intent intent = new Intent(mContext, DiscussChatActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.ll_voice:
                if (R.id.ll_voice == view.getId()) {
                    if ("play".equals(messageListBean.getPlayStatus())) {
                        AudioPlayManager.getInstance().stopPlay();
                        messageListBean.setPlayStatus("stop");
                        playingPosition = -1;
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (playingPosition != -1) {
                            PrivateChatHistoryBean.MessageListBean oldBean = data.get(playingPosition);
                            oldBean.setPlayStatus("stop");
                            AudioPlayManager.getInstance().stopPlay();
                            mAdapter.notifyDataSetChanged();
                        }
                        playingPosition = position;
                        Uri audioUri = mAdapter.getData().get(position).getVoiceUri();
                        //Uri audioUri = Uri.fromFile(item);
                        Log.d("P_startPlayRecord", audioUri.toString());
                        AudioPlayManager.getInstance().startPlay(mContext, audioUri, new IAudioPlayListener() {
                            @Override
                            public void onStart(Uri var1) {
                                messageListBean.setPlayStatus("play");
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onStop(Uri var1) {
                                messageListBean.setPlayStatus("stop");
                                playingPosition = -1;
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onComplete(Uri var1) {
                                messageListBean.setPlayStatus("stop");
                                playingPosition = -1;
                            }
                        });
                    }


                }
                break;
        }
    }


    /**
     * 发送文本消息
     */
    private void sendTextMessage() {
        String sendText = mEdtMessage.getText().toString();

        if (!TextUtils.isEmpty(sendText)) {
            presenter.postText(SpHelper.getInstance(mContext).getUserId(), mTargetId, sendText, "single", "text");
            mEdtMessage.setText("");

            PrivateChatHistoryBean.MessageListBean messageListBean = new PrivateChatHistoryBean.MessageListBean();
            Map map = new HashMap();
            map.put("text", sendText);
            Gson gson = new Gson();
            String bodyString = gson.toJson(map);
            Log.e("net666", bodyString);
            messageListBean.setBody(bodyString);
            messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
            messageListBean.setTargetId(mTargetId);
            messageListBean.setJCreateTime(System.currentTimeMillis());
            messageListBean.setMsgType("text");
            messageListBean.setFromUserAvatar(SpHelper.getInstance(mContext).getAvatar());
            List<PrivateChatHistoryBean.MessageListBean> data = mAdapter.getData();
            int dataSize = data.size();


            // 设置时间
            if (dataSize > 0) {
                long jCreateTime = data.get(dataSize - 1).getJCreateTime();
                if (messageListBean.getjCreateTime() - jCreateTime > 3 * 60 * 1000) {
                    messageListBean.setShowTime(CommonUtil.descriptiveData(messageListBean.getjCreateTime()));
                }
            }


            //showToast("newmessage");
            mAdapter.addData(messageListBean);
            List<PrivateChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
            mRecyclerView.scrollToPosition(messageList.size());
        }
    }


    /**
     * 发送图片消息
     */
    private void sendImgMessage() {
        // 打开本地相册
//        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // 第二个参数是请求码
        // 设定结果返回
        startActivityForResult(intent, 100);
    }


    /**
     * 返回图片路径
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            Uri uri = data.getData();
            String imagePath;
            // 第二个参数是想要获取的数据
            Cursor cursor = getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA},
                            null, null, null);
            if (cursor == null) {
                imagePath = uri.getPath();
            } else {
                cursor.moveToFirst();
                // 获取数据所在的列下标
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                imagePath = cursor.getString(index);  // 获取指定列的数据
                cursor.close();
            }
            postImageMessage(imagePath);
            PrivateChatHistoryBean.MessageListBean messageListBean = new PrivateChatHistoryBean.MessageListBean();
            messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
            messageListBean.setImageUri(uri);
            messageListBean.setMsgType("image");

            mAdapter.addData(messageListBean);
            List<PrivateChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
            mRecyclerView.scrollToPosition(messageList.size());
        }

        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            if (isAndroidQ) {
                if (resultCode == 0) {
                    return;
                }
                Uri uri = mCameraUri;//Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                PrivateChatHistoryBean.MessageListBean messageListBean = new PrivateChatHistoryBean.MessageListBean();
                messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
                messageListBean.setImageUri(uri);
                messageListBean.setMsgType("image");
                mAdapter.addData(messageListBean);
                List<PrivateChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
                mRecyclerView.scrollToPosition(messageList.size());
                String imagePath = Utils.uri2File(mContext, mCameraUri).getPath();  //mCameraUri.getPath();
                postImageMessage(imagePath);
            } else {
                // 使用图片路径加载
                //ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
                if (resultCode == 0) {
                    return;
                }
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mCameraImagePath), null, null));
                PrivateChatHistoryBean.MessageListBean messageListBean = new PrivateChatHistoryBean.MessageListBean();
                messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
                messageListBean.setImageUri(uri);
                messageListBean.setMsgType("image");

                mAdapter.addData(messageListBean);
                List<PrivateChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
                mRecyclerView.scrollToPosition(messageList.size());
                postImageMessage(Utils.uri2File(mContext, uri).getPath());
            }
        }
    }


    private void postImageMessage(String imagePath) {
        if (imagePath == null) {
            showToast("上传图片失败,请稍后重试");
            return;
        }

        String imageUrl = OSSUploadHelper.uploadImage(imagePath);

        if (imageUrl == null) {
            showToast("上传图片失败,请稍后重试");
            return;
        }
        Map customMessage = new HashMap();
        Map imageData = new HashMap();
        imageData.put("image", imageUrl);
        imageData.put("msgType", "image");
        //imageData.put("url","2");
        customMessage.put("extras", imageData);
        customMessage.put("text", imageUrl);
        Map body = customMessage;//Utils.getListForJson(data).get(0);
        Gson gson = new Gson();
        String jsonS = gson.toJson(body);
        //sendMessage(fromId,targetId,jsonS,"single","image_message");
        presenter.sendMessage(SpHelper.getInstance(mContext).getUserId(), mTargetId, jsonS, "single", "image_message");
    }


    /**
     * 上传图片成功
     *
     * @param data
     */
    @Override
    public void postImageSuccess(String data) {


    }

    /**
     * 上传图片失败
     *
     * @param errMsg
     */
    @Override
    public void postImageError(String errMsg) {

    }


    /*
     * 发送语音消息
     * */
    @Override
    public void postVoiceSuccess(String data) {

    }

    @Override
    public void postVoiceError(String errMsg) {

    }


    /*
     * 底部功能按钮
     * */
    @Override
    public void getChatBottomItemSuccess(ChatBottomBean bean) {
        mBottomItemList.addAll(bean.getData());
        mBottomAdapter.notifyDataSetChanged();
    }

    @Override
    public void getChatBottomItemError(String errMsg) {

    }

    @Override
    public void getChatBottomItemErrorComplete() {

    }


    /**
     * 接收到新消息
     *
     * @param newMessageListBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetNewMessage(PrivateChatHistoryBean.MessageListBean newMessageListBean) {


        if (SpHelper.getInstance(mContext).getUserId().equals(newMessageListBean.getFromId())) {
            return;
        }

        if ("image".equals(newMessageListBean.getMsgType())) {
            if (newMessageListBean.getFromId().equals(SpHelper.getInstance(mContext).getUserId())) {
                return;
            }
        }
        List<PrivateChatHistoryBean.MessageListBean> data = mAdapter.getData();
        int dataSize = data.size();

        String fromId = newMessageListBean.getFromId();
        String targetId = newMessageListBean.getTargetId();
        long newJCreateTime = newMessageListBean.getJCreateTime();

        if (dataSize > 0) {
            long jCreateTime = data.get(dataSize - 1).getJCreateTime();
            if (newJCreateTime - jCreateTime > 3 * 60 * 1000) {
                newMessageListBean.setShowTime(CommonUtil.descriptiveData(newJCreateTime));
            }
        }

        if (!TextUtils.isEmpty(targetId) && targetId.equals(mTargetId)) {
            Logg.d("fromId:" + fromId + "  targetId: " + targetId);
            mAdapter.addData(newMessageListBean);
            List<PrivateChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
            //mRecyclerView.scrollToPosition(messageList.size());
            if (target < 10) {
                mRecyclerView.scrollToPosition(messageList.size() - 1);
            } else {
                mRecyclerView.scrollToPosition(messageList.size());
            }
        }
    }


    /**
     * 动态权限
     */
    public void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
            ArrayList<String> mPermissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (mPermissionList.isEmpty()) {
                // 非初次进入App且已授权
                switch (requestCode) {
                    case REQUEST_CODE_CAMARE:
//                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                        Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                        takePhoto();
                        break;
                    case REQUEST_CODE_STORAGE:
//                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                        Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                        if (statusCode == 100){
                            sendImgMessage();
                        }else {
                            onCamareDidClick();
                        }
                        break;
                    case REQUEST_CODE_RECORD_AUDIO:
//                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                        Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                        mBottomRecyclerView.setVisibility(View.GONE);
                        mVoiceRecord.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                // 请求权限方法
                String[] requestPermissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                // 这个触发下面onRequestPermissionsResult这个回调
                ActivityCompat.requestPermissions(activity, requestPermissions, requestCode);
            }
        }
    }

    /**
     * requestPermissions的回调
     * 一个或多个权限请求结果回调
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        // 判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                break;
            }
        }
        if (hasAllGranted) {
            switch (requestCode) {
                case REQUEST_CODE_CAMARE:
//                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    takePhoto();
                    break;
                case REQUEST_CODE_STORAGE:
//                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    if (statusCode == 100){
                        sendImgMessage();
                    }else {
                        onCamareDidClick();
                    }

                    break;
                case REQUEST_CODE_RECORD_AUDIO:
//                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    mBottomRecyclerView.setVisibility(View.GONE);
                    mVoiceRecord.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_CAMARE:
                    content = "在设置-应用-瓦丁-权限中开启打开相机权限，以正常使用拍照等功能";
                    break;
                case REQUEST_CODE_RECORD_AUDIO:
                    content = "在设置-应用-瓦丁-权限中开启打开录取权限，以正常使用发送语音等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(mContext, "权限申请",
                    content,
                    "确定", "取消");
            mConfirmDialog.setOnDialogClickListener(new ConfirmDialog.OnDialogClickListener() {
                @Override
                public void onOkClick() {
                    mConfirmDialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                }//"a2c3b2f6d32345b2818be757f5adb54f"

                @Override
                public void onCancelClick() {
                    mConfirmDialog.dismiss();
                }
            });
            // 点击空白处消失
            mConfirmDialog.setCancelable(false);
            mConfirmDialog.show();
        }
    }


    private void takePhoto() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;
            if (isAndroidQ) {
                photoUri = createImageUri();
            } else {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        String authority = "com.cfp.wading" + ".updateFileProvider";
                        photoUri = FileProvider.getUriForFile(this, authority, photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }
            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }

    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }


    /**
     * 初始化音频播放管理对象
     */
    private void initAudioManager() {
        AudioRecordManager.getInstance(mContext).setAudioSavePath(mAudioDir.getAbsolutePath());
        AudioRecordManager.getInstance(mContext).setMaxVoiceDuration(MAX_VOICE_TIME);
        AudioRecordManager.getInstance(mContext).setAudioRecordListener(new IAudioRecordListener() {
            @Override
            public void initTipView() {
                showNormalTipView();
            }

            @Override
            public void setTimeoutTipView(int counter) {
                showTimeOutTipView(counter);
            }

            @Override
            public void setRecordingTipView() {
                showRecordingTipView();
            }

            @Override
            public void setAudioShortTipView() {
                showRecordTooShortTipView();
            }

            @Override
            public void setCancelTipView() {
                showCancelTipView();
            }

            @Override
            public void destroyTipView() {
                hideTipView();
            }

            @Override
            public void onStartRecord() {

            }

            @Override
            public void onFinish(Uri audioPath, int duration) {
                File file = new File(audioPath.getPath());
                if (file.exists()) {
                    Uri audio = Uri.parse(file.toString());
                    RequestBody body = Utils.uri2requestBody(mContext, audio);
                    //presenter.postImage(body,"","");

                    PrivateChatHistoryBean.MessageListBean messageListBean = new PrivateChatHistoryBean.MessageListBean();
                    messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
                    messageListBean.setVoiceUri(audioPath);
                    messageListBean.setMsgType("voice_message");
                    List<PrivateChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
                    mRecyclerView.scrollToPosition(messageList.size());
                    MediaPlayer meidaPlayer = new MediaPlayer();
                    try {
                        meidaPlayer.setDataSource(file.getPath());
                        meidaPlayer.prepare();
                        long time = meidaPlayer.getDuration();
                        long seconds = time % 60000;
                        int second = Math.round((float) seconds / 1000);
                        messageListBean.setAudioLength(second);
                        // showToast(second + "");
                        mAdapter.addData(messageListBean);
                        mRecyclerView.scrollToPosition(messageList.size());
                        presenter.postVoice(body, SpHelper.getInstance(mContext).getUserId(), mTargetId, second);
                    } catch (Exception e) {

                    }
                    mListData.add(file);
                }
            }

            @Override
            public void onAudioDBChanged(int db) {
                mRecordVoicePopWindow.updateCurrentVolume(db);
            }
        });
    }


    public void showNormalTipView() {
        if (mRecordVoicePopWindow == null) {
            mRecordVoicePopWindow = new RecordVoicePopWindow(mContext);
        }
        int height = dip2px(mContext, 100);
        mRecordVoicePopWindow.showAtLocation(mRoot, Gravity.BOTTOM | Gravity.RIGHT, 0, height);
        //mRecordVoicePopWindow.showAsDropDown(mRoot);
    }

    public void showTimeOutTipView(int remainder) {
        if (mRecordVoicePopWindow != null) {
            mRecordVoicePopWindow.showTimeOutTipView(remainder);
        }
    }

    public void showRecordingTipView() {
        if (mRecordVoicePopWindow != null) {
            mRecordVoicePopWindow.showRecordingTipView();
        }
    }


    public void showRecordTooShortTipView() {
        if (mRecordVoicePopWindow != null) {
            mRecordVoicePopWindow.showRecordTooShortTipView();
        }
    }


    public void showCancelTipView() {
        if (mRecordVoicePopWindow != null) {
            mRecordVoicePopWindow.showCancelTipView();
        }
    }

    public void hideTipView() {
        if (mRecordVoicePopWindow != null) {
            mRecordVoicePopWindow.dismiss();
        }
    }

    public void startRecord() {


        AudioRecordManager.getInstance(mContext).startRecord();
    }

    public void willCancelRecord() {
        AudioRecordManager.getInstance(mContext).willCancelRecord();
    }

    public void continueRecord() {
        AudioRecordManager.getInstance(mContext).continueRecord();
    }

    public void stopRecord() {
        AudioRecordManager.getInstance(mContext).stopRecord();
    }

    private static class BottomItemAdapter extends BaseQuickAdapter<ChatBottomBean.ChatBottomItem, BaseViewHolder> {

        BottomItemAdapter(int layoutResId, @Nullable List<ChatBottomBean.ChatBottomItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ChatBottomBean.ChatBottomItem item) {
            ImageView icon = helper.getView(R.id.icon);
            GlideApp.with(mContext).load(item.getImageUrl()).into(icon);
        }
    }

}
