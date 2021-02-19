package com.wd.winddots.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wd.winddots.R;
import com.wd.winddots.bean.resp.DiscussChatBean;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.TitleTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName: DiscussChatDetailFooter
 * Author: 郑
 * Date: 2020/6/15 3:16 PM
 * Description:
 */
public class DiscussChatDetailFooter extends LinearLayout {

    private TitleTextView mTitleView;
    private TitleTextView mContentView;
    private TitleTextView mstartView;
    private TitleTextView mEndView;
    private TitleTextView mConfirmView;
    private RecyclerView mRecyclerView;
    private TextView mPhotoTextView;


    public DiscussChatDetailFooter(Context context) {
        super(context);
        initView();
    }

    public DiscussChatDetailFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DiscussChatDetailFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_discusschat_footer, this, false);
        mTitleView = view.findViewById(R.id.view_title);
        mContentView = view.findViewById(R.id.view_content);
        mstartView = view.findViewById(R.id.view_start);
        mEndView = view.findViewById(R.id.view_end);
        mConfirmView = view.findViewById(R.id.view_confirmMenber);
        mRecyclerView = view.findViewById(R.id.rlist);
        mPhotoTextView  = view.findViewById(R.id.tv_photoText);

        mTitleView.setTitle(getContext().getString(R.string.title));
        mContentView.setTitle(getContext().getString(R.string.discuss_content));
        mstartView.setTitle(getContext().getString(R.string.discuss_start));
        mEndView.setTitle(getContext().getString(R.string.discuss_end));
        mConfirmView.setTitle(getContext().getString(R.string.discuss_confirm));

        mTitleView.setRightIconVisibility(View.GONE);
        mContentView.setRightIconVisibility(View.GONE);
        mstartView.setRightIconVisibility(View.GONE);
        mEndView.setRightIconVisibility(View.GONE);
        mConfirmView.setRightIconVisibility(View.GONE);

        this.addView(view);
    }

    public void setData(DiscussChatBean bean) {
        mTitleView.setContent(bean.getData().getTitle());
        mContentView.setContent(bean.getData().getContent());

        String startTime = "";
        String endTime = "";
        if (bean.getData().getStartTime().length() >= 16) {
            if (bean.getData().getStartTime().substring(11, 16).equals("00:00")) {
                startTime = bean.getData().getStartTime().substring(0, 10);
            } else {
                startTime = bean.getData().getStartTime().substring(0, 16);
            }
        }
        if (bean.getData().getEndTime().length() >= 16) {
            if (bean.getData().getEndTime().substring(11, 16).equals("00:00")) {
                endTime = bean.getData().getEndTime().substring(0, 10);
            } else {
                endTime = bean.getData().getEndTime().substring(0, 16);
            }
        }
        mstartView.setContent(startTime);
        mEndView.setContent(endTime);
        mConfirmView.setContent(bean.getData().getReaderCount() + " / " + bean.getData().getQuestionUsers().size());

        String imageJson = bean.getData().getRelativePhotos();
        List<String> images = new ArrayList<>();
        try {
            Gson gson = new Gson();
            List<String> temp = gson.fromJson(imageJson, new TypeToken<List<String>>() {
            }.getType());
            images.addAll(temp);
        } catch (Exception e) {
        }
        if (images.size() > 0) {
            mPhotoTextView.setVisibility(View.VISIBLE);
            List<ApplyDetailBean.Invoice> imageList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                ApplyDetailBean.Invoice item = new ApplyDetailBean.Invoice();
                item.setInvoiceImage(images.get(i));
                imageList.add(item);
            }
            int mItemS = Utils.getScreenWidth(getContext()) / 3;
            MineClaimingImagePickerAdpater adpater1 = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, imageList, mItemS);
            adpater1.setDeleteIconVisibility(View.GONE);
            mRecyclerView.setAdapter(adpater1);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
            mRecyclerView.setLayoutManager(manager);
            int rowCount = ((imageList.size() - 1) / 3) + 1;
            mRecyclerView.getLayoutParams().height = rowCount * mItemS;
        }else {
            mPhotoTextView.setVisibility(View.GONE);
        }

    }

}

