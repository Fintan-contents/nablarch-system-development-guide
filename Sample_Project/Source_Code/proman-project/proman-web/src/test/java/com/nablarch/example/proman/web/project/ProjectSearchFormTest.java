package com.nablarch.example.proman.web.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * ProjectSearchFormのテスト
 */
class ProjectSearchFormTest {

    /**
     * 正常系（プロジェクト開始日From＜プロジェクト開始日To）
     */
    @Test
    void testIsValidProjectStartDateRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/01");
        form.setProjectStartDateTo("2022/01/02");
        boolean result = form.isValidProjectStartDateRange();
        assertTrue(result, "プロジェクト開始日From＜プロジェクト開始日To");
    }

    /**
     * 正常系（プロジェクト終了日From＜プロジェクト終了日To）
     */
    @Test
    void testIsValidProjectEndDateRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/01");
        form.setProjectEndDateTo("2022/01/02");
        boolean result = form.isValidProjectEndDateRange();
        assertTrue(result, "プロジェクト終了日From＜プロジェクト終了日To");
    }

    
    /**
     * 正常系（売上高From＜売上高To）
     */
    @Test
    void testIsValidProjectSalesRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1000");
        form.setSalesTo("1001");
        boolean result = form.isValidProjectSalesRange();
        assertTrue(result, "売上高From＜売上高To");
    }

    /**
     * 正常系（プロジェクト開始日From＝プロジェクト開始日To）
     */
    @Test
    void testIsValidProjectStartDateRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/01");
        form.setProjectStartDateTo("2022/01/01");
        boolean result = form.isValidProjectStartDateRange();
        assertTrue(result, "プロジェクト開始日From＝プロジェクト開始日To");
    }

    /**
     * 正常系（プロジェクト終了日From＝プロジェクト終了日To）
     */
    @Test
    void testIsValidProjectEndDateRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/01");
        form.setProjectEndDateTo("2022/01/01");
        boolean result = form.isValidProjectEndDateRange();
        assertTrue(result, "プロジェクト終了日From＝プロジェクト終了日To");
    }

    /**
     * 正常系（売上高From＝売上高To）
     */
    @Test
    void testIsValidProjectSalesRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1000");
        form.setSalesTo("1000");
        boolean result = form.isValidProjectSalesRange();
        assertTrue(result, "売上高From＝売上高To");
    }

    /**
     * 異常系（プロジェクト開始日From＞プロジェクト開始日To）
     */
    @Test
    void testIsValidProjectStartDateRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/02");
        form.setProjectStartDateTo("2022/01/01");
        boolean result = form.isValidProjectStartDateRange();
        assertFalse(result, "プロジェクト開始日From＞プロジェクト開始日To");
    }

    /**
     * 異常系（プロジェクト終了日From＞プロジェクト終了日To）
     */
    @Test
    void testIsValidProjectEndDateRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/02");
        form.setProjectEndDateTo("2022/01/01");
        boolean result = form.isValidProjectEndDateRange();
        assertFalse(result, "プロジェクト終了日From＞プロジェクト終了日To");
    }

    /**
     * 異常系（売上高From＞売上高To）
     */
    @Test
    void testIsValidProjectSalesRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1001");
        form.setSalesTo("1000");
        boolean result = form.isValidProjectSalesRange();
        assertFalse(result, "売上高From＞売上高To");
    }
}
