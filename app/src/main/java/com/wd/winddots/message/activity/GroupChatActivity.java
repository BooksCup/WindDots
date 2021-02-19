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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.message.adapter.GroupChatBottomIconAdapter;
import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.message.bean.GroupChatHistoryBean;
import com.wd.winddots.listener.SoftKeyBoardListener;
import com.wd.winddots.message.bean.GroupChatMsgModeBean;
import com.wd.winddots.message.presenter.impl.GroupChatPresenterImpl;
import com.wd.winddots.message.presenter.view.GroupChatView;
import com.wd.winddots.message.adapter.GroupChatAdapter;
import com.wd.winddots.message.record.RecordAudioButton;
import com.wd.winddots.message.record.RecordVoicePopWindow;
import com.wd.winddots.message.record.audio.AudioPlayManager;
import com.wd.winddots.message.record.audio.AudioRecordManager;
import com.wd.winddots.message.record.audio.IAudioRecordListener;
import com.wd.winddots.mvp.widget.WebViewActivity;
import com.wd.winddots.utils.CommonUtil;
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
import java.util.Timer;
import java.util.TimerTask;

public class GroupChatActivity extends CommonActivity<GroupChatView, GroupChatPresenterImpl>
        implements
        GroupChatView,
        BaseQuickAdapter.UpFetchListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemLongClickListener {


    private static final int MAX_VOICE_TIME = 30;//声音最大时间
    private static final String AUDIO_DIR_NAME = "audio_cache";
    private File mAudioDir;
    private List<File> mListData = new ArrayList<>();

    private Timer timer;
    private TimerTask task;
    private int timerInt = 0;
    int keyboardShowTarget = 0;

    private static final int REQUEST_CODE_AT = 10086;
    private static final int REQUEST_CODE_CAMARE = 10000;
    private static final int REQUEST_CODE_STORAGE = 10010;
    private static final int REQUEST_CODE_TAKE_PHOTO = 10001;
    private static final int REQUEST_CODE_ADD_MEMBERS = 10002;
    private static final int REQUEST_CODE_RECORD_AUDIO = 10003; //录音权限

    private RecyclerView mRecyclerView;
    private RecyclerView mBottomRecyclerView;
    private TextView mTvHeaderLoadMore;
    private EditText mEdtMessage;
    private TextView mTvSend;
    private RecordAudioButton mVoiceRecord;
    private LinearLayout mRoot;

    private RecordVoicePopWindow mRecordVoicePopWindow;//提示

    long beforeJCreateTime = 0;
    private RelativeLayout mSendLayout;
    private LinearLayout mSelectLayout;
    private ImageView mIvSelectPhoto;
    private ImageView mIvKeyboard;
    private RelativeLayout mInputLayout;
    private String mId;
    private String mTitle;
    private String mTargetId;
    private String mGroupId;
    private String mUserId;

    //用于保存拍照图片的uri
    private Uri mCameraUri;

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private String mCameraImagePath;

    private List<GroupChatHistoryBean.MessageListBean> mMessageListBeans = new ArrayList<>();
    private List<GroupChatHistoryBean.AvatarMapBean> mAvatarMapBeans = new ArrayList<>();
    private List<GroupChatHistoryBean.AvatarMapBean> atList = new ArrayList<>();
    private GroupChatAdapter mAdapter;
    private GroupChatBottomIconAdapter mBottomAdapter;


    private RecyclerView mBottomItemRecyclerView;
    private List<ChatBottomBean.ChatBottomItem> mBottomItemList = new ArrayList<>();
    private BottomItemAdapter mBottomItemAdapter;

    private long loadJCreateTime;

    private int target = 0;


    @Override
    public GroupChatPresenterImpl initPresenter() {
        return new GroupChatPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_group_chat);
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("title");
            mId = intent.getStringExtra("id");
            mTargetId = intent.getStringExtra("targetId");
            mGroupId = intent.getStringExtra("groupId");
            setTitleText(mTitle);
        }

        mAudioDir = new File(mContext.getExternalCacheDir(), AUDIO_DIR_NAME);
        if (!mAudioDir.exists()) {//判断文件夹是否存在，不存在则创建
            mAudioDir.mkdirs();
        }

        initAudioManager();

        mBackIv.setVisibility(View.GONE);
        mUserId = SpHelper.getInstance(mContext).getUserId();

        mRoot = findViewById(R.id.rl_root);
        mRecyclerView = ((RecyclerView) findViewById(R.id.activity_group_chat_rv));
        mBottomRecyclerView = ((RecyclerView) findViewById(R.id.bottom_list));
        mInputLayout = ((RelativeLayout) findViewById(R.id.activity_chat_input_layout));
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
        mAdapter = new GroupChatAdapter(mContext, R.layout.item_group_chat, mMessageListBeans, SpHelper.getInstance(mContext).getUserId());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setUpFetchEnable(true);
        mAdapter.setUpFetchListener(this);
        mAdapter.mActivity = this;

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBottomRecyclerView.setLayoutManager(layoutManager1);
        mBottomAdapter = new GroupChatBottomIconAdapter(R.layout.item_group_chat_bottom_icon, mAvatarMapBeans);
        mBottomRecyclerView.setAdapter(mBottomAdapter);
        mBottomAdapter.setOnItemClickListener(this);
        mBottomAdapter.setOnItemLongClickListener(this);


        mBottomItemRecyclerView = findViewById(R.id.bottom_item_list);
        GridLayoutManager manager1 = new GridLayoutManager(mContext, 7);
        mBottomItemRecyclerView.setLayoutManager(manager1);
        mBottomItemAdapter = new BottomItemAdapter(R.layout.item_chat_bottom, mBottomItemList);
        mBottomItemRecyclerView.setAdapter(mBottomItemAdapter);

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
                mBottomItemAdapter.notifyItemMoved(fromPosition, toPosition);
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
        mItemTouchHelper1.attachToRecyclerView(mBottomItemRecyclerView);


        ImageView camareIv = ((ImageView) findViewById(R.id.activity_chat_camare_iv));
        camareIv.setOnClickListener(this);


        ViewTreeObserver vto = mRecyclerView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = mRecyclerView.getMeasuredHeight();
                int width = mRecyclerView.getMeasuredWidth();
                //tvValues.append("方法三: height:"+height + ",width:" + width + "..\n");
                Log.e("net666", height + "mmmmm" + width);
                return true;
            }
        });

        ImageView backIv = ((ImageView) findViewById(R.id.activity_chat_back));
        backIv.setOnClickListener(this);


        mVoiceRecord.setOnVoiceButtonCallBack(new RecordAudioButton.OnVoiceButtonCallBack() {
            @Override
            public void onStartRecord() {
                AudioPlayManager.getInstance().stopPlay();
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

        mTvSend.setOnClickListener(this);
        mIvKeyboard.setOnClickListener(this);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mEdtMessage.requestFocus();
                //mSelectLayout.setVisibility(View.GONE);
                mBottomItemRecyclerView.setVisibility(View.GONE);
                mSendLayout.setVisibility(View.VISIBLE);
                mVoiceRecord.setVisibility(View.GONE);
                //mRecyclerView.scrollToPosition(mAdapter.getData().size());//弹出软键盘 列表滚动到最下方
                if (target < 10) {
                    mRecyclerView.scrollToPosition(mAdapter.getData().size() - 1);

                } else {
                    mRecyclerView.scrollToPosition(mAdapter.getData().size());
                }
                Log.e("net666", height + "height");


            }

            @Override
            public void keyBoardHide(int height) {

                if (keyboardShowTarget == 2) {
                    if (timerInt < 2) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
                        //imm.showSoftInput(mEdtMessage,1);
                        mSendLayout.setVisibility(View.VISIBLE);
                        //mSelectLayout.setVisibility(View.GONE);
                        mVoiceRecord.setVisibility(View.GONE);
                        mBottomItemRecyclerView.setVisibility(View.GONE);
                        mEdtMessage.setFocusable(true);
                        mEdtMessage.setFocusableInTouchMode(true);
                        mEdtMessage.requestFocus();
                        isKeyboardShow = true;
                        return;
                    }
                }

                mSendLayout.setVisibility(View.GONE);
                //mSelectLayout.setVisibility(View.VISIBLE);
                mBottomItemRecyclerView.setVisibility(View.VISIBLE);
                mEdtMessage.clearFocus();
            }
        });


        mEdtMessage.addTextChangedListener(new TextWatcher() {

            int inputSize = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = mEdtMessage.getText().toString();
                if (!StringUtils.isNullOrEmpty(text)) {
                    String text1 = text.substring(text.length() - 1);
                    if (text1.equals("@") && text.length() > inputSize) {
                        List<Map> atList = new ArrayList<>();
                        List<GroupChatHistoryBean.AvatarMapBean> usersBeans = mAvatarMapBeans;
                        for (int m = 0; m < usersBeans.size(); m++) {
                            GroupChatHistoryBean.AvatarMapBean user = usersBeans.get(m);
                            if (!mUserId.equals(user.getId())) {
                                Map map = new HashMap();
                                map.put("name", user.getName());
                                map.put("id", user.getId());
                                map.put("avatar", user.getAvatar());
                                atList.add(map);
                            }
                        }
                        Gson gson = new Gson();
                        String jsonS = gson.toJson(atList);
                        Intent intent = new Intent(mContext, AtListActivity.class);
                        intent.putExtra("data", jsonS);
                        startActivityForResult(intent, REQUEST_CODE_AT);
                        // mEdtMessage.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        //imm.hideSoftInputFromWindow(mEdtMessage.getWindowToken(), 0);
                    }
                }
                inputSize = text.length();
            }
        });


        mIvSelectPhoto.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mBottomItemAdapter.setOnItemClickListener(this);
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
                sendImgMessage();
                break;
            case R.id.activity_chat_camare_iv://选择图片
                checkStorage();
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

    private void checkStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                onCamareDidClick();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
            }
        } else {
            onCamareDidClick();
        }
    }

    private void onCamareDidClick() {
        String[] cameraPermissions = new String[]{Manifest.permission.CAMERA};
        requestPermissions(this, cameraPermissions, REQUEST_CODE_CAMARE);
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


    private boolean isKeyboardShow;

    private void clickKeyboard() {
        if (isKeyboardShow) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.hideSoftInputFromWindow(mEdtMessage.getWindowToken(), 0);
            isKeyboardShow = false;
            mSendLayout.setVisibility(View.GONE);
            mVoiceRecord.setVisibility(View.GONE);
            //mSelectLayout.setVisibility(View.VISIBLE);
            mBottomItemRecyclerView.setVisibility(View.VISIBLE);
            mEdtMessage.clearFocus();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
            //imm.showSoftInput(mEdtMessage,1);
            mSendLayout.setVisibility(View.VISIBLE);
            //mSelectLayout.setVisibility(View.GONE);
            mBottomItemRecyclerView.setVisibility(View.GONE);
            mVoiceRecord.setVisibility(View.GONE);
            mEdtMessage.requestFocus();
            isKeyboardShow = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlayManager.getInstance().stopPlay();
    }

    @Override
    public void initData() {
        super.initData();
        presenter.getChatBottomItem(SpHelper.getInstance(mContext).getUserId(), 2);
        presenter.getGroupMembers(mGroupId);
        presenter.initMessage(SpHelper.getInstance(mContext).getUserId(), mId, 0);
    }

    @Override
    public void onUpFetch() {
        presenter.loadMoreMessage(SpHelper.getInstance(mContext).getUserId(), mId, loadJCreateTime);
    }

    @Override
    public void initChatMessageHistorySuccess(GroupChatHistoryBean bean) {

        List<GroupChatHistoryBean.AvatarMapBean> avatarMap = bean.getAvatarMap();
        //mAvatarMapBeans.addAll(avatarMap);

        final List<GroupChatHistoryBean.MessageListBean> messageList = bean.getMessageList();
        //消息倒序
        Collections.reverse(messageList);
        loadJCreateTime = messageList.get(0).getJCreateTime();
        mAdapter.setNewData(messageList);
        //显示最新消息
        int size = messageList.size();

        // TODO: 2020/4/16 设置时间可见不可见
        for (int i = 0; i < size; i++) {
            GroupChatHistoryBean.MessageListBean messageListBean = messageList.get(i);
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

        if (size < 8) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            assert linearLayoutManager != null;
            linearLayoutManager.setStackFromEnd(false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.scrollToPosition(messageList.size());
        } else {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            assert linearLayoutManager != null;
            linearLayoutManager.setStackFromEnd(true);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }


        if (size == 10) {
            if (mTvHeaderLoadMore == null) {
                mTvHeaderLoadMore = (TextView) LayoutInflater.from(mContext).inflate(R.layout.header_chat_loadmore, null, false);
            }
            mAdapter.addHeaderView(mTvHeaderLoadMore);
            //loadJCreateTime = messageList.get(messageList.size()-1).getJCreateTime();
//            mTvHeaderLoadMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    presenter.loadMoreMessage(MyApplication.USER_ID, mId, messageList.get(0).getJCreateTime());
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


    // TODO: 2020/4/21 加载更多
    @Override
    public void loadMoreChatMessageHistorySuccess(GroupChatHistoryBean bean) {
        final List<GroupChatHistoryBean.MessageListBean> messageList = bean.getMessageList();


        //消息倒序
        Collections.reverse(messageList);
        loadJCreateTime = messageList.get(0).getJCreateTime();
        mAdapter.addData(0, messageList);
        mAdapter.removeAllHeaderView();

//        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)  mRecyclerView.getLayoutManager();
//        assert linearLayoutManager != null;
//        linearLayoutManager.setStackFromEnd(true);
//        mRecyclerView.setLayoutManager(linearLayoutManager);

        target = mAdapter.getData().size();
        int size = messageList.size();

        // TODO: 2020/4/16 设置时间显示可见不可见
        for (int i = 0; i < size; i++) {
            GroupChatHistoryBean.MessageListBean messageListBean = messageList.get(i);
            long jCreateTime = messageListBean.getJCreateTime();
            if (Math.abs(jCreateTime - beforeJCreateTime) > 3 * 60 * 1000) {
                messageListBean.setShowTime(CommonUtil.descriptiveData(messageListBean.getjCreateTime()));
            }
            beforeJCreateTime = jCreateTime;
        }


        if (size == 10) {
            if (mTvHeaderLoadMore == null) {
                mTvHeaderLoadMore = (TextView) LayoutInflater.from(mContext).inflate(R.layout.header_chat_loadmore, null, false);
            }

            mAdapter.addHeaderView(mTvHeaderLoadMore);
//            mTvHeaderLoadMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    presenter.loadMoreMessage(MyApplication.USER_ID, mId, messageList.get(0).getJCreateTime());
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
    public void getGroupMembersSuccess(List<GroupChatHistoryBean.AvatarMapBean> list) {
        mAvatarMapBeans.clear();
        mAvatarMapBeans.addAll(list);
        GroupChatHistoryBean.AvatarMapBean delete = new GroupChatHistoryBean.AvatarMapBean();
        delete.setType("delete");
        mAvatarMapBeans.add(delete);
        mBottomAdapter.notifyDataSetChanged();
    }

    @Override
    public void getGroupMembersError() {

    }

    @Override
    public void getGroupMembersComplete() {

    }


    /**
     * 发送文本消息
     */
    private void sendTextMessage() {
        String sendText = mEdtMessage.getText().toString();
        mEdtMessage.setText("");
        if (!TextUtils.isEmpty(sendText)) {

            List<Map> messageUserGroupDTOs = new ArrayList<>();
            for (int i = 0;i < atList.size();i++){
                Map map = new HashMap();
                GroupChatHistoryBean.AvatarMapBean avatarMapBean = atList.get(i);
                map.put("userId",avatarMapBean.getId());
                messageUserGroupDTOs.add(map);
            }
            RequestBody body = Utils.list2requestBody(messageUserGroupDTOs);
            presenter.atMembers(SpHelper.getInstance(mContext).getUserId(),mGroupId,body);
            presenter.postText(SpHelper.getInstance(mContext).getUserId(), mGroupId, sendText, "group", "text");
            mEdtMessage.setText("");
            GroupChatHistoryBean.MessageListBean messageListBean = new GroupChatHistoryBean.MessageListBean();
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
            List<GroupChatHistoryBean.MessageListBean> data = mAdapter.getData();
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
            List<GroupChatHistoryBean.MessageListBean> messageList = mAdapter.getData();

            if (target < 10) {
                mRecyclerView.scrollToPosition(messageList.size() - 1);
            } else {
                mRecyclerView.scrollToPosition(messageList.size());
            }

            Log.e("net666", "ttttttttttttttttttt");

        }
    }

    @Override
    public void postMessageSuccess() {
    }
    @Override
    public void postMessageError() {
    }
    @Override
    public void postMessageComplete() {
    }

    /*
    * 发送语音
    * */
    @Override
    public void postVoiceSuccess(String data) {
    }
    @Override
    public void postVoiceError(String errMsg) {
    }

    /*
    * 发送图片
    * */
    @Override
    public void postImageSuccess(String data) {
    }
    @Override
    public void postImageError(String errMsg) {
    }


    /*
     * 底部功能按钮
     * */
    @Override
    public void getChatBottomItemSuccess(ChatBottomBean bean) {
        mBottomItemList.addAll(bean.getData());
        mBottomItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void getChatBottomItemError(String errMsg) {

    }

    @Override
    public void getChatBottomItemErrorComplete() {

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

            GroupChatHistoryBean.MessageListBean messageListBean = new GroupChatHistoryBean.MessageListBean();
            messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
            messageListBean.setImageUri(uri);
            messageListBean.setMsgType("image");
            messageListBean.setFromUserAvatar(getHeaderImgFromUserId(SpHelper.getInstance(mContext).getUserId()));

            mAdapter.addData(messageListBean);
            List<GroupChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
            mRecyclerView.scrollToPosition(messageList.size());


            RequestBody body = Utils.uri2requestBody(mContext, uri);

            String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            presenter.postImage(body, SpHelper.getInstance(mContext).getUserId(), mGroupId);
        }


        if (requestCode == REQUEST_CODE_AT && data == null) {
            keyboardShowTarget = 2;
            task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerInt += 1;
                            Log.e("net666---timerInt", String.valueOf(timerInt));
                            if (timerInt > 3) {
                                keyboardShowTarget = 0;
                                timer.cancel();
                                timer = null;
                            }
                        }
                    });
                }
            };
            timer = new Timer();
            timerInt = 0;
            timer.schedule(task, 0, 1000);
            return;
        }


        if (requestCode == REQUEST_CODE_AT) {
            keyboardShowTarget = 2;
            task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerInt += 1;
                            Log.e("net666---timerInt", String.valueOf(timerInt));
                            if (timerInt > 3) {
                                keyboardShowTarget = 0;
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                }
                            }
                        }
                    });
                }
            };
            timer = new Timer();
            timerInt = 0;
            timer.schedule(task, 0, 1000);
            Log.e("net666", "onActivityResult");
            String name = data.getStringExtra("name");
            String text = Utils.nullOrEmpty(mEdtMessage.getText().toString());
            text = text + name + " ";
            mEdtMessage.setText(text);
            mEdtMessage.setSelection(text.length());
            GroupChatHistoryBean.AvatarMapBean usersBean = new GroupChatHistoryBean.AvatarMapBean();
            usersBean.setId(data.getStringExtra("id"));
            usersBean.setName(name);
//            Map atMap = new HashMap();
//            atMap.put("name",name);
//            atMap.put("userId",data.getStringExtra("id"));
            atList.add(usersBean);
            return;
        }

        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            if (isAndroidQ) {
                if (resultCode == 0) {
                    return;
                }
                Uri uri = mCameraUri;//Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                GroupChatHistoryBean.MessageListBean messageListBean = new GroupChatHistoryBean.MessageListBean();
                messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
                messageListBean.setImageUri(uri);
                messageListBean.setMsgType("image");
                messageListBean.setFromUserAvatar(getHeaderImgFromUserId(SpHelper.getInstance(mContext).getUserId()));

                mAdapter.addData(messageListBean);
                List<GroupChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
                mRecyclerView.scrollToPosition(messageList.size());
                RequestBody body = Utils.uri2requestBody(mContext, mCameraUri);
                //String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                presenter.postImage(body, SpHelper.getInstance(mContext).getUserId(), mGroupId);

                mCameraUri = null;
            } else {
                // 使用图片路径加载
                //ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
                if (resultCode == 0) {
                    return;
                }
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mCameraImagePath), null, null));
                GroupChatHistoryBean.MessageListBean messageListBean = new GroupChatHistoryBean.MessageListBean();
                messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
                messageListBean.setImageUri(uri);
                messageListBean.setMsgType("image");
                messageListBean.setFromUserAvatar(getHeaderImgFromUserId(SpHelper.getInstance(mContext).getUserId()));

                mAdapter.addData(messageListBean);
                List<GroupChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
                mRecyclerView.scrollToPosition(messageList.size());
                RequestBody body = Utils.uri2requestBody(mContext, uri);
                //String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                presenter.postImage(body, SpHelper.getInstance(mContext).getUserId(), mGroupId);

                mCameraImagePath = null;
            }
            return;
        }

        if (requestCode == REQUEST_CODE_ADD_MEMBERS && data != null) {
            String dataString = data.getStringExtra("data");
            if ("delete".equals(dataString)) {
                finish();
            } else {
                presenter.getGroupMembers(mGroupId);
            }
            return;
        }


    }

    /**
     * 根据userId来查找头像
     *
     * @param userId
     * @return
     */
    private String getHeaderImgFromUserId(String userId) {
        String userHeaderImg = "";
        int size = mAvatarMapBeans.size();

        for (int i = 0; i < size; i++) {
            GroupChatHistoryBean.AvatarMapBean avatarMapBean = mAvatarMapBeans.get(i);
            if (!TextUtils.isEmpty(userId) && userId.equals(avatarMapBean.getId())) {
                userHeaderImg = avatarMapBean.getAvatar();
                break;
            }
        }
        return userHeaderImg;
    }


    /**
     * 接收到新消息
     *
     * @param newMessageListBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetNewMessage(GroupChatHistoryBean.MessageListBean newMessageListBean) {

        Log.e("ssss", "全局接收到群聊新消息：333");
        Gson gson = new Gson();
        String jsonS = gson.toJson(newMessageListBean);
        Log.e("net666", jsonS);

        if (SpHelper.getInstance(mContext).getUserId().equals(newMessageListBean.getFromId()) || "系统消息".equals(newMessageListBean.getFromId())) {
            return;
        }


        if ("image".equals(newMessageListBean.getMsgType())) {
            if (newMessageListBean.getFromId().equals(SpHelper.getInstance(mContext).getUserId())) {
                return;
            }
        }

        List<GroupChatHistoryBean.MessageListBean> data = mAdapter.getData();
        int dataSize = data.size();

        String fromId = newMessageListBean.getFromId();
        String targetId = newMessageListBean.getTargetId();
        long newJCreateTime = newMessageListBean.getjCreateTime();
        newMessageListBean.setFromUserAvatar(getHeaderImgFromUserId(fromId));//设置头像
        newMessageListBean.setFromUserName("");

        // 设置时间
        if (dataSize > 0) {
            long jCreateTime = data.get(dataSize - 1).getJCreateTime();
            if (newJCreateTime - jCreateTime > 3 * 60 * 1000) {
                newMessageListBean.setShowTime(CommonUtil.descriptiveData(newJCreateTime));
            }
        }

        // 如果接收到的新消息是本群的id （targetId） 那么就添加到底部
        if (!TextUtils.isEmpty(targetId) && targetId.equals(mTargetId)) {
            //showToast("newmessage");
            mAdapter.addData(newMessageListBean);
            List<GroupChatHistoryBean.MessageListBean> messageList = mAdapter.getData();

            //mRecyclerView.scrollToPosition(messageList.size()-1);
            if (target < 10) {
                mRecyclerView.scrollToPosition(messageList.size() - 1);
            } else {
                mRecyclerView.scrollToPosition(messageList.size());
            }
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter == mBottomAdapter) {
            GroupChatHistoryBean.AvatarMapBean bean = mAvatarMapBeans.get(position);

            if ("add".equals(bean.getType())) {
                Intent intent = new Intent(mContext, AddMenbersActivity.class);
                List<String> ids = new ArrayList<>();
                for (int i = 0; i < mAvatarMapBeans.size(); i++) {
                    GroupChatHistoryBean.AvatarMapBean bean1 = mAvatarMapBeans.get(i);
                    if (!StringUtils.isNullOrEmpty(bean1.getId())) {
                        ids.add(bean1.getId());
                    }
                }
                Gson gson = new Gson();
                String jsonS = gson.toJson(ids);
                intent.putExtra("data", jsonS);
                intent.putExtra("groupId", mGroupId);
                startActivityForResult(intent, REQUEST_CODE_ADD_MEMBERS);
            } else if ("delete".equals(bean.getType())) {

                Intent intent = new Intent(mContext, GroupMembersActivity.class);
                intent.putExtra("groupId", mGroupId);
                intent.putExtra("title", mTitle);
                startActivityForResult(intent, REQUEST_CODE_ADD_MEMBERS);

            } else if (!StringUtils.isNullOrEmpty(bean.getId())) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("id", bean.getId());
                startActivity(intent);
            }


        } else if (adapter == mBottomItemAdapter) {

            ChatBottomBean.ChatBottomItem item = mBottomItemList.get(position);
            switch (item.getType()) {
                case "back":
                    finish();
                    break;
                case "camera":
                    checkStorage();
                    break;
                case "picture":
                    sendImgMessage();
                    break;
                case "voice":
                    //sendImgMessage();
//                    mBottomRecyclerView.setVisibility(View.GONE);
//                    mVoiceRecord.setVisibility(View.VISIBLE);
                    String[] cameraPermissions = new String[]{Manifest.permission.RECORD_AUDIO};
                    requestPermissions(this, cameraPermissions, REQUEST_CODE_RECORD_AUDIO);
                    break;
            }


        } else {
            GroupChatHistoryBean.MessageListBean messageListBean = mAdapter.getData().get(position);
            if ("custom_message".equals(messageListBean.getMsgType())) {
                String body = messageListBean.getBody();
                body = body.replaceAll("\\\\", "");
                Gson gson = new Gson();
                GroupChatMsgModeBean.CustomMessageBean customMessageBean = gson.fromJson(body, GroupChatMsgModeBean.CustomMessageBean.class);
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Const.WEB_ACTIVITY_URL_INTENT, customMessageBean.getExtras().getUrl());
                intent.putExtra("title", customMessageBean.getText());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == mBottomAdapter && !mUserId.equals(mAvatarMapBeans.get(position).getId())) {

            if (!mEdtMessage.isFocused()) {
                clickKeyboard();
            }

            String text = Utils.nullOrEmpty(mEdtMessage.getText().toString());
            text = text + "@" + mAvatarMapBeans.get(position).getName() + " ";
            mEdtMessage.setText(text);
            mEdtMessage.setSelection(text.length());
//            DiscussChatBean.DataBean.QuestionUsersBean usersBean = new DiscussChatBean.DataBean.QuestionUsersBean();
//            usersBean.setUserId(data.getStringExtra("id"));
//            usersBean.setUserName(name);
//            Map atMap = new HashMap();
//            atMap.put("name",name);
//            atMap.put("userId",data.getStringExtra("id"));
            atList.add(mAvatarMapBeans.get(position));
        }
        return true;
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
//                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                        onCamareDidClick();
                        break;
                    case REQUEST_CODE_RECORD_AUDIO:
//                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                        Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                        mBottomItemRecyclerView.setVisibility(View.GONE);
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
                    onCamareDidClick();
                    break;
                case REQUEST_CODE_RECORD_AUDIO:
//                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
//                    Objects.requireNonNull(this).startActivityForResult(intent1, REQUEST_CODE_SCAN);
                    mBottomItemRecyclerView.setVisibility(View.GONE);
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
                    content = "在设置-应用-瓦丁-权限中开启打开录音权限，以正常使用发送语音等功能";
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


    @Override
    protected void onStop() {
        presenter.leaveGroup(SpHelper.getInstance(mContext).getUserId(), mGroupId);
        super.onStop();
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

                    GroupChatHistoryBean.MessageListBean messageListBean = new GroupChatHistoryBean.MessageListBean();
                    messageListBean.setFromId(SpHelper.getInstance(mContext).getUserId());
                    messageListBean.setVoiceUri(audioPath);
                    messageListBean.setMsgType("voice_message_group");
                    messageListBean.setFromUserAvatar(getHeaderImgFromUserId(SpHelper.getInstance(mContext).getUserId()));
                    List<GroupChatHistoryBean.MessageListBean> messageList = mAdapter.getData();
                    mRecyclerView.scrollToPosition(messageList.size());
                    MediaPlayer meidaPlayer = new MediaPlayer();
                    try {
                        meidaPlayer.setDataSource(file.getPath());
                        meidaPlayer.prepare();
                        long time = meidaPlayer.getDuration();
                        long seconds = time % 60000 ;
                        int second = Math.round((float)seconds/1000);
                        messageListBean.setAudioLength(second);
                        // showToast(second + "");
                        mAdapter.addData(messageListBean);
                        mRecyclerView.scrollToPosition(messageList.size());
                        presenter.postVoice(body,SpHelper.getInstance(mContext).getUserId(),mGroupId,second);
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
