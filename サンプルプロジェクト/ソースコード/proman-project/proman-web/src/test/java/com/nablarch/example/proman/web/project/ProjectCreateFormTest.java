package com.nablarch.example.proman.web.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * ProjectCreateFormのテスト
 */
class ProjectCreateFormTest {

    /**
     * 正常系（プロジェクト開始日＜プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriod() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/02");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "プロジェクト開始日＜プロジェクト終了日");
    }

    /**
     * 正常系（プロジェクト開始日＝プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodEqual() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "プロジェクト開始日＝プロジェクト終了日");
    }

    /**
     * 異常系（プロジェクト開始日＞プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodSmallerThanTo() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/02");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertFalse(result, "プロジェクト開始日＞プロジェクト終了日");
    }
}
