package com.test.harmonyosdemo.slice.test;

import com.test.harmonyosdemo.utils.ThreadPoolUtil;
import ohos.net.NetHandle;
import ohos.net.NetManager;
import ohos.net.NetStatusCallback;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 使用指定网络进行数据访问
 */
public class MyNetCallback extends NetStatusCallback {
    public static MyNetCallback netCallback;
    private final NetManager netManager = NetManager.getInstance(null);
    /**
     * 自定义接口
     */
    private OnNetCallback onNetCallback;
    /**
     * 成功code
     */
    private final int HTTP_OK = 200;

    public NetManager getNetManager() {
        return netManager;
    }

    public static MyNetCallback getNetCallback() {
        if (netCallback == null) {
            synchronized (MyNetCallback.class) {
                if (netCallback == null) {
                    netCallback = new MyNetCallback();
                }
            }
        }
        return netCallback;
    }

    public void setOnNetCallback(OnNetCallback onNetCallback) {
        this.onNetCallback = onNetCallback;
    }

    public interface OnNetCallback {
        void onResponseOk(HttpsURLConnection connection) throws Exception;

        void onError() throws Exception;
    }

    /**
     * 可用数据网络
     *
     * @param handle 当前网络
     */
    @Override
    public void onAvailable(NetHandle handle) {
        super.onAvailable(handle);
        // 通过setAppNet把后续应用所有的请求都通过该网络进行发送
        netManager.setAppNet(handle);
        ThreadPoolUtil.submit(() -> {
            String urlString = "https://img-blog.csdn.net/20160121201510406?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center";
            HttpsURLConnection connection = null;
            try {
                URL url = new URL(urlString);
                URLConnection urlConnection = handle.openConnection(url, java.net.Proxy.NO_PROXY);
                if (urlConnection instanceof HttpsURLConnection) {
                    connection = (HttpsURLConnection) urlConnection;
                }
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == HTTP_OK) {
                    onNetCallback.onResponseOk(connection);
                } else {
                    onNetCallback.onError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            // 如果业务执行完毕，可以停止获取
            netManager.removeNetStatusCallback(this);
        });

    }

    /**
     * 网络断开
     *
     * @param handle      当前网络
     * @param maxMsToLive 运行多少毫秒
     */
    @Override
    public void onLosing(NetHandle handle, long maxMsToLive) {
        super.onLosing(handle, maxMsToLive);
    }
}
