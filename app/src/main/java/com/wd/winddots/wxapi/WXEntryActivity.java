package com.wd.winddots.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.winddots.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * FileName: WXEntryActivity
 * Author: 郑
 * Date: 2020/9/21 2:37 PM
 * Description: 微信分享
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI wxapi;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxapi.handleIntent(intent, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);

        wxapi = WXAPIFactory.createWXAPI(this, "你的appId");
        wxapi.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            // 正确返回
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        // 只是做了简单的finish操作
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                // 错误返回
                switch (baseResp.getType()) {
                    // 微信分享
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        Log.e("WXEntryActivity" , ">>>errCode = " + baseResp.errCode);
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}
