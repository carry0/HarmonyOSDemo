package com.test.harmonyosdemo.handler;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

public class TestHandler extends EventHandler {
    private static final int EVENT_MESSAGE_NORMAL = 1;
    private static final int EVENT_MESSAGE_DELAY = 2;
    public TestHandler(EventRunner runner) throws IllegalArgumentException {
        super(runner);
    }
    // 重写实现processEvent方法
    @Override
    protected void processEvent(InnerEvent event) {
        super.processEvent(event);
        if (event==null){
            return;
        }
        int eventId = event.eventId;
        switch (eventId){
            case EVENT_MESSAGE_NORMAL:
                // 待执行的操作，由开发者定义
                break;
            case EVENT_MESSAGE_DELAY:
                // 待执行的操作，由开发者定义
                break;
            default:
                break;
        }
    }

    private void setInnerEvent(){
        long param= 0L;
        Object object = null;
        InnerEvent normalInnerEvent = InnerEvent.get(EVENT_MESSAGE_NORMAL, param, object);
        InnerEvent delayInnerEvent = InnerEvent.get(EVENT_MESSAGE_DELAY, param, object);
    }
}
