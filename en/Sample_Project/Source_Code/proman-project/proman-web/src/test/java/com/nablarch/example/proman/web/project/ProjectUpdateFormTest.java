package com.nablarch.example.proman.web.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * ProjectUpdateFormのテスト
 *
 */
class ProjectUpdateFormTest {

    /**
     * Normal test (project start date < project end date)
     */
    @Test
    void testIsValidProjectPeriod() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/31");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "project start date < project end date");
    }

    /**
     * Normal test (project start date = project end date)
     */
    @Test
    void testIsValidProjectPeriodEqual() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "project start date = project end date");
    }

    /**
     * Anomaly testing (project start date > project end date)
     */
    @Test
    void testIsValidProjectPeriodSmallerThanTo() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/31");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertFalse(result, "project start date > project end date");
    }
}
