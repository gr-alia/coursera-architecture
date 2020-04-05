package com.elegion.test.behancer.common;

import android.support.v4.app.Fragment;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected abstract P getPresenter();

    @Override
    public void onDetach() {
        super.onDetach();
        if (getPresenter() == null){
            return;
        }

        getPresenter().disposeAll();
    }
}
