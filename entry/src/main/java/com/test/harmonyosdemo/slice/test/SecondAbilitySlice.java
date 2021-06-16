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
    private Button butAdd, butLose;
    public Text tvTime;
    private SecondAdapter adapter;
    private ListContainer lisView;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_second);
        initView();
        initListener();
    }

    private void initView() {
        butAdd = (Button) findComponentById(ResourceTable.Id_but_add);
        butLose = (Button) findComponentById(ResourceTable.Id_but_lose);
        tvTime = (Text) findComponentById(ResourceTable.Id_tv_time);
        lisView = (ListContainer) findComponentById(ResourceTable.Id_list);
        List<String> list = new ArrayList<>();
        int j = 0, k = 5;
        while (j < k) {
            list.add("测试数据" + j);
            j++;
        }
        adapter = new SecondAdapter(this);
        adapter.setList(list);
        lisView.setItemProvider(adapter);
    }

    private void initListener() {
        butAdd.setClickedListener(component -> adapter.addData());
        butLose.setClickedListener(component -> adapter.loseData());
        //设置响应点击事件
        lisView.setItemClickedListener((listContainer, component, i, l) -> {
            String item = (String) listContainer.getItemProvider().getItem(i);
            new ToastDialog(getContext()).setText(item).setAlignment(LayoutAlignment.CENTER).show();
        });
    }

}
