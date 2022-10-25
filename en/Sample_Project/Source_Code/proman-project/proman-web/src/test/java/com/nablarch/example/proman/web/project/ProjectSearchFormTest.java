package com.nablarch.example.proman.web.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test ProjectSearchForm
 */
class ProjectSearchFormTest {

    /**
     * Normal test (project start date From < project start date To)
     */
    @Test
    void testIsValidProjectStartDateRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/01");
        form.setProjectStartDateTo("2022/01/02");
        boolean result = form.isValidProjectStartDateRange();
        assertTrue(result, "project start date From < project start date To");
    }

    /**
     * Normal test (project end date From < project end date To)
     */
    @Test
    void testIsValidProjectEndDateRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/01");
        form.setProjectEndDateTo("2022/01/02");
        boolean result = form.isValidProjectEndDateRange();
        assertTrue(result, "project end date From < project end date To");
    }

    
    /**
     * Normal test (sales From < sales To)
     */
    @Test
    void testIsValidProjectSalesRange() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1000");
        form.setSalesTo("1001");
        boolean result = form.isValidProjectSalesRange();
        assertTrue(result, "sales From < sales To");
    }

    /**
     * Normal test (project start date From = project start date To)
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
     * Normal test (project end date From = project end date To)
     */
    @Test
    void testIsValidProjectEndDateRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/01");
        form.setProjectEndDateTo("2022/01/01");
        boolean result = form.isValidProjectEndDateRange();
        assertTrue(result, "project end date From = project end date To");
    }

    /**
     * Normal test (sales From = sales To)
     */
    @Test
    void testIsValidProjectSalesRangeEqual() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1000");
        form.setSalesTo("1000");
        boolean result = form.isValidProjectSalesRange();
        assertTrue(result, "sales From = sales To");
    }

    /**
     * Anomaly testing (project start date From > project start date To)
     */
    @Test
    void testIsValidProjectStartDateRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectStartDateFrom("2022/01/02");
        form.setProjectStartDateTo("2022/01/01");
        boolean result = form.isValidProjectStartDateRange();
        assertFalse(result, "project start date From > project start date To");
    }

    /**
     * Anomaly testing (project end date From > project end date To)
     */
    @Test
    void testIsValidProjectEndDateRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setProjectEndDateFrom("2022/01/02");
        form.setProjectEndDateTo("2022/01/01");
        boolean result = form.isValidProjectEndDateRange();
        assertFalse(result, "project end date From > project end date To");
    }

    /**
     * Anomaly testing (sales From > sales To)
     */
    @Test
    void testIsValidProjectSalesRangeSmallerThanTo() {
        ProjectSearchForm form = new ProjectSearchForm();
        form.setSalesFrom("1001");
        form.setSalesTo("1000");
        boolean result = form.isValidProjectSalesRange();
        assertFalse(result, "sales From > sales To");
    }
}
