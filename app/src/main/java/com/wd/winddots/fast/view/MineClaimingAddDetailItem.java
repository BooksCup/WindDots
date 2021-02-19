package com.wd.winddots.fast.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wd.winddots.R;
import com.wd.winddots.confifg.Const;
import com.wd.winddots.fast.adapter.MineClaimingImagePickerAdpater;
import com.wd.winddots.fast.bean.ApplyDetailBean;
import com.wd.winddots.view.selector.SelectBean;
import com.wd.winddots.view.selector.SelectView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

/**
 * FileName: MineClaimingAddDetailItem
 * Author: 郑
 * Date: 2020/5/8 10:30 AM
 * Description: 添加报销详情 item
 */
public class MineClaimingAddDetailItem extends LinearLayout
        implements SelectView.SelectViewOnselectListener,
        BaseQuickAdapter.OnItemClickListener,
        MineClaimingImagePickerAdpater.MineClaimingImagePickerAdpaterDeleteListener {


    /*
     * view
     * */
    private EditText mProjectEditText;
    private EditText mAmountEditText;
    private TextView mAmountTextView;
    private TextView mDateTextView;
    private SelectView mInvoiceSelectView;
    private ImageView mDeleteIcon;
    private RecyclerView mRecyclerView;
    private TextView mInvoiceTextView;
    private MineClaimingImagePickerAdpater mAdapter;
    private LinearLayout mInvoiceDatePickerLinearLayout;
    private TextView mInvouceDateTextView;


    private MineClaimingAddDetailItemActionListener listener;


    /*
     * data
     * */
    private List<SelectBean> list = new ArrayList<>();
    private ApplyDetailBean.ClaimingDetail mData;
    private Context mContext;
    private int mItemS;


    public ApplyDetailBean.ClaimingDetail getItemData() {
        return mData;
    }

    public MineClaimingAddDetailItem(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }

    public MineClaimingAddDetailItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initData();
    }

    public MineClaimingAddDetailItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_claimingadd_detail, this, false);

        mInvoiceSelectView = view.findViewById(R.id.item_claimingadd_invoicetype);//helper.getView(R.id.item_claimingadd_invoicetype);

        mInvoiceSelectView.setSelectViewOnselectListener(this);


        mAmountTextView = view.findViewById(R.id.item_claimingadd_amountext); //helper.getView(R.id.item_claimingadd_amountext);
        mAmountEditText = view.findViewById(R.id.item_claimingadd_amountedit);// helper.getView(R.id.item_claimingadd_amountedit);
        mProjectEditText = view.findViewById(R.id.item_claimingadd_titleEdittext);//helper.getView(R.id.item_claimingadd_titleEdittext);
        mDeleteIcon = view.findViewById(R.id.item_claimingadd_delete);
        mInvoiceDatePickerLinearLayout = view.findViewById(R.id.item_claimingadd_invoiceDatepick);
        mInvouceDateTextView = view.findViewById(R.id.item_claimingadd_invoiceDatepicktext);

        mDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.deleIconDidClick(mData);
                }
            }
        });


        LinearLayout datePicker = view.findViewById(R.id.item_claimingadd_datepick);//helper.getView(R.id.item_claimingadd_datepick);
        mDateTextView = view.findViewById(R.id.item_claimingadd_datepicktext);//helper.getView(R.id.item_claimingadd_datepicktext);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (listener != null) {
//                    listener.datePickerItemDidClick(mDateTextView, mData);
//                }


                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                        long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
                        int year = datePicker.getYear();//年
                        int month = datePicker.getMonth() + 1;//月-1
                        int day = datePicker.getDayOfMonth();//日*
                        //iyear:年，monthOfYear:月-1，dayOfMonth:日

                        String monthS;
                        String dayS;
                        if (month < 10) {
                            monthS = "0" + month;
                        } else {
                            monthS = "" + month;
                        }

                        if (day < 10) {
                            dayS = "0" + day;
                        } else {
                            dayS = "" + day;
                        }
                        String date = year + "-" + monthS + '-' + dayS;
                        mDateTextView.setText(date);
                        mData.setTime(date);
                    }


                }, year, month, day);//2013:初始年份，2：初始月份-1 ，1：初始日期

                dp.show();


            }
        });


        mRecyclerView = view.findViewById(R.id.item_claimingadd_imagePicker);
        mInvoiceTextView = view.findViewById(R.id.item_claimingadd_invoicetext);


        mInvoiceDatePickerLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (listener != null) {
//                    listener.datePickerItemDidClick(mDateTextView, mData);
//                }


                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                        long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
                        int year = datePicker.getYear();//年
                        int month = datePicker.getMonth() + 1;//月-1
                        int day = datePicker.getDayOfMonth();//日*
                        //iyear:年，monthOfYear:月-1，dayOfMonth:日

                        String monthS;
                        String dayS;
                        if (month < 10) {
                            monthS = "0" + month;
                        } else {
                            monthS = "" + month;
                        }

                        if (day < 10) {
                            dayS = "0" + day;
                        } else {
                            dayS = "" + day;
                        }
                        String date = year + "-" + monthS + '-' + dayS;
                        mInvouceDateTextView.setText(date);
                        mData.setReachTime(date);
                    }
                }, year, month, day);//2013:初始年份，2：初始月份-1 ，1：初始日期
                dp.show();
            }
        });



        this.addView(view);
    }


    private void initData() {
        list.add(new SelectBean("不开票", "1"));
        list.add(new SelectBean("开票已到", "0"));
        list.add(new SelectBean("开票未到", "2"));
        mInvoiceSelectView.setSelectList(list);
    }


    public void setUpData(final ApplyDetailBean.ClaimingDetail item) {

        mData = item;

        String projectTitle = StringUtils.isNullOrEmpty(item.getProjectTitle()) ? "" : item.getProjectTitle();
        mProjectEditText.setText(projectTitle);
        mProjectEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setProjectTitle(mProjectEditText.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        String amount = StringUtils.isNullOrEmpty(item.getAmount()) ? "" : item.getAmount();
        mAmountEditText.setText(amount);
        mAmountTextView.setText(amount);
        mAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAmountTextView.setText(mAmountEditText.getText().toString().trim());
                item.setAmount(mAmountEditText.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String time = StringUtils.isNullOrEmpty(item.getTime()) ? "选择日期" : item.getTime();
        mDateTextView.setText(time);

        if (!StringUtils.isNullOrEmpty(item.getInvoice())) {
            switch (item.getInvoice()) {
                case "0": //开票已到
                    mInvoiceSelectView.setDefaultPosition(1);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mInvoiceTextView.setVisibility(View.VISIBLE);
                    mInvoiceDatePickerLinearLayout.setVisibility(View.GONE);
                    break;
                case "1": //不开票
                    mInvoiceSelectView.setDefaultPosition(2);
                    mRecyclerView.setVisibility(View.GONE);
                    mInvoiceTextView.setVisibility(View.GONE);
                    mInvoiceDatePickerLinearLayout.setVisibility(View.GONE);
                    break;
                case "2": //开票未到
                    mInvoiceSelectView.setDefaultPosition(0);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mInvoiceTextView.setVisibility(View.VISIBLE);
                    mInvoiceDatePickerLinearLayout.setVisibility(View.VISIBLE);
                    break;
            }
        }


        if (item.getInvoices() == null || item.getInvoices().size() == 0) {
            item.setInvoices(new ArrayList<ApplyDetailBean.Invoice>());
            ApplyDetailBean.Invoice m = new ApplyDetailBean.Invoice();
            item.getInvoices().add(m);
        }else {
            if (item.getInvoices().size() != 9){
                ApplyDetailBean.Invoice m = item.getInvoices().get(item.getInvoices().size()-1);
                if (m.getUri() != null || !StringUtils.isNullOrEmpty(m.getInvoiceImage())){
                    ApplyDetailBean.Invoice m1 = new ApplyDetailBean.Invoice();
                    item.getInvoices().add(m1);
                }
            }
        }



        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        //int heightPixel = outMetrics.heightPixels;
        mItemS = widthPixel/3;


       if (!StringUtils.isNullOrEmpty(item.getInvoice())){
           switch (item.getInvoice()){
               case Const.INVOICE_TYPE_NO_INVOICE:
                   mInvoiceSelectView.setDefaultPosition(0);
                   mRecyclerView.setVisibility(View.GONE);
                   mInvoiceTextView.setVisibility(View.GONE);
                   mInvoiceDatePickerLinearLayout.setVisibility(View.GONE);
                   break;
               case Const.INVOICE_TYPE_ALREADY:
                   mInvoiceSelectView.setDefaultPosition(1);
                   mRecyclerView.setVisibility(View.VISIBLE);
                   mInvoiceTextView.setVisibility(View.VISIBLE);
                   mInvoiceDatePickerLinearLayout.setVisibility(View.GONE);
                   break;
               case Const.INVOICE_TYPE_NO_ALREADY:
                   mInvoiceSelectView.setDefaultPosition(2);
                   mRecyclerView.setVisibility(View.VISIBLE);
                   mInvoiceTextView.setVisibility(View.VISIBLE);
                   mInvoiceDatePickerLinearLayout.setVisibility(View.VISIBLE);
                   mInvouceDateTextView.setText(item.getReachTime());
                   break;

           }
       }
        mAdapter = new MineClaimingImagePickerAdpater(R.layout.item_image_pcker, item.getInvoices(), mItemS);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setMineClaimingImagePickerAdpaterDeleteListener(this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        int rowCount = ((mData.getInvoices().size() - 1) / 3) + 1;
        mRecyclerView.getLayoutParams().height = rowCount * mItemS;

    }

    public void setClaimingAddDetailItemActionListener(MineClaimingAddDetailItemActionListener listener1) {
        listener = listener1;
    }

    /*
     * 选择器代理
     * */
    @Override
    public void onselect(int position, SelectView view) {
        mData.setInvoiceText(list.get(position).getName());
        mData.setInvoice(list.get(position).getValue());

        if (position == 0) {

            mRecyclerView.setVisibility(View.GONE);
            mInvoiceTextView.setVisibility(View.GONE);
            mInvoiceDatePickerLinearLayout.setVisibility(View.GONE);
        } else if (position == 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mInvoiceTextView.setVisibility(View.VISIBLE);
            mInvoiceDatePickerLinearLayout.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mInvoiceTextView.setVisibility(View.VISIBLE);
            mInvoiceDatePickerLinearLayout.setVisibility(View.VISIBLE);
        }

    }


    public interface MineClaimingAddDetailItemActionListener {
        /*
         * 点击删除 item
         * */
        void deleIconDidClick(ApplyDetailBean.ClaimingDetail item);

        void datePickerItemDidClick(TextView textView, ApplyDetailBean.ClaimingDetail item);

        void imagePickerDidClick(MineClaimingAddDetailItem item);
    }


    /*
     *点击图片 item
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        ClaimingDetailBean.Invoice m = new ClaimingDetailBean.Invoice();
//        mData.getInvoices().add(m);
//        mAdapter.notifyDataSetChanged();

        ApplyDetailBean.Invoice m = mData.getInvoices().get(position);

        if (m.getUri() == null) {
            if (listener != null) {
                listener.imagePickerDidClick(this);
            }
        }
    }


    /*
     * 删除选中的图片
     * */
    @Override
    public void deleIconDidClick(ApplyDetailBean.Invoice item) {


        if (mData.getInvoices().size() == 1) {
            ApplyDetailBean.Invoice newItem = new ApplyDetailBean.Invoice();
            mData.getInvoices().add(newItem);
            return;
        } else if (mData.getInvoices().size() == 9) {
            ApplyDetailBean.Invoice m = mData.getInvoices().get(8);
            if (m.getUri() == null && m.getInvoiceImage() == null){
            }else {
                ApplyDetailBean.Invoice newItem = new ApplyDetailBean.Invoice();
                mData.getInvoices().add(newItem);
            }
        }
        mData.getInvoices().remove(item);
        imagePickerChange();


    }


    public void imagePickerChange() {
        int rowCount = ((mData.getInvoices().size() - 1) / 3) + 1;
        mRecyclerView.getLayoutParams().height = rowCount * mItemS;
        mAdapter.notifyDataSetChanged();
    }







}
