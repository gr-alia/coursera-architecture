package com.elegion.test.behancer.ui.projects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    private ProjectsView view;
    private Storage storage;

    public ProjectsPresenter(ProjectsView view, Storage storage) {
        this.view = view;
        this.storage = storage;
    }

    public void getProjects() {

        disposable.add(ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .doOnSuccess(response -> storage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? storage.getProjects() : null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showLoading())
                .doFinally(() -> view.hideLoading())
                .subscribe(
                        response -> view.showProjects(response.getProjects()),
                        throwable -> view.showError()));

    }

    public void openProfileFragment(String username) {
        view.openProfileFragment(username);
    }
}
