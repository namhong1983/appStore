package com.meiliwu.installer.entity;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Author:  ZhangTao
 * Date: 2018/3/6.
 */

public class APKEntity {
    /**
     * id : 464
     * version_name : 4.9.3
     * application_id : 1
     * application_name : 美丽屋经纪人
     * system_name : android
     * version_describe : null
     * download_url : http://cdn.mse.mlwplus.com/meiliwu/applications/2018/02/02/18/37/mlw_release_V4.9.3_2018_02_02_18.apk
     * plist_url :
     * version_type : 正式
     * if_deleted : 0
     * status : 1
     * create_time : 1517550821
     * update_time : 1517567888
     * update_type : 1
     * uid : 50200d0ba8207c4b80a17744
     * icon_url : http://cdn.mse.mlwplus.com/meiliwu/applications/2018/03/05/18/23/jingjiren.png
     */

    private String id;
    private String version_name;
    private String application_id;
    private String application_name;
    private String system_name;
    private Object version_describe;
    private String download_url;
    private String plist_url;
    private String version_type;
    private String if_deleted;
    private String status;
    private String create_time;
    private String update_time;
    private String update_type;
    private String uid;
    private String icon_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getSystem_name() {
        return system_name;
    }

    public void setSystem_name(String system_name) {
        this.system_name = system_name;
    }

    public Object getVersion_describe() {
        return version_describe;
    }

    public void setVersion_describe(Object version_describe) {
        this.version_describe = version_describe;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getPlist_url() {
        return plist_url;
    }

    public void setPlist_url(String plist_url) {
        this.plist_url = plist_url;
    }

    public String getVersion_type() {
        return version_type;
    }

    public void setVersion_type(String version_type) {
        this.version_type = version_type;
    }

    public String getIf_deleted() {
        return if_deleted;
    }

    public void setIf_deleted(String if_deleted) {
        this.if_deleted = if_deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String format = simpleDateFormat.format(Long.valueOf(create_time) * 1000);
        return format;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(String update_type) {
        this.update_type = update_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }



}
