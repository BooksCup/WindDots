package com.wd.winddots.message.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.haibin.calendarview.Calendar;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.components.calendar.range.CalendarRangeView;
import com.wd.winddots.components.users.MultipleUserSelectActivity;
import com.wd.winddots.components.users.UserBean;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.entity.User;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.message.presenter.impl.DiscussChatAddPresenterImpl;
import com.wd.winddots.message.presenter.view.DiscussChatAddView;
import com.wd.winddots.schedule.adapter.ScheduleUserAdapter;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.EditCell;
import com.wd.winddots.view.TitleTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * FileName: ScheduleDetailActivity
 * Author: 郑
 * Date: 2020/6/4 9:32 AM
 * Description:
 */
public class DiscussChatAddActivity extends CommonActivity<DiscussChatAddView, DiscussChatAddPresenterImpl>
        implements DiscussChatAddView, View.OnClickListener, CalendarRangeView.CalendarRangeViewActionListener,
        MineClaimingImagePickerAdpater.MineClaimingImagePickerAdpaterDeleteListener, BaseQuickAdapter.OnItemClickListener{


    private RecyclerView mRecyclerView;

    private EditCell mTitleEditCell;
    private TitleTextView mDatePicker;
    private EditText mContentEditText;
    private LinearLayout mBottomBar;
    private RecyclerView mImageRecyclerView;
    private MineClaimingImagePickerAdpater mImageListAdapter;

    private ScheduleUserAdapter mAdapter;


    private PopupWindow mPopupWindow;
    private LinearLayout mBody;
    private CalendarRangeView mCalendarRangeView;


    private String mStartTime;
    private String mEndTime;
    private List<User> userBeanList = new ArrayList<>();

    private List<String> imageUrlList = new ArrayList<>();
    private List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();
    private String mUserId;

    private DiscussChatBean mData;

    private int mUploadLength = 0;
    private Map mUploadData;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                Gson gson = new Gson();
                mUploadData.put("relativePhotos",gson.toJson(imageUrlList));
                if (mData == null){
                    String bodyString = gson.toJson(mUploadData);
                    Log.e("net666",bodyString);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
                    presenter.addDiscuss(requestBody);
                }else {
                    mUploadData.put("id",mData.getData().getId());
                    String bodyString = gson.toJson(mUploadData);
                    Log.e("net666",bodyString);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
                    presenter.editDiscuss(requestBody);
                }

            }  else if (msg.what == 10087) {
                hideLoading();
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };




    @Override
    public DiscussChatAddPresenterImpl initPresenter() {
        return new DiscussChatAddPresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_discuss_add);

        mUserId = SpHelper.getInstance(mContext).getUserId();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (!StringUtils.isNullOrEmpty(data)){
            setTitleText("编辑群聊");
            DiscussChatBean discussChatBean = new Gson().fromJson(data, DiscussChatBean.class);
            mData = discussChatBean;
        }else {
            setTitleText("新建群聊");
        }


        final LinearLayout userPicker = findViewById(R.id.activity_discussadd_user);
        TextView commitBtn = findViewById(R.id.activity_discussadd_commit);

        mTitleEditCell = findViewById(R.id.et_title);
        mTitleEditCell.setTitle(mContext.getString(R.string.title));
        mTitleEditCell.setHint("群聊");
        //mTitleEditCell.setText("群聊");
        //mTitleEditCell.mContentEditText.setSelection(2);
        mDatePicker = findViewById(R.id.view_datepicker);
        mBottomBar = findViewById(R.id.activity_schedule_bottomBar);
//        mTitleEditText = findViewById(R.id.activity_discussadd_title);
//        mDateTextView = findViewById(R.id.activity_discussadd__datepicktext);
        mRecyclerView = findViewById(R.id.activity_discussadd_userList);
        mContentEditText = findViewById(R.id.activity_discussadd_content);
        mBody = findViewById(R.id.activity_discussadd_body);

        mAdapter = new ScheduleUserAdapter(R.layout.item_schedule_user, userBeanList);
        GridLayoutManager manager = new GridLayoutManager(mContext, 5);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    userPicker.performClick();
                }
                return false;
            }
        });

        int itemS = getScreenWidth() / 3;
        mImageRecyclerView = findViewById(R.id.activity_discussadd_imageList);
        mImageListAdapter = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, imageList, itemS);
        mImageListAdapter.setOnItemClickListener(this);
        mImageListAdapter.setMineClaimingImagePickerAdpaterDeleteListener(this);
        GridLayoutManager manager1 = new GridLayoutManager(mContext, 3);
        mImageRecyclerView.setLayoutManager(manager1);
        mImageRecyclerView.setAdapter(mImageListAdapter);
        int rowCount = ((imageList.size() - 1) / 3) + 1;
        mImageRecyclerView.getLayoutParams().height = rowCount * itemS;

        mDatePicker.setOnClickListener(this);
        userPicker.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }


    @Override
    public void initData() {
        super.initData();

        if (mData != null){
            mTitleEditCell.setText(mData.getData().getTitle());
            mTitleEditCell.mContentEt.setSelection(mData.getData().getTitle().length());
            String start = mData.getData().getStartTime();
            String end = mData.getData().getEndTime();
            String startTime = "";
            String endTime = "";
            if (!StringUtils.isNullOrEmpty(start) && start.length() >= 16) {
                if (start.substring(11, 16).equals("00:00")) {
                    startTime = start.substring(0, 10);
                } else {
                    startTime = start.substring(0, 16);
                }
            }
            if (!StringUtils.isNullOrEmpty(end) && end.length() >= 16) {
                if (end.substring(11, 16).equals("00:00")) {
                    endTime = end.substring(0, 10);
                } else {
                    endTime = end.substring(0, 16);
                }
            }
            mStartTime = startTime;
            mEndTime = endTime;
            if (!StringUtils.isNullOrEmpty(start)){
                mDatePicker.setContent(startTime + "~" + endTime);
            }
            mContentEditText.setText(mData.getData().getContent());

            List<User> userBeans = new ArrayList<>();
            List<DiscussChatBean.DataBean.QuestionUsersBean> questionUsersBeans = mData.getData().getQuestionUsers();
            if (questionUsersBeans != null && questionUsersBeans.size() > 0){
                for (int i = 0;i < questionUsersBeans.size();i++){
                    User bean = new User();
                    DiscussChatBean.DataBean.QuestionUsersBean questionUsersBean = questionUsersBeans.get(i);
                    bean.setName(questionUsersBean.getUserName());
                    bean.setAvatar(questionUsersBean.getUserAvatar());
                    bean.setId(questionUsersBean.getUserId());
                    userBeans.add(bean);
                }
                userBeanList.addAll(userBeans);
                mAdapter.notifyDataSetChanged();
            }

            String imageJson = mData.getData().getRelativePhotos();
            List<String> images = new ArrayList<>();
            try {
                Gson gson = new Gson();
                List<String> temp = gson.fromJson(imageJson, new TypeToken<List<String>>() {
                }.getType());
                images.addAll(temp);
            } catch (Exception e) {
            }
            if (images.size() > 0) {
                List<ApplyDetailBean.Invoice> imageList1 = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    ApplyDetailBean.Invoice item = new ApplyDetailBean.Invoice();
                    item.setInvoiceImage(images.get(i));
                    imageList1.add(item);
                }
                imageList.addAll(imageList1);
            }

        }

        imageList.add(new ApplyDetailBean.Invoice());
        imagePickerChange();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.view_datepicker:
                hideKeyboard();
                timePicker();
                break;
            case R.id.activity_discussadd_user:
                userPicker();
                break;
            case R.id.activity_discussadd_commit:
                onCommit();
                break;
        }
    }

    /*
     * 选择日期
     * */
    private void timePicker() {
        mCalendarRangeView = new CalendarRangeView(mContext);
        mCalendarRangeView.setCalendarRangeViewActionListener(this);
        if (!StringUtils.isNullOrEmpty(mStartTime)){
            mCalendarRangeView.setSelectRange(mStartTime, mEndTime);
        }
        mPopupWindow = new PopupWindow(mCalendarRangeView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBody, Gravity.TOP | Gravity.LEFT, 0, 0);
    }


    /*
     * 选择用户
     * */
    private void userPicker() {
        List<String> ids = new ArrayList<>();
        List<String> disableIds = new ArrayList<>();
        if (userBeanList.size() > 0) {
            for (int i = 0; i < userBeanList.size(); i++) {
                if (!mUserId.equals(userBeanList.get(i).getId())) {
                    ids.add(userBeanList.get(i).getId());
                }
            }
        }
        disableIds.add(mUserId);
        Gson gson = new Gson();
        Intent intent = new Intent(mContext, MultipleUserSelectActivity.class);
        intent.putExtra("disableIds", gson.toJson(disableIds));
        intent.putExtra("selectIds", gson.toJson(ids));
        startActivityForResult(intent, 0);
    }

    @Override
    public void onCancel() {
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }

    @Override
    public void onConfirm() {
        Calendar startCalendar = mCalendarRangeView.getStartCalendar();
        Calendar endCalendar = mCalendarRangeView.getEndCalendar();
        String startHour = mCalendarRangeView.getStartHour();
        String endHour = mCalendarRangeView.getEndHour();
        if (startCalendar == null) {
            //showToast("请先选择开始时间");
            mPopupWindow.dismiss();
            mPopupWindow = null;
            return;
        }
        String startMonthS = Utils.int2String(startCalendar.getMonth());
        String startDayS = Utils.int2String(startCalendar.getDay());
        String startTime = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS + " ";
        String endDate = "";
        if (endCalendar == null) {
            endDate = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS + " ";
        } else {
            String endMonthS = Utils.int2String(endCalendar.getMonth());
            String endDayS = Utils.int2String(endCalendar.getDay());
            endDate = endCalendar.getYear() + "-" + endMonthS + "-" + endDayS + " ";
        }
        String endTime = endDate;
        if (!StringUtils.isNullOrEmpty(startHour)) {
            if (StringUtils.isNullOrEmpty(endHour)) {
                showToast("请先填写结束时间");
                return;
            }
            if (startHour.length() == 1) {
                startHour = "0" + startHour;
            }

            if (endHour.length() == 1) {
                endHour = "0" + endHour;
            }

            String startMin = StringUtils.isNullOrEmpty(mCalendarRangeView.getStartMin()) ? "00" : mCalendarRangeView.getStartMin();
            if (startMin.length() == 1) {
                startMin = "0" + startMin;
            }

            String endMin = StringUtils.isNullOrEmpty(mCalendarRangeView.getEndMin()) ? "00" : mCalendarRangeView.getEndMin();
            if (endMin.length() == 1) {
                endMin = "0" + endMin;
            }
            startTime = startTime + startHour + ":" + startMin;
            endTime = endTime + endHour + ":" + endMin;
        }
        mStartTime = startTime;
        mEndTime = endTime;
        mDatePicker.setContent(startTime + "~" + endTime);
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 1000) {
            List<MediaEntity> result = Phoenix.result(data);
            List<Uri> pathList = new ArrayList<>();
            for (int i = 0;i < result.size();i++){
                MediaEntity mediaEntity = result.get(i);
                pathList.add(Utils.path2uri(mContext,mediaEntity.getFinalPath()));
            }
            if (pathList.size() > 0) {
                for (int i = 0; i < pathList.size(); i++) {
                    if (imageList.size() == 9) {
                        ApplyDetailBean.Invoice m = imageList.get(8);
                        m.setUri(pathList.get(i));
                    } else {
                        ApplyDetailBean.Invoice m = new ApplyDetailBean.Invoice();
                        m.setUri(pathList.get(i));
                        imageList.add(imageList.size() - 1, m);
                    }
                }
                imagePickerChange();
            }
        } else if (resultCode == Const.MULTIPLE_USER_SELECT_TARGRT) {
            List<User> list = (List<User>) data.getSerializableExtra("list");
            StringBuilder name = new StringBuilder();
            userBeanList.clear();
            userBeanList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    /*
     * 点击提交
     * */
    private void onCommit() {
        String title = mTitleEditCell.getText();
//        if (StringUtils.isNullOrEmpty(title)) {
//            showToast("请先输入标题");
//            return;
//        }

        if (StringUtils.isNullOrEmpty(title)){
            title = "群聊";
        }
        String content = mContentEditText.getText().toString().trim();
        Map data = new HashMap();
        data.put("title",title);
        data.put("remindTime",-1);
        data.put("creatorId",SpHelper.getInstance(mContext).getUserId());
        data.put("smsNotifyFlag",1);
        data.put("mailNotifyFlag",0);
        String name = SpHelper.getInstance(mContext).getString("name");
        if (!StringUtils.isNullOrEmpty(content)){
            data.put("content",content);
        }
        if (!StringUtils.isNullOrEmpty(name)){
            data.put("creatorName",name);
        }
        if (!StringUtils.isNullOrEmpty(mStartTime)){
            data.put("startTime",mStartTime);
            data.put("endTime",mEndTime);
            if (mStartTime.length() > 10){
                data.put("isAllDay",0);
            }else {
                data.put("isAllDay",1);
            }
        }else {
            data.put("isAllDay",1);
        }

        List<Map> questionUsers = new ArrayList<>();
        for (int i = 0;i < userBeanList.size();i++){
            User bean = userBeanList.get(i);
            if (!bean.getId().equals(SpHelper.getInstance(mContext).getUserId())){
                Map user = new HashMap();
                user.put("userId",bean.getId());
                user.put("userPhone",bean.getPhone());
                user.put("userName",bean.getName());
                questionUsers.add(user);
            }
        }
        data.put("questionUsers",questionUsers);

        showLoading();
        imageUrlList.clear();
        List<String> uploadList = new ArrayList<>();
//        for (int i = 0;i < imageList.size();i++){
//            ApplyDetailBean.Invoice item = imageList.get(i);
//            if (!StringUtils.isNullOrEmpty(item.getInvoiceImage())){
//                imageUrlList.add(item.getInvoiceImage());
//            }else if (item.getUri() != null){
//                RequestBody body = Utils.uri2requestBody(mContext,item.getUri());
//                presenter.upLoadImage(body);
//            }
//        }


        int uploadLength = 0;
        ApplyDetailBean.Invoice last = imageList.get(imageList.size() -1);
        if (last.getUri() == null && StringUtils.isNullOrEmpty(last.getInvoiceImage())){
            uploadLength = imageList.size() - 1;
        }else {
            uploadLength = imageList.size();
        }
        mUploadLength = uploadLength;
        mUploadData = data;

//        if (imageList.size() == 1 || uploadLength == imageUrlList.size()){
//            Gson gson = new Gson();
//            data.put("relativePhotos",gson.toJson(imageUrlList));
////            if (mData != null){
////                data.put("id",mData.getId());
////            }
//            //presenter.addApply(requestBody);
//            if (mData == null){
//                String bodyString = gson.toJson(data);
//                Log.e("net666",bodyString);
//                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
//                presenter.addDiscuss(requestBody);
//            }else {
//                data.put("id",mData.getData().getId());
//                String bodyString = gson.toJson(data);
//                Log.e("net666",bodyString);
//                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
//                presenter.editDiscuss(requestBody);
//            }
//        }else {
//            // Log.e("net666","bodyString" + mUploadLength + uploadLength);
//            mUploadData = data;
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < imageList.size(); i++) {
                    ApplyDetailBean.Invoice item = imageList.get(i);
                    if (!StringUtils.isNullOrEmpty(item.getInvoiceImage())) {
                        imageUrlList.add(item.getInvoiceImage());
                    } else if (item.getUri() != null) {
                        //RequestBody body = Utils.uri2requestBody(mContext, item.getUri());
                        //presenter.upLoadImage(body);
                        String imagePath = Utils.uri2File(mContext, item.getUri()).getPath();
                        String imageUrl = OSSUploadHelper.uploadImage(imagePath);
                        if (imageUrl != null) {
                            imageUrlList.add(imageUrl);
                            if (imageUrlList.size() == mUploadLength) {
                                Message msg = new Message();
                                msg.what = 10086;
                                mhandler.sendMessage(msg);
                            }
                        } else {
                            Message msg = new Message();
                            msg.what = 10087;
                            msg.arg1 = i;
                            mhandler.sendMessage(msg);
                            return;
                        }
                    }
                }
                if (mUploadLength == imageUrlList.size()) {
                    Message msg = new Message();
                    msg.what = 10086;
                    mhandler.sendMessage(msg);
                }
            }
        }).start();




    }

    /*
    * 删除图片
    * */
    @Override
    public void deleIconDidClick(ApplyDetailBean.Invoice item) {
        if (imageList.size() == 1) {
            ApplyDetailBean.Invoice newItem = new ApplyDetailBean.Invoice();
            imageList.add(newItem);
            return;
        } else if (imageList.size() == 9) {
            ApplyDetailBean.Invoice m = imageList.get(8);
            if (m.getUri() == null && m.getInvoiceImage() == null) {
            } else {
                ApplyDetailBean.Invoice newItem = new ApplyDetailBean.Invoice();
                imageList.add(newItem);
            }
        }
        imageList.remove(item);
        imagePickerChange();
    }

    /*
    * 添加图片
    * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ApplyDetailBean.Invoice item = imageList.get(position);
        if (item.getUri() == null && StringUtils.isNullOrEmpty(item.getInvoiceImage())) {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //showToast("没有相册权限");
                ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return;
            }else {
                //showToast("有相册权限");
            }

            int maxCount = 9 - imageList.size() + 1;
//            Matisse.from(this).
//                    choose(MimeType.ofImage()).
//                    showSingleMediaType(true).
//                    capture(false).
//                    countable(true).
//                    maxSelectable(maxCount).
//                    restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).
//                    thumbnailScale(0.8f).
//                    theme(R.style.Matisse_Dracula).
//                    imageEngine(new WdGlideEngine()).
//                    forResult(1000);

            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
                    .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                    .maxPickNumber(maxCount)// 最大选择数量
                    .minPickNumber(0)// 最小选择数量
                    .spanCount(4)// 每行显示个数
                    .enablePreview(true)// 是否开启预览
                    .enableCamera(true)// 是否开启拍照
                    .enableAnimation(true)// 选择界面图片点击效果
                    .enableCompress(true)// 是否开启压缩
                    .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                    .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                    .thumbnailHeight(160)// 选择界面图片高度
                    .thumbnailWidth(160)// 选择界面图片宽度
                    .enableClickSound(false)// 是否开启点击声音
                    .videoFilterTime(0)//显示多少秒以内的视频
                    .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                    //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                    .start(DiscussChatAddActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 1000);
        }
    }



    public void imagePickerChange() {
        int rowCount = ((imageList.size() - 1) / 3) + 1;
        mImageRecyclerView.getLayoutParams().height = rowCount * getScreenWidth() / 3;
        mImageListAdapter.notifyDataSetChanged();
    }


    /*
    * 上传图片
    * */
    @Override
    public void uploadImageSuccess(UploadImageBean bean) {
        imageUrlList.add("http:" + bean.getList().get(0));
        if (imageUrlList.size() == mUploadLength){
            Gson gson = new Gson();
            mUploadData.put("relativePhotos",gson.toJson(imageUrlList));
            if (mData == null){
                String bodyString = gson.toJson(mUploadData);
                Log.e("net666",bodyString);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
                presenter.addDiscuss(requestBody);
            }else {
                mUploadData.put("id",mData.getData().getId());
                String bodyString = gson.toJson(mUploadData);
                Log.e("net666",bodyString);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
                presenter.editDiscuss(requestBody);
            }
        }
    }
    @Override
    public void uploadImageError() {
        hideLoading();
        showToast("上传图片失败,请稍后重试");
    }

    @Override
    public void uploadImageCompleted() {
    }




    /*
    * 添加会务
    * */
    @Override
    public void addDiscussSuccess() {
        hideLoading();
        showToast("添加成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }
    @Override
    public void addDiscussError() {
        hideLoading();
        showToast("添加失败,请稍后重试");
    }
    @Override
    public void addDiscussComplete() {
    }


    /*
    * 编辑会务
    * */
    @Override
    public void editDiscussSuccess() {
        hideLoading();
        showToast("编辑成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void editDiscussError() {
        hideLoading();
        showToast("编辑失败,请稍后重试");
    }

    @Override
    public void editDiscussComplete() {

    }


}
