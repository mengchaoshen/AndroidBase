//package com.smc.androidbase.other;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.webkit.DownloadListener;
//import android.webkit.JsResult;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Toast;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.maoliicai.common.R;
//import com.maoliicai.common.R2;
//import com.maoliicai.common.base.BaseFragment;
//import com.maoliicai.common.base.RouterExtra;
//import com.maoliicai.common.base.RouterPath;
//import com.maoliicai.common.eventbus.EventType;
//import com.maoliicai.common.utils.DownLoadUtil;
//import com.maoliicai.common.utils.SP;
//import com.maoliicai.common.utils.ShareDialogUtil;
//import com.maoliicai.common.utils.ToastUtils;
//import com.maoliicai.common.webview_javascript.JavaScriptInterface;
//import com.maoliicai.common.widget.dialog.BindSuccessDialog;
//import com.maoliicai.common.widget.html.FileType;
//import com.maoliicai.common.widget.html.ShowNotification;
//
//import java.io.File;
//import java.net.URISyntaxException;
//
//import butterknife.BindView;
//
///**
// * yangyoupeng  on 2018/5/8.
// */
//
//@SuppressLint("ValidFragment")
//public class HtmlFragment extends BaseFragment implements DownloadListener {
//
//    private static final String TAG = "HtmlFragment";
//    /**
//     * 兑吧链接 重定向页面 防止一直可以点击后退
//     */
//    public static final String DUIBA_URL = "home.m.duiba.com.cn/chome/index?from=";
//
//    @BindView(R2.id.wb_help)
//    WebView mWbHelp;
//
//    private String mVersionNumber;
//    private JavaScriptInterface mJSInterface;
//    private boolean mNeedClearHistory = false;
//    private String mType, mUrl;
//
//    @Override
//    public int getFragmentLayout() {
//        return R.layout.fragment_html;
//    }
//
//    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
//    @Override
//    protected void initView() {
//
//        //获取版本号
//        try {
//            PackageManager manager = getContext().getPackageManager();
//            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
//            mVersionNumber = info.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        showLoadingDialog();
//        //清理缓存
//        mWbHelp.clearCache(true);
//        //1.设置支持JavaScript
//        WebSettings webSettings = mWbHelp.getSettings();
//        webSettings.setJavaScriptEnabled(true);//设置支持javascript脚本
//        mJSInterface = new com.maoliicai.common.webview_javascript.JavaScriptInterface(getContext());
//        mWbHelp.addJavascriptInterface(mJSInterface, "JSInterface");//new JavaScriptInterface()接口定义
//        mJSInterface.setShareWebListener(new JavaScriptInterface.OnShareWebListener() {
//            @Override
//            public void shareDo(String title, String description, String url) {
//                ShareDialogUtil.showShareDialog(getContext(),
//                        title, url, description);
//            }
//
//            @Override
//            public void inviteShareDo(String title, String description, String url) {
//                ShareDialogUtil.showInviteShareDialog(getContext(),
//                        title, url, description, new ShareDialogUtil.OnInviteShareListener() {
//                            @Override
//                            public void getShareWx() {
//                                //调用邀请好友统计分享次数
//                                ((CommonActivity) mActivity).getShareWx();
//                            }
//                        });
//            }
//
//        });
//        mWbHelp.setWebChromeClient(new WebChromeClient() {//设置webview可显示alert提示
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                return super.onJsAlert(view, url, message, result);
//            }
//        });
//        //可以加载http以及https混合内容
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        //2.设置WebView中链接在WebView内部跳转
//        mWbHelp.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return loadUrl(view, url);
//            }
//
//            /**
//             * 通知应用程序页面加载完成
//             */
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                hideLoadingDialog();
//                onPageLoadFinished();
//            }
//
//            @Override
//            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
//                super.doUpdateVisitedHistory(view, url, isReload);
//                if (mNeedClearHistory) {
//                    mWbHelp.clearHistory();
//                    mNeedClearHistory = false;
//                }
//            }
//        });
//
//        //3.下载文件的监听
//        mWbHelp.setDownloadListener(this);
//
//        //补充链接后面的参数
//        Bundle arguments = getArguments();
//        mType = arguments.getString(RouterExtra.EXTRA_TYPE);
//        mUrl = arguments.getString(RouterExtra.EXTRA_TITLE_URL);
//
//        String url = getUrl(mUrl);
//        mWbHelp.loadUrl(url);
//
//        mWbHelp.clearHistory();
//        mWbHelp.clearCache();
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    /**
//     * 获取H5页面链接
//     */
//    private String getUrl(String url) {
//        String exactUrl = (url.contains("?") ? url + "&auth=" : url + "?auth=")
//                + SP.getToken(mActivity)
//                + "&registerType=" + SP.getChannel(mActivity)
//                + "&source=android"
//                + "&versionNumber=" + mVersionNumber;
//
//        return exactUrl;
//    }
//
//    /**
//     * webView内部跳转
//     */
//    private boolean loadUrl(WebView view, String url) {
//        //返回值是true的时候控制去WebView内部打开，为false调用系统浏览器或第三方浏览器
//        if (url.startsWith("http:") || url.startsWith("https:")) {
//            view.loadUrl(url);
//            return false;
//        } else {
//            if (!url.startsWith("mailto:")) {
//                try {
//                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                    intent.addCategory("android.intent.category.BROWSABLE");
//                    intent.setComponent(null);
//                    startActivity(intent);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                    Log.d(TAG, "shouldOverrideUrlLoading: " + e.getMessage());
//                }
//            }
//            return true;
//        }
//    }
//
//    /**
//     * 处理是否显示左上角的叉叉
//     */
//    private void onPageLoadFinished() {
//        switch (mType) {
//            case RouterExtra.EXTRA_TYPE_SIGNIN://每日签到
//            case RouterExtra.EXTRA_TYPE_TASK_CENTER://任务中心
//            case RouterExtra.EXTRA_TYPE_NOTICE_LIST://公告列表
//            case RouterExtra.EXTRA_TYPE_COMMON_PROBLEM://常见问题
//            case RouterExtra.EXTRA_TYPE_ACTIVITY://活动详情页
//                if (mWbHelp.canGoBack()) {
//                    ((CommonActivity) getActivity()).showCloseAll(true);
//                } else {
//                    ((CommonActivity) getActivity()).showCloseAll(false);
//                }
//                break;
//            case RouterExtra.EXTRA_TYPE_SHOP_DUIBA://积分商城
//                if (mWbHelp.canGoBack() && !mWbHelp.getUrl().contains(DUIBA_URL)) {
//                    ((CommonActivity) getActivity()).showCloseAll(true);
//                } else {
//                    ((CommonActivity) getActivity()).showCloseAll(false);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mWbHelp != null) {
//            mWbHelp.destroy();//结束加载网页
//        }
//        super.onDestroy();
//    }
//
//    /**
//     * H5页面有多级页面 需要后退
//     */
//    public boolean canGoBack() {
//        switch (mType) {
//            case RouterExtra.EXTRA_TYPE_ACTIVITY://活动详情页
//            case RouterExtra.EXTRA_TYPE_NOTICE_LIST://公告列表
//            case RouterExtra.EXTRA_TYPE_SIGNIN://每日签到
//            case RouterExtra.EXTRA_TYPE_TASK_CENTER://任务中心
//            case RouterExtra.EXTRA_TYPE_COMMON_PROBLEM://常见问题
//                return mWbHelp.canGoBack();
//            case RouterExtra.EXTRA_TYPE_SHOP_DUIBA:
//                if (mWbHelp.canGoBack() && !mWbHelp.getUrl().contains(DUIBA_URL)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            default:
//                return false;
//        }
//
//    }
//
//    /**
//     * 是否有上级H5页面可以返回
//     */
//    public void goBack() {
//        mWbHelp.goBack();
//    }
//
//
//    @Override
//    public void onEventType(EventType eventType) {
//        super.onEventType(eventType);
//        switch (eventType.getType()) {
//            case EventType.EVENT_LOGIN:
////                if (mType == RouterExtra.EXTRA_TYPE_INVITE_FRIEND) {//邀请好友
////                    mWbHelp.loadUrl(getUrl(mUrl));
////                }
////                if (mType == RouterExtra.EXTRA_TYPE_SHOP_DUIBA) {//积分商城
////                    mWbHelp.loadUrl(getUrl(mUrl));
////                }
//                mWbHelp.clearCache(true);
//                mWbHelp.clearHistory();
//
//                String url = getUrl(mUrl);
//                mWbHelp.loadUrl(url);
//                break;
//            case EventType.EVENT_AUTH_SUCCESS_FROM_HTML://认证成功后
//                if (RouterExtra.EXTRA_TYPE_TASK_CENTER.equals(mType)) {//任务中心
//                    //发放认证成功奖励的dialog
//                    String ralNameAuthenticationImgUrl = SP.getRalNameAuthenticationImgUrl(mActivity);
//                    showBindSuccessDialog(mActivity, ralNameAuthenticationImgUrl);
//                    mWbHelp.loadUrl(getUrl(mUrl));
//                }
//                break;
//            case EventType.EVENT_SET_PAY_PWD_SUCCESS://设置交易密码
//                if (RouterExtra.EXTRA_TYPE_TASK_CENTER.equals(mType)) {//任务中心
//                    mWbHelp.loadUrl(getUrl(mUrl));
//                }
//                break;
//        }
//    }
//
//    /**
//     * 实名成功后的弹窗
//     */
//    public static void showBindSuccessDialog(Activity activity, String imgUrl) {
//        BindSuccessDialog bindSuccessDialog = new BindSuccessDialog(activity, imgUrl, "", "");
//        bindSuccessDialog.setCallback(new BindSuccessDialog.Callback() {
//            @Override
//            public void toTicket() {
//                ARouter.getInstance().build(RouterPath.SELECT_VOUCHER_ACTIVITY)
//                        .withString("myAssets", "myAssets")
//                        .navigation();
//            }
//
//            @Override
//            public void toInvest() {
//                ARouter.getInstance().build(RouterPath.FRAMWORK_ACTIVITY)
//                        .withString("homeInvestment", "homeInvestment")
//                        .navigation();
//            }
//        });
//        bindSuccessDialog.setCancelable(false);
//        bindSuccessDialog.show();
//    }
//
//    public void getShare() {
//
//    }
//
//
//    /**
//     * 下载文件的监听
//     */
//    @Override
//    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//        Toast.makeText(getContext(), "正在下载", Toast.LENGTH_LONG).show();
//        DownLoadUtil.downloadFile(getContext(), url, new DownLoadUtil.DownLoadListener() {
//            @Override
//            public void download(long fileSize, long downLoadFileSize) {
//                ShowNotification.showNotification(getActivity(), fileSize, downLoadFileSize);
//            }
//
//            @Override
//            public void downloadComplete(long fileSize, long downLoadFileSize, String apkFile) {
//                ShowNotification.canle();
//                Intent intent = FileType.getFileIntent(new File(apkFile));
//                startActivity(intent);
//            }
//
//            @Override
//            public void downloadFaile(boolean isConnect) {
//                ShowNotification.canle();
//                if (!isConnect) {
//                    ToastUtils.showShortToast("请检查网络后，重新下载");
//                }
//            }
//        });
//    }
//}
