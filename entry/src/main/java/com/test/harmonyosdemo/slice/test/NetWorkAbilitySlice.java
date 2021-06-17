package com.test.harmonyosdemo.slice.test;

import com.test.harmonyosdemo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.net.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

public class NetWorkAbilitySlice extends AbilitySlice implements MyNetCallback.OnNetCallback {

    public Image image;
    private Button butGetPicture;
    private MyNetCallback myNetCallback;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_net_work);
        initView();
        initListener();

    }

    private void initView() {
        image = (Image) findComponentById(ResourceTable.Id_iv_show_picture);
        butGetPicture = (Button) findComponentById(ResourceTable.Id_but_get_picture);

    }

    private void initListener() {
        butGetPicture.setClickedListener(component -> initNetWork());
    }

    private void initNetWork() {
//        ThreadPoolUtil.submit(() -> {
        // 可以获取网络状态的变化
//        initNetSpecifier(callback);
        myNetCallback =  MyNetCallback.getNetCallback();
        myNetCallback.setOnNetCallback(this);
        myNetCallback.getNetManager().addDefaultNetStatusCallback(myNetCallback);

        // 通过openConnection来获取URLConnection
//            HttpsURLConnection connection = null;
//
//            //使用当前网络进行Socket数据传输
////            DatagramSocket socket = null;
//            try {
////                // 通过Socket绑定来进行数据传输
////                InetAddress inetAddress = defaultNet.getByName("");
////                socket = new DatagramSocket();
////                defaultNet.bindSocket(socket);
////                byte[] bytes = new byte[100];
////                DatagramPacket request  = new DatagramPacket(bytes,bytes.length,inetAddress,1);
////                // 发送数据
////                socket.send(request);
//
//                String urlString = "https://img-blog.csdn.net/20160121201510406?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center";
//                URL url = new URL(urlString);
//                URLConnection urlConnection = defaultNet.openConnection(url, java.net.Proxy.NO_PROXY);
//                if (urlConnection instanceof HttpsURLConnection) {
//                    connection = (HttpsURLConnection) urlConnection;
//                }
//                connection.setRequestMethod("GET");
//                connection.connect();
//                if (connection.getResponseCode() == HTTP_OK) {
//                    ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
//                    ImageSource imageSource = ImageSource.create(connection.getInputStream(), srcOpts);
//                    PixelMap pixelmap = imageSource.createPixelmap(null);
//                    getUITaskDispatcher().syncDispatch(() -> image.setPixelMap(pixelmap));
//                }
//                connection.disconnect();
//                // 之后可进行url的其他操作
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
////                socket.close();
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            }
//        });
    }

    /**
     * 配置一个彩信类型的蜂窝网络
     */
    private void initNetSpecifier(MyNetCallback callback) {
        NetSpecifier req = new NetSpecifier.Builder()
                .addCapability(NetCapabilities.NET_CAPABILITY_MMS)
                .addBearer(NetCapabilities.BEARER_CELLULAR)
                .build();

        // 建立数据网络，通过callback获取网络变更状态
        myNetCallback.getNetManager().setupSpecificNet(req, callback);
    }


    /**
     * 网络访问成功回调
     * @param connection 当前连接
     * @throws Exception 异常
     */
    @Override
    public void onResponseOk(HttpsURLConnection connection) throws Exception {
        ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
        ImageSource imageSource = ImageSource.create(connection.getInputStream(), srcOpts);
        PixelMap pixelmap = imageSource.createPixelmap(null);
        getUITaskDispatcher().syncDispatch(() -> image.setPixelMap(pixelmap));
    }

    /**
     * 网络失败回调
     */
    @Override
    public void onError() {

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
