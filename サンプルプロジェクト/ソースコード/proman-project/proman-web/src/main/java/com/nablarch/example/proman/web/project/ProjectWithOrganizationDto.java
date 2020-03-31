package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.entity.Project;

/**
 * プロジェクト検索結果DTO
 */
public class ProjectWithOrganizationDto extends Project {
    /** 事業部名 */
    private String divisionName;
    /** 部門名 */
    private String organizationName;

    /**
     * 事業部名 を取得する。
     *
     * @return 事業部名
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * 事業部名 を設定する
     *
     * @param divisionName 事業部名
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * 部門名 を取得する。
     *
     * @return 部門名
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * 部門名 を設定する
     *
     * @param organizationName 部門名
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
