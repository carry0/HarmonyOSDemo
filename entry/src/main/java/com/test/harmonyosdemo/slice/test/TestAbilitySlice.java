package com.test.harmonyosdemo.slice.test;

import com.test.harmonyosdemo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class TestAbilitySlice extends AbilitySlice {
    private TextField textField;
    private Button butConfirm;
    private final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");

    /**
     * 相当于onCreate()
     *
     * @param intent 当前intent
     */
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_test_ability);
        initView();
        initListener();
        HiLog.info(LABEL, "onStart");
    }

    /**
     * 相当于onResume()
     */
    @Override
    protected void onActive() {
        super.onActive();
        HiLog.info(LABEL, "onActive");
    }

    private void initView() {
        textField = (TextField) findComponentById(ResourceTable.Id_tf_context);
        butConfirm = (Button) findComponentById(ResourceTable.Id_but_confirm);
    }

    private void initListener() {
        butConfirm.setClickedListener(component -> {
            if (textField.getText().length() == 0) {
                new ToastDialog(TestAbilitySlice.this).setText("请输入你要输入的内容").show();
            } else {
                //带回调参数
                presentForResult(new SecondAbilitySlice(), new Intent(), 0);
//                正常跳转
//                present(new SecondAbilitySlice(), new Intent());
            }
        });

    }

    /**
     * 相当于onPause()
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        HiLog.info(LABEL, "onInactive");
    }

    /**
     * 相当于onDestroyView()
     */
    @Override
    protected void onBackground() {
        super.onBackground();
        HiLog.info(LABEL, "onBackground");
    }

    /**
     * 相当于onActivityCreated()
     *
     * @param intent 回调的
     */
    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        HiLog.info(LABEL, "onForeground");
    }

    /**
     * 相当于onDestroy()
     */
    @Override
    protected void onStop() {
        super.onStop();
        HiLog.info(LABEL, "onStop");
    }

    /**
     * 相当于onActivityResult()
     *
     * @param requestCode  intent参数
     * @param resultIntent intent参数
     */
    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        super.onResult(requestCode, resultIntent);
        if (requestCode == 0) {
            new ToastDialog(this).setText("测试").show();
        }
    }
}
