package com.elegion.test.behancer.ui.userprojects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UserProjectsPresenter extends BasePresenter<UserProjectsView> {

    private UserProjectsView view;
    private Storage storage;

    public UserProjectsPresenter(UserProjectsView view, Storage storage) {
        this.view = view;
        this.storage = storage;
    }

    public void getUserProjects(String username) {

        disposable.add(ApiUtils.getApiService().getProjectsByUser(username)
                .doOnSuccess(response -> {
                    //storage.insertProjects(response)
                })
                .onErrorReturn(throwable -> {
                    // ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? storage.getProjects() : null
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showLoading())
                .doFinally(() -> view.hideLoading())
                .subscribe(response -> view.showUserProjects(response.getProjects()),
                        throwable -> view.showError())

        );
    }
}
