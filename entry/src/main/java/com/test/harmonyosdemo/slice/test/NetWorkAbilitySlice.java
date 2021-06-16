package com.test.harmonyosdemo.slice.test;

import com.test.harmonyosdemo.ResourceTable;
import com.test.harmonyosdemo.utils.ThreadPoolUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.net.HttpResponseCache;
import ohos.net.NetHandle;
import ohos.net.NetManager;
import ohos.net.NetStatusCallback;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class NetWorkAbilitySlice extends AbilitySlice {

    private final int HTTP_OK = 200;
    private Image image;
    private Button butGetPicture;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_net_work);
        initView();
        initListener();
    }

    private void initView() {
        image = (Image) findComponentById(ResourceTable.Id_iv_show_picture);
        butGetPicture = (Button)findComponentById(ResourceTable.Id_but_get_picture);

    }

    private void initListener() {
        butGetPicture.setClickedListener(component ->initNetWork());
    }

    private void initNetWork() {
        ThreadPoolUtil.submit(() -> {
            NetManager netManager = NetManager.getInstance(this);
            if (!netManager.hasDefaultNet()) {
                return;
            }
            NetHandle defaultNet = netManager.getDefaultNet();
            // 可以获取网络状态的变化
            NetStatusCallback callback = new NetStatusCallback();

            netManager.addDefaultNetStatusCallback(callback);
            // 通过openConnection来获取URLConnection
            HttpsURLConnection connection = null;
            try {
                String urlString = "https://img-blog.csdn.net/20160121201510406?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center";
                URL url = new URL(urlString);
                URLConnection urlConnection = defaultNet.openConnection(url, java.net.Proxy.NO_PROXY);
                if (urlConnection instanceof HttpsURLConnection) {
                    connection = (HttpsURLConnection) urlConnection;
                }
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == HTTP_OK) {
                    ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
                    ImageSource imageSource = ImageSource.create(connection.getInputStream(), srcOpts);
                    PixelMap pixelmap = imageSource.createPixelmap(null);
                    getUITaskDispatcher().syncDispatch(() -> image.setPixelMap(pixelmap));
                }
                connection.disconnect();
                // 之后可进行url的其他操作
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            try {
                cache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
