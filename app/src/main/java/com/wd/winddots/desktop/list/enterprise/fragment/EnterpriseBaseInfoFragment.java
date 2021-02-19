package com.wd.winddots.desktop.list.enterprise.fragment;

import android.widget.TextView;

import com.wd.winddots.R;
import com.wd.winddots.base.BaseFragment;
import com.wd.winddots.base.BasePresenter;
import com.wd.winddots.desktop.list.enterprise.bean.EnterpriseDetailBean;
import com.wd.winddots.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName: EnterpriseBaseInfoFragment
 * Author: 郑
 * Date: 2020/12/23 9:53 AM
 * Description: 企业详情-基本信息
 */
public class EnterpriseBaseInfoFragment extends BaseFragment {


    /// 纳税人识别号
    private TextView taxNumberTextView;
    /// 组织机构代码证
    private TextView orgNumberTextView;
    /// 工商注册号
    private TextView regNumberTextView;
    /// 统一社会信用代码
    private TextView creditCodeTextView;
    /// 企业类型
    private TextView companyOrgTypeTextView;
    /// 行业
    private TextView industryTextView;
    /// 经营范围
    private TextView businessScopeTextView;
    /// 营业结束时间
    private TextView toTimeTextView;
    /// 人员规模
    private TextView staffNumRangeTextView;
    /// 参保人数
    private TextView socialStaffNumTextView;
    /// 注册地址
    private TextView regLocationTextView;





    private EnterpriseDetailBean enterpriseDetailBean;


    public void setData(EnterpriseDetailBean enterpriseDetailBean) {

        taxNumberTextView.setText("纳税人识别号: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getTaxNumber()));
        orgNumberTextView.setText("组织机构代码: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getOrgNumber()));
        regNumberTextView.setText("工商注册号: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getRegNumber()));
        creditCodeTextView.setText("统一社会信用代码: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getCreditCode()));
        companyOrgTypeTextView.setText("企业类型: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getCompanyOrgType()));
        industryTextView.setText("行业: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getIndustry()));

        businessScopeTextView.setText("经营范围: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getBusinessScope()));
        Date d = new Date(enterpriseDetailBean.getTycCompany().getToTime());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(d);

        toTimeTextView.setText("组营业期限: " + date);
        staffNumRangeTextView.setText("人员规模: " + Utils.numberNullOrEmpty(enterpriseDetailBean.getTycCompany().getStaffNumRange()));
        socialStaffNumTextView.setText("参保人数: " + enterpriseDetailBean.getTycCompany().getSocialStaffNum());
        regLocationTextView.setText("注册地址: " + Utils.nullOrEmpty(enterpriseDetailBean.getTycCompany().getRegLocation()));
        //industryTextView.setText("行业: " + enterpriseDetailBean.getTycCompany().getIndustry());


    }

    public static BaseFragment newInstance() {
        EnterpriseBaseInfoFragment fragment = new EnterpriseBaseInfoFragment();
        return fragment;
    }


    @Override
    public BasePresenter initPresenter() {
        return new  BasePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_enterprise_base_info;
    }

    @Override
    public void initView() {

        taxNumberTextView = mView.findViewById(R.id.tv_taxpayer_code);
        orgNumberTextView = mView.findViewById(R.id.tv_organization_code);
        regNumberTextView = mView.findViewById(R.id.tv_register_code);
        creditCodeTextView = mView.findViewById(R.id.tv_credit);

        companyOrgTypeTextView = mView.findViewById(R.id.tv_enterprose_type);
        industryTextView = mView.findViewById(R.id.tv_industry);
        toTimeTextView = mView.findViewById(R.id.tv_deadline);
        staffNumRangeTextView = mView.findViewById(R.id.tv_numbers);
        socialStaffNumTextView = mView.findViewById(R.id.tv_insurance_numbers);

        regLocationTextView = mView.findViewById(R.id.tv_address);


        businessScopeTextView = mView.findViewById(R.id.tv_businessScope);


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
