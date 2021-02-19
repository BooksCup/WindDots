package com.wd.winddots.desktop.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.wd.winddots.GlideApp;
import com.wd.winddots.R;
import com.wd.winddots.desktop.bean.DesktopListBean;
import com.wd.winddots.net.ElseDataManager;
import com.wd.winddots.utils.SpHelper;
import com.wd.winddots.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class StoreAdapter extends BaseQuickAdapter<DesktopListBean.StoreListBena, BaseViewHolder> {


    public CompositeSubscription compositeSubscription;
    public ElseDataManager dataManager;

    private List<Map> ids;

    public void setIds(List<Map> ids) {
        this.ids = ids;
    }

    public StoreAdapter(int layoutResId, @Nullable List<DesktopListBean.StoreListBena> data) {
        super(layoutResId, data);
        compositeSubscription = new CompositeSubscription();
        dataManager = new ElseDataManager();
    }

    @Override
    protected void convert(BaseViewHolder helper, final DesktopListBean.StoreListBena item) {

        GlideApp.with(mContext).
                load(item.getApplicationPhoto()).
                into((ImageView) helper.getView(R.id.item_store_icon));

        helper.setText(R.id.item_store_nameText, item.getName());
        helper.setText(R.id.item_store_descText, item.getRemark());

        final TextView downBtn = helper.getView(R.id.item_store_downloadBtn);
        final TextView alText = helper.getView(R.id.item_store_alreadyText);
        final ProgressBar bar = helper.getView(R.id.item_store_progress_bar);

        if ("0".equals(item.getApplicationType())) {
            downBtn.setVisibility(View.VISIBLE);
            alText.setVisibility(View.GONE);
        } else {
            downBtn.setVisibility(View.GONE);
            alText.setVisibility(View.VISIBLE);
        }


        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map map = new HashMap();
                map.put("applicationId",item.getId());
                map.put("status","1");
                ids.add(map);
                downBtn.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);

                Map body = new HashMap();
                body.put("userId", SpHelper.getInstance(mContext).getUserId());
                body.put("applicationId", item.getId());
                body.put("applicationType", "0");
                body.put("applicationIds",ids);

                Gson gson = new Gson();
                String bodyString = gson.toJson(body);
                Log.e("net666",bodyString);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);

                compositeSubscription.add(
                        dataManager.addOrRemoveStore(requestBody,SpHelper.getInstance(mContext).getEnterpriseId()).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException error) {
                                    e.printStackTrace();
                                }
                                downBtn.setVisibility(View.VISIBLE);
                                bar.setVisibility(View.GONE);
                                alText.setVisibility(View.GONE);
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e("net666",s);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Map res = Utils.getMapForJson(s);
                                if (res == null){
                                    downBtn.setVisibility(View.VISIBLE);
                                    bar.setVisibility(View.GONE);
                                    alText.setVisibility(View.GONE);
                                    return;
                                }
                                int code = (int)res.get("code");
                                if (code == 0){
                                    downBtn.setVisibility(View.GONE);
                                    bar.setVisibility(View.GONE);
                                    alText.setVisibility(View.VISIBLE);
                                }else {
                                    downBtn.setVisibility(View.VISIBLE);
                                    bar.setVisibility(View.GONE);
                                    alText.setVisibility(View.GONE);
                                }
                            }
                        })
                );

            }
        });


    }

}
