package com.test.harmonyosdemo.adapter;

import com.test.harmonyosdemo.slice.ViewCreateHelper;

import ohos.agp.components.PageSlider;

/**
 * SliderListener
 */
public class SliderListener implements PageSlider.PageChangedListener {
    ViewCreateHelper viewCreateHelper;

    /**
     * SliderListener
     *
     * @param viewCreateHelper helper tool class
     */
    public SliderListener(ViewCreateHelper viewCreateHelper) {
        this.viewCreateHelper = viewCreateHelper;
    }

    @Override
    public void onPageSliding(int i, float v, int i1) {
    }

    @Override
    public void onPageSlideStateChanged(int i) {
    }

    @Override
    public void onPageChosen(int position) {
    }
}
