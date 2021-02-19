package com.wd.winddots.message.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.components.users.UserInfoActivity;
import com.wd.winddots.listener.SoftKeyBoardListener;
import com.wd.winddots.message.adapter.DiscussChatBottomIconAdapter;
import com.wd.winddots.message.bean.ChatBottomBean;
import com.wd.winddots.mvp.presenter.impl.DiscussChatPresenterImpl;
import com.wd.winddots.mvp.view.DiscussChatView;
import com.wd.winddots.mvp.widget.adapter.rv.DiscussChatAdapter;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscussChatActivity extends CommonActivity<DiscussChatView, DiscussChatPresenterImpl>
        implements DiscussChatView, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {


    private Timer timer;
    private TimerTask task;
    private int timerInt = 0;


    private static final int REQUEST_CODE_AT = 10086;

    int keyboardShowTarget = 0;

    private LinearLayout mBody;
    private RelativeLayout mInputLayout;
    private RelativeLayout mSendLayout;
    private LinearLayout mSelectLayout;
    private ImageView mIvSelectPhoto;
    private ImageView mIvKeyboard;
    private PopupWindow mPopupWindow;

    private RecyclerView mRecyclerView;
    private RecyclerView mBottomRecyclerView;
    private String mId;
    private String mTitle;
    private String mUserId;
    private TextView mTvTime;
    private DiscussChatAdapter mAdapter;
    private EditText mEdtMessage;
    private TextView mTvSend;


    private DiscussChatBean mData;
    private List<DiscussChatBean.DataBean.CommentsBean> mCommentsBeanList = new ArrayList<>();
    private List<DiscussChatBean.DataBean.QuestionUsersBean> mAvatarList = new ArrayList<>();
    private List<DiscussChatBean.DataBean.QuestionUsersBean> atList = new ArrayList<>();
    private int mQuestionStatus = 1;  // 1开启状态  0 关闭状态


    private DiscussChatBottomIconAdapter mBottomAdapter;


    private RecyclerView mBottomItemRecyclerView;
    private List<ChatBottomBean.ChatBottomItem> mBottomItemList = new ArrayList<>();
    private BottomItemAdapter mBottomItemAdapter;



    @Override
    public DiscussChatPresenterImpl initPresenter() {
        return new DiscussChatPresenterImpl();
    }


    @Override
    public void initView() {
        super.initView();

        addBadyView(R.layout.activity_discuss_chat);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.more);
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getStringExtra("id");
            mTitle = intent.getStringExtra("title");
            setTitleText(mTitle);
        }
        mUserId = SpHelper.getInstance(mContext).getUserId();

        mBackIv.setVisibility(View.GONE);

        mBody = findViewById(R.id.rl_body);
        mInputLayout = findViewById(R.id.activity_chat_input_layout);
        mSendLayout = findViewById(R.id.activity_chat_send_layout);
        mSelectLayout = findViewById(R.id.activity_chat_select_layout);
        mEdtMessage = findViewById(R.id.activity_chat_send_edt);
        mTvSend = findViewById(R.id.send_tv);
        mIvSelectPhoto = findViewById(R.id.activity_chat_select_photo_iv);
        mIvKeyboard = findViewById(R.id.activity_chat_keyboard_iv);
        mTvTime = findViewById(R.id.activity_discuss_chat_time_tv);

        mRecyclerView = findViewById(R.id.activity_discuss_chat_rv);
        mAdapter = new DiscussChatAdapter(mContext, R.layout.item_discuss_chat, mCommentsBeanList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.mActivity = this;

        mBottomRecyclerView = findViewById(R.id.bottom_list);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBottomRecyclerView.setLayoutManager(layoutManager1);
        mBottomAdapter = new DiscussChatBottomIconAdapter(R.layout.item_group_chat_bottom_icon, mAvatarList);
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
                for (int i = 0;i < mBottomItemList.size();i++){
                    ChatBottomBean.ChatBottomItem item = mBottomItemList.get(i);
                    Map map = new HashMap();
                    map.put("id",item.getId());
                    applicationImages.add(map);
                }
                RequestBody body = Utils.list2requestBody(applicationImages);
                presenter.chatBottomItemSort(SpHelper.getInstance(mContext).getUserId(),body);


            }
        });
        mItemTouchHelper1.attachToRecyclerView(mBottomItemRecyclerView);

        ImageView groupIv = findViewById(R.id.iv_group);
        groupIv.setOnClickListener(this);

        ImageView backIv = ((ImageView) findViewById(R.id.activity_chat_back));
        backIv.setOnClickListener(this);
    }


    @Override
    public void initListener() {
        super.initListener();
        mRightIcon.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
        mIvKeyboard.setOnClickListener(this);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mEdtMessage.requestFocus();
                //mSelectLayout.setVisibility(View.GONE);
                mBottomItemRecyclerView.setVisibility(View.GONE);
                mSendLayout.setVisibility(View.VISIBLE);
                mRecyclerView.scrollToPosition(mCommentsBeanList.size() - 1);
                //mRecyclerView.scrollToPosition(mAdapter.getData().size());//弹出软键盘 列表滚动到最下方
            }

            @Override
            public void keyBoardHide(int height) {
                if (keyboardShowTarget == 2) {
                    if (timerInt < 2){
                        Log.e("net666", "keyBoardHide22222222222");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
                        //imm.showSoftInput(mEdtMessage,1);
                        mSendLayout.setVisibility(View.VISIBLE);
                        //mSelectLayout.setVisibility(View.GONE);
                        mBottomItemRecyclerView.setVisibility(View.GONE);
                        mEdtMessage.setFocusable(true);
                        mEdtMessage.setFocusableInTouchMode(true);
                        mEdtMessage.requestFocus();
                        isKeyboardShow = true;
                        return;
                    }
                }
                mSendLayout.setVisibility(View.GONE);
                mSelectLayout.setVisibility(View.VISIBLE);
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
                        List<DiscussChatBean.DataBean.QuestionUsersBean> usersBeans = mData.getData().getQuestionUsers();
                        for (int m = 0; m < usersBeans.size(); m++) {
                            DiscussChatBean.DataBean.QuestionUsersBean user = usersBeans.get(m);
                            if (!mUserId.equals(user.getUserId())) {
                                Map map = new HashMap();
                                map.put("name", user.getUserName());
                                map.put("id", user.getUserId());
                                map.put("avatar", user.getUserAvatar());
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
        mBottomItemAdapter.setOnItemClickListener(this);
    }


    @Override
    public void initData() {
        super.initData();
        presenter.getChatBottomItem(SpHelper.getInstance(mContext).getUserId(),2);
        presenter.initMessage(mId, SpHelper.getInstance(mContext).getUserId());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_chat_keyboard_iv:
                clickKeyboard();
                break;
            case R.id.send_tv:
                sendTextMessage();
                break;
            case R.id.iv_group://选择图片
                Intent intent = new Intent(mContext, DiscussChatDetailActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("title", mTitle);
                startActivity(intent);
                break;
            case R.id.activity_common_right_icon:
                showMenu();
                break;
            case R.id.tv_status:
                onStatusMenuDidClick();
                break;
            case R.id.tv_delete:
                onDeleteMenuDidClick();
                break;
            case R.id.activity_chat_back://底部返回
                back();
                break;
        }
    }

    private void back(){
        finish();
    }

    /*
     * 开启或变比问题
     * */
    private void onStatusMenuDidClick() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        String status = mQuestionStatus == 1 ? "关闭" : "开启";
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确定要" + status + "该问题吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int questionStatus = mQuestionStatus == 1 ? 0 : 1;
                presenter.updateDiscussStatus(mId, questionStatus);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    /*
     * 删除问题
     * */
    private void onDeleteMenuDidClick() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确定要删除该问题吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.deleteDiscuss(mId);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    private void showMenu() {
        if (mData == null) return;

        View mPopupView = View.inflate(this, R.layout.menu_discusschat, null);
        TextView statusBtn = mPopupView.findViewById(R.id.tv_status);
        TextView deleteBtn = mPopupView.findViewById(R.id.tv_delete);
        if (mQuestionStatus == 1) {
            statusBtn.setText(mContext.getString(R.string.discuss_status_close));
        } else {
            statusBtn.setText(mContext.getString(R.string.discuss_status_open));
        }
        statusBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        mPopupWindow = new PopupWindow(mPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopupWindow.setBackgroundDrawable(dw);
        int height = dip2px(mContext, 40) + getStatusBarHeight();
        mPopupWindow.showAtLocation(mBody, Gravity.TOP | Gravity.RIGHT, 30, height);
    }


    /*
     * 发送消息
     * */
    private void sendTextMessage() {
        String content = mEdtMessage.getText().toString();
        if (StringUtils.isNullOrEmpty(content)) {
            return;
        }

        String reg = "@\\w+ ";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        List<String> temp = new ArrayList<>();
        String atSting = "";
        while (matcher.find()) {
            Log.e("net666----", "atList");
            MatchResult mr = matcher.toMatchResult();
            String values = mr.group().trim();
            Log.e("net666----values", values);
            temp.add(values.substring(1));
        }

        Log.e("net666----", atSting);
        Log.e("net666----", "atList------" + atList);
        Log.e("net666----", "temp------" + temp);

        List<Map> ids = new ArrayList<>();
        for (int i = 0;i < temp.size();i++){
            String name = temp.get(i);
            for (int m = 0;m < atList.size();m++){
                DiscussChatBean.DataBean.QuestionUsersBean usersBean = atList.get(m);
                String atName = usersBean.getUserName();
                if (name.equals(atName)){
                    Map map = new HashMap();
                    map.put("userId",usersBean.getUserId());
                    ids.add(map);
                    break;
                }
            }
        }
        DiscussChatBean.DataBean.CommentsBean commentsBean = new DiscussChatBean.DataBean.CommentsBean();
        commentsBean.setUserId(SpHelper.getInstance(mContext).getUserId());
        commentsBean.setContent(content);
        commentsBean.setUserAvatar(SpHelper.getInstance(mContext).getAvatar());
        commentsBean.setReduceAvatar(SpHelper.getInstance(mContext).getAvatar());
        commentsBean.setUserName(SpHelper.getInstance(mContext).getName());
        mCommentsBeanList.add(commentsBean);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mCommentsBeanList.size() - 1);
        mEdtMessage.setText("");

        Map data = new HashMap();
        data.put("content", content);
        data.put("questionTitle", mTitle);
        data.put("userId", SpHelper.getInstance(mContext).getUserId());
        data.put("questionId", mId);
        data.put("workGroupCommentUsers",ids);
        RequestBody body = Utils.map2requestBody(data);
        presenter.sendMessage(body);

    }

    /*
     * 切换键盘
     * */
    private boolean isKeyboardShow;

    private void clickKeyboard() {
        if (isKeyboardShow) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.hideSoftInputFromWindow(mEdtMessage.getWindowToken(), 0);
            isKeyboardShow = false;
            mSendLayout.setVisibility(View.GONE);
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
            mEdtMessage.requestFocus();
            isKeyboardShow = true;
        }
    }


    @Override
    public void initMessageDataListSuccess(DiscussChatBean bean) {
        DiscussChatBean.DataBean data = bean.getData();
        if (data == null) return;
        mData = bean;
        mQuestionStatus = bean.getData().getQuestionStatus();
        List<DiscussChatBean.DataBean.QuestionUsersBean> list = mData.getData().getQuestionUsers();
        DiscussChatBean.DataBean.QuestionUsersBean lastBean = new  DiscussChatBean.DataBean.QuestionUsersBean();
        lastBean.setType("group");
        if (list != null && list.size() > 0) {
            mAvatarList.addAll(list);
            mAvatarList.add(lastBean);
            mBottomAdapter.notifyDataSetChanged();
        }
        // 设置时间段
        String startTime = data.getStartTime();
        String endTime = data.getEndTime();
        //       StringBuilder sb = new StringBuilder();
//        sb.append(CommonUtils.subTime2Min(startTime)).append(" ～ ").append(CommonUtils.subTime2Min(endTime));
//        mTvTime.setText(sb.toString());

        if (!StringUtils.isNullOrEmpty(data.getStartTime())) {
            mTvTime.setText(data.getStartTime() + "~" + data.getEndTime());
            mTvTime.setVisibility(View.VISIBLE);
        } else {
            mTvTime.setVisibility(View.GONE);
        }

        List<DiscussChatBean.DataBean.CommentsBean> comments = data.getComments();//显示条目

        // 添加第一条 头 要讨论的问题条目
        String relativePhotos = data.getRelativePhotos();
        String userName = data.getCreatorName();
        String creatorAvatar = data.getCreatorAvatar();
        String userId = data.getCreatorId();
        String createTime = data.getCreateTime();
        String content = data.getContent();

        DiscussChatBean.DataBean.CommentsBean commentsBean = new DiscussChatBean.DataBean.CommentsBean();
        commentsBean.setRelativePhotos(relativePhotos);
        commentsBean.setUserName(userName);
        commentsBean.setUserAvatar(creatorAvatar);
        commentsBean.setUserId(userId);
        commentsBean.setCreateTime(createTime);
        commentsBean.setContent(content);

        comments.add(0, commentsBean);
        mCommentsBeanList.addAll(comments);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mCommentsBeanList.size() - 1);
        //mAdapter.setNewData(comments);
        if (bean.getData().getCreatorId().equals(SpHelper.getInstance(mContext).getUserId())) {
            mRightIcon.setVisibility(View.VISIBLE);
        } else {
            mRightIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public void initMessageDataListError(String errMsg) {
    }

    @Override
    public void initMessageDataListComplete() {
    }

    /*
     * 添加会务评论
     * */
    @Override
    public void senMessageSuccess() {
    }

    @Override
    public void senMessageError() {
    }

    @Override
    public void senMessageComplete() {
    }

    /*
     * 删除会务
     * */
    @Override
    public void deleteDiscussSuccess() {
        showToast("操作成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void deleteDiscussError() {
        showToast("删除失败,请稍后重试");
    }

    @Override
    public void deleteDiscussComplete() {
    }

    /*
     * 开启或关闭会务
     * */
    @Override
    public void updateDiscussSuccess() {
        if (mQuestionStatus == 1) {
            mQuestionStatus = 0;
        } else {
            mQuestionStatus = 1;
        }
        showToast("操作成功");
    }

    @Override
    public void updateDiscussError() {
        showToast("操作失败,请稍后重试");
    }

    @Override
    public void updateDiscussComplete() {

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                            if (timerInt > 3){
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

        if (data == null) {
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
                            if (timerInt > 3){
                                keyboardShowTarget = 0;
                               if (timer != null){
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
            DiscussChatBean.DataBean.QuestionUsersBean usersBean = new DiscussChatBean.DataBean.QuestionUsersBean();
            usersBean.setUserId(data.getStringExtra("id"));
            usersBean.setUserName(name);
//            Map atMap = new HashMap();
//            atMap.put("name",name);
//            atMap.put("userId",data.getStringExtra("id"));
            atList.add(usersBean);
        }

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == mBottomAdapter) {

            DiscussChatBean.DataBean.QuestionUsersBean usersBean = mAvatarList.get(position);
            if ("group".equals(usersBean.getType())){
                Intent intent = new Intent(mContext, DiscussChatDetailActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("title", mTitle);
                startActivity(intent);
            }else {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra("id", mAvatarList.get(position).getUserId());
                startActivity(intent);
            }
        }else if (adapter == mBottomItemAdapter) {
            ChatBottomBean.ChatBottomItem item = mBottomItemList.get(position);
            switch (item.getType()) {
                case "back":
                    finish();
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == mBottomAdapter && !mUserId.equals(mAvatarList.get(position).getUserId())) {

            if (!mEdtMessage.isFocused()){
                clickKeyboard();
            }


            String text = Utils.nullOrEmpty(mEdtMessage.getText().toString());
            text = text + "@" + mAvatarList.get(position).getUserName() + " ";
            mEdtMessage.setText(text);
            mEdtMessage.setSelection(text.length());
//            DiscussChatBean.DataBean.QuestionUsersBean usersBean = new DiscussChatBean.DataBean.QuestionUsersBean();
//            usersBean.setUserId(data.getStringExtra("id"));
//            usersBean.setUserName(name);
//            Map atMap = new HashMap();
//            atMap.put("name",name);
//            atMap.put("userId",data.getStringExtra("id"));
            atList.add(mAvatarList.get(position));
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        if (timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }


    private static class BottomItemAdapter extends BaseQuickAdapter<ChatBottomBean.ChatBottomItem, BaseViewHolder>{

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
