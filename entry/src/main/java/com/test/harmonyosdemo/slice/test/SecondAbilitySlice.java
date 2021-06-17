package com.test.harmonyosdemo.slice.test;

import com.test.harmonyosdemo.ResourceTable;
import com.test.harmonyosdemo.handler.TestHandler;
import com.test.harmonyosdemo.slice.test.adapter.SecondAdapter;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventQueue;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class SecondAbilitySlice extends AbilitySlice {
    private Button butAdd, butLose, butStart;
    public Text tvTime;
    private SecondAdapter adapter;
    private ListContainer lisView;
    private static final int EVENT_MESSAGE_NORMAL = 1;
    private static final int EVENT_MESSAGE_DELAY = 2;
    public EventRunner eventRunner;
    private TestHandler testHandler;


    private final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "TEST_time");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_second);
        initHandler();
        initView();
        initListener();
    }

    private void initHandler() {
        eventRunner = EventRunner.create("TestRunner");
        testHandler = new TestHandler(eventRunner);
    }

    private void initView() {
        butAdd = (Button) findComponentById(ResourceTable.Id_but_add);
        butLose = (Button) findComponentById(ResourceTable.Id_but_lose);
        butStart = (Button) findComponentById(ResourceTable.Id_but_start);
        tvTime = (Text) findComponentById(ResourceTable.Id_tv_time);
        lisView = (ListContainer) findComponentById(ResourceTable.Id_list);
        List<String> list = new ArrayList<>();
        int i = 0, k = 5;
        while (i < k) {
            list.add("测试数据" + i);
            i++;
        }
        adapter = new SecondAdapter(this);
        adapter.setList(list);
        lisView.setItemProvider(adapter);

    }


    class TestHandler extends EventHandler {
        private int i = 10;

        public TestHandler(EventRunner runner) throws IllegalArgumentException {
            super(runner);
        }

        // 重写实现processEvent方法
        @Override
        protected void processEvent(InnerEvent event) {
            super.processEvent(event);
            if (event == null) {
                return;
            }
            int eventId = event.eventId;
            switch (eventId) {
                case EVENT_MESSAGE_NORMAL:
                    if (i == 0) {
                        getUITaskDispatcher().syncDispatch(() -> butStart.setText(ResourceTable.String_but_start));
                        removeEvent(eventId);
                        return;
                    } else {
                        i--;
                        sendEvent(EVENT_MESSAGE_NORMAL, 1000);
                        getUITaskDispatcher().syncDispatch(() -> butStart.setText(ResourceTable.String_but_now_time));
                        getUITaskDispatcher().syncDispatch(() -> tvTime.setText(String.valueOf(i)));
                    }
                    HiLog.info(LABEL, "" + i);
                    break;
                case EVENT_MESSAGE_DELAY:
                    // 待执行的操作，由开发者定义
                    break;
                default:
                    break;
            }
        }
    }

    private void initListener() {
        butAdd.setClickedListener(component -> adapter.addData());
        butLose.setClickedListener(component -> adapter.loseData());
        butStart.setClickedListener(component ->
                //发送一个延时(1秒)事件到事件队列
                testHandler.sendEvent(EVENT_MESSAGE_NORMAL, 1000));
        //设置响应点击事件
        lisView.setItemClickedListener((listContainer, component, i, l) -> {
            String item = (String) listContainer.getItemProvider().getItem(i);
            new ToastDialog(getContext()).setText(item).setAlignment(LayoutAlignment.TOP).show();
        });
    }

}
