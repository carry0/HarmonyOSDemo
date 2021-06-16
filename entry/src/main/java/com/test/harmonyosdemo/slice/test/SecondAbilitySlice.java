package com.test.harmonyosdemo.slice.test;

import com.test.harmonyosdemo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.window.dialog.ToastDialog;

public class SecondAbilitySlice extends AbilitySlice {
    private Button butBack;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_second);
        initView();
    }

    private void initView() {
        butBack = (Button) findComponentById(ResourceTable.Id_but_back);
        butBack.setClickedListener(component -> onBackPressed());
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

}
