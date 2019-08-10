package com.meiliwu.installer.mvp;

import com.meiliwu.installer.entity.APKEntity;
import com.meiliwu.installer.entity.PackageEntity;
import com.meiliwu.installer.entity.Result;
import com.meiliwu.installer.rx.RxErrorHandlerSubscriber;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author Tao.ZT.Zhang
 * Date   2017/10/30
 */

public class MyPresenter {
    private MvpContract.IView view;
    private MvpContract.IModel model;
    private CompositeSubscription compositeSubscription;

    public MyPresenter(MvpContract.IView iView) {
        view = iView;
        model = new Model();
        compositeSubscription = new CompositeSubscription();
    }

    public void getApplicationList() {
        Observable<Result<List<PackageEntity>>> observable = model.getApplicationList();
        Subscription subscribe = observable.subscribe(new RxErrorHandlerSubscriber<List<PackageEntity>>() {
            @Override
            public void onStart() {
                super.onStart();
                view.checkNetWork();
            }

            @Override
            public void onSuccess(List<PackageEntity> data) {
                if (data.size() > 0) {
                    view.onLoadApplicationListSuccess(data);
                }
            }

            @Override
            public void onError(String message) {
                view.onLoadApplicationListFailed();
                view.onError(message);
            }
        });

        compositeSubscription.add(subscribe);
    }

    public void getPackageList(String system_name, String application_id, String version_type, int pageIndex) {
        Observable<Result<List<APKEntity>>> specifiedAPKVersionList = model.getPackageList(system_name, application_id, version_type, pageIndex);
        RxErrorHandlerSubscriber<List<APKEntity>> rxErrorHandlerSubscriber = new RxErrorHandlerSubscriber<List<APKEntity>>() {
            @Override
            public void onSuccess(List<APKEntity> data) {
                if (data.size() > 0) {
                    view.showContentView();
                    view.notifyDataSize(data.size());
                    view.onLoadPackageListSuccess(data);
                } else {
                    view.showEmptyView();
                }
            }

            @Override
            public void onError(String message) {
                view.showErrorView();
                view.onError(message);
            }
        };

        Subscription subscribe = specifiedAPKVersionList.subscribe(rxErrorHandlerSubscriber);

        compositeSubscription.add(subscribe);

    }

    public void onDestroy() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

}
