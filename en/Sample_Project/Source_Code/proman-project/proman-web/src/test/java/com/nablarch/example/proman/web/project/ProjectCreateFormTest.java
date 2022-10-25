package com.nablarch.example.proman.web.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test ProjectCreateForm
 */
class ProjectCreateFormTest {

    /**
     * Normal test (project start date < project end date)
     */
    @Test
    void testIsValidProjectPeriod() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/02");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "project start date < project end date");
    }

    /**
     * Normal test (project start date = project end date)
     */
    @Test
    void testIsValidProjectPeriodEqual() {
        ProjectCreateForm form = new ProjectCreateForm();
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
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/02");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertFalse(result, "project start date > project end date");
    }
}
