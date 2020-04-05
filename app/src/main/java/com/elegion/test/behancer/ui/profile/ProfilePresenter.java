package com.elegion.test.behancer.ui.profile;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private ProfileView view;
    private Storage storage;

    public ProfilePresenter(ProfileView view, Storage storage) {
        this.view = view;
        this.storage = storage;
    }

    public void getProfile(String username) {
        disposable.add(ApiUtils.getApiService().getUserInfo(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> storage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                storage.getUser(username) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showLoading())
                .doFinally(() -> view.hideLoading())
                .subscribe(
                        response -> view.showProfile(response.getUser()),
                        throwable -> view.showError()));
    }

    public void openUserProjectsFragment(String username) {
        view.openUserProjectsFragment(username);
    }

}
