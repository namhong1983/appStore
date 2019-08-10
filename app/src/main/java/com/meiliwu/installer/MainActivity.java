package com.meiliwu.installer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.meiliwu.installer.adapter.CommonViewHolder;
import com.meiliwu.installer.adapter.CustomRecyclerAdapter;
import com.meiliwu.installer.entity.APKEntity;
import com.meiliwu.installer.entity.BuildType;
import com.meiliwu.installer.entity.ISelectable;
import com.meiliwu.installer.entity.PackageEntity;
import com.meiliwu.installer.mvp.MvpContract;
import com.meiliwu.installer.mvp.MyPresenter;
import com.meiliwu.installer.rx.ResponseErrorListener;
import com.meiliwu.installer.rx.RxErrorHandler;
import com.meiliwu.installer.service.DownloadService;
import com.meiliwu.installer.utils.EndlessRecyclerOnScrollListener;
import com.meiliwu.installer.utils.NetWorkUtil;
import com.meiliwu.installer.view.CustomBottomSheetDialog;
import com.meiliwu.installer.view.FilterTabItemView;
import com.meiliwu.installer.view.StatusLayout;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements MvpContract.IView, SwipeRefreshLayout.OnRefreshListener, ResponseErrorListener, CustomBottomSheetDialog.OnItemClickListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.filter_buildType)
    FilterTabItemView filterBuildType;
    @BindView(R.id.filter_packageName)
    FilterTabItemView filterPackageName;

    private CustomRecyclerAdapter<APKEntity> adapter;
    private final List<APKEntity> mApplicationList = new ArrayList<>();
    private final List<PackageEntity> mPkgList = new ArrayList<>();
    private final ArrayList<BuildType> mBuildTypeList = new ArrayList<>();

    private MyPresenter presenter;
    private String selectedVersionType;
    private String selectedApplicationID;
    private static final String defaultSystemType = "android";
    public static String DOWNLOAD_PATH;
    private MyReceiver receiver;
    private int pageIndex = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static int dataListSize;
    private CustomBottomSheetDialog packageBottomSheetDialog;
    private CustomBottomSheetDialog buildTypeBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MyPresenter(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        initData();
        DOWNLOAD_PATH = Environment.DIRECTORY_DOWNLOADS + "/installer_app";
        requestPermissions();
        registerReceiver();
        initAdapter();

        //请求数据
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.secodePrimaryColor), getResources().getColor(R.color.primaryColor));

        presenter.getApplicationList();
        presenter.getPackageList(defaultSystemType, selectedApplicationID, selectedVersionType, pageIndex);

        filterBuildType.setTitle("正式/测试");
        filterPackageName.setTitle("应用");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        presenter.onDestroy();
    }

    private void requestPermissions() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (!granted) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("提示")
                                    .setMessage("请务必给予存储权限，以便您的使用")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions();
                                        }
                                    });
                            builder.create().show();
                        }
                    }
                });
    }

    private void initData() {
        mBuildTypeList.add(new BuildType("全部"));
        mBuildTypeList.add(new BuildType("正式"));
        mBuildTypeList.add(new BuildType("测试"));
    }

    private void initAdapter() {
        adapter = new CustomRecyclerAdapter<APKEntity>(mApplicationList) {
            @Override
            public void convert(CommonViewHolder holder, final APKEntity apkEntity, int position) {
                Glide.with(holder.itemView.getContext())
                        .load(apkEntity.getIcon_url())
                        .into(((ImageView) holder.getView(R.id.img_icon)));
                holder.setText(R.id.tv_packageName, apkEntity.getApplication_name() + "(" + apkEntity.getVersion_name() + ")");
                holder.setText(R.id.tv_timeStamp, apkEntity.getCreate_time());
                if ((!TextUtils.isEmpty(apkEntity.getVersion_type())) && apkEntity.getVersion_type().equals("测试")) {
                    holder.setVisible(R.id.tv_isDebugVersion, View.VISIBLE);
                    holder.setText(R.id.tv_isDebugVersion, apkEntity.getVersion_type());
                } else {
                    holder.setVisible(R.id.tv_isDebugVersion, View.INVISIBLE);
                }
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(MainActivity.this, apkEntity.getApplication_name() + apkEntity.getVersion_name() + apkEntity.getVersion_type() + "正在下载，请稍后.....", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        String[] split = apkEntity.getDownload_url().split("/");
                        List<String> strings = Arrays.asList(split);
                        strings.get(strings.size() - 1);
                        downLoadAPK(apkEntity.getDownload_url(), strings.get(strings.size() - 1));
//                        downLoadAPKNew(apkEntity.getDownload_url(), strings.get(strings.size() - 1));
                    }
                }, R.id.btn_downLoad);
            }

            @Override
            public int getItemLayoutID() {
                return R.layout.layout_recyclerview_package_item;
            }

            @Override
            public int getFootViewLayoutID() {
                return R.layout.layout_footer_view;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return swipeRefreshLayout.isRefreshing();
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (mApplicationList.size() < dataListSize) {
                    adapter.setLoadState(CustomRecyclerAdapter.LOADING);
                    presenter.getPackageList(defaultSystemType, selectedApplicationID, selectedVersionType, pageIndex);
                }

            }

            @Override
            public void setFlag(boolean flag) {
                if (mApplicationList.size() < dataListSize) {
                    EndlessRecyclerOnScrollListener.flag = false;
                }
            }
        });
    }

    @Override
    public void onLoadApplicationListSuccess(List<PackageEntity> dataList) {
        swipeRefreshLayout.setRefreshing(false);
        mPkgList.clear();
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setApplication_name("全部");
        mPkgList.add(0, packageEntity);
        mPkgList.addAll(dataList);
    }

    @Override
    public void onLoadApplicationListFailed() {
        packageBottomSheetDialog.showProgressBar(false);
    }

    @Override
    public void onLoadPackageListSuccess(List<APKEntity> dataSource) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setLoadState(CustomRecyclerAdapter.LOADING_COMPLETE);

        if (dataSource != null) {
            if (dataSource.size() < DEFAULT_PAGE_SIZE) {
                adapter.setLoadState(CustomRecyclerAdapter.LOADING_END);
            } else {
                pageIndex++;
            }

            mApplicationList.addAll(dataSource);
            adapter.notifyItemRangeInserted(mApplicationList.size(), dataSource.size());
        }
    }

    @Override
    public void onLoadPackageListFailed() {
        adapter.setLoadState(CustomRecyclerAdapter.LOADING_COMPLETE);
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPackageList(defaultSystemType, selectedApplicationID, selectedVersionType, pageIndex);
            }
        });
    }

    @Override
    public void notifyDataSize(int count) {
        dataListSize = count;
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.showErrorView();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusLayout.showContentView();
                swipeRefreshLayout.setRefreshing(true);
                presenter.getPackageList(defaultSystemType, selectedApplicationID, selectedVersionType, pageIndex);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void checkNetWork() {
        if (!NetWorkUtil.isNetConnected()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("alert")
                    .setMessage("当前网络不可用，请检查网络设置")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_SETTINGS);
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        /*清空已加载的apk数据*/
        mApplicationList.clear();
        adapter.notifyItemMoved(0, mApplicationList.size());
        pageIndex = 1;
        presenter.getPackageList(defaultSystemType, selectedApplicationID, selectedVersionType, pageIndex);
    }

    @OnClick({R.id.filter_buildType, R.id.filter_packageName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.filter_buildType:
                showBottomSheetDialog(mBuildTypeList);
                break;
            case R.id.filter_packageName:
                showBottomSheetDialog(mPkgList);
                break;
            default:
                break;
        }
    }

    /*正式/测试筛选*/
    private void showBottomSheetDialog(final ArrayList<BuildType> buildTypes) {
        if (buildTypeBottomSheetDialog == null) {
            buildTypeBottomSheetDialog = new CustomBottomSheetDialog(this);
        }
        buildTypeBottomSheetDialog.setDataList(buildTypes);
        buildTypeBottomSheetDialog.show();
        buildTypeBottomSheetDialog.setOnItemClickListener(new CustomBottomSheetDialog.OnItemClickListener() {
            @Override
            public void ontItemClick(View view, ISelectable packageEntity, int position) {
                mApplicationList.clear();
                if (position == 0) {
                    selectedVersionType = null;
                    filterBuildType.setHighlight(false);
                } else {
                    selectedVersionType = packageEntity.getName();
                    filterBuildType.setHighlight(true);
                }
                filterBuildType.setTitle(packageEntity.getName());
                doFilter(selectedApplicationID, selectedVersionType);
                buildTypeBottomSheetDialog.dismiss();
            }
        });
    }

    /*APK筛选*/
    private void        showBottomSheetDialog(final List<PackageEntity> dataSource) {
        if (packageBottomSheetDialog == null) {
            packageBottomSheetDialog = new CustomBottomSheetDialog(this);
        }
        packageBottomSheetDialog.setDataList(dataSource);
        packageBottomSheetDialog.show();
        packageBottomSheetDialog.setOnItemClickListener(this);
        if (mPkgList.size() == 0) {
            packageBottomSheetDialog.showProgressBar(true);
            presenter.getApplicationList();
        }
    }

    private void doFilter(String application_id, String version_type) {
        swipeRefreshLayout.setRefreshing(true);
        pageIndex = 1;
        presenter.getPackageList(MainActivity.defaultSystemType, application_id, version_type, pageIndex);
    }

    private void registerReceiver() {
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

    }

    private void downLoadAPK(String url, String fileName) {
        Intent serviceIntent = new Intent(this, DownloadService.class);
        serviceIntent.setData(Uri.parse(url));
        serviceIntent.putExtra(DownloadService.FILE_NAME, fileName);
        startService(serviceIntent);
    }

    private void downLoadAPKNew(String url, final String fileName) {
        FileDownloader.getImpl().create(url)
                .setPath(Environment.getExternalStoragePublicDirectory(DOWNLOAD_PATH).getPath(), true)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "pending: ");

                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "connected: ");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "progress: ");
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.i(TAG, "blockComplete: ");
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                        Log.i(TAG, "retry: ");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.i(TAG, "completed:getFilename " + task.getFilename());
                        installAPK(task.getFilename());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i(TAG, "paused: ");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.i(TAG, "error: ");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();


    }

    @Override
    public void handlerResponseError(Context context, Exception e) {
        adapter.setLoadState(CustomRecyclerAdapter.LOADING_COMPLETE);
        showErrorView();
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ontItemClick(View view, ISelectable entity, int position) {
        mApplicationList.clear();
        if (entity instanceof PackageEntity) {
            if (position == 0) {
                selectedApplicationID = null;
                filterPackageName.setHighlight(false);
            } else {
                selectedApplicationID = entity.getID();
                filterPackageName.setHighlight(true);
            }
            filterPackageName.setTitle(entity.getName());
            doFilter(selectedApplicationID, selectedVersionType);
        }
        packageBottomSheetDialog.dismiss();
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fileName = intent.getStringExtra(DownloadService.FILE_NAME);
            installAPK(fileName);
        }

    }

    private void installAPK(String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(Environment.getExternalStoragePublicDirectory(DOWNLOAD_PATH), fileName);
        Uri uri;
        //在Android7.0(Android N)及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
