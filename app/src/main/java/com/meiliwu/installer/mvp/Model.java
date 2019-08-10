package com.meiliwu.installer.mvp;

import com.meiliwu.installer.entity.APKEntity;
import com.meiliwu.installer.entity.PackageEntity;
import com.meiliwu.installer.entity.Result;
import com.meiliwu.installer.utils.ApiServiceUtil;
import com.meiliwu.installer.utils.RxsRxSchedulers;

import java.util.List;

import rx.Observable;


/**
 * Author Tao.ZT.Zhang
 * Date   2017/10/30
 */

public class Model implements MvpContract.IModel {
    @Override
    public  Observable<Result<List<PackageEntity>>> getApplicationList() {
        return ApiServiceUtil.getApplicationList().compose(RxsRxSchedulers.<Result<List<PackageEntity>>>io_main());
    }

    @Override
    public Observable<Result<List<APKEntity>>> getPackageList(String system_name, String application_id, String version_type, int pageIndex) {
        return ApiServiceUtil.getPackageList(system_name, application_id, version_type, pageIndex).compose(RxsRxSchedulers.<Result<List<APKEntity>>>io_main());
    }

}
