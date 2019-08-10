package com.meiliwu.installer.service;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

import com.meiliwu.installer.MainActivity;

/**
 * Author:  ZhangTao
 * Date: 2018/3/7.
 */

public class DownloadService extends IntentService {
    public static final String BROADCAST_ACTION =
            "com.meiliwu.installer.service.BROADCAST";
    public static final String EXTENDED_DATA_STATUS =
            "com.meiliwu.installer.service.STATUS";
    public static final String FILE_NAME = "fileName";

    private LocalBroadcastManager mLocalBroadcastManager;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //获取下载地址
        String url = intent.getDataString();
        String fileName = intent.getStringExtra(FILE_NAME);
        //获取DownloadManager对象
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //指定APK缓存路径和应用名称，可在SD卡/storage/sdcard0/Download文件夹中查看
        request.setDestinationInExternalPublicDir(MainActivity.DOWNLOAD_PATH , fileName);
        //设置网络下载环境为wifi和Mobile环境
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //设置下载文件的mineType。因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，所以我们可以利用这个属性。
        request.setMimeType("application/vnd.android.package-archive");
        //设置显示通知栏，下载完成后通知栏自动消失
        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //设置通知栏标题
        request.setTitle(fileName);
        request.setDescription("应用正在下载");
        //设置是否允许漫游连接
        request.setAllowedOverRoaming(false);
        //获得唯一下载id
        long requestId = downloadManager.enqueue(request);
        //将id放进Intent
        Intent localIntent = new Intent(BROADCAST_ACTION);
        localIntent.putExtra(DownloadManager.EXTRA_DOWNLOAD_ID, requestId);
        localIntent.putExtra(FILE_NAME, fileName);
        //查询下载信息
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(requestId);
        try {
            boolean isGoing = true;
            while (isGoing) {
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        case DownloadManager.STATUS_SUCCESSFUL:
                            isGoing = false;
                            //调用LocalBroadcastManager.sendBroadcast将intent传递回去
                            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
                            mLocalBroadcastManager.sendBroadcast(localIntent);
                            break;

                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
