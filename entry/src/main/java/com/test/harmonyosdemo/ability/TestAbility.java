package com.test.harmonyosdemo.ability;

import com.test.harmonyosdemo.slice.test.TestAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class TestAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(TestAbilitySlice.class.getName());
    }
}
