package com.wd.winddots.desktop.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.wd.winddots.R;
import com.wd.winddots.activity.work.DeliveryActivity;
import com.wd.winddots.base.BaseFragment;

import com.wd.winddots.confifg.Const;
import com.wd.winddots.desktop.activity.StoreActivity;
import com.wd.winddots.desktop.bean.DesktopListBean;
import com.wd.winddots.desktop.adapter.WorkAdapter;
import com.wd.winddots.desktop.list.card.activity.FriendListActivity;
import com.wd.winddots.desktop.list.check.activity.SelectGoodsActivity;
import com.wd.winddots.desktop.list.contact.activity.ContactListActivity;
import com.wd.winddots.desktop.list.disk.DiskListActivity;
import com.wd.winddots.desktop.list.employee.activity.EmployeeActivity;
import com.wd.winddots.desktop.list.goods.activity.GoodsListActivity;
import com.wd.winddots.desktop.list.invoice.activity.InvoiceDetailActivity;
import com.wd.winddots.desktop.list.invoice.activity.InvoiceListActivity;
import com.wd.winddots.desktop.list.invoice.bean.InvoiceDetailBean;
import com.wd.winddots.desktop.list.order.activity.OrderListActivity;
import com.wd.winddots.desktop.list.quote.activity.QuoteListActivity;
import com.wd.winddots.desktop.list.requirement.activity.RequirementActivity;
import com.wd.winddots.desktop.list.warehouse.activity.WarehouseListActivity;
import com.wd.winddots.desktop.presenter.impl.WorkPresenterImpl;
import com.wd.winddots.desktop.presenter.view.WorkView;

import com.wd.winddots.fast.activity.MeAttendanceActivity;
import com.wd.winddots.fast.activity.MineClaimingActivity;
import com.wd.winddots.mvp.listener.MainActivityDispatchEventListener;
import com.wd.winddots.mvp.widget.MainActivity;
import com.wd.winddots.mvp.widget.WebViewActivity;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.net.msg.MsgDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;
import com.wd.winddots.view.BottomSearchBarView;
import com.wd.winddots.view.LoadingDialog;
import com.wd.winddots.view.PopShortcut;
import com.wd.winddots.view.dialog.ConfirmDialog;
import com.wd.winddots.zxing.client.android.CaptureActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class WorkFragment extends BaseFragment<WorkView, WorkPresenterImpl>
        implements WorkView, SwipeRefreshLayout.OnRefreshListener, BottomSearchBarView.BottomSearchBarViewClickListener,
        BaseQuickAdapter.OnItemClickListener, View.OnClickListener, BaseQuickAdapter.OnItemLongClickListener,
        MainActivityDispatchEventListener {


    private static final int REQUEST_CODE_LOCATION = 1;
    private static final int REQUEST_CODE_CAMERA = 2;

    private static final int REQUEST_CODE_SCAN = 10086;
    private static final int REQUEST_CODE_ADD_ITEM = 10087;



    private float rawY;
    private int movePosition = 10000;

    private boolean editModel;


    /*
     * view
     * */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView mFastRecyclerView;
    private BottomSearchBarView mSearchBarView;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper mItemTouchHelper1;
    private TextView mFinishTv;
    private ImageView mEditImageView;
    private LinearLayout body;
    private PopShortcut mPopupView;
    private PopupWindow mPopupWindow;

    private float ACTION_DOWN_X = 0;
    private float ACTION_DOWN_Y = 0;

    /*
     * 0 选择快捷栏  1 选择桌面
     * */
    private int mPopupWindowTarget = 0;


    /*
     * data
     * */
    private List<DesktopListBean.StoreListBena> mDataSource = new ArrayList<>();
    private List<DesktopListBean.StoreListBena> mFastDataSource = new ArrayList<>();
    private WorkAdapter mAdapter;
    private WorkAdapter mFastAdapter;

    public CompositeSubscription compositeSubscription;
    public MsgDataManager dataManager;
    public ElseDataManager elseDataManager;


    private int mDeletedPosition = 10000;

    private LoadingDialog mLoadingDialog;


    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        fragment.compositeSubscription = new CompositeSubscription();
        fragment.dataManager = new MsgDataManager();
        fragment.elseDataManager = new ElseDataManager();
        return fragment;
    }


    @Override
    public WorkPresenterImpl initPresenter() {
        return new WorkPresenterImpl();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_tap_store;
    }

    @Override
    public void initView() {

        compositeSubscription = new CompositeSubscription();
        dataManager = new MsgDataManager();
        elseDataManager = new ElseDataManager();

        body = mView.findViewById(R.id.rl_body);

        mSwipeRefreshLayout = mView.findViewById(R.id.fragment_tap_store_srl);
        mRecyclerView = mView.findViewById(R.id.fragment_tap_store_list);
        mAdapter = new WorkAdapter(R.layout.item_tap_store, mDataSource);
        mSearchBarView = mView.findViewById(R.id.fragment_tap_store_searchbar);


        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


//        DesktopListBean.StoreListBena bena1 = new DesktopListBean.StoreListBena();
//        bena1.setApplicationPhoto("http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/fbb79c0308ff43f7b0a476c6b554ec1d.png");
//        bena1.setName("考勤");
//        bena1.setRouteAddress("Signin");
//
//        DesktopListBean.StoreListBena bena2 = new DesktopListBean.StoreListBena();
//        bena2.setApplicationPhoto("http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/1cd9556663b04709ba4809addaab7614.png");
//        bena2.setName("报销");
//        bena2.setRouteAddress("WorkClaiming");
//
//        DesktopListBean.StoreListBena bena3 = new DesktopListBean.StoreListBena();
//        bena3.setApplicationPhoto("http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/c222e6f86ba84e34b64691f2efe3c014.png");
//        bena3.setName("扫描");
//        bena3.setRouteAddress("QrCodePage");
//        mFastDataSource.add(bena1);
//        mFastDataSource.add(bena2);
//        mFastDataSource.add(bena3);
        mFastRecyclerView = mView.findViewById(R.id.rlist_fast);
        mFastAdapter = new WorkAdapter(R.layout.item_tap_fast_store, mFastDataSource);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFastRecyclerView.setLayoutManager(layoutManager1);
        mFastRecyclerView.setAdapter(mFastAdapter);


        //body.bringChildToFront(mFastRecyclerView);


        MainActivity mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;
        mainActivity.setDispatchEventListener(this);


        mItemTouchHelper1 = new ItemTouchHelper(new ItemTouchHelper.Callback() {

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

                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }

                int[] location = new int[2];
                mFastRecyclerView.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];


                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mFastDataSource, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mFastDataSource, i, i - 1);
                    }
                }
                mFastAdapter.notifyItemMoved(fromPosition, toPosition);
                movePosition = toPosition;
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
                    movePosition = viewHolder.getAdapterPosition();

                    View mPopupView = View.inflate(mContext, R.layout.pop_store_long_press, null);
                    //mPopupView = new PopShortcut(mContext);
                    //mPopupView.setTabBottomBarClickListener(this);
                    mPopupWindow = new PopupWindow(mPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    mPopupWindow.setFocusable(true);
                    mPopupWindow.setTouchable(true);
                    mPopupWindow.setOutsideTouchable(true);

                    TextView deleteTv = mPopupView.findViewById(R.id.tv_delete);
                    deleteTv.setOnClickListener(WorkFragment.this);
                    TextView addTv = mPopupView.findViewById(R.id.tv_add);
                    addTv.setOnClickListener(WorkFragment.this);

                    addTv.setText("移除快捷");
                    // 实例化一个ColorDrawable颜色为半透明
                    ColorDrawable dw = new ColorDrawable(0000000000);
                    // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
                    mPopupWindow.setBackgroundDrawable(dw);


                    int[] location = new int[2];
                    viewHolder.itemView.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];

                    int X = Utils.dip2px(mContext, x);
                    int Y = Utils.dip2px(mContext, y);
                    Log.e("net666", "itemView--->" + x + "  " + "itemView--->" + y);

                    mPopupWindow.showAtLocation(body, Gravity.TOP | Gravity.LEFT, (x - (viewHolder.itemView.getWidth() / 6)), (int) (y - (viewHolder.itemView.getHeight() * 1.3)));

                    mPopupWindowTarget = 0;

                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);

                if (mPopupWindow == null){
                    itemChange();
                }

                //  movePosition = 10000;
               //
            }
        });
        mItemTouchHelper1.attachToRecyclerView(mFastRecyclerView);


        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                int[] location = new int[2];
                mFastRecyclerView.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];


                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDataSource, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mDataSource, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                movePosition = toPosition;
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }


            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    //mRecyclerView.bringToFront();
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                    movePosition = viewHolder.getAdapterPosition();
                    View mPopupView;
                    int[] location = new int[2];
                    viewHolder.itemView.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    if (movePosition <= 3){
                        mPopupView = View.inflate(mContext, R.layout.pop_store_long_press1, null);
                        y = y + viewHolder.itemView.getHeight();
                    }else {
                        mPopupView = View.inflate(mContext, R.layout.pop_store_long_press, null);
                        y = y - viewHolder.itemView.getHeight();
                    }


                    //mPopupView = new PopShortcut(mContext);
                    //mPopupView.setTabBottomBarClickListener(this);
                    mPopupWindow = new PopupWindow(mPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    mPopupWindow.setFocusable(true);
                    mPopupWindow.setTouchable(true);
                    mPopupWindow.setOutsideTouchable(true);

                    TextView deleteTv = mPopupView.findViewById(R.id.tv_delete);
                    deleteTv.setOnClickListener(WorkFragment.this);
                    TextView addTv = mPopupView.findViewById(R.id.tv_add);
                    addTv.setOnClickListener(WorkFragment.this);


                    // 实例化一个ColorDrawable颜色为半透明
                    ColorDrawable dw = new ColorDrawable(0000000000);
                    // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
                    mPopupWindow.setBackgroundDrawable(dw);

                    mPopupWindow.showAtLocation(body, Gravity.TOP | Gravity.LEFT, (x - (viewHolder.itemView.getWidth() / 6)),y);

                    mPopupWindowTarget = 1;
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
//                if (mPopupWindow != null){
//                    mPopupWindow.dismiss();
//                    mPopupWindow = null;
//                }
                viewHolder.itemView.setBackgroundColor(0);
                int index = viewHolder.getAdapterPosition();
//                List<Map> items = new ArrayList<>();
//                for (int i = 0; i < mDataSource.size(); i++) {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("applicationId", mDataSource.get(i).getId());
//                    items.add(map);
//                }
//                int[] location = new int[2];
//                mFastRecyclerView.getLocationOnScreen(location);
//                int x = location[0];
//                int y = location[1];
//                Log.e("net666", "mFastRecyclerView--->" + x + "  " + "mFastRecyclerView--->" + y);

//                if (rawY > y) {
//                    DesktopListBean.StoreListBena item = mDataSource.get(index);
//                    mFastAdapter.addData(item);
//                    mAdapter.remove(index);
//                    mFastRecyclerView.scrollToPosition(mFastAdapter.getData().size() - 1);
//                }
                //movePosition = 10000;

                if (mPopupWindow == null){
                    itemChange();
                }
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        onRefresh();
    }

    @Override
    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSearchBarView.setOnIconClickListener(this);
        //mAdapter.setOnItemLongClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mFastAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        presenter.getWorkList(SpHelper.getInstance(mContext.getApplicationContext()).getString("id")
        );

    }

    @Override
    public void onRefresh() {

        editModel = false;
        presenter.getWorkList(SpHelper.getInstance(mContext.getApplicationContext()).getString("id"));
    }


    /*
     * searchBar 点击事件
     * */
    @Override
    public void onAddIconDidClick() {
//        if (mDataSource.size() == 0){
//            return;
//        }

        List<Map> ids = new ArrayList<>();
        for (int i = 0; i < mDataSource.size(); i++) {
            Map map = new HashMap();
            DesktopListBean.StoreListBena bena = mDataSource.get(i);
            map.put("applicationId", bena.getId());
            map.put("status", "1");
            ids.add(map);
        }
        for (int i = 0; i < mFastDataSource.size(); i++) {
            Map map = new HashMap();
            DesktopListBean.StoreListBena bena = mFastDataSource.get(i);
            map.put("applicationId", bena.getId());
            map.put("status", "2");
            ids.add(map);
        }
        Gson gson = new Gson();
        String jsonS = gson.toJson(ids);
        Intent intent = new Intent(mContext, StoreActivity.class);
        intent.putExtra("data", jsonS);
        startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
    }

    @Override
    public void onSearchIconDidClick() {
    }


    /*
     * item 点击事件
     * */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (editModel) {
            if (adapter == mFastAdapter) {
                mAdapter.addData(mFastDataSource.get(position));
                mFastAdapter.remove(position);
            } else {
                mAdapter.remove(position);
            }
            return;
        }

        if (adapter == mFastAdapter) {
            DesktopListBean.StoreListBena bena = mFastDataSource.get(position);
            jumpToActivity(bena);
        } else {
            DesktopListBean.StoreListBena bena = mDataSource.get(position);
            jumpToActivity(bena);
        }

    }

    private void jumpToActivity(DesktopListBean.StoreListBena bena) {
        if (StringUtils.isNullOrEmpty(bena.getRouteAddress())) {
            return;
        }
        Intent intent;
        switch (bena.getRouteAddress()) {
            case "FriendList"://名片
                intent = new Intent(mContext, FriendListActivity.class);
                startActivity(intent);
                break;
            case "InvoiceList"://发票
                intent = new Intent(mContext, InvoiceListActivity.class);
                startActivity(intent);
                break;
            case "QuoteList"://报价
                intent = new Intent(mContext, QuoteListActivity.class);
                startActivity(intent);
                break;
            case "GoodsListPage"://物品
                intent = new Intent(mContext, GoodsListActivity.class);
                startActivity(intent);
                break;
            case "WarehouseList"://仓库
                intent = new Intent(mContext, WarehouseListActivity.class);
                startActivity(intent);
                break;
            case "WorkClaiming"://个人报销
                intent = new Intent(mContext, MineClaimingActivity.class);
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
                intent = new Intent(mContext, OrderListActivity.class);
                startActivity(intent);
                break;
            case "EmployeeList"://人员
                intent = new Intent(mContext, EmployeeActivity.class);
                startActivity(intent);
                break;
            case "RequirementList"://需求
                intent = new Intent(mContext, RequirementActivity.class);
                startActivity(intent);
                break;
            case "ContractList"://合同
                intent = new Intent(mContext, ContactListActivity.class);
                startActivity(intent);
                break;
            case "WorkExamine"://盘点
                intent = new Intent(mContext, SelectGoodsActivity.class);
                startActivity(intent);
                break;
            case "11":
                intent = new Intent(mContext, DeliveryActivity.class);
                startActivity(intent);
                break;
//            default:
//                intent = new Intent(mContext, DiskListActivity.class);
//                startActivity(intent);
            //case "AttenceStatistics"://合同


        }

    }

    /*
     * item 长按事件
     * */
    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {

        final DesktopListBean.StoreListBena item = mDataSource.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确定要删除" + item.getName() + "模块吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mLoadingDialog = LoadingDialog.getInstance(mContext);//.show();
                mLoadingDialog.show();
                mDeletedPosition = position;

                List<String> ids = new ArrayList<>();
                for (int m = 0; m < mDataSource.size(); m++) {
                    if (m != position) {
                        ids.add(mDataSource.get(m).getId());
                    }
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
        return true;
    }


    /*
     * 获取列表数据
     * */
    @Override
    public void getWorkListSuccess(DesktopListBean listBean) {

        mDataSource.clear();
        mFastDataSource.clear();
        List<DesktopListBean.StoreListBena> list = listBean.getList();
        if (list == null) {
            list = new ArrayList<>();
        }
        for (int i = 0; i < list.size(); i++) {
            DesktopListBean.StoreListBena item = list.get(i);
            if ("1".equals(item.getBottomState())) {
                mDataSource.add(item);
            } else {
                mFastDataSource.add(item);
            }
        }
        mAdapter.notifyDataSetChanged();
        mFastAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getWorkListError(Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        showToast("获取失败,请稍后重试");
    }

    @Override
    public void getWorkListCompleted() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    /*
     * 删除 item
     * */
    @Override
    public void deleteItemSuccess() {
        mLoadingDialog.hide();
        mLoadingDialog = null;
        if (mDeletedPosition != 10000) {
            //mDataSource.remove(mDeletedPosition);
            //mAdapter.notifyDataSetChanged();
            //mAdapter.notifyItemChanged(mDeletedPosition);
            mAdapter.remove(mDeletedPosition);
            mDeletedPosition = 10000;
        }
    }

    @Override
    public void deleteItemError(String s) {
    }

    @Override
    public void deleteItemCompleted() {

    }

    /*
     * item 排序
     * */
    @Override
    public void setApplicationSortSuccess() {
    }

    @Override
    public void setApplicationSortError(String s) {
    }

    @Override
    public void setApplicationSortCompleted() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                onDeleteItem();
                break;
            case R.id.tv_add:
                onMoveItem();
                break;
        }
    }

    private void onDeleteItem() {

        if (movePosition == 10000){
            return;
        }

        DesktopListBean.StoreListBena item;

        if (mPopupWindowTarget == 1) {
            item = mDataSource.get(movePosition);
            mAdapter.remove(movePosition);
            mPopupWindow.dismiss();
            mPopupWindow = null;
        } else {
            item = mFastDataSource.get(movePosition);
            mFastAdapter.remove(movePosition);
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }

        List<Map> ids = new ArrayList<>();
        for (int i = 0; i < mDataSource.size(); i++) {
            Map map = new HashMap();
            DesktopListBean.StoreListBena bena = mDataSource.get(i);
            map.put("applicationId", bena.getId());
            map.put("status", "1");
            ids.add(map);
        }
        for (int i = 0; i < mFastDataSource.size(); i++) {
            Map map = new HashMap();
            DesktopListBean.StoreListBena bena = mFastDataSource.get(i);
            map.put("applicationId", bena.getId());
            map.put("status", "2");
            ids.add(map);
        }
        movePosition = 10000;

        presenter.deleteItem(
                SpHelper.getInstance(mContext).getUserId(),
                item.getId(),
                ids,
                SpHelper.getInstance(mContext).getEnterpriseId()
        );
    }

    private void onMoveItem() {

        if (movePosition == 10000){
            return;
        }

        if (mPopupWindowTarget == 1) {
            mFastAdapter.addData(0,mDataSource.get(movePosition));
            mAdapter.remove(movePosition);
            mPopupWindow.dismiss();
            mPopupWindow = null;
            mFastRecyclerView.scrollToPosition(0);
        } else {
            mAdapter.addData(mFastDataSource.get(movePosition));
            mFastAdapter.remove(movePosition);
            mPopupWindow.dismiss();
            mPopupWindow = null;
            mRecyclerView.scrollToPosition(mDataSource.size() - 1);
            // mFastRecyclerView.scrollToPosition(mFastDataSource.size()-1);
        }


        itemChange();
    }

    private void itemChange() {

        DesktopListBean.StoreListBena item = mDataSource.get(0);
        List<Map> ids = new ArrayList<>();
        for (int i = 0; i < mDataSource.size(); i++) {
            Map map = new HashMap();
            DesktopListBean.StoreListBena bena = mDataSource.get(i);
            map.put("applicationId", bena.getId());
            map.put("status", "1");
            ids.add(map);
        }
        for (int i = 0; i < mFastDataSource.size(); i++) {
            Map map = new HashMap();
            DesktopListBean.StoreListBena bena = mFastDataSource.get(i);
            map.put("applicationId", bena.getId());
            map.put("status", "2");
            ids.add(map);
        }
        RequestBody requestBody = Utils.list2requestBody(ids);
        presenter.setApplicationSort(
                SpHelper.getInstance(mContext).getUserId(),
                requestBody,
                SpHelper.getInstance(mContext).getEnterpriseId()
        );
        movePosition = 10000;
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
                    case REQUEST_CODE_LOCATION:
                        startActivity(MeAttendanceActivity.class);
                        break;
                    case REQUEST_CODE_CAMERA:
                        Intent intent1 = new Intent(mContext, CaptureActivity.class);
                        Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
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
                    startActivity(MeAttendanceActivity.class);
                    break;
                case REQUEST_CODE_CAMERA:
                    Intent intent1 = new Intent(mContext, CaptureActivity.class);
                    Objects.requireNonNull(getActivity()).startActivityForResult(intent1, REQUEST_CODE_SCAN);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ITEM){
            onRefresh();
        }


        if (data == null) {
            return;
        }
        String resultStr = data.getStringExtra("result");
        if (StringUtils.isNullOrEmpty(resultStr)) {
            return;
        }

        if (requestCode == REQUEST_CODE_SCAN){
            if (resultStr.length() > 4 && "http".equals(resultStr.substring(0, 4))) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Const.WEB_ACTIVITY_URL_INTENT, resultStr);
                startActivity(intent);
            } else {

                Log.e("net666", resultStr);
                compositeSubscription.add(elseDataManager.getInvoiceDetailByScan(resultStr, SpHelper.getInstance(mContext).getUserId()).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("net666", String.valueOf(e));
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e("net666", s);
                                Gson gson = new Gson();
                                InvoiceDetailBean invoiceDetailBean = gson.fromJson(s, InvoiceDetailBean.class);
                                if (0 == invoiceDetailBean.getCode()) {
                                    Intent intent = new Intent(mContext, InvoiceDetailActivity.class);
                                    String jsonS = gson.toJson(invoiceDetailBean.getData());
                                    intent.putExtra("data", s);
                                    intent.putExtra("isScan", true);
                                    startActivity(intent);
                                } else {
                                    showToast("未能识别的二维码");
                                }
                            }
                        })
                );
            }
        }




    }


    @Override
    public void dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Log.e("net666","点击");
                //Log.e("net666", "按下--" + ev.getRawX() + "按下---" + ev.getRawY());
                ACTION_DOWN_X = ev.getRawX();
                ACTION_DOWN_Y = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.e("net666","移动");
                //Log.e("net666", "移动--" + ev.getRawX() + "移动---" + ev.getRawY());

                if (mPopupWindow == null) {
                    return;
                }

                float xValue = Math.abs(ACTION_DOWN_X - ev.getRawX());
                float yValue = Math.abs(ACTION_DOWN_Y - ev.getRawY());

                if (xValue > 30 || yValue > 30) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                    ACTION_DOWN_X = 0;
                    ACTION_DOWN_Y = 0;
                }


                break;
            case MotionEvent.ACTION_UP:
                //Log.e("net666", "松手--" + ev.getRawX() + "松手---" + ev.getRawY());
                rawY = ev.getRawY();
                int[] location = new int[2];
                mFastRecyclerView.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                //Log.e("net666", "mFastRecyclerView--->" + x + "  " + "mFastRecyclerView--->" + y);

//                if (rawY > y) {
//                    if (movePosition != 10000) {
//                        Log.e("net666", "movePosition-----" + movePosition);
//                        LinearLayout body = (LinearLayout) mRecyclerView.getChildAt(movePosition);
//                        body.setVisibility(View.GONE);
//                    }
//                }
                break;
        }
    }

}
