package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.util.DateRelationUtil;
import com.nablarch.example.proman.common.util.MoneyRelationUtil;
import com.nablarch.example.proman.web.common.SearchFormBase;
import nablarch.core.util.StringUtil;
import nablarch.core.validation.ee.Domain;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * プロジェクト検索フォーム
 */
public class ProjectSearchForm extends SearchFormBase implements Serializable {
    /** 事業部 */
    @Domain("organizationId")
    private String divisionId;
    /** 部門 */
    @Domain("organizationId")
    private String organizationId;
    /** プロジェクト種別 */
    @Valid
    private List<ProjectType> projectType;
    /** プロジェクト分類 */
    @Valid
    private List<ProjectClass> projectClass;
    /** 売上高_実績_FROM */
    @Domain("amountOfMoney")
    private String salesFrom;
    /** 売上高_実績_TO */
    @Domain("amountOfMoney")
    private String salesTo;
    /** 開始日_FROM */
    @Domain("date")
    private String projectStartDateFrom;
    /** 開始日_TO */
    @Domain("date")
    private String projectStartDateTo;
    /** 終了日_FROM */
    @Domain("date")
    private String projectEndDateFrom;
    /** 終了日_TO */
    @Domain("date")
    private String projectEndDateTo;
    /** プロジェクト名 */
    @Domain("projectName")
    private String projectName;

    /**
     * 事業部 を取得する。
     *
     * @return 事業部
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * 事業部 を設定する
     *
     * @param divisionId 事業部
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * 部門 を取得する。
     *
     * @return 部門
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 部門 を設定する
     *
     * @param organizationId 部門
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * プロジェクト種別 を取得する。
     *
     * @return プロジェクト種別
     */
    public String[] getProjectType() {
        if (projectType == null) {
            return null;
        }
        return projectType.stream()
                .map(ProjectType::getProjectType)
                .toArray(String[]::new);
    }

    /**
     * プロジェクト種別 を設定する。
     *
     * @param projectType プロジェクト種別
     */
    public void setProjectType(String[] projectType) {
        if (projectType == null) {
            this.projectType = null;
            return;
        }
        final List<ProjectType> projectTypeList = Arrays.stream(projectType)
                .filter(StringUtil::hasValue)
                .map(p -> {
                    ProjectType bean = new ProjectType();
                    bean.setProjectType(p);
                    return bean;
                })
                .collect(Collectors.toList());
        this.projectType = projectTypeList.isEmpty() ? null : projectTypeList;
    }

    /**
     * プロジェクト分類 を取得する。
     *
     * @return プロジェクト分類
     */
    public String[] getProjectClass() {

        if (projectClass == null) {
            return null;
        }
        return projectClass.stream()
                .map(ProjectClass::getProjectClass)
                .toArray(String[]::new);
    }

    /**
     * プロジェクト分類 を設定する。
     *
     * @param projectClass プロジェクト分類
     */
    public void setProjectClass(String[] projectClass) {
        if (projectClass == null) {
            this.projectClass = null;
            return;
        }
        final List<ProjectClass> projectClassList = Arrays.stream(projectClass)
                .filter(StringUtil::hasValue)
                .map(p -> {
                    ProjectClass bean = new ProjectClass();
                    bean.setProjectClass(p);
                    return bean;
                })
                .collect(Collectors.toList());
        this.projectClass = projectClassList.isEmpty() ? null : projectClassList;
    }

    /**
     * 売上高_実績_FROM を取得する。
     *
     * @return 売上高_実績_FROM
     */
    public String getSalesFrom() {
        return salesFrom;
    }

    /**
     * 売上高_実績_FROM を設定する
     *
     * @param salesFrom 売上高_実績_FROM
     */
    public void setSalesFrom(String salesFrom) {
        this.salesFrom = salesFrom;
    }

    /**
     * 売上高_実績_TO を取得する。
     *
     * @return 売上高_実績_TO
     */
    public String getSalesTo() {
        return salesTo;
    }

    /**
     * 売上高_実績_TO を設定する
     *
     * @param salesTo 売上高_実績_TO
     */
    public void setSalesTo(String salesTo) {
        this.salesTo = salesTo;
    }

    /**
     * 開始日_FROM を取得する。
     *
     * @return 開始日_FROM
     */
    public String getProjectStartDateFrom() {
        return projectStartDateFrom;
    }

    /**
     * 開始日_FROM を設定する
     *
     * @param projectStartDateFrom 開始日_FROM
     */
    public void setProjectStartDateFrom(String projectStartDateFrom) {
        this.projectStartDateFrom = projectStartDateFrom;
    }

    /**
     * 開始日_TO を取得する。
     *
     * @return 開始日_TO
     */
    public String getProjectStartDateTo() {
        return projectStartDateTo;
    }

    /**
     * 開始日_TO を設定する
     *
     * @param projectStartDateTo 開始日_TO
     */
    public void setProjectStartDateTo(String projectStartDateTo) {
        this.projectStartDateTo = projectStartDateTo;
    }

    /**
     * 終了日_FROM を取得する。
     *
     * @return 終了日_FROM
     */
    public String getProjectEndDateFrom() {
        return projectEndDateFrom;
    }

    /**
     * 終了日_FROM を設定する
     *
     * @param projectEndDateFrom 終了日_FROM
     */
    public void setProjectEndDateFrom(String projectEndDateFrom) {
        this.projectEndDateFrom = projectEndDateFrom;
    }

    /**
     * 終了日_TO を取得する。
     *
     * @return 終了日_TO
     */
    public String getProjectEndDateTo() {
        return projectEndDateTo;
    }

    /**
     * 終了日_TO を設定する
     *
     * @param projectEndDateTo 終了日_TO
     */
    public void setProjectEndDateTo(String projectEndDateTo) {
        this.projectEndDateTo = projectEndDateTo;
    }

    /**
     * プロジェクト名 を取得する。
     *
     * @return プロジェクト名
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * プロジェクト名 を設定する
     *
     * @param projectName プロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 売上高_実績のFROM/TOが正しく設定されているかを判定します。
     * FROMにTOより大きい金額が設定されていた場合は正しくないと判定します。
     * それ以外の場合は問題なしとします。
     *
     * @return FROMにTOより大きい金額が設定されていた場合は false それ以外（FROM、TOの両方又はいずれかが未定の場合も含む）は true
     */
    @AssertTrue(message = "{validator.priceRange.message}")
    public boolean isValidProjectSalesRange() {
        return MoneyRelationUtil.isValid(salesFrom, salesTo);
    }

    /**
     * プロジェクト開始日のFROM/TOが正しく設定されているかを判定します。
     * FROMにTOより後の日付が設定されていた場合は正しくないと判定します。
     * それ以外の場合は問題なしとします。
     *
     * @return FROMにTOより後の日付が設定されていた場合は false それ以外（FROM、TOの両方又はいずれかが未定の場合も含む）は true
     */
    @AssertTrue(message = "{validator.dateRange.message}")
    public boolean isValidProjectStartDateRange() {
        return DateRelationUtil.isValid(projectStartDateFrom, projectStartDateTo);
    }

    /**
     * プロジェクト終了日のFROM/TOが正しく設定されているかを判定します。
     * FROMにTOより後の日付が設定されていた場合は正しくないと判定します。
     * それ以外の場合は問題なしとします。
     *
     * @return FROMにTOより後の日付が設定されていた場合は false それ以外（FROM、TOの両方又はいずれかが未定の場合も含む）は true
     */

    @AssertTrue(message = "{validator.dateRange.message}")
    public boolean isValidProjectEndDateRange() {
        return DateRelationUtil.isValid(projectEndDateFrom, projectEndDateTo);
    }

    /**
     * プロジェクト種別Bean
     */
    private static class ProjectType implements Serializable {
        /**
         * プロジェクト種別
         */
        @Domain("projectType")
        private String projectType;

        /**
         * プロジェクト種別 を取得する。
         *
         * @return プロジェクト種別
         */
        public String getProjectType() {
            return projectType;
        }

        /**
         * プロジェクト種別 を設定する。
         *
         * @param projectType プロジェクト種別
         */
        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }
    }

    /**
     * プロジェクト分類Bean
     */
    private static class ProjectClass implements Serializable {
        /**
         * プロジェクト分類
         */
        @Domain("projectClass")
        private String projectClass;

        /**
         * プロジェクト分類を取得する。
         *
         * @return プロジェクト分類
         */
        public String getProjectClass() {
            return projectClass;
        }

        /**
         * プロジェクト分類を設定する。
         *
         * @param projectClass プロジェクト分類
         */
        public void setProjectClass(String projectClass) {
            this.projectClass = projectClass;
        }
    }
}
