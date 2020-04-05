package com.elegion.test.behancer.common;

import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class BaseFragment extends MvpAppCompatFragment {

    protected abstract BasePresenter getPresenter();

    @Override
    public void onDetach() {
        super.onDetach();
        if (getPresenter() == null) {
            return;
        }

        getPresenter().disposeAll();
    }
}
