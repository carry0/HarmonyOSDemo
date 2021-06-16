package com.test.harmonyosdemo.ability;

import com.test.harmonyosdemo.slice.test.NetWorkAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class NetWorkAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(NetWorkAbilitySlice.class.getName());
    }
}
