package com.meiliwu.installer.entity;

/**
 * Author:  ZhangTao
 * Date: 2018/3/13.
 */

public class BuildType implements ISelectable {


    private String buildType;


    public BuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    @Override
    public String getID() {
        return "0";
    }

    @Override
    public String getName() {
        return buildType;
    }
}
