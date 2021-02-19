package com.wd.winddots.fast.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.haibin.calendarview.Calendar;
import com.wd.winddots.R;
import com.wd.winddots.base.CommonActivity;
import com.wd.winddots.components.UploadImageBean;
import com.wd.winddots.components.calendar.range.CalendarRangeView;
import com.wd.winddots.components.users.MultipleUserSelectActivity;
import com.wd.winddots.components.users.SingleUserSelectActivity;
import com.wd.winddots.components.users.UserBean;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.entity.User;
import com.wd.winddots.fast.adapter.MineAttendanceAddTimeAdapter;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.fast.bean.MineAttendanceListBean;
import com.wd.winddots.fast.presenter.impl.MineAttendancePresenterImpl;
import com.wd.winddots.fast.presenter.view.MineAttendanceView;
import com.wd.winddots.utils.OSSUploadHelper;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.dialog.ConfirmDialog;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * FileName: MineAttendanceAddActivity
 * Author: 郑
 * Date: 2020/5/27 2:55 PM
 * Description:
 */
public class MineAttendanceAddActivity extends CommonActivity<MineAttendanceView, MineAttendancePresenterImpl>
        implements MineAttendanceView,
        BaseQuickAdapter.OnItemClickListener,
        MineClaimingImagePickerAdpater.MineClaimingImagePickerAdpaterDeleteListener,
        View.OnClickListener, MineAttendanceAddTimeAdapter.MineAttendanceAddTimeAdapterActionListener,
        CalendarRangeView.CalendarRangeViewActionListener {


    final static int REQUEST_CODE_PHOTO = 1;


    /*
     * view
     * */
    private RecyclerView mTimeRecyclerView;
    private MineAttendanceAddTimeAdapter mTimeAdapter;
    private RecyclerView mImageRecyclerView;
    private MineClaimingImagePickerAdpater mImageListAdapter;
    private Selector mSelector;
    private TextView mCopyText;
    private TextView mRviewer;
    private PopupWindow mPopupWindow;
    private LinearLayout mBody;
    private CalendarRangeView mCalendarRangeView;
    private EditText mCountEditText;
    private EditText mCauseEditText;
    private BaseViewHolder selectHolder;


    private String mType;
    private String mTypeS;
    private User mReviewerUser; //审核人
    private List<User> mCopyList = new ArrayList<>();//抄送人

    private List<String> imageUrlList = new ArrayList<>();
    private int mUploadLength = 0;
    private Map mUploadData;
    private ApplyDetailBean mData;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10086) {
                Gson gson = new Gson();
                mUploadData.put("voucher", gson.toJson(imageUrlList));
                if (mData != null) {
                    mUploadData.put("id", mData.getId());
                }
                String bodyString = gson.toJson(mUploadData);
                Log.e("net666", bodyString);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
                if (mData == null) {
                    presenter.addApply(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
                } else {
                    presenter.updateApply(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
                }
            }  else if (msg.what == 10087) {
                hideLoading();
                showToast("上传图片失败,请稍后重试");
                return;
            }
        }
    };


    /*
     * 相册选择的最大图片数量
     * */
    private int mMaxCount = 0;

    private List<MineAttendanceListBean.MineAttendanceDetailBean> timeList = new ArrayList<>();
    private List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();


    @Override
    public MineAttendancePresenterImpl initPresenter() {
        return new MineAttendancePresenterImpl();
    }

    @Override
    public void initView() {
        super.initView();
        addBadyView(R.layout.activity_mine_attendance_add);

        mBody = findViewById(R.id.activity_mine_attendance_add_body);
        TextView titleTextView = findViewById(R.id.activity_mine_attendance_add_title);
        mCountEditText = findViewById(R.id.activity_mine_attendance_add_countEdit);
        mSelector = findViewById(R.id.activity_mine_attendance_add_selector);
        mTimeRecyclerView = findViewById(R.id.activity_mine_attendance_add_timelist);
        mImageRecyclerView = findViewById(R.id.activity_mine_attendance_add_imagelist);
        mCopyText = findViewById(R.id.activity_mine_attendance_add_copy);
        mRviewer = findViewById(R.id.activity_mine_attendance_add_reviewer);
        mCauseEditText = findViewById(R.id.activity_mine_attendance_add_cause);


        mSelector.setTitleText("请假类型");
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String typeS = "";
        switch (type) {
            case "0":
                typeS = "请假";
                titleTextView.setText("请假天数");
                mCountEditText.setHint("输入请假天数");
                mCauseEditText.setHint("请输入请假事由");
                mSelector.setVisibility(View.VISIBLE);
                break;
            case "1":
                typeS = "加班";
                titleTextView.setText("加班时长");
                mCountEditText.setHint("输入加班时长(小时)");
                mCauseEditText.setHint("请输入加班事由");
                mSelector.setVisibility(View.GONE);
                break;
            case "11":
                typeS = "公出";
                titleTextView.setText("公出天数");
                mCountEditText.setHint("输入公出天数");
                mCauseEditText.setHint("请输入公出事由");
                mSelector.setVisibility(View.GONE);
                break;

        }
        mTypeS = typeS;
        mType = type;
        setTitleText(typeS);
        String data = intent.getStringExtra("data");
        if (!StringUtils.isNullOrEmpty(data)) {
            Gson gson = new Gson();
            ApplyDetailBean bean = gson.fromJson(data, ApplyDetailBean.class);
            mData = bean;
        }

        mTimeAdapter = new MineAttendanceAddTimeAdapter(R.layout.item_mine_attendance_addtime, timeList, typeS);
        mTimeAdapter.setMineAttendanceAddTimeAdapterActionListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTimeRecyclerView.setLayoutManager(layoutManager);
        mTimeRecyclerView.setAdapter(mTimeAdapter);

        int itemS = getScreenWidth() / 3;
        mImageListAdapter = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, imageList, itemS);
        mImageListAdapter.setOnItemClickListener(this);
        mImageListAdapter.setMineClaimingImagePickerAdpaterDeleteListener(this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mImageRecyclerView.setLayoutManager(manager);
        mImageRecyclerView.setAdapter(mImageListAdapter);
        int rowCount = ((imageList.size() - 1) / 3) + 1;
        mImageRecyclerView.getLayoutParams().height = rowCount * itemS;

        LinearLayout addBtn = findViewById(R.id.activity_mine_attendance_add_icon);
        LinearLayout reviewerCell = findViewById(R.id.activity_mine_attendance_add_reviewercell);
        LinearLayout copyCell = findViewById(R.id.activity_mine_attendance_add_copycell);
        LinearLayout saveBtn = findViewById(R.id.activity_mine_attendance_add_save);
        addBtn.setOnClickListener(this);
        reviewerCell.setOnClickListener(this);
        copyCell.setOnClickListener(this);
        saveBtn.setOnClickListener(this);


    }

    @Override
    public void initData() {
        super.initData();
        if (mData != null) {
            List<ApplyDetailBean.ClaimingModel> detailList = mData.getDetailList();
            if (detailList == null) {
                detailList = new ArrayList<>();
            }
            for (int i = 0; i < detailList.size(); i++) {
                ApplyDetailBean.ClaimingModel model = detailList.get(i);
                MineAttendanceListBean.MineAttendanceDetailBean bean = new MineAttendanceListBean.MineAttendanceDetailBean();
                bean.setStartTime(model.getStartTime());
                bean.setEndTime(model.getEndTime());
                timeList.add(bean);
            }

            mCauseEditText.setText(mData.getCause());
            mCountEditText.setText(mData.getLeaveDays());

            ApplyDetailBean.User user = mData.getUsers().get(mData.getUsers().size() - 1);
            User reviewer = new User();
            reviewer.setName(user.getUserName());
            reviewer.setId(user.getUserId());
            mReviewerUser = reviewer;
            mRviewer.setText(user.getUserName());

            List<ApplyDetailBean.User> copyUsers = mData.getCopyUsers();
            List<User> list = new ArrayList<>();
            if (copyUsers == null) {
                copyUsers = new ArrayList<>();
            }
            String copyNames = "";
            for (int i = 0; i < copyUsers.size(); i++) {
                User userBean = new User();
                ApplyDetailBean.User user1 = copyUsers.get(i);
                copyNames = copyNames + " " + user1.getUserName();
                userBean.setId(user1.getUserId());
                userBean.setName(user1.getUserName());
                list.add(userBean);
            }
            mCopyText.setText(copyNames);
            mCopyList = list;

            String imageJson = mData.getVoucher();
            List<String> images = new ArrayList<>();
            try {
                Gson gson = new Gson();
                List<String> temp = gson.fromJson(imageJson, new TypeToken<List<String>>() {
                }.getType());
                images.addAll(temp);
            } catch (Exception e) {
            }


            if (images.size() > 0) {
                //List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    ApplyDetailBean.Invoice item = new ApplyDetailBean.Invoice();
                    item.setInvoiceImage(images.get(i));
                    imageList.add(item);
                }
                int mItemS = getScreenWidth() / 3;
            }


        } else {
            timeList.add(new MineAttendanceListBean.MineAttendanceDetailBean());
        }
        imageList.add(new ApplyDetailBean.Invoice());
        imagePickerChange();
        mTimeAdapter.notifyDataSetChanged();
        presenter.getLeaveTypeList(SpHelper.getInstance(mContext).getEnterpriseId());
    }

    /*
     * 获取请假类型
     * */
    @Override
    public void getLeaveTypeListSuccess(List<SelectBean> list) {
        mSelector.setSelectList(list);
        if (mData != null) {
            for (int i = 0; i < list.size(); i++) {
                SelectBean bean1 = list.get(i);
                if (bean1.getName().equals(mData.getLeaveType())) {
                    mSelector.setDefaultPosition(i);
                    break;
                }
            }
        }
    }

    @Override
    public void getLeaveTypeListError() {
    }

    @Override
    public void getLeaveTypeListCompleted() {
    }


    /*
     * 点击图片
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        ApplyDetailBean.Invoice item = imageList.get(position);
        if (item.getUri() == null && StringUtils.isNullOrEmpty(item.getInvoiceImage())) {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PHOTO);
                return;
            } else {
                openPhotoLibrary();
            }
        }
    }

    private void openPhotoLibrary() {
        int maxCount = 9 - imageList.size() + 1;
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
                .start(MineAttendanceAddActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 1000);
    }


    public void imagePickerChange() {
        int rowCount = ((imageList.size() - 1) / 3) + 1;
        mImageRecyclerView.getLayoutParams().height = rowCount * getScreenWidth() / 3;
        mImageListAdapter.notifyDataSetChanged();
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
            for (int i = 0; i < result.size(); i++) {
                MediaEntity mediaEntity = result.get(i);
                pathList.add(Utils.path2uri(mContext, mediaEntity.getFinalPath()));
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
            for (int i = 0; i < list.size(); i++) {
                User item = list.get(i);
                name.append(" ").append(item.getName());
            }
            mCopyText.setText(name.toString());
            mCopyList.clear();
            mCopyList.addAll(list);
        } else if (resultCode == Const.SINGLE_USER_SELECT_TARGRT) {
            User user = (User) data.getSerializableExtra("user");
            mRviewer.setText(user.getName());
            mReviewerUser = user;
        }
    }


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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_mine_attendance_add_icon:
                timeList.add(new MineAttendanceListBean.MineAttendanceDetailBean());
                mTimeAdapter.notifyDataSetChanged();
                break;
            case R.id.activity_mine_attendance_add_copycell:
                copyUserSelect();
                break;
            case R.id.activity_mine_attendance_add_reviewercell:
                reviewerUserSelect();
                break;

            case R.id.activity_mine_attendance_add_save:
                onSave();
                break;
        }

    }


    private void copyUserSelect() {
        List<String> ids = new ArrayList<>();
        List<String> disableIds = new ArrayList<>();
        if (mCopyList.size() > 0) {
            for (int i = 0; i < mCopyList.size(); i++) {
                ids.add(mCopyList.get(i).getId());
            }
        }
        disableIds.add(SpHelper.getInstance(mContext).getUserId());
        if (mReviewerUser != null) {
            disableIds.add(mReviewerUser.getId());
        }
        Gson gson = new Gson();
        Intent intent = new Intent(mContext, MultipleUserSelectActivity.class);
        intent.putExtra("disableIds", gson.toJson(disableIds));
        intent.putExtra("selectIds", gson.toJson(ids));
        startActivityForResult(intent, 0);
    }

    private void reviewerUserSelect() {
        List<String> disableIds = new ArrayList<>();
        if (mCopyList.size() > 0) {
            for (int i = 0; i < mCopyList.size(); i++) {
                disableIds.add(mCopyList.get(i).getId());
            }
        }
        disableIds.add(SpHelper.getInstance(mContext).getUserId());
        Gson gson = new Gson();
        Intent intent = new Intent(mContext, SingleUserSelectActivity.class);
        intent.putExtra("disableIds", gson.toJson(disableIds));
        startActivityForResult(intent, 0);
    }

    /*
     * 删除时间
     * */
    @Override
    public void onDeleteIconDidClick(MineAttendanceListBean.MineAttendanceDetailBean item) {
        if (timeList.size() == 1) {
            return;
        }
        timeList.remove(item);
        mTimeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTimeDidClick(BaseViewHolder holder) {

        selectHolder = holder;
        MineAttendanceListBean.MineAttendanceDetailBean item = timeList.get(holder.getAdapterPosition());
        mCalendarRangeView = new CalendarRangeView(mContext);
        if (!StringUtils.isNullOrEmpty(item.getStartTime()) && !StringUtils.isNullOrEmpty(item.getEndTime())) {
            mCalendarRangeView.setSelectRange(item.getStartTime(), item.getEndTime());
        }
        mCalendarRangeView.setCalendarRangeViewActionListener(this);
        mPopupWindow = new PopupWindow(mCalendarRangeView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mBody, Gravity.TOP | Gravity.LEFT, 0, 0);
    }


    @Override
    public void onCancel() {
//        if (mPopupWindow == null){
//            return;
//        }
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
            showToast("请先选择开始时间");
            return;
        }
        if (StringUtils.isNullOrEmpty(startHour)) {
            showToast("请先填写开始时间");
            return;
        }
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

        String startMonthS = Utils.int2String(startCalendar.getMonth());
        String startDayS = Utils.int2String(startCalendar.getDay());
        String startMin = StringUtils.isNullOrEmpty(mCalendarRangeView.getStartMin()) ? "00" : mCalendarRangeView.getStartMin();
        if (startMin.length() == 1) {
            startMin = "0" + startMin;
        }
        String endMin = StringUtils.isNullOrEmpty(mCalendarRangeView.getEndMin()) ? "00" : mCalendarRangeView.getEndMin();
        if (endMin.length() == 1) {
            endMin = "0" + endMin;
        }

        String startTime = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS + " " + startHour + ":" + startMin;

        String endDate = "";
        if (endCalendar == null) {
            endDate = startCalendar.getYear() + "-" + startMonthS + "-" + startDayS + " ";
        } else {
            String endMonthS = Utils.int2String(endCalendar.getMonth());
            String endDayS = Utils.int2String(endCalendar.getDay());
            endDate = endCalendar.getYear() + "-" + endMonthS + "-" + endDayS + " ";
        }
        String endTime = endDate + endHour + ":" + endMin;
        MineAttendanceListBean.MineAttendanceDetailBean item = timeList.get(selectHolder.getAdapterPosition());
        item.setStartTime(startTime);
        item.setEndTime(endTime);
        selectHolder.setText(R.id.item_mine_attendance_addtime_content, startTime + "~" + endTime);
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }


    private void onSave() {
        Map data = new HashMap<>();
        data.put("type", mType);
        data.put("createdBy", SpHelper.getInstance(mContext).getUserId());
        data.put("applyType", "0");

        if (Const.APPROVA_LEAVE_0.equals(mType)) {
            data.put("leaveType", mSelector.getItem().getName());
        }
        String leaveDays = mCountEditText.getText().toString().trim();
        if (StringUtils.isNullOrEmpty(leaveDays)) {
            showToast("请先填写" + mTypeS + "时长");
            return;
        }
        data.put("leaveDays", leaveDays);

        String cause = mCauseEditText.getText().toString().trim();
        if (StringUtils.isNullOrEmpty(cause)) {
            showToast("请先填写" + mTypeS + "事由");
            return;
        }
        data.put("cause", cause);

        List<Map> users = new ArrayList<>();
        if (mReviewerUser == null) {
            showToast("请先选择审核人");
            return;
        }
        Map<String, String> reviewerMap = new HashMap<>();
        reviewerMap.put("userId", mReviewerUser.getId());
        reviewerMap.put("type", "0");
        users.add(reviewerMap);

        for (int i = 0; i < mCopyList.size(); i++) {
            User userBean = mCopyList.get(i);
            Map<String, String> map = new HashMap<>();
            map.put("userId", userBean.getId());
            map.put("type", "1");
            users.add(map);
        }
        data.put("users", users);

        List<Map> detailList = new ArrayList<>();
        for (int i = 0; i < timeList.size(); i++) {
            MineAttendanceListBean.MineAttendanceDetailBean item = timeList.get(i);
            if (StringUtils.isNullOrEmpty(item.getStartTime()) || StringUtils.isNullOrEmpty(item.getEndTime())) {
                showToast("请先完善" + mTypeS + "时间");
                return;
            }
            Map detail = new HashMap();
            detail.put("startTime", item.getStartTime());
            detail.put("endTime", item.getEndTime());
            detailList.add(detail);
        }
        data.put("detailList", detailList);

        showLoading();
        imageUrlList.clear();
        List<String> uploadList = new ArrayList<>();

        int uploadLength = 0;
        ApplyDetailBean.Invoice last = imageList.get(imageList.size() - 1);
        if (last.getUri() == null && StringUtils.isNullOrEmpty(last.getInvoiceImage())) {
            uploadLength = imageList.size() - 1;
        } else {
            uploadLength = imageList.size();
        }
        mUploadLength = uploadLength;
        mUploadData = data;

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


//        Gson gson = new Gson();
//        data.put("voucher", gson.toJson(imageUrlList));
//        if (mData != null) {
//            data.put("id", mData.getId());
//        }
//        String bodyString = gson.toJson(data);
//        Log.e("net666", bodyString);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
//        if (mData == null) {
//            presenter.addApply(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
//        } else {
//            presenter.updateApply(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
//        }


    }


    /*
     * 上传图片
     * */
    @Override
    public void uploadImageSuccess(UploadImageBean bean) {
        imageUrlList.add("http:" + bean.getList().get(0));
        if (imageUrlList.size() == mUploadLength) {
            Gson gson = new Gson();
            mUploadData.put("voucher", gson.toJson(imageUrlList));
            if (mData != null) {
                mUploadData.put("id", mData.getId());
            }
            String bodyString = gson.toJson(mUploadData);
            Log.e("net666", bodyString);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
            if (mData == null) {
                presenter.addApply(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
            } else {
                presenter.updateApply(requestBody, SpHelper.getInstance(mContext).getEnterpriseId());
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
     * 新增
     * */
    @Override
    public void addApplySuccess() {
        hideLoading();
        showToast("提交成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void addApplyError() {
        hideLoading();
        showToast("提交失败,请稍后重试");
    }

    @Override
    public void addApplyCompleted() {
    }

    /*
     * 编辑
     * */
    @Override
    public void updateApplySuccess() {
        hideLoading();
        showToast("提交成功");
        Intent intent = new Intent();
        intent.putExtra("data", "data");
        setResult(250, intent);
        finish();
    }

    @Override
    public void updateApplyError() {
        hideLoading();
        showToast("提交失败,请稍后重试");
    }

    @Override
    public void updateApplyCompleted() {

    }


    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
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
                    case REQUEST_CODE_PHOTO:
                        //startActivity(MeAttendanceActivity.class);
                        openPhotoLibrary();
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
                case REQUEST_CODE_PHOTO:
                    // 同意定位权限,进入地图选择器
                    //startActivity(MeAttendanceActivity.class);
                    openPhotoLibrary();
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(MineAttendanceAddActivity.this, permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    content = "在设置-应用-瓦丁-权限中开启相册访问权限，以正常使用上传发票等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(MineAttendanceAddActivity.this, "权限申请",
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
                }

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

}
