package com.nablarch.example.proman.web.project;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import com.nablarch.example.proman.web.common.FormTestSupport;

/**
 * ProjectSearchFormのテスト
 */
class ProjectSearchFormTest extends FormTestSupport<ProjectSearchForm> {

    /**
     * 正常系（売上高From＜売上高To）
     */
    @Test
    void testIsValidProjectSalesRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("100");
        form.setSalesTo("1000");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectSalesRange", form);
        assertTrue("売上高From＜売上高To", result);
    }

    /**
     * 正常系（プロジェクト開始日From＜プロジェクト開始日To）
     */
    @Test
    void testIsValidProjectStartDateRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/01");
        form.setProjectStartDateTo("2022/01/31");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectStartDateRange", form);
        assertTrue("プロジェクト開始日From＜プロジェクト開始日To", result);
    }

    /**
     * 正常系（プロジェクト終了日From＜プロジェクト終了日To）
     */
    @Test
    void testIsValidProjectEndDateRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/01");
        form.setProjectEndDateTo("2022/01/31");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectEndDateRange", form);
        assertTrue("プロジェクト終了日From＜プロジェクト終了日To", result);
    }

    
    /**
     * 正常系（売上高From＝売上高To）
     */
    @Test
    void testIsValidProjectSalesRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1000");
        form.setSalesTo("1000");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectSalesRange", form);
        assertTrue("売上高From＝売上高To", result);
    }

    /**
     * 正常系（プロジェクト開始日From＝プロジェクト開始日To）
     */
    @Test
    void testIsValidProjectStartDateRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/01");
        form.setProjectStartDateTo("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectStartDateRange", form);
        assertTrue("プロジェクト開始日From＝プロジェクト開始日To", result);
    }

    /**
     * 正常系（プロジェクト終了日From＝プロジェクト終了日To）
     */
    @Test
    void testIsValidProjectEndDateRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/01");
        form.setProjectEndDateTo("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectEndDateRange", form);
        assertTrue("プロジェクト終了日From＝プロジェクト終了日To", result);
    }

    /**
     * 異常系（売上高From＜売上高To）
     */
    @Test
    void testIsValidProjectSalesRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1000");
        form.setSalesTo("100");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectSalesRange", form);
        assertFalse("売上高From＞売上高To", result);
    }

    /**
     * 異常系（プロジェクト開始日From＜プロジェクト開始日To）
     */
    @Test
    void testIsValidProjectStartDateRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/31");
        form.setProjectStartDateTo("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectStartDateRange", form);
        assertFalse("プロジェクト開始日From＞プロジェクト開始日To", result);
    }

    /**
     * 異常系（プロジェクト終了日From＜プロジェクト終了日To）
     */
    @Test
    void testIsValidProjectEndDateRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/31");
        form.setProjectEndDateTo("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectSearchForm.class, "isValidProjectEndDateRange", form);
        assertFalse("プロジェクト終了日From＞プロジェクト終了日To", result);
    }
}
