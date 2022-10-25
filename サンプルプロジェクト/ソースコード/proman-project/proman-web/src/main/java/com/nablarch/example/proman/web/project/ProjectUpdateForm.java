package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.util.DateRelationUtil;
import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;

/**
 * プロジェクト更新フォーム。
 *
 * @author Masaya Seko
 */
public class ProjectUpdateForm implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * プロジェクト名
     */
    @Required
    @Domain("projectName")
    private String projectName;

    /**
     * プロジェクト種別
     */
    @Required
    @Domain("projectType")
    private String projectType;

    /**
     * プロジェクト分類
     */
    @Required
    @Domain("projectClass")
    private String projectClass;

    /**
     * プロジェクト開始日付
     */
    @Required
    @Domain("date")
    private String projectStartDate;

    /**
     * プロジェクト終了日付
     */
    @Required
    @Domain("date")
    private String projectEndDate;

    /**
     * 事業部ID
     */
    @Required
    @Domain("organizationId")
    private String divisionId;

    /**
     * 部門ID
     */
    @Required
    @Domain("organizationId")
    private String organizationId;

    /**
     * 顧客ID
     */
    //TODO:顧客選択が実装されたら必須にする。
    //@Required
    @Domain("clientId")
    private String clientId;

    /**
     * プロジェクトマネージャ名
     */
    @Required
    @Domain("userName")
    private String pmKanjiName;

    /**
     * プロジェクトリーダー名
     */
    @Required
    @Domain("userName")
    private String plKanjiName;

    /**
     * 備考
     */
    @Domain("note")
    private String note;

    /**
     * 売上高
     */
    @Domain("amountOfMoney")
    private String salesAmount;

    /**
     * プロジェクト名を取得する。
     *
     * @return プロジェクト名
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * プロジェクト名を設定する。
     *
     * @param projectName 設定するプロジェクト名
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    /**
     * プロジェクト種別を取得する。
     *
     * @return プロジェクト種別
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * プロジェクト種別を設定する。
     *
     * @param projectType 設定するプロジェクト種別
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

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
     * @param projectClass 設定するプロジェクト分類
     */
    public void setProjectClass(String projectClass) {
        this.projectClass = projectClass;
    }

    /**
     * プロジェクト開始日付を取得する。
     *
     * @return プロジェクト開始日付
     */
    public String getProjectStartDate() {
        return projectStartDate;
    }

    /**
     * プロジェクト開始日付を設定する。
     *
     * @param projectStartDate 設定するプロジェクト開始日付
     */
    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    /**
     * プロジェクト終了日付を取得する。
     *
     * @return プロジェクト終了日付
     */
    public String getProjectEndDate() {
        return projectEndDate;
    }

    /**
     * プロジェクト終了日付を設定する。
     *
     * @param projectEndDate 設定するプロジェクト終了日付
     */
    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    /**
     * 事業部IDを取得する。
     *
     * @return 事業部ID
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * 事業部IDを設定する。
     *
     * @param divisionId 設定する事業部ID
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * 部門IDを取得する。
     *
     * @return 部門ID
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 部門IDを設定する。
     *
     * @param organizationId 設定する部門ID
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 顧客IDを取得する。
     *
     * @return 顧客ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 顧客IDを設定する。
     *
     * @param clientId 設定する顧客ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * プロジェクトマネージャ名を取得する。
     *
     * @return プロジェクトマネージャ名
     */
    public String getPmKanjiName() {
        return pmKanjiName;
    }

    /**
     * プロジェクトマネージャ名を設定する。
     *
     * @param pmKanjiName 設定するプロジェクトマネージャ名
     */
    public void setPmKanjiName(String pmKanjiName) {
        this.pmKanjiName = pmKanjiName;
    }

    /**
     * プロジェクトリーダー名を取得する。
     *
     * @return プロジェクトリーダー名
     */
    public String getPlKanjiName() {
        return plKanjiName;
    }

    /**
     * プロジェクトリーダー名を設定する。
     *
     * @param plKanjiName 設定するプロジェクトリーダー名
     */
    public void setPlKanjiName(String plKanjiName) {
        this.plKanjiName = plKanjiName;
    }

    /**
     * 備考を取得する。
     *
     * @return 備考
     */
    public String getNote() {
        return note;
    }

    /**
     * 備考を設定する。
     *
     * @param note 設定する備考
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 売上高を取得する。
     *
     * @return 売上高
     */
    public String getSalesAmount() {
        return salesAmount;
    }

    /**
     * 売上高を設定する。
     *
     * @param sales 設定する売上高
     */
    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }

    /**
     * プロジェクト期間（プロジェクト開始日～プロジェクト終了日）正しく設定されているかを判定します。
     * 開始日に終了日より後の日付が設定されていた場合はプロジェクト期間が正しくないと判定します。
     * それ以外の場合は問題なしとします。
     *
     * @return 開始日に終了日より後の日付が設定されていた場合は false それ以外（開始日、終了日の両方又はいずれかが未定の場合も含む）は true
     */
    @AssertTrue(message = "{com.nablarch.example.app.entity.core.validation.validator.DateRelationUtil.message}")
    public boolean isValidProjectPeriod() {
        return DateRelationUtil.isValid(projectStartDate, projectEndDate);
    }
}
