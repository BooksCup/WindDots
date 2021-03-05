package com.wd.winddots.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.client.android.CaptureActivity;
import com.wd.winddots.R;
import com.wd.winddots.activity.check.fabric.FabricCheckProcessActivity;
import com.wd.winddots.activity.employee.EmployeeActivity;
import com.wd.winddots.activity.stock.in.StockInApplyActivity;
import com.wd.winddots.activity.work.DeliveryActivity;
import com.wd.winddots.adapter.work.AppAdapter;
import com.wd.winddots.cons.Constant;
import com.wd.winddots.desktop.list.card.activity.FriendListActivity;
import com.wd.winddots.desktop.list.contact.activity.ContactListActivity;
import com.wd.winddots.desktop.list.goods.activity.GoodsListActivity;
import com.wd.winddots.desktop.list.invoice.activity.InvoiceListActivity;
import com.wd.winddots.desktop.list.order.activity.OrderListActivity;
import com.wd.winddots.desktop.list.quote.activity.QuoteListActivity;
import com.wd.winddots.desktop.list.requirement.activity.RequirementActivity;
import com.wd.winddots.desktop.list.warehouse.activity.WarehouseListActivity;
import com.wd.winddots.entity.App;
import com.wd.winddots.entity.UserApp;
import com.wd.winddots.fast.activity.MeAttendanceActivity;
import com.wd.winddots.fast.activity.MineClaimingActivity;
import com.wd.winddots.utils.VolleyUtil;
import com.wd.winddots.view.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jmessage.support.qiniu.android.utils.StringUtils;

public class AppFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener, View.OnClickListener, BaseQuickAdapter.OnItemLongClickListener {

    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    private static final int REQUEST_CODE_SCAN = 3;

    @BindView(R.id.rv_app)
    RecyclerView mAppRv;

    @BindView(R.id.rv_fast_app)
    RecyclerView mFastAppRv;

    @BindView(R.id.srl_app)
    SwipeRefreshLayout mAppSrl;

    private AppAdapter mAppAdapter;
    private AppAdapter mFastAppAdapter;
    private VolleyUtil mVolleyUtil;
    private List<App> mAppList = new ArrayList<>();
    private List<App> mFastAppList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        mVolleyUtil = VolleyUtil.getInstance(getActivity());
        mAppAdapter = new AppAdapter(R.layout.item_app, mAppList);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mAppRv.setLayoutManager(manager);
        mAppRv.setAdapter(mAppAdapter);


        mFastAppAdapter = new AppAdapter(R.layout.item_fast_app, mFastAppList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFastAppRv.setLayoutManager(layoutManager);
        mFastAppRv.setAdapter(mFastAppAdapter);

        mAppSrl.setOnRefreshListener(this);
        mAppSrl.setRefreshing(true);
        getAppList();
    }

    private void initListener() {
        mAppAdapter.setOnItemClickListener(this);
        mFastAppAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        mAppSrl.setRefreshing(true);
        getAppList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == mFastAppAdapter) {
            App app = mFastAppList.get(position);
            startActivity(app);
        } else {
            App app = mAppList.get(position);
            startActivity(app);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    private void getAppList() {
        String userId = "40db1c5ede0311eabfad06ca1dafdbae";
        String url = Constant.APP_BASE_URL + "user/" + userId + "/app";

        mVolleyUtil.httpGetRequest(url, response -> {
            final UserApp userApp = JSONArray.parseObject(response, UserApp.class);
            mAppList = userApp.getUserAppList();
            mFastAppList = userApp.getUserFastAppList();
            mAppAdapter.setNewData(userApp.getUserAppList());
            mFastAppAdapter.setNewData(userApp.getUserFastAppList());

            mAppSrl.setRefreshing(false);
        }, volleyError -> mAppSrl.setRefreshing(false));
    }

    private void startActivity(App app) {
        if (StringUtils.isNullOrEmpty(app.getRoute())) {
            return;
        }
        Intent intent;
        switch (app.getRoute()) {
            case "FriendList"://名片
                intent = new Intent(getActivity(), FriendListActivity.class);
                startActivity(intent);
                break;
            case "InvoiceList"://发票
                intent = new Intent(getActivity(), InvoiceListActivity.class);
                startActivity(intent);
                break;
            case "QuoteList"://报价
                intent = new Intent(getActivity(), QuoteListActivity.class);
                startActivity(intent);
                break;
            case "GoodsListPage"://物品
                intent = new Intent(getActivity(), GoodsListActivity.class);
                startActivity(intent);
                break;
            case "WarehouseList"://仓库
                intent = new Intent(getActivity(), WarehouseListActivity.class);
                startActivity(intent);
                break;
            case "WorkClaiming"://个人报销
                intent = new Intent(getActivity(), MineClaimingActivity.class);
                startActivity(intent);
                break;
            case "Signin"://个人考勤
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(getActivity(), permissions, REQUEST_CODE_LOCATION);
                break;
            case "QrCodePage"://扫描
                String[] cameraPermissions = new String[]{Manifest.permission.CAMERA};
                requestPermissions(getActivity(), cameraPermissions, REQUEST_CODE_CAMERA);
                break;
            case "OrderList"://订单
                intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
                break;
            case "EmployeeList":
                // 人员
                intent = new Intent(getActivity(), EmployeeActivity.class);
                startActivity(intent);
                break;
            case "RequirementList"://需求
                intent = new Intent(getActivity(), RequirementActivity.class);
                startActivity(intent);
                break;
            case "ContractList"://合同
                intent = new Intent(getActivity(), ContactListActivity.class);
                startActivity(intent);
                break;
            case "WorkExamine"://盘点
                intent = new Intent(getActivity(), FabricCheckProcessActivity.class);
                startActivity(intent);
                break;
            case "StockInApply":
                // 入库单
                intent = new Intent(getActivity(), StockInApplyActivity.class);
                startActivity(intent);
                break;
            case "StockIn":
                // 入库
                intent = new Intent(getActivity(), StockInApplyActivity.class);
                startActivity(intent);
                break;
            case "11":
                intent = new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intent);
                break;

        }

    }

    /**
     * 动态权限
     */
    public void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0开始的动态权限，这里进行版本判断
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
                    case REQUEST_CODE_LOCATION:
                        startActivity(new Intent(getActivity(), MeAttendanceActivity.class));
                        break;
                    case REQUEST_CODE_CAMERA:
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        Objects.requireNonNull(getActivity()).startActivityForResult(intent, REQUEST_CODE_SCAN);
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
                case REQUEST_CODE_LOCATION:
                    // 同意定位权限,进入地图选择器
                    startActivity(new Intent(getActivity(), MeAttendanceActivity.class));
                    break;
                case REQUEST_CODE_CAMERA:
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    Objects.requireNonNull(getActivity()).startActivityForResult(intent, REQUEST_CODE_SCAN);
                    break;
            }
        } else {
            // 拒绝授权做的处理，弹出弹框提示用户授权
            handleRejectPermission(getActivity(), permissions[0], requestCode);
        }
    }

    public void handleRejectPermission(final Activity context, String permission, int requestCode) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            String content = "";
            switch (requestCode) {
                case REQUEST_CODE_LOCATION:
                    content = "在设置-应用-瓦丁-权限中开启位置信息权限，以正常使用考勤等功能";
                    break;
                case REQUEST_CODE_CAMERA:
                    content = "在设置-应用-瓦丁-权限中开启打开相机权限，以正常使用扫码等功能";
                    break;
            }
            final ConfirmDialog mConfirmDialog = new ConfirmDialog(getActivity(), "权限申请",
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
