package com.elegion.test.behancer.common;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {

    protected CompositeDisposable disposable = new CompositeDisposable();

    public void disposeAll(){
        disposable.clear();
    }
}
